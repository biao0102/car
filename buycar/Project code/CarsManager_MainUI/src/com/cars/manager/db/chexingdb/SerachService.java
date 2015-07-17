package com.cars.manager.db.chexingdb;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import com.cars.manager.db.table.Chexi;
import com.cars.manager.db.table.Chexing;

/**
 * Search keyword database
 * 
 * @author landmark
 * 
 * @date 2010-11-18
 * 
 */
public class SerachService extends DataBaseService {

	private static final String TABLE_NAME = DatabaseHelper.TABLE_SEARCH_KEYWORD;

	private static final String CHEXI_TABLE_NAME = DatabaseHelper.TABLE_CHEXI;

	public SerachService(Context context) {
		super(context);
	}

	/******************************** 车型 ***************************************/
	/**
	 * 获取所有车型
	 * 
	 * @return
	 */
	public ArrayList<Chexing> getChexingsAll() {
		ArrayList<Chexing> items = null;
		Cursor cursor = null;
		try {
			db = this.open();
			cursor = db.rawQuery("select _id,brandId,name from " + TABLE_NAME, null);
			if (cursor != null && cursor.moveToFirst()) {
				items = new ArrayList<Chexing>();
				do {
					Chexing aChexing = new Chexing();
					aChexing.setId(cursor.getString(0));
					aChexing.setBrandId(cursor.getString(1));
					aChexing.setName(cursor.getString(2));
					items.add(aChexing);
				} while (cursor.moveToNext());
			}
		} finally {
			if (cursor != null)
				cursor.close();
			this.close();
		}
		return items;
	}

	/**
	 * 根据品牌ID获取车型
	 * 
	 * */
	public ArrayList<Chexing> getChexingsWithBrandId(String brandId) {
		ArrayList<Chexing> items = new ArrayList<Chexing>();
		Cursor cursor = null;
		try {
			db = this.open();
			cursor = db.rawQuery("select * from " + TABLE_NAME + " where brandId = '" + brandId + "'", null);
			if (cursor != null && cursor.moveToFirst()) {
				do {
					Chexing aChexing = new Chexing();
					aChexing.setId(cursor.getString(0));
					aChexing.setBrandId(cursor.getString(1));
					aChexing.setName(cursor.getString(2));
					items.add(aChexing);
				} while (cursor.moveToNext());
			}
		} finally {
			if (cursor != null)
				cursor.close();
			this.close();
		}
		return items;
	}

	/**
	 * 根据ID获取车型
	 * 
	 * */
	public Chexing getChexingsWithId(String id) {
		Chexing aChexing = new Chexing();
		Cursor cursor = null;
		try {
			db = this.open();
			cursor = db.rawQuery("select * from " + TABLE_NAME + " where _id = '" + id + "'", null);
			if (cursor != null && cursor.moveToFirst()) {
				do {
					aChexing.setId(cursor.getString(0));
					aChexing.setBrandId(cursor.getString(1));
					aChexing.setName(cursor.getString(2));
				} while (cursor.moveToNext());
			}
		} finally {
			if (cursor != null)
				cursor.close();
			this.close();
		}
		return aChexing;
	}

	/**
	 * 保存车型
	 * 
	 * @param chexings
	 */
	public void saveChexing(ArrayList<Chexing> chexings) throws Exception {
		db = this.open();
		String sql = "insert into " + TABLE_NAME + "(_id,brandId,name) values(?,?,?)";
		SQLiteStatement stat = db.compileStatement(sql);
		db.beginTransaction();

		for (Chexing chexing : chexings) {
			stat.bindString(1, chexing.getId());
			stat.bindString(2, chexing.getBrandId());
			stat.bindString(3, chexing.getName());
			stat.executeInsert();
		}
		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();
	}

	/**
	 * 保存前检测保存车系
	 * 
	 * @param chexis
	 */
	public void saveChexingWithSearch(ArrayList<Chexing> chexings) throws Exception {
		for (int i = 0; i < chexings.size(); i++) {
			if (getChexisWithId(chexings.get(i).getId()) == null) {
				saveSingleChexing(chexings.get(i));
			} else {
				updateSingleChexing(chexings.get(i));
			}
		}
	}

	public void deleteAllChexing() {
		try {
			db = this.open();
			String sql = "delete from " + TABLE_NAME;
			db.execSQL(sql);
		} finally {
			this.close();
		}
	}

