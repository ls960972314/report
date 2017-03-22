package com.report.common.dal.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

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
