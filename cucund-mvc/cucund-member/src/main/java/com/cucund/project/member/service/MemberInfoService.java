package com.cucund.project.member.service;

import com.cucund.project.member.dto.member.MemberModifyReq;
import com.cucund.project.member.dto.register.UserRegisterReq;
import com.cucund.project.member.entity.MemberInfoDat;

import java.util.Map;

public interface MemberInfoService {

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    MemberInfoDat findMemberByName(String username);

    /**
     * 新增 用户
     * @param registerReq
     * @return
     */
    MemberInfoDat insert(UserRegisterReq registerReq);

    /**
     * 更新 用户信息
     * @param req
     */
    void update(MemberModifyReq req);

    Map<String,Object> getData(Map<String,Object> map);
}
