package com.bm.wanma.entity;

import java.io.Serializable;

import com.amap.api.services.core.LatLonPoint;

public class PoiLatLngBean implements Serializable{

	private String title;
	private String lat;
	private String lng;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	

}
