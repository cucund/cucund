package com.project.cucund.redis.utils;

import com.cucund.project.tool.utils.exception.SystemError;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;

public class ToBytes {

    public static byte[] to(String str,String charSet){
        try {
            if(StringUtils.isBlank(str))
                SystemError.throwException("字符转换异常");
            return str.getBytes(charSet);
        } catch (UnsupportedEncodingException e) {
            SystemError.throwException(e.getMessage());
            return null;
        }
    }

}
