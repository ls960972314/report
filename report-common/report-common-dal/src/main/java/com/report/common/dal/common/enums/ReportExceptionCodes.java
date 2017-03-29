package com.report.common.dal.common.enums;

import com.report.common.dal.common.utils.StringUtil;

/**
 * @author lishun
 * @since 2017年2月27日 上午10:25:01
 */
public enum ReportExceptionCodes  implements BaseEnum {

	DB_EXCEPTION("0001", "DB查询异常"),
	SYSTEM_EXCEPTION("0002", "系统异常"),
	PARAM_EXCEPTION("0003", "参数错误"),
	UTIL_DATE_RUNTIME_EXCEPTION("0004", "日期转换错误");
	
    private final String code;

    private final String message;

    private ReportExceptionCodes(String code, String message){
        this.code = code;
        this.message = message;
    }

    public static ReportExceptionCodes getByCode(String code){
        for(ReportExceptionCodes t : values()){
            if(StringUtil.equals(t.getCode(), code)){
                return t;
            }
        }
        return null;
    }

    public boolean isEquals(String code){
        return StringUtil.equals(this.getCode(), code);
    }

    public boolean isEquals(ReportExceptionCodes reconBizExceptionCodes) {
        if(reconBizExceptionCodes == null) return false;

        return isEquals(reconBizExceptionCodes.getCode());
    }
    
    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
