package com.project.cucund.redis.utils;


import com.cucund.project.tool.utils.spring.SpringUtil;
import com.project.cucund.redis.*;
import com.project.cucund.redis.redisson.*;
import org.redisson.spring.data.connection.RedissonConnectionFactory;

public class RedisUtil {

    private static StringRedis stringRedis;

    private static HashRedis hashRedis;

    private static ListRedis listRedis;

    private static SetRedis setRedis;

    private static LockRedis lockRedis;

    static{
        RedissonConnectionFactory factory = SpringUtil.getBean(RedissonConnectionFactory.class);

        stringRedis = new StringRedissonImpl(factory,"UTF-8");

        hashRedis = new HashRedissonImpl(factory,"UTF-8");

        listRedis = new ListRedissonImpl(factory,"UTF-8");

        setRedis = new SetRedissonImpl(factory,"UTF-8");

        lockRedis = new LockRedissonImpl(factory,"UTF-8");

    }

}
