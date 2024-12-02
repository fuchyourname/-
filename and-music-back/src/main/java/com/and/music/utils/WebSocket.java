package com.and.music.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.and.music.dto.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@Slf4j
@ServerEndpoint("/websocket/{userId}")
public class WebSocket {
    private final StringRedisTemplate stringRedisTemplate = SpringUtils.getBean(StringRedisTemplate.class);;
    private Session session;
    private String userId;
    private static CopyOnWriteArraySet<WebSocket> webSockets = new CopyOnWriteArraySet<>();
    private static ConcurrentHashMap<String, Session> sessionPool = new ConcurrentHashMap<String, Session>();
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "userId") String userId) {
        try {
            List<String> msgs = stringRedisTemplate.opsForList()
                    .range(userId, 0, -1);
            if(msgs != null){
                for(String msg : msgs){
                    Message message = JSON.parseObject(msg, Message.class);
                    log.info(message.toString());
                    session.getAsyncRemote().sendText(JSON.toJSONString(message));
                }
            }
            this.session = session;
            this.userId = userId;
            webSockets.add(this);
            sessionPool.put(userId, session);
            sendAllMessage(userId + "上线了",0);
            log.info("【websocket消息】有新的连接，总数为:" + webSockets.size());
        } catch (Exception e) {
        }
    }
    @OnClose
    public void onClose() {
        try {
            webSockets.remove(this);
            sessionPool.remove(this.userId);
            log.info("【websocket消息】"+ this.userId + "连接断开，总数为:" + webSockets.size());
        } catch (Exception ignored) {
        }
    }

    @OnMessage
    public void onMessage(String message) {
        try {
            JSONObject jsonObject = JSON.parseObject(message);
            String userName = jsonObject.getString("toUserName");
            String content = jsonObject.getString("info");

            if (sessionPool.containsKey(userName)) {
                Session session = sessionPool.get(userName);
                if (session != null && session.isOpen()) {
                    log.info("【websocket消息】 单点消息:" + message);
                    Message msg = new Message();
                    msg.setMessage(content);
                    msg.setFrom(userId);
                    msg.setType(1);
                    session.getAsyncRemote().sendText(JSON.toJSONString(msg));
                }
            } else {
                 stringRedisTemplate.opsForList()
                        .rightPush(userName, JSON.toJSONString(new Message(userName,userId ,content,1)));
                log.warn("【websocket消息】找不到用户: " + userName);
            }
        } catch (JSONException e) {
            log.error("【websocket消息】JSON 解析失败: " + e.getMessage());
        } catch (Exception e) {
            log.error("【websocket消息】处理消息时发生异常: " + e.getMessage());
        }
        log.info("【websocket消息】收到客户端消息:" + message);
    }


    @OnError
    public void onError(Session session, Throwable error) {

        log.error("用户错误,原因:" + error.getMessage());
        error.printStackTrace();
    }

    public void sendAllMessage(String message,Integer type) {
        log.info("【websocket消息】广播消息:" + message);
        for (WebSocket webSocket : webSockets) {
            try {
                if (webSocket.session.isOpen() && webSocket.session != this.session) {
                    Message msg = new Message();
                    msg.setMessage(message);
                    message = type == 0 ? "系统" : message;
                    msg.setFrom(message);
                    msg.setType(0);
                    webSocket.session.getAsyncRemote().sendText(JSON.toJSONString(msg));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sendOneMessage(String userId, String message) {
        Session session = sessionPool.get(userId);
        if (session != null && session.isOpen()) {
            try {
                log.info("【websocket消息】 单点消息:" + message);
                session.getAsyncRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void sendActionInfo(String fromId, String toId, String message) {
        Session session = sessionPool.get(toId);
        if (session != null && session.isOpen()) {
            try {
                log.info("【websocket消息】 单点消息:" + message);
                String content = " 给您的文章 " + message + "了";
                Message msg = new Message();
                msg.setMessage(content);
                msg.setFrom(fromId);
                msg.setType(1);
                session.getAsyncRemote().sendText(JSON.toJSONString(msg));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            stringRedisTemplate.opsForList()
                    .rightPush(toId, JSON.toJSONString(new Message(fromId,toId ,message,1)));
        }
    }

    public void sendMoreMessage(String[] userIds, String message) {
        for (String userId : userIds) {
            Session session = sessionPool.get(userId);
            if (session != null && session.isOpen()) {
                try {
                    log.info("【websocket消息】 单点消息:" + message);
                    session.getAsyncRemote().sendText(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
