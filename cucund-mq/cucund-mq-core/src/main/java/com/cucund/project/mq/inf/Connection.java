package com.cucund.project.mq.inf;

import com.cucund.project.mq.bean.MQConnection;

public interface Connection {

    static final String defaultName = "DEFAULT";

    public MQConnection getConnection(String name);

    public MQConnection getConnection();

}
