package com.cucund.project.rabbit.client.consumer;

import com.cucund.project.mq.inf.Closeable;
import com.cucund.project.rabbit.client.bean.ChannelStore;
import com.cucund.project.rabbit.client.consumer.impl.BasicConsumer;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class RabbitMQConsumer extends ChannelStore {


    /**
     * 推模式
     * @param queue
     * @param autoAck
     * @param consumerTag
     * @param noLocal       如果服务器不应向此消费者发送此通道连接上发布的消息，则设置为true
     * @param exclusive     排他队列
     * @param arguments
     * @param callback
     * @return
     * @throws IOException
     */
    public String push(String queue, final boolean autoAck, String consumerTag, boolean noLocal, boolean exclusive, Map<String, Object> arguments, final BasicConsumer callback) throws IOException {
        Channel channel = getChannel();
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                callback.execute(consumerTag,envelope,properties,new String(body),channel);
                if(!autoAck){
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            }
        };
        return channel.basicConsume(queue, autoAck, consumerTag, noLocal, exclusive, arguments, defaultConsumer);
    }

    /**
     * 拉模式
     * @param queue
     * @param autoAck
     * @return
     * @throws IOException
     */
    public GetResponse pull(String queue, final boolean autoAck) throws IOException {
        return getChannel().basicGet(queue, autoAck);
    }

    /**
     * 限流
     * @param //prefetchSize  0
     * @param prefetchCount 会告诉RabbitMQ不要同时给一个消费者推送多于N个消息，即一旦有N个消息还没有ack，则该consumer将block掉，直到有消息ack
     * @param //global        true\false 是否将上面设置应用于channel，简单点说，就是上面限制是channel级别的还是consumer级别
     * @throws IOException
     */
    public void qos( int prefetchCount) throws IOException {
        getChannel().basicQos(0, prefetchCount, false);
    }

    public RabbitMQConsumer(Connection connection) throws IOException {
        super(connection);
    }

}
