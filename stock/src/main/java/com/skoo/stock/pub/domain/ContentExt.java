package com.skoo.stock.pub.domain;
import com.skoo.orm.domain.BaseEntity;
import org.apache.ibatis.type.Alias;


/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Alias("ContentExt")
public class ContentExt extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
				
	/** 内容ID**/
	private Integer contentId;
			
	/** 内容类型**/
	private String contentType;
			
	/** 标题**/
	private String title;
			
	/** 简短标题**/
	private String shortTitle;
			
	/** 作者**/
	private String author;
			
	/** 来源**/
	private String origin;
			
	/** 来源链接**/
	private String originUrl;
			
	/** 描述**/
	private String description;
			
	/** 发布日期**/
	private java.util.Date releaseDate;
			
	/** 媒体路径**/
	private String mediaPath;
			
	/** 媒体类型**/
	private String mediaType;
			
	/** 标题颜色**/
	private String titleColor;
			
	/** 是否加粗**/
	private String isBold;
			
	/** 标题图片**/
	private String titleImg;
			
	/** 内容图片**/
	private String contentImg;
			
	/** 类型图片**/
	private String typeImg;
			
	/** 外部链接**/
	private String link;
			
	/** 指定模板**/
	private String tplContent;
			
	/** 需要重新生成静态页**/
	private String needRegenerate;
			
	/** 排序**/
	private Integer orderBy;
					
	public void setContentId(Integer contentId){
		this.contentId = contentId;
	} 
	
	public Integer getContentId(){
		return contentId;
	} 
			
	public void setContentType(String contentType){
		this.contentType = contentType;
	} 
	
	public String getContentType(){
		return contentType;
	} 
			
	public void setTitle(String title){
		this.title = title;
	} 
	
	public String getTitle(){
		return title;
	} 
			
	public void setShortTitle(String shortTitle){
		this.shortTitle = shortTitle;
	} 
	
	public String getShortTitle(){
		return shortTitle;
	} 
			
	public void setAuthor(String author){
		this.author = author;
	} 
	
	public String getAuthor(){
		return author;
	} 
			
	public void setOrigin(String origin){
		this.origin = origin;
	} 
	
	public String getOrigin(){
		return origin;
	} 
			
	public void setOriginUrl(String originUrl){
		this.originUrl = originUrl;
	} 
	
	public String getOriginUrl(){
		return originUrl;
	} 
			
	public void setDescription(String description){
		this.description = description;
	} 
	
	public String getDescription(){
		return description;
	} 
			
	public void setReleaseDate(java.util.Date releaseDate){
		this.releaseDate = releaseDate;
	} 
	
	public java.util.Date getReleaseDate(){
		return releaseDate;
	} 
			
	public void setMediaPath(String mediaPath){
		this.mediaPath = mediaPath;
	} 
	
	public String getMediaPath(){
		return mediaPath;
	} 
			
	public void setMediaType(String mediaType){
		this.mediaType = mediaType;
	} 
	
	public String getMediaType(){
		return mediaType;
	} 
			
	public void setTitleColor(String titleColor){
		this.titleColor = titleColor;
	} 
	
	public String getTitleColor(){
		return titleColor;
	} 
			
	public void setIsBold(String isBold){
		this.isBold = isBold;
	} 
	
	public String getIsBold(){
		return isBold;
	} 
			
	public void setTitleImg(String titleImg){
		this.titleImg = titleImg;
	} 
	
	public String getTitleImg(){
		return titleImg;
	} 
			
	public void setContentImg(String contentImg){
		this.contentImg = contentImg;
	} 
	
	public String getContentImg(){
		return contentImg;
	} 
			
	public void setTypeImg(String typeImg){
		this.typeImg = typeImg;
	} 
	
	public String getTypeImg(){
		return typeImg;
	} 
			
	public void setLink(String link){
		this.link = link;
	} 
	
	public String getLink(){
		return link;
	} 
			
	public void setTplContent(String tplContent){
		this.tplContent = tplContent;
	} 
	
	public String getTplContent(){
		return tplContent;
	} 
			
	public void setNeedRegenerate(String needRegenerate){
		this.needRegenerate = needRegenerate;
	} 
	
	public String getNeedRegenerate(){
		return needRegenerate;
	} 
			
	public void setOrderBy(Integer orderBy){
		this.orderBy = orderBy;
	} 
	
	public Integer getOrderBy(){
		return orderBy;
	} 
	}
