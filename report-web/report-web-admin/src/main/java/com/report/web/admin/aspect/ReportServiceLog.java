package com.report.web.admin.aspect;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 拦截service注解
 * @author lishun
 * @since 2017年4月19日 下午6:51:49
 */
@Documented
@Target({ METHOD, PARAMETER })
@Retention(RUNTIME)
public @interface ReportServiceLog {

    /**
     * 操作类型
     * @return
     */
    OperTypeEnum opType() default OperTypeEnum.QUERY;
    /**
     * 资源编码
     * @return
     */
    String resCode() default "";
    /**
     * 描述操作的功能信息
     * @return
     */
    String description()  default "";
    /**
     * 操作说明
     * @return
     */
    String opDes() default "";
}
