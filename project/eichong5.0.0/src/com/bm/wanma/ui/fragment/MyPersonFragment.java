package com.bm.wanma.ui.fragment;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

import com.bm.wanma.R;
import com.bm.wanma.broadcast.BroadcastUtil;
import com.bm.wanma.entity.BanlanceBean;
import com.bm.wanma.entity.CouponInfoBean;
import com.bm.wanma.entity.IntegralEventBean;
import com.bm.wanma.entity.PersonIntegralBean;
import com.bm.wanma.entity.UserInfoBean;
import com.bm.wanma.jpush.MyReceiver;
import com.bm.wanma.jpush.MyReceiver.SystemNoticeListener;
import com.bm.wanma.net.GetDataPost;
import com.bm.wanma.net.Protocol;
import com.bm.wanma.socket.SocketConstant;
import com.bm.wanma.socket.StreamUtil;
import com.bm.wanma.socket.TCPSocketManager;
import com.bm.wanma.ui.activity.EverydayGetActivity;
import com.bm.wanma.ui.activity.ITcpCallBack;
import com.bm.wanma.ui.activity.IntegralDetailActivity;
import com.bm.wanma.ui.activity.MyBillActivity;
import com.bm.wanma.ui.activity.MyChargeActivity;
import com.bm.wanma.ui.activity.MyCouponActivity;
import com.bm.wanma.ui.activity.LoginActivity;
import com.bm.wanma.ui.activity.MyNewsActivity;
import com.bm.wanma.ui.activity.MyUserInfoActivity;
import com.bm.wanma.ui.activity.NickActivity;
import com.bm.wanma.ui.activity.RealTimeChargeActivity;
import com.bm.wanma.ui.activity.RechargeActivity;
import com.bm.wanma.ui.activity.SettingsActivity;
import com.bm.wanma.utils.LogUtil;
import com.bm.wanma.utils.PreferencesUtil;
import com.bm.wanma.utils.Tools;
import com.bm.wanma.view.RoundImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 我的个人中心界面
 * @author cm
 *
 */
public class MyPersonFragment extends BaseFragment implements OnClickListener,ITcpCallBack,SystemNoticeListener{
//	private FrameLayout fl_charging_confirm;
	private RoundImageView iv_photo;
	private TextView tv_login,tv_nick,tv_integral,tv_balance,tv_freeze;
	private String pkUserId,userName,userPhone,imgurl;
	private String integralsize="0";
	private UserInfoBean userInfoBean;
	private CouponInfoBean couponInfoBean;
	private FrameLayout fl_msg;
	private TextView tv_recharge,tv_bill,tv_coupon,tv_activity,rl_mySettings,tv_sign_in;
	private TextView tv_recharge_record,tv_charge_finish,tv_charge_unfinish;
	private RelativeLayout rl_myCoupon,rl_freeze_v,rl_integral_detail,rl_bianji;
	private View myNews_red,myCoupon_red;
	private boolean isShowNotice,isShowOrderStatus,isCoupon,isCurrentFragment;
	private TextView iv_charging_animation;
	private String pilenum,headnum, nickName ;
	private TCPSocketManager mTcpSocketManager;
//	private AlphaAnimation animation = new AlphaAnimation(1, 0);
	public static final int NICK = 0x01;
	private String  provincecode, citycode;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		isCurrentFragment = true;
		mPageName = "MyPersonFragment"; 
		
		registerBoradcastReceiver();//注册调用户信息接口
		MyReceiver.setOnSystemNoticeListener(this);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View MypersonFragment = inflater.inflate(
				R.layout.fragment_myperson, container, false);
		initView(MypersonFragment);
		pkUserId = PreferencesUtil.getStringPreferences(getActivity(), "pkUserinfo");
		if(!Tools.isEmptyString(pkUserId)){
			 GetDataPost.getInstance(getActivity()).getUserInfo(handler, pkUserId);
		 }
	
