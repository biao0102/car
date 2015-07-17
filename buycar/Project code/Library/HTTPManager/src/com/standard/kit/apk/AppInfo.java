package com.standard.kit.apk;

import java.io.Serializable;

import android.graphics.drawable.Drawable;

/**
 * @author landmark
 * 
 * @date 2012-11-12 下午2:41:02
 * 
 * @version
 */
public class AppInfo implements Serializable {

	public static final long serialVersionUID = 1L;

	public String lable;

	public Drawable icon;

	public String versionName;

	public int versionCode;

	public String packageName;

	public String publicSourceDir;

	public String publicKey;

	public String packageMd5;

	public long apkSize = 0;

	public String packageSize;

	public long installTime;

}
