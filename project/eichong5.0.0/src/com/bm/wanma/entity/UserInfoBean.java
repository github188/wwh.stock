package com.bm.wanma.entity;

import java.io.Serializable;

/**
 * 用户详细信息
 * cm
 */

public class UserInfoBean implements Serializable {

	/* 用户ID */
	private String pkUserId;
	/* 用户头像 */
	private String userImage;
	/* 用户真实姓名 */
	private String userRealName;
	/* 用户昵称 */
	private String userNickName;
	/* 性别  0 男 1 女 */
	private String userSex;
	/* 生日 2015.01.04*/
	private String userBrithy;
	/* IC卡号 */
	private String userIcNo;
	/* 充电卡号 */
	private String chargeCard;
	/* 充电卡号 */
	private String userCardNo;
	/* 手机号 */
	private String userTel;
	/* 邮箱 */
	private String userMail;
	/* 驾驶证号 */
	private String userDriveNo;
	/* 品牌车型 */
	private String userCarType;
	/*品牌车型名称 */
	private String userCarTypeName;
	/* 用户积分 */
	private String userIntegral;
	/* 用户类型 1-普通 2-商户 */
	private String userType;
	/* 用户余额 */
	private String userAB;
	/* 省编码 */
	private String pCode; 
	/* 市编码 */
	private String cCode; 
	/* 区县编码 */
	private String aCode; 
	/* 地址 */
	private String address; 
	/*车牌号*/
	private String plateNum;
	/*车架号*/
	private String vehicleNum;
	private String applyCard;// 是否申请充电卡 1已申请2已发放
	
	
	
	public String getApplyCard() {
		return applyCard;
	}
	public void setApplyCard(String applyCard) {
		this.applyCard = applyCard;
	}
	public String getChargeCard() {
		return chargeCard;
	}
	public void setChargeCard(String chargeCard) {
		this.chargeCard = chargeCard;
	}
	public String getUserCardNo() {
		return userCardNo;
	}
	public void setUserCardNo(String userCardNo) {
		this.userCardNo = userCardNo;
	}
	public String getUserAB() {
		return userAB;
	}
	public void setUserAB(String userAB) {
		this.userAB = userAB;
	}
	public String getpCode() {
		return pCode;
	}
	public void setpCode(String pCode) {
		this.pCode = pCode;
	}
	public String getcCode() {
		return cCode;
	}
	public void setcCode(String cCode) {
		this.cCode = cCode;
	}
	public String getaCode() {
		return aCode;
	}
	public void setaCode(String aCode) {
		this.aCode = aCode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getUserBlance() {
		return userAB;
	}
	public void setUserBlance(String userBlance) {
		this.userAB = userBlance;
	}
	public String getUserCarTypeName() {
		return userCarTypeName;
	}
	public void setUserCarTypeName(String userCarTypeName) {
		this.userCarTypeName = userCarTypeName;
	}
	public String getPkUserId() {
		return pkUserId;
	}
	public void setPkUserId(String pkUserId) {
		this.pkUserId = pkUserId;
	}
	public String getUserImage() {
		return userImage;
	}
	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}
	public String getUserRealName() {
		return userRealName;
	}
	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}
	public String getUserNickName() {
		return userNickName;
	}
	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}
	public String getUserSex() {
		return userSex;
	}
	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}
	public String getUserBrithy() {
		return userBrithy;
	}
	public void setUserBrithy(String userBrithy) {
		this.userBrithy = userBrithy;
	}
	public String getUserIcNo() {
		return userIcNo;
	}
	public void setUserIcNo(String userIcNo) {
		this.userIcNo = userIcNo;
	}
	public String getUserTel() {
		return userTel;
	}
	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}
	public String getUserMail() {
		return userMail;
	}
	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}
	public String getUserDriveNo() {
		return userDriveNo;
	}
	public void setUserDriveNo(String userDriveNo) {
		this.userDriveNo = userDriveNo;
	}
	public String getUserCarType() {
		return userCarType;
	}
	public void setUserCarType(String userCarType) {
		this.userCarType = userCarType;
	}
	public String getUserIntegral() {
		return userIntegral;
	}
	public void setUserIntegral(String userIntegral) {
		this.userIntegral = userIntegral;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getPlateNum() {
		return plateNum;
	}
	public void setPlateNum(String plateNum) {
		this.plateNum = plateNum;
	}
	public String getVehicleNum() {
		return vehicleNum;
	}
	public void setVehicleNum(String vehicleNum) {
		this.vehicleNum = vehicleNum;
	}

	

}
