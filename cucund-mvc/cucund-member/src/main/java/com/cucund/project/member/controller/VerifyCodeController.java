package com.cucund.project.member.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/open/verify/code")
public class VerifyCodeController {

    @GetMapping("send")
    public void sendVerifyCode(){
        //验证码生成策略读取 并生成验证码

        //短信发送服务
    }

}
