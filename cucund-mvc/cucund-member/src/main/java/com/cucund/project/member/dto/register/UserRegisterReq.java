package com.cucund.project.member.dto.register;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserRegisterReq {

    @NotBlank(message="用户名为空")
    private String username;

    private String password;

    private String type;

}
