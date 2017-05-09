package com.hebut.flybird.sys.service;

import com.hebut.flybird.sys.entity.Contact;
import com.hebut.flybird.sys.entity.User;
import org.apache.shiro.authc.Account;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by WangJL on 2017/5/7.
 */
public interface ContactService  {
    void save(Contact contact);
}
