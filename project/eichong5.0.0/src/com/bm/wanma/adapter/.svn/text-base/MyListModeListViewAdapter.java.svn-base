package com.bm.wanma.adapter;

import java.util.ArrayList;
import java.util.List;

import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.AMapNaviViewListener;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.bm.wanma.R;
import com.bm.wanma.entity.ElectricPileBean;
import com.bm.wanma.entity.ListModeBean;
import com.bm.wanma.entity.MapModeBean;
import com.bm.wanma.entity.PowerStationBean;
import com.bm.wanma.net.GetDataPost;
import com.bm.wanma.net.Protocol;
import com.bm.wanma.ui.activity.ChargeListActivity;
import com.bm.wanma.ui.activity.LoginActivity;
import com.bm.wanma.ui.activity.StationStiltDetailActivity;
import com.bm.wanma.ui.navigation.NaviCustomActivity;
import com.bm.wanma.ui.navigation.TTSController;
import com.bm.wanma.ui.navigation.Utils;
import com.bm.wanma.utils.PreferencesUtil;
import com.bm.wanma.utils.ToastUtil;
import com.bm.wanma.utils.Tools;
import com.umeng.analytics.MobclickAgent;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyListModeListViewAdapter extends GetDataPostAdapter implements
		AMapNaviListener, AMapNaviViewListener {

	// 实体类
	private List<MapModeBean> mdata;
	private Context mContext;
	private LayoutInflater inflater;
	// 起点终点列表
	private ArrayList<NaviLatLng> mStartPoints = new ArrayList<NaviLatLng>();
	private ArrayList<NaviLatLng> mEndPoints = new ArrayList<NaviLatLng>();
	private ProgressDialog mRouteCalculatorProgressDialog;// 路径规划过程显示状态
	private String geoLat,geoLng,edoLat,edoLng;
	private Double startGeoLat,startGeoLng,endEdoLat,endEdoLng;
	private String pkUserinfo;
	private String electricType,electricId;
	private ArrayList<PowerStationBean> stationBeanList;
	private PowerStationBean stationBean;
	private ArrayList<ElectricPileBean> pileBeanList;
	private ElectricPileBean pileBean;
	private String type;
	private int positiont;
	private MapModeBean listBean;
	public MyListModeListViewAdapter(Context context,
			List<MapModeBean> data,String type) {
		super(context);
		this.mdata = data;
		this.mContext = context;
		this.type = type;
		inflater = LayoutInflater.from(context);
		pkUserinfo = PreferencesUtil.getStringPreferences(mContext,"pkUserinfo");
		// init();
	}

	private void init(MapModeBean navbean) {
		TTSController.getInstance(mContext).startSpeaking();
		//获取当前经纬度
		geoLat = PreferencesUtil.getStringPreferences(mContext, "currentlat");
		geoLng = PreferencesUtil.getStringPreferences(mContext, "currentlng");
		startGeoLat = Double.parseDouble(geoLat);
		startGeoLng = Double.parseDouble(geoLng);
		// 获取目的地经纬度
		edoLat = navbean.getPoStLatitude();
		edoLng = navbean.getPoStLongitude();
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

	@Override
	public int getCount() {
		return mdata.size();
	}

	@Override
	public Object getItem(int position) {
		return mdata.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View conventview, ViewGroup arg2) {
		LinearLayout ll_pop_detail_layout = null;
		TextView tv_charging_point = null;//充电点
		TextView tv_distance = null;//距离
		TextView tv_price = null;//价格
		TextView tv_fastNum = null;//直流
		TextView tv_slowNum = null;//交流
	    ImageView iv_navigation = null;//导航

	    TextView tv_address = null;//地址
	    View v = null;
		if (conventview == null) {
			conventview = inflater.inflate(R.layout.listview_item_list_listmode,
					null);
			ll_pop_detail_layout = (LinearLayout) conventview
			.findViewById(R.id.pop_detail_layout);
			tv_charging_point = (TextView) conventview
					.findViewById(R.id.listview_charging_point);
			tv_distance = (TextView) conventview
					.findViewById(R.id.listview_distance);
			tv_price = (TextView) conventview
					.findViewById(R.id.listview_tv_price);
			tv_fastNum = (TextView) conventview
					.findViewById(R.id.listview_fast_num);
			tv_slowNum = (TextView) conventview
					.findViewById(R.id.listview_slow_num);
			v = conventview.findViewById(R.id.v);

			iv_navigation = (ImageView)conventview.findViewById(R.id.navigation);

			if (type.equals("2")) {
				v.setVisibility(View.VISIBLE);
			}
			tv_address = (TextView) conventview
					.findViewById(R.id.listview_addr);
			// 保存conventview对象到ObjectClass类中
			conventview.setTag(new ObjectClass(ll_pop_detail_layout,tv_charging_point, tv_distance, tv_price, tv_fastNum, tv_slowNum, tv_address, iv_navigation));

		} else {
			// 得到保存的对象
			ObjectClass objectclass = (ObjectClass) conventview.getTag();
			ll_pop_detail_layout = objectclass.obj_ll_pop_detail_layout;
			tv_charging_point = objectclass.obj_tv_charging_point;
			tv_distance = objectclass.obj_tv_distance;
			tv_fastNum = objectclass.obj_tv_fastNum;
			tv_slowNum = objectclass.obj_tv_slowNum;
			tv_price = objectclass.obj_tv_price;
			tv_address = objectclass.obj_tv_address;
			iv_navigation =  objectclass.obj_iv_navigation;

		}
		
		listBean = (MapModeBean) mdata
				.get(position);
		positiont = position;
		if(listBean != null){
			tv_charging_point.setText(""+listBean.getPoStName());
			tv_address.setText(""+listBean.getPoStAddress());
			String distance = listBean.getDistance();
			if(!Tools.isEmptyString(distance)){
				distance = Tools.getMeterOrKM(distance);
				tv_distance.setText(distance);
			}
			tv_fastNum.setText(""+listBean.getDc());
			tv_slowNum.setText(""+listBean.getAc());
			//if(!Tools.isEmptyString(listBean.getServiceCharge())){
				tv_price.setText(listBean.getCurrentRate()+"元/度");
			//}
			
			// 添加导航图标点击事件
			iv_navigation.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					MobclickAgent.onEvent(mContext, "chongdian_listdaohang");
					if (isNetConnection()) {
					MapModeBean listBean = (MapModeBean) mdata.get(position);
					Log.i("fragment", "position"+positiont);
					// 先初始化导航数据
					init(listBean);
					// 启动FPS导航
					if (AMapNavi.getInstance(mContext) != null) {
						AMapNavi.getInstance(mContext).calculateDriveRoute(
								mStartPoints, mEndPoints, null,
								AMapNavi.DrivingDefault);
						mRouteCalculatorProgressDialog.show();
					}	
					}else {
						ToastUtil.TshowToast("请检查网络是否正常");
					}
				}
			});
//			ll_pop_detail_layout.setOnClickListener(this);
			
		}
		
		return conventview;

	}

	private final class ObjectClass {
		LinearLayout obj_ll_pop_detail_layout = null;
	    TextView obj_tv_charging_point = null;//充电点
		TextView obj_tv_distance = null;//距离
		TextView obj_tv_price = null;//价格
		TextView obj_tv_fastNum = null;//直流
		TextView obj_tv_slowNum = null;//交流
	    ImageView obj_iv_navigation = null;//导航
	    TextView obj_tv_address = null;//地址
	    
		public ObjectClass(LinearLayout ll_pop_detail_layout , TextView tv_charging_point, TextView tv_distance, TextView tv_price,
				TextView tv_fastNum, TextView tv_slowNum, TextView tv_address,ImageView iv_navigation) {
			this.obj_tv_charging_point = tv_charging_point;
			this.obj_tv_distance = tv_distance;
			this.obj_tv_price = tv_price;
			this.obj_tv_fastNum = tv_fastNum;
			this.obj_tv_slowNum = tv_slowNum;
			this.obj_iv_navigation = iv_navigation;
			this.obj_tv_address = tv_address;
			this.obj_ll_pop_detail_layout = ll_pop_detail_layout;

		}
	}
