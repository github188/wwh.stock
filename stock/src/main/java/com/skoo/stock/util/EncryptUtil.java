package com.skoo.stock.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

public class EncryptUtil {

    private static String AES = "AES";
    private static String EN_STR = "sgiwar8d324@3%45";//密钥

    /**
     * 加密
     *
     * @param oriStr 需要加密的内容
     * @return 加密串
     */
    public static String encrypt(String oriStr) {
        return encrypt(oriStr, EN_STR);
    }

    /**
     * 解密
     *
     * @param enStr 待解密内容
     * @return 解密串
     */
    public static String decrypt(String enStr) {
        return decrypt(enStr, EN_STR);
    }

    /**
     * 加密
     *
     * @param oriStr   需要加密的内容
     * @param password 加密密码
     * @return 加密串
     */
    public static String encrypt(String oriStr, String password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance(AES);
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(password.getBytes());
            kgen.init(128, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, AES);
            Cipher cipher = Cipher.getInstance(AES);// 创建密码器
            byte[] byteContent = oriStr.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return parseByte2HexStr(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 解密
     *
     * @param enStr    待解密内容
     * @param password 解密密钥
     * @return 解密串
     */
    public static String decrypt(String enStr, String password) {
        try {
            byte[] content = parseHexStr2Byte(enStr);
            KeyGenerator kgen = KeyGenerator.getInstance(AES);
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(password.getBytes());
            kgen.init(128, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, AES);
            Cipher cipher = Cipher.getInstance(AES);// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(content);
            return new String(result); // 加密
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 将二进制转换成16进制
     *
     * @param buf 二进制
     * @return 16进制串
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr 16进制串
     * @return 转换后二进制
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    public static void main(String args[]) {


        String bbb = EncryptUtil.encrypt("7532159", "sdiwer8i324@3%45");
        String aaa = EncryptUtil.decrypt(bbb, "sdiwer8i324@3%45");
        System.out.println(bbb);
        System.out.println(aaa);
    }

}
