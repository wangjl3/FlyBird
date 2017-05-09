package com.hebut.flybird.websocket;

import com.hebut.flybird.sys.Global.GlobalFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Socket 建立连接与断开
 * Created by WangJL on 2017/5/5.
 */
public class HandShake implements HandshakeInterceptor{
    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        System.out.println("Websocket:用户[ID:" + ((ServletServerHttpRequest) serverHttpRequest).getServletRequest().getSession(false).getAttribute("account") + "]已经建立连接");
        if (serverHttpRequest instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletServerHttpRequest = (ServletServerHttpRequest) serverHttpRequest;
            HttpSession session = servletServerHttpRequest.getServletRequest().getSession(false);
            //标记用户
            String account = (String) session.getAttribute("account");
            Integer uplineStatus = (Integer) session.getAttribute("uplineStatus");
            if(account != null){
                //map存储了建立连接的用户的信息
                map.put("account",account);
                GlobalFactory.getUsersUplineStatusMap().put(account,uplineStatus);
            }else {
                return  false;
            }
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

    }
}
