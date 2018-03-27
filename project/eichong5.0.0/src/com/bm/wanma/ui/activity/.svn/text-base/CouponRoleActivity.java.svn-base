package com.bm.wanma.ui.activity;

import com.bm.wanma.R;
import com.bm.wanma.net.Protocol;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

public class CouponRoleActivity extends Activity implements OnClickListener{
	
	private ImageButton ib_back;
	private String url;
	private WebView webView;
	
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coupon_role);
		ib_back = (ImageButton) findViewById(R.id.coupon_role_back);
		ib_back.setOnClickListener(this);
		webView = (WebView) findViewById(R.id.coupon_role_web);
		webView.getSettings().setBuiltInZoomControls(true);
		webView.getSettings().setSupportZoom(false);// 便页面支持缩放
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setUseWideViewPort(true);// 设置此属性，可任意比例缩放
		webView.getSettings().setLoadWithOverviewMode(true);
		webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);//设置 缓存模式
		url = Protocol.SERVER_ADDRESS_HTML+"/html/couponRules/couponRules.html";
		webView.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
		webView.loadUrl(url);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(true);
			SystemBarTintManager tintManager = new SystemBarTintManager(this);
			tintManager.setStatusBarTintEnabled(true);
			tintManager.setStatusBarTintResource(R.color.common_orange);
        }
//		android:fitsSystemWindows="true"
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
		case R.id.coupon_role_back:
			finish();
			break;

		default:
			break;
		}
		
	}
	
	
	
	//按返回键时， 不退出程序而是返回上一浏览页面
	public boolean onKeyDown(int keyCode, KeyEvent event) {       
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {       
        	webView.goBack();       
            return true;       
        }       
        return super.onKeyDown(keyCode, event);       
    }
	

}
