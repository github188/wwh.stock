package com.zjhcsoft.struc.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class BaiduUtil {
	public static String getjson(String s) {
		StringBuffer sb = new StringBuffer();
		try {
			URL url = new URL(s);
			URLConnection connection = url.openConnection();
			HttpURLConnection httpConn = (HttpURLConnection) connection;
			System.setProperty("sun.net.client.defaultConnectTimeout", "150000");
			System.setProperty("sun.net.client.defaultReadTimeout", "150000");
			httpConn.setRequestMethod("GET");// 设置请求方法
			connection.setDoOutput(true);// 可以输出
			InputStreamReader isr = new InputStreamReader(
					httpConn.getInputStream(), "UTF-8");

			BufferedReader br = new BufferedReader(isr);
			String temp;
			while ((temp = br.readLine()) != null) {
				temp = temp.trim();
				if (temp != null && temp.length() > 0) {
					sb.append(temp);
				}
			}
			br.close();
			isr.close();
		} catch (Exception e) {
			return null;
		}
		return sb.toString();
	}

	public static String getAdd(String name) {
		String api = " http://api.map.baidu.com/?qt=s&c=&wd=" + encode(name)
				+ "&rn=10&ie=utf-8&oue=1&res=api";

		JSONObject object1 = JSON.parseObject(getjson(api));
		JSONArray array = object1.getJSONArray("content");
		JSONObject object3 = array.getJSONObject(0);
		return object3.getString("addr");
	}

	public static String getURLContents(String region) {
		/*
		 * String
		 * strURL="http://api.map.baidu.com/place/v2/search?region="+encode
		 * (region)+ "&ak=2KxGOjRPTqhKlurM2XjlNN8A&page_size=10&scope=1"
		 * +"&page_num=0&output=json";
		 */
		String strURL = "http://api.map.baidu.com/geocoder/v2/?address="
				+ encode(region) + "&output=json&ak=2KxGOjRPTqhKlurM2XjlNN8A";
		StringBuffer sb = new StringBuffer();
		try {
			URL url = new URL(strURL);
			URLConnection connection = url.openConnection();
			HttpURLConnection httpConn = (HttpURLConnection) connection;
			System.setProperty("sun.net.client.defaultConnectTimeout", "150000");
			System.setProperty("sun.net.client.defaultReadTimeout", "150000");
			httpConn.setRequestMethod("GET");// 设置请求方法
			connection.setDoOutput(true);// 可以输出
			InputStreamReader isr = new InputStreamReader(
					httpConn.getInputStream(), "UTF-8");

			BufferedReader br = new BufferedReader(isr);
			String temp;
			while ((temp = br.readLine()) != null) {
				temp = temp.trim();
				if (temp != null && temp.length() > 0) {
					sb.append(temp);
				}
			}
			br.close();
			isr.close();
		} catch (Exception e) {
			return null;
		}
		return sb.toString();
	}

	private static String encode(String str) {
		String encode = "";
		try {
			encode = URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encode;
	}

	public static Map<String, Double> getLongitudeLatitude(String region) {
		Map<String, Double> map = new HashMap<String, Double>();
		String oridata = getURLContents(region);
		if (oridata == null) {
			return map;
		}
		System.out.println(oridata);
		JSONObject o = JSONObject.parseObject(oridata);
		JSONObject result = o.getJSONObject("result");
		if (result == null) {
			return null;
		}
		JSONObject location = result.getJSONObject("location");
		double lng = location.getDoubleValue("lng");
		double lat = location.getDoubleValue("lat");
		map.put("lng", lng);
		map.put("lat", lat);
		return map;
	}

	public static void main(String[] args) {
	}
}
