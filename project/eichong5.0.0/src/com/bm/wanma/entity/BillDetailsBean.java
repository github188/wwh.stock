package com.bm.wanma.entity;

import java.io.Serializable;

/**
 * 我的账单item
 * cm
 */
public class BillDetailsBean implements Serializable {
	
	/* 消费类型*/
	private String type;
	/* 时间 */
	private String phTm;
	/* 充值金额*/
	private String mn;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPhTm() {
		return phTm;
	}
	public void setPhTm(String phTm) {
		this.phTm = phTm;
	}
	public String getMn() {
		return mn;
	}
	public void setMn(String mn) {
		this.mn = mn;
	}

}
