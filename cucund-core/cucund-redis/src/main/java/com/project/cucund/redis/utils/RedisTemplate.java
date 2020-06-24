package com.project.cucund.redis.utils;

import org.springframework.data.redis.connection.RedisConnection;

public interface RedisTemplate<T> {

    public T execute(RedisConnection connection);

}
