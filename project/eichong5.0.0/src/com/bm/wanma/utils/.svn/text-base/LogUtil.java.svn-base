package com.bm.wanma.utils;

public class LogUtil {
    public static boolean isDebug= true; //app上线 改成 false
    public static String LOG_TAG = "cm_test";

    public static void v(String msg) {
        LogUtil.v(LOG_TAG, msg);
    }

	public static void v(String tag, String msg) {
		if (isDebug)
            try {
                android.util.Log.v(tag, msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

	public static void v(String tag, String msg, Throwable t) {
		if (isDebug)
            try {
                android.util.Log.v(tag, msg, t);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public static void d(String msg) {
        LogUtil.d(LOG_TAG, msg);
    }

	public static void d(String tag, String msg) {
		if (isDebug)
			android.util.Log.d(tag, msg);
	}

	public static void d(String tag, String msg, Throwable t) {
		if (isDebug)
            try {
                android.util.Log.d(tag, msg, t);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public static void i(String msg) {
        LogUtil.i(LOG_TAG, msg);
    }

	public static void i(String tag, String msg) {
		if (isDebug)
            try {
                android.util.Log.i(tag, msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

	public static void i(String tag, String msg, Throwable t) {
		if (isDebug)
            try {
                android.util.Log.i(tag, msg, t);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public static void w(String msg) {
        LogUtil.w(LOG_TAG, msg);
    }

	public static void w(String tag, String msg) {
		if (isDebug)
            try {
                android.util.Log.w(tag, msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

	public static void w(String tag, String msg, Throwable t) {
		if (isDebug)
            try {
                android.util.Log.w(tag, msg, t);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public static void e(String msg) {
        LogUtil.e(LOG_TAG, msg);
    }

	public static void e(String tag, String msg) {
		if (isDebug)
            try {
                android.util.Log.e(tag, msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

	public static void e(String tag, String msg, Throwable t) {
		if (isDebug)
            try {
                android.util.Log.e(tag, msg, t);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
