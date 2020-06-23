package com.cucund.project.third.strategy.resolve;

import com.cucund.project.third.dto.ResolveDto;

public class JSONResolveStrategy implements ResolveStrategy {
    @Override
    public boolean condition(ResolveDto resolveDto) {
        return true;
    }

    @Override
    public String doSomething(ResolveDto resolveDto) {
        return resolveDto.getBody();
    }
}
