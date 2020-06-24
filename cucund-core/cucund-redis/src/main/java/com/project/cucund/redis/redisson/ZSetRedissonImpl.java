package com.project.cucund.redis.redisson;

import com.project.cucund.redis.Redis;
import com.project.cucund.redis.ZSetRedis;
import org.redisson.spring.data.connection.RedissonConnectionFactory;

public class ZSetRedissonImpl extends Redis implements ZSetRedis {


    protected ZSetRedissonImpl(RedissonConnectionFactory factory, String charSet) {
        super(factory, charSet);
    }


}
