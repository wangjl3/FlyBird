package com.hebut.flybird.sys.repository;

import com.hebut.flybird.sys.entity.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by WangJL on 2017/5/6.
 */
public interface UserRepository extends BaseRepository<User,Long>,JpaSpecificationExecutor<User>{
    User  findByAccount(String account);
    List<User> findByAccountIn(List<String> accounts);
}
