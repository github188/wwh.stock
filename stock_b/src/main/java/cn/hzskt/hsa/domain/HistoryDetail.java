package cn.hzskt.hsa.domain;
import com.zjhcsoft.smartcity.magic.orm.domain.BaseEntity;


/**
 * @description:
 * @author: autoCode
 * @history:
 */
public class HistoryDetail extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
				
	/** 代码**/
	private String code;
			
	/** 简称**/
	private String name;
			
	/** 当前价**/
	private Double currentPrice;
			
	/** 压力位**/
	private Double pressurePrice;
			
	/** 支撑位**/
	private Double supportPrice;
			
	/** 最高价**/
	private Double highestPrice;
			
	/** 最低价**/
	private Double lowestPrice;
			
	/** 5日涨幅**/
	private Double fiveWidth;
			
	/** 10日涨幅**/
	private Double tenWidth;
			
	/** 20日涨幅**/
	private Double twentyWidth;
			
	/** 停牌天数**/
	private Integer suspensionDays;
			
	/** 停牌起始日期**/
	private String startDate;
			
	/** 停牌结束日期**/
	private String endDate;
			
	/** 流通股本**/
	private Double circulationEquity;
			
	/** 总股本**/
	private Double totalEquity;
			
	/** 每股收益**/
	private Double perProfit;
			
	/** 每股净资产**/
	private Double netAssets;
			
	/** 每股资本公积金**/
	private Double capitalFund;
			
	/** 净利润**/
	private Double netProfit;
			
	/** 净利润描述**/
	private String profitDescribe;
			
	/** 主营业务**/
	private String mainBusiness;
			
	/** 所属板块**/
	private String thePlate;
			
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
			
	public void setCurrentPrice(Double currentPrice){
		this.currentPrice = currentPrice;
	} 
	
	public Double getCurrentPrice(){
		return currentPrice;
	} 
			
	public void setPressurePrice(Double pressurePrice){
		this.pressurePrice = pressurePrice;
	} 
	
	public Double getPressurePrice(){
		return pressurePrice;
	} 
			
	public void setSupportPrice(Double supportPrice){
		this.supportPrice = supportPrice;
	} 
	
	public Double getSupportPrice(){
		return supportPrice;
	} 
			
	public void setHighestPrice(Double highestPrice){
		this.highestPrice = highestPrice;
	} 
	
	public Double getHighestPrice(){
		return highestPrice;
	} 
			
	public void setLowestPrice(Double lowestPrice){
		this.lowestPrice = lowestPrice;
	} 
	
	public Double getLowestPrice(){
		return lowestPrice;
	} 
			
	public void setFiveWidth(Double fiveWidth){
		this.fiveWidth = fiveWidth;
	} 
	
	public Double getFiveWidth(){
		return fiveWidth;
	} 
			
	public void setTenWidth(Double tenWidth){
		this.tenWidth = tenWidth;
	} 
	
	public Double getTenWidth(){
		return tenWidth;
	} 
			
	public void setTwentyWidth(Double twentyWidth){
		this.twentyWidth = twentyWidth;
	} 
	
	public Double getTwentyWidth(){
		return twentyWidth;
	} 
			
	public void setSuspensionDays(Integer suspensionDays){
		this.suspensionDays = suspensionDays;
	} 
	
	public Integer getSuspensionDays(){
		return suspensionDays;
	} 
			
	public void setStartDate(String startDate){
		this.startDate = startDate;
	} 
	
	public String getStartDate(){
		return startDate;
	} 
			
	public void setEndDate(String endDate){
		this.endDate = endDate;
	} 
	
	public String getEndDate(){
		return endDate;
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
			
	public void setCapitalFund(Double capitalFund){
		this.capitalFund = capitalFund;
	} 
	
	public Double getCapitalFund(){
		return capitalFund;
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
