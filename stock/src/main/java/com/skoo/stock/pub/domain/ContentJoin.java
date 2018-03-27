package com.skoo.stock.pub.domain;

import com.skoo.orm.domain.BaseEntity;

import java.util.Date;


/**
 * @description:
 * @author: autoCode
 * @history:
 */
public class ContentJoin extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
				
	/** 公告编号**/
	private Integer contentId;
			
	/** 公司**/
	private String corp;
			
	/** 参与者姓名**/
	private String name;
			
	/** 邮件**/
	private String mail;
			
	/** 电话**/
	private String phone;
	
	/** 微信/QQ **/
	private String weixin;
			
	/** 职务**/
	private String duty;
			
	/** 备注**/
	private String memo;
	
	/** 回执日期**/
	private Date createDate;

	/** 活动名**/
	private String contentname;

	/** 栏目名**/
	private String categoryname;

	/** 栏回执日期**/
	private String receiptdate;
	
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getWeixin() {
		return weixin;
	}

	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public void setContentId(Integer contentId){
		this.contentId = contentId;
	} 
	
	public Integer getContentId(){
		return contentId;
	} 
			
	public void setCorp(String corp){
		this.corp = corp;
	} 
	
	public String getCorp(){
		return corp;
	} 
			
	public void setName(String name){
		this.name = name;
	} 
	
	public String getName(){
		return name;
	} 
			
	public void setMail(String mail){
		this.mail = mail;
	} 
	
	public String getMail(){
		return mail;
	} 
			
	public void setPhone(String phone){
		this.phone = phone;
	} 
	
	public String getPhone(){
		return phone;
	} 
			
	public void setMemo(String memo){
		this.memo = memo;
	} 
	
	public String getMemo(){
		return memo;
	}

	public String getContentname() {
		return contentname;
	}

	public void setContentname(String contentname) {
		this.contentname = contentname;
	}

	public String getCategoryname() {
		return categoryname;
	}

	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}

	public String getReceiptdate() {
		return receiptdate;
	}

	public void setReceiptdate(String receiptdate) {
		this.receiptdate = receiptdate;
	}
}
