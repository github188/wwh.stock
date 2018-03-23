package cn.hzskt.bdtg.task.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import cn.hzskt.bdtg.common.domain.BaseDomain;

/**
 * @description: 任务稿件评论列表
 * @author: autoCode
 * @history:
 */
@Table(name = "task_comment")
public class Comment extends BaseDomain {

	private static final long serialVersionUID = 1L;

	/** 对象编号 **/
	@Column(name = "obj_id")
	private Integer objId;

	/** 源编号 **/
	@Column(name = "origin_id")
	private Integer originId;

	/**
	 * '评论类型'(task=>任务交流,Work=>稿件评论,Kf=>客服留言 ,Shop=>网店评论,Case=>案例留言
	 * ,Service=>服务留言)
	 **/
	@Column(name = "obj_type")
	private String objType;

	/** 父编号 **/
	@Column(name = "p_id")
	private Integer pId;

	/** 用户编号 **/
	@Column(name = "uid")
	private Integer uid;

	/** 用户名 **/
	@Column(name = "username")
	private String username;

	/** 评论内容 **/
	@Column(name = "content")
	private String content;

	/** 评论时间 **/
	@Column(name = "on_time")
	private Date onTime;

	/** 评论状态 **/
	@Column(name = "status")
	private Integer status;

	public void setObjId(Integer objId) {
		this.objId = objId;
	}

	public Integer getObjId() {
		return objId;
	}

	public void setOriginId(Integer originId) {
		this.originId = originId;
	}

	public Integer getOriginId() {
		return originId;
	}

	public void setObjType(String objType) {
		this.objType = objType;
	}

	public String getObjType() {
		return objType;
	}

	public void setPId(Integer pId) {
		this.pId = pId;
	}

	public Integer getPId() {
		return pId;
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

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}

}
