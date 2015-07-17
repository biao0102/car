package com.standard.kit.usage;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * 定时器--定时启动服务
 * 
 * @author landmark
 * 
 */
public class UsageStatsTimer {

	public static final long TRIGGER_TIME_USAGESTATS = 1 * 5 * 1000;

	private static UsageStatsTimer instance = null;

	private AlarmManager am = null;

	private Context context;

	private UsageStatsTimer(Context context) {
		this.context = context;
		am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
	}

	public static UsageStatsTimer getInstance(Context context) {
		if (instance == null) {
			instance = new UsageStatsTimer(context);
		}
		return instance;
	}

	/**
	 * AlarmManager的取消：
	 * 
	 * 其中需要注意的是取消的Intent必须与启动Intent保持绝对一致才能支持取消AlarmManager
	 * 
	 */
	public void cancel(String action) {
		Intent intent = new Intent(action);
		PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
		am.cancel(sender);
	}

	/**
	 * 周期性的执行某项操作
	 * 
	 * @param action
	 * @param triggerAtTime
	 *            执行周期：毫秒
	 */
	public void setRepeating(String action, long triggerAtTime) {
		Intent intent = new Intent(action);
		PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		long firstime = System.currentTimeMillis();
		am.setRepeating(AlarmManager.RTC_WAKEUP, firstime, triggerAtTime, sender);
	}

	public void startUsageService() {
		Intent intent = new Intent(UsageStatsReceiver.ACTION_USAGE_STATS);
		PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		long firstime = System.currentTimeMillis();
		am.setRepeating(AlarmManager.RTC, firstime, TRIGGER_TIME_USAGESTATS, sender);
	}
}
