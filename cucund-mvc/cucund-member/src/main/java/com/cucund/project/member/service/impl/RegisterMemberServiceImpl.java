package com.cucund.project.member.service.impl;

import com.cucund.project.member.dto.register.UserRegisterReq;
import com.cucund.project.member.entity.MemberInfoDat;
import com.cucund.project.member.service.MemberInfoService;
import com.cucund.project.member.service.RegisterMemberService;
import com.cucund.project.tool.bean.HttpStatus;
import com.cucund.project.tool.utils.exception.SystemError;
import com.cucund.project.tool.utils.lock.KeyLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class RegisterMemberServiceImpl implements RegisterMemberService {

    @Autowired
    MemberInfoService memberInfoService;

    @Autowired
    private KeyLock keyLock;

    @Override
    public MemberInfoDat registerMember(UserRegisterReq registerReq) {
        //加锁
        insertLock(registerReq);

        //用户数据入库
        MemberInfoDat dat = memberInfoService.insert(registerReq);
        //移除密码
        dat.setPassword(null);
        
        //解锁
        insertUnLock(registerReq);

        return dat;
    }

    private void insertLock(UserRegisterReq registerReq) {
        try {
            keyLock.tryLock(registerReq.getUsername());
        } catch (InterruptedException e) {
            SystemError.throwException(HttpStatus.H510,"超时获取锁");
        }
    }

    private void insertUnLock(UserRegisterReq registerReq) {
        keyLock.unlock(registerReq.getUsername());
    }
}
