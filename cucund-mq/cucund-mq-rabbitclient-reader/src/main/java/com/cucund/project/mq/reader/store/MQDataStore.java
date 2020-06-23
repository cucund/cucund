package com.cucund.project.mq.reader.store;

import com.cucund.project.rabbit.client.factory.RabbitMQ;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MQDataStore {

    private Map<String,RabbitMQProperty> map = new HashMap<String,RabbitMQProperty>();

    private RabbitMQ rabbitMQ ;

    public void clear(){
        stop();
    }

    public void stop(){
        if(rabbitMQ!= null)
            rabbitMQ.close();
    }

    public void refresh(){
        //启动前准备

        //启动前验证

        //启动

        //启动后准备

    }
}
