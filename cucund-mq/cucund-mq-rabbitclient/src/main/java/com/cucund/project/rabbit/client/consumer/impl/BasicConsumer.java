package com.cucund.project.rabbit.client.consumer.impl;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

public interface BasicConsumer {

    public void execute(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, String body, Channel channel) throws IOException;

}
