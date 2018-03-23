package com.zjhcsoft.struc.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {
	String url = "http://shuiwu.fynews.com.cn/Item/Show.asp?m=1&d=2532";
	String charset = "gb2312";

	public String execute() throws Exception {
		try {
			HttpPost httpPost = new HttpPost(url);
			HttpClient client = new DefaultHttpClient();
			List<NameValuePair> valuePairs = new ArrayList<NameValuePair>(1);
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(valuePairs, "GBK");
			httpPost.setEntity(formEntity);
			HttpResponse resp = client.execute(httpPost);

			HttpEntity entity = resp.getEntity();
			String respContent = EntityUtils.toString(entity, "GB2312").trim();
			httpPost.abort();
			client.getConnectionManager().shutdown();

			System.err.println(respContent);
			return respContent;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) throws Exception {
		HttpClientUtil util = new HttpClientUtil();
		util.execute();
	}
}
