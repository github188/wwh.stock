package com.bm.wanma.popup;

import java.lang.reflect.Method;
import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.bm.wanma.R;
import com.bm.wanma.entity.MapModeBean;
import com.bm.wanma.ui.activity.StartActivity;
import com.bm.wanma.ui.activity.StationStiltDetailActivity;
import com.bm.wanma.ui.navigation.NaviCustomActivity;
import com.bm.wanma.ui.navigation.TTSController;
import com.bm.wanma.ui.navigation.Utils;
import com.bm.wanma.utils.PreferencesUtil;
import com.bm.wanma.utils.Tools;
import com.tencent.stat.StatAppMonitor;

/**
 * @author cm
 *点击图标，底层弹出详情框
 */
public class AnchorPopupWindow extends BasePopupWindow implements OnClickListener,AMapNaviListener{
	private Context mContext;
	// 起点终点列表
	private ArrayList<NaviLatLng> mStartPoints = new ArrayList<NaviLatLng>();
	private ArrayList<NaviLatLng> mEndPoints = new ArrayList<NaviLatLng>();
	private ProgressDialog mRouteCalculatorProgressDialog;// 路径规划过程显示状态
	private Double startGeoLat,startGeoLng;
	private String markerLat,markerLng;
	private Double endEdoLat,endEdoLng;
	private View content;
	private TextView tv_name,tv_distance,tv_price,tv_dc,tv_ac,tv_nav;
	private LayoutInflater inflater;
	private MapModeBean mapModeBean;
	
	public AnchorPopupWindow(Context context,MapModeBean bean) {
		super(context);
		this.mContext = context;
		mapModeBean = bean;
		inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		content = inflater.inflate(R.layout.popup_anchor_summary, null);
		tv_nav = (TextView) content.findViewById(R.id.anchor_viewpage_nav);
		tv_nav.setOnClickListener(this);
		tv_name = (TextView) content.findViewById(R.id.anchor_viewpage_name);
		tv_distance = (TextView) content.findViewById(R.id.anchor_viewpage_distance);
		tv_price = (TextView) content.findViewById(R.id.anchor_viewpage_price);
		tv_dc = (TextView) content.findViewById(R.id.anchor_viewpage_dc);
		tv_ac = (TextView) content.findViewById(R.id.anchor_viewpage_ac);
		content.findViewById(R.id.pop_detail_layout).setOnClickListener(this);
		content.findViewById(R.id.anchor_viewpage_close).setOnClickListener(this);
		initData(bean);
        this.setContentView(content);
        this.setWidth(LayoutParams.MATCH_PARENT);
        this.setHeight(LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        try {
           Method  method = PopupWindow.class.getDeclaredMethod("setTouchModal",
                    boolean.class);
            method.setAccessible(true);
            method.invoke(this, false);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        this.setAnimationStyle(R.style.AnimBottom);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        dw.setAlpha(200);
        this.setBackgroundDrawable(dw);
       // this.setOutsideTouchable(true);  
     
	}
	
	public void initData(MapModeBean data){
		if(data != null){
			mapModeBean = data;
			tv_name.setText(""+data.getPoStName());
			tv_distance.setText(Tools.getMeterOrKM(data.getDistance()));
			tv_price.setText(data.getCurrentRate()+"元/度");
			tv_dc.setText(""+data.getDc());
			tv_ac.setText(""+data.getAc());
		}
	}
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pop_detail_layout:
			//跳转到详情
			Intent detail = new Intent(mContext,StationStiltDetailActivity.class);
			detail.putExtra("mapModeBean", mapModeBean);
			mContext.startActivity(detail);
			break;
		case R.id.anchor_viewpage_close:
			//关闭弹框
			dismiss();
			break;
		case R.id.anchor_viewpage_nav:
			if(isNetConnection()){
				initNavigation();
				// 启动FPS导航
				if (AMapNavi.getInstance(mContext) != null) {
					AMapNavi.getInstance(mContext)
							.calculateDriveRoute(mStartPoints,
									mEndPoints, null,
									AMapNavi.DrivingDefault);
					mRouteCalculatorProgressDialog.show();
				}
			}else {
				showToast("亲，网络不稳，请检查网络连接!");
			}
			break;
		default:
			break;
		}
		
	}

	// 初始化导航
	private void initNavigation() {
		TTSController.getInstance(mContext).startSpeaking();
		// 获取当前经纬度
		String geoLat = PreferencesUtil.getStringPreferences(mContext,
				"currentlat");
		String geoLng = PreferencesUtil.getStringPreferences(mContext,
				"currentlng");
		startGeoLat = Double.parseDouble(geoLat);
		startGeoLng = Double.parseDouble(geoLng);
		// 获取目的地经纬度
		markerLat = mapModeBean.getPoStLatitude();
		markerLng = mapModeBean.getPoStLongitude();
		if (!Tools.isEmptyString(markerLng) && !Tools.isEmptyString(markerLat)) {
			endEdoLat = Double.parseDouble(markerLat.trim());
			endEdoLng = Double.parseDouble(markerLng.trim());
			NaviLatLng mNaviStart = new NaviLatLng(startGeoLat, startGeoLng);
			NaviLatLng mNaviEnd = new NaviLatLng(endEdoLat, endEdoLng);
			mStartPoints.clear();
			mEndPoints.clear();
			mStartPoints.add(mNaviStart);
			mEndPoints.add(mNaviEnd);
		}

		mRouteCalculatorProgressDialog = new ProgressDialog(mContext);
		mRouteCalculatorProgressDialog.setCancelable(true);
		AMapNavi aMapNavi = AMapNavi.getInstance(mContext);
		if (this instanceof AMapNaviListener && aMapNavi != null) {
			aMapNavi.setAMapNaviListener(this);
		}

	}
	


	@Override
	public void onArriveDestination() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onArrivedWayPoint(int arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onCalculateRouteFailure(int arg0) {
		mRouteCalculatorProgressDialog.dismiss();
		
	}
	@Override
	public void onCalculateRouteSuccess() {
		mRouteCalculatorProgressDialog.dismiss();
		Intent intent = new Intent(mContext, NaviCustomActivity.class);
		//Intent intent = new Intent(StationDetailActivity.this, SimpleNaviActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		Bundle bundle = new Bundle();
		bundle.putInt(Utils.ACTIVITYINDEX, Utils.ANCHORSUMMARY);
		intent.putExtras(bundle);
		mContext.startActivity(intent);
		
	}
	@Override
	public void onEndEmulatorNavi() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onGetNavigationText(int arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onGpsOpenStatus(boolean arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onInitNaviFailure() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onInitNaviSuccess() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onLocationChange(AMapNaviLocation arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onNaviInfoUpdate(NaviInfo arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	@Deprecated
	public void onNaviInfoUpdated(AMapNaviInfo arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onReCalculateRouteForTrafficJam() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onReCalculateRouteForYaw() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onStartNavi(int arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onTrafficStatusUpdate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSuccess(String sign, Bundle bundle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFaile(String sign, Bundle bundle) {
		// TODO Auto-generated method stub
		
	}


}
