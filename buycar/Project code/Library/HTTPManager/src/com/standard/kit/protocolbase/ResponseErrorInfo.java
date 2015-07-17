package com.standard.kit.protocolbase;

public class ResponseErrorInfo {

	/**
	 * 错误码9000以上,表示网络错误+HTTP错误 ;
	 * 
	 * 9000物理引擎错误
	 * 
	 * 9000以下为协议内定的错误
	 */
	public int mCode = -1;

	/**
	 * 错误提示
	 */
	public String mErrorTips = null;

	/**
	 * 异常action用于判断具体某个协议出错
	 */
	public String mAction = null;

	/**
	 * 出错协议服务器地址
	 */
	public String mUrl = null;
}
