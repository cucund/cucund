package com.cucund.project.admin.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SysUser extends BaseModel {

    private String name;

    private String password;

    private String salt;

    private String email;

    private String mobile;

    private Byte status;

    private Long deptId;
    
    private String deptName;
    
    private Byte delFlag;
    
    private String roleNames;
    
    private List<SysUserRole> userRoles = new ArrayList<>();


}