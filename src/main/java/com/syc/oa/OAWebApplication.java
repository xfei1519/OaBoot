package com.syc.oa;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//spring-boot项目入口文件
@MapperScan("com.syc.oa.mapper")
@SpringBootApplication
public class OAWebApplication {
    //入口函数
    public static void main(String[] args) {
        SpringApplication.run(OAWebApplication.class,args);
    }
}
