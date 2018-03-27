package com.bm.wanma.entity;

import java.io.Serializable;

/**
 *  急救电话
 */
public class EmergencyCallBean implements Serializable {
	
	private String com_name;//公司名称
	private String com_phone;//联系电话
	
	
	public String getCom_name() {
		return com_name;
	}
	public void setCom_name(String com_name) {
		this.com_name = com_name;
	}
	public String getCom_phone() {
		return com_phone;
	}
	public void setCom_phone(String com_phone) {
		this.com_phone = com_phone;
	}
	
	
	
}
