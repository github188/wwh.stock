package cn.hzstk.securities.task.domain;

import javax.persistence.Column;
import javax.persistence.Table;

import cn.hzstk.securities.common.domain.BaseDomain;

/**
 * @description:任务评价实体类
 * @author: autoCode
 * @history:
 */
@Table(name = "task_mark")
public class Mark extends BaseDomain {

	private static final long serialVersionUID = 1L;

	/** 模型编号 **/
	@Column(name = "model_code")
	private String modelCode;

	/** 源对象编号 **/
	@Column(name = "origin_id")
	private Integer originId;

	/** 对象编号 **/
	@Column(name = "obj_id")
	private Integer objId;

	/** 对象金额 **/
	@Column(name = "obj_cash")
	private Integer objCash;

	/** '评价状态' (0为尚未评 1好 2中 3差) **/
	@Column(name = "mark_status")
	private Integer markStatus;

	/** 评价内容 **/
	@Column(name = "mark_content")
	private String markContent;

	/** 评价时间 **/
	@Column(name = "mark_time")
	private java.util.Date markTime;

	/** 被评者编号 **/
	@Column(name = "uid")
	private Integer uid;

	/** 被评者姓名 **/
	@Column(name = "username")
	private String username;

	/** 自动评论过期时间 **/
	@Column(name = "mark_max_time")
	private Integer markMaxTime;

	/** 评论人编号 **/
	@Column(name = "by_uid")
	private Integer byUid;

	/** 评论人用户名 **/
	@Column(name = "by_username")
	private String byUsername;

	/** '评价项' (12,3=>对威客的评价项,4,5=>对雇主的评价项) **/
	@Column(name = "aid")
	private String aid;

	/** 对应的评价项的星数 **/
	@Column(name = "aid_star")
	private String aidStar;

	/** 评分所得能力值或信誉值 **/
	@Column(name = "mark_value")
	private Integer markValue;

	/** '评论者角色' (1任务发布者或买家 2为任务威客或卖家) **/
	@Column(name = "mark_type")
	private Integer markType;

	/** 评价次数 **/
	@Column(name = "mark_count")
	private Integer markCount;

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	public String getModelCode() {
		return modelCode;
	}

	public void setOriginId(Integer originId) {
		this.originId = originId;
	}

	public Integer getOriginId() {
		return originId;
	}

	public void setObjId(Integer objId) {
		this.objId = objId;
	}

	public Integer getObjId() {
		return objId;
	}

	public void setObjCash(Integer objCash) {
		this.objCash = objCash;
	}

	public Integer getObjCash() {
		return objCash;
	}

	public void setMarkStatus(Integer markStatus) {
		this.markStatus = markStatus;
	}

	public Integer getMarkStatus() {
		return markStatus;
	}

	public void setMarkContent(String markContent) {
		this.markContent = markContent;
	}

	public String getMarkContent() {
		return markContent;
	}

	public void setMarkTime(java.util.Date markTime) {
		this.markTime = markTime;
	}

	public java.util.Date getMarkTime() {
		return markTime;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setMarkMaxTime(Integer markMaxTime) {
		this.markMaxTime = markMaxTime;
	}

	public Integer getMarkMaxTime() {
		return markMaxTime;
	}

	public void setByUid(Integer byUid) {
		this.byUid = byUid;
	}

	public Integer getByUid() {
		return byUid;
	}

	public void setByUsername(String byUsername) {
		this.byUsername = byUsername;
	}

	public String getByUsername() {
		return byUsername;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getAid() {
		return aid;
	}

	public void setAidStar(String aidStar) {
		this.aidStar = aidStar;
	}

	public String getAidStar() {
		return aidStar;
	}

	public void setMarkValue(Integer markValue) {
		this.markValue = markValue;
	}

	public Integer getMarkValue() {
		return markValue;
	}

	public void setMarkType(Integer markType) {
		this.markType = markType;
	}

	public Integer getMarkType() {
		return markType;
	}

	public void setMarkCount(Integer markCount) {
		this.markCount = markCount;
	}

	public Integer getMarkCount() {
		return markCount;
	}
	
}