	/**
	 * 单系保存车型
	 * */
	public void saveSingleChexing(Chexing achexing) throws Exception {
		db = this.open();
		String sql = "insert into " + TABLE_NAME + "(_id,brandId,name) values(?,?,?)";
		SQLiteStatement stat = db.compileStatement(sql);
		db.beginTransaction();

		stat.bindString(1, achexing.getId());
		stat.bindString(2, achexing.getBrandId());
		stat.bindString(3, achexing.getName());
		stat.executeInsert();
		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();
	}

	/**
	 * 单系更新车系
	 * */
	public void updateSingleChexing(Chexing achexing) throws Exception {
		try {
			db = this.open();
			db.execSQL("update " + TABLE_NAME + " set brandId = ? ,name = ? where _id = ?",
					new Object[] { achexing.getBrandId(), achexing.getName(), achexing.getId() });
		} finally {
			this.close();
		}
	}

	/******************************** 车系 ****************************************/
	/**
	 * 批量保存车系
	 * 
	 * @param chexis
	 */
	public void saveChexi(ArrayList<Chexi> chexis) throws Exception {
		db = this.open();
		String sql = "insert into " + CHEXI_TABLE_NAME + "(_id,brandId,name) values(?,?,?)";
		SQLiteStatement stat = db.compileStatement(sql);
		db.beginTransaction();

		for (Chexi chexi : chexis) {
			stat.bindString(1, chexi.getId());
			stat.bindString(2, chexi.getBrandId());
			stat.bindString(3, chexi.getName());
			stat.executeInsert();
		}
		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();
	}

	/**
	 * 保存前检测保存车系
	 * 
	 * @param chexis
	 */
	public void saveChexiWithSearch(ArrayList<Chexi> chexis) throws Exception {
		for (int i = 0; i < chexis.size(); i++) {
			if (getChexisWithId(chexis.get(i).getId()) == null) {
				saveSingleChexi(chexis.get(i));
			} else {
				updateSingleChexi(chexis.get(i));
			}
		}
	}

	/**
	 * 单系保存车系
	 * */
	public void saveSingleChexi(Chexi achexi) throws Exception {
		db = this.open();
		String sql = "insert into " + CHEXI_TABLE_NAME + "(_id,brandId,name) values(?,?,?)";
		SQLiteStatement stat = db.compileStatement(sql);
		db.beginTransaction();

		stat.bindString(1, achexi.getId());
		stat.bindString(2, achexi.getBrandId());
		stat.bindString(3, achexi.getName());
		stat.executeInsert();
		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();
	}

	/**
	 * 单系更新车系
	 * */
	public void updateSingleChexi(Chexi achexi) throws Exception {
		try {
			db = this.open();
			db.execSQL("update " + CHEXI_TABLE_NAME + " set brandId = ? ,name = ? where _id = ?",
					new Object[] { achexi.getBrandId(), achexi.getName(), achexi.getId() });
		} finally {
			this.close();
		}
	}

	/**
	 * 根据brandId获取车系列表
	 * 
	 * @param brandId
	 */
	public ArrayList<Chexi> getChexisWithBrandId(String brandId) {
		ArrayList<Chexi> items = new ArrayList<Chexi>();
		Cursor cursor = null;
		try {
			db = this.open();
			cursor = db.rawQuery("select * from " + CHEXI_TABLE_NAME + " where brandId = '" + brandId + "'", null);
			if (cursor != null && cursor.moveToFirst()) {
				do {
					Chexi aChexi = new Chexi();
					aChexi.setId(cursor.getString(0));
					aChexi.setBrandId(cursor.getString(1));
					aChexi.setName(cursor.getString(2));
					items.add(aChexi);
				} while (cursor.moveToNext());
			}
		} finally {
			if (cursor != null)
				cursor.close();
			this.close();
		}
		return items;
	}

	/**
	 * 根据Id获取车系
	 * 
	 * @param brandId
	 */
	public Chexi getChexisWithId(String id) {
		Chexi aChexi = null;
		Cursor cursor = null;
		try {
			db = this.open();
			cursor = db.rawQuery("select * from " + CHEXI_TABLE_NAME + " where _id = '" + id + "'", null);
			if (cursor != null && cursor.moveToFirst()) {
				do {
					aChexi = new Chexi();
					aChexi.setId(cursor.getString(0));
					aChexi.setBrandId(cursor.getString(1));
					aChexi.setName(cursor.getString(2));
				} while (cursor.moveToNext());
			}
		} finally {
			if (cursor != null)
				cursor.close();
			this.close();
		}
		return aChexi;
	}
}
