package com.skoo.stock.hsa.domain;
import com.skoo.orm.domain.BaseEntity;
import org.apache.ibatis.type.Alias;


/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Alias("HistoryDetail")
public class HistoryDetail extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
				
	/** 代码**/
	private String code;
			
	/** 简称**/
	private String name;
			
	/** 当前价**/
	private Double currentPrice;
			
	/** 预测价**/
	private Double forecastPrice;
			
	/** 起算价**/
	private Double initialPrice;
			
	/** 压力位**/
	private Double pressurePrice;
			
	/** 支撑位**/
	private Double supportPrice;
			
	/** 最高价**/
	private Double highestPrice;
			
	/** 最低价**/
	private Double lowestPrice;
			
	/** 5日线**/
	private Double fiveLine;
			
	/** 10日线**/
	private Double tenLine;
			
	/** 30日线**/
	private Double thirtyLine;
			
	/** 动态线**/
	private Double dynamicLine;
			
	/** 5日涨幅**/
	private Double fiveWidth;
			
	/** 10日涨幅**/
	private Double tenWidth;
			
	/** 20日涨幅**/
	private Double twentyWidth;
			
	/** 动态涨幅**/
	private Double dynamicWidth;
			
	/** 除权除息日**/
	private String exdividendDate;
			
	/** 复牌日**/
	private String resumeDate;
			
	/** 停牌日**/
	private String suspendDate;
			
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
			
	public void setForecastPrice(Double forecastPrice){
		this.forecastPrice = forecastPrice;
	} 
	
	public Double getForecastPrice(){
		return forecastPrice;
	} 
			
	public void setInitialPrice(Double initialPrice){
		this.initialPrice = initialPrice;
	} 
	
	public Double getInitialPrice(){
		return initialPrice;
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
			
	public void setFiveLine(Double fiveLine){
		this.fiveLine = fiveLine;
	} 
	
	public Double getFiveLine(){
		return fiveLine;
	} 
			
	public void setTenLine(Double tenLine){
		this.tenLine = tenLine;
	} 
	
	public Double getTenLine(){
		return tenLine;
	} 
			
	public void setThirtyLine(Double thirtyLine){
		this.thirtyLine = thirtyLine;
	} 
	
	public Double getThirtyLine(){
		return thirtyLine;
	} 
			
	public void setDynamicLine(Double dynamicLine){
		this.dynamicLine = dynamicLine;
	} 
	
	public Double getDynamicLine(){
		return dynamicLine;
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
			
	public void setDynamicWidth(Double dynamicWidth){
		this.dynamicWidth = dynamicWidth;
	} 
	
	public Double getDynamicWidth(){
		return dynamicWidth;
	} 
			
	public void setExdividendDate(String exdividendDate){
		this.exdividendDate = exdividendDate;
	} 
	
	public String getExdividendDate(){
		return exdividendDate;
	} 
			
	public void setResumeDate(String resumeDate){
		this.resumeDate = resumeDate;
	} 
	
	public String getResumeDate(){
		return resumeDate;
	} 
			
	public void setSuspendDate(String suspendDate){
		this.suspendDate = suspendDate;
	} 
	
	public String getSuspendDate(){
		return suspendDate;
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
