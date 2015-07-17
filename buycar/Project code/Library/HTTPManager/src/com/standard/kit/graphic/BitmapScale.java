package com.standard.kit.graphic;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * @author landmark
 * 
 * @date 2013-5-21 下午6:19:09
 * 
 * @version media
 */
public class BitmapScale {

	public enum ScalingLogic {
		FIT, CROP
	}

	// 裁剪解决方案---（根据Bitmap图片压缩）
	public static Bitmap createScaledBitmap(Bitmap unscaledBitmap, int dstWidth, int dstHeight, ScalingLogic scalingLogic) {

		Rect srcRect = calculateSrcRect(unscaledBitmap.getWidth(), unscaledBitmap.getHeight(), dstWidth, dstHeight, scalingLogic);

		Rect dstRect = calculateDstRect(unscaledBitmap.getWidth(), unscaledBitmap.getHeight(), dstWidth, dstHeight, scalingLogic);

		Bitmap scaledBitmap = Bitmap.createBitmap(dstRect.width(), dstRect.height(), Config.RGB_565);

		Canvas canvas = new Canvas(scaledBitmap);

		canvas.drawBitmap(unscaledBitmap, srcRect, dstRect, new Paint(Paint.FILTER_BITMAP_FLAG));

		return scaledBitmap;
	}

	public static Rect calculateSrcRect(int srcWidth, int srcHeight, int dstWidth, int dstHeight, ScalingLogic scalingLogic) {
		if (scalingLogic == ScalingLogic.CROP) {
			final float srcAspect = (float) srcWidth / (float) srcHeight;
			final float dstAspect = (float) dstWidth / (float) dstHeight;
			if (srcAspect > dstAspect) {
				final int srcRectWidth = (int) (srcHeight * dstAspect);
				final int srcRectLeft = (srcWidth - srcRectWidth) / 2;
				return new Rect(srcRectLeft, 0, srcRectLeft + srcRectWidth, srcHeight);
			} else {
				final int srcRectHeight = (int) (srcWidth / dstAspect);
				final int scrRectTop = (int) (srcHeight - srcRectHeight) / 2;
				return new Rect(0, scrRectTop, srcWidth, scrRectTop + srcRectHeight);
			}
		} else {
			return new Rect(0, 0, srcWidth, srcHeight);
		}
	}

	public static Rect calculateDstRect(int srcWidth, int srcHeight, int dstWidth, int dstHeight, ScalingLogic scalingLogic) {
		if (scalingLogic == ScalingLogic.FIT) {
			final float srcAspect = (float) srcWidth / (float) srcHeight;
			final float dstAspect = (float) dstWidth / (float) dstHeight;
			if (srcAspect > dstAspect) {
				return new Rect(0, 0, dstWidth, (int) (dstWidth / srcAspect));
			} else {
				return new Rect(0, 0, (int) (dstHeight * srcAspect), dstHeight);
			}
		} else {
			return new Rect(0, 0, dstWidth, dstHeight);
		}
	}

	// 同比缩放解决方案---图片按比例大小压缩方法（根据路径获取图片并压缩）
	public static Bitmap compressByFilePath(String pathName, int dstWidth, int dstHeight, int sizeInKb, ScalingLogic scalingLogic) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(pathName, options);
		int inSampleSize = calculateSampleSize(options.outWidth, options.outHeight, dstWidth, dstHeight, scalingLogic);
		options.inSampleSize = inSampleSize > 0 ? inSampleSize : 1;
		options.inJustDecodeBounds = false;
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		options.inPurgeable = true;
		options.inInputShareable = true;
		bitmap = BitmapFactory.decodeFile(pathName, options);
		return compressImage(bitmap, sizeInKb);
	}

	// 同比缩放解决方案---图片按比例大小压缩方法（根据Bitmap图片压缩）
	public static Bitmap compressByBitmap(Bitmap srcBitmap, int dstWidth, int dstHeight, int sizeInKb, ScalingLogic scalingLogic) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		srcBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		int quality = 100;
		while (baos.toByteArray().length / 1024 > 1024) {// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
			baos.reset();// 重置baos即清空baos
			srcBitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);// 这里压缩quality%，把压缩后的数据存放到baos中
			quality -= 10;// 每次都减少10
		}
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		BitmapFactory.Options options = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		options.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(bais, null, options);
		int inSampleSize = calculateSampleSize(options.outWidth, options.outHeight, dstWidth, dstHeight, scalingLogic);
		options.inSampleSize = inSampleSize > 0 ? inSampleSize : 1;// 设置缩放比例
		options.inJustDecodeBounds = false;
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		options.inPurgeable = true;
		options.inInputShareable = true;
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		bais = new ByteArrayInputStream(baos.toByteArray());
		bitmap = BitmapFactory.decodeStream(bais, null, options);
		return compressImage(bitmap, sizeInKb);// 压缩好比例大小后再进行质量压缩
	}

	public static int calculateSampleSize(int srcWidth, int srcHeight, int dstWidth, int dstHeight, ScalingLogic scalingLogic) {
		final float srcAspect = (float) srcWidth / (float) srcHeight;
		final float dstAspect = (float) dstWidth / (float) dstHeight;
		if (scalingLogic == ScalingLogic.FIT) {
			if (srcAspect > dstAspect) {
				return srcWidth / dstWidth;
			} else {
				return srcHeight / dstHeight;
			}
		} else {
			if (srcAspect > dstAspect) {
				return srcHeight / dstHeight;
			} else {
				return srcWidth / dstWidth;
			}
		}
	}

	/**
	 * 图片质量压缩
	 * 
	 * @param srcBitmap
	 * @param sizeInKb
	 * @return
	 */
	public static Bitmap compressImage(Bitmap srcBitmap, int sizeInKb) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		srcBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while (baos.toByteArray().length / 1024 > sizeInKb) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			srcBitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			options -= 10;// 每次都减少10
		}
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(bais, null, null);// 把ByteArrayInputStream数据生成图片
		return bitmap;
	}
}
