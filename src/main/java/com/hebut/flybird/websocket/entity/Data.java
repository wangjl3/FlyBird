package com.hebut.flybird.websocket.entity;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by WangJL on 2017/5/5.
 */
public class Data extends HashMap<String,Object>{
    //文本消息
    public static final int CODE_TEXT_DATA = -1;
    //好友上线状态改变通知
    public static final int CODE_UPDATE_STATUS = -2;
    //被别人添加为好友
    public static  final int CODE_FRIENDING_DATA  = -3;
    public Data code(int code){
        put("code",code);
        return this;
    }
    public Data put(String key,Object value){
        super.put(key, value);
        return this;
    }
}
