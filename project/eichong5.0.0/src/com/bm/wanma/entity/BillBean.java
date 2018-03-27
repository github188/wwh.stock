package com.bm.wanma.entity;

import java.io.Serializable;

/**
 * 我的账单item
 * cm
 */
public class BillBean implements Serializable {

	/* 消费标题 */
	private String recordTitle;
	/* 消费内容 */
	private String recordContent;
	/* 消费金额 */
	private String recordMoney;
	/* 消费时间 */
	private String recordTime;

	public String getRecordTitle() {
		return recordTitle;
	}

	public void setRecordTitle(String recordTitle) {
		this.recordTitle = recordTitle;
	}

	public String getRecordContent() {
		return recordContent;
	}

	public void setRecordContent(String recordContent) {
		this.recordContent = recordContent;
	}

	public String getRecordMoney() {
		return recordMoney;
	}

	public void setRecordMoney(String recordMoney) {
		this.recordMoney = recordMoney;
	}

	public String getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
	}

}
