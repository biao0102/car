package com.cars.manager.utils;

import com.standard.kit.file.FileUtil;
import com.standard.kit.format.DateTimeUtil;
import com.standard.kit.thread.ThreadPoolUtil;

/**
 * @author wht
 * 
 * @date 2013年7月15日15:39:20
 * 
 *       记录请求和回复时间和command
 */
public class RecordDataUtils {

	private static String requestRecordFileName = "request.txt";

	private static String responseRecordFileName = "response.txt";

	private static String showRecordFileName = "show.txt";

	private static final String RECORD_FILE_PATH = FileUtil.SDCARD_PATH + "/data/record/";

	/**
	 * 请求记录
	 */
	public static void requestRecordLog(final String command) {
		ThreadPoolUtil.getInstance().execute(new Runnable() {
			@Override
			public void run() {

				String str = command + "_" + DateTimeUtil.getCurrentTime(DateTimeUtil.PATTERN_CURRENT_TIME) + "\n";

				byte[] bytes = str.getBytes();

				FileUtil.append2File(RECORD_FILE_PATH, requestRecordFileName, bytes);
			}
		});
	}

	/**
	 * 回复记录
	 */
	public static void responseRecordLog(final String command) {
		ThreadPoolUtil.getInstance().execute(new Runnable() {
			@Override
			public void run() {

				String str = command + "_" + DateTimeUtil.getCurrentTime(DateTimeUtil.PATTERN_CURRENT_TIME) + "\n";

				byte[] bytes = str.getBytes();

				FileUtil.append2File(RECORD_FILE_PATH, responseRecordFileName, bytes);
			}
		});
	}

	/**
	 * 页面显示记录
	 */
	public static void showRecordLog(final String command) {
		ThreadPoolUtil.getInstance().execute(new Runnable() {
			@Override
			public void run() {

				String str = command + "_" + DateTimeUtil.getCurrentTime(DateTimeUtil.PATTERN_CURRENT_TIME) + "\n";

				byte[] bytes = str.getBytes();

				FileUtil.append2File(RECORD_FILE_PATH, showRecordFileName, bytes);
			}
		});
	}

}
