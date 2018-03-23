package cn.hzskt.bdtg.manage.domain;

import java.util.List;

import cn.hzskt.bdtg.common.domain.BaseDomain;

public class ComboTreeModel {
	private long id;
	
	private String text;
	private String state;
	private List<ComboTreeModel> children;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<ComboTreeModel> getChildren() {
		return children;
	}

	public void setChildren(List<ComboTreeModel> children) {
		this.children = children;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	
}
