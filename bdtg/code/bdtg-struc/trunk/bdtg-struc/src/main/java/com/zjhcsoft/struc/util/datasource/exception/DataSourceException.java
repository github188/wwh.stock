package com.zjhcsoft.struc.util.datasource.exception;

public class DataSourceException extends Exception {

	private static final long serialVersionUID = -6150711454655178151L;

	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
