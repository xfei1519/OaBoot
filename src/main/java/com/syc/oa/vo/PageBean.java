package com.syc.oa.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PageBean<T> {
    //数据的总条数
    private long total;
    //分页的数据源
    private List<T> rows;
}
