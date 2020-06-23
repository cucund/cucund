package com.cucund.project.tool.utils.strategy;



public interface StrategyModel<T,R> {

    public boolean condition(T t);

    public R doSomething(T t);

}
