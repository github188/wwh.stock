package com.zjhcsoft.struc.util.datasource.formatter;

/**
 * 水质类别转换
 * 
 * @author wangtao
 * @since 2014年10月29日
 */
public class WaterTypeFormatter implements Formatter {

	/**
	 * 字符串中包含出厂水即转换为1，包含管网水即转换为2
	 */
	@Override
	public String format(String formattingString) {
		if (formattingString.contains("出厂水")) {
			return "1";
		} else if (formattingString.contains("管网水")) {
			return "2";
		} else {
			return null;
		}
	}

}
