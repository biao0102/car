package com.cars.manager.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Application;

import com.cars.manager.db.helper.DBHelper;
import com.cars.manager.db.table.Pinpais;
import com.cars.manager.managers.UserInfoManager;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class App extends Application {

	public static App mApp;

	public static Map<String, ArrayList<Pinpais>> pinpaiMap = new HashMap<String, ArrayList<Pinpais>>();

	public static ArrayList<String> charaterList = new ArrayList<String>();

	@Override
	public void onCreate() {
		super.onCreate();

		mApp = this;

		this.initImageEngine();
	}

	/* 初始化ImagerLoader */
	private void initImageEngine() {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).denyCacheImageMultipleSizesInMemory().threadPoolSize(3)
				.threadPriority(Thread.NORM_PRIORITY - 2).tasksProcessingOrder(QueueProcessingType.LIFO).memoryCache(new LruMemoryCache(5 * 1024 * 1024))
				.build();

		ImageLoader.getInstance().init(config);
	}

	public static synchronized void updatePinPaiDB(ArrayList<Pinpais> pinpaiList, UpdatePPDBListener aUpdatePPDBListener) {

		if (pinpaiList != null) {
			if (UserInfoManager.getIsFirstUpdatePinpaiDB(mApp).equals("0")) {
				UserInfoManager.setIsFirstUpdatePinpaiDB(mApp, "1");
				for (int i = 0; i < pinpaiList.size(); i++) {
					new DBHelper(mApp).addPinpaiWithoutSearch(pinpaiList.get(i));
				}
			} else {
				for (int i = 0; i < pinpaiList.size(); i++) {
					new DBHelper(mApp).addPinpai(pinpaiList.get(i));
				}
			}
		}

		setPinPaiMap(new DBHelper(mApp).getAllPinpairs());

		aUpdatePPDBListener.onUpdatePPComplete(true);
	}

	private static synchronized void setPinPaiMap(List<Pinpais> pinpaiList) {

		pinpaiMap.put("A", new ArrayList<Pinpais>());
		pinpaiMap.put("B", new ArrayList<Pinpais>());
		pinpaiMap.put("C", new ArrayList<Pinpais>());
		pinpaiMap.put("D", new ArrayList<Pinpais>());
		pinpaiMap.put("E", new ArrayList<Pinpais>());
		pinpaiMap.put("F", new ArrayList<Pinpais>());
		pinpaiMap.put("G", new ArrayList<Pinpais>());
		pinpaiMap.put("H", new ArrayList<Pinpais>());
		pinpaiMap.put("I", new ArrayList<Pinpais>());
		pinpaiMap.put("J", new ArrayList<Pinpais>());
		pinpaiMap.put("K", new ArrayList<Pinpais>());
		pinpaiMap.put("L", new ArrayList<Pinpais>());
		pinpaiMap.put("M", new ArrayList<Pinpais>());
		pinpaiMap.put("N", new ArrayList<Pinpais>());
		pinpaiMap.put("O", new ArrayList<Pinpais>());
		pinpaiMap.put("P", new ArrayList<Pinpais>());
		pinpaiMap.put("Q", new ArrayList<Pinpais>());
		pinpaiMap.put("R", new ArrayList<Pinpais>());
		pinpaiMap.put("S", new ArrayList<Pinpais>());
		pinpaiMap.put("T", new ArrayList<Pinpais>());
		pinpaiMap.put("U", new ArrayList<Pinpais>());
		pinpaiMap.put("V", new ArrayList<Pinpais>());
		pinpaiMap.put("W", new ArrayList<Pinpais>());
		pinpaiMap.put("X", new ArrayList<Pinpais>());
		pinpaiMap.put("Y", new ArrayList<Pinpais>());
		pinpaiMap.put("Z", new ArrayList<Pinpais>());

		for (int i = 0; i < pinpaiList.size(); i++) {
			((ArrayList<Pinpais>) pinpaiMap.get(pinpaiList.get(i).getFirstCharacter())).add(pinpaiList.get(i));
		}

		charaterList.add("A");
		charaterList.add("B");
		charaterList.add("C");
		charaterList.add("D");
		charaterList.add("E");
		charaterList.add("F");
		charaterList.add("G");
		charaterList.add("H");
		charaterList.add("I");
		charaterList.add("J");
		charaterList.add("K");
		charaterList.add("L");
		charaterList.add("M");
		charaterList.add("N");
		charaterList.add("O");
		charaterList.add("P");
		charaterList.add("Q");
		charaterList.add("R");
		charaterList.add("S");
		charaterList.add("T");
		charaterList.add("U");
		charaterList.add("V");
		charaterList.add("W");
		charaterList.add("X");
		charaterList.add("Y");
		charaterList.add("Z");
	}

	public static synchronized Map<String, ArrayList<Pinpais>> getPinpaiMap() {
		return pinpaiMap;
	}

	public static synchronized ArrayList<String> getCharaterList() {
		return charaterList;
	}

	/* 品牌 */
	public interface UpdatePPDBListener {
		public void onUpdatePPComplete(boolean pinpai);
	}

	UpdatePPDBListener mUpdatePPDBListener;

	public void setmUpdatePPDBListener(UpdatePPDBListener mUpdatePPDBListener) {
		this.mUpdatePPDBListener = mUpdatePPDBListener;
	}

	/* 车系 */

	public interface UpdateCXDBListener {
		public void onUpdateCXComplete(boolean chexi);
	}

	UpdateCXDBListener mUpdateCXDBListener;

	public void setmUpdateCXDBListener(UpdateCXDBListener mUpdateCXDBListener) {
		this.mUpdateCXDBListener = mUpdateCXDBListener;
	}

	/* 车型 */

	public interface UpdateCXINGDBListener {
		public void onUpdateCXINGComplete(boolean chexing);
	}

	UpdateCXINGDBListener mUpdateCXINGDBListener;

	public void setmUpdateCXINGDBListener(UpdateCXINGDBListener mUpdateCXINGDBListener) {
		this.mUpdateCXINGDBListener = mUpdateCXINGDBListener;
	}
}
