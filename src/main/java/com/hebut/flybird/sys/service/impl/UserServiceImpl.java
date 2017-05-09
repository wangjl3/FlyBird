package com.hebut.flybird.sys.service.impl;

import com.hebut.flybird.sys.entity.Contact;
import com.hebut.flybird.sys.entity.User;
import com.hebut.flybird.sys.repository.ContactRepository;
import com.hebut.flybird.sys.repository.UserRepository;
import com.hebut.flybird.sys.service.ContactService;
import com.hebut.flybird.sys.service.UserService;
import com.hebut.flybird.util.EndecryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by WangJL on 2017/5/6.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ContactRepository contactRepository;
    @Override
    public User queryByAccount(String account) {
        return userRepository.findByAccount(account);
    }

    @Override
    public boolean save(User user) {
        User account = userRepository.findByAccount(user.getAccount());
        if(account!=null){
            return false;
        }
        user.setUpdateTime(new Date());
        user.setSalt(EndecryptUtils.generateRandomNumber());
        String pwd = EndecryptUtils.md5HexPassword(user.getAccount(), user.getPassword(), user.getSalt(), 2);
        user.setPassword(pwd);
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean login(User user) {
        User dbUser = userRepository.findByAccount(user.getAccount());
        if(dbUser!=null){
            String dbPwd = dbUser.getPassword();
            String userPwd = EndecryptUtils.md5HexPassword(user.getAccount(), user.getPassword(), dbUser.getSalt(), 2);
            if(dbPwd.equals(userPwd)){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<User> queryLinkmansByAccount(String account) {
        List<Contact> contacts = contactRepository.findByAccount(account);
        List<String> accounts = new ArrayList<String>();
        //定义账号和备注的map
        Map<String,String> map = new HashMap<String, String>();
        //得到所有的好友账号
        for (Contact contact : contacts) {
            map.put(contact.getLinkmanAccount(),contact.getRemark());
            accounts.add(contact.getLinkmanAccount());
        }
        //查询所有好友信息
        List<User> linkmans = userRepository.findByAccountIn(accounts);
        //加载备注信息
        for (User linkman : linkmans) {
            if (map.get(linkman.getAccount())!=null) {
                //有备注设置备注
                linkman.setRemark(map.get(linkman.getAccount()));
            }else {
                //没有备注，备注为好友昵称
                linkman.setRemark(linkman.getNickname());
            }
        }
        return  linkmans;
    }
}
