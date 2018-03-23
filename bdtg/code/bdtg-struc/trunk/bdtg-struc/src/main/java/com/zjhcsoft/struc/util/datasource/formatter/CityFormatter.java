package com.zjhcsoft.struc.util.datasource.formatter;

/**
 * 时间格式化
 * 
 * @author wujunchao
 * @since 2014年10月28日
 */
public class CityFormatter implements Formatter {

	/**
	 * 格式化后的时间格式是yyyy-MM-dd HH:mm:ss
	 */
	@Override
	public String format(String formattingString) {
		String zoneName ="";
		formattingString = formattingString.trim();
		formattingString =formattingString.replace("宁夏","");
		formattingString =formattingString.replace("银川","");
		formattingString =formattingString.replace("石嘴山","");
		formattingString =formattingString.replace("吴忠","");
		formattingString =formattingString.replace("中卫","");
		formattingString =formattingString.replace("固原","");
		formattingString =formattingString.replace("市","");
		formattingString =formattingString.replace("园区","");
		formattingString =formattingString.replace("功能区","");
		formattingString =formattingString.replace("开发区","");
		formattingString =formattingString.replace("经济区","");
		
		if (formattingString.contains("区")) {
			int zoneindex = formattingString.indexOf("区");
			zoneName = formattingString.substring(0, zoneindex+1);
			}
			else if (formattingString.contains("县")) {
			int zoneindex = formattingString.indexOf("县");
			zoneName = formattingString.substring(0, zoneindex+1);
			}
			else{
				return "";
			}
		String formatstring =zoneName; 
		if(formatstring!=""&&formatstring!=null){
			return formatstring;
		}
		return "";
	}

	public static void main(String[] args) {
		Formatter formatter = new CityFormatter();
		// LOG.info(formatter.format("作者：无  来源：总量（科监）处  发布时间：2014-09-23  浏览次数：165  【字号：大 中 小】  【保护视力色：  杏仁黄  秋叶褐  胭脂红  芥末绿  天蓝  雪青  灰  默认色】"));

		System.out.println(formatter.format("海原县西安镇小河桥中靖公路边"));
	}
}
