package com.bm.wanma.entity;

import java.io.Serializable;
import java.util.ArrayList;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

/**
 *
 */
@Table(name = "tb_information_data")
public class InformationBean implements Serializable {

	
	@Id(column="id")
	private int id;
	/*主键*/
	private String pkNeiId;
	/*资讯标题*/
	private String neiName;
	/*URL地址*/
	private String neiUrl;
	/*1 有效 */
	private String neiStatus;
	/*创建时间*/
	private String neiCreatedate;
	/*更新时间*/
	private String neiUpdatedate;
	/*资讯图片*/
	private String newsPicUrl;
	public InformationBean() { }
	public String getPkNeiId() {
		return pkNeiId;
	}
	public void setPkNeiId(String pkNeiId) {
		this.pkNeiId = pkNeiId;
	}
	public String getNeiName() {
		return neiName;
	}
	public void setNeiName(String neiName) {
		this.neiName = neiName;
	}
	public String getNeiUrl() {
		return neiUrl;
	}
	public void setNeiUrl(String neiUrl) {
		this.neiUrl = neiUrl;
	}
	public String getNeiStatus() {
		return neiStatus;
	}
	public void setNeiStatus(String neiStatus) {
		this.neiStatus = neiStatus;
	}
	public String getNeiCreatedate() {
		return neiCreatedate;
	}
	public void setNeiCreatedate(String neiCreatedate) {
		this.neiCreatedate = neiCreatedate;
	}
	public String getNeiUpdatedate() {
		return neiUpdatedate;
	}
	public void setNeiUpdatedate(String neiUpdatedate) {
		this.neiUpdatedate = neiUpdatedate;
	}
	public String getNewsPicUrl() {
		return newsPicUrl;
	}
	public void setNewsPicUrl(String newsPicUrl) {
		this.newsPicUrl = newsPicUrl;
	}
	
}
