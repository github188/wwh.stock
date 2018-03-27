package com.skoo.stock.zs.domain;
import com.skoo.orm.domain.BaseEntity;
import org.apache.ibatis.type.Alias;


/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Alias("RxData")
public class RxData extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
				
	/** 代码**/
	private String stockCode;
			
	/** 名称**/
	private String stockName;
			
	/** 日期**/
	private String dt;
			
	/** 开盘**/
	private String curOpen;
			
	/** 最高**/
	private String high;
			
	/** 最低**/
	private String low;
			
	/** 收盘**/
	private String curClose;
			
	/** 成交量**/
	private String volume;
			
	/** 5日线**/
	private String ma1;
			
	/** 10日线**/
	private String ma2;
			
	/** 20日线**/
	private String ma3;
			
	/** 60日线**/
	private String ma4;
			
	/** 5日量**/
	private String mavol1;
			
	/** 10日量**/
	private String mavol2;
			
	/** KDJ.K**/
	private String k;
			
	/** KDJ.D**/
	private String d;
			
	/** KDJ.J**/
	private String j;
			
	/** 顺序**/
	private Integer orderBy;
					
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
			
	public void setDt(String dt){
		this.dt = dt;
	} 
	
	public String getDt(){
		return dt;
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
			
	public void setCurClose(String curClose){
		this.curClose = curClose;
	} 
	
	public String getCurClose(){
		return curClose;
	} 
			
	public void setVolume(String volume){
		this.volume = volume;
	} 
	
	public String getVolume(){
		return volume;
	} 
			
	public void setMa1(String ma1){
		this.ma1 = ma1;
	} 
	
	public String getMa1(){
		return ma1;
	} 
			
	public void setMa2(String ma2){
		this.ma2 = ma2;
	} 
	
	public String getMa2(){
		return ma2;
	} 
			
	public void setMa3(String ma3){
		this.ma3 = ma3;
	} 
	
	public String getMa3(){
		return ma3;
	} 
			
	public void setMa4(String ma4){
		this.ma4 = ma4;
	} 
	
	public String getMa4(){
		return ma4;
	} 
			
	public void setMavol1(String mavol1){
		this.mavol1 = mavol1;
	} 
	
	public String getMavol1(){
		return mavol1;
	} 
			
	public void setMavol2(String mavol2){
		this.mavol2 = mavol2;
	} 
	
	public String getMavol2(){
		return mavol2;
	} 
			
	public void setK(String k){
		this.k = k;
	} 
	
	public String getK(){
		return k;
	} 
			
	public void setD(String d){
		this.d = d;
	} 
	
	public String getD(){
		return d;
	} 
			
	public void setJ(String j){
		this.j = j;
	} 
	
	public String getJ(){
		return j;
	} 
			
	public void setOrderBy(Integer orderBy){
		this.orderBy = orderBy;
	} 
	
	public Integer getOrderBy(){
		return orderBy;
	} 
	}
