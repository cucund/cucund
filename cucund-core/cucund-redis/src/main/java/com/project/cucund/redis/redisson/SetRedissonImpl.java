package com.project.cucund.redis.redisson;

import com.project.cucund.redis.SetRedis;
import com.project.cucund.redis.Redis;
import org.redisson.spring.data.connection.RedissonConnectionFactory;

public class SetRedissonImpl extends Redis implements SetRedis {
    public SetRedissonImpl(RedissonConnectionFactory factory, String charSet) {
        super(factory,charSet);
    }
}
