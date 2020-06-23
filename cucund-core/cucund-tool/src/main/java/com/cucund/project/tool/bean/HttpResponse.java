package com.cucund.project.tool.bean;


import lombok.Data;

@Data
public class HttpResponse {

    private Integer code;

    private String message;

    private boolean success;

    private Object data;

}
