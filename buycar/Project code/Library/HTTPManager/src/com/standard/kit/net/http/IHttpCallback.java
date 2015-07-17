package com.standard.kit.net.http;

/****
 * 联网异步回调接口
 * 
 * @author ljy
 * 
 */
public interface IHttpCallback {
	/****
	 * 
	 * @param aErrorExceptoine
	 *            各种异常。
	 */
	public void onException(Exception aErrorExceptoine);

	/****
	 * 网络正常，服务器传回了数据，回调查些接口。
	 * 
	 * @param aRespondCode
	 *            服务器响应的HTTP头，正常是200 。 500以上是服务器错误。400-500是客户端请求错误，参阅HTTP协议。
	 * @param aData
	 *            服务器传回的数据
	 */
	public void onCompleted(int aRespondCode, byte[] aData);

}
