package com.bm.wanma.net;

import java.io.Serializable;

import android.text.Html;
/**
 * Http返回结果
 * @author jiangshihua 2014年8月25日
 *
 */
public class HttpStringResult implements Serializable{
	private static final long serialVersionUID = -3499016800119861019L;
	public String result;
	/**
	 * HTTP状态码
	 */
	public int resCode = -1;
	public String msg;
	public void setErrorMsg(String msg){
		this.msg = msg;
	}
	
	public void setResult(String xml) {
		//this.result = Html.fromHtml(xml).toString();
		this.result = xml;
	}
}
