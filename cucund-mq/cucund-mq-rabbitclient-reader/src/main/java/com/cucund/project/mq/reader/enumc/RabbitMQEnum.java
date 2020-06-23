package com.cucund.project.mq.reader.enumc;

public enum RabbitMQEnum {

    EXCHANGE("EXCHANGE"),
    SERVER("SERVER"),
    QUEUE("QUEUE")
    ;

    private String msg;

    private RabbitMQEnum(String msg){
        this.msg = msg;
    }



}
