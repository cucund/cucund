package com.cucund.project.netty.handler;

import com.cucund.project.tool.utils.spring.SpringUtil;
import io.netty.channel.ChannelHandler.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import javax.swing.*;

@Slf4j
@Sharable
public class NettyServerMessageHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        String clazzName = null;
        try {
            clazzName = msg.substring(0,msg.indexOf(":"));
        }catch (Exception e){
            log.error("未成功解析 客户端参数");
        }
        String data = null;
        try {
            data = msg.substring(msg.indexOf(":")+1, msg.length());
        }catch (Exception e){
            log.error("未成功解析 客户端参数");
        }
        try {
            Handler handler = SpringUtil.getBean(clazzName, Handler.class);
            handler.doWork(ctx,data);
        }catch (NoSuchBeanDefinitionException e){
            log.error("未找到Bean实体");
        }
    }
}
