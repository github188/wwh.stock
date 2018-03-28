package com.ijustyce.fastandroiddev3.contentprovider;
import com.ijustyce.fastandroiddev3.baseLib.utils.ILog;
import com.ijustyce.fastandroiddev3.baseLib.utils.TaskUtils;
import com.ijustyce.fastandroiddev3.baseLib.utils.ThreadUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangchun on 16/7/7.
 */
public class CommonData {

    private static String userId = "0";
    public interface DataCall{
        void onGet(String t);
    }

    public static String get(String key, String userId){
        ArrayList<String> result = DBTools.getCommonValue(key, userId);
        return result == null || result.isEmpty() ? null : result.get(result.size() -1);
    }

    public static String get(String key){
        ArrayList<String> result = DBTools.getCommonValue(key, userId);
        return result == null || result.isEmpty() ? null : result.get(result.size() -1);
    }

    public static void get(final String key, final DataCall dataCall){
        new TaskUtils<String>(){
            @Override
            protected String doInBackground(Integer... params) {
                return CommonData.get(key);
            }

            @Override
            protected void onPostExecute(String result) {
                if (dataCall != null) dataCall.onGet(result);
            }
        }.execute();
    }

    public static void get(final String key, final String userId, final DataCall dataCall){
        new TaskUtils<String>(){
            @Override
            protected String doInBackground(Integer... params) {
                return CommonData.get(key, userId);
            }

            @Override
            protected void onPostExecute(String result) {
                if (dataCall != null) dataCall.onGet(result);
            }
        }.execute();
    }

    public static void put(final String key, final Object value) {
        if (value == null) {
            ILog.e("value is null , not save...");
            return;
        }
        ThreadUtils.execute(new Runnable() {
           @Override
           public void run() {
               DBTools.deleteCommonByKey(key, userId);
               DBTools.saveCommonData(key, String.valueOf(value), userId);
           }
       });
    }

    public static void put(final String key, final Object value, final String userId){
        if (value == null) {
            ILog.e("value is null , not save...");
            return;
        }
        ThreadUtils.execute(new Runnable() {
            @Override
            public void run() {
                DBTools.deleteCommonByKey(key, userId);
                DBTools.saveCommonData(key, String.valueOf(value), userId);
            }
        });
    }

    public static void saveCommonList(List<CommonBean> list) {
        DBTools.saveCommonList(list);
    }

    public static void remove(final String key){
        ThreadUtils.execute(new Runnable() {
           @Override
           public void run() {
               DBTools.deleteCommonByKey(key, userId);
           }
       });
    }

    public static void remove(final String key, final String userId){
        ThreadUtils.execute(new Runnable() {
            @Override
            public void run() {
                DBTools.deleteCommonByKey(key, userId);
            }
        });
    }
}
