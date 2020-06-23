package com.cucund.project.mq.reader.store;

import com.cucund.project.mq.reader.enumc.RabbitMQEnum;
import com.cucund.project.rabbit.client.factory.RabbitMQ;
import lombok.Data;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class RabbitMQProperty {

    private RabbitMQEnum type;

    private RabbitMQBean property;

}


@Getter
class RabbitMQBean{

    private Map<String,String> map = new HashMap<>();

    public RabbitMQBean(Map<String,String> map ){
        this.map.putAll(map);
    }

}
