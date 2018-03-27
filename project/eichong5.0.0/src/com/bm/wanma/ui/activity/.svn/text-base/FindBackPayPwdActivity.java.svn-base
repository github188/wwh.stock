package com.bm.wanma.ui.activity;

import com.bm.wanma.R;
import com.bm.wanma.net.GetDataGet;
import com.bm.wanma.net.GetDataPost;
import com.bm.wanma.net.Protocol;
import com.bm.wanma.utils.PreferencesUtil;
import com.bm.wanma.utils.RegularExpressionUtil;
import com.bm.wanma.utils.Tools;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author cm
 * 找回支付密码
 */
public class FindBackPayPwdActivity extends BaseActivity implements OnClickListener{
	private TextView tv_close,tv_phone,tv_commit;
	private EditText et_yanzm,et_new_pwd,et_confirm_pwd;
	private Button bt_yzm;
	private String pkuserid,phone,captcha,password,confirmPwd;
	private MyCount mc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_findback_paypwd);
		phone = PreferencesUtil.getStringPreferences(this, "usinPhone");
		initView();
	}
	
	private void initView(){
		
		tv_close = (TextView) findViewById(R.id.findback_paypwd_close);
		tv_close.setOnClickListener(this);
		tv_phone = (TextView) findViewById(R.id.findback_paypwd_userphone);
		tv_phone.setText(phone);
		et_yanzm = (EditText) findViewById(R.id.register_et_captcha);
		bt_yzm = (Button) findViewById(R.id.register_btn_captcha);
		bt_yzm.setOnClickListener(this);
		et_new_pwd = (EditText) findViewById(R.id.register_set_pwd);
		et_confirm_pwd = (EditText) findViewById(R.id.register_confirm_pwd);
		
		tv_commit = (TextView) findViewById(R.id.register_commit);
		tv_commit.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.findback_paypwd_close:
			finish();
			break;
		case R.id.register_btn_captcha:
			//获取验证码
			getCode();
			break;
		case R.id.register_commit:
			//提交找回密码
			captcha = et_yanzm.getText().toString();
			password = et_new_pwd.getText().toString();
			confirmPwd = et_confirm_pwd.getText().toString();
			if(captcha.isEmpty()){
				showToast("请输入验证码");
				return;
			}
			
			if(!RegularExpressionUtil.isPassword6Length(password)){
				showToast("请输入6为纯数字密码");
				return;
			}
			
			if(!Tools.isEmptyString(password) && password.equals(confirmPwd)){
				GetDataPost.getInstance(getActivity()).checkCode(handler, phone, captcha);
			}else {
				showToast("两次输入密码不一致！");
			}
			
			break;
		default:
			break;
		}
		
	}
	@Override
	protected void getData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSuccess(String sign, Bundle bundle) {
		if (Tools.judgeString(sign, Protocol.GET_AUTH_CODE)) {
			showToast("请查收验证码");
		}else if (Tools.judgeString(sign, Protocol.CHECK_CODE)) {
			// 验证码通过，发送修改信息
			pkuserid = PreferencesUtil.getStringPreferences(this, "pkUserinfo");
			GetDataPost.getInstance(this).setPayPwd(pkuserid,Tools.encoderByMd5(password), handler);
		}else if(Tools.judgeString(sign, Protocol.SET_PAY_PWD)){
			showToast("找回成功");
			finish();
		}

	}

	@Override
	public void onFaile(String sign, Bundle bundle) {
		showToast(bundle.getString(Protocol.MSG));

	}
	//定义一个倒计时的内部类
	class MyCount extends CountDownTimer {
		public MyCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}
		@Override
		public void onFinish() {
			bt_yzm.setClickable(true);
			bt_yzm.setText("获取验证码");
			bt_yzm.setBackgroundResource(R.drawable.common_shape_corner_orange);
		}
		@Override
		public void onTick(long millisUntilFinished) {
			bt_yzm.setClickable(false);
			bt_yzm.setText("等待("+ millisUntilFinished / 1000 + ")秒");
			bt_yzm.setBackgroundResource(R.drawable.common_shape_corner_gray);
		}
	}
	// 获取短信验证码
	public void getCode() {
		if (isNetConnection()) {
			GetDataGet.getInstance(this).getCode(handler, phone);
			mc = new MyCount(60000, 1000);
			mc.start();
		} else {
			showToast("亲，网络不稳，请稍后再试...");
		}

	}

}
