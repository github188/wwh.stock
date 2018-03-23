package com.zjhcsoft.struc.util.datasource.formatter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 时间格式化
 * 
 * @author wujunchao
 * @since 2015年03月20日
 */
public class NumberFormat implements Formatter {

	/**
	 * 格式化后的时间格式是yyyy-MM-dd HH:mm:ss
	 */
	@Override
	public String format(String formattingString) {
		formattingString = formattingString.trim();

//		String regular1 = "[1-2]{1}\\d{3}";
		String regular1 ="[\\d]+";
		Matcher matcher1 = Pattern.compile(regular1).matcher(formattingString);

		String formatnum = "";
		if (matcher1.find()) {
			formatnum = matcher1.group(0);
		}
		return formatnum;
	}

	public static void main(String[] args) {
		Formatter formatter = new NumberFormat();
		System.out.println(formatter.format("杭州萧山供水有限公司2015"));

	}
}
