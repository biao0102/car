package com.standard.kit.net.volley;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.standard.kit.base64.Base64;
import com.standard.kit.net.http.IHttpCallback;

/**
 * 联网工具类, 异步方式
 * 
 * @author neil lizhize
 * 
 * @see com.standard.ket.HttpTransactionActivity
 * 
 * @see IHttpCallback
 */

public class VolleyConnection {

	private Context mContext;
	private String mUrl;
	private byte[] mRequestBytes;
	private IHttpCallback mHttpCallback;
	private int METHOD;

	/**
	 * 判断同步方式 HTTP POST或GET
	 * 
	 * @param aUrl
	 *            服务器地址
	 * @param aRequestBytes
	 *            POST到服务器的数据。
	 * @param aCallBack
	 *            回调接口。
	 * 
	 * */
	public VolleyConnection(Context aContext, String aUrl,
			byte[] aRequestBytes, IHttpCallback aCallBack) {
		mContext = aContext;
		mUrl = aUrl;
		mRequestBytes = aRequestBytes;
		mHttpCallback = aCallBack;
		if (mRequestBytes == null || mRequestBytes.length == 0) {
			METHOD = Request.Method.GET;
		} else {
			METHOD = Request.Method.POST;
		}
	}

	private Listener<NetworkResponse> getResponseListener() {
		return new Response.Listener<NetworkResponse>() {
			@Override
			public void onResponse(NetworkResponse response) {
				if (mHttpCallback != null) {
					mHttpCallback.onCompleted(response.statusCode,
							response.data);
				}
			}
		};
	}

	private ErrorListener getErrorListener() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				if (mHttpCallback != null) {
					mHttpCallback.onException(error);
				}
			}
		};
	}

	public void connect() {
		VolleyRequest request = new VolleyRequest(METHOD, mUrl,
				getResponseListener(), getErrorListener());
		Map<String, String> headers = new HashMap<String, String>();
		headers.put(
				"Authorization",
				"Basic "
						+ new String(Base64.encodeBase64("flsyjh:flSYjhx2012"
								.getBytes())));
		request.setHeaders(headers);
		request.setRequestBytes(mRequestBytes);
		VolleyConnectionManager.addRequest(request, mContext);
	}
}
