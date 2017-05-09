package com.hebut.flybird.websocket;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.*;

import javax.annotation.Resource;

/**
 * WebSocket 配置处理器
 * Created by WangJL on 2017/5/5.
 */
@Component
@EnableWebSocket
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {

    @Resource
    SysWebSocketHandler handler;
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(handler,"/ws").addInterceptors(new HandShake());
        webSocketHandlerRegistry.addHandler(handler,"ws/sockjs").addInterceptors(new HandShake()).withSockJS();
    }
}
