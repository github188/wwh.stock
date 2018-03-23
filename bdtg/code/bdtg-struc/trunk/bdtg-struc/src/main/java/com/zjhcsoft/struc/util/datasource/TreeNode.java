package com.zjhcsoft.struc.util.datasource;

import java.util.HashMap;
import java.util.Map;

public class TreeNode {

	private TreeNode parent;
	private Map<String, TreeNode> children;
	private String url;
	private Map<String, String> valueMap;

	public void addChild(TreeNode child) {
		if (children == null) {
			children = new HashMap<String, TreeNode>();
		}
		children.put(child.getUrl(), child);
	}

	public TreeNode getChild(String url) {
		return children.get(url);
	}

	public TreeNode getParent() {
		return parent;
	}

	public void setParent(TreeNode parent) {
		this.parent = parent;
	}

	public Map<String, TreeNode> getChildren() {
		return children;
	}

	public void setChildren(Map<String, TreeNode> children) {
		this.children = children;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, String> getValueMap() {
		return valueMap;
	}

	public void setValueMap(Map<String, String> valueMap) {
		this.valueMap = valueMap;
	}

}
