package com.skoo.stock.sys.domain;
import com.skoo.orm.domain.BaseEntity;
import org.apache.ibatis.type.Alias;


/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Alias("Sysuser")
public class Sysuser extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
				
	/** 用户编号**/
	private String userId;
			
	/** 用户名**/
	private String userName;
			
	/** 密码**/
	private String password;
			
	/** 失效日**/
	private java.util.Date invalidDate;
			
	/** **/
	private String mainDept;
			
	/** **/
	private String mngFlag;
			
	/** **/
	private String secondDept;
			
	/** 顺序**/
	private Integer orderBy;
					
	public void setUserId(String userId){
		this.userId = userId;
	} 
	
	public String getUserId(){
		return userId;
	} 
			
	public void setUserName(String userName){
		this.userName = userName;
	} 
	
	public String getUserName(){
		return userName;
	} 
			
	public void setPassword(String password){
		this.password = password;
	} 
	
	public String getPassword(){
		return password;
	} 
			
	public void setInvalidDate(java.util.Date invalidDate){
		this.invalidDate = invalidDate;
	} 
	
	public java.util.Date getInvalidDate(){
		return invalidDate;
	} 
			
	public void setMainDept(String mainDept){
		this.mainDept = mainDept;
	} 
	
	public String getMainDept(){
		return mainDept;
	} 
			
	public void setMngFlag(String mngFlag){
		this.mngFlag = mngFlag;
	} 
	
	public String getMngFlag(){
		return mngFlag;
	} 
			
	public void setSecondDept(String secondDept){
		this.secondDept = secondDept;
	} 
	
	public String getSecondDept(){
		return secondDept;
	} 
			
	public void setOrderBy(Integer orderBy){
		this.orderBy = orderBy;
	} 
	
	public Integer getOrderBy(){
		return orderBy;
	} 
	}
