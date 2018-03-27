package com.bm.wanma.utils;

import java.io.File;
import java.security.Security;
import java.util.ArrayList;

import android.app.Activity;
import android.app.Application;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;

import com.amap.api.navi.AMapNavi;
import com.bm.wanma.R;
import com.bm.wanma.entity.LoginBean;
import com.bm.wanma.entity.SelectValueBean;
import com.bm.wanma.model.net.ImageLoaderFactory;
import com.bm.wanma.net.Protocol;
import com.bm.wanma.ui.navigation.TTSController;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.common.QueuedWork;
public class ProjectApplication extends Application {
	public static String PREFERENCE_IS_LOGIN = "PREFERENCE_IS_LOGIN";
	public static String PREFERENCE_USER_DATA = "PREFERENCE_USER_DATA";
	public static String PREFERENCE_IS_SHOW_GUIDE = "PREFERENCE_IS_SHOW_GUIDE";// 是否显示引导页
	public static String PREFERENCE_IS_SHOW_BUTTON = "PREFERENCE_IS_SHOW_BUTTON";// 是否显示引导页
	public static boolean isSuccess=false;
	public Handler handler;
	public static int DISPLAY_WID;// 屏幕宽 width
	public static int DISPLAY_HEL; // 屏幕高 height
	public static float DISPLAY_DEN; // 屏幕密度 density
	public static float bannerRate = (float) (6 * 1.0 / 4);// banner宽高比例
	private LoginBean loginBean;
	private SelectValueBean selectValueBean;
	//private VersionInfoBean versionBean;
	public static String CITY;
	ArrayList<Activity> activities = new ArrayList<Activity>();
	ArrayList<Activity> exitActivities = new ArrayList<Activity>();
	private static ProjectApplication instance;
	/** 图片下载工具类 */
	public static ImageLoader IMAGE_LOADER;
	public static DisplayImageOptions IMAGE_OPTIONS;


	@Override
	public void onCreate() {
		super.onCreate();
		
		Config.DEBUG = true;
        QueuedWork.isUseThreadPool = false;
        UMShareAPI.get(this);
        PlatformConfig.setWeixin("wx6f19e4001b2c467a", "fad1e3cfaa3b823bde4b84afcc332944");
        //豆瓣RENREN平台目前只能在服务器端配置
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad","http://sns.whalecloud.com");
        PlatformConfig.setYixin("yxc0614e80c9304c11b0391514d09f13bf");
        PlatformConfig.setQQZone("1104471069", "ug6Dxu0oXMv3UFrz");
        PlatformConfig.setTwitter("3aIN7fuF685MuZ7jtXkQxalyi", "MK6FEYG63eWcpDFgRYw4w9puJhzDl0tyuqWjZ3M7XJuuG7mMbO");
        PlatformConfig.setAlipay("2015111700822536");
        PlatformConfig.setLaiwang("laiwangd497e70d4", "d497e70d4c3e4efeab1381476bac4c5e");
        PlatformConfig.setPinterest("1439206");
        PlatformConfig.setKakao("e4f60e065048eb031e235c806b31c70f");
        PlatformConfig.setDing("dingoalmlnohc0wggfedpk");
        PlatformConfig.setVKontakte("5764965","5My6SNliAaLxEm3Lyd9J");
        PlatformConfig.setDropbox("oz8v5apet3arcdy","h7p2pjbzkkxt02a");
        PlatformConfig.setYnote("9c82bf470cba7bd2f1819b0ee26f86c6ce670e9b");
		
		
		Security.setProperty("networkaddress.cache.ttl", String.valueOf(0));  
		Security.setProperty("networkaddress.cache.negative.ttl", String.valueOf(0));
		handler = new Handler();
		instance = this;
		initUtils();
		ImageLoaderFactory.init(this);
		JPushInterface.init(this); //初始化 JPush 
		String isShowNotify = PreferencesUtil.getStringPreferences(this,"shownotify");
		if(isShowNotify.equals("noshow")){
			BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(this);
			builder.notificationDefaults = 0;
			JPushInterface.setPushNotificationBuilder(1, builder);
		}	  
		//NotificationManager mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE); 
		
		JPushInterface.setDebugMode(true); //设置开启日志,发布时请关闭日志     
		initImageLoader();
		TTSController ttsManager = TTSController.getInstance(this);// 初始化语音模块
		ttsManager.init();
		AMapNavi.getInstance(this).setAMapNaviListener(ttsManager);// 设置语音模块播报
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}
	
	public void addActivity(Activity activity) {
		activities.add(activity);
	}
	public void removeActivity(Activity activity) {
		if(activities.size()>0){
			activities.remove(activity);
		}
	}
	public void addExitActivities(Activity activity) {
		exitActivities.add(activity);
	}
	/**
	 * 退出功能
	 * @param context
	 */

	public void exitForce(int code) {
		for (Activity activity : exitActivities) {
			activity.finish();
		}
	}

	/**
	 * 注销功能
	 * @param context
	 */

	public void exit(int code) {
		for (Activity activity : activities) {
			activity.finish();
		}
		clearUserInfo();
	}

