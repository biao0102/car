/*******************************************************************************
 * Copyright 2013 Kumar Bibek
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *    
 * http://www.apache.org/licenses/LICENSE-2.0
 * 	
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/

package com.cars.manager.utils.imageChooser.api;

import java.io.File;
import java.io.OutputStream;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;

import com.cars.manager.views.widgts.supertoast.SuperToast;
import com.cars.managers.R;

public class FileUtils {
	public static String getDirectory(String foldername) {
		File directory = null;
		directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + foldername);
		if (!directory.exists()) {
			directory.mkdir();
		}
		return directory.getAbsolutePath();
	}

	public static String getFileExtension(String filename) {
		String extension = "";
		try {
			extension = filename.substring(filename.lastIndexOf(".") + 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return extension;
	}

	// /**
	// * 获取利用imageloader下载的图片的地址
	// *
	// * 有些有些手机加载的图片已.0为结尾，有些则不是，该方法进行判断
	// * */
	// public static String getImagePathFromUrl(String url) {
	// String pathNameWithnormal = Config.IMAGE_PATH +
	// FileNameGenerator.generate(url);
	// File tempFileNormal = new File(pathNameWithnormal);
	//
	// String pathNameWithZero = Config.IMAGE_PATH +
	// FileNameGenerator.generate(url) + ".0";
	// File tempFileWithZero = new File(pathNameWithZero);
	//
	// if (tempFileNormal.exists()) {
	// return pathNameWithnormal;
	// } else if (tempFileWithZero.exists()) {
	// return pathNameWithZero;
	// }
	// return "";
	// }

	/**
	 * 将bitmap保存至相册 photoName：照片名称 bitmap：要保存的图像
	 * */
	public static void savePhotoToAlbum(Context context, String photoName, Bitmap bitmap) {
		// 创建contentValuse对象，保存相片属性
		ContentValues values = new ContentValues(7);
		// 相片标题
		values.put(Images.Media.TITLE, context.getResources().getString(R.string.app_name));
		// 相片名称
		values.put(Images.Media.DISPLAY_NAME, photoName);
		// 相片类型
		values.put(Images.Media.MIME_TYPE, "image/jpeg");
		// 相片方向
		values.put(Images.Media.ORIENTATION, 0);
		// 相片拍摄时间
		String saveTime = System.currentTimeMillis() + "";
		// String saveTime =
		// DateFormat.format(DateTimeUtil.PATTERN_CURRENT_TIME,
		// System.currentTimeMillis()).toString();
		values.put(Images.Media.DATE_TAKEN, saveTime);
		// 相片路径
		final String CAMERA_IMAGE_BUCKET_NAME = Environment.getExternalStorageDirectory().getPath() + "dcim/camera";
		File parentFile = new File(CAMERA_IMAGE_BUCKET_NAME);
		String name = parentFile.getName().toLowerCase();
		values.put(Images.ImageColumns.BUCKET_DISPLAY_NAME, name);
		// 相片ID
		final String CAMERA_IMAGE_BUCKET_ID = String.valueOf(CAMERA_IMAGE_BUCKET_NAME.hashCode());
		values.put(Images.ImageColumns.BUCKET_ID, CAMERA_IMAGE_BUCKET_ID);

		// 将contentValuse转化为Uri
		Uri dataUri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
		// 根据Uri将图片写入相册
		try {
			OutputStream outStream = context.getContentResolver().openOutputStream(dataUri);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
			outStream.close();
			bitmap.recycle();
			SuperToast.show("保存图片成功,请到相册查看!", context);
			return;
		} catch (Exception e) {
			SuperToast.show("保存图片失败,请查看网络!", context);
		}
	}
}
