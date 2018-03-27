package com.bm.wanma.ui.activity;

import com.bm.wanma.R;
import com.bm.wanma.utils.LogUtil;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class InvoiceTypeActivity extends Activity implements OnClickListener{
	
	private ImageButton ib_back,ib_close;
	private TextView tv_company,tv_personal,tv_title;
	private String pcode,pmoney;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invoice_type);
		ib_back = (ImageButton) findViewById(R.id.invoice_back);
		ib_back.setOnClickListener(this);
		ib_close = (ImageButton) findViewById(R.id.invoice_close);
		ib_close.setOnClickListener(this);
		tv_company = (TextView) findViewById(R.id.invoice_type_company);
		tv_company.setOnClickListener(this);
		
		tv_title = (TextView) findViewById(R.id.common_invoice_title);
		tv_title.setText("选择开票类型");
		tv_personal = (TextView) findViewById(R.id.invoice_type_personal);
		tv_personal.setOnClickListener(this);
		registerBoradcastReceiver();
		pcode = getIntent().getStringExtra("pcode");
		pmoney = getIntent().getStringExtra("pmoney");
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
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mBroadcastReceiver);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.invoice_back:
			finish();
			break;
		case R.id.invoice_close:
			sendBroadcast(new Intent("action.invoice.close"));
			finish();
			break;
		case R.id.invoice_type_company:
			Intent company = new Intent(this,InvoiceCompanyActivity.class);
			company.putExtra("pcode",pcode);
			company.putExtra("pmoney",pmoney);
			startActivity(company);
			break;
		case R.id.invoice_type_personal:
			Intent personal = new Intent(this,InvoicePersonalActivity.class);
			personal.putExtra("pcode",pcode);
			personal.putExtra("pmoney",pmoney);
			startActivity(personal);
			
			break;
		default:
			break;
		}
		
	}
	public void registerBoradcastReceiver(){  
        IntentFilter myIntentFilter = new IntentFilter();  
        myIntentFilter.addAction("action.invoice.close"); 
        //注册广播        
        registerReceiver(mBroadcastReceiver, myIntentFilter);  
    }  
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){

		@Override
		public void onReceive(Context context, Intent intent) {
			  String action = intent.getAction();
			  if(action.equals("action.invoice.close")){
				  finish();
			  }
		 }
	};
	
	
}
