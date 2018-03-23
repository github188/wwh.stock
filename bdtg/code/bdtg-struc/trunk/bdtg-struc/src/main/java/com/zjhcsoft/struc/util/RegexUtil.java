package com.zjhcsoft.struc.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 * 
 * @author wangtao
 * 
 */
public class RegexUtil {
	/**
	 * 通过正则表达式完全匹配
	 * 
	 * @param text
	 * @param regex
	 * @return
	 */
	public static boolean ExactMatch(String text, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);
		return matcher.find();
	}
}
