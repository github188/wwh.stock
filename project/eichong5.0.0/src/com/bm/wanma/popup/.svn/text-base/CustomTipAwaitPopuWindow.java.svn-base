package com.bm.wanma.popup;


import java.lang.reflect.Method;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bm.wanma.R;
/**
 * @Function: eichong4.0版本通用提示对话框
 * @author cm
 */
public class CustomTipAwaitPopuWindow extends BasePopupWindow implements android.view.View.OnClickListener{
	private Context mContext;
	private View view;
	private TextView tv_t_explain,tv_t_more,tv_custom_know,tv_cust_await;
	public static AwaitChange awaitchange = null;
	
	private String buttonchange;
	public CustomTipAwaitPopuWindow(Context context,String buttonchange) {
		super(context);
		this.mContext = context;
		this.buttonchange = buttonchange;
		LayoutInflater layoutInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = layoutInflater.inflate(R.layout.popup_custom_await, null);
		view.setAlpha(100);
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

		this.setAnimationStyle(R.style.AnimBottom);
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		this.setBackgroundDrawable(dw);
		this.setOutsideTouchable(false);
	}

	private void initView(View view) {
		tv_t_explain = (TextView) view.findViewById(R.id.t_explain);
		tv_t_more = (TextView) view.findViewById(R.id.t_more);
		tv_t_more.setOnClickListener(this);
		tv_custom_know = (TextView) view.findViewById(R.id.custom_know);
		tv_custom_know.setOnClickListener(this);
		tv_cust_await = (TextView) view.findViewById(R.id.cust_await);
		tv_cust_await.setOnClickListener(this);
		if (buttonchange.equals("知道")) {
			view.findViewById(R.id.v_await).setVisibility(View.GONE);
			tv_cust_await.setVisibility(View.GONE);	
			tv_t_more.setVisibility(View.GONE);
			tv_t_explain.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.t_more:
			tv_t_more.setVisibility(View.GONE);
			tv_t_explain.setVisibility(View.VISIBLE);
			break;
		case R.id.custom_know:
			if (!buttonchange.equals("知道")) {
				awaitchange.know();
			}
			dismiss();
			break;
		case R.id.cust_await:
			if (!buttonchange.equals("知道")) {
				awaitchange.await();
			}
			dismiss();
			break;
		}

	}

	@Override
	public void onSuccess(String sign, Bundle bundle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFaile(String sign, Bundle bundle) {
		// TODO Auto-generated method stub
		
	}
	
	public static void setCouponChangeSize(AwaitChange change){
		awaitchange = change;
	}
	public interface AwaitChange{
		public abstract void know();
		public abstract void await();
	}
}