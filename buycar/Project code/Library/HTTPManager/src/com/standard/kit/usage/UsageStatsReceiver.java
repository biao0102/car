package com.standard.kit.usage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class UsageStatsReceiver extends BroadcastReceiver {

	public static final String ACTION_USAGE_STATS = "android.intent.action.USAGE_STATS";

	@Override
	public void onReceive(Context context, Intent intent) {
		if (ACTION_USAGE_STATS.equals(intent.getAction())) {
			startServier(context);
		} else if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
			UsageStatsTimer.getInstance(context).startUsageService();
		}
	}

	/**
	 * 启动服务
	 * 
	 * @param context
	 */
	private void startServier(Context context) {
		Intent service = new Intent(context, UsageStatsService.class);
		context.startService(service);
	}

}
