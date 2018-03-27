package com.skoo.stock.app.domain;
import com.skoo.orm.domain.BaseEntity;


/**
 * @description:
 * @author: autoCode
 * @history:
 */
public class AppVersions extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
				
	/** ios版本号**/
	private String iosversion;
			
	/** 安卓版本号**/
	private String androidversion;
			
	/** ios是否强制更新**/
	private String iosforceupdflg;
			
	/** 安卓是否强制更新**/
	private String androidforceupdflg;
			
	/** ios更新描述**/
	private String iosdesc;
			
	/** 安卓更新描述**/
	private String androiddesc;
			
	/** 排序**/
	private Integer orderBy;
					
	public void setIosversion(String iosversion){
		this.iosversion = iosversion;
	} 
	
	public String getIosversion(){
		return iosversion;
	} 
			
	public void setAndroidversion(String androidversion){
		this.androidversion = androidversion;
	} 
	
	public String getAndroidversion(){
		return androidversion;
	} 
			
	public void setIosforceupdflg(String iosforceupdflg){
		this.iosforceupdflg = iosforceupdflg;
	} 
	
	public String getIosforceupdflg(){
		return iosforceupdflg;
	} 
			
	public void setAndroidforceupdflg(String androidforceupdflg){
		this.androidforceupdflg = androidforceupdflg;
	} 
	
	public String getAndroidforceupdflg(){
		return androidforceupdflg;
	} 
			
	public void setIosdesc(String iosdesc){
		this.iosdesc = iosdesc;
	} 
	
	public String getIosdesc(){
		return iosdesc;
	} 
			
	public void setAndroiddesc(String androiddesc){
		this.androiddesc = androiddesc;
	} 
	
	public String getAndroiddesc(){
		return androiddesc;
	} 
			
	public void setOrderBy(Integer orderBy){
		this.orderBy = orderBy;
	} 
	
	public Integer getOrderBy(){
		return orderBy;
	} 
	}
