package com.cucund.project.test.controller;


import com.cucund.project.netty.ConnectionMap;
import com.cucund.project.netty.handler.NettyServerHandler;
import com.cucund.project.netty.init.NettyServerInitializer;
import com.cucund.project.netty.init.WebSocketInitializer;
import com.cucund.project.netty.server.NettyServer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/netty")
@Slf4j
public class NettyController {

    NettyServer nettyServer;

    @PostConstruct
    public void init(){
        String start = start();
        log.info(start);
    }

    @GetMapping("start")
    public String start(){
        if(nettyServer==null){
//            NettyServerInitializer initializer = new NettyServerInitializer();//socket
            WebSocketInitializer initializer = new WebSocketInitializer();//web socket
            nettyServer = new NettyServer(2010,initializer);
            nettyServer.init();
            return "Netty服务启动成功";
        }
        return "Netty服务正在运行中";
    }

    @GetMapping("destroy")
    public String destroy(){
        if(nettyServer != null){
            nettyServer.destroy();
            nettyServer = null;
        }
        return "销毁成功";
    }

    @GetMapping("sendMessage")
    public String sendMessage(String userId,String msg){
        ChannelHandlerContext ctx = ConnectionMap.getChannel(userId);
        ctx.channel().writeAndFlush(new TextWebSocketFrame(msg));
        return "ok";
    }

}

