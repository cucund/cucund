package com.cucund.project.third.strategy.postage;

import com.cucund.project.third.entity.PostageDat;

import java.math.BigDecimal;

public class CostStrategy implements PostageStrategy{
    @Override
    public boolean condition(PostageDat postageDat) {
        String type = postageDat.getType();
        if("2".equals(type))
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
