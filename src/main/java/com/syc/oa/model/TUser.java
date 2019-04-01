package com.syc.oa.model;

import lombok.Data;

import java.util.Date;

@Data
public class TUser {
    private Integer id;

    private String username;

    private String password;

    private String loginName;

    private Integer status;

    private Date createDate;

}