package com.cucund.project.spring.ob;

import com.cucund.project.tool.utils.exception.SystemError;
import com.cucund.project.tool.utils.spring.SpringUtil;
import org.springframework.context.ApplicationListener;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;

@Component
public class ObListener implements ApplicationListener<PayloadApplicationEvent> {

    @Override
    public void onApplicationEvent(PayloadApplicationEvent ev) {
        if(ev.getPayload() instanceof ObEvent){
            ObEvent ob = (ObEvent)ev.getPayload();
            InvokeClass ic = ob.getInvokeClass();
            Object bean = SpringUtil.getBean(ic.getClassName());
            if(bean == null)
                SystemError.throwException("类获取失败:"+ic.getClassName());
            try {
                ic.getMethodObj().invoke(SpringUtil.getBean(ic.getClassName()),ob.getParams());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
