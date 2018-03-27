package com.bm.wanma.ui.activity;

import java.util.ArrayList;

import cn.jpush.android.api.JPushInterface;

import com.bm.wanma.R;
import com.bm.wanma.entity.AdvertisementBean;
import com.bm.wanma.entity.MyDynamicListBean;
import com.bm.wanma.net.GetDataPost;
import com.bm.wanma.net.Protocol;
import com.bm.wanma.utils.IntentUtil;
import com.bm.wanma.utils.PreferencesUtil;
import com.bm.wanma.utils.ProjectApplication;
import com.bm.wanma.utils.TimeUtil;
import com.bm.wanma.utils.Tools;
import com.umeng.analytics.MobclickAgent;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

/**
 * @author cm 启动界面
 * 
 */
public class StartActivity extends BaseActivity {
	private Uri uri;
	private int verN;
	private int versNumber;
	private String versName;
	private ImageView iv_advertisement;
	private Button btn_skip;
	private MyCount mc;
	private boolean isStart = true;//是否允许跳转到下一个界面
	String url = null;
	ArrayList<AdvertisementBean> advertisementBeans = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//定义全屏参数
        int flag=WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //获得当前窗体对象
        Window window=this.getWindow();
        //设置当前窗体为全屏显示
        window.setFlags(flag, flag);
		setContentView(R.layout.activity_start);
		Intent i_getvalue = getIntent();
		uri = i_getvalue.getData();
		iv_advertisement = (ImageView) findViewById(R.id.im_advertisement_splash);
		if(Tools.isPicture("Startpage")){
		iv_advertisement.setImageBitmap(BitmapFactory.decodeFile(Tools.advertisementpath + "SplashScreenadvertisement"));
		}else {
			PreferencesUtil.setPreferences(this, "StartpageValidity",false);
		}
		if(!Tools.isPicture("fenxiang")){			
			Bitmap thumb = BitmapFactory.decodeResource(StartActivity.this.getResources(),  
					R.drawable.eichong_icon);
			Tools.saveBitmap(thumb);
		}
		// 程序启动时，获取版本信息
		PackageManager packageManager = getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo;
		try {
			packInfo = packageManager.getPackageInfo(getPackageName(), 0);
			versNumber = packInfo.versionCode;
			versName = packInfo.versionName;
			String temp = PreferencesUtil.getStringPreferences(this,
					"versNumber");
			PreferencesUtil.setPreferences(getApplicationContext(),
					"versNumber", String.valueOf(versNumber));
			PreferencesUtil.setPreferences(getApplicationContext(), "versName",
					versName);
			if (!Tools.isEmptyString(temp)) {
				verN = Integer.valueOf(temp);
				if (versNumber > verN) {
					ProjectApplication.setGuideType(false);// 版本更新后再次进入引导页闪屏
				}
			}

		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		
		if (!Tools.isEmptyString(PreferencesUtil.getStringPreferences(this,
				"provincecode"))||!Tools.isEmptyString(PreferencesUtil.getStringPreferences(this, "citycode"))) {
			 PreferencesUtil.setPreferences(this, "provincecode", "330000");
			 PreferencesUtil.setPreferences(this, "citycode", "330100");			
		}
		AdvertisementValidity();
	}
	
