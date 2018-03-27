package com.bm.wanma.model.net;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

import com.bm.wanma.utils.LogUtil;
import com.bm.wanma.utils.ToastUtil;
import com.bm.wanma.widget.LoadingDialog;

import net.tsz.afinal.http.AjaxCallBack;


import java.lang.ref.WeakReference;

/**
 *  Afinal请求网络回调类
 */
public class ProjectAjaxCallBack extends AjaxCallBack<Object> {
	LoadingDialog loadingDialog;
	private String mText;
	private WeakReference<Context> mContext;
	private boolean mNeedDialog;
	private boolean mIsDispaly = true;//错误提示是否提示,默认提示
	private boolean mIsFinishThisActivity = false;//请求失败的时候是否关闭当前的activity；默认false
	private boolean isLoading = true;//是否正在加载中


	public ProjectAjaxCallBack(String text, Context context, boolean needDialog) {
		mText = text;
		mContext = new WeakReference<Context>(context);
		mNeedDialog = needDialog;
	}

	public ProjectAjaxCallBack(String text, Context context, boolean needDialog, boolean isDispaly) {
		mText = text;
		mContext = new WeakReference<Context>(context);
		mNeedDialog = needDialog;
		mIsDispaly = isDispaly;
	}

	public ProjectAjaxCallBack(String text, Context context, boolean needDialog, boolean isDispaly, boolean isFinishThisActivity) {
		mText = text;
		mContext = new WeakReference<Context>(context);
		mNeedDialog = needDialog;
		mIsDispaly = isDispaly;
		mIsFinishThisActivity = isFinishThisActivity;
	}

	@Override
	public void onFailure(Throwable t, int errorNo, String strMsg) {
		super.onFailure(t, errorNo, strMsg);
		this.onFinish();
		LogUtil.d("请求失败："+strMsg);
		if (mIsDispaly) {
			//ToastUtil.TshowToast("请求失败，请检查网络后重试");
		}
	}

	@Override
	public void onSuccess(Object o) {
		super.onSuccess(o);
		this.onFinish();
		LogUtil.d(o.toString());
	}

	/**
	 *  无论成功或者失败都会调用的方法
	 */
	protected void onFinish() {
		if (mNeedDialog) {
			Context context = mContext.get();
			//避免这个异常：java.lang.IllegalArgumentException: View not attached to window manager
			if (context != null && loadingDialog != null && context instanceof Activity && !((Activity)context).isFinishing()) {
				loadingDialog.cancel();
			}
		}
		isLoading = false;
	}

	@Override
	public void onStart() {
		super.onStart();
		isLoading = true;
		if (mNeedDialog) {
			loadingDialog = new LoadingDialog(mContext.get());
			loadingDialog.setText(TextUtils.isEmpty(mText) ? "加载中" : mText);
			Context context = mContext.get();
			//android.view.WindowManager$BadTokenException: Unable to add window -- token android.os.BinderProxy@4171e400 is not valid; is your activity running?
			if (context != null && loadingDialog != null && context instanceof Activity && !((Activity)context).isFinishing()) {
				loadingDialog.show();
				getDialog();
			}
		}
	}

	/**
	 * 获取当前Dialog,方便子类在dialog消失的时候进行处理
	 * eg:有些页面要求在没有加载成功的时候，按返回键需要把当前页面关闭
	 * @return
	 */
	public Dialog getDialog() {
		if (mIsFinishThisActivity) {
			loadingDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					try {
						Activity context = (Activity) mContext.get();
						if (isLoading) {//还没有请求成功;并且正在加载
							context.finish();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
		return loadingDialog;
	}

}
