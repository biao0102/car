package com.cars.manager.db.helper;

import java.util.List;

import android.content.Context;

import com.cars.manager.db.afinal.FinalDb;
import com.cars.manager.db.table.Chexing;

/**
 * @author cyf
 * 
 *         针对车型创建的数据库
 * */
public class DBCXHelper {

	// db name
	private static final String DB_NAME = "carchexing.db";

	private static FinalDb db;

	public DBCXHelper(Context context) {
		db = FinalDb.create(context, DB_NAME);
	}

	/* 车型 */

	public void addChexingWithoutSearch(Chexing aChexing) {
		db.save(aChexing);
	}

	public void addChexing(Chexing aChexing) {
		if (selectChexingWithId(aChexing.getId()) == null) {
			db.save(aChexing);
		}
	}

	public Chexing selectChexingWithId(String id) {
		return db.findById(id, Chexing.class);
	}

	public List<Chexing> selectChexingsWithId(String brandId) {
		return db.findAllByWhere(Chexing.class, "brandId=\"" + brandId + "\"");
	}

	public List<Chexing> selectChexingsAll() {
		return db.findAll(Chexing.class);
	}

}
