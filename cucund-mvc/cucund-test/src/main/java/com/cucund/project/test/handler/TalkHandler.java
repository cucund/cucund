package com.cucund.project.test.handler;


import com.cucund.project.netty.ConnectionMap;
import com.cucund.project.netty.handler.Handler;
import com.cucund.project.tool.utils.json.JSONUtils;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("talk")
@Slf4j
public class TalkHandler implements Handler {


    @Override
    public void doWork(ChannelHandlerContext ctx, String msg) {
        Map<String, Object> map = null;
        try {
            map = JSONUtils.json2mapDeeply(msg);
        } catch (Exception e) {
            ConnectionMap.ctxSend(ctx,"无法解析协议");
            return ;
        }
        String toUser = map.get("t")+"";
        String message = map.get("m")+"";
        String fromUser = ConnectionMap.getUser(ctx);
        try{
            ChannelHandlerContext channel = ConnectionMap.getChannel(toUser);
            Map<String,String > result = new HashMap<>();
            result.put("m",message);
            result.put("f",fromUser);
            ConnectionMap.send(toUser,JSONUtils.mapToJson(result));
            ConnectionMap.send(fromUser,"ok");
        }catch (Exception e){
            ConnectionMap.send(fromUser,"未找到对方"+toUser);
        }
    }

}
