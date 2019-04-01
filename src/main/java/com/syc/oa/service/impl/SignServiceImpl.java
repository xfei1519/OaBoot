package com.syc.oa.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.syc.oa.mapper.TSignMapper;
import com.syc.oa.mapper.TUserMapper;
import com.syc.oa.model.TSign;
import com.syc.oa.model.TSignExample;
import com.syc.oa.service.SignService;
import com.syc.oa.vo.SignChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service("signService")
public class SignServiceImpl implements SignService {

    @Autowired
    private TSignMapper signMapper;

    @Autowired
    private TUserMapper userMapper;

    @Override
    public PageInfo<TSign> findSign(String startDate, String endDate,Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        TSignExample example = new TSignExample();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date start = sdf.parse(startDate + " 00:00:00");
            Date end = sdf.parse(endDate + " 24:00:00");
            example.createCriteria().andCreateTimeBetween(start,end);
            List<TSign> signs = signMapper.selectByExample(example);
            for (TSign sign : signs) {
                sign.setUser(userMapper.selectByPrimaryKey(sign.getUid()));
            }
            PageInfo<TSign> info =new PageInfo<>(signs);
            return info;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean isSign(Integer uid) {
        TSignExample example = new TSignExample();
        example.createCriteria().andUidEqualTo(uid).andFlagEqualTo(1);
        List<TSign> signs = signMapper.selectByExample(example);
        if (signs!=null && signs.size()>0){
            return signs.get(0) != null;
        }
        return false;
    }

    @Override
    public boolean addSign(Integer uid) {
        TSign sign = new TSign();
        sign.setUid(uid);
        //1:已打卡,0:未打卡
        sign.setFlag(1);
        sign.setUser(userMapper.selectByPrimaryKey(uid));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
        String date = format.format(new Date());
        try {
            sign.setCreateTime(format.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            sign.setFlag(0);
        }
        return signMapper.insertSelective(sign)>0;
    }

    @Override
    public List<SignChart> loadSignChart(String beginDay) {
        return signMapper.loadSignChart(beginDay);
    }
}
