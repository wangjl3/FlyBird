package com.hebut.flybird.sys.entity;

import java.util.Date;

/**
 * Created by WangJL on 2017/5/10.
 */
public class MessageItem{
    public String fromAccount;//发送者账户
    public String fromName;//发送者名称(名称或者备注)
    public String toAccount;//接受者账户
    public String text;//发送的文本
    public Date date;//发送时间
    public boolean isSented; //是否发送标记

}