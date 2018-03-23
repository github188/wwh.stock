package com.zjhcsoft.struc.util.datasource.formatter;

/**
 * 单位格式化类，保留值，去掉相应的单位
 * 
 * @author wangtao
 * @since 2014年10月28日
 */
public class UnitFormatter implements Formatter {

	/**
	 * 0.24NTU格式化后的时间格式是0.24
	 */
	@Override
	public String format(String formattingString) {
		char f[] = formattingString.toCharArray();
		Integer letterIndex = null;
		boolean lastCharIsDigitPoint = false;
		for (int i = 0; i < f.length; i++) {
			if (Character.isDigit(f[i]) || f[i] == '.') {
				lastCharIsDigitPoint = true;
			} else if (lastCharIsDigitPoint) {
				letterIndex = i;
				break;
			}
		}
		if (letterIndex != null) {
			return formattingString.substring(0, letterIndex).trim();
		}

		return formattingString;
	}

	public static void main(String[] args) {
		Formatter formatter = new UnitFormatter();
		LOG.info(formatter.format("0.24(NTU)"));
	}

}
