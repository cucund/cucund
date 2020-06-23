package com.cucund.project.member.config;


import com.cucund.project.spring.ob.InvokeClass;
import com.cucund.project.tool.utils.lock.KeyLock;
import com.cucund.project.tool.utils.lock.NativeLock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.concurrent.TimeUnit;

@Configuration
public class BeanConfig {

    @Bean
    public KeyLock keyLock(){
        return new NativeLock(5, TimeUnit.SECONDS);
    }

    @Bean
    @DependsOn("springUtil")
    public InvokeClass invokeClazz() throws ClassNotFoundException {
        return new InvokeClass("invokeClass","invoke",new Class[]{Integer.class});
    }
}
