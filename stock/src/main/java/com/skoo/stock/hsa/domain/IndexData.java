package com.skoo.stock.hsa.domain;
import com.skoo.orm.domain.BaseEntity;
import org.apache.ibatis.type.Alias;


/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Alias("IndexData")
public class IndexData extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
				
	/** 代码**/
	private String code;
			
	/** 简称**/
	private String name;
			
	/** 日期**/
	private String dt;
			
	/** 今开**/
	private Double startPrice;
			
	/** 昨收**/
	private Double endPrice;
			
	/** 当前点数**/
	private Double closingPrice;
			
	/** 涨跌额**/
	private Double udAmount;
			
	/** 涨跌幅**/
	private String udWidth;
			
	/** 最高价**/
	private Double highestPrice;
			
	/** 最低价**/
	private Double lowestPrice;
			
	/** 成交量(手)**/
	private Double volume;
			
	/** 成交额(万元)**/
	private Double turnVolume;
			
	/** 上涨家数**/
	private String riseCnt;
			
	/** 平盘家数**/
	private String flatCnt;
			
	/** 下跌家数**/
	private String fallCnt;
			
	/** 相对涨跌幅**/
	private Double curUdWidth;
			
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
			
	public void setDt(String dt){
		this.dt = dt;
	} 
	
	public String getDt(){
		return dt;
	} 
			
	public void setStartPrice(Double startPrice){
		this.startPrice = startPrice;
	} 
	
	public Double getStartPrice(){
		return startPrice;
	} 
			
	public void setEndPrice(Double endPrice){
		this.endPrice = endPrice;
	} 
	
	public Double getEndPrice(){
		return endPrice;
	} 
			
	public void setClosingPrice(Double closingPrice){
		this.closingPrice = closingPrice;
	} 
	
	public Double getClosingPrice(){
		return closingPrice;
	} 
			
	public void setUdAmount(Double udAmount){
		this.udAmount = udAmount;
	} 
	
	public Double getUdAmount(){
		return udAmount;
	} 
			
	public void setUdWidth(String udWidth){
		this.udWidth = udWidth;
	} 
	
	public String getUdWidth(){
		return udWidth;
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
			
	public void setRiseCnt(String riseCnt){
		this.riseCnt = riseCnt;
	} 
	
	public String getRiseCnt(){
		return riseCnt;
	} 
			
	public void setFlatCnt(String flatCnt){
		this.flatCnt = flatCnt;
	} 
	
	public String getFlatCnt(){
		return flatCnt;
	} 
			
	public void setFallCnt(String fallCnt){
		this.fallCnt = fallCnt;
	} 
	
	public String getFallCnt(){
		return fallCnt;
	} 
			
	public void setCurUdWidth(Double curUdWidth){
		this.curUdWidth = curUdWidth;
	} 
	
	public Double getCurUdWidth(){
		return curUdWidth;
	} 
			
	public void setOrderBy(Integer orderBy){
		this.orderBy = orderBy;
	} 
	
	public Integer getOrderBy(){
		return orderBy;
	} 
	}
