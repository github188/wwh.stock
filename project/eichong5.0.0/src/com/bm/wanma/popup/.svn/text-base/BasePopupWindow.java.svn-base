package com.bm.wanma.popup;

import com.bm.wanma.R;
import com.bm.wanma.alipay.Base64;
import com.bm.wanma.utils.PreferencesUtil;
import com.bm.wanma.utils.Tools;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.Toast;

/**
 * @author cm 基础pop，设置回调接口
 * 
 */
public abstract class BasePopupWindow extends PopupWindow {

	private ProgressDialog pd;
	public static final int SHOW_PD = 225;
	public static final int THREAD = 226;
	public static final int HIDE_PD = 224;
	private String apiToken;
	private long timeStamp;
	private Context mContext;

	protected Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case 0:
				onFaile(msg.obj.toString(), msg.getData());
				break;

			case 1:
				onSuccess(msg.obj.toString(), msg.getData());
				break;
			case SHOW_PD:
				showPD();
				break;
			case HIDE_PD:
				cancelPD();
				break;
			}

		}

	};

	public BasePopupWindow(Context context) {
		super(context);
		mContext = context;
	}

	/* 获取token */
	public String getAccessToken() {
		apiToken = PreferencesUtil.getStringPreferences(mContext, "apiToken");
		timeStamp = System.currentTimeMillis();
		String toToken = apiToken + timeStamp;
		String replaceToken = "";
		char[] chars = toToken.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			replaceToken += Tools.replace((byte) chars[i]);
		}
		replaceToken = Base64.encode(replaceToken.getBytes());
		return replaceToken;
	}

	/* 判断是否有网络 */
	protected boolean isNetConnection() {
		ConnectivityManager cwjManager = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cwjManager.getActiveNetworkInfo();
		if (info != null && info.isAvailable()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 显示等待窗
	 * 
	 * @param content
	 */
	protected void showPD(String content) {
		if (null == pd) {
			pd = new ProgressDialog(mContext);
		}
		if (null != content) {
			pd.setMessage(content);
			// 设置点击屏幕Dialog不消失
			pd.setCanceledOnTouchOutside(false);
			// 不能取消
			// pd.setCancelable(false);
		}

		if (!pd.isShowing()) {
			pd.show();
		}
	}
	/**
	 *  Toast提示
	 */
	protected void showToast(String content) {
		if (content != null && content.length() > 0)
			Toast.makeText(mContext, content, Toast.LENGTH_LONG).show();
	}

	protected void showPD() {
		showPD(mContext.getString(R.string.request_data));
	}

	/**
	 * 关闭等待窗
	 */
	protected void cancelPD() {
		if (null != pd && pd.isShowing()) {
			pd.dismiss();
		}
	}

	public abstract void onSuccess(String sign, Bundle bundle);

	public abstract void onFaile(String sign, Bundle bundle);

}
