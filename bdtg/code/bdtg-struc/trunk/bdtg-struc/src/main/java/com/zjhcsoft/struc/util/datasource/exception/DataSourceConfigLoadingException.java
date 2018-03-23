package com.zjhcsoft.struc.util.datasource.exception;

public class DataSourceConfigLoadingException extends DataSourceException {

	private static final long serialVersionUID = -6070076300851894431L;

	public DataSourceConfigLoadingException(String msg) {
		if (msg == null || "".equals(msg)) {
			setMsg("数据源配置文件加载失败...");
		} else {
			setMsg(msg);
		}
	}
}
