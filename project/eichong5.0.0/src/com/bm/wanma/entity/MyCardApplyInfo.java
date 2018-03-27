package com.bm.wanma.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 我的充电卡申请信息
 * @author cm
 */
public class MyCardApplyInfo implements Serializable {

	private String name;//联系人
	private String phone;// 联系人电话
	private String addr;// 邮寄地址 
	private String appStatus;// 申请状态  0：申请中，1：申请成功 , 2:申请失败
	private String outNum;//外卡号
	private String appTime;// 申请时间
	private String cardStatus;// 卡状态 0：正常，1：挂失
	private String lossTime;// 挂失时间 只有卡在挂失状态下才显示
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getAppStatus() {
		return appStatus;
	}
	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}
	public String getOutNum() {
		return outNum;
	}
	public void setOutNum(String outNum) {
		this.outNum = outNum;
	}
	public String getAppTime() {
		return appTime;
	}
	public void setAppTime(String appTime) {
		this.appTime = appTime;
	}
	public String getCardStatus() {
		return cardStatus;
	}
	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}
	public String getLossTime() {
		return lossTime;
	}
	public void setLossTime(String lossTime) {
		this.lossTime = lossTime;
	}
	
	
	
}
