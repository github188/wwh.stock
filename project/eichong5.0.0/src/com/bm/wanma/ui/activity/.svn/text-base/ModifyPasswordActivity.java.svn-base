package com.bm.wanma.ui.activity;

import com.bm.wanma.R;
import com.bm.wanma.ui.fragment.BaseFragment;
import com.bm.wanma.ui.fragment.ChargeDoneFragment;
import com.bm.wanma.ui.fragment.PasswordModifyFragment;
import com.bm.wanma.ui.fragment.PasswordModifyMsgFragment;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 登录密码界面
 * cm
 */

public class ModifyPasswordActivity extends BaseActivity implements OnClickListener{
	
	private ImageButton btn_back;
	private RelativeLayout rl_normal,rl_msg;
	private TextView tv_password_modify,tv_msg_modify;
	private View l_password_modify,l_msg_modify;
	private boolean isNormal;
	private PasswordModifyFragment pModifyFragment;
	private PasswordModifyMsgFragment msgModifyMsgFragment;
	private BaseFragment currentFragment;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modify_pwd);
		initView();
	}

	private void initView(){
		btn_back = (ImageButton)findViewById(R.id.login_password_back);
		btn_back.setOnClickListener(this);
		rl_normal = (RelativeLayout) findViewById(R.id.normal_modify_rl);
		rl_normal.setOnClickListener(this);
		rl_msg = (RelativeLayout) findViewById(R.id.msg_modify_rl);
		rl_msg.setOnClickListener(this);
		tv_password_modify = (TextView)findViewById(R.id.normal_modify_tv);
		tv_msg_modify = (TextView)findViewById(R.id.msg_modify_tv);
		l_password_modify = findViewById(R.id.normal_modify_v);
		l_msg_modify = findViewById(R.id.msg_modify_v);
		
		if(pModifyFragment == null){
			pModifyFragment = new PasswordModifyFragment();
		}
		if(!pModifyFragment.isAdded()){
			getFragmentManager().beginTransaction().
			add(R.id.modify_pwd_content_layout,pModifyFragment).commit();
			currentFragment = pModifyFragment;
			isNormal = true;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_password_back:
			finish();
			break;
		case R.id.normal_modify_rl:
			clickNormal();
			break;
		case R.id.msg_modify_rl:
			clickMsg();
			break;
			default:
				break;
		}
		
	}

	private void clickNormal(){
		if(!isNormal){
			isNormal = true;
			tv_password_modify.setTextColor(getResources().getColor(R.color.common_orange));
			tv_msg_modify.setTextColor(getResources().getColor(R.color.common_light_black));
			l_password_modify.setVisibility(View.VISIBLE);
			l_msg_modify.setVisibility(View.GONE);
			
			if(pModifyFragment == null){
				pModifyFragment = new PasswordModifyFragment();
			}
			addOrShowFragment(getFragmentManager().beginTransaction(),
					pModifyFragment);
		}
	}
	private void clickMsg(){
		if(isNormal){
			isNormal = false;
			tv_password_modify.setTextColor(getResources().getColor(R.color.common_light_black));
			tv_msg_modify.setTextColor(getResources().getColor(R.color.common_orange));
			l_password_modify.setVisibility(View.GONE);
			l_msg_modify.setVisibility(View.VISIBLE);
			if(msgModifyMsgFragment == null){
				msgModifyMsgFragment = new PasswordModifyMsgFragment();
			}
			addOrShowFragment(getFragmentManager().beginTransaction(),
					msgModifyMsgFragment);
		}
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
			.add(R.id.modify_pwd_content_layout, fragment).commit();
		} else {
			transaction.hide(currentFragment).show(fragment).commit();
		}
		currentFragment = fragment;
	}

	
}
