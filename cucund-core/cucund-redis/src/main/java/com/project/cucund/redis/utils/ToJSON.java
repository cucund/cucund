package com.project.cucund.redis.utils;

import com.cucund.project.tool.utils.exception.SystemError;
import com.cucund.project.tool.utils.json.JSONUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class ToJSON {

    public static <T> T to(String json,Class<T> clazz){
        if(StringUtils.isBlank(json))
            return null;
        try {
            return (T) JSONUtils.json2pojo(json,clazz);
        } catch (Exception e) {
            log.error(json,e);
            SystemError.throwException("json解析异常");
            return null;
        }
    }
}
