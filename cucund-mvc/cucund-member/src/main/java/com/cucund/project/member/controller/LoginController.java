package com.cucund.project.member.controller;


import com.cucund.project.member.dto.login.UserLoginReq;
import com.cucund.project.member.dto.login.UserLoginResp;
import com.cucund.project.member.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/open/user")
@Slf4j
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping("/login")
    public UserLoginResp login(@RequestBody UserLoginReq req){
        return loginService.login(req);
    }


}
