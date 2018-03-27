package com.bm.wanma.ui.activity;

import java.util.ArrayList;

import com.bm.wanma.R;
import com.bm.wanma.alipay.Base64;
import com.bm.wanma.broadcast.BroadcastUtil;
import com.bm.wanma.entity.AnchorAll;
import com.bm.wanma.entity.ChargeFinishAndDoneBean;
import com.bm.wanma.entity.ElectricPileDetailsBean;
import com.bm.wanma.entity.ListModeBean;
import com.bm.wanma.entity.LoginBean;
import com.bm.wanma.entity.MapModeBean;
import com.bm.wanma.entity.PowerStationBean;
import com.bm.wanma.entity.UserInfoBean;
import com.bm.wanma.net.GetDataPost;
import com.bm.wanma.net.Protocol;
import com.bm.wanma.ui.scan.CaptureActivity;
import com.bm.wanma.utils.IntentUtil;
import com.bm.wanma.utils.LogUtil;
import com.bm.wanma.utils.PreferencesUtil;
import com.bm.wanma.utils.RegularExpressionUtil;
import com.bm.wanma.utils.ToastUtil;
import com.bm.wanma.utils.Tools;
import com.bm.wanma.ui.activity.MyCouponActivity;
import com.bm.wanma.view.FocusChange;
import com.umeng.analytics.MobclickAgent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 登录界面
 * 
 * @author 刘亚辉
 * 
 */
@SuppressWarnings("unused")
public class LoginActivity extends BaseActivity implements OnClickListener {
	private EditText et_login_phone;
	private EditText et_login_pwd;
	private Button btn_commit;
	private TextView tv_forgetPwd, regist;
	private String userPhone, userPassword, deviceId;
	private LoginBean loginBean;// 登录实体类
	private ArrayList<ChargeFinishAndDoneBean> chargeList;
	private ChargeFinishAndDoneBean chargeOrderBean;
	MapModeBean bean = null;
	private String electricType, electricId;
	private ElectricPileDetailsBean pileBean;
	private AnchorAll anchorBean;
	private UserInfoBean userInfoBean;
	private String pkUserinfo;
	private int len = 0;
	private boolean isactivatephone = false;
	private boolean isactivatepassword = false;
	private boolean iseyes = false;
	private boolean isapassword = false;
	private ImageButton  img_clear;
	private ImageView im_close,img_eyes;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		mPageName = "LoginActivity";
		registerBoradcastReceiver();
		initUI();
	}

	private void initUI() {
		img_clear = (ImageButton) findViewById(R.id.img_login_clear);
		img_clear.setVisibility(View.INVISIBLE);
		img_clear.setOnClickListener(this);
		
		et_login_phone = (EditText) findViewById(R.id.login_userphone);
		et_login_phone.requestFocus();
		et_login_phone.setOnClickListener(this);
		et_login_phone.setOnFocusChangeListener(new FocusChange(et_login_phone, "手机号", img_clear));
		et_login_phone.addTextChangedListener(new MyLoginTextWatch());
		et_login_pwd = (EditText) findViewById(R.id.login_userpassword);
		et_login_pwd.setOnClickListener(this);
		et_login_pwd.addTextChangedListener(new MyLoginpasswordTextWatch());
		et_login_pwd.setOnFocusChangeListener(new FocusChange(et_login_pwd, "数字密码",null));
		regist = (TextView) findViewById(R.id.regist);
		regist.setOnClickListener(this);
		btn_commit = (Button) findViewById(R.id.login_commit);
		btn_commit.setOnClickListener(this);
		btn_commit.setEnabled(false);
		btn_commit.setBackgroundResource(R.drawable.common_button_shape);
		tv_forgetPwd = (TextView) findViewById(R.id.login_reset_password);
		tv_forgetPwd.setOnClickListener(this);
		img_eyes = (ImageView) findViewById(R.id.img_eyes);
		img_eyes.setOnClickListener(this);
		
		im_close = (ImageView) findViewById(R.id.login_register_close);
		im_close.setOnClickListener(this);
		
//		initFocus();

	}

