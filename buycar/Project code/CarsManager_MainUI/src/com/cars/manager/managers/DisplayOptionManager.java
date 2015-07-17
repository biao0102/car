package com.cars.manager.managers;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * @author chengyanfang
 * 
 *         提供图片加载模式
 * 
 * */
public class DisplayOptionManager {

	public static DisplayImageOptions getDefaultDisplayOptions(int resId) {
		return new DisplayImageOptions.Builder().cacheInMemory(true).resetViewBeforeLoading(true).imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565).showImageOnLoading(resId).showImageOnFail(resId).showImageForEmptyUri(resId).build();

	}

	public static DisplayImageOptions getRoundedDisplayOptions(int resId) {
		return new DisplayImageOptions.Builder().delayBeforeLoading(0).cacheInMemory(true).showImageOnLoading(resId).resetViewBeforeLoading(false)
				.cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).showStubImage(resId).showImageOnFail(resId).showImageForEmptyUri(resId).build();
	}

	public static DisplayImageOptions getRoundedDisplayOptions(int resId, int rounderpx) {
		return new DisplayImageOptions.Builder().delayBeforeLoading(0).considerExifParams(true).cacheInMemory(true).resetViewBeforeLoading(false)
				.cacheOnDisc(true).bitmapConfig(Bitmap.Config.ARGB_8888).displayer(new RoundedBitmapDisplayer(rounderpx)).build();
	}

	public static DisplayImageOptions getNoneDisplayOptions(int resId) {
		return new DisplayImageOptions.Builder().cacheInMemory(true).resetViewBeforeLoading(false).cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565)
				.showStubImage(resId).showImageOnFail(resId).showImageForEmptyUri(resId).build();
	}
}
