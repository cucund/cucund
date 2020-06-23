package com.cucund.project.spring.ob;


import com.cucund.project.tool.utils.spring.SpringUtil;
import lombok.Data;
import lombok.Getter;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;

@Getter
public class InvokeClass {

    private String className;

    private String method;

    private Class<?>[] paramClass;

    private Method methodObj ;

    public InvokeClass(String className,String method,Class<?>[] paramClass) throws ClassNotFoundException {
        this.className = className;
        this.method = method;
        this.paramClass = paramClass;
        this.methodObj = init();
    }
    private static ApplicationContext applicationContext;

    private Method init() throws ClassNotFoundException {
        return MethodUtils.getMatchingMethod(SpringUtil.getBean(className).getClass(), method, paramClass);
    }

}
