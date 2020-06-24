package com.project.cucund.redis;

import com.cucund.project.tool.utils.spring.SpringUtil;
import com.project.cucund.redis.utils.RedisActuator;
import com.project.cucund.redis.utils.ToBytes;
import com.project.cucund.redis.utils.ToString;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.data.redis.core.types.Expiration;

import java.util.concurrent.TimeUnit;

public abstract class Redis {

    protected RedisActuator redis ;

    protected String charSet;

    protected Redis(RedissonConnectionFactory factory ,String charSet){
        redis = new RedisActuator(factory);
        charSet = "UTF-8";
    }

    public Boolean expire(String key,long time, TimeUnit unit) {
        return redis.doExecute(((connection) -> {
            return connection.expire(toBytes(key), Expiration.from(time,unit).getExpirationTimeInSeconds());
        }));
    }

    protected byte[] toBytes(String str){
        return ToBytes.to(str,charSet);
    }

    protected String toStr(byte[] bytes){
        return ToString.to(bytes,charSet);
    }
}
