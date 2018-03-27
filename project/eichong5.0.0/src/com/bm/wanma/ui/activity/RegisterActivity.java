package com.bm.wanma.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.wanma.R;
import com.bm.wanma.alipay.Base64;
import com.bm.wanma.broadcast.BroadcastUtil;
import com.bm.wanma.entity.ElectricPileDetailsBean;
import com.bm.wanma.entity.LoginBean;
import com.bm.wanma.entity.MapModeBean;
import com.bm.wanma.entity.UserInfoBean;
import com.bm.wanma.net.GetDataGet;
import com.bm.wanma.net.GetDataPost;
import com.bm.wanma.net.Protocol;
import com.bm.wanma.ui.scan.CaptureActivity;
import com.bm.wanma.utils.IntentUtil;
import com.bm.wanma.utils.LogUtil;
import com.bm.wanma.utils.PreferencesUtil;
import com.bm.wanma.utils.RegularExpressionUtil;
import com.bm.wanma.utils.Tools;
import com.bm.wanma.view.FocusChange;
import com.umeng.analytics.MobclickAgent;
/**
 * 注册界面
 * @author 刘亚辉
 *
 */
public class RegisterActivity extends BaseActivity implements OnClickListener {
	private ImageView img_eyes;
	public ImageButton im_close, img_clear,img_clear2;
	private EditText et_phone, et_captcha, et_password, et_invitePhone;
	private Button btn_chptcha, btn_commit;
	private TextView tv_protocol;
	private MyCount mc;
	private String userPhone, captcha, password, invitePhone;
	private LoginBean loginBean;//用户信息
	private boolean isactivatephone = false;//用户phone状态
	private boolean isactivatepassword = false;//用户密码状态
	private boolean iscaptcha = false; //验证码状态
	private boolean iseyes = false; //眼睛状态
	private MapModeBean bean = null;
	private String  electricId;
	private ElectricPileDetailsBean pileBean;
	private UserInfoBean userInfoBean;
	private String pkUserinfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		mPageName = "RegisterActivity";
		initUI();
	}

	private void initUI() {
		img_clear = (ImageButton) findViewById(R.id.img_regist_clear);
		img_clear.setOnClickListener(this);
		img_clear2 = (ImageButton) findViewById(R.id.img_clear2);
		img_clear2.setOnClickListener(this);
		
		im_close = (ImageButton) findViewById(R.id.register_close);
		im_close.setOnClickListener(this);
		et_phone = (EditText) findViewById(R.id.register_userphone);
		et_phone.requestFocus();
		et_phone.setOnClickListener(this);
		et_phone.setOnFocusChangeListener(new FocusChange(et_phone, "手机号", img_clear));
		et_phone.addTextChangedListener(new MyRegistTextWatch());
		et_captcha = (EditText) findViewById(R.id.register_et_captcha);
		et_captcha.setOnClickListener(this);
		et_captcha.addTextChangedListener(new MycaptchaTextWatch());
		et_captcha.setOnFocusChangeListener(new FocusChange(et_captcha, "验证码",null));
		et_password = (EditText) findViewById(R.id.register_set_pwd);
		et_password.setOnClickListener(this);
		et_password.addTextChangedListener(new MyLoginpasswordTextWatch());
		et_password.setOnFocusChangeListener(new FocusChange(et_password, "数字密码",null));
		img_eyes = (ImageView) findViewById(R.id.img_eyes);
		img_eyes.setOnClickListener(this);
		et_invitePhone = (EditText) findViewById(R.id.register_userphone2);
		et_invitePhone.setOnClickListener(this);
		et_invitePhone.addTextChangedListener(new MyinvitePhoneTextWatch());
		et_invitePhone.setOnFocusChangeListener(new FocusChange(et_invitePhone, "邀请人手机号(可不填)", img_clear2));
		btn_commit = (Button) findViewById(R.id.register_commit);
		btn_commit.setOnClickListener(this);
		btn_commit.setEnabled(false);
		btn_commit.setBackgroundResource(R.drawable.common_button_shape);
		btn_chptcha = (Button) findViewById(R.id.register_btn_captcha);
		btn_chptcha.setOnClickListener(this);
		tv_protocol = (TextView) findViewById(R.id.register_commit_tv_protocol);
		tv_protocol.setOnClickListener(this);
		img_clear.setVisibility(View.INVISIBLE);
		img_clear2.setVisibility(View.INVISIBLE);
		
//		initPhone();
	}

