package com.skoo.stock.hsa.domain;
import com.skoo.orm.domain.BaseEntity;
import org.apache.ibatis.type.Alias;


/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Alias("StockNew")
public class StockNew extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
				
	/** 代码**/
	private String code;
			
	/** 简称**/
	private String name;
			
	/** 最新价**/
	private String latestPrice;
			
	/** 平均价**/
	private String averagePrice;
			
	/** 涨跌幅**/
	private String udWidth;
			
	/** 涨跌额**/
	private String udAmount;
			
	/** 成交量(万手)**/
	private String volume;
			
	/** 成交额(亿元)**/
	private String turnVolume;
			
	/** 换手率**/
	private String turnoverRate;
			
	/** 量比**/
	private String volumeRatio;
			
	/** 振幅**/
	private String amplitude;
			
	/** 委比**/
	private String committee;
			
	/** 市盈率**/
	private String peRatio;
			
	/** 5分钟涨幅**/
	private String fiveWidth;
			
	/** 网站标志**/
	private String netFlag;
			
	/** 最高**/
	private String highestPrice;
			
	/** 最低**/
	private String lowestPrice;
			
	/** 今开**/
	private String todayPrice;
			
	/** 昨收**/
	private String yesterdayPrice;
			
	/** 涨停**/
	private String maxPrice;
			
	/** 跌停**/
	private String minPrice;
			
	/** 外盘**/
	private String outsideDish;
			
	/** 内盘**/
	private String insideDish;
			
	/** 主力净流入(万元)**/
	private String inflowFund;
			
	/** 超大单流入(万元)**/
	private String inflowLarge;
			
	/** 大单流入(万元)**/
	private String inflowBig;
			
	/** 龙头标志**/
	private Integer topFlag;
			
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
			
	public void setLatestPrice(String latestPrice){
		this.latestPrice = latestPrice;
	} 
	
	public String getLatestPrice(){
		return latestPrice;
	} 
			
	public void setAveragePrice(String averagePrice){
		this.averagePrice = averagePrice;
	} 
	
	public String getAveragePrice(){
		return averagePrice;
	} 
			
	public void setUdWidth(String udWidth){
		this.udWidth = udWidth;
	} 
	
	public String getUdWidth(){
		return udWidth;
	} 
			
	public void setUdAmount(String udAmount){
		this.udAmount = udAmount;
	} 
	
	public String getUdAmount(){
		return udAmount;
	} 
			
	public void setVolume(String volume){
		this.volume = volume;
	} 
	
	public String getVolume(){
		return volume;
	} 
			
	public void setTurnVolume(String turnVolume){
		this.turnVolume = turnVolume;
	} 
	
	public String getTurnVolume(){
		return turnVolume;
	} 
			
	public void setTurnoverRate(String turnoverRate){
		this.turnoverRate = turnoverRate;
	} 
	
	public String getTurnoverRate(){
		return turnoverRate;
	} 
			
	public void setVolumeRatio(String volumeRatio){
		this.volumeRatio = volumeRatio;
	} 
	
	public String getVolumeRatio(){
		return volumeRatio;
	} 
			
	public void setAmplitude(String amplitude){
		this.amplitude = amplitude;
	} 
	
	public String getAmplitude(){
		return amplitude;
	} 
			
	public void setCommittee(String committee){
		this.committee = committee;
	} 
	
	public String getCommittee(){
		return committee;
	} 
			
	public void setPeRatio(String peRatio){
		this.peRatio = peRatio;
	} 
	
	public String getPeRatio(){
		return peRatio;
	} 
			
	public void setFiveWidth(String fiveWidth){
		this.fiveWidth = fiveWidth;
	} 
	
	public String getFiveWidth(){
		return fiveWidth;
	} 
			
	public void setNetFlag(String netFlag){
		this.netFlag = netFlag;
	} 
	
	public String getNetFlag(){
		return netFlag;
	} 
			
	public void setHighestPrice(String highestPrice){
		this.highestPrice = highestPrice;
	} 
	
	public String getHighestPrice(){
		return highestPrice;
	} 
			
	public void setLowestPrice(String lowestPrice){
		this.lowestPrice = lowestPrice;
	} 
	
	public String getLowestPrice(){
		return lowestPrice;
	} 
			
	public void setTodayPrice(String todayPrice){
		this.todayPrice = todayPrice;
	} 
	
	public String getTodayPrice(){
		return todayPrice;
	} 
			
	public void setYesterdayPrice(String yesterdayPrice){
		this.yesterdayPrice = yesterdayPrice;
	} 
	
	public String getYesterdayPrice(){
		return yesterdayPrice;
	} 
			
	public void setMaxPrice(String maxPrice){
		this.maxPrice = maxPrice;
	} 
	
	public String getMaxPrice(){
		return maxPrice;
	} 
			
	public void setMinPrice(String minPrice){
		this.minPrice = minPrice;
	} 
	
	public String getMinPrice(){
		return minPrice;
	} 
			
	public void setOutsideDish(String outsideDish){
		this.outsideDish = outsideDish;
	} 
	
	public String getOutsideDish(){
		return outsideDish;
	} 
			
	public void setInsideDish(String insideDish){
		this.insideDish = insideDish;
	} 
	
	public String getInsideDish(){
		return insideDish;
	} 
			
	public void setInflowFund(String inflowFund){
		this.inflowFund = inflowFund;
	} 
	
	public String getInflowFund(){
		return inflowFund;
	} 
			
	public void setInflowLarge(String inflowLarge){
		this.inflowLarge = inflowLarge;
	} 
	
	public String getInflowLarge(){
		return inflowLarge;
	} 
			
	public void setInflowBig(String inflowBig){
		this.inflowBig = inflowBig;
	} 
	
	public String getInflowBig(){
		return inflowBig;
	} 
			
	public void setTopFlag(Integer topFlag){
		this.topFlag = topFlag;
	} 
	
	public Integer getTopFlag(){
		return topFlag;
	} 
			
	public void setOrderBy(Integer orderBy){
		this.orderBy = orderBy;
	} 
	
	public Integer getOrderBy(){
		return orderBy;
	} 
	}
