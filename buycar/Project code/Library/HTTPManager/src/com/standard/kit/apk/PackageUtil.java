package com.standard.kit.apk;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;

import com.standard.entity.RunningAppInfo;
import com.standard.kit.file.FileUtil;
import com.standard.kit.md5.MD5;
import com.standard.kit.text.TextUtil;

/**
 * 完成以下功能： 己安装程序，APK相关信息，包名，路径，证书公钥等读取；APK安装，卸载；文件打开
 * 
 * @version v1.0
 * 
 * @author neil lizhize
 */
public class PackageUtil
{
	private final static String[][] MIME_MapTable = { { ".3gp", "video/3gpp" }, { ".apk", "application/vnd.android.package-archive" },
			{ ".asf", "video/x-ms-asf" }, { ".avi", "video/x-msvideo" }, { ".bin", "application/octet-stream" }, { ".bmp", "image/bmp" },
			{ ".c", "text/plain" }, { ".class", "application/octet-stream" }, { ".conf", "text/plain" }, { ".cpp", "text/plain" },
			{ ".doc", "application/msword" }, { ".exe", "application/octet-stream" }, { ".gif", "image/gif" },
			{ ".gtar", "application/x-gtar" }, { ".gz", "application/x-gzip" }, { ".h", "text/plain" }, { ".htm", "text/html" },
			{ ".html", "text/html" }, { ".jar", "application/java-archive" }, { ".java", "text/plain" }, { ".jpeg", "image/jpeg" },
			{ ".jpg", "image/jpeg" }, { ".js", "application/x-javascript" }, { ".log", "text/plain" }, { ".m3u", "audio/x-mpegurl" },
			{ ".m4a", "audio/mp4a-latm" }, { ".m4b", "audio/mp4a-latm" }, { ".m4p", "audio/mp4a-latm" }, { ".m4u", "video/vnd.mpegurl" },
			{ ".m4v", "video/x-m4v" }, { ".mov", "video/quicktime" }, { ".mp2", "audio/x-mpeg" }, { ".mp3", "audio/x-mpeg" },
			{ ".mp4", "video/mp4" }, { ".mpc", "application/vnd.mpohun.certificate" }, { ".mpe", "video/mpeg" }, { ".mpeg", "video/mpeg" },
			{ ".mpg", "video/mpeg" }, { ".mpg4", "video/mp4" }, { ".mpga", "audio/mpeg" }, { ".msg", "application/vnd.ms-outlook" },
			{ ".ogg", "audio/ogg" }, { ".pdf", "application/pdf" }, { ".png", "image/png" }, { ".pps", "application/vnd.ms-powerpoint" },
			{ ".ppt", "application/vnd.ms-powerpoint" }, { ".prop", "text/plain" }, { ".rar", "application/x-rar-compressed" },
			{ ".rc", "text/plain" }, { ".rmvb", "audio/x-pn-realaudio" }, { ".rtf", "application/rtf" }, { ".sh", "text/plain" },
			{ ".tar", "application/x-tar" }, { ".tgz", "application/x-compressed" }, { ".txt", "text/plain" }, { ".wav", "audio/x-wav" },
			{ ".wma", "audio/x-ms-wma" }, { ".wmv", "audio/x-ms-wmv" }, { ".wps", "application/vnd.ms-works" }, { ".xml", "text/xml" },
			{ ".xml", "text/plain" }, { ".z", "application/x-compress" }, { ".zip", "application/zip" }, { "", "*/*" } };

	/**
	 * 查询手机内所有支持分享的应用
	 * 
	 * @param context
	 * @return 支持分享的应用列表
	 */
	public static List<ResolveInfo> getShareApps(Context context)
	{
		List<ResolveInfo> mApps = new ArrayList<ResolveInfo>();
		Intent intent = new Intent(Intent.ACTION_SEND, null);
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		intent.setType("text/plain");
		PackageManager pManager = context.getPackageManager();
		mApps = pManager.queryIntentActivities(intent, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);

		return mApps;
	}

