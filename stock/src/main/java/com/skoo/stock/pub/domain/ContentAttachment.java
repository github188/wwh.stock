package com.skoo.stock.pub.domain;
import com.skoo.orm.domain.BaseEntity;
import org.apache.ibatis.type.Alias;


/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Alias("ContentAttachment")
public class ContentAttachment extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
				
	/** 内容ID**/
	private Integer contentId;
			
	/** 排列顺序**/
	private Integer priority;
			
	/** 附件路径**/
	private String attachmentPath;
			
	/** 附件名称**/
	private String attachmentName;
			
	/** 文件名**/
	private String filename;
			
	/** 下载次数**/
	private Integer downloadCount;
			
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
			
	public void setAttachmentPath(String attachmentPath){
		this.attachmentPath = attachmentPath;
	} 
	
	public String getAttachmentPath(){
		return attachmentPath;
	} 
			
	public void setAttachmentName(String attachmentName){
		this.attachmentName = attachmentName;
	} 
	
	public String getAttachmentName(){
		return attachmentName;
	} 
			
	public void setFilename(String filename){
		this.filename = filename;
	} 
	
	public String getFilename(){
		return filename;
	} 
			
	public void setDownloadCount(Integer downloadCount){
		this.downloadCount = downloadCount;
	} 
	
	public Integer getDownloadCount(){
		return downloadCount;
	} 
			
	public void setOrderBy(Integer orderBy){
		this.orderBy = orderBy;
	} 
	
	public Integer getOrderBy(){
		return orderBy;
	} 
	}
