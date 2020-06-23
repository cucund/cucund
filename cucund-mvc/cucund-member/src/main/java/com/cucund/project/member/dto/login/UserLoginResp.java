package com.cucund.project.member.dto.login;

import lombok.Data;

@Data
public class UserLoginResp {

    private String token;

    private String username;

    private String nickname;

    private String virtualHead;

}
