package com.hebut.flybird.sys.service;

import com.hebut.flybird.sys.entity.AddFriendItem;
import com.hebut.flybird.sys.entity.User;

import java.util.List;

/**
 * Created by WangJL on 2017/5/7.
 */
public interface AddFriendItemService {
    void save(AddFriendItem addFriendItem);
    boolean exists(AddFriendItem addFriendItem);
    List<User> findByToAcc(String toAcc);
    AddFriendItem findByFromAndTo(String fromAcc,String toAcc);
    void delete(Long id);
}
