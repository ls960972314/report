package com.sypay.omp.per.util;

import java.math.BigDecimal;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class NumberUtil {

	public static final String DIVISOR_100 = "100";

	public static final int BIGDECIMAL_SCALE_2 = 2;

	/**
	 * 判断是否为数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	/**
	 * String类型转换为Integer类型
	 * 
	 * @param str
	 * @return
	 */
	public static Integer stringToInt(String str) {
		if (StringUtils.isEmpty(str) || !isNumeric(str)) {
			return null;
		}
		return Integer.valueOf(str);
	}

	/**
	 * String类型转换为Long类型
	 * 
	 * @param str
	 * @return
	 */
	public static Long stringToLong(String str) {
		if (StringUtils.isEmpty(str) || !isNumeric(str)) {
			return null;
		}
		return Long.valueOf(str);
	}

	/**
	 * 提供精确的小数位四舍五入处理
	 * 
	 * @param val需要四舍五入的数字
	 * @param divisor除数
	 * @param scale小数点后保留几位
	 * @param round_mode指定的舍入模式
	 * @return
	 */
	public static String numberFormat(BigDecimal val, BigDecimal divisor,
			int scale, int round_mode) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive or zero");
		}

		BigDecimal bd = val.divide(divisor);

		return bd.setScale(scale, round_mode).toString();
	}

	public static String numberFormat(int val) {
		return numberFormat(new BigDecimal(val), new BigDecimal(DIVISOR_100),
				BIGDECIMAL_SCALE_2, BigDecimal.ROUND_UNNECESSARY);
	}

	public static String numberFormat(long val) {
		return numberFormat(new BigDecimal(val), new BigDecimal(DIVISOR_100),
				BIGDECIMAL_SCALE_2, BigDecimal.ROUND_UNNECESSARY);
	}

	public static String numberFormat(double val) {
		return numberFormat(new BigDecimal(val), new BigDecimal(DIVISOR_100),
				BIGDECIMAL_SCALE_2, BigDecimal.ROUND_UNNECESSARY);
	}

	/**
	 * 提供精确的加法运算。
	 * 
	 * @param v1
	 *            加数
	 * @param v2
	 *            被加数
	 * @return 两个参数的和
	 */
	public static BigDecimal add(String v1, String v2) {
		return new BigDecimal(v1).add(new BigDecimal(v2));
	}

	/**
	 * 提供精确的减法运算。
	 * 
	 * @param v1
	 *            减数
	 * @param v2
	 *            被减数
	 * @return 两个参数的差
	 */
	public static BigDecimal sub(String v1, String v2) {
		return new BigDecimal(v1).subtract(new BigDecimal(v2));
	}

	/**
	 * 提供精确的乘法运算。
	 * 
	 * @param v1
	 *            乘数
	 * @param v2
	 *            被乘数
	 * @return 两个参数的积
	 */
	public static BigDecimal mul(String v1, String v2) {
		return new BigDecimal(v1).multiply(new BigDecimal(v2));
	}

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            除数
	 * @param v2
	 *            被除数
	 * @param scale
	 *            表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */
	public static BigDecimal div(String v1, String v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		return new BigDecimal(v1).divide(new BigDecimal(v2), scale,
				BigDecimal.ROUND_HALF_UP);
	}
	
	public static Double centToYuan(Long v) {
		return null == v ? new Double(0) : centToYuan(new BigDecimal(v.toString()));
	}

	public static Double centToYuan(BigDecimal v) {
		return null == v ? new Double(0) : v.divide(new BigDecimal(DIVISOR_100), 2,
				BigDecimal.ROUND_HALF_UP).doubleValue();
	}
}
