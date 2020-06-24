package com.project.cucund.redis;

import java.util.concurrent.TimeUnit;

public interface StringRedis {

    public String get(String key);

    public Boolean set(String key, String value);

    public Boolean set(String key, String value, long time, TimeUnit unit);

}
