package com.cars.manager.utils;

import java.util.Stack;

import android.app.Activity;

public class ActivityStack {
	private static Stack<Activity> activityStack;
	private static ActivityStack instance;

	private ActivityStack() {
	}

	public static ActivityStack getActivityStack() {
		if (instance == null) {
			instance = new ActivityStack();
		}
		return instance;
	}

	// 退出栈顶Activity
	public void popActivity(Activity activity) {
		if (activity != null) {
			activity.finish();
			activityStack.remove(activity);
			activity = null;
		}
	}

	// 获得当前栈顶Activity
	public Activity currentActivity() {
		Activity activity = activityStack.lastElement();
		return activity;
	}

	// 将当前Activity推入栈中
	public void pushActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
	}

	// 退出栈中所有Activity
	public void popAllActivityExceptOne(Class cls) {
		while (activityStack != null && !activityStack.isEmpty()) {
			Activity activity = currentActivity();
			if (activity == null) {
				break;
			}
			if (activity.getClass().equals(cls)) {
				break;
			}
			popActivity(activity);
		}
	}
}