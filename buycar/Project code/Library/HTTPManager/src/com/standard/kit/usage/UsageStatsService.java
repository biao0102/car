package com.standard.kit.usage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * 日志统计
 * 
 * @author landmark
 * 
 * @date 2012-10-17
 * 
 */
public class UsageStatsService extends Service implements Runnable {

	private static final String REGEX = "cmp=[a-zA-Z_]\\w*(\\.[a-zA-Z_]\\w*)*/([a-zA-Z_]\\w*)?(\\.[a-zA-Z_]\\w*)+";

	private static final String REGEX_LANUCHER = "cat=\\[android\\.intent\\.category\\.LAUNCHER\\]";

	private boolean isStop = false;

	private UsageStats mUsageStats = null;

	@Override
	public void onCreate() {
		super.onCreate();
		new Thread(this).start();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		isStop = false;
		if (mUsageStats == null) {
			mUsageStats = new UsageStats(this);
		}
	}

	@Override
	public void run() {
		while (!isStop) {
			readLogcat();
		}
	}

	private void readLogcat() {
		BufferedReader reader = null;
		String line = null;
		try { // 获取logcat日志信息
			Process mLogcatProc = Runtime.getRuntime().exec(new String[] { "logcat", "-v", "time", "-s", "ActivityManager:I" });
			reader = new BufferedReader(new InputStreamReader(mLogcatProc.getInputStream()));
			while ((line = reader.readLine()) != null) {
				Matcher m = Pattern.compile(REGEX_LANUCHER).matcher(line);
				if (m.find()) {
					m = Pattern.compile(REGEX).matcher(line);
					if (m.find()) {
						String usageTime = line.substring(0, 18);
						String cmp = m.group();
						String packageName = cmp.substring(cmp.indexOf("=") + 1, cmp.indexOf("/"));
						mUsageStats.putUsageStats(packageName, usageTime);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		isStop = true;
	}

}
