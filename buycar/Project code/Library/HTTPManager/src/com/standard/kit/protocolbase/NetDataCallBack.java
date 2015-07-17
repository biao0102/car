package com.standard.kit.protocolbase;

/**
 * 
 * @author yaoyang
 *
 */
public interface NetDataCallBack {

	/**
	 * 成功
	 * @param aData
	 */
	public void onResult(ResponseData aData);

	/**
	 * 三种错误
	 * 
	 * 1,异常 exception 如网络超时
	 * 
	 * 2.http response code !=200
	 * 
	 * 3,协议解析错误。
	 * 
	 * @param aErrorInfo
	 */
	public void onError(ResponseErrorInfo aErrorInfo);

	/**
	 * 一种错误 http response code ==200但报文逻辑不通，服务器认为出错了。
	 * 
	 * @param aData
	 */
	public void onProtocolError(ResponseData aData);
}
