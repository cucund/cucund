# cucund
###cucund: 
##### 丨-- cucund-db   数据库封装包
##### 丨-- cucund-log  日志封装包
##### 丨-- cucund-netty
##### 丨-- cucund-redis
##### 丨-- cucund-spring-config  spring常用 配置
##### 丨-- cucund-tool 常用工具类
  
####  cucund-db:
    实现功能 :多数据源切换(没啥用), AOP实现
            sql存储数据仓库,mybatis注解 @*(增删改查)Provider实现
            基础配置
####  cucund-log:
    实现功能 :规整jar
####  cucund-netty:
     实现功能:   快速启动 netty服务器,进行简易封装
    starter:    NettyServer
        参照:    cucund-test 下 NettyController
####  cucund-redis
    实现功能:    简易封装 Redis工具
      工具类:    RedisUtil
####  cucund-spring-config
    实现功能:    简易封装spring常用配置
####  cucund-tool
    实现功能:     简易封装各种常用工具类