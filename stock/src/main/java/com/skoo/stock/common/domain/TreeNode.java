package com.skoo.stock.common.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TreeNode implements Serializable {

    private long id;

    private long pid;

    private String text;

    private String state;

    private Map<String, String> attributes;

    private boolean checked;

    private List<TreeNode> children;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public List<TreeNode> getChildren() {

        children = children == null ? new ArrayList<TreeNode>() : children;
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }
}
