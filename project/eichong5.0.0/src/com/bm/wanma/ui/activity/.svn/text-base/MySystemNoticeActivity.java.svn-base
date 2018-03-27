package com.bm.wanma.ui.activity;

import java.util.List;

import com.bm.wanma.R;
import com.bm.wanma.adapter.SystemNoticeAdapter;
import com.bm.wanma.entity.SystemNoticeBean;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ListView;

/**
 * 系统通知
 * 
 * @author lyh
 * 
 */
public class MySystemNoticeActivity extends Activity {
	private ListView listView;
	private SystemNoticeAdapter adapter;
	private List<SystemNoticeBean> mdata;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_system_notice);
		InitStatusBar();
		InitData();
		InitView();
	}

	@SuppressWarnings("unchecked")
	public void InitData() {
		mdata = (List<SystemNoticeBean>) getIntent().getSerializableExtra(
				"systemBeans");
	}

	public void InitView() {
		listView = (ListView) findViewById(R.id.mine_information);
		if (adapter == null) {
			adapter = new SystemNoticeAdapter(this, mdata);
			listView.setAdapter(adapter);
		}
		findViewById(R.id.information_close).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						finish();
					}
				});

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

	private void InitStatusBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(true);
			SystemBarTintManager tintManager = new SystemBarTintManager(this);
			tintManager.setStatusBarTintEnabled(true);
			tintManager.setStatusBarTintResource(R.color.common_orange);
		}
	}
}
