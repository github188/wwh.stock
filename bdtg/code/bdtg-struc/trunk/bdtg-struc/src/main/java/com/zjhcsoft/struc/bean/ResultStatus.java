package com.zjhcsoft.struc.bean;

public enum ResultStatus {
	FETCH_FAILURE("fetch_failure","抓取阶段失败"),
	PARSE_FAILURE("parse_failure","解析阶段失败"),
	SAVE_FAILURE("save_failure","保存阶段失败");
	
	private String statusType;
	private String statusDescription;
	
	ResultStatus(String statusType,String statusDescription){
		this.statusType = statusType;
		this.statusDescription = statusDescription;
	}

	public String getStatusType() {
		return statusType;
	}

	public void setStatusType(String statusType) {
		this.statusType = statusType;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}
}
