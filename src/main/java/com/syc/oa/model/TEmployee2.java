package com.syc.oa.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class TEmployee2 {
    private Integer id;

    private String name;

    private Integer gender;

    private Date birthday;

    private String address;

    private String cardId;

    private String education;

    private String phone;

    private String email;

    private String qq;

    private String hobby;

    private String party;

    private String race;

    private String remark;

    private String speciality;

    private Date createDate;

    private TDept dept;

    private TJob job;

    private TUser user;

}