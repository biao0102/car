package com.standard.kit.secret;

import com.standard.kit.zip.Zip;

/**
 * 常用的简单压缩加密，解密解压缩算法。
 * 
 * @author neil lizhize
 * @version 1.0
 */
public class SimpleCipher
{

	/**
	 * 先压缩后加密 .取反加密。
	 * 
	 * @param bytes
	 *            需要操作的对象字节。
	 * @return 加密压缩后的字节码,二进制数组。
	 */
	public static byte[] ecryptAfterCompressData(byte[] bytes)
	{
		return ecrypt(Zip.compressData(bytes));
	}

	/**
	 * 先解密后解压缩
	 * 
	 * @param bytes
	 *            需要操作的对象字节
	 * @return 解密解压缩后的字节码，二进制数组
	 * @throws Exception
	 */
	public static byte[] decompressAfterdecrypt(byte[] bytes) throws Exception
	{
		if (bytes == null || bytes.length == 0) return null;

		return Zip.decompressData(decrypt(bytes));
	}

	private static int CRYPTKEY = 90;

	/**
	 * 解密
	 * 
	 * @param bytes
	 *            被解密的字节码
	 * @return 字节码
	 */
	public static byte[] decrypt(byte[] bytes)
	{
		int len = bytes.length;
		byte[] _returnBytes = new byte[len];
		for (int i = 0; i < len; i++)
		{
			_returnBytes[i] = (byte) (bytes[i] ^ CRYPTKEY);
		}

		return _returnBytes;
	}

	/**
	 * 加密
	 * 
	 * @param bytes
	 *            要加密的对象字节
	 * @return 字节码
	 */
	public static byte[] ecrypt(byte[] bytes)
	{
		int len = bytes.length;
		byte[] _returnBytes = new byte[len];
		for (int i = 0; i < len; i++)
		{
			_returnBytes[i] = (byte) (bytes[i] ^ CRYPTKEY);
		}
		return _returnBytes;
	}

	public static byte[] cancelHead(byte[] bytes)
	{
		if ((null == bytes) || (bytes.length < 8))
		{
			return null;
		}

		if (('f' == bytes[0]) && ('l' == bytes[1]) && ('y' == bytes[2]) && ('s' == bytes[3]) && ('t' == bytes[4]) && ('m' == bytes[5])
				&& ('!' == bytes[6]))
		{
			int iHeaderLength = 7;

			byte[] bodytemp = new byte[bytes.length - iHeaderLength];
			System.arraycopy(bytes, iHeaderLength, bodytemp, 0, bytes.length - iHeaderLength);
			return bodytemp;
		}
		else
		{
			return bytes;
		}

	}
}