	public static boolean isSystemApp(ApplicationInfo applicationInfo)
	{
		return (applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
	}

	public static boolean isSystemUpdateApp(ApplicationInfo applicationInfo)
	{
		return (applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0;
	}

	/**
	 * 枚举出己安装的应用。
	 * 
	 * @param context
	 *            context句柄，读取安装包的信息。
	 * @return List<PackageInfo> 己安装应用的列表。
	 */
	public static List<PackageInfo> getInstallApp(Context context)
	{
		List<PackageInfo> list = context.getPackageManager().getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
		int i = list.size() - 1;
		while (i > 0)
		{
			if (!isAppLaunchable(context, list.get(i).packageName))
			{
				list.remove(i);
			}
			i--;
		}
		return list;
	}

	public static boolean isAppLaunchable(Context context, String packageName)
	{
		return null != context.getPackageManager().getLaunchIntentForPackage(packageName);
	}

	public static boolean isNeedUninstall(Context context, String packageName, String filePath)
	{
		PackageInfo installInfo = getPackageInfo(context, packageName);
		if (installInfo == null)
		{
			return false;
		}
		String installKey = PublicKeyUtil.getPublicKeyByPackageInfo(installInfo);
		if (TextUtil.isEmpty(installKey))
		{
			return false;
		}
		if (filePath == null)
		{
			return false;
		}
		String fileKey = PublicKeyUtil.getPublicKeyByPath(filePath);
		if (TextUtil.isEmpty(fileKey))
		{
			return false;
		}
		if (fileKey.equals(installKey))
		{
			return false;
		}
		return true;
	}

	public static Drawable getUninstallApkIcon(Context context, String filePath)
	{
		if (Build.VERSION.SDK_INT >= 8)
		{// 2.2以上单独处理
			return new UnInstallApkInfoUtils(context, filePath).getIcon();
		}
		return loadUninstallApkIcon(context, filePath);
		// PackageManager pm = context.getPackageManager();
		// PackageInfo info = getUninstallPackageInfo(context, filePath);
		// if (info != null) {
		// return pm.getApplicationIcon(info.applicationInfo);
		// }
		// return null;
	}

	public static Drawable loadUninstallApkIcon(Context context, String filePath)
	{
		PackageManager pm = context.getPackageManager();
		PackageInfo info = pm.getPackageArchiveInfo(filePath, PackageManager.GET_ACTIVITIES);

		if (info == null)
		{
			return null;
		}

		ApplicationInfo appInfo = info.applicationInfo;
		appInfo.sourceDir = filePath;
		appInfo.publicSourceDir = filePath;

		return appInfo.loadIcon(pm);
	}

	public static Drawable getApkIcon(Context context, String packageName)
	{
		try
		{
			PackageManager pm = context.getPackageManager();
			PackageInfo packageInfo = pm.getPackageInfo(packageName, 0);
			Drawable d = packageInfo.applicationInfo.loadIcon(pm);
			return d;
		}
		catch (NameNotFoundException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static boolean isPackageInstalled(Context context, String packageName)
	{
		try
		{
			PackageManager pm = context.getPackageManager();
			// pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES |
			// PackageManager.GET_META_DATA);
			pm.getPackageInfo(packageName, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
			return true;
		}
		catch (NameNotFoundException e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public static int getVersionCode(Context context, String packageName)
	{
		int versionCode = 1;
		PackageInfo packageInfo = getPackageInfo(context, packageName);
		if (packageInfo != null)
		{
			versionCode = packageInfo.versionCode;
		}
		return versionCode;
	}

	public static String getVersionName(Context context, String packageName)
	{
		String versionName = "1.0.0";
		PackageInfo packageInfo = getPackageInfo(context, packageName);
		if (packageInfo != null)
		{
			versionName = packageInfo.versionName;
		}
		return versionName;
	}

	public static PackageInfo getPackageInfo(Context context, String packageName)
	{
		try
		{
			PackageManager pm = context.getPackageManager();
			PackageInfo info = pm.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
			return info;
		}
		catch (NameNotFoundException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static PackageInfo getUninstallPackageInfo(Context context, String filePath)
	{
		PackageManager pm = context.getPackageManager();
		PackageInfo info = pm.getPackageArchiveInfo(filePath, PackageManager.GET_ACTIVITIES);
		return info;
	}

	public static String getInstalledVersion(Context context, String packageName)
	{
		String version = null;
		PackageInfo packageInfo = getPackageInfo(context, packageName);
		if (packageInfo == null)
		{
			version = null;
		}
		else
		{
			version = packageInfo.versionCode + "/" + packageInfo.versionName;
		}
		return version;
	}

	public static int getVersionCodeInt(String version)
	{
		if (TextUtil.isEmpty(version))
		{
			return 0;
		}
		int index = version.indexOf('/');
		if (index == -1)
		{
			return 0;
		}
		int versionCode = Integer.parseInt(version.substring(0, index).trim());
		return versionCode;
	}

	public static void installApk(Context context, String filePath)
	{
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		Uri uri = Uri.fromFile(new File(filePath));
		intent.setDataAndType(uri, "application/vnd.android.package-archive");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	public static void unInstall(Context context, String packageName)
	{
		Uri uri = Uri.fromParts("package", packageName, null);
		Intent intent = new Intent(Intent.ACTION_DELETE, uri);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	public static void startUp(Context context, String packageName)
	{
		Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	public static void openFile(Context context, String filePath)
	{
		if (FileUtil.isEixstsFile(filePath))
		{
			// Uri uri = Uri.parse("file://"+file.getAbsolutePath());
			Intent intent = new Intent();
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			// 设置intent的Action属性
			intent.setAction(Intent.ACTION_VIEW);
			// 获取文件file的MIME类型
			String type = getMIMEType(filePath);
			// 设置intent的data和Type属性。
			intent.setDataAndType(Uri.fromFile(new File(filePath)), type);
			// 跳转
			context.startActivity(intent);
		}
	}

	private static String getMIMEType(String filePath)
	{
		String type = "*/*";
		// 获取后缀名前的分隔符"."在fName中的位置。
		int dotIndex = filePath.lastIndexOf(".");
		if (dotIndex == -1) return type;
		/* 获取文件的后缀名 */
		String end = filePath.substring(dotIndex, filePath.length()).toLowerCase();
		if (end == "") return type;
		// 在MIME和文件类型的匹配表中找到对应的MIME类型。
		for (int i = 0; i < MIME_MapTable.length; i++)
		{
			if (end.equals(MIME_MapTable[i][0])) type = MIME_MapTable[i][1];
		}
		return type;
	}

	public static boolean startPackageLaunch(Context mContext, String packageName)
	{
		try
		{
			Intent intent = mContext.getPackageManager().getLaunchIntentForPackage(packageName);
			mContext.startActivity(intent);
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public static List<RunningAppInfo> getAllRunningAppInfo(Context aContext, boolean isSystemInclude)
	{
		PackageManager _pm = aContext.getPackageManager();
		// 查询所有已经安装的应用程序
		List<ApplicationInfo> listAppcations = _pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
		Collections.sort(listAppcations, new ApplicationInfo.DisplayNameComparator(_pm));// 排序

		// 保存所有正在运行的包名 以及它所在的进程信息
		Map<String, ActivityManager.RunningAppProcessInfo> pgkProcessAppMap = new HashMap<String, ActivityManager.RunningAppProcessInfo>();

		ActivityManager mActivityManager = (ActivityManager) aContext.getSystemService(Context.ACTIVITY_SERVICE);
		// 通过调用ActivityManager的getRunningAppProcesses()方法获得系统里所有正在运行的进程
		List<ActivityManager.RunningAppProcessInfo> appProcessList = mActivityManager.getRunningAppProcesses();

		for (ActivityManager.RunningAppProcessInfo appProcess : appProcessList)
		{
			String[] pkgNameList = appProcess.pkgList; // 获得运行在该进程里的所有应用程序包

			// 输出所有应用程序的包名
			for (int i = 0; i < pkgNameList.length; i++)
			{
				String pkgName = pkgNameList[i];
				// 加入至map对象里
				pgkProcessAppMap.put(pkgName, appProcess);
			}
		}
		// 保存所有正在运行的应用程序信息
		List<RunningAppInfo> runningAppInfos = new ArrayList<RunningAppInfo>(); // 保存过滤查到的AppInfo

		for (ApplicationInfo app : listAppcations)
		{
			// 如果该包名存在 则构造一个RunningAppInfo对象
			if (pgkProcessAppMap.containsKey(app.packageName))
			{
				// 根据参数进行系统应用过滤
				if (isSystemInclude || ((app.flags & ApplicationInfo.FLAG_SYSTEM) == 0))
				{
					// 获得该packageName的 pid 和 processName
					int pid = pgkProcessAppMap.get(app.packageName).pid;
					String processName = pgkProcessAppMap.get(app.packageName).processName;
					runningAppInfos.add(getAppInfo(aContext, app, pid, processName));
				}
			}
		}
		return runningAppInfos;
	}

	/**
	 * 构造一个RunningAppInfo对象 ，并赋值
	 * 
	 * @param app
	 *            ：应用信息
	 * @param pid
	 *            : 该应用程序所在的进程号
	 * @param processName
	 *            ：该应用程序所在的进程名
	 * @return
	 */
	private static RunningAppInfo getAppInfo(Context aContext, ApplicationInfo app, int pid, String processName)
	{
		PackageManager _pm = aContext.getPackageManager();
		RunningAppInfo appInfo = new RunningAppInfo();
		appInfo.setAppLabel((String) app.loadLabel(_pm));
		appInfo.setAppIcon(app.loadIcon(_pm));
		appInfo.setPkgName(app.packageName);
		appInfo.setPid(pid);
		appInfo.setProcessName(processName);
		return appInfo;
	}

	public static boolean isServiceRunning(Context aContext, String aServiceClassName)
	{
		boolean isRunning = false;
		ActivityManager myManager = (ActivityManager) aContext.getSystemService(Context.ACTIVITY_SERVICE);
		ArrayList<RunningServiceInfo> runningService = (ArrayList<RunningServiceInfo>) myManager.getRunningServices(30);
		for (int i = 0; i < runningService.size(); i++)
		{
			if (runningService.get(i).service.getClassName().toString().equals(aServiceClassName))
			{
				isRunning = true;
				break;
			}
		}
		return isRunning;
	}

	public static List<AppInfo> getAppList(Context aContext)
	{
		List<PackageInfo> _apks = PackageUtil.getInstallApp(aContext);
		List<AppInfo> _result = new ArrayList<AppInfo>();
		for (PackageInfo packageInfo : _apks)
		{
			AppInfo _apkInfo = new AppInfo();
			_apkInfo.lable = packageInfo.applicationInfo.loadLabel(aContext.getPackageManager()).toString();
			_apkInfo.packageName = packageInfo.packageName;
			_apkInfo.versionCode = packageInfo.versionCode;
			_apkInfo.versionName = packageInfo.versionName;
			_apkInfo.publicSourceDir = packageInfo.applicationInfo.publicSourceDir;
			_apkInfo.publicKey = PublicKeyUtil.getPublicKeyByPath(_apkInfo.publicSourceDir);
			_apkInfo.packageMd5 = MD5.getFileMd5(_apkInfo.publicSourceDir);
			_result.add(_apkInfo);
		}
		return _result;
	}

	public static List<AppInfo> reloadApplist(Context aContext, ArrayList<AppInfo> aAppList)
	{
		List<PackageInfo> _apks = PackageUtil.getInstallApp(aContext);
		List<AppInfo> _result = aAppList;
		aAppList.clear();
		for (PackageInfo packageInfo : _apks)
		{
			AppInfo _apkInfo = new AppInfo();
			_apkInfo.lable = packageInfo.applicationInfo.loadLabel(aContext.getPackageManager()).toString();
			_apkInfo.packageName = packageInfo.packageName;
			_apkInfo.versionCode = packageInfo.versionCode;
			_apkInfo.versionName = packageInfo.versionName;
			_apkInfo.publicSourceDir = packageInfo.applicationInfo.publicSourceDir;
			_apkInfo.publicKey = PublicKeyUtil.getPublicKeyByPath(_apkInfo.publicSourceDir);
			_apkInfo.packageMd5 = MD5.getFileMd5(_apkInfo.publicSourceDir);
			_result.add(_apkInfo);
		}
		return _result;
	}

}
