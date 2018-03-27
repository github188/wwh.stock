package com.skoo.stock.bus.domain;
import com.skoo.orm.domain.BaseEntity;
import com.skoo.stock.sys.utils.json.DictAnnotation;
import org.apache.ibatis.type.Alias;


/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Alias("ConsultingComplaints")
public class ConsultingComplaints extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
				
	/** 问题类型**/
	@DictAnnotation(key = "question_type")
	private String type;
			
	/** 问题标题**/
	private String caption;
			
	/** 问题内容**/
	private String content;
			
	/** 联系方式（手机）**/
	private String phone;
			
	/** 邮箱**/
	private String email;
			
	/** 回复内容**/
	private String reply;
			
	/** 回复状态**/
	@DictAnnotation(key = "reply_status")
	private String replyStatus;
			
	/** 回复时间**/
	private java.util.Date replyTime;
			
	/** 是否在前台显示**/
	@DictAnnotation(key = "flag")
	private String toshow;
					
	public void setType(String type){
		this.type = type;
	} 
	
	public String getType(){
		return type;
	} 
			
	public void setCaption(String caption){
		this.caption = caption;
	} 
	
	public String getCaption(){
		return caption;
	} 
			
	public void setContent(String content){
		this.content = content;
	} 
	
	public String getContent(){
		return content;
	} 
			
	public void setPhone(String phone){
		this.phone = phone;
	} 
	
	public String getPhone(){
		return phone;
	} 
			
	public void setEmail(String email){
		this.email = email;
	} 
	
	public String getEmail(){
		return email;
	} 
			
	public void setReply(String reply){
		this.reply = reply;
	} 
	
	public String getReply(){
		return reply;
	} 
			
	public void setReplyStatus(String replyStatus){
		this.replyStatus = replyStatus;
	} 
	
	public String getReplyStatus(){
		return replyStatus;
	} 
			
	public void setReplyTime(java.util.Date replyTime){
		this.replyTime = replyTime;
	} 
	
	public java.util.Date getReplyTime(){
		return replyTime;
	} 
			
	public void setToshow(String show){
		this.toshow = show;
	} 
	
	public String getToshow(){
		return toshow;
	} 
	}
