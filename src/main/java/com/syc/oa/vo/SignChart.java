package com.syc.oa.vo;

import lombok.Data;

@Data
public class SignChart {
    //签到打卡日期
    private String day;
    //打卡人数
    private Long num;
}
