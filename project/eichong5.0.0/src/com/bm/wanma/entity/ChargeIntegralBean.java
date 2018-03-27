package com.bm.wanma.entity;

import java.io.Serializable;

/**
 * 充电送积分
 * lyh
 */
public class ChargeIntegralBean implements Serializable {
	//赠送的积分
	private String integralValue;
	//赠送的优惠券个数
	private String couponCount;
	//抽奖次数
	private String choiceCount;
	public String getIntegralValue() {
		return integralValue;
	}
	public void setIntegralValue(String integralValue) {
		this.integralValue = integralValue;
	}
	public String getCouponCount() {
		return couponCount;
	}
	public void setCouponCount(String couponCount) {
		this.couponCount = couponCount;
	}
	public String getChoiceCount() {
		return choiceCount;
	}
	public void setChoiceCount(String choiceCount) {
		this.choiceCount = choiceCount;
	}	

	
	
}
