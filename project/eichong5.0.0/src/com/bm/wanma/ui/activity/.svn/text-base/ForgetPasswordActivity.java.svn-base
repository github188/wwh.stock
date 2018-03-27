package com.bm.wanma.ui.activity;

import com.bm.wanma.R;

import com.bm.wanma.net.GetDataGet;
import com.bm.wanma.net.GetDataPost;
import com.bm.wanma.net.Protocol;
import com.bm.wanma.utils.IntentUtil;
import com.bm.wanma.utils.LogUtil;
import com.bm.wanma.utils.PreferencesUtil;
import com.bm.wanma.utils.RegularExpressionUtil;
import com.bm.wanma.utils.Tools;
import com.bm.wanma.view.FocusChange;
import com.umeng.analytics.MobclickAgent;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
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

/**
 * 忘记密码界面 cm
 */

public class ForgetPasswordActivity extends BaseActivity implements
		OnClickListener {
	private EditText et_phone, et_captcha, et_password;
	private Button btn_chptcha, btn_commit;
	private ImageButton im_close, img_clear;
	private ImageView img_eyes;
	private MyCount mc;
	private String userPhone, captcha, password;
	private boolean iseyes = false;
	private boolean isactivatephone = false;//用户phone状态
	private boolean isactivatepassword = false;//用户密码状态
	private boolean iscaptcha = false; //验证码状态

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget_pwd);
		mPageName = "ForgetPasswordActivity";
		img_clear = (ImageButton) findViewById(R.id.img_forget_clear);
		img_clear.setOnClickListener(this);
		et_phone = (EditText) findViewById(R.id.forget_et_userphone);
		et_phone.addTextChangedListener(new MyforgetTextWatch());
		et_phone.setOnClickListener(this);
		et_phone.requestFocus();
		et_phone.setOnFocusChangeListener(new FocusChange(et_phone, "手机号", img_clear));

		et_captcha = (EditText) findViewById(R.id.forget_et_captcha);
		et_captcha.addTextChangedListener(new MycaptchaTextWatch());
		et_captcha.setOnClickListener(this);
		et_captcha.setOnFocusChangeListener(new FocusChange(et_captcha, "验证码",null));
		et_password = (EditText) findViewById(R.id.forget_set_pwd);
		et_password.addTextChangedListener(new MyforgetpasswordTextWatch());
		et_password.setOnFocusChangeListener(new FocusChange(et_password, "数字密码",null));
		et_password.setOnClickListener(this);
		img_eyes = (ImageView) findViewById(R.id.img_eyes);
		img_eyes.setOnClickListener(this);

		btn_chptcha = (Button) findViewById(R.id.forget_btn_captcha);
		btn_chptcha.setOnClickListener(this);
		
		btn_commit = (Button) findViewById(R.id.forget_commit);
		btn_commit.setOnClickListener(this);
		btn_commit.setEnabled(false);
		btn_commit.setBackgroundResource(R.drawable.common_button_shape);
		im_close = (ImageButton) findViewById(R.id.forget_close);
		im_close.setOnClickListener(this);
		
		img_clear.setVisibility(View.INVISIBLE);
//		initFocus();
	}

