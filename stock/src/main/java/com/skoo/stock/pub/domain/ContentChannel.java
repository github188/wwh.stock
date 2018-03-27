package com.skoo.stock.pub.domain;
import com.skoo.orm.domain.BaseEntity;
import org.apache.ibatis.type.Alias;


/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Alias("ContentChannel")
public class ContentChannel extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
				
	/** 栏目ID**/
	private Integer channelId;
			
	/** 内容ID**/
	private Integer contentId;
			
	/** 排序**/
	private Integer orderBy;
					
	public void setChannelId(Integer channelId){
		this.channelId = channelId;
	} 
	
	public Integer getChannelId(){
		return channelId;
	} 
			
	public void setContentId(Integer contentId){
		this.contentId = contentId;
	} 
	
	public Integer getContentId(){
		return contentId;
	} 
			
	public void setOrderBy(Integer orderBy){
		this.orderBy = orderBy;
	} 
	
	public Integer getOrderBy(){
		return orderBy;
	} 
	}
