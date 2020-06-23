package com.cucund.project.member.dto.login;

import lombok.Data;

@Data
public class UserLoginReq {

    private String username;

    private String password;

    private String type;

}
