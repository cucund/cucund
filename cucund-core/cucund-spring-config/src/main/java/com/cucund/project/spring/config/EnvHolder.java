package com.cucund.project.spring.config;


import com.cucund.project.tool.utils.spring.SpringUtil;
import lombok.Data;

@Data
public class EnvHolder {

    private String env;

    private String serviceName;

    public static EnvHolder getInstance(){
        return SpringUtil.getBean(EnvHolder.class);
    }

}
