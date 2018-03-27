package com.skoo.stock.pub.domain;
import com.skoo.orm.domain.BaseEntity;


/**
 * @description:
 * @author: autoCode
 * @history:
 */
public class Question extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
				
	/** 标题**/
	private String title;
			
	/** 留言**/
	private String content;
			
	/** 回复**/
	private String reply;
			
	/** 回复时间**/
	private java.util.Date replyDate;
			
	/** 是否回复**/
	private String isReply;
					
	public void setTitle(String title){
		this.title = title;
	} 
	
	public String getTitle(){
		return title;
	} 
			
	public void setContent(String content){
		this.content = content;
	} 
	
	public String getContent(){
		return content;
	} 
			
	public void setReply(String reply){
		this.reply = reply;
	} 
	
	public String getReply(){
		return reply;
	} 
			
	public void setReplyDate(java.util.Date replyDate){
		this.replyDate = replyDate;
	} 
	
	public java.util.Date getReplyDate(){
		return replyDate;
	} 
			
	public void setIsReply(String isReply){
		this.isReply = isReply;
	} 
	
	public String getIsReply(){
		return isReply;
	} 
	}
