package com.cucund.project.rabbit.client.exchange;

import com.cucund.project.mq.inf.Closeable;
import com.cucund.project.rabbit.client.bean.ChannelStore;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class RabbitMQExchange extends ChannelStore {

    private boolean noWait = false;

    public RabbitMQExchange(Connection connection) throws IOException {
        super(connection);
    }

    public RabbitMQExchange noWait(boolean noWait){
        this.noWait = noWait;
        return this;
    }


    /**
     * 声明 交换器
     * @param exchange      交换机名
     * @param type          交换机类型 事例:TOPIC
     * @param durable       是否持久化
     * @param autoDelete    是否自动删除
     * @param internal      是否是内部交换机
     * @param arguments     交换机属性
     * @throws IOException
     */
    public AMQP.Exchange.DeclareOk exchangeDeclare(String exchange, BuiltinExchangeType type, boolean durable, boolean autoDelete, boolean internal, Map<String, Object> arguments) throws IOException {
        if(!noWait){
            AMQP.Exchange.DeclareOk declareOk = getChannel().exchangeDeclare(exchange, type, durable, autoDelete, internal, arguments);
            return declareOk;
        }else{
            getChannel().exchangeDeclareNoWait(exchange, type, durable, autoDelete, internal, arguments);
            return null;
        }
    }

    /**
     * 交换机是否为空
     * @param exchange
     * @return
     */
    public AMQP.Exchange.DeclareOk exchangeDeclarePassive(String exchange){
        try {
            AMQP.Exchange.DeclareOk declareOk = getChannel().exchangeDeclarePassive(exchange);
            return declareOk;
        } catch (IOException e) {
        }
        return null;
    }

    /**
     * 交换机绑定
     * @param destination 队列名
     * @param source      绑定交换机名
     * @param routingKey  路由KEY
     * @param arguments   参数
     * @return
     * @throws IOException
     */
    public AMQP.Exchange.BindOk exchangeBind(String destination, String source, String routingKey, Map<String, Object> arguments) throws IOException {
        if(!noWait){
            AMQP.Exchange.BindOk bindOk = getChannel().exchangeBind(destination, source, routingKey, arguments);
            return bindOk;
        }else{
            getChannel().exchangeBindNoWait(destination, source, routingKey, arguments);
            return null;
        }
    }

    /**
     * 交换机删除
     * @param exchange
     * @param ifUnused
     * @return
     * @throws IOException
     */
    public AMQP.Exchange.DeleteOk exchangeDelete(String exchange,boolean ifUnused)throws IOException{
        if (!noWait){
            AMQP.Exchange.DeleteOk deleteOk = getChannel().exchangeDelete(exchange, ifUnused);
            return deleteOk;
        }else{
            getChannel().exchangeDeleteNoWait(exchange, ifUnused);
            return null;
        }
    }


    /**
     * 交换机解绑
     * @param destination
     * @param source
     * @param routingKey
     * @param arguments
     * @return
     * @throws IOException
     */
    public AMQP.Exchange.UnbindOk exchangeUnbind(String destination, String source, String routingKey, Map<String, Object> arguments) throws IOException {
        if(!noWait){
            AMQP.Exchange.UnbindOk unbindOk = getChannel().exchangeUnbind(destination, source, routingKey, arguments);
            return unbindOk;
        }else{
            getChannel().exchangeUnbindNoWait(destination, source, routingKey, arguments);
            return null;
        }
    }





}

