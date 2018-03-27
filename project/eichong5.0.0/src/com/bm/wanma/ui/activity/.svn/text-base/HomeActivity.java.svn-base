package com.bm.wanma.ui.activity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bm.wanma.R;
import com.bm.wanma.broadcast.BroadcastUtil;
import com.bm.wanma.broadcast.NetWorkChangeBroadcastReceiver;
import com.bm.wanma.entity.AdvertisementBean;
import com.bm.wanma.entity.ChargeFinishAndDoneBean;
import com.bm.wanma.entity.ElectricPileDetailsBean;
import com.bm.wanma.entity.MyDynamicListBean;
import com.bm.wanma.entity.ScanInfoBean;
import com.bm.wanma.entity.VersionInfoBean;
import com.bm.wanma.jpush.MyReceiver;
import com.bm.wanma.jpush.MyReceiver.SystemNoticeListener;
import com.bm.wanma.net.GetDataPost;
import com.bm.wanma.net.PictureUpload;
import com.bm.wanma.net.PictureUpload.ControlAppear;
import com.bm.wanma.net.Protocol;
import com.bm.wanma.popup.InterstitialPopupWindow;
import com.bm.wanma.socket.SocketConstant;
import com.bm.wanma.socket.StreamUtil;
import com.bm.wanma.socket.TCPSocketManager;
import com.bm.wanma.ui.activity.MoreActivity.MoreMapCallback;
import com.bm.wanma.ui.fragment.BaseFragment;
import com.bm.wanma.ui.fragment.HomePageFragment;
import com.bm.wanma.ui.fragment.HomePageFragment.HomeMapCallback;
import com.bm.wanma.ui.fragment.MapModeFragment;
import com.bm.wanma.ui.fragment.MyHelperFragment;
import com.bm.wanma.ui.fragment.MyPersonFragment;
import com.bm.wanma.utils.LogUtil;
import com.bm.wanma.utils.PreferencesUtil;
import com.bm.wanma.utils.TimeUtil;
import com.bm.wanma.utils.Tools;
import com.bm.wanma.utils.UpdateAppManager;
import com.umeng.analytics.MobclickAgent;

/**
 * 普通用户
 * 主界面
 * 
 * @author cm
 */

public class HomeActivity extends BaseActivity implements OnClickListener ,ITcpCallBack,ControlAppear,SystemNoticeListener,HomeMapCallback,MoreMapCallback{
	private MyDynamicListBean itemBean = new MyDynamicListBean();
	// 四三个tab布局
	private RelativeLayout PileLayout, MyorderLayout, MyPersonLayout,
			MyHelperLayout;
	// 底部标签切换的Fragment
	private BaseFragment currentFragment;
	private MapModeFragment mapFragment;
	private MyPersonFragment myPersonFragment;
	private MyHelperFragment myHelperFragment;
	private HomePageFragment homePageFragment;
	// 底部标签图片
	private ImageView PileImg, MyOrderImg, MyPersonImg, MyHelperImg;
	// 底部标签的文本
	private TextView PileTv, MyOrderTv, MyPersonTv, MyHelperTv;
	private long exitTime;
	private String pkUserId;
	private String electricId;

	private ElectricPileDetailsBean pileBean;
	private VersionInfoBean versionBean;
	private UpdateAppManager mAppManager;

