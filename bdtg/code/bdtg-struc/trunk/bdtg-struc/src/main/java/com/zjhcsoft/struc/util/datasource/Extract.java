package com.zjhcsoft.struc.util.datasource;

public class Extract {

	private String extractAttr = "href";
	private String include;
	private String exclude;
	private String ignoreXpath2Format;

	public String getExtractAttr() {
		return extractAttr;
	}

	public void setExtractAttr(String extractAttr) {
		this.extractAttr = extractAttr;
	}

	public String getInclude() {
		return include;
	}

	public void setInclude(String include) {
		this.include = include;
	}

	public String getExclude() {
		return exclude;
	}

	public void setExclude(String exclude) {
		this.exclude = exclude;
	}

	public String getIgnoreXpath2Format() {
		return ignoreXpath2Format;
	}

	public void setIgnoreXpath2Format(String ignoreXpath2Format) {
		this.ignoreXpath2Format = ignoreXpath2Format;
	}

}
