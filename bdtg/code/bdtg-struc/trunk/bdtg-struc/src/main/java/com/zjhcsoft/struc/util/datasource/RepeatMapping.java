package com.zjhcsoft.struc.util.datasource;

import java.util.List;

public class RepeatMapping {

	private String lengthXpath;
	private Integer startNum = 1;
	private Integer endNum = 0;
	private Integer[] ignoreNumArray;;
	private List<Mapping> mappingList;
	private RepeatMapping repeatMapping;

	public String getLengthXpath() {
		return lengthXpath;
	}

	public void setLengthXpath(String lengthXpath) {
		this.lengthXpath = lengthXpath;
	}

	public Integer getStartNum() {
		return startNum;
	}

	public void setStartNum(Integer startNum) {
		this.startNum = startNum;
	}

	public Integer getEndNum() {
		return endNum;
	}

	public void setEndNum(Integer endNum) {
		this.endNum = endNum;
	}

	public Integer[] getIgnoreNumArray() {
		return ignoreNumArray;
	}

	public void setIgnoreNumArray(Integer[] ignoreNumArray) {
		this.ignoreNumArray = ignoreNumArray;
	}

	public List<Mapping> getMappingList() {
		return mappingList;
	}

	public void setMappingList(List<Mapping> mappingList) {
		this.mappingList = mappingList;
	}

	public RepeatMapping getRepeatMapping() {
		return repeatMapping;
	}

	public void setRepeatMapping(RepeatMapping repeatMapping) {
		this.repeatMapping = repeatMapping;
	}

}
