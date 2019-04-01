package com.syc.oa.service;

import com.github.pagehelper.PageInfo;
import com.syc.oa.model.TAdvice;

import java.util.Map;

public interface AdviceService {

    PageInfo<TAdvice> selectAdvice(Map<String,Object> params);

    TAdvice selectAdviceById(Integer id);

    boolean addAdvice(Integer uid,TAdvice advice);

    boolean updateAdvice(TAdvice advice);

    boolean removeOne(Integer id);

    boolean removeMore(Integer[] ids);
}
