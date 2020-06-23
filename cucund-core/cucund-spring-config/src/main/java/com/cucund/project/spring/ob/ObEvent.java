package com.cucund.project.spring.ob;

import lombok.Data;


@Data
public class ObEvent {

    private InvokeClass invokeClass;

    private Object[] params;

    public ObEvent(InvokeClass invokeClass,Object... params){
        this.invokeClass = invokeClass;
        this.params = params;
    }



}
