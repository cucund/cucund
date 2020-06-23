package com.cucund.project.mq.bean;

import com.cucund.project.mq.inf.Closeable;
import lombok.Getter;

@Getter
public abstract class MQConnection implements Closeable {

    protected boolean isClose = false;

}
