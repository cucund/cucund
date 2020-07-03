package com.cucund.project.netty.handler;

import com.cucund.project.netty.ConnectionMap;
import io.netty.channel.*;
import io.netty.channel.ChannelHandler.Sharable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Sharable
public class NettyServerHandler extends ChannelInboundHandlerAdapter /*ChannelHandlerAdapter*/{


    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        log.info(ctx.channel().remoteAddress() + " 频道已注册 " );
        ConnectionMap.setChannel(ctx);
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        log.info(ctx.channel().remoteAddress() + " 频道未注册 " );
        ConnectionMap.remove(ConnectionMap.getId(ctx));
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info(ctx.channel().remoteAddress() + " 通道激活 " );
//        ConnectionMap.setChannel(ctx);
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info(ctx.channel().remoteAddress() + " 通道不激活 " );
//        ConnectionMap.remove(ConnectionMap.getId(ctx));
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info(ctx.channel().remoteAddress() + " 例外情况 :" + cause.getMessage() );
        super.exceptionCaught(ctx, cause);
    }


}
