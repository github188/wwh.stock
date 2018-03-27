package com.bm.wanma.entity;

import java.io.Serializable;

/**
 * 车俩维修实体类
 */
public class CarRepairBean implements Serializable {
	
	private String id;//修理厂id
	private String address ;//修理厂地址
	private String name ;//修理厂名称
	private String phone;//电话
	private String distance;// 距离 
	private String longitude;// 经度
	private String latitude ;// 维度
	
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	
	
	
}
