package com.bm.wanma.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;


public class IntentUtil {
    public static void startIntent(Context context,Class<?> c){
        Intent intent = new Intent(context,c);
        context.startActivity(intent);
        ((Activity)context).overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    public static void startIntent(Context context,Intent intent){
        context.startActivity(intent);
        ((Activity)context).overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    public static void startIntent(Intent intent){
        Context context = ProjectApplication.getInstance().getApplicationContext();
        // context调用startActivity必须加这个flag
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
