package com.bm.wanma.ui.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;

import com.bm.wanma.R;
import com.bm.wanma.alipay.Base64;
import com.bm.wanma.entity.LoginBean;
import com.bm.wanma.utils.LogUtil;
import com.bm.wanma.utils.PreferencesUtil;
import com.bm.wanma.utils.ProjectApplication;
import com.bm.wanma.utils.Tools;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.MobclickAgent.EScenarioType;

public abstract class BaseActivity extends Activity {

	private ProgressDialog pd;
	private Toast mToast;
	private static String oldMsg;
	private static long oneTime = 0;
	private static long twoTime = 0;
	public static final int SHOW_PD = 225;
	public static final int THREAD = 226;
	public static final int HIDE_PD = 224;
	/* 经度，纬度 */
	public static String lon, lat;
	public static boolean myoder = false;
	/* 反编码 */
	Geocoder geocoder;
	private String apiToken;
	private long timeStamp;
	public boolean islockScreen;
	public boolean isBackgroud;
	public static boolean isConceal = false;
	public Context mContext;
	public String mPageName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		ProjectApplication.getInstance().addActivity(this);
		ProjectApplication.getInstance().addExitActivities(this);
		// 注册事件  
        registerReceiver(myReceiver, new IntentFilter(Intent.ACTION_SCREEN_ON));  
        registerReceiver(myReceiver, new IntentFilter(Intent.ACTION_SCREEN_OFF));  
        registerReceiver(myReceiver, new IntentFilter(Intent.ACTION_USER_PRESENT)); 
       // islockScreen = true; contentno==com.bm.wanma.ui.activity.LoginActivity@77bf080 .LoginActivit
        if (isNetConnection()) {
        	getData();
		} else {
			showToast("亲，网络不稳，请检查网络连接!");
		}
        if(this.toString().contains("ForceOffline")||this.toString().contains("NaviCustomActivity")
        		||this.toString().contains("NaviRouteActivity")){
        	isConceal = false;
		}
//        if(!this.toString().contains("HomeActivity")){        	
        	MobclickAgent.setDebugMode(true);
        	MobclickAgent.openActivityDurationTrack(false);
        	MobclickAgent.setScenarioType(this, EScenarioType.E_UM_NORMAL);
//        }
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)&&isConceal) {
			if(this.toString().contains("LoginActivity")){
				setTranslucentStatus(true);
				SystemBarTintManager tintManager = new SystemBarTintManager(this);
				tintManager.setStatusBarTintEnabled(true);
				tintManager.setStatusBarTintResource(R.color.common_light_white);
			}else if(this.toString().contains("CaptureActivity")){
				setTranslucentStatus(true);
			}else if(this.toString().contains("StationStiltDetailActivity") ){
				setTranslucentStatus(true);
			}else {
				setTranslucentStatus(true);
				SystemBarTintManager tintManager = new SystemBarTintManager(this);
				tintManager.setStatusBarTintEnabled(true);
				tintManager.setStatusBarTintResource(R.color.common_orange);
			}
			
        }
	}
	
	@TargetApi(19) 
	private void setTranslucentStatus(boolean on) {
		Window win = getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}
	
	public boolean isRunningForeground (Context context)  
    {  
        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);  
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;  
        String currentPackageName = cn.getPackageName();  
        if(!TextUtils.isEmpty(currentPackageName) && currentPackageName.equals(getPackageName()))  
        {  
            return true ;  
        }  
       
        return false ;  
    }  
	
	
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
	}

	public String getDeviceId(){
		TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
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
	    protected void onUserLeaveHint() { //当用户按Home键等操作使程序进入后台时调用  
	        super.onUserLeaveHint();  
	        LogUtil.i("cm_leave", "后台");
	        isBackgroud = false;
	    }  
	 @Override  
	    protected void onResume() { //当用户使程序恢复为前台显示时执行onResume()方法 
	        super.onResume();  
			JPushInterface.onResume(this);
			if(islockScreen){
				LogUtil.i("cm_lock", "前台");
			}else {
				LogUtil.i("cm_leave", "前台");
				isBackgroud = true;
			}
			
	    }  
	/* 判断是否有网络 */
	public boolean isNetConnection() {
		ConnectivityManager cwjManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cwjManager.getActiveNetworkInfo();
		if (info != null && info.isAvailable()) {
			return true;
		} else {
			return false;
		}
	} 
	/* 判断是否有wifi网络 */
	public boolean isNotWifiConnection(){
		ConnectivityManager cwjManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cwjManager.getActiveNetworkInfo();
		if(info !=null && info.isAvailable() && info.getType() ==  ConnectivityManager.TYPE_MOBILE){
			return true;
		}else {
			return false;
		}
	}
	
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
		case 6203:
			showToast(getResources().getString(R.string.tip_error_6203));
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
	
	/* 获取token */
	public String getAccessToken(){
		apiToken = PreferencesUtil.getStringPreferences(getActivity(), "apiToken");
		timeStamp = System.currentTimeMillis();
		String toToken = apiToken + timeStamp ;
		String replaceToken = "";
		char[] chars = toToken.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			replaceToken += Tools.replace((byte) chars[i]);
		}
		replaceToken = Base64.encode(replaceToken.getBytes());
		return replaceToken;
	}
	
	/**
	 * 获取从上个页面传递过来的数据，或者需要从本地读取的数据，如用户数据。
	 */
	protected abstract void getData();
	
	
	
	public abstract void onSuccess(String sign, Bundle bundle);

	public abstract void onFaile(String sign, Bundle bundle);

	
	public LoginBean getLoginBean() {
		return ProjectApplication.getInstance().getLoginBean();
	}
	public void setLoginBean(LoginBean loginBean) {
		ProjectApplication.getInstance().setLoginBean(loginBean);
	}
	
	@SuppressLint("HandlerLeak")
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
			default:
				break;
			}

		}

	};
	
