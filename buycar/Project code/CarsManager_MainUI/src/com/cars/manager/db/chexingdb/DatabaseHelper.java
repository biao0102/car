package com.cars.manager.db.chexingdb;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * First create the database
 * 
 * @author landmark
 * 
 * @date 2010-11-01
 * 
 */
public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "gamecenter.db";

	private static final int VERSION = 5;

	public static final String TABLE_SEARCH_KEYWORD = "search_keyword";

	public static final String TABLE_CHEXI = "chexi_table";

	public static DatabaseHelper instance = null;

	private DatabaseHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
	}

	public static DatabaseHelper getInstance(Context context) {
		if (instance == null) {
			instance = new DatabaseHelper(context);
		}
		return instance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		createChexingsTable(db);
		createChexisTable(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String chexingSql = getChexingSql(db);
		if (chexingSql == null) {
			db.execSQL("alter table " + TABLE_SEARCH_KEYWORD + " add search_time long(32) default '0'");
		}

		String chexiSql = getChexiSql(db);
		if (chexiSql == null) {
			db.execSQL("alter table " + TABLE_CHEXI + " add search_time long(32) default '0'");
		}
	}

	private void createChexingsTable(SQLiteDatabase db) {
		db.execSQL("create table if not exists " + TABLE_SEARCH_KEYWORD//
				+ "(_id integer,"//
				+ "brandId varchar(20)," // 搜索关键字
				+ "name varchar(70)" // 搜索时间
				+ ")");
	}

	private void createChexisTable(SQLiteDatabase db) {
		db.execSQL("create table if not exists " + TABLE_CHEXI//
				+ "(_id integer,"//
				+ "brandId varchar(20)," // 搜索关键字
				+ "name varchar(70)" // 搜索时间
				+ ")");
	}

	private String getChexingSql(SQLiteDatabase db) {
		String sql_statement = null;
		String sql = "select sql from sqlite_master where tbl_name='" + TABLE_SEARCH_KEYWORD + "' and type='table'";
		Cursor c = db.rawQuery(sql, null);
		if (c != null && c.moveToFirst()) {
			sql_statement = c.getString(0);
		}
		return sql_statement;
	}

	private String getChexiSql(SQLiteDatabase db) {
		String sql_statement = null;
		String sql = "select sql from sqlite_master where tbl_name='" + TABLE_CHEXI + "' and type='table'";
		Cursor c = db.rawQuery(sql, null);
		if (c != null && c.moveToFirst()) {
			sql_statement = c.getString(0);
		}
		return sql_statement;
	}
}
