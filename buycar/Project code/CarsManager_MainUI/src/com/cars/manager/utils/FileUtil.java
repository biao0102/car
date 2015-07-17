package com.cars.manager.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.text.TextUtils;
import android.text.format.Formatter;

/**
 * 文件修改，追加，拷贝，各种重命名。SD卡是否存在。验证文件名是否合法。
 * 
 * @version v1.0
 * 
 * @author neil lizhize
 * 
 */
public class FileUtil {

	public static final String SDCARD_PATH = FileUtil.getSavePath();

	/** 测试的请求和回复日志路径 **/
	public static final String LOG_RECORD_PATH = SDCARD_PATH + "/data/record/";

	/** 日志文件路径 **/
	public static final String LOG_FILE_PATH = SDCARD_PATH + "/data/log/";

	public static boolean savePhotoToSDCard(byte[] bytes, String fileDir, String imgUrl) {
		File f = null;
		FileOutputStream fout = null;
		f = FileUtil.createNewFile(fileDir, MD5.toMd5(imgUrl.getBytes()));
		try {
			fout = new FileOutputStream(f, false);
			fout.write(bytes);
			fout.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			fout = null;
			f = null;
		}
		return false;
	}

	public static boolean deleteSDcardFile(String fileDir, String imgUrl) {
		File f = new File(fileDir + MD5.toMd5(imgUrl.getBytes()));
		if (f.exists()) {
			f.delete();
			return true;
		}
		return false;
	}

	/**
	 * 重命名文件，不改变文件后缀。
	 * 
	 * @param aFromFilePath
	 *            源文件。
	 * @param aToFileName
	 *            目标名称。
	 * @return
	 */
	public static String reNameFilePrefix(String aFromFilePath, String aToFileName) {
		File f = new File(aFromFilePath);
		if (!f.exists()) {
			return null;
		}
		int _pathLength = aFromFilePath.indexOf(f.getParent());
		String _fileName = aFromFilePath.substring(_pathLength);
		String _ext = null;
		if (_fileName != null) {
			int _dot = _fileName.lastIndexOf(".");
			_ext = _fileName.substring(_dot);
		}
		String _newPath = f.getParent() + File.separatorChar + aToFileName + _ext;
		File _toFile = new File(_newPath);
		if (_toFile.exists()) {
			return null;
		} else if (f.renameTo(_toFile)) {
			return _toFile.getAbsolutePath();
		}
		return null;
	}

	/****
	 * 重命名文件
	 * 
	 * @param aFromFilePath
	 *            文件源
	 * @param aToFilePath
	 *            目标文件
	 * @return TRUE 成功
	 */
	public static boolean reNameFile(String aFromFilePath, String aToFilePath) {
		File _formFile = new File(aFromFilePath);
		if (!_formFile.exists()) {
			return false;
		}
		File _toFile = new File(aToFilePath);
		if (_toFile.exists()) {
			return false;
		} else {
			return _formFile.renameTo(new File(aToFilePath));
		}
	}

	/***
	 * 验证文件名是否合法，将不合法的用下划线替换掉。
	 * 
	 * @return 正确的文件名
	 * */
	public static String validFileName(String inputFileNameStr) {
		String patternStr = "[" + '/' + '\n' + '\r' + ' ' + '\t' + '\0' + '\f' + '`' + '?' + '*' + '\\' + '<' + '>' + '|' + '\"' + ':' + "]";
		String replacementStr = "_";
		// Compile regular expression
		Pattern pattern = Pattern.compile(patternStr);
		// Replace all occurrences of pattern in input
		Matcher matcher = pattern.matcher(inputFileNameStr);
		String output = matcher.replaceAll(replacementStr);
		return output;
	}