		return MypersonFragment;
	}
	 @Override
	public void onStart() {
		super.onStart();
		
	}
	 
	 @Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		if(!hidden){
			//重新加载数据
			isCurrentFragment = true;
			onResume();
		}else {
			isCurrentFragment = false;
		}
	}


	@Override
	public void onResume() { 
		super.onResume();
		if(!isCurrentFragment){
			return;
		}

		pkUserId = PreferencesUtil.getStringPreferences(getActivity(), "pkUserinfo");
		if(Tools.isEmptyString(pkUserId)){//未登录
			tv_nick.setVisibility(View.GONE);
			rl_bianji.setVisibility(View.GONE);
			
			tv_integral.setVisibility(View.GONE);
			rl_integral_detail.setVisibility(View.GONE);
			
			tv_login.setVisibility(View.VISIBLE);
			tv_recharge_record.setVisibility(View.INVISIBLE);
			myNews_red.setVisibility(View.GONE);
			myCoupon_red.setVisibility(View.GONE);
			tv_balance.setVisibility(View.INVISIBLE);
			tv_activity.setText("");
//			iv_charging_animation.setVisibility(View.GONE);
//			fl_charging_confirm.setVisibility(View.GONE);
			iv_photo.setImageBitmap(BitmapFactory.decodeStream(
					getResources().openRawResource(R.drawable.default_user_img)));
		}else {
			 userName = PreferencesUtil.getStringPreferences(getActivity(), "nickName");
			 userPhone = PreferencesUtil.getStringPreferences(getActivity(), "usinPhone");
			 //userBalance = PreferencesUtil.getStringPreferences(getActivity(), "usinAccountbalance");
			 imgurl = PreferencesUtil.getStringPreferences(getActivity(), "usinHeadimage");
			 tv_nick.setVisibility(View.VISIBLE);
			 rl_bianji.setVisibility(View.VISIBLE);
			 GetDataPost.getInstance(getActivity()).getUserInfo(handler, pkUserId);
//			 if (integralsize.equals("0")) {
//				 tv_integral.setVisibility(View.GONE);
//				 rl_integral_detail.setVisibility(View.GONE);
//			}else {				
				tv_integral.setVisibility(View.VISIBLE);
				rl_integral_detail.setVisibility(View.VISIBLE);
//			}
			 
			 tv_login.setVisibility(View.GONE);
			 tv_recharge_record.setVisibility(View.VISIBLE);
			// tv_balance.setVisibility(View.VISIBLE);
			 if(isNetConnection()){
				 GetDataPost.getInstance(getActivity()).getMyCouponInfo(handler, pkUserId);
				 GetDataPost.getInstance(getActivity()).getBalance(handler, pkUserId);
				 GetDataPost.getInstance(getActivity()).getIntegralPersonage(handler, pkUserId);
				 provincecode = PreferencesUtil.getStringPreferences(getActivity(),"provincecode");
				 citycode = PreferencesUtil.getStringPreferences(getActivity(), "citycode");
				 if (Tools.isEmptyString(provincecode)||Tools.isEmptyString(citycode)) {
						provincecode = "330000";
						citycode = "330100";
					}
				 GetDataPost.getInstance(getActivity()).getIntegralEvent(handler, pkUserId, provincecode, citycode, "3");
				 tv_balance.setVisibility(View.VISIBLE);
			 }else {
				 tv_balance.setText("--");
			 }
			if(Tools.isEmptyString(userName)){//昵称为空
				tv_nick.setText(userPhone);
			}else {
				tv_nick.setText(userName);
			}
//			if (integralsize.equals("0")) {
//				tv_integral.setVisibility(View.GONE);
//				rl_integral_detail.setVisibility(View.GONE);
//			}else {
				tv_integral.setVisibility(View.VISIBLE);
				rl_integral_detail.setVisibility(View.VISIBLE);
//			}
			tv_integral.setText("积分 "+integralsize);
			isShowNotice = PreferencesUtil.getBooleanPreferences(getActivity(), "isShowSystemNotice", false);
			isShowOrderStatus = PreferencesUtil.getBooleanPreferences(getActivity(), "isShowOrderStatus", false);
			isCoupon = PreferencesUtil.getBooleanPreferences(getActivity(), "isCoupon", false);
			if(isShowNotice || isShowOrderStatus){
				myNews_red.setVisibility(View.VISIBLE);
			}else {
				myNews_red.setVisibility(View.GONE);
			}
			if(isCoupon){
				myCoupon_red.setVisibility(View.VISIBLE);
			}else {
				myCoupon_red.setVisibility(View.GONE);
			}
			if(isShowNotice || isShowOrderStatus || isCoupon){
				getActivity().findViewById(R.id.fragment_myperson_red).setVisibility(View.VISIBLE);
			}else {
				getActivity().findViewById(R.id.fragment_myperson_red).setVisibility(View.GONE);
			}
			//充电动画
//			pilenum = PreferencesUtil.getStringPreferences(getActivity(),"chargepilenum");
//			headnum = PreferencesUtil.getStringPreferences(getActivity(),"chargeheadnum");
//			if(!Tools.isEmptyString(pilenum) && !Tools.isEmptyString(headnum)){
//				iv_charging_animation.setVisibility(View.VISIBLE);
//				fl_charging_confirm.setVisibility(View.VISIBLE);
//				animation.setDuration(3000);
//				animation.setRepeatCount(-1);
//				iv_charging_animation.startAnimation(animation);
//			}else {
				iv_charging_animation.setVisibility(View.GONE);
//				fl_charging_confirm.setVisibility(View.GONE);
//			}
			//设置头像
			if(!Tools.isEmptyString(imgurl)){
				DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.default_user_img)
				.showImageOnFail(R.drawable.default_user_img) 
				.cacheInMemory(true)
				.cacheOnDisk(false)
				.bitmapConfig(Config.RGB_565)
				.build();
				ImageLoader.getInstance().displayImage(imgurl, iv_photo, options);
			}
		}
		
		
	}

	private void updataView() {

		tv_nick.setVisibility(View.VISIBLE);
		rl_bianji.setVisibility(View.VISIBLE);
//		if (integralsize.equals("0")) {
//			tv_integral.setVisibility(View.GONE);
//			rl_integral_detail.setVisibility(View.GONE);
//		}else {			
			tv_integral.setVisibility(View.VISIBLE);
			rl_integral_detail.setVisibility(View.VISIBLE);
//		}
		tv_login.setVisibility(View.GONE);
		tv_recharge_record.setVisibility(View.VISIBLE);
		if (!Tools.isEmptyString(userInfoBean.getUserNickName())) {// 昵称为空
			tv_nick.setText(userInfoBean.getUserNickName());
		} else {
			tv_nick.setText(""+userInfoBean.getUserTel());
		}
		tv_integral.setText("积分 "+integralsize);
		//tv_balance.setText("" +userInfoBean.getUserBlance());
		// 设置头像
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.default_user_img)
				.showImageOnFail(R.drawable.default_user_img).cacheInMemory(true)
				.cacheOnDisk(false).bitmapConfig(Config.RGB_565).build();
		ImageLoader.getInstance().displayImage(userInfoBean.getUserImage(), iv_photo, options);
	}


	private void initView(View mainView){
		iv_photo = (RoundImageView) mainView.findViewById(R.id.fragment_myperson_photo);
		//iv_photo.setOnClickListener(this);
		tv_login = (TextView) mainView.findViewById(R.id.fragment_myperson_login);
		tv_login.setOnClickListener(this);
		tv_nick = (TextView) mainView.findViewById(R.id.fragment_myperson_nick);
		tv_integral = (TextView) mainView.findViewById(R.id.fragment_myperson_integral);
//		fl_charging_confirm = (FrameLayout) mainView.findViewById(R.id.charging_confirm);
		fl_msg = (FrameLayout) mainView.findViewById(R.id.fragment_myperson_msg);
		fl_msg.setOnClickListener(this);
		
		iv_charging_animation = (TextView) mainView.findViewById(R.id.fragment_myperson_charging);
		iv_charging_animation.setOnClickListener(this);
		
		tv_balance = (TextView) mainView.findViewById(R.id.fragment_myperson_blance);
		rl_freeze_v = (RelativeLayout) mainView.findViewById(R.id.rl_freeze);
		tv_freeze = (TextView) mainView.findViewById(R.id.fragment_myperson_freeze);
		
		tv_recharge = (TextView) mainView.findViewById(R.id.fragment_myperson_recharge);
		tv_recharge.setOnClickListener(this);
		tv_coupon = (TextView) mainView.findViewById(R.id.fragment_myperson_coupon_out);
		
		tv_bill = (TextView) mainView.findViewById(R.id.fragment_myperson_bill);
		tv_bill.setOnClickListener(this);
		tv_recharge_record = (TextView) mainView.findViewById(R.id.fragment_myperson_charge_record);
		tv_recharge_record.setOnClickListener(this);
		tv_charge_finish = (TextView) mainView.findViewById(R.id.fragment_myperson_charge_finish);
		tv_charge_finish.setOnClickListener(this);
		tv_charge_unfinish = (TextView) mainView.findViewById(R.id.fragment_myperson_charge_unfinish);
		tv_charge_unfinish.setOnClickListener(this);
		
		rl_myCoupon = (RelativeLayout) mainView.findViewById(R.id.fragment_myperson_coupon);
		rl_myCoupon.setOnClickListener(this);
		rl_mySettings = (TextView) mainView.findViewById(R.id.fragment_myperson_settings);
		rl_mySettings.setOnClickListener(this);
		myNews_red = mainView.findViewById(R.id.fragment_myperson_msg_red);
		myCoupon_red = mainView.findViewById(R.id.fragment_myperson_coupon_red);
		tv_activity = (TextView) mainView.findViewById(R.id.fragment_myperson_hint);
		
		rl_bianji = (RelativeLayout) mainView.findViewById(R.id.bianji_rl);
		rl_bianji.setOnClickListener(this);
		
		rl_integral_detail = (RelativeLayout) mainView.findViewById(R.id.integral_detail_rl);
		rl_integral_detail.setOnClickListener(this);
		
		tv_sign_in = (TextView) mainView.findViewById(R.id.fragment_myperson_sign_in);
		tv_sign_in.setOnClickListener(this);
	}
		@Override
		public void onActivityResult(int requestCode, int resultCode, Intent intent) {
			if (resultCode == 0) {
				return;
			}
			switch (requestCode) {
			
			case NICK:
				if(!TextUtils.isEmpty(intent.getStringExtra("nick")) && 
						!intent.getStringExtra("nick").equals(nickName)){
					nickName = intent.getStringExtra("nick");
					
					commitNick();
				}
				break;
				}
			
			super.onActivityResult(requestCode, resultCode, intent);
		}
		private void commitNick(){
			if(isNetConnection()){
				
				tv_nick.setText(nickName +"");
				GetDataPost.getInstance(getActivity()).modifyUserInfo(handler, pkUserId, nickName, "", "", "");
			}else {
				showToast("网络连接异常，请稍后再试...");
			}
		}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bianji_rl:
			//昵称
