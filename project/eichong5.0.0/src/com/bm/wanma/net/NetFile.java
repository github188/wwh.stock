package com.bm.wanma.net;

import java.io.File;
import java.util.LinkedHashMap;

import com.bm.wanma.alipay.Base64;
import com.bm.wanma.entity.BaseBean;
import com.bm.wanma.net.HttpHelper.HttpStringHandler;
import com.bm.wanma.utils.LogUtil;
import com.bm.wanma.utils.PreferencesUtil;
import com.bm.wanma.utils.Tools;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

public class NetFile extends NetBase {

	private static NetFile instance;
	public static String replaceToken = "";
 
	public static NetFile getInstance(Context mcontext) {
		String apiToken = PreferencesUtil.getStringPreferences(mcontext, "apiToken");
		Long timeStamp = System.currentTimeMillis();
		String toToken = apiToken + timeStamp ;
		replaceToken = "";
		char[] chars = toToken.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			replaceToken += Tools.replace((byte) chars[i]);
		} 
		LogUtil.i("cm_replaceToken", replaceToken);
		replaceToken = Base64.encode(replaceToken.getBytes());
		if (instance == null) {
			instance = new NetFile();
		} 
		return instance;
	}
	/**
	 * 修改个人资料  cm
	 * @param pkUserinfo 用户id
	 * @param allMultiFile 头像（文件流）
	 * @param usinUsername 昵称
	 * @param usinSex 性别（0男1女）
	 * @param usinCarinfoId 车型ID
	 * @param usinUseraddress 联系地址
	 * @param pCode 省份代码
	 * @param cCode 城市代码
	 * @param aCode 区县代码
	 * @param handler
	 */
	public void modifyPersonalInfo(final Handler handler, String pkUserinfo,String allMultiFile,
			String usinUsername, String usinSex,String usinCarinfoId,String usinUseraddress,String pCode,
			String cCode,String aCode) {
		LinkedHashMap<String, String> ajaxParams = new LinkedHashMap<String, String>();
			ajaxParams.put("pkUserinfo", pkUserinfo);
		if(usinUsername != null){
			ajaxParams.put("usinUsername", usinUsername);
		}
		
		if(usinSex != null){
			ajaxParams.put("usinSex", usinSex);
		}
		if(usinCarinfoId != null){
			ajaxParams.put("usinCarinfoId",usinCarinfoId);
		}
		
		if(usinUseraddress != null){
			ajaxParams.put("usinUseraddress",usinUseraddress);
		}
		if(pCode != null){
			ajaxParams.put("pCode",pCode);
		}
		if(cCode != null){
			ajaxParams.put("cCode",cCode);
		}
		if(aCode != null){
			ajaxParams.put("aCode",aCode);
		}
		ajaxParams.put("t",replaceToken);
		LinkedHashMap<String, File> files = new LinkedHashMap<String, File>();
			if (!Tools.isNull(allMultiFile)&&allMultiFile.length() > 0) {
				files.put("allMultiFile", new File(allMultiFile));
			}
			HttpHelper.asyncFormPost(Protocol.MODIFY_USER_INFO, ajaxParams, files, new HttpStringHandler() {
 
			@Override
			public void handleResponse(HttpStringResult result) {
				Gson gson = new Gson();
				String content = result.result;
				BaseBean bean = null;
				if (content != null && content.length() > 0) {
					try {
						Log.i("cm_gson", ""+content);
						bean = gson.fromJson(content, new TypeToken<BaseBean<?>>() {
						}.getType());
					} catch (JsonSyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				setHandler(handler, bean, Protocol.MODIFY_USER_INFO);

			}
		});
	}
	/**
	 * 完善用户资料  cm
	 * @param pkUserinfo 用户id
	 * @param usinPhone 手机号
	 * @param allMultiFile 头像（文件流）
	 * usinEmail 邮箱
	 * usinFacticityname 真实姓名
	 * usinUsername 昵称
	 * usinSex 性别（0男1女）
	 * usinBirthdate 出生日期（2015-04-15 00:00:00）
	 * usinMembercode 会员卡号
	 * usinIccode ic卡号
	 * usinDrivinglicense 驾驶证号
	 * usinCarinfoId 车型ID
	 * usinVehiclenumbe 车架号（申请电卡时必填）
	 * usinPlatenumber 车牌号
	 * usinUseraddress 联系地址
	 * pCode 省份代码
	 * cCode 城市代码
	 * aCode 区县代码
	 * isCard 是否申请电卡 1是（申请电卡时必填）
	 * @param handler
	 */
	public void modifyUserInfo(final Handler handler, String pkUserinfo, String usinPhone, String allMultiFile, String usinEmail,
			String usinFacticityname,String usinUsername, String usinSex, String usinBirthdate, String usinMembercode, String usinIccode,
			String usinDrivinglicense,String usinCarinfoId,String usinVehiclenumbe,String usinPlatenumber,String usinUseraddress,String pCode,
			String cCode,String aCode,String isCard) {
		LinkedHashMap<String, String> ajaxParams = new LinkedHashMap<String, String>();
			ajaxParams.put("pkUserinfo", pkUserinfo);
		if(!Tools.isEmptyString(usinPhone)){
			ajaxParams.put("usinPhone", usinPhone);
		}
		if(!Tools.isEmptyString(usinEmail)){
			ajaxParams.put("usinEmail", usinEmail);
		}else {
			ajaxParams.put("usinEmail", "");
		}
		
		if(!Tools.isEmptyString(usinFacticityname)){
			ajaxParams.put("usinFacticityname", usinFacticityname);
		}else {
			ajaxParams.put("usinFacticityname", "");
		}
		
		if(!Tools.isEmptyString(usinUsername)){
			ajaxParams.put("usinUsername", usinUsername);
		}else {
			ajaxParams.put("usinUsername", "");
		}
		
		if(!Tools.isEmptyString(usinSex)){
			ajaxParams.put("usinSex", usinSex);
		}
		
		if(!Tools.isEmptyString(usinBirthdate)){
			ajaxParams.put("usinBirthdate", usinBirthdate);
		}
		
		if(!Tools.isEmptyString(usinMembercode)){
			ajaxParams.put("usinMembercode", usinMembercode);
		}else {
			ajaxParams.put("usinMembercode"," ");
		}
		
		if(!Tools.isEmptyString(usinIccode)){
			ajaxParams.put("usinIccode", usinIccode);
		}else {
			ajaxParams.put("usinIccode"," ");
		}
		
		if(!Tools.isEmptyString(usinDrivinglicense)){
			ajaxParams.put("usinDrivinglicense", usinDrivinglicense);
		}else {
			ajaxParams.put("usinDrivinglicense"," ");
		}
		
		if(!Tools.isEmptyString(usinCarinfoId)){
			ajaxParams.put("usinCarinfoId",usinCarinfoId);
		}else {
			ajaxParams.put("usinCarinfoId"," ");
		}
		
		if(!Tools.isEmptyString(usinVehiclenumbe)){
			ajaxParams.put("usinVehiclenumbe",usinVehiclenumbe);
		}else {
			ajaxParams.put("usinVehiclenumbe"," ");
		}
		
		if(!Tools.isEmptyString(usinPlatenumber)){
			ajaxParams.put("usinPlatenumber",usinPlatenumber);
		}else {
			ajaxParams.put("usinPlatenumber"," ");
		}
		
		if(!Tools.isEmptyString(usinUseraddress)){
			ajaxParams.put("usinUseraddress",usinUseraddress);
		}else {
			ajaxParams.put("usinUseraddress", "");
		}
		
		if(!Tools.isEmptyString(pCode)){
			ajaxParams.put("pCode",pCode);
		}
		if(!Tools.isEmptyString(cCode)){
			ajaxParams.put("cCode",cCode);
		}
		if(!Tools.isEmptyString(aCode)){
			ajaxParams.put("aCode",aCode);
		}
		if(!Tools.isEmptyString(isCard)){
			ajaxParams.put("isCard",isCard);
		}	

			ajaxParams.put("t",replaceToken);
			LinkedHashMap<String, File> files = new LinkedHashMap<String, File>();
			if (!Tools.isNull(allMultiFile)&&allMultiFile.length() > 0) {
				files.put("allMultiFile", new File(allMultiFile));
			}
			HttpHelper.asyncFormPost(Protocol.MODIFY_USER_INFO, ajaxParams, files, new HttpStringHandler() {
 
			@Override
			public void handleResponse(HttpStringResult result) {
				Gson gson = new Gson();
				String content = result.result;
				BaseBean bean = null;
				if (content != null && content.length() > 0) {
					try {
						Log.i("cm_gson", ""+content);
						bean = gson.fromJson(content, new TypeToken<BaseBean<?>>() {
						}.getType());
					} catch (JsonSyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				setHandler(handler, bean, Protocol.MODIFY_USER_INFO);

			}
		});
	}
	/**
	 * 分享电桩 cm
	 * @param address 电桩地址
	 * @param longitude 经度
	 * @param latitude  维度
	 * @param parameter_note 参数说明
	 * @param allMultiFile 图片
	 * @param handler
	 */
	public void sharePile(final Handler handler, String address, String longitude, String latitude, String parameter_note,String allMultiFile) {
		LinkedHashMap<String, String> ajaxParams = new LinkedHashMap<String, String>();
			if(!Tools.isEmptyString(address)){
				ajaxParams.put("address", address);
			}
			if(!Tools.isEmptyString(longitude)){
				ajaxParams.put("longitude", longitude);
			}
			if(!Tools.isEmptyString(latitude)){
				ajaxParams.put("latitude", latitude);
			}
			if(!Tools.isEmptyString(parameter_note)){
				ajaxParams.put("parameter_note", parameter_note);
			}
			ajaxParams.put("t",replaceToken);
			LinkedHashMap<String, File> files = new LinkedHashMap<String, File>();
			if (!Tools.isNull(allMultiFile)&&allMultiFile.length() > 0) {
				files.put("allMultiFile", new File(allMultiFile));
			}
			HttpHelper.asyncFormPost(Protocol.SHARE_PILE, ajaxParams, files, new HttpStringHandler() {
 
			@Override
			public void handleResponse(HttpStringResult result) {
				Gson gson = new Gson();
				String content = result.result;
				BaseBean bean = null;
				if (content != null && content.length() > 0) {
					try {
						Log.i("cm_gson", ""+content);
						bean = gson.fromJson(content, new TypeToken<BaseBean<?>>() {
						}.getType());
					} catch (JsonSyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				setHandler(handler, bean, Protocol.SHARE_PILE);

			}
		});
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
