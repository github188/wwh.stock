package com.bm.wanma.entity;

import java.io.Serializable;



public class IntegralRechargeBean  implements Serializable{
	private String integralValue;//赠送的积分
    private String couponCount;//赠送的优惠券个数
    private String choiceCount;//抽奖次数
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
