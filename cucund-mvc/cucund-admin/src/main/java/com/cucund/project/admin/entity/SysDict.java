package com.cucund.project.admin.entity;

import lombok.Data;

@Data
public class SysDict extends BaseModel {

    private String value;

    private String label;

    private String type;

    private String description;

    private Long sort;

    private String remarks;

    private Byte delFlag;

}