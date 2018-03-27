package com.bm.wanma.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 取消预约返回实体类
 * @author cm
 */
public class CancleBespokeBean implements Serializable {

	/*账号余额*/
	private String balance;
	/*预约金额*/
	private String consumamt;
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getConsumamt() {
		return consumamt;
	}
	public void setConsumamt(String consumamt) {
		this.consumamt = consumamt;
	}
	
	
	
	
}
