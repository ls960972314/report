package com.sypay.omp.report.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日期工具类
 * @author lishun
 *
 */
public class TimeUtil {
	private static final Logger LOG = LoggerFactory.getLogger(TimeUtil.class);

	// 格式1
	public static final String DATE_FORMAT_1 = "yyyy-MM-dd HH:mm:ss";
	// 格式2
	public static final String DATE_FORMAT_2 = "yyyy-MM-dd";
	// 格式3
	public static final String DATE_FORMAT_3 = "hh:mm:ss";
	// 格式4
	public static final String DATE_FORMAT_4 = "yyyyMMddHHmmss";
	// 格式5
	public static final String DATE_FORMAT_5 = "yyyy年MM月dd日 HH:mm";
	// 格式6
	public static final String DATE_FORMAT_6 = "MM月dd日";
	// 格式7
	public static final String DATE_FORMAT_7 = "yyyy-MM";
	// 格式8
	public static final String DATE_FORMAT_8 = "MMddHHmmssSSS";
	// 格式9
	public static final String DATE_FORMAT_9 = "yyyyMMdd";
	//时间格式开始
	public static final String TIME_FORMAT_000000 = "00:00:00";
	//时间格式结束
	public static final String TIME_FORMAT_235959 = "23:59:59";
	
	/**
	 * 字符串时间转换为时间类型
	 * 
	 * @param dateString
	 * @param format
	 * @return
	 */
	public static Date stringToDate(String dateString) {
		if(StringUtils.isBlank(dateString)){
			return null;
		}
		try {
			dateString = dateString.trim();
			String format = DATE_FORMAT_2;
			if(dateString.indexOf("-") > -1){
				if (dateString.indexOf("-", dateString.indexOf("-")) > -1) {
					if (dateString.indexOf(":") > -1) {
						format = DATE_FORMAT_1;
					}
				}else{
					format = DATE_FORMAT_7;
				}
			}else{
				if (dateString.indexOf(":") > -1) {
					if (dateString.indexOf(":", dateString.indexOf(":")) > -1) {
						format = DATE_FORMAT_3;
					}else if(dateString.indexOf("年") > -1 && dateString.indexOf("月") > -1 && dateString.indexOf("日") > -1) {
						format = DATE_FORMAT_5;
					}
				}else{
					if(dateString.indexOf("年") <= -1 && dateString.indexOf("月") > -1 && dateString.indexOf("日") > -1) {
						format = DATE_FORMAT_6;
					}else if(dateString.length() == DATE_FORMAT_4.length()){
						format = DATE_FORMAT_4;
					}else if(dateString.length() == DATE_FORMAT_8.length()){
						format = DATE_FORMAT_8;
					}else if(dateString.length() == DATE_FORMAT_9.length()){
						format = DATE_FORMAT_9;
					}
				}
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.parse(dateString);
		} catch (ParseException e) {
			LOG.error("Error when  getDateFromString from dateString, errmsg: "
							+ e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 字符串时间转换为时间类型
	 * 
	 * @param dateString
	 * @param format
	 * @return
	 */
	public static Date stringToDate(String dateString, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.parse(dateString);
		} catch (ParseException e) {
			throw new RuntimeException(
					"Error when  getDateFromString from dateString, errmsg: "
							+ e.getMessage(), e);
		}
	}
	
	public static Date stringToDate(String dateString,String HHmmss ,String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.parse(dateString + " " + HHmmss);
		} catch (ParseException e) {
			throw new RuntimeException(
					"Error when  getDateFromString from dateString, errmsg: "
							+ e.getMessage(), e);
		}
	}

	/**
	 * toDate for format 1
	 * 
	 * @param dateString
	 * @return
	 */
	public static Date toDateFormat_1(String dateString) {
		return stringToDate(dateString, DATE_FORMAT_1);
	}

	/**
	 * toDate for format 2
	 * 
	 * @param dateString
	 * @return
	 */
	public static Date toDateFormat_2(String dateString) {
		return stringToDate(dateString, DATE_FORMAT_2);
	}

	/**
	 * toDate for format 4
	 * 
	 * @param dateString
	 * @return
	 */
	public static Date toDateFormat_4(String dateString) {
		return stringToDate(dateString, DATE_FORMAT_4);
	}

	/**
	 * toDate for format 5
	 * 
	 * @param dateString
	 * @return
	 */
	public static Date toDateFormat_9(String dateString) {
		return stringToDate(dateString, DATE_FORMAT_9);
	}

	/**
	 * 时间类型转换为字符串类型
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String dateToString(Date date, String format) {
		if (null == date) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * toString for format 1
	 * 
	 * @param date
	 * @return
	 */
	public static String toStringFormat_1(Date date) {

		return dateToString(date, DATE_FORMAT_1);
	}

	/**
	 * 比较时间
	 * 
	 * @param endDate
	 * @param startDate
	 * @return
	 */
	public static long diff(Date endDate, Date startDate) {
		long endTime = getMillis(endDate);
		long startTime = getMillis(startDate);
		return endTime - startTime;
	}

	public static boolean diff(Date endDate, Date startDate, int n) {
		long endTime = getMillis(endDate);
		long startTime = getMillis(startDate);
		return (endTime - startTime - n * 24 * 3600 * 1000L) > 0 ? true : false;
	}

	public static boolean diffY(Date endDate, Date startDate) {
		String endTime = toStringFormat_2(endDate);
		String startTime = toStringFormat_2(startDate);

		return endTime.compareTo(startTime) >= 1 ? true : false;
	}

	/**
	 * get million
	 * 
	 * @param dt
	 * @return
	 */
	public static long getMillis(Date dt) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		return cal.getTimeInMillis();
	}

	/**
	 * toString for format 4
	 * 
	 * @param date
	 * @return
	 */
	public static String toStringFormat_4(Date date) {

		return dateToString(date, DATE_FORMAT_4);
	}

	public static String toStringFormat_5(Date date) {
		return dateToString(date, DATE_FORMAT_5);
	}

	public static String toStringFormat_6(Date date) {
		return dateToString(date, DATE_FORMAT_6);
	}

	/**
	 * toString for format 2
	 * 
	 * @param date
	 * @return
	 */
	public static String toStringFormat_2(Date date) {

		return dateToString(date, DATE_FORMAT_2);
	}

	/**
	 * toString for format 7
	 * 
	 * @param date
	 * @return
	 */
	public static String toStringFormat_7(Date date) {

		return dateToString(date, DATE_FORMAT_7);
	}

	/**
	 * 与当前时间的间隔
	 * 
	 * @param n
	 * @return
	 * @author weiyuanhua
	 * @date 2012-8-16 下午2:40:29
	 */
	public static Date getDateDiff(int n) {
		Date d = new Date();
		Date returnDay = new Date(d.getTime() + n * 24 * 3600 * 1000L);
		return returnDay;
	}

	public static String getDiffToString(Date date, int n, String pattern) {
		Date returnDay = new Date(date.getTime() + n * 24 * 3600 * 1000L);
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		return df.format(returnDay);
	}

	/**
	 * 与当前时间的间隔
	 * 
	 * @param n
	 * @return
	 * @author weiyuanhua
	 * @date 2012-8-16 下午2:39:21
	 */
	public static Timestamp getTimestampDiff(int n) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = new Date();
		Date dayDiff = new Date(d.getTime() + n * 24 * 3600 * 1000L);
		String time = df.format(dayDiff);
		return Timestamp.valueOf(time);
	}

