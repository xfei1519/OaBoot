package com.syc.oa.service;

import com.github.pagehelper.PageInfo;
import com.syc.oa.model.TUser;
import com.syc.oa.vo.PageBean;

public interface UserService {
    TUser findUserByNameAndPassword(String name,String password);

    PageInfo<TUser> findAll(String username, String status, Integer pageNumber, Integer pageSize);

    boolean addUser(TUser user);

    TUser findUserById(Integer id);

    boolean updateUser(TUser user);

    boolean removeOne(Integer id);

    boolean removeMore(Integer[] ids);
}
