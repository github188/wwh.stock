package com.bm.wanma.entity;

import java.io.Serializable;

/*地图模式实体类 */

public class MapModeBean implements Serializable {
	private static final long serialVersionUID = -3224864416259424059L;
	private String pkPowerStation;//电站id
    private String poStLatitude;// 纬度
    private String poStLongitude;//经度
    private String poStName;//充电点名称
    private String PoStAddress;//充电点地址
    private String dc;//含有空闲的直流枪头
    private String ac;//含有空闲的交流枪头
    private String currentRate;//电价
    private String distance;//距离当前位置，单位m

	public String getPkPowerStation() {
		return pkPowerStation;
	}
	public void setPkPowerStation(String pkPowerStation) {
		this.pkPowerStation = pkPowerStation;
	}
	public String getPoStLatitude() {
		return poStLatitude;
	}
	public void setPoStLatitude(String poStLatitude) {
		this.poStLatitude = poStLatitude;
	}
	public String getPoStLongitude() {
		return poStLongitude;
	}
	public void setPoStLongitude(String poStLongitude) {
		this.poStLongitude = poStLongitude;
	}
	public String getPoStName() {
		return poStName;
	}
	public void setPoStName(String poStName) {
		this.poStName = poStName;
	}
	public String getPoStAddress() {
		return PoStAddress;
	}
	public void setPoStAddress(String poStAddress) {
		PoStAddress = poStAddress;
	}
	public String getDc() {
		return dc;
	}
	public void setDc(String dc) {
		this.dc = dc;
	}
	public String getAc() {
		return ac;
	}
	public void setAc(String ac) {
		this.ac = ac;
	}
	public String getCurrentRate() {
		return currentRate;
	}
	public void setCurrentRate(String currentRate) {
		this.currentRate = currentRate;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	
  

  
}
