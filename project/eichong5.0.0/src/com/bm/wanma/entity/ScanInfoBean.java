package com.bm.wanma.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 扫描二维码返回实体类
 * @author cm
 */
public class ScanInfoBean implements Serializable {

	/*桩体编号*/
	private String elpiElectricpilecode;
	/*电桩id*/
	private String pkElectricpile;
	/*参数属性(电桩额定功率)*/
	private String elPiPowerSize ;
	/*参数属性(电桩额定电流)*/
	private String oCurrent;
	/*电桩名称*/
	private String elpiElectricpilename;
	/*参数属性(电桩接口方式)*/
	private String elPiPowerInterface;
	/*枪头编号*/
	private String ePHeElectricpileHeadId;
	/*充电地址 */
	private String elpiElectricpileaddress;
	/*电桩枪头状态（0空闲中，3预约中，6充电中，9停用中）*/
	private String ePHe_ElectricpileHeadState;
	/*充电方式*/
	private String elPiChargingMode;
	/*连接状态，当为0时，立即充电按钮不可用*/
	private String comm_status;
	/*停车费*/
	private String parkFee;
	/*当前电价*/
	private String currentRate;
	/*服务费*/
	private String serPrice;
	/*费率ID*/
	private String rateId ;
	/*最新电价*/
	private String pPrice;
	
	
	/*预充金额*/
	private String prechargeMoney;

	
	
	
	public String getpPrice() {
		return pPrice;
	}
	public void setpPrice(String pPrice) {
		this.pPrice = pPrice;
	}
	public String getParkFee() {
		return parkFee;
	}
	public void setParkFee(String parkFee) {
		this.parkFee = parkFee;
	}
	public String getCurrentRate() {
		return currentRate;
	}
	public void setCurrentRate(String currentRate) {
		this.currentRate = currentRate;
	}
	public String getRateId() {
		return rateId;
	}
	public void setRateId(String rateId) {
		this.rateId = rateId;
	}
	public String getoCurrent() {
		return oCurrent;
	}
	public void setoCurrent(String oCurrent) {
		this.oCurrent = oCurrent;
	}
	public String getElpiElectricpilecode() {
		return elpiElectricpilecode;
	}
	public void setElpiElectricpilecode(String elpiElectricpilecode) {
		this.elpiElectricpilecode = elpiElectricpilecode;
	}
	public String getPkElectricpile() {
		return pkElectricpile;
	}
	public void setPkElectricpile(String pkElectricpile) {
		this.pkElectricpile = pkElectricpile;
	}
	public String getElPiPowerSize() {
		return elPiPowerSize;
	}
	public void setElPiPowerSize(String elPiPowerSize) {
		this.elPiPowerSize = elPiPowerSize;
	}
	public String getElpiElectricpilename() {
		return elpiElectricpilename;
	}
	public void setElpiElectricpilename(String elpiElectricpilename) {
		this.elpiElectricpilename = elpiElectricpilename;
	}
	public String getElPiPowerInterface() {
		return elPiPowerInterface;
	}
	public void setElPiPowerInterface(String elPiPowerInterface) {
		this.elPiPowerInterface = elPiPowerInterface;
	}
	public String getePHeElectricpileHeadId() {
		return ePHeElectricpileHeadId;
	}
	public void setePHeElectricpileHeadId(String ePHeElectricpileHeadId) {
		this.ePHeElectricpileHeadId = ePHeElectricpileHeadId;
	}
	public String getElpiElectricpileaddress() {
		return elpiElectricpileaddress;
	}
	public void setElpiElectricpileaddress(String elpiElectricpileaddress) {
		this.elpiElectricpileaddress = elpiElectricpileaddress;
	}
	public String getePHe_ElectricpileHeadState() {
		return ePHe_ElectricpileHeadState;
	}
	public void setePHe_ElectricpileHeadState(String ePHe_ElectricpileHeadState) {
		this.ePHe_ElectricpileHeadState = ePHe_ElectricpileHeadState;
	}
	public String getElPiChargingMode() {
		return elPiChargingMode;
	}
	public void setElPiChargingMode(String elPiChargingMode) {
		this.elPiChargingMode = elPiChargingMode;
	}
	public String getComm_status() {
		return comm_status;
	}
	public void setComm_status(String comm_status) {
		this.comm_status = comm_status;
	}
	
	
	
	public String getPrechargeMoney() {
		return prechargeMoney;
	}
	public void setPrechargeMoney(String prechargeMoney) {
		this.prechargeMoney = prechargeMoney;
	}
	public String getSerPrice() {
		return serPrice;
	}
	public void setSerPrice(String serPrice) {
		this.serPrice = serPrice;
	}
	
	
	
	
	
}
