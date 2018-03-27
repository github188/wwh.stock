package com.bm.wanma.utils;

import java.util.ArrayList;
import java.util.List;

import android.graphics.drawable.Drawable;


/**
 * @author cm
 * 获取资源工具类
 */
public class GetResourceUtil {
    public static String getString(int id){
        return ProjectApplication.getInstance().getResources().getString(id);
    }
    public static String[] getStringArrays(int id){
        return ProjectApplication.getInstance().getResources().getStringArray(id);
    }
    public static List<String> getStringLists(int id){
        String[] strings = getStringArrays(id);
        List<String> list = new ArrayList<String>();
        for (int i = 0 ; i < strings.length; ++i) {
            list.add(strings[i]);
        }
        return list;
    }
    public static int getColor(int id){
        return ProjectApplication.getInstance().getResources().getColor(id);
    }
    public static Drawable getDrawable(int id){
        return ProjectApplication.getInstance().getResources().getDrawable(id);
    }
}
