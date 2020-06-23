package com.cucund.project.test.config;

import com.cucund.project.spring.ob.InvokeClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class BeanConfig {


    @Bean
    @DependsOn("springUtil")
    public InvokeClass invokeClazz() throws ClassNotFoundException {
        return new InvokeClass("invokeClass","invoke",new Class[]{Integer.class});
    }

}
