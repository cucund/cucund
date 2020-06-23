package com.cucund.project.mq.bean;


import com.cucund.project.mq.inf.Connection;

public abstract class MQFactory {

    protected ServerBean serverBean;

    public MQFactory(ServerBean serverBean){
        this.serverBean = serverBean;
    }

}
