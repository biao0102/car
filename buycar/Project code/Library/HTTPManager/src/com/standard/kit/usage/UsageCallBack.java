package com.standard.kit.usage;

/**
 * 接口回调
 * 
 * @author landmark
 * 
 */
public class UsageCallBack {

	private static UsageCallBack instance;

	private UsageCallBack() {

	}

	public static UsageCallBack getInstance() {
		if (instance == null) {
			instance = new UsageCallBack();
		}
		return instance;
	}

	interface UsageObserver {
		void launchActivity(String packageName);
	}

	public UsageObserver mUsageObserver = null;

	public void setUsageObserver(UsageObserver usageObserver) {
		this.mUsageObserver = usageObserver;
	}
}
