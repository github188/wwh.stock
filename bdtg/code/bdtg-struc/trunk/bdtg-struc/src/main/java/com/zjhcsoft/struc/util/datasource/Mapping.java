package com.zjhcsoft.struc.util.datasource;

public class Mapping {

	private String id;
	private String refId;
	private String refValue;
	private String dbColumn;
	private String xpath;
	private String type = "String";
	private String replace;
	private String startString;
	private String endString;
	private boolean insertJudge = false;
	private boolean updateJudge = false;
	private String formatter;
	private Integer Xbias;
	private Integer Ybias;
	private String defaultValue;
	private String targetX;
	private String targetY;
	private String preid=null;
	private String value;
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getPreid() {
		return preid;
	}

	public void setPreid(String preid) {
		this.preid = preid;
	}

	public Integer getXbias() {
		return Xbias;
	}

	public void setXbias(Integer xbias) {
		Xbias = xbias;
	}

	public Integer getYbias() {
		return Ybias;
	}

	public void setYbias(Integer ybias) {
		Ybias = ybias;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public String getRefValue() {
		return refValue;
	}

	public void setRefValue(String refValue) {
		this.refValue = refValue;
	}

	public String getDbColumn() {
		return dbColumn;
	}

	public void setDbColumn(String dbColumn) {
		this.dbColumn = dbColumn;
	}

	public String getXpath() {
		return xpath;
	}

	public void setXpath(String xpath) {
		this.xpath = xpath;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getReplace() {
		return replace;
	}

	public void setReplace(String replace) {
		this.replace = replace;
	}

	public String getStartString() {
		return startString;
	}

	public void setStartString(String startString) {
		this.startString = startString;
	}

	public String getEndString() {
		return endString;
	}

	public void setEndString(String endString) {
		this.endString = endString;
	}

	public boolean isInsertJudge() {
		return insertJudge;
	}

	public void setInsertJudge(boolean insertJudge) {
		this.insertJudge = insertJudge;
	}

	public boolean isUpdateJudge() {
		return updateJudge;
	}

	public void setUpdateJudge(boolean updateJudge) {
		this.updateJudge = updateJudge;
	}

	public String getFormatter() {
		return formatter;
	}

	public void setFormatter(String formatter) {
		this.formatter = formatter;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public String getTargetX() {
		return targetX;
	}

	public void setTargetX(String targetX) {
		this.targetX = targetX;
	}

	public String getTargetY() {
		return targetY;
	}

	public void setTargetY(String targetY) {
		this.targetY = targetY;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

}
