package com.standard.kit.net.http;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.standard.kit.file.FileCache;
import com.standard.kit.file.FileUtil;

public class HttpImageCache {

	public HttpImageCache() {
		// HttpImageCache.getImg(aUrl, aContext)
	}

	/***
	 * 
	 * @param aUrl
	 *            �������ͼƬURL��ַ
	 * @param aSubCachePath
	 *            ��Ż���ͼƬ����Ŀ¼����Ŀ¼λ��TF���ϡ����û��TF���ͻ�浽�ֻ��ڲ��洢�ϡ�
	 * @param aContext
	 * @return
	 */
	public static Bitmap getImg(final String aUrl, final String aSubCachePath, Context aContext) {
		Context _Context = aContext;
		if ((null == aUrl) || (aUrl.length() < 1)) {
			return null;
		}
		try {
			new URL(aUrl);
		} catch (MalformedURLException e) {
			return null;
		}
		byte[] _imgBytes = null;
		if ((_Context == null) || (aSubCachePath == null)) {
			HttpTransaction _http = new HttpTransaction(_Context, aUrl);
			_imgBytes = _http.getData();
			Bitmap aBit = BitmapFactory.decodeByteArray(_imgBytes, 0, _imgBytes.length);
			return aBit;
		}
		FileCache tFileCache = new FileCache(_Context, aSubCachePath);
		File tFile = tFileCache.getFileWithJpg(aUrl);
		String _cachePath = tFile.getAbsolutePath();
		if (tFile.exists()) {
			_imgBytes = FileUtil.getFileContent(_cachePath);
		} else {
			HttpTransaction _http = new HttpTransaction(_Context, aUrl);
			_imgBytes = _http.getData();
			if (null != _imgBytes) {
				FileUtil.createNewFile(_cachePath, _imgBytes);
			}
		}
		if (null == _imgBytes) {
			FileUtil.delFile(_cachePath);
			return null;
		}
		Bitmap aBit = BitmapFactory.decodeByteArray(_imgBytes, 0, _imgBytes.length);
		if (null == aBit) {
			FileUtil.delFile(_cachePath);
			HttpTransaction _http = new HttpTransaction(_Context, aUrl);
			_imgBytes = _http.getData();
			aBit = BitmapFactory.decodeByteArray(_imgBytes, 0, _imgBytes.length);
			if ((null != _imgBytes) && (FileUtil.isExistSdCard())) {
				FileUtil.createNewFile(_cachePath, _imgBytes);
			}
		}
		return aBit;
	}

	public static Bitmap getImg(final String aUrl, Context aContext) {
		Context _Context = aContext;
		if ((null == aUrl) || (aUrl.length() < 1)) {
			return null;
		}
		try {
			new URL(aUrl);
		} catch (MalformedURLException e) {
			return null;
		}
		byte[] _imgBytes = null;
		if (_Context == null) {
			HttpTransaction _http = new HttpTransaction(_Context, aUrl);
			_imgBytes = _http.getData();
			Bitmap aBit = BitmapFactory.decodeByteArray(_imgBytes, 0, _imgBytes.length);
			return aBit;
		}
		FileCache tFileCache = new FileCache(_Context);
		File tFile = tFileCache.getFile(aUrl);
		String _cachePath = tFile.getAbsolutePath();
		
		if (tFile.exists()) {
			_imgBytes = FileUtil.getFileContent(_cachePath);
		} else {
			HttpTransaction _http = new HttpTransaction(_Context, aUrl);
			_imgBytes = _http.getData();
			if (null != _imgBytes) {
				FileUtil.createNewFile(_cachePath, _imgBytes);
			}
		}
		if (null == _imgBytes) {
			FileUtil.delFile(_cachePath);
			return null;
		}
		Bitmap aBit = BitmapFactory.decodeByteArray(_imgBytes, 0, _imgBytes.length);
		if (null == aBit) {
			FileUtil.delFile(_cachePath);
			HttpTransaction _http = new HttpTransaction(_Context, aUrl);
			_imgBytes = _http.getData();
			aBit = BitmapFactory.decodeByteArray(_imgBytes, 0, _imgBytes.length);
			if ((null != _imgBytes) && (FileUtil.isExistSdCard())) {
				FileUtil.createNewFile(_cachePath, _imgBytes);
			}
		}
		return aBit;
	}

}
