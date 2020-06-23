package com.cucund.project.db.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @see org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration
 */
@Configuration
@MapperScan("com.cucund.project.**.dao")
public class MybatisConfig {



  @Bean
  public PaginationInterceptor paginationInterceptor() {
    PaginationInterceptor page = new PaginationInterceptor();
    // 设置方言
    page.setDialectType("mysql");
    return page;
  }

}