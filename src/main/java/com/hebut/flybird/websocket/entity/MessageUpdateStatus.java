package com.hebut.flybird.websocket.entity;

/**
 * Created by WangJL on 2017/5/9.
 */
public  class MessageUpdateStatus {
     private String account;
     private Integer status;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public MessageUpdateStatus(String account, Integer status) {
        this.account = account;
        this.status = status;
    }
}
