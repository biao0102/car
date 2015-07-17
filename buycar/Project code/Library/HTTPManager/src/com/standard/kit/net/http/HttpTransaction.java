package com.standard.kit.net.http;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.util.Log;

import com.standard.kit.base64.Base64;
import com.standard.kit.net.BasicAuthorUtil;
import com.standard.kit.net.NetUtil;

/**
 * 联网工具类, 同步方式
 * 
 * @author neil lizhize
 * 
 * @see com.standard.ket.AndroidSdkActivity
 */
public class HttpTransaction {
	protected Context mContext;
	protected String mUrl;
	protected byte[] aRequestData;
	protected HttpURLConnection httpurlconnection;

	protected final int MAX_RETRY_CNT = 3;
	protected final int MAX_REDIRECT_CNT = 3;

	/**
	 * @param aUrl
	 *            服务器地址 同步方式 HTTP GET
	 * */
	public HttpTransaction(Context aContext, String aUrl) {
		mContext = aContext;
		mUrl = aUrl;
	}

	/**
	 * @param aUrl
	 *            服务器地址 同步方式 HTTP POST
	 * */
	public HttpTransaction(Context aContext, String aUrl, byte[] aRequestBytes) {
		mContext = aContext;
		mUrl = aUrl;
		aRequestData = aRequestBytes;
	}

	public static final int BUFFER_SIZE = 1024;

	public byte[] getData() {

		// 不使用网关，适应运营商cmwap网关调整变化。
		// boolean _notUseProxy = false;
		byte[] resultBytes = null;
		URL url;
		java.net.Proxy proxyHere = null;
		int httpStatusCode;
		int retryCnt = 0;
		int redirectCnt = 0;
		String headerContentLocation = null;
		byte data[] = new byte[BUFFER_SIZE];
		while ((redirectCnt < MAX_REDIRECT_CNT) && (retryCnt < MAX_RETRY_CNT)) {
			try {
				url = (null == headerContentLocation) ? new URL(mUrl) : new URL(headerContentLocation);
			} catch (MalformedURLException e) {
				break;
			}

			proxyHere = NetUtil.getApnProxy(mContext);

			try {
				if (proxyHere == null) {
					httpurlconnection = (HttpURLConnection) url.openConnection();
					httpurlconnection.setConnectTimeout(30000);
					httpurlconnection.setReadTimeout(30000);
				} else {
					httpurlconnection = (HttpURLConnection) url.openConnection(proxyHere);
					httpurlconnection.setConnectTimeout(30000);
					httpurlconnection.setReadTimeout(30000);
				}

				// basic认证账户密码
				if (BasicAuthorUtil.isBasicAuthor(mUrl)) {
					httpurlconnection.addRequestProperty("Authorization",
							"Basic " + new String(Base64.encodeBase64("flsyjh:flSYjhx2012".getBytes())));
				}

				if (null != aRequestData) {
					httpurlconnection.setRequestMethod("POST");
					httpurlconnection.setRequestProperty("Content-Length", "" + Integer.toString(aRequestData.length));
					httpurlconnection.setDoOutput(true);
				} else {
					httpurlconnection.setRequestMethod("GET");
				}

				httpurlconnection.setDoInput(true);

				if (null != aRequestData) {
					httpurlconnection.setUseCaches(false);
					httpurlconnection.setRequestProperty("Content-Type", "application/octet-stream");
					DataOutputStream dos = new DataOutputStream(httpurlconnection.getOutputStream());
					dos.write(aRequestData);
					dos.flush();
					dos.close();

				}

				httpurlconnection.connect();
				httpStatusCode = httpurlconnection.getResponseCode();
				if (httpStatusCode == 503 && (retryCnt < this.MAX_RETRY_CNT)) {
					retryCnt++;
					httpurlconnection.disconnect();
					continue;
				}
				if (httpStatusCode == 301 || httpStatusCode == 302 || httpStatusCode == 303 || httpStatusCode == 307) {
					headerContentLocation = httpurlconnection.getHeaderField("Location");
					redirectCnt++;
					httpurlconnection.disconnect();
					continue;
				}
				if (httpStatusCode == 200) {
					// cmcc jump page
					String cType = httpurlconnection.getContentType();
					if (null != cType) {
						if (cType.contains("wml") && (httpStatusCode == 200)) {
							if (redirectCnt <= MAX_REDIRECT_CNT) {
								redirectCnt++;
								httpurlconnection.disconnect();
								continue;
							}
						}
					}
					int bytesRead = 0;
					InputStream indataStream = httpurlconnection.getInputStream();
					ByteArrayOutputStream baos = new ByteArrayOutputStream();

					bytesRead = indataStream.read(data);
					while (-1 != bytesRead) {
						baos.write(data, 0, bytesRead);
						bytesRead = indataStream.read(data);
						redirectCnt = 0;
						retryCnt = 0;
					}

					resultBytes = baos.toByteArray();
					baos.close();
					baos = null;
					httpurlconnection.disconnect();

					return resultBytes;
				} else if ((-1) == httpStatusCode) {
					resultBytes = null;
					retryCnt++;
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e1) {
					}
				} else {
					int bytesRead = 0;
					InputStream indataStream = httpurlconnection.getInputStream();
					ByteArrayOutputStream baos = new ByteArrayOutputStream();

					bytesRead = indataStream.read(data);
					while (-1 != bytesRead) {
						baos.write(data, 0, bytesRead);
						bytesRead = indataStream.read(data);
						redirectCnt = 0;
						retryCnt = 0;
					}

					resultBytes = baos.toByteArray();
					baos.close();
					baos = null;
					httpurlconnection.disconnect();
					retryCnt++;
				}

			} catch (IOException e) {
				resultBytes = null;
				retryCnt++;
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
				}
			}
		}

		return resultBytes;

	}

}
