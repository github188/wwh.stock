package com.bm.wanma.entity;

import java.io.Serializable;

/**
 * 可开发票
 * cm
 */
public class EnableInvoiceBean implements Serializable {
    private String pId;//消费记录Id
    private String pMoney;//消费金额
    private String pTime;//消费时间
    private String pContent;//消费内容
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getpMoney() {
		return pMoney;
	}
	public void setpMoney(String pMoney) {
		this.pMoney = pMoney;
	}
	public String getpTime() {
		return pTime;
	}
	public void setpTime(String pTime) {
		this.pTime = pTime;
	}
	public String getpContent() {
		return pContent;
	}
	public void setpContent(String pContent) {
		this.pContent = pContent;
	}

   
}
