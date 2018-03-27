package com.bm.wanma.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 地图锚点站、桩
 * @author cm
 */
public class AnchorSummary implements Serializable {

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
	
	
	
}
