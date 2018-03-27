package com.skoo.stock.hsa.domain;
import com.skoo.orm.domain.BaseEntity;
import org.apache.ibatis.type.Alias;


/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Alias("IndustryData")
public class IndustryData extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
				
	/** 板块编码**/
	private String industryId;
			
	/** 板块名称**/
	private String industryName;
			
	/** 股票数量**/
	private Integer stockNum;
			
	/** 加权涨跌幅**/
	private String udWidth;
			
	/** 上涨家数**/
	private Integer upNum;
			
	/** 涨幅**/
	private String upWidth;
			
	/** 下跌家数**/
	private Integer downNum;
			
	/** 跌幅**/
	private String downWidth;
			
	/** 成交量(手)**/
	private Double volume;
			
	/** 成交额(万元)**/
	private Double turnVolume;
			
	/** 总流通市值**/
	private Double circuValue;
			
	/** 网址**/
	private String netAddress;
			
	/** 顺序**/
	private Integer orderBy;
			
	/** 网站标志**/
	private String netFlag;
			
	/** 相关资讯**/
	private String fundAddress;
			
	/** 换手率(%)**/
	private String turnoverRate;
			
	/** 领涨股票**/
	private String stockName;
			
	/** 股票网址**/
	private String stockAddress;
			
	/** 涨跌幅**/
	private String stockWidth;
			
	/** 板块类别**/
	private String industryType;
					
	public void setIndustryId(String industryId){
		this.industryId = industryId;
	} 
	
	public String getIndustryId(){
		return industryId;
	} 
			
	public void setIndustryName(String industryName){
		this.industryName = industryName;
	} 
	
	public String getIndustryName(){
		return industryName;
	} 
			
	public void setStockNum(Integer stockNum){
		this.stockNum = stockNum;
	} 
	
	public Integer getStockNum(){
		return stockNum;
	} 
			
	public void setUdWidth(String udWidth){
		this.udWidth = udWidth;
	} 
	
	public String getUdWidth(){
		return udWidth;
	} 
			
	public void setUpNum(Integer upNum){
		this.upNum = upNum;
	} 
	
	public Integer getUpNum(){
		return upNum;
	} 
			
	public void setUpWidth(String upWidth){
		this.upWidth = upWidth;
	} 
	
	public String getUpWidth(){
		return upWidth;
	} 
			
	public void setDownNum(Integer downNum){
		this.downNum = downNum;
	} 
	
	public Integer getDownNum(){
		return downNum;
	} 
			
	public void setDownWidth(String downWidth){
		this.downWidth = downWidth;
	} 
	
	public String getDownWidth(){
		return downWidth;
	} 
			
	public void setVolume(Double volume){
		this.volume = volume;
	} 
	
	public Double getVolume(){
		return volume;
	} 
			
	public void setTurnVolume(Double turnVolume){
		this.turnVolume = turnVolume;
	} 
	
	public Double getTurnVolume(){
		return turnVolume;
	} 
			
	public void setCircuValue(Double circuValue){
		this.circuValue = circuValue;
	} 
	
	public Double getCircuValue(){
		return circuValue;
	} 
			
	public void setNetAddress(String netAddress){
		this.netAddress = netAddress;
	} 
	
	public String getNetAddress(){
		return netAddress;
	} 
			
	public void setOrderBy(Integer orderBy){
		this.orderBy = orderBy;
	} 
	
	public Integer getOrderBy(){
		return orderBy;
	} 
			
	public void setNetFlag(String netFlag){
		this.netFlag = netFlag;
	} 
	
	public String getNetFlag(){
		return netFlag;
	} 
			
	public void setFundAddress(String fundAddress){
		this.fundAddress = fundAddress;
	} 
	
	public String getFundAddress(){
		return fundAddress;
	} 
			
	public void setTurnoverRate(String turnoverRate){
		this.turnoverRate = turnoverRate;
	} 
	
	public String getTurnoverRate(){
		return turnoverRate;
	} 
			
	public void setStockName(String stockName){
		this.stockName = stockName;
	} 
	
	public String getStockName(){
		return stockName;
	} 
			
	public void setStockAddress(String stockAddress){
		this.stockAddress = stockAddress;
	} 
	
	public String getStockAddress(){
		return stockAddress;
	} 
			
	public void setStockWidth(String stockWidth){
		this.stockWidth = stockWidth;
	} 
	
	public String getStockWidth(){
		return stockWidth;
	} 
			
	public void setIndustryType(String industryType){
		this.industryType = industryType;
	} 
	
	public String getIndustryType(){
		return industryType;
	} 
	}
