package com.bm.wanma.adapter;

import java.util.ArrayList;

import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.AMapNaviViewListener;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.bm.wanma.R;
import com.bm.wanma.dialog.CancleBespokeDialog;
import com.bm.wanma.entity.CarRepairBean;
import com.bm.wanma.entity.EmergencyCallBean;
import com.bm.wanma.entity.ListModeBean;
import com.bm.wanma.entity.MyDynamicListBean;
import com.bm.wanma.entity.MyNewsSystemBean;
import com.bm.wanma.ui.navigation.NaviCustomActivity;
import com.bm.wanma.ui.navigation.TTSController;
import com.bm.wanma.ui.navigation.Utils;
import com.bm.wanma.utils.PreferencesUtil;
import com.bm.wanma.utils.Tools;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author cm
 * 车俩维修 listview适配器
 *
 */
public class CarRepairAdapter extends BaseAdapter implements
AMapNaviListener, AMapNaviViewListener{
	private Context mContext;
	private ArrayList<CarRepairBean> mdata;
	private LayoutInflater inflater;
	private CarRepairBean bean;
	private CancleBespokeDialog cancleBespokeDialog;
	// 起点终点列表
		private ArrayList<NaviLatLng> mStartPoints = new ArrayList<NaviLatLng>();
		private ArrayList<NaviLatLng> mEndPoints = new ArrayList<NaviLatLng>();
		private ProgressDialog mRouteCalculatorProgressDialog;// 路径规划过程显示状态
		private String geoLat,geoLng,edoLat,edoLng;
		private Double startGeoLat,startGeoLng,endEdoLat,endEdoLng;
		private String pkUserinfo;
	
	public CarRepairAdapter(Context context,ArrayList<CarRepairBean> data) {
		this.mContext = context;
		this.mdata = data;
		inflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return mdata.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		TextView tv_name = null;
		TextView tv_addr = null;
		TextView tv_distance = null;
		TextView tv_tel = null;
		TextView tv_nav = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.listview_item_car_repair, null);
			tv_name = (TextView)convertView.findViewById(R.id.listview_item_car_repair_name);
			tv_addr = (TextView)convertView.findViewById(R.id.listview_item_car_repair_addr);
			tv_distance = (TextView)convertView.findViewById(R.id.listview_item_car_repair_distance);
			tv_tel = (TextView)convertView.findViewById(R.id.listview_item_car_repair_tel);
			tv_nav = (TextView)convertView.findViewById(R.id.listview_item_car_repair_nav);
			convertView.setTag(new MyHold(tv_name,tv_addr,tv_distance,tv_tel,tv_nav));
			
		}else {
			MyHold hold = (MyHold) convertView.getTag();
			tv_name = hold.hold_tv_name;
			tv_addr = hold.hold_tv_addr;
			tv_distance = hold.hold_tv_dist;
			tv_tel = hold.hold_tv_tel;
			tv_nav = hold.hold_tv_nav;
		} 
		bean = mdata.get(position);
		if(bean != null){
			tv_name.setText(""+bean.getName());
			tv_addr.setText(""+bean.getAddress());
			String tempdis = bean.getDistance();
			tempdis = Tools.getMeterOrKM(tempdis);
			tv_distance.setText(tempdis);
			tv_tel.setText(bean.getPhone());
			tv_tel.setTag(bean.getPhone());
			tv_tel.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					//联系电话
					final String tel = (String) v.getTag();
					cancleBespokeDialog = new CancleBespokeDialog(mContext,tel);
					cancleBespokeDialog.setCancelable(false);
					cancleBespokeDialog.setButtonTitle("呼叫", "取消");
			        cancleBespokeDialog.setOnPositiveListener(new OnClickListener() {
				        @Override
				        public void onClick(View v) {
				        	//拨打电话
				        	Intent telintent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+tel));//直接拨打
				        	//Intent telintent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+TEL));
							telintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
							mContext.startActivity(telintent);
				        	cancleBespokeDialog.dismiss();
				        }
				    });
			        cancleBespokeDialog.setOnNegativeListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							cancleBespokeDialog.dismiss();
						}
					});
			        cancleBespokeDialog.show();
				}
			});
			//点击进入导航
			tv_nav.setTag(bean);
			tv_nav.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					CarRepairBean listBean = (CarRepairBean) v.getTag();
					// 先初始化导航数据
					initNav(listBean);
					// 启动FPS导航
					if (AMapNavi.getInstance(mContext) != null) {
						AMapNavi.getInstance(mContext).calculateDriveRoute(
								mStartPoints, mEndPoints, null,
								AMapNavi.DrivingDefault);
						mRouteCalculatorProgressDialog.show();
					}
				}
			});
			
		}
		
		return convertView;
	}
	
	private void initNav(CarRepairBean navbean){
		TTSController.getInstance(mContext).startSpeaking();
		//获取当前经纬度
		geoLat = PreferencesUtil.getStringPreferences(mContext, "currentlat");
		geoLng = PreferencesUtil.getStringPreferences(mContext, "currentlng");
		startGeoLat = Double.parseDouble(geoLat);
		startGeoLng = Double.parseDouble(geoLng);
		// 获取目的地经纬度
		edoLat = navbean.getLatitude();
		edoLng = navbean.getLongitude();
		if (!Tools.isEmptyString(edoLat) && !Tools.isEmptyString(edoLng)) {
			endEdoLat = Double.parseDouble(edoLat.trim());
			endEdoLng = Double.parseDouble(edoLng.trim());
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
	
	
	private final class MyHold {
		TextView hold_tv_name = null;
		TextView hold_tv_addr = null;
		TextView hold_tv_dist = null;
		TextView hold_tv_tel = null;
		TextView hold_tv_nav = null;
		public MyHold(
				TextView tvname,TextView tvaddr,TextView tvdis,TextView tvatel,TextView tvnav){
			this.hold_tv_name = tvname;
			this.hold_tv_addr = tvaddr;
			this.hold_tv_dist = tvdis;
			this.hold_tv_tel = tvatel;
			this.hold_tv_nav = tvnav;
		}
	}

	@Override
	public void onLockMap(boolean arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNaviCancel() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNaviMapMode(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNaviSetting() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNaviTurnClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNextRoadClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScanViewButtonClick() {
		// TODO Auto-generated method stub
		
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
		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		Bundle bundle = new Bundle();
		bundle.putInt(Utils.ACTIVITYINDEX, Utils.CARREPAIR);
		bundle.putBoolean(Utils.ISEMULATOR, false);
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
	
	
}
