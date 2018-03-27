package com.bm.wanma.entity;

import java.io.Serializable;

/**
 * 积分明细
 * lyh
 */
public class IntegralDetailBean implements Serializable {
	//活动名称
	private String activity_name;
	//积分
	private String integral_value;
	//日期
	private String integral_date;
	//类型  0:获取积分 1:消耗积分
	private String direction;
	public String getActivity_name() {
		return activity_name;
	}
	public void setActivity_name(String activity_name) {
		this.activity_name = activity_name;
	}
	public String getIntegral_value() {
		return integral_value;
	}
	public void setIntegral_value(String integral_value) {
		this.integral_value = integral_value;
	}
	public String getIntegral_date() {
		return integral_date;
	}
	public void setIntegral_date(String integral_date) {
		this.integral_date = integral_date;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	

}
