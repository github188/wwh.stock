package com.bm.wanma.ui.fragment;

import com.bm.wanma.R;
import com.bm.wanma.alipay.Base64;
import com.bm.wanma.utils.LogUtil;
import com.bm.wanma.utils.PreferencesUtil;
import com.bm.wanma.utils.Tools;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public abstract class BaseFragment extends Fragment {
	public static CouponChangeSize CouponchangeSize = null;
	boolean isRunning=true;
	private ProgressDialog pd;
	private Toast mToast;
	private static String oldMsg;
	private static long oneTime = 0;
	private static long twoTime = 0;
	public static final int SHOW_PD = 225;
	public static final int THREAD = 226;
	public static final int HIDE_PD = 224;
	public boolean islockScreen;
	private String apiToken;
	private long timeStamp;
	public Context mContext;
	public String mPageName;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = getActivity();
		if (isNetConnection()) {
		} else {
			showToast("亲，网络不稳，请检查网络连接!");
		}
		// 注册事件
		getActivity().registerReceiver(myReceiver,
				new IntentFilter(Intent.ACTION_SCREEN_ON));
		getActivity().registerReceiver(myReceiver,
				new IntentFilter(Intent.ACTION_SCREEN_OFF));
		getActivity().registerReceiver(myReceiver,
				new IntentFilter(Intent.ACTION_USER_PRESENT));
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		isRunning=false;
		getActivity().unregisterReceiver(myReceiver);
	}

	/*
	 * @Override public void onDetach() { super.onDetach();
	 * 
	 * try { Field childFM =
	 * Fragment.class.getDeclaredField("mChildFragmentManager");
	 * childFM.setAccessible(true); childFM.set(this, null);
	 * 
	 * } catch (NoSuchFieldException e) { throw new RuntimeException(e); }catch
	 * (IllegalAccessException e) { throw new RuntimeException(e); }
	 * 
	 * 
	 * }
	 */

	/**
	 * Toast提示
	 */
	protected void showToast(String content) {
		if (content != null && content.length() > 0)
			if (mToast == null) {
				mToast = Toast.makeText(getActivity(), content,
						Toast.LENGTH_SHORT);
				mToast.show();
				oneTime = System.currentTimeMillis();
			} else {
				twoTime = System.currentTimeMillis();
				if (content.equals(oldMsg)) {
					if (twoTime - oneTime > Toast.LENGTH_SHORT) {
						mToast.show();
					}
				} else {
					oldMsg = content;
					mToast.setText(content);
					mToast.show();
				}
			}
		oneTime = twoTime;
	}

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

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

	/* 获取token */
	public String getAccessToken() {
		apiToken = PreferencesUtil.getStringPreferences(getActivity(),
				"apiToken");
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
		ConnectivityManager cwjManager = (ConnectivityManager) getActivity()
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
			pd = new ProgressDialog(getActivity());
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
		showPD(getString(R.string.request_data));
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

	private BroadcastReceiver myReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if (Intent.ACTION_SCREEN_ON.equals(intent.getAction())) {// 当按下电源键，屏幕亮起的时候
				// LogUtil.i("cm", "屏幕亮起");
				islockScreen = true;
			}
			if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())) {// 当按下电源键，屏幕变黑的时候
				islockScreen = true;
				// LogUtil.i("cm", "屏幕变黑");
			}
			if (Intent.ACTION_USER_PRESENT.equals(intent.getAction())) {// 当解除锁屏的时候 
				islockScreen = false;
				LogUtil.i("cm", "解除锁屏");
			}
		}
	};
	public void showErrorCode(int error){
		switch (error) {
		case 110:
			showToast(getResources().getString(R.string.tip_error_110));
			break;
		case 1002:
			showToast(getResources().getString(R.string.tip_error_1002));
			break;
		case 6000:
			showToast(getResources().getString(R.string.tip_error_6000));
			break;
		case 6001:
			showToast(getResources().getString(R.string.tip_error_6001));
			break;
		case 6100:
			showToast(getResources().getString(R.string.tip_error_6100));
			break;
		case 6101:
			showToast(getResources().getString(R.string.tip_error_6101));
			break;
		case 6102:
			showToast(getResources().getString(R.string.tip_error_6102));
			break;	
		case 6104:
			showToast(getResources().getString(R.string.tip_error_6104));
			break;
		case 6105:
			showToast(getResources().getString(R.string.tip_error_6105));
			break;
		case 6200:
			showToast(getResources().getString(R.string.tip_error_6200));
			break;
		case 6201:
			showToast(getResources().getString(R.string.tip_error_6201));
			break;
		case 6202:
			showToast(getResources().getString(R.string.tip_error_6202));
			break;
		case 6300:
			showToast(getResources().getString(R.string.tip_error_6300));
			break;
		case 6301:
			showToast(getResources().getString(R.string.tip_error_6301));
			break;
		case 6401:
			showToast(getResources().getString(R.string.tip_error_6401));
			break;
		case 6402:
			showToast(getResources().getString(R.string.tip_error_6402));
			break;
		case 6403:
			showToast(getResources().getString(R.string.tip_error_6403));
			break;
		case 6404:
			showToast(getResources().getString(R.string.tip_error_6404));
			break;
		case 6405:
			showToast(getResources().getString(R.string.tip_error_6405));
			break;
		case 6406:
			showToast(getResources().getString(R.string.tip_error_6406));
			break;
		case 6600:
			showToast(getResources().getString(R.string.tip_error_6600));
			break;	
		case 6601:
			showToast(getResources().getString(R.string.tip_error_6601));
			break;	
		case 6633:
			showToast(getResources().getString(R.string.tip_error_6633));
			break;	
		case 6700:
			showToast(getResources().getString(R.string.tip_error_6700));
			break;
		case 6701:
			showToast(getResources().getString(R.string.tip_error_6701));
			break;
		case 6702:
			showToast(getResources().getString(R.string.tip_error_6702));
			break;
		case 6703:
			showToast(getResources().getString(R.string.tip_error_6703));
			break;
		case 6704:
			showToast(getResources().getString(R.string.tip_error_6704));
			break;
		case 6705:
			showToast(getResources().getString(R.string.tip_error_6705));
			break; 
		case 6706:
			showToast(getResources().getString(R.string.tip_error_6706));
			break; 
		case 6800:
			showToast(getResources().getString(R.string.tip_error_6800));
			break;
		case 6801:
			showToast(getResources().getString(R.string.tip_error_6801));
			break;
		case 6802:
			showToast(getResources().getString(R.string.tip_error_6802));
			break;
		case 6803:
			showToast(getResources().getString(R.string.tip_error_6803));
			break;
		case 6804:
			showToast(getResources().getString(R.string.tip_error_6804));
			break;
		default:
			showToast("未知原因");
			break;
		}
		
	}
	
	public static void setCouponChangeSize(CouponChangeSize changeSize){
		CouponchangeSize = changeSize;
	}
	public interface CouponChangeSize{
		public abstract void couponused();
		public abstract void couponusable();
		public abstract void coupondeprecated();
	}
}
