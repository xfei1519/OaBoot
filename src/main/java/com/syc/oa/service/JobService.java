package com.syc.oa.service;

import com.syc.oa.model.TDept;
import com.syc.oa.model.TJob;
import com.syc.oa.vo.PageBean;

public interface JobService {

    PageBean<TJob> findAll(String name, Integer pageNumber, Integer pageSize);

    boolean addJob(TJob job);

    TJob findJobById(Integer id);

    boolean updateJob(TJob job);

    boolean removeOne(Integer id);

    boolean removeMore(Integer[] ids);
}