//			nickName = tv_nick.getText().toString().trim();
//			Intent nickIn = new Intent(getActivity(),NickActivity.class);
//			nickIn.putExtra("nick", nickName);
//			startActivityForResult(nickIn, NICK);

			//用户详情信息
			if(userInfoBean!= null){
				Intent myuserinfoIn = new Intent();
				myuserinfoIn.putExtra("userInfo", userInfoBean);
				myuserinfoIn.setClass(getActivity(), MyUserInfoActivity.class);
				startActivity(myuserinfoIn);
				updataUserInfo();
			}
			
			break;
			
		case R.id.integral_detail_rl:
			Intent integralIn = new Intent();
			integralIn.putExtra("integralsize", integralsize);
			integralIn.setClass(getActivity(), IntegralDetailActivity.class);
			getActivity().startActivity(integralIn);
			break;
		
		case R.id.fragment_myperson_photo:
			//个人资料
			if(Tools.isEmptyString(pkUserId)){
				Intent loginIn = new Intent();
				loginIn.setClass(getActivity(), LoginActivity.class);
				loginIn.putExtra("source_inteface", "myperson_photo");
				getActivity().startActivity(loginIn);
			}else {
				Intent myuserinfoIn = new Intent();
				myuserinfoIn.setClass(getActivity(), MyUserInfoActivity.class);
				if(userInfoBean != null){
					myuserinfoIn.putExtra("userInfo", userInfoBean);
					getActivity().startActivity(myuserinfoIn);
				}else {
					showToast("网络不稳，稍后再试");
					GetDataPost.getInstance(getActivity()).getUserInfo(handler, pkUserId);
				}
			}
			break;
	
		case R.id.fragment_myperson_login:
			MobclickAgent.onEvent(mContext, "wode_denglu");
			//未登录
			Intent loginIn = new Intent();
			loginIn.putExtra("source_inteface", "login");
			loginIn.setClass(getActivity(), LoginActivity.class);
			getActivity().startActivity(loginIn);
			break;
		case R.id.fragment_myperson_msg:
			MobclickAgent.onEvent(mContext, "wode_xinxitubiao");
			//消息
			getActivity().startActivity(new Intent(getActivity(),MyNewsActivity.class));
			break;

		case R.id.fragment_myperson_recharge:
			MobclickAgent.onEvent(mContext, "wode_chongzhi");
			//充值
			Intent rechargeIn = new Intent();
			if(Tools.isEmptyString(pkUserId)){
				rechargeIn.putExtra("source_inteface", "myperson_recharge");
				rechargeIn.setClass(getActivity(), LoginActivity.class);
			}else {
				rechargeIn.setClass(getActivity(), RechargeActivity.class);
			}
			getActivity().startActivity(rechargeIn);
			break;
		case R.id.fragment_myperson_bill:
			MobclickAgent.onEvent(mContext, "wode_zhangdan");
			//账单
			Intent billIn = new Intent();
			if(Tools.isEmptyString(pkUserId)){
				billIn.putExtra("source_inteface", "myperson_bill");
				billIn.setClass(getActivity(), LoginActivity.class);
			}else {
				billIn.setClass(getActivity(), MyBillActivity.class);
			}
			getActivity().startActivity(billIn);
			break;
		case R.id.fragment_myperson_charge_record:
			MobclickAgent.onEvent(mContext, "wode_wodechongdian");
		case R.id.fragment_myperson_charge_unfinish:
			//查看全部充电记录,未完成
			Intent recordIn = new Intent();
			recordIn.putExtra("defaultpage", "pagedun");
			if(Tools.isEmptyString(pkUserId)){
				recordIn.putExtra("source_inteface", "myperson_record");
				recordIn.setClass(getActivity(), LoginActivity.class); 
			}else {
				recordIn.setClass(getActivity(), MyChargeActivity.class);
			}
			getActivity().startActivity(recordIn);
			break;
		case R.id.fragment_myperson_charge_finish:
			//已完成
			Intent rechargeFinishIn = new Intent();
			rechargeFinishIn.putExtra("defaultpage", "pagedone");
			if(Tools.isEmptyString(pkUserId)){
				rechargeFinishIn.putExtra("source_inteface", "myperson_record");
				rechargeFinishIn.setClass(getActivity(), LoginActivity.class);
			}else {
				rechargeFinishIn.setClass(getActivity(), MyChargeActivity.class);
			}
			getActivity().startActivity(rechargeFinishIn);
			break;
		case R.id.fragment_myperson_sign_in:
			Intent everydayIn = new Intent();
			everydayIn.setClass(getActivity(), EverydayGetActivity.class);
			getActivity().startActivity(everydayIn);
			break;
		case R.id.fragment_myperson_coupon:
			MobclickAgent.onEvent(mContext, "wode_youhuiquan");
			//优惠券
			Intent couponIn = new Intent();
			if(Tools.isEmptyString(pkUserId)){
				couponIn.putExtra("source_inteface", "myperson_coupon");
				couponIn.setClass(getActivity(), LoginActivity.class);
			}else {
				couponIn.setClass(getActivity(), MyCouponActivity.class);
			}
			getActivity().startActivity(couponIn);
			PreferencesUtil.setPreferences(getActivity(), "isCoupon", false);
			
			break;

		case R.id.fragment_myperson_settings:
			MobclickAgent.onEvent(mContext, "wode_shezhi");
			//设置
			if(Tools.isEmptyString(pkUserId)){
				Intent newsIn = new Intent();
				newsIn.putExtra("source_inteface", "myperson_settings");
				newsIn.setClass(getActivity(), LoginActivity.class);
				getActivity().startActivity(newsIn);
			}else {
				Intent settingsIn = new Intent();
				settingsIn.setClass(getActivity(),SettingsActivity.class);
				getActivity().startActivity(settingsIn);
			}
			break;
		case R.id.fragment_myperson_charging:
			MobclickAgent.onEvent(mContext, "wode_zhengzaichongdian");
			//充电中
			showPD("正在获取充电信息...");
			mTcpSocketManager = TCPSocketManager.getInstance(getActivity());
			mTcpSocketManager.setTcpCallback(this);
			if(!mTcpSocketManager.hasTcpConnection()&&!Tools.isEmptyString(pilenum)
					&& !Tools.isEmptyString(headnum)){
				mTcpSocketManager.conn(pilenum, 
						Byte.parseByte(headnum));
			}else {
				cancelPD();
				showErrorCode(110);
			}
			
			break;
		default:
			break;
		}
		
	}

	
	
	@Override
	public void onSuccess(String sign, Bundle bundle) {
		if (sign.equals(Protocol.INTEGRAL_PERSONAGE)) {
			//个人积分
			if (bundle != null) {
			PersonIntegralBean personIntegralBean = (PersonIntegralBean) bundle.getSerializable(Protocol.DATA);
			if(personIntegralBean!= null){
//				if (personIntegralBean.getPoint().equals("0")) {
//					tv_integral.setVisibility(View.GONE);
//					rl_integral_detail.setVisibility(View.GONE);
//				}else {
					tv_integral.setVisibility(View.VISIBLE);
					rl_integral_detail.setVisibility(View.VISIBLE);
//				}
				integralsize = personIntegralBean.getPoint();
				tv_integral.setText("积分 "+personIntegralBean.getPoint());
			}}
		}else if (sign.equals(Protocol.GET_USER_INFO)) {
			//用户详情信息 
			if (bundle != null) {
			userInfoBean = (UserInfoBean) bundle.getSerializable(Protocol.DATA);
			if(userInfoBean!= null){
				
				updataView();
				updataUserInfo();
			}}
		}else if(sign.equals(Protocol.BANLANCE)){
			//获取余额
			if (bundle != null) {
				BanlanceBean mBanlanceBean = (BanlanceBean) bundle
						.getSerializable(Protocol.DATA);
				if(mBanlanceBean != null){
					tv_balance.setText(mBanlanceBean.getUserAB()+"元");
					if (!Tools.isEmptyString(mBanlanceBean.getFreezingAmt())
		  			  			&&Float.valueOf(mBanlanceBean.getFreezingAmt())>0) {
						rl_freeze_v.setVisibility(View.VISIBLE);
						BigDecimal servicemoney = new BigDecimal(mBanlanceBean.getFreezingAmt()).setScale(2,BigDecimal.ROUND_HALF_UP);
						tv_freeze.setText(servicemoney.toString()+" 元");
					}else {
						rl_freeze_v.setVisibility(View.GONE);
					}
				}else {
					rl_freeze_v.setVisibility(View.GONE);
					tv_balance.setText("--");
				}
			}
		}else if(sign.equals(Protocol.GET_COUPON_INFO)){
			 //活动
			if (bundle != null) {
			 couponInfoBean = (CouponInfoBean) bundle.getSerializable(Protocol.DATA);
			 if(couponInfoBean != null){
				 if(!TextUtils.isEmpty(couponInfoBean.getAct_Remark()) && couponInfoBean.getAct_Remark().length()>0){
					 tv_activity.setText(couponInfoBean.getAct_Remark());
					 tv_activity.setPadding(0, 8, 0, 8);
				 }else{
					 tv_activity.setText("");
					 tv_activity.setPadding(0, 0, 0, 0);
				 }
				 try {
					if(!TextUtils.isEmpty(couponInfoBean.getOverCpNum()) &&
							Integer.parseInt(couponInfoBean.getOverCpNum())>0 ){
						 tv_coupon.setText(String.format(getResources().getString(R.string.coupon_info),
								 couponInfoBean.getOverCpNum()));
					 }else {
						 tv_coupon.setText("");
					 }
				} catch (NumberFormatException e) {
					 tv_coupon.setText("");
					e.printStackTrace();
				}
			 }
		   }
		}else if (sign.equals(Protocol.MODIFY_USER_INFO)) {
			PreferencesUtil.setPreferences(getActivity(), "nickName",
					nickName);
			tv_nick.setText(""+nickName);
		}else if (sign.equals(Protocol.INTEGRAL_EVENT)) {
			//用户详情信息 
			if (bundle != null) {
				ArrayList<IntegralEventBean> integralEventBeans = (ArrayList<IntegralEventBean>) bundle.getSerializable(Protocol.DATA);
			if (integralEventBeans.size()>0) {
				IntegralEventBean integralEventBean = integralEventBeans.get(0);
				if(integralEventBean!= null){
					tv_sign_in.setVisibility(View.VISIBLE);
					
				}
			}else {
				tv_sign_in.setVisibility(View.GONE);
			}
			}
		}
		
		
	}

	@Override
	public void onFaile(String sign, Bundle bundle) {
		if(sign.equals(Protocol.BANLANCE)){
			tv_balance.setText("--");
		}else if (sign.equals(Protocol.INTEGRAL_EVENT)) {
			tv_sign_in.setVisibility(View.GONE);
		}else if (sign.equals(Protocol.INTEGRAL_PERSONAGE)) {
			tv_integral.setVisibility(View.GONE);
			rl_integral_detail.setVisibility(View.GONE);
		}
		
	}
	
	
	private void updataUserInfo(){
		PreferencesUtil.setPreferences(getActivity(), "usinHeadimage",
				userInfoBean.getUserImage());
		PreferencesUtil.setPreferences(getActivity(), "usinAccountbalance",
				userInfoBean.getUserBlance());
		PreferencesUtil.setPreferences(getActivity(), "carType",
				userInfoBean.getUserCarType());
		PreferencesUtil.setPreferences(getActivity(), "usinPhone",
				userInfoBean.getUserTel());
		PreferencesUtil.setPreferences(getActivity(), "nickName",
				userInfoBean.getUserNickName());
		PreferencesUtil.setPreferences(getActivity(), "pkUserinfo",
				userInfoBean.getPkUserId());
		PreferencesUtil.setPreferences(getActivity(), "usinFacticityname",
				userInfoBean.getUserRealName());
		PreferencesUtil.setPreferences(getActivity(), "usinSex",
				userInfoBean.getUserSex());
		PreferencesUtil.setPreferences(getActivity(), "usinBirthdate",
				userInfoBean.getUserBrithy());
		/*PreferencesUtil.setPreferences(getActivity(), "usinUserstatus",
				userInfoBean.get);*/
	}
	
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		getActivity().unregisterReceiver(mBroadcastReceiver);
	}
	public void registerBoradcastReceiver(){  
        IntentFilter myIntentFilter = new IntentFilter();  
        myIntentFilter.addAction(BroadcastUtil.BROADCAST_Modify_UserInfo);  
        myIntentFilter.addAction(BroadcastUtil.BROADCAST_LOGIN); 
        myIntentFilter.addAction(BroadcastUtil.BROADCAST_Charge_Ing);  
        myIntentFilter.addAction(BroadcastUtil.BROADCAST_Charge_CANCLE);  
        //注册广播        
        getActivity().registerReceiver(mBroadcastReceiver, myIntentFilter);  
    } 
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){  
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			 if(action.equals(BroadcastUtil.BROADCAST_Modify_UserInfo)){  
					 GetDataPost.getInstance(getActivity()).getUserInfo(handler, pkUserId);
	         } else if( action.equals(BroadcastUtil.BROADCAST_LOGIN)){
	        	 String pkid = intent.getStringExtra("pkUserId");
	        	 GetDataPost.getInstance(getActivity()).getUserInfo(handler, pkid);
	         }  
//	         else if(action.equals(BroadcastUtil.BROADCAST_Charge_CANCLE)){
//	        	 //充电结束
//	        	 PreferencesUtil.setPreferences(getActivity(),"chargepilenum","");
//	        	 PreferencesUtil.setPreferences(getActivity(),"chargeheadnum","");
////	        	 iv_charging_animation.setVisibility(View.GONE);
////	        	 fl_charging_confirm.setVisibility(View.GONE);
//	         } else if(action.equals(BroadcastUtil.BROADCAST_Charge_Ing)){
//	        	 //充电进行中
//	        	 pilenum = intent.getStringExtra("chargepilenum");
//	        	 headnum = intent.getStringExtra("chargeheadnum");
//	        	 PreferencesUtil.setPreferences(getActivity(),"chargepilenum",pilenum);
//	        	 PreferencesUtil.setPreferences(getActivity(),"chargeheadnum",headnum);
////	        	 iv_charging_animation.setVisibility(View.VISIBLE);
////	        	 fl_charging_confirm.setVisibility(View.VISIBLE);
////				 animation.setDuration(3000);
////				 animation.setRepeatCount(-1);
////				 iv_charging_animation.startAnimation(animation);
//	        	  
//	         }
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
				Intent realIn = new Intent(getActivity(),
						RealTimeChargeActivity.class);
				realIn.putExtra("state", state);
				realIn.putExtra("chargeTime", chargeTime);
				realIn.putExtra("diandu", diandu);
				realIn.putExtra("feilv", feilv);
				realIn.putExtra("yuchong", yuchong);
				realIn.putExtra("yichong", yichong);
				realIn.putExtra("soc", soc);
				realIn.putExtra("interfacefrom", "person");
				//mTcpSocketManager.close();
				startActivity(realIn);
			}
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
					//连接成功
					if(3 == headState || 0 == headState){
						 showToast("充电已结束");
						 PreferencesUtil.setPreferences(getActivity(),"chargepilenum","");
			        	 PreferencesUtil.setPreferences(getActivity(),"chargeheadnum","");
			        	 iv_charging_animation.setVisibility(View.GONE);
//			        	 fl_charging_confirm.setVisibility(View.GONE);
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
			public void systemNotice() {
				if(!TextUtils.isEmpty(pkUserId)){
					myNews_red.setVisibility(View.VISIBLE);
				}
				
			}
			@Override
			public void orderStatusChange() {
				if(!TextUtils.isEmpty(pkUserId)){
					myNews_red.setVisibility(View.VISIBLE);
				}
				
			}
			@Override
			public void couponNotice() {
				if(!TextUtils.isEmpty(pkUserId)){
					myCoupon_red.setVisibility(View.VISIBLE);
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

}