	private void initView(final String adtgo,final long SplashScreenatime) {
		btn_skip = (Button) findViewById(R.id.btn_skip);
		iv_advertisement.setImageBitmap(BitmapFactory.decodeFile(Tools.advertisementpath + "SplashScreenadvertisement"));
		
		btn_skip.setVisibility(View.VISIBLE);
		mc = new MyCount(SplashScreenatime, 1000);
		mc.start();
		
		iv_advertisement.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {  
				if(isNetConnection()
						&&Tools.judgeString(adtgo.replaceAll(" ", ""),"1")
						){
					Advertisementskip();
//					GetDataPost.getInstance(StartActivity.this).getAdvertisement(handler,"1","");
				}
			}			
		});
		
		btn_skip.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				initskip(0);
			}
		});
	}

	private void Advertisementskip() {
		
		if(!Tools.isEmptyString(PreferencesUtil.getStringPreferences(this, "SplashScreenUrl"))
				){
			isConceal = true;
			MyDynamicListBean itemBean = new MyDynamicListBean();
			itemBean.setAdUrl(PreferencesUtil.getStringPreferences(this, "SplashScreenUrl"));
			PreferencesUtil.setPreferences(this, "onclickSplash", true);
			Intent Interstitial = new Intent();
			Interstitial.putExtra("releaseId", itemBean);
			Interstitial.setClass(this, MyDynamicDetailActivity.class);
			startActivity(Interstitial);
			finish();
		}
	}
	@Override
	protected void onStart() {
		super.onStart();

	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(mContext);
		JPushInterface.onPause(this);
	}
	@Override
	protected void onStop() {
		super.onStop();
	}
	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(mContext);
		if(!isStart){
			SkipControl();
		}
		JPushInterface.onResume(this);
	}

	@Override
	protected void getData() {
		GetDataPost.getInstance(this).getApiToken(handler);
		
	}

	@Override
	public void onSuccess(String sign, Bundle bundle) {
		if (sign.equals(Protocol.GET_API_TOKEN)) {// 获取token
			String apiToken = (String) bundle.getSerializable(Protocol.DATA);
			PreferencesUtil.setPreferences(getApplicationContext(), "apiToken",	apiToken);
		}
	}
	/**
	 * 有效期相关  
	 * @param advertisementBean 广告相关信息
	 */
	private void AdvertisementValidity() {
		final long time = !Tools.isEmptyString(PreferencesUtil.getStringPreferences(this, "SplashScreenatime"))?
				Long.parseLong(PreferencesUtil.getStringPreferences(this, "SplashScreenatime"))*1000:5000;

		if(!Tools.isEmptyString(PreferencesUtil.getStringPreferences(this, "SplashScreenadvertisementadtgo"))
				&& Tools.isPicture("SplashScreenadvertisement")
				&& !Tools.isEmptyString(PreferencesUtil.getStringPreferences(this, "SplashScreenadvertisementBeginTime"))
				&& !Tools.isEmptyString(PreferencesUtil.getStringPreferences(this, "SplashScreenadvertisementEndTime"))
				&& System.currentTimeMillis() >= TimeUtil.getTimestamp(PreferencesUtil.getStringPreferences(this, "SplashScreenadvertisementBeginTime"),"yyyy-MM-dd HH:mm:ss")
				&& System.currentTimeMillis() <= TimeUtil.getTimestamp(PreferencesUtil.getStringPreferences(this, "SplashScreenadvertisementEndTime"),"yyyy-MM-dd HH:mm:ss")
				){
			final String adtgo = PreferencesUtil.getStringPreferences(this, "SplashScreenadvertisementadtgo");
			
			//在有效期
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					
					initView(adtgo ,time);
				}
			}, 1000);
		}else {
			//不在有效期
			initskip(3000);
		}
		
	}
	@Override
	public void onFaile(String sign, Bundle bundle) {

	}

	// 定义一个倒计时的内部类
	class MyCount extends CountDownTimer {
		public MyCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
			if(!isBackgroud){
				isStart = false;
			}else {
				SkipControl();
			}
//			initskip(0);
			}

		@Override
		public void onTick(long millisUntilFinished) {
			btn_skip.setText(millisUntilFinished / 1000 + "秒后跳转");
		}
	}

	private void initskip(long time) {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				if (isStart) {
					SkipControl();
				}
			}
		}, time);

	}
	@Override
	protected void onDestroy() {
		isStart = false;
		super.onDestroy();
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
	private void SkipControl() {
		if (ProjectApplication.getGuideType()) {
			if (!ProjectApplication.getButtonType()&&isNetConnection()) {
				ProjectApplication.setButtonType(true);
			}
			isConceal = true;
			if (uri != null) {
				Bundle detailBudle = new Bundle();
				String pt = uri.getQueryParameter("pt");// 参数类型
				String d = uri.getQueryParameter("d"); // 电桩或电站id
				String et = uri.getQueryParameter("et"); // 1为电桩，2为电站 advertisementBeans
				Intent detailin = new Intent();
				detailBudle.putString("pt", pt);
				detailBudle.putString("d", d);
				detailBudle.putString("et", et);
				detailin.putExtras(detailBudle);
				detailin.setClass(StartActivity.this,HomeActivity.class);
				startActivity(detailin);
				finish();
			} else {
				IntentUtil.startIntent(StartActivity.this,HomeActivity.class);
				finish();
			}
		} else {// 进入引导页
			IntentUtil.startIntent(StartActivity.this,GuideActivity.class);
			ProjectApplication.setGuideType(true);// 让其以后不进入引导页
			
			finish();
		}
		
	}
}
