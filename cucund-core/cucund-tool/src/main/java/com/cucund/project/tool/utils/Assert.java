package com.cucund.project.tool.utils;

import com.cucund.project.tool.utils.exception.SystemError;

public class Assert {
    public static void isTrue(boolean expression,String msg ){
        if(!expression)
            SystemError.throwException(msg);
    }
    public static void isFalse(boolean expression,String msg ){
        if(expression)
            SystemError.throwException(msg);
    }

    public static void isNull(Object data,String msg) {
        if(data == null)
            SystemError.throwException(msg);
    }
}
