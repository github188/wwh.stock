package com.bm.wanma.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 电桩详情 返回字段
 * @author cm
 */
public class ElectricPileBean implements Serializable {
	
	private String electricPileImage;//电桩列表图片
	private String electricPileName ;//电桩名称 
	private String powerUser;//电桩用途 3电动车 4电动自行车 13多功能
	private String electricPileState;//电桩状态 (10-离线15-上线）（非智能，智能）
	private String electricPileNo;//电桩编号
	private String electricPileChargingMode;//5 直流桩（快充） 14 交流桩（慢充）
	private String electricPileParam;//参数属性
	private String electricPileAdress;//电桩地址
	private String electricPileRemark;//电桩备注
	private String electricPowerSize;//功率
	private String electricPowerInterface;//7国标 19美标 20欧标
	private String electricPileTell;//电桩电话
	private String onlineTime;//开放时间
	private String ownerCompany;//电桩所属 0其他，1爱充网，2国网，3特斯拉
	private String electricPileCommentSum;//电桩评论总数
	private String electricPileCommentStar;//电桩星评等级
	private String electricPileCommentUser;
	private String electricPileCommentContent;	
	private String commentList;
	private String jlHeadNum;//交流桩枪口总数
	private String jlFreeHeadNum;//交流桩空闲枪口总数
	private String zlHeadNum;//直流桩枪口总数
	private String zlFreeHeadNum;//直流桩空闲枪口总数
	private String isCollect;//是否收藏
	private String raIn_ReservationRate;//预约单价
	private String raIn_ServiceCharge;//服务费
	private String currentRate;//电费
	private String distance;//距离
	private String comm_status;//连接状态 0断开，1连接（0时即便是智能桩也不能操作）
	private String elpiLongitude;//经纬度
	private String elpiLatitude;
	private String rateId;//费率id
	private String haveLine;// 是否有枪 0：没有,1：有
	private ArrayList<PileHead> pileHeadList;
	
	
	
