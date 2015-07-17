package com.standard.kit.format;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期时间处理
 * 
 * @author landmark
 * 
 */
public class DateTimeUtil {

	public static final String PATTERN_BIRTHDAY = "yyyy-MM-dd";
	public static final String PATTERN_CURRENT_TIME = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 获取指定格式的当前时间
	 * 
	 * @param pattern
	 * @return
	 */
	public static String getCurrentTime(String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Date data = new Date(System.currentTimeMillis());
		String time = format.format(data);
		return time;
	}

	/**
	 * 当前时间：yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String getCurrentTime() {
		SimpleDateFormat _formatter = new SimpleDateFormat(PATTERN_CURRENT_TIME);
		Date date = new Date(System.currentTimeMillis());
		String time = _formatter.format(date);
		return time;
	}

	/**
	 * 获取指定时间毫秒数
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static long getSpecificTimeMillis(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		month--;
		calendar.set(year, month, day);
		return calendar.getTimeInMillis();
	}

	/**
	 * 获取特定时间特定格式的时间
	 * 
	 * @param milliseconds
	 * @param pattern
	 * @return
	 */
	public static String getSpecificTime(long milliseconds, String pattern) {
		SimpleDateFormat _formatter = new SimpleDateFormat(pattern);
		Date date = new Date(milliseconds);
		String time = _formatter.format(date);
		return time;
	}

	public static int getYear(long milliseconds) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(milliseconds);
		return calendar.get(Calendar.YEAR);
	}

	public static int getMonth(long milliseconds) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(milliseconds);
		return calendar.get(Calendar.MONTH);
	}

	public static int getDay(long milliseconds) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(milliseconds);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	public static int getCurrentDay() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.DATE);
	}
}
