package com.syc.oa.service;

import com.github.pagehelper.PageInfo;
import com.syc.oa.model.TSign;
import com.syc.oa.vo.SignChart;

import java.util.List;

public interface SignService {
    PageInfo<TSign> findSign(String startDate, String endDate,Integer pageNum,Integer pageSize);

    boolean isSign(Integer uid);

    boolean addSign(Integer uid);

    List<SignChart> loadSignChart(String beginDay);
}
