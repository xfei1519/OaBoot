package com.syc.oa.service;

import com.github.pagehelper.PageInfo;
import com.syc.oa.model.TDoc;

public interface DocumentService {
    PageInfo<TDoc> selectDocument(Integer pageNum, Integer pageSize, String title);

    boolean saveDocument(TDoc document);

    boolean removeOne(Integer id);

    boolean removeMore(Integer[] ids);
}
