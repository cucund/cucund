package com.cucund.project.netty.handler;

import io.netty.channel.ChannelHandlerContext;

public interface Handler {

    public void doWork(ChannelHandlerContext ctx, String msg);


}
