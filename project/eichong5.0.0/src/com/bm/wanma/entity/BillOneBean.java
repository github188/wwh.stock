package com.bm.wanma.entity;

import java.io.Serializable;

/**
 * 我的账单item
 * cm
 */
public class BillOneBean implements Serializable {
	/* 电量 */
	private String qt;
	/* 电费*/
	private String mn;
	/* 充值 */
	private String cz;
	/* 优惠多少钱 */
	private String cp;
	/* 月数 */
	private String mt;
	public String getQt() {
		return qt;
	}
	public void setQt(String qt) {
		this.qt = qt;
	}
	public String getMn() {
		return mn;
	}
	public void setMn(String mn) {
		this.mn = mn;
	}
	public String getCz() {
		return cz;
	}
	public void setCz(String cz) {
		this.cz = cz;
	}
	public String getCp() {
		return cp;
	}
	public void setCp(String cp) {
		this.cp = cp;
	}
	public String getMt() {
		return mt;
	}
	public void setMt(String mt) {
		this.mt = mt;
	}


}
