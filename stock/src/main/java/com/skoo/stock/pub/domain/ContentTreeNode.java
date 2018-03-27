package com.skoo.stock.pub.domain;

import com.skoo.orm.domain.BaseEntity;

import java.util.List;

/**
 * @description:
 * @author: autoCode
 * @history:
 */
public class ContentTreeNode extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/** 编号 **/
	private Long id;

	/** 名称 **/
	private String text;

	/** 节点状态 **/
	private String state = "close";
	private boolean checked = false;
	private Object attributes;
	private String iconCls;
	private String pid;
	private  List<ContentTreeNode> children;
	public String getState() {
		return state;
	}

	public void setState(String state) {
		if (state == null) {
			state = "open";
		} else {
			state = "closed";
		}
		this.state = state;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public List<ContentTreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<ContentTreeNode> children) {
		this.children = children;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Object getAttributes() {
		return attributes;
	}

	public void setAttributes(Object attributes) {
		this.attributes = attributes;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

}
