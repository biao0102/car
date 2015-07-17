
package com.standard.kit.format;

import java.text.DecimalFormat;

/**
 * @author landmark
 * 
 * @date 2012-11-15 下午12:55:08
 * 
 * @version
 */
public class StringFormat {

	/****
	 * 将一个文件的长度转换成String显示到界面上。结果为12.44M 1.33G 1G(注意不是1.00G)
	 * 
	 * @param size
	 *            文件大小
	 * @return 返回文件大小的字符串表示。如12.44M 1.33G 保留两位小数
	 */
	public static String getFileSize(long size) {
		if (size <= 0)
			return "0";
		final String[] units = new String[] { "B", "K", "M", "G", "T" };
		int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
		return new DecimalFormat("#0.##").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
	}

	public static String getProgressPercent(long downloadSize, long totalSize) {
		DecimalFormat aDf = new DecimalFormat("0.0");
		if (totalSize == 0)
			return "0.0%";
		else
			return aDf.format(downloadSize * 100.0 / totalSize) + "%";
	}
}
