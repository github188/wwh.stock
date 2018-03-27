package com.bm.wanma.entity;

import java.io.Serializable;

/**
 * 文字轮播bean
 */
public class TextCarouselBean implements Serializable{
	
	private String pkMeiId;
	private String meiName;
	private String meiContent;
	private String meiStatus;
	private String meiType;
	private String meiBeginTime;
	private String meiendTime;
	private String meiCreatedate;
	private String meiUpdatedate;
	private String address;
	public String getPkMeiId() {
		return pkMeiId;
	}
	public void setPkMeiId(String pkMeiId) {
		this.pkMeiId = pkMeiId;
	}
	public String getMeiName() {
		return meiName;
	}
	public void setMeiName(String meiName) {
		this.meiName = meiName;
	}
	public String getMeiContent() {
		return meiContent;
	}
	public void setMeiContent(String meiContent) {
		this.meiContent = meiContent;
	}
	public String getMeiStatus() {
		return meiStatus;
	}
	public void setMeiStatus(String meiStatus) {
		this.meiStatus = meiStatus;
	}
	public String getMeiType() {
		return meiType;
	}
	public void setMeiType(String meiType) {
		this.meiType = meiType;
	}
	public String getMeiBeginTime() {
		return meiBeginTime;
	}
	public void setMeiBeginTime(String meiBeginTime) {
		this.meiBeginTime = meiBeginTime;
	}
	public String getMeiendTime() {
		return meiendTime;
	}
	public void setMeiendTime(String meiendTime) {
		this.meiendTime = meiendTime;
	}
	public String getMeiCreatedate() {
		return meiCreatedate;
	}
	public void setMeiCreatedate(String meiCreatedate) {
		this.meiCreatedate = meiCreatedate;
	}
	public String getMeiUpdatedate() {
		return meiUpdatedate;
	}
	public void setMeiUpdatedate(String meiUpdatedate) {
		this.meiUpdatedate = meiUpdatedate;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "TextCarouselBean [pkMeiId=" + pkMeiId + ", meiName=" + meiName
				+ ", meiContent=" + meiContent + ", meiStatus=" + meiStatus
				+ ", meiType=" + meiType + ", meiBeginTime=" + meiBeginTime
				+ ", meiendTime=" + meiendTime + ", meiCreatedate="
				+ meiCreatedate + ", meiUpdatedate=" + meiUpdatedate
				+ ", address=" + address + "]";
	}

}