	private int versNumber;
	private String tab = "Tab1";
	private String tabClick;
	// private String versName;
	private InterstitialPopupWindow popupWindow;
	ArrayList<AdvertisementBean> advertisementBeans = null;
	private boolean istab = true;
	private boolean istab1= false;
	private boolean istab2= false;
	private boolean istab3= false;
	private boolean istab4= false;
	private boolean isSplashScreena= false;
	boolean pop = true;
	/**
	 * 充电
	 */
//	private boolean iselectricize= false;
	public static MapAwaitChange mapawaitchange = null;
	private boolean isfirstcharge = true;
//	private boolean isonclick= false;
	private String pkuserId;
	private ArrayList<ChargeFinishAndDoneBean> chargeList;
	private ChargeFinishAndDoneBean chargeOrderBean;
	private TextView iv_charging_animation;
	private String pilenum,headnum;
	private TCPSocketManager mTcpSocketManager;
	private ScanInfoBean mScanInfoBean;
	@TargetApi(19)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPageName = "MyPersonFragment";
		setContentView(R.layout.activity_home);
		registerBoradcastReceiver();
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		MyReceiver.setOnSystemNoticeListener(this);
		HomePageFragment.setcallback(this);
		MoreActivity.setcallback(this);
		initUI();
		initTab();
		if(!isNetConnection()){
			if(istab){
				tabClick = "Tab1Click";
				tab = "Tab1";
				initPopupWindow();
			}
		}
	}
	private void InitAdvertisementPopupWindow() {
		
		if (popupWindow == null) {
			popupWindow = new InterstitialPopupWindow(this);
		}
		popupWindow.setInitValue(tab, tabClick,itemBean);
		popupWindow.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {

			}
		});
		popupWindow.showAtLocation(this.findViewById(R.id.rl_main),
				Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// 注销掉下边代码，让其不再保存Fragment的状态，达到其随着MainActivity一起被回收的效果！
		// super.onSaveInstanceState(outState);
	}

	/**
	 * 初始化UI
	 */
	private void initUI() {
		iv_charging_animation = (TextView) findViewById(R.id.fragment_charging);
		iv_charging_animation.setOnClickListener(this);
		PileLayout = (RelativeLayout) findViewById(R.id.rl_pile);
		MyorderLayout = (RelativeLayout) findViewById(R.id.rl_myorder);
		MyPersonLayout = (RelativeLayout) findViewById(R.id.rl_myperson);
		PileLayout.setOnClickListener(this);
		MyorderLayout.setOnClickListener(this);
		MyPersonLayout.setOnClickListener(this);
		MyHelperLayout = (RelativeLayout) findViewById(R.id.rl_myhelper);
		MyHelperLayout.setOnClickListener(this);

		PileImg = (ImageView) findViewById(R.id.iv_pile);
		MyOrderImg = (ImageView) findViewById(R.id.iv_myorder);
		MyPersonImg = (ImageView) findViewById(R.id.iv_myperson);
		PileTv = (TextView) findViewById(R.id.tv_pile);
		MyOrderTv = (TextView) findViewById(R.id.tv_myorder);
		MyPersonTv = (TextView) findViewById(R.id.tv_myperson);
		MyHelperImg = (ImageView) findViewById(R.id.iv_myhelper);
		MyHelperTv = (TextView) findViewById(R.id.tv_myhelper);

	}

	/**
	 * 初始化底部标签
	 */
	private void initTab() {
		if (homePageFragment == null) {
			homePageFragment = new HomePageFragment();
		}
		if (!homePageFragment.isAdded()) {

			// 提交事务
			getFragmentManager().beginTransaction()
					.add(R.id.content_layout, homePageFragment).commit();
			// 记录当前Fragment
			currentFragment = homePageFragment;
			// 设置图片文本的变化
			PileImg.setImageResource(R.drawable.tab_home_p);
			PileTv.setTextColor(getResources().getColor(R.color.common_orange));
			MyOrderImg.setImageResource(R.drawable.tab_order_d);
			MyOrderTv
					.setTextColor(getResources().getColor(R.color.common_gray));
			MyPersonImg.setImageResource(R.drawable.tab_my_d);
			MyPersonTv.setTextColor(getResources()
					.getColor(R.color.common_gray));
			MyHelperImg.setImageResource(R.drawable.tab_help_d);
			MyHelperTv.setTextColor(getResources()
					.getColor(R.color.common_gray));

		}
	}

	@Override
	protected void onResume() {
		getIntent().getStringExtra("source_inteface");
		super.onResume();
		MobclickAgent.onResume(mContext);

		pkuserId = PreferencesUtil.getStringPreferences(getActivity(), "pkUserinfo");
		if(Tools.isEmptyString(pkuserId)){//未登录
			iv_charging_animation.setVisibility(View.GONE);
//			fl_charging_confirm.setVisibility(View.GONE);
		}else {
//			if (isfirstcharge) {
//				GetDataPost.getInstance(getActivity()).getChargeInfoList(handler,pkuserId, "0", "1", "1");
//				//充电动画
//				pilenum = PreferencesUtil.getStringPreferences(getActivity(),"chargepilenum");
//				headnum = PreferencesUtil.getStringPreferences(getActivity(),"chargeheadnum");
//				if(!Tools.isEmptyString(pilenum) && !Tools.isEmptyString(headnum)){
//					if (!tab.equals("Tab3")) {
//		        		 iv_charging_animation.setVisibility(View.VISIBLE);
//		        	 }
//					
//				}else {
//					iv_charging_animation.setVisibility(View.GONE);
//				}
//			}else {
			GetDataPost.getInstance(getActivity()).getChargeInfoList(handler,pkuserId, "0", "1", "1");
				//充电动画
				pilenum = PreferencesUtil.getStringPreferences(getActivity(),"chargepilenum");
				headnum = PreferencesUtil.getStringPreferences(getActivity(),"chargeheadnum");
				if(!Tools.isEmptyString(pilenum) && !Tools.isEmptyString(headnum)){
//					mTcpSocketManager.close();
					if (!tab.equals("Tab3")) {
		        		 iv_charging_animation.setVisibility(View.VISIBLE);
		        	 }

				}else {
					iv_charging_animation.setVisibility(View.GONE);
				}
	        	 if (mapawaitchange!=null) {					
	        		 mapawaitchange.mapawaitchange();
				}
//			}
			
		}
	}
	private void ChargeCheck() {
		pkuserId = PreferencesUtil.getStringPreferences(getActivity(), "pkUserinfo");
		if(Tools.isEmptyString(pkuserId)){//未登录
			iv_charging_animation.setVisibility(View.GONE);
//			fl_charging_confirm.setVisibility(View.GONE);
		}else {
			if (isfirstcharge) {
//				GetDataPost.getInstance(getActivity()).getChargeInfoList(handler,pkuserId, "2", "1", "1");
			}
			//充电动画
			pilenum = PreferencesUtil.getStringPreferences(getActivity(),"chargepilenum");
			headnum = PreferencesUtil.getStringPreferences(getActivity(),"chargeheadnum");
			if(!Tools.isEmptyString(pilenum) && !Tools.isEmptyString(headnum)){
				if (!tab.equals("Tab3")) {
	        		 iv_charging_animation.setVisibility(View.VISIBLE);
	        	 }
//	        	 iselectricize = true;
//				fl_charging_confirm.setVisibility(View.VISIBLE);
//				animation.setDuration(3000);
//				animation.setRepeatCount(-1);
//				iv_charging_animation.startAnimation(animation);
			}else {
				iv_charging_animation.setVisibility(View.GONE);
//				fl_charging_confirm.setVisibility(View.GONE);
			}
		}
	}
