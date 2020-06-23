package com.cucund.project.member.service;

import com.cucund.project.member.dto.register.UserRegisterReq;
import com.cucund.project.member.entity.MemberInfoDat;

public interface RegisterMemberService {

    MemberInfoDat registerMember(UserRegisterReq registerReq);

}
