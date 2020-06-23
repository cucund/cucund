package com.cucund.project.spring.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

@Configuration
public class Config extends WebMvcConfigurationSupport {


    /**
     * 重置 StringHttpMessageConverter
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        addDefaultHttpMessageConverters(converters);
        StringHttpMessageConverter converterTemp = null;
        for (HttpMessageConverter converter:converters) {
            if (converter instanceof StringHttpMessageConverter)
                converterTemp = (StringHttpMessageConverter) converter;
        }
//        converters.remove(converterTemp);
        converterTemp.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON));
        converterTemp.setDefaultCharset(Charset.forName("UTF-8"));
        converterTemp.setWriteAcceptCharset(false);
    }

}
