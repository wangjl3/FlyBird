package com.hebut.flybird.sys.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by WangJL on 2017/5/6.
 */
@Table(name = "t_add_friend_item")
@Entity
public class AddFriendItem extends BaseEntity<Long>{
    private String fromAcc;
    private String toAcc;

    public String getFromAcc() {
        return fromAcc;
    }

    public void setFromAcc(String fromAcc) {
        this.fromAcc = fromAcc;
    }

    public String getToAcc() {
        return toAcc;
    }

    public void setToAcc(String toAcc) {
        this.toAcc = toAcc;
    }
}
