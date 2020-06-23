package com.cucund.project.mq.bean;


import lombok.Data;

@Data
public class ServerBean {

    private String serverName;

    private String host;

    private int port;

    private String username;

    private String password;

}
