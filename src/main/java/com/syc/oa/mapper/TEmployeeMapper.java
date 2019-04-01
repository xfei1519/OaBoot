package com.syc.oa.mapper;

import com.syc.oa.model.TEmployee;
import com.syc.oa.model.TEmployeeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TEmployeeMapper {
    long countByExample(TEmployeeExample example);

    int deleteByExample(TEmployeeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TEmployee record);

    int insertSelective(TEmployee record);

    List<TEmployee> selectByExample(TEmployeeExample example);

    TEmployee selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TEmployee record, @Param("example") TEmployeeExample example);

    int updateByExample(@Param("record") TEmployee record, @Param("example") TEmployeeExample example);

    int updateByPrimaryKeySelective(TEmployee record);

    int updateByPrimaryKey(TEmployee record);
}