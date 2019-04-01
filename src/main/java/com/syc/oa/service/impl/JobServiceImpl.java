package com.syc.oa.service.impl;

import com.syc.oa.mapper.TJobMapper;
import com.syc.oa.model.TJob;
import com.syc.oa.model.TJobExample;
import com.syc.oa.service.JobService;
import com.syc.oa.vo.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service("jobService")
public class JobServiceImpl implements JobService {

    @Autowired
    private TJobMapper jobMapper;


    @Override
    public PageBean<TJob> findAll(String name, Integer pageNumber, Integer pageSize) {
        TJobExample example = new TJobExample();
        TJobExample.Criteria criteria = example.createCriteria();
        criteria.andNameLike("%"+name+"%");
        List<TJob> tJobs = jobMapper.selectByExample(example);
        PageBean<TJob> pageBean = new PageBean<>();
        pageBean.setTotal(tJobs.size());
        pageBean.setRows(tJobs);
        return pageBean;
    }

    @Override
    public boolean addJob(TJob job) {
        return jobMapper.insertSelective(job)>0;
    }

    @Override
    public TJob findJobById(Integer id) {
        return jobMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean updateJob(TJob job) {
        TJobExample example = new TJobExample();
        example.createCriteria().andIdEqualTo(job.getId());
        return jobMapper.updateByExampleSelective(job,example)>0;
    }

    @Override
    public boolean removeOne(Integer id) {
        return jobMapper.deleteByPrimaryKey(id)>0;
    }

    @Override
    public boolean removeMore(Integer[] ids) {
        TJobExample example = new TJobExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        return jobMapper.deleteByExample(example)>0;
    }
}
