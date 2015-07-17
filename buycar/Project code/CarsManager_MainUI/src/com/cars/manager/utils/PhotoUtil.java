package com.cars.manager.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;

import com.standard.kit.graphic.BitmapUtils;
import com.standard.kit.text.TextUtil;

/**
 * 照片处理工具
 * */
public class PhotoUtil {

	public static final int TAKE_PHOTO_REQUEST_CODE = 2;
	public static final int CROP_PHOTO_REQUEST_CODE = 3;

	/**
	 * 跳转到系统剪裁页面
	 * */
	public static void startToCropPhoto(Activity activity, Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");// crop=true 有这句才能出来最后的裁剪页面.
		intent.putExtra("aspectX", 3);// 这两项为裁剪框的比例.
		intent.putExtra("aspectY", 2);// x:y=1:1
		intent.putExtra("outputX", (int) (222 * 2.5));// 图片输出大小
		intent.putExtra("outputY", (int) (148 * 2.5));
		intent.putExtra("output", uri);
		intent.putExtra("outputFormat", "JPEG");// 返回格式
		activity.startActivityForResult(intent, CROP_PHOTO_REQUEST_CODE);
	}

	/**
	 * 根据路径获取图片并利用Base64压缩
	 * 
	 * @param
	 * */
	public static String getBitmapBase64String(Context aContext, String path) {

		if (TextUtil.isEmpty(path) || path.length() < 7)
			return "";

		path = path.substring(7, path.length());

		Bitmap aBitmap = getBitmapWithFilepath(aContext, path);

		if (aBitmap == null)
			return "";

		byte[] photoByte = bitmapToByte(aBitmap);

		if (photoByte.length == 0)
			return "";

		return new String(org.springframework.security.crypto.codec.Base64.encode(photoByte));
	}

	/**
	 * 根据路径获取对应的bitmap<无压缩>
	 * */
	public static Bitmap getBitmapWithFilepath(Context aContext, String path) {
		try {
			InputStream in = new FileInputStream(path);
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inSampleSize = 1;
			opts.inPreferredConfig = Config.ARGB_8888;
			opts.inPurgeable = true;
			opts.inInputShareable = true;
			Bitmap bitmap = null;
			try {
				bitmap = BitmapFactory.decodeStream(in, null, opts);
				return bitmap;
			} catch (OutOfMemoryError e) {
				return null;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/** bitmap -> byte */
	public static byte[] bitmapToByte(Bitmap bitmap) {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
			byte[] array = out.toByteArray();
			return array;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/** bitmap -> drawable */
	public static BitmapDrawable bitmapToDrawable(Bitmap bitmap) {
		BitmapDrawable bd = new BitmapDrawable(bitmap);
		return bd;
	}

	/** byte -> drawable */
	public static BitmapDrawable byteToDrawable(byte[] bytes) {
		Bitmap bitmap = BitmapUtils.createImg(bytes);
		// BitmapFactory.decodeByteArray(bytes, 0, bytes.length,null);
		if (null == bitmap) {
			return null;
		}
		BitmapDrawable bd = new BitmapDrawable(bitmap);
		return bd;
	}

	// public static String image2byte(String path) {
	// path = "C:\\Users\\Public\\Pictures\\Sample Pictures\\bizhi.jpg";
	// byte[] data = null;
	// FileImageInputStream input = null;
	// try {
	// input = new FileImageInputStream(new File(path));
	// ByteArrayOutputStream output = new ByteArrayOutputStream();
	// byte[] buf = new byte[1024];
	// int numBytesRead = 0;
	// while ((numBytesRead = input.read(buf)) != -1) {
	// output.write(buf, 0, numBytesRead);
	// }
	// data = output.toByteArray();
	// output.close();
	// input.close();
	// } catch (FileNotFoundException ex1) {
	// ex1.printStackTrace();
	// } catch (IOException ex1) {
	// ex1.printStackTrace();
	// }
	// // String str = new String(Base64.encode(data));
	// return "";
	// }

}
