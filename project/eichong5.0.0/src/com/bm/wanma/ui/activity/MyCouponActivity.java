package com.bm.wanma.ui.activity;

import com.bm.wanma.R;
import com.bm.wanma.entity.CouponSizeBean;
import com.bm.wanma.net.GetDataPost;
import com.bm.wanma.net.Protocol;
import com.bm.wanma.ui.fragment.BaseFragment;
import com.bm.wanma.ui.fragment.BaseFragment.CouponChangeSize;
import com.bm.wanma.ui.fragment.CouponDeprecatedFragment;
import com.bm.wanma.ui.fragment.CouponUsableFragment;
import com.bm.wanma.ui.fragment.CouponUsedFragment;
import com.bm.wanma.utils.PreferencesUtil;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
/**
 * @author cm
 *优惠券宿主 含三个fragment
 */
public class MyCouponActivity extends BaseActivity implements OnClickListener ,CouponChangeSize{
	private CouponSizeBean couponSizeBean = null;
	private CouponDeprecatedFragment deprecatedFragment;
	private CouponUsableFragment usableFragment;
	private CouponUsedFragment usedFragment;
	private BaseFragment currentFragment;
	private ImageButton ib_back;
	private RelativeLayout rl_usable,rl_used,rl_deprecate;
	private View v_usable,v_used,v_deprecate;
	private TextView tv_coupon,tv_usable,tv_used,tv_deprecate,tv_coupon_usable_tv_size,tv_coupon_used_tv_size,tv_coupon_deprecate_tv_size;
	private String pkuserId;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_mycoupon);
		BaseFragment.setCouponChangeSize(this);
		mPageName = "MyCouponActivity";
		initView();
		if(usableFragment == null){
			usableFragment = new CouponUsableFragment();
		}
		if(!usableFragment.isAdded()){
			getFragmentManager().beginTransaction().
			add(R.id.coupon_content_layout,usableFragment).commit();
			currentFragment = usableFragment;
		}
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
	
	private void initView(){
		tv_coupon_usable_tv_size = (TextView) findViewById(R.id.coupon_usable_tv_size);
		tv_coupon_used_tv_size = (TextView) findViewById(R.id.coupon_used_tv_size);
		tv_coupon_deprecate_tv_size = (TextView) findViewById(R.id.coupon_deprecate_tv_size);
		rl_usable = (RelativeLayout) findViewById(R.id.coupon_usable_rl);
		rl_usable.setOnClickListener(this);
		v_usable = findViewById(R.id.coupon_usable_v);
		tv_usable = (TextView) findViewById(R.id.coupon_usable_tv);
		rl_used = (RelativeLayout) findViewById(R.id.coupon_used_rl);
		rl_used.setOnClickListener(this);
		v_used = findViewById(R.id.coupon_used_v);
		tv_used = (TextView) findViewById(R.id.coupon_used_tv);
		rl_deprecate = (RelativeLayout) findViewById(R.id.coupon_deprecate_rl);
		rl_deprecate.setOnClickListener(this);
		v_deprecate = findViewById(R.id.coupon_deprecate_v);
		tv_deprecate = (TextView) findViewById(R.id.coupon_deprecate_tv);
		tv_coupon = (TextView) findViewById(R.id.coupon_entrance);
		tv_coupon.setOnClickListener(this);
		ib_back = (ImageButton) findViewById(R.id.coupon_back);
		ib_back.setOnClickListener(this);
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
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.coupon_usable_rl:
			clickUsable();
			break;
		case R.id.coupon_used_rl:
			clickUsed();
			break;
		case R.id.coupon_deprecate_rl:
			clickDeprecate();
			break;
		case R.id.coupon_entrance:
			MobclickAgent.onEvent(mContext, "wode_youhuiquan_duihuan");
			startActivity(new Intent(MyCouponActivity.this,CouponExchangeActivity.class));
			break;
		case R.id.coupon_back:
			finish();
			break;
		default:
			break;
		}
		
	}
	
	
	
	private void clickUsable(){
		if(usableFragment == null){
			usableFragment = new CouponUsableFragment();
		}
		addOrShowFragment(getFragmentManager().beginTransaction(),
				usableFragment);
		tv_usable.setTextColor(getResources().getColor(R.color.common_black));
		v_usable.setVisibility(View.VISIBLE);
		tv_coupon_usable_tv_size.setTextColor(getResources().getColor(R.color.common_black));
		tv_used.setTextColor(getResources().getColor(R.color.common_gray));
		v_used.setVisibility(View.GONE);
		tv_coupon_used_tv_size.setTextColor(getResources().getColor(R.color.common_gray));
		tv_deprecate.setTextColor(getResources().getColor(R.color.common_gray));
		v_deprecate.setVisibility(View.GONE);
		tv_coupon_deprecate_tv_size.setTextColor(getResources().getColor(R.color.common_gray));
	}
	private void clickUsed(){
		if(usedFragment == null){
			usedFragment = new CouponUsedFragment();
		}
		addOrShowFragment(getFragmentManager().beginTransaction(),
				usedFragment);
		tv_usable.setTextColor(getResources().getColor(R.color.common_gray));
		tv_coupon_usable_tv_size.setTextColor(getResources().getColor(R.color.common_gray));
		v_usable.setVisibility(View.GONE);
		tv_used.setTextColor(getResources().getColor(R.color.common_black));
		tv_coupon_used_tv_size.setTextColor(getResources().getColor(R.color.common_black));
		v_used.setVisibility(View.VISIBLE);
		tv_deprecate.setTextColor(getResources().getColor(R.color.common_gray));
		tv_coupon_deprecate_tv_size.setTextColor(getResources().getColor(R.color.common_gray));
		v_deprecate.setVisibility(View.GONE);
	}
	private void clickDeprecate(){
		if(deprecatedFragment == null){
			deprecatedFragment = new CouponDeprecatedFragment();
		}
		addOrShowFragment(getFragmentManager().beginTransaction(),
				deprecatedFragment);
		tv_usable.setTextColor(getResources().getColor(R.color.common_gray));
		tv_coupon_usable_tv_size.setTextColor(getResources().getColor(R.color.common_gray));
		v_usable.setVisibility(View.GONE);
		tv_used.setTextColor(getResources().getColor(R.color.common_gray));
		tv_coupon_used_tv_size.setTextColor(getResources().getColor(R.color.common_gray));
		v_used.setVisibility(View.GONE);
		tv_deprecate.setTextColor(getResources().getColor(R.color.common_black));
		tv_coupon_deprecate_tv_size.setTextColor(getResources().getColor(R.color.common_black));
		v_deprecate.setVisibility(View.VISIBLE);
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
			.add(R.id.coupon_content_layout, fragment).commit();
		} else {
			transaction.hide(currentFragment).show(fragment).commit();
		}
		currentFragment = fragment;
	}

	@Override
	protected void getData() {
		pkuserId = PreferencesUtil.getStringPreferences(this,"pkUserinfo");
		GetDataPost.getInstance(getActivity()).getMyCouponSize(handler, pkuserId);
	}

	@Override
	public void onSuccess(String sign, Bundle bundle) {
		if (bundle != null) {
			couponSizeBean = (CouponSizeBean) bundle.getSerializable(Protocol.DATA);
			if (!couponSizeBean.getUsedCount().equals("0")) {
				tv_coupon_used_tv_size.setVisibility(View.VISIBLE);
				tv_coupon_used_tv_size.setText("("+couponSizeBean.getUsedCount()+")");
			}else {
				tv_coupon_used_tv_size.setVisibility(View.GONE);
			}
			if (!couponSizeBean.getOverCount().equals("0")) {
				tv_coupon_deprecate_tv_size.setVisibility(View.VISIBLE);
				tv_coupon_deprecate_tv_size.setText("("+couponSizeBean.getOverCount()+")");
			}else {
				tv_coupon_deprecate_tv_size.setVisibility(View.GONE);
			}
			if (!couponSizeBean.getUnusedCount().equals("0")) {
				tv_coupon_usable_tv_size.setVisibility(View.VISIBLE);
				tv_coupon_usable_tv_size.setText("("+couponSizeBean.getUnusedCount()+")");
			}else {
				tv_coupon_usable_tv_size.setVisibility(View.GONE);
			}
		}
	}

	@Override
	public void onFaile(String sign, Bundle bundle) {
		
	}

	@Override
	public void couponused() {
		
		if (isNetConnection()) {
			GetDataPost.getInstance(getActivity()).getMyCouponSize(handler, pkuserId);
		}
//		if (!size.equals("0")) {
//			tv_coupon_used_tv_size.setVisibility(View.VISIBLE);
//			tv_coupon_used_tv_size.setText("("+couponSizeBean.getUsedCount()+")");
//		}else {
//			tv_coupon_used_tv_size.setVisibility(View.GONE);
//		}
	
	}

	@Override
	public void couponusable() {
		if (isNetConnection()) {
			GetDataPost.getInstance(getActivity()).getMyCouponSize(handler, pkuserId);
		}
//		if (!size.equals("0")) {
//			tv_coupon_usable_tv_size.setVisibility(View.VISIBLE);
//			tv_coupon_usable_tv_size.setText("("+couponSizeBean.getUnusedCount()+")");
//		}else {
//			tv_coupon_usable_tv_size.setVisibility(View.GONE);
//		}
	}

	@Override
	public void coupondeprecated() {
		if (isNetConnection()) {
			GetDataPost.getInstance(getActivity()).getMyCouponSize(handler, pkuserId);
		}
//		if (!size.equals("0")) {
//			tv_coupon_deprecate_tv_size.setVisibility(View.VISIBLE);
//			tv_coupon_deprecate_tv_size.setText("("+couponSizeBean.getOverCount()+")");
//		}else {
//			tv_coupon_deprecate_tv_size.setVisibility(View.GONE);
//		}
		
	}



	
}
