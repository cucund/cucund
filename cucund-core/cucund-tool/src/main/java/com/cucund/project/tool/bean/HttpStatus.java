package com.cucund.project.tool.bean;

import lombok.Getter;

@Getter
public enum HttpStatus {

    H500(500,"系统错误"),
    H501(501,"参数错误"),
    H510(510,"数据更新失败"),



    H404(404,"服务找不到"),
    H401(401,"未授权"),
    H200(200,"请求成功")
    ;

    private Integer code;

    private String desc;

    HttpStatus(Integer code,String desc){
        this.code = code;
        this.desc = desc;
    }
}
