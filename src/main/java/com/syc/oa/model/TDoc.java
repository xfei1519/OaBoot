package com.syc.oa.model;

import lombok.Data;

import java.util.Date;

@Data
public class TDoc {
    private Integer id;

    private String title;

    private String fileName;

    private Date createDate;

    private Integer uid;

    private String remark;

    private TUser user;

}