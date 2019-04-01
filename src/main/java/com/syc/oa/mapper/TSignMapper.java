package com.syc.oa.mapper;

import com.syc.oa.model.TSign;
import com.syc.oa.model.TSignExample;
import java.util.List;

import com.syc.oa.vo.SignChart;
import org.apache.ibatis.annotations.Param;

public interface TSignMapper {
    long countByExample(TSignExample example);

    int deleteByExample(TSignExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TSign record);

    int insertSelective(TSign record);

    List<TSign> selectByExample(TSignExample example);

    TSign selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TSign record, @Param("example") TSignExample example);

    int updateByExample(@Param("record") TSign record, @Param("example") TSignExample example);

    int updateByPrimaryKeySelective(TSign record);

    int updateByPrimaryKey(TSign record);

    List<SignChart> loadSignChart(String beginDay);
}