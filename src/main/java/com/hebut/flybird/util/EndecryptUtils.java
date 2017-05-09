package com.hebut.flybird.util;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * Created by WangJL on 2017/5/6.
 */
public class EndecryptUtils {
    /**
     * base64进制加密
     *
     * @param password 明文密码
     * @return 加密密码
     */
    public static String encrytBase64(String password) {
        byte[] bytes = password.getBytes();
        return Base64.encodeToString(bytes);
    }

    /**
     * base64进制解密
     *
     * @param cipherText 加密密码
     * @return 明文密码
     */
    public static String decryptBase64(String cipherText) {
        return Base64.decodeToString(cipherText);
    }

    /**
     * 16进制加密
     *
     * @param password 明文密码
     * @return 加密密码
     */
    public static String encrytHex(String password) {
        byte[] bytes = password.getBytes();
        return Hex.encodeToString(bytes);
    }

    /**
     * 16进制解密
     *
     * @param cipherText 加密密码
     * @return 明文密码
     */
    public static String decryptHex(String cipherText) {
        return new String(Hex.decode(cipherText));
    }

    /**
     * 生成安全随机码
     *
     * @return 安全随机码
     */
    public static String generateRandomNumber() {
        SecureRandomNumberGenerator secureRandomNumberGenerator = new SecureRandomNumberGenerator();
        String salt = secureRandomNumberGenerator.nextBytes().toHex();
        return salt;
    }

    /**
     * 对密码进行md5加密 base64编码存储
     * 默认使用username + salt混淆模式
     *
     * @param username       用户名
     * @param password       密码
     * @param salt           盐
     * @param hashIterations 迭代次数
     * @return 加密密码
     */
    public static String md5Base64Password(String username, String password, String salt, int hashIterations) {
        String password_cryto = new Md5Hash(password, username + salt, hashIterations).toBase64();
        return password_cryto;
    }

    /**
     * 对密码进行md5加密 hex编码存储
     *
     * @param username       用户名
     * @param password       密码
     * @param salt           盐
     * @param hashIterations 迭代次数
     * @return 加密密码
     */
    public static String md5HexPassword(String username, String password, String salt, int hashIterations) {
        String password_cryto = new Md5Hash(password, username + salt, hashIterations).toHex();
        return password_cryto;
    }
}
