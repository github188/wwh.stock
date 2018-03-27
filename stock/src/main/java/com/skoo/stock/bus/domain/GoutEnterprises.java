package com.skoo.stock.bus.domain;
import com.skoo.orm.domain.BaseEntity;
import org.apache.ibatis.type.Alias;

import java.util.Date;


/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Alias("GoutEnterprises")
public class GoutEnterprises extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
				
	/** 投资项目编号**/
	private String numberOfInvestmentProjects;
			
	/** 申请模式**/
	private String applicationMode;
			
	/** 原**/
	private String former;
			
	/** 现改为**/
	private String nowChanged;
			
	/** 变更年份**/
	private String yearChange;
			
	/** 境内投资主体名称**/
	private String domesticInvestorName;
			
	/** 组织机构代码证号**/
	private String organizationCodeNo;
			
	/** 法定代表人**/
	private String legalRepresentative;
			
	/** 注册资金（万元）**/
	private String registeredCapital;
			
	/** 办公地址**/
	private String officeAddress;
			
	/** 城市**/
	private String domesticCity;
			
	/** 区县**/
	private String county;
			
	/** 联系人姓名**/
	private String contactName;
			
	/** 联系人座机**/
	private String contactLandline;
			
	/** 联系人传真**/
	private String contactFax;
			
	/** 联系人手机**/
	private String contactPhone;
			
	/** 联系人电子邮件**/
	private String contactEmail;
			
	/** 归属最终母公司全称**/
	private String fullNameOfTheUltimateParent;
			
	/** 境外企业名称(中文)**/
	private String overseasCompanyChineseName;
			
	/** 境外企业名称(外文)**/
	private String overseasCompanyForeignName;
			
	/** 第一层级公司名称**/
	private String firstLevelCompanyName;
			
	/** 第一层级公司所处国别（地区）**/
	private String countryFirstLevelCompanyLocates;
			
	/** 国家（地区）**/
	private String overseasInvestmentCountry;
			
	/** 省（州）**/
	private String province;
			
	/** 城市**/
	private String overseasCity;
			
	/** 中方股东名称**/
	private String chineseShareholder;
			
	/** 中方股比**/
	private String chineseEquityRatio;
			
	/** 外方股东名称**/
	private String foreignShareholder;
			
	/** 外方股比**/
	private String foreignEquityRatio;
			
	/** 境外企业经营范围**/
	private String overseasBusinessScope;
			
	/** 境外企业所属行业**/
	private String overseasEnterpriseIndustry;
			
	/** 投资总额（美金)**/
	private String totalInvestment;
			
	/** 中方投资额（美元/人民币）**/
	private String chineseInvestment;
			
	/** 外方投资额**/
	private String foreignInvestment;
			
	/** 投资动机（营销网络）**/
	private String marketingNetwork;
			
	/** 带动出口（万美金）**/
	private String stimulateExports;
			
	/** 带动进口（万美金）**/
	private String stimulateImports;
			
	/** 投资动机（品牌收购）**/
	private String brandAcquisitions;
			
	/** 获得品牌名称**/
	private String obtainBrandName;
			
	/** 投资动机（技术研发）**/
	private String technologyResearch;
			
	/** 获得技术**/
	private String technologyAcquired;
			
	/** 投资动机（工程开发）**/
	private String engineeringDevelopment;
			
	/** 带动营业额**/
	private String boostingSales;
			
	/** 投资动机（资源开发）**/
	private String resourceDevelopment;
			
	/** 资源回运量**/
	private String resourcesBackShipments;
			
	/** 资源开发量**/
	private String resourcesAmount;
			
	/** 资源名称**/
	private String resourceName;
			
	/** 投资动机（产业链延伸）**/
	private String extensionOfIndustrialChain;
			
	/** 简述**/
	private String sketch;
			
	/** 投资动机（其他）**/
	private String rests;
			
	/** 投资回报年限**/
	private String paybackPeriod;
			
	/** 备注**/
	private String remark;
			
	/** 附件**/
	private String accessory;
			
	/** 排序**/
	private Integer orderBy;
					
	public void setNumberOfInvestmentProjects(String numberOfInvestmentProjects){
		this.numberOfInvestmentProjects = numberOfInvestmentProjects;
	} 
	
	public String getNumberOfInvestmentProjects(){
		return numberOfInvestmentProjects;
	} 
			
	public void setApplicationMode(String applicationMode){
		this.applicationMode = applicationMode;
	} 
	
	public String getApplicationMode(){
		return applicationMode;
	} 
			
	public void setFormer(String former){
		this.former = former;
	} 
	
	public String getFormer(){
		return former;
	} 
			
	public void setNowChanged(String nowChanged){
		this.nowChanged = nowChanged;
	} 
	
	public String getNowChanged(){
		return nowChanged;
	} 
			
	public void setYearChange(String yearChange){
		this.yearChange = yearChange;
	} 
	
	public String getYearChange(){
		return yearChange;
	} 
			
	public void setDomesticInvestorName(String domesticInvestorName){
		this.domesticInvestorName = domesticInvestorName;
	} 
	
	public String getDomesticInvestorName(){
		return domesticInvestorName;
	} 
			
	public void setOrganizationCodeNo(String organizationCodeNo){
		this.organizationCodeNo = organizationCodeNo;
	} 
	
	public String getOrganizationCodeNo(){
		return organizationCodeNo;
	} 
			
	public void setLegalRepresentative(String legalRepresentative){
		this.legalRepresentative = legalRepresentative;
	} 
	
	public String getLegalRepresentative(){
		return legalRepresentative;
	} 
			
	public void setRegisteredCapital(String registeredCapital){
		this.registeredCapital = registeredCapital;
	} 
	
	public String getRegisteredCapital(){
		return registeredCapital;
	} 
			
	public void setOfficeAddress(String officeAddress){
		this.officeAddress = officeAddress;
	} 
	
	public String getOfficeAddress(){
		return officeAddress;
	} 
			
	public void setDomesticCity(String domesticCity){
		this.domesticCity = domesticCity;
	} 
	
	public String getDomesticCity(){
		return domesticCity;
	} 
			
	public void setCounty(String county){
		this.county = county;
	} 
	
	public String getCounty(){
		return county;
	} 
			
	public void setContactName(String contactName){
		this.contactName = contactName;
	} 
	
	public String getContactName(){
		return contactName;
	} 
			
	public void setContactLandline(String contactLandline){
		this.contactLandline = contactLandline;
	} 
	
	public String getContactLandline(){
		return contactLandline;
	} 
			
	public void setContactFax(String contactFax){
		this.contactFax = contactFax;
	} 
	
	public String getContactFax(){
		return contactFax;
	} 
			
	public void setContactPhone(String contactPhone){
		this.contactPhone = contactPhone;
	} 
	
	public String getContactPhone(){
		return contactPhone;
	} 
			
	public void setContactEmail(String contactEmail){
		this.contactEmail = contactEmail;
	} 
	
	public String getContactEmail(){
		return contactEmail;
	} 
			
	public void setFullNameOfTheUltimateParent(String fullNameOfTheUltimateParent){
		this.fullNameOfTheUltimateParent = fullNameOfTheUltimateParent;
	} 
	
	public String getFullNameOfTheUltimateParent(){
		return fullNameOfTheUltimateParent;
	} 
			
	public void setOverseasCompanyChineseName(String overseasCompanyChineseName){
		this.overseasCompanyChineseName = overseasCompanyChineseName;
	} 
	
	public String getOverseasCompanyChineseName(){
		return overseasCompanyChineseName;
	} 
			
	public void setOverseasCompanyForeignName(String overseasCompanyForeignName){
		this.overseasCompanyForeignName = overseasCompanyForeignName;
	} 
	
	public String getOverseasCompanyForeignName(){
		return overseasCompanyForeignName;
	} 
			
	public void setFirstLevelCompanyName(String firstLevelCompanyName){
		this.firstLevelCompanyName = firstLevelCompanyName;
	} 
	
	public String getFirstLevelCompanyName(){
		return firstLevelCompanyName;
	} 
			
	public void setCountryFirstLevelCompanyLocates(String countryFirstLevelCompanyLocates){
		this.countryFirstLevelCompanyLocates = countryFirstLevelCompanyLocates;
	} 
	
	public String getCountryFirstLevelCompanyLocates(){
		return countryFirstLevelCompanyLocates;
	} 
			
	public void setOverseasInvestmentCountry(String overseasInvestmentCountry){
		this.overseasInvestmentCountry = overseasInvestmentCountry;
	} 
	
	public String getOverseasInvestmentCountry(){
		return overseasInvestmentCountry;
	} 
			
	public void setProvince(String province){
		this.province = province;
	} 
	
	public String getProvince(){
		return province;
	} 
			
	public void setOverseasCity(String overseasCity){
		this.overseasCity = overseasCity;
	} 
	
	public String getOverseasCity(){
		return overseasCity;
	} 
			
	public void setChineseShareholder(String chineseShareholder){
		this.chineseShareholder = chineseShareholder;
	} 
	
	public String getChineseShareholder(){
		return chineseShareholder;
	} 
			
	public void setChineseEquityRatio(String chineseEquityRatio){
		this.chineseEquityRatio = chineseEquityRatio;
	} 
	
	public String getChineseEquityRatio(){
		return chineseEquityRatio;
	} 
			
	public void setForeignShareholder(String foreignShareholder){
		this.foreignShareholder = foreignShareholder;
	} 
	
	public String getForeignShareholder(){
		return foreignShareholder;
	} 
			
	public void setForeignEquityRatio(String foreignEquityRatio){
		this.foreignEquityRatio = foreignEquityRatio;
	} 
	
	public String getForeignEquityRatio(){
		return foreignEquityRatio;
	} 
			
	public void setOverseasBusinessScope(String overseasBusinessScope){
		this.overseasBusinessScope = overseasBusinessScope;
	} 
	
	public String getOverseasBusinessScope(){
		return overseasBusinessScope;
	} 
			
	public void setOverseasEnterpriseIndustry(String overseasEnterpriseIndustry){
		this.overseasEnterpriseIndustry = overseasEnterpriseIndustry;
	} 
	
	public String getOverseasEnterpriseIndustry(){
		return overseasEnterpriseIndustry;
	} 
			
	public void setTotalInvestment(String totalInvestment){
		this.totalInvestment = totalInvestment;
	} 
	
	public String getTotalInvestment(){
		return totalInvestment;
	} 
			
	public void setChineseInvestment(String chineseInvestment){
		this.chineseInvestment = chineseInvestment;
	} 
	
	public String getChineseInvestment(){
		return chineseInvestment;
	} 
			
	public void setForeignInvestment(String foreignInvestment){
		this.foreignInvestment = foreignInvestment;
	} 
	
	public String getForeignInvestment(){
		return foreignInvestment;
	} 
			
	public void setMarketingNetwork(String marketingNetwork){
		this.marketingNetwork = marketingNetwork;
	} 
	
	public String getMarketingNetwork(){
		return marketingNetwork;
	} 
			
	public void setStimulateExports(String stimulateExports){
		this.stimulateExports = stimulateExports;
	} 
	
	public String getStimulateExports(){
		return stimulateExports;
	} 
			
	public void setStimulateImports(String stimulateImports){
		this.stimulateImports = stimulateImports;
	} 
	
	public String getStimulateImports(){
		return stimulateImports;
	} 
			
	public void setBrandAcquisitions(String brandAcquisitions){
		this.brandAcquisitions = brandAcquisitions;
	} 
	
	public String getBrandAcquisitions(){
		return brandAcquisitions;
	} 
			
	public void setObtainBrandName(String obtainBrandName){
		this.obtainBrandName = obtainBrandName;
	} 
	
	public String getObtainBrandName(){
		return obtainBrandName;
	} 
			
	public void setTechnologyResearch(String technologyResearch){
		this.technologyResearch = technologyResearch;
	} 
	
	public String getTechnologyResearch(){
		return technologyResearch;
	} 
			
	public void setTechnologyAcquired(String technologyAcquired){
		this.technologyAcquired = technologyAcquired;
	} 
	
	public String getTechnologyAcquired(){
		return technologyAcquired;
	} 
			
	public void setEngineeringDevelopment(String engineeringDevelopment){
		this.engineeringDevelopment = engineeringDevelopment;
	} 
	
	public String getEngineeringDevelopment(){
		return engineeringDevelopment;
	} 
			
	public void setBoostingSales(String boostingSales){
		this.boostingSales = boostingSales;
	} 
	
	public String getBoostingSales(){
		return boostingSales;
	} 
			
	public void setResourceDevelopment(String resourceDevelopment){
		this.resourceDevelopment = resourceDevelopment;
	} 
	
	public String getResourceDevelopment(){
		return resourceDevelopment;
	} 
			
	public void setResourcesBackShipments(String resourcesBackShipments){
		this.resourcesBackShipments = resourcesBackShipments;
	} 
	
	public String getResourcesBackShipments(){
		return resourcesBackShipments;
	} 
			
	public void setResourcesAmount(String resourcesAmount){
		this.resourcesAmount = resourcesAmount;
	} 
	
	public String getResourcesAmount(){
		return resourcesAmount;
	} 
			
	public void setResourceName(String resourceName){
		this.resourceName = resourceName;
	} 
	
	public String getResourceName(){
		return resourceName;
	} 
			
	public void setExtensionOfIndustrialChain(String extensionOfIndustrialChain){
		this.extensionOfIndustrialChain = extensionOfIndustrialChain;
	} 
	
	public String getExtensionOfIndustrialChain(){
		return extensionOfIndustrialChain;
	} 
			
	public void setSketch(String sketch){
		this.sketch = sketch;
	} 
	
	public String getSketch(){
		return sketch;
	} 
			
	public void setRests(String rests){
		this.rests = rests;
	} 
	
	public String getRests(){
		return rests;
	} 
			
	public void setPaybackPeriod(String paybackPeriod){
		this.paybackPeriod = paybackPeriod;
	} 
	
	public String getPaybackPeriod(){
		return paybackPeriod;
	} 
			
	public void setRemark(String remark){
		this.remark = remark;
	} 
	
	public String getRemark(){
		return remark;
	} 
			
	public void setAccessory(String accessory){
		this.accessory = accessory;
	} 
	
	public String getAccessory(){
		return accessory;
	} 
			
	public void setOrderBy(Integer orderBy){
		this.orderBy = orderBy;
	} 
	
	public Integer getOrderBy(){
		return orderBy;
	} 
	}