	/**
	 * 清空登录信息
	 * @param context
	 */
	private void clearUserInfo(){
		PreferencesUtil.setPreferences(getApplicationContext(), "pkUserinfo","");
		PreferencesUtil.setPreferences(getApplicationContext(), "usinPhone", "");
		PreferencesUtil.setPreferences(getApplicationContext(), "usinFacticityname","");
		PreferencesUtil.setPreferences(getApplicationContext(), "usinSex","");
		PreferencesUtil.setPreferences(getApplicationContext(), "usinAccountbalance","");
		PreferencesUtil.setPreferences(getApplicationContext(), "usinBirthdate","");
		PreferencesUtil.setPreferences(getApplicationContext(), "usinUserstatus","");
		PreferencesUtil.setPreferences(getApplicationContext(), "usinIccode","");
		PreferencesUtil.setPreferences(getApplicationContext(), "usinHeadimage","");
		PreferencesUtil.setPreferences(getApplicationContext(), "nickName","");
		
	}
	
	/**
	 * 图片下载初始化
	 * @param context
	 */
	private void initImageLoader() {
		File cacheDir =StorageUtils.getOwnCacheDirectory(this, "eichong/imageloader/Cache"); 
		ImageLoaderConfiguration imageLoaderConfiguration = new ImageLoaderConfiguration.Builder(this).threadPriority(Thread.NORM_PRIORITY)
				.denyCacheImageMultipleSizesInMemory().discCacheFileNameGenerator(new Md5FileNameGenerator())
				.discCacheFileCount(50) //缓存的文件数量  
				.discCache(new UnlimitedDiscCache(cacheDir))//自定义缓存路径   
				.tasksProcessingOrder(QueueProcessingType.LIFO).writeDebugLogs().build();
		IMAGE_LOADER = ImageLoader.getInstance();
		IMAGE_LOADER.init(imageLoaderConfiguration);
	}

	/**
	 * 下载图片
	 * @param uri
	 * @param imageView
	 */
	public static void loadPhotoImage(String uri, ImageView imageView) {
		if (null == IMAGE_OPTIONS) {
			IMAGE_OPTIONS = new DisplayImageOptions.Builder().cacheInMemory(false).showImageOnFail(R.drawable.imguser)
					.showImageOnLoading(R.drawable.imguser).cacheOnDisc(true).build();
		}
		if (uri != null) {
			if (!Tools.judgeString("", uri) && !uri.endsWith("null")) {
				if (uri.startsWith("http")) {
					IMAGE_LOADER.displayImage(uri, imageView, IMAGE_OPTIONS);
				} else {
					IMAGE_LOADER.displayImage(Protocol.SERVER_ADDRESS + "/" + uri, imageView);
				}
			} else {
				imageView.setImageResource(R.drawable.imguser);
			}
		}
	}
   /*图像操作是否参与缓存以及图像效果的配置操作 */
	/*
	DisplayImageOptions options = new DisplayImageOptions.Builder()  
    .showImageOnLoading(R.drawable.ic_stub)            //加载图片时的图片  
    .showImageForEmptyUri(R.drawable.ic_empty)         //没有图片资源时的默认图片  
    .showImageOnFail(R.drawable.ic_error)              //加载失败时的图片  
    .cacheInMemory(true)                               //启用内存缓存  
    .cacheOnDisk(true)                                 //启用外存缓存  
    .considerExifParams(true)                          //启用EXIF和JPEG图像格式  
    .displayer(new RoundedBitmapDisplayer(20))         //设置显示风格这里是圆角矩形  
    .build();  */
	
	private void initUtils() {
		ToastUtil.init(this);
		initParam();
	}

	private void initParam() {
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		DISPLAY_WID = metrics.widthPixels;
		DISPLAY_HEL = metrics.heightPixels;
		DISPLAY_DEN = metrics.density;
	}

	public static ProjectApplication getInstance() {
		return instance;
	}

	public static void setInstance(ProjectApplication instance) {
		ProjectApplication.instance = instance;
	}
	
	public SelectValueBean getSelectValueBean() {
		return selectValueBean;
	}

	public void setSelectValueBean(SelectValueBean selectValueBean) {
		this.selectValueBean = selectValueBean;

	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;

	}
	


	// 获取app登录状态
	public static boolean getLoginType() {
		return PreferencesUtil.getBooleanPreferences(instance, PREFERENCE_IS_LOGIN, false);
	}

	// 设置app登录状态
	public static void setLoginType(boolean isLogin) {
		PreferencesUtil.setPreferences(instance, PREFERENCE_IS_LOGIN, false);
	}

	// 获取app是否出现引导
	public static boolean getGuideType() {
		return PreferencesUtil.getBooleanPreferences(instance, PREFERENCE_IS_SHOW_GUIDE, false);
	}

	// 设置app是否出现引导
	public static void setGuideType(boolean isLogin) {
		PreferencesUtil.setPreferences(instance, PREFERENCE_IS_SHOW_GUIDE, isLogin);
	}
	
	
	// 获取app是否出现引导
	public static boolean getButtonType() {
		return PreferencesUtil.getBooleanPreferences(instance, PREFERENCE_IS_SHOW_BUTTON, false);
	}
	
	// 设置app是否出现引导
	public static void setButtonType(boolean isLogin) {
		PreferencesUtil.setPreferences(instance, PREFERENCE_IS_SHOW_BUTTON, isLogin);
	}
}
