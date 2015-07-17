package com.cars.manager.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.Inflater;

import android.util.Log;

import com.cars.manager.application.App;
import com.cars.manager.db.table.PhotoInfo;
import com.standard.kit.format.DateTimeUtil;
import com.standard.kit.text.TextUtil;

public class CompressUtil {

	/**
	 * 压缩
	 * */
	public static byte[] compress(String data) {
		try {
			byte[] dataBytes = data.getBytes("UTF-8");
			ByteArrayInputStream bais = new ByteArrayInputStream(dataBytes);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] temp = new byte[1024];
			int count;
			GZIPOutputStream gos = new GZIPOutputStream(baos);
			while ((count = bais.read(temp, 0, temp.length)) != -1) {
				gos.write(temp, 0, count);
			}
			gos.finish();
			if (android.os.Build.VERSION.SDK_INT <= 18) {
				gos.flush();
			}

			gos.close();
			bais.close();
			baos.close();
			dataBytes = null;
			return baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static byte[] decompress2(byte[] data) {
		byte[] output = new byte[0];
		Inflater decompresser = new Inflater();
		decompresser.reset();
		decompresser.setInput(data);
		ByteArrayOutputStream o = new ByteArrayOutputStream(data.length);
		try {
			byte[] buf = new byte[1024];
			while (!decompresser.finished()) {
				int i = decompresser.inflate(buf);
				o.write(buf, 0, i);
			}
			output = o.toByteArray();
		} catch (Exception e) {
			output = data;
			e.printStackTrace();
		} finally {
			try {
				o.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		decompresser.end();
		return output;
	}

	public static String decompress(InputStream in) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int count;
		byte[] temp = new byte[1024];
		String result = null;
		try {
			GZIPInputStream gis = new GZIPInputStream(in);
			while ((count = gis.read(temp, 0, temp.length)) != -1) {
				baos.write(temp, 0, count);
			}
			gis.close();
			result = new String(baos.toByteArray(), "UTF-8");
			baos.flush();
			baos.close();
			in.close();
			temp = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String decompress(byte[] data) {
		try {
			return decompress(new ByteArrayInputStream(data));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String md5(byte[] bytes) throws NoSuchAlgorithmException {
		MessageDigest messageDigest = null;

		messageDigest = MessageDigest.getInstance("MD5");
		messageDigest.reset();
		messageDigest.update(bytes);

		byte[] byteArray = messageDigest.digest();
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		bytes = null;
		byteArray = null;
		return md5StrBuff.toString();
	}

	/* 获取压缩列表 */
	public static List<PhotoInfo> setCompressedPhotoInfoList(List<PhotoInfo> aCurrentSelectedCarPhotos) {

		Log.d("time", DateTimeUtil.getCurrentTime() + "start");

		for (int i = 0; i < aCurrentSelectedCarPhotos.size(); i++) {

			if (!TextUtil.isEmpty(aCurrentSelectedCarPhotos.get(i).getSdCardPath())) {
				aCurrentSelectedCarPhotos.get(i).setImage(PhotoUtil.getBitmapBase64String(App.mApp, aCurrentSelectedCarPhotos.get(i).getSdCardPath()));
			}
		}

		Log.d("time", DateTimeUtil.getCurrentTime() + "end");

		return aCurrentSelectedCarPhotos;

	}
}
