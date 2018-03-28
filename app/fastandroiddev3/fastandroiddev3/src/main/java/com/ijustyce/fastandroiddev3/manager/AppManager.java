package com.ijustyce.fastandroiddev3.manager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.ijustyce.fastandroiddev3.R;
import com.ijustyce.fastandroiddev3.baseLib.utils.ILog;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.ListIterator;
import java.util.Map;

/**
 * Created by yc on 15-12-25.   activity管理类
 */
public class AppManager {

    public static void newActivity(Activity activity, Class<? extends Activity> mClass) {
        newActivity(activity, mClass, null);
    }

    public static void newActivity(Activity activity, Class<? extends Activity> mClass, Bundle bundle) {
        newActivity(activity, new Intent(activity, mClass), bundle);
    }

    public static void newActivity(Activity activity, Intent intent) {
        newActivity(activity, intent, null);
    }

    public static void newActivity(Activity activity, Intent intent, Bundle bundle) {
        if (activity == null || intent == null) {
            ILog.e("===AppManager===", "activity or intent is null ...");
            return;
        }
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        try {
            activity.startActivity(intent);
        } catch (Exception ignore) {
            try {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    //  保证顺序，因为我可能需要结束某几个
    private static LinkedHashMap<String, WeakReference<Activity>> allActivity;

    static {

        allActivity = new LinkedHashMap<>();
    }

    public static void add(String key, Activity activity){

        //  allActivity.remove(className);
        allActivity.put(key, new WeakReference<>(activity));
    }

    public static void finishAll(){

        if (allActivity == null){
            return;
        }

        for (WeakReference<Activity> tmp : allActivity.values()) {
            if (tmp != null && tmp.get()!= null){
                tmp.get().finish();
            }
        }
        allActivity.clear();
    }

    public static void finishExcept(Class className){

        if (allActivity == null) return;
        HashMap<String, WeakReference<Activity>> removes = new HashMap<>();
        for (Map.Entry<String, WeakReference<Activity>> tmp : allActivity.entrySet()){
            if (tmp == null || tmp.getValue() == null || tmp.getValue().get() == null) continue;
            if (!tmp.getKey().startsWith(className.getName())){
                removes.put(tmp.getKey(), tmp.getValue());
                tmp.getValue().get().finish();
            }
        }
        for(Map.Entry<String, WeakReference<Activity>> remove : removes.entrySet()){
            allActivity.remove(remove.getKey());
        }
    }

    /**
     * 结束Activity，
     * @param max   保留的个数
     */
    public static void removeLeft(Class className, int max){

        if (allActivity == null) return;
        HashMap<String, WeakReference<Activity>> removes = new HashMap<>();
        for (Map.Entry<String, WeakReference<Activity>> tmp : allActivity.entrySet()){
            if (tmp == null || tmp.getValue() == null || tmp.getValue().get() == null) {
                continue;
            }
            if (tmp.getKey().startsWith(className.getName())){
                if (max > 0){
                    max--;
                    continue;
                }
                removes.put(tmp.getKey(), tmp.getValue());
                tmp.getValue().get().finish();
            }
        }
        for(Map.Entry<String, WeakReference<Activity>> remove : removes.entrySet()){
            allActivity.remove(remove.getKey());
        }
    }

    /**
     * 结束Activity，
     * @param max   最大结束的个数
     */
    public static void remove(Class className, int max){

        if (allActivity == null) return;
        HashMap<String, WeakReference<Activity>> removes = new HashMap<>();
        ListIterator<Map.Entry<String,WeakReference<Activity>>> iterator = new ArrayList<>
                (allActivity.entrySet()).listIterator(allActivity.size());
        while(iterator.hasPrevious()) {
            Map.Entry<String, WeakReference<Activity>> tmp = iterator.previous();
            if (max < 1){
                break;
            }
            if (tmp == null || tmp.getValue() == null || tmp.getValue().get() == null) continue;
            if (tmp.getKey().startsWith(className.getName())){
                removes.put(tmp.getKey(), tmp.getValue());
                tmp.getValue().get().finish();
                max--;
            }
        }
        for(Map.Entry<String, WeakReference<Activity>> remove : removes.entrySet()){
            allActivity.remove(remove.getKey());
        }
    }

    public static void delete(String key){
        if (allActivity == null) return;
        allActivity.remove(key);
    }

    public static void remove(Class className){

        if (allActivity == null) return;
        HashMap<String, WeakReference<Activity>> removes = new HashMap<>();
        for (Map.Entry<String, WeakReference<Activity>> tmp : allActivity.entrySet()){
            if (tmp == null || tmp.getValue() == null || tmp.getValue().get() == null) continue;
            if (tmp.getKey().startsWith(className.getName())){
                removes.put(tmp.getKey(), tmp.getValue());
                tmp.getValue().get().finish();
            }
        }
        for(Map.Entry<String, WeakReference<Activity>> remove : removes.entrySet()){
            allActivity.remove(remove.getKey());
        }
    }
}