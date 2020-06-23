package com.cucund.project.rabbit.client.queue;

import com.cucund.project.mq.inf.Closeable;
import com.cucund.project.rabbit.client.bean.ChannelStore;
import com.cucund.project.rabbit.client.exchange.RabbitMQExchange;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class RabbitMQQueue extends ChannelStore {


    ChannelStore cs = null;

    private boolean noWait = false;

    public RabbitMQQueue(Connection connection) throws IOException {
        super(connection);
    }

    public RabbitMQQueue noWait(boolean noWait){
        this.noWait = noWait;
        return this;
    }

    /**
     * 声明队列
     * @param queue
     * @param durable
     * @param exclusive
     * @param autoDelete
     * @param arguments
     * @return
     * @throws IOException
     */
    public AMQP.Queue.DeclareOk declareQueue(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments) throws IOException {
        if(!noWait){
            AMQP.Queue.DeclareOk declareOk = getChannel().queueDeclare(queue, durable, exclusive, autoDelete, arguments);
            return declareOk;
        }else{
            getChannel().queueDeclareNoWait(queue, durable, exclusive, autoDelete, arguments);
            return null;
        }
    }

    /**
     * 队列是否为空
     * @param queue
     * @return
     */
    public AMQP.Queue.DeclareOk queueDeclarePassive(String queue){
        AMQP.Queue.DeclareOk declareOk = null;
        try {
            declareOk = getChannel().queueDeclarePassive(queue);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return declareOk;
    }

    /**
     * 队列绑定
     * @param queue
     * @param exchange
     * @param routingKey
     * @param arguments
     * @return
     * @throws IOException
     */
    public AMQP.Queue.BindOk queueBind(String queue, String exchange, String routingKey,Map<String, Object> arguments) throws IOException {
        if(!noWait){
            AMQP.Queue.BindOk bindOk = getChannel().queueBind(queue, exchange, routingKey,arguments);
            return bindOk;
        }else{
            getChannel().queueBindNoWait(queue, exchange, routingKey,arguments);
            return null;
        }
    }

    /**
     * 删除队列
     * @param queue
     * @param ifUnused
     * @param ifEmpty
     * @return
     * @throws IOException
     */
    public AMQP.Queue.DeleteOk queueDelete(String queue, boolean ifUnused, boolean ifEmpty) throws IOException {
        if(!noWait){
            AMQP.Queue.DeleteOk deleteOk = getChannel().queueDelete(queue, ifUnused, ifEmpty);
            return deleteOk;
        }else{
            getChannel().queueDeleteNoWait(queue, ifUnused, ifEmpty);
            return null;
        }
    }

    /**
     * 解绑队列
     * @param queue
     * @param exchange
     * @param routingKey
     * @param arguments
     * @return
     * @throws IOException
     */
    public AMQP.Queue.UnbindOk queueUnbind(String queue, String exchange, String routingKey,Map<String, Object> arguments) throws IOException {
        AMQP.Queue.UnbindOk unbindOk = getChannel().queueUnbind(queue, exchange, routingKey, arguments);
        return unbindOk;
    }

    /**
     * 清除队列
     * @param queue
     * @return
     * @throws IOException
     */
    public AMQP.Queue.PurgeOk queuePurge(String queue) throws IOException {
        AMQP.Queue.PurgeOk purgeOk = getChannel().queuePurge(queue);
        return purgeOk;
    }






}
