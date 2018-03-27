package com.bm.wanma.popup;

import java.lang.reflect.Method;

import com.bm.wanma.R;
import com.bm.wanma.entity.MyDynamicListBean;
import com.bm.wanma.ui.activity.MyDynamicDetailActivity;
import com.bm.wanma.utils.PreferencesUtil;
import com.bm.wanma.utils.Tools;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.PopupWindow;
public class InterstitialPopupWindow extends BasePopupWindow implements
		OnClickListener {
	private Context mContext;
	private View view;
	private ImageView im_Interstitial, im_close;
	private MyDynamicListBean itemBean;
	private String tabclick;
	private boolean isadtago = false;
	public InterstitialPopupWindow(Context context) {
		super(context);
		this.mContext = context;
		LayoutInflater layoutInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = layoutInflater.inflate(R.layout.popup_interstitial, null);
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
		im_Interstitial = (ImageView) view.findViewById(R.id.im_Interstitial);
		im_close = (ImageView) view.findViewById(R.id.im_close);
		im_Interstitial.setOnClickListener(this);
		im_close.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.im_Interstitial:
			if(isadtago&&isNetConnection()){
				PreferencesUtil.setPreferences(mContext, tabclick, false);
				Intent Interstitial = new Intent();
				Interstitial.putExtra("releaseId", itemBean);
				Interstitial.setClass(mContext, MyDynamicDetailActivity.class);
				mContext.startActivity(Interstitial);
				dismiss();	
			}
			break;

		case R.id.im_close:
			PreferencesUtil.setPreferences(mContext, tabclick, false);
			dismiss();
			break;
		}

	}
	
	@Override
	public void onSuccess(String sign, Bundle bundle) {}

	@Override
	public void onFaile(String sign, Bundle bundle) {}

	public void setInitValue(String tab, String tabclick,MyDynamicListBean itemBean) {
		this.tabclick = tabclick;
		this.itemBean = itemBean;
		if(!Tools.isEmptyString(PreferencesUtil.getStringPreferences(mContext, tab+"adtgo"))
				&&PreferencesUtil.getStringPreferences(mContext, tab+"adtgo").equals("1")
				&& !Tools.isPicture(PreferencesUtil.getStringPreferences(mContext, tab+"AdId"))
				){
			isadtago = true;
		}else{
			isadtago = false;
		}
		im_Interstitial.setImageBitmap(BitmapFactory.decodeFile(Tools.advertisementpath + tab));
	}
	
}
