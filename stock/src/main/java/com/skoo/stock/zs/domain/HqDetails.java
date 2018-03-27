package com.skoo.stock.zs.domain;
import com.skoo.orm.domain.BaseEntity;
import org.apache.ibatis.type.Alias;


/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Alias("HqDetails")
public class HqDetails extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
				
	/** 日期**/
	private String dt;
			
	/** 代码**/
	private String stockCode;
			
	/** 名称**/
	private String stockName;
			
	/** 涨幅**/
	private String changeWidth;
			
	/** 现价**/
	private String price;
			
	/** 涨跌**/
	private String changeAmount;
			
	/** 买价**/
	private String buyPrice;
			
	/** 卖价**/
	private String salePrice;
			
	/** 总量**/
	private String totalVolume;
			
	/** 现量**/
	private String volume;
			
	/** 涨速**/
	private String changeRate;
			
	/** 换手**/
	private String turnOver;
			
	/** 今开**/
	private String curOpen;
			
	/** 最高**/
	private String high;
			
	/** 最低**/
	private String low;
			
	/** 昨收**/
	private String preClose;
			
	/** 市盈(动)**/
	private String pe;
			
	/** 总金额**/
	private String amount;
			
	/** 量比**/
	private String volumeRatio;
			
	/** 顺序**/
	private Integer orderBy;
					
	public void setDt(String dt){
		this.dt = dt;
	} 
	
	public String getDt(){
		return dt;
	} 
			
	public void setStockCode(String stockCode){
		this.stockCode = stockCode;
	} 
	
	public String getStockCode(){
		return stockCode;
	} 
			
	public void setStockName(String stockName){
		this.stockName = stockName;
	} 
	
	public String getStockName(){
		return stockName;
	} 
			
	public void setChangeWidth(String changeWidth){
		this.changeWidth = changeWidth;
	} 
	
	public String getChangeWidth(){
		return changeWidth;
	} 
			
	public void setPrice(String price){
		this.price = price;
	} 
	
	public String getPrice(){
		return price;
	} 
			
	public void setChangeAmount(String changeAmount){
		this.changeAmount = changeAmount;
	} 
	
	public String getChangeAmount(){
		return changeAmount;
	} 
			
	public void setBuyPrice(String buyPrice){
		this.buyPrice = buyPrice;
	} 
	
	public String getBuyPrice(){
		return buyPrice;
	} 
			
	public void setSalePrice(String salePrice){
		this.salePrice = salePrice;
	} 
	
	public String getSalePrice(){
		return salePrice;
	} 
			
	public void setTotalVolume(String totalVolume){
		this.totalVolume = totalVolume;
	} 
	
	public String getTotalVolume(){
		return totalVolume;
	} 
			
	public void setVolume(String volume){
		this.volume = volume;
	} 
	
	public String getVolume(){
		return volume;
	} 
			
	public void setChangeRate(String changeRate){
		this.changeRate = changeRate;
	} 
	
	public String getChangeRate(){
		return changeRate;
	} 
			
	public void setTurnOver(String turnOver){
		this.turnOver = turnOver;
	} 
	
	public String getTurnOver(){
		return turnOver;
	} 
			
	public void setCurOpen(String curOpen){
		this.curOpen = curOpen;
	} 
	
	public String getCurOpen(){
		return curOpen;
	} 
			
	public void setHigh(String high){
		this.high = high;
	} 
	
	public String getHigh(){
		return high;
	} 
			
	public void setLow(String low){
		this.low = low;
	} 
	
	public String getLow(){
		return low;
	} 
			
	public void setPreClose(String preClose){
		this.preClose = preClose;
	} 
	
	public String getPreClose(){
		return preClose;
	} 
			
	public void setPe(String pe){
		this.pe = pe;
	} 
	
	public String getPe(){
		return pe;
	} 
			
	public void setAmount(String amount){
		this.amount = amount;
	} 
	
	public String getAmount(){
		return amount;
	} 
			
	public void setVolumeRatio(String volumeRatio){
		this.volumeRatio = volumeRatio;
	} 
	
	public String getVolumeRatio(){
		return volumeRatio;
	} 
			
	public void setOrderBy(Integer orderBy){
		this.orderBy = orderBy;
	} 
	
	public Integer getOrderBy(){
		return orderBy;
	} 
	}
