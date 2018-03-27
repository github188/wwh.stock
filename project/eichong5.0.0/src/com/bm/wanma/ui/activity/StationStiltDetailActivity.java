package com.bm.wanma.ui.activity;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.OnMapTouchListener;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.AMapNaviViewListener;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.bm.wanma.R;
import com.bm.wanma.broadcast.BroadcastUtil;
import com.bm.wanma.entity.ElectricPileBean;
import com.bm.wanma.entity.ElectricPileDetailsBean;
import com.bm.wanma.entity.MapModeBean;
import com.bm.wanma.entity.MyCollectBean;
import com.bm.wanma.entity.PileHead;
import com.bm.wanma.entity.PowerElectricpileListBean;
import com.bm.wanma.entity.PowerStationBean;
import com.bm.wanma.net.GetDataPost;
import com.bm.wanma.net.Protocol;
import com.bm.wanma.ui.navigation.NaviCustomActivity;
import com.bm.wanma.ui.navigation.TTSController;
import com.bm.wanma.ui.navigation.Utils;
import com.bm.wanma.ui.scan.CaptureActivity;
import com.bm.wanma.utils.LogUtil;
import com.bm.wanma.utils.PreferencesUtil;
import com.bm.wanma.utils.ToastUtil;
import com.bm.wanma.utils.Tools;
import com.bm.wanma.view.ContentScrollView;
import com.bm.wanma.view.ContentScrollView.OnScrollChangedListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;

