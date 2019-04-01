package com.syc.oa.service;

import com.syc.oa.model.TEmployee;
import com.syc.oa.model.TEmployee2;
import com.syc.oa.vo.PageBean;

public interface TEmployeeService {

    PageBean<TEmployee2> getAll(Integer did, String name, String cardId, Integer jid, Integer gender, String phone, Integer pageSize, Integer pageNumber);

    boolean addEmployee(TEmployee employee);

    Object findEmployeeById(Integer id);

    boolean updateEmployee(TEmployee employee);

    boolean removeOne(Integer id);

    boolean removeMore(Integer[] ids);
}
