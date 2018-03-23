package com.zjhcsoft.struc.util;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zjhcsoft.struc.fetch.wrapper.HttpCommonFetcherWrapper;

public class GaoDeMapUtil {

	/*
	 * @author wjc
	 * 
	 * @param String qname
	 */
	@SuppressWarnings("deprecation")
	public static Map<String, String> GetLocation(String qname) {
		Map<String, String> map = new HashMap<String, String>();
		String url = "http://restapi.amap.com/v3/geocode/geo?key=504f7baea37afa32451ce710ea89000e&address="
				+ URLEncoder.encode(qname);
		String result = HttpCommonFetcherWrapper.httpExecute(url, "UTF-8",
				"GET");
		if (result == null) {
			return null;
		}
		JSONArray ja = JSON.parseObject(result).getJSONArray("geocodes");
		if(ja==null||ja.size()<=0){
			return null;
		}
		JSONObject jo1 = ja.getJSONObject(0);
		String province = jo1.getString("province");
		String level = jo1.getString("level");
		String location = jo1.getString("location");
		String lon = location.substring(0, location.indexOf(","));
		String lat = location.substring(location.indexOf(",") + 1);
		map.put("lng", lon);
		map.put("lat", lat);
		map.put("province", province);
		map.put("level", level);
		return map;

	}

	public static void main(String args[]) {
		GetLocation("厦门银鹭食品集团有限公司");
	}
}
