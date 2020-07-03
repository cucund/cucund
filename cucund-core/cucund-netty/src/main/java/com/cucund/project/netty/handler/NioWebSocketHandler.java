package com.cucund.project.netty.handler;

import com.cucund.project.netty.ConnectionMap;
import com.cucund.project.tool.utils.spring.SpringUtil;
import com.cucund.project.tool.utils.string.MessageUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Slf4j
public class NioWebSocketHandler extends SimpleChannelInboundHandler<Object> {


    private WebSocketServerHandshaker handshaker;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.debug("收到消息："+msg);
        if (msg instanceof FullHttpRequest){
            //以http请求形式接入，但是走的是websocket
            handleHttpRequest(ctx, (FullHttpRequest) msg);
        }else
        if (msg instanceof WebSocketFrame){
            //处理websocket客户端的消息
            handlerWebSocketFrame(ctx, (WebSocketFrame) msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().flush();
    }

    private void handlerWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame){
        // 判断是否关闭链路的指令
        if (frame instanceof CloseWebSocketFrame) {
            handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            return;
        }
        // 判断是否ping消息
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(
                    new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        // 本例程仅支持文本消息，不支持二进制消息
        if (!(frame instanceof TextWebSocketFrame)) {
            log.debug("本例程仅支持文本消息，不支持二进制消息");
            throw new UnsupportedOperationException(String.format(
                    "%s frame types not supported", frame.getClass().getName()));
        }
        // 返回应答消息
        String request = ((TextWebSocketFrame) frame).text();
        log.debug("服务端收到：" + request);
        TextWebSocketFrame tws = new TextWebSocketFrame(request);
        // 群发
        String msg = tws.text();
        log.info(msg);
        handler(ctx, msg);
    }

    private void handler(ChannelHandlerContext ctx, String msg) {
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
        Map<String, Handler> beans = SpringUtil.getBeans(Handler.class);
        Handler handler = beans.get(clazzName);
        if(handler == null) {
            log.error("未找到Bean实体");
            return ;
        }
        handler.doWork(ctx,data);
    }

    /**
     * 唯一的一次http请求，用于创建websocket
     * */
    private void handleHttpRequest(ChannelHandlerContext ctx,
                                   FullHttpRequest req) {
        //要求Upgrade为websocket，过滤掉get/Post
        if (!req.decoderResult().isSuccess()
                || (!"websocket".equals(req.headers().get("Upgrade")))) {
            //若不是websocket方式，则创建BAD_REQUEST的req，返回给客户端
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
                "ws://localhost:2010/websocket", null, false);
        handshaker = wsFactory.newHandshaker(req);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory
                    .sendUnsupportedVersionResponse(ctx.channel());
        } else {
            handshaker.handshake(ctx.channel(), req);
        }
    }
    /**
     * 拒绝不合法的请求，并返回错误信息
     * */
    private static void sendHttpResponse(ChannelHandlerContext ctx,
                                         FullHttpRequest req, DefaultFullHttpResponse res) {
        // 返回应答给客户端
        if (res.status().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(),
                    CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
        }
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        // 如果是非Keep-Alive，关闭连接
        if (!isKeepAlive(req) || res.status().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    private static boolean isKeepAlive(FullHttpRequest req) {
        String s = req.headers().get("Connection");
        if ("keep-alive".equals(s))
            return true;
        return false;
    }
}