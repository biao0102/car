package com.cars.manager.db.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;

import com.cars.manager.db.afinal.FinalDb;
import com.cars.manager.db.table.CarResource;
import com.cars.manager.db.table.Cars;
import com.cars.manager.db.table.Chexi;
import com.cars.manager.db.table.Gongyingshang;
import com.cars.manager.db.table.Locations;
import com.cars.manager.db.table.Manager;
import com.cars.manager.db.table.PhotoInfo;
import com.cars.manager.db.table.Pinpais;
import com.cars.manager.db.table.Tabs;
import com.cars.manager.db.table.UpdateCarInfo;
import com.cars.manager.db.table.Yanse;
import com.cars.manager.db.table.Zhuangtais;

public class DBHelper {

	// db name
	private static final String DB_NAME = "carmanager.db";

	private static FinalDb db;

	public DBHelper(Context context) {
		db = FinalDb.create(context, DB_NAME);
	}

	/* table car operations */
	public void addCar(Cars aCar) {

		Cars car = selectCarsWithId(aCar.getId());
		if (car == null) {
			db.save(aCar);
		} else {
			db.update(aCar);
		}

	}

	public void updateCar(Cars aCar) {
		db.update(aCar);
	}

	public ArrayList<Cars> selectAllCar() {
		List<Cars> allCars = db.findAll(Cars.class);
		ArrayList<Cars> allCarsList = new ArrayList<Cars>();
		for (int i = 0; i < allCars.size(); i++) {
			allCarsList.add(allCars.get(i));
		}
		return allCarsList;
	}

	public Cars selectCarsWithId(String id) {
		return db.findById(id, Cars.class);
	}

	/* 品牌 */

	public void addPinpaiWithoutSearch(Pinpais aPinpais) {
		db.save(aPinpais);
	}

	public void addPinpai(Pinpais aPinpais) {
		if (selectPinpaisiWithId(aPinpais.getId()) == null) {
			db.save(aPinpais);
		}
	}

	public List<Pinpais> getAllPinpairs() {
		return db.findAll(Pinpais.class);
	}

	public Pinpais selectPinpaisiWithId(String id) {
		return db.findById(id, Pinpais.class);
	}

	public List<Pinpais> selectPinpaisiWithName(String name) {
		List<Pinpais> all = getAllPinpairs();
		List<Pinpais> result = new ArrayList<Pinpais>();
		for (int i = 0; i < all.size(); i++) {
			if (all.get(i).getName().contains(name)) {
				result.add(all.get(i));
			}
		}
		return result;
	}

	public ArrayList<Pinpais> selectPinpaisiWithFirstChar(String firstCar) {
		List<Pinpais> all = getAllPinpairs();
		ArrayList<Pinpais> results = new ArrayList<Pinpais>();
		for (int i = 0; i < all.size(); i++) {
			if (all.get(i).getFirstCharacter().equals(firstCar)) {
				results.add(all.get(i));
			}
		}
		return results;
	}

	/* 车系 */
	public void addChexiWithoutSearch(Chexi aChexi) {
		db.save(aChexi);
	}

	public void addChexi(Chexi aChexi) {

		if (selectChexiWithId(aChexi.getId()) == null) {
			db.save(aChexi);
		}

	}

	public Chexi selectChexiWithId(String id) {
		return db.findById(id, Chexi.class);
	}

	public List<Chexi> getAllChexis() {
		return db.findAll(Chexi.class);
	}

	public List<Chexi> selectChexisWithId(String brandId) {
		return db.findAllByWhere(Chexi.class, "brandId=\"" + brandId + "\"");
	}

	/* 状态 */
	public void addStatuses(ArrayList<Zhuangtais> mZhuangtais) {
		for (int i = 0; i < mZhuangtais.size(); i++) {
			if (selectZhuangtaisWithId(mZhuangtais.get(i).getStatus()) == null) {
				db.save(mZhuangtais.get(i));
			}
		}
	}

	public Zhuangtais selectZhuangtaisWithId(String id) {
		return db.findById(id, Zhuangtais.class);
	}

	public List<Zhuangtais> selectZhuangtaisAll() {
		return db.findAll(Zhuangtais.class);
	}

	public Zhuangtais selectWeiShouZhuangtais() {

		List<Zhuangtais> all = selectZhuangtaisAll();

		for (int i = 0; i < all.size(); i++) {
			if (all.get(i).getStatusValue().equals("未售")) {
				return all.get(i);
			}
		}

		return null;
	}

	/* 供应商 */
	public void addGYS(ArrayList<Gongyingshang> mGongyingshangs) {
		for (int i = 0; i < mGongyingshangs.size(); i++) {
			if (selectGongyingshangWithId(mGongyingshangs.get(i).getId()) == null) {
				db.save(mGongyingshangs.get(i));
			}
		}
	}

	public Gongyingshang selectGongyingshangWithId(String id) {
		return db.findById(id, Gongyingshang.class);
	}

	public List<Gongyingshang> selectGongyingshangWithName(String name) {
		List<Gongyingshang> gysList = selectGYSAll();
		List<Gongyingshang> resultList = new ArrayList<Gongyingshang>();

		for (int i = 0; i < gysList.size(); i++) {
			if (gysList.get(i).getName().contains(name) || gysList.get(i).getId().contains(name)) {
				resultList.add(gysList.get(i));
			}
		}
		return resultList;
	}

