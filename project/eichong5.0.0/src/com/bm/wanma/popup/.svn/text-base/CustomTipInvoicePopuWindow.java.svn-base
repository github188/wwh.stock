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
public class CustomTipInvoicePopuWindow extends PopupWindow implements android.view.View.OnClickListener{
	private Context mContext;
	private View view;
	private TextView tv_custom_know;
	public CustomTipInvoicePopuWindow(Context context) {
		super(context);
		this.mContext = context;
		LayoutInflater layoutInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = layoutInflater.inflate(R.layout.popup_custom_invoice, null);
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
		tv_custom_know = (TextView) view.findViewById(R.id.custom_know);
		tv_custom_know.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.custom_know:
			dismiss();
			break;
		}
	}


}