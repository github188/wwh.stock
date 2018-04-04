package net.ryian.commons;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

/**
 * Date: 2013-08-19 7:31:40 PM
 *
 * @author cyj
 */
public class DateUtils {

	public static final String DATE_FORMAT_STR = "yyyy-MM-dd";

	protected static DateFormat DATE_FORMAT = new SimpleDateFormat(
			DATE_FORMAT_STR, Locale.CHINESE);
	protected static DateFormat TIME_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss", Locale.CHINESE);
	protected static DateFormat MONTH_FORMAT = new SimpleDateFormat("yyyy-MM",
			Locale.CHINESE);
	protected static DateFormat YEAR_FORMAT = new SimpleDateFormat("yyyy",
			Locale.CHINESE);

	public static java.util.Date stringToUtilDate(String str) {
		str = str.replaceAll("/", "-");
		if (null != str && str.length() > 0) {
			try {
				if (str.length() <= DATE_FORMAT_STR.length()) {
					return (DATE_FORMAT).parse(str);
				} else {
					return (TIME_FORMAT).parse(str);
				}
			} catch (ParseException ex) {
				return null;
			}
		} else {
			return null;
		}
	}

	public static java.sql.Date stringToSqlDate(String str) {
		if (stringToUtilDate(str) == null || str.length() < 1) {
			return null;
		} else {
			return new java.sql.Date(stringToUtilDate(str).getTime());
		}
	}

	public static Date[] getYears(Date day, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(day);
		cal.add(Calendar.YEAR, 1 - n);

		Date[] ret = new Date[n];
		for (int i = 0; i < n; i++) {
			Date year = getStartOfYear(cal.getTime());
			ret[i] = year;
			cal.setTime(nextYear(year));
		}
		return ret;
	}

	public static String getYear(Date day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(day);
		return cal.get(Calendar.YEAR) + "";
	}

	public static long getSeconds(Date d) {
		long time = d.getTime();
		return time - time % 1000;
	}

	public static long getMinutes(Date d) {
		long time = d.getTime();
		return time - time % (1000 * 60);
	}

