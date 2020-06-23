package com.cucund.project.third.strategy.postage;

import com.cucund.project.third.entity.PostageDat;
import com.cucund.project.tool.utils.strategy.StrategyModel;

public class DefaultCostStrategy implements PostageStrategy {
    @Override
    public boolean condition(PostageDat postageDat) {
        return true;
    }

    @Override
    public Boolean doSomething(PostageDat postageDat) {
        return true;
    }
}
