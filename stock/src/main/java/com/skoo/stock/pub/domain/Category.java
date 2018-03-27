package com.skoo.stock.pub.domain;
import com.skoo.orm.domain.BaseEntity;


/**
 * @description:
 * @author: autoCode
 * @history:
 */
public class Category extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
				
	/** 父栏目编号**/
	private Long upId;
	
	/** 上级栏目 **/
	private Category upCategory;
			
	/** 栏目编码**/
	private String catCode;
			
	/** 栏目名称**/
	private String catName;
			
	/** 组织机构**/
	private Long catOrg;
			
	/** 1:显示，0：不显示**/
	private String isShow;
			
	/** 顺序**/
	private Integer catOrder;
	
					
	public Category getUpCategory() {
		return upCategory;
	}

	public void setUpCategory(Category upCategory) {
		this.upCategory = upCategory;
	}

	public void setUpId(Long upId){
		this.upId = upId;
	} 
	
	public Long getUpId(){
		return upId;
	} 
			
	public void setCatCode(String catCode){
		this.catCode = catCode;
	} 
	
	public String getCatCode(){
		return catCode;
	} 
			
	public void setCatName(String catName){
		this.catName = catName;
	} 
	
	public String getCatName(){
		return catName;
	} 
			
	public void setCatOrg(Long catOrg){
		this.catOrg = catOrg;
	} 
	
	public Long getCatOrg(){
		return catOrg;
	} 
			
	public void setIsShow(String isShow){
		this.isShow = isShow;
	} 
	
	public String getIsShow(){
		return isShow;
	} 
			
	public void setCatOrder(Integer catOrder){
		this.catOrder = catOrder;
	} 
	
	public Integer getCatOrder(){
		return catOrder;
	} 
	}
