package com.standard.kit.net;

import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpHost;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * 完成以下功能：读到设备 WIFI的MAC 判断当前网络的类型，状态。取当前接入点，网关
 * 
 * @version v1.0
 * 
 * @author neil lizhize
 */
public class NetUtil
{

	/**
	 * 获取wifi的MAC 地址。
	 * 
	 * @return wifi的MAC地址。
	 * */
	public static String getWifiMac(Context aContext)
	{
		WifiManager wifiMan = (WifiManager) aContext.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInf = wifiMan.getConnectionInfo();
		String macAddr = wifiInf.getMacAddress();
		return macAddr;
	}

	/***
	 * 当前联接是不是WIFI
	 * 
	 * @param aContext
	 * @return true表示为wifi。false为非WIFI
	 */
	public static boolean isWifi(Context aContext)
	{
		ConnectivityManager cm = (ConnectivityManager) aContext.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo network_info = cm.getActiveNetworkInfo();
		if (network_info != null && network_info.isConnected())
		{
			if (network_info.getType() == ConnectivityManager.TYPE_WIFI) return true;
			else return false;
		}
		return false;
	}

	/**
	 * 获取联网方式的名称
	 * 
	 * @param context
	 * @return 如果返回值为空，表示当前无网络。
	 */
	public static String getAccessPointTypeName(Context context)
	{
		String apn = null;
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();

		if (info != null)
		{
			apn = info.getTypeName();
			switch (info.getType())
			{
			case ConnectivityManager.TYPE_WIFI:
				if (apn == null)
				{
					apn = "wifi";
				}
				break;
			case ConnectivityManager.TYPE_MOBILE:
				if (info.getExtraInfo() != null)
				{
					apn = info.getExtraInfo().toLowerCase();
				}
				if (apn == null)
				{
					apn = "mobile";
				}
				break;
			default:
				if (info.getExtraInfo() != null)
				{
					apn = info.getExtraInfo().toLowerCase();
				}
				break;
			}
		}
		return apn;
	}

	/***
	 * 得到当前使用的接入点的代理地址
	 * 
	 * @param context
	 * @return 代理服务器地址
	 */
	public static Proxy getApnProxy(Context context)
	{
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		// 如果有wifi连接，则选择wifi，不返回代理 ;这是因为接入点设置仅在GPRS下有效，wifi下无效。
		if (ni.isConnected())
		{
			return null;
		}
		else
		{
			String proxy = android.net.Proxy.getDefaultHost();
			int port = android.net.Proxy.getDefaultPort();
			if (proxy != null && !proxy.equals("")) // 代理地址不为空
			{
				if (validateIp(proxy))
				{
					// android 2.3.x 会把IP当域名做解析。导致联网失败。所以这样外理一下。
					return new Proxy(Proxy.Type.HTTP, InetSocketAddress.createUnresolved(proxy, Integer.valueOf(port)));
				}
				else
				{
					return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxy, Integer.valueOf(port)));
				}
			}

		}
		return null;
	}

	/**
	 * Validate ip address with regular expression 验证IP格式是否正确
	 * 
	 * @param aIp
	 *            ip address for validation
	 * @return true valid ip address, false invalid ip address
	 */
	public static boolean validateIp(final String aIp)
	{
		String IPADDRESS_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
				+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
		Pattern pattern;
		Matcher matcher;
		pattern = Pattern.compile(IPADDRESS_PATTERN);
		matcher = pattern.matcher(aIp);
		return matcher.matches();
	}

	/****
	 * 取得一个HTTP连接
	 * 
	 * @param url
	 *            需要联接的服务器地址
	 * @param context
	 * @return 得到一个HttpURLConnection 对象
	 */
	public static HttpURLConnection getConnection(String url, Context context)
	{
		try
		{
			URL url_ = new URL(url);
			HttpURLConnection connection = null;
			Proxy proxy = null;
			if ((proxy = getApnProxy(context)) != null)
			{
				connection = (HttpURLConnection) url_.openConnection(proxy);
			}
			else
			{
				connection = (HttpURLConnection) url_.openConnection();
			}
			return connection;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 取得代理设置
	 * 
	 * @param context
	 * @return 返回网关的地址。HttpHost类型
	 */
	public static HttpHost getProxyToHttpClient(Context context)
	{
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		// 如果有wifi连接，则选择wifi，不返回代理 ;这是因为接入点设置仅在GPRS下有效，wifi下无效。
		if (ni.isConnected())
		{
			return null;
		}
		else
		{
			String proxy = android.net.Proxy.getDefaultHost();
			int port = android.net.Proxy.getDefaultPort();
			if (proxy != null && !proxy.equals("")) // 代理地址不为空
			{
				if (proxy != null && !proxy.equals(""))
				{
					return new HttpHost(proxy, port);
				}
				else
				{
					return null;
				}
			}
		}
		return null;
	}

	/**
	 * check current avilable apn is wap
	 * 
	 * @param conn
	 * @return boolean
	 */
	public static boolean isWap(HttpURLConnection conn)
	{
		boolean isWap = false;
		String contentType = conn.getContentType();
		isWap = contentType != null && (contentType.contains("wap") || contentType.contains("wml"));
		return isWap;
	}

}
