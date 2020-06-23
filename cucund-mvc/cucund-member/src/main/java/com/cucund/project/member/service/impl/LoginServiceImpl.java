package com.cucund.project.member.service.impl;

import com.cucund.project.member.dto.login.UserLoginReq;
import com.cucund.project.member.dto.login.UserLoginResp;
import com.cucund.project.member.entity.MemberInfoDat;
import com.cucund.project.member.service.LoginService;
import com.cucund.project.member.service.MemberInfoService;
import com.cucund.project.member.strategy.MemberLoginStrategy;
import com.cucund.project.member.strategy.MemberUsernameStrategy;
import com.cucund.project.member.strategy.MemberVerCodeStrategy;
import com.cucund.project.tool.utils.strategy.StrategyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    MemberInfoService memberInfoService;

    private StrategyUtil<UserLoginReq, UserLoginResp> utils = new StrategyUtil<>();

    @PostConstruct
    public void init() {
        utils.add(new MemberUsernameStrategy());
        utils.add(new MemberVerCodeStrategy());
    }

    @Override
    public UserLoginResp login(UserLoginReq req) {
        return utils.loop(req);
    }

}
