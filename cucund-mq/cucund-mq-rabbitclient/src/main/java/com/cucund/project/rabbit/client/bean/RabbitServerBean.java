package com.cucund.project.rabbit.client.bean;

import com.cucund.project.mq.bean.ServerBean;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RabbitServerBean extends ServerBean {

    private String virtualHost;

}
