package com.bm.wanma.popup;

import java.lang.reflect.Method;

import com.bm.wanma.R;
import com.bm.wanma.ui.activity.RechargeActivity;
import com.bm.wanma.ui.activity.RechargeActivity.IsVisible;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;
/**
 *  支付弹出窗
 * @author lyh
 *
 */
public class SelectPatternPopupWindow extends BasePopupWindow implements
		OnClickListener ,IsVisible{
	private static selectpattern slebool;
	private Context mContext;
	private View view ,v_semitransparent;
	private ImageView iv_select_wechat , iv_select_alipay, im_pattern_close;
	private RelativeLayout rl_select_wechat , rl_select_alipay;
	public SelectPatternPopupWindow(Context context) {
		super(context);
		this.mContext = context;
		LayoutInflater layoutInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = layoutInflater.inflate(R.layout.popup_select_pattern, null);
		
		initView(view);
		this.setContentView(view);
		this.setWidth(LayoutParams.MATCH_PARENT);
		this.setHeight(LayoutParams.MATCH_PARENT);
		view.setFocusable(true);
		view.setFocusableInTouchMode(true);
		view.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				return false;
			}
		});
		try {
			Method method = PopupWindow.class.getDeclaredMethod(
					"setTouchModal", boolean.class);
			method.setAccessible(true);
			method.invoke(this, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		RechargeActivity.isvisible(SelectPatternPopupWindow.this);
		this.setAnimationStyle(R.style.AnimBottom2);
		ColorDrawable dw = new ColorDrawable(Color.parseColor("#00000000"));
		this.setBackgroundDrawable(dw);
		this.setOutsideTouchable(false);
	             
	}

	private void initView(View view) {
		v_semitransparent = view.findViewById(R.id.semitransparent);
		rl_select_alipay= (RelativeLayout) view.findViewById(R.id.recharge_alipay);
		rl_select_wechat = (RelativeLayout) view.findViewById(R.id.recharge_wx);
		
		iv_select_wechat = (ImageView) view.findViewById(R.id.select_wechat);
		iv_select_alipay = (ImageView) view.findViewById(R.id.select_alipay);
		im_pattern_close = (ImageView) view.findViewById(R.id.pattern_close);
		rl_select_wechat.setOnClickListener(this);
		rl_select_alipay.setOnClickListener(this);
		im_pattern_close.setOnClickListener(this);
	
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.recharge_alipay:
			v_semitransparent.setVisibility(View.INVISIBLE);
			iv_select_wechat.setVisibility(View.INVISIBLE);
			iv_select_alipay.setVisibility(View.VISIBLE);
			slebool.select(true);
			dismiss();
			break;
		case R.id.recharge_wx:
			v_semitransparent.setVisibility(View.INVISIBLE);
			iv_select_wechat.setVisibility(View.VISIBLE);
			iv_select_alipay.setVisibility(View.INVISIBLE);
			slebool.select(false);
			dismiss();
			break;
		case R.id.pattern_close:
			v_semitransparent.setVisibility(View.INVISIBLE);
			slebool.isback();
			dismiss();
			break;
		}

	}
	
	@Override
	public void onSuccess(String sign, Bundle bundle) {}

	@Override
	public void onFaile(String sign, Bundle bundle) {}
	
	public static void setcallback(selectpattern select){
		slebool = select;
	}
	
	public interface selectpattern{
		void select(boolean b);
		void isback();
 	}

	@Override
	public void isvisible(boolean par) {
		if (par) {
			iv_select_wechat.setVisibility(View.VISIBLE);
			iv_select_alipay.setVisibility(View.INVISIBLE);
		}else{
			iv_select_alipay.setVisibility(View.VISIBLE);
			iv_select_wechat.setVisibility(View.INVISIBLE);
		}
		v_semitransparent.setVisibility(View.VISIBLE);
		v_semitransparent.getBackground().setAlpha(100);
	}
}
