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
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RabbitMQDelayQueueTest {

    /**
     * 正常队列
     * @throws IOException
     */
    @Test
    public void setListenerQueue() throws IOException {
        RabbitServerBean serverBean = new RabbitServerBean();
        serverBean.setHost("192.168.1.139");
        serverBean.setPort(5672);
        serverBean.setUsername("root");
        serverBean.setPassword("root");
        serverBean.setVirtualHost("/");
        RabbitMQ rabbitMQ= RabbitMQ.build();
        rabbitMQ.add(serverBean);
        RabbitMQConnection connection = (RabbitMQConnection) rabbitMQ.getConnection();
        RabbitMQExchange exchange = connection.getExchange();
        RabbitMQQueue queue = connection.getQueue();
        AMQP.Exchange.DeclareOk declareOk = exchange.exchangeDeclare("cucund.exchange", BuiltinExchangeType.DIRECT, true, false, false, new HashMap<>());
        System.out.println(declareOk);
        AMQP.Queue.DeclareOk declareOk1 = queue.declareQueue("cucund.queue", true, false, false, new HashMap<>());
        System.out.println(declareOk1);
        AMQP.Queue.BindOk bindOk = queue.queueBind("cucund.queue", "cucund.exchange", "cucund", new HashMap<>());
        System.out.println(bindOk);
//        exchange.close();
//        queue.close();
        AMQP.BasicProperties build = new AMQP.BasicProperties.Builder().expiration("60000").build();
        connection.getProduct().publish("cucund.exchange","cucund",build,System.currentTimeMillis()+"");
        connection.close();
    }

    /**
     * 延时队列
     * @throws IOException
     */
    @Test
    public void test() throws IOException {
        RabbitServerBean serverBean = new RabbitServerBean();
        serverBean.setHost("192.168.1.139");
        serverBean.setPort(5672);
        serverBean.setUsername("root");
        serverBean.setPassword("root");
        serverBean.setVirtualHost("/");
        RabbitMQ rabbitMQ= RabbitMQ.build();
        rabbitMQ.add(serverBean);
        RabbitMQConnection connection = (RabbitMQConnection) rabbitMQ.getConnection();
        RabbitMQExchange exchange = connection.getExchange();
        RabbitMQQueue queue = connection.getQueue();
        AMQP.Exchange.DeclareOk declareOk = exchange.exchangeDeclare("cucund.exchange.dead", BuiltinExchangeType.TOPIC, true, false, false, new HashMap<>());
        System.out.println(declareOk);

        Map<String, Object> args = new HashMap<>(3);
        // x-dead-letter-exchange    这里声明当前队列绑定的死信交换机
        args.put("x-dead-letter-exchange", "cucund.exchange");
        // x-dead-letter-routing-key  这里声明当前队列的死信路由key
        args.put("x-dead-letter-routing-key", "cucund");
        AMQP.Queue.DeclareOk declareOk1 = queue.declareQueue("cucund.queue.dead", true, false, false, args);
        System.out.println(declareOk1);
        AMQP.Queue.BindOk bindOk = queue.queueBind("cucund.queue.dead", "cucund.exchange.dead", "cucund.dead", new HashMap<>());
        System.out.println(bindOk);
//        exchange.close();
//        queue.close();
        AMQP.BasicProperties build = new AMQP.BasicProperties.Builder().expiration("60000").build();
        connection.getProduct().publish("cucund.exchange.dead","cucund.dead",build,System.currentTimeMillis()+"");
        connection.close();
    }

    /**
     * 监听队列
     * @param args
     * @throws IOException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        RabbitServerBean serverBean = new RabbitServerBean();
        serverBean.setHost("192.168.1.139");
        serverBean.setPort(5672);
        serverBean.setUsername("root");
        serverBean.setPassword("root");
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
                System.out.println("延迟打印时间:"+(System.currentTimeMillis()-Long.valueOf(body)));
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        RabbitMQConsumer consumer = connection.getConsumer();
        consumer.push("cucund.queue",false,"消费者",false,false,null,b);//1



    }
}
