package com.cucund.project.member.controller;


import com.cucund.project.member.dto.member.MemberModifyReq;
import com.cucund.project.member.service.MemberInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member/modify")
@Slf4j
public class MemberDataModifyController {

    public MemberDataModifyController(){
      log.info("MemberDataModifyController 初始化");
    }

    @Autowired
    MemberInfoService memberInfoService;

    @PostMapping("info")
    public void modifyMember(@RequestBody MemberModifyReq req){
        memberInfoService.update(req);
    }

}
