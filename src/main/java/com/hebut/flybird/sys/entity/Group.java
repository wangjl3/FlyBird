package com.hebut.flybird.sys.entity;

/**
 * Created by WangJL on 2017/5/12. 群聊
 */
public class Group extends BaseEntity<Long> {
    private String groupName;
    private String numOfMember;
    private String createrAccount;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getNumOfMember() {
        return numOfMember;
    }

    public void setNumOfMember(String numOfMember) {
        this.numOfMember = numOfMember;
    }

    public String getCreaterAccount() {
        return createrAccount;
    }

    public void setCreaterAccount(String createrAccount) {
        this.createrAccount = createrAccount;
    }
}
