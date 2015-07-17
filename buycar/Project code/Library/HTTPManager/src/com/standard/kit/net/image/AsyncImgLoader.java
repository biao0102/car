package com.standard.kit.net.image;

import java.lang.ref.SoftReference;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.standard.kit.file.FileUtil;
import com.standard.kit.net.http.HttpImageCache;

public class AsyncImgLoader {
	private static Context mContext = null;
	private HashMap<String, SoftReference<Drawable>> imageCache;

	public AsyncImgLoader(Context c) {
		mContext = c;
		imageCache = new HashMap<String, SoftReference<Drawable>>();
	}

	public AsyncImgLoader() {
		imageCache = new HashMap<String, SoftReference<Drawable>>();
	}

	/**
	 * ????????????
	 * 
	 * @param v
	 * @param url
	 * @return
	 */
	public void setViewImage(final ImageView v, String url) {
		if ((null == url) || (url.length() < 1)) {
			return;
		}
		loadDrawable(url, new AsyncImgLoader.ImageCallback() {
			@Override
			public void imageLoaded(Drawable imageDrawable, String imageUrl) {
				if (imageDrawable != null && imageDrawable.getIntrinsicWidth() > 0) {
					try {
						v.setImageDrawable(imageDrawable);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	/**
	 * ????????????
	 * 
	 * @param v
	 * @param aPath
	 * @return
	 */
	public void setViewLocalImage(final ImageView v, String aPath) {
		if ((null == aPath) || (aPath.length() < 1)) {
			return;
		}
		loadDrawableFromLocal(aPath, new AsyncImgLoader.ImageCallback() {
			@Override
			public void imageLoaded(Drawable imageDrawable, String imageUrl) {
				if (imageDrawable != null && imageDrawable.getIntrinsicWidth() > 0) {
					try {
						v.setImageDrawable(imageDrawable);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	/**
	 * ??????
	 * 
	 * @param imageUrl
	 * @param imageCallback
	 */
	private void loadDrawable(final String imageUrl, final ImageCallback imageCallback) {

		if (imageCache.containsKey(imageUrl)) {
			SoftReference<Drawable> softReference = imageCache.get(imageUrl);
			Drawable drawable = softReference.get();
			if (drawable != null) {
				imageCallback.imageLoaded(drawable, imageUrl);
				return;
			}
		}
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message message) {
				imageCallback.imageLoaded((Drawable) message.obj, imageUrl);
			}
		};
		new Thread() {
			@Override
			public void run() {
				Drawable drawable = null;
				drawable = loadImageFromUrlWithCache(imageUrl);
				// if(mContext==null){
				// drawable= loadImageFromUrlWithCache(imageUrl);
				// }else{
				// drawable = loadImageFromUrlWithCache(imageUrl);
				// }
				imageCache.put(imageUrl, new SoftReference<Drawable>(drawable));
				Message message = handler.obtainMessage(0, drawable);
				handler.sendMessage(message);
			}
		}.start();
	}

	/**
	 * ??????
	 * 
	 * @param imgPath
	 * @param imageCallback
	 */
	private void loadDrawableFromLocal(final String imgPath, final ImageCallback imageCallback) {

		if (imageCache.containsKey(imgPath)) {
			SoftReference<Drawable> softReference = imageCache.get(imgPath);
			Drawable drawable = softReference.get();
			if (drawable != null) {
				imageCallback.imageLoaded(drawable, imgPath);
				return;
			}
		}
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message message) {
				imageCallback.imageLoaded((Drawable) message.obj, imgPath);
			}
		};
		new Thread() {
			@Override
			public void run() {
				byte[] _imgBytes = FileUtil.getFileContent(imgPath);
				Options opt = getProperOption(_imgBytes, 201, 151);
				Bitmap bit = BitmapFactory.decodeByteArray(_imgBytes, 0, _imgBytes.length, opt);

				// byte[] _imgBytes = FileUtil.getFileContent(imgPath);
				// Bitmap aBit = BitmapFactory.decodeByteArray(_imgBytes, 0,
				// _imgBytes.length);
				Drawable drawable = new BitmapDrawable(bit);

				imageCache.put(imgPath, new SoftReference<Drawable>(drawable));
				Message message = handler.obtainMessage(0, drawable);
				handler.sendMessage(message);
			}
		}.start();
	}

	/**
	 * To get an Option object which is often used to resize a picture according
	 * to limited width and height.
	 * 
	 * @return
	 */
	public static BitmapFactory.Options getProperOption(byte[] byteArray, int screenWidth, int screenHeight) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inJustDecodeBounds = true;

		int picWidth = opt.outWidth;
		int picHeight = opt.outHeight;

		opt.inSampleSize = 1;
		if (picWidth > picHeight) {
			if (picWidth > screenWidth)
				opt.inSampleSize = picWidth / screenWidth;
		} else {
			if (picHeight > screenHeight)
				opt.inSampleSize = picHeight / screenHeight;
		}

		opt.inJustDecodeBounds = false;

		return opt;
	}

	private Drawable loadImageFromUrlWithCache(String url) {

		Bitmap _b = HttpImageCache.getImg(url, mContext);
		if (null == _b) {
			return null;
		}
		Drawable d = new BitmapDrawable(_b);
		return d;
	}

	public interface ImageCallback {
		public void imageLoaded(Drawable imageDrawable, String imageUrl);
	}

}