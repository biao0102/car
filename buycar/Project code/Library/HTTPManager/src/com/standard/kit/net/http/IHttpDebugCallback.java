package com.standard.kit.net.http;

import java.util.List;
import java.util.Map;

public interface IHttpDebugCallback extends IHttpCallback {
	/***
	 * 发给服务器的HTTP 头
	 * 
	 * @param aHeaderMap
	 */
	public void onRequestHeader(Map<String, List<String>> aHeaderMap);

	/***
	 * * 收到服务器了响应的HTTP头。
	 * 
	 * @param aHeaderMap
	 */
	public void onResponseHeader(Map<String, List<String>> aHeaderMap);
	// 考虑续传，设计的接口。
	// public void onBody(byte[] aDate);
}
