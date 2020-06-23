package com.cucund.project.tool.utils.exception;

import com.cucund.project.tool.bean.HttpStatus;

public class SQLUtils {

    public static void check(int i,String message) {
        if(i<=0)
            SystemError.throwException(HttpStatus.H510,message);
    }
}
