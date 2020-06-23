package com.cucund.project.spring.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class EnvConfig {

    public EnvConfig(){
        log.info("EnvConfig 初始化");
    }

    @Bean
    public EnvHolder envHolder(){
        return new EnvHolder();
    }

}
