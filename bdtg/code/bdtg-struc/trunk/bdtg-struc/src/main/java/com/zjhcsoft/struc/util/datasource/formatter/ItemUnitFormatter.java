package com.zjhcsoft.struc.util.datasource.formatter;

/**
 * 单位格式化类，名称和单位在一起时使用。保留名称，去掉相应的单位
 * 
 * @author wangtao
 * @since 2014年10月29日
 */
public class ItemUnitFormatter implements Formatter {

	/**
	 * 浊度(NTU)格式化后的时间格式是浊度
	 */
	@Override
	public String format(String formattingString) {
		char f[] = formattingString.toCharArray();
		Integer letterIndex = null;
		for (int i = 0; i < f.length; i++) {
			if (!Character.isLetter(f[i])&&!Character.isDigit(f[i])) {
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
		Formatter formatter = new ItemUnitFormatter();
		LOG.info(formatter.format("浊度mg/L"));
	}

}
