package com.cucund.project.third.strategy.postage;

import com.cucund.project.third.entity.PostageDat;

public class UnlimitedStrategy implements PostageStrategy{
    @Override
    public boolean condition(PostageDat postageDat) {
        return postageDat.isUnlimited();
    }

    @Override
    public Boolean doSomething(PostageDat postageDat) {
        return true;
    }
}
