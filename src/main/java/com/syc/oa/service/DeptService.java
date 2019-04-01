package com.syc.oa.service;

import com.syc.oa.model.TDept;
import com.syc.oa.vo.PageBean;

public interface DeptService {

    PageBean<TDept> findAll(String name, Integer pageNumber, Integer pageSize);

    boolean addDept(TDept dept);

    TDept findDeptById(Integer id);

    boolean updateDept(TDept dept);

    boolean removeOne(Integer id);

    boolean removeMore(Integer[] ids);
}
