package com.report.common.dal.report.entity.vo;

/**
 * 线程中存储数据源
 * @author 887961
 *
 */
public class SpObserver {
	private static ThreadLocal local = new ThreadLocal();
	
	public static String defaultDataBase = "ods";
	
	public static void putSp(String sp) {
		local.set(sp);
	}

	public static String getSp() {
		return ((String) local.get());
	}
}
