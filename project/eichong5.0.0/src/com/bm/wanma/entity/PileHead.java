package com.bm.wanma.entity;

import java.io.Serializable;

/**
 * @author cm
 * 电桩枪头列表
 */
public class PileHead implements Serializable{

	private String pileHeadId;//枪头id
	private String pileHeadName;//枪头名称
	private String pileHeadState;//电桩枪头状态（0空闲中，3预约中，6充电中，9停用中）
	private String headNum;//电桩枪头编号
	private String parkNum;//枪头对应的车位号
	public String getPileHeadId() {
		return pileHeadId;
	}
	public void setPileHeadId(String pileHeadId) {
		this.pileHeadId = pileHeadId;
	}
	public String getPileHeadName() {
		return pileHeadName;
	}
	public void setPileHeadName(String pileHeadName) {
		this.pileHeadName = pileHeadName;
	}
	public String getPileHeadState() {
		return pileHeadState;
	}
	public void setPileHeadState(String pileHeadState) {
		this.pileHeadState = pileHeadState;
	}
	public String getHeadNum() {
		return headNum;
	}
	public void setHeadNum(String headNum) {
		this.headNum = headNum;
	}
	public String getParkNum() {
		return parkNum;
	}
	public void setParkNum(String parkNum) {
		this.parkNum = parkNum;
	}
	

}
