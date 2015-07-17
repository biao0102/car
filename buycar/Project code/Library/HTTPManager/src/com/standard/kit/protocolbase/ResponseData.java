package com.standard.kit.protocolbase;

import java.io.UnsupportedEncodingException;

import org.json.JSONException;
import org.json.JSONObject;

import com.standard.kit.file.FileUtil;
import com.standard.kit.thread.ThreadPoolUtil;

public abstract class ResponseData {

	public String command = "";

	public int code = -1;

	public String tips = "";

	protected boolean isHasCacheFile = false;

	protected String filePath, fileName;

	public abstract byte[] decode(byte[] aSecretData);

	public abstract void parse(byte[] aData);

	public byte[] originalData;

	/**
	 * 初始化JSON解析对象
	 * 
	 * @param datas
	 */
	protected JSONObject initJsonObject(byte[] datas) {
		try {
			String object = new String(datas, "UTF-8");

			return new JSONObject(object);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void storeCache(final byte[] aSecretData) {
		if (!isHasCacheFile) {
			if (filePath != null && fileName != null) {
				ThreadPoolUtil.getInstance().execute(new Runnable() {

					@Override
					public void run() {
						FileUtil.createNewFile(filePath, fileName, aSecretData);
					}
				});
			}
		}
	}

	public boolean loadCache(String filePath, String fileName) {

		this.fileName = fileName;
		this.filePath = filePath;

		if (filePath == null || fileName == null) {
			return false;
		}

		byte[] datas = null;

		if (FileUtil.isExistSdCard()) {
			FileUtil.createFolder(filePath);
			if (FileUtil.isEixstsFile(filePath + fileName)) {
				isHasCacheFile = true;
				datas = decode(FileUtil.getFileContent(filePath + fileName));
			} else {
				return false;
			}
		} else {
			return false;
		}

		if (datas == null || datas.length == 0) {
			isHasCacheFile = false;
			return false;
		}

		try {
			parse(datas);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
