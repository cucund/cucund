package com.cucund.project.member.service;

import com.cucund.project.member.dto.login.UserLoginReq;
import com.cucund.project.member.dto.login.UserLoginResp;

public interface LoginService {

    public UserLoginResp login(UserLoginReq req);


}
