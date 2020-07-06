package com.cucund.project.spring.impl;

import com.cucund.project.tool.bean.HttpResponse;
import com.cucund.project.tool.bean.HttpStatus;
import com.cucund.project.tool.utils.json.JSONUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.ArrayList;
import java.util.List;


/**
 * RequestResponseBodyAdviceChain#processBody(Object, MethodParameter, MediaType, Class, ServerHttpRequest, ServerHttpResponse)
 */
@RestControllerAdvice
public class ResultResponseAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    static List<MediaType> list = new ArrayList<>();
    static {
        list.add(MediaType.APPLICATION_JSON);
        list.add(MediaType.APPLICATION_JSON_UTF8);
        list.add(MediaType.APPLICATION_XML);
        list.add(MediaType.TEXT_HTML);
        list.add(MediaType.APPLICATION_XHTML_XML);
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if(!list.contains(mediaType)){
            return null;
        }
        Object data = null;
        if(o == null){
            data = null;
        }else if(o instanceof HttpResponse) {
            return o;
        }else if(o instanceof byte[]){
            return o;
        }else{
            try {
                data = JSONUtils.obj2json(o);  //基础类型也可以正确转换
            } catch (Exception e) {
            }
        }
        HttpResponse resp = new HttpResponse();         //
        resp.setData(data);
        resp.setMessage(HttpStatus.H200.getDesc());
        resp.setCode(HttpStatus.H200.getCode());
        resp.setSuccess(true);
        if(o instanceof String ){
            try {
                return JSONUtils.obj2json(resp);
            } catch (Exception e) {
            }
        }
        return resp;
    }
}
