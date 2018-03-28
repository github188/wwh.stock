package com.ijustyce.fastandroiddev3.contentprovider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.ijustyce.fastandroiddev3.IApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangchun on 16/6/19.
 */
class DBTools {

    private static ContentResolver contentResolver;
    private static String COMMON;

    static {
        contentResolver = IApplication.getInstance().getContentResolver();
        COMMON = "content://" + IApplication.getInstance().getPackageName() + ".contentprovider/common";
    }

    static void saveCommonData(String key, String value, String userId) {
        ContentValues values = new ContentValues();
        values.put("key", key);
        values.put("value", value);
        values.put("userId", userId);
        contentResolver.insert(Uri.parse(COMMON), values);
        values.clear();
    }

    static ArrayList<String> getCommonValue(String key, String userId) {
        Uri uri = Uri.parse(COMMON);
        String[] projection = {"_id", "key", "value"};
        String sortOrder = "_id ASC";
        Cursor cursor = contentResolver.query(uri, projection, "key = '" + key + "' and userId = '" + userId + "'", null, sortOrder);
        ArrayList<String> result = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String value = cursor.getString(cursor.getColumnIndex("value"));
                result.add(value);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return result;
    }

    public static void deleteAllCommon(String userId) {
        Uri uri = Uri.parse(COMMON);
        try {
            String sql = userId == null ? null : "userId = '" + userId + "'";
            contentResolver.delete(uri, sql, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveCommonList(final List<CommonBean> beans) {
        if (beans == null || beans.isEmpty()) return;
        ContentValues[] array = new ContentValues[beans.size()];
        int index = 0;
        for (CommonBean bean : beans) {

            ContentValues values = new ContentValues();
            values.put("key", bean.key);
            values.put("value", bean.value);
            values.put("userId", bean.userId);
            array[index] = values;
            index++;
        }
        try {
            contentResolver.bulkInsert(Uri.parse(COMMON), array);
            beans.clear();
        }catch (Exception e) {
            beans.clear();
            e.printStackTrace();
        }
    }

    static void deleteCommonByKey(String key, String userId) {
        Uri uri = Uri.parse(COMMON);
        try {
            contentResolver.delete(uri, "key = '" + key + "' and userId = '" + userId + "'", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
