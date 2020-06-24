package com.project.cucund.redis.redisson;

import com.cucund.project.tool.utils.exception.SystemError;
import com.cucund.project.tool.utils.spring.SpringUtil;
import com.project.cucund.redis.LockRedis;
import com.project.cucund.redis.Redis;
import com.project.cucund.redis.redisson.enumc.LockType;
import org.redisson.RedissonMultiLock;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.redisson.spring.data.connection.RedissonConnectionFactory;

public class LockRedissonImpl extends Redis implements LockRedis {

    public LockRedissonImpl(RedissonConnectionFactory factory, String charSet) {
        super(factory, charSet);
    }

    @Override
    public RLock getLock(String key, LockType lockType) {
        return redis.doExecute(connection -> {
            RedissonClient client = SpringUtil.getBean(RedissonClient.class);
            RLock lock = null;
            switch (lockType){
                case NORMAL:lock = client.getLock(key);break;
                case FAIR:lock = client.getFairLock(key);break;
                case MULTI:lock = new RedissonMultiLock(client.getLock(key));break;
                case RED:lock = new RedissonRedLock(client.getLock(key));break;
                case READ_WRITE:SystemError.throwException("这个方法无法生成读写锁.. 请使用getReadWriteLock");break;
                default:;
            }
            return lock;
        });
    }

    @Override
    public RReadWriteLock getReadWriteLock(String key) {
        return redis.doExecute(connection -> {
            RedissonClient client = SpringUtil.getBean(RedissonClient.class);
            RReadWriteLock lock = client.getReadWriteLock(key);
            return lock;
        });
    }

    @Override
    public RLock getLock(String key) {
        return this.getLock(key,LockType.NORMAL);
    }
}
