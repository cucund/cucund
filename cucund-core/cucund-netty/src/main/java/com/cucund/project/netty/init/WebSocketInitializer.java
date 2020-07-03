package com.cucund.project.netty.init;


import com.cucund.project.netty.handler.NettyServerHandler;
import com.cucund.project.netty.handler.NioWebSocketHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class WebSocketInitializer  extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) {
        ch.pipeline().addLast("logging",new LoggingHandler("DEBUG"));//设置log监听器，并且日志级别为debug，方便观察运行流程
//        ch.pipeline().addLast("http-c-codec",new HttpClientCodec());//设置解码器
        ch.pipeline().addLast("http-s-codec",new HttpServerCodec());//设置解码器
        ch.pipeline().addLast("aggregator",new HttpObjectAggregator(65536));//聚合器，使用websocket会用到
        ch.pipeline().addLast("http-chunked",new ChunkedWriteHandler());//用于大数据的分区传输
        ch.pipeline().addLast("handler",new NettyServerHandler());
        ch.pipeline().addLast("msg-handler",new NioWebSocketHandler());//自定义的业务handler
    }
}