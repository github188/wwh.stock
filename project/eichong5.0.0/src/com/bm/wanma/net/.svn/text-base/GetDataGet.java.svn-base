package com.bm.wanma.net;

import java.lang.reflect.Type;

import net.tsz.afinal.http.AjaxParams;
import android.content.Context;
import android.os.Handler;

import com.bm.wanma.alipay.Base64;
import com.bm.wanma.entity.BaseBean;
import com.bm.wanma.utils.PreferencesUtil;
import com.bm.wanma.utils.Tools;
import com.google.gson.reflect.TypeToken;

public class GetDataGet extends NetGet {

	public static GetDataGet instance;
	public static String replaceToken = "";

	public Type defaulType = new TypeToken<BaseBean<?>>() {
	}.getType();

	public static GetDataGet getInstance(Context mc) {
		String apiToken = PreferencesUtil.getStringPreferences(mc, "apiToken");
		Long timeStamp = System.currentTimeMillis();
		String toToken = apiToken + timeStamp;
		replaceToken = "";
		char[] chars = toToken.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			replaceToken += Tools.replace((byte) chars[i]);
		}
		replaceToken = Base64.encode(replaceToken.getBytes());
		if (instance == null) {
			instance = new GetDataGet();
		}
		return instance;
	}
	/**
	 * 获取api token
	 * @author cm
	 * @param handler        
	 */
	public void getApiToken(Handler handler) {
		AjaxParams params = new AjaxParams();
		getData(handler, Protocol.GET_API_TOKEN, params, defaulType);
	}
	
	/**
	 * 获得短信验证码
	 * 
	 * @param handler
	 * @param mobileNumber
	 */
	public void getCode(Handler handler, String mobileNumber) {
		AjaxParams ajaxParams = new AjaxParams();
		ajaxParams.put("mobileNumber", mobileNumber);
		ajaxParams.put("t", replaceToken);
		getData(handler, Protocol.GET_AUTH_CODE, ajaxParams, defaulType);
	}

	

}
