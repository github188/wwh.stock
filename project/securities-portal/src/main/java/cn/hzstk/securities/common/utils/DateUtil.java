package cn.hzstk.securities.common.utils;

import cn.hzstk.securities.common.constants.Constant;
import net.ryian.commons.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    public static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 获取下一天
     *
     * @param day 当前日期yyyyMMdd
     * @return String
     */
    public static String getNextDay(String day) {
        Calendar cal = formatYYYYMMDD(day);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        return sdf.format(cal.getTime());
    }
    public static String formatNextDay(String day) {
        Calendar cal = formatYYYYMMDD(day);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        if  (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            cal.add(Calendar.DAY_OF_MONTH, 2);
        } else  if  (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }

        return sdf1.format(cal.getTime());
    }
    public static String getPreDay(String day) {
        Calendar cal = formatYYYYMMDD(day);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return sdf.format(cal.getTime());
    }
    public static String getLastDay(String day) {
        Calendar cal = formatYYYYMMDD(day);
        if  (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        } else  if  (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            cal.add(Calendar.DAY_OF_MONTH, -2);
        }

        return sdf1.format(cal.getTime());
    }

    public static int getDiffSemiYear(String month) {
        String year = getSemiYear();

        int curYear = Integer.valueOf(year.substring(0, 4));
        int curMonth = Integer.valueOf(year.substring(4, 6));
        int calYear = Integer.valueOf(month.substring(0, 4));
        int calMonth = Integer.valueOf(month.substring(4, 6));
        int diff = (curYear - calYear)*2 + (curMonth - calMonth)/6;

        return diff;
    }

    /**
     * 获取某月的最后一天
     * @param dt
     * @return
     */
    public static String getLastDayOfMonth(String dt)
    {
        int year = Integer.valueOf(dt.substring(0, 4));
        int month = Integer.valueOf(dt.substring(4,6));
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR,year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String lastDayOfMonth = sdf.format(cal.getTime());

        return lastDayOfMonth;
    }
    public static String getAnnualYear() {
        String str = DateUtils.format(new Date(), "MM");
        String year = DateUtils.getYear(new Date());
        int mon = Integer.valueOf(str);
        if (mon > 3 && mon < 10) {
            str = year + "06";
        } else if (mon > 9) {
            str = year + "12";
        } else {
            str = DateUtils.getYear(DateUtils.preYear(new Date())) + "12";
        }
        return str;
    }
    public static String getCurSemi() {
        String str = DateUtils.format(new Date(), "MM");
        String year = DateUtils.getYear(new Date());
        int mon = Integer.valueOf(str);
        if (mon <= 3) {
            str = DateUtils.getYear(DateUtils.preYear(new Date())) + "-12-31";
        } else if (mon > 3 && mon <= 6) {
            str = year + "-03-31";
        } else if (mon > 6 && mon <= 9) {
            str = year + "-06-30";
        } else {
            str = year + "-09-30";
        }
        return str;
    }
    public static String getSemiYear() {
        return getMonthByType(Constant.SEMI_YEAR);
    }
    public static String getMonthByType(String type) {
        String str = DateUtils.format(new Date(), "MM");
        String year = DateUtils.getYear(new Date());
        int mon = Integer.valueOf(str);
        if (mon > 6) {
            str = year + "06";
            if (Constant.QUARTER.equals(type) && mon > 9) str = year + "09";
        } else {
            str = DateUtils.getYear(DateUtils.preYear(new Date())) + "12";
            if (Constant.QUARTER.equals(type) && mon > 3) str = year + "03";
        }
        return str;
    }
    public static String getNextMonthByType(String month, String type) {
        return getNextMonthByType(month, 1, type);
    }
    public static String getNextMonthByType(String month, int cnt, String type) {
        int mon = 6;
        if (Constant.QUARTER.equals(type)) mon = 3;

        Calendar cal = formatYYYYMMDD(month + "01");
        cal.add(Calendar.MONTH, cnt * mon);

        return sdf.format(cal.getTime()).substring(0, 6);
    }

    /**
     * 如果当前日期处于周末，则返回下周一
     *
     * @param day 当前日期yyyyMMdd
     * @return String
     */
    public static String getDayExceptWeekend(String day) {
        Calendar cal = formatYYYYMMDD(day);
        if  (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            cal.add(Calendar.DAY_OF_MONTH, 2);
        } else  if  (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        return sdf.format(cal.getTime());
    }

    /**
     * 将字符串类型日期转换为Calendar
     *
     * @param day 当前日期yyyyMMdd
     * @return Calendar
     */
    public static Calendar formatYYYYMMDD(String day) {
        Calendar cal = Calendar.getInstance();
        if (day.length() == 8) {
            cal.set(Integer.parseInt(day.substring(0, 4)), (Integer.parseInt(day.substring(4, 6)) - 1), Integer.parseInt(day.substring(6)));
        } else if (day.length() == 10) {
            cal.set(Integer.parseInt(day.substring(0, 4)), (Integer.parseInt(day.substring(5, 7)) - 1), Integer.parseInt(day.substring(8)));
        }
        return cal;
    }

    /**
     * 判断是否周末
     *
     * @param day 当前日期
     * @return boolean
     */
    public static boolean isWeekend(Date day) {
        boolean ret = false;
        if(day == null) {
            day = new Date();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(day);
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            ret = true;
        } else if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            ret = true;
        }

        return ret;
    }

    public static void main(String[] args) {
        String tmp = DateUtils.format(new Date(), Constant.DATE_FORMAT8_STR);
        System.out.print(tmp);
        isWeekend(null);
        Date dt = DateUtils.getEndOfWeek();
        dt = DateUtils.getEndOfWeek(new Date());
        System.out.print(dt.toString());
    }
}

