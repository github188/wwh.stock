package com.skoo.stock.pub.domain;
import com.skoo.orm.domain.BaseEntity;
import org.apache.ibatis.type.Alias;


/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Alias("Content")
public class Content extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
				
	/** 语言**/
	private String lang;
			
	/** 栏目ID**/
	private Integer channelId;
			
	/** 用户ID**/
	private Integer userId;
			
	/** 属性ID**/
	private Integer typeId;
			
	/** 模型ID**/
	private Integer modelId;
			
	/** 站点ID**/
	private Integer siteId;
			
	/** 年度**/
	private String pubYear;
			
	/** 排序日期**/
	private java.util.Date sortDate;
			
	/** 固顶级别**/
	private Integer topLevel;
			
	/** 是否有标题图**/
	private String hasTitleImg;
			
	/** 是否推荐**/
	private String isRecommend;
			
	/** 关键词**/
	private String keywd;
			
	/** 状态(0:草稿;1:审核中;2:审核通过;3:回收站)**/
	private String status;
			
	/** 日访问数**/
	private Integer viewsDay;
			
	/** 日评论数**/
	private Integer commentsDay;
			
	/** 日下载数**/
	private Integer downloadsDay;
			
	/** 日顶数**/
	private Integer upsDay;
			
	/** 排序**/
	private Integer orderBy;
					
	public void setLang(String lang){
		this.lang = lang;
	} 
	
	public String getLang(){
		return lang;
	} 
			
	public void setChannelId(Integer channelId){
		this.channelId = channelId;
	} 
	
	public Integer getChannelId(){
		return channelId;
	} 
			
	public void setUserId(Integer userId){
		this.userId = userId;
	} 
	
	public Integer getUserId(){
		return userId;
	} 
			
	public void setTypeId(Integer typeId){
		this.typeId = typeId;
	} 
	
	public Integer getTypeId(){
		return typeId;
	} 
			
	public void setModelId(Integer modelId){
		this.modelId = modelId;
	} 
	
	public Integer getModelId(){
		return modelId;
	} 
			
	public void setSiteId(Integer siteId){
		this.siteId = siteId;
	} 
	
	public Integer getSiteId(){
		return siteId;
	} 
			
	public void setPubYear(String pubYear){
		this.pubYear = pubYear;
	} 
	
	public String getPubYear(){
		return pubYear;
	} 
			
	public void setSortDate(java.util.Date sortDate){
		this.sortDate = sortDate;
	} 
	
	public java.util.Date getSortDate(){
		return sortDate;
	} 
			
	public void setTopLevel(Integer topLevel){
		this.topLevel = topLevel;
	} 
	
	public Integer getTopLevel(){
		return topLevel;
	} 
			
	public void setHasTitleImg(String hasTitleImg){
		this.hasTitleImg = hasTitleImg;
	} 
	
	public String getHasTitleImg(){
		return hasTitleImg;
	} 
			
	public void setIsRecommend(String isRecommend){
		this.isRecommend = isRecommend;
	} 
	
	public String getIsRecommend(){
		return isRecommend;
	} 
			
	public void setKeywd(String keywd){
		this.keywd = keywd;
	} 
	
	public String getKeywd(){
		return keywd;
	} 
			
	public void setStatus(String status){
		this.status = status;
	} 
	
	public String getStatus(){
		return status;
	} 
			
	public void setViewsDay(Integer viewsDay){
		this.viewsDay = viewsDay;
	} 
	
	public Integer getViewsDay(){
		return viewsDay;
	} 
			
	public void setCommentsDay(Integer commentsDay){
		this.commentsDay = commentsDay;
	} 
	
	public Integer getCommentsDay(){
		return commentsDay;
	} 
			
	public void setDownloadsDay(Integer downloadsDay){
		this.downloadsDay = downloadsDay;
	} 
	
	public Integer getDownloadsDay(){
		return downloadsDay;
	} 
			
	public void setUpsDay(Integer upsDay){
		this.upsDay = upsDay;
	} 
	
	public Integer getUpsDay(){
		return upsDay;
	} 
			
	public void setOrderBy(Integer orderBy){
		this.orderBy = orderBy;
	} 
	
	public Integer getOrderBy(){
		return orderBy;
	} 
	}
