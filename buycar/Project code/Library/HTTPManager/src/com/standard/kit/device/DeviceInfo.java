package com.standard.kit.device;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.Locale;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;

/**
 * 完成以下功能：读到设备的IMEI IMSI 型号 出厂串号 CPU类型 WIFI的MAC 设备的语言 厂商
 * 
 * @version v1.0
 * 
 * @author neil lizhize
 * 
 */
public class DeviceInfo {

	static TelephonyManager mTelephonyMgr;
	public static String IMEI;
	public static String IMSI;

	// 得到手机的IMEI号，需要context参数
	public static String initIMEI(Context context) {
		if (mTelephonyMgr == null) {
			mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		}
		IMEI = mTelephonyMgr.getDeviceId();
		IMSI = mTelephonyMgr.getSubscriberId();
		return IMEI;
	}

	// 得到手机的IMSI号，需要context参数
	public static String initIMSI(Context context) {
		if (mTelephonyMgr == null) {
			mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		}
		IMEI = mTelephonyMgr.getDeviceId();
		IMSI = mTelephonyMgr.getSubscriberId();
		return IMSI;
	}

	public final static String LANGUAGE_TW = "30";
	public final static String Language_ZH = "31";
	public final static String Language_EN = "1";

	/**
	 * 系统语言 根据协议 需要上传阿拉伯数字
	 * 
	 * @return 1表示手机显示语言为非中文。31表示手机语言为中文。30为繁体中文。
	 */
	public static String getMobileLanguage() {
		String Language = Locale.getDefault().getLanguage();
		if (Language.equalsIgnoreCase("zh")) {// 中文
			return Language_ZH;
		} else if (Language.equalsIgnoreCase("en")) {// 英文
			return Language_EN;
		} else if (Language.equalsIgnoreCase("tw")) {
			Language = LANGUAGE_TW;
		}
		return Language_EN;
	}

	/**
	 * 系统语言是否是英文
	 * 
	 * @return true or false
	 */
	public static boolean isEnglish() {
		try {
			if (Locale.getDefault().getLanguage().equalsIgnoreCase("en"))
				return true;
		} catch (Exception e) {
		}
		return false;
	}

	/***
	 * 返回手机型号
	 * 
	 * @return 手机型号
	 * */
	public static String getPhoneMODEL() {
		return android.os.Build.MODEL;
	}

	/***
	 * 返回手机CPU型号。可用作兼容ARM,MIPS型号CPU的参数
	 * 
	 * @return CPU型号
	 * */
	public static String getCpuType() {

		return android.os.Build.CPU_ABI;
	}

	/*****
	 * 取设备厂商名称
	 * 
	 * @return 设备厂商名称
	 */
	public static String getMANUFACTURER() {
		return android.os.Build.MANUFACTURER;
	}

	public static String phoneVerionCode() {
		return "" + android.os.Build.VERSION.SDK_INT;
	}

	/***
	 * 返回厂商定的设备串号
	 * 
	 * Android系统2.3版本以上可以通过下面的方法得到Serial Number，且非手机设备也可以通过该接口获取。
	 * 
	 * String SerialNumber = android.os.Build.SERIAL;
	 * 
	 * @return 返回厂商定的设备串号 .出错的情况下返回null。
	 * */
	public static String getPhoneSerial() {
		String serial = null;
		try {
			Class<?> c = Class.forName("android.os.SystemProperties");
			Method get = c.getMethod("get", String.class);
			serial = (String) get.invoke(c, "ro.serialno");
		} catch (Exception ignored) {
			return null;
		}
		return serial;
	}

	/**
	 * 获取wifi的MAC 地址。
	 * 
	 * @return wifi的MAC 地址。如果没有WIFI设备，返回NULL
	 * */
	public static String getWifiMac(Context aContext) {
		WifiManager wifiMan = (WifiManager) aContext.getSystemService(Context.WIFI_SERVICE);
		if (null == wifiMan) {
			return null;
		}
		WifiInfo wifiInf = wifiMan.getConnectionInfo();
		if (null == wifiInf) {
			return null;
		}
		String macAddr = wifiInf.getMacAddress();
		return macAddr;
	}

	/**
	 * ANDROID_ID 在设备首次启动时，系统会随机生成一个64位的数字，并把这个数字以16进制字符串的形式保存下来，
	 * 这个16进制的字符串就是ANDROID_ID，当设备被wipe后该值会被重置。可以通过下面的方法获取：
	 * 
	 * @param context
	 * @return
	 */
	public static String getAndroidId(Context context) {
		String deviceId = Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID);
		if (deviceId != null) {
			return deviceId;
		} else {
			return "";
		}
	}

	public static String getCpuModel() {
		String _cpuType = getFileCpuInfo();
		if (null == _cpuType) {
			_cpuType = getCpuModelFromRun();
		}
		if (null != _cpuType) {
			String[] _tmpStr = _cpuType.split(":");
			if ((null != _tmpStr) && (2 >= _tmpStr.length)) {
				_cpuType = _tmpStr[1];
			}
		}
		return _cpuType;
	}

	private static String getCpuModelFromRun() {
		Runtime runtime = Runtime.getRuntime();
		Process process = null;
		try {
			process = runtime.exec("/system/bin/cat /proc/cpuinfo");
			process.waitFor();
		} catch (Exception e) {
			return null;
		}
		InputStream in = process.getInputStream();
		BufferedReader boy = new BufferedReader(new InputStreamReader(in));
		String mystring = null;
		try {
			mystring = boy.readLine();
			while (mystring != null) {
				if (mystring.toLowerCase().indexOf("hardware") >= 0) {
					return mystring;
				}
				mystring = boy.readLine();
			}
		} catch (IOException e) {
		}
		return null;
	}

	private static String getFileCpuInfo() {
		String _cmd_cpuinfo = "/proc/cpuinfo";
		String _cpuHardWare = null;
		try {
			FileReader fr = new FileReader(_cmd_cpuinfo);
			BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
			while ((_cpuHardWare = localBufferedReader.readLine()) != null) {
				if (_cpuHardWare.toLowerCase().indexOf("hardware") >= 0) {
					localBufferedReader.close();
					return _cpuHardWare;
				}
			}
			localBufferedReader.close();
		} catch (IOException e) {
		}
		return null;
	}

	public static boolean isEmulator() {
		Runtime runtime = Runtime.getRuntime();
		Process process = null;
		try {
			process = runtime.exec("/system/bin/cat /proc/cpuinfo");
			process.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}

		InputStream in = process.getInputStream();
		BufferedReader boy = new BufferedReader(new InputStreamReader(in));
		String mystring = null;
		try {
			mystring = boy.readLine();
			while (mystring != null) {
				mystring = mystring.trim().toLowerCase();
				if ((mystring.startsWith("hardware")) && mystring.endsWith("goldfish")) {
					return true;
				}
				mystring = boy.readLine();
			}
		} catch (IOException e) {
			return false;
		}
		return false;
	}
}
