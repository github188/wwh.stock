package com.bm.wanma.ui.activity;

import java.util.Map;

import com.bm.wanma.R;
import com.bm.wanma.utils.LogUtil;
import com.bm.wanma.utils.TCPUtil;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;

public class OpenDoorActivity extends Activity implements OnClickListener{
	
	private static final String IP = "155.94.206.11";
	private static final int port = 7478;
	private static final String cmd_open = "5501110000000269";
	private static final String cmd_reset = "550112000000026A";
	private Map<String, String> repMap;
	private NetWorkTask mTask;
	
	private ImageButton ib_back;
	private ImageView iv_content;
	private byte[] open,reset;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_open_door);
		ib_back = (ImageButton) findViewById(R.id.open_door_back);
		ib_back.setOnClickListener(this);
		iv_content = (ImageView) findViewById(R.id.open_door_content);
		 /** 设置缩放动画 */ 
		final ScaleAnimation animation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, 
		Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f); 
		animation.setDuration(5000);//设置动画持续时间 
		iv_content.startAnimation(animation);
		//mTask = new NetWorkTask();
		//mTask.execute(cmd_open);
		openDoor();
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
		case R.id.open_door_back:
			finish();
			break;
		default:
			break;
		}
		
	}
	
	private void openDoor(){
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				
				repMap = TCPUtil.sendTCPRequest(IP, port, cmd_open);
				LogUtil.i("cm_socket","请求报文如下-----"+ repMap.get("reqData"));
				LogUtil.i("cm_socket","响应报文如下-----"+ repMap.get("respData"));
				LogUtil.i("cm_socket","响应十六进制如下-----"+ repMap.get("respDataHex"));
				
			}
		}).start();
	}
	
	private void resetDoor(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				repMap = TCPUtil.sendTCPRequest(IP, port,cmd_reset);
				LogUtil.i("cm_socket","请求报文如下-----"+ repMap.get("reqData"));
				LogUtil.i("cm_socket","响应报文如下-----"+ repMap.get("respData"));
				LogUtil.i("cm_socket","响应十六进制如下-----"+ repMap.get("respDataHex"));
				
			}
		}).start();
		
		
	}
	
	
	
	class NetWorkTask extends AsyncTask<String,Integer,Map<String, String>>{
		@Override
		protected Map<String, String> doInBackground(String... params) {
			repMap = TCPUtil.sendTCPRequest(IP, port, params[0]);
			LogUtil.i("cm_socket","请求报文如下-----"+ repMap.get("reqData"));
			LogUtil.i("cm_socket","响应报文如下-----"+ repMap.get("respData"));
			LogUtil.i("cm_socket","响应十六进制如下-----"+ repMap.get("respDataHex"));
			
			return repMap;
		}
		
		
	}
	
	
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		resetDoor();
	}
	
	
	

}
