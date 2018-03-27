package com.bm.wanma.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 我的账单列表
 * cm
 */
public class MyBillList implements Serializable {

	/* 余额 */
	private String balance;
	/* 消费 */
	private ArrayList<BillBean> consumeRecord;

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public ArrayList<BillBean> getConsumeRecord() {
		return consumeRecord;
	}

	public void setConsumeRecord(ArrayList<BillBean> consumeRecord) {
		this.consumeRecord = consumeRecord;
	}


}
