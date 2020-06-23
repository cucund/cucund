package com.cucund.project.mq;

import com.cucund.project.mq.bean.MQConnection;
import com.cucund.project.mq.bean.MQFactory;
import com.cucund.project.mq.inf.Closeable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Factory implements Closeable {


    protected Map<String, MQFactory> factoryMap = new ConcurrentHashMap<String,MQFactory>();

    protected Map<String, MQConnection> connectionMap = new ConcurrentHashMap<String,MQConnection>();


}
