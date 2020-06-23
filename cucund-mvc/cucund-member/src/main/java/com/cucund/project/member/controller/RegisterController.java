package com.cucund.project.member.controller;

import com.cucund.project.member.dto.login.UserLoginReq;
import com.cucund.project.member.dto.register.UserRegisterReq;
import com.cucund.project.member.entity.MemberInfoDat;
import com.cucund.project.member.service.RegisterMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/open/user")
public class RegisterController {
    @Autowired
    RegisterMemberService registerMemberService;

    @PostMapping("register")
    public void register(@RequestBody UserRegisterReq req){
        MemberInfoDat dat = registerMemberService.registerMember(req);
        //推送至  新用户注册MQ
        sendMessageForRegisterSuccess(dat);
    }

    private void sendMessageForRegisterSuccess(MemberInfoDat dat) {

    }

}
