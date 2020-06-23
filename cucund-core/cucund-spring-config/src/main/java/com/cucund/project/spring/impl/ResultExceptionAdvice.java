package com.cucund.project.spring.impl;

import com.cucund.project.tool.bean.HttpResponse;
import com.cucund.project.tool.bean.HttpStatus;
import com.cucund.project.tool.utils.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @see org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod#invokeAndHandle(ServletWebRequest, ModelAndViewContainer, Object...)
 */
@RestControllerAdvice
@Slf4j
public class ResultExceptionAdvice {

        /**
         * 全局异常处理
         */
        @ExceptionHandler
        public HttpResponse handleException(HttpServletRequest request, HttpServletResponse response, final Exception ex) {
            HttpResponse resp = new HttpResponse();
            if (ex instanceof SystemException) {
                HttpStatus code = ((SystemException) ex).getCode();
                String message = ex.getMessage();               //错误信息
                resp.setCode(code.getCode());
                resp.setMessage(message);
            } else {
                String message = ex.getMessage();               //错误信息
                resp.setCode(HttpStatus.H500.getCode());
                resp.setMessage(HttpStatus.H500.getDesc());
                log.error("打印系统错误信息", ex);
            }
            resp.setSuccess(false);
            return resp;
        }

}
