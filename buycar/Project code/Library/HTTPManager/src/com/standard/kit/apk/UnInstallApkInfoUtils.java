package com.standard.kit.apk;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;

public class UnInstallApkInfoUtils {

	private String mApkPath;

	private Context mContext;

	private ApplicationInfo mApplicationInfo;

	private Resources mRes;

	public boolean isBadFile = false;

	public UnInstallApkInfoUtils(Context context, String filePath) {
		this.mApkPath = filePath;
		this.mContext = context;
		initApkInfo();
	}

	private void initApkInfo() {
		File apkFile = new File(mApkPath);
		if (!apkFile.exists() || !mApkPath.toLowerCase().endsWith(".apk")) {
			isBadFile = true;
		}
		String PATH_PackageParser = "android.content.pm.PackageParser";
		String PATH_AssetManager = "android.content.res.AssetManager";
		try {
			// 反射得到pkgParserCls对象并实例化,有参数
			Class<?> pkgParserCls = Class.forName(PATH_PackageParser);
			Class<?>[] typeArgs = { String.class };
			Constructor<?> pkgParserCt = pkgParserCls.getConstructor(typeArgs);
			Object[] valueArgs = { mApkPath };
			Object pkgParser = pkgParserCt.newInstance(valueArgs);

			// 从pkgParserCls类得到parsePackage方法
			DisplayMetrics metrics = new DisplayMetrics();
			metrics.setToDefaults();// 这个是与显示有关的, 这边使用默认
			typeArgs = new Class<?>[] { File.class, String.class, DisplayMetrics.class, int.class };
			Method pkgParser_parsePackageMtd = pkgParserCls.getDeclaredMethod("parsePackage", typeArgs);

			valueArgs = new Object[] { new File(mApkPath), mApkPath, metrics, 0 };

			// 执行pkgParser_parsePackageMtd方法并返回
			Object pkgParserPkg = pkgParser_parsePackageMtd.invoke(pkgParser, valueArgs);

			// 从返回的对象得到名为"applicationInfo"的字段对象
			if (pkgParserPkg == null) {
				// return null;
				isBadFile = true;
			}
			Field appInfoFld = pkgParserPkg.getClass().getDeclaredField("applicationInfo");

			// 从对象"pkgParserPkg"得到字段"appInfoFld"的值
			if (appInfoFld.get(pkgParserPkg) == null) {
				// return null;
				isBadFile = true;
			}
			this.mApplicationInfo = (ApplicationInfo) appInfoFld.get(pkgParserPkg);

			// 反射得到assetMagCls对象并实例化,无参
			Class<?> assetMagCls = Class.forName(PATH_AssetManager);
			Object assetMag = assetMagCls.newInstance();
			// 从assetMagCls类得到addAssetPath方法
			typeArgs = new Class[1];
			typeArgs[0] = String.class;
			Method assetMag_addAssetPathMtd = assetMagCls.getDeclaredMethod("addAssetPath", typeArgs);
			valueArgs = new Object[1];
			valueArgs[0] = mApkPath;
			// 执行assetMag_addAssetPathMtd方法
			assetMag_addAssetPathMtd.invoke(assetMag, valueArgs);

			// 得到Resources对象并实例化,有参数
			Resources res = mContext.getResources();
			typeArgs = new Class[3];
			typeArgs[0] = assetMag.getClass();
			typeArgs[1] = res.getDisplayMetrics().getClass();
			typeArgs[2] = res.getConfiguration().getClass();
			Constructor<Resources> resCt = Resources.class.getConstructor(typeArgs);
			valueArgs = new Object[3];
			valueArgs[0] = assetMag;
			valueArgs[1] = res.getDisplayMetrics();
			valueArgs[2] = res.getConfiguration();
			this.mRes = (Resources) resCt.newInstance(valueArgs);

		} catch (Exception e) {
			e.printStackTrace();
			this.isBadFile = true;
		}
	}

	public Drawable getIcon() {
		if (this.isBadFile)
			return null;
		Drawable icon = mRes.getDrawable(mApplicationInfo.icon);
		return icon;
	}

	public String getName() {
		if (this.isBadFile)
			return null;
		return mRes.getString(mApplicationInfo.labelRes);
	}

}
