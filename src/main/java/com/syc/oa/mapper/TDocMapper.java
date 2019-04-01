package com.syc.oa.mapper;

import com.syc.oa.model.TDoc;
import com.syc.oa.model.TDocExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TDocMapper {
    long countByExample(TDocExample example);

    int deleteByExample(TDocExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TDoc record);

    int insertSelective(TDoc record);

    List<TDoc> selectByExampleWithBLOBs(TDocExample example);

    List<TDoc> selectByExample(TDocExample example);

    TDoc selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TDoc record, @Param("example") TDocExample example);

    int updateByExampleWithBLOBs(@Param("record") TDoc record, @Param("example") TDocExample example);

    int updateByExample(@Param("record") TDoc record, @Param("example") TDocExample example);

    int updateByPrimaryKeySelective(TDoc record);

    int updateByPrimaryKeyWithBLOBs(TDoc record);

    int updateByPrimaryKey(TDoc record);
}