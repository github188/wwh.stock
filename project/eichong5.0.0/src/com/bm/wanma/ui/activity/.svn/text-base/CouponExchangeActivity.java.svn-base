package com.bm.wanma.ui.activity;

import com.bm.wanma.R;
import com.bm.wanma.net.GetDataPost;
import com.bm.wanma.net.Protocol;
import com.bm.wanma.utils.PreferencesUtil;
import com.umeng.analytics.MobclickAgent;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * @author cm
 * 兑换优惠券
 *
 */
public class CouponExchangeActivity extends BaseActivity implements OnClickListener{
	private ImageButton ib_back;
	private EditText et_coupon;
	private TextView tv_role,tv_commit;
	private String code,uId;
	private boolean isRefresh;
	private static CouponCallback mCouponCallback;
	
    
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coupon_exchange);
		uId = PreferencesUtil.getStringPreferences(getActivity(),"pkUserinfo");
		ib_back = (ImageButton) findViewById(R.id.coupon_exchange_back);
		ib_back.setOnClickListener(this);
		tv_role =  (TextView) findViewById(R.id.coupon_exchange_role);
		tv_role.setOnClickListener(this);
		tv_commit = (TextView) findViewById(R.id.coupon_exchange_commit);
		tv_commit.setOnClickListener(this);
		et_coupon = (EditText) findViewById(R.id.coupon_exchange_et);
		et_coupon.addTextChangedListener(new MyCouponTextWatch());
		et_coupon.setOnClickListener(this);
	}
	
	
	public static void setOnCouponListener(CouponCallback c){
		mCouponCallback = c;
	}
@Override
protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	MobclickAgent.onResume(mContext);
}
@Override
protected void onPause() {
	// TODO Auto-generated method stub
	super.onPause();
	MobclickAgent.onPause(mContext);
}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.coupon_exchange_et:
			MobclickAgent.onEvent(mContext, "wode_youhuiquan_duihuanshurukuang");
			break;
		case R.id.coupon_exchange_back:
			finish();
			break;
		case R.id.coupon_exchange_role:
			startActivity(new Intent(CouponExchangeActivity.this,CouponRoleActivity.class));
			break;
		case R.id.coupon_exchange_commit:
			MobclickAgent.onEvent(mContext, "wode_youhuiquan_duihuananniu");
			code = et_coupon.getText().toString();
			if(isNetConnection()){
				showPD("正在兑换，请稍后");
				GetDataPost.getInstance(CouponExchangeActivity.this).
				exchangeCoupon(handler, uId, code);
			}else {
				showToast("网络不稳，请稍后再试");
			}
			break;

		default:
			break;
		}
		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(mCouponCallback != null && isRefresh){
			mCouponCallback.convertSuccess();
		}
	}
	

	@Override
	protected void getData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSuccess(String sign, Bundle bundle) {
		cancelPD();
		isRefresh = true;
		showToast("兑换成功");
		
	}

	@Override
	public void onFaile(String sign, Bundle bundle) {
		cancelPD();
		//showToast("优惠码不可用或者已过期");
		showToast(bundle.getString(Protocol.MSG));

	}

	// 兑换框textwatch
	private class MyCouponTextWatch implements TextWatcher {

		@Override
		public void afterTextChanged(Editable s) {
			
			if (s.toString().length() == 5) {
				tv_commit.setEnabled(true);
				int left = tv_commit.getPaddingLeft();
				tv_commit.setBackgroundResource(R.drawable.common_shape_corner_orange);
				tv_commit.setPadding(left, left, left, left);
			} else {
				tv_commit.setEnabled(false);
				int left = tv_commit.getPaddingLeft();
				tv_commit.setBackgroundResource(R.drawable.common_shape_corner_gray);
				tv_commit.setPadding(left, left, left, left);
			} 

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub

		}

	}
	
	public interface CouponCallback{
		public void convertSuccess();
	}


}
