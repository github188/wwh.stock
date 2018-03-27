package com.bm.wanma.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bm.wanma.net.Protocol;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static DatabaseHelper mInstance = null;

	/** 数据库名称 **/

	//public static final String DATABASE_NAME = "eichong.db";

	/** 数据库版本号 **/
	//private static final int DATABASE_VERSION = 1;
	private Context mcontext;

	/** 数据库SQL语句 添加一个表 **/
	/*
	 * private static final String NAME_TABLE_CREATE =
	 * "create table tb_m_city(PROVINCE_ID varchar(20)," +
	 * "CITY_ID varchar(20),"+"CITY_NAME varchar(40),"+
	 * "mp INTEGER DEFAULT 100," + "number INTEGER);";
	 */
	/*
	 * private static final String NAME_TABLE_CREATE =
	 * "create table tb_m_province(PROVINCE_ID varchar(20)," +
	 * "PROVINCE_NAME varchar(20);" ;
	 */

	public DatabaseHelper(Context context,int numb) {
		super(context, Protocol.DATABASE_NAME, null, numb);
		mcontext = context;
	}

	/** 单例模式 **/

	public static synchronized DatabaseHelper getInstance(Context context,int nub) {
		if (mInstance == null) {
			mInstance = new DatabaseHelper(context,nub);
		}
		return mInstance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		/** 向数据中添加表 **/
		// db.execSQL(NAME_TABLE_CREATE);
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(mcontext.getAssets()
					.open("android_city.sql")));
			// System.out.println("路径:" + Configuration.DB_PATH + "/" +
			// schemaName);
			String line;
			String buffer = "";
			while ((line = in.readLine()) != null) {
				buffer += line;
				if (line.trim().endsWith(";")) {
					db.execSQL(buffer.replace(";", ""));
					buffer = "";
				}
			}
		} catch (IOException e) {
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
			}
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		/** 可以拿到当前数据库的版本信息 与之前数据库的版本信息 用来更新数据库 **/

	}

	/**
	 * 判断某张表是否存在
	 * @param tabName
	 *            表名
	 * @return
	 */
	public boolean tabIsExist(String tabName) {
		boolean result = false;
		if (tabName == null) {
			return false;
		}
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = this.getReadableDatabase();// 此this是继承SQLiteOpenHelper类得到的
			String sql = "select count(*) as c from sqlite_master where type ='table' and name ='"
					+ tabName.trim() + "' ";
			cursor = db.rawQuery(sql, null);
			if (cursor.moveToNext()) {
				int count = cursor.getInt(0);
				if (count > 0) {
					result = true;
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}

	/**
	 * 删除数据库
	 * 
	 * @param context
	 * @return
	 */
	public boolean deleteDatabase(Context context) {
		return context.deleteDatabase(Protocol.DATABASE_NAME);
	}
}