//	private void initPhone() {
//		
//		et_phone.setOnTouchListener(new OnTouchListener() {
//			
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				et_phone.clearFocus();
//				return false;
//			}
//		});
//
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
		case R.id.register_userphone:
			MobclickAgent.onEvent(mContext, "wode_zhuce_shoujihao");
			break;
		case R.id.register_et_captcha:
			MobclickAgent.onEvent(mContext, "wode_zhuce_yanzhengma");
			break;
		case R.id.register_set_pwd:
			MobclickAgent.onEvent(mContext, "wode_zhuce_mima");
			break;
		case R.id.register_userphone2:
			MobclickAgent.onEvent(mContext, "wode_zhuce_yaoqingren");
			break;
		// 注册提交
		case R.id.register_commit:
			MobclickAgent.onEvent(mContext, "wode_zhuce_tijiao");
			captcha = et_captcha.getText().toString();
			password = et_password.getText().toString();
			userPhone = et_phone.getText().toString().replaceAll(" ", "");
			invitePhone = et_invitePhone.getText().toString()
					.replaceAll(" ", "");
			if (!RegularExpressionUtil.isMobilephone(userPhone)) {
				showToast("请输入正确的手机号码！");
				return;
			} 
			if (!invitePhone.equals("")) {
				if (!RegularExpressionUtil.isMobilephone(invitePhone)) {
					showToast("邀请人手机号格式不正确！");
					return;
				}
			}

			if (captcha.isEmpty()) {
				showToast("请输入验证码");
				return;
			}

			if (!RegularExpressionUtil.isPasswordLength(password)) {
				showToast("请输入6-8位密码");
				return;
			}

			showPD("正在提交信息");
			GetDataPost.getInstance(this).checkCode(handler,
					userPhone, captcha);

			break;
		// 获取验证码
		case R.id.register_btn_captcha:
			MobclickAgent.onEvent(mContext, "wode_zhuce_huoquyanzhengma");
			userPhone = et_phone.getText().toString().replaceAll(" ", "");
			MobclickAgent.onEvent(mContext, "wode_zhuce");
			if (RegularExpressionUtil.isMobilephone(userPhone)) {
				if (isNetConnection()) {
					GetDataPost.getInstance(this).checkPhone(handler,
							userPhone,"1000");
				} else {
					showToast("网络不稳，请稍后再试");
				}

			} else {
				showToast("请输入正确的手机号码！");
			}

			break;
		case R.id.register_commit_tv_protocol:
