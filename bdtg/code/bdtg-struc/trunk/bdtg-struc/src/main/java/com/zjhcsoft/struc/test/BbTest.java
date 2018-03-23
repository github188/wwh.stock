package com.zjhcsoft.struc.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zjhcsoft.struc.fetch.wrapper.HttpCommonFetcherWrapper;

public class BbTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String url ="http://10.80.10.67:8080/dataapi/jhapi/lvyou/newsdetail?newscode=28306";
		String content = HttpCommonFetcherWrapper.httpExecute(url, "UTF-8", "GET");
		System.out.println(content);
		JSONObject obj = JSON.parseObject(content);
		JSONObject obj1 = obj.getJSONObject("result");
		System.out.println(obj1.getString("Content"));
	}

}
