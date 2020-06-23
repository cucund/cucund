package com.cucund.project.member.strategy;

import com.cucund.project.member.dto.login.UserLoginReq;
import com.cucund.project.member.dto.login.UserLoginResp;
import com.cucund.project.member.entity.MemberInfoDat;
import com.cucund.project.member.service.MemberInfoService;
import com.cucund.project.tool.utils.exception.SystemError;
import com.cucund.project.tool.utils.spring.SpringUtil;
import com.cucund.project.tool.utils.strategy.StrategyModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * 正常 用户名登录
 */
public class MemberUsernameStrategy implements MemberLoginStrategy {
    @Override
    public boolean condition(UserLoginReq o) {
        String type = o.getType();
        if("0".equals(type))
            return true;
        return false;
    }

    @Override
    public UserLoginResp doSomething(UserLoginReq req) {
        MemberInfoService memberInfoService = SpringUtil.getBean(MemberInfoService.class);
        MemberInfoDat dat = memberInfoService.findMemberByName(req.getUsername());
        if(dat==null){
            SystemError.throwException("用户未注册");
        }else if(StringUtils.isBlank(dat.getPassword())){
            SystemError.throwException("用户未设置密码,无法使用账密登录功能,请去设置密码");
        }else{
            if(!isMatches(req, dat))
                SystemError.throwException("用户密码输入错误");
        }
        UserLoginResp loginResp = new UserLoginResp();
        BeanUtils.copyProperties(dat,loginResp);
        return loginResp;
    }

    /**
     * 密码是否匹配
     * @param req
     * @param dat
     * @return
     */
    private boolean isMatches(UserLoginReq req, MemberInfoDat dat) {
        String password = req.getPassword();
        String passwordDb = dat.getPassword();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(passwordDb, password);
    }
}
