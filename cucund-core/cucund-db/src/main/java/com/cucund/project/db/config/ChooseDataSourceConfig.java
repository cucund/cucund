package com.cucund.project.db.config;


import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.cucund.project.db.dynamic.ChooseDataSource;
import com.cucund.project.db.dynamic.DataSourceAspect;
import com.cucund.project.db.entity.ChooseSource;
import com.cucund.project.db.entity.DataSourceInner;
import com.cucund.project.db.properties.ChooseDataSourceProperties;
import com.cucund.project.db.properties.DataSourceProperties;
import com.cucund.project.db.properties.DruidDataSourceProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 数据源切换工程配置
 * 但微服务化以后 数据源切换好像也没啥作用  完全可以用mycat代替
 * 如果要用这个方法请再打开
 * @see com.cucund.project.db.aop.DataSourceAspect
 */
//@Configuration
//@EnableConfigurationProperties({ChooseDataSourceProperties.class})
public class ChooseDataSourceConfig {


    @Autowired
    ChooseDataSourceProperties chooseDataSourceProperties;


    @Bean
    public DataSourceAspect DataSourceAspect(){
        return new DataSourceAspect();
    }

    @Bean
    public ChooseDataSource chooseDataSource(){
        List<DataSourceProperties> dynamic = chooseDataSourceProperties.getDynamic();
        if(dynamic == null||dynamic.isEmpty())
            throw new RuntimeException("无数据源选择");
        Map<Object,Object> map = new HashMap<Object,Object>();
        for (DataSourceProperties properties:dynamic) {
            map.put(properties.getDatasourceName(),druidDataSource(properties));
        }

        ChooseDataSource dataSource = new ChooseDataSource();
        dataSource.setDefaultTargetDataSource(map.get(dynamic.get(0).getDatasourceName()));
        dataSource.setTargetDataSources(map);
        return dataSource;
    }

    @Bean
    public ChooseSource chooseSource(){
        List<DataSourceInner> dataSourceList = new ArrayList<>();

        for (DataSourceProperties properties:chooseDataSourceProperties.getDynamic()) {
            String datasourceName = properties.getDatasourceName();
            dataSourceList.add(new DataSourceInner(datasourceName,properties.getDatasourceType()));
        }

        ChooseSource chooseSource = new ChooseSource();
        chooseSource.setDataSources(dataSourceList);
        chooseSource.setModel(chooseDataSourceProperties.getModel());
        return chooseSource;
    }

    public DataSource druidDataSource(DataSourceProperties properties) {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(properties.getDriverClassName());
        druidDataSource.setUrl(properties.getUrl());
        druidDataSource.setUsername(properties.getUsername());
        druidDataSource.setPassword(properties.getPassword());
        druidDataSource.setInitialSize(properties.getInitialSize());
        druidDataSource.setMinIdle(properties.getMinIdle());
        druidDataSource.setMaxActive(properties.getMaxActive());
        druidDataSource.setMaxWait(properties.getMaxWait());
        druidDataSource.setTimeBetweenEvictionRunsMillis(properties.getTimeBetweenEvictionRunsMillis());
        druidDataSource.setMinEvictableIdleTimeMillis(properties.getMinEvictableIdleTimeMillis());
        druidDataSource.setValidationQuery(properties.getValidationQuery());
        druidDataSource.setTestWhileIdle(properties.isTestWhileIdle());
        druidDataSource.setTestOnBorrow(properties.isTestOnBorrow());
        druidDataSource.setTestOnReturn(properties.isTestOnReturn());
        druidDataSource.setPoolPreparedStatements(properties.isPoolPreparedStatements());
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(properties.getMaxPoolPreparedStatementPerConnectionSize());

        try {
            druidDataSource.setFilters(properties.getFilters());
            druidDataSource.init();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return druidDataSource;
    }


    /**
     * 注册Servlet信息， 配置监控视图
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public ServletRegistrationBean<Servlet> druidServlet() {
        ServletRegistrationBean<Servlet> servletRegistrationBean = new ServletRegistrationBean<Servlet>(new StatViewServlet(), "/druid/*");

        //白名单：
        servletRegistrationBean.addInitParameter("allow","127.0.0.1");
        //IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
        servletRegistrationBean.addInitParameter("deny","192.168.1.119");
        //登录查看信息的账号密码, 用于登录Druid监控后台
        servletRegistrationBean.addInitParameter("loginUsername", "admin");
        servletRegistrationBean.addInitParameter("loginPassword", "admin");
        //是否能够重置数据.
        servletRegistrationBean.addInitParameter("resetEnable", "true");
        return servletRegistrationBean;

    }

    /**
     * 注册Filter信息, 监控拦截器
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public FilterRegistrationBean<Filter> filterRegistrationBean() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<Filter>();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }

}
