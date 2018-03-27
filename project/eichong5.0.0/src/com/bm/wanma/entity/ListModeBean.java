package com.bm.wanma.entity;

import java.io.Serializable;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

/*列表模式实体类 */

public class ListModeBean implements Serializable {
    private String electricId;//电桩或电站id
    private String electricType;//1：充电桩2：电站 
    private String electricName;//电桩/电站名称
    private String electricPileSum;//电桩数量
    private String electricAddress;//电桩/电站地址
    private String electricDistance;//电桩/电站距离
    private String electriChargingMode;//电桩充电方式
    private String electricPowerInterface;//电桩接口方式
    private String electricPowerSize;//电桩额定功率
    private String electricMaxElectricity;//最大电流
    private String serviceCharge  ;//服务费
    private String commentStart  ;//星级评价
    private String jlHeadNum ;//交流桩枪口总数-慢
    private String jlFreeHeadNum ;//交流桩空闲枪口总数
    private String zlHeadNum  ;//直流桩枪口总数
    private String zlFreeHeadNum  ;//直流桩空闲枪口总数-快
    private String electricLongitude;//经度
    private String electricLatitude;//维度
    private String isAppoint;//1支持预约，其他不支持
    private String electricUse;//
    private String electricImage;//
    private String currentRate;
    
	//必须包含这个默认的构造方法，否则在进行数据查找时，会报错
	public ListModeBean() {
		
	}

	
	
	
	public String getCurrentRate() {
		return currentRate;
	}




	public void setCurrentRate(String currentRate) {
		this.currentRate = currentRate;
	}




	public String getIsAppoint() {
		return isAppoint;
	}

	public void setIsAppoint(String isAppoint) {
		this.isAppoint = isAppoint;
	}

	public String getElectricId() {
		return electricId;
	}

	public void setElectricId(String electricId) {
		this.electricId = electricId;
	}

	public String getElectricType() {
		return electricType;
	}

	public void setElectricType(String electricType) {
		this.electricType = electricType;
	}

	public String getElectricName() {
		return electricName;
	}

	public void setElectricName(String electricName) {
		this.electricName = electricName;
	}

	public String getElectricPileSum() {
		return electricPileSum;
	}

	public void setElectricPileSum(String electricPileSum) {
		this.electricPileSum = electricPileSum;
	}

	public String getElectricAddress() {
		return electricAddress;
	}

	public void setElectricAddress(String electricAddress) {
		this.electricAddress = electricAddress;
	}

	public String getElectricDistance() {
		return electricDistance;
	}

	public void setElectricDistance(String electricDistance) {
		this.electricDistance = electricDistance;
	}

	public String getElectriChargingMode() {
		return electriChargingMode;
	}

	public void setElectriChargingMode(String electriChargingMode) {
		this.electriChargingMode = electriChargingMode;
	}

	public String getElectricPowerInterface() {
		return electricPowerInterface;
	}

	public void setElectricPowerInterface(String electricPowerInterface) {
		this.electricPowerInterface = electricPowerInterface;
	}

	public String getElectricPowerSize() {
		return electricPowerSize;
	}

	public void setElectricPowerSize(String electricPowerSize) {
		this.electricPowerSize = electricPowerSize;
	}

	public String getElectricMaxElectricity() {
		return electricMaxElectricity;
	}

	public void setElectricMaxElectricity(String electricMaxElectricity) {
		this.electricMaxElectricity = electricMaxElectricity;
	}

	public String getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(String serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	public String getCommentStart() {
		return commentStart;
	}

	public void setCommentStart(String commentStart) {
		this.commentStart = commentStart;
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

	public String getElectricLongitude() {
		return electricLongitude;
	}

	public void setElectricLongitude(String electricLongitude) {
		this.electricLongitude = electricLongitude;
	}

	public String getElectricLatitude() {
		return electricLatitude;
	}

	public void setElectricLatitude(String electricLatitude) {
		this.electricLatitude = electricLatitude;
	}

	public String getElectricUse() {
		return electricUse;
	}

	public void setElectricUse(String electricUse) {
		this.electricUse = electricUse;
	}

	public String getElectricImage() {
		return electricImage;
	}

	public void setElectricImage(String electricImage) {
		this.electricImage = electricImage;
	}
	
  
  
}
