package com.cucund.project.test.invoke;


import org.springframework.stereotype.Component;

@Component("invokeClass")
public class InvokeClass {


    public String invoke(String param){
        return param;
    }

    public String invoke(Integer param){
        return ""+param;
    }
}
