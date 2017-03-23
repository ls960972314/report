package com.report.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateCommonUtils {

	public static String getTheDate() {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		String dateStr = sd.format(new Date());
		print("*******得到今天的日期*******" + dateStr);
		return dateStr;
	}

	/**
	 * 得到本周第一天
	 * @return
	 * @throws ParseException 
	 */
	public static String getWeekBeginDay(String date) throws ParseException {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		cal.setTime(df.parse(date));
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return df.format(cal.getTime());
	}
	/**
	 * 得到本周最后一天
	 * @return
	 * @throws ParseException 
	 */
	public static String getWeekEndDay(String date) throws ParseException {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		cal.setTime(df.parse(date));
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		return df.format(cal.getTime());
	}

	public static String getMonthDate(String date) throws ParseException {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		cal.setTime(df.parse(date));
        int month = cal.get(Calendar.MONTH) + 1;//月
        int year = cal.get(Calendar.YEAR);      //年
		return year + "-" + (month>10 ? month : "0" + month);
	}

	private static void print(Object o) {
		System.out.println(o.toString());
	}

}
