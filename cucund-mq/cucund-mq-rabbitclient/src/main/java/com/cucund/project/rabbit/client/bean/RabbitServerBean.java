package com.cucund.project.rabbit.client.bean;

import com.cucund.project.mq.bean.ServerBean;
import com.sun.istack.internal.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RabbitServerBean extends ServerBean {

    private String virtualHost;

}
