package com.cucund.project.netty.server;

import com.cucund.project.netty.handler.NettyServerHandler;
import com.cucund.project.netty.init.NettyServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyServer implements Runnable {
    private int port;
    private Channel channel;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    ChannelInitializer<SocketChannel> initializer;

    public NettyServer(int port, ChannelInitializer<SocketChannel> initializer){
        this.port = port;
        this.initializer = initializer;
    }

    public void init() {
        Thread nServer = new Thread(this);
        nServer.start();
    }

    public void destroy() {
        log.info("destroy server resources");
        if (null == channel) {
            log.info("server channel is null");
        }
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
        channel.closeFuture().syncUninterruptibly();
        bossGroup = null;
        workerGroup = null;
        channel = null;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup);
            b.channel(NioServerSocketChannel.class);
            b.childHandler(initializer);
            log.info("Netty服务启动成功");
            // 服务器绑定端口监听
            ChannelFuture f = b.bind(port).sync();
            // 监听服务器关闭监听
            f.channel().closeFuture().sync();
            channel = f.channel();
            // 可以简写为
            /* b.bind(portNumber).sync().channel().closeFuture().sync(); */
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}