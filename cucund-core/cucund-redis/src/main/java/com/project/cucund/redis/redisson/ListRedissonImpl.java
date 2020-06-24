package com.project.cucund.redis.redisson;

import com.project.cucund.redis.ListRedis;
import com.project.cucund.redis.Redis;
import org.redisson.spring.data.connection.RedissonConnectionFactory;

public class ListRedissonImpl extends Redis implements ListRedis {


    public ListRedissonImpl(RedissonConnectionFactory factory, String charSet) {
        super(factory,charSet);
    }


}
