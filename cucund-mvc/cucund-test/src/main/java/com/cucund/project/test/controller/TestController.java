package com.cucund.project.test.controller;

import com.cucund.project.spring.ob.InvokeClass;
import com.cucund.project.spring.ob.ObEvent;
import com.cucund.project.spring.ob.Observer;
import com.cucund.project.test.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Map;

@RestController
@Slf4j
public class TestController {

    public TestController(){
        log.info("TestController 初始化");
    }

    @Autowired
    TestService testService;


    @Autowired
    @Qualifier("invokeClazz")
    private InvokeClass invokeClass ;

    @GetMapping("test")
    public Object data(){
        ObEvent ob = new ObEvent(invokeClass,1);
        Observer.publishEvent(ob);
        return "";
    }



    @GetMapping("read")
    public Map<String,Object> read(@RequestParam Map<String,Object> map ){
        return testService.getData(map);
    }

    @PostConstruct
    public void init() throws ClassNotFoundException {
        log.info("TestController PostConstruct 方法执行");
    }
}