//			MobclickAgent.onEvent(mContext, "wode_zhuce");
			startActivity(
					new Intent(this, AboutProtolActivity.class));

			break;
		case R.id.register_close:
			finish();
			break;
		case R.id.img_regist_clear:
			et_phone.setText("");
			
			break;
		case R.id.img_clear2:
			et_invitePhone.setText("");
			break;
		case R.id.img_eyes:
			if (iseyes) {
				iseyes = false;
				img_eyes.setImageResource(R.drawable.btn_eyes);
				et_password
						.setTransformationMethod(PasswordTransformationMethod
								.getInstance());
			} else {
				iseyes = true;
				img_eyes.setImageResource(R.drawable.btn_eyes2);
				et_password
						.setTransformationMethod(HideReturnsTransformationMethod
								.getInstance());
			}
			String pw = et_password.getText().toString();
			et_password.setText(pw);
			et_password.setSelection(pw.length());
			break;
		default:
			break;
		}
	}



	@Override
	protected void getData() {

	}

	@SuppressWarnings("unchecked")
	@Override
	public void onSuccess(String sign, Bundle bundle) {
		cancelPD();
		if (Tools.judgeString(sign, Protocol.CHECK_PHONE)) {
			// 发送验证码
			getCode();
		} else if (Tools.judgeString(sign, Protocol.CHECK_CODE)) {
			// 验证码通过，发送注册信息
			goRegister();
		} else if (Tools.judgeString(sign, Protocol.GET_AUTH_CODE)) {
			showToast("请查收验证码");
		} else if (Tools.judgeString(sign, Protocol.TO_REGIST)) {

			String jpushRegistrationid = PreferencesUtil.getStringPreferences(
					this, "jpushRegistrationid");
			String devicetype = "1";
			String deviceId = getDeviceId();
			PreferencesUtil.setPreferences(this, "password", password);
			StringBuilder repwd1 = new StringBuilder();
			StringBuilder repwd2 = new StringBuilder();
			repwd1 = repwd1.append(password).append(userPhone);
			password = Tools.encoderByMd5(repwd1.toString());
			String random = Tools.getRandomChar(1);
			password = repwd2.append(password).append(random).toString();
			GetDataPost.getInstance(this).login(userPhone, password,
					jpushRegistrationid, devicetype, deviceId,"1000", handler);

		} else if (Tools.judgeString(sign, Protocol.TO_LOGIN)) {// 登录成功
			loginBean = (LoginBean) bundle.getSerializable(Protocol.DATA);
			sendBroadcast(new Intent(BroadcastUtil.BROADCAST_REGISTER_FINISH));
			// 登录成功之后，获取用户信息，并保存！！
			if (loginBean != null) {
				saveUserInfo();
				Intent loginIn = new Intent();
				loginIn.setAction(BroadcastUtil.BROADCAST_LOGIN);
				loginIn.putExtra("pkUserId", loginBean.getPkUserinfo());
				sendBroadcast(loginIn);
				initskip(loginBean.getPkUserinfo());
				// go2Home();
			}
		}  else if (sign.equals(Protocol.GET_USER_INFO)) {
			// 用户详情信息
			userInfoBean = (UserInfoBean) bundle.getSerializable(Protocol.DATA);
			Intent myuserinfoIn = new Intent();
			myuserinfoIn.setClass(this, MyUserInfoActivity.class);
			if (userInfoBean != null) {
				myuserinfoIn.putExtra("userInfo", userInfoBean);
				startActivity(myuserinfoIn);
				finish();
			} else {
				showToast("网络不稳，稍后再试");
				GetDataPost.getInstance(this).getUserInfo(handler,
						pkUserinfo);
			}

		}
	}

	/**
	 * 注册
	 */
	private void goRegister() {
		password = Tools.encoderByMd5(password);
		if (isNetConnection()) {
			showPD("正在提交注册信息，请稍等...");
			
			GetDataPost.getInstance(this).register(handler, userPhone,
					password, invitePhone,"1000");
		} else {
			showToast("亲，网络不稳，请检查网络连接!");
		}
	}

	public void initskip(String pk) {
		pkUserinfo = pk;
		String source_inteface = getIntent().getStringExtra("source_inteface");
		
		if (source_inteface.equals("login")) {
			go2Home();
			return;
		}
		if (source_inteface.equals("myperson_wallet")) {

			Intent mywalletIn = new Intent();
			mywalletIn.setClass(this, MyWalletActivity.class);
			startActivity(mywalletIn);
		}  else if (source_inteface.equals("myperson_settings")) {

			Intent settingsIn = new Intent();
			settingsIn.setClass(this, SettingsActivity.class);
			startActivity(settingsIn);
		} else if (source_inteface.equals("captureactivity")) {

			Intent scanIntent = new Intent();
			scanIntent.setClass(this, CaptureActivity.class);
			startActivity(scanIntent);
		} else if (source_inteface.equals("OfMyorder")) {
			Intent scanIntent = new Intent(RegisterActivity.this,
					HomeActivity.class);
			myoder = true;
			startActivity(scanIntent);
		} else if (source_inteface.equals("myperson_photo")) {
			GetDataPost.getInstance(this).getUserInfo(handler,
					pkUserinfo);
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


	// 获取短信验证码
	public void getCode() {
		if (isNetConnection()) {
			showPD("正在获取信息");
			GetDataGet.getInstance(this).getCode(handler, userPhone);
			mc = new MyCount(60000, 1000);
			mc.start();
		} else {
			showToast("亲，网络不稳，请稍后再试...");
		}

	}

	@Override
	public void onFaile(String sign, Bundle bundle) {
		cancelPD();
		// 登录失败
		if (LogUtil.isDebug) {
			showToast("错误码" + bundle.getString(Protocol.CODE) + "\n"
					+ bundle.getString(Protocol.MSG));
		} else {
			showToast(bundle.getString(Protocol.MSG));
		}

	}

	// 定义一个倒计时的内部类
	class MyCount extends CountDownTimer {
		public MyCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
			btn_chptcha.setClickable(true);
			btn_chptcha.setText("重新获取验证码");
			btn_chptcha .setBackgroundColor(Color.parseColor("#ff8800"));
//			btn_chptcha	.setBackgroundResource(R.drawable.common_shape_corner_orange);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			btn_chptcha.setClickable(false);
			btn_chptcha.setText(millisUntilFinished / 1000 + "秒重新获取");
//			btn_chptcha.setBackgroundResource(R.drawable.common_shape_corner_gray);
			btn_chptcha .setBackgroundColor(Color.parseColor("#dcdcdc"));
		}
	}

	private class MyRegistTextWatch implements TextWatcher {
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
					contentText(contents);
				} else { // + 输入时
					contents = contents.substring(0, 3) + " "
							+ contents.substring(3);
					contentText(contents);
				}
			} else if (length == 9) {
				if (contents.substring(8).equals(new String(" "))) { // -
					contents = contents.substring(0, 8);
					contentText(contents);
				} else {// +
					contents = contents.substring(0, 8) + " "
							+ contents.substring(8);
					contentText(contents);
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
			if (isactivatephone && isactivatepassword && iscaptcha) {
				btn_commit.setEnabled(true);
				btn_commit.setBackgroundResource(R.drawable.common_button_shape_activate);
			} else {
				btn_commit.setEnabled(false);
				btn_commit.setBackgroundResource(R.drawable.common_button_shape);
			}
		}
		public void contentText(String contents){
			
				et_phone.setText(contents);
				et_phone.setSelection(contents.length());
			
		}
	}
	private class MyinvitePhoneTextWatch implements TextWatcher {
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
					contentText(contents);
				} else { // + 输入时
					contents = contents.substring(0, 3) + " "
							+ contents.substring(3);
					contentText(contents);
				}
			} else if (length == 9) {
				if (contents.substring(8).equals(new String(" "))) { // -
					contents = contents.substring(0, 8);
					contentText(contents);
				} else {// +
					contents = contents.substring(0, 8) + " "
							+ contents.substring(8);
					contentText(contents);
				}
			}
		}

		@Override
		public void afterTextChanged(Editable s) {
			String phone = s.toString();
			if (phone.length() < 1) {
					if (img_clear2.getVisibility() == 0) {
						img_clear2.setVisibility(View.INVISIBLE);
					}
				return;
			}
			
				if (img_clear2.getVisibility() == 4) {
					img_clear2.setVisibility(View.VISIBLE);
				}
			
			
			if (phone.length() == 13) {
				if (String.valueOf(phone.charAt(0)).equals("1")) {
				} else {
					showToast("手机号格式不正确!");
				}

			} 
			
		}
		public void contentText(String contents){
			
				et_invitePhone.setText(contents);
				et_invitePhone.setSelection(contents.length());
			
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

			if (isactivatephone && isactivatepassword && iscaptcha) {
				btn_commit.setEnabled(true);
				btn_commit.setBackgroundResource(R.drawable.common_button_shape_activate);
			} else {
				btn_commit.setEnabled(false);
				btn_commit.setBackgroundResource(R.drawable.common_button_shape);
			}

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {

		}

	}

	// 登陆密码textwatch
	private class MycaptchaTextWatch implements TextWatcher {

		@Override
		public void afterTextChanged(Editable s) {

			if (s.toString().length() >= 4) {
				iscaptcha = true;
			} else {
				iscaptcha = false;
			}

			if (isactivatephone && isactivatepassword && iscaptcha) {
				btn_commit.setEnabled(true);
				btn_commit.setBackgroundResource(R.drawable.common_button_shape_activate);
			} else {
				btn_commit.setEnabled(false);
				btn_commit.setBackgroundResource(R.drawable.common_button_shape);
			}

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {

		}

	}

	// 获取设备id
	public String getDeviceId() {
		TelephonyManager tm = (TelephonyManager) this
				.getSystemService(Context.TELEPHONY_SERVICE);
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

	/**
	 * 跳转首页
	 */
	private void go2Home() {
		IntentUtil.startIntent(this, HomeActivity.class);
		finish();
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

	}

}
