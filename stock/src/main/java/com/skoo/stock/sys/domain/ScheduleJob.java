package com.skoo.stock.sys.domain;
import com.skoo.orm.domain.BaseEntity;
import com.skoo.stock.sys.utils.json.DictAnnotation;
import org.apache.ibatis.type.Alias;


/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Alias("ScheduleJob")
public class ScheduleJob extends BaseEntity {
	public static final String STATUS_PAUSE = "1";
	public static final String STATUS_DELETE = "0";
	public static final String STATUS_RESUME = "2";
/*
	public static final String CONCURRENT_IS = "1";
	public static final String CONCURRENT_NOT = "0";
*/

	private static final long serialVersionUID = 1L;
	
				
	/** 任务id**/
	private String jobId;
			
	/** 任务名称**/
	private String jobName;
			
	/** 任务分组**/
	private String jobGroup;
			
	/** 任务状态**/
	@DictAnnotation(key = "job_sts")
	private String jobStatus;
			
	/** cron表达式**/
	private String cronExpression;
			
	/** 描述**/
	private String description;
			
	/** 包名+类名**/
	private String beanClass;
			
	/** 任务是否有状态**/
	private String isConcurrent;
			
	/** spring bean**/
	private String springId;
			
	/** 任务调用的方法名**/
	private String methodName;
			
	/** 顺序**/
	private Integer orderBy;
					
	public void setJobId(String jobId){
		this.jobId = jobId;
	} 
	
	public String getJobId(){
		return jobId;
	} 
			
	public void setJobName(String jobName){
		this.jobName = jobName;
	} 
	
	public String getJobName(){
		return jobName;
	} 
			
	public void setJobGroup(String jobGroup){
		this.jobGroup = jobGroup;
	} 
	
	public String getJobGroup(){
		return jobGroup;
	} 
			
	public void setJobStatus(String jobStatus){
		this.jobStatus = jobStatus;
	} 
	
	public String getJobStatus(){
		return jobStatus;
	} 
			
	public void setCronExpression(String cronExpression){
		this.cronExpression = cronExpression;
	} 
	
	public String getCronExpression(){
		return cronExpression;
	} 
			
	public void setDescription(String description){
		this.description = description;
	} 
	
	public String getDescription(){
		return description;
	} 
			
	public void setBeanClass(String beanClass){
		this.beanClass = beanClass;
	} 
	
	public String getBeanClass(){
		return beanClass;
	} 
			
	public void setIsConcurrent(String isConcurrent){
		this.isConcurrent = isConcurrent;
	} 
	
	public String getIsConcurrent(){
		return isConcurrent;
	} 
			
	public void setSpringId(String springId){
		this.springId = springId;
	} 
	
	public String getSpringId(){
		return springId;
	} 
			
	public void setMethodName(String methodName){
		this.methodName = methodName;
	} 
	
	public String getMethodName(){
		return methodName;
	} 
			
	public void setOrderBy(Integer orderBy){
		this.orderBy = orderBy;
	} 
	
	public Integer getOrderBy(){
		return orderBy;
	} 
	}
