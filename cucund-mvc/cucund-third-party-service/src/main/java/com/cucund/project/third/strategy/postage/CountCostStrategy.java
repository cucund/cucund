package com.cucund.project.third.strategy.postage;

import com.cucund.project.third.entity.PostageDat;
import com.cucund.project.third.service.PostageService;

import java.math.BigDecimal;

public class CountCostStrategy implements PostageStrategy {
    @Override
    public boolean condition(PostageDat postageDat) {
        String type = postageDat.getType();
        if("1".equals(type))
            return true;
        return false;
    }

    @Override
    public Boolean doSomething(PostageDat postageDat) {
        if (postageDat.getMinCost()==null)
            return true;
        BigDecimal minCost = postageDat.getMinCost();
        int i = minCost.compareTo(postageDat.getCost());
        return (i <= 0);
    }
}
