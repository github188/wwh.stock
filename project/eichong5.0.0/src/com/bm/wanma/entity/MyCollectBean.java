package com.bm.wanma.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 我的收藏实体类
 * @author cm
 */
public class MyCollectBean implements Serializable {

	/*收藏id*/
	private String pk_UserCollect;
	/*用户id*/
	private String usCo_Userid;
	/*产品ID、电桩电站id*/
	private String usCo_Objectid ;
	/*类型（1电桩，2电站）*/
	private String usCo_Type;
	/*收藏时间*/
	private String usCo_AddTime;
	/*桩、电站名称*/
	private String NAME;
	/*桩、电站地址 */
	private String addr;
	/*直流枪口总数--快充*/
	private String zlHeadNum;
	/*直流空闲枪口总数*/
	private String zlFreeHeadNum;
	/*交流枪口数--慢充*/
	private String jlHeadNum;
	/*交流空闲枪口总数*/
	private String jlFreeHeadNum;
	/*1支持预约 0不支持*/
	private String poSt_IsAppoint;
	/*距离 如果不传经纬度则返回0*/
	private String distance;
	private String lng;
	private String lat;
	
	
	
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	public String getPk_UserCollect() {
		return pk_UserCollect;
	}
	public void setPk_UserCollect(String pk_UserCollect) {
		this.pk_UserCollect = pk_UserCollect;
	}
	public String getUsCo_Userid() {
		return usCo_Userid;
	}
	public void setUsCo_Userid(String usCo_Userid) {
		this.usCo_Userid = usCo_Userid;
	}
	public String getUsCo_Objectid() {
		return usCo_Objectid;
	}
	public void setUsCo_Objectid(String usCo_Objectid) {
		this.usCo_Objectid = usCo_Objectid;
	}
	public String getUsCo_Type() {
		return usCo_Type;
	}
	public void setUsCo_Type(String usCo_Type) {
		this.usCo_Type = usCo_Type;
	}
	public String getUsCo_AddTime() {
		return usCo_AddTime;
	}
	public void setUsCo_AddTime(String usCo_AddTime) {
		this.usCo_AddTime = usCo_AddTime;
	}
	
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
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
	public String getPoSt_IsAppoint() {
		return poSt_IsAppoint;
	}
	public void setPoSt_IsAppoint(String poSt_IsAppoint) {
		this.poSt_IsAppoint = poSt_IsAppoint;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
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
	
	
	
}
