package com.cucund.project.rabbit.client.bean;

import com.cucund.project.mq.inf.Closeable;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ChannelStore implements Closeable {

    protected Store store = null;

    public ChannelStore(Connection connection) throws IOException {
        store = new Store(connection);
    }

    public Channel getChannel() {
        return store.getChannel();
    }

    @Override
    public void close() {
        store.close();
    }
}

class Store implements Closeable {

    private Channel channel = null;

    private boolean isClose = false;

    public Store(Connection connection) throws IOException {
        this.channel = connection.createChannel();
    }

    public Channel getChannel() {
        return channel;
    }

    @Override
    public void close() {
        try {
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }finally {
            channel = null;
            isClose = true;
        }
    }
}
