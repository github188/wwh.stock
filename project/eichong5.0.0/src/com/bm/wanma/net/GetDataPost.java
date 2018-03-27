package com.bm.wanma.net;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import net.tsz.afinal.http.AjaxParams;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.bm.wanma.alipay.Base64;
import com.bm.wanma.entity.AdvertisementBean;
import com.bm.wanma.entity.AnchorSummary;
import com.bm.wanma.entity.BanlanceBean;
import com.bm.wanma.entity.BaseBean;
import com.bm.wanma.entity.BillDetailsBean;
import com.bm.wanma.entity.BillOneBean;
import com.bm.wanma.entity.CarNameBean;
import com.bm.wanma.entity.CarRepairBean;
import com.bm.wanma.entity.CarTypeBean;
import com.bm.wanma.entity.ChargeFinishAndDoneBean;
import com.bm.wanma.entity.ChargeIntegralBean;
import com.bm.wanma.entity.ChargeOrderDetailsBean;
import com.bm.wanma.entity.CheckInvoiceBean;
import com.bm.wanma.entity.CouponBean;
import com.bm.wanma.entity.CouponInfoBean;
import com.bm.wanma.entity.CouponSizeBean;
import com.bm.wanma.entity.ElectricPileDetailsBean;
import com.bm.wanma.entity.EmergencyCallBean;
import com.bm.wanma.entity.EnableInvoiceBean;
import com.bm.wanma.entity.EverydayGetBean;
import com.bm.wanma.entity.FunctionButtonBean;
import com.bm.wanma.entity.ImageCarouselBean;
import com.bm.wanma.entity.InformationBean;
import com.bm.wanma.entity.IntegralDetailBean;
import com.bm.wanma.entity.IntegralEventBean;
import com.bm.wanma.entity.IntegralProportionBean;
import com.bm.wanma.entity.IntegralRechargeBean;
import com.bm.wanma.entity.InvoiceConfigBean;
import com.bm.wanma.entity.InvoiceRecordBean;
import com.bm.wanma.entity.InvoiceRecordDetailBean;
import com.bm.wanma.entity.MalfunctionBean;
import com.bm.wanma.entity.MarkerCityBean;
import com.bm.wanma.entity.MyCardApplyInfo;
import com.bm.wanma.entity.MyCardInfo;
import com.bm.wanma.entity.EquipmentBean;
import com.bm.wanma.entity.LoginBean;
import com.bm.wanma.entity.MapModeBean;
import com.bm.wanma.entity.MyCollectBean;
import com.bm.wanma.entity.MyDynamicListBean;
import com.bm.wanma.entity.MyNewsFeedbackBean;
import com.bm.wanma.entity.MyNewsSystemBean;
import com.bm.wanma.entity.PersonIntegralBean;
import com.bm.wanma.entity.PileCommentListBean;
import com.bm.wanma.entity.RechargeRecordBean;
import com.bm.wanma.entity.ScanInfoBean;
import com.bm.wanma.entity.ShareIntegralBean;
import com.bm.wanma.entity.StationCommentListBean;
import com.bm.wanma.entity.TextCarouselBean;
import com.bm.wanma.entity.UserInfoBean;
import com.bm.wanma.entity.VersionInfoBean;
import com.bm.wanma.entity.MyBillList;
import com.bm.wanma.net.HttpHelper.HttpStringHandler;
import com.bm.wanma.utils.PreferencesUtil;
import com.bm.wanma.utils.Tools;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class GetDataPost extends NetPost {

	public static GetDataPost instance;
	public static String replaceToken = "";
	/** 默认type */
	private Type defaulType = new TypeToken<BaseBean<?>>() {
	}.getType();
	/** 获取版本信息 */
	private Type VersionInfoType = new TypeToken<BaseBean<VersionInfoBean>>() {
	}.getType();
	/** 获取地图锚点信息 */
	private Type AnchorSummaryType = new TypeToken<BaseBean<AnchorSummary>>() {
	}.getType();
	/** 电站评价列表 */
	private Type StationCommentListType = new TypeToken<BaseBean<ArrayList<StationCommentListBean>>>() {
	}.getType();
	/** 电桩评价列表 */
	private Type StiltCommentListType = new TypeToken<BaseBean<ArrayList<PileCommentListBean>>>() {
	}.getType();
	/** 用户信息 */
	private Type userType = new TypeToken<BaseBean<UserInfoBean>>() {
	}.getType();
	
	/** 获取版本信息 */
	private Type advertisement = new TypeToken<BaseBean<ArrayList<AdvertisementBean>>>() {
	}.getType();
	/** 我的账单 */
	private Type billlist = new TypeToken<BaseBean<ArrayList<BillOneBean>>>() {
	}.getType();
	/** 账单明细 */
	private Type mothbilllist = new TypeToken<BaseBean<ArrayList<BillDetailsBean>>>() {
	}.getType();
	/** 充值记录*/
	private Type rechargellist = new TypeToken<BaseBean<ArrayList<RechargeRecordBean>>>() {
	}.getType();
	/**聚合点*/
	private Type markerCityType =  new TypeToken<BaseBean<ArrayList<MarkerCityBean>>>() {
	}.getType();
    /**
     * @param mc
     * @return Instance
     * 获得实例
     */
	public static GetDataPost getInstance(Context mc) {
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
			instance = new GetDataPost();
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
	 * @param handler
	 * @param mobileNumber
	 *//*
	public void getCode(Handler handler, String mobileNumber) {
		AjaxParams ajaxParams = new AjaxParams();
		ajaxParams.put("mobileNumber", mobileNumber);
		ajaxParams.put("t", replaceToken);
		getData(handler, Protocol.GET_AUTH_CODE, ajaxParams, defaulType);
	}*/
	
	/**
	 * 获取版本信息
	 * @author cm
	 * @param handler        
	 */
	public void getAppVersion(Handler handler,String versNumber) {
		AjaxParams params = new AjaxParams();
		params.put("versNumber", versNumber);
		params.put("versType", "1");
		params.put("t", replaceToken);
		getData(handler, Protocol.GET_APP_VERSION_INFO, params, VersionInfoType);
	}
	
	/**
	 * 电桩 电站 --地图列表查找
	 * @author cm 2017年1月16日
	 * @param handler
	 * @param latitude 经度(用户当前位置经度)
	 * @param longitude 纬度(用户当前位置纬度)
	 * @param dc 筛选条件直流，选中为1，不选为0
	 * @param ac 筛选条件交流，选中为1，不选为0
	 * @param freetime 筛选条件空闲，选中为1，不选为0
	 * @param freeparking 筛选条件免费停车，选中为1，不选为0
	 * @param userid 用户id
	 */
	public void getElectricPileMapList(Handler handler,String currentlatitude,String currentlongitude, String latitude,String longitude,
			String dc,String ac,String freetime,String freeparking,String userid,String cpynum) {
		AjaxParams params = new AjaxParams();
		params.put("currentlat", currentlatitude);
		params.put("currentlng", currentlongitude);
		params.put("latitude", latitude);
		params.put("longitude", longitude);
		params.put("dc", dc);
		params.put("ac", ac);
		params.put("freetime", freetime);
		params.put("freeparking", freeparking);
		params.put("userid", userid);
		params.put("cpynum", cpynum);
		params.put("t", replaceToken);
		getData(handler, Protocol.GET_MAP_LIST, params, new TypeToken<BaseBean<ArrayList<MapModeBean>>>() {
		}.getType());
	}
	/**
	 * 电桩 电站 --搜索
	 * @author cm 2017年1月16日
	 * @param handler
	 * @param latitude 经度(用户当前位置经度)
	 * @param longitude 纬度(用户当前位置纬度)
	 * @param dc 筛选条件直流，选中为1，不选为0
	 * @param ac 筛选条件交流，选中为1，不选为0
	 * @param freetime 筛选条件空闲，选中为1，不选为0
	 * @param freeparking 筛选条件免费停车，选中为1，不选为0
	 * @param userid 用户id
	 */
	public void getSearchList(Handler handler, String longitude,String latitude,String keyword,String cpynum) {
		AjaxParams params = new AjaxParams();
		params.put("longitude", longitude);
		params.put("latitude", latitude);
		params.put("cpynum", cpynum);
		params.put("keyword", keyword);
		params.put("t", replaceToken);
		getData(handler, Protocol.GET_SEARCH_LIST, params, new TypeToken<BaseBean<ArrayList<MapModeBean>>>() {
		}.getType());
	}
	/**
	 * 获取地图锚点 简介信息
	 * @author cm
	 * @param handler  
	 * @param lng  经度（人所在地的经纬)
	 * @param lat 
	 * @param eid 桩、站id
	 * @param type  1桩2站
	 */
	public void getAnchorSummary(Handler handler,String lng,String lat,String eid,String type) {
		AjaxParams params = new AjaxParams();
		params.put("lng", lng);
		params.put("lat", lat);
		params.put("eid", eid);
		params.put("type", type);
		params.put("t", replaceToken);
		getData(handler, Protocol.GET_ANCHOR_SUMMARY, params, AnchorSummaryType);
	}
	
	/**
	 * 登录
	 * cm
	 * @param usinPhone   登录手机号
	 * @param usInPassword 密码（md5加密
	 * @param jpushRegistrationid 极光推送手机唯一标示
	 * @param jpushDevicetype  设备类型（1安卓2ios）
	 * @param handler
	 */
	public void login(String usinPhone, String usInPassword,String jpushRegistrationid,String jpushDevicetype,String did,String cpynum, Handler handler) {
		AjaxParams ajaxParams = new AjaxParams();
		ajaxParams.put("usinPhone", usinPhone);
		ajaxParams.put("usInPassword", usInPassword);
		ajaxParams.put("jpushRegistrationid", jpushRegistrationid);
		ajaxParams.put("jpushDevicetype", jpushDevicetype);
		ajaxParams.put("did", did);
		ajaxParams.put("cpynum", cpynum);
		ajaxParams.put("t", replaceToken);
		getData(handler, Protocol.TO_LOGIN, ajaxParams, new TypeToken<BaseBean<LoginBean>>() {
		}.getType());
	}
	/**
	 * 用户注销
	 * @param userId  用户ID  是
	 */
	public void logout(Handler handler, String userId) {
		AjaxParams params = new AjaxParams();
		params.put("userId", userId);
		params.put("t", replaceToken);
		getData(handler, Protocol.TO_LOGOUT, params, defaulType);
	}
	
	/**
	 * 检查手机号码是否已注册
	 * @author cm
	 * @param handler
	 * @param phone
	 */
	public void checkPhone(Handler handler, String phone,String cpynum) {
		AjaxParams params = new AjaxParams();
		params.put("phone", phone);
		params.put("cpynum", cpynum);
		params.put("t", replaceToken);
		getData(handler, Protocol.CHECK_PHONE, params, defaulType);
	}

	/**
	 * 检查验证码是否正确
	 * @author cm
	 * @param handler
	 * @param usinPhone
	 * @param authCode
	 */
	public void checkCode(Handler handler, String usinPhone, String authCode) {
		AjaxParams params = new AjaxParams();
		params.put("usinPhone", usinPhone);
		params.put("authCode", authCode);
		params.put("t", replaceToken);
		getData(handler, Protocol.CHECK_CODE, params, defaulType);
	}
	/**
	 * 找回密码  cm
	 * @param usinPhone
	 * @param usInPassword
	 * @param authCode
	 * @param handler
	 */

	public void resetPwd(String userPhone, String pwd, String smsCode, String cpynum, Handler handler) {
		AjaxParams ajaxParams = new AjaxParams();
		ajaxParams.put("usinPhone", userPhone);
		ajaxParams.put("usinPassword", pwd);
		ajaxParams.put("authCode", smsCode);
		ajaxParams.put("cpynum", cpynum);
		ajaxParams.put("t", replaceToken);
		getData(handler, Protocol.RESET_PWD, ajaxParams, new TypeToken<BaseBean<?>>() {
		}.getType());

	}
	
	/**
	 *  修改密码 cm
	 * @param uId 用户id
	 * @param opw 原密码（md5）
	 * @param npw 新密码（md5加密）
	 * @param handler
	 */

	public void modifyPwd(String uId, String opw, String npw, Handler handler) {
		AjaxParams ajaxParams = new AjaxParams();
		ajaxParams.put("uId", uId);
		ajaxParams.put("opw", opw);
		ajaxParams.put("npw", npw);
		ajaxParams.put("t", replaceToken);
		getData(handler, Protocol.MODIFY_PWD, ajaxParams, new TypeToken<BaseBean<?>>() {
		}.getType());

	}
	/**
	 * 检查支付密码 cm
	 * @param uid 用户id
	 * @param pwd （md5加密）
	 * @param handler
	 * 输错3次，当天锁定
	 */

	public void checkPayPwd(String uid, String pwd,Handler handler) {
		AjaxParams ajaxParams = new AjaxParams();
		ajaxParams.put("uid", uid);
		ajaxParams.put("pwd", pwd);
		ajaxParams.put("t", replaceToken);
		getData(handler, Protocol.CHECK_PAY_PWD, ajaxParams, new TypeToken<BaseBean<?>>() {
		}.getType());

	}
	/**
	 *  修改支付密码 cm
	 * @param uid 用户id
	 * @param oppw 原密码（md5）
	 * @param nppw 新密码（md5加密）
	 * @param handler
	 */

	public void modifyPayPwd(String uid, String oppw, String nppw, Handler handler) {
		AjaxParams ajaxParams = new AjaxParams();
		ajaxParams.put("uid", uid);
		ajaxParams.put("oppw", oppw);
		ajaxParams.put("nppw", nppw);
		ajaxParams.put("t", replaceToken);
		getData(handler, Protocol.MODIFY_PAY_PWD, ajaxParams, new TypeToken<BaseBean<?>>() {
		}.getType());

	}
	/**
	 *  设置支付密码 cm
	 * @param uid 用户id
	 * @param pwd 密码（md5）
	 * @param handler
	 */

	public void setPayPwd(String uid, String pwd,Handler handler) {
		AjaxParams ajaxParams = new AjaxParams();
		ajaxParams.put("uid", uid);
		ajaxParams.put("pwd", pwd);
		ajaxParams.put("t", replaceToken);
		getData(handler, Protocol.SET_PAY_PWD, ajaxParams, new TypeToken<BaseBean<?>>() {
		}.getType());

	}
	/**
	 * 账号注册--只有手机号码 和 密码
	 * @author cm
	 * @param handler
	 * @param usinPhone
	 * @param usinPassword 密码（md5加密）
	 * @param platform 3android 4ios(固定)	
	 */
	public void register(Handler handler, String usinPhone, String usinPassword ,String invitePhone,String cpynum) {
		AjaxParams params = new AjaxParams();
		params.put("usinPhone", usinPhone);
		params.put("usinPassword", usinPassword);
		params.put("platform", "3");
		params.put("cpynum", cpynum);
		params.put("t", replaceToken);
		if(!invitePhone.equals("")&&!invitePhone.equals(usinPhone)){
			params.put("invitePhone", invitePhone);
		}
		getData(handler, Protocol.TO_REGIST, params, defaulType);
	}

	/**
	 * 获取市级充电点聚合
	 * cm
	 * @param handler
	 * @param location(lng,lat)
	 */
	public void getMarkerCity(Handler handler, String location, String cpynum) {
		AjaxParams params = new AjaxParams();
		params.put("location", location);
		params.put("cpynum", cpynum);
		params.put("t", replaceToken);
		getData(handler, Protocol.GET_MARKER_CITY, params,markerCityType);
	}
	/**
	 * 获取省级充电点聚合
	 * cm
	 * @param handler
	 */
	public void getMarkerProvince(Handler handler, String cpynum) {
		AjaxParams params = new AjaxParams();
		params.put("cpynum", cpynum);
		params.put("t", replaceToken);
		getData(handler, Protocol.GET_MARKER_PROVINCE, params,markerCityType);
	}
	/**
	 * 获取电桩详情
	 * cm
	 * @param handler
	 * @param electricPileId
	 * @param pkUserinfo
	 * @param longitude
	 * @param latitude
	 */
	public void getPileDetail(Handler handler, String powerStationId,String longitude,String latitude) {
		AjaxParams params = new AjaxParams();
		params.put("powerStationId", powerStationId);
		params.put("longitude", longitude);
		params.put("latitude", latitude);
		params.put("t", replaceToken);
		getData(handler, Protocol.POWER_Pile_DETAIL, params, new TypeToken<BaseBean<ElectricPileDetailsBean>>() {
		}.getType());
	}
	/**
	 * 收藏电站，电桩
	 * @author cm
	 * @param handler
	 * @param userId 用户id
	 * @param favoriteType 类型（1电桩，2电站）
	 * @param favoriteTypeId 电桩ID/电桩ID
	 */
	public void collectStationPile(Handler handler, String userId, String favoriteType, String favoriteTypeId) {
		AjaxParams params = new AjaxParams();
		params.put("favoriteType", favoriteType);
		params.put("userId", userId);
		params.put("favoriteTypeId", favoriteTypeId);
		params.put("t", replaceToken);
		getData(handler, Protocol.COLLECT_STATION_PILE, params, defaulType);
	}
	/**
	 * 删除我的收藏
	 * 
	 * @author cm
	 * @param handler
	 * @param favoriteType 收藏类型  3-商城收藏  1-电桩 2-电站
	 * @param userCollectId
	 */
	public void removeMyCollect(Handler handler, String userCollectId,String favoriteType) {
		AjaxParams params = new AjaxParams();
		params.put("userCollectId", userCollectId);
		params.put("favoriteType", favoriteType);
		params.put("t", replaceToken);
		getData(handler, Protocol.REMOVE_COLLECTED, params, defaulType);
	}
	/**
	 * 获取电站评价列表   
	 * @author cm 
	 * @param handler
	 * @param pageNum 每页多少条
	 * @param pageNumber 当前第几页
	 * @param prCoProductId 电站ID
	 */
	public void getStationCommentList(Handler handler,String prCoProductId ,String pageNum,String pageNumber) {
		AjaxParams params = new AjaxParams();
		params.put("prCoProductId", prCoProductId);
		params.put("pageNum", pageNum);
		params.put("pageNumber", pageNumber);
		params.put("t", replaceToken);
		getData(handler, Protocol.GET_STATION_COMMENT, params,StationCommentListType);
	}
	/**
	 * 获取电桩评论列表  
	 * @author cm 
	 * @param handler
	 * @param pageNum 每页多少条
	 * @param pageNumber 当前第几页
	 * @param prCoProductId 电站ID
	 * @param type 1评价2留言
	 */
	public void getPileCommentList(Handler handler,String prCoProductId ,String pageNum,String pageNumber) {
		AjaxParams params = new AjaxParams();
		params.put("prCoProductId", prCoProductId);
		params.put("pageNum", pageNum);
		params.put("pageNumber", pageNumber);
		params.put("type", "1");
		params.put("t", replaceToken);
		getData(handler, Protocol.GET_PILE_COMMENT, params,StiltCommentListType);
	}
	/**
	 * 获得设备保修类型
	 * cm
	 * @param handler
	 * @param cpId 配置类型 （固定值29）
	 */
	public void getEquipment(Handler handler) {
		AjaxParams ajaxParams = new AjaxParams();
		ajaxParams.put("cpId", "29");
		ajaxParams.put("t", replaceToken);
		getData(handler, Protocol.GET_EQUIPMENT_TYPE, ajaxParams, new TypeToken<BaseBean<ArrayList<EquipmentBean>>>() {
		}.getType());
	}

	/**
	 * 提交设备保修
	 * cm
	 * @param handler
	 * @param eqreUserid 用户id
	 * @param eqreWarrantytypeid 设备报修类型（保修项id）
	 * @param epId 电桩、电站id
	 * @param deviceType 设备类型 1电桩2电站
	 * @param eqreContent 报修内容   (非必须)
	 */
	public void commitEquipment(Handler handler, String eqreUserid, String eqreWarrantytypeid, String epId,String deviceType,String eqreContent) {
		AjaxParams ajaxParams = new AjaxParams();
		ajaxParams.put("eqreUserid",eqreUserid );
		ajaxParams.put("eqreWarrantytypeid", eqreWarrantytypeid);
		ajaxParams.put("epId", epId);
		ajaxParams.put("deviceType", deviceType);
		ajaxParams.put("eqreContent", eqreContent);
		ajaxParams.put("t", replaceToken);
		getData(handler, Protocol.COMMIT_EQUIPMENT, ajaxParams,defaulType);
	}

	/**
	 * 获取用户信息
	 * cm
	 * @param handler
	 * @param userId
	 */
	public void getUserInfo(Handler handler, String userId ) {
		AjaxParams params = new AjaxParams();
		params.put("userId", userId);
		params.put("t", replaceToken);
		getData(handler, Protocol.GET_USER_INFO, params, userType);
	}
	/**
	 * 我的账单
	 * lyh
	 * @param handler
	 * @param uId
	 * @param pageNumber 当前页码从1开始
	 * @param pageNum 每页数据量
	 */
	public void getMyBillList(Handler handler, String uId,String pageNumber,String pageNum) {
		AjaxParams params = new AjaxParams();
		params.put("uId", uId);
		params.put("pageNumber", pageNumber);
		params.put("pageNum", pageNum);
		params.put("t", replaceToken);
		getData(handler, Protocol.MY_BILL, params, billlist);
	}
	/**
	 * 月账单明细
	 * lyh
	 * @param handler
	 * @param uId
	 * @param pageNumber 当前页码从1开始
	 * @param pageNum 每页数据量
	 */
	public void getMyMothBillList(Handler handler, String uId,String mt,String pageNumber,String pageNum) {
		AjaxParams params = new AjaxParams();
		params.put("uId", uId);
		params.put("mt", mt);
		params.put("pageNumber", pageNumber);
		params.put("pageNum", pageNum);
		params.put("t", replaceToken);
		getData(handler, Protocol.MY_MOTH_BILL, params, mothbilllist);
	}
	
	
	/**
	 * 充值记录
	 * lyh
	 * @param handler
	 * @param uId
	 * @param pageNumber 当前页码从1开始
	 * @param pageNum 每页数据量
	 */
	public void getMyRechargeList(Handler handler, String uId,String pageNumber,String pageNum) {
		AjaxParams params = new AjaxParams();
		params.put("uId", uId);
		params.put("pageNumber", pageNumber);
		params.put("pageNum", pageNum);
		params.put("t", replaceToken);
		getData(handler, Protocol.RECHARGERECOR, params, rechargellist);
	}
	/**
	 * 获取发票配置信息
	 * cm
	 * @param handler
	 */
	public void getInvoiceConfig(Handler handler) {
		AjaxParams params = new AjaxParams();
		params.put("t", replaceToken);
		getData(handler, Protocol.MY_INVOICE_CONFIG, params, new TypeToken<BaseBean<InvoiceConfigBean>>() {
		}.getType());
	}
	
	/**
	 * 检查用户是否看过开票流程
	 * cm
	 * @param handler
	 * @param uId
	 */
	public void getInvoiceCheck(Handler handler,String uId) {
		AjaxParams params = new AjaxParams();
		params.put("t", replaceToken);
		params.put("uId", uId);
		getData(handler, Protocol.GET_INVOICE_CHECK, params,new TypeToken<BaseBean<CheckInvoiceBean>>() {
		}.getType());
	}
	/**
	 * 添加用户是否看过开票流程
	 * cm
	 * @param handler
	 * @param uId
	 */
	public void addInvoiceCheck(Handler handler,String uId) {
		AjaxParams params = new AjaxParams();
		params.put("t", replaceToken);
		params.put("uId", uId);
		getData(handler, Protocol.ADD_INVOICE_CHECK, params,defaulType);
	}
	
	/**
	 * 可开发票列表
	 * cm
	 * @param handler
	 * @param uId 
	 */
	public void getMyEnableInvoiceList(Handler handler, String uId) {
		AjaxParams params = new AjaxParams();
		params.put("uId", uId);
		params.put("t", replaceToken);
		getData(handler, Protocol.MY_ENABLE_INVOICE, params, new TypeToken<BaseBean<ArrayList<EnableInvoiceBean>>>() {
		}.getType());
	}
	/**
	 * 开发票
	 * cm
	 * @param handler
	 * @param ivUserId 用户ID
	 * @param ivType 开票类型：0-个人开票 ，1-公司开票
	 * @param ivFreightAmount 邮费
	 * @param ivContent 发票内容
	 * @param ivCompanyName 发票台头
	 * @param ivAmount 发票金额
	 * @param ivTaxpayerNumbe 纳税人识别号(类型为公司开票时必填)
	 * @param ivCompanyAddress 公司地址(类型为公司开票时必填)
	 * @param ivPhoneNumber 公司电话(类型为公司开票时必填)
	 * @param ivBankName 开户银行名称(类型为公司开票时必填)
	 * @param ivBankAccount 开户银行账号(类型为公司开票时必填)
	 * @param ivPayFreight 开票支付类型1-支付宝支付，2-微信支付，4-货到付款
	 * @param ivRecipients 收件人姓名
	 * @param ivRecipientsNumber 收件人手机号码
	 * @param ivProvince 所属省份代码
	 * @param ivCity 所属城市代码
	 * @param ivCounty 所属区县代码 
	 * @param ivAddress 收件人地址
	 * @param ivRecords 开票关联消费记录ID，用’,’隔开
	 */
	public void commitInvoice(Handler handler, String ivUserId,String ivType,String ivContent,String ivCompanyName,String ivAmount,String ivTaxpayerNumber,
			String ivCompanyAddress,String ivPhoneNumber,String ivBankName,String ivBankAccount,String ivPayFreight,String ivRecipients,String ivRecipientsNumber,
			String ivProvince,String ivCity,String ivCounty,String ivAddress,String ivRecords,String ivFreightAmount,String pkInvoice) {
		AjaxParams params = new AjaxParams();
		params.put("ivUserId", ivUserId);
		params.put("ivType", ivType);
		params.put("ivContent", ivContent);
		params.put("ivCompanyName", ivCompanyName);
		params.put("ivAmount", ivAmount);
		params.put("ivTaxpayerNumber", ivTaxpayerNumber);
		params.put("ivCompanyAddress",ivCompanyAddress);
		params.put("ivPhoneNumber", ivPhoneNumber);
		params.put("ivBankName", ivBankName);
		params.put("ivBankAccount", ivBankAccount);
		params.put("ivPayFreight", ivPayFreight);
		params.put("ivRecipients", ivRecipients);
		params.put("ivRecipientsNumber", ivRecipientsNumber);
		params.put("ivProvince", ivProvince);
		params.put("ivCity", ivCity);
		params.put("ivCounty", ivCounty);
		params.put("ivAddress", ivAddress);
		params.put("ivRecords", ivRecords);
		params.put("ivFreightAmount", ivFreightAmount);
		params.put("pkInvoice", pkInvoice);
		params.put("t", replaceToken);
		getData(handler, Protocol.COMMIT_INVOICE, params, defaulType);
	}	
	/**
	 * 个人开过的发票记录
	 * cm
	 * @param handler
	 * @param uId
	 * @param pageNumber 当前页码从1开始
	 * @param pageNum 每页数据量
	 */
	public void getMyInvoiceRecordList(Handler handler, String uId,String pageNumber,String pageNum) {
		AjaxParams params = new AjaxParams();
		params.put("uId", uId);
		params.put("pageNumber", pageNumber);
		params.put("pageNum", pageNum);
		params.put("t", replaceToken);
		getData(handler, Protocol.MY_INVOICE_RECORD, params, new TypeToken<BaseBean<ArrayList<InvoiceRecordBean>>>() {
		}.getType());
	}
	
	/**
	 * 发票详情
	 * cm
	 * @param handler
	 * @param iId
	 */
	public void getMyInvoiceRecordDetail(Handler handler, String iId) {
		AjaxParams params = new AjaxParams();
		params.put("iId", iId);
		params.put("t", replaceToken);
		getData(handler, Protocol.MY_INVOICE_RECORD_DETAIL, params, new TypeToken<BaseBean<InvoiceRecordDetailBean>>() {
		}.getType());
	}
	/**
	 * 发票所包含的消费记录
	 * cm
	 * @param handler
	 * @param iId
	 */
	public void getMyInvoiceRecordPur(Handler handler, String iId) {
		AjaxParams params = new AjaxParams();
		params.put("iId", iId);
		params.put("t", replaceToken);
		getData(handler, Protocol.MY_INVOICE_RECORD_PUR, params, new TypeToken<BaseBean<MyBillList>>() {
		}.getType());
	}
	
	
	
	
	
	/**
	 * 降地锁 
	 * cm
	 * @param handler
	 * @param epCode 电桩编号
	 * @param headNum 枪口号，不是枪口id
	 * @param parkNum 枪口对应的车位号
	 * @param uid 用户id
	 * @param lat 维度（手机经纬度）
	 * @param lng 经度
	 * @param eplat 维度（桩的经纬度）
	 * @param eplng 经度
	 */
	public void downParkLock(Handler handler, String epCode,String headNum,
			String parkNum,String uid,String lat,String lng,String eplat,String eplng){
		AjaxParams params = new AjaxParams();
		params.put("epCode", epCode);
		params.put("headNum", headNum);
		params.put("parkNum", parkNum);
		params.put("uid", uid);
		params.put("lat", lat);
		params.put("lng", lng);
		params.put("eplat", eplat);
		params.put("eplng", eplng);
		params.put("t", replaceToken);
		getData(handler, Protocol.DOWN_PARKLOCK, params, defaulType);
	}
	/**
	 * LED开关
	 * cm
	 * @param handler
	 * @param epCode 电桩编号
	 * @param type 1开，2关
	 * @param remainTime 持续闪烁时间，分钟为单位  否
	 * @param uid 用户id
	 * @param lat 维度（手机经纬度）
	 * @param lng 经度
	 * @param eplat 维度（桩的经纬度）
	 * @param eplng 经度
	 */
	public void ledSwitch(Handler handler, String epCode,String type,
			String remainTime,String uid,String lat,String lng,String eplat,String eplng) {
		AjaxParams params = new AjaxParams();
		params.put("epCode", epCode);
		params.put("type", type);
		params.put("remainTime", remainTime);
		params.put("uid", uid);
		params.put("lat", lat);
		params.put("lng", lng);
		params.put("eplat", eplat);
		params.put("eplng", eplng);
		params.put("t", replaceToken);
		getData(handler, Protocol.LED, params, defaulType);
	}

	/**
	 * 获得车型
	 * cm
	 * @param handler
	 * @param carcompanyId 品牌厂家ID
	 */
	public void findCar(Handler handler, String carcompanyId) {
		AjaxParams ajaxParams = new AjaxParams();
		ajaxParams.put("carcompanyId", carcompanyId);
		ajaxParams.put("t", replaceToken);
		getData(handler, Protocol.FIND_CAR_INFO, ajaxParams, new TypeToken<BaseBean<ArrayList<CarNameBean>>>() {
		}.getType());

	}

	/**
	 * 获得品牌
	 * @param paraType
	 * @param handler
	 * cm
	 */
	public void getCarType(Handler handler) {
		AjaxParams ajaxParams = new AjaxParams();
		ajaxParams.put("paraType", "1");
		ajaxParams.put("t", replaceToken);
		getData(handler, Protocol.FIND_PARACONFIG_LIST, ajaxParams, new TypeToken<BaseBean<ArrayList<CarTypeBean>>>() {
		}.getType());

	}
	
	/**
	 * 我的收藏
	 * @param userId
	 * @param handler
	 * @param lat lng
	 * cm
	 */
	public void getMyCollectList(Handler handler,String userId,String lat,String lng) {
		AjaxParams ajaxParams = new AjaxParams();
		ajaxParams.put("userId", userId);
		ajaxParams.put("lat", lat);
		ajaxParams.put("lng", lng);
		ajaxParams.put("t", replaceToken);
		getData(handler, Protocol.GET_MYCOLLECT_LIST, ajaxParams, new TypeToken<BaseBean<ArrayList<MyCollectBean>>>() {
		}.getType());

	}
	
	/**
	 * 系统消息
	 * @param userId 用户id
	 * @param handler
	 * cm
	 */
	public void getMyNewsSystemList(Handler handler,String userId) {
		AjaxParams ajaxParams = new AjaxParams();
		ajaxParams.put("userId", userId);
		ajaxParams.put("t", replaceToken);
		getData(handler, Protocol.GET_MYNEWS_SYSTEM_LIST, ajaxParams, new TypeToken<BaseBean<ArrayList<MyNewsSystemBean>>>() {
		}.getType());

	}
	/**
	 * 系统消息详情
	 * @param mid 消息id
	 * @param handler
	 * cm
	 */
	public void getMyNewsSystemDetail(Handler handler,String mid) {
		AjaxParams ajaxParams = new AjaxParams();
		ajaxParams.put("mid", mid);
		ajaxParams.put("t", replaceToken);
		getData(handler, Protocol.GET_MYNEWS_SYSTEM_DETAIL, ajaxParams, new TypeToken<BaseBean<ArrayList<MyNewsSystemBean>>>() {
		}.getType());

	}	
	/**
	 * 提交我的反馈
	 * @param febaUserid 用户id 
	 * @param handler
	 * @param febaContent 反馈内容	
	 * cm
	 */
	public void commitMyFeedback(Handler handler,String febaUserid,String febaContent) {
		AjaxParams ajaxParams = new AjaxParams();
		ajaxParams.put("febaUserid", febaUserid);
		ajaxParams.put("febaContent", febaContent);
		ajaxParams.put("t", replaceToken);
		getData(handler, Protocol.COMMIT_MYFEEDBACK, ajaxParams,defaulType);

	}	
	/**
	 * 获取我的反馈
	 * @param userId 
	 * @param handler
	 * cm
	 */
	public void getMyNewsFeedback(Handler handler,String userId) {
		AjaxParams ajaxParams = new AjaxParams();
		ajaxParams.put("userId", userId);
		ajaxParams.put("t", replaceToken);
		getData(handler, Protocol.GET_MYNEWS_FEEDBACK_LIST, ajaxParams, new TypeToken<BaseBean<ArrayList<MyNewsFeedbackBean>>>() {
		}.getType());

	}	
	
	/**
	 * 获取我的优惠信息
	 * @param uId 
	 * @param handler
	 * cm
	 */
	public void getMyCouponInfo(Handler handler,String uId) {
		AjaxParams ajaxParams = new AjaxParams();
		ajaxParams.put("uId", uId);
		ajaxParams.put("t", replaceToken);
		getData(handler, Protocol.GET_COUPON_INFO, ajaxParams, new TypeToken<BaseBean<CouponInfoBean>>() {
		}.getType());

	}	
	/**
	 * 获取我的优惠数量
	 * @param uId 
	 * @param handler
	 * lyh
	 */
	public void getMyCouponSize(Handler handler,String uId) {
		AjaxParams ajaxParams = new AjaxParams();
		ajaxParams.put("uId", uId);
		ajaxParams.put("t", replaceToken);
		getData(handler, Protocol.GET_COUPON_SIZE, ajaxParams, new TypeToken<BaseBean<CouponSizeBean>>() {
		}.getType());

	}
	/**
	 * 获取优惠券列表
	 * @param uId 
	 * @param type--1未使用2已使用3已过期
	 * @param pageNumber 当前页码从1开始
	 * @param pageNum 每页数据量
	 * @param handler
	 * cm
	 */
	public void getMyCouponList(Handler handler,String uId,String type,String pageNumber,String pageNum) {
		AjaxParams ajaxParams = new AjaxParams();
		ajaxParams.put("uId", uId);
		ajaxParams.put("type", type);
		ajaxParams.put("pageNumber", pageNumber);
		ajaxParams.put("pageNum", pageNum);
		ajaxParams.put("t", replaceToken);
		getData(handler, Protocol.GET_COUPON_LIST, ajaxParams, new TypeToken<BaseBean<ArrayList<CouponBean>>>() {
		}.getType());

	}	
	/**
	 * 兑换优惠券
	 * @param uId 
	 * @param code--兑换码
	 * @param handler
	 * cm
	 */
	public void exchangeCoupon(Handler handler,String uId,String code) {
		AjaxParams ajaxParams = new AjaxParams();
		ajaxParams.put("uId", uId);
		ajaxParams.put("code", code);
		ajaxParams.put("t", replaceToken);
		getData(handler, Protocol.COUPON_EXCHANGE, ajaxParams, defaulType);
	}
	
	
	
	
	
	
	
	
	/**
	 * 活动列表页--动态
	 * @param pageNumber 当前页码从1开始
	 * @param handler
	 * @param pageNum 每页数据量
	 * cm
	 */
	public void getMyDynamicList(Handler handler,String pageNumber,String pageNum) {
		AjaxParams ajaxParams = new AjaxParams();
		ajaxParams.put("pageNumber", pageNumber);
		ajaxParams.put("pageNum", pageNum);
		ajaxParams.put("t", replaceToken);
		getData(handler, Protocol.GET_MYDYNAMIC_LIST, ajaxParams, new TypeToken<BaseBean<ArrayList<MyDynamicListBean>>>() {
		}.getType());

	}	
	/**
	 *急救电话
	 * @param handler
	 * cm
	 */
	public void getEmergencyCall(Handler handler) {
		AjaxParams ajaxParams = new AjaxParams();
		ajaxParams.put("t", replaceToken);
		getData(handler, Protocol.GET_EMERGENCY_CALL, ajaxParams, new TypeToken<BaseBean<ArrayList<EmergencyCallBean>>>() {
		}.getType());

	}	
	/**
	 *车俩维修
	 * @param handler
	 * @param latitude
	 * @param longitude
	 * @param pageNumber 当前页码从1开始（默认列表的话为必须项）
	 * @param pageNum 每页数据量（默认列表的话为必须项）
	 * @param kw 查询关键字（搜索的话为必须项）
	 * @param type 查询类型 1名称 2地址（搜索的话为必须项）
	 * cm
	 */
	public void getCarRepair(Handler handler,String latitude,String longitude,String pageNumber,String pageNum,String kw,String type) {
		AjaxParams ajaxParams = new AjaxParams();
		ajaxParams.put("t", replaceToken);
		ajaxParams.put("latitude", latitude);
		ajaxParams.put("longitude", longitude);
		ajaxParams.put("pageNumber", pageNumber);
		ajaxParams.put("pageNum", pageNum);
		ajaxParams.put("kw", kw);
		ajaxParams.put("type", type);
		getData(handler, Protocol.GET_CAR_REPAIR, ajaxParams, new TypeToken<BaseBean<ArrayList<CarRepairBean>>>() {
		}.getType());

	}	
	/**
	 * 获取二维码信息
	 * @param handler
	 * @param elpiElectricpilecode 桩体编号，不是桩体id
	 * @param ePHeElectricpileHeadId 枪头编号，不是枪头id
	 * cm
	 */
	public void getScanInfo(Handler handler,String elpiElectricpilecode,String ePHeElectricpileHeadId) {
		AjaxParams ajaxParams = new AjaxParams();
		ajaxParams.put("t", replaceToken);
		ajaxParams.put("elpiElectricpilecode", elpiElectricpilecode);
		ajaxParams.put("ePHeElectricpileHeadId", ePHeElectricpileHeadId);
		getData(handler, Protocol.GET_SCAN_INFO, ajaxParams, new TypeToken<BaseBean<ScanInfoBean>>() {
		}.getType());

	}	
	/**
	 * 获取数字码信息
	 * @param handler
	 * @param zCodeNum  二维码数字编码
	 * cm
	 */
	public void getzNumSelPileInfo(Handler handler,String zCodeNum) {
		AjaxParams ajaxParams = new AjaxParams();
		ajaxParams.put("t", replaceToken);
		ajaxParams.put("zCodeNum", zCodeNum);
		getData(handler, Protocol.GET_ZNum_PILE_INFO, ajaxParams, new TypeToken<BaseBean<ScanInfoBean>>() {
		}.getType());

	}
	
		
	/**
	 * 获取账号余额
	 * @param handler
	 * @param uid 用户id
	 * cm
	 */
	public void getBalance(Handler handler,String uid) {
		AjaxParams ajaxParams = new AjaxParams();
		ajaxParams.put("t", replaceToken);
		ajaxParams.put("uid", uid);
		getData(handler, Protocol.BANLANCE, ajaxParams, new TypeToken<BaseBean<BanlanceBean>>() {
		}.getType());

	}	
	/**
	 * 电桩评价添加
	 * @param handler
	 * @param epId 电桩ID
	 * @param uId 用户id
	 * @param pcId 评论id（对桩的评论为0，对评论的回复为被回复评论id）
	 * @param uName 用户名称
	 * @param epContent 评论内容
	 * @param type 1评价2留言
	 * 
	 * cm
	 */
	public void commitPileComment(Handler handler,String epId,String uId,String pcId,String uName,String epContent) {
		AjaxParams ajaxParams = new AjaxParams();
		ajaxParams.put("t", replaceToken);
		ajaxParams.put("epId", epId);
		ajaxParams.put("uId", uId);
		ajaxParams.put("pcId", pcId);
		ajaxParams.put("uName", uName);
		ajaxParams.put("epContent", epContent);
		ajaxParams.put("type", "1");
		getData(handler, Protocol.COMMIT_PILE_COMMENT, ajaxParams,defaulType);

	}	
	
	/**
	 * 电桩评分添加
	 * @param handler
	 * @param epId 电桩ID
	 * @param uId 用户id
	 * @param pcId 评论id（对桩的评论为0，对评论的回复为被回复评论id）
	 * @param uName 用户名称
	 * @param epStar 评论内容
	 * cm
	 */
	public void commitPileStar(Handler handler,String epId,String uId,String uName,String epStar) {
		AjaxParams ajaxParams = new AjaxParams();
		ajaxParams.put("t", replaceToken);
		ajaxParams.put("epId", epId);
		ajaxParams.put("uId", uId);
		ajaxParams.put("uName", uName);
		ajaxParams.put("epStar", epStar);
		getData(handler, Protocol.COMMIT_PILE_STAR, ajaxParams,defaulType);

	}	
	
	
	/**
	 * 获取alipay支付信息
	 * @author cm
	 * @param handler        
	 * @param subject 标题     是
	 * @param body  内容（使用用户id）   是
	 * @param price 订单价格   是
	 * @param userMobel  用户手机号  是
	 */
	public void getAlipayInfo(Handler handler, String subject, String body, String price, String userMobel) {
		AjaxParams params = new AjaxParams();
		params.put("subject", subject);
		params.put("body", body);
		params.put("price", price);
		params.put("userMobel", userMobel);
		params.put("t", replaceToken);
		getData(handler, Protocol.AliPayURL, params, defaulType);
	}
	/**
	 * 获取WX支付信息
	 * @author cm
	 * @param handler  
	 * @param userId  用户id  
	 * @param ipAddr 手机ip   是
	 * @param body  内容   是
	 * @param price 订单价格   是
	 * @param userMobel  用户手机号  是
	 * @param tradeType  请求类型（APP或WAP)
	 * @param chargeType 支付类型4充值 5开发票
	 * @param orderId 充值时为0，开发票时为发票
	 */
	public void getWXPrepayInfo(Handler handler, String userId,String ipAddr, String body, String price, String userMobel,String tradeType,String chargeType,String orderId) {
		AjaxParams params = new AjaxParams();
		params.put("userId", userId);
		params.put("ipAddr", ipAddr);
		params.put("body", body);
		params.put("price", price);
		params.put("userMobel", userMobel);
		params.put("tradeType", tradeType);
		params.put("chargeType", chargeType);
		params.put("orderId", orderId);
		params.put("t", replaceToken);
		getData(handler, Protocol.WeiXinPayURL, params, defaulType);
	}
	
	
	/**
	 * 获取我的充电卡
	 * @param handler
	 * @param uId 用户id
	 * cm
	 */
	public void getMyCardListInfo(Handler handler,String uId) {
		AjaxParams ajaxParams = new AjaxParams();
		ajaxParams.put("t", replaceToken);
		ajaxParams.put("uId", uId);
		getData(handler, Protocol.GET_MY_CARDLIST, ajaxParams,new TypeToken<BaseBean<ArrayList<MyCardInfo>>>() {
		}.getType());

	}		
	/**
	 * 挂失我的充电卡
	 * @param handler
	 * @param uId 用户id
	 * @param outNum
	 * cm
	 */
	public void reportLossCard(Handler handler,String uId,String outNum,String ucPayMode) {
		AjaxParams ajaxParams = new AjaxParams();
		ajaxParams.put("t", replaceToken);
		ajaxParams.put("uId", uId);
		ajaxParams.put("outNum", outNum);
		ajaxParams.put("ucPayMode", ucPayMode);
		getData(handler, Protocol.REPORTLOSS_CARD, ajaxParams,defaulType);

	}	
	/**
	 * 获取我的充电卡申请列表
	 * @param handler
	 * @param uId 用户id
	 * cm
	 */
	public void getMyCardApplyListInfo(Handler handler,String uId) {
		AjaxParams ajaxParams = new AjaxParams();
		ajaxParams.put("t", replaceToken);
		ajaxParams.put("uId", uId);
		getData(handler, Protocol.GET_MY_CARD_APPLYLIST, ajaxParams,new TypeToken<BaseBean<ArrayList<MyCardApplyInfo>>>() {
		}.getType());

	}	
	/**
	 * 申领我的充电卡
	 * @param handler
	 * @param uId 用户id
	 * @param name
	 * @param phone
	 * @param addr
	 * cm
	 */
	public void applyMyCard(Handler handler,String uId,String name,String phone,String addr) {
		AjaxParams ajaxParams = new AjaxParams();
		ajaxParams.put("t", replaceToken);
		ajaxParams.put("uId", uId);
		ajaxParams.put("name", name);
		ajaxParams.put("phone", phone);
		ajaxParams.put("addr", addr);
		getData(handler, Protocol.APPLY_MY_CARD, ajaxParams,defaulType);

	}	
	
	
	/**
	 * 广告
	 * @param handler
	 * @param location 
	 */
	public void getAdvertisement(Handler handler,String type,String location) {
		AjaxParams ajaxParams = new AjaxParams();
		ajaxParams.put("t", replaceToken);
		ajaxParams.put("type", type);
		ajaxParams.put("location", location);
		getData(handler, Protocol.ADVERTISEMENT, ajaxParams,advertisement);
	}
	/**
	 * 获取我的充电订单
	 * @param userId 用户id
	 * @param sts 状态（0：未完成 1：已完成 2：充电中）
	 * @param pageNumber 当前页码从1开始
	 * @param pageNum 每页数据量
	 */
	public void getChargeInfoList(Handler handler,String userId,String sts,String pageNumber,String pageNum) {
		AjaxParams ajaxParams = new AjaxParams();
		ajaxParams.put("userId", userId);
		ajaxParams.put("sts", sts);
		ajaxParams.put("pageNumber", pageNumber);
		ajaxParams.put("pageNum", pageNum);
		ajaxParams.put("t", replaceToken);
		getData(handler, Protocol.INDENT_PARTICULARS, ajaxParams, new TypeToken<BaseBean<ArrayList<ChargeFinishAndDoneBean>>>() {
		}.getType());

	}
	
	/**
	 * 获取我的充电订单详情
	 * @author 
	 * @param handler
	 * @param coId 订单id（不传查列表，传查详情） 	
	 */
	public void getMyChargeOrder(Handler handler,String coId) {
		AjaxParams params = new AjaxParams();
		params.put("coId", coId);
		params.put("t", replaceToken);
		getData(handler, Protocol.MYCHARGE_ORDER_DETAILS, params,new TypeToken<BaseBean<ChargeOrderDetailsBean>>() {
		}.getType());
	}
	
	public void getMyCarousel(Handler handler,String provincecode,String citycode) {
		AjaxParams params = new AjaxParams();
		params.put("provincecode", provincecode);
		params.put("citycode", citycode);
		params.put("t", replaceToken);
		getData(handler, Protocol.IMAGE_CAROUSEL, params,new TypeToken<BaseBean<ArrayList<ImageCarouselBean>>>() {
		}.getType());
	}
	
	public void getMyTextCarousel(Handler handler,String provincecode,String citycode) {
		AjaxParams params = new AjaxParams();
		params.put("provincecode", provincecode);
		params.put("citycode", citycode);
		params.put("t", replaceToken);
		getData(handler, Protocol.TEXT_CAROUSEL, params,new TypeToken<BaseBean<ArrayList<TextCarouselBean>>>() {
		}.getType());
	}
	
	public void getMyFunctionButton(Handler handler) {
		AjaxParams params = new AjaxParams();
		params.put("t", replaceToken);
		getData(handler, Protocol.FUNCTION_BUTTON, params,new TypeToken<BaseBean<ArrayList<FunctionButtonBean>>>() {
		}.getType());
	}
	
	public void getMyInformationList(Handler handler,String pageNumber,String pageNum) {
		AjaxParams params = new AjaxParams();
		params.put("pageNumber", pageNumber);
		params.put("pageNum", pageNum);
		params.put("t", replaceToken);
		getData(handler, Protocol.INFORMATION_LIST, params,new TypeToken<BaseBean<ArrayList<InformationBean>>>() {
		}.getType());
	}
	
	
	public void getMyMalfunctionList(Handler handler,String pkMeiId) {
		AjaxParams params = new AjaxParams();
		params.put("pkMeiId", pkMeiId);
		params.put("t", replaceToken);
		getData(handler, Protocol.FAULT_LIST, params,new TypeToken<BaseBean<ArrayList<MalfunctionBean>>>() {
		}.getType());
	}
	
	
	//积分相关
	/*
	 * 每日领取
	 */
	public void getEverydayGetIntegral(Handler handler,String userId,String provincecode,String citycode) {
		AjaxParams params = new AjaxParams();
		params.put("userId", userId);
		params.put("pid", provincecode);
		params.put("cid", citycode);
		params.put("t", replaceToken);
		getData(handler, Protocol.EVERY_DAY_GET_INTEGRAL, params,new TypeToken<BaseBean<EverydayGetBean>>() {
		}.getType());
	}
	/**
	 * 签到
	 * @param handler
	 * @param userId
	 * @param provincecode
	 * @param citycode
	 */
	public void getSignIn(Handler handler,String userId,String provincecode,String citycode) {
		AjaxParams params = new AjaxParams();
		params.put("userId", userId);
		params.put("pid", provincecode);
		params.put("cid", citycode);
		params.put("t", replaceToken);
		getData(handler, Protocol.SIGN_IN_GET, params,new TypeToken<BaseBean<ArrayList<EverydayGetBean>>>() {
		}.getType());
	}
	
	/**
	 * 积分详情
	 * @param handler
	 * @param userId
	 */
	public void getIntegralDetails(Handler handler,String userId,String pageNumber,String pageNum) {
		AjaxParams params = new AjaxParams();
		params.put("userId", userId);
		params.put("t", replaceToken);
		params.put("pageNumber", pageNumber);
		params.put("pageNum", pageNum);
		getData(handler, Protocol.INTEGRAL_DETAILS_LIST, params,new TypeToken<BaseBean<ArrayList<IntegralDetailBean>>>() {
		}.getType());
	}
	
	/**
	 * 个人积分
	 * @param handler
	 * @param userId
	 */
	public void getIntegralPersonage(Handler handler,String userId) {
		AjaxParams params = new AjaxParams();
		params.put("userId", userId);
		params.put("t", replaceToken);
		getData(handler, Protocol.INTEGRAL_PERSONAGE, params,new TypeToken<BaseBean<PersonIntegralBean>>() {
		}.getType());
	}
	/**
	 * 充值充电送积分比例
	 * @param handler
	 * @param userId
	 */
	public void getIntegralProportion(Handler handler,String userId,String provincecode,String citycode,String type) {
		AjaxParams params = new AjaxParams();
		params.put("userId", userId);
		params.put("pid", provincecode);
		params.put("cid", citycode);
		params.put("type", type);
		params.put("t", replaceToken);
		getData(handler, Protocol.INTEGRAL_PROPORTION, params,new TypeToken<BaseBean<ArrayList<IntegralProportionBean>>>() {
		}.getType());
	}
	/**
	 * 充值送积分
	 * @param handler
	 * @param userId
	 */
	public void getRechargeIntegral(Handler handler,String userId,String provincecode,String citycode,String amount) {
		AjaxParams params = new AjaxParams();
		params.put("userId", userId);
		params.put("pid", provincecode);
		params.put("cid", citycode);
		params.put("amount", amount);
		params.put("t", replaceToken);
		getData(handler, Protocol.INTEGRAL_RECHARGE, params,new TypeToken<BaseBean<IntegralRechargeBean>>() {
		}.getType());
	}
	/**
	 * 充电送积分
	 * @param handler
	 * @param userId
	 */
	public void getChargeIntegral(Handler handler,String userId,String provincecode,String citycode,String amount,String oid) {
		AjaxParams params = new AjaxParams();
		params.put("userId", userId);
		params.put("pid", provincecode);
		params.put("cid", citycode);
		params.put("amount", amount);
		params.put("oid", oid);
		params.put("t", replaceToken);
		getData(handler, Protocol.INTEGRAL_CHARGE, params,new TypeToken<BaseBean<ChargeIntegralBean>>() {
		}.getType());
	}
	
	/**
	 * 分享送积分
	 * @param handler
	 * @param userId
	 */
	public void getShareIntegral(Handler handler,String userId,String provincecode,String citycode,String oid) {
		AjaxParams params = new AjaxParams();
		params.put("userId", userId);
		params.put("pid", provincecode);
		params.put("cid", citycode);
		params.put("oid", oid);
		params.put("t", replaceToken);
		getData(handler, Protocol.SHARE_INTEGRAL, params,new TypeToken<BaseBean<ShareIntegralBean>>() {
		}.getType());
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
	public void modifyUserInfo(Handler handler,String pkUserinfo, String usinUsername, String usinUseraddress, String usinBirthdate, String usinPlatenumber) {
		AjaxParams params = new AjaxParams();
		params.put("pkUserinfo", pkUserinfo);
		if(!Tools.isEmptyString(usinUsername)){
			params.put("usinUsername", usinUsername);
		}
		if(!Tools.isEmptyString(usinBirthdate)){
			params.put("usinBirthdate", usinBirthdate);
		}

		if(!Tools.isEmptyString(usinPlatenumber)){
			params.put("usinPlatenumber",usinPlatenumber);
		}
		if(!Tools.isEmptyString(usinUseraddress)){
			params.put("usinUseraddress",usinUseraddress);
		}
		params.put("t", replaceToken);
		getData(handler, Protocol.MODIFY_USER_INFO, params,defaulType);
	}
	
	
	/**
	 * 活动
	 * @param handler
	 * @param userId
	 */
	public void getIntegralEvent(Handler handler,String userId,String provincecode,String citycode,String type) {
		AjaxParams params = new AjaxParams();
		params.put("userId", userId);
		params.put("pid", provincecode);
		params.put("cid", citycode);
		params.put("type", type);
		params.put("t", replaceToken);
		getData(handler, Protocol.INTEGRAL_EVENT, params,new TypeToken<BaseBean<ArrayList<IntegralEventBean>>>() {
		}.getType());
	}
}
