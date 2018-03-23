package cn.hzskt.bdtg.manage.domain;
import java.text.SimpleDateFormat;

import cn.hzskt.bdtg.common.domain.BaseDomain;


/**
 * @description:
 * @author: autoCode
 * @history:
 */
public class Content extends BaseDomain {

	private static final long serialVersionUID = 1L;
	
				
	/** 编号**/
	private Long id;
			
	/** 分类**/
	private Long typeCode;
			
	/** 标题**/
	private String contentTile;
			
	/** 摘要**/
	private String contentAbstract;
	/** 来源**/
	private String source;
			
	/** 主图片**/
	private String picture;
			
	/** 城市**/
	private String contentCity;
			
	/** 标签**/
	private String tag;
			
	/** 内容**/
	private String content;
			
	/** 发生日期**/
	private java.util.Date newsDate;
	
	private java.util.Date startDate;
	private java.util.Date endDate;
			
	/** 更新时间**/
	private java.util.Date updateDate;
			
	/** 录入时间**/
	private java.util.Date addDate;
			
	/** 排序**/
	private Integer contentOrder;
	/** 置顶 **/
	private boolean toHome=false;
	
	/** 图片集**/
	private String pictures;
	
	/** 状态1、已修改,2初始导入，3、已审核 **/
	private String status;
	
	/** 外站对应URL**/
	private String url;
	
	/** 城市名称 **/
	private String cityName;
	
	/**
	 * 栏目名称
	 */
	private String categoryName;
	
	/**
	 * 定时发布设定
	 */
	private String publishScheduleDate;
	
	/** 平台数据 */
	private String plaformData;
	
	/** 基础浏览数量 */
	private String baseViews;
	
	/** 一般评价 */
	private String normalEvas;
	
	/** 好评价 */
	private String goodEvas;
	
	/** 差评价 */
	private String badEvas;	
	
	/** 评论数 */
	private String  commentCount;
	
	/** 更新时间**/
	private java.util.Date shelfDate;
	
	
	public java.util.Date getShelfDate() {
		return shelfDate;
	}

	public void setShelfDate(java.util.Date shelfDate) {
		this.shelfDate = shelfDate;
	}

	/**
	  * 编号
	  *
	  */
	public void setId(Long id){
		this.id = id;
	} 
	
	/**
	  * 编号
	  *
	  */
	public Long getId(){
		return id;
	} 
			
	/**
	  * 分类
	  *
	  */
	public void setTypeCode(Long typeCode){
		this.typeCode = typeCode;
	} 
	
	/**
	  * 分类
	  *
	  */
	public Long getTypeCode(){
		return typeCode;
	} 
			
	/**
	  * 标题
	  *
	  */
	public void setContentTile(String contentTile){
		this.contentTile = contentTile;
	} 
	
	/**
	  * 标题
	  *
	  */
	public String getContentTile(){
		return contentTile;
	} 
			
	/**
	  * 摘要
	  *
	  */
	public void setContentAbstract(String contentAbstract){
		this.contentAbstract = contentAbstract;
	} 
	
	/**
	  * 摘要
	  *
	  */
	public String getContentAbstract(){
		return contentAbstract;
	} 
			
	/**
	  * 主图片
	  *
	  */
	public void setPicture(String picture){
		this.picture = picture;
	} 
	
	/**
	  * 主图片
	  *
	  */
	public String getPicture(){
		return picture;
	} 
			
	/**
	  * 城市
	  *
	  */
	public void setContentCity(String contentCity){
		this.contentCity = contentCity;
	} 
	
	/**
	  * 城市
	  *
	  */
	public String getContentCity(){
		return contentCity;
	} 
			
	/**
	  * 标签
	  *
	  */
	public void setTag(String tag){
		this.tag = tag;
	} 
	
	/**
	  * 标签
	  *
	  */
	public String getTag(){
		return tag;
	} 
	/**
	 * 
	 * @Title: getTags 
	 * @return: String[]
	 */
	public String[] getTags(){
		if(null  == tag){
			return null;
		}
		return tag.split(";");
	}
	/**
	  * 内容
	  *
	  */
	public void setContent(String content){
		this.content = content;
	} 
	
	/**
	  * 内容
	  *
	  */
	public String getContent(){
		return content;
	} 
			
	/**
	  * 发生日期
	  *
	  */
	public void setNewsDate(java.util.Date newsDate){
		this.newsDate = newsDate;
	} 
	
	/**
	  * 发生日期
	  *
	  */
	public java.util.Date getNewsDate(){
		return newsDate;
	}
		
	public String getNewsDateString(){
		return (newsDate == null) ? "" : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(newsDate);
	}
	/**
	  * 更新时间
	  *
	  */
	public void setUpdateDate(java.util.Date updateDate){
		this.updateDate = updateDate;
	} 
	
	/**
	  * 更新时间
	  *
	  */
	public java.util.Date getUpdateDate(){
		return updateDate;
	} 
			
	/**
	  * 录入时间
	  *
	  */
	public void setAddDate(java.util.Date addDate){
		this.addDate = addDate;
	} 
	
	/**
	  * 录入时间
	  *
	  */
	public java.util.Date getAddDate(){
		return addDate;
	} 
	
			
	/**
	  * 排序
	  *
	  */
	public void setContentOrder(Integer contentOrder){
		this.contentOrder = contentOrder;
	} 
	
	/**
	  * 排序
	  *
	  */
	public Integer getContentOrder(){
		return contentOrder;
	}

	/**
	 * @return the toHome
	 */
	public boolean isToHome() {
		return toHome;
	}

	/**
	 * @param toHome the toHome to set
	 */
	public void setToHome(boolean toHome) {
		this.toHome = toHome;
	}

	public String getPictures() {
		return pictures;
	}

	public void setPictures(String pictures) {
		this.pictures = pictures;
	}
	/** 
	 * 状态1、已发布,2初始导入，3、已审核
	 **/
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	} 
	
	public String getPublishScheduleDate() {
		return publishScheduleDate;
	}

	public void setPublishScheduleDate(String publishScheduleDate) {
		this.publishScheduleDate = publishScheduleDate;
	}
	
	/**
	 * 获取固定长度的内容
	 * 
	 * @param field
	 * @param length
	 * @return
	 */
	public String getShowText(String field, int length) {
		if (field != null) {
			return field.length() > length ? field.substring(0,length) : field;
		} 
		return field;
	}
	
	
	public String getFormatDatString(java.util.Date date){
		return (date == null) ? "" : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}

	public java.util.Date getStartDate() {
		return startDate;
	}

	public void setStartDate(java.util.Date startDate) {
		this.startDate = startDate;
	}

	public java.util.Date getEndDate() {
		return endDate;
	}

	public void setEndDate(java.util.Date endDate) {
		this.endDate = endDate;
	}

	public String getPlaformData() {
		return plaformData;
	}

	public void setPlaformData(String plaformData) {
		this.plaformData = plaformData;
	}

	public String getBaseViews() {
		return baseViews;
	}

	public void setBaseViews(String baseViews) {
		this.baseViews = baseViews;
	}

	public String getNormalEvas() {
		return normalEvas;
	}

	public void setNormalEvas(String normalEvas) {
		this.normalEvas = normalEvas;
	}

	public String getGoodEvas() {
		return goodEvas;
	}

	public void setGoodEvas(String goodEvas) {
		this.goodEvas = goodEvas;
	}

	public String getBadEvas() {
		return badEvas;
	}

	public void setBadEvas(String badEvas) {
		this.badEvas = badEvas;
	}

	public String getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(String commentCount) {
		this.commentCount = commentCount;
	}
	
}
