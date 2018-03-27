package com.bm.wanma.entity;

import java.io.Serializable;

public class ShareToThirdBean implements Serializable {

	/* 地址 */
	private String addr;
	/* 名称 */
	private String name;
	/* 经度 */
	private String lng;
	/* 纬度 */
	private String lat;
	/* 1电站2电桩 */
	private String type;
	/* 服务费 */
	private String service;
	
	
	
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	
}
