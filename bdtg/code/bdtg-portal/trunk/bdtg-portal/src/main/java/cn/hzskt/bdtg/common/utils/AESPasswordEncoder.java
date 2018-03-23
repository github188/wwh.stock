package cn.hzskt.bdtg.common.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by xwh on 2016-04-13.
 */
public class AESPasswordEncoder  {
    private static String KEY = "~i6n5txn2td3q:zh";

    public AESPasswordEncoder() {
    }

    public static String encode(String password) {
        try {
            byte[] e = KEY.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(e, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(1, skeySpec);
            byte[] encrypted = cipher.doFinal(password.getBytes());
            return byte2hex(encrypted).toLowerCase();
        } catch (Exception var6) {
            throw new RuntimeException(var6);
        }
    }

    private static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";

        for(int n = 0; n < b.length; ++n) {
            stmp = Integer.toHexString(b[n] & 255);
            if(stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }

        return hs.toUpperCase();
    }
}