	/**
	 * 读出文件的内容
	 * 
	 * @param filePath
	 *            文件的路径。
	 * @return 文件的二进制内容
	 * */
	public static byte[] getFileContent(String filePath) {
		File file = FileUtil.createNewFile(filePath);
		BufferedInputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(file));
			int length = (int) file.length();
			byte[] temp = new byte[length];
			in.read(temp, 0, length);
			in.close();
			return temp;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
					in = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			file = null;
		}
		return null;
	}

	/**
	 * 拷贝文件
	 * 
	 * @param fromPath
	 *            被拷贝文件路径。
	 * @param toPath
	 *            要拷贝到的路径。
	 * @return 成功TRUE 失败FALSE
	 * */
	public static boolean copyfile(String fromPath, String toPath) {
		try {
			pipe(new FileInputStream(fromPath), new FileOutputStream(toPath, false));
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/***
	 * 为新文件追加新内容。
	 * 
	 * @param filePath
	 *            文件的路径
	 * @param bytes
	 *            文件的内容
	 * */
	public static void append2File(String filePath, byte[] bytes) {
		File f = FileUtil.createNewFile(filePath);
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(f, true);
			fout.write(bytes);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fout != null) {
				try {
					fout.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			fout = null;
			f = null;
		}

	}

	/***
	 * 为新文件追加新内容。
	 * 
	 * @param filePath
	 *            文件的路径
	 * @param fileName
	 *            文件的名称
	 * @param bytes
	 *            文件的内容
	 * */
	public static void append2File(String filePath, String fileName, byte[] bytes) {
		File f = FileUtil.createNewFile(filePath, fileName);
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(f, true);
			fout.write(bytes);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fout != null) {
				try {
					fout.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			fout = null;
			f = null;
		}
	}

	/***
	 * 创建一个新文件。覆盖原有文件
	 * 
	 * @param filePath
	 *            文件的路径
	 * @param bytes
	 *            文件的内容
	 * */
	public static void createNewFile(String filePath, byte[] bytes) {
		File f = FileUtil.createNewFile(filePath);
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(f, false);
			fout.write(bytes);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fout != null) {
				try {
					fout.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			fout = null;
			f = null;
		}
	}

	/***
	 * 创建一个新文件。覆盖原有文件
	 * 
	 * @param filePath
	 *            文件的路径
	 * 
	 * @param fileName
	 *            文件的名称
	 * @param bytes
	 *            文件的内容
	 * */
	public static void createNewFile(String filePath, String fileName, byte[] bytes) {
		File f = FileUtil.createNewFile(filePath, fileName);
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(f, false);
			fout.write(bytes);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fout != null) {
				try {
					fout.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			fout = null;
			f = null;
		}
	}

	/***
	 * 从一个输入流input 把数据拷到另一个输出流 output
	 * 
	 * @param
	 * */
	private static void pipe(InputStream in, OutputStream out) throws IOException {
		byte[] buf = new byte[1024];
		int nread;
		synchronized (in) {
			while ((nread = in.read(buf, 0, buf.length)) >= 0) {
				out.write(buf, 0, nread);
			}
		}
		out.flush();
		buf = null;
	}

	public static boolean createFolder(String filePath) {
		File f = new File(filePath);
		if (f.exists()) {
			return true;
		} else {
			f.mkdirs();
			return false;
		}
	}

	public static File createNewFile(String filePath) {
		File f = new File(filePath);
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return f;
	}

	public static File createNewFile(String filePath, String fileName) {
		FileUtil.createFolder(filePath);
		File f = new File(filePath, fileName);
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return f;
	}

	public static boolean isEixstsFile(String path) {
		if (path == null) {
			return false;
		}
		File f = new File(path);
		if (f.exists())
			return true;
		return false;
	}

	public static boolean isExistSdCard() {
		if (!TextUtils.isEmpty(FileUtil.getSavePath()))
			return true;
		return false;
	}

	public static String getFileName(String filePath) {
		File file = new File(filePath);
		return file.getName();
	}

	public static long getFileLength(File f) {
		long size = 0;
		File flist[] = f.listFiles();
		for (int i = 0; i < flist.length; i++) {
			if (flist[i].isDirectory()) {
				size = size + getFileLength(flist[i]);
			} else {
				size = size + flist[i].length();
			}
		}
		return size;
	}

	public static String getFileSize(Context context, long value) {
		return Formatter.formatFileSize(context, value);
	}

	public static String getFilePath(String filePath) {
		File file = new File(filePath);
		return file.getAbsolutePath();
	}

	public static String getPhotoPath(String fileDir, String imageUrl) {
		return fileDir + MD5.toMd5(imageUrl.getBytes());
	}

	public static boolean isDirectory(String filePath) {
		File file = new File(filePath);
		if (file.isDirectory())
			return true;
		return false;
	}

	public static boolean delFile(String path) {
		if (path == null) {
			return false;
		}
		File f = new File(path);
		if (f.exists()) {
			return f.delete();
		}
		return false;
	}

	public static void delFiles(String fileDirPath) {
		if (isEixstsFile(fileDirPath)) {
			try {
				Runtime.getRuntime().exec("rm -r " + fileDirPath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 移动目录
	 * 
	 * @param srcDirName
	 *            源目录完整路径
	 * @param destDirName
	 *            目的目录完整路径
	 * @return 目录移动成功返回true，否则返回false
	 */
	public static boolean moveDirectory(String srcDirName, String destDirName) {

		File srcDir = new File(srcDirName);
		if (!srcDir.exists() || !srcDir.isDirectory())
			return false;

		File destDir = new File(destDirName);
		if (!destDir.exists())
			destDir.mkdirs();

		/**
		 * 如果是文件则移动，否则递归移动文件夹。删除最终的空源文件夹 注意移动文件夹时保持文件夹的树状结构
		 */
		File[] sourceFiles = srcDir.listFiles();
		for (File sourceFile : sourceFiles) {
			if (sourceFile.isFile())
				moveFile(sourceFile.getAbsolutePath(), destDir.getAbsolutePath());
			else if (sourceFile.isDirectory())
				moveDirectory(sourceFile.getAbsolutePath(), destDir.getAbsolutePath() + File.separator + sourceFile.getName());
			else
				;
		}
		return srcDir.delete();
	}

	/**
	 * 移动文件
	 * 
	 * @param srcFileName
	 *            源文件完整路径
	 * @param destDirName
	 *            目的目录完整路径
	 * @return 文件移动成功返回true，否则返回false
	 */
	public static boolean moveFile(String srcFileName, String destDirName) {

		File srcFile = new File(srcFileName);
		if (!srcFile.exists() || !srcFile.isFile())
			return false;

		File destDir = new File(destDirName);
		if (!destDir.exists())
			destDir.mkdirs();

		return srcFile.renameTo(new File(destDirName + File.separator + srcFile.getName()));
	}

	/**
	 * 移动文件修改后缀
	 * 
	 * @param srcFileName
	 * @param destDirName
	 * @return
	 */
	public static boolean moveFileWithPrefix(String srcFileName, String destDirName, String name) {

		File srcFile = new File(srcFileName);
		if (!srcFile.exists() || !srcFile.isFile())
			return false;

		File destDir = new File(destDirName);
		if (!destDir.exists())
			destDir.mkdirs();
		return srcFile.renameTo(new File(destDirName + File.separator + name));
	}

	public static String getSavePath() {
		String filePath = getSavePath(1024);
		return filePath.endsWith(File.separator) ? filePath : filePath + File.separator;
	}

	public static String getSavePath(long saveSize) {
		String savePath = null;
		if (StorageUtil.getExternaltStorageAvailableSpace() > saveSize) {
			savePath = StorageUtil.getExternalStorageDirectory();
			File saveFile = new File(savePath);
			if (!saveFile.exists()) {
				saveFile.mkdirs();
			} else if (!saveFile.isDirectory()) {
				saveFile.delete();
				saveFile.mkdirs();
			}
		} else if (StorageUtil.getSdcard2StorageAvailableSpace() > saveSize) {
			savePath = StorageUtil.getSdcard2StorageDirectory();
			File saveFile = new File(savePath);
			if (!saveFile.exists()) {
				saveFile.mkdirs();
			} else if (!saveFile.isDirectory()) {
				saveFile.delete();
				saveFile.mkdirs();
			}
		} else if (StorageUtil.getInternalStorageAvailableSpace() > saveSize) {
			savePath = StorageUtil.internalStorageDirectory + File.separator;
		} else if (StorageUtil.getEmmcStorageAvailableSpace() > saveSize) {
			savePath = StorageUtil.getEmmcStorageDirectory();
			File saveFile = new File(savePath);
			if (!saveFile.exists()) {
				saveFile.mkdirs();
			} else if (!saveFile.isDirectory()) {
				saveFile.delete();
				saveFile.mkdirs();
			}
		} else if (StorageUtil.getOtherExternaltStorageAvailableSpace() > saveSize) {

			savePath = StorageUtil.getOtherExternalStorageDirectory();
			File saveFile = new File(savePath);
			if (!saveFile.exists()) {
				saveFile.mkdirs();
			} else if (!saveFile.isDirectory()) {
				saveFile.delete();
				saveFile.mkdirs();
			}
		}
		return savePath;
	}
}
