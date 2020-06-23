package com.cucund.project.rabbit.client.bean;

import com.cucund.project.mq.bean.MQConnection;
import com.cucund.project.mq.bean.MQFactory;
import com.cucund.project.rabbit.client.consumer.RabbitMQConsumer;
import com.cucund.project.rabbit.client.exchange.RabbitMQExchange;
import com.cucund.project.rabbit.client.product.RabbitMQProduct;
import com.cucund.project.rabbit.client.queue.RabbitMQQueue;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.Getter;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


@Getter
public class RabbitMQConnection extends MQConnection {

    private volatile Connection connection = null;

    public RabbitMQConnection(MQFactory cf) {
        super();
        try {
            connection = ((RabbitMQFactory)cf).getConnectionFactory().newConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    public RabbitMQExchange getExchange() throws IOException {
        return new RabbitMQExchange(connection);
    }
    public RabbitMQConsumer getConsumer() throws IOException {
        return new RabbitMQConsumer(connection);
    }
    public RabbitMQProduct getProduct() throws IOException {
        return new RabbitMQProduct(connection);
    }
    public RabbitMQQueue getQueue() throws IOException {
        return new RabbitMQQueue(connection);
    }







    @Override
    public void close() {
        try {
            connection.close();
        } catch (IOException e) {
        }finally {
            connection = null;
            isClose = true;
        }
    }

}
