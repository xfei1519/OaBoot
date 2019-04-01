package com.syc.oa.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.security.*;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA算法工具类
 */
public class RSAKeyUtil {

    private static final String ALGORITHM = "RSA";
    private static final String PUBLIC_KEY = "publicKey";
    private static final String PRIVATE_KEY = "privateKey";

    public static Map<String, Object> initKey() {
        try {
            //得到一个RSA算法的密钥对
            KeyPairGenerator instance = KeyPairGenerator.getInstance(ALGORITHM);
            //设置秘钥的长度,一般最低512位
            instance.initialize(1024);
            //得到密钥对
            KeyPair keyPair = instance.generateKeyPair();
            //得到公钥,一般公钥加密
            PublicKey publicKey = keyPair.getPublic();
            //得到私钥,私钥解密
            PrivateKey privateKey = keyPair.getPrivate();
            Map<String, Object> map = new HashMap<>();
            map.put(PUBLIC_KEY, publicKey);
            map.put(PRIVATE_KEY, privateKey);
            return map;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    //获取字符串类型的公钥
    public static String getPublicKey(Map<String, Object> map) {
        Key publicKey = (Key) map.get(PUBLIC_KEY);
        return base64Encrypt(publicKey.getEncoded());
    }

    //获取字符串类型的私钥
    public static String getPrivateKey(Map<String, Object> map) {
        Key privateKey = (Key) map.get(PRIVATE_KEY);
        return base64Encrypt(privateKey.getEncoded());
    }

    //利用Base64编码方法,得到一个编码后的字符串
    private static String base64Encrypt(byte[] encoded) {
        return new BASE64Encoder().encodeBuffer(encoded);
    }

    //利用Base64编码方法,对字符串进行解码的方法
    private static byte[] base64Decrypt(String key) throws Exception {
        return new BASE64Decoder().decodeBuffer(key);
    }

    public static void main(String[] args){
        Map<String, Object> map = initKey();
        String publicKey = getPublicKey(map);
        String privateKey = getPrivateKey(map);
        System.out.println("publicKey="+publicKey);
        System.out.println("privateKey="+privateKey);
    }
}
