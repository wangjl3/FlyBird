package com.hebut.flybird.sys.entity;

import com.fasterxml.jackson.databind.deser.Deserializers;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 联系人条目表
 * Created by WangJL on 2017/5/6.
 */
@Table(name = "t_sys_contact")
@Entity
public class Contact extends BaseEntity<Long>{
    private String account;  //自己的账号
    private String linkmanAccount; //联系人的账号
    private String remark;   //备注

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getLinkmanAccount() {
        return linkmanAccount;
    }

    public void setLinkmanAccount(String linkmanAccount) {
        this.linkmanAccount = linkmanAccount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
