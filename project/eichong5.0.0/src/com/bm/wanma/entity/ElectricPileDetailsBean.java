package com.bm.wanma.entity;

import java.io.Serializable;

public class ElectricPileDetailsBean  implements Serializable {
	private String postPic;//电桩列表图片
	private String powerStationId ;//电站ID
	private String name;	//电站名称
	private String addr;//电站地址
	private String distance;//距离
	private String jlHeadNum;//交流枪口数
	private String zlHeadNum;//直流枪口数
	private String jlFreeHeadNum;//交流空闲枪口数
	private String zlFreeHeadNum ;//直流空闲枪口数
	private String onLineTime;//开放时间
	private String parkFee;//停车费
	private String elecPrice;//电价
	private String longitude;
	private String latitude;
	private String rateId;
	public String getPostPic() {
		return postPic;
	}

	public void setPostPic(String postPic) {
		this.postPic = postPic;
	}

	public String getPowerStationId() {
		return powerStationId;
	}
	
	public void setPowerStationId(String powerStationId) {
		this.powerStationId = powerStationId;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getJlHeadNum() {
		return jlHeadNum;
	}
	public void setJlHeadNum(String jlHeadNum) {
		this.jlHeadNum = jlHeadNum;
	}
	public String getZlHeadNum() {
		return zlHeadNum;
	}
	public void setZlHeadNum(String zlHeadNum) {
		this.zlHeadNum = zlHeadNum;
	}
	public String getJlFreeHeadNum() {
		return jlFreeHeadNum;
	}
	public void setJlFreeHeadNum(String jlFreeHeadNum) {
		this.jlFreeHeadNum = jlFreeHeadNum;
	}
	public String getZlFreeHeadNum() {
		return zlFreeHeadNum;
	}
	public void setZlFreeHeadNum(String zlFreeHeadNum) {
		this.zlFreeHeadNum = zlFreeHeadNum;
	}
	public String getOnLineTime() {
		return onLineTime;
	}
	public void setOnLineTime(String onLineTime) {
		this.onLineTime = onLineTime;
	}
	public String getParkFee() {
		return parkFee;
	}
	public void setParkFee(String parkFee) {
		this.parkFee = parkFee;
	}
	public String getElecPrice() {
		return elecPrice;
	}
	public void setElecPrice(String elecPrice) {
		this.elecPrice = elecPrice;
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

	public String getRateId() {
		return rateId;
	}

	public void setRateId(String rateId) {
		this.rateId = rateId;
	}
	
	
}
