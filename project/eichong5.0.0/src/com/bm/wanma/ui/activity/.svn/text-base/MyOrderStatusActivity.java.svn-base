package com.bm.wanma.ui.activity;

import java.util.List;

import com.bm.wanma.R;
import com.bm.wanma.adapter.MineInformationAdapter;
import com.bm.wanma.entity.OrderStatusBean;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
/**
 * 我的消息
 * @author lyh
 *
 */
public class MyOrderStatusActivity extends Activity {
	private ListView listView;
	private MineInformationAdapter adapter;
	private List<OrderStatusBean> mdata;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mine_information);
		InitStatusBar();		
		InitData();
		InitView();	
	}
	@SuppressWarnings("unchecked")
	public void InitData() {
		mdata =  (List<OrderStatusBean>) getIntent().getSerializableExtra("orderBeans");
	}
	public void InitView() {
		listView = (ListView) findViewById(R.id.mine_information);
		if(adapter == null){
			adapter = new MineInformationAdapter(this, mdata);
			listView.setAdapter(adapter);
		}
		/*listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					adapter.getView(position, view, listView);
				}
			});*/
		findViewById(R.id.information_close).setOnClickListener(new OnClickListener() {
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
