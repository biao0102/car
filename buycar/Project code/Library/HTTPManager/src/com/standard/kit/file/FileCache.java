package com.standard.kit.file;

import java.io.File;

import android.content.Context;
import android.text.TextUtils;

/**
 * ������¹��ܣ�
 * ��������Ŀ¼������ļ���URL��ɻ����ļ��ı����ļ����֧�ִ洢�����ֻ��ڲ��洢�����ȴ�����洢����
 * 
 * @version v1.0
 * @author neil lizhize
 * 
 */
public class FileCache {

	private File cacheDir;

	public FileCache(Context context) {
		// Find the dir to save cached images
		if (!TextUtils.isEmpty(FileUtil.getSavePath())) {
			cacheDir = new File(FileUtil.getSavePath(), "AppCache");
		} else {
			// 2.2����֧�ִ洢���ϵ�cache
			// context.getExternalCacheDir();
			cacheDir = context.getCacheDir();
		}
		if (!cacheDir.exists()) {
			cacheDir.mkdirs();
		}
	}

	public FileCache(Context context, String aSubCachePath) {
		String _subCachePath;
		if (null == aSubCachePath) {
			_subCachePath = "AppCache";
		} else {
			_subCachePath = aSubCachePath;
		}
		// Find the dir to save cached images
		if (!TextUtils.isEmpty(FileUtil.getSavePath())) {
			cacheDir = new File(FileUtil.getSavePath(), _subCachePath);
		} else {
			// 2.2����֧�ִ洢���ϵ�cache
			// context.getExternalCacheDir();
			cacheDir = context.getCacheDir();
		}
		if (!cacheDir.exists()) {
			cacheDir.mkdirs();
		}
	}

	public File getCacheDir() {
		return cacheDir;
	}

	public File getFileWithJpg(String url) {
		// I identify images by hashcode. Not a perfect solution, good for the
		// demo.
		if (!cacheDir.exists()) {
			cacheDir.mkdirs();
		}
		String filename = String.valueOf(url.hashCode());

		File f = new File(cacheDir, filename + ".jpg");
		return f;

	}

	/***
	 * ����ļ���URL��ɻ����ļ��ı����ļ����
	 * 
	 * @param url
	 *            �ļ���URL��ַ
	 * @return �����ļ����ļ����
	 */
	public File getFile(String url) {
		// I identify images by hashcode. Not a perfect solution, good for the
		// demo.
		if (!cacheDir.exists()) {
			cacheDir.mkdirs();
		}
		String filename = String.valueOf(url.hashCode());

		File f = new File(cacheDir, filename);
		return f;

	}

	public File getFileCache(String url) {
		// I identify images by hashcode. Not a perfect solution, good for the
		// demo.
		if (!cacheDir.exists()) {
			cacheDir.mkdirs();
		}
		String filename = String.valueOf(url.hashCode());
		File f = new File(cacheDir, filename);
		return f;

	}

	public File getFilePath(String aFileName) {
		if (!cacheDir.exists()) {
			cacheDir.mkdirs();
		}
		File f = new File(cacheDir, aFileName);
		return f;

	}

	/****
	 * ���SD���ϵĻ���
	 */
	public void clear() {
		File[] files = cacheDir.listFiles();
		for (File f : files)
			f.delete();
	}

	/**
	 * ����ֻ��ڲ��ϵĻ���
	 */
	public void clearInterCache() {
		if (TextUtils.isEmpty(FileUtil.getSavePath())) {
			return;
		} else {
			File[] files = cacheDir.listFiles();
			for (File f : files)
				f.delete();
		}
	}
}