	public String getHaveLine() {
		return haveLine;
	}
	public void setHaveLine(String haveLine) {
		this.haveLine = haveLine;
	}
	public String getCurrentRate() {
		return currentRate;
	}
	public void setCurrentRate(String currentRate) {
		this.currentRate = currentRate;
	}
	public String getRateId() {
		return rateId;
	}
	public void setRateId(String rateId) {
		this.rateId = rateId;
	}
	public String getElectricPileImage() {
		return electricPileImage;
	}
	public void setElectricPileImage(String electricPileImage) {
		this.electricPileImage = electricPileImage;
	}
	public String getElectricPileName() {
		return electricPileName;
	}
	public void setElectricPileName(String electricPileName) {
		this.electricPileName = electricPileName;
	}
	public String getPowerUser() {
		return powerUser;
	}
	public void setPowerUser(String powerUser) {
		this.powerUser = powerUser;
	}
	public String getElectricPileState() {
		return electricPileState;
	}
	public void setElectricPileState(String electricPileState) {
		this.electricPileState = electricPileState;
	}
	public String getElectricPileNo() {
		return electricPileNo;
	}
	public void setElectricPileNo(String electricPileNo) {
		this.electricPileNo = electricPileNo;
	}
	public String getElectricPileChargingMode() {
		return electricPileChargingMode;
	}
	public void setElectricPileChargingMode(String electricPileChargingMode) {
		this.electricPileChargingMode = electricPileChargingMode;
	}
	public String getElectricPileParam() {
		return electricPileParam;
	}
	public void setElectricPileParam(String electricPileParam) {
		this.electricPileParam = electricPileParam;
	}
	public String getElectricPileAdress() {
		return electricPileAdress;
	}
	public void setElectricPileAdress(String electricPileAdress) {
		this.electricPileAdress = electricPileAdress;
	}
	public String getElectricPileRemark() {
		return electricPileRemark;
	}
	public void setElectricPileRemark(String electricPileRemark) {
		this.electricPileRemark = electricPileRemark;
	}
	public String getElectricPowerSize() {
		return electricPowerSize;
	}
	public void setElectricPowerSize(String electricPowerSize) {
		this.electricPowerSize = electricPowerSize;
	}
	public String getElectricPowerInterface() {
		return electricPowerInterface;
	}
	public void setElectricPowerInterface(String electricPowerInterface) {
		this.electricPowerInterface = electricPowerInterface;
	}
	public String getElectricPileTell() {
		return electricPileTell;
	}
	public void setElectricPileTell(String electricPileTell) {
		this.electricPileTell = electricPileTell;
	}
	public String getOnlineTime() {
		return onlineTime;
	}
	public void setOnlineTime(String onlineTime) {
		this.onlineTime = onlineTime;
	}
	public String getOwnerCompany() {
		return ownerCompany;
	}
	public void setOwnerCompany(String ownerCompany) {
		this.ownerCompany = ownerCompany;
	}
	public String getElectricPileCommentSum() {
		return electricPileCommentSum;
	}
	public void setElectricPileCommentSum(String electricPileCommentSum) {
		this.electricPileCommentSum = electricPileCommentSum;
	}
	public String getElectricPileCommentStar() {
		return electricPileCommentStar;
	}
	public void setElectricPileCommentStar(String electricPileCommentStar) {
		this.electricPileCommentStar = electricPileCommentStar;
	}
	public String getElectricPileCommentUser() {
		return electricPileCommentUser;
	}
	public void setElectricPileCommentUser(String electricPileCommentUser) {
		this.electricPileCommentUser = electricPileCommentUser;
	}
	public String getElectricPileCommentContent() {
		return electricPileCommentContent;
	}
	public void setElectricPileCommentContent(String electricPileCommentContent) {
		this.electricPileCommentContent = electricPileCommentContent;
	}
	public String getCommentList() {
		return commentList;
	}
	public void setCommentList(String commentList) {
		this.commentList = commentList;
	}
	public String getJlHeadNum() {
		return jlHeadNum;
	}
	public void setJlHeadNum(String jlHeadNum) {
		this.jlHeadNum = jlHeadNum;
	}
	public String getJlFreeHeadNum() {
		return jlFreeHeadNum;
	}
	public void setJlFreeHeadNum(String jlFreeHeadNum) {
		this.jlFreeHeadNum = jlFreeHeadNum;
	}
	public String getZlHeadNum() {
		return zlHeadNum;
	}
	public void setZlHeadNum(String zlHeadNum) {
		this.zlHeadNum = zlHeadNum;
	}
	public String getZlFreeHeadNum() {
		return zlFreeHeadNum;
	}
	public void setZlFreeHeadNum(String zlFreeHeadNum) {
		this.zlFreeHeadNum = zlFreeHeadNum;
	}
	public String getIsCollect() {
		return isCollect;
	}
	public void setIsCollect(String isCollect) {
		this.isCollect = isCollect;
	}
	public String getRaIn_ReservationRate() {
		return raIn_ReservationRate;
	}
	public void setRaIn_ReservationRate(String raIn_ReservationRate) {
		this.raIn_ReservationRate = raIn_ReservationRate;
	}
	public String getRaIn_ServiceCharge() {
		return raIn_ServiceCharge;
	}
	public void setRaIn_ServiceCharge(String raIn_ServiceCharge) {
		this.raIn_ServiceCharge = raIn_ServiceCharge;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getComm_status() {
		return comm_status;
	}
	public void setComm_status(String comm_status) {
		this.comm_status = comm_status;
	}
	public String getElpiLongitude() {
		return elpiLongitude;
	}
	public void setElpiLongitude(String elpiLongitude) {
		this.elpiLongitude = elpiLongitude;
	}
	public String getElpiLatitude() {
		return elpiLatitude;
	}
	public void setElpiLatitude(String elpiLatitude) {
		this.elpiLatitude = elpiLatitude;
	}
	public ArrayList<PileHead> getPileHeadList() {
		return pileHeadList;
	}
	public void setPileHeadList(ArrayList<PileHead> pileHeadList) {
		this.pileHeadList = pileHeadList;
	}
	
	
}
