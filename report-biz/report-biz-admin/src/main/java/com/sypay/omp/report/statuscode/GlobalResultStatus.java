package com.sypay.omp.report.statuscode;

/**
 * 全局结果状态码
 *
 * @author lishun
 */
public class GlobalResultStatus {
	public static final ResultStatus SUCCESS = new ResultStatus(0, "成功");

	public static final ResultStatus AUTH_MISSING = new ResultStatus(100010100, "缺少授权信息");
	public static final ResultStatus ACCESS_TOKEN_ERROR = new ResultStatus(100010101, "accessToken错误或已过期");
	public static final ResultStatus REFRESH_TOKEN_ERROR = new ResultStatus(100010102, "refreshToken错误或已过期");
	public static final ResultStatus APPID_ERROR = new ResultStatus(100010103, "AppId不合法");
	public static final ResultStatus TIMESTAMP_EXPIRED = new ResultStatus(100010104, "timestamp过期");
	public static final ResultStatus SIGNATURE_ERROR = new ResultStatus(100010105, "签名错误");
	public static final ResultStatus REQUEST_URL_ERROR = new ResultStatus(100010106, "请求地址错误");
	public static final ResultStatus REQUEST_SCHEME_ERROR = new ResultStatus(100010107, "请求Scheme错误");
	public static final ResultStatus REQUEST_METHOD_ERROR = new ResultStatus(100010108, "请求方法错误");

	public static final ResultStatus ERROR = new ResultStatus(100010200, "失败");
	public static final ResultStatus PARAM_MISSING = new ResultStatus(100010201, "缺少参数");
	public static final ResultStatus PARAM_ERROR = new ResultStatus(100010202, "参数错误");
	
	public static final ResultStatus UNKNOWN_FAIL = new ResultStatus(100020100, "未知错误");
	
	public static final ResultStatus REPORT_EXIST = new ResultStatus(100030100, "报表已经存在");

}
