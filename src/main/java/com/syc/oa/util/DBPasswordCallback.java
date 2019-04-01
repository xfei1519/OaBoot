package com.syc.oa.util;

import com.alibaba.druid.filter.config.ConfigTools;
import com.alibaba.druid.util.DruidPasswordCallback;

import java.util.Properties;

/**
 *
 */
public class DBPasswordCallback extends DruidPasswordCallback {

    @Override
    public void setProperties(Properties properties) {
        super.setProperties(properties);
        //根据spring.datasource.druid.connection-properties=config.decrypt=true;
        // publickey=${spring.datasource.publicKey};password=${spring.datasource.druid.password}
        //中的password取值
        //注意:此时取出的密码是密文
        String password = properties.getProperty("password");
        //此时如果不进行解密操作,直接把password传递给mysql服务器,会产生AccessDenyExcetion.
        String publickey = properties.getProperty("publickey");
        if (password != null && publickey != null) {
            //加密方法
            //ConfigTools.encrypt();
            //解密方法
            //此时进过解密,得到解密后的密码明文
            String plainPwd = null;
            try {
                plainPwd = ConfigTools.decrypt(publickey, password);
                //char[]与String="xxx"?
                setPassword(plainPwd.toCharArray());
                System.out.println("pwd=" + plainPwd);
            } catch (Exception e) {
                e.printStackTrace();
                setPassword(plainPwd.toCharArray());
            }
        }
    }
}
