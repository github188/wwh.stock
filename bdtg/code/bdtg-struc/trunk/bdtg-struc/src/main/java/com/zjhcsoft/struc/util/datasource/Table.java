package com.zjhcsoft.struc.util.datasource;

import java.util.List;

public class Table {

	private String schema;
	private String tableName;
	private boolean insertJudge = false;
	private boolean updateJudge = false;
	private List<Mapping> mappingList;
	private RepeatMapping repeatMapping;
	private String checkpath=null;
	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
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

	public String getCheckpath() {
		return checkpath;
	}

	public void setCheckpath(String checkpath) {
		this.checkpath = checkpath;
	}
	
	
}