@Override
protected void onPause() {
	// TODO Auto-generated method stub
	super.onPause();
//	if(mTcpSocketManager != null){		
//		mTcpSocketManager.close();
//	}
	MobclickAgent.onPause(mContext);
	if (mPageName.equals("MyPersonFragment")) {		
		MobclickAgent.onPageEnd(mPageName);
	}
}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fragment_charging: // 充电
//			mTcpSocketManager.close();
//			isonclick = true;
			//充电中
			if (iv_charging_animation.getText().equals("等待中")) {
				GetDataPost.getInstance(this).getScanInfo(handler, pilenum, headnum);
			}else if (iv_charging_animation.getText().equals("充电中")) {
				
				showPD("正在获取充电信息...");
				mTcpSocketManager = TCPSocketManager.getInstance(getActivity());
				mTcpSocketManager.setTcpCallback(this);
				if(!mTcpSocketManager.hasTcpConnection()&&!Tools.isEmptyString(pilenum)
						&& !Tools.isEmptyString(headnum)
						){
					mTcpSocketManager.conn(pilenum, 
							Byte.parseByte(headnum));
				}else {
					cancelPD();
					showErrorCode(110);
//				isonclick = false;
				}
			}
			break;
		case R.id.rl_pile: // 首页
			clickTabOfPile();
			ChargeCheck();
			MobclickAgent.onEvent(mContext, "anniu_shouye");
			break;
		case R.id.rl_myorder: // 订单
			istab = false;
			clickTabOfMyorder();
			ChargeCheck();
			MobclickAgent.onEvent(mContext, "anniu_chogndian");
			break;
		case R.id.rl_myperson: // 我的
			istab = false;
			clickTabOfMyperson();
			ChargeCheck();
			mPageName = "MyPersonFragment";
			MobclickAgent.onPageStart(mPageName);
			MobclickAgent.onEvent(mContext, "anniu_wode");
			break;
		case R.id.rl_myhelper:// 小助手
			istab = false;
			clickTabOfMyhelper();
			iv_charging_animation.setVisibility(View.GONE);
			MobclickAgent.onEvent(mContext, "anniu_faxian");
			break;

		default:
			break;
		}

	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		String tag = intent.getStringExtra("tag");
		if ("1".equals(tag)) {
			clickTabOfPile();
		} else if ("2".equals(tag)) {
			clickTabOfMyorder();
		} else if ("3".equals(tag)) {
			clickTabOfMyperson();
		} else if ("4".equals(tag)) {
			clickTabOfMyhelper();
		}

	}

	/**
	 * 点击第一个tab--Pile首页
	 */
	private void clickTabOfPile() {
		if (homePageFragment == null) {
			homePageFragment = new HomePageFragment();
		}
		addOrShowFragment(getFragmentManager().beginTransaction(),
				homePageFragment);
		// 设置底部tab变化
		PileImg.setImageResource(R.drawable.tab_home_p);
		PileTv.setTextColor(getResources().getColor(R.color.common_orange));
		MyOrderImg.setImageResource(R.drawable.tab_order_d);
		MyOrderTv.setTextColor(getResources().getColor(R.color.common_gray));
		MyPersonImg.setImageResource(R.drawable.tab_my_d);
		MyPersonTv.setTextColor(getResources().getColor(R.color.common_gray));
		MyHelperImg.setImageResource(R.drawable.tab_help_d);
		MyHelperTv.setTextColor(getResources().getColor(R.color.common_gray));
		tabClick = "Tab1Click";
		tab = "Tab1";
		initPopupWindow();
	}

	
	
	/**
	 * 点击第二个tab --订单
	 */
	private void clickTabOfMyorder() {
		if (mapFragment == null) {
			mapFragment = new MapModeFragment();
		}
		addOrShowFragment(getFragmentManager().beginTransaction(),
				mapFragment);

		PileImg.setImageResource(R.drawable.tab_home_d);
		PileTv.setTextColor(getResources().getColor(R.color.common_gray));
		MyOrderImg.setImageResource(R.drawable.tab_order_p);
		MyOrderTv.setTextColor(getResources().getColor(R.color.common_orange));
		MyPersonImg.setImageResource(R.drawable.tab_my_d);
		MyPersonTv.setTextColor(getResources().getColor(R.color.common_gray));
		MyHelperImg.setImageResource(R.drawable.tab_help_d);
		MyHelperTv.setTextColor(getResources().getColor(R.color.common_gray));
		tabClick = "Tab2Click";
		tab = "Tab2";
		initPopupWindow();

	}

	/**
	 * 点击第三个tab -- Myperson
	 */
	private void clickTabOfMyperson() {
		if (myPersonFragment == null) {
			myPersonFragment = new MyPersonFragment();
		}

		addOrShowFragment(getFragmentManager().beginTransaction(),
				myPersonFragment);
		PileImg.setImageResource(R.drawable.tab_home_d);
		PileTv.setTextColor(getResources().getColor(R.color.common_gray));
		MyOrderImg.setImageResource(R.drawable.tab_order_d);
		MyOrderTv.setTextColor(getResources().getColor(R.color.common_gray));
		MyPersonImg.setImageResource(R.drawable.tab_my_p);
		MyPersonTv.setTextColor(getResources().getColor(R.color.common_orange));
		MyHelperImg.setImageResource(R.drawable.tab_help_d);
		MyHelperTv.setTextColor(getResources().getColor(R.color.common_gray));
		tabClick = "Tab4Click";
		tab = "Tab4";
		initPopupWindow();
	}

	/**
	 * 点击第四个tab--Myhelper
	 */
	private void clickTabOfMyhelper() {
		if (myHelperFragment == null) {
			myHelperFragment = new MyHelperFragment();
		}

		addOrShowFragment(getFragmentManager().beginTransaction(),
				myHelperFragment);
		PileImg.setImageResource(R.drawable.tab_home_d);
		PileTv.setTextColor(getResources().getColor(R.color.common_gray));
		MyOrderImg.setImageResource(R.drawable.tab_order_d);
		MyOrderTv.setTextColor(getResources().getColor(R.color.common_gray));
		MyPersonImg.setImageResource(R.drawable.tab_my_d);
		MyPersonTv.setTextColor(getResources().getColor(R.color.common_gray));
		MyHelperImg.setImageResource(R.drawable.tab_help_p);
		MyHelperTv.setTextColor(getResources().getColor(R.color.common_orange));
		tabClick = "Tab3Click";
		tab = "Tab3";
		initPopupWindow();
	}
	private void addOrShowFragment(FragmentTransaction transaction,
			BaseFragment fragment) {
		if (currentFragment == fragment) {
			return;
		} else {
			if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
				transaction.hide(currentFragment)
						.add(R.id.content_layout, fragment).commit();
			} else {
				transaction.hide(currentFragment).show(fragment).commit();
			}
		}

		currentFragment = fragment;
