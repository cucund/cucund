package com.cucund.project.rabbit.client.bean;

import com.cucund.project.mq.bean.MQFactory;
import com.cucund.project.mq.bean.ServerBean;
import com.rabbitmq.client.ConnectionFactory;
import lombok.Data;
import lombok.Getter;


@Getter
public class RabbitMQFactory extends MQFactory {

    private ConnectionFactory connectionFactory;

    public RabbitMQFactory(RabbitServerBean serverBean) {
        super(serverBean);
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(serverBean.getHost());
        connectionFactory.setUsername(serverBean.getUsername());
        connectionFactory.setPassword(serverBean.getPassword());
        connectionFactory.setPort(serverBean.getPort());
        connectionFactory.setVirtualHost(serverBean.getVirtualHost());
    }


}