	public static Date nextWeek(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.WEEK_OF_YEAR, 1);
		return c.getTime();
	}

	public static Date preWeek(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.WEEK_OF_YEAR, -1);
		return c.getTime();
	}

	public static Date nextMonth(Date now) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.MONTH, 1);
		return cal.getTime();
	}

	public static Date preMonth(Date now) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.MONTH, -1);
		return cal.getTime();
	}

	public static Date nextHours(Date day, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(day);
		cal.add(Calendar.HOUR, n);
		return getEndOfHour(cal.getTime());
	}

	public static Date nextDay(Date now) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.DATE, 1);
		return cal.getTime();
	}

	public static Date preDay(Date now) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.DATE, -1);
		return cal.getTime();
	}

	public static Date preHour(Date now) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.HOUR, -1);
		return cal.getTime();
	}

	public static Date beforeHours(Date now, int hours) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.HOUR, -hours);
		return cal.getTime();
	}

	public static Date nextHour(Date now) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.HOUR, 1);
		return cal.getTime();
	}

	public static Date preYear(Date now) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.YEAR, -1);
		return cal.getTime();
	}

	public static Date nextYear(Date now) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.YEAR, 1);
		return cal.getTime();
	}

	/**
	 * split the begin and end in mutil in one month period.
	 * 
	 * @param begin
	 * @param end
	 * @return
	 */
	public static Iterator<Date[]> iterate(final Date begin, final Date end) {
		Iterator<Date[]> iter = new Iterator<Date[]>() {
			boolean hasNext = true;
			Date b = (Date) begin.clone();
			Date e = (Date) end.clone();

			public void remove() {
			}

			public Date[] next() {
				Date theEndOfMonth = getEndOfMonth(b);
				Date[] r = new Date[2];
				if (theEndOfMonth.after(end)) {
					hasNext = false;
					r[0] = b;
					r[1] = e;
				} else {
					r[0] = b;
					r[1] = theEndOfMonth;
					Calendar cal = Calendar.getInstance();
					cal.setTime(theEndOfMonth);
					cal.add(Calendar.SECOND, 1);
					b = cal.getTime();
				}
				return r;
			}

			public boolean hasNext() {
				return this.hasNext;
			}
		};
		return iter;
	}

	public static Date parseDate(String dateStr) {
		try {
			synchronized (DATE_FORMAT) {
				return DATE_FORMAT.parse(dateStr);
			}
		} catch (ParseException e) {
			try {
				return MONTH_FORMAT.parse(dateStr);
			} catch (ParseException e1) {
				return null;
			}
		}
	}

	public static String format(Date date) {
		try {
			synchronized (DATE_FORMAT) {
				return DATE_FORMAT.format(date);
			}
		} catch (Exception e) {
			return null;
		}
	}

	public static String format(Date date, String format) {
		try {
			SimpleDateFormat sf = new SimpleDateFormat(format);
			return sf.format(date);
		} catch (Exception e) {
			return null;
		}
	}

	public static Date parseTime(String timeStr) {
		try {
			synchronized (TIME_FORMAT) {
				return TIME_FORMAT.parse(timeStr);
			}
		} catch (ParseException e) {
			return null;
		}
	}

	public static String formatMonth(Date date) {
		try {
			synchronized (MONTH_FORMAT) {
				return MONTH_FORMAT.format(date);
			}
		} catch (Exception e) {
			return null;
		}
	}

	public static String formatYear(Date date) {
		try {
			synchronized (YEAR_FORMAT) {
				return YEAR_FORMAT.format(date);
			}
		} catch (Exception e) {
			return null;
		}
	}

	public static String formatTime(Date date) {
		try {
			synchronized (TIME_FORMAT) {
				return TIME_FORMAT.format(date);
			}
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Returns a Date set to the first possible millisecond of the day, just
	 * after midnight. If a null day is passed in, a new Date is created.
	 * midnight (00m 00h 00s)
	 */
	public static Date getStartOfDay(Date day) {
		return getStartOfDay(day, Calendar.getInstance());
	}

	public static Date getStartOfDay() {
		return getStartOfDay(null, Calendar.getInstance());
	}

	public static Date getStartOfWeek() {
		return getStartOfWeek(null, Calendar.getInstance());
	}

	public static Date getStartOfWeek(Date day) {
		return getStartOfWeek(day, Calendar.getInstance());
	}

	public static Date getStartOfWeek(Date day, Calendar cal) {
		if (day == null)
			day = new Date();
		cal.setTime(day);
		cal.set(Calendar.HOUR_OF_DAY, cal.getMinimum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, cal.getMinimum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getMinimum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal.getMinimum(Calendar.MILLISECOND));
		int week = (cal.get(Calendar.DAY_OF_WEEK) - 1);
		week = week == 0 ? 7 : week;
		cal.add(Calendar.DAY_OF_MONTH, 1 - week);
		return cal.getTime();
	}

	/**
	 * Returns a Date set to the first possible millisecond of the day, just
	 * after midnight. If a null day is passed in, a new Date is created.
	 * midnight (00m 00h 00s)
	 */
	public static Date getStartOfDay(Date day, Calendar cal) {
		if (day == null)
			day = new Date();
		cal.setTime(day);
		cal.set(Calendar.HOUR_OF_DAY, cal.getMinimum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, cal.getMinimum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getMinimum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal.getMinimum(Calendar.MILLISECOND));
		return cal.getTime();
	}

	/**
	 * Returns a Date set to the last possible millisecond of the day, just
	 * before midnight. If a null day is passed in, a new Date is created.
	 * midnight (00m 00h 00s)
	 */
	public static Date getEndOfDay(Date day) {
		return getEndOfDay(day, Calendar.getInstance());
	}

	public static Date getEndOfDay() {
		return getEndOfDay(null, Calendar.getInstance());
	}

	public static Date getEndOfWeek(Date day) {
		return getEndOfWeek(day, Calendar.getInstance());
	}

	public static Date getEndOfWeek() {
		return getEndOfWeek(null, Calendar.getInstance());
	}

	public static Date getEndOfWeek(Date day, Calendar cal) {
		if (day == null)
			day = new Date();
		cal.setTime(day);
		cal.set(Calendar.HOUR_OF_DAY, cal.getMaximum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, cal.getMaximum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getMaximum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal.getMaximum(Calendar.MILLISECOND));

		int week = (7 - cal.get(Calendar.DAY_OF_WEEK));
		week = week == 6 ? 0 : week + 1;
		cal.add(Calendar.DAY_OF_MONTH, week);
		return cal.getTime();

	}

	public static Date getEndOfDay(Date day, Calendar cal) {
		if (day == null)
			day = new Date();
		cal.setTime(day);
		cal.set(Calendar.HOUR_OF_DAY, cal.getMaximum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, cal.getMaximum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getMaximum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal.getMaximum(Calendar.MILLISECOND));
		return cal.getTime();
	}

	/**
	 * Returns a Date set to the first possible millisecond of the hour. If a
	 * null day is passed in, a new Date is created.
	 */
	public static Date getStartOfHour(Date day) {
		return getStartOfHour(day, Calendar.getInstance());
	}

	public static Date getStartOfHour() {
		return getStartOfHour(null, Calendar.getInstance());
	}

	/**
	 * Returns a Date set to the first possible millisecond of the hour. If a
	 * null day is passed in, a new Date is created.
	 */
	public static Date getStartOfHour(Date day, Calendar cal) {
		if (day == null)
			day = new Date();
		cal.setTime(day);
		cal.set(Calendar.MINUTE, cal.getMinimum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getMinimum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal.getMinimum(Calendar.MILLISECOND));
		return cal.getTime();
	}

	/**
	 * Returns a Date set to the last possible millisecond of the day, just
	 * before midnight. If a null day is passed in, a new Date is created.
	 * midnight (00m 00h 00s)
	 */
	public static Date getEndOfHour(Date day) {
		return getEndOfHour(day, Calendar.getInstance());
	}

	public static Date getEndOfHour() {
		return getEndOfHour(null, Calendar.getInstance());
	}

	public static Date getEndOfHour(Date day, Calendar cal) {
		if (day == null) {
			day = new Date();
		}
		cal.setTime(day);
		cal.set(Calendar.MINUTE, cal.getMaximum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getMaximum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal.getMaximum(Calendar.MILLISECOND));
		return cal.getTime();
	}

	public static void main(String[] args) {
		Date e = DateUtils.getStartOfDay();
		System.out.println(nextHours(e, 6));

		for (Date s : getYears(new Date(), 5)) {
			System.out.println(s);
		}

		System.out.println(format(new Date(), "yyyy"));

		String y = "2006";

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(y));

		System.out.println(beforeHours(new Date(), 18));
	}

	public static int getMonthDays(Date day) {
		if (day == null)
			day = new Date();
		Date lastDayOfMonth = getEndOfMonth(day);
		Calendar cal = Calendar.getInstance();
		cal.setTime(lastDayOfMonth);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Returns a Date set to the first possible millisecond of the minute. If a
	 * null day is passed in, a new Date is created.
	 */
	public static Date getStartOfMinute() {
		return getStartOfMinute(null, Calendar.getInstance());
	}

	public static Date getStartOfMinute(Date day) {
		return getStartOfMinute(day, Calendar.getInstance());
	}

	/**
	 * Returns a Date set to the first possible millisecond of the minute. If a
	 * null day is passed in, a new Date is created.
	 */
	public static Date getStartOfMinute(Date day, Calendar cal) {
		if (day == null)
			day = new Date();
		cal.setTime(day);
		cal.set(Calendar.SECOND, cal.getMinimum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal.getMinimum(Calendar.MILLISECOND));
		return cal.getTime();
	}

	/**
	 * Returns a Date set to the last possible millisecond of the minute. If a
	 * null day is passed in, a new Date is created.
	 */
	public static Date getEndOfMinute() {
		return getEndOfMinute(null, Calendar.getInstance());
	}

	public static Date getEndOfMinute(Date day) {
		return getEndOfMinute(day, Calendar.getInstance());
	}

	public static Date getEndOfMinute(Date day, Calendar cal) {
		if (day == null || cal == null) {
			return day;
		}

		cal.setTime(day);
		cal.set(Calendar.SECOND, cal.getMaximum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal.getMaximum(Calendar.MILLISECOND));
		return cal.getTime();
	}

	/**
	 * Returns a Date set to the first possible millisecond of the month, just
	 * after midnight. If a null day is passed in, a new Date is created.
	 * midnight (00m 00h 00s)
	 */
	public static Date getStartOfMonth(Date day) {
		return getStartOfMonth(day, Calendar.getInstance());
	}

	public static Date getStartOfMonth() {
		return getStartOfMonth(null, Calendar.getInstance());
	}

	public static Date getStartOfMonth(Date day, Calendar cal) {
		if (day == null)
			day = new Date();
		cal.setTime(day);

		// set time to start of day
		cal.set(Calendar.HOUR_OF_DAY, cal.getMinimum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, cal.getMinimum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getMinimum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal.getMinimum(Calendar.MILLISECOND));

		// set time to first day of month
		cal.set(Calendar.DAY_OF_MONTH, 1);

		return cal.getTime();
	}

	/**
	 * Returns a Date set to the last possible millisecond of the month, just
	 * before midnight. If a null day is passed in, a new Date is created.
	 * midnight (00m 00h 00s)
	 */
	public static Date getEndOfMonth() {
		return getEndOfMonth(null, Calendar.getInstance());
	}

	public static Date getEndOfMonth(Date day) {
		return getEndOfMonth(day, Calendar.getInstance());
	}

	public static Date getStartOfYear() {
		return getStartOfYear(null, Calendar.getInstance());
	}

	public static Date getStartOfYear(Date day) {
		return getStartOfYear(day, Calendar.getInstance());
	}

	public static Date getStartOfYear(Date day, Calendar cal) {
		if (day == null)
			day = new Date();
		cal.setTime(day);

		// set time to end of day
		cal.set(Calendar.HOUR_OF_DAY, cal.getMinimum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, cal.getMinimum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getMinimum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal.getMinimum(Calendar.MILLISECOND));
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}

	public static Date getEndOfYear() {
		return getEndOfYear(null, Calendar.getInstance());
	}

	public static Date getEndOfYear(Date day) {
		return getEndOfYear(day, Calendar.getInstance());
	}

	public static Date getEndOfYear(Date day, Calendar cal) {
		if (day == null)
			day = new Date();
		cal.setTime(day);
		// set time to end of day
		cal.set(Calendar.HOUR_OF_DAY, cal.getMaximum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, cal.getMaximum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getMaximum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal.getMaximum(Calendar.MILLISECOND));
		cal.set(Calendar.MONTH, 11);
		cal.set(Calendar.DAY_OF_MONTH, cal.getMaximum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}

	public static Date getEndOfMonth(Date day, Calendar cal) {
		if (day == null)
			day = new Date();
		cal.setTime(day);

		// set time to end of day
		cal.set(Calendar.HOUR_OF_DAY, cal.getMaximum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, cal.getMaximum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getMaximum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal.getMaximum(Calendar.MILLISECOND));
		// set time to first day of month
		cal.set(Calendar.DAY_OF_MONTH, 1);
		// add one month
		cal.add(Calendar.MONTH, 1);
		// back up one day
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}
}
