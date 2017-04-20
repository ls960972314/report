package com.report.web.admin.aspect;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.report.common.model.SessionUtil;
import com.report.common.model.UserInfo;

import lombok.extern.slf4j.Slf4j;

/**
 * 基于自定义注解的aop切入点
 * @author lishun
 * @since 2017年4月19日 下午7:56:59
 */
@Slf4j
@Aspect
@Component  
public  class ReportLogAspect {


    //Service层切点  
    @Pointcut("@annotation(com.report.web.admin.aspect.ReportServiceLog)")  
     public  void serviceAspect() {
    }  

    //Controller层切点  
    @Pointcut("@annotation(com.report.web.admin.aspect.ReportControllerLog)")  
     public  void controllerAspect() {
    }  

    /** 
     * 前置通知 用于拦截Controller层记录用户的操作 
     * 
     * @param joinPoint 切点 
     */
    @Before("controllerAspect()")  
     public void doBefore(JoinPoint joinPoint) {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            //请求的IP  
            String ip = IPUtil.getIpAddress(request);
            if(StringUtils.isBlank(ip))
                ip = "not known IP";
            MDC.put("ip", ip);
            //*========控制台输出=========*//  
            ServiceInfo  info = getControllerMethodDescription(joinPoint);
            log.info("=====前置通知开始====="); 
            log.info("请求方法:[{}],方法描述:[{}],请求IP:[{}]",((joinPoint.getTarget().getClass().getName() + "." 
                + joinPoint.getSignature().getName() + "()")),info.toString(),ip);
            //读取session中的用户 
            UserInfo user = SessionUtil.getUserInfo();
            HttpSession session = request.getSession();
            if(null == user){
                 String ajaxSubmit = request.getHeader("X-Requested-With");
                 log.info("user session timeout X-Requested-With = [{}],session = [{}]", ajaxSubmit, session);
                 response.setHeader("sessionstatus", "timeout");
                 return;
             }
            //日志  
        }  catch (Exception e) {  
            //记录本地异常日志  
            log.error("前置通知异常==异常信息:{}", e);  
        }  
    }  

    /** 
     * 异常通知 用于拦截service层记录异常日志 
     * 
     * @param joinPoint 
     * @param e 
     */  
    @AfterThrowing(pointcut = "serviceAspect()", throwing = "e")  
     public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {  
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();  
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        // 获取请求ip
        String ip = IPUtil.getIpAddress(request);
        if(StringUtils.isBlank(ip))
            ip = "not known IP";  
        MDC.put("ip", ip);
        String params = "";  
         try {
             //读取session中的用户  
             UserInfo user = SessionUtil.getUserInfo();        
             if(null == user){
                 String ajaxSubmit = request.getHeader("X-Requested-With");
                 log.info("user session timeout X-Requested-With = [{}]", ajaxSubmit);
                 response.setHeader("sessionstatus", "timeout");
                 return;
             }
           //获取用户请求方法的参数并序列化为JSON格式字符串 
             ServiceInfo info = getServiceMthodDescription(joinPoint);
             if (joinPoint.getArgs() !=  null && joinPoint.getArgs().length > 0) {  
                 for ( int i = 0; i < joinPoint.getArgs().length; i++) {  
                    params += JSON.toJSONString(joinPoint.getArgs()[i]) + ";";  
                }
             }
             String method = joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()";

             /*========控制台输出=========*/  
            log.info("=====异常通知开始=====");  
            log.info("异常代码:[{}],异常信息:[{}],异常方法:[{}],方法描述:[{}],请求人:[{}],请求IP:[{}],请求参数:[{}]",
                e.getClass().getName(),e.getMessage(),method,info.getOpContent(), user.getMemberName(), ip, params);  
            // 日志  
        }  catch (Exception ex) {  
            //记录本地异常日志  
            log.error("异常通知异常==异常信息:{}", ex);  
        }  
         /*==========记录本地异常日志==========*/  
        log.error("异常方法:{}异常代码:{}异常信息:{}参数:{}", 
            joinPoint.getTarget().getClass().getName() + joinPoint.getSignature().getName(), 
            e.getClass().getName(), e.getMessage(), params);  
    }

    /** 
     * 获取注解中对方法的描述信息 用于service层注解 
     * 
     * @param joinPoint 切点 
     * @return 方法描述 
     * @throws Exception 
     */  
    public static ServiceInfo getServiceMthodDescription(JoinPoint joinPoint) throws Exception {  
        String targetName = joinPoint.getTarget().getClass().getName();  
        String methodName = joinPoint.getSignature().getName();  
        Object[] arguments = joinPoint.getArgs();  
        Class targetClass = Class.forName(targetName);  
        Method[] methods = targetClass.getMethods();  
        ServiceInfo info = new ServiceInfo();
         for (Method method : methods) {  
             if (method.getName().equals(methodName)) {  
                Class[] clazzs = method.getParameterTypes();  
                 if (clazzs.length == arguments.length) {  
                    info.setOpContent(method.getAnnotation(ReportServiceLog.class).description());
                    info.setOpType(method.getAnnotation(ReportServiceLog.class).opType());
                    info.setResCode(method.getAnnotation(ReportServiceLog.class).resCode());
                    info.setOpDes(method.getAnnotation(ReportServiceLog.class).opDes());  
                     break;  
                }  
            }  
        }  
         return info;  
    }  

    /** 
     * 获取注解中对方法的描述信息 用于Controller层注解 
     * 
     * @param joinPoint 切点 
     * @return 方法描述 
     * @throws Exception 
     */  
     public  static ServiceInfo getControllerMethodDescription(JoinPoint joinPoint)  throws Exception {  
        String targetName = joinPoint.getTarget().getClass().getName();  
        String methodName = joinPoint.getSignature().getName();  
        Object[] arguments = joinPoint.getArgs();  
        Class targetClass = Class.forName(targetName);  
        Method[] methods = targetClass.getMethods();  
        ServiceInfo info = new ServiceInfo();
         for (Method method : methods) {  
             if (method.getName().equals(methodName)) {  
                Class[] clazzs = method.getParameterTypes();  
                 if (clazzs.length == arguments.length) {
                    info.setOpContent(method.getAnnotation(ReportControllerLog.class).description());
                    info.setOpType(method.getAnnotation(ReportControllerLog.class).opType());
                    info.setResCode(method.getAnnotation(ReportControllerLog.class).resCode());
                    info.setOpDes(method.getAnnotation(ReportControllerLog.class).opDes());
                    break;  
                }  
            }  
        }  
         return info;  
    }  
}