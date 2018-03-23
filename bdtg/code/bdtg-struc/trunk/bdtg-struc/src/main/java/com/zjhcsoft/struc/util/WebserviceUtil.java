package com.zjhcsoft.struc.util;

import java.util.HashMap;
import java.util.Map;

public class WebserviceUtil {
	/**
	 * 组装URL
	 * 
	 * @param tableName
	 *            地表水数据所在表名
	 * @param pageindex
	 *            第N页记录
	 * @param filter
	 *            过滤条件
	 */
	public static String generaterUrl(String tableName, String pageindex, String filter) {
		String appkey = "da305e05-9ed1-41a7-8e54-d085aa69dbe";
		// 应用对应秘钥
		String appsecret = "d3fdb1b6-b7b5-4b30-9063-b9f27ece181";
		// 接口服务地址（根据实际部署替换）
		String serverUrl = "http://220.191.237.103:8088/smartcity/apishell.action?";
		// 接口版本
		String v = "1.0";
		// 返回数据格式化方式
		String messageFormat = "json";
		// 批量返回最大数
		String pageSize = "1000";

		/* 下面参数每次接口调用回有所不同 */

		// 批量返回次数
		String pageIndex = pageindex;
		// 发起调用的接口名称
		String method = tableName;
		// 调用参数签名
		String sign = null;

		// 生成调用参数签名
		Map<String, String> param = new HashMap<String, String>();
		param.put("appkey", appkey);
		param.put("method", method);
		param.put("v", v);
		param.put("messageFormat", messageFormat);
		param.put("pageSize", pageSize);
		param.put("pageIndex", pageIndex);
		param.put("filter", filter);
		String url = "";
		try {
			sign = SignUtil.getSignature(param, appsecret);
			// 参数签名一并加入参数串中
			param.put("sign", sign);
			url = serverUrl + HttpUtil.buildQuery(param, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}
	
	public static void main(String args[]){
		System.out.print(generaterUrl("air_aqi_station_hour_date","0","moni_date=to_date('2015-07-09 01:00:00','yyyy-mm-dd hh24:mi:ss')"));
	}
}
