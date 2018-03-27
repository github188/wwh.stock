package com.skoo.stock.pub.domain;
import com.skoo.orm.domain.BaseEntity;
import com.skoo.stock.sys.utils.json.DictAnnotation;
import org.apache.ibatis.type.Alias;

import java.util.Date;


/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Alias("ContentCust")
public class ContentCust extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/** 语言**/
	@DictAnnotation(key="lang")
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

	/** 排序日期**/
	private java.util.Date releaseDate;
			
	/** 固顶级别**/
	private Integer topLevel;
			
	/** 是否推荐**/
	@DictAnnotation(key="flag")
	private String isRecommend;
			
	/** 关键词**/
	private String keywd;
			
	/** 状态(0:草稿;1:审核中;2:审核通过;3:回收站)**/
	@DictAnnotation(key="pub_status")
	private String status;

	/** 内容类型**/
	private String contentType;

	/** 标题**/
	private String title;

	/** 简短标题**/
	private String shortTitle;

	/** 作者**/
	private String author;

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Integer getModelId() {
		return modelId;
	}

	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public String getPubYear() {
		return pubYear;
	}

	public void setPubYear(String pubYear) {
		this.pubYear = pubYear;
	}

	public Date getSortDate() {
		return sortDate;
	}

	public void setSortDate(Date sortDate) {
		this.sortDate = sortDate;
	}

	public Integer getTopLevel() {
		return topLevel;
	}

	public void setTopLevel(Integer topLevel) {
		this.topLevel = topLevel;
	}

	public String getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(String isRecommend) {
		this.isRecommend = isRecommend;
	}

	public String getKeywd() {
		return keywd;
	}

	public void setKeywd(String keywd) {
		this.keywd = keywd;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getShortTitle() {
		return shortTitle;
	}

	public void setShortTitle(String shortTitle) {
		this.shortTitle = shortTitle;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
}
