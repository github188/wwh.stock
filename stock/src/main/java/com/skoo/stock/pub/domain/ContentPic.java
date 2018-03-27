package com.skoo.stock.pub.domain;
import com.skoo.orm.domain.BaseEntity;
import org.apache.ibatis.type.Alias;


/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Alias("ContentPic")
public class ContentPic extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
				
	/** 内容ID**/
	private Integer contentId;
			
	/** 排列顺序**/
	private Integer priority;
			
	/** 图片地址**/
	private String imgPath;
			
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
			
	public void setPriority(Integer priority){
		this.priority = priority;
	} 
	
	public Integer getPriority(){
		return priority;
	} 
			
	public void setImgPath(String imgPath){
		this.imgPath = imgPath;
	} 
	
	public String getImgPath(){
		return imgPath;
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
