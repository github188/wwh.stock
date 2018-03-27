package com.bm.wanma.entity;

import java.io.Serializable;

public class AdvertisementBean implements Serializable {

	private String adId;
	private String adGoto;
	private String beginTime;
	private String endTime;
	private String adLocation;
	private String adTime;
	private String adUrl;
	private String adName;
	private String advertPic;
	private String adType;
	public String getAdId() {
		return adId;
	}
	public void setAdId(String adId) {
		this.adId = adId;
	}
	public String getAdGoto() {
		return adGoto;
	}
	public void setAdGoto(String adGoto) {
		this.adGoto = adGoto;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getAdLocation() {
		return adLocation;
	}
	public void setAdLocation(String adLocation) {
		this.adLocation = adLocation;
	}
	public String getAdTime() {
		return adTime;
	}
	public void setAdTime(String adTime) {
		this.adTime = adTime;
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
	public String getAdvertPic() {
		return advertPic;
	}
	public void setAdvertPic(String advertPic) {
		this.advertPic = advertPic;
	}
	public String getAdType() {
		return adType;
	}
	public void setAdType(String adType) {
		this.adType = adType;
	}
	@Override
	public String toString() {
		return "AdvertisementBean [adId=" + adId + ", adGoto=" + adGoto
				+ ", beginTime=" + beginTime + ", endTime=" + endTime
				+ ", adLocation=" + adLocation + ", adTime=" + adTime
				+ ", adUrl=" + adUrl + ", adName=" + adName + ", advertPic="
				+ advertPic + ", adType=" + adType + "]";
	}
}
