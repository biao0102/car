package com.cars.manager.db.chexingdb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * abstract database
 * 
 * @author cyf
 * 
 * 
 */
public abstract class DataBaseService {

	private DatabaseHelper dbOpenHelper;
	protected SQLiteDatabase db = null;
	private static int count = 0;

	public DataBaseService(Context context) {
		dbOpenHelper = DatabaseHelper.getInstance(context);
	}

	/**
	 * Create and/or open a database that will be used for reading and writing.
	 * Once opened successfully, the database is cached, so you can call this
	 * method every time you need to write to the database. Make sure to call
	 * {@link #close} when you no longer need it.
	 * 
	 * @return
	 */
	public SQLiteDatabase open() {
		db = dbOpenHelper.getWritableDatabase();
		count++;
		return db;
	}

	/**
	 * Close any open database object.
	 */
	public void close() {
		count--;
		if (count <= 0) {
			db.close();
		}
	}

}
