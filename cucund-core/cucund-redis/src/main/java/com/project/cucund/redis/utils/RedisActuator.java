package com.project.cucund.redis.utils;

import com.cucund.project.tool.utils.exception.SystemError;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.data.redis.connection.RedisConnection;

public class RedisActuator {

    private RedissonConnectionFactory factory;


    public RedisActuator(RedissonConnectionFactory factory) {
        this.factory = factory;
    }

    public <T> T doExecute(RedisTemplate<T> redisTemplate){
        RedisConnection connection = null;
        try{
            T execute = redisTemplate.execute(connection = factory.getConnection());
            return execute;
        }catch (Exception e){
            e.printStackTrace();
            SystemError.throwException(e.getMessage());
        }finally {
            connection.close();
        }
        return null;
    }
}
