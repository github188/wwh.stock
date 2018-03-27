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
 * 详情扫码入口
 * @author lyh
 *
 */
public class ScanEntrancePopupWindow extends BasePopupWindow implements
		OnClickListener{
	private Context mContext;
	private View view;

	private RelativeLayout rl_scan_entrance;
	public ScanEntrancePopupWindow(Context context) {
		super(context);
		this.mContext = context;
		LayoutInflater layoutInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = layoutInflater.inflate(R.layout.popup_scan_entrance, null);
		
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
		this.setAnimationStyle(R.style.AnimBottom2);
		ColorDrawable dw = new ColorDrawable(Color.parseColor("#00000000"));
		this.setBackgroundDrawable(dw);
		this.setOutsideTouchable(false);
	             
	}

	private void initView(View view) {
		
		rl_scan_entrance= (RelativeLayout) view.findViewById(R.id.scan_entrance);
		rl_scan_entrance.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.scan_entrance:

			break;

		}

	}
	
	@Override
	public void onSuccess(String sign, Bundle bundle) {}

	@Override
	public void onFaile(String sign, Bundle bundle) {}
	

	
}
