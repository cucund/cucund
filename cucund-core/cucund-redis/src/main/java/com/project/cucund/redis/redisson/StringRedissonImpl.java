package com.project.cucund.redis.redisson;

import com.project.cucund.redis.Redis;
import com.project.cucund.redis.StringRedis;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.types.Expiration;

import java.util.concurrent.TimeUnit;

public class StringRedissonImpl extends Redis implements StringRedis {

    public StringRedissonImpl(RedissonConnectionFactory factory, String charSet) {
        super(factory, charSet);
    }

    @Override
    public String get(String key){
        return redis.doExecute((connection) -> {
            byte[] bytes = connection.get(toBytes(key));
            return toStr(bytes);
        });
    }

    @Override
    public Boolean set(String key, String value) {
        return redis.doExecute((connection) -> {
            return connection.set(toBytes(key),toBytes(value));
        });
    }

    @Override
    public Boolean set(String key, String value, long time, TimeUnit unit) {
        return redis.doExecute((connection) -> {
            Boolean flag = connection.set(toBytes(key),toBytes(value), Expiration.from(time,unit), RedisStringCommands.SetOption.UPSERT);
            return flag;
        });
    }

}
