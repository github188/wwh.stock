package com.zjhcsoft.struc.util.datasource;

import java.util.List;

public class Outlink {

	private String charset = "utf-8";
	private String datatype = "html/xml";
	private String xpath;
	private String xpathType = "range";
	private Integer number = 1;
	private List<Extract> extractList;
	private List<Mapping> mappingList;
	private Outlink outlink;
	private String numberpath;
	private String fetchtype="";
	private String timeparam;

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getDatatype() {
		return datatype;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	public String getXpath() {
		return xpath;
	}

	public void setXpath(String xpath) {
		this.xpath = xpath;
	}

	public String getXpathType() {
		return xpathType;
	}

	public void setXpathType(String xpathType) {
		this.xpathType = xpathType;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public List<Extract> getExtractList() {
		return extractList;
	}

	public void setExtractList(List<Extract> extractList) {
		this.extractList = extractList;
	}

	public List<Mapping> getMappingList() {
		return mappingList;
	}

	public void setMappingList(List<Mapping> mappingList) {
		this.mappingList = mappingList;
	}

	public Outlink getOutlink() {
		return outlink;
	}

	public void setOutlink(Outlink outlink) {
		this.outlink = outlink;
	}

	public String getNumberpath() {
		return numberpath;
	}

	public void setNumberpath(String numberpath) {
		this.numberpath = numberpath;
	}

	public String getFetchtype() {
		return fetchtype;
	}

	public void setFetchtype(String fetchtype) {
		this.fetchtype = fetchtype;
	}

	public String getTimeparam() {
		return timeparam;
	}

	public void setTimeparam(String timeparam) {
		this.timeparam = timeparam;
	}
	
	 

}
