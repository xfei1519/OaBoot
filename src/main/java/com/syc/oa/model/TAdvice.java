package com.syc.oa.model;

import lombok.Data;

import java.util.Date;

@Data
public class TAdvice {
    private Integer id;

    private String title;

    private Date createDate;

    private Integer uid;

    private String content;

    private TUser user;

}