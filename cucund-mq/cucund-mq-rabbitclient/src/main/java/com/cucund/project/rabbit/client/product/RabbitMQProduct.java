package com.cucund.project.rabbit.client.product;

import com.cucund.project.rabbit.client.exception.Assert;
import com.cucund.project.rabbit.client.bean.ChannelStore;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Connection;

import java.io.IOException;

public class RabbitMQProduct extends ChannelStore {


    private String charset;

    /**
     *
     * @param exchange
     * @param routingKey
     * @param mandatory 当mandatory标志位设置为true时，如果exchange根据自身类型和消息routeKey无法找到一个符合条件的queue，那么会调用basic.return方法将消息返回给生产者（Basic.Return + Content-Header + Content-Body）；当mandatory设置为false时，出现上述情形broker会直接将消息扔掉。
     * @param immediate 当immediate标志位设置为true时，如果exchange在将消息路由到queue(s)时发现对于的queue上么有消费者，那么这条消息不会放入队列中。当与消息routeKey关联的所有queue（一个或者多个）都没有消费者时，该消息会通过basic.return方法返还给生产者。
     *                     3.0版本后  immediate被禁用 immediate标记会影响镜像队列性能，增加代码复杂性，并建议采用“TTL”和“DLX”等方式替代。
     * @param props
     * @param body
     * @throws IOException
     */
    public void publish(String exchange, String routingKey, boolean mandatory, boolean immediate, AMQP.BasicProperties props, byte[] body) throws IOException {
        Assert.isNull(body,"推送内容为空");
        getChannel().basicPublish(exchange, routingKey, mandatory, immediate, props, body);
    }

    /**
     *
     * @param exchange
     * @param routingKey
     * @param mandatory 当mandatory标志位设置为true时，如果exchange根据自身类型和消息routeKey无法找到一个符合条件的queue，那么会调用basic.return方法将消息返回给生产者（Basic.Return + Content-Header + Content-Body）；当mandatory设置为false时，出现上述情形broker会直接将消息扔掉。
     * @param props
     * @param body
     * immediate 禁用
     * @throws IOException
     */
    public void publish(String exchange, String routingKey, boolean mandatory,  AMQP.BasicProperties props, byte[] body) throws IOException {
        Assert.isNull(body,"推送内容为空");
        getChannel().basicPublish(exchange, routingKey, mandatory, false, props, body);
    }


    /**
     *
     * @param exchange
     * @param routingKey
     * @param props
     * @param msg
     * immediate 禁用
     * @throws IOException
     */
    public void publish(String exchange, String routingKey,  AMQP.BasicProperties props, String msg) throws IOException {
        Assert.isBlank(msg,"推送内容为空");
        getChannel().basicPublish(exchange, routingKey, false, false, props, msg.getBytes(charset));
    }


    /**
     *
     * @param exchange
     * @param routingKey
     * @param msg
     * immediate 禁用
     * @throws IOException
     */
    public void publish(String exchange, String routingKey,  String msg) throws IOException {
        Assert.isBlank(msg,"推送内容为空");
        AMQP.BasicProperties basicProperties = new AMQP.BasicProperties.Builder()
                .deliveryMode(2)
                .contentEncoding(charset)
                .build();
        getChannel().basicPublish(exchange, routingKey, false, false, basicProperties , msg.getBytes(charset));
    }


    public RabbitMQProduct(Connection connection) throws IOException {
        this(connection,"UTF-8");
    }

    public RabbitMQProduct(Connection connection,String charset) throws IOException {
        super(connection);
        this.charset = charset;
    }
}
