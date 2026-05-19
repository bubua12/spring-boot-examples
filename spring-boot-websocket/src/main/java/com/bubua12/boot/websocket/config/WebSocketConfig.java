package com.bubua12.boot.websocket.config;

import com.bubua12.boot.websocket.handler.ChatHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * 实现 WebSocket 配置接口，Spring 会调用里面的 registerWebSocketHandlers 方法
 * 通俗理解： 这个类就是"前台接待"，告诉 Spring："有人从 /ws/chat 这个门进来，你就把他带到 ChatHandler 那里去
 *
 * @author bubua12
 * @since 2026/5/19 18:54
 */
@Configuration
@EnableWebSocket // 开启 WebSocket 功能
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 注册路由：当客户端连接 /ws/chat 这个路径时，交给 ChatHandler 来处理
        registry.addHandler(new ChatHandler(), "/ws/chat")
                // 允许所有域名的网页来连接。* 表示不限制，开发阶段用；生产环境应该写具体域名
                .setAllowedOrigins("*");
    }
}
