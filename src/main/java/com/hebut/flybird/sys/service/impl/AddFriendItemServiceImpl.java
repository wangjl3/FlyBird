package com.hebut.flybird.sys.service.impl;

import com.hebut.flybird.sys.entity.AddFriendItem;
import com.hebut.flybird.sys.repository.AddFriendItemRepository;
import com.hebut.flybird.sys.service.AddFriendItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by WangJL on 2017/5/7.
 */
@Service
public class AddFriendItemServiceImpl  implements AddFriendItemService{
    @Autowired
    private AddFriendItemRepository addFriendItemRepository;
    @Override
    public void save(AddFriendItem addFriendItem) {
       addFriendItemRepository.save(addFriendItem);
    }
}
