package com.cucund.project.tool.utils.exception;

import com.cucund.project.tool.bean.HttpStatus;

public class SystemError {

    public static void throwException(HttpStatus code, String message){
        throw new SystemException(code,message);
    }


    public static void throwException(String message){
        throw new SystemException(HttpStatus.H501,message);
    }



}
