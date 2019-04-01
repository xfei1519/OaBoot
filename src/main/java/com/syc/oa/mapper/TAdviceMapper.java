package com.syc.oa.mapper;

import com.syc.oa.model.TAdvice;
import com.syc.oa.model.TAdviceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TAdviceMapper {
    long countByExample(TAdviceExample example);

    int deleteByExample(TAdviceExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TAdvice record);

    int insertSelective(TAdvice record);

    List<TAdvice> selectByExampleWithBLOBs(TAdviceExample example);

    List<TAdvice> selectByExample(TAdviceExample example);

    TAdvice selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TAdvice record, @Param("example") TAdviceExample example);

    int updateByExampleWithBLOBs(@Param("record") TAdvice record, @Param("example") TAdviceExample example);

    int updateByExample(@Param("record") TAdvice record, @Param("example") TAdviceExample example);

    int updateByPrimaryKeySelective(TAdvice record);

    int updateByPrimaryKeyWithBLOBs(TAdvice record);

    int updateByPrimaryKey(TAdvice record);
}