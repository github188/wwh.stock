package com.bm.wanma.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 我的充电卡
 * @author cm
 */
public class MyCardInfo implements Serializable {

	private String payMode;//支付方式10普通充电卡，11特殊卡·储值充电卡，12特殊卡·信用充电卡
	private String outNum;//外卡号
	private String status;//卡状态  0：正常，1：挂失 
	public String getPayMode() {
		return payMode;
	}
	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}
	public String getOutNum() {
		return outNum;
	}
	public void setOutNum(String outNum) {
		this.outNum = outNum;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
