package com.standard.kit.zip;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * ZIP压缩算法，支持RAR，ZIP，JAR格式。
 * 
 * @author neil lizhize
 * @version 1.0
 */
public class Zip {

	/**
	 * 内存中压缩数据使用zlib压缩
	 * 
	 * @param aBeforeCompressBytes
	 *            压缩前的字节码。
	 * @return 压缩后的字节码。
	 */
	public static byte[] compressData(byte[] aBeforeCompressBytes) {
		ByteArrayOutputStream bis = new ByteArrayOutputStream();
		Deflater compresser = new Deflater();
		try {
			// Compress the bytes 开始压缩数据,
			byte[] tempByte = new byte[1024];
			int compressedDataLength = -1;
			compresser.setInput(aBeforeCompressBytes); // 要压缩的数据包
			compresser.finish();
			while (compressedDataLength != 0) {
				compressedDataLength = compresser.deflate(tempByte); // 压缩，返回的是数据包经过缩缩后的大小
				bis.write(tempByte, 0, compressedDataLength);
			}
		} catch (Exception ex) {
			// handle
			ex.printStackTrace();
		} finally {
			if (compresser != null)
				compresser.end();
		}
		return bis.toByteArray();
	}

	/**
	 * 解压数据使用zlib解压
	 * 
	 * @param bytes
	 *            解压 前的字节码。
	 * @return 解压 后的字节码。
	 * @throws DataFormatException
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] decompressData(byte[] bytes)
			throws DataFormatException, IOException {

		ByteArrayOutputStream _bos = new ByteArrayOutputStream();

		int resultLength = -1;
		try { // Decompress the bytes // 开始解压
			Inflater decompresser = new Inflater();
			decompresser.setInput(bytes, 0, bytes.length); // 对byte[]进行解压，同时可以要解压的数据包中的某一段数据，就好像从zip中解压出某一个文件一样。
			byte[] result = new byte[1024];
			while (resultLength != 0) {
				resultLength = decompresser.inflate(result); // 返回的是解压后的的数据包大小，
				_bos.write(result, 0, resultLength);
			}
			decompresser.end();
			result = null;
			decompresser = null;
		} catch (Exception ex) {
		}

		return _bos.toByteArray();
	}

	/**
	 * 执行解压缩操作
	 * 
	 * @param apkPathFile
	 *            apk文件路径
	 * @param aDestPath
	 *            要解压释放到的路径
	 * @param filter
	 *            需要解压的文件 为空解压全部
	 * 
	 */
	public static void doDecompression(String apkPathFile, String aDestPath,
			String filter) {
		try {

			// 先指定压缩档的位置和档名，建立FileInputStream对象
			FileInputStream fins = new FileInputStream(apkPathFile);
			// 将fins传入ZipInputStream中
			ZipInputStream zins = new ZipInputStream(fins);
			ZipEntry ze = null;
			File zfile = null;
			File fpath = null;
			byte ch[] = new byte[256];
			while ((ze = zins.getNextEntry()) != null) {

				if ((null != filter) && (!ze.getName().equals(filter))) {
					continue;
				}

				zfile = new File(aDestPath + ze.getName());
				fpath = new File(zfile.getParentFile().getPath());
				if (ze.isDirectory()) {
					if (!zfile.exists())
						zfile.mkdirs();
					zins.closeEntry();
				} else {
					if (!fpath.exists())
						fpath.mkdirs();
					FileOutputStream fouts = new FileOutputStream(zfile);
					int i;
					while ((i = zins.read(ch)) != -1)
						fouts.write(ch, 0, i);
					zins.closeEntry();
					fouts.close();
				}
			}
			fins.close();
			zins.close();
			ch = null;
		} catch (Exception e) {

		}
	}

	/**
	 * 解压缩文件 解压某个文件夹
	 * 
	 * @param apkPathFile
	 *            需要解压的文件路径
	 * @param DestPath
	 *            目标文件夹
	 * @param filter
	 *            需要解压出来的文件夹 filter 为空解压全部
	 */
	public static void doDecompressionDir(String apkPathFile, String DestPath,
			String filter) {
		try {

			// 先指定压缩档的位置和档名，建立FileInputStream对象
			FileInputStream fins = new FileInputStream(apkPathFile);
			// 将fins传入ZipInputStream中
			ZipInputStream zins = new ZipInputStream(fins);
			ZipEntry ze = null;
			File zfile = null;
			File fpath = null;
			byte ch[] = new byte[256];
			while ((ze = zins.getNextEntry()) != null) {
				while (ze != null) {

					if ((filter != null) && (!ze.getName().startsWith(filter))) {
						continue;
					}

					zfile = new File(DestPath + ze.getName());
					fpath = new File(zfile.getParentFile().getPath());
					if (ze.isDirectory()) {
						if (!zfile.exists())
							zfile.mkdirs();
						zins.closeEntry();
					} else {
						if (!fpath.exists())
							fpath.mkdirs();
						FileOutputStream fouts = new FileOutputStream(zfile);
						int i;
						while ((i = zins.read(ch)) != -1)
							fouts.write(ch, 0, i);
						zins.closeEntry();
						fouts.close();
					}
					ze = zins.getNextEntry();
				}
				fins.close();
				zins.close();
				ch = null;
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
