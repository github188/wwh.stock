package cn.hzskt.bdtg.manage.domain;

import java.util.List;

public class GridTreeModel {
	private long id;
	private long upId;
	private String catCode;
	private String catName;
	private long catOrg;
	private String isShow;
	private Integer catOrder;
	private String upCatName;
	private String state;
	private List<GridTreeModel> children;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public long getUpId() {
		return upId;
	}

	public void setUpId(long upId) {
		this.upId = upId;
	}

	public String getCatCode() {
		return catCode;
	}

	public void setCatCode(String catCode) {
		this.catCode = catCode;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public long getCatOrg() {
		return catOrg;
	}

	public void setCatOrg(long catOrg) {
		this.catOrg = catOrg;
	}

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	public Integer getCatOrder() {
		return catOrder;
	}

	public void setCatOrder(Integer catOrder) {
		this.catOrder = catOrder;
	}

	
	public String getUpCatName() {
		return upCatName;
	}

	public void setUpCatName(String upCatName) {
		this.upCatName = upCatName;
	}

	public List<GridTreeModel> getChildren() {
		return children;
	}

	public void setChildren(List<GridTreeModel> children) {
		this.children = children;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	public String getText(){
		return this.catName;
	}
}
