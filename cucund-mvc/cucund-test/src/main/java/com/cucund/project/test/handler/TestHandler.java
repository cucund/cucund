package com.cucund.project.test.handler;

import com.cucund.project.netty.handler.Handler;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Component("test")
@Slf4j
public class TestHandler implements Handler {
    @Override
    public void doWork(ChannelHandlerContext ctx, String msg) {
        String id = ctx.channel().id().asLongText();
        log.info("通道Id:"+id+",消息:"+msg);
        ctx.writeAndFlush(msg);
    }
}
