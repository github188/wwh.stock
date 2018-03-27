package com.bm.wanma.ui.activity;

import com.bm.wanma.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

public class CommonH5Activity extends BaseActivity implements OnClickListener{
	private String h5;
	private WebView webView;
	private TextView tv_title,tv_heavy_to_pull;
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_h5);
		h5 = getIntent().getStringExtra("h5url");
		String h5title = getIntent().getStringExtra("h5title");
		tv_title = (TextView) findViewById(R.id.title);
		tv_heavy_to_pull = (TextView) findViewById(R.id.heavy_to_pull);
		tv_title.setText(h5title);
		webView = (WebView) findViewById(R.id.eichong_protocel_web);
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
		if (isNetConnection()) {
			webView.setVisibility(View.VISIBLE);
			webView.loadUrl(h5);
			tv_heavy_to_pull.setVisibility(View.GONE);
		}else {
			webView.setVisibility(View.GONE);
			tv_heavy_to_pull.setVisibility(View.VISIBLE);
			tv_heavy_to_pull.setOnClickListener(this);
		}
		webView.getSettings().setDisplayZoomControls(false);
		findViewById(R.id.register_agreement_close).setOnClickListener(this);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(true);
			SystemBarTintManager tintManager = new SystemBarTintManager(this);
			tintManager.setStatusBarTintEnabled(true);
			tintManager.setStatusBarTintResource(R.color.common_orange);
        }
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.register_agreement_close:
			finish();
			break;
		case R.id.heavy_to_pull:
			if (isNetConnection()) {
				webView.setVisibility(View.VISIBLE);
				webView.loadUrl(h5);
				tv_heavy_to_pull.setVisibility(View.GONE);
			}
			break;
		default:
			break;
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
	protected void getData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSuccess(String sign, Bundle bundle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFaile(String sign, Bundle bundle) {
		// TODO Auto-generated method stub
		
	}

	

}
