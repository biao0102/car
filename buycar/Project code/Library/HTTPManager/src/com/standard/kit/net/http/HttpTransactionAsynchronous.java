package com.standard.kit.net.http;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import android.content.Context;

import com.standard.kit.base64.Base64;
import com.standard.kit.net.BasicAuthorUtil;
import com.standard.kit.net.NetUtil;

/**
 * 联网工具类, 异步方式
 * 
 * @author neil lizhize
 * 
 * @see com.standard.ket.HttpTransactionActivity
 * 
 * @see IHttpCallback
 */

public class HttpTransactionAsynchronous extends HttpTransaction {

	private IHttpCallback mHttpCallback;

	/**
	 * @param aUrl
	 *            服务器地址 异步方式 HTTP GET
	 */
	public HttpTransactionAsynchronous(Context aContext, String aUrl, IHttpCallback aCallBack) {
		super(aContext, aUrl);
		mHttpCallback = aCallBack;
	}

	/**
	 * 同步方式 HTTP POST
	 * 
	 * @param aUrl
	 *            服务器地址
	 * @param aRequestBytes
	 *            POST到服务器的数据。
	 * @param aCallBack
	 *            回调接口。
	 * 
	 * */
	public HttpTransactionAsynchronous(Context aContext, String aUrl, byte[] aRequestBytes, IHttpCallback aCallBack) {
		super(aContext, aUrl, aRequestBytes);
		mHttpCallback = aCallBack;
	}

	public void start() {
		new Thread() {
			public void run() {
				transaction();
			}
		}.start();

	}

	private void transaction() {
		// 不使用网关，适应运营商cmwap网关调整变化。
		// boolean _notUseProxy = false;
		Exception _exce = null;
		byte[] resultBytes = null;
		URL url;
		java.net.Proxy _proxyHere = null;

		int httpStatusCode = 0;
		int retryCnt = 0;
		int redirectCnt = 0;
		String headerContentLocation = null;
		byte data[] = new byte[BUFFER_SIZE];
		while ((redirectCnt < MAX_REDIRECT_CNT) && (retryCnt < MAX_RETRY_CNT)) {
			try {
				url = (null == headerContentLocation) ? new URL(mUrl) : new URL(headerContentLocation);
			} catch (MalformedURLException e) {
				_exce = e;
				break;
			}

			_proxyHere = NetUtil.getApnProxy(mContext);
			try {
				if (_proxyHere == null) {
					httpurlconnection = (HttpURLConnection) url.openConnection();
					httpurlconnection.setConnectTimeout(30000);
					httpurlconnection.setReadTimeout(30000);
				} else {
					httpurlconnection = (HttpURLConnection) url.openConnection(_proxyHere);
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

				Map<String, List<String>> _requestHeader = httpurlconnection.getRequestProperties();
				if (mHttpCallback instanceof IHttpDebugCallback) {
					IHttpDebugCallback _iDebug = (IHttpDebugCallback) mHttpCallback;
					_iDebug.onRequestHeader(_requestHeader);
				}

				httpurlconnection.setDoInput(true);

				if (null != aRequestData) {
					httpurlconnection.setUseCaches(false);
					httpurlconnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
					DataOutputStream dos = new DataOutputStream(httpurlconnection.getOutputStream());
					dos.write(aRequestData);
					dos.flush();
					dos.close();
				}

				httpurlconnection.connect();
				httpStatusCode = httpurlconnection.getResponseCode();
				
				Map<String, List<String>> _responseHeader = httpurlconnection.getHeaderFields();
				if (mHttpCallback instanceof IHttpDebugCallback) {
					IHttpDebugCallback _iDebug = (IHttpDebugCallback) mHttpCallback;
					_iDebug.onResponseHeader(_responseHeader);
				}
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
					if (httpStatusCode != 200) {
						mHttpCallback.onException(new Exception("server error"));
					} else {
						mHttpCallback.onCompleted(httpStatusCode, resultBytes);
					}

					// 收到数据正常结束。
					return;
				} else {
					httpurlconnection.disconnect();
					retryCnt++;
				}

			} catch (IOException e) {
				_exce = e;
				resultBytes = null;
				retryCnt++;
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
				}
			}
		}
		// 重试多次后，还是失败了。
		if (null != _exce) {
			mHttpCallback.onException(_exce);
		} else {
			mHttpCallback.onException(new Exception("unknown"));
		}
		return;
	}

}
