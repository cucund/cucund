package com.cucund.project.member.controller;


import com.cucund.project.member.service.MemberInfoService;
import com.cucund.project.spring.ob.InvokeClass;
import com.cucund.project.spring.ob.ObEvent;
import com.cucund.project.spring.ob.Observer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Map;

@RestController
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TestController {


    public TestController(){
      log.info("TestController 初始化");
    }

    @Autowired
    @Qualifier("invokeClazz")
    private InvokeClass invokeClass ;

    @Autowired
    MemberInfoService memberInfoService;

    @GetMapping("test")
    public Object data(){
        ObEvent ob = new ObEvent(invokeClass,1);
        Observer.publishEvent(ob);
        return "";
    }

    @GetMapping("read")
    public Object read( @RequestParam Map<String,Object> map ){
        return memberInfoService.getData(map);
    }

    @PostConstruct
    public void init() throws ClassNotFoundException {
        log.info("TestController PostConstruct 方法执行");
    }
}
