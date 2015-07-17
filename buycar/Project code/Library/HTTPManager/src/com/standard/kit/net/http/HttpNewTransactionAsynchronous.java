package com.standard.kit.net.http;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.standard.kit.base64.Base64;


import android.content.Context;


/**
 * 联网工具类, 异步方式
 * 
 * @author jet
 * 
 * @see com.standard.ket.HttpTransactionActivity
 * 
 * @see IHttpCallback
 */

public class HttpNewTransactionAsynchronous extends HttpTransaction {

	private IHttpCallback mHttpCallback;
	
	public Map<String, String> params;
	public Map<String, File> files;
	/**
	 * @param aUrl
	 *            服务器地址 异步方式 HTTP GET
	 */
	public HttpNewTransactionAsynchronous(Context aContext, String aUrl, IHttpCallback aCallBack) {
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
	public HttpNewTransactionAsynchronous(Context aContext, String aUrl, byte[] aRequestBytes, IHttpCallback aCallBack) {
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

		int httpStatusCode = 0;
		int retryCnt = 0;
		int redirectCnt = 0;
		String headerContentLocation = null;
		if (params == null) {
			return;
		}
		while ((redirectCnt < MAX_REDIRECT_CNT) && (retryCnt < MAX_RETRY_CNT)) {
			try {
				 
			    String BOUNDARY = "jet_" + java.util.UUID.randomUUID().toString()  + "_jet";  
			    String PREFIX = "--" , LINEND = "\r\n";
			    String MULTIPART_FROM_DATA = "multipart/form-data"; 
			    String CHARSET = "UTF-8";

			    URL uri = new URL(mUrl); 
			    httpurlconnection = (HttpURLConnection) uri.openConnection(); 
			    httpurlconnection.setReadTimeout(5 * 1000); // 缓存的最长时间 
			    httpurlconnection.setDoInput(true);// 允许输入 
			    httpurlconnection.setDoOutput(true);// 允许输出 
			    httpurlconnection.setUseCaches(false); // 不允许使用缓存 
			    httpurlconnection.setRequestMethod("POST"); 
			    httpurlconnection.setRequestProperty("connection", "keep-alive");
			    httpurlconnection.setRequestProperty("Charsert", "UTF-8"); 
			    httpurlconnection.setRequestProperty("Content-Type", MULTIPART_FROM_DATA + ";boundary=" + BOUNDARY); 
			    httpurlconnection.addRequestProperty("Authorization",
						"Basic " + new String(Base64.encodeBase64("flsyjh:flSYjhx2012".getBytes())));
			    // 首先组拼文本类型的参数 
			    StringBuilder sb = new StringBuilder(); 
			    for (Map.Entry<String, String> entry : params.entrySet()) { 
			    	sb.append(PREFIX); 
			        sb.append(BOUNDARY); 
			        sb.append(LINEND); 
			        sb.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINEND);
			        sb.append("Content-Type: text/plain; charset=" + CHARSET+LINEND);
			        sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
			        sb.append(LINEND);
			        sb.append(entry.getValue()); 
			        sb.append(LINEND); 
			    } 
			    
			    DataOutputStream outStream = new DataOutputStream(httpurlconnection.getOutputStream()); 
			    outStream.write(sb.toString().getBytes()); 
			    // 发送文件数据 
			    if(files!=null){
			    	for (Map.Entry<String, File> file: files.entrySet()) {
			    		StringBuilder sb1 = new StringBuilder(); 
			    		sb1.append(PREFIX); 
			            sb1.append(BOUNDARY); 
			            sb1.append(LINEND); 
			            sb1.append("Content-Disposition: form-data; name=\""+file.getKey()+"\"; filename=\""+file.getValue().getName()+"\""+LINEND);
			            sb1.append("Content-Type: image/jpg"+LINEND);
			            sb1.append(LINEND);
			            
			            outStream.write(sb1.toString().getBytes()); 

			            InputStream is = new FileInputStream(file.getValue());
			            byte[] buffer = new byte[1024]; 
			            int len = 0; 
			            while ((len = is.read(buffer)) != -1) { 
			            	outStream.write(buffer, 0, len); 
			            }
			            
			            is.close(); 
			            outStream.write(LINEND.getBytes()); 
			    	} 
			    }
			  
			    //请求结束标志
			    byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes(); 
			    outStream.write(end_data); 
			    outStream.flush(); 

			    //得到响应码 
			    httpStatusCode = httpurlconnection.getResponseCode();
				
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
					
					InputStream in = httpurlconnection.getInputStream(); 
					try {
						resultBytes = readInputStream(in);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
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
	
	private static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		inStream.close();
		return outStream.toByteArray();
	}

}
