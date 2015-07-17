package com.standard.kit.apk;

import java.lang.reflect.Field;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;

public class AppToSD {

	private static final String SCHEME = "package";

	/**
	 * 调用系统InstalledAppDetails界面所需的Extra名称(用于Android 2.1及之前版本)
	 */
	private static final String APP_PKG_NAME_21 = "com.android.settings.ApplicationPkgName";

	/**
	 * 调用系统InstalledAppDetails界面所需的Extra名称(用于Android 2.2)
	 */
	private static final String APP_PKG_NAME_22 = "pkg";

	/**
	 * InstalledAppDetails所在包名
	 */
	private static final String APP_DETAILS_PACKAGE_NAME = "com.android.settings";

	/**
	 * InstalledAppDetails类名
	 */
	private static final String APP_DETAILS_CLASS_NAME = "com.android.settings.InstalledAppDetails";

	/**
	 * 调用系统InstalledAppDetails界面显示已安装应用程序的详细信息。 对于Android 2.3（Api Level
	 * 9）以上，使用SDK提供的接口； 2.3以下，使用非公开的接口（查看InstalledAppDetails源码）。
	 * 
	 * @param context
	 * 
	 * @param packageName
	 *            应用程序的包名
	 */
	public static void showInstalledAppDetails(Context context, String packageName) {
		Intent intent = getMoveToSdcardIntent(context, packageName);
		context.startActivity(intent);
	}

	public static Intent getMoveToSdcardIntent(Context context, String packageName) {
		Intent intent = new Intent();
		final int apiLevel = Build.VERSION.SDK_INT;
		if (apiLevel >= 9) { // 2.3（ApiLevel 9）以上，使用SDK提供的接口
			// intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
			intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
			Uri uri = Uri.fromParts(SCHEME, packageName, null);
			intent.setData(uri);
		} else { // 2.3以下，使用非公开的接口（查看InstalledAppDetails源码）
			// 2.2和2.1中，InstalledAppDetails使用的APP_PKG_NAME不同。
			final String appPkgName = (apiLevel == 8 ? APP_PKG_NAME_22 : APP_PKG_NAME_21);
			intent.setAction(Intent.ACTION_VIEW);
			intent.setClassName(APP_DETAILS_PACKAGE_NAME, APP_DETAILS_CLASS_NAME);
			intent.putExtra(appPkgName, packageName);
		}
		return intent;
	}

	public static boolean isInstallOnSDCard(Context context, String packageName) {
		PackageInfo packageInfo = PackageUtil.getPackageInfo(context, packageName);
		if (packageInfo == null) {
			return false;
		}
		ApplicationInfo applicationInfo = packageInfo.applicationInfo;
		if (applicationInfo == null) {
			return false;
		}
		if (VERSION.SDK_INT == Build.VERSION_CODES.ECLAIR_MR1) {
			String filesDir = context.getFilesDir().getAbsolutePath();
			if (filesDir.startsWith("/data/")) {
				return false;
			} else if (filesDir.contains("/mnt/") || filesDir.contains("/sdcard/")) {
				return true;
			}
		} else if (VERSION.SDK_INT > Build.VERSION_CODES.ECLAIR_MR1) {
			return (applicationInfo.flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) != 0;
		}
		return false;
	}

	public static boolean isMovableToSDcard(Context context, String packageName) {
		PackageInfo packageInfo = PackageUtil.getPackageInfo(context, packageName);
		if (packageInfo == null) {
			return false;
		}
		ApplicationInfo applicationInfo = packageInfo.applicationInfo;
		if (applicationInfo == null) {
			return false;
		}
		if (PackageUtil.isSystemApp(applicationInfo)) {
			return false;
		}
		int installLocation = getInstallLocationInt(packageInfo);
		if (installLocation == 0 || installLocation == 2) {
			return true;
		}
		return false;
	}

	private static final int getInstallLocationInt(PackageInfo packageInfo) {
		try {
			Class<?> cls = Class.forName(packageInfo.getClass().getName());
			Field field = cls.getDeclaredField("installLocation");
			field.setAccessible(true);
			if (field != null) {
				return field.getInt(packageInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}
