package cn.hzstk.securities.task.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import cn.hzstk.securities.common.domain.BaseDomain;

/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Table(name = "task_bid")
public class TaskBid extends BaseDomain {

	private static final long serialVersionUID = 1L;

	/** 任务编号 **/
	@Column(name = "task_id")
	private Integer taskId;

	/** 用户编号 **/
	@Column(name = "uid")
	private Long uid;

	/** 用户名 **/
	@Column(name = "username")
	private String username;

	/** 投标报价 **/
	@Column(name = "quote")
	private Integer quote;

	/** 投标周期 **/
	@Column(name = "cycle")
	private Integer cycle;

	/** 投标地区 **/
	@Column(name = "area")
	private String area;

	/** 投标备注 **/
	@Column(name = "message")
	private String message;

	/** 投标状态 **/
	@Column(name = "bid_status")
	private Integer bidStatus;

	/** 投标时间 **/
	@Column(name = "bid_time")
	private Date bidTime;

	/** 隐藏状态 **/
	@Column(name = "hidden_status")
	private Integer hiddenStatus;

	/** 扩展状态 **/
	@Column(name = "ext_status")
	private Integer extStatus;

	/** 留言次数 **/
	@Column(name = "comment_num")
	private Integer commentNum;

	/** 是否查看 **/
	@Column(name = "is_view")
	private Integer isView;

	/** 用户中心我的稿件删除后的状态，如果删除状态为1,默认为0,当状态为1的时候 不在我的稿件中显示。 **/
	@Column(name = "hasdel")
	private Integer hasdel;

	/** 是否隐藏 **/
	@Column(name = "workhide")
	private Integer workhide;
	

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Long getUid() {
		return uid;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setQuote(Integer quote) {
		this.quote = quote;
	}

	public Integer getQuote() {
		return quote;
	}

	public void setCycle(Integer cycle) {
		this.cycle = cycle;
	}

	public Integer getCycle() {
		return cycle;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getArea() {
		return area;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setBidStatus(Integer bidStatus) {
		this.bidStatus = bidStatus;
	}

	public Integer getBidStatus() {
		return bidStatus;
	}

	public void setBidTime(Date bidTime) {
		this.bidTime = bidTime;
	}

	public Date getBidTime() {
		return bidTime;
	}

	public void setHiddenStatus(Integer hiddenStatus) {
		this.hiddenStatus = hiddenStatus;
	}

	public Integer getHiddenStatus() {
		return hiddenStatus;
	}

	public void setExtStatus(Integer extStatus) {
		this.extStatus = extStatus;
	}

	public Integer getExtStatus() {
		return extStatus;
	}

	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}

	public Integer getCommentNum() {
		return commentNum;
	}

	public void setIsView(Integer isView) {
		this.isView = isView;
	}

	public Integer getIsView() {
		return isView;
	}

	public void setHasdel(Integer hasdel) {
		this.hasdel = hasdel;
	}

	public Integer getHasdel() {
		return hasdel;
	}

	public void setWorkhide(Integer workhide) {
		this.workhide = workhide;
	}

	public Integer getWorkhide() {
		return workhide;
	}
	
}
