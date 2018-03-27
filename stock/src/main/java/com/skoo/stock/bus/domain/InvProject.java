package com.skoo.stock.bus.domain;
import com.skoo.orm.domain.BaseEntity;
import org.apache.ibatis.type.Alias;


/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Alias("InvProject")
public class InvProject extends BaseEntity {

	private static final long serialVersionUID = 1L;


	/** 项目名称**/
	private String projectName;

	/** 项目大类**/
	private String projectCategories;

	/** 项目小类**/
	private String brokendownCategory;

	/** 发布日期**/
	private java.util.Date releaseDate;

	/** 发布时间**/
	private java.util.Date releaseTime;

	/** 项目类型**/
	private String projectType;

	/** 投资方式**/
	private String investmentWay;

	/** 所属行业**/
	private String industry;

	/** 项目地点**/
	private String projectLocation;

	/** 建设该项目的优势条件**/
	private String advantageousConditionsOfTheProject;

	/** 项目有效期**/
	private java.util.Date lifeOfTheProject;

	/** 项目标示**/
	private String projectsMark;

	/** 项目性质**/
	private String projectNature;

	/** 项目状态**/
	private String projectStatus;

	/** 项目总金额**/
	private String totalAmount;

	/** 拟吸引投资总金额**/
	private String totalAmountToAttractInvestment;

	/** 预计年销售收入**/
	private String estimatedAnnualSalesIncome;

	/** 预计投资回报期**/
	private Double expectedPaybackPeriod;

	/** 预计就业人数**/
	private String expectedEmployment;

	/** 项目环保简述**/
	private String environmentalProjectBrief;

	/** 投资者条件简述**/
	private String investorRequirments;

	/** 项目背景及进展情况**/
	private String projectBackgroundAndProgress;

	/** 项目内容描述**/
	private String projectContentDescription;

	/** 合作意向或洽谈对象**/
	private String cooperationIntention;

	/** 项目单位名称**/
	private String projectUnitName;

	/** 招商项目单位简介**/
	private String introductionOfInvestmentProjects;

	/** 项目单位地址**/
	private String projectAddress;

	/** 项目联系人名称**/
	private String projectContact;

	/** 项目联系人手机**/
	private String contactPhone;

	/** 项目联系人电话**/
	private String contactCall;

	/** 项目联系人邮箱**/
	private String contactEmail;

	/** 备注**/
	private String remark;

	/** 审核状态**/
	private String approvalStatus;

	/** 排序**/
	private Integer orderBy;

	public void setProjectName(String projectName){
		this.projectName = projectName;
	}

	public String getProjectName(){
		return projectName;
	}

	public void setProjectCategories(String projectCategories){
		this.projectCategories = projectCategories;
	}

	public String getProjectCategories(){
		return projectCategories;
	}

	public void setBrokendownCategory(String brokendownCategory){
		this.brokendownCategory = brokendownCategory;
	}

	public String getBrokendownCategory(){
		return brokendownCategory;
	}

	public void setReleaseDate(java.util.Date releaseDate){
		this.releaseDate = releaseDate;
	}

	public java.util.Date getReleaseDate(){
		return releaseDate;
	}

	public void setReleaseTime(java.util.Date releaseTime){
		this.releaseTime = releaseTime;
	}

	public java.util.Date getReleaseTime(){
		return releaseTime;
	}

	public void setProjectType(String projectType){
		this.projectType = projectType;
	}

	public String getProjectType(){
		return projectType;
	}

	public void setInvestmentWay(String investmentWay){
		this.investmentWay = investmentWay;
	}

	public String getInvestmentWay(){
		return investmentWay;
	}

	public void setIndustry(String industry){
		this.industry = industry;
	}

	public String getIndustry(){
		return industry;
	}

	public void setProjectLocation(String projectLocation){
		this.projectLocation = projectLocation;
	}

	public String getProjectLocation(){
		return projectLocation;
	}

	public void setAdvantageousConditionsOfTheProject(String advantageousConditionsOfTheProject){
		this.advantageousConditionsOfTheProject = advantageousConditionsOfTheProject;
	}

	public String getAdvantageousConditionsOfTheProject(){
		return advantageousConditionsOfTheProject;
	}

	public void setLifeOfTheProject(java.util.Date lifeOfTheProject){
		this.lifeOfTheProject = lifeOfTheProject;
	}

