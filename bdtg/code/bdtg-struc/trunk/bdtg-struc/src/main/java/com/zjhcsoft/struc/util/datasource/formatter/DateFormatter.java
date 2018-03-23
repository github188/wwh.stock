package com.zjhcsoft.struc.util.datasource.formatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.zjhcsoft.struc.util.TimingUtil;

/**
 * 时间格式化
 * 
 * @author wangtao
 * @since 2014年10月28日
 */
public class DateFormatter implements Formatter {

	/**
	 * 格式化后的时间格式是yyyy-MM-dd HH:mm:ss
	 */
	@Override
	public String format(String formattingString) {
		formattingString = formattingString.trim();

		String regular1 = "[1-2]{1}\\d{3}-[0-1]{0,1}\\d-[0-3]{0,1}\\d [0-2]{0,1}\\d:[0-6]{0,1}\\d:[0-6]{0,1}\\d";
		String regular2 = "[1-2]{1}\\d{3}年[0-1]{0,1}\\d月[0-3]{0,1}\\d日";
		String regular3 = "[1-2]{1}\\d{3}/[0-1]{0,1}\\d/[0-3]{0,1}\\d [0-2]{0,1}\\d:[0-6]{0,1}\\d:[0-6]{0,1}\\d";
		String regular4 = "[1-2]{1}\\d{3}/[0-1]{0,1}\\d/[0-3]{0,1}\\d";
		String regular5 = "[1-2]{1}\\d{3}-[0-1]{0,1}\\d-[0-3]{0,1}\\d";
		String regular6 = "[1-2]{1}\\d{3}-[0-1]{0,1}\\d-[0-3]{0,1}\\d [0-2]{0,1}\\d";
		String regular7 = "[1-2]{1}\\d{3}/[0-1]{0,1}\\d/[0-3]{0,1}\\d am";
		String regular8 = "[1-2]{1}\\d{3}/[0-1]{0,1}\\d/[0-3]{0,1}\\d pm";
		Matcher matcher1 = Pattern.compile(regular1).matcher(formattingString);
		Matcher matcher2 = Pattern.compile(regular2).matcher(formattingString);
		Matcher matcher3 = Pattern.compile(regular3).matcher(formattingString);
		Matcher matcher4 = Pattern.compile(regular4).matcher(formattingString);
		Matcher matcher5 = Pattern.compile(regular5).matcher(formattingString);
		Matcher matcher6 = Pattern.compile(regular6).matcher(formattingString);
		Matcher matcher7 = Pattern.compile(regular7).matcher(formattingString);
		Matcher matcher8 = Pattern.compile(regular8).matcher(formattingString);
		String formatTime = "";
		if (matcher1.find()) {
			formatTime = matcher1.group(0);
		} else if (matcher2.find()) {
			formatTime = matcher2.group(0);
			formatTime = formatTime.replaceAll("年|月", "-").replaceAll("日", "");
		} else if (matcher3.find()) {
			formatTime = matcher3.group(0).trim();
			formatTime = formatTime.replaceAll("/", "-");
		} else if (matcher4.find()) {
			formatTime = matcher4.group(0).trim();
			formatTime = formatTime.replaceAll("/", "-");
		} else if (matcher5.find()) {
			if (matcher6.find()) {
				formatTime = matcher6.group(0).trim()+":00:00";
			}else{
			formatTime = matcher5.group(0).trim();
			}
		}  else if (formattingString.endsWith("AM")) {
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
			formattingString = StringUtils.substring(formattingString, 0, formattingString.indexOf("am"));
			try {
				Date date = sdf.parse(formattingString);
				formatTime = TimingUtil.sdf1.format(date);
				System.out.println(formatTime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				LOG.info("不符合格式：" + formattingString);
			}
			
		} else if (formattingString.endsWith("PM")) {
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
			formattingString = StringUtils.substring(formattingString, 0, formattingString.indexOf("pm"));
			String[] s = formattingString.split(" ");
			String[] pm = s[1].split(":");
			int hour = Integer.parseInt(pm[0])+12;
			try {
				formattingString = s[0]+" "+hour+":"+pm[1]+":"+pm[2];
				Date date = sdf.parse(formattingString);
				formatTime = TimingUtil.sdf1.format(date);
				System.out.println("pm："+formatTime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				LOG.info("不符合格式：" + formattingString);
				return null;
			}
		} else {
			LOG.info("不符合格式：" + formattingString);
			return null;
		}

		// 1.对于月、日、时、分、秒省略0的情况进行处理，比如2014-9-8 处理成 2014-09-08
		// 2.对于只有年月日的情况进行补全，比如2014-09-16 处理成 2014-09-16 00:00:00
		String[] tempDate = formatTime.split(" ");
		if (tempDate.length == 1) {
			formatTime = processLessthan2Patch0(formatTime, "-") + " 00:00:00";
		} else if (tempDate.length == 2) {
			formatTime = processLessthan2Patch0(tempDate[0], "-") + " " + processLessthan2Patch0(tempDate[1], ":");
		} else {
			LOG.info("不符合格式：" + formattingString);
		}
		
		if(formatTime.equals("0 00:00:00")){
			System.out.println("--------format:"+formattingString);
		}
		return formatTime;
	}

	/**
	 * 根据分隔符分隔字符串，长度小于2的字符串前面补0
	 * 
	 * @param processString
	 *            待处理字符串
	 * @param separator
	 *            分隔符
	 */
	public static String processLessthan2Patch0(String processString, String separator) {
		StringBuilder tempSb = new StringBuilder();
		String[] temp = processString.split(separator);
		for (String s : temp) {
			if (s.length() < 2) {
				tempSb.append("0");
			}
			tempSb.append(s);
			tempSb.append(separator);
		}
		return tempSb.substring(0, tempSb.length() - 1);
	}

	public static void main(String[] args) {
		Formatter formatter = new DateFormatter();
		// LOG.info(formatter.format("作者：无  来源：总量（科监）处  发布时间：2014-09-23  浏览次数：165  【字号：大 中 小】  【保护视力色：  杏仁黄  秋叶褐  胭脂红  芥末绿  天蓝  雪青  灰  默认色】"));

		System.out.println(formatter.format(""));
//		System.out.println(formatter.format("12/02/2014 04:16:03 pm"));
//		/*DateFormat format = new SimpleDateFormat();
//		format.parse(source, pos)*/
//		
//		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
//			try {
//				System.out.println(sdf.parse("12/02/2014 04:16:03").toString());
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		
		
	   
	}
}
