package com.cucund.project.db.aop;

import com.cucund.project.db.anno.Dynamic;
import com.cucund.project.db.entity.ChooseSource;
import com.cucund.project.db.entity.ProxySource;
import com.cucund.project.db.holder.DataSourceHolder;
import com.cucund.project.db.selector.impl.ClusterSource;
import com.cucund.project.tool.utils.strategy.StrategyUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
//@Component
public class DataSourceAspect {

    @Autowired
    ChooseSource chooseSource;

    private static StrategyUtil strategyUtil = new StrategyUtil();
    static{
        //主从复制 策略实现
        strategyUtil.add(new ClusterSource());
    }

    public DataSourceAspect(){
        System.out.println("this is init");
    }

    @Pointcut("execution(* com.cucund.project.*.service.*.*(..))")
    public void pointCut(){}

    @Before("pointCut()")
    public void doBefore(JoinPoint joinPoint){
        ProxySource proxy = new ProxySource();
        proxy.setChooseSource(chooseSource);
        proxy.setJoinPoint(joinPoint);
        strategyUtil.loop(proxy);
    }

    @After("pointCut()")
    public void doAfter(){
        DataSourceHolder.clear();
    }


}
