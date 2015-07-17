package com.standard.kit.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 获取app线程
 * 
 * @author landmark
 * 
 * @date 2010-11-11
 * 
 */
public class ThreadPoolUtil {

	private static ExecutorService instance = null;

	private ThreadPoolUtil() {
	}

	public static ExecutorService getInstance() {
		if (instance == null)
			instance = Executors.newCachedThreadPool();
		return instance;
	}
}