//		if (fragment == myPersonFragment) {
//			Intent intnet = new Intent("com.bm.wanma.getuserinfo");
//			sendBroadcast(intnet);
//		}

	}

	// 两次返回键退出程序
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) // System.currentTimeMillis()无论何时调用，肯定大于2000
			{
				Toast.makeText(getApplicationContext(), "再按一次退出程序",
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void getData() {
		
		try {
			String tempversNumber = PreferencesUtil.getStringPreferences(this,
					"versNumber");
			versNumber = Integer.valueOf(tempversNumber);
			if (isNetConnection()) {
				GetDataPost.getInstance(this).getAppVersion(handler,
						String.valueOf(versNumber));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		// 通过扫描web端二维码进来
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			electricId = bundle.getString("d");// id
			if (!Tools.isEmptyString(electricId)) {
				pkUserId = PreferencesUtil.getStringPreferences(
						getApplicationContext(), "pkUserinfo");
				Intent detailin = new Intent();
				if (!Tools.isEmptyString(pkUserId)) {
					String currentLat = PreferencesUtil.getStringPreferences(
							getActivity(), "currentlat");
					String currentLng = PreferencesUtil.getStringPreferences(
							getActivity(), "currentlng");
					
						showPD("正在加载数据...");
						GetDataPost.getInstance(this).getPileDetail(handler,
								electricId, currentLng, currentLat);
					

				} else {
					detailin.setClass(HomeActivity.this, LoginActivity.class);
					detailin.putExtra("source_inteface", "login");
				}
				startActivity(detailin);
			}
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onSuccess(String sign, Bundle bundle) {
		
		if (sign.equals(Protocol.POWER_Pile_DETAIL)) {
			// 电桩详情
			pileBean = (ElectricPileDetailsBean) bundle
					.getSerializable(Protocol.DATA);
			if (pileBean != null) {
				Intent detailIn = new Intent();
				detailIn.setClass(this, StationStiltDetailActivity.class);
				detailIn.putExtra("pileBean", pileBean);
				this.startActivity(detailIn);
			}
		} else if (sign.equals(Protocol.GET_APP_VERSION_INFO)) {
			// 获取版本信息
			versionBean = (VersionInfoBean) bundle.getSerializable(Protocol.DATA);
			if (versionBean != null) {
				PreferencesUtil.setPreferences(getApplicationContext(),
						"versNumberServer", versionBean.getVersNumber());
				if (mAppManager == null) {
					mAppManager = new UpdateAppManager(this, versionBean,
							versNumber);
				}
				mAppManager.checkUpdate();
			}
			if(versionBean.getVersNumber() != null && versNumber < Integer.parseInt(versionBean.getVersNumber())){
				istab = false;
			}
			//广告信息
			GetDataPost.getInstance(this).getAdvertisement(handler,null,null);
		}else if (sign.equals(Protocol.ADVERTISEMENT)) {
			//获取闪屏页广告有效期
			advertisementBeans =  (ArrayList<AdvertisementBean>) bundle.getSerializable(Protocol.DATA);
			initImageDate();
		}else if (sign.equals(Protocol.INDENT_PARTICULARS)) {
			//充电中订单列表
			chargeList = (ArrayList<ChargeFinishAndDoneBean>) bundle
					.getSerializable(Protocol.DATA);
			if (chargeList != null && chargeList.size() > 0) {
				chargeOrderBean = chargeList.get(0);
				if (chargeOrderBean != null) {
//					Intent intnet = new Intent(BroadcastUtil.BROADCAST_Charge_Ing);
//					intnet.putExtra("chargepilenum",chargeOrderBean.getElPi_ElectricPileCode());
//					intnet.putExtra("chargeheadnum",chargeOrderBean.getHeadCode());
//					getActivity().sendBroadcast(intnet);
//					if (mTcpSocketManager != null) {						
//						mTcpSocketManager.close();
//					}
					pilenum = chargeOrderBean.getElPi_ElectricPileCode();
		        	 headnum = chargeOrderBean.getHeadCode();
		        	 PreferencesUtil.setPreferences(getActivity(),"chargepilenum",pilenum);
		        	 PreferencesUtil.setPreferences(getActivity(),"chargeheadnum",headnum);
		        	 if (!tab.equals("Tab3")) {
		        		 iv_charging_animation.setVisibility(View.VISIBLE);
		        	 }
		        	 if (chargeOrderBean.getChOr_ChargingStatus().equals("1")) {	
							iv_charging_animation.setText("充电中");
						}
//		        	 else if (chargeOrderBean.getChOr_ChargingStatus().equals("17")) {	
//							iv_charging_animation.setText("等待中");
//					}
						else {
						 iv_charging_animation.setVisibility(View.GONE);
						PreferencesUtil.setPreferences(getActivity(),"chargepilenum","");
			        	 PreferencesUtil.setPreferences(getActivity(),"chargeheadnum","");
					}
		        	 
					isfirstcharge = false;
					if (mapawaitchange!=null) {					
		        		 mapawaitchange.mapawaitchange();
					}
//					mTcpSocketManager = TCPSocketManager.getInstance(getActivity());
//					mTcpSocketManager.setTcpCallback(this);
//					if(!mTcpSocketManager.hasTcpConnection()&&!Tools.isEmptyString(pilenum)
//							&& !Tools.isEmptyString(headnum)){
//						mTcpSocketManager.conn(pilenum, 
//								Byte.parseByte(headnum));
//					}else {
//						showErrorCode(110);
////						isonclick = false;
//					}
				}
			}else if (chargeList != null && chargeList.size() == 0) { 
				//充电结束
	        	 PreferencesUtil.setPreferences(getActivity(),"chargepilenum","");
	        	 PreferencesUtil.setPreferences(getActivity(),"chargeheadnum","");
	        	 iv_charging_animation.setVisibility(View.GONE);
	        	 if (mapawaitchange!=null) {					
	        		 mapawaitchange.mapawaitchange();
				}
			}
		}else if (sign.equals(Protocol.GET_SCAN_INFO)) {
			mScanInfoBean = (ScanInfoBean) bundle.getSerializable(Protocol.DATA);
			mScanInfoBean.setPrechargeMoney(""+chargeOrderBean.getChOr_ChargeMoney());
			showPD("正在获取充电信息...");
			mTcpSocketManager = TCPSocketManager.getInstance(getActivity());
			mTcpSocketManager.setTcpCallback(this);
			if(!mTcpSocketManager.hasTcpConnection()&&!Tools.isEmptyString(pilenum)
					&& !Tools.isEmptyString(headnum)
					&&	mScanInfoBean!=null
					){
				mTcpSocketManager.conn(pilenum, 
						Byte.parseByte(headnum));
			}else {
				cancelPD();
				showErrorCode(110);
//			isonclick = false;
			}
		}
	}
	
	private void initPopupWindow() {
		if (!Tools.isEmptyString(PreferencesUtil.getStringPreferences(this, tab+"Url"))
				&& PreferencesUtil.getBooleanPreferences(HomeActivity.this, tabClick , false)
				&& Tools.isPicture(tab)
				&& !Tools.isEmptyString(PreferencesUtil.getStringPreferences(this, tab+"BeginTime"))
				&& !Tools.isEmptyString(PreferencesUtil.getStringPreferences(this, tab+"EndTime"))
				&& System.currentTimeMillis()>= TimeUtil.getTimestamp(PreferencesUtil.getStringPreferences(this, tab+"BeginTime"),"yyyy-MM-dd HH:mm:ss")
				&& System.currentTimeMillis() <= TimeUtil.getTimestamp(PreferencesUtil.getStringPreferences(this, tab+"EndTime"),"yyyy-MM-dd HH:mm:ss")
				) {
			itemBean.setAdUrl(PreferencesUtil.getStringPreferences(this, tab+"AdUrl"));
			InitAdvertisementPopupWindow();
		}
	}
	
	@Override
	public void onFaile(String sign, Bundle bundle) {
		if (sign.equals(Protocol.GET_APP_VERSION_INFO)) {
			PreferencesUtil.setPreferences(getApplicationContext(),
					"versNumberServer", String.valueOf(versNumber));
			//广告信息
			GetDataPost.getInstance(this).getAdvertisement(handler,null,null);
		} else {
			showToast(bundle.getString(Protocol.MSG));
		}
	}
	
	private void initImageDate() {
		
		for (AdvertisementBean bean : advertisementBeans) {
			if(!Tools.isEmptyString(bean.getAdvertPic())
					&& !Tools.isEmptyString(bean.getBeginTime()) 
					&& !Tools.isEmptyString(bean.getEndTime())
					){					
//					update(bean);
					if(System.currentTimeMillis() > TimeUtil.getTimestamp(bean.getBeginTime(),"yyyy-MM-dd HH:mm:ss")
						&& System.currentTimeMillis() <= TimeUtil.getTimestamp(bean.getEndTime(),"yyyy-MM-dd HH:mm:ss")){
					//未结束可更新
					if(Tools.judgeString(bean.getAdLocation(), "5")){
						ImageLoader(bean,  "StartpageAdUrl",  "Startpage", "StartpageClick");
					}else if(Tools.judgeString(bean.getAdLocation(),"0")){
						isSplashScreena = true;
						if(!Tools.isEmptyString(bean.getAdTime())){
							PreferencesUtil.setPreferences(this, "SplashScreenatime",bean.getAdTime());
						}
						ImageLoader(bean, "SplashScreenUrl",  "SplashScreenadvertisement", "SplashScreenadvertisementClick");
					}else if(Tools.judgeString(bean.getAdLocation(),"1")){
						istab1 = true;
						ImageLoader(bean, "Tab1AdUrl",  "Tab1", "Tab1Click");
					}else if(Tools.judgeString(bean.getAdLocation(),"2")) {
						istab2 = true;
						ImageLoader(bean, "Tab2AdUrl",  "Tab2", "Tab2Click");
					}else if(Tools.judgeString(bean.getAdLocation(),"3")) {
						istab3 = true;
						ImageLoader(bean, "Tab3AdUrl",  "Tab3", "Tab3Click");
					}else if(Tools.judgeString(bean.getAdLocation(),"4")) {
						istab4 = true;
						ImageLoader(bean, "Tab4AdUrl",  "Tab4", "Tab4Click");
					}
				}
			}			
		}
		EmptyProcess();
	}
	/**
	 * 判断数据不存在处理办法
	 */
	private void EmptyProcess() {
		if(!isSplashScreena){
			PreferencesUtil.setPreferences(this, "SplashScreenadvertisementBeginTime","2000-09-16 13:30:15");
			PreferencesUtil.setPreferences(this, "SplashScreenadvertisementEndTime","2000-09-16 13:30:15");
		}
		if (!istab1) {
			PreferencesUtil.setPreferences(this, "Tab1BeginTime","2000-09-16 13:30:15");
			PreferencesUtil.setPreferences(this, "Tab1EndTime","2000-09-16 13:30:15");
		}
		if (!istab2) {
			PreferencesUtil.setPreferences(this, "Tab2BeginTime","2000-09-16 13:30:15");
			PreferencesUtil.setPreferences(this, "Tab2EndTime","2000-09-16 13:30:15");
		}
		if (!istab3) {
			PreferencesUtil.setPreferences(this, "Tab3BeginTime","2000-09-16 13:30:15");
			PreferencesUtil.setPreferences(this, "Tab3EndTime","2000-09-16 13:30:15");
		}
		if (!istab4) {
			PreferencesUtil.setPreferences(this, "Tab4BeginTime","2000-09-16 13:30:15");
			PreferencesUtil.setPreferences(this, "Tab4EndTime","2000-09-16 13:30:15");
		}
	}


	/**
	 * 更新时间
	 * @param
	 */
	private void savetime(String Picname,String begintime,String endtime,String adtgo) {
		PreferencesUtil.setPreferences(this, Picname+"BeginTime",begintime);
		PreferencesUtil.setPreferences(this, Picname+"EndTime",endtime);
		PreferencesUtil.setPreferences(this, Picname+"adtgo",adtgo);
	}
	
	/**
	 * 图片加载
	 * @param bean AdvertisementBean 图片信息
	 * @param AdUrlName 广告名字	
	 * @param Picname 广告图片名字				
	 * @param ClickName 保存是否已经点击的文件名		 	
	 */
	public void ImageLoader(AdvertisementBean bean ,String AdUrlName,String Picname ,String ClickName){
		if((!Tools.isPicture(bean.getAdId())
				&& !Tools.isPicture(PreferencesUtil.getStringPreferences(this, Picname+"AdId"))
				&& !PreferencesUtil.getStringPreferences(this, Picname+"AdId").equals(bean.getAdId()))
				|| (!Tools.isPicture(bean.getAdId()) 
				&& Tools.isPicture(PreferencesUtil.getStringPreferences(this, Picname+"AdId")))
				){
			new PictureUpload(this,this, bean.getAdvertPic(), Picname,bean.getBeginTime(),bean.getEndTime(),bean.getAdId(),bean.getAdGoto(),AdUrlName, bean.getAdUrl())
			.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"");
		}else {
			if(istab&&Picname.equals("Tab1")){
				tabClick = "Tab1Click";
				tab = "Tab1";
				initPopupWindow();				
			}
		}
	}
	
	@Override
	public void Appear() {
		if(istab){
			tabClick = "Tab1Click";
			tab = "Tab1";
			initPopupWindow();
		}
	}
	@Override
	public void systemNotice() {
		findViewById(R.id.fragment_myperson_red).setVisibility(View.VISIBLE);
		
	}
	@Override
	public void orderStatusChange() {
		findViewById(R.id.fragment_myperson_red).setVisibility(View.VISIBLE);
		
	}
	@Override
	public void couponNotice() {
		findViewById(R.id.fragment_myperson_red).setVisibility(View.VISIBLE);
	}
	@Override
	public void homemap() {
		istab = false;
		clickTabOfMyorder();
	} 
	/*
	 * 充电
	 */
	
	public void registerBoradcastReceiver(){  
        IntentFilter myIntentFilter = new IntentFilter();  
        myIntentFilter.addAction(BroadcastUtil.BROADCAST_Modify_UserInfo);  
        myIntentFilter.addAction(BroadcastUtil.BROADCAST_LOGIN); 
        myIntentFilter.addAction(BroadcastUtil.BROADCAST_Charge_Ing);  
        myIntentFilter.addAction(BroadcastUtil.BROADCAST_Charge_CANCLE);  
        myIntentFilter.addAction(BroadcastUtil.BROADCAST_Charge_CHANGE);  
        //注册广播        
        getActivity().registerReceiver(mBroadcastReceiver, myIntentFilter);  
        IntentFilter myIntentFilter2 = new IntentFilter();  
        myIntentFilter2.addAction(ConnectivityManager.CONNECTIVITY_ACTION);  
        getActivity().registerReceiver(broadcastReceiver, myIntentFilter2);
    } 
	NetWorkChangeBroadcastReceiver broadcastReceiver = new NetWorkChangeBroadcastReceiver();
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){  
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			 if(action.equals(BroadcastUtil.BROADCAST_Modify_UserInfo)){  
					 GetDataPost.getInstance(getActivity()).getUserInfo(handler, pkuserId);
	         } else if( action.equals(BroadcastUtil.BROADCAST_LOGIN)){
	        	 String pkid = intent.getStringExtra("pkUserId");
	        	 GetDataPost.getInstance(getActivity()).getUserInfo(handler, pkid);
	         }  else if(action.equals(BroadcastUtil.BROADCAST_Charge_CANCLE)){
	        	 //充电结束
	        	 PreferencesUtil.setPreferences(getActivity(),"chargepilenum","");
	        	 PreferencesUtil.setPreferences(getActivity(),"chargeheadnum","");
	        	 iv_charging_animation.setVisibility(View.GONE);
//	        	 fl_charging_confirm.setVisibility(View.GONE);
	         } else if(action.equals(BroadcastUtil.BROADCAST_Charge_Ing)){
	        	 //充电进行中
	        	 pilenum = intent.getStringExtra("chargepilenum");
	        	 headnum = intent.getStringExtra("chargeheadnum");
	        	 PreferencesUtil.setPreferences(getActivity(),"chargepilenum",pilenum);
	        	 PreferencesUtil.setPreferences(getActivity(),"chargeheadnum",headnum);
	        	 iv_charging_animation.setVisibility(View.VISIBLE);
//	        	 fl_charging_confirm.setVisibility(View.VISIBLE);
//				 animation.setDuration(3000);
//				 animation.setRepeatCount(-1);
//				 iv_charging_animation.startAnimation(animation);
	        	  
	         }else if(action.equals(BroadcastUtil.BROADCAST_Charge_CHANGE)){
	        	 iv_charging_animation.setText("充电中");
//	        	 fl_charging_confirm.setVisibility(View.GONE);
	         }
		}  
		
    };
    
    
    @Override
	public void handleTcpPacket(final ByteArrayInputStream result) {
		//收到实时数据，进入实时数据界面
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						cancelPD();
					    try {
							StreamUtil.readByte(result);//int reason = 
							short cmdtype = StreamUtil.readShort(result);
							switch (cmdtype) {
							case SocketConstant.CMD_TYPE_REAL_DATA:
								handleRealDataEvent(result);
								break;
							case SocketConstant.CMD_TYPE_CONNECT:
								handleConnectEvent(result);
								break;
							case SocketConstant.CMD_TYPE_CONSUME_RECORD:
								cancelPD();
								showToast("充电已结束");
								 PreferencesUtil.setPreferences(getActivity(),"chargepilenum","");
					        	 PreferencesUtil.setPreferences(getActivity(),"chargeheadnum","");
					        	 iv_charging_animation.setVisibility(View.GONE);
//					        	 fl_charging_confirm.setVisibility(View.GONE);
					        	 if (mapawaitchange!=null) {					
					        		 mapawaitchange.mapawaitchange();
								}
					        	 mTcpSocketManager.close();
								break;
							default:
								break;
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				});
			}
			//处理socket-实时数据事件
			private void handleRealDataEvent(ByteArrayInputStream result) throws IOException{
				int state = StreamUtil.readByte(result);
				short chargeTime = StreamUtil.readShort(result);
				StreamUtil.readShort(result);//short dianya = 
				StreamUtil.readShort(result);//short dianliu = 
				int diandu = StreamUtil.readInt(result);
				short feilv = StreamUtil.readShort(result);
				int yuchong = StreamUtil.readInt(result);
				int yichong = StreamUtil.readInt(result);
				int soc = StreamUtil.readByte(result);
				StreamUtil.readInt(result);//int fushu = 
				StreamUtil.readInt(result);//int gaojing = 
				
//				if (17 == state) {
//					iv_charging_animation.setText("等待中");
					
//					if (isonclick) {							
//						Intent realIn = new Intent(getActivity(),
//								PileDetailActivity.class);
//						if (tab.equals("Tab1")) {
//							realIn.putExtra("interfacefrom", "home");
//						}else if (tab.equals("Tab2")) {
//							realIn.putExtra("interfacefrom", "mapmob");
//						}else if (tab.equals("Tab4")) {
//							realIn.putExtra("interfacefrom", "person");
//						}
//						realIn.putExtra("awaitType", 1);//1等待 0详情
//						realIn.putExtra("scanInfo", mScanInfoBean);
//						startActivity(realIn);
//						isonclick = false;
//					}
//				}else 
				if (6 == state/*&&isonclick*/) {	
					iv_charging_animation.setText("充电中");
//					if (isonclick) {	
				Intent realIn = new Intent(getActivity(),
						RealTimeChargeActivity.class);
				realIn.putExtra("state", state);
				realIn.putExtra("chargeTime", chargeTime);
				realIn.putExtra("diandu", diandu);
				realIn.putExtra("feilv", feilv);
				realIn.putExtra("yuchong", yuchong);
				realIn.putExtra("yichong", yichong);
				realIn.putExtra("soc", soc);
				if (tab.equals("Tab1")) {
					realIn.putExtra("interfacefrom", "home");
				}else if (tab.equals("Tab2")) {
					realIn.putExtra("interfacefrom", "mapmob");
				}else if (tab.equals("Tab4")) {
					realIn.putExtra("interfacefrom", "person");
				}
				//mTcpSocketManager.close();
				startActivity(realIn);
//				isonclick = false;
					}	
				}
//			}
			//处理socket-连接充电桩事件
			private void handleConnectEvent(ByteArrayInputStream result) throws IOException {
				int successflag = StreamUtil.readByte(result);
				short errorcode = StreamUtil.readShort(result);
				int headState = StreamUtil.readByte(result);
				int type = 1;
				 try {
					type = StreamUtil.readByte(result);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mTcpSocketManager.setPileType(type);
				switch (successflag) {
				case 1:
					LogUtil.i("cm_netPost", "headState = " + headState);
					//连接成功
					/*if (17 == headState) {
						iv_charging_animation.setText("等待中");
//						if (isonclick) {							
							Intent realIn = new Intent(getActivity(),
									PileDetailActivity.class);
							if (tab.equals("Tab1")) {
								realIn.putExtra("interfacefrom", "home");
							}else if (tab.equals("Tab2")) {
								realIn.putExtra("interfacefrom", "mapmob");
							}else if (tab.equals("Tab4")) {
								realIn.putExtra("interfacefrom", "person");
							}
							realIn.putExtra("awaitType", 1);//1等待 0详情
							System.out.println("```````````````````````````````==="+mScanInfoBean.toString());
							realIn.putExtra("scanInfo", mScanInfoBean);
							startActivity(realIn);
//							isonclick = false;
//						}
					}else*/
						if (6 == headState) {
						iv_charging_animation.setText("充电中");
					}else if(3 == headState || 0 == headState){
						 showToast("充电已结束");
						 PreferencesUtil.setPreferences(getActivity(),"chargepilenum","");
			        	 PreferencesUtil.setPreferences(getActivity(),"chargeheadnum","");
			        	 iv_charging_animation.setVisibility(View.GONE);
//			        	 fl_charging_confirm.setVisibility(View.GONE);
			        	 if (mapawaitchange!=null) {					
			        		 mapawaitchange.mapawaitchange();
						}
			        	 mTcpSocketManager.close();
					}
					break;
				case 0:
					showErrorCode(errorcode);
					mTcpSocketManager.close();
					break;
				default:
					break;
				}
			}
			
			@Override
			public void handleSocketException() {
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						cancelPD();
						showErrorCode(110);
					}
				});
			}
			
			
			public static void setmapAwaitChangeSize(MapAwaitChange mapchange){
				mapawaitchange = mapchange;
			}
			public interface MapAwaitChange{
				public abstract void mapawaitchange();
			}
}
