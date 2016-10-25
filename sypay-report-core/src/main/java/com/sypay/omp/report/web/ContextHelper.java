package com.sypay.omp.report.web;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


/**
 * 类说明：<br>
 *      应用上下文获取bean辅助类
 * 
 * <p>
 * 详细描述：<br>
 *      应用上下文获取bean辅助类
 * 
 * </p>
 * 
 * @author 483879 张玉龙
 * 
 * CreateDate: 2014-8-4
 */
@Component
public final class ContextHelper implements ApplicationContextAware {
    private ApplicationContext context;
    
    private static ContextHelper helper = new ContextHelper();
    
    private ContextHelper() {}
    
    public static ContextHelper getInstance() {
        return helper;
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        helper.context = context;
    }
    
    public <T> T getBean(Class<T> clazz) {
        return context.getBean(clazz);
    }

    public <T> T getBean(String name, Class<T> clazz) {
        return context.getBean(name, clazz);
    }

    public <T> T getBean(String name){
        return (T) context.getBean(name);
    }
}

