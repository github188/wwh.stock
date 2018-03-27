package com.bm.wanma.ui.activity;

import com.bm.wanma.R;
import com.bm.wanma.entity.UserInfoBean;
import com.bm.wanma.ui.fragment.BaseFragment;
import com.bm.wanma.ui.fragment.MyApplyCardInfoFragment;
import com.bm.wanma.ui.fragment.MyCardInfoFragment;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author cm
 * 我的电卡
 */
public class MyIcCardActivity extends Activity implements OnClickListener{
	
	private ImageButton ib_back;
	private RelativeLayout rl_tab_card,rl_tab_apply;
	private TextView tv_tab_card,tv_tab_apply;
	private View v_tab_card,v_tab_apply;
	private BaseFragment currentFragment;
	private MyCardInfoFragment myCardInfoFragment;
	private MyApplyCardInfoFragment myApplyCardInfoFragment;
	private boolean hasFirstCardInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mycard);
		initView();
		initFragment();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(true);
			SystemBarTintManager tintManager = new SystemBarTintManager(this);
			tintManager.setStatusBarTintEnabled(true);
			tintManager.setStatusBarTintResource(R.color.common_orange);
        }
//		android:fitsSystemWindows="true"
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

	private void initView() {
		ib_back = (ImageButton) findViewById(R.id.activity_my_card_back);
		ib_back.setOnClickListener(this);
		rl_tab_card = (RelativeLayout) findViewById(R.id.my_card_rl_info);
		rl_tab_card.setOnClickListener(this);
		tv_tab_card = (TextView) findViewById(R.id.my_card_tv_info);
		v_tab_card = findViewById(R.id.my_card_v_info);
		rl_tab_apply = (RelativeLayout) findViewById(R.id.my_card_rl_apply);
		rl_tab_apply.setOnClickListener(this);
		tv_tab_apply = (TextView) findViewById(R.id.my_card_tv_apply);
		v_tab_apply = findViewById(R.id.my_card_v_apply);
	}
	/**
	 * 初始化头部标签
	 */
	private void initFragment(){
		if(myCardInfoFragment == null){
			myCardInfoFragment = new MyCardInfoFragment();
		}
		if (!myCardInfoFragment.isAdded()) {
			// 提交事务
			getFragmentManager().beginTransaction()
					.add(R.id.myCard_content_layout, myCardInfoFragment).commit();
			// 记录当前Fragment
			currentFragment = myCardInfoFragment;
		}
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.activity_my_card_back:
			finish();
			break;
		case R.id.my_card_rl_info:
			//卡信息
			clickTabOfCardInfo();
		break;
		case R.id.my_card_rl_apply:
			//申请信息
			clickTabOfApplyInfo();
		break;
		
		default:
			break;
		}
	}
	//卡信息
	private void clickTabOfCardInfo(){
		if(myCardInfoFragment == null){
			myCardInfoFragment = new MyCardInfoFragment();
		}
		addOrShowFragment(getFragmentManager().beginTransaction(), myCardInfoFragment);
		tv_tab_card.setTextColor(getResources().getColor(R.color.common_orange));
		v_tab_card.setBackgroundColor(getResources().getColor(R.color.common_orange));
		tv_tab_apply.setTextColor(getResources().getColor(R.color.common_light_black));
		v_tab_apply.setBackgroundColor(getResources().getColor(R.color.common_middle_gray));
	}
	//申请信息
	private void clickTabOfApplyInfo(){
		if(myApplyCardInfoFragment == null){
			hasFirstCardInfo = true;
			myApplyCardInfoFragment = new MyApplyCardInfoFragment();
		}
		addOrShowFragment(getFragmentManager().beginTransaction(), myApplyCardInfoFragment);
		tv_tab_card.setTextColor(getResources().getColor(R.color.common_light_black));
		v_tab_card.setBackgroundColor(getResources().getColor(R.color.common_middle_gray));
		tv_tab_apply.setTextColor(getResources().getColor(R.color.common_orange));
		v_tab_apply.setBackgroundColor(getResources().getColor(R.color.common_orange));	
		
		if(myCardInfoFragment.isPullData && !hasFirstCardInfo){
			myApplyCardInfoFragment.notifyDataChange();
		}
	}
	private void addOrShowFragment(FragmentTransaction transaction,
			BaseFragment fragment) {
		if (currentFragment == fragment){
			return;
		}else {
			if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
				transaction.hide(currentFragment)
						.add(R.id.myCard_content_layout, fragment).commit();
			} else {
				transaction.hide(currentFragment).show(fragment).commit();
			}
		}
		currentFragment = fragment;
	}


}
