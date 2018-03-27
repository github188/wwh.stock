package com.skoo.stock.pub.domain;
import com.skoo.orm.domain.BaseEntity;


/**
 * @description:
 * @author: autoCode
 * @history:
 */
public class Channel extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
				
	/** 父栏目编号**/
	private Long upId;
	
	/** 上级栏目 **/
	private Channel upChannel;
			
	/** 栏目编码**/
	private String chnCode;
			
	/** 栏目名称**/
	private String chnName;
			
	/** 组织机构**/
	private Long chnOrg;
			
	/** 1:显示，0：不显示**/
	private String isShow;
			
	/** 顺序**/
	private Integer chnOrder;


	public Channel getUpChannel() {
		return upChannel;
	}

	public void setUpChannel(Channel upChannel) {
		this.upChannel = upChannel;
	}

	public void setUpId(Long upId){
		this.upId = upId;
	} 
	
	public Long getUpId(){
		return upId;
	} 
			
	public void setChnCode(String chnCode){
		this.chnCode = chnCode;
	} 
	
	public String getChnCode(){
		return chnCode;
	} 
			
	public void setChnName(String chnName){
		this.chnName = chnName;
	} 
	
	public String getChnName(){
		return chnName;
	} 
			
	public void setChnOrg(Long chnOrg){
		this.chnOrg = chnOrg;
	} 
	
	public Long getChnOrg(){
		return chnOrg;
	} 
			
	public void setIsShow(String isShow){
		this.isShow = isShow;
	} 
	
	public String getIsShow(){
		return isShow;
	} 
			
	public void setChnOrder(Integer chnOrder){
		this.chnOrder = chnOrder;
	} 
	
	public Integer getChnOrder(){
		return chnOrder;
	} 
	}
