package com.standard.kit.device;

import android.os.StatFs;
import android.text.TextUtils;

import com.standard.kit.file.FileUtil;

public class DiskInfo {
	static final int ERROR = -1;

	static public long getAvailableInternalMemorySize() {
		StatFs stat = new StatFs(FileUtil.getSavePath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		return availableBlocks * blockSize;
	}

	static public long getTotalInternalMemorySize() {
		StatFs stat = new StatFs(FileUtil.getSavePath());
		long blockSize = stat.getBlockSize();
		long totalBlocks = stat.getBlockCount();
		return totalBlocks * blockSize;
	}

	static public long getAvailableExternalMemorySize() {
		if (isExistSdCard()) {
			StatFs stat = new StatFs(FileUtil.getSavePath());
			long blockSize = stat.getBlockSize();
			long availableBlocks = stat.getAvailableBlocks();
			return availableBlocks * blockSize;
		} else {
			return ERROR;
		}
	}

	static public long getTotalExternalMemorySize() {
		if (isExistSdCard()) {
			StatFs stat = new StatFs(FileUtil.getSavePath());
			long blockSize = stat.getBlockSize();
			long totalBlocks = stat.getBlockCount();
			return totalBlocks * blockSize;
		} else {
			return ERROR;
		}
	}

	/**
	 * sdcard是否存在
	 * 
	 * @return 存在返回TRUE。其它FALSE；
	 */
	private static boolean isExistSdCard() {
		try {
			if (!TextUtils.isEmpty(FileUtil.getSavePath()))
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
