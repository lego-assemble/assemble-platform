package xyz.leo.lego.assemble.platform.common.util;

import org.springframework.util.Base64Utils;
import xyz.leo.lego.assemble.platform.common.util.DesUtils;

/**
 * @author xuyangze
 * @date 2019/5/13 3:54 PM
 */
public class EncryptUtils {
    private static final String DES_PWD = "521b469a";

    /**
     * 加密
     * @param source
     * @return
     */
    public static String encrypt(String source) {
        return encrypt(source, DES_PWD);
    }

    /**
     * 解密
     * @param source
     * @return
     */
    public static String decrypt(String source) {
        return decrypt(source, DES_PWD);
    }

    /**
     * 加密
     * @param source
     * @param password
     * @return
     */
    public static String encrypt(String source, String password) {
        byte []des = DesUtils.encrypt(source.getBytes(), password);
        return Base64Utils.encodeToString(des);
    }

    /**
     * 解密
     * @param source
     * @param password
     * @return
     */
    public static String decrypt(String source, String password) {
        byte []des = Base64Utils.decodeFromString(source);
        byte []data = DesUtils.decrypt(des, password);
        return new String(data);
    }

    public static void main(String []args) {
        System.out.println(encrypt("loveinsky100@163.com"));
        System.out.println(encrypt("13586005013"));
    }
}
