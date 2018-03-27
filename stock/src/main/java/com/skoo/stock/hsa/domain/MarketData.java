package com.skoo.stock.hsa.domain;
import com.skoo.orm.domain.BaseEntity;
import org.apache.ibatis.type.Alias;


/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Alias("MarketData")
public class MarketData extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
				
	/** 代码**/
	private String code;
			
	/** 简称**/
	private String name;
			
	/** 最新价**/
	private Double latestPrice;
			
	/** 涨跌幅**/
	private Double udWidth;
			
	/** 涨跌额**/
	private Double udAmount;
			
	/** 5分钟涨幅**/
	private String fiveWidth;
			
	/** 成交量(手)**/
	private Double volume;
			
	/** 成交额(万元)**/
	private Double turnVolume;
			
	/** 换手率**/
	private String turnoverRate;
			
	/** 振幅**/
	private String amplitude;
			
	/** 量比**/
	private Double volumeRatio;
			
	/** 委比**/
	private Double committee;
			
	/** 市盈率**/
	private Double peRatio;
			
	/** 网站标志**/
	private String netFlag;
			
	/** 顺序**/
	private Integer orderBy;
			
	/** 昨收**/
	private Double yesterdayPrice;
			
	/** 今开**/
	private Double todayPrice;
			
	/** 最高**/
	private Double highestPrice;
			
	/** 最低**/
	private Double lowestPrice;
					
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
			
	public void setLatestPrice(Double latestPrice){
		this.latestPrice = latestPrice;
	} 
	
	public Double getLatestPrice(){
		return latestPrice;
	} 
			
	public void setUdWidth(Double udWidth){
		this.udWidth = udWidth;
	} 
	
	public Double getUdWidth(){
		return udWidth;
	} 
			
	public void setUdAmount(Double udAmount){
		this.udAmount = udAmount;
	} 
	
	public Double getUdAmount(){
		return udAmount;
	} 
			
	public void setFiveWidth(String fiveWidth){
		this.fiveWidth = fiveWidth;
	} 
	
	public String getFiveWidth(){
		return fiveWidth;
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
			
	public void setTurnoverRate(String turnoverRate){
		this.turnoverRate = turnoverRate;
	} 
	
	public String getTurnoverRate(){
		return turnoverRate;
	} 
			
	public void setAmplitude(String amplitude){
		this.amplitude = amplitude;
	} 
	
	public String getAmplitude(){
		return amplitude;
	} 
			
	public void setVolumeRatio(Double volumeRatio){
		this.volumeRatio = volumeRatio;
	} 
	
	public Double getVolumeRatio(){
		return volumeRatio;
	} 
			
	public void setCommittee(Double committee){
		this.committee = committee;
	} 
	
	public Double getCommittee(){
		return committee;
	} 
			
	public void setPeRatio(Double peRatio){
		this.peRatio = peRatio;
	} 
	
	public Double getPeRatio(){
		return peRatio;
	} 
			
	public void setNetFlag(String netFlag){
		this.netFlag = netFlag;
	} 
	
	public String getNetFlag(){
		return netFlag;
	} 
			
	public void setOrderBy(Integer orderBy){
		this.orderBy = orderBy;
	} 
	
	public Integer getOrderBy(){
		return orderBy;
	} 
			
	public void setYesterdayPrice(Double yesterdayPrice){
		this.yesterdayPrice = yesterdayPrice;
	} 
	
	public Double getYesterdayPrice(){
		return yesterdayPrice;
	} 
			
	public void setTodayPrice(Double todayPrice){
		this.todayPrice = todayPrice;
	} 
	
	public Double getTodayPrice(){
		return todayPrice;
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
	}
