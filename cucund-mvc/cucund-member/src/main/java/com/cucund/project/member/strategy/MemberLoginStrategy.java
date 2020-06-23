package com.cucund.project.member.strategy;

import com.cucund.project.member.dto.login.UserLoginReq;
import com.cucund.project.member.dto.login.UserLoginResp;
import com.cucund.project.tool.utils.strategy.StrategyModel;

public interface MemberLoginStrategy extends StrategyModel<UserLoginReq, UserLoginResp> {
}