	/**
	 * Date 转 Timestamp
	 * 
	 * @return
	 * @author weiyuanhua
	 * @date 2012-8-16 下午2:41:01
	 */
	public static Timestamp stringToTimestamp() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = df.format(new Date());
		return Timestamp.valueOf(time);
	}

	/**
	 * 时间格式化
	 * 
	 * @param date
	 * @param style
	 * @return
	 * @author weiyuanhua
	 * @date 2012-8-16 下午2:41:40
	 */
	public static String parseToString(Date date, String pattern) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
		simpleDateFormat.applyPattern(pattern);
		String str = null;
		if (date == null)
			return null;
		str = simpleDateFormat.format(date);
		return str;
	}

	/**
	 * 时间比大小
	 */
	public static int compareWithNow(String t) {
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_4);
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		try {
			c1.setTime(formatter.parse(t));
			c2.setTime(new Date());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return c1.compareTo(c2);
	}
	
	/**
	 * 时间比大小
	 */
	public static int compareWithNow_2(String t) {
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_2);
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		try {
			c1.setTime(formatter.parse(t));
			c2.setTime(new Date());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return c1.compareTo(c2);
	}

	public static Timestamp stringToTimestamp(String dateStr) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Calendar cal = Calendar.getInstance();
		try {
			Date date = sdf.parse(dateStr);
			date.getTime();
			cal.setTime(date);
			return new Timestamp(cal.getTimeInMillis());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		cal.setTime(new Date());
		return new Timestamp(cal.getTimeInMillis());
	}

	public static Timestamp stringToTimestamp(String dateStr, String pattern) {
		Timestamp dateTime = new Timestamp(System.currentTimeMillis());
		String date = dateStr + " 00:00:00";
		try {
			dateTime = Timestamp.valueOf(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dateTime;
	}
	
	public static Timestamp stringToTimestamp(String dateStr,String HHmmss, String pattern) {
		Timestamp dateTime = new Timestamp(System.currentTimeMillis());
		String date = dateStr + " " + HHmmss;
		try {
			dateTime = Timestamp.valueOf(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dateTime;
	}

	public static String timestampToString(Timestamp ts, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		String str = df.format(ts);
		return str;
	}

	public static Date formatter(Date date) {
		if (date == null)
			return null;
		SimpleDateFormat df = new SimpleDateFormat(TimeUtil.DATE_FORMAT_2);
		String time = df.format(date);
		try {
			return df.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date addDay(Date date, int dayNum) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(calendar.DATE, dayNum);// 把日期往后增加dayNum天,整数往后推,负数往前移动
		return calendar.getTime();
	}
	
	/**
	 * get max time in that day.
	 * 
	 * @param dateStr
	 * @param dateFormat
	 * @return
	 */
	public static Date getMaxDate(String dateStr, String dateFormat) {
		Date dt = stringToDate(dateStr, dateFormat);
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		cal.set(Calendar.HOUR, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}
	
	/**
	 * get max time in that day.
	 * 
	 * @param dateStr
	 * @param dateFormat
	 * @return
	 */
	public static Date getMaxDate(Date dt) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		cal.set(Calendar.HOUR, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}


	/**
	 * get min time in that day.
	 * 
	 * @param dateStr
	 * @param dateFormat
	 * @return
	 */
	public static Date getMinDate(String dateStr, String dateFormat) {
		Date dt = stringToDate(dateStr, dateFormat);
		
		return getMinDate(dt);
	}
	
	/**
	 * 获取开始时间
	 * @param dt
	 * @return
	 */
	public static Date getBeginDate(String date){
		Date dt = stringToDate(date);
		return getBeginDate(dt);
	}
	
	/**
	 * 获取结束时间
	 * @param dt
	 * @return
	 */
	public static Date getEndDate(String date){
		Date dt = stringToDate(date);
		return getEndDate(dt);
	}
	
	/**
	 * 获取结束时间
	 * @param dt
	 * @return
	 */
	public static Date getNextBeginDate(String date){
		Date dt = stringToDate(date);
		return getNextMinDate(dt);
	}
	
	/**
	 * 获取开始时间
	 * @param dt
	 * @return
	 */
	public static Date getBeginDate(Date dt){
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		
		// 已经有时间，直接返回
		if (0 < cal.get(Calendar.HOUR_OF_DAY) || 0 < cal.get(Calendar.MINUTE) || 0 < cal.get(Calendar.SECOND)) {
			return cal.getTime();
		}
		return getMinDate(dt);
	}
	
	/**
	 * 获取结束时间
	 * @param dt
	 * @return
	 */
	public static Date getEndDate(Date dt){
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		
		// 已经有时间，直接返回
		if (0 < cal.get(Calendar.HOUR_OF_DAY) || 0 < cal.get(Calendar.MINUTE) || 0 < cal.get(Calendar.SECOND)) {
			return cal.getTime();
		}
		return getMaxDate(dt);
	}
	
	/**
	 * get min time in that day.
	 * 
	 * @param dt
	 * @return
	 */
	public static Date getMinDate(Date dt) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}
	
	public static String getMinDate(String data){
		Date dt = stringToDate(data, DATE_FORMAT_2);
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return toStringFormat_1(cal.getTime());
	}
	
	public static String getMaxDate(String data){
		Date dt = stringToDate(data, DATE_FORMAT_2);
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		cal.set(Calendar.HOUR, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return toStringFormat_1(cal.getTime());
	}
	
	/**
	 * 获取第二天的0点，用于最大值
	 * @param data
	 * @return
	 */
	public static Date getNextMinDate(String data){
		Date dt = stringToDate(data, DATE_FORMAT_2);
		return getNextMinDate(dt);
	}
	
	/**
	 * 获取第二天的0点，用于最大值
	 * @param data
	 * @return
	 */
	public static Date getNextMinDate(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}
	
	/**
	 * 取得多少小时以前或以后的时间
	 * @param d
	 * @param hours
	 * @return
	 * @author niejing@sypay.com
	 * @date 2013-10-23 下午5:01:33
	 */
	public static Date addHours(Date d, int hours) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(c.HOUR, hours);

		return c.getTime();
	}

	/**
	 * 取得多少小时以前或以后的时间
	 * @param date YYYYMMDDHHmmss
	 * @param hours
	 * @return
	 * @author niejing@sypay.com
	 * @date 2013-10-23 下午5:01:33
	 */
	public static Date addHours(String date, int hours) {
		Date d = stringToDate(date, DATE_FORMAT_4);
		return addHours(d, hours);
	}
	
	/**
	 * test
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
//		System.out.println(TimeUtil.stringToDate("2014-05-14",TimeUtil.TIME_FORMAT_235959,
//				TimeUtil.DATE_FORMAT_1));
//		System.out.println(TimeUtil.dateToString(new Date(), TimeUtil.DATE_FORMAT_8));
		System.out.println(getNextMinDate("2014-09-20"));
	}

}
