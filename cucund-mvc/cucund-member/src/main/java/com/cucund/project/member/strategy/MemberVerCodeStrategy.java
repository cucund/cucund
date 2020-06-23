package com.cucund.project.member.strategy;

import com.cucund.project.member.dto.login.UserLoginReq;
import com.cucund.project.member.dto.login.UserLoginResp;
import com.cucund.project.member.dto.register.UserRegisterReq;
import com.cucund.project.member.entity.MemberInfoDat;
import com.cucund.project.member.service.MemberInfoService;
import com.cucund.project.member.service.RegisterMemberService;
import com.cucund.project.tool.utils.exception.SystemError;
import com.cucund.project.tool.utils.spring.SpringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.rmi.registry.Registry;


/**
 * 正常 用户名登录
 */
public class MemberVerCodeStrategy implements MemberLoginStrategy {
    @Override
    public boolean condition(UserLoginReq o) {
        String type = o.getType();
        if("0".equals(type))
            return true;
        return false;
    }

    @Override
    public UserLoginResp doSomething(UserLoginReq req) {
        //PASSWORD字段验证
       if(StringUtils.isBlank(req.getPassword()))
           SystemError.throwException("请输入验证码");
        //是否继续
        MemberInfoService memberInfoService = SpringUtil.getBean(MemberInfoService.class);
        MemberInfoDat dat = memberInfoService.findMemberByName(req.getUsername());
        if(dat == null){
            // 自动注册逻辑
            RegisterMemberService registerMemberService = MemberRegisterServiceStore.get();
            UserRegisterReq registerReq = new UserRegisterReq();
            BeanUtils.copyProperties(req,registerReq);
            dat = registerMemberService.registerMember(registerReq);
        }
        UserLoginResp loginResp = new UserLoginResp();
        BeanUtils.copyProperties(dat,loginResp);
        return loginResp;
    }
}


class MemberRegisterServiceStore {

    static RegisterMemberService registerMemberService ;

    private MemberRegisterServiceStore(){}

    public static RegisterMemberService get(){
        if(registerMemberService==null){
            synchronized (registerMemberService){
                if(registerMemberService == null){
                    registerMemberService = SpringUtil.getBean(RegisterMemberService.class);
                }
            }
        }
        return registerMemberService;
    }

}