public class StationStiltDetailActivity extends BaseActivity implements OnClickListener,OnItemClickListener,AMapNaviListener, AMapNaviViewListener,OnScrollChangedListener{
	private TextureMapView mMapView = null;
	private AMap aMap;
	private MarkerOptions markerOption;
	private BitmapDescriptor marker_icon;
	private ImageView ib_back,iv_photo,iv_price_referral,iv_detail_image;
	private TextView tv_detail_charging_point,tv_electricize_address,tv_electricity_price,tv_map_distance,
	tv_parking_money,tv_open_time,tv_cocurrent_employ,tv_cocurrent_leisure,tv_exchange_employ,tv_exchange_leisure;
	private View v_detail_title_background;
	private ContentScrollView scrollView;
	private RelativeLayout rl_scan_entrance;
	private ImageView tv_detail_navgation;
	private String pkUserinfo,photo,detail_charging_point,electricize_address,electricity_price,parking_money
	,open_time,detail_navgation;
	private String currentLat,currentLng;
	private int imghight;
	private ElectricPileDetailsBean electricPileBean;
	private MapModeBean mapModeBean;
	private String zlnum,zlfree,jlnum,jlfree;
	private String edolat,edolng;
	// 起点终点列表
	private ArrayList<NaviLatLng> mStartPoints = new ArrayList<NaviLatLng>();
	private ArrayList<NaviLatLng> mEndPoints = new ArrayList<NaviLatLng>();
	private MapModeBean collectlistBean = new MapModeBean();
	private ProgressDialog mRouteCalculatorProgressDialog;// 路径规划过程显示状态
	private String electricId;
	private Double startGeoLat,startGeoLng,endEdoLat,endEdoLng;
	private String pkuserId;
	private ArrayList<String> urls;//图片url集合
	private String rateId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_station_pile_detail);
		
		registerBoradcastReceiver();//注册广播，预约成功，关闭activity
		
		//获取地图控件引用
	    mMapView = (TextureMapView) findViewById(R.id.map);
	    
	    //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
	    mMapView.onCreate(savedInstanceState);
	    if (aMap == null) {
			aMap = mMapView.getMap();
			aMap.getUiSettings().setZoomControlsEnabled(false);//设置默认缩放按钮是否显示
			aMap.getUiSettings().setZoomGesturesEnabled(false);
			aMap.getUiSettings().setScrollGesturesEnabled(false);
		}
	    
	    initView();
	    
		initValue();
	}
	private void initView(){
//		ib_back = (ImageView)findViewById(R.id.detail_back);//返回按钮
//		ib_back.setOnClickListener(this);
		iv_detail_image = (ImageView) findViewById(R.id.detail_image);
		scrollView = (ContentScrollView) findViewById(R.id.scroll);
		scrollView.setOnScrollChangeListener(this);
		v_detail_title_background = findViewById(R.id.detail_title_background);
		iv_photo = (ImageView)findViewById(R.id.detail_image);//图片
		iv_photo.setOnClickListener(this);
		
		tv_detail_charging_point = (TextView) findViewById(R.id.detail_charging_point);//充电点
		tv_detail_charging_point.setOnClickListener(this);
		tv_electricize_address = (TextView) findViewById(R.id.electricize_address);//地址
		tv_map_distance = (TextView)findViewById(R.id.detail_distance);//返回按钮
		tv_map_distance.setOnClickListener(this);
		
		tv_electricity_price = (TextView) findViewById(R.id.electricity_price);//电价
		iv_price_referral = (ImageView)findViewById(R.id.iv_price_referral);//电价疑问
		iv_price_referral.setOnClickListener(this);
		
		tv_parking_money = (TextView) findViewById(R.id.parking_money);//停车费
		
		tv_open_time = (TextView) findViewById(R.id.open_time);//开放时间
		tv_detail_navgation = (ImageView) findViewById(R.id.detail_navgation);//导航
		tv_detail_navgation.setOnClickListener(this);
		
		tv_cocurrent_employ = (TextView) findViewById(R.id.cocurrent_employ);//直流使用中
		tv_cocurrent_leisure = (TextView) findViewById(R.id.cocurrent_leisure);//直流空闲
		tv_exchange_employ = (TextView) findViewById(R.id.exchange_employ);//交流使用中
		tv_exchange_leisure = (TextView) findViewById(R.id.exchange_leisure);//交流空闲
		rl_scan_entrance = (RelativeLayout) findViewById(R.id.scan_entrance);//扫描
		rl_scan_entrance.setOnClickListener(this);
		aMap.setOnMapTouchListener(new OnMapTouchListener() {

            @Override
            public void onTouch(MotionEvent arg0) {
                    // TODO Auto-generated method stub
//                    if(arg0.getAction() == MotionEvent.ACTION_DOWN){  
//                    	scrollView.requestDisallowInterceptTouchEvent(false);  
//                    }else{  
//                    	scrollView.requestDisallowInterceptTouchEvent(true);  
//                    }  
            }
    });
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes", "static-access" })
	private void initValue(){
		pkUserinfo = PreferencesUtil.getStringPreferences(getActivity(),"pkUserinfo");
		currentLat = PreferencesUtil.getStringPreferences(getActivity(),"currentlat");
		currentLng = PreferencesUtil.getStringPreferences(getActivity(),"currentlng");
		//GetDataPost.getInstance(this).getStationDetail(handler, powerStationId, pkUserinfo, currentLng, currentLat);
		Intent getDetaIn = getIntent(); 
		mapModeBean = (MapModeBean) getDetaIn.getSerializableExtra("mapModeBean");
		if (isNetConnection()) {			
			goToDetail(mapModeBean);
		}else {
			setValueToView2();
		}
		
		detail_charging_point = mapModeBean.getPoStName();//充电点
		electricize_address = mapModeBean.getPoStAddress();//地址
		electricity_price = mapModeBean.getCurrentRate();//电价
		detail_navgation = mapModeBean.getDistance();//导航距离
		zlfree = mapModeBean.getDc();//直流空闲
		jlfree = mapModeBean.getAc();//交流空闲
		edolat = mapModeBean.getPoStLatitude();
		edolng = mapModeBean.getPoStLongitude();
		marker_icon = BitmapDescriptorFactory.fromResource(R.drawable.marker_icon);
		aMap.animateCamera( new CameraUpdateFactory().newLatLngZoom (new
				LatLng(Double.parseDouble(edolat), Double.parseDouble(edolng)),
				14));
		
		markerOption = new MarkerOptions();
		markerOption.position(new LatLng(Double.parseDouble(edolat),
				Double.parseDouble(edolng)));
		markerOption.icon(marker_icon);
		aMap.addMarker(markerOption);
		
		setValueToView();

	}
	private void setValueToView2() {
		if (!Tools.isEmptyString(parking_money)) {
			tv_parking_money.setText(parking_money+"元");//停车费
		}else {
			tv_parking_money.setText("--");
		}
		if (!Tools.isEmptyString(open_time)) {
			
			tv_open_time.setText(open_time);//开放时间 
		}else {
			tv_open_time.setText("--");
		}

		if (!Tools.isEmptyString(zlnum)) {
			
			tv_cocurrent_employ.setText(zlnum);//直流总共
		}else {
			tv_cocurrent_employ.setText("--");
		}
		if (!Tools.isEmptyString(jlnum)) {			
			tv_exchange_employ.setText(jlnum);//交流总共
		}else {
			tv_exchange_employ.setText("--");//交流总共
		}
	}
	//为控件赋值
	private void setValueToView(){

		if (!Tools.isEmptyString(detail_charging_point)) {
			tv_detail_charging_point.setText(detail_charging_point);//充电点
		}else {
			tv_detail_charging_point.setText("--");
		}
		if (!Tools.isEmptyString(electricize_address)) {
			
			tv_electricize_address.setText(electricize_address);//地址
		}else {
			tv_electricize_address.setText("--");
		}
		if (!Tools.isEmptyString(electricity_price)) {
			tv_electricity_price.setText(electricity_price+"元");//电价
		}else {
			tv_electricity_price.setText("--");
		}

		if (!Tools.isEmptyString(detail_navgation)) {
			
			tv_map_distance.setText(Tools.getMeterOrKM(detail_navgation));//导航
		}else {
			tv_map_distance.setText("--");
		}
		if (!Tools.isEmptyString(zlfree)) {
			
			tv_cocurrent_leisure.setText(zlfree);//直流空闲
		}else {
			tv_cocurrent_leisure.setText("--");
		}
		if (!Tools.isEmptyString(jlfree)) {			
			tv_exchange_leisure.setText(jlfree);//交流空闲
		}else {
			tv_exchange_leisure.setText("--");//交流空闲
		}
	}
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		//返回按钮
		case R.id.detail_charging_point:
			/*Intent listIn = new Intent();
			listIn.setClass(this, HomeActivity.class);
			listIn.putExtra("tag", "1");
			startActivity(listIn);*/
			finish();
			break;
		// 返回地图

		case R.id.scan_entrance:
			MobclickAgent.onEvent(mContext, "chogndian_listcardsaoyisao");
			clickScanBtn();
			break;
		//点击查看多张大图
		case R.id.detail_image:
			if(urls.size()>0){
				imageBrower(0, urls);
			}
			break;

		//价格 疑问
		case R.id.iv_price_referral:
			
			if(isNetConnection()){
				Intent priceIn = new Intent();
				priceIn.putExtra("priceId", rateId);
				priceIn.setClass(StationStiltDetailActivity.this, AboutPriceActivity.class);
				startActivity(priceIn);
			}else {
				showToast("请检查网络是否正常");
			}
			break;
		//导航
		case R.id.detail_navgation:
			MobclickAgent.onEvent(mContext, "chogndian_listcarddaohang");
			if(isNetConnection()){
				// 启动FPS导航
				if (AMapNavi.getInstance(StationStiltDetailActivity.this) != null) {
					AMapNavi.getInstance(StationStiltDetailActivity.this)
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
	
	
	/**
	 * 点击扫描按钮
	 * 
	 */
	private void clickScanBtn() {
		 
		pkUserinfo = PreferencesUtil.getStringPreferences(getActivity(), "pkUserinfo");
		
		if(!Tools.isEmptyString(pkUserinfo)){
			Intent scanIntent = new Intent();
			scanIntent.setClass(getActivity(),
					CaptureActivity.class);
			startActivity(scanIntent);
		}else {
			Intent in = new Intent();  //跳转登录界面
			in.setClass(getActivity(), LoginActivity.class);
			in.putExtra("source_inteface", "captureactivity");
			startActivity(in);
		}
		
		
		
	}
	@Override
	protected void onStart() {
		super.onStart();
		initNavigation();
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mBroadcastReceiver);
		//在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
	    mMapView.onDestroy();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(mContext);
		 //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
	    mMapView.onResume();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(mContext);
		 //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
	    mMapView.onPause();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		
		//在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
	    mMapView.onSaveInstanceState(outState);
	}
	@SuppressWarnings("unchecked")
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {}
	
	/**
	 * 打开图片查看器
	 * @param position
	 * @param urls2
	 */
	protected void imageBrower(int position, ArrayList<String> urls2) {
		Intent intent = new Intent(this, ImagePagerActivity.class);
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls2);
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
		//intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);
		//overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
		
	}
	/**
	 * 初始化导航
	 */
	private void initNavigation(){
		TTSController.getInstance(StationStiltDetailActivity.this).startSpeaking();
		// 获取当前经纬度
		currentLat = PreferencesUtil.getStringPreferences(this, "currentlat");
		currentLng = PreferencesUtil.getStringPreferences(this, "currentlng");
		// 获取目的地经纬度
		if (!Tools.isEmptyString(edolat) && !Tools.isEmptyString(edolng) && !Tools.isEmptyString(currentLat) && !Tools.isEmptyString(currentLng)) {
			startGeoLat = Double.parseDouble(currentLat);
			startGeoLng = Double.parseDouble(currentLng);
			endEdoLat = Double.parseDouble(edolat);
			endEdoLng = Double.parseDouble(edolng);
			NaviLatLng mNaviStart = new NaviLatLng(startGeoLat, startGeoLng);
			NaviLatLng mNaviEnd = new NaviLatLng(endEdoLat, endEdoLng);
			mStartPoints.clear();
			mEndPoints.clear();
			mStartPoints.add(mNaviStart);
			mEndPoints.add(mNaviEnd);
		}

		mRouteCalculatorProgressDialog = new ProgressDialog(StationStiltDetailActivity.this);
		mRouteCalculatorProgressDialog.setCancelable(true);
		AMapNavi aMapNavi = AMapNavi.getInstance(StationStiltDetailActivity.this);
		if (this instanceof AMapNaviListener && aMapNavi != null) {
			aMapNavi.setAMapNaviListener(this);
		}
	}
	
	@Override
	protected void getData() {
		
		
	}
	

	@Override
	public void onSuccess(String sign, Bundle bundle) {
		if (sign.equals(Protocol.POWER_Pile_DETAIL)) {
			//电站详情
			cancelPD();
			electricPileBean = (ElectricPileDetailsBean) bundle.getSerializable(Protocol.DATA);
			if(electricPileBean != null){
				photo = electricPileBean.getPostPic();
				parking_money = electricPileBean.getParkFee();// 停车费

				open_time = electricPileBean.getOnLineTime();// 开放时间

				zlnum = electricPileBean.getZlHeadNum();// 直流总数
				jlnum = electricPileBean.getJlHeadNum();// 交流总数

				
				detail_navgation = electricPileBean.getDistance();//导航距离
				zlfree = electricPileBean.getZlFreeHeadNum();//直流空闲
				jlfree = electricPileBean.getJlFreeHeadNum();//交流空闲
				
//				if (!Tools.isEmptyString(zlnum) && !Tools.isEmptyString(zlfree)) {
//					cocurrent_employ = String.valueOf(Integer.parseInt(zlnum)
//							- Integer.parseInt(zlfree));// 直流使用中
//				}
//				if (!Tools.isEmptyString(jlnum) && !Tools.isEmptyString(jlfree)) {
//					exchange_employ = String.valueOf(Integer.parseInt(jlnum)
//							- Integer.parseInt(jlfree));// 交流使用中
//				}

				rateId = electricPileBean.getRateId();

				urls = new ArrayList<String>();

				if (!Tools.isEmptyString(photo)) {
					String[] urllist = photo.split(",");

					for (int i = 0; i < urllist.length; i++) {
						urls.add(urllist[i]);
					}
					DisplayImageOptions options = new DisplayImageOptions.Builder()
							.showImageForEmptyUri(R.drawable.imgno)
							.showImageOnFail(R.drawable.imgno)
							.cacheInMemory(true).cacheOnDisk(false)
							.bitmapConfig(Config.RGB_565).build();
					ImageLoader.getInstance().displayImage(urls.get(0),
							iv_photo, options);

				}
				setValueToView();
				setValueToView2();
			}
		}

	}

	@Override
	public void onFaile(String sign, Bundle bundle) {

		setValueToView2();
	}
	
	public void registerBoradcastReceiver(){  
        IntentFilter myIntentFilter = new IntentFilter();  
        myIntentFilter.addAction("com.bm.wanma.bespoke.ok");  
        //注册广播        
        registerReceiver(mBroadcastReceiver, myIntentFilter);  
    }  
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){  
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			 if(action.equals("com.bm.wanma.bespoke.ok")){  
				// showToast("com.bm.wanma.bespoke.ok");
				 finish();
	         }  
		}  
          
    };  
	
	
	@Override
	public void onLockMap(boolean arg0) { }
	@Override
	public void onNaviCancel() { }
	@Override
	public void onNaviMapMode(int arg0) { }
	@Override
	public void onNaviSetting() { }
	@Override
	public void onNaviTurnClick() { }
	@Override
	public void onNextRoadClick() { }
	@Override
	public void onScanViewButtonClick() { }
	@Override
	public void onArriveDestination() { }
	@Override
	public void onArrivedWayPoint(int arg0) { }
	@Override
	public void onCalculateRouteFailure(int arg0) {
		mRouteCalculatorProgressDialog.dismiss(); }
	@Override
	public void onCalculateRouteSuccess() {
		mRouteCalculatorProgressDialog.dismiss();
		Intent intent = new Intent(StationStiltDetailActivity.this, NaviCustomActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		Bundle bundle = new Bundle();
		bundle.putInt(Utils.ACTIVITYINDEX, Utils.STATIONDETAIL);
		bundle.putBoolean(Utils.ISEMULATOR, false);
		intent.putExtras(bundle);
		startActivity(intent);
		
	}
	@Override
	public void onEndEmulatorNavi() { }
	@Override
	public void onGetNavigationText(int arg0, String arg1) { }
	@Override
	public void onGpsOpenStatus(boolean arg0) { }
	@Override
	public void onInitNaviFailure() { }
	@Override
	public void onInitNaviSuccess() { }
	@Override
	public void onLocationChange(AMapNaviLocation arg0) { }
	@Override
	public void onNaviInfoUpdate(NaviInfo arg0) { }
	@Override
	@Deprecated
	public void onNaviInfoUpdated(AMapNaviInfo arg0) { }
	@Override
	public void onReCalculateRouteForTrafficJam() { }
	@Override
	public void onReCalculateRouteForYaw() { }
	@Override
	public void onStartNavi(int arg0) { }
	@Override
	public void onTrafficStatusUpdate() { }
	
	@Override
	public void onScrollChanged(int l, int t, int oldl, int oldt) {
		
		v_detail_title_background.setAlpha(Float.valueOf(t)/Float.valueOf(iv_detail_image.getHeight()-50));
		LogUtil.i("cm_netPost","t==" +  t+"   imghight==" +  iv_detail_image.getHeight()+"    t/iv_detail_image.getHeight()==" +  Float.valueOf(t)/Float.valueOf(iv_detail_image.getHeight()));
	}
	
	private void goToDetail(MapModeBean bean){
		pkuserId = PreferencesUtil.getStringPreferences(getActivity(),"pkUserinfo");//3055
		String geoLat = PreferencesUtil.getStringPreferences(getActivity(),
				"currentlat");
		String geoLng = PreferencesUtil.getStringPreferences(getActivity(),
				"currentlng");
		
//		if(!Tools.isEmptyString(pkuserId)){
			//先去请求电桩 电站信息，如果有数据，在onsuccess里跳转到详情界面 
			if (isNetConnection()) {
				showPD("正在加载数据...");
				try {
					electricId = bean.getPkPowerStation();
					PreferencesUtil.setPreferences(mContext, "PkPowerStation", electricId);
				} catch (Exception e) {
					electricId = PreferencesUtil.getStringPreferences(getActivity(),
							"PkPowerStation");
				}
				GetDataPost.getInstance(getActivity()).getPileDetail(handler,
						electricId,  geoLng, geoLat);

			} else {
				showToast("网络不稳，请稍后再试");
			}
			
//		}
	}
	
	
}
