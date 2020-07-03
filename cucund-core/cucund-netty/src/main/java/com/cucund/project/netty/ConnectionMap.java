package com.cucund.project.netty;

import com.cucund.project.tool.utils.exception.SystemError;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class ConnectionMap {

    private static final Map<String, ChannelHandlerContext> channelMap = new ConcurrentHashMap<String,ChannelHandlerContext>();

    private static final Map<String,String> userConnectionMap = new ConcurrentHashMap<>();

    private static final Map<String,String> channelConnectionMap = new ConcurrentHashMap<>();

    public static void setChannel(ChannelHandlerContext ctx){
        String shortId = getId(ctx);
        ChannelHandlerContext context = channelMap.get(shortId);
        if(context==null)
            channelMap.put(shortId,ctx);
    }

    public static void setUser(String userId,ChannelHandlerContext ctx){
        if(userConnectionMap.get(userId)!=null)
            SystemError.throwException("已绑定");
        String shortId = getId(ctx);

        userConnectionMap.put(userId,shortId);

        channelConnectionMap.put(shortId,userId);

        setChannel(ctx);
    }

    public static String getId(ChannelHandlerContext ctx){
        return ctx.channel().id().asShortText();
    }

    public static ChannelHandlerContext remove(String channelId){
        ChannelHandlerContext ctx = channelMap.remove(channelId);
        if(ctx == null)
            return null;
        String userId = channelConnectionMap.remove(channelId);
        if(userId == null)
            return null;
        userConnectionMap.remove(userId);
        return ctx;
    }


    public static String getUser(ChannelHandlerContext ctx){
        String userId = channelConnectionMap.get(getId(ctx));
        return userId;
    }

    public static ChannelHandlerContext getChannel(String userId){
        String channelId = userConnectionMap.get(userId);
        if(StringUtils.isBlank(channelId))
            SystemError.throwException("未检测到通道");
        ChannelHandlerContext ctx = channelMap.get(channelId);
        return ctx;
    }

    public static void send(String userId,String msg){
        ChannelHandlerContext ctx = getChannel(userId);
        ctxSend(ctx,msg);
    }

    public static void ctxSend(ChannelHandlerContext ctx, String msg) {
        ctx.channel().writeAndFlush(new TextWebSocketFrame(msg));
    }
}