	public List<Gongyingshang> selectGYSAll() {
		return db.findAll(Gongyingshang.class);
	}

	/* 地域 */
	public void addLoactions(ArrayList<Locations> aLocationss) {
		for (int i = 0; i < aLocationss.size(); i++) {
			if (selectLocationsWithId(aLocationss.get(i).getCityId()) == null) {
				db.save(aLocationss.get(i));
			}
		}
	}

	public Locations selectLocationsWithId(String id) {
		return db.findById(id, Locations.class);
	}

	public List<Locations> selectLoactionAll() {
		return db.findAll(Locations.class);
	}

	/* 颜色 */
	public void addYanses(ArrayList<Yanse> aYanses) {
		for (int i = 0; i < aYanses.size(); i++) {
			if (selectYanseWithId(aYanses.get(i).getColor()) == null) {
				db.save(aYanses.get(i));
			}
		}
	}

	public Yanse selectYanseWithId(String id) {
		return db.findById(id, Yanse.class);
	}

	public List<Yanse> selectYanseAll() {
		return db.findAll(Yanse.class);
	}

	/* 用途 */
	public void addTabs(ArrayList<Tabs> aTabs) {
		for (int i = 0; i < aTabs.size(); i++) {
			if (selectTabsWithId(aTabs.get(i).getTabid()) == null) {
				db.save(aTabs.get(i));
			}
		}
	}

	public Tabs selectTabsWithId(String id) {
		return db.findById(id, Tabs.class);
	}

	public List<Tabs> selectTabsAll() {
		return db.findAll(Tabs.class);
	}

	public ArrayList<Tabs> selectTabslistWithId(String ids) {
		ArrayList<Tabs> tempTabs = new ArrayList<Tabs>();
		String[] subIds = ids.split(",");

		for (int i = 0; i < subIds.length; i++) {
			Tabs aTab = selectTabsWithId(subIds[i]);
			if (aTab != null) {
				aTab.setSelected(true);
				tempTabs.add(aTab);
			}
		}

		return tempTabs;

	}

	/* 车辆 */
	public void addCarResource(CarResource resource) {
		if (selectCarResourceWithId(resource.getVin()) == null) {
			db.save(resource);
		}

	}

	public CarResource selectCarResourceWithId(String id) {
		return db.findById(id, CarResource.class);
	}

	public List<CarResource> selectCarResourceAll() {
		return db.findAll(CarResource.class);
	}

	/* 商户负责人 */
	public void addManager(ArrayList<Manager> resources) {
		for (int i = 0; i < resources.size(); i++) {
			if (getManagerWithId(resources.get(i).getManagerId()) == null) {
				db.save(resources.get(i));
			} else {
				db.update(resources.get(i));
			}
		}
	}

	public Manager getManagerWithId(String id) {
		return db.findById(id, Manager.class);
	}

	public Manager getManagerWithName(String name) {
		List<Manager> managers = selectManagerAll();
		for (int i = 0; i < managers.size(); i++) {
			if (managers.get(i).getManagerName().equals(name)) {
				return managers.get(i);
			}
		}
		return null;
	}

	public List<Manager> selectManagerAll() {
		return db.findAll(Manager.class);
	}

	/* 需要上传的车辆信息 */
	public void addUpdateCarInfo(UpdateCarInfo aUpdateInfo) {
		db.save(aUpdateInfo);
	}

	public void deleteUpdateCarInfo(UpdateCarInfo aUpdateInfo) {
		db.delete(aUpdateInfo);
	}

	public void updateUpdateCarInfo(UpdateCarInfo aUpdateInfo) {
		db.update(aUpdateInfo);
	}

	public UpdateCarInfo selectUpdateCarInfoWithId(String id) {
		return db.findById(id, UpdateCarInfo.class);
	}

	public List<UpdateCarInfo> selectUpdateCarInfoAll() {
		List<UpdateCarInfo> list = db.findAll(UpdateCarInfo.class);
		Collections.reverse(list);
		return list;
	}

	/**
	 * 获取某用户Id下的为上传车辆
	 * */
	public List<UpdateCarInfo> selectUpdateCarInfoWithUuid(String uuid) {
		List<UpdateCarInfo> list = selectUpdateCarInfoAll();
		List<UpdateCarInfo> resultlist = new ArrayList<UpdateCarInfo>();

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getManagerId().equals(uuid)) {
				resultlist.add(list.get(i));
			}
		}

		return resultlist;
	}

	/* 需要上传的图片 */
	public void addUpdateCarPhoto(PhotoInfo aPhotoInfo) {
		if (getPhotoInfoWithId(aPhotoInfo.getIdType()) == null) {
			db.save(aPhotoInfo);
		} else {
			db.update(aPhotoInfo);
		}
	}

	public PhotoInfo getPhotoInfoWithId(String id) {
		return db.findById(id, PhotoInfo.class);
	}

	public List<PhotoInfo> selectUpdatePhotoInfoAll() {
		return db.findAll(PhotoInfo.class);
	}

	public ArrayList<PhotoInfo> selectPhotoInfoWithId(String id) {
		ArrayList<PhotoInfo> mPhotos = new ArrayList<PhotoInfo>();

		List<PhotoInfo> all = db.findAll(PhotoInfo.class);

		for (int i = 0; i < all.size(); i++) {
			if (all.get(i).getPhotoInfoId().equals(id)) {
				mPhotos.add(all.get(i));
			}
		}
		return mPhotos;
	}

}