//	public void particulars(int position) {
//		MapModeBean listBean = (MapModeBean) mdata.get(position);
//		if(listBean != null){
//			goToDetail(listBean);
//		}
//
//	}
	@SuppressWarnings("unchecked")
	@Override
	public void onSuccess(String sign, Bundle bundle) {
				//电站详情
					cancelPD();
				if (sign.equals(Protocol.POWER_STATION_DETAIL)) {
					stationBeanList = (ArrayList<PowerStationBean>) bundle.getSerializable(Protocol.DATA);
					if(stationBeanList != null){
						stationBean = stationBeanList.get(0);
						Intent detailIn = new Intent();
						detailIn.setClass(mContext, StationStiltDetailActivity.class);
						detailIn.putExtra("stationBean", stationBean);
						detailIn.putExtra("type", "2");
						detailIn.putExtra("electricId", electricId);
						mContext.startActivity(detailIn);
					}
				}else if (sign.equals(Protocol.POWER_Pile_DETAIL)) {
					//电桩详情
					pileBeanList = (ArrayList<ElectricPileBean>) bundle.getSerializable(Protocol.DATA);
					if(pileBeanList != null){
						pileBean = pileBeanList.get(0);
						Intent detailIn = new Intent();
						detailIn.setClass(mContext, StationStiltDetailActivity.class);
						detailIn.putExtra("pileBean", pileBean);
						detailIn.putExtra("type", "1");
						detailIn.putExtra("electricId", electricId);
						mContext.startActivity(detailIn);
					}
				}
		
	}

	@Override
	public void onFaile(String sign, Bundle bundle) {
		// TODO Auto-generated method stub
		
	}
