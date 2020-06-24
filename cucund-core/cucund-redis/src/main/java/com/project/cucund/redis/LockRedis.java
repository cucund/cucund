package com.project.cucund.redis;

import com.project.cucund.redis.redisson.enumc.LockType;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;

public interface LockRedis {

    RLock getLock(String key, LockType lockType);

    RReadWriteLock getReadWriteLock(String key);

    public RLock getLock(String key);

}
