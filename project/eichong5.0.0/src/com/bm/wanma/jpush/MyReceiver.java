package com.bm.wanma.jpush;

import net.tsz.afinal.FinalDb;

import org.json.JSONException;
import org.json.JSONObject;
import com.bm.wanma.broadcast.BroadcastUtil;
import com.bm.wanma.entity.OrderStatusBean;
import com.bm.wanma.entity.SystemNoticeBean;
import com.bm.wanma.net.Protocol;
import com.bm.wanma.ui.activity.ForceOffline;
import com.bm.wanma.utils.LogUtil;
import com.bm.wanma.utils.PreferencesUtil;
import com.bm.wanma.utils.Tools;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则： 1) 默认用户会打开主界面 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
	private static final String TAG = "cm_Jpush";
	private Context mcontext;
	private String pkuserId;
	private FinalDb finalDb;
	private static SystemNoticeListener noticeListener;

/*	private void notifyUser(Context context) {
		soundManager = new SoundManager(context);
		vibrator = (Vibrator) context
				.getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(200);
		soundManager.addSound("notify", R.raw.shake_match);
		soundManager.playSound("notify");
	}*/

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		mcontext = context;
		if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
			String regId = bundle
					.getString(JPushInterface.EXTRA_REGISTRATION_ID);
			// 保存极光推送id
			PreferencesUtil.setPreferences(context, "jpushRegistrationid",
					regId);
			LogUtil.i(TAG, "regId" + regId);
		} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent
				.getAction())) {
			/*
			 * 根据type判断类型
			 * 1、充电结束；2、余额不足；3、充电开始推送；4、消费记录；5、预约完成；6、取消预约；7、后台推送
			 * 8、不同设备登录；9、系统通知；10、新增优惠券；11、临时结算；12、订单信息变更、13、系统公告
			 * （11、12、13）extra里包含的字段：msg-消息内容，title-消息标题，tm-消息时间，orderid-订单主键
			 */
			LogUtil.i(TAG,"[MyReceiver] 接收到推送下来的自定义消息cm: "
							+ bundle.getString(JPushInterface.EXTRA_MESSAGE));
			String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
			JSONObject jsonObject;
			pkuserId = PreferencesUtil.getStringPreferences(mcontext, "pkUserinfo");
			try {
				jsonObject = new JSONObject(extra);
				String type = jsonObject.getString("type");
				LogUtil.i(TAG, "自定义消息type:" + type);
				if ("1".equals(type)) {
					LogUtil.i(TAG, "充电结束");
					Intent chargefinishIn = new Intent();
					chargefinishIn.setAction(BroadcastUtil.BROADCAST_Charge_CANCLE);
					mcontext.sendBroadcast(chargefinishIn);
				}else if("2".equals(type)){
					LogUtil.i(TAG, "余额不足");
				}else if("3".equals(type)){
					LogUtil.i(TAG, "充电开始推送");
				}else if("4".equals(type)){
					LogUtil.i(TAG, "消费记录");
				}else if("5".equals(type)){
					LogUtil.i(TAG, "预约完成");
					Intent bespokefinishIn = new Intent();
					bespokefinishIn.setAction(BroadcastUtil.BROADCAST_Bespoke_Finish);
					mcontext.sendBroadcast(bespokefinishIn);
				}else if("6".equals(type)){
					LogUtil.i(TAG, "取消预约");
					Intent bespokefinishIn = new Intent();
					bespokefinishIn.setAction(BroadcastUtil.BROADCAST_Bespoke_CANCLE);
					mcontext.sendBroadcast(bespokefinishIn);
				}else if("7".equals(type)){
					LogUtil.i(TAG, "后台推送");
				}else if("8".equals(type) && !Tools.isEmptyString(pkuserId)){
					LogUtil.i(TAG, "不同设备登录");
					Intent forceofflineIn = new Intent(context,
							ForceOffline.class);
					forceofflineIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(forceofflineIn);
					
				}else if("9".equals(type)){
					LogUtil.i(TAG, "系统通知");
				}else if("10".equals(type)){
					LogUtil.i(TAG, "新增优惠券");
					PreferencesUtil.setPreferences(mcontext, "isCoupon", true);
					if(noticeListener != null){
						noticeListener.couponNotice();
					}
				}else if("11".equals(type) || "12".equals(type) ){
					handleOrderStatus(jsonObject);
					if(noticeListener != null){
						noticeListener.orderStatusChange();
					}
				}else if("13".equals(type)){
					handleSystemNotice(jsonObject);
					if(noticeListener != null){
						noticeListener.systemNotice();
					}
				}else if("99".equals(type)){
					Intent chargefinishIn = new Intent();
					chargefinishIn.setAction(BroadcastUtil.BROADCAST_Charge_CHANGE);
					mcontext.sendBroadcast(chargefinishIn);
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}

		} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent
				.getAction())) {
			LogUtil.i(TAG, "[MyReceiver] 接收到推送下来的通知");
			// bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
			// LogUtil.i(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
		} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent
				.getAction())) {
			LogUtil.i(TAG, "[MyReceiver] 用户点击打开了通知");
			String type = bundle.getString(JPushInterface.EXTRA_EXTRA);
			LogUtil.i(TAG, "type:" + type);
			/*try {
				JSONObject jsonObject = new JSONObject(type);
				String str = jsonObject.getString("type");
				// 根据type跳转不同界面 消息类型 1 充电结束推送 2 余额不足推送 3充电开始推送 4消费记录 5预约完成 6取消预约
				// 类型为1时，进入 充电订单列表界面；3.充电展示界面 5和6.预约列表 7.推送消息通知8.强制下线
				if (str.equals("1")) {
				} else if (str.equals("3")) {
					Intent i = new Intent(context,
							ChargingDisplayActivity.class);
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(i);
				} else if (str.equals("5")) {
					Intent i = new Intent(context, MyBespokeListActivity.class);
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(i);
				} else if (str.equals("6")) {
					Intent i = new Intent(context, MyBespokeListActivity.class);
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(i);
				} else if (str.equals("7")) {
					Intent i = new Intent(context, MyNewsActivity.class);
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(i);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
*/
			
		} else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent
				.getAction())) {
			LogUtil.i(
					TAG,
					"[MyReceiver] 用户收到到RICH PUSH CALLBACK: "
							+ bundle.getString(JPushInterface.EXTRA_EXTRA));
			// 在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity，
			// 打开一个网页等..

		} else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent
				.getAction())) {
			boolean connected = intent.getBooleanExtra(
					JPushInterface.EXTRA_CONNECTION_CHANGE, false);
			LogUtil.i(TAG, "[MyReceiver]" + intent.getAction()
					+ " connected state change to " + connected);
		} else {
			LogUtil.i(TAG,
					"[MyReceiver] Unhandled intent - " + intent.getAction());
		}
	}

	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			} else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
			} else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}
		return sb.toString();
	}
	
	
	private void handleOrderStatus(JSONObject jsonObject) throws JSONException {
		//String time = jsonObject.getString("tm");
		long time = jsonObject.getLong("tm");
		String title = jsonObject.getString("title");
		String msg = jsonObject.getString("msg");
		String orderid = jsonObject.getString("orderid");
		LogUtil.i(TAG, "自定义消息time:" + time);
		LogUtil.i(TAG, "自定义消息title:" + title);
		LogUtil.i(TAG, "自定义消息msg:" + msg);
		LogUtil.i(TAG, "自定义消息orderid:" + orderid);
		PreferencesUtil.setPreferences(mcontext, "isShowOrderStatus",true);
		if(finalDb == null){
			finalDb = FinalDb.create(mcontext,Protocol.DATABASE_NAME,true,Protocol.dbNumer,null);
		}
		OrderStatusBean bean = new OrderStatusBean();
		bean.setTitle(title);
		bean.setContent(msg);
		bean.setTime(time);
		bean.setOrderid(orderid);
		finalDb.save(bean);
		
	}
	
	private void handleSystemNotice(JSONObject jsonObject) throws JSONException {
		//String time = jsonObject.getString("tm");
		long time = jsonObject.getLong("tm");
		String title = jsonObject.getString("title");
		String msg = jsonObject.getString("msg");
		LogUtil.i(TAG, "自定义消息time:" + time);
		LogUtil.i(TAG, "自定义消息title:" + title);
		LogUtil.i(TAG, "自定义消息msg:" + msg);
		PreferencesUtil.setPreferences(mcontext, "isShowSystemNotice", true);
		if(finalDb == null){
			finalDb = FinalDb.create(mcontext,Protocol.DATABASE_NAME,true,Protocol.dbNumer,null);
		}
		SystemNoticeBean bean = new SystemNoticeBean();
		bean.setTitle(title);
		bean.setContent(msg);
		bean.setTime(time);
		finalDb.save(bean);
	}
	
	public interface SystemNoticeListener {
		 void systemNotice();
		 void orderStatusChange();
		 void couponNotice();
	} 
	
	public static void setOnSystemNoticeListener(SystemNoticeListener l){
			noticeListener = l;
	}

	// 注销用户信息
	private void logoutInfo() {
		PreferencesUtil.setPreferences(mcontext, "pkUserinfo", "");
		PreferencesUtil.setPreferences(mcontext, "usinPhone", "");
		PreferencesUtil.setPreferences(mcontext, "usinFacticityname", "");
		PreferencesUtil.setPreferences(mcontext, "usinSex", "");
		PreferencesUtil.setPreferences(mcontext, "usinAccountbalance", "");
		PreferencesUtil.setPreferences(mcontext, "usinBirthdate", "");
		PreferencesUtil.setPreferences(mcontext, "usinUserstatus", "");
		PreferencesUtil.setPreferences(mcontext, "usinHeadimage", "");
		PreferencesUtil.setPreferences(mcontext, "nickName", "");

	}

}
