package com.standard.kit.protocolbase;

import java.io.UnsupportedEncodingException;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.standard.kit.file.FileUtil;
import com.standard.kit.format.DateTimeUtil;
import com.standard.kit.net.http.HttpTransactionAsynchronous;
import com.standard.kit.net.http.IHttpCallback;
import com.standard.kit.net.volley.VolleyConnection;
import com.standard.kit.thread.ThreadPoolUtil;

public class NetDataEngine implements IHttpCallback {

	private RequestData mRequest;
	private ResponseData mResponse;

	private NetDataCallBack mCallBack;
	private Context mContext;

	/** 记录请求和回复开关 */
	public static boolean isRecordLog = true;

	/** 日志开关控制 */
	private boolean isShowLog = true;

	public NetDataEngine(Context aContext) {
		this(null, aContext);
	}

	public NetDataEngine(NetDataCallBack aCallBacke, Context aContext) {
		mCallBack = aCallBacke;
		mContext = aContext;
	}

	/**
	 * 是否连网，Add by liaoyongxiong 20140401
	 * 
	 * @return
	 */
	private boolean isNetworkConnected() {
		ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
		if (mNetworkInfo != null) {
			return mNetworkInfo.isAvailable();
		}
		return false;
	}

	/**
	 * change by liaoyongxiong 增加是否连网判断
	 */
	public void connection() {
		String _url = mRequest.getServerUrl();
		byte[] _requestBody = mRequest.encode();
		this.connection(_url, _requestBody);

	}

	public void connection(final String pURL, byte[] pRequestBody) {
		HttpTransactionAsynchronous _http;
		if (isShowLog) {
			this.requestLog();
		}
		String _url = mRequest.getServerUrl();
		byte[] _requestBody = mRequest.encode();
		if (_requestBody.length == 0) {
			_requestBody = null;
		}
		if (null == _requestBody) {
			_http = new HttpTransactionAsynchronous(mContext, _url, this);
		} else {
			_http = new HttpTransactionAsynchronous(mContext, _url, _requestBody, this);
		}
		_http.start();
	}

	/**
	 * 请求报文日志
	 */
	private void requestLog() {
		ThreadPoolUtil.getInstance().execute(new Runnable() {
			@Override
			public void run() {
				try {
					if (mRequest.getDataBytes() == null) {
						return;
					}
					String requestString = new String(mRequest.getDataBytes(), "UTF-8");
					Log.v("request", requestString);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				if (mRequest.mAction == null) {
					return;
				}
				String fileName = mRequest.mAction.replaceAll("/", "_");
				FileUtil.createNewFile(FileUtil.LOG_FILE_PATH, fileName + "_request_" + DateTimeUtil.getCurrentTime(DateTimeUtil.PATTERN_BIRTHDAY),
						mRequest.getDataBytes());
			}
		});
	}

	@Override
	public void onException(Exception aErrorExceptoine) {
		if (isShowLog) {
			passToUiError(9000, aErrorExceptoine.toString());
		} else {
			passToUiError(9000, HttpErrorTips.HTTP_ERROR_CONN_DATA[HttpErrorTips.LANGUAGEINDEX]);
		}
	}

	@Override
	public void onCompleted(int aRespondCode, byte[] aData) {

		if (200 != aRespondCode) {
			String mErrorTips;
			try {
				mErrorTips = new String(aData, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				mErrorTips = e.toString();
			}
			if (aRespondCode >= 500) {
				if (isShowLog) {
					passToUiError(9000 + aRespondCode, mErrorTips);
				} else {
					passToUiError(9000 + aRespondCode, HttpErrorTips.HTTP_ERROR_SERVER[HttpErrorTips.LANGUAGEINDEX]);
				}
			} else if (aRespondCode >= 400) {
				if (isShowLog) {
					passToUiError(9000 + aRespondCode, mErrorTips);
				} else {
					passToUiError(9000 + aRespondCode, HttpErrorTips.HTTP_ERROR_HTTP[HttpErrorTips.LANGUAGEINDEX]);
				}
			}
			return;
		}

		if (null == mResponse) {
			if (mCallBack != null) {
				mResponse = new ResponseData() {

					@Override
					public void parse(byte[] aData) {
						// TODO Auto-generated method stub

					}

					@Override
					public byte[] decode(byte[] aSecretData) {
						// TODO Auto-generated method stub
						return null;
					}
				};
				mResponse.originalData = aData;
				mCallBack.onResult(mResponse);
			}
			return;
		}

		// byte[] pdata = null;
		byte[] pdata = aData;
		// try {
		// pdata = mResponse.decode(aData);
		// } catch (Exception e1) {
		// pdata = null;
		// }

		if (null == pdata) {
			if (isShowLog) {
				passToUiError(8000 + aRespondCode, "decode protocl error");
			} else {
				passToUiError(8000 + aRespondCode, HttpErrorTips.HTTP_ERROR_FILE[HttpErrorTips.LANGUAGEINDEX]);
			}
			return;
		}

		mResponse.parse(pdata);

		if (isShowLog) {
			this.saveProtocol(pdata);
		}

		if (mResponse.code == 1) {
			if (mCallBack != null)
				mCallBack.onResult(mResponse);
		} else {
			if (mCallBack != null)
				mCallBack.onProtocolError(mResponse);
			// passToUiError(mResponse.code, mResponse.tips);
		}
	}

	/**
	 * 响应报文日志
	 * 
	 * @param pdata
	 */
	private void saveProtocol(final byte[] pdata) {
		ThreadPoolUtil.getInstance().execute(new Runnable() {
			@Override
			public void run() {
				try {
					Log.v("response", new String(pdata, "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				if (mRequest.mAction == null) {
					return;
				}
				String fileName = mRequest.mAction.replaceAll("/", "_");
				FileUtil.createNewFile(FileUtil.LOG_FILE_PATH, fileName + "_" + DateTimeUtil.getCurrentTime(DateTimeUtil.PATTERN_BIRTHDAY), pdata);
			}
		});
	}

	/**
	 * 错误码9000以上,表示网络错误+HTTP错误 ;9000以下为协议内定的错误
	 * 
	 * @param aErrorCode
	 * @param aTips
	 */
	private void passToUiError(int aErrorCode, String aTips) {
		ResponseErrorInfo _errorInfo = new ResponseErrorInfo();
		_errorInfo.mCode = aErrorCode;
		_errorInfo.mErrorTips = aTips;
		if (mRequest != null) {
			_errorInfo.mAction = this.mRequest.mAction;
			_errorInfo.mUrl = this.mRequest.getServerUrl();
		}
		mCallBack.onError(_errorInfo);
	}

	public RequestData getmRequest() {
		return mRequest;
	}

	public void setmRequest(RequestData mRequest) {
		this.mRequest = mRequest;
	}

	public ResponseData getmResponse() {
		return mResponse;
	}

	public void setmResponse(ResponseData mResponse) {
		this.mResponse = mResponse;
	}
}
