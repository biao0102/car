package com.standard.kit.apk;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.opengl.GLES10;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

/**
 * @author landmark
 * 
 * @data 2011-11-11
 * 
 */
public class AppUtil {

	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static float sp2px(Context context, int spValue) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue,
				context.getResources().getDisplayMetrics());
	}

	public static int getWidth(Context context) {
		DisplayMetrics dm = context.getApplicationContext().getResources()
				.getDisplayMetrics();
		return dm.widthPixels;
	}

	public static int getHeight(Context context) {
		DisplayMetrics dm = context.getApplicationContext().getResources()
				.getDisplayMetrics();
		return dm.heightPixels;
	}

	public static int getStatusBarHeight(Context context) {
		Rect rect = new Rect();
		((Activity) context).getWindow().getDecorView()
				.getWindowVisibleDisplayFrame(rect);
		return rect.top;
	}

	public static int getTitleBarHeight(Context context) {
		int contentTop = ((Activity) context).getWindow()
				.findViewById(Window.ID_ANDROID_CONTENT).getTop();
		int statusBarHeight = getStatusBarHeight(context);
		return contentTop - statusBarHeight;
	}

	public static void showSoftInput(Context aContext, View view) {
		InputMethodManager im = ((InputMethodManager) aContext
				.getSystemService(Context.INPUT_METHOD_SERVICE));
		im.showSoftInput(view, 0);
	}

	public static void hideSoftInput(Context aContext, View view) {
		InputMethodManager im = ((InputMethodManager) aContext
				.getSystemService(Context.INPUT_METHOD_SERVICE));
		im.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	public static boolean isOpenInputMethod(Context aContext) {
		try {
			InputMethodManager imm = (InputMethodManager) aContext
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			return imm.isActive();
		} catch (Exception e) {
			return false;
		}
	}

	public static float getDensity(Context context) {
		return context.getResources().getDisplayMetrics().density;
	}

	public static int getRandomNum(int mixNum, int _num) {
		return (int) (Math.random() * _num);
	}

	public static int getProgress(long downloadSize, long totalSize) {
		if (totalSize == 0)
			return 0;
		else
			return (int) (downloadSize * 100 / totalSize);
	}

	public static float getRating(String rate) {
		if (rate != null) {
			if (rate.trim().length() == 2) {
				return Float.valueOf(rate) / 10;
			} else {
				return Float.valueOf(rate);
			}
		}
		return 0;
	}

	public static int[] getLocationOnScreen(View v) {
		int[] location = new int[2];
		v.getLocationOnScreen(location);
		return location;
	}

	public static String ToDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

	public static void setComponentEnabled(Context context, Class<?> cls,
			boolean enabled) {
		final ComponentName c = new ComponentName(context, cls.getName());
		context.getPackageManager().setComponentEnabledSetting(
				c,
				enabled ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED
						: PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
				PackageManager.DONT_KILL_APP);
	}

	public static int getTextToInteger(String text) {
		if (TextUtils.isEmpty(text)) {
			return 0;
		} else if (TextUtils.isDigitsOnly(text)) {
			return Integer.valueOf(text);
		}
		return 0;
	}

	public static float getTextToFloat(String text) {
		if (TextUtils.isEmpty(text)) {
			return 0;
		} else {
			return Float.valueOf(text);
		}
	}

	/**
	 * 图片采用的720的，所以获取比例会/720
	 * 
	 * @param mContext
	 * @return 得到手机宽相对720的比例
	 */
	public static float getScale(Context mContext) {
		float scale;
		int width = getWidth(mContext);
		int height = getHeight(mContext);
		scale = width < height ? width / 720f : height / 720f;
		return scale;
	}

	public static Bitmap revitionImageSize(Bitmap aBitmap, int size)
			throws IOException {
		// 取得图片
		InputStream temp = Bitmap2InputStream(aBitmap, 50);
		BitmapFactory.Options options = new BitmapFactory.Options();
		// 这个参数代表，不为bitmap分配内存空间，只记录一些该图片的信息（例如图片大小），说白了就是为了内存优化
		options.inJustDecodeBounds = true;
		// 通过创建图片的方式，取得options的内容（这里就是利用了java的地址传递来赋值）
		BitmapFactory.decodeStream(temp, null, options);
		// 关闭流
		temp.close();

		// 生成压缩的图片
		int i = 0;
		Bitmap bitmap = null;
		while (true) {
			// 这一步是根据要设置的大小，使宽和高都能满足
			if (options.outHeight >> i <= size) {
				// 重新取得流，注意：这里一定要再次加载，不能二次使用之前的流！
				temp = Bitmap2InputStream(aBitmap, 50);
				// 这个参数表示 新生成的图片为原始图片的几分之一。
				options.inSampleSize = (int) Math.pow(2.0D, i);
				// 这里之前设置为了true，所以要改为false，否则就创建不出图片
				options.inJustDecodeBounds = false;

				bitmap = BitmapFactory.decodeStream(temp, null, options);
				break;
			}
			i += 1;
		}
		return bitmap;
	}

	// 将Bitmap转换成InputStream
	public static InputStream Bitmap2InputStream(Bitmap bm, int quality) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, quality, baos);
		InputStream is = new ByteArrayInputStream(baos.toByteArray());
		return is;
	}

	public static int getMaxLeftWidthOrHeightImageViewCanRead(int heightOrWidth) {
		int[] maxSizeArray = new int[1];
		GLES10.glGetIntegerv(GL10.GL_MAX_TEXTURE_SIZE, maxSizeArray, 0);

		if (maxSizeArray[0] == 0) {
			GLES10.glGetIntegerv(GL11.GL_MAX_TEXTURE_SIZE, maxSizeArray, 0);
		}
		int maxHeight = maxSizeArray[0];
		int maxWidth = maxSizeArray[0];

		return (maxHeight * maxWidth) / heightOrWidth;
	}

	// 获取适配长度，若屏宽大于等于720返回0;否则返回AppUtil.dip2px(12)
	public static int getRightMarginOrPading(Context context) {

		if (AppUtil.getWidth(context) >= 720) {
			return 0;
		} else {
			return AppUtil.dip2px(context, 12);
		}
	}

}
