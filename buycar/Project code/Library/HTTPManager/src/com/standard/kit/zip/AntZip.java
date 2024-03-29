package com.standard.kit.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

/**
 * apache下的解压缩
 * 
 * @author thinkpad
 * 
 */
public class AntZip {

	public static void zipFile(String zipFileName, String inputFileName) throws Exception {
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
		out.setEncoding("UTF-8");
		File inputFile = new File(inputFileName);
		zipIt(out, inputFile, "", true);
		out.close();
	}

	/**
	 * 能支持中文的压缩 参数base 开始为"" first 开始为true
	 */
	public static void zipIt(ZipOutputStream out, File f, String base, boolean first) throws Exception {
		if (f.isDirectory()) {
			File[] fl = f.listFiles();
			if (first) {
				first = false;
			} else {
				base = base + File.separator;// "/";
			}
			for (int i = 0; i < fl.length; i++) {
				zipIt(out, fl[i], base + fl[i].getName(), first);
			}
		} else {
			if (first) {
				base = f.getName();
			}
			out.putNextEntry(new ZipEntry(base));
			FileInputStream in = new FileInputStream(f);
			int b;
			while ((b = in.read()) != -1) {
				out.write(b);
			}
			in.close();
		}
	}

	/**
	 * 解压缩入口
	 * 
	 * @param unZipFileName
	 *            要解压的路径
	 * @param unZipPath
	 *            释放的路径
	 * @throws Exception
	 */
	public static void unZipFile(String unZipFileName, String unZipPath) throws Exception {
		ZipFile zipFile = new ZipFile(unZipFileName);
		unZipFileByOpache(zipFile, unZipPath);
	}

	/**
	 * 解压文件 unZip为解压路径
	 */
	public static void unZipFileByOpache(ZipFile zipFile, String unZipRoot) throws Exception, IOException {
		Enumeration<?> e = zipFile.getEntries();
		ZipEntry zipEntry;
		while (e.hasMoreElements()) {
			zipEntry = (ZipEntry) e.nextElement();
			InputStream fis = zipFile.getInputStream(zipEntry);
			if (zipEntry.isDirectory()) {
			} else {
				File file = new File(unZipRoot + File.separator + zipEntry.getName());
				File parentFile = file.getParentFile();
				parentFile.mkdirs();
				FileOutputStream fos = new FileOutputStream(file);
				byte[] b = new byte[1024];
				int len;
				while ((len = fis.read(b, 0, b.length)) != -1) {
					fos.write(b, 0, len);
				}
				fos.close();
				fis.close();
			}
		}
	}
}
