package com.standard.kit.usage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.content.Context;

import com.standard.kit.preferences.PreferencesUtil;
import com.standard.kit.usage.UsageCallBack.UsageObserver;

/**
 * 应用使用统计 接口回调
 * 
 * @author landmark
 * 
 */
public class UsageStats {

	private PreferencesUtil mPreferencesUtil = null;

	public UsageStats(Context context) {
		init(context);
	}

	/**
	 * 用于监听应用启动
	 * 
	 * @param usageObserver
	 */
	public UsageStats(Context context, UsageObserver usageObserver) {
		UsageCallBack.getInstance().setUsageObserver(usageObserver);
		init(context);
	}

	private void init(Context context) {
		mPreferencesUtil = new PreferencesUtil(context, "usage_stats");
	}

	/**
	 * 应用使用统计保存格式：packageName-usageTiem-launchCount
	 * 
	 * @param packageName
	 * @param usageTime
	 */
	public void putUsageStats(String packageName, String usageTime) {
		String value = mPreferencesUtil.getString(packageName);
		int launchCount = 0;
		if (value != null) {
			launchCount = Integer.valueOf(value.substring(value.lastIndexOf("-") + 1));
		}
		launchCount++;
		value = packageName + "-" + getUsageTime(usageTime) + "-" + launchCount;
		mPreferencesUtil.putString(packageName, value);
	}

	private static final String REGEX = "[-:\\.\" \"]";

	/**
	 * uasgeTime格式：10-16 17:33:29.470
	 * 
	 * @param usageTime
	 * @return
	 */
	private long getUsageTime(String usageTime) {
		try {
			String[] times = usageTime.split(REGEX);
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.MONTH, Integer.valueOf(times[0]));
			cal.set(Calendar.DAY_OF_MONTH, Integer.valueOf(times[1]));
			cal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(times[2]));
			cal.set(Calendar.MINUTE, Integer.valueOf(times[3]));
			cal.set(Calendar.SECOND, Integer.valueOf(times[4]));
			cal.set(Calendar.MILLISECOND, Integer.valueOf(times[5]));
			return cal.getTimeInMillis();
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return System.currentTimeMillis();
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList<PkgUsageStats> getAllPkgUsageStats() {
		Map<String, ?> map = mPreferencesUtil.getAll();
		if (map == null || map.isEmpty()) {
			return null;
		}
		ArrayList<PkgUsageStats> mUsageStats = new ArrayList<PkgUsageStats>();
		Iterator<?> iterator = map.entrySet().iterator();
		PkgUsageStats pkgUsageStats;
		while (iterator.hasNext()) {
			Entry<String, String> entry = (Entry<String, String>) iterator.next();
			String value = entry.getValue();
			String[] usage = value.split("-");
			pkgUsageStats = new PkgUsageStats();
			pkgUsageStats.packageName = usage[0];
			pkgUsageStats.usageTime = Long.valueOf(usage[1]);
			pkgUsageStats.launchCount = Integer.valueOf(usage[2]);
			mUsageStats.add(pkgUsageStats);
		}
		return mUsageStats;
	}

}
