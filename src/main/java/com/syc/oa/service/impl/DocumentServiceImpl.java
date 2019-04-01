package com.syc.oa.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.syc.oa.mapper.TDocMapper;
import com.syc.oa.mapper.TUserMapper;
import com.syc.oa.model.TDoc;
import com.syc.oa.model.TDocExample;
import com.syc.oa.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service("documentService")
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private TDocMapper tDocMapper;

    @Autowired
    private TUserMapper userMapper;

    @Override
    public PageInfo<TDoc> selectDocument(Integer pageNum, Integer pageSize, String title) {
        PageHelper.startPage(pageNum,pageSize);
        TDocExample example = new TDocExample();
        example.createCriteria().andTitleLike("%"+title+"%");
        List<TDoc> docs = tDocMapper.selectByExampleWithBLOBs(example);
        for (TDoc doc : docs) {
            doc.setUser(userMapper.selectByPrimaryKey(doc.getUid()));
        }
        return new PageInfo<>(docs);
    }

    @Override
    public boolean saveDocument(TDoc document) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            document.setCreateDate(sdf.parse(sdf.format(new Date())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        document.setUser(userMapper.selectByPrimaryKey(document.getUid()));
        return tDocMapper.insertSelective(document)>0;
    }

    @Override
    public boolean removeOne(Integer id) {
        return tDocMapper.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public boolean removeMore(Integer[] ids) {
        TDocExample example = new TDocExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        return tDocMapper.deleteByExample(example) > 0;
    }
}
