package com.cucund.project.tool.utils.exception;

import com.cucund.project.tool.bean.HttpStatus;
import lombok.Getter;

@Getter
public class SystemException extends RuntimeException{

    private HttpStatus code;

    public SystemException(HttpStatus code,String message){
        super(message);
        this.code = code;
    }

}