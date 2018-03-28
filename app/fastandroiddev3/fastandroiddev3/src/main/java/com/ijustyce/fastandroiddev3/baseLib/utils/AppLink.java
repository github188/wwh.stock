package com.ijustyce.fastandroiddev3.baseLib.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by yangchun on 2016/12/19.
 */

public class AppLink {

    public static boolean newActivity(Activity activity, Class<? extends Activity> mClass) {
        if (activity == null || mClass == null) return false;
        Intent intent = new Intent(activity, mClass);
        activity.startActivity(intent);
        return true;
    }

    public static boolean newActivity(Activity activity, Class<? extends Activity> mClass, Bundle params) {
        if (activity == null || mClass == null) return false;
        Intent intent = new Intent(activity, mClass);
        if (params != null && !params.isEmpty()) {
            intent.putExtras(params);
        }
        activity.startActivity(intent);
        return true;
    }
}
