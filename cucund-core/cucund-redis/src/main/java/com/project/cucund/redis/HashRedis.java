package com.project.cucund.redis;

import java.util.Map;

public interface HashRedis {

    public Map<String,String> hGet(String key);

    public String hGet(String key,String hKey);

    public Boolean hSet(String key, String hKey, String value);

}
