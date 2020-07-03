package com.cucund.project.netty.init;

import com.cucund.project.netty.handler.NettyServerHandler;
import com.cucund.project.netty.handler.NettyServerMessageHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyServerInitializer  extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        log.info("通道初始化");

        // 以("\n")为结尾分割的 解码器
//        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(10, Delimiters.lineDelimiter()));

        // 字符串解码 和 编码
        pipeline.addLast("decoder", new StringDecoder());
        pipeline.addLast("encoder", new StringEncoder());
        // 自己的逻辑Handler
        pipeline.addLast("handler", new NettyServerHandler());
        pipeline.addLast("msgHandler", new NettyServerMessageHandler());
    }
}