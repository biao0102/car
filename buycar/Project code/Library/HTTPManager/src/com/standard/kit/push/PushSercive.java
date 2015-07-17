package com.standard.kit.push;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class PushSercive extends Service implements Runnable {

	private boolean isStop = false;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		new Thread(this).start();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		isStop = false;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		if (!isStop) {
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		isStop = true;
	}
}
