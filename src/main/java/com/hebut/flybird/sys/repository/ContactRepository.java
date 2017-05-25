package com.hebut.flybird.sys.repository;

import com.hebut.flybird.sys.entity.Contact;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by WangJL on 2017/5/7.
 */
public interface ContactRepository extends BaseRepository<Contact,Long>,JpaSpecificationExecutor<Contact>{
    List<Contact> findByAccountAndRemarkLike(String account,String remark);
    List<Contact> findByAccount(String account);
}
