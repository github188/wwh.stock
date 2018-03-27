package com.bm.wanma.ui.activity;

import com.bm.wanma.R;
import com.bm.wanma.broadcast.BroadcastUtil;
import com.bm.wanma.dialog.CancleBespokeDialog;
import com.bm.wanma.entity.UserInfoBean;
import com.bm.wanma.net.GetDataPost;
import com.bm.wanma.net.Protocol;
import com.bm.wanma.utils.PreferencesUtil;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * @author cm
 *  设置
 */
public class SettingsActivity extends BaseActivity implements OnClickListener{
	
	private ImageButton ib_back;
	private TextView tv_personal,tv_security,tv_feedback;
	private TextView tv_contact,tv_about,tv_mark,tv_logout;
	private String pkUserinfo;
	private UserInfoBean userInfoBean;
	private CancleBespokeDialog cancleBespokeDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		initView();
	}
	private void initView(){
		ib_back = (ImageButton) findViewById(R.id.settings_back);
		ib_back.setOnClickListener(this);
		tv_personal = (TextView) findViewById(R.id.settings_personal);
		tv_personal.setOnClickListener(this);
		tv_security = (TextView) findViewById(R.id.settings_security);
		tv_security.setOnClickListener(this);
		tv_feedback = (TextView) findViewById(R.id.settings_feedback);
		tv_feedback.setOnClickListener(this);
		tv_contact = (TextView) findViewById(R.id.settings_contact_us);
		tv_contact.setOnClickListener(this);
		tv_about = (TextView) findViewById(R.id.settings_about);
		tv_about.setOnClickListener(this);
		tv_mark = (TextView) findViewById(R.id.settings_mark);
		tv_mark.setOnClickListener(this);
		tv_logout = (TextView) findViewById(R.id.settings_logout);
		tv_logout.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.settings_back:
			finish();
			break;
		case R.id.settings_personal:
			//个人资料
			if(isNetConnection()){
				String pkUserId = PreferencesUtil.getStringPreferences(this, "pkUserinfo");
				showPD("获取个人资料");
				GetDataPost.getInstance(SettingsActivity.this).getUserInfo(handler, pkUserId);
			}else {
				showToast("网络不稳，稍后再试");
			}
			break;
		case R.id.settings_security:
			//安全设置
			startActivity(new Intent(this,SecuritySettingsActivity.class));
			break;
		case R.id.settings_feedback:
			//意见反馈
			Intent feedbackIn = new Intent();
			feedbackIn.setClass(SettingsActivity.this, CommitFeedbackActivity.class);
			startActivity(feedbackIn);
			break;
		case R.id.settings_about:
			//关于爱充
			startActivity(new Intent(this,AboutWeActivity.class));
			break;
		case R.id.settings_mark:
			//去评分
			String marketAddress = "market://details?id=" + getPackageName(); 
			Intent marketIn = new Intent(Intent.ACTION_VIEW); 
			marketIn.setData(Uri.parse(marketAddress)); 
			marketIn.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
			startActivity(marketIn);
			break;
		case R.id.settings_contact_us:
			//联系我们
			startActivity(new Intent(this,ContactUsActivity.class));
			break;
		case R.id.settings_logout:
			//退出登录 
			if(cancleBespokeDialog == null){
				cancleBespokeDialog = new CancleBespokeDialog(this,"是否退出登录?");
			}
			cancleBespokeDialog.setCancelable(false);
	        cancleBespokeDialog.setOnPositiveListener(new OnClickListener() {
		        @Override
		        public void onClick(View v) {
		        	//正在退出登录
		        	if(isNetConnection()){
		        		showPD("正在退出登录...");
						pkUserinfo = PreferencesUtil.getStringPreferences(SettingsActivity.this,"pkUserinfo");
						GetDataPost.getInstance(SettingsActivity.this).logout(handler, pkUserinfo);
					}else {
						showToast("网络不稳，请稍后再试...");
					}
		        	cancleBespokeDialog.dismiss();
		        }
		    });
	        cancleBespokeDialog.setOnNegativeListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					cancleBespokeDialog.dismiss();
				}
			});
	        if(!cancleBespokeDialog.isShowing()){
	        	cancleBespokeDialog.show();
	        }
			break;
		default:
			break;
		}
		
	}
	@Override
	protected void getData() {
		
	}
	@Override
	public void onSuccess(String sign, Bundle bundle) {
		cancelPD();
	  if(sign.equals(Protocol.TO_LOGOUT)){
		  //退出登录
			clearInfo();
			Intent bespokefinishIn = new Intent();
			bespokefinishIn.setAction(BroadcastUtil.BROADCAST_Bespoke_Finish);
			sendBroadcast(bespokefinishIn);
			bespokefinishIn.setAction(BroadcastUtil.BROADCAST_Charge_CANCLE);
			sendBroadcast(bespokefinishIn);
			finish();
		}else if (sign.equals(Protocol.GET_USER_INFO)) {
			//用户详情信息
			userInfoBean = (UserInfoBean) bundle.getSerializable(Protocol.DATA);
			if(userInfoBean!= null){
				Intent myuserinfoIn = new Intent();
				myuserinfoIn.putExtra("userInfo", userInfoBean);
				myuserinfoIn.setClass(SettingsActivity.this, MyUserInfoActivity.class);
				startActivity(myuserinfoIn);
				updataUserInfo();
			}
		}
		
	}
	@Override
	public void onFaile(String sign, Bundle bundle) {
		cancelPD();
		showToast(bundle.getString(Protocol.MSG));
	}
	private void clearInfo(){
		PreferencesUtil.setPreferences(this, "pkUserinfo", "");
		PreferencesUtil.setPreferences(this, "usinPhone", "");
		PreferencesUtil.setPreferences(this, "usinFacticityname", "");
		PreferencesUtil.setPreferences(this, "usinSex", "");
		PreferencesUtil.setPreferences(this, "usinAccountbalance", "");
		PreferencesUtil.setPreferences(this, "usinBirthdate", "");
		PreferencesUtil.setPreferences(this, "usinUserstatus", "");
		PreferencesUtil.setPreferences(this, "usinHeadimage", "");
		PreferencesUtil.setPreferences(this, "nickName", "");
		PreferencesUtil.setPreferences(this, "carType", "");
		PreferencesUtil.setPreferences(this, "isPpw", "");
		
		PreferencesUtil.setPreferences(this, "chargepilenum","");
		PreferencesUtil.setPreferences(this, "chargeheadnum","");
	}
	private void updataUserInfo(){
		PreferencesUtil.setPreferences(this, "usinHeadimage",
				userInfoBean.getUserImage());
		PreferencesUtil.setPreferences(this, "usinAccountbalance",
				userInfoBean.getUserBlance());
		PreferencesUtil.setPreferences(this, "carType",
				userInfoBean.getUserCarType());
		PreferencesUtil.setPreferences(this, "usinPhone",
				userInfoBean.getUserTel());
		PreferencesUtil.setPreferences(this, "nickName",
				userInfoBean.getUserNickName());
		PreferencesUtil.setPreferences(this, "pkUserinfo",
				userInfoBean.getPkUserId());
		PreferencesUtil.setPreferences(this, "usinFacticityname",
				userInfoBean.getUserRealName());
		PreferencesUtil.setPreferences(this, "usinSex",
				userInfoBean.getUserSex());
		PreferencesUtil.setPreferences(this, "usinBirthdate",
				userInfoBean.getUserBrithy());
	}
	

	
}