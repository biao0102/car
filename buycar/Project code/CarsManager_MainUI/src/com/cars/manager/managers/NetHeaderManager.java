package com.cars.manager.managers;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import com.cars.manager.utils.Base64;

/**
 * 提供网络请求头部信息
 * */
public class NetHeaderManager {

	public static Header[] getRequestHeaders() {
		List<Header> headers = new ArrayList<Header>();

		final String aHeaderBasic = "flsyjh:flSYjhx2012";

		headers.add(new BasicHeader("Authorization", "Basic " + new String(Base64.encodeBase64(aHeaderBasic.getBytes()))));

		return headers.toArray(new Header[headers.size()]);
	}

}
