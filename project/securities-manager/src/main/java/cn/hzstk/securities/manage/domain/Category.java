package cn.hzstk.securities.manage.domain;

import cn.hzstk.securities.common.domain.BaseDomain;

/**
 * @description:
 * @author: autoCode
 * @history:
 */
public class Category extends BaseDomain {

	private static final long serialVersionUID = 1L;

	/** **/
	private Long id;

	/** **/
	private Long upId;

	/** 活动编码 **/
	private String catCode;

	/** **/
	private String catName;

	/** **/
	private Long catOrg;

	/** 1:显示，0：不显示 **/
	private String isShow;

	/** 排序 **/
	private Integer catOrder;


	/** 父栏目名称 **/
	private String upCatName;

	/**
	  * 
	  *
	  */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	  * 
	  *
	  */
	public Long getId() {
		return id;
	}

	/**
	  * 
	  *
	  */
	public void setUpId(Long upId) {
		this.upId = upId;
	}

	/**
	  * 
	  *
	  */
	public Long getUpId() {
		return upId;
	}

	public String getCatCode() {
		return catCode;
	}

	public void setCatCode(String catCode) {
		this.catCode = catCode;
	}

	/**
	  * 
	  *
	  */
	public void setCatName(String catName) {
		this.catName = catName;
	}

	/**
	  * 
	  *
	  */
	public String getCatName() {
		return catName;
	}

	/**
	  * 
	  *
	  */
	public void setCatOrg(Long catOrg) {
		this.catOrg = catOrg;
	}

	/**
	  * 
	  *
	  */
	public Long getCatOrg() {
		return catOrg;
	}

	/**
	 * 1:显示，0：不显示
	 * 
	 */
	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	/**
	 * 1:显示，0：不显示
	 * 
	 */
	public String getIsShow() {
		return isShow;
	}

	/**
	  * 
	  *
	  */
	public void setCatOrder(Integer catOrder) {
		this.catOrder = catOrder;
	}

	/**
	  * 
	  *
	  */
	public Integer getCatOrder() {
		return catOrder;
	}


	public String getUpCatName() {
		return upCatName;
	}

	public void setUpCatName(String upCatName) {
		this.upCatName = upCatName;
	}

}
