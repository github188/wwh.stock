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

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * 设置支付密码界面
 * cm
 */

public class SetPayPasswordActivity extends BaseActivity implements OnClickListener{
	
	private EditText et_current_pwd,et_confirm_pwd;
	private Button btn_commit;
	private TextView tv_close;
	private String currentpwd,confirmPwd;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_pay_pwd);
		
		et_current_pwd = (EditText)findViewById(R.id.modify_et_current_pwd);
		et_confirm_pwd = (EditText)findViewById(R.id.modify_et_confirm_pwd);
		btn_commit = (Button)findViewById(R.id.modify_commit);
		btn_commit.setOnClickListener(this);
		tv_close = (TextView)findViewById(R.id.modify_close);
		tv_close.setOnClickListener(this);
		 
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.modify_commit://确定
			check();
			break;
			//返回
		case R.id.modify_close:
			finish();
		}
		
	}

		
	private void check() {
		currentpwd = et_current_pwd.getText().toString().trim();
		confirmPwd = et_confirm_pwd.getText().toString().trim();
		if (!RegularExpressionUtil.isPassword6Length(currentpwd)) {
			showToast("请输入6位纯数字密码");
			return;
		}
	
		if (!currentpwd.equals(confirmPwd)) {
			showToast("两次输入的密码不一致");
			return;
		}
		setPayPwd();
	}
	
		// 设置支付密码的网络请求
		private void setPayPwd() {
			if(isNetConnection()){
				String uId = PreferencesUtil.getStringPreferences(this, "pkUserinfo");
				showPD("正在提交信息，请稍等...");
				GetDataPost.getInstance(this).setPayPwd(uId,Tools.encoderByMd5(currentpwd),handler);
			}else {
				showToast("亲，网络不稳，请检查网络连接!");
			}
		}
	 
	  
		
	@Override
	protected void getData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSuccess(String sign, Bundle bundle) {
		//showToast(bundle.getString(Protocol.MSG));
		showToast("设置成功");
		PreferencesUtil.setPreferences(this, "paypassword",
				Tools.encoderByMd5(currentpwd));
		PreferencesUtil.setPreferences(this, "isPpw",
				"1");
		finish();

	}

	@Override
	public void onFaile(String sign, Bundle bundle) {
		
		showToast(bundle.getString(Protocol.MSG));
		
	}



}
