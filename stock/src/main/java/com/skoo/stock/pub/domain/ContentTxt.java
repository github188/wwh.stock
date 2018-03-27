package com.skoo.stock.pub.domain;
import com.skoo.orm.domain.BaseEntity;
import org.apache.ibatis.type.Alias;


/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Alias("ContentTxt")
public class ContentTxt extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
				
	/** 内容ID**/
	private Integer contentId;
			
	/** 文章内容**/
	private String txt;
			
	/** 扩展内容1**/
	private String txt1;
			
	/** 扩展内容2**/
	private String txt2;
			
	/** 扩展内容3**/
	private String txt3;
			
	/** 描述**/
	private String description;
			
	/** 排序**/
	private Integer orderBy;
					
	public void setContentId(Integer contentId){
		this.contentId = contentId;
	} 
	
	public Integer getContentId(){
		return contentId;
	} 
			
	public void setTxt(String txt){
		this.txt = txt;
	} 
	
	public String getTxt(){
		return txt;
	} 
			
	public void setTxt1(String txt1){
		this.txt1 = txt1;
	} 
	
	public String getTxt1(){
		return txt1;
	} 
			
	public void setTxt2(String txt2){
		this.txt2 = txt2;
	} 
	
	public String getTxt2(){
		return txt2;
	} 
			
	public void setTxt3(String txt3){
		this.txt3 = txt3;
	} 
	
	public String getTxt3(){
		return txt3;
	} 
			
	public void setDescription(String description){
		this.description = description;
	} 
	
	public String getDescription(){
		return description;
	} 
			
	public void setOrderBy(Integer orderBy){
		this.orderBy = orderBy;
	} 
	
	public Integer getOrderBy(){
		return orderBy;
	} 
	}
