package com.cucund.project.tool.utils.spring;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class SpringUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    public SpringUtil() {
        log.info("SpringUtil 初始化");
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.info("ApplicationContextAware");
        SpringUtil.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }


    public static <T> Map<String,T> getBeans( Class<T> clazz) {
        return getApplicationContext().getBeansOfType(clazz);
    }

    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    private static ApplicationContext getApplicationContext() {
        return applicationContext == null ? null : applicationContext;
    }

}
