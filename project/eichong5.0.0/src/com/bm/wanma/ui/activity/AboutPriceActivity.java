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
import android.widget.TextView;

public class AboutPriceActivity extends Activity  implements OnClickListener{

	private WebView webView;  
	private TextView tv_close;
	private String url;
	private String priceId;
	 
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_price_detail);
		tv_close = (TextView) findViewById(R.id.about_price_close);
		tv_close.setOnClickListener(this);
		priceId = getIntent().getStringExtra("priceId");
		webView = (WebView) findViewById(R.id.about_price_webview);
		webView.getSettings().setBuiltInZoomControls(true);
		webView.getSettings().setSupportZoom(true);// 便页面支持缩放
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setUseWideViewPort(true);// 设置此属性，可任意比例缩放
		webView.getSettings().setLoadWithOverviewMode(true);
		webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);//设置 缓存模式
		//url = Protocol.SERVER_ADDRESS_HTML + "/html/rateinfo/detail.html?id="+priceId;
		url = Protocol.ABOUT_PRICE + "?id="+priceId;
		Log.i("socket_cm", url);
		webView.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
		webView.loadUrl(url);
	         
	        /*webView.addJavascriptInterface(new Object() {       
	            public void clickOnAndroid() {       
	                mHandler.post(new Runnable() {       
	                    public void run() {       
	                    	webView.loadUrl("javascript:wave()");       
	                    }       
	                });       
	            }       
	        }, "demo"); */
		
		
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
	//按返回键时， 不退出程序而是返回上一浏览页面
	public boolean onKeyDown(int keyCode, KeyEvent event) {       
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {       
        	webView.goBack();       
            return true;       
        }       
        return super.onKeyDown(keyCode, event);       
    }
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.about_price_close:
			finish();
			break;

		default:
			break;
		}
		
	}
}
