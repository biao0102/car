package com.standard.kit.apk;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.interfaces.DSAPublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Collection;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.content.pm.PackageInfo;

/**
 * 获取证书的publicKey,验证签名十分一致
 * 
 * @author landmark
 * 
 * @date 2012-11-12 下午4:25:43
 * 
 * @version
 */
public class PublicKeyUtil {

	public static final String[] FILTER_STR = { "RSA", "DSA" };

	public static String getPublicKeyByPackageInfo(PackageInfo packageInfo) {
		String path = packageInfo.applicationInfo.publicSourceDir;
		byte[] bytes = extractFromZipFileMatchExtension(path, FILTER_STR);
		String publickey = getCertKeyByContent(bytes);
		return publickey;
	}

	public static String getPublicKeyByPath(String path) {
		byte[] bytes = extractFromZipFileMatchExtension(path, FILTER_STR);
		String publickey = getCertKeyByContent(bytes);
		return publickey;
	}

	private static byte[] extractFromZipFileMatchExtension(String apkPathFile, String[] filter) {
		byte[] bytes = null;
		FileInputStream fins = null;
		ZipInputStream zins = null;
		try {
			// 先指定压缩档的位置和档名，建立FileInputStream对象
			fins = new FileInputStream(apkPathFile);
			// 将fins传入ZipInputStream中
			zins = new ZipInputStream(fins);
			ZipEntry ze = null;

			byte ch[] = new byte[256];
			boolean _filterStatus = false;
			int _filterCnt = filter.length;
			while ((ze = zins.getNextEntry()) != null) {
				_filterStatus = false;
				if (!ze.getName().toUpperCase().startsWith("META-INF")) {
					continue;
				}
				for (int i = 0; i < _filterCnt; i++) {
					_filterStatus = _filterStatus || ze.getName().toUpperCase().endsWith(filter[i]);
				}
				if (!_filterStatus) {
					continue;
				}
				if (ze.isDirectory()) {
					zins.closeEntry();
				} else {
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					int i = 0;
					while ((i = zins.read(ch)) != -1)
						bos.write(ch, 0, i);
					zins.closeEntry();
					ch = null;
					bytes = bos.toByteArray();
					zins.closeEntry();
					return bytes;
				}
			}
			ch = null;
		} catch (Exception e) {
			bytes = null;
		} finally {
			try {
				if (null != fins)
					fins.close();
				if (null != zins)
					zins.close();
			} catch (IOException e) {
			}
		}
		return bytes;
	}

	private static String getCertKeyByContent(byte[] bytes) {
		try {
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			Collection<?> c = cf.generateCertificates(new ByteArrayInputStream(bytes));
			Iterator<?> i = c.iterator();
			while (i.hasNext()) {
				Certificate cert = null;
				try {
					cert = (Certificate) i.next();
				} catch (Exception e) {
					continue;
				}
				PublicKey pk = cert.getPublicKey();
				BigInteger modulus = new BigInteger("0");
				if (pk instanceof DSAPublicKey) {
					modulus = ((DSAPublicKey) pk).getParams().getP();
				}
				if (pk instanceof RSAPublicKey) {
					modulus = ((RSAPublicKey) pk).getModulus();
				}
				return modulus.toString();
			}
		} catch (Exception e) {
		}
		return null;
	}
}