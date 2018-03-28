package com.ijustyce.weekly1601.jni;

/**
 * Created by yangchun on 2017/4/13.
 */

public class JniUtils {
    private volatile static JniUtils mJniUtils;
    static {
        System.loadLibrary("JniUtils");
    }

    private JniUtils(){
    }
    public static JniUtils getInstance(){
        JniUtils result = mJniUtils;
        if (result == null){
            synchronized (JniUtils.class){
                result = mJniUtils;
                if (result == null){
                    mJniUtils = result = new JniUtils();
                }
            }
        }
        return result;
    }

    public native int add(int a, int b);
}