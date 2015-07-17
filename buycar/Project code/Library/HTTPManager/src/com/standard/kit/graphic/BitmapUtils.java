package com.standard.kit.graphic;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;

/**
 * 完成以下功能：图片缩放；
 * 
 * @version v1.0
 * 
 * @author landmark
 */
public class BitmapUtils {

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

	/** drawable -> byte */
	public static byte[] drawableToByte(Drawable drawable) {
		Bitmap bitmap = drawableToBitmap(drawable);
		return bitmapToByte(bitmap);
	}

	/** drawable -> bitmap */
	public static Bitmap drawableToBitmap(Drawable drawable) {
		BitmapDrawable bd = (BitmapDrawable) drawable;
		Bitmap bm = bd.getBitmap();
		return bm;
	}

	/** byte -> bitmap */
	public static Bitmap byteToBitmap(byte[] bytes) {
		Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length,
				null);
		return bitmap;
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

	public static Bitmap getCameraBitmap(Context context, Uri photoUriPath)
			throws IOException {
		Uri photoUri = photoUriPath;

		int inSampleSize = 8;
		InputStream photoStream = context.getContentResolver().openInputStream(
				photoUri);
		int filesize = photoStream.available();
		Bitmap photoBitmap = null;
		if (filesize < 102400) {
			inSampleSize = 1;
		} else if (filesize < 1024000) {
			inSampleSize = 4;
		} else if (filesize < 3024000) {
			inSampleSize = 6;
		} else {
			inSampleSize = filesize / 12800;
		}
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = inSampleSize;
		photoBitmap = BitmapFactory.decodeStream(photoStream, null, options);
		return photoBitmap;
	}

	public static Bitmap getCameraBitmapFromPath(Context context,
			String photoUriPath) throws IOException {
		File imageFile = new File(photoUriPath);
		int inSampleSize = 8;
		long filesize = imageFile.length();
		Bitmap photoBitmap = null;
		if (filesize < 102400) {
			inSampleSize = 1;
		} else if (filesize < 1024000) {
			inSampleSize = 4;
		} else if (filesize < 3024000) {
			inSampleSize = 6;
		} else {
			inSampleSize = (int) filesize / 12800;
		}
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = inSampleSize;
		photoBitmap = BitmapFactory.decodeFile(photoUriPath, options);
		return photoBitmap;
	}

	public static Bitmap getResizedBitmap(Context context, String photoUrlPath,
			int width, int height) throws IOException {
		int newHeight = height;
		int newWidth = width;
		Uri photoUri = Uri.parse(photoUrlPath);

		int inSampleSize = 8;
		InputStream photoStream = context.getContentResolver().openInputStream(
				photoUri);
		int filesize = photoStream.available();
		Bitmap photoBitmap = null;
		if (filesize < 102400) {
			inSampleSize = 1;
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = inSampleSize;
			photoBitmap = BitmapFactory
					.decodeStream(photoStream, null, options);
		} else {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = inSampleSize;
			photoBitmap = BitmapFactory
					.decodeStream(photoStream, null, options);

			while (options.outHeight < height * inSampleSize
					|| options.outWidth < height * inSampleSize) {
				inSampleSize--;
				if (inSampleSize == 1) {
					break;
				}
			}

			options.inSampleSize = inSampleSize;
			photoBitmap = BitmapFactory
					.decodeStream(photoStream, null, options);
		}

		Bitmap bitmap = null;
		int h = photoBitmap.getHeight();
		int w = photoBitmap.getWidth();
		if ((w < h) && (width > height)) {
			newHeight = width;
			newWidth = height;
		}
		if ((w < newWidth) || (h < newHeight)) {
			BitmapFactory.Options options2 = new BitmapFactory.Options();
			inSampleSize--;
			options2.inSampleSize = inSampleSize;
			InputStream photoStream2 = context.getContentResolver()
					.openInputStream(photoUri);
			bitmap = BitmapFactory.decodeStream(photoStream2, null, options2);
			h = bitmap.getHeight();
			w = bitmap.getWidth();
		} else {
			bitmap = photoBitmap;
		}
		return getResizedBitmap(bitmap, newHeight, newWidth);
	}

