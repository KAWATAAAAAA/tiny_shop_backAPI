package cn.wuyuwei.tiny_shop.websocket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import cn.wuyuwei.tiny_shop.config.MyEndpointConfigure;
import cn.wuyuwei.tiny_shop.entity.Chat;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@ServerEndpoint(value="/websocket/{userId}",configurator=MyEndpointConfigure.class)
@Component
@Slf4j
public class WebSocketServiceImple {

    public static Map<String, Session> sessionMap = new ConcurrentHashMap<String, Session>();

    @OnOpen
    public void onOpen(@PathParam("userId") String userId,Session session) {
        log.info("WebSocketService onOpen: "+userId);
        if(sessionMap == null) {
            sessionMap = new ConcurrentHashMap<String, Session>();
        }
        sessionMap.put(userId, session);
    }

    @OnClose
    public void OnClose(@PathParam("userId") String userId) {
        log.info("WebSocketService OnClose: "+userId);
        sessionMap.remove(userId);
    }
    @OnMessage
    public void OnMessage(@PathParam("userId") String userId,Session session,String message) {
        log.info("WebSocketService OnMessage: "+message);

        /*确认发送方和接收方*/
        Chat chat = new Chat();
        for (Map.Entry<String,Session> entry:sessionMap.entrySet())
        {
            System.out.println(entry.getKey());
            if (entry.getKey().equals(userId))
            {
                chat.setTo(entry.getKey());
            }
            else {
                chat.setFrom(entry.getKey());
            }
        }
        chat.setContent(message);
        System.out.println(chat);
        /*发送方和接收方各发送一份*/
        for(Session session_ : sessionMap.values()) {
            //session_.getAsyncRemote().sendText("sb");
            session_.getAsyncRemote().sendText(JSON.toJSONString(chat));
        }
    }
    @OnError
    public void error(Session session, Throwable t) {
        t.printStackTrace();
    }
}