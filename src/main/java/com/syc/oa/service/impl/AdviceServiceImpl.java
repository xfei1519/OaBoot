package com.syc.oa.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.syc.oa.mapper.TAdviceMapper;
import com.syc.oa.mapper.TUserMapper;
import com.syc.oa.model.TAdvice;
import com.syc.oa.model.TAdviceExample;
import com.syc.oa.model.TUser;
import com.syc.oa.service.AdviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("adviceService")
public class AdviceServiceImpl implements AdviceService {

    @Autowired
    private TAdviceMapper adviceMapper;
    @Autowired
    private TUserMapper userMapper;

    @Override
    public PageInfo<TAdvice> selectAdvice(Map<String, Object> params) {
        Integer pageNum = (Integer)params.getOrDefault("pageNum", 1);
        Integer pageSize = (Integer)params.getOrDefault("pageSize", 10);
        PageHelper.startPage(pageNum,pageSize);
        String title = (String) params.getOrDefault("title", "");
        TAdviceExample example = new TAdviceExample();
        //链式调用,建造器模式--->当要设置很多属性时
        example.createCriteria().andTitleLike("%"+title+"%");
        List<TAdvice> advices = adviceMapper.selectByExampleWithBLOBs(example);
        for (TAdvice advice : advices) {
            TUser tUser = userMapper.selectByPrimaryKey(advice.getUid());
            advice.setUser(tUser);
        }
        return new PageInfo<>(advices);
    }

    @Override
    public TAdvice selectAdviceById(Integer id) {
        TAdvice advice = adviceMapper.selectByPrimaryKey(id);
        TUser tUser = userMapper.selectByPrimaryKey(advice.getUid());
        advice.setUser(tUser);
        return advice;
    }

    @Override
    public boolean addAdvice(Integer uid, TAdvice advice) {
        advice.setUid(uid);
        TUser tUser = userMapper.selectByPrimaryKey(advice.getUid());
        advice.setUser(tUser);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            advice.setCreateDate(sdf.parse(sdf.format(new Date())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return adviceMapper.insertSelective(advice)>0;
    }

    @Override
    public boolean updateAdvice(TAdvice advice) {
        return adviceMapper.updateByPrimaryKeySelective(advice)>0;
    }

    @Override
    public boolean removeOne(Integer id) {
        return adviceMapper.deleteByPrimaryKey(id)>0;
    }

    @Override
    public boolean removeMore(Integer[] ids) {
        TAdviceExample example = new TAdviceExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        return adviceMapper.deleteByExample(example)>0;
    }

}
