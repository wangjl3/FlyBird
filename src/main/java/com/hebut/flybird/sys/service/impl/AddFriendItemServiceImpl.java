package com.hebut.flybird.sys.service.impl;

import com.hebut.flybird.sys.entity.AddFriendItem;
import com.hebut.flybird.sys.entity.User;
import com.hebut.flybird.sys.repository.AddFriendItemRepository;
import com.hebut.flybird.sys.repository.UserRepository;
import com.hebut.flybird.sys.service.AddFriendItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangJL on 2017/5/7.
 */
@Service
public class AddFriendItemServiceImpl  implements AddFriendItemService{
    @Autowired
    private AddFriendItemRepository addFriendItemRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public void save(AddFriendItem addFriendItem) {
       addFriendItemRepository.save(addFriendItem);
    }

    @Override
    public boolean exists(AddFriendItem addFriendItem) {
        AddFriendItem addFriendItemInDB = addFriendItemRepository.findByFromAccAndToAcc(addFriendItem.getFromAcc(), addFriendItem.getToAcc());
        if (addFriendItemInDB!=null){
            return true;
        }
        return false;
    }

    @Override
    public List<User> findByToAcc(String toAcc) {
        List<AddFriendItem> addFriendItems = addFriendItemRepository.findByToAcc(toAcc);
        List<String> accounts = new ArrayList<String>();
        for (AddFriendItem addFriendItem : addFriendItems) {
            accounts.add(addFriendItem.getFromAcc());
        }
        List<User> users = userRepository.findByAccountIn(accounts);
        return users;
    }

    @Override
    public AddFriendItem findByFromAndTo(String fromAcc, String toAcc) {
        return null;
    }

    @Override
    public void delete(Long id) {
        addFriendItemRepository.delete(id);
    }
}
