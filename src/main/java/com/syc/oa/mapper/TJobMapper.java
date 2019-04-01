package com.syc.oa.mapper;

import com.syc.oa.model.TJob;
import com.syc.oa.model.TJobExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TJobMapper {
    long countByExample(TJobExample example);

    int deleteByExample(TJobExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TJob record);

    int insertSelective(TJob record);

    List<TJob> selectByExample(TJobExample example);

    TJob selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TJob record, @Param("example") TJobExample example);

    int updateByExample(@Param("record") TJob record, @Param("example") TJobExample example);

    int updateByPrimaryKeySelective(TJob record);

    int updateByPrimaryKey(TJob record);
}