package com.report.web.admin.aspect;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;


/**
 * 
 * @author lishun
 * @since 2017年4月19日 下午7:58:42
 */
@Aspect
@Component
@Slf4j
public class SysLogAspect {

    @Pointcut("within(@org.springframework.stereotype.Service *)")
    public void cutService() {}

    @Pointcut("within(@org.springframework.stereotype.Controller *)")
    public void cutController() {}

    @Around("cutService()")
    public Object recordServiceLog(ProceedingJoinPoint point) throws Throwable {
        String strMethodName = point.getSignature().getName();
        String strClassName = point.getTarget().getClass().getName();
        log.info("methodName={},className={}",strMethodName,strClassName);
        Object[] params = point.getArgs();
        StringBuffer bfParams = new StringBuffer();
        Enumeration<String> paraNames = null;
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        MDC.put("ip", IPUtil.getIpAddress(request));
        if (params != null && params.length > 0) {
            paraNames = request.getParameterNames();
            String key;
            String value;
            while (paraNames.hasMoreElements()) {
                key = paraNames.nextElement();
                value = request.getParameter(key);
                bfParams.append(key).append("=").append(value).append("&");
            }
            if (StringUtils.isBlank(bfParams)) {
                bfParams.append(request.getQueryString());
            }
        }
        log.info("SysLogAspect recordServiceLog className={},methodName={},params=[{}]",
            strClassName, strMethodName, bfParams.toString());
        return point.proceed();
    }

    @Around("cutController()")
    public Object recordControllerLog(ProceedingJoinPoint point) throws Throwable {
        String strMethodName = point.getSignature().getName();
        String strClassName = point.getTarget().getClass().getName();
        log.info("methodName={},className={}",strMethodName,strClassName);
        Object[] params = point.getArgs();

        StringBuffer bfParams = new StringBuffer();
        Enumeration<String> paraNames = null;
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        MDC.put("ip", IPUtil.getIpAddress(request));
        if (params != null && params.length > 0) {
            paraNames = request.getParameterNames();
            String key;
            String value;
            while (paraNames.hasMoreElements()) {
                key = paraNames.nextElement();
                value = request.getParameter(key);
                bfParams.append(key).append("=").append(value).append("&");
            }
            if (StringUtils.isBlank(bfParams)) {
                bfParams.append(request.getQueryString());
            }
        }
        log.info("SysLogAspect recordControllerLog className={},methodName={},params=[{}]",
            strClassName, strMethodName, bfParams.toString());
        return point.proceed();
    }
}
