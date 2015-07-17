package com.cars.manager.utils;

import android.content.Context;
import android.content.Intent;

public class IntentUtil {

	/**
	 * 跳转到某个Activity
	 * */
	public static void forwordToActivity(Context context, Class<?> cls) {
		Intent aIntent = new Intent(context, cls);
		context.startActivity(aIntent);
	}
}
