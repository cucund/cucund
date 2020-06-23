package com.cucund.project.rabbit.client.exception;

import org.apache.commons.lang3.StringUtils;

public class Assert {

    public static void isBlank(String data,String msg) {
        if(StringUtils.isBlank(data))
            throw new MQException(msg);
    }


    public static void isNull(Object data,String msg) {
        if(data == null)
            throw new MQException(msg);
    }

}
