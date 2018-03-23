package com.zjhcsoft.struc.util;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

/**
 * 基本工具类
 * 
 * @author qiaoel@zjhcsoft.com
 * 
 */
public final class SignUtil {

    /**
     * 签名生成算法
     * 
     * @param HashMap<String,String> params 请求参数集，所有参数必须已转换为字符串类型
     * @param String secret 签名密钥
     * @return 签名
     * @throws IOException
     */
    public static String getSignature(Map<String, String> params, String secret) throws IOException {
        // 先将参数以其参数名的字典序升序进行排序
        Map<String, String> sortedParams = new TreeMap<String, String>(params);
        Set<Entry<String, String>> entrys = sortedParams.entrySet();

        // 遍历排序后的字典，将所有参数按"keyvalue"格式拼接在一起
        StringBuilder basestring = new StringBuilder();
        
        basestring.append(secret);//参数串前秘钥
        for (Entry<String, String> param : entrys) {
        	// 空参数忽略
        	if(param.getValue() !=null && !"".equals(param.getValue().trim()) ){
        		basestring.append(param.getKey()).append(param.getValue());
        	}
        }
        basestring.append(secret);//参数串后秘钥

        // 使用MD5对待签名串求签
        byte[] bytes = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            bytes = md5.digest(basestring.toString().getBytes("UTF-8"));
        } catch (GeneralSecurityException ex) {
            throw new IOException(ex);
        }

        // 将MD5输出的二进制结果转换为小写的十六进制
        return toHexString(bytes);
    }

    /**
     * 将字节流转换成16进制字符串
     * 
     * @param bytes 字节流
     * @return 16进制字符串
     */
    public static String toHexString(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex);
        }
        return sign.toString();
    }

}
