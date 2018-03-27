package com.bm.wanma.entity;

import java.io.Serializable;
/* cm
 * 充电订单实体类 */
public class MyOrderParticularsBean implements Serializable {

	/*订单编号 */
	private String  chOr_Code ;
	/* 订单主键*/
	private String pk_ChargingOrder;
	/* 充电开始时间  2015-11-334 18:43:54 */
	private String begin_charge_time; 
	/* 结算时间 */
	private String chOr_Createdate;
	/*电桩编号 */
	private String elPi_ElectricPileCode;
	/* 电费总额 */
	private String chOr_ChargeMoney;
	/* 桩体名称 */
	private String elPi_ElectricPileName;
	/* 充电结束时间 */
	private String end_charge_time;
	/*枪编号*/
	private String headCode ;
	/* 总电量*/
	private String chOr_QuantityElectricity;
	/* 枪口序号 */
	private String chOr_Muzzle;
	/* 服务费总额 */
	private String chOr_ServiceMoney;
	/* 电桩编号*/
	private String chOr_PileNumber	;
	/* 充电时间段 */
	private String chOr_TimeQuantum;
	/*订单状态 1：待支付 2：支付成功 3 完成操作 （1状态下需要支付  3完成未结算）4异常订单5.临时结算*/
	private String chOr_ChargingStatus;
	private String status;
	/* 桩体地址 */
	private String elPi_ElectricPileAddress;
	/*优惠券抵扣金额 */
	private String couMoney ;

	
	public String getCouMoney() {
		return couMoney;
	}
	public void setCouMoney(String couMoney) {
		this.couMoney = couMoney;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getElPi_ElectricPileCode() {
		return elPi_ElectricPileCode;
	}
	public void setElPi_ElectricPileCode(String elPi_ElectricPileCode) {
		this.elPi_ElectricPileCode = elPi_ElectricPileCode;
	}
	public String getHeadCode() {
		return headCode;
	}
	public void setHeadCode(String headCode) {
		this.headCode = headCode;
	}
	public String getChOr_ChargeMoney() {
		return chOr_ChargeMoney;
	}
	public void setChOr_ChargeMoney(String chOr_ChargeMoney) {
		this.chOr_ChargeMoney = chOr_ChargeMoney;
	}
	public String getElPi_ElectricPileName() {
		return elPi_ElectricPileName;
	}
	public void setElPi_ElectricPileName(String elPi_ElectricPileName) {
		this.elPi_ElectricPileName = elPi_ElectricPileName;
	}
	public String getElPi_ElectricPileAddress() {
		return elPi_ElectricPileAddress;
	}
	public void setElPi_ElectricPileAddress(String elPi_ElectricPileAddress) {
		this.elPi_ElectricPileAddress = elPi_ElectricPileAddress;
	}
	public String getBegin_charge_time() {
		return begin_charge_time;
	}
	public void setBegin_charge_time(String begin_charge_time) {
		this.begin_charge_time = begin_charge_time;
	}
	public String getEnd_charge_time() {
		return end_charge_time;
	}
	public void setEnd_charge_time(String end_charge_time) {
		this.end_charge_time = end_charge_time;
	}
	public String getChOr_Code() {
		return chOr_Code;
	}
	public void setChOr_Code(String chOr_Code) {
		this.chOr_Code = chOr_Code;
	}
	public String getPk_ChargingOrder() {
		return pk_ChargingOrder;
	}
	public void setPk_ChargingOrder(String pk_ChargingOrder) {
		this.pk_ChargingOrder = pk_ChargingOrder;
	}
	public String getChOr_QuantityElectricity() {
		return chOr_QuantityElectricity;
	}
	public void setChOr_QuantityElectricity(String chOr_QuantityElectricity) {
		this.chOr_QuantityElectricity = chOr_QuantityElectricity;
	}
	public String getChOr_Muzzle() {
		return chOr_Muzzle;
	}
	public void setChOr_Muzzle(String chOr_Muzzle) {
		this.chOr_Muzzle = chOr_Muzzle;
	}
	public String getChOr_ServiceMoney() {
		return chOr_ServiceMoney;
	}
	public void setChOr_ServiceMoney(String chOr_ServiceMoney) {
		this.chOr_ServiceMoney = chOr_ServiceMoney;
	}
	public String gethOr_ChargeMoney() {
		return chOr_ChargeMoney;
	}
	public void sethOr_ChargeMoney(String hOr_ChargeMoney) {
		this.chOr_ChargeMoney = hOr_ChargeMoney;
	}
	public String getChOr_PileNumber() {
		return chOr_PileNumber;
	}
	public void setChOr_PileNumber(String chOr_PileNumber) {
		this.chOr_PileNumber = chOr_PileNumber;
	}
	public String getChOr_TimeQuantum() {
		return chOr_TimeQuantum;
	}
	public void setChOr_TimeQuantum(String chOr_TimeQuantum) {
		this.chOr_TimeQuantum = chOr_TimeQuantum;
	}
	public String getChOr_Createdate() {
		return chOr_Createdate;
	}
	public void setChOr_Createdate(String chOr_Createdate) {
		this.chOr_Createdate = chOr_Createdate;
	}
	public String getChOr_ChargingStatus() {
		return chOr_ChargingStatus;
	}
	public void setChOr_ChargingStatus(String chOr_ChargingStatus) {
		this.chOr_ChargingStatus = chOr_ChargingStatus;
	}
	
	

}
