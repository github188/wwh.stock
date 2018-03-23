package com.zjhcsoft.struc.util.datasource;

import java.util.List;

public class Tables {

	private String parsetype;
	private String savetype;

	public String getSavetype() {
		return savetype;
	}

	public void setSavetype(String savetype) {
		this.savetype = savetype;
	}

	private List<Table> tableList;

	public List<Table> getTableList() {
		return tableList;
	}

	public void setTableList(List<Table> tableList) {
		this.tableList = tableList;
	}

	public String getParsetype() {
		return parsetype;
	}

	public void setParsetype(String parsetype) {
		this.parsetype = parsetype;
	}
}
