package com.project.cucund.redis.utils;

import com.cucund.project.tool.utils.exception.SystemError;

import java.io.UnsupportedEncodingException;

public class ToString {

    public static String to(byte[] bytes,String charSet){
        try {
            if(bytes==null)
                return null;
            return new String(bytes,charSet);
        } catch (UnsupportedEncodingException e) {
            SystemError.throwException(e.getMessage());
            return null;
        }
    }

}