	public static Bitmap getResizedBitmap(Bitmap bitmap, int height, int width) {
		int h = bitmap.getHeight();
		int w = bitmap.getWidth();
		if (w >= h && w >= width) {
			float ratio = (float) (height) / h;
			h = height;
			w = (int) (ratio * w);
		} else if (h > w && h > height) {
			float ratio = (float) (width) / w;
			w = width;
			h = (int) (ratio * h);
		}

		Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, w, h, true);
		return resizedBitmap;
	}

	public static Bitmap createImg(byte[] aImgBytes) {
		BitmapFactory.Options bitopt = new BitmapFactory.Options();
		bitopt.inJustDecodeBounds = false;
		Bitmap bit = null;
		try {
			bit = BitmapFactory.decodeByteArray(aImgBytes, 0, aImgBytes.length,
					bitopt);
		} catch (OutOfMemoryError e) {
			bit = null;
		}

		int _resizeTimes = 0;
		int w = bitopt.outWidth / 2;
		int h = bitopt.outHeight / 2;
		while (null == bit) {
			bit = shrinkMethod(aImgBytes, w, h);
			_resizeTimes++;
			if (_resizeTimes > 5) {
				return bit;
			}
			w = w / 2;
			h = h / 2;
		}

		return bit;
	}

	private static Bitmap shrinkMethod(byte[] aImgByte, int width, int height) {
		Bitmap bit = null;
		BitmapFactory.Options bitopt = new BitmapFactory.Options();
		bitopt.inJustDecodeBounds = true;
		BitmapFactory.decodeByteArray(aImgByte, 0, aImgByte.length, bitopt);

		if (width == 0) {
			width = 1;
		}

		if (height == 0) {
			height = 1;
		}

		int h = (int) Math.ceil(bitopt.outHeight / width);
		int w = (int) Math.ceil(bitopt.outWidth / height);

		if (h > 1 || w > 1) {
			if (h > w) {
				bitopt.inSampleSize = h;

			} else {
				bitopt.inSampleSize = w;
			}
		}
		bitopt.inJustDecodeBounds = false;
		try {
			bit = BitmapFactory.decodeByteArray(aImgByte, 0, aImgByte.length,
					bitopt);
		} catch (OutOfMemoryError e) {
			bit = null;
		}

		return bit;
	}

	public static Bitmap createImg(File aFile) {
		BitmapFactory.Options bitopt = new BitmapFactory.Options();
		bitopt.inJustDecodeBounds = false;
		Bitmap bit = null;
		try {
			bit = BitmapFactory.decodeFile(aFile.getAbsolutePath(), bitopt);
		} catch (OutOfMemoryError e) {
			bit = null;
		}
		if (null == bit) {
			bitopt.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(aFile.getAbsolutePath(), bitopt);
		}
		int _resizeTimes = 0;
		int w = bitopt.outWidth / 2;
		int h = bitopt.outHeight / 2;
		while (null == bit) {
			bit = shrinkMethod(aFile, w, h);
			_resizeTimes++;
			if (_resizeTimes > 5) {
				return bit;
			}
			w = w / 2;
			h = h / 2;
		}

		return bit;
	}

	public static Bitmap shrinkMethod(File aFile, int width, int height) {
		Bitmap bit = null;
		BitmapFactory.Options bitopt = new BitmapFactory.Options();
		bitopt.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(aFile.getAbsolutePath(), bitopt);

		if (width == 0) {
			width = 1;
		}

		if (height == 0) {
			height = 1;
		}

		int h = (int) Math.ceil(bitopt.outHeight / width);
		int w = (int) Math.ceil(bitopt.outWidth / height);

		if (h > 1 || w > 1) {
			if (h > w) {
				bitopt.inSampleSize = h;

			} else {
				bitopt.inSampleSize = w;
			}
		}
		bitopt.inJustDecodeBounds = false;
		try {
			bit = BitmapFactory.decodeFile(aFile.getAbsolutePath(), bitopt);
		} catch (OutOfMemoryError e) {
			bit = null;
		}

		return bit;
	}

	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(),
				bitmap.getHeight() + 30);
		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		// 取两者交集，显示上层
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	public static Bitmap getGrayscaleBitmap(Bitmap bmpOriginal) {
		final int height = bmpOriginal.getHeight();
		final int width = bmpOriginal.getWidth();
		Bitmap bmpGrayscale = Bitmap.createBitmap(width, height,
				Bitmap.Config.RGB_565);
		Canvas c = new Canvas(bmpGrayscale);
		Paint paint = new Paint();
		ColorMatrix cm = new ColorMatrix();
		cm.setSaturation(0);
		ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
		paint.setColorFilter(f);
		c.drawBitmap(bmpOriginal, 0, 0, paint);
		return bmpGrayscale;
	}

	public static Bitmap getRoundedBitmap(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float roundPx;
		float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
		if (width <= height) {
			roundPx = width / 2;
			left = 0;
			top = 0;
			right = width;
			bottom = width;

			height = width;

			dst_left = 0;
			dst_top = 0;
			dst_right = width;
			dst_bottom = width;
		} else {
			roundPx = height / 2;
			float clip = (width - height) / 2;
			left = clip;
			top = 0;
			right = width - clip;
			bottom = height;

			width = height;

			dst_left = 0;
			dst_top = 0;
			dst_right = height;
			dst_bottom = height;
		}

		Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect src = new Rect((int) left, (int) top, (int) right,
				(int) bottom);
		final Rect dst = new Rect((int) dst_left, (int) dst_top,
				(int) dst_right, (int) dst_bottom);
		final RectF rectF = new RectF(dst);

		paint.setAntiAlias(true);

		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, src, dst, paint);
		return output;
	}

	public static void recycleBitmap(Bitmap bitmap) {
		if (bitmap != null && !bitmap.isRecycled()) {
			bitmap.recycle();
			bitmap = null;
		}
		System.gc();
	}

	/**
	 * 图片转化成圆角
	 * 
	 * @param filePath
	 * @return
	 */
	public static BitmapDrawable getRoundedDrawable(String filePath) {
		Bitmap bm = BitmapFactory.decodeFile(filePath);
		bm = BitmapUtils.getRoundedCornerBitmap(bm, 7.0f);
		return bitmapToDrawable(bm);
	}

	/**
	 * 图片转化成圆角
	 * 
	 * @param filePath
	 * @return
	 */
	public static BitmapDrawable getRoundedDrawable(Drawable d) {
		Bitmap bm = drawableToBitmap(d);
		bm = BitmapUtils.getRoundedCornerBitmap(bm, 7.0f);
		return bitmapToDrawable(bm);
	}

	/**
	 * 图片加背景
	 * 
	 * @param color
	 * @param mBitmap
	 * @return
	 */
	public static Bitmap drawBgBitmap(int color, Bitmap mBitmap) {
		Paint mPaint = new Paint();
		mPaint.setColor(color);
		Bitmap aBitmap = Bitmap.createBitmap(mBitmap.getWidth(),
				mBitmap.getHeight(), mBitmap.getConfig());
		Canvas mCanvas = new Canvas(aBitmap);
		mCanvas.drawRect(0, 0, mBitmap.getWidth(), mBitmap.getHeight(), mPaint);
		mCanvas.drawBitmap(mBitmap, 0, 0, mPaint);
		return aBitmap;
	}

}
