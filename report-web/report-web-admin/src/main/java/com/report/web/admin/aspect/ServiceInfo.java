package com.report.web.admin.aspect;

import lombok.Data;

/**
 * 操作配置信息
 * @author lishun
 * @since 2017年4月19日 下午7:03:56
 */
@Data
public class ServiceInfo implements java.io.Serializable{

    private static final long serialVersionUID = -4779034930748703428L;

    public ServiceInfo() {
    }

    private OperTypeEnum opType;

    private String resCode;

    private String opContent;

    private String opDes;

    /**
     * @param opType
     * @param resCode
     * @param opContent
     * @param opDes
     */
    public ServiceInfo(OperTypeEnum opType, String resCode, String opContent, String opDes) {
        super();
        this.opType = opType;
        this.resCode = resCode;
        this.opContent = opContent;
        this.opDes = opDes;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ServiceInfo [opType=" + opType + ", resCode=" + resCode + ", opContent=" + opContent + ", opDes="
                + opDes + "]";
    }
}
