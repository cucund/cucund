package com.cucund.project.member.entity;


import com.cucund.project.db.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

@Data
public class MemberInfoDat extends BaseEntity {

    private String username;

    private String password;

    private String nickname;

    private String virtualHead;

    private Integer age;

    private Integer gender;

    private Date birthday;

}