//	private void initFocus() {
//		et_phone.setOnTouchListener(new OnTouchListener() {
//			
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				et_phone.clearFocus();
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
		case R.id.forget_et_userphone:
			MobclickAgent.onEvent(mContext, "wode_wangjimima_shujihao");
			break;
		case R.id.forget_et_captcha:
			MobclickAgent.onEvent(mContext, "wode_wangjimima_yanzhengma");
			break;
		case R.id.forget_set_pwd:
			MobclickAgent.onEvent(mContext, "wode_wangjimima_mima");
			break;
		case R.id.forget_btn_captcha: // 获取验证码
			MobclickAgent.onEvent(mContext, "wode_wangjimima_huoquyanzhengma");
			userPhone = et_phone.getText().toString().trim()
					.replaceAll(" ", "");
			if (!RegularExpressionUtil.isMobilephone(userPhone)) {
				showToast("请输入正确的手机号码");
				return;
			}
			if (isNetConnection()) {
				GetDataPost.getInstance(ForgetPasswordActivity.this)
						.checkPhone(handler, userPhone,"1000");
			} else {
				showToast("亲，网络不稳，请检查网络连接");
			}
			// getCode();
			break;
		case R.id.forget_commit: // 完成
			MobclickAgent.onEvent(mContext, "wode_wangjimima_tijiao");
			check();
			break;
		// 返回
		case R.id.forget_close:

			finish();
			break;
		case R.id.img_forget_clear:
			et_phone.setText("");
			
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
		}

	}

	private class MyTextWatch implements TextWatcher {

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {

		}

		@Override
		public void afterTextChanged(Editable str) {
			String contents = str.toString();
			int length = contents.length();

			if (length == 4) {
				if (contents.substring(3).equals(new String(" "))) { // - 删除
					contents = contents.substring(0, 3);
					et_phone.setText(contents);
					et_phone.setSelection(contents.length());
				} else { // + 输入时
					contents = contents.substring(0, 3) + " "
							+ contents.substring(3);
					et_phone.setText(contents);
					et_phone.setSelection(contents.length());
				}
			} else if (length == 9) {
				if (contents.substring(8).equals(new String(" "))) { // -
					contents = contents.substring(0, 8);
					et_phone.setText(contents);
					et_phone.setSelection(contents.length());
				} else {// +
					contents = contents.substring(0, 8) + " "
							+ contents.substring(8);
					et_phone.setText(contents);
					et_phone.setSelection(contents.length());
				}
			}
			if (length < 1) {
				if (img_clear.getVisibility() == 0) {
					img_clear.setVisibility(View.INVISIBLE);
				}
				return;
			}
			if (img_clear.getVisibility() == 4) {
				img_clear.setVisibility(View.VISIBLE);
			}
		}

	}

	private void check() {
		userPhone = et_phone.getText().toString().trim().replaceAll(" ", "");
		captcha = et_captcha.getText().toString().trim();
		password = et_password.getText().toString().trim();
		// confirmPwd = et_confirm_pwd.getText().toString().trim();

		if (!RegularExpressionUtil.isMobilephone(userPhone)) {
			showToast("请输入正确的手机号码");
			return;
		}
		/*
		 * if (!RegularExpressionUtil.isPassword(ac_reset_newpassword.getText().
		 * toString().trim())) { ToastUtil.showToast("密码不能为空"); return; }
		 */
		if (captcha.isEmpty()) {
			showToast("请输入验证码!");
			return;
		}
		if (!RegularExpressionUtil.isPasswordLength(password)) {
			showToast("请输入6-8位纯数字密码");
			return;
		}
		// if (!password.equals(confirmPwd)) {
		// showToast("两次输入的密码不一致");
		// return;
		// }

		forgetPwd();
	}

	// 获取短信验证码
	public void getCode() {
		if (isNetConnection()) {
			GetDataGet.getInstance(this).getCode(handler, userPhone);
			mc = new MyCount(60000, 1000);
			mc.start();
		} else {
			showToast("亲，网络不稳，请检查网络连接！");
		}
	}

	// 忘记密码的网络请求
	private void forgetPwd() {

		password = Tools.encoderByMd5(password);
		// smsCode = Tools.encoderByMd5(smsCode);
		if (isNetConnection()) {
			showPD("正在提交修改信息，请稍等...");
			GetDataPost.getInstance(this).resetPwd(userPhone, password,	captcha,"1000", handler);
		} else {
			showToast("亲，网络不稳，请检查网络连接!");
		}
	}

	/* 定义一个倒计时的内部类 */
	class MyCount extends CountDownTimer {
		public MyCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
			btn_chptcha.setClickable(true);
			btn_chptcha.setText("重新获取验证码");
//			btn_chptcha.setBackgroundResource(R.drawable.common_shape_corner_orange);
			btn_chptcha .setBackgroundColor(Color.parseColor("#ff8800"));
		}

		@Override
		public void onTick(long millisUntilFinished) {
			btn_chptcha.setClickable(false);
			btn_chptcha.setText(millisUntilFinished / 1000 + "秒重新获取");
//			btn_chptcha.setBackgroundResource(R.drawable.common_shape_corner_gray);
			btn_chptcha .setBackgroundColor(Color.parseColor("#dcdcdc"));
		}
	}

	@Override
	protected void getData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSuccess(String sign, Bundle bundle) {
		if (Tools.judgeString(sign, Protocol.CHECK_PHONE)) {
			showToast("该手机号未注册...");
		} else if (sign.equals(Protocol.RESET_PWD)) {
			showToast("修改密码成功");
			PreferencesUtil.setPreferences(this, "password", password);
			// IntentUtil.startIntent(ForgetPasswordActivity.this,
			// LoginAndRegisterActivity.class);
			Intent loginIn = new Intent();
			loginIn.setClass(ForgetPasswordActivity.this, LoginActivity.class);
			loginIn.putExtra("source_inteface", "login");
			startActivity(loginIn);
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);
		} else if (Tools.judgeString(sign, Protocol.GET_AUTH_CODE)) {
			showToast("请查收验证码");
		}

	}

	@Override
	public void onFaile(String sign, Bundle bundle) {

		if (Tools.judgeString(sign, Protocol.CHECK_PHONE)) {
			// 发送验证码
			getCode();
		} else if (sign.equals(Protocol.RESET_PWD)) {
			if (LogUtil.isDebug) {
				showToast("错误码" + bundle.getString(Protocol.CODE) + "\n"
						+ bundle.getString(Protocol.MSG));
			} else {
				showToast(bundle.getString(Protocol.MSG));
			}
		} else if (Tools.judgeString(sign, Protocol.GET_AUTH_CODE)) {
			if (LogUtil.isDebug) {
				showToast("错误码" + bundle.getString(Protocol.CODE) + "\n"
						+ bundle.getString(Protocol.MSG));
			} else {
				showToast(bundle.getString(Protocol.MSG));
			}
		}

	}
	private class MyforgetTextWatch implements TextWatcher {
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
	
	// 登陆密码textwatch
	private class MyforgetpasswordTextWatch implements TextWatcher {

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
			// TODO Auto-generated method stub

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub

		}

	}
}
