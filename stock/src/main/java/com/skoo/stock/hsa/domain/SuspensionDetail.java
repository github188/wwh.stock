package com.skoo.stock.hsa.domain;
import com.skoo.orm.domain.BaseEntity;
import org.apache.ibatis.type.Alias;


/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Alias("SuspensionDetail")
public class SuspensionDetail extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
				
	/** 代码**/
	private String code;
			
	/** 简称**/
	private String name;
			
	/** 相关资料**/
	private String relativeInfo;
			
	/** 停牌时间**/
	private String startDate;
			
	/** 停牌截止时间**/
	private String endDate;
			
	/** 预计复牌时间**/
	private String resumeDate;
			
	/** 停牌期限**/
	private String suspensionPeriod;
			
	/** 停牌原因**/
	private String suspensionReason;
			
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
			
	public void setRelativeInfo(String relativeInfo){
		this.relativeInfo = relativeInfo;
	} 
	
	public String getRelativeInfo(){
		return relativeInfo;
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
			
	public void setResumeDate(String resumeDate){
		this.resumeDate = resumeDate;
	} 
	
	public String getResumeDate(){
		return resumeDate;
	} 
			
	public void setSuspensionPeriod(String suspensionPeriod){
		this.suspensionPeriod = suspensionPeriod;
	} 
	
	public String getSuspensionPeriod(){
		return suspensionPeriod;
	} 
			
	public void setSuspensionReason(String suspensionReason){
		this.suspensionReason = suspensionReason;
	} 
	
	public String getSuspensionReason(){
		return suspensionReason;
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
