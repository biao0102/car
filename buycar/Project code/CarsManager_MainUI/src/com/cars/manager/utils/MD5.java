package com.cars.manager.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 完成以下功能：计算文件或二进制数组的MD5码
 * 
 * @version v1.0
 * 
 * @author neil lizhize
 * 
 */
public class MD5 {

	private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
			'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
			'W', 'X', 'Y', 'Z' };

	/***
	 * 二进制内容的MD5码。
	 * 
	 * @param bytes
	 *            二进制内容
	 * @return 返回二进制内容的MD5码。
	 */
	public static String toMd5(byte[] bytes) {
		try {
			MessageDigest algorithm = MessageDigest.getInstance("MD5");
			// algorithm.reset();
			algorithm.update(bytes);
			return toHexString(algorithm.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	/****
	 * 将二进制转换成十六进制的字符串。
	 * 
	 * @param aBytes
	 *            二进制内容
	 * @return 返回二进制内容的十六进制表示
	 */
	public static String toHexString(byte[] aBytes) {
		StringBuilder sb = new StringBuilder(aBytes.length * 2);
		for (int i = 0; i < aBytes.length; i++) {
			sb.append(HEX_DIGITS[(aBytes[i] & 0xf0) >>> 4]);
			sb.append(HEX_DIGITS[aBytes[i] & 0x0f]);
		}
		return sb.toString();
	}

	/***
	 * 取得文件的MD5码
	 * 
	 * @return null where file isn's exits 当文件不存在时返回空。
	 * */
	public static String getFileMd5(String aFilePath) {
		InputStream fis;
		byte[] buffer = new byte[1024];
		int numRead = 0;
		MessageDigest md5;
		try {
			fis = new FileInputStream(aFilePath);
			md5 = MessageDigest.getInstance("MD5");
			while ((numRead = fis.read(buffer)) > 0) {
				md5.update(buffer, 0, numRead);
			}
			fis.close();
			return toHexString(md5.digest());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
