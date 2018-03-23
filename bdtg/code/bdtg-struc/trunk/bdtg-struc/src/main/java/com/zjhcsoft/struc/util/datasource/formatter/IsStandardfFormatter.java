package com.zjhcsoft.struc.util.datasource.formatter;

/**
 * 单位格式化类，名称和单位在一起时使用。保留名称，去掉相应的单位
 * 
 * @author wangtao
 * @since 2014年10月29日
 */
public class IsStandardfFormatter implements Formatter {

	/**
	 * 浊度(NTU)格式化后的时间格式是浊度
	 */
	@Override
	public String format(String formattingString) {
		
		if(formattingString.equals("达标")){
			formattingString = "1";
		}else if(formattingString.equals("不达标")){
			formattingString = "0";
		}else{
			formattingString ="";
		}

		return formattingString;
	}

	public static void main(String[] args) {
		Formatter formatter = new IsStandardfFormatter();
		LOG.info(formatter.format(" /"));
	}

}
