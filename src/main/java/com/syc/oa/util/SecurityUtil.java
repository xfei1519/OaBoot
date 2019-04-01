package com.syc.oa.util;

import com.alibaba.druid.filter.config.ConfigTools;

/**
 *
 */
public class SecurityUtil {

    //一般情况下,公钥用来加密,私钥用来解密.
    //但是在支付的ConfigTools的api中,反过来了.

    //支付宝的工具类中,私钥用来加密了
    private static final String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJUs+nhdxxjzZOJk8QvL3Km9LHrJi+TsQXHGr6AeHLAT+7+8KGJFnJh8ZMDBfJ5mHSvB5A9NY/Vvgy3YFNg0zrDr4RMWTapeURtvYpvQm35eCLjNspi+3Wdwc30dePdYR+W6vum+T7zzfSYNwLKBm0X5sOcLdaIQoYa/uk4ouDhDAgMBAAECgYEAhlP4P0cCr58MayjGPf8W9unYEcFHbZ6o19E3+JDk+FEmUuMV4lgYyN7Kwx/4HZKKFgsEWBsyNaJyl2eKbZ4vt5L5SxhHF2Xo+G1a2OBBd1OwDw/3XSdj5mh9kqwe3I2BXRVTGUmIcflYgXz9ltgjW+KlSLjLNtdVks1vPResq3ECQQDYxteAYDlswCVs8c6E6NbL55i/Tz3ekCtGWWIkDDLBsF2glEYqFbJsthDLiJ+vfilbSC3NItfh6jg9BqkBr7IvAkEAsCrWkLQsRPvG/BWXU6b2FTC4o6e+B6RyNHp7IXdZf/udRvEvOULQiaFFEuMlUahIK/xzdnlhvVH0QmeHc2z6LQJAdAmUR7K6an08S2Hwo3KjsAhr2lRHkQBpOcIU56jj7oxCwfc8y8BLfyfCO8EJgX4uEx/C7KDvII4oxnnvM+jhnwJACTm1Es7coqAsw3a9/ft4V5O9l5RpNy09bdIcMJx3a2RZ0CVBO9Zr5Uk0vNB4W9ZcMTF/Om0Q2UE5cWDKjxjd4QJAUOk1/bGco9eIgOUfbqxB0BRm3Zl1swGYrmezNzqSgDbpnOAomhnjiYdXx4S/qHsMy074dpsz8lLd0s79nOqCfg==";

    //支付宝的工具类中,公钥用来解密了
    private static final String publicKey="";

    //真正进行对明文加密的方法
    public static String encrypt(String plainPwd) {
        try {
            return ConfigTools.encrypt(privateKey, plainPwd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String cipherPwd) {
        try {
            return ConfigTools.decrypt(publicKey,cipherPwd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String cipherPwd = encrypt("syc");
        System.out.println("password=" + cipherPwd);
    }
}
