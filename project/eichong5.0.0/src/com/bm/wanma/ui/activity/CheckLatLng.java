package com.bm.wanma.ui.activity;

import java.text.DecimalFormat;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.OnCameraChangeListener;
import com.amap.api.maps.AMap.OnMapTouchListener;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.Projection;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.GeocodeSearch.OnGeocodeSearchListener;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.bm.wanma.R;
import com.bm.wanma.entity.PoiAdcodeBean;
import com.bm.wanma.entity.PoiLatLngBean;
import com.bm.wanma.utils.PreferencesUtil;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TextView;

public class CheckLatLng extends BaseActivity implements OnCameraChangeListener,OnMapTouchListener,
			OnGeocodeSearchListener,OnClickListener,TextWatcher {
	
	private MapView mapView;
	private AMap aMap;
	private String currentlat,currentlng;
	private String correctlat,correctlng;
	private String addressName;
	//private Marker myMarker;
	private GeocodeSearch geocoderSearch;
	private LatLonPoint latLonPoint;
	private TextView tv_confirm,tv_addr,tv_lng,tv_lat,tv_search;
	private AutoCompleteTextView act_search;
	private ImageButton ib_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_check_latlng);
		geocoderSearch = new GeocodeSearch(this); 
		geocoderSearch.setOnGeocodeSearchListener(this);
		
		tv_addr = (TextView)findViewById(R.id.check_latlng_display_addr);
		tv_lng = (TextView)findViewById(R.id.check_latlng_display_lng);
		tv_lat = (TextView)findViewById(R.id.check_latlng_display_lat);
		tv_confirm = (TextView)findViewById(R.id.check_latlng_commit);
		tv_confirm.setOnClickListener(this);
		ib_back = (ImageButton) findViewById(R.id.check_latlng_title_back);
		ib_back.setOnClickListener(this);
		tv_search = (TextView) findViewById(R.id.check_latlng_title_search);
		tv_search.setOnClickListener(this);
		act_search = (AutoCompleteTextView) findViewById(R.id.check_latlng_title_et);
		//act_search.addTextChangedListener(this);// 添加文本输入框监听事件
		act_search.setOnClickListener(this);
		currentlat = PreferencesUtil.getStringPreferences(this, "currentlat");
		currentlng = PreferencesUtil.getStringPreferences(this, "currentlng");
		mapView = (MapView) findViewById(R.id.check_latlng_map);
		mapView.onCreate(savedInstanceState);
		if (aMap == null) {
			aMap = mapView.getMap();
		}
		
		aMap.setOnCameraChangeListener(this);
		aMap.setOnMapTouchListener(this);
		move2currentLatLng(currentlat,currentlng);
		
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.check_latlng_commit:
			//确定
			Intent data = new Intent();
			data.putExtra("correctlat", correctlat);
			data.putExtra("correctlng", correctlng);
			data.putExtra("addressName", addressName);
			setResult(RESULT_OK, data);
			finish();
			break;
		case R.id.check_latlng_title_back:
			finish();
			break;
		case R.id.check_latlng_title_et:
			Intent in = new Intent();
			in.setClass(CheckLatLng.this, PoiKeywordSearchActivity.class);
			startActivityForResult(in, 0x87);
			break;
		default:
			break;
		}
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(0x87 == requestCode && resultCode == RESULT_OK){
			PoiLatLngBean result = (PoiLatLngBean) data.getSerializableExtra("poikeywrod");			
			String resultLat = result.getLat();
			String resultLng = result.getLng();
			move2currentLatLng(resultLat, resultLng);
			getAddress(new LatLonPoint(Double.valueOf(resultLat), Double.valueOf(resultLng)));
			//地理编码
			/*GeocodeQuery query = new GeocodeQuery(result.getTitle(),result.getAdcode()); 
			geocoderSearch.getFromLocationNameAsyn(query);*/
		}else if(0x87 == requestCode && resultCode == RESULT_FIRST_USER){
			
			PoiAdcodeBean bean = (PoiAdcodeBean) data.getSerializableExtra("poikeywrod");
			Log.i("cm_socket", bean.getTitle()+"adcode"+bean.getAdcode());
			GeocodeQuery query = new GeocodeQuery(bean.getTitle(),bean.getAdcode()); 
			geocoderSearch.getFromLocationNameAsyn(query);
			
		}

	}
	
	private void move2currentLatLng(String lat,String lng){
		aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
				new LatLng(Double.parseDouble(lat),
		Double.parseDouble(lng)), 16));	
	}
	

	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}
	/**
	 * marker点击时跳动一下
	 */
	public void jumpPoint(final Marker marker,final LatLng startL) {
		final Handler jhandler = new Handler();
		final long start = SystemClock.uptimeMillis();
		Projection proj = aMap.getProjection();
		Point startPoint = proj.toScreenLocation(startL);
		startPoint.offset(0, -50);
		final LatLng startLatLng = proj.fromScreenLocation(startPoint);
		final long duration = 1000;

		final Interpolator interpolator = new BounceInterpolator();
		jhandler.post(new Runnable() {
			@Override
			public void run() {
				long elapsed = SystemClock.uptimeMillis() - start;
				float t = interpolator.getInterpolation((float) elapsed
						/ duration);
				double lng = t * startL.longitude + (1 - t)
						* startLatLng.longitude;
				double lat = t * startL.latitude + (1 - t)
						* startLatLng.latitude;
				marker.setPosition(new LatLng(lat, lng));
				
				if ( t < 1.0) {
					jhandler.postDelayed(this,16);
				}
			}
		});
	}

	@Override
	public void onTouch(MotionEvent arg0) {
		
	}

	//移动地图时回调
	@Override
	public void onCameraChange(CameraPosition arg0) {
		// 获取当前地图中心点的坐标
		LatLng mTarget = aMap.getCameraPosition().target;
		//myMarker.setPosition(mTarget);
		
		/*BigDecimal bd = new BigDecimal(mTarget.latitude);
		bd.setScale(6,BigDecimal.ROUND_HALF_UP).doubleValue();*/
		DecimalFormat myformat = new DecimalFormat("#.000000");
		correctlat = myformat.format(mTarget.latitude); 
		correctlng = myformat.format(mTarget.longitude);
		tv_lat.setText(correctlat);
		tv_lng.setText(correctlng);
		//tv_addr.setText(addressName);
		
	}
	//移动地图结束回调
	@Override
	public void onCameraChangeFinish(CameraPosition arg0) {
		LatLng mTarget = aMap.getCameraPosition().target;
		/*if (isJump) {
			LatLng mTarget = aMap.getCameraPosition().target;
			jumpPoint(myMarker, mTarget);
		}
		isJump = true;*/
		latLonPoint = new LatLonPoint(mTarget.latitude, mTarget.longitude);
		getAddress(latLonPoint);
	}
	
	/**
	 * 响应逆地理编码
	 */
	public void getAddress(final LatLonPoint latLonPoint) {
		//showDialog();
		RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200,
				GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
		geocoderSearch.getFromLocationAsyn(query);// 设置同步逆地理编码请求
	}
	
	//地理编码结果回调
	@Override
	public void onGeocodeSearched(GeocodeResult result, int rCode) {
		if (rCode == 0) {
			if (result != null && result.getGeocodeAddressList() != null
					&& result.getGeocodeAddressList().size() > 0) {
				GeocodeAddress address = result.getGeocodeAddressList().get(0);
				
				aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
						convertToLatLng(address.getLatLonPoint()), 14));
				
				addressName = "经纬度值:" + address.getLatLonPoint() + "\n位置描述:"
						+ address.getFormatAddress();
		}
	  } else {
			Log.i("cm_socket", "地理编码错误");
		}
	}
	//逆地理编码回调接口
	@Override
	public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
		
		if (rCode == 0) {
			if (result != null && result.getRegeocodeAddress() != null
					&& result.getRegeocodeAddress().getFormatAddress() != null) {
				addressName = result.getRegeocodeAddress().getFormatAddress();
						//+ "附近";
				tv_addr.setText(addressName);
			} 
		} 
	}

	
	@Override
	protected void getData() {

	}

	@Override
	public void onSuccess(String sign, Bundle bundle) {

	}

	@Override
	public void onFaile(String sign, Bundle bundle) {

	}
	
	
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 把LatLonPoint对象转化为LatLon对象
	 */
	public  LatLng convertToLatLng(LatLonPoint latLonPoint) {
		return new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude());
	}


}
