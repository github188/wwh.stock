package com.bm.wanma.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 账号余额
 * @author cm
 */
public class BanlanceBean implements Serializable {

	/*账号余额*/
	private String userAB;
	/*冻结金额*/
	private String freezingAmt;
	public String getUserAB() {
		return userAB;
	}

	public void setUserAB(String userAB) {
		this.userAB = userAB;
	}

	public String getFreezingAmt() {
		return freezingAmt;
	}

	public void setFreezingAmt(String freezingAmt) {
		this.freezingAmt = freezingAmt;
	}
	
	
	
	
}
