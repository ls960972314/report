package com.report.common.dal.common.exception;

import com.report.common.dal.common.enums.ReportExceptionCodes;

/**
 * 异常工具类
 * @author lishun
 * @since 2017年2月27日 上午11:25:47
 */
public class ReportExceptionUtil {

	private ReportExceptionUtil() {}
	
	/**
	 * 异常转换
	 * @param e
	 * @throws ReportException
	 */
	public static void FormatException(Exception e) throws ReportException {
		if (e instanceof ReportException) {
			ReportExceptionCodes code = ReportExceptionCodes.getByCode(((ReportException) e).getCode());
			throw new ReportException(code);
		} else {
			throw new ReportException(ReportExceptionCodes.SYSTEM_EXCEPTION);
		}
	}
}
