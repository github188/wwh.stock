package com.bm.wanma.ui.activity;

import java.io.Serializable;
import java.util.List;

import net.tsz.afinal.FinalDb;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bm.wanma.R;
import com.bm.wanma.entity.OrderStatusBean;
import com.bm.wanma.entity.SystemNoticeBean;
import com.bm.wanma.net.Protocol;
import com.bm.wanma.utils.PreferencesUtil;
import com.bm.wanma.utils.TimeUtil;
import com.bm.wanma.utils.Tools;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * @author cm 我的消息
 * 
 */
public class MyNewsActivity extends Activity implements OnClickListener {

	private ImageButton ib_back;
	private RelativeLayout rl_news_system, rl_news_msg;
	private TextView tv_system_content, tv_system_time;
	private TextView tv_msg_content, tv_msg_time;
	private FinalDb finalDb;
	private List<SystemNoticeBean> systemBeans;
	private List<OrderStatusBean> orderBeans;
	private boolean isShowNotice,isShowOrderStatus;
	private String pkUserId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mynews);
		
		ib_back = (ImageButton) findViewById(R.id.mynews_back);
		ib_back.setOnClickListener(this);
		rl_news_system = (RelativeLayout) findViewById(R.id.mynews_system);
		rl_news_system.setOnClickListener(this);
		rl_news_msg = (RelativeLayout) findViewById(R.id.mynews_msg);
		rl_news_msg.setOnClickListener(this);

		tv_system_content = (TextView) findViewById(R.id.mynews_system_content);
		tv_system_time = (TextView) findViewById(R.id.mynews_system_time);
		tv_msg_content = (TextView) findViewById(R.id.mynews_msg_content);
		tv_msg_time = (TextView) findViewById(R.id.mynews_msg_time);
		if (finalDb == null) {
			finalDb = FinalDb.create(this, Protocol.DATABASE_NAME, true,
					Protocol.dbNumer, null);
		}
		//initValue();
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

	private void initValue() {
		systemBeans = finalDb.findAllDesc(SystemNoticeBean.class,"id");
		if (systemBeans.size() > 0) {
			try {
				tv_system_content.setText("" + systemBeans.get(0).getContent());
				tv_system_time.setText(TimeUtil.getDataForNews(
						systemBeans.get(0).getTime() * 1000, "yyyy-MM-dd"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			tv_system_content.setText("暂无系统公告");
		}
		pkUserId = PreferencesUtil.getStringPreferences(this, "pkUserinfo");
		if(!Tools.isEmptyString(pkUserId)){
			orderBeans = finalDb.findAllByWhereDesc(OrderStatusBean.class,pkUserId,"id");
			if (orderBeans.size() > 0) {
				try {
					tv_msg_content.setText("" + orderBeans.get(0).getContent());
					tv_msg_time.setText(TimeUtil.getDataForNews(orderBeans.get(0)
							.getTime() * 1000, "yyyy-MM-dd"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				tv_msg_content.setText("暂无我的消息");
			}
		}else {
			tv_msg_content.setText("暂无我的消息");
		}
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		initValue();
		isShowNotice = PreferencesUtil.getBooleanPreferences(this, "isShowSystemNotice", false);
		isShowOrderStatus = PreferencesUtil.getBooleanPreferences(this, "isShowOrderStatus", false);
		if(isShowOrderStatus){
			findViewById(R.id.mynews_msg_red).setVisibility(View.VISIBLE);
		}else {
			findViewById(R.id.mynews_msg_red).setVisibility(View.GONE);
		}
		if(isShowNotice){
			findViewById(R.id.mynews_system_red).setVisibility(View.VISIBLE);
		}else {
			findViewById(R.id.mynews_system_red).setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mynews_back:
			finish();
			break;
		case R.id.mynews_system:
			if (systemBeans.size() > 0) {
				PreferencesUtil.setPreferences(MyNewsActivity.this, "isShowSystemNotice",false);
				startActivity(new Intent(MyNewsActivity.this,
						MySystemNoticeActivity.class).putExtra("systemBeans",
						(Serializable) systemBeans));
			}
			break;
		case R.id.mynews_msg:
			if(!Tools.isEmptyString(pkUserId)){
				if (orderBeans.size() > 0) {
					PreferencesUtil.setPreferences(MyNewsActivity.this, "isShowOrderStatus",false);
					startActivity(new Intent(MyNewsActivity.this,
							MyOrderStatusActivity.class).putExtra("orderBeans",
							(Serializable) orderBeans));
				}
			}else {
				Intent loginIn = new Intent();
				loginIn.putExtra("source_inteface", "myNews");
				loginIn.setClass(this, LoginActivity.class);
				startActivity(loginIn);
			}
			
			break;

		default:
			break;
		}

	}

}
