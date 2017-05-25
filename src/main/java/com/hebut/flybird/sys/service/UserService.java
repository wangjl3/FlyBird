package com.hebut.flybird.sys.service;

import com.hebut.flybird.sys.entity.User;

import java.util.List;

/**
 * Created by WangJL on 2017/5/6.
 */
public interface UserService {
    User queryByAccount(String account);
    List<User> queryLinkmansByAccountAndRemarkLike(String account,String remark);
    boolean save(User user);
    boolean login(User user);
    List<User> queryLinkmansByAccount(String account);

    void updateProfile(String account,User user);

    User queryByStrangeAccount(String account,String strangeAcc);
}
