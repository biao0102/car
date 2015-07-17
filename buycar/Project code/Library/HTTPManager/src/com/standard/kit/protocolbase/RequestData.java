package com.standard.kit.protocolbase;

public abstract class RequestData {

	/** 响应服务器的地址。 */
	protected String mUrl;

	/** 协议的类型，目的。如登录，退出，请求内容 */
	protected String mAction;

	/**
	 * 服务器的URL 各位同事设计出来的方案不一样。有的喜欢URL里带参数；有的喜欢POST协议；有的喜欢前两种方式组合。所以这里就复杂点。
	 * URL在某些情况下是根据mAction等参数拼出来的。mUrl不直接爆露出来。通过getServerUrl()传出去。
	 * 
	 * @return 服务器地址
	 */
	public abstract String getServerUrl();

	/**
	 * 加密报文
	 * 
	 * @return
	 */
	public abstract byte[] encode();

	/**
	 * @return 返回协议的文本 ,用于调式或者上传。
	 */
	public abstract byte[] getDataBytes();

	public void setmUrl(String mUrl) {
		this.mUrl = mUrl;
	}

}
