package com.bm.wanma.ui.activity;

import com.amap.api.maps.model.Text;
import com.bm.wanma.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * @author cm
 * 关于我们
 *
 */
public class AboutWeActivity extends Activity implements OnClickListener{

	private TextView tv_protocol;
	private ImageButton ib_back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_we);
		tv_protocol = (TextView) findViewById(R.id.about_we_protocol);
		tv_protocol.setOnClickListener(this);
		ib_back = (ImageButton) findViewById(R.id.about_we_back);
		ib_back.setOnClickListener(this);
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
		case R.id.about_we_protocol:
			
			startActivity(new Intent(this,AboutProtolActivity.class));
			
			break;
		case R.id.about_we_back:
			finish();
			break;

		default:
			break;
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
