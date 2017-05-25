package com.hebut.flybird.sys.repository;

import com.hebut.flybird.sys.entity.AddFriendItem;

import java.util.List;

/**
 * Created by WangJL on 2017/5/7.
 */
public interface AddFriendItemRepository extends BaseRepository<AddFriendItem,Long> {
    AddFriendItem findByFromAccAndToAcc(String fromAcc,String toAcc);
    List<AddFriendItem> findByToAcc(String toAcc);
}
