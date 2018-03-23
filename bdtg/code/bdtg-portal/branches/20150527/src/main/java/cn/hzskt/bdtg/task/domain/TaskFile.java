package cn.hzskt.bdtg.task.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import cn.hzskt.bdtg.common.domain.BaseDomain;

/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Table(name = "task_file")
public class TaskFile extends BaseDomain {

	private static final long serialVersionUID = 1L;

	/** 对象类 **/
	@Column(name = "obj_type")
	private String objType;

	/** 对象编号 **/
	@Column(name = "obj_id")
	private Integer objId;

	/** 任务编号 **/
	@Column(name = "task_id")
	private Integer taskId;

	/** 稿件编号 **/
	@Column(name = "work_id")
	private Integer workId;

	/** 任务标题 **/
	@Column(name = "task_title")
	private String taskTitle;

	/** 保存前文件 **/
	@Column(name = "file_name")
	private String fileName;

	/** 保存后文件 **/
	@Column(name = "save_name")
	private String saveName;

	/** 用户编号 **/
	@Column(name = "uid")
	private Integer uid;

	/** 用户名 **/
	@Column(name = "username")
	private String username;

	/** 更新时间 **/
	@Column(name = "on_time")
	private Date onTime;

	public void setObjType(String objType) {
		this.objType = objType;
	}

	public String getObjType() {
		return objType;
	}

	public void setObjId(Integer objId) {
		this.objId = objId;
	}

	public Integer getObjId() {
		return objId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setWorkId(Integer workId) {
		this.workId = workId;
	}

	public Integer getWorkId() {
		return workId;
	}

	public void setTaskTitle(String taskTitle) {
		this.taskTitle = taskTitle;
	}

	public String getTaskTitle() {
		return taskTitle;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}

	public String getSaveName() {
		return saveName;
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

	public void setOnTime(Date onTime) {
		this.onTime = onTime;
	}

	public Date getOnTime() {
		return onTime;
	}

}
