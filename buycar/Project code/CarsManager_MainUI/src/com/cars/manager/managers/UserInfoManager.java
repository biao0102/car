package com.cars.manager.managers;

import android.content.Context;

import com.cars.manager.utils.PreferencesUtil;

public class UserInfoManager {

	public static final String USER_INFO = "propertiesInfo";

	public static PreferencesUtil mInfoUtil = null;

	public static String getUuid(Context context) {
		mInfoUtil = getPreferencesUtil(context);
		return mInfoUtil.getString("uuid");
	}

	public static void setUuid(Context context, String forumUuid) {
		mInfoUtil = getPreferencesUtil(context);
		mInfoUtil.putString("uuid", forumUuid);
	}

	public static String getCityId(Context context) {
		mInfoUtil = getPreferencesUtil(context);
		return mInfoUtil.getString("cityid");
	}

	public static void setCityId(Context context, String cityid) {
		mInfoUtil = getPreferencesUtil(context);
		mInfoUtil.putString("cityid", cityid);
	}

	public static String getType(Context context) {
		mInfoUtil = getPreferencesUtil(context);
		return mInfoUtil.getString("type");
	}

	public static void setType(Context context, String type) {
		mInfoUtil = getPreferencesUtil(context);
		mInfoUtil.putString("type", type);
	}

	public static String getUuidAndType(Context context) {
		mInfoUtil = getPreferencesUtil(context);
		return mInfoUtil.getString("type") + mInfoUtil.getString("uuid");
	}

	public static boolean isBusiness(Context context) {
		mInfoUtil = getPreferencesUtil(context);
		return mInfoUtil.getString("type").equals("2");
	}

	public static void clearUserData(Context context) {
		mInfoUtil = getPreferencesUtil(context);
		mInfoUtil.putString("uuid", "");
		mInfoUtil.putString("cityid", "");
		mInfoUtil.putString("type", "");
	}

	/* 第一次请求弹出提示 */
	public static void setIsFirstUpdate(Context context, String isFirst) {
		mInfoUtil = getPreferencesUtil(context);
		mInfoUtil.putString("isFirstUpdate", isFirst);
	}

	public static String getIsFirstUpdate(Context context) {
		mInfoUtil = getPreferencesUtil(context);
		return mInfoUtil.getString("isFirstUpdate", "0");
	}

	/* 第一次请求更新品牌 */
	public static void setIsFirstUpdatePinpai(Context context, String isFirst) {
		mInfoUtil = getPreferencesUtil(context);
		mInfoUtil.putString("isFirstUpdatePinpai", isFirst);
	}

	public static String getIsFirstUpdatePinpai(Context context) {
		mInfoUtil = getPreferencesUtil(context);
		return mInfoUtil.getString("isFirstUpdatePinpai", "0");
	}

	/* 第一次请求更新品牌数据库 */
	public static void setIsFirstUpdatePinpaiDB(Context context, String isFirst) {
		mInfoUtil = getPreferencesUtil(context);
		mInfoUtil.putString("isFirstUpdatePinpaiDB", isFirst);
	}

	public static String getIsFirstUpdatePinpaiDB(Context context) {
		mInfoUtil = getPreferencesUtil(context);
		return mInfoUtil.getString("isFirstUpdatePinpaiDB", "0");
	}

	/* 第一次请求更新车系 */
	public static void setIsFirstUpdateChexi(Context context, String isFirst) {
		mInfoUtil = getPreferencesUtil(context);
		mInfoUtil.putString("isFirstUpdateChexi", isFirst);
	}

	public static String getIsFirstUpdateChexi(Context context) {
		mInfoUtil = getPreferencesUtil(context);
		return mInfoUtil.getString("isFirstUpdateChexi", "0");
	}

	/* 第一次请求更新车系数据库 */
	public static void setIsFirstUpdateChexiDB(Context context, String isFirst) {
		mInfoUtil = getPreferencesUtil(context);
		mInfoUtil.putString("isFirstUpdateChexiDB", isFirst);
	}

	public static String getIsFirstUpdateChexiDB(Context context) {
		mInfoUtil = getPreferencesUtil(context);
		return mInfoUtil.getString("isFirstUpdateChexiDB", "0");
	}

	/* 第一次请求更新车系 */
	public static void setIsFirstUpdateChexing(Context context, String isFirst) {
		mInfoUtil = getPreferencesUtil(context);
		mInfoUtil.putString("isFirstUpdateChexing", isFirst);
	}

	public static String getIsFirstUpdateChexing(Context context) {
		mInfoUtil = getPreferencesUtil(context);
		return mInfoUtil.getString("isFirstUpdateChexing", "0");
	}

	/* 第一次请求更新车系数据库 */
	public static void setIsFirstUpdateChexingDB(Context context, String isFirst) {
		mInfoUtil = getPreferencesUtil(context);
		mInfoUtil.putString("isFirstUpdateChexingDB", isFirst);
	}

	public static String getIsFirstUpdateChexingDB(Context context) {
		mInfoUtil = getPreferencesUtil(context);
		return mInfoUtil.getString("isFirstUpdateChexingDB", "0");
	}

	/* 颜色、供应商、地域请求时间间隔 */
	public static void setCarSecondInfoUpdateDay(Context context, int day) {
		mInfoUtil = getPreferencesUtil(context);
		mInfoUtil.putInt("CarSecondInfoUpdateDay", day);
	}

	public static int getCarSecondInfoUpdateDay(Context context) {
		mInfoUtil = getPreferencesUtil(context);
		return mInfoUtil.getInt("CarSecondInfoUpdateDay");
	}

	/* 请求品牌时间戳 */
	public static void setPinpaiTimeStamp(Context context, String timeStamp) {
		mInfoUtil = getPreferencesUtil(context);
		mInfoUtil.putString("pinpaiTimeStamp", timeStamp);
	}

	public static String getPinpaiTimeStamp(Context context) {
		mInfoUtil = getPreferencesUtil(context);
		return mInfoUtil.getString("pinpaiTimeStamp");
	}

	/* 请求车系时间戳 */
	public static void setChexiTimeStamp(Context context, String timeStamp) {
		mInfoUtil = getPreferencesUtil(context);
		mInfoUtil.putString("chexiTimeStamp", timeStamp);
	}

	public static String getChexiTimeStamp(Context context) {
		mInfoUtil = getPreferencesUtil(context);
		return mInfoUtil.getString("chexiTimeStamp");
	}

	/* 请求车型时间戳 */
	public static void setChexingTimeStamp(Context context, String timeStamp) {
		mInfoUtil = getPreferencesUtil(context);
		mInfoUtil.putString("chexingTimeStamp", timeStamp);
	}

	public static String getChexingTimeStamp(Context context) {
		mInfoUtil = getPreferencesUtil(context);
		return mInfoUtil.getString("chexingTimeStamp");
	}

	/* 请求供应商时间戳 */
	public static void setGYSTimeStamp(Context context, String timeStamp) {
		mInfoUtil = getPreferencesUtil(context);
		mInfoUtil.putString("gysTimeStamp", timeStamp);
	}

	public static String getGYSTimeStamp(Context context) {
		mInfoUtil = getPreferencesUtil(context);
		return mInfoUtil.getString("gysTimeStamp");
	}

	/**
	 * Preferences文件
	 */
	private static PreferencesUtil getPreferencesUtil(Context context) {
		if (mInfoUtil == null) {
			mInfoUtil = new PreferencesUtil(context, USER_INFO);
		}
		return mInfoUtil;
	}

}