//	private void initFocus() {
//		et_login_phone.setOnTouchListener(new OnTouchListener() {
//			
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				et_login_phone.clearFocus();
//				return false;
//			}
//		});
//	}
@Override
protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	MobclickAgent.onResume(mContext);
	MobclickAgent.onPageStart(mPageName);
}
@Override
protected void onPause() {
	// TODO Auto-generated method stub
	super.onPause();
	MobclickAgent.onPause(mContext);
	MobclickAgent.onPageEnd(mPageName);
}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_userphone:
			MobclickAgent.onEvent(mContext, "wode_denglu_shoujihao");
			break;
		case R.id.login_userpassword:
			MobclickAgent.onEvent(mContext, "wode_denglu_mima");
			break;
		case R.id.login_register_close:
			Intent homeintent = new Intent();
			homeintent.setClass(LoginActivity.this, HomeActivity.class);
			startActivity(homeintent);
			finish();
			overridePendingTransition(0, R.anim.login_bottom_out);
			break;
		case R.id.login_commit:
			MobclickAgent.onEvent(mContext, "wode_denglu_dengluanniu");
			String usename = et_login_phone.getText().toString().trim();
			userPhone = usename.replaceAll(" ", "");
			userPassword = et_login_pwd.getText().toString().trim();
			check();

			break;
		case R.id.login_reset_password:
			MobclickAgent.onEvent(mContext, "wode_wangjimima");
			Intent resetIn = new Intent(this,
					ForgetPasswordActivity.class);
			startActivity(resetIn);

			break;
		case R.id.regist:
			MobclickAgent.onEvent(mContext, "wode_zhuce");
			Intent register = new Intent(this, RegisterActivity.class);
			register.putExtra("source_inteface", getIntent().getStringExtra("source_inteface"));
			if (getIntent().getStringExtra("source_inteface").equals("myperson_record")){
				register.putExtra("defaultpage", getIntent().getStringExtra("defaultpage"));
			}
			startActivity(register);
			break;
		case R.id.img_login_clear:
			et_login_phone.setText("");
			break;
		case R.id.img_eyes:
			if (iseyes) {
				iseyes = false;
				img_eyes.setImageResource(R.drawable.btn_eyes);
				et_login_pwd
						.setTransformationMethod(PasswordTransformationMethod
								.getInstance());
			} else {
				iseyes = true;
				img_eyes.setImageResource(R.drawable.btn_eyes2);
				et_login_pwd
						.setTransformationMethod(HideReturnsTransformationMethod
								.getInstance());
			}
			String pw = et_login_pwd.getText().toString();
			et_login_pwd.setText(pw);
			et_login_pwd.setSelection(pw.length());
			break;
		default:
			break;
		}
	}

	@Override
	protected void getData() {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	@Override
	public void onSuccess(String sign, Bundle bundle) {
		if (sign.equals(Protocol.TO_LOGIN)) {
			loginBean = (LoginBean) bundle.getSerializable(Protocol.DATA);
			// 登录成功之后，获取用户信息，并保存！！
			if (loginBean != null) {
				// 获取预约列表
				GetDataPost.getInstance(this).getChargeInfoList(
						handler,loginBean.getPkUserinfo(), "2", "1", "1");
				saveUserInfo();
				Intent intnet = new Intent(BroadcastUtil.BROADCAST_LOGIN);
				intnet.putExtra("pkUserId", loginBean.getPkUserinfo());
				sendBroadcast(intnet);
				initskip(loginBean.getPkUserinfo());
			}
		} else if (sign.equals(Protocol.INDENT_PARTICULARS)) {
			//充电中订单列表
			chargeList = (ArrayList<ChargeFinishAndDoneBean>) bundle
					.getSerializable(Protocol.DATA);
			if (chargeList != null && chargeList.size() > 0) {
				chargeOrderBean = chargeList.get(0);
				if (chargeOrderBean != null) {
					Intent intnet = new Intent(BroadcastUtil.BROADCAST_Charge_Ing);
					intnet.putExtra("chargepilenum",chargeOrderBean.getElPi_ElectricPileCode());
					intnet.putExtra("chargeheadnum",chargeOrderBean.getHeadCode());
					sendBroadcast(intnet);
				}
			}
		} else if (sign.equals(Protocol.GET_USER_INFO)) {
			// 用户详情信息
			userInfoBean = (UserInfoBean) bundle.getSerializable(Protocol.DATA);
			Intent myuserinfoIn = new Intent();
			myuserinfoIn.setClass(this, MyUserInfoActivity.class);
			if (userInfoBean != null) {
				myuserinfoIn.putExtra("userInfo", userInfoBean);
				startActivity(myuserinfoIn);
			} else {
				showToast("网络不稳，稍后再试");
				GetDataPost.getInstance(this).getUserInfo(handler,pkUserinfo);
			}

		}
	}

	public void initskip(String pk) {
		pkUserinfo = pk;
		String source_inteface = getIntent().getStringExtra("source_inteface");
		if (source_inteface.equals("login")) {
			go2Home();
			return;
		}
		//go2Home();
		if (source_inteface.equals("myperson_wallet")) {

			Intent mywalletIn = new Intent();
			mywalletIn.setClass(this, MyWalletActivity.class);
			startActivity(mywalletIn);
		} else if (source_inteface.equals("myperson_settings")) {

			Intent settingsIn = new Intent();
			settingsIn.setClass(this, SettingsActivity.class);
			startActivity(settingsIn);
		} else if (source_inteface.equals("captureactivity")) {

			Intent scanIntent = new Intent();
			scanIntent.setClass(this, CaptureActivity.class);
			startActivity(scanIntent);
		} else if (source_inteface.equals("OfMyorder")) {
			Intent scanIntent = new Intent(LoginActivity.this,
					HomeActivity.class);
			scanIntent.putExtra("myorder", "aaa");
			myoder = true;
			startActivity(scanIntent);
		} else if (source_inteface.equals("myperson_photo")) {
			GetDataPost.getInstance(this).getUserInfo(handler, pk);
		}else if (source_inteface.equals("myperson_coupon")) {
			Intent couponIn = new Intent();
			couponIn.setClass(this, MyCouponActivity.class);
			startActivity(couponIn);
		}else if (source_inteface.equals("myperson_recharge")) {
			Intent couponIn = new Intent();
			couponIn.setClass(this, RechargeActivity.class);
			startActivity(couponIn);
		}else if (source_inteface.equals("myperson_bill")) {
			Intent couponIn = new Intent();
			couponIn.setClass(this, MyBillActivity.class);
			startActivity(couponIn);
		}else if (source_inteface.equals("myperson_record")) {
			Intent couponIn = new Intent();
			couponIn.putExtra("defaultpage", getIntent().getStringExtra("defaultpage"));
			couponIn.setClass(this, MyChargeActivity.class);
			startActivity(couponIn);
		}else if (source_inteface.equals("myNews")) {
			Intent couponIn = new Intent();
			couponIn.setClass(this, MyNewsActivity.class);
			startActivity(couponIn);
		}
		finish();
	}


	@Override
	public void onFaile(String sign, Bundle bundle) {
		// 登录失败
		if (LogUtil.isDebug) {
			showToast("错误码" + bundle.getString(Protocol.CODE) + "\n"
					+ bundle.getString(Protocol.MSG));
		} else {
			showToast(bundle.getString(Protocol.MSG));
		}
	}

	private void saveUserInfo() {
		PreferencesUtil.setPreferences(this, "pkUserinfo",
				loginBean.getPkUserinfo());
		PreferencesUtil.setPreferences(this, "usinPhone",
				loginBean.getUsinPhone());
		PreferencesUtil.setPreferences(this, "usinFacticityname",
				loginBean.getUsinFacticityname());
		PreferencesUtil.setPreferences(this, "usinSex",
				loginBean.getUsinSex());
		PreferencesUtil.setPreferences(this, "usinAccountbalance",
				loginBean.getUsinAccountbalance());
		PreferencesUtil.setPreferences(this, "usinBirthdate",
				loginBean.getUsinBirthdate());
		PreferencesUtil.setPreferences(this, "usinUserstatus",
				loginBean.getUsinUserstatus());
		PreferencesUtil.setPreferences(this, "usinHeadimage",
				loginBean.getUsinHeadimage());
		PreferencesUtil.setPreferences(this, "nickName",
				loginBean.getUsinUsername());
		PreferencesUtil.setPreferences(this, "carType",
				loginBean.getUsinCarinfoId());
		PreferencesUtil.setPreferences(this, "isPpw",
				loginBean.getIsPpw());
		PreferencesUtil.setPreferences(this, "usinBirthdate",
				loginBean.getUsinBirthdate());

	}

	/**
	 * 跳转首页
	 */
	private void go2Home() {
		IntentUtil.startIntent(this, HomeActivity.class);
		finish();

	}

	private void check() {
		if (!RegularExpressionUtil.isMobilephone(userPhone)) {
			showToast("请输入正确的手机号码！");
			return;
		}

		if (!RegularExpressionUtil.isPasswordLength(userPassword)) {
			showToast("请重新输入6-8位密码");
			return;
		}

		if (isNetConnection()) {
			loginPost();
		} else {
			showToast("亲，网络不稳，请检查网络连接!");
		}
	}

	// 登录请求
	private void loginPost() {

		deviceId = getDeviceId();
		String jpushRegistrationid = PreferencesUtil.getStringPreferences(
				this, "jpushRegistrationid");
		String devicetype = "1";
		userPassword = Tools.encoderByMd5(userPassword);
		PreferencesUtil.setPreferences(this, "password", userPassword);
		StringBuilder repwd1 = new StringBuilder();
		StringBuilder repwd2 = new StringBuilder();
		repwd1 = repwd1.append(userPassword).append(userPhone);
		userPassword = Tools.encoderByMd5(repwd1.toString());
		String random = Tools.getRandomChar(1);
		userPassword = repwd2.append(userPassword).append(random).toString();

		if (isNetConnection()) {
			showPD("正在登录，请稍等...");
			GetDataPost.getInstance(this)
			.login(userPhone,userPassword, jpushRegistrationid, devicetype, deviceId,"1000",handler);
		} else {
			showToast("亲，网络不稳，请检查网络连接!");
		}

	}

	// 获取设备id
	public String getDeviceId() {
		TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
		String deviceId = tm.getDeviceId();
		deviceId = Tools.encoderByMd5(deviceId);
		char[] chars = deviceId.toCharArray();
		String encodeID = "";
		for (int i = 0; i < chars.length; i++) {
			encodeID += Tools.replace((byte) chars[i]);
		}
		encodeID = Base64.encode(encodeID.getBytes());
		return encodeID;
	}

	// 手机号码 textwatch
	private class MyLoginTextWatch implements TextWatcher {
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {

		}

		@Override
		public void onTextChanged(CharSequence str, int start, int before,
				int count) {
			String contents = str.toString();
			int length = contents.length();
			if (length == 4) {
				if (contents.substring(3).equals(new String(" "))) { // - 删除
					contents = contents.substring(0, 3);
					et_login_phone.setText(contents);
					et_login_phone.setSelection(contents.length());
				} else { // + 输入时
					contents = contents.substring(0, 3) + " "
							+ contents.substring(3);
					et_login_phone.setText(contents);
					et_login_phone.setSelection(contents.length());
				}
			} else if (length == 9) {
				if (contents.substring(8).equals(new String(" "))) { // -
					contents = contents.substring(0, 8);
					et_login_phone.setText(contents);
					et_login_phone.setSelection(contents.length());
				} else {// +
					contents = contents.substring(0, 8) + " "
							+ contents.substring(8);
					et_login_phone.setText(contents);
					et_login_phone.setSelection(contents.length());
				}
			}
		}

		@Override
		public void afterTextChanged(Editable s) {
			String phone = s.toString();
			if (phone.length() < 1) {
				btn_commit.setEnabled(false);
				btn_commit.setBackgroundResource(R.drawable.common_button_shape);
				if (img_clear.getVisibility() == 0) {
					img_clear.setVisibility(View.INVISIBLE);
				}
				return;
			}
			if (img_clear.getVisibility() == 4) {
				img_clear.setVisibility(View.VISIBLE);
			}

			if (phone.length() == 13) {
				if (String.valueOf(phone.charAt(0)).equals("1")) {
					isactivatephone = true;
				} else {
					showToast("手机号格式不正确!");
					isactivatephone = false;
				}

			} else {
				isactivatephone = false;
			}
			if (isactivatephone && isactivatepassword) {
				btn_commit.setEnabled(true);
				btn_commit
						.setBackgroundResource(R.drawable.common_button_shape_activate);
			} else {
				btn_commit.setEnabled(false);
				btn_commit
						.setBackgroundResource(R.drawable.common_button_shape);
			}
////			155 6590 8062
//			if (3<phone.replaceAll(" ", "").length()
//					&&phone.replaceAll(" ", "").length()<7) {
//				et_login_phone.setText(phone.replaceAll(" ", "").substring(0, 3)+" "
//						+phone.replaceAll(" ", "").substring(3, phone.replaceAll(" ", "").length()-1));
//			}else if (7<phone.replaceAll(" ", "").length()
//					&&phone.replaceAll(" ", "").length()<12) {
//				et_login_phone.setText(phone.replaceAll(" ", "").substring(0, 3)+" "
//						+phone.replaceAll(" ", "").substring(3, 7)+" "
//							+phone.replaceAll(" ", "").substring(7, phone.replaceAll(" ", "").length()));
//			}
			if (phone.length()==11&&phone.replaceAll(" ", "").length()==11) {
				et_login_phone.setText(phone.replaceAll(" ", "").substring(0, 3)+" "
										+phone.replaceAll(" ", "").substring(3, 7)+" "
											+phone.replaceAll(" ", "").substring(7, 11));
				et_login_phone.setSelection(phone.length());
			}
				
//			if (phone.length()-len>2&&phone.length()-1==phone.replaceAll(" ", "").length()&&phone.length()>8) {
//				et_login_phone.setText(phone.replaceAll(" ", "").substring(0, 3)+" "
//						+phone.replaceAll(" ", "").substring(3, 7)+" "
//							+phone.substring(7, phone.replaceAll(" ", "").length()));
//				et_login_phone.setSelection(phone.length()+1);
//				
//			}else if (phone.length()-len>2&&phone.length()==phone.replaceAll(" ", "").length()&&phone.length()>8) {
//				et_login_phone.setText(phone.replaceAll(" ", "").substring(0, 3)+" "
//						+phone.replaceAll(" ", "").substring(3, 7)+" "
//							+phone.replaceAll(" ", "").substring(7, phone.replaceAll(" ", "").length()));
//				et_login_phone.setSelection(phone.length()+1);
//			}else if (phone.length()==phone.replaceAll(" ", "").length()&&phone.length()>3) {
//				et_login_phone.setText(phone.replaceAll(" ", "").substring(0, 3)+" "
//						+phone.replaceAll(" ", "").substring(3, phone.replaceAll(" ", "").length()));
//				et_login_phone.setSelection(phone.length()+1);
//			}
//			len = phone.length();
		}

	}

	// 登陆密码textwatch
	private class MyLoginpasswordTextWatch implements TextWatcher {

		@Override
		public void afterTextChanged(Editable s) {

			if (s.toString().length() >= 6) {
				isactivatepassword = true;
			} else {
				isactivatepassword = false;
			}

			if (isactivatephone && isactivatepassword) {
				btn_commit.setEnabled(true);
				btn_commit
						.setBackgroundResource(R.drawable.common_button_shape_activate);
			} else {
				btn_commit.setEnabled(false);
				btn_commit
						.setBackgroundResource(R.drawable.common_button_shape);
			}

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {

		}

		@Override
		public void onTextChanged(CharSequence str, int start, int before,
				int count) {
		}

	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mBroadcastReceiver);
	}

	// 重写返回键处理事件
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			finish();
			overridePendingTransition(0, R.anim.login_bottom_out);
			if (!myoder) {
				Intent scanIntent = new Intent(LoginActivity.this,
						HomeActivity.class);
				startActivity(scanIntent);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	public void registerBoradcastReceiver(){  
        IntentFilter myIntentFilter = new IntentFilter();  
        myIntentFilter.addAction(BroadcastUtil.BROADCAST_REGISTER_FINISH); 
        //注册广播        
        registerReceiver(mBroadcastReceiver, myIntentFilter);  
    }  
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){

		@Override
		public void onReceive(Context context, Intent intent) {
			  String action = intent.getAction();
			  if(action.equals(BroadcastUtil.BROADCAST_REGISTER_FINISH)){
				  finish();
			  }
		 }
	};
	
}
