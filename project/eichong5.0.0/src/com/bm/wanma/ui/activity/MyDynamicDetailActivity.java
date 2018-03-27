package com.bm.wanma.ui.activity;

import com.bm.wanma.R;
import com.bm.wanma.entity.MyDynamicListBean;
import com.bm.wanma.utils.PreferencesUtil;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
/**
 * @author cm
 * 动态详情界面
 */
public class MyDynamicDetailActivity extends Activity implements OnClickListener{

	private WebView webView;  
	private ImageButton ib_back;
	private MyDynamicListBean bean;
	private Uri uri;
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dynamic_detail);
		uri = getIntent().getData();
		bean = (MyDynamicListBean) getIntent().getSerializableExtra("releaseId");
		ib_back = (ImageButton) findViewById(R.id.activity_dynamic_detail_back);
		ib_back.setOnClickListener(this);
		webView = (WebView) findViewById(R.id.activity_dynamic_detail_web);
		webView.getSettings().setBuiltInZoomControls(true);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setBuiltInZoomControls(true);
		webView.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});

		webView.loadUrl(bean.getAdUrl());
		webView.getSettings().setDisplayZoomControls(false);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(true);
			SystemBarTintManager tintManager = new SystemBarTintManager(this);
			tintManager.setStatusBarTintEnabled(true);
			tintManager.setStatusBarTintResource(R.color.common_orange);
        }
	}
	
	@TargetApi(19) 
	private void setTranslucentStatus(boolean on) {
		Window win = getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.activity_dynamic_detail_back:
			SkipControl();
			finish();
			break;
		default:
			break;
		}
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			SkipControl();
			finish();
		}
		return false;
	}
	private void SkipControl() {
		if(PreferencesUtil.getBooleanPreferences(this, "onclickSplash", false)){
			if (uri != null) {
				String pt = uri.getQueryParameter("pt");// 参数类型
														// 为2进入详情
				String d = uri.getQueryParameter("d"); // 电桩或电站id
				String et = uri.getQueryParameter("et"); // 1为电桩，2为电站
				Intent detailin = new Intent();
				Bundle detailBudle = new Bundle();
				detailBudle.putString("pt", pt);
				detailBudle.putString("d", d);
				detailBudle.putString("et", et);
				detailin.putExtras(detailBudle);
				detailin.setClass(this,
						HomeActivity.class);
				startActivity(detailin);
			} else {
				Intent in = new Intent();
				in.setClass(this, HomeActivity.class);
				startActivity(in);
			}
			PreferencesUtil.setPreferences(this, "onclickSplash", false);
		}
	}
}
