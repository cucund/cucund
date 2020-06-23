package com.cucund.project.rabbit.client.factory;

import com.cucund.project.mq.Factory;
import com.cucund.project.mq.bean.MQConnection;
import com.cucund.project.mq.bean.MQFactory;
import com.cucund.project.mq.inf.Connection;
import com.cucund.project.rabbit.client.bean.RabbitMQConnection;
import com.cucund.project.rabbit.client.bean.RabbitMQFactory;
import com.cucund.project.rabbit.client.bean.RabbitServerBean;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class RabbitMQ extends Factory implements Connection{



    public static RabbitMQ build(){
        return new RabbitMQ();
    }

    /**
     *
     */
    public void add(RabbitServerBean serverBean){
        MQFactory factory = createFactory(serverBean);
        String serverName = serverBean.getServerName();
        factoryMap.put(serverName,factory);
    }

    public void addAll(List<RabbitServerBean> serverBeans){
        Map<String, MQFactory> factoryMapTemp = new HashMap<String,MQFactory>();
        for (RabbitServerBean serverBean: serverBeans) {
            MQFactory factory = createFactory(serverBean);
            String serverName = serverBean.getServerName();
            factoryMapTemp.put(serverName,factory);
        }
        factoryMap.putAll(factoryMapTemp);
    }

    private MQFactory createFactory(RabbitServerBean serverBean) {
        MQFactory mqFactory = new RabbitMQFactory(serverBean);
        String serverName = serverBean.getServerName();
        if(StringUtils.isBlank(serverName)){
            MQFactory factory = factoryMap.get(defaultName);
            if(factory == null)
                serverBean.setServerName(defaultName);
            else
                throw new RuntimeException("重复使用默认名称");
        }
        return mqFactory;
    }



    @Override
    public MQConnection getConnection(String name) {
        MQConnection connection = getMQConnection(name);
        return connection;
    }

    @Override
    public MQConnection getConnection() {
        MQConnection connection = getMQConnection(defaultName);
        return connection;
    }

    private MQConnection getMQConnection(String serverName) {
        MQConnection connection = connectionMap.get(serverName);
        if (connection == null||connection.isClose()) {
            MQFactory cf = factoryMap.get(serverName);
            connection = new RabbitMQConnection(cf);
            connectionMap.put(serverName, connection);
        }
        return connection;
    }

    @Override
    public void close() {
        Iterator<Map.Entry<String, MQConnection>> it = connectionMap.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String, MQConnection> next = it.next();
            String key = next.getKey();
            MQConnection mqConnection = next.getValue();
            mqConnection.close();
            it.remove();
            factoryMap.remove(key);
        }
    }
}
