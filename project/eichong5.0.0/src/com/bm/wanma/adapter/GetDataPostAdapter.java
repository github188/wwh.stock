package com.bm.wanma.adapter;

import com.bm.wanma.R;
import com.bm.wanma.alipay.Base64;
import com.bm.wanma.utils.LogUtil;
import com.bm.wanma.utils.PreferencesUtil;
import com.bm.wanma.utils.Tools;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

public abstract class GetDataPostAdapter extends BaseAdapter {
	
	private ProgressDialog pd;
	public static final int SHOW_PD = 1225;
	public static final int THREAD = 1226;
	public static final int HIDE_PD = 1224;
	private Context mcontext;
	private String apiToken;
	private long timeStamp;
	
	public GetDataPostAdapter(Context context) {
		this.mcontext = context;
	}
	
	/* 判断是否有网络 */
	public boolean isNetConnection() {
		ConnectivityManager cwjManager = (ConnectivityManager) mcontext.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cwjManager.getActiveNetworkInfo();
		if (info != null && info.isAvailable()) {
			return true;
		} else {
			return false;
		}
	} 
	
	public String getDeviceId(){
		TelephonyManager tm = (TelephonyManager)mcontext.getSystemService(Context.TELEPHONY_SERVICE);
		String deviceId = tm.getDeviceId(); 
		deviceId = Tools.encoderByMd5(deviceId);
   		char[] chars = deviceId.toCharArray();
   		String encodeID = "";
   		for (int i = 0; i < chars.length; i++) {
   			encodeID += Tools.replace((byte) chars[i]);
   		}
   		encodeID = Base64.encode(encodeID.getBytes()); 
   		return encodeID;
   	}

	@Override
	public int getCount() {
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		return arg1;
	}

	public abstract void onSuccess(String sign, Bundle bundle);

	public abstract void onFaile(String sign, Bundle bundle);
	
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

	/**
	 *  Toast提示
	 */
	protected void showToast(String content) {
		if (content != null && content.length() > 0)
			Toast.makeText(mcontext, content, Toast.LENGTH_LONG).show();
	}
	
	/**
	 * 显示等待窗
	 * 
	 * @param content
	 */
	protected void showPD(String content) {
		if (null == pd) {
			pd = new ProgressDialog(mcontext);
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

	protected void showPD() {
		showPD(mcontext.getString(R.string.request_data));
	}

	/**
	 * 关闭等待窗
	 */
	protected void cancelPD() {
		if (null != pd && pd.isShowing()) {
			pd.dismiss();
		}
	}
	
	/* 获取token */
	public String getAccessToken(){
		apiToken = PreferencesUtil.getStringPreferences(mcontext, "apiToken");
		timeStamp = System.currentTimeMillis();
		String toToken = apiToken + timeStamp ;
		LogUtil.i("cm_origin", toToken);
		String replaceToken = "";
		char[] chars = toToken.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			replaceToken += Tools.replace((byte) chars[i]);
		}
		LogUtil.i("cm_replace", replaceToken);
		replaceToken = Base64.encode(replaceToken.getBytes());
		LogUtil.i("cm_replace_Base64", replaceToken);
		return replaceToken;
	}
	
	
}
