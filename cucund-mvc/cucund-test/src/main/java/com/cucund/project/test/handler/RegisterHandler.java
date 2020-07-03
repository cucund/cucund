package com.cucund.project.test.handler;

import com.cucund.project.netty.ConnectionMap;
import com.cucund.project.netty.handler.Handler;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component("reg")
@Slf4j
public class RegisterHandler implements Handler {


    @Override
    public void doWork(ChannelHandlerContext ctx, String msg) {
        ConnectionMap.setUser(msg,ctx);
        log.info("设备号:"+msg+"绑定成功");
        ctx.channel().write("用户号:"+msg+"绑定成功");
    }
}
