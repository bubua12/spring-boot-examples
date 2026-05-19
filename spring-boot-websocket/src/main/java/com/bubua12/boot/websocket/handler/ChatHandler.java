package com.bubua12.boot.websocket.handler;

import org.springframework.lang.NonNull;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 消息处理器
 *
 * @author bubua12
 * @since 2026/5/19 18:55
 */
public class ChatHandler extends TextWebSocketHandler {

    // 1 存放所有连接进来的客户端
    private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet(); // 为什么需要线程安全？因为可能同时有多个客户端连接/断开，多个线程在操作这个集合


    // 2 连接建立时触发（有人进来了）
    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) {
        sessions.add(session);
    }

    // 3 收到消息时触发（有人说话了）
    @Override
    protected void handleTextMessage(@NonNull WebSocketSession session, @NonNull TextMessage message) throws IOException {
        for (WebSocketSession s : sessions) {
            if (s.isOpen()) {
                s.sendMessage(new TextMessage("收到: " + message.getPayload()));
            }
        }
    }

    // 4 连接关闭时触发（有人离开了）
    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus status) {
        sessions.remove(session);
    }
}
