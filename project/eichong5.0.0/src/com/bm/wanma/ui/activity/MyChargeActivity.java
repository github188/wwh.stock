package com.bm.wanma.ui.activity;

import com.bm.wanma.R;
import com.bm.wanma.ui.fragment.BaseFragment;
import com.bm.wanma.ui.fragment.ChargeDoneFragment;
import com.bm.wanma.ui.fragment.ChargeUnfinishedFragment;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.umeng.analytics.MobclickAgent;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
/**
 * @author lyh
 *我的充电宿主 含二个fragment
 */
public class MyChargeActivity extends BaseActivity implements OnClickListener{
	
	private ChargeDoneFragment chargedoneFragment;
	private ChargeUnfinishedFragment chargeunfinishedFragment;
	private BaseFragment currentFragment;
	private ImageButton ib_back;
	private RelativeLayout rl_chargeunfinished,rl_chargedone;
	private View v_chargeunfinished,v_chargedone;
	private TextView tv_chargeunfinished,tv_chargedone;
	private ImageView tv_state_explain;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		mPageName = "MyChargeActivity";
		setContentView(R.layout.activity_mycharge);
		initView();
		String page = getIntent().getStringExtra("defaultpage");
		if(page.equals("pagedone")){
			DefaultPageDone();
		}else {
			DefaultPageFinished();
		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(true);
			SystemBarTintManager tintManager = new SystemBarTintManager(this);
			tintManager.setStatusBarTintEnabled(true);
			tintManager.setStatusBarTintResource(R.color.common_orange);
        }
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(mContext);
		MobclickAgent.onPageStart(mPageName);
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(mContext);
		MobclickAgent.onPageEnd(mPageName);
	}
	private void DefaultPageDone() {
		if(chargedoneFragment == null){
			chargedoneFragment = new ChargeDoneFragment();
		}
		if(!chargedoneFragment.isAdded()){
			getFragmentManager().beginTransaction().
			add(R.id.charge_content_layout,chargedoneFragment).commit();
			currentFragment = chargedoneFragment;
			tv_chargeunfinished.setTextColor(getResources().getColor(R.color.common_black));
			v_chargeunfinished.setVisibility(View.GONE);
			tv_chargedone.setTextColor(getResources().getColor(R.color.common_orange));
			v_chargedone.setVisibility(View.VISIBLE);
		}
		
	}
	private void DefaultPageFinished() {
		if(chargeunfinishedFragment == null){
			chargeunfinishedFragment = new ChargeUnfinishedFragment();
		}
		if(!chargeunfinishedFragment.isAdded()){
			getFragmentManager().beginTransaction().
			add(R.id.charge_content_layout,chargeunfinishedFragment).commit();
			currentFragment = chargeunfinishedFragment;
			tv_chargeunfinished.setTextColor(getResources().getColor(R.color.common_orange));
			v_chargeunfinished.setVisibility(View.VISIBLE);
			tv_chargedone.setTextColor(getResources().getColor(R.color.common_black));
			v_chargedone.setVisibility(View.GONE);
		}

	};
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
	
	private void initView(){
		//未完成
		rl_chargeunfinished = (RelativeLayout) findViewById(R.id.chargeunfinished_rl);
		rl_chargeunfinished.setOnClickListener(this);
		v_chargeunfinished = findViewById(R.id.chargeunfinished_v);
		tv_chargeunfinished = (TextView) findViewById(R.id.chargeunfinished_tv);
		//已完成
		rl_chargedone = (RelativeLayout) findViewById(R.id.chargedone_rl);
		rl_chargedone.setOnClickListener(this);
		v_chargedone = findViewById(R.id.chargedone_v);
		tv_chargedone = (TextView) findViewById(R.id.chargedone_tv);
		//状态按钮
		tv_state_explain = (ImageView) findViewById(R.id.state_explain);
		tv_state_explain.setOnClickListener(this);
		//返回按钮
		ib_back = (ImageButton) findViewById(R.id.charge_back);
		ib_back.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.chargeunfinished_rl:
			ChargeUnfinished();
			break;
		case R.id.chargedone_rl:
			clickChargeDone();
			break;
		case R.id.state_explain:
			startActivity(new Intent(MyChargeActivity.this,StateDeclareActivity.class));
			break;
		case R.id.charge_back:
			finish();
			break;
		default:
			break;
		}
		
	}
	
	
	
	private void ChargeUnfinished(){
		if(chargeunfinishedFragment == null){
			chargeunfinishedFragment = new ChargeUnfinishedFragment();
		}
		addOrShowFragment(getFragmentManager().beginTransaction(),
				chargeunfinishedFragment);
		tv_chargeunfinished.setTextColor(getResources().getColor(R.color.common_orange));
		v_chargeunfinished.setVisibility(View.VISIBLE);
		tv_chargedone.setTextColor(getResources().getColor(R.color.common_black));
		v_chargedone.setVisibility(View.GONE);
	}

	private void clickChargeDone(){
		if(chargedoneFragment == null){
			chargedoneFragment = new ChargeDoneFragment();
		}
		addOrShowFragment(getFragmentManager().beginTransaction(),
				chargedoneFragment);
		tv_chargeunfinished.setTextColor(getResources().getColor(R.color.common_black));
		v_chargeunfinished.setVisibility(View.GONE);
		tv_chargedone.setTextColor(getResources().getColor(R.color.common_orange));
		v_chargedone.setVisibility(View.VISIBLE);
	}

	/**
	 * 添加或者显示碎片
	 * @param transaction
	 * @param fragment
	 */
	public void addOrShowFragment(FragmentTransaction transaction,
			BaseFragment fragment) {
		if (currentFragment == fragment)
			return;
		if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
			transaction.hide(currentFragment)
			.add(R.id.charge_content_layout, fragment).commit();
		} else {
			transaction.hide(currentFragment).show(fragment).commit();
		}
		currentFragment = fragment;
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
