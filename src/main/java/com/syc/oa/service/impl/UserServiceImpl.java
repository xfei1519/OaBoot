package com.syc.oa.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.syc.oa.mapper.TUserMapper;
import com.syc.oa.model.TUser;
import com.syc.oa.model.TUserExample;
import com.syc.oa.service.UserService;
import com.syc.oa.vo.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private TUserMapper userMapper;

    @Override
    public TUser findUserByNameAndPassword(String name, String password) {
        //TUserExample相当于select语句中where关键字后面的部分
        TUserExample example = new TUserExample();
        TUserExample.Criteria criteria = example.createCriteria();
        criteria.andLoginNameEqualTo(name).andPasswordEqualTo(password);
        List<TUser> users = userMapper.selectByExample(example);
        if (users !=null && users.size()>0){
            return users.get(0);
        }
        return null;
    }

    @Override
    public PageInfo<TUser> findAll(String username, String status, Integer pageNumber, Integer pageSize) {
        //在查询前进行分页设置
        PageHelper.startPage(pageNumber,pageSize);

        TUserExample example = new TUserExample();
        TUserExample.Criteria criteria = example.createCriteria();
        if ("".equals(status)){
            criteria.andUsernameLike("%"+username+"%");
        }else{
            criteria.andStatusEqualTo(Integer.parseInt(status));
            criteria.andUsernameLike("%"+username+"%");
        }
        List<TUser> users = userMapper.selectByExample(example);

        PageInfo<TUser> info =new PageInfo<>(users);
        return info;

        /*PageBean<TUser> pageBean = new PageBean<>();
        pageBean.setTotal(users.size());
        pageBean.setRows(users);
        return pageBean;*/
    }

    @Override
    public boolean addUser(TUser user) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            user.setCreateDate(sdf.parse(sdf.format(new Date())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return userMapper.insertSelective(user)>0;
    }

    @Override
    public TUser findUserById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean updateUser(TUser user) {
        TUserExample example = new TUserExample();
        example.createCriteria().andIdEqualTo(user.getId());
        return userMapper.updateByExampleSelective(user,example)>0;
    }

    @Override
    public boolean removeOne(Integer id) {
        return userMapper.deleteByPrimaryKey(id)>0;
    }

    @Override
    public boolean removeMore(Integer[] ids) {
        TUserExample example = new TUserExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        return userMapper.deleteByExample(example)>0;
    }
}
