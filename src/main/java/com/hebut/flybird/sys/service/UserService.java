package com.hebut.flybird.sys.service;

import com.hebut.flybird.sys.entity.User;

import java.util.List;

/**
 * Created by WangJL on 2017/5/6.
 */
public interface UserService {
    User queryByAccount(String account);
    boolean save(User user);
    boolean login(User user);
    List<User> queryLinkmansByAccount(String account);
}
