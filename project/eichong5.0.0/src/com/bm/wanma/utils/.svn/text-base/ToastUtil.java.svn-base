package com.bm.wanma.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;
public class ToastUtil {
    private static String oldMsg;
    protected static Toast toast = null;
    private static long oneTime = 0;
    private static long twoTime = 0;
    public static Context mContext;

    public static void init(Context context){
        mContext = context;
    }

    public static void TshowToast(String s) {
        //s = TextUtils.isEmpty(s) ? "提示信息为空" : s;
        if (toast == null) {
            toast = Toast.makeText(mContext, s, Toast.LENGTH_SHORT);
            toast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (s.equals(oldMsg)) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                    toast.show();
                }
            } else {
                oldMsg = s;
				toast.setText(s);
				toast.show();
			}
		}
		oneTime = twoTime;
	}

    public static void showToast(String s, String defautStr) {
        if (!TextUtils.isEmpty(s)) {
            TshowToast(s);
        } else if(!TextUtils.isEmpty(defautStr)) {
           TshowToast(defautStr);
        }
    }

    public static void showToast(int resId) {
        TshowToast(mContext.getString(resId));
    }
}
