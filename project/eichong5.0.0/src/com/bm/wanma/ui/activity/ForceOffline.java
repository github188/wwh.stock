package com.bm.wanma.ui.activity;

import com.bm.wanma.R;
import com.bm.wanma.broadcast.BroadcastUtil;
import com.bm.wanma.utils.PreferencesUtil;
import com.bm.wanma.utils.ProjectApplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

public class ForceOffline extends BaseActivity implements OnClickListener{

	private TextView tv_quit,tv_again_login;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawableResource(R.drawable.common_shape_dialog);
		setContentView(R.layout.activity_forceoffline);
		setFinishOnTouchOutside(false);
		tv_quit = (TextView)findViewById(R.id.forceoffline_quit);
		tv_quit.setOnClickListener(this);
		tv_again_login = (TextView)findViewById(R.id.forceoffline_again);
		tv_again_login.setOnClickListener(this);
		logoutInfo();
		//清空预约图标
		Intent bespokefinishIn = new Intent();
		bespokefinishIn.setAction(BroadcastUtil.BROADCAST_Force_Offline);
		sendBroadcast(bespokefinishIn);
	}
 
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.forceoffline_again:
			//showToast("重新登录");
			//logoutInfo();
			//ProjectApplication.getInstance().exitForce(0);
//			IntentUtil.startIntent(ForceOffline.this, LoginAndRegisterActivity.class);
			isConceal = true;
			Intent loginIn = new Intent();
			loginIn.setClass(ForceOffline.this, LoginActivity.class);
			loginIn.putExtra("source_inteface", "login");
			startActivity(loginIn);
			overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
			finish();
			break;

		case R.id.forceoffline_quit:
			//logoutInfo();
			ProjectApplication.getInstance().exitForce(0);
			//ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
			//am.killBackgroundProcesses(getPackageName());
			//finish();  
            System.exit(0); 
			break;
		}
		
	}
	
	// 注销用户信息
	private void logoutInfo() {
		PreferencesUtil.setPreferences(getActivity(), "pkUserinfo", "");
		PreferencesUtil.setPreferences(getActivity(), "usinPhone", "");
		PreferencesUtil.setPreferences(getActivity(), "usinFacticityname", "");
		PreferencesUtil.setPreferences(getActivity(), "usinSex", "");
		PreferencesUtil.setPreferences(getActivity(), "usinAccountbalance", "");
		PreferencesUtil.setPreferences(getActivity(), "usinBirthdate", "");
		PreferencesUtil.setPreferences(getActivity(), "usinUserstatus", "");
		PreferencesUtil.setPreferences(getActivity(), "usinHeadimage", "");
		PreferencesUtil.setPreferences(getActivity(), "nickName", "");
		PreferencesUtil.setPreferences(getActivity(), "carType", "");
		PreferencesUtil.setPreferences(getActivity(), "isPpw", "");

	}
	@Override
	protected void getData() {

	}

	@Override
	public void onSuccess(String sign, Bundle bundle) {

	}

	@Override
	public void onFaile(String sign, Bundle bundle) {

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		 switch (keyCode) {   
	        case KeyEvent.KEYCODE_BACK: 
	        	
		 }
		
		
		return false;
	}
	
	
	
	
	

}
