package com.bm.wanma.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 地图锚点所有信息
 * @author cm
 */
public class AnchorAll implements Serializable {

	/*站、桩 名称*/
	private String name;
	/*站、桩  地址*/
	private String address;
	/*站、桩 type*/
	private String electricType;
	/*电桩 电站id*/
	private String electricId;
	/*锚点经纬度*/
	private String markerLng;
	private String markerLat;
	/*直流枪口总数--快充*/
	private String zlHeadNum;
	/*直流空闲枪口总数*/
	private String zlFreeHeadNum;
	/*交流枪口数--慢充*/
	private String jlHeadNum;
	/*交流空闲枪口总数*/
	private String jlFreeHeadNum;
	/*1支持预约 0不支持*/
	private String isAppoint;
	/*距离 不传经纬度时无此字段返回*/
	private String distance;
	
	
	public String getElectricType() {
		return electricType;
	}
	public void setElectricType(String electricType) {
		this.electricType = electricType;
	}
	public String getElectricId() {
		return electricId;
	}
	public void setElectricId(String electricId) {
		this.electricId = electricId;
	}
	public String getMarkerLng() {
		return markerLng;
	}
	public void setMarkerLng(String markerLng) {
		this.markerLng = markerLng;
	}
	public String getMarkerLat() {
		return markerLat;
	}
	public void setMarkerLat(String markerLat) {
		this.markerLat = markerLat;
	}
	public String getZlHeadNum() {
		return zlHeadNum;
	}
	public void setZlHeadNum(String zlHeadNum) {
		this.zlHeadNum = zlHeadNum;
	}
	public String getZlFreeHeadNum() {
		return zlFreeHeadNum;
	}
	public void setZlFreeHeadNum(String zlFreeHeadNum) {
		this.zlFreeHeadNum = zlFreeHeadNum;
	}
	public String getJlHeadNum() {
		return jlHeadNum;
	}
	public void setJlHeadNum(String jlHeadNum) {
		this.jlHeadNum = jlHeadNum;
	}
	public String getJlFreeHeadNum() {
		return jlFreeHeadNum;
	}
	public void setJlFreeHeadNum(String jlFreeHeadNum) {
		this.jlFreeHeadNum = jlFreeHeadNum;
	}
	public String getIsAppoint() {
		return isAppoint;
	}
	public void setIsAppoint(String isAppoint) {
		this.isAppoint = isAppoint;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLng() {
		return markerLng;
	}
	public void setLng(String lng) {
		this.markerLng = lng;
	}
	public String getLat() {
		return markerLat;
	}
	public void setLat(String lat) {
		this.markerLat = lat;
	}
	
	
	
}
