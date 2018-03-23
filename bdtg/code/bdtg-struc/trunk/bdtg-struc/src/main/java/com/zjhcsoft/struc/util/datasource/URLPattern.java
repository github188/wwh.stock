package com.zjhcsoft.struc.util.datasource;

import java.util.List;

public class URLPattern {

	private String charset = "utf-8";
	private String datatype = "html/xml";
	private String fetchtype = "http";
	private String httpmethod = "POST";
	private String url;
	private List<Mapping> mappingList;
	private Outlink outlink;

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public String getFetchtype() {
		return fetchtype;
	}

	public void setFetchtype(String fetchtype) {
		this.fetchtype = fetchtype;
	}

	public String getHttpmethod() {
		return httpmethod;
	}

	public void setHttpmethod(String httpmethod) {
		this.httpmethod = httpmethod;
	}
}
