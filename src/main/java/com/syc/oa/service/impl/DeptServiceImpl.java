package com.syc.oa.service.impl;

import com.syc.oa.mapper.TDeptMapper;
import com.syc.oa.model.TDept;
import com.syc.oa.model.TDeptExample;
import com.syc.oa.service.DeptService;
import com.syc.oa.vo.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service("deptService")
public class DeptServiceImpl implements DeptService {

    @Autowired
    private TDeptMapper deptMapper;


    @Override
    public PageBean<TDept> findAll(String name, Integer pageNumber, Integer pageSize) {
        TDeptExample example = new TDeptExample();
        TDeptExample.Criteria criteria = example.createCriteria();
        criteria.andNameLike("%"+name+"%");
        List<TDept> tDepts = deptMapper.selectByExample(example);
        PageBean<TDept> pageBean = new PageBean<>();
        pageBean.setTotal(tDepts.size());
        pageBean.setRows(tDepts);
        return pageBean;
    }

    @Override
    public boolean addDept(TDept dept) {
        return deptMapper.insertSelective(dept)>0;
    }

    @Override
    public TDept findDeptById(Integer id) {
        return deptMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean updateDept(TDept dept) {
        TDeptExample example = new TDeptExample();
        example.createCriteria().andIdEqualTo(dept.getId());
        return deptMapper.updateByExampleSelective(dept,example)>0;
    }

    @Override
    public boolean removeOne(Integer id) {
        return deptMapper.deleteByPrimaryKey(id)>0;
    }

    @Override
    public boolean removeMore(Integer[] ids) {
        TDeptExample example = new TDeptExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        return deptMapper.deleteByExample(example)>0;
    }
}
