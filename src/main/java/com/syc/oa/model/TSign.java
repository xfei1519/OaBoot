package com.syc.oa.model;

import lombok.Data;

import java.util.Date;

@Data
public class TSign {
    private Integer id;

    private Integer flag;

    private Date createTime;

    private Integer uid;

    private TUser user;
}