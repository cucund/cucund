package com.cucund.project.rabbit.client.product;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.impl.AMQBasicProperties;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DefaultProperties {


    public static AMQP.BasicProperties get(){
        return new AMQP.BasicProperties.Builder()
                .contentEncoding("UTF-8")
                .deliveryMode(2)
                .headers(new HashMap<>())
                .build();
    }

    public static AMQP.BasicProperties get(int expiration, Map<String,Object> map){
        return new AMQP.BasicProperties.Builder()
                .contentEncoding("UTF-8")
                .deliveryMode(2)
                .expiration(expiration+"")
                .headers(map)
                .build();
    }
    public static AMQP.BasicProperties get(int deliveryMode,int expiration, Map<String,Object> map){
        return new AMQP.BasicProperties.Builder()
                .contentEncoding("UTF-8")
                .deliveryMode(2)
                .expiration(expiration+"")
                .headers(map)
                .build();
    }
    /**
     * BasicProperties.Builder propsBuilder = new BasicProperties.Builder();
     * propsBuilder
     *     .appId(???)       // 产生消息的应用程序的表示符  比如你的计算机名称
     * .clusterId(???)    //
     * .contentEncoding(???) // 编码格式
     * .contentType(???)  //消息内容类型
     * .correlationId(???) //关联id 关于此消息 可用于标识客户端之间的的特定id
     * .deliveryMode(2) //是否持久化属性
     * .expiration(???) //消息过期时间
     * .headers(???) //特定消息头
     * .messageId(???) //消息表示符 用于标示消息
     * .priority(???) //消息优先级
     * .timestamp(???) //消息发送的时间戳
     * .type(???) //消息类型，例如该消息代表什么类型的事件或命令
     * .userId(???); //通过RabbitMQ针对实际连接的用户名
     * •replyTo：一般用来命名一个回调queue。
     * •correlationId：用来关联RPC的请求和响应。
     * @return
     */
    public static AMQP.BasicProperties get(String contentType, String contentEncoding, Map<String, Object> headers, Integer deliveryMode, Integer priority, String correlationId, String replyTo, String expiration, String messageId, Date timestamp, String type, String userId, String appId, String clusterId){
        return new AMQP.BasicProperties(contentType, contentEncoding, headers, deliveryMode, priority, correlationId, replyTo, expiration, messageId, timestamp, type, userId, appId, clusterId);
    }
}
