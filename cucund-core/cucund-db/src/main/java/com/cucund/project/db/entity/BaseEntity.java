package com.cucund.project.db.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class BaseEntity {

    @TableId("id")
    private Integer id;

    private String  createdUser;

    private String  updatedUser;

    private Date    createdDate;

    private Date    updatedDate;

    private boolean del;

}
