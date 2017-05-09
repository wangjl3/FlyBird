package com.hebut.flybird.websocket.entity;

import java.util.Date;

/**
 * Created by WangJL on 2017/5/8.
 */
//服务端转发的其他客户端的普通文本消息
public class MessageInstant {

    public String fromAccount;//发送者账户
    public String fromName;//发送者名称(名称或者备注)
    public String toAccount;//接受者账户
    public String text;//发送的文本
    public Date date;//发送日期

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
