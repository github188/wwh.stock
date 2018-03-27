package com.bm.wanma.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 活动列表--动态-实体类
 * @author cm
 */
public class MyDynamicListBean implements Serializable {

	/*广告id*/
	private String adId;
	/*广告URL地址*/
	private String adUrl;
	/*广告名称*/
	private String adName;
	/*广告发布时间*/
	private String adDate;
	/*广告标题*/
	private String adDesc ;
	/*广告图片url地址*/
	private String dynamicPic;
	public String getAdId() {
		return adId;
	}
	public void setAdId(String adId) {
		this.adId = adId;
	}
	public String getAdUrl() {
		return adUrl;
	}
	public void setAdUrl(String adUrl) {
		this.adUrl = adUrl;
	}
	public String getAdName() {
		return adName;
	}
	public void setAdName(String adName) {
		this.adName = adName;
	}
	public String getAdDate() {
		return adDate;
	}
	public void setAdDate(String adDate) {
		this.adDate = adDate;
	}
	public String getAdDesc() {
		return adDesc;
	}
	public void setAdDesc(String adDesc) {
		this.adDesc = adDesc;
	}
	public String getDynamicPic() {
		return dynamicPic;
	}
	public void setDynamicPic(String dynamicPic) {
		this.dynamicPic = dynamicPic;
	}
	@Override
	public String toString() {
		return "MyDynamicListBean [adId=" + adId + ", adUrl=" + adUrl
				+ ", adName=" + adName + ", adDate=" + adDate + ", adDesc="
				+ adDesc + ", dynamicPic=" + dynamicPic + "]";
	}
	
}
