package com.cucund.project.tool.utils.strategy;

import java.util.ArrayList;
import java.util.List;

public class StrategyUtil<T,R> {

    public StrategyUtil(){}

    public StrategyUtil(boolean debug){this.debug = debug;}

    private List<StrategyModel<T,R>> strategyModelList = new ArrayList<>();

    private boolean debug = false;

    public StrategyUtil add(StrategyModel<T,R> strategyModel){
        strategyModelList.add(strategyModel);
        return this;
    }

    public StrategyUtil addAll(List<StrategyModel<T,R>> strategyModelList){
        strategyModelList.addAll(strategyModelList);
        return this;
    }

    public R loop(T t){
        if (strategyModelList.isEmpty())
            return null;
        for (StrategyModel<T,R> strategyModel:strategyModelList) {
            boolean condition = false;
            try {
                condition = strategyModel.condition(t);
            }catch (Exception e){
                if(debug) {
                    e.printStackTrace();
                }
            }
            if(condition){
                return strategyModel.doSomething(t);
            }
        }
        return null;
    }


}
