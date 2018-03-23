package com.zjhcsoft.struc.util;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author wangtao 2013-12-12
 * 
 */
public class TimingUtil {

	private static long[] TIME_FACTOR = { 60 * 60 * 1000, 60 * 1000, 1000 };
	public static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd,HH");
	public static SimpleDateFormat sdf3 = new SimpleDateFormat("yy-MM-dd HH:mm");
	public static SimpleDateFormat sdf4 = new SimpleDateFormat("yyyyMMdd");
	public static SimpleDateFormat sdf5 = new SimpleDateFormat("dd日HH时");
	public static SimpleDateFormat sdf6 = new SimpleDateFormat("yyyyMM");
	public static SimpleDateFormat sdf7 = new SimpleDateFormat("MM月dd日");
	public static SimpleDateFormat sdf8 = new SimpleDateFormat("yyyyM");
	public static SimpleDateFormat sdf9 = new SimpleDateFormat("yyyyMMddHHmmss");
	public static SimpleDateFormat sdf10 = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat sdf11 = new SimpleDateFormat("yyyy-MM-dd,HH");
	public static SimpleDateFormat sdf12 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
	public static SimpleDateFormat sdf13 = new SimpleDateFormat("yy-MM-dd HH");
	public static SimpleDateFormat sdf14 = new SimpleDateFormat("yyyy-MM-dd#00:00:00");
	public static SimpleDateFormat sdf15 = new SimpleDateFormat("yyyy-MM-dd#23:59:59");
	public static final Logger LOG = LoggerFactory.getLogger(TimingUtil.class);
	private static long now = System.currentTimeMillis();
	/**
	 * Calculate the elapsed time between two times specified in milliseconds.
	 * 
	 * @param start
	 *            The start of the time period
	 * @param end
	 *            The end of the time period
	 * @return a string of the form "XhYmZs" when the elapsed time is X hours, Y
	 *         minutes and Z seconds or null if start > end.
	 */
	public static String elapsedTime(long start, long end) {
		if (start > end) {
			return null;
		}

		long[] elapsedTime = new long[TIME_FACTOR.length];

		for (int i = 0; i < TIME_FACTOR.length; i++) {
			elapsedTime[i] = start > end ? -1 : (end - start) / TIME_FACTOR[i];
			start += TIME_FACTOR[i] * elapsedTime[i];
		}

		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumIntegerDigits(2);
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < elapsedTime.length; i++) {
			if (i > 0) {
				buf.append(":");
			}
			buf.append(nf.format(elapsedTime[i]));
		}
		return buf.toString();
	}

	/**
	 * 格式化时间 格式是2013-12-12 10:28:15
	 */
	public static String getFormatTime(String oldTime) {
		oldTime = oldTime.trim();

		String regular1 = "201\\d-(0\\d|1[0-2]{1})-[0-3]{1}\\d [0-2]{1}\\d:[0-6]{1}\\d:[0-6]{1}\\d";
		String regular2 = "201\\d-(0\\d|1[0-2]{1})-[0-3]{1}\\d";
		String regular3 = "[0-2]{1}\\d:[0-6]{1}\\d:[0-6]{1}\\d";
		String regular4 = "201\\d";
		String regular5 = "0\\d|1[0-2]{1}";
		String regular6 = "0\\d|1\\d|2[0-3]{1}";
		String regular7 = "0\\d|[1-2]{1}\\d|3[0-1]{1}";
		String regular8 = "0\\d|[1-5]{1}\\d";
		String regular9 = "\\d";

		String formatTime = "";

		if (!Pattern.matches(regular1, oldTime)) {
			String oldTimeArray[] = oldTime.split(" ");
			int oldTimeLen = oldTimeArray.length;

			String ymd = "";
			String hms = "";

			if (Pattern.matches(regular2, oldTimeArray[0])) {
				ymd = oldTimeArray[0].trim();
			} else {
				String ymdArray[] = oldTimeArray[0].split("-");
				switch (ymdArray.length) {
				case 1:
					if (Pattern.matches(regular4, ymdArray[0])) {
						formatTime = oldTimeArray[0].trim() + "-01-01 " + "00:00:00";
					} else {
						System.out.println("不符合格式：[" + oldTime + "]");
						return "";
					}
					break;
				case 2:
					if (Pattern.matches(regular4, ymdArray[0]) && Pattern.matches(regular5, ymdArray[1])) {
						ymd = oldTimeArray[0].trim() + "-01";
					} else {
						System.out.println("不符合格式：[" + oldTime + "]");
						return "";
					}
					break;
				case 3:
					if (Pattern.matches(regular4, ymdArray[0])
							&& (Pattern.matches(regular5, ymdArray[1]) || Pattern.matches(regular9, ymdArray[1])
									&& (Pattern.matches(regular7, ymdArray[2]) || Pattern.matches(regular9, ymdArray[2])))) {
						for (int i = 0; i < ymdArray.length; i++) {
							ymd += addZero(ymdArray[i]);
							if (i < ymdArray.length - 1) {
								ymd += "-";
							}
						}
					} else {
						System.out.println("不符合格式：[" + oldTime + "]");
						return "";
					}
					break;
				default:
					System.out.println("不符合格式：[" + oldTime + "]");
					return "";
				}
			}
			formatTime = ymd + " 00:00:00";

			if (oldTimeLen == 2) {
				String hmsArray[] = oldTimeArray[1].split(":");

				if (Pattern.matches(regular3, oldTimeArray[1])) {
					hms = oldTimeArray[1].trim();
				} else {
					String newHmsArray[] = "00:00:00".split(":");

					if (hmsArray.length > 1 && hmsArray.length < 4) {
						if ((Pattern.matches(regular6, hmsArray[0]) || Pattern.matches(regular9, hmsArray[0]))) {
							newHmsArray[0] = addZero(hmsArray[0]);
						} else {
							System.out.println("不符合格式：[" + oldTime + "]");
							return "";
						}

						for (int i = 1; i < hmsArray.length; i++) {
							if ((Pattern.matches(regular8, hmsArray[i]) || Pattern.matches(regular9, hmsArray[i]))) {
								newHmsArray[i] = addZero(hmsArray[i]);
							} else {
								System.out.println("不符合格式：[" + oldTime + "]");
								return "";
							}
						}
						hms = newHmsArray[0] + ":" + newHmsArray[1] + ":" + newHmsArray[2];
					} else {
						System.out.println("不符合格式：[" + oldTime + "]");
						return "";
					}
				}
				formatTime = ymd + " " + hms;
			} else if (oldTimeLen > 2) {
				System.out.println("不符合格式：[" + oldTime + "]");
				return "";
			}
		} else {
			formatTime = oldTime;
		}

		return formatTime;
	}

	public static String addZero(String num) {
		int len = num.length();

		if (len == 0) {
			num = "00";
		} else if (len == 1) {
			num = "0" + num;
		}
		return num;
	}

	/**
	 * 从字符串中提取时间
	 */
	public static String getTime(String text) {
		Pattern pattern = Pattern.compile("201[0-9]{1}[0-9-/: ：年月　日 ]{5,30}");
		Matcher matcher = pattern.matcher(text);
		if (matcher.find()) {
			Matcher matcher2 = Pattern.compile("[年月]").matcher(matcher.group(0).trim());
			Matcher matcher3 = Pattern.compile("日").matcher(matcher2.replaceAll("-"));
			Matcher matcher4 = Pattern.compile("/").matcher(matcher3.replaceAll(" "));
			text = matcher4.replaceAll("-");

			StringBuilder stringBuilder = new StringBuilder(text);

			Matcher matcher5 = Pattern.compile("[0-9-]{8,10}").matcher(text);

			if (matcher5.find()) {
				text = matcher5.group(0);
				if (text.split("-").length > 2) {

					if (text.split("-")[1].length() < 2) {
						stringBuilder.insert(5, '0');
					}

					if (text.split("-")[2].length() < 2) {
						stringBuilder.insert(8, '0');
					}
				}
			}
			String results[] = stringBuilder.toString().split("[\\s\\p{Zs}]");// 同时匹配 \s 以及各种其他的空白字符（包括全角空格等）
			return getFormatTime(results.length > 1 ? results[0] + " " + results[results.length - 1] : results[0]);
		}
		return "";
	}

	/**
	 * 获得当前年份
	 */
	public static int getThisYear() {
		String year = new SimpleDateFormat("yyyy").format(now);
		return Integer.parseInt(year);
	}
	
	/**
	 * 获得当前月份
	 */
	public static int getThisMonth() {
		String year = new SimpleDateFormat("MM").format(now);
		return Integer.parseInt(year);
	}
	
	/**
	 * 获得当前天
	 */
	public static int getThisDay() {
		String year = new SimpleDateFormat("dd").format(now);
		return Integer.parseInt(year);
	}
	
	/**
	 * 获得当前小时
	 */
	public static int getThisHour() {
		String year = new SimpleDateFormat("HH").format(now);
		return Integer.parseInt(year);
	}
	
	/**
	 * 获得当前分钟
	 */
	public static int getThisMinute() {
		String year = new SimpleDateFormat("mm").format(now);
		return Integer.parseInt(year);
	}
	
	/**
	 * 获得当前秒数
	 */
	public static int getThisSecond() {
		String year = new SimpleDateFormat("ss").format(now);
		return Integer.parseInt(year);
	}
	
	/**
	 * 获得当前某年某月
	 */
	public synchronized static String getThisYearAndMonth() {
		return sdf6.format(now);
	}
	
	/**
	 * 获得当前某月某日
	 */
	public synchronized static String  getThisMonthAndDay() {
		return sdf7.format(now);
	}
	
	/**
	 * 获得当前某日某时
	 */
	public synchronized static String getThisDayAndHour() {
		return sdf5.format(now);
	}
	
	@SuppressWarnings("deprecation")
	public static String getNowString() {
		return getNow().toLocaleString();
	}
	
	/**
	 * 获得当前时间
	 */
	public static Date getNow() {
		Date date = new Date(now);
		return date;
	}

	public static List<Date> getDayListBetween(Date d1,Date d2){
		return getDayListBetween(d1,d2,1);
	}
	
	public static List<Date> getHourListBetween(Date d1,Date d2){
		return getHourListBetween(d1,d2,1);
	}
	
	/**
	 * 两个时间点之间的天列表
	 * @param d1
	 * @param d2
	 * @param m 倍数
	 * @return
	 */
	public static List<Date> getDayListBetween(Date d1,Date d2,int m){
		List<Date> dateList = new ArrayList<>();
		Calendar startCalendar = Calendar.getInstance();
		Calendar endCalendar = Calendar.getInstance();
		
		if (d1.compareTo(d2)>=0) {
			startCalendar.setTime(d2);
			endCalendar.setTime(d1);
		}else {
			startCalendar.setTime(d1);
			endCalendar.setTime(d2);
		}

		endCalendar.set(Calendar.HOUR_OF_DAY, 0);// 时 清零
		endCalendar.set(Calendar.MINUTE, 0);// 分 清零
		endCalendar.set(Calendar.SECOND, 0);// 秒 清零
		endCalendar.add(Calendar.DAY_OF_MONTH, - endCalendar.get(Calendar.DAY_OF_MONTH) % m);// 时 设置为小于且最接近endTime的4的倍数

		while (endCalendar.compareTo(startCalendar)>=0) {
			dateList.add(endCalendar.getTime());
			endCalendar.add(Calendar.DAY_OF_MONTH, 0-m);
		}

		return dateList;
	}
	
	/**
	 * 两个时间点之间的时列表
	 * @param d1
	 * @param d2
	 * @param m 倍数
	 * @return
	 */
	public static List<Date> getHourListBetween(Date d1,Date d2,int m){
		List<Date> dateList = new ArrayList<>();
		Calendar startCalendar = Calendar.getInstance();
		Calendar endCalendar = Calendar.getInstance();
		
		if (d1.compareTo(d2)>=0) {
			startCalendar.setTime(d2);
			endCalendar.setTime(d1);
		}else {
			startCalendar.setTime(d1);
			endCalendar.setTime(d2);
		}

		endCalendar.set(Calendar.MINUTE, 0);// 分 清零
		endCalendar.set(Calendar.SECOND, 0);// 秒 清零
		endCalendar.add(Calendar.HOUR_OF_DAY, - endCalendar.get(Calendar.HOUR_OF_DAY) % m);// 时 设置为小于且最接近endTime的4的倍数

		while (endCalendar.compareTo(startCalendar)>=0) {
			dateList.add(endCalendar.getTime());
			endCalendar.add(Calendar.HOUR_OF_DAY, 0-m);
		}

		return dateList;
	}
	
	/**
	 * 用dayNum把startDate和endDate切分成N个时间点
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @param dayNum 时间间隔
	 * @return
	 */
	public static List<Date> splitTimeByDay(Date startDate,Date endDate,int dayNum){
		List<Date> dateList = new ArrayList<>();
		
		dateList.add(startDate);
		
		Calendar startCalendar = Calendar.getInstance();
		Calendar endCalendar = Calendar.getInstance();
		
		startCalendar.setTime(startDate);
		endCalendar.setTime(endDate);

		endCalendar.add(Calendar.DAY_OF_MONTH, -dayNum);
		
		while (startCalendar.compareTo(endCalendar)<=0) {
			startCalendar.add(Calendar.DAY_OF_MONTH, dayNum);
			dateList.add(startCalendar.getTime());
		}
		
		dateList.add(endDate);
//		for (int i = 0; i < dateList.size(); i++) {
//			System.out.println(dateList.get(i).toLocaleString());
//		}
		return dateList;
	}
	
	/**
	 * 用hourNum把startDate和endDate切分成N个时间点
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @param hourNum 间隔小时数
	 * @param isCleared 是否情况分和秒
	 * @return
	 */
	public static List<Date> splitTimeByHour(Date startDate,Date endDate,int hourNum,boolean isCleared){
		List<Date> dateList = new ArrayList<>();
		
		Calendar startCalendar = Calendar.getInstance();
		Calendar endCalendar = Calendar.getInstance();
		startCalendar.setTime(startDate);
		endCalendar.setTime(endDate);
		
		if(isCleared){
			startCalendar.set(Calendar.SECOND, 0);
			startCalendar.set(Calendar.MINUTE, 0);
			endCalendar.set(Calendar.MINUTE, 0);
			endCalendar.set(Calendar.MINUTE, 0);
		}
		
		endCalendar.add(Calendar.HOUR_OF_DAY, -hourNum);
		
		while (startCalendar.compareTo(endCalendar)<=0) {
			startCalendar.add(Calendar.HOUR_OF_DAY, hourNum);
			dateList.add(startCalendar.getTime());
		}
		
//		for (int i = 0; i < dateList.size(); i++) {
//			System.out.println(dateList.get(i).toLocaleString());
//		}
		return dateList;
	}
	
	/**
	 * 用hourNum把startDate和endDate切分成N个时间点
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @param hourNum 间隔小时数
	 * @param isCleared 是否情况时分秒
	 * @return
	 */
	public static List<Date> splitTimeByDay(Date startDate,Date endDate,int dayNum,boolean isCleared){
		List<Date> dateList = new ArrayList<>();
		
		Calendar startCalendar = Calendar.getInstance();
		Calendar endCalendar = Calendar.getInstance();
		startCalendar.setTime(startDate);
		endCalendar.setTime(endDate);
		
		if(isCleared){
			startCalendar.set(Calendar.SECOND, 0);
			startCalendar.set(Calendar.MINUTE, 0);
			startCalendar.set(Calendar.HOUR_OF_DAY, 0);
			endCalendar.set(Calendar.MINUTE, 0);
			endCalendar.set(Calendar.MINUTE, 0);
			endCalendar.set(Calendar.HOUR_OF_DAY, 0);
		}
		
		endCalendar.add(Calendar.DAY_OF_MONTH, -dayNum);
		
		while (startCalendar.compareTo(endCalendar)<=0) {
			startCalendar.add(Calendar.DAY_OF_MONTH, dayNum);
			dateList.add(startCalendar.getTime());
		}
		
		for (int i = 0; i < dateList.size(); i++) {
			System.out.println(dateList.get(i).toLocaleString());
		}
		return dateList;
	}
	/**
	 * 获取最近3个月的时间
	 */
	public static Date getStartTime() {
		Calendar c =  Calendar.getInstance();
		c.add(Calendar.DATE, -1);
		return c.getTime();
	}
	/**
	 * 获取时间的小时数
	 * @param date
	 * @return
	 */
	public static int getHour(Date date) {
		Calendar c =  Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.HOUR_OF_DAY);
	}
	public static void main(String[] args) throws ParseException {
		//splitTimeByDay(TimingUtil.sdf1.parse("2014-05-11 12:02:30"),TimingUtil.sdf1.parse("2014-05-15 16:01:21"),1,true);
		Date d = getStartTime();
		System.out.println(getHour(d));
	}
}
