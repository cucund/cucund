package com.cucund.project.third.entity;


import com.cucund.project.db.entity.BaseEntity;
import lombok.Data;

@Data
public class PartyPathDat extends BaseEntity {

    /**
     * 路由ID
     */
    private String routerId;

    /**
     * GET,POST
     */
    private String method;

    /**
     * 地址
     */
    private String path;

    /**
     * URL 参数json
     */
    private String paramJson;

    /**
     * 头部 参数json
     */
    private String headerJson;

    /**
     * form 参数json
     */
    private String formJson;

    /**
     * HTTP 消息发送类型
     */
    private String contentType;
}
