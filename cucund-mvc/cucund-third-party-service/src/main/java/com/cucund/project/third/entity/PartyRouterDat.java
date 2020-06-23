package com.cucund.project.third.entity;


import com.cucund.project.db.entity.BaseEntity;
import lombok.Data;

@Data
public class PartyRouterDat extends BaseEntity {

    /**
     * 第三方应用名称
     */
    private String partyName;

    /**
     * 第三方系统内代号
     */
    private String partyCode;

    /**
     * 第三方系统沙盒环境域名
     */
    private String sandboxDomain;

    /**
     * 第三方系统正式环境域名
     */
    private String prodDomain;

}

