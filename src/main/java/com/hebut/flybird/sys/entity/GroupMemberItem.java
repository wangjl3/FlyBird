package com.hebut.flybird.sys.entity;

/**
 * Created by WangJL on 2017/5/12. 群聊成员
 */
public class GroupMemberItem extends  BaseEntity<Long>{
    private String groupId;
    private String memberAccount;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getMemberAccount() {
        return memberAccount;
    }

    public void setMemberAccount(String memberAccount) {
        this.memberAccount = memberAccount;
    }
}
