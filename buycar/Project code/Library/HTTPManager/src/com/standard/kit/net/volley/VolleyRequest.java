package com.standard.kit.net.volley;

import java.util.Collections;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

public class VolleyRequest extends Request<NetworkResponse> {

	private Listener<NetworkResponse> mListener;
	private byte[] mRequestBytes;
	private Map<String, String> mHeaders;
	public Map<String, String> mParams;

	/**
	 * 初始化请求
	 * 
	 * @param method
	 *            请求类型
	 * @param url
	 *            请求地址
	 * @param listener
	 *            成功回调
	 * @param errorListener
	 *            失败回调
	 * 
	 * */
	public VolleyRequest(int method, String url,
			Listener<NetworkResponse> listener,
			Response.ErrorListener errorListener) {
		super(method, url, errorListener);
		mListener = listener;

	}

	@Override
	protected void deliverResponse(NetworkResponse response) {
		mListener.onResponse(response);
	}

	@Override
	protected Response<NetworkResponse> parseNetworkResponse(
			NetworkResponse response) {
		return Response.success(response,
				HttpHeaderParser.parseCacheHeaders(response));
	}

	public void setRequestBytes(byte[] pRequestBytes) {
		this.mRequestBytes = pRequestBytes;
	}

	public void setParams(Map<String, String> pParams) {
		this.mParams = pParams;
	}

	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		return this.mParams;
	}

	@Override
	public byte[] getBody() throws AuthFailureError {
		if (getParams() != null && getParams().size() > 0) {
			return super.getBody();
		} else {
			return mRequestBytes;
		}
	}

	public void setHeaders(Map<String, String> pHeaders) {
		this.mHeaders = pHeaders;
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		if (mHeaders != null && mHeaders.size() > 0)
			return mHeaders;
		return Collections.emptyMap();
	}

}
