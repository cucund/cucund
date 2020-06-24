package com.project.cucund.redis.redisson;

import com.project.cucund.redis.HashRedis;
import com.project.cucund.redis.Redis;
import org.redisson.spring.data.connection.RedissonConnectionFactory;

import java.util.HashMap;
import java.util.Map;

public class HashRedissonImpl extends Redis implements HashRedis  {


    public HashRedissonImpl(RedissonConnectionFactory factory, String charSet) {
        super(factory, charSet);
    }

    @Override
    public String hGet(String key,String hKey) {
        return redis.doExecute(((connection) -> {
            byte[] bytes = connection.hGet(toBytes(key),toBytes(hKey));
            return toStr(bytes);
        }));
    }

    @Override
    public Boolean hSet(String key, String hKey, String value) {
        return redis.doExecute(((connection) -> {
            return connection.hSet(toBytes(key),toBytes(hKey),toBytes(value));
        }));
    }


    @Override
    public Map<String,String> hGet(String key) {
        return redis.doExecute(((connection) -> {
            Map<byte[], byte[]> map = connection.hGetAll(toBytes(key));
            Map<String,String> map2 = new HashMap<>();
            for (byte[] bK:map.keySet()) {
                map2.put(toStr(bK),toStr(map.get(bK)));
            }
            return map2;
        }));
    }
}
