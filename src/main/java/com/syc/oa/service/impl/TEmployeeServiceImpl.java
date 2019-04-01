package com.syc.oa.service.impl;

import com.syc.oa.mapper.TDeptMapper;
import com.syc.oa.mapper.TEmployeeMapper;
import com.syc.oa.mapper.TJobMapper;
import com.syc.oa.model.*;
import com.syc.oa.service.TEmployeeService;
import com.syc.oa.vo.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service("tEmployeeService")
public class TEmployeeServiceImpl implements TEmployeeService {

    @Autowired
    private TEmployeeMapper tEmployeeMapper;
    @Autowired
    private TDeptMapper tDeptMapper;
    @Autowired
    private TJobMapper tJobMapper;

    @Override
    public PageBean<TEmployee2> getAll(Integer did, String name, String cardId, Integer jid, Integer gender, String phone, Integer pageSize, Integer pageNumber) {
        TEmployeeExample example = new TEmployeeExample();
        TEmployeeExample.Criteria criteria = example.createCriteria();
        if(!"-1".equals(did+"")){
            criteria.andDidEqualTo(did);
        }else if(!"-1".equals(jid+"")){
            criteria.andJidEqualTo(jid);
        }else if(!"-1".equals(gender+"")){
            criteria.andGenderEqualTo(gender);
        }
        criteria.andNameLike("%"+name+"%");
        criteria.andCardIdLike("%"+cardId+"%");
        criteria.andPhoneLike("%"+phone+"%");
        List<TEmployee> tEmployees = tEmployeeMapper.selectByExample(example);
        ArrayList<TEmployee2> tEmployee2s = new ArrayList<>();
        for (TEmployee tEmployee : tEmployees) {
            TEmployee2 tEmployee2 = new TEmployee2();
            TJob job = tJobMapper.selectByPrimaryKey(tEmployee.getJid());
            TDept dept = tDeptMapper.selectByPrimaryKey(tEmployee.getDid());
            tEmployee2.setId(tEmployee.getId()).setName(tEmployee.getName()).setGender(tEmployee.getGender()).setPhone(tEmployee.getPhone()).setEmail(tEmployee.getEmail())
                    .setJob(job).setEducation(tEmployee.getEducation()).setCardId(tEmployee.getCardId()).setDept(dept).setAddress(tEmployee.getAddress()).setCreateDate(tEmployee.getCreateDate());
            tEmployee2s.add(tEmployee2);
        }
        PageBean<TEmployee2> pageBean = new PageBean<>();
        pageBean.setTotal(tEmployee2s.size());
        pageBean.setRows(tEmployee2s);
        return pageBean;
    }

    @Override
    public boolean addEmployee(TEmployee employee) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String data = sdf.format(new Date());
        Date date = null;
        try {
            date = sdf.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        employee.setCreateDate(date);
        return tEmployeeMapper.insertSelective(employee)>0;
    }

    @Override
    public Object findEmployeeById(Integer id) {
        return tEmployeeMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean updateEmployee(TEmployee employee) {
        /*TEmployeeExample example = new TEmployeeExample();
        example.createCriteria().andIdEqualTo(employee.getId());
        return tEmployeeMapper.updateByExampleSelective(employee,example)>0;*/
        return tEmployeeMapper.updateByPrimaryKeySelective(employee)>0;
    }

    @Override
    public boolean removeOne(Integer id) {
        return tEmployeeMapper.deleteByPrimaryKey(id)>0;
    }

    @Override
    public boolean removeMore(Integer[] ids) {
        TEmployeeExample example = new TEmployeeExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        return tEmployeeMapper.deleteByExample(example)>0;
    }
}