//	/**
//	 * 进入详情界面
//	 */
//	private void goToDetail(MapModeBean bean){
//		pkUserinfo = PreferencesUtil.getStringPreferences(mContext,"pkUserinfo");//3055
//		String geoLat = PreferencesUtil.getStringPreferences(mContext,
//				"currentlat");
//		String geoLng = PreferencesUtil.getStringPreferences(mContext,
//				"currentlng");//30.271278 120.128224
//		
//		if(!Tools.isEmptyString(pkUserinfo)){
//			//先去请求电桩 电站信息，如果有数据，在onsuccess里跳转到详情界面 
//			if(isNetConnection()){
//				//1是桩，2是站  
//				electricType = bean.getElectricType();//
//				electricId = bean.getElectricId();//
//				if("1".equals(electricType)){
//					showPD("正在加载数据...");
//					GetDataPost.getInstance(mContext).getPileDetail(handler,
//							electricId, pkUserinfo, geoLng, geoLat);
//				}else if("2".equals(electricType)){
//					showPD("正在加载数据...");
//					GetDataPost.getInstance(mContext).getStationDetail(handler,
//							electricId, pkUserinfo, geoLng, geoLat);
//				}
//			
//			}else {
//				showToast("网络不稳，请稍后再试");
//			}
//			
//		}else {
//			Bundle extras=new Bundle();
//			extras.putSerializable("electric_pile_details", bean);
//			Intent loginIn = new Intent();
//			loginIn.setClass(mContext, LoginActivity.class);
//			loginIn.putExtra("source_inteface", "ElectricPileDetails");
//			loginIn.putExtras(extras);
//			mContext.startActivity(loginIn);
//		}
//		
//		
//		
//	}

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
		bundle.putInt(Utils.ACTIVITYINDEX, Utils.LISTMODE);
		bundle.putBoolean(Utils.ISEMULATOR, false);
		intent.putExtras(bundle);
		mContext.startActivity(intent);
		// mcontext.finish();

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
	public void onLockMap(boolean arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNaviInfoUpdate(NaviInfo arg0) {
		// TODO Auto-generated method stub
		
	}

//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.navigation:
//			MobclickAgent.onEvent(mContext, "chongdian_listdaohang");
//			if (isNetConnection()) {
//			MapModeBean listBean = (MapModeBean) mdata.get(positiont);
//			Log.i("fragment", "position"+positiont);
//			// 先初始化导航数据
//			init(listBean);
//			// 启动FPS导航
//			if (AMapNavi.getInstance(mContext) != null) {
//				AMapNavi.getInstance(mContext).calculateDriveRoute(
//						mStartPoints, mEndPoints, null,
//						AMapNavi.DrivingDefault);
//				mRouteCalculatorProgressDialog.show();
//			}	
//			}else {
//				ToastUtil.TshowToast("请检查网络是否正常");
//			}
//			break;
//
//		default:
//			MobclickAgent.onEvent(mContext, "chongdian_listcard");
//			Intent detail = new Intent(mContext,StationStiltDetailActivity.class);
//			detail.putExtra("mapModeBean", listBean);
//			mContext.startActivity(detail);
//			break;
//		}
		
//	}

	


}
