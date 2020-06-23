package com.cucund.project.rabbit.test;


import com.cucund.project.mq.bean.MQConnection;
import com.cucund.project.rabbit.client.bean.RabbitMQConnection;
import com.cucund.project.rabbit.client.bean.RabbitServerBean;
import com.cucund.project.rabbit.client.consumer.RabbitMQConsumer;
import com.cucund.project.rabbit.client.consumer.impl.BasicConsumer;
import com.cucund.project.rabbit.client.exchange.RabbitMQExchange;
import com.cucund.project.rabbit.client.factory.RabbitMQ;
import com.cucund.project.rabbit.client.queue.RabbitMQQueue;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;

public class MainTest {

//    public static void main(String[] args) throws IOException {
//        RabbitServerBean serverBean = new RabbitServerBean();
//        serverBean.setHost("192.168.1.139");
//        serverBean.setPort(5672);
//        serverBean.setUsername("root");
//        serverBean.setPassword("root@kz365");
//        serverBean.setVirtualHost("/");
//        RabbitMQ rabbitMQ= RabbitMQ.build();
//        rabbitMQ.add(serverBean);
//        RabbitMQConnection connection = (RabbitMQConnection) rabbitMQ.getConnection();
//        RabbitMQExchange exchange = connection.getExchange();
//        RabbitMQQueue queue = connection.getQueue();
//        AMQP.Exchange.DeclareOk declareOk = exchange.exchangeDeclare("cucund.exchange", BuiltinExchangeType.      , true, false, false, new HashMap<>());
//        System.out.println(declareOk);
//        AMQP.Queue.DeclareOk declareOk1 = queue.declareQueue("cucund.queue", true, false, false, new HashMap<>());
//        System.out.println(declareOk1);
//        AMQP.Queue.BindOk bindOk = queue.queueBind("cucund.queue", "cucund.exchange", "cucund", new HashMap<>());
//        System.out.println(bindOk);
////        exchange.close();
////        queue.close();
//        connection.close();
//    }

    public static void main(String[] args) throws IOException, InterruptedException {
        RabbitServerBean serverBean = new RabbitServerBean();
        serverBean.setHost("192.168.1.139");
        serverBean.setPort(5672);
        serverBean.setUsername("root");
        serverBean.setPassword("root@kz365");
        serverBean.setVirtualHost("/");
        RabbitMQ rabbitMQ= RabbitMQ.build();
        rabbitMQ.add(serverBean);
        RabbitMQConnection connection = (RabbitMQConnection) rabbitMQ.getConnection();

        BasicConsumer b = new BasicConsumer() {
            @Override
            public void execute(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, String body, Channel channel) throws IOException {
                System.out.println(consumerTag);
                System.out.println(envelope.toString());
                System.out.println(properties.toString());
                System.out.println("由消费者1打印");
                System.out.println("消息内容:" + body);
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        BasicConsumer b1 = new BasicConsumer() {
            @Override
            public void execute(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, String body, Channel channel) throws IOException {
                System.out.println(consumerTag);
                System.out.println(envelope.toString());
                System.out.println(properties.toString());
                System.out.println("由消费者2打印");
                System.out.println("消息内容:" + body);
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        RabbitMQConsumer consumer = connection.getConsumer();
        consumer.push("cucund.queue",false,"456",false,false,null,b1);//2
        Thread.sleep(10);
        consumer.push("cucund.queue",false,"123",false,false,null,b);//1
//        connection.getProduct().publish("cucund.exchange","cucund","XXXXXXXXXXXXXXXXX");



    }
}
