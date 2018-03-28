package com.ijustyce.fastandroiddev3.contentprovider;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;

/**
 * Created by yangchun on 16/5/18.  内容提供者：字号、城市
 */
public class IProvider extends ContentProvider {

    private DBManager dbManager;
    private final static String DB_NAME = "I_data";
    private final static int COMMON = 3;
    private final static String TAB_COMMON = "common";
    private static UriMatcher mBrandMatcher = null;

    @Override
    public int delete(@NonNull Uri uri, String where, String[] whereArgs) {
        SQLiteDatabase database = dbManager.getWritableDatabase();
        if (database.isReadOnly()) return -1;
        switch (mBrandMatcher.match(uri)) {

            case COMMON:
                return database.delete(TAB_COMMON, where, whereArgs);

            default:
                throw new IllegalArgumentException("UnKnown uri: " + uri);
        }
    }

    @Override
    public boolean onCreate() {
        dbManager = new DBManager(getContext(), DB_NAME, null);
        mBrandMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mBrandMatcher.addURI(getContext().getPackageName() + ".contentprovider", TAB_COMMON, COMMON);
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteDatabase database = dbManager.getWritableDatabase();
        switch (mBrandMatcher.match(uri)) {

            case COMMON:
                return database.query(TAB_COMMON, projection, selection, selectionArgs, null, null, sortOrder);

            default:
                throw new IllegalArgumentException("UnKnown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return "com.lzhplus.lzh.brand";
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {

        int code = mBrandMatcher.match(uri);
        switch (code) {

            case COMMON:
                return doInsert(uri, values, TAB_COMMON);
        }
        return null;
    }

    @Override
    @NonNull
    public ContentProviderResult[] applyBatch(@NonNull ArrayList<ContentProviderOperation> operations) {
        SQLiteDatabase db = dbManager.getWritableDatabase();
        db.beginTransaction();//开始事务
        try {
            ContentProviderResult[] results = super.applyBatch(operations);
            db.setTransactionSuccessful();//设置事务标记为successful
            return results;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            db.endTransaction();//结束事务
        }
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        int code = mBrandMatcher.match(uri);
        switch (code) {

            case COMMON:
                doInsert(uri, values, TAB_COMMON);
                break;
        }
        return values == null ? 0 : values.length;
    }

    private void doInsert(Uri uri, ContentValues[] values, String tab) {
        SQLiteDatabase database = dbManager.getWritableDatabase();
        database.beginTransaction();
        for (ContentValues tmp : values) {
            database.insert(tab, null, tmp);
            tmp.clear();
        }
        database.setTransactionSuccessful();//设置事务标记为Successful
        database.endTransaction();//提交事务
        Context context = getContext();
        ContentResolver cr = context == null ? null : context.getContentResolver();
        if (cr != null) cr.notifyChange(uri, null);
    }

    private Uri doInsert(Uri uri, ContentValues values, String tab) throws SQLiteConstraintException{
        SQLiteDatabase database = dbManager.getWritableDatabase();
        database.beginTransaction();
        long id = database.insert(tab, null, values);
        database.setTransactionSuccessful();//设置事务标记为Successful
        database.endTransaction();//提交事务
        values.clear();
        Context context = getContext();
        ContentResolver cr = context == null ? null : context.getContentResolver();
        if (cr != null) cr.notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase database = dbManager.getWritableDatabase();
        switch (mBrandMatcher.match(uri)) {

            case COMMON:
                return database.update(TAB_COMMON, values, selection, selectionArgs);

            default:
                throw new IllegalArgumentException("UnKnown uri: " + uri);
        }
    }
}
