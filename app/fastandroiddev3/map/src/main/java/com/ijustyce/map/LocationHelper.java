package com.ijustyce.map;

import android.app.Activity;
import android.os.Build;

import com.baidu.location.Address;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.ijustyce.fastandroiddev3.baseLib.utils.IJson;
import com.ijustyce.fastandroiddev3.baseLib.utils.ILog;
import com.ijustyce.fastandroiddev3.baseLib.utils.RunTimePermission;
import com.ijustyce.fastandroiddev3.baseLib.utils.TaskUtils;
import com.ijustyce.fastandroiddev3.contentprovider.CommonData;

import java.lang.ref.WeakReference;

/**
 * Created by yc on 17-5-7.
 */

public class LocationHelper implements BDLocationListener {

    private LocationClient mLocationClient;
    private WeakReference<LocationListener> mListener;
    private int errorTime = 0;
    private static final int MAX_RETRY = 10;

    public LocationHelper(Activity activity) {
        this(activity, null);
    }

    public LocationHelper(Activity activity, LocationListener listener) {
        checkPermission(activity);

        LocationClientOption option = new LocationClientOption();
        buildLocationOption(option);

        mLocationClient = new LocationClient(activity, option);
        if (listener != null) {
            mListener = new WeakReference<>(listener);
        }
        mLocationClient.registerLocationListener(this);
    }

    private boolean checkPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity != null) {
            RunTimePermission runTimePermission = new RunTimePermission(activity);
            if (!runTimePermission.checkPermissionForLocation()) {
                runTimePermission.requestPermissionForLocation();
                return false;
            }
        }
        return true;
    }

    // 这个方法，用于设置请求参数
    private void buildLocationOption(LocationClientOption option) {
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式
        option.setCoorType("gcj02");// 返回的定位结果是百度经纬度，默认值gcj02
        option.setScanSpan(5_0000);// 设置发起定位请求的间隔时间为5000ms
        option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
        option.setNeedDeviceDirect(false);// 返回的定位结果包含手机机头的方向
    }

    private void getFromCache() {
        new TaskUtils<BDLocation>() {
            @Override
            protected void onPostExecute(BDLocation bean) {
                updateLocation(bean);
            }

            @Override
            protected BDLocation doInBackground(Integer... params) {
                String result = CommonData.get("user_location");
                LocationBean locationBean = IJson.fromJson(result, LocationBean.class);
                if (locationBean == null) return null;
                BDLocation bdLocation = new BDLocation();
                bdLocation.setLatitude(locationBean.latitude);
                bdLocation.setLongitude(locationBean.longitude);
                Address address = new Address.Builder().city(locationBean.city).build();
                bdLocation.setAddr(address);
                return isLocationValid(bdLocation) ? bdLocation : null;
            }
        }.execute();
    }

    public void request() {
        getFromCache();
        mLocationClient.start();
        // 请求定位
        mLocationClient.requestLocation();
        ILog.d("请求定位!!!!!!!!!!");
    }

    private void stopLocation() {
        mListener = null;
        mLocationClient.stop();
        mLocationClient.unRegisterLocationListener(this);
    }

    private boolean saveLocation(BDLocation location) {
        if (!isLocationValid(location)) return false;
        LocationBean locationBean = new LocationBean();
        locationBean.longitude = location.getLongitude();
        locationBean.latitude = location.getLatitude();
        locationBean.city = location.getCity();
        Address address = location.getAddress();
        if (address != null) {
            locationBean.address = address.city + address.district + address.street;
            locationBean.address = locationBean.address.replaceAll("null", "");
            locationBean.fullAddress = address.address;
        }
        CommonData.put("user_location", IJson.toJson(locationBean, LocationBean.class));
        return true;
    }

    // 注册了监听函数以后，在这里，可以接收到定位结果

    private void updateLocation(BDLocation location) {
        if (isLocationValid(location) && mListener != null && mListener.get() != null) {
            mListener.get().onUpdateLocation(location);
        }
    }

    @Override
    public void onReceiveLocation(BDLocation location) {
        updateLocation(location);
        if (saveLocation(location) || errorTime > MAX_RETRY) {
            stopLocation();
        } else {
            ILog.e("===location===", "get location failed ...");
            errorTime++;
        }
    }

    @Override
    public void onConnectHotSpotMessage(String s, int i) {
        ILog.e("===location===", s);
    }

    public static boolean isLocationValid(BDLocation location) {
        return location != null && location.getLongitude() != 0 && location.getLatitude() != 0
                && location.getLongitude() != 4.9E-324 && location.getLatitude() != 4.9E-324;
    }

    public interface LocationListener {
        void onUpdateLocation(BDLocation location);
    }
}