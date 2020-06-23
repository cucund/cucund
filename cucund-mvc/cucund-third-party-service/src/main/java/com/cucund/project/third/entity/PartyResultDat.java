package com.cucund.project.third.entity;


import com.cucund.project.db.entity.BaseEntity;
import lombok.Data;

@Data
public class PartyResultDat extends BaseEntity {

    private String name;

    private String key;

    private String example;

    private String parentId;

    private String contentType;

}
