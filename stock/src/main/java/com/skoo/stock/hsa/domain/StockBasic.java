package com.skoo.stock.hsa.domain;
import com.skoo.orm.domain.BaseEntity;
import org.apache.ibatis.type.Alias;


/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Alias("StockBasic")
public class StockBasic extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
				
	/** 代码**/
	private String code;
			
	/** 简称**/
	private String name;
			
	/** 流通股本**/
	private Double circulationEquity;
			
	/** 总股本**/
	private Double totalEquity;
			
	/** 每股收益**/
	private Double perProfit;
			
	/** 每股净资产**/
	private Double netAssets;
			
	/** 每股未分配利润**/
	private Double ndistribProfit;
			
	/** 每股资本公积金**/
	private Double capitalFund;
			
	/** 净资产收益率**/
	private Double assetsYield;
			
	/** 主营业务收入**/
	private Double mainRevenue;
			
	/** 净利润**/
	private Double netProfit;
			
	/** 净利润描述**/
	private String profitDescribe;
			
	/** 主营业务**/
	private String mainBusiness;
			
	/** 所属板块**/
	private String thePlate;
			
	/** 上市日期**/
	private String openDate;
			
	/** 备注**/
	private String memo;
			
	/** 顺序**/
	private Integer orderBy;
					
	public void setCode(String code){
		this.code = code;
	} 
	
	public String getCode(){
		return code;
	} 
			
	public void setName(String name){
		this.name = name;
	} 
	
	public String getName(){
		return name;
	} 
			
	public void setCirculationEquity(Double circulationEquity){
		this.circulationEquity = circulationEquity;
	} 
	
	public Double getCirculationEquity(){
		return circulationEquity;
	} 
			
	public void setTotalEquity(Double totalEquity){
		this.totalEquity = totalEquity;
	} 
	
	public Double getTotalEquity(){
		return totalEquity;
	} 
			
	public void setPerProfit(Double perProfit){
		this.perProfit = perProfit;
	} 
	
	public Double getPerProfit(){
		return perProfit;
	} 
			
	public void setNetAssets(Double netAssets){
		this.netAssets = netAssets;
	} 
	
	public Double getNetAssets(){
		return netAssets;
	} 
			
	public void setNdistribProfit(Double ndistribProfit){
		this.ndistribProfit = ndistribProfit;
	} 
	
	public Double getNdistribProfit(){
		return ndistribProfit;
	} 
			
	public void setCapitalFund(Double capitalFund){
		this.capitalFund = capitalFund;
	} 
	
	public Double getCapitalFund(){
		return capitalFund;
	} 
			
	public void setAssetsYield(Double assetsYield){
		this.assetsYield = assetsYield;
	} 
	
	public Double getAssetsYield(){
		return assetsYield;
	} 
			
	public void setMainRevenue(Double mainRevenue){
		this.mainRevenue = mainRevenue;
	} 
	
	public Double getMainRevenue(){
		return mainRevenue;
	} 
			
	public void setNetProfit(Double netProfit){
		this.netProfit = netProfit;
	} 
	
	public Double getNetProfit(){
		return netProfit;
	} 
			
	public void setProfitDescribe(String profitDescribe){
		this.profitDescribe = profitDescribe;
	} 
	
	public String getProfitDescribe(){
		return profitDescribe;
	} 
			
	public void setMainBusiness(String mainBusiness){
		this.mainBusiness = mainBusiness;
	} 
	
	public String getMainBusiness(){
		return mainBusiness;
	} 
			
	public void setThePlate(String thePlate){
		this.thePlate = thePlate;
	} 
	
	public String getThePlate(){
		return thePlate;
	} 
			
	public void setOpenDate(String openDate){
		this.openDate = openDate;
	} 
	
	public String getOpenDate(){
		return openDate;
	} 
			
	public void setMemo(String memo){
		this.memo = memo;
	} 
	
	public String getMemo(){
		return memo;
	} 
			
	public void setOrderBy(Integer orderBy){
		this.orderBy = orderBy;
	} 
	
	public Integer getOrderBy(){
		return orderBy;
	} 
	}
