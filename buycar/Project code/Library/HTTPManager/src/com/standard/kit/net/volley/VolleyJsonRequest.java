package com.standard.kit.net.volley;

import java.util.Collections;
import java.util.Map;

import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;

public class VolleyJsonRequest extends JsonObjectRequest {

	private Map<String, String> mHeaders;

	public VolleyJsonRequest(int method, String url, JSONObject jsonRequest,
			Listener<JSONObject> jsonListener, ErrorListener errorListener) {
		super(method, url, jsonRequest, jsonListener, errorListener);
	}

	@Override
	protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
		return super.parseNetworkResponse(response);
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
