package com.report.common.dal.common.utils;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.report.common.dal.common.enums.ReportExceptionCodes;
import com.report.common.dal.common.exception.ReportException;

/**
 * 时间工具类
 * @author lishun
 * @since 2017年2月8日 上午10:38:17
 */
public class DateUtil {

	private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);
	
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
	
	/**
	 * 字符串时间转换为时间类型
	 * @param dateString
	 * @param format
	 * @return
	 * @throws ReportException 
	 */
	public static Date stringToDate(String dateString) throws ReportException {
		if (StringUtils.isBlank(dateString)) {
			return null;
		}
		try {
			dateString = dateString.trim();
			String format = DATE_FORMAT_2;
			if (dateString.indexOf("-") > -1) {
				if (dateString.indexOf("-", dateString.indexOf("-")) > -1) {
					if (dateString.indexOf(":") > -1) {
						format = DATE_FORMAT_1;
					}
				} else {
					format = DATE_FORMAT_7;
				}
			} else {
				if (dateString.indexOf(":") > -1) {
					if (dateString.indexOf(":", dateString.indexOf(":")) > -1) {
						format = DATE_FORMAT_3;
					} else if (dateString.indexOf("年") > -1 && dateString.indexOf("月") > -1 && dateString.indexOf("日") > -1) {
						format = DATE_FORMAT_5;
					}
				} else {
					if (dateString.indexOf("年") <= -1 && dateString.indexOf("月") > -1 && dateString.indexOf("日") > -1) {
						format = DATE_FORMAT_6;
					} else if (dateString.length() == DATE_FORMAT_4.length()) {
						format = DATE_FORMAT_4;
					} else if (dateString.length() == DATE_FORMAT_8.length()) {
						format = DATE_FORMAT_8;
					} else if (dateString.length() == DATE_FORMAT_9.length()) {
						format = DATE_FORMAT_9;
					}
				}
			}
			DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(format);
			DateTime dateTime = DateTime.parse(dateString, dateTimeFormatter);
			return dateTime.toDate();
			
		} catch (Exception e) {
			logger.error("stringToDate Exception", e);
			throw new ReportException(ReportExceptionCodes.UTIL_DATE_RUNTIME_EXCEPTION);
		}
	}

	/**
	 * 字符串转换为时间类型
	 * 
	 * @param dateString
	 * @param format
	 * @return
	 * @throws ReportException 
	 */
	public static Date stringToDate(String dateString, String format) throws ReportException {
		try {
			DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(format);
			DateTime dateTime = DateTime.parse(dateString, dateTimeFormatter);
			return dateTime.toDate();
		} catch (Exception e) {
			logger.error("stringToDate Exception", e);
			throw new ReportException(ReportExceptionCodes.UTIL_DATE_RUNTIME_EXCEPTION);
		}
	}
	
	/**
	 * toDate for yyyy-MM-dd HH:mm:ss
	 * @param dateString
	 * @return
	 * @throws ReportException 
	 */
	public static Date toDateFormat_1(String dateString) throws ReportException {
		return stringToDate(dateString, DATE_FORMAT_1);
	}

	/**
	 * toDate for yyyy-MM-dd
	 * @param dateString
	 * @return
	 * @throws ReportException 
	 */
	public static Date toDateFormat_2(String dateString) throws ReportException {
		return stringToDate(dateString, DATE_FORMAT_2);
	}
	
	/**
	 * toDate for hh:mm:ss
	 * @param dateString
	 * @return
	 * @throws ReportException 
	 */
	public static Date toDateFormat_3(String dateString) throws ReportException {
		return stringToDate(dateString, DATE_FORMAT_3);
	}

	/**
	 * toDate for yyyyMMddHHmmss
	 * @param dateString
	 * @return
	 * @throws ReportException 
	 */
	public static Date toDateFormat_4(String dateString) throws ReportException {
		return stringToDate(dateString, DATE_FORMAT_4);
	}
	
	/**
	 * toDate for yyyy年MM月dd日 HH:mm
	 * @param dateString
	 * @return
	 * @throws ReportException 
	 */
	public static Date toDateFormat_5(String dateString) throws ReportException {
		return stringToDate(dateString, DATE_FORMAT_5);
	}
	
	/**
	 * toDate for MM月dd日
	 * @param dateString
	 * @return
	 * @throws ReportException 
	 */
	public static Date toDateFormat_6(String dateString) throws ReportException {
		return stringToDate(dateString, DATE_FORMAT_6);
	}
	
	/**
	 * toDate for yyyy-MM
	 * @param dateString
	 * @return
	 * @throws ReportException 
	 */
	public static Date toDateFormat_7(String dateString) throws ReportException {
		return stringToDate(dateString, DATE_FORMAT_7);
	}
	
	/**
	 * toDate for MMddHHmmssSSS
	 * @param dateString
	 * @return
	 * @throws ReportException 
	 */
	public static Date toDateFormat_8(String dateString) throws ReportException {
		return stringToDate(dateString, DATE_FORMAT_8);
	}

	/**
	 * toDate for yyyyMMdd
	 * @param dateString
	 * @return
	 * @throws ReportException 
	 */
	public static Date toDateFormat_9(String dateString) throws ReportException {
		return stringToDate(dateString, DATE_FORMAT_9);
	}

	/**
	 * 时间类型转换为字符串类型
	 * @param date
	 * @param format
	 * @return
	 * @throws ReportException 
	 */
	public static String dateToString(Date date, String format) throws ReportException {
		try {
			if (null == date) {
				return "";
			}
			DateTime dateTime = new DateTime(date);
			return dateTime.toString(format);
		} catch (Exception e) {
			logger.error("dateToString Exception", e);
			throw new ReportException(ReportExceptionCodes.UTIL_DATE_RUNTIME_EXCEPTION);
		}
	}

	/**
	 * toString for yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @return
	 * @throws ReportException 
	 */
	public static String toStringFormat_1(Date date) throws ReportException {
		return dateToString(date, DATE_FORMAT_1);
	}

	/**
	 * toString for yyyy-MM-dd
	 * @param date
	 * @return
	 * @throws ReportException 
	 */
	public static String toStringFormat_2(Date date) throws ReportException {
		return dateToString(date, DATE_FORMAT_2);
	}
	
	/**
	 * toString for hh:mm:ss
	 * @param date
	 * @return
	 * @throws ReportException 
	 */
	public static String toStringFormat_3(Date date) throws ReportException {
		return dateToString(date, DATE_FORMAT_3);
	}
	
	/**
	 * toString for yyyyMMddHHmmss
	 * @param date
	 * @return
	 * @throws ReportException 
	 */
	public static String toStringFormat_4(Date date) throws ReportException {
		return dateToString(date, DATE_FORMAT_4);
	}

	/**
	 * toString for yyyy年MM月dd日 HH:mm
	 * @param date
	 * @return
	 * @throws ReportException 
	 */
	public static String toStringFormat_5(Date date) throws ReportException {
		return dateToString(date, DATE_FORMAT_5);
	}

	/**
	 * toString for MM月dd日
	 * @param date
	 * @return
	 * @throws ReportException 
	 */
	public static String toStringFormat_6(Date date) throws ReportException {
		return dateToString(date, DATE_FORMAT_6);
	}

	/**
	 * toString for yyyy-MM
	 * @param date
	 * @return
	 * @throws ReportException 
	 */
	public static String toStringFormat_7(Date date) throws ReportException {
		return dateToString(date, DATE_FORMAT_7);
	}
	
	/**
	 * toString for MMddHHmmssSSS
	 * @param date
	 * @return
	 * @throws ReportException 
	 */
	public static String toStringFormat_8(Date date) throws ReportException {
		return dateToString(date, DATE_FORMAT_8);
	}
	
	/**
	 * toString for yyyyMMdd
	 * @param date
	 * @return
	 * @throws ReportException 
	 */
	public static String toStringFormat_9(Date date) throws ReportException {
		return dateToString(date, DATE_FORMAT_9);
	}
	
	/**
	 * 日期计算
	 * @param dateString 日期字符串
	 * @param format 日期字符串格式
	 * @param d 天 正数向后加  负数向前减
	 * @return
	 * @throws ReportException
	 */
	public static String calcDay(String dateString, String format, int d) throws ReportException {
		try {
			DateTime dateTime = new DateTime(stringToDate(dateString, format));
	        dateTime = dateTime.plusDays(d);
	        return dateTime.toString(format);
		} catch (Exception e) {
			logger.error("calcDay Exception", e);
			throw new ReportException(ReportExceptionCodes.UTIL_DATE_RUNTIME_EXCEPTION);
		}
	}
	
	/**
	 * 日期计算
	 * @param date 日期
	 * @param format 日期字符串格式
	 * @param d 天 正数向后加  负数向前减
	 * @return
	 * @throws ReportException
	 */
	public static String calcDay(Date date, String format, int d) throws ReportException {
		try {
			DateTime dateTime = new DateTime(date);
	        dateTime = dateTime.plusDays(d);
	        return dateTime.toString(format);
		} catch (Exception e) {
			logger.error("calcDay Exception", e);
			throw new ReportException(ReportExceptionCodes.UTIL_DATE_RUNTIME_EXCEPTION);
		}
	}
	
	/**
	 * 日期计算
	 * @param date 日期字符串
	 * @param d 天 正数向后加  负数向前减
	 * @return
	 * @throws ReportException
	 */
	public static Date calcDay(Date date, int d) throws ReportException {
		try {
			DateTime dateTime = new DateTime(date);
	        dateTime = dateTime.plusDays(d);
	        return dateTime.toDate();
		} catch (Exception e) {
			logger.error("calcDay Exception", e);
			throw new ReportException(ReportExceptionCodes.UTIL_DATE_RUNTIME_EXCEPTION);
		}
	}
	
	/**
	 * 日期计算
	 * @param dateString 日期字符串
	 * @param format 日期字符串格式
	 * @param h 小时  正数向后加  负数向前减
	 * @return
	 * @throws ReportException
	 */
	public static String calcHour(String dateString, String format, int h) throws ReportException {
		try {
			DateTime dateTime = new DateTime(stringToDate(dateString, format));
	        dateTime = dateTime.plusHours(h);
	        return dateTime.toString(format);
		} catch (Exception e) {
			logger.error("calcHour Exception", e);
			throw new ReportException(ReportExceptionCodes.UTIL_DATE_RUNTIME_EXCEPTION);
		}
	}
	
	/**
	 * 日期计算
	 * @param date 日期
	 * @param h 小时  正数向后加  负数向前减
	 * @return
	 * @throws ReportException
	 */
	public static Date calcHour(Date date, int h) throws ReportException {
		try {
			DateTime dateTime = new DateTime(date);
	        dateTime = dateTime.plusHours(h);
	        return dateTime.toDate();
		} catch (Exception e) {
			logger.error("calcHour Exception", e);
			throw new ReportException(ReportExceptionCodes.UTIL_DATE_RUNTIME_EXCEPTION);
		}
	}
	
	/**
	 * 校验字符串是否为日期格式
	 * @param dateString 日期字符串
	 * @param format 日期字符串格式
	 * @return
	 */
	public static boolean isValidDate(String dateString, String format) {
		try {
			stringToDate(dateString, format);
			return true;
		} catch (Exception e) {
			logger.error("isValidDate Exception", e);
			return false;
		}
	}

	/**
	 * date是否为今天凌晨之前的时间
	 * @param date
	 * @return 是 true 否 false
	 */
	public static boolean isLastDay(Date date) {
		DateTime dateTime = new DateTime().withMillisOfDay(0);
		return date.before(dateTime.toDate());
	}
    /**
     * 当前时间是否超过入参时间
     * @param date
     * @return 超过 true 否则 fasle
     */
    public static boolean timeOverDate(Date date) {
        Date now = new Date();
        return now.after(date);
    }

    /**
     * 获取指定时间的时间戳
     * @param hour 小时(24小时制)
     * @param minute 分钟
     * @param second 秒
     * @param millisecond 毫秒
     * @return 时间戳
     */
    public static long getTimes(int hour, int minute, int second, int millisecond) {
        Calendar cal = Calendar.getInstance(); 
        cal.set(Calendar.HOUR_OF_DAY, hour); 
        cal.set(Calendar.MINUTE, minute); 
        cal.set(Calendar.SECOND, second); 
        cal.set(Calendar.MILLISECOND, millisecond); 
        return cal.getTimeInMillis(); 
    }
}
