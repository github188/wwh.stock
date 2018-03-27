package com.skoo.stock.hsa.domain;
import com.skoo.orm.domain.BaseEntity;
import org.apache.ibatis.type.Alias;


/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Alias("TurnsendDetail")
public class TurnsendDetail extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
				
	/** 代码**/
	private String code;
			
	/** 简称**/
	private String name;
			
	/** 预案公布日**/
	private String planDate;
			
	/** 送股比例(10送X)**/
	private Integer sendScale;
			
	/** 转增比例(10转X)**/
	private Integer turnScale;
			
	/** 派现比例(10派X)**/
	private Double cashScale;
			
	/** 股东大会通过日**/
	private String passDate;
			
	/** 股权登记日**/
	private String registerDate;
			
	/** 除权除息日**/
	private String dividendDate;
			
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
			
	public void setPlanDate(String planDate){
		this.planDate = planDate;
	} 
	
	public String getPlanDate(){
		return planDate;
	} 
			
	public void setSendScale(Integer sendScale){
		this.sendScale = sendScale;
	} 
	
	public Integer getSendScale(){
		return sendScale;
	} 
			
	public void setTurnScale(Integer turnScale){
		this.turnScale = turnScale;
	} 
	
	public Integer getTurnScale(){
		return turnScale;
	} 
			
	public void setCashScale(Double cashScale){
		this.cashScale = cashScale;
	} 
	
	public Double getCashScale(){
		return cashScale;
	} 
			
	public void setPassDate(String passDate){
		this.passDate = passDate;
	} 
	
	public String getPassDate(){
		return passDate;
	} 
			
	public void setRegisterDate(String registerDate){
		this.registerDate = registerDate;
	} 
	
	public String getRegisterDate(){
		return registerDate;
	} 
			
	public void setDividendDate(String dividendDate){
		this.dividendDate = dividendDate;
	} 
	
	public String getDividendDate(){
		return dividendDate;
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
