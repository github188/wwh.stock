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
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * @author cm
 * 操作说明
 */
public class InstructionActivity extends Activity  implements OnClickListener{

	private WebView webView;  
	private ImageButton ib_close;
	private String url;
	//private String priceId;
	
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_instruction);
		ib_close = (ImageButton) findViewById(R.id.instruction_back);
		ib_close.setOnClickListener(this);
		//priceId = getIntent().getStringExtra("priceId");
		webView = (WebView) findViewById(R.id.instruction_webview);
		
		//是否支持缩放，默认不支持(看起来更native)
		webView.getSettings().setBuiltInZoomControls(false);
		webView.getSettings().setSupportZoom(false);
		//允许JS弹出提示框
		//webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setUseWideViewPort(true);// 设置此属性，可任意比例缩放
		webView.getSettings().setLoadWithOverviewMode(true);
		url = Protocol.INSTRUCTION;
		//url = "http://html.eichong.com/html/help/index.html";
		webView.addJavascriptInterface(getHtmlObject(), "jsCall");
		webView.setWebChromeClient(new WebChromeClient());//允许alert对话框弹出
		
		webView.loadUrl(url);
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
		case R.id.instruction_back:
			finish();
			break;

		default:
			break;
		}
		
	}

	public Object getHtmlObject() {
		Object insertObj = new Object() {
			@JavascriptInterface
			public void close() {
				finish();
			}
		};
		return insertObj;
	}
	
	@Override
	public void finish() {
		//ViewGroup view = (ViewGroup) getWindow().getDecorView();
		//view.removeAllViews();
		//webView.setVisibility(View.GONE);
		super.finish();
	}
	
}
