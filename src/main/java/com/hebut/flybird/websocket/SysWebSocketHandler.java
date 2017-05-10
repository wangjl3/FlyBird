package com.hebut.flybird.websocket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Until;
import com.hebut.flybird.sys.Constants;
import com.hebut.flybird.sys.entity.*;
import com.hebut.flybird.sys.entity.User;
import com.hebut.flybird.sys.service.UserService;
import com.hebut.flybird.util.DateUtil;
import com.hebut.flybird.websocket.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.TextMessage;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * socket处理器
 * Created by WangJL on 2017/5/5.
 */
@Component
public class SysWebSocketHandler implements WebSocketHandler {
    @Autowired
    public UserService userService;
    //存储客户端连接会话的map
    public static  final Map<String,WebSocketSession> userSocketSessionMap;
    static {
        userSocketSessionMap = new HashMap<String, WebSocketSession>();
    }
    /**
     * 建立连接后
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session)
            throws Exception {
        String account = (String) session.getAttributes().get("account");
        if (userSocketSessionMap.get(account) == null) {
            userSocketSessionMap.put(account, session);
            //上线通知
            List<User> users = userService.queryLinkmansByAccount(account);
            //所有联系人账号
            List<String>  linkManAccounts = new ArrayList<String>();
            for (User user : users) {
                linkManAccounts.add(user.getAccount());
            }
            //存储所有在线联系人账号
            List<String> linkManUplineAccounts = new ArrayList<String>();
            //所有账号
            Set<String> allAccounts = userSocketSessionMap.keySet();
            for (String linkManAccount : linkManAccounts) {
                if(allAccounts.contains(linkManAccount)){
                    linkManUplineAccounts.add(linkManAccount);
                }
            }
            Data data = new Data().code(Data.CODE_UPDATE_STATUS).put("msg",new MessageUpdateStatus(account,Constants.FREE));
            uplineNotice(linkManUplineAccounts,new TextMessage(new GsonBuilder().create().toJson(data)));

        }
    }
    /**
     * 消息处理，在客户端通过Websocket API发送的消息会经过这里，然后进行相应的处理
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        if(message.getPayloadLength()==0)return;
        MessageInstant messageInstant=new Gson().fromJson(message.getPayload().toString(),MessageInstant.class);
        messageInstant.setDate(new Date());
        Data  data = new Data().code(Data.CODE_TEXT_DATA).put("msg",messageInstant);
        sendMessageToUser(messageInstant.getToAccount(), new TextMessage(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(data)));
    }

    /**
     * 消息传输错误处理
     * @param session
     * @param exception
     * @throws Exception
     */
    @Override
    public void handleTransportError(WebSocketSession session,
                                     Throwable exception) throws Exception {
        if(session.isOpen()){
            session.close();
        }
        Iterator<Map.Entry<String, WebSocketSession>> iterator = userSocketSessionMap.entrySet().iterator();
        //移除回话
        while (iterator.hasNext()){
            Map.Entry<String, WebSocketSession> entry = iterator.next();
            if(entry.getValue().getId().equals(session.getId())){
                userSocketSessionMap.remove(session.getId());
                System.out.println("Socket会话已经移除"+entry.getKey());
                break;
            }
        }
    }

    /**
     * 关闭连接后
     * @param session
     * @param closeStatus
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session,
                                      CloseStatus closeStatus) throws Exception {
        System.out.println("Websocket:" + session.getId() + "已经关闭");
        Iterator<Map.Entry<String, WebSocketSession>> iterator = userSocketSessionMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, WebSocketSession> entry = iterator.next();
            if(entry.getValue().getId().equals(session.getId())){
                userSocketSessionMap.remove(entry.getKey());
                System.out.println("Socket会话已经移除"+entry.getKey());
                break;
            }
        }
    }
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
    //上线通知
    private void uplineNotice(List<String> accounts, final TextMessage message){
        Iterator<Map.Entry<String, WebSocketSession>> iterator = userSocketSessionMap.entrySet().iterator();
        //多线程群发
        while (iterator.hasNext()){
            final  Map.Entry<String, WebSocketSession> entry = iterator.next();
            String acccount = entry.getKey();
            if (accounts.contains(acccount)) {
                if(entry.getValue().isOpen()){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if(entry.getValue().isOpen()){
                                    entry.getValue().sendMessage(message);
                                }
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        }
    }
    private void sendMessageToUser(String account,TextMessage message) throws IOException {
        //获取消息接收者的会话
        WebSocketSession webSocketSession = userSocketSessionMap.get(account);
        //如果消息接收者在线，则直接发送消息
        if(webSocketSession != null && webSocketSession.isOpen()){
            webSocketSession.sendMessage(message);
        }
    }
}