	public java.util.Date getLifeOfTheProject(){
		return lifeOfTheProject;
	}

	public void setProjectsMark(String projectsMark){
		this.projectsMark = projectsMark;
	}

	public String getProjectsMark(){
		return projectsMark;
	}

	public void setProjectNature(String projectNature){
		this.projectNature = projectNature;
	}

	public String getProjectNature(){
		return projectNature;
	}

	public void setProjectStatus(String projectStatus){
		this.projectStatus = projectStatus;
	}

	public String getProjectStatus(){
		return projectStatus;
	}

	public void setTotalAmount(String totalAmount){
		this.totalAmount = totalAmount;
	}

	public String getTotalAmount(){
		return totalAmount;
	}

	public void setTotalAmountToAttractInvestment(String totalAmountToAttractInvestment){
		this.totalAmountToAttractInvestment = totalAmountToAttractInvestment;
	}

	public String getTotalAmountToAttractInvestment(){
		return totalAmountToAttractInvestment;
	}

	public void setEstimatedAnnualSalesIncome(String estimatedAnnualSalesIncome){
		this.estimatedAnnualSalesIncome = estimatedAnnualSalesIncome;
	}

	public String getEstimatedAnnualSalesIncome(){
		return estimatedAnnualSalesIncome;
	}

	public void setExpectedPaybackPeriod(Double expectedPaybackPeriod){
		this.expectedPaybackPeriod = expectedPaybackPeriod;
	}

	public Double getExpectedPaybackPeriod(){
		return expectedPaybackPeriod;
	}

	public void setExpectedEmployment(String expectedEmployment){
		this.expectedEmployment = expectedEmployment;
	}

	public String getExpectedEmployment(){
		return expectedEmployment;
	}

	public void setEnvironmentalProjectBrief(String environmentalProjectBrief){
		this.environmentalProjectBrief = environmentalProjectBrief;
	}

	public String getEnvironmentalProjectBrief(){
		return environmentalProjectBrief;
	}

	public void setInvestorRequirments(String investorRequirments){
		this.investorRequirments = investorRequirments;
	}

	public String getInvestorRequirments(){
		return investorRequirments;
	}

	public void setProjectBackgroundAndProgress(String projectBackgroundAndProgress){
		this.projectBackgroundAndProgress = projectBackgroundAndProgress;
	}

	public String getProjectBackgroundAndProgress(){
		return projectBackgroundAndProgress;
	}

	public void setProjectContentDescription(String projectContentDescription){
		this.projectContentDescription = projectContentDescription;
	}

	public String getProjectContentDescription(){
		return projectContentDescription;
	}

	public void setCooperationIntention(String cooperationIntention){
		this.cooperationIntention = cooperationIntention;
	}

	public String getCooperationIntention(){
		return cooperationIntention;
	}

	public void setProjectUnitName(String projectUnitName){
		this.projectUnitName = projectUnitName;
	}

	public String getProjectUnitName(){
		return projectUnitName;
	}

	public void setIntroductionOfInvestmentProjects(String introductionOfInvestmentProjects){
		this.introductionOfInvestmentProjects = introductionOfInvestmentProjects;
	}

	public String getIntroductionOfInvestmentProjects(){
		return introductionOfInvestmentProjects;
	}

	public void setProjectAddress(String projectAddress){
		this.projectAddress = projectAddress;
	}

	public String getProjectAddress(){
		return projectAddress;
	}

	public void setProjectContact(String projectContact){
		this.projectContact = projectContact;
	}

	public String getProjectContact(){
		return projectContact;
	}

	public void setContactPhone(String contactPhone){
		this.contactPhone = contactPhone;
	}

	public String getContactPhone(){
		return contactPhone;
	}

	public void setContactCall(String contactCall){
		this.contactCall = contactCall;
	}

	public String getContactCall(){
		return contactCall;
	}

	public void setContactEmail(String contactEmail){
		this.contactEmail = contactEmail;
	}

	public String getContactEmail(){
		return contactEmail;
	}

	public void setRemark(String remark){
		this.remark = remark;
	}

	public String getRemark(){
		return remark;
	}

	public void setApprovalStatus(String approvalStatus){
		this.approvalStatus = approvalStatus;
	}

	public String getApprovalStatus(){
		return approvalStatus;
	}

	public void setOrderBy(Integer orderBy){
		this.orderBy = orderBy;
	}

	public Integer getOrderBy(){
		return orderBy;
	}
}