/*	public static class MyHandle extends Handler{

		private WeakReference<Activity> reference;
		
		public MyHandle(Activity activity){
			reference = new WeakReference<Activity>(activity);
		}
		
		@Override
		public void handleMessage(Message msg) {
			
			if(reference.get() != null){
				
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
				default:
					break;
				}
				
			}
			
		}
	}*/
	
	

	/**
	 *  Toast提示
	 */
	protected void showToast(String content) {
		if (content != null && content.length() > 0)
			if(mToast == null){
				mToast = Toast.makeText(this, content, Toast.LENGTH_LONG);
				mToast.show();
				oneTime = System.currentTimeMillis();
			}else {
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
	
	/**
	 * 显示等待窗
	 * 
	 * @param content
	 */
	protected void showPD(String content) {
		if (null == pd) {
			pd = new ProgressDialog(this);
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
	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	/**
	 * 隐藏输入法面板
	 */

	public void hideKeyboard(View view) {
		InputMethodManager imm = ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE));
		if (imm != null && getCurrentFocus() != null) {
			imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}


	protected void onPause() {
		super.onPause();
		JPushInterface.onPause(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(myReceiver);
	}


	public BaseActivity getActivity() {
		return this;
	}
	private BroadcastReceiver myReceiver = new BroadcastReceiver() {  
        
        @Override  
        public void onReceive(Context context, Intent intent) {  
            // TODO Auto-generated method stub  
            if (Intent.ACTION_SCREEN_ON.equals(intent.getAction()) ) {//当按下电源键，屏幕亮起的时候  
            	//LogUtil.i("cm", "屏幕亮起");
            	islockScreen = true;  
            }  
            if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction()) ) {//当按下电源键，屏幕变黑的时候  
               islockScreen = true;  
              // LogUtil.i("cm", "屏幕变黑");
            }  
            if (Intent.ACTION_USER_PRESENT.equals(intent.getAction()) ) {//当解除锁屏的时候  
                islockScreen = false;  
                LogUtil.i("cm", "解除锁屏");
            }  
        }  
    };  

}
