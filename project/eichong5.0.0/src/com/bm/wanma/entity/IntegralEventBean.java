package com.bm.wanma.entity;

import java.io.Serializable;

/**
 * 充电送积分
 * lyh
 */
public class IntegralEventBean implements Serializable {
	//活动名称
	private String activity_name;
	private String activity_id;
	private String point;
	private String city_id;
	private String hasshared;
	public String getActivity_name() {
		return activity_name;
	}
	public void setActivity_name(String activity_name) {
		this.activity_name = activity_name;
	}
	public String getActivity_id() {
		return activity_id;
	}
	public void setActivity_id(String activity_id) {
		this.activity_id = activity_id;
	}
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
	}
	public String getCity_id() {
		return city_id;
	}
	public void setCity_id(String city_id) {
		this.city_id = city_id;
	}
	public String getHasshared() {
		return hasshared;
	}
	public void setHasshared(String hasshared) {
		this.hasshared = hasshared;
	}
	
	
}
