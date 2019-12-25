package com.tmri.framework.util;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.Deflater;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.Inflater;
import java.util.zip.DataFormatException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 
 * <p>
 * Title: 基层基础平台
 * </p>
 * <p>
 * Description: 字节级处理工具类，将对象转为字节流/字符串
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: 公安部交通管理科学研究所
 * </p>
 * 
 * @author 武红斌
 * @version 1.0 20060403@manshan 增加 字节流加密 20081114 邵志骅
 */
public class WrapHelper {
	static String key = "1234567890abcdefghijklmn";

	/**
	 * translate a byte array into a HEX string
	 * 
	 * @param b
	 *            byte[]
	 * @return String
	 */
	public static String byte2hex(byte[] b) {
		StringBuffer hs = new StringBuffer();
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs.append("0");
				hs.append(stmp);
			} else {
				hs.append(stmp);
				;
			}
		}
		return hs.toString().toUpperCase();
	}

	/**
	 * translate a HEX string to a byte array
	 * 
	 * @param strkey
	 *            String
	 * @return byte[]
	 */
	public static byte[] hex2byte(String strkey) {
		int keylength = strkey.length() / 2;
		String strValue = "";
		byte[] key = new byte[keylength];
		for (int i = 0; i < keylength; i++) {
			strValue = strkey.substring(2 * i, 2 * (i + 1));
			key[i] = Integer.valueOf(strValue, 16).byteValue();
		}
		return key;
	}

	/**
	 * populate a HEX Serialized string into a Object
	 * 
	 * @param hexStr
	 *            String the string is compressed
	 * @return Object
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Object pupuZipHexString2Object(String hexStr)
			throws IOException, ClassNotFoundException, DataFormatException {
		byte[] unzip = hex2byte(hexStr); // deCompress(hex2byte(hexStr));
		return byte2Object(unzip);
	}

	/**
	 * populate a Serializable Object into a string
	 * 
	 * @param srcObject
	 *            Object
	 * @return String the result string is compressed
	 * @throws IOException
	 */
	public static String popuObject2ZipHexString(Object srcObject)
			throws IOException {
		byte[] ziped = object2Byte(srcObject); // compress(object2Byte(srcObject));
		String tmp = byte2hex(ziped);
		// System.out.print("zipedObjStr="+tmp);
		return tmp;
	}

	public static Object byte2Object(byte[] srcByte)
			throws ClassNotFoundException, IOException {
		ByteArrayInputStream is = new ByteArrayInputStream(srcByte);
		ObjectInputStream ois = new ObjectInputStream(is);
		return ois.readObject();
	}

	public static byte[] object2Byte(Object srcObject) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(os);
		oos.writeObject(srcObject);
		return os.toByteArray();
	}

	/**
	 * compress a byte array using Deflater
	 * 
	 * @param srcByte
	 *            byte[]
	 * @return byte[]
	 */
	public static byte[] compress(byte[] srcByte) {
		byte[] output = new byte[srcByte.length];
		Deflater compresser = new Deflater();
		compresser.setInput(srcByte);
		compresser.finish();
		int compressedDataLength = compresser.deflate(output);
		byte[] result = new byte[compressedDataLength];
		System.arraycopy(output, 0, result, 0, compressedDataLength);
		return result;
	}

	/**
	 * decompress a byte array using Inflater
	 * 
	 * @param compressedByte
	 *            byte[]
	 * @return byte[]
	 * @throws DataFormatException
	 */
	public static byte[] deCompress(byte[] compressedByte)
			throws DataFormatException {
		Inflater decompresser = new Inflater();
		decompresser.setInput(compressedByte, 0, compressedByte.length);
		byte[] output = new byte[2 * compressedByte.length];
		int resultLength = decompresser.inflate(output);
		decompresser.end();
		byte[] result = new byte[resultLength];
		System.arraycopy(output, 0, result, 0, resultLength);
		return result;
	}

	public static byte[] getBytes(InputStream is) throws Exception {
		byte[] data = null;
		Collection chunks = new ArrayList();
		byte[] buffer = new byte[1024 * 1000];
		int read = -1;
		int size = 0;
		while ((read = is.read(buffer)) != -1) {
			if (read > 0) {
				byte[] chunk = new byte[read];
				System.arraycopy(buffer, 0, chunk, 0, read);
				chunks.add(chunk);
				size += chunk.length;
			}
		}
		if (size > 0) {
			ByteArrayOutputStream bos = null;
			try {
				bos = new ByteArrayOutputStream(size);
				for (Iterator itr = chunks.iterator(); itr.hasNext();) {
					byte[] chunk = (byte[]) itr.next();
					bos.write(chunk);
				}
				data = bos.toByteArray();
			} finally {
				if (bos != null) {
					bos.close();
				}
			}
		}
		return data;
	}

	/**
	 * 
	 * 通过GZIP算法压缩将对象转换成字节数组
	 * @param object
	 * @return
	 * @throws java.lang.Exception
	 * The ZIP format, which is record-based, is not really suitable for this
	 * job.
	 * The GZIP is more appropriate as it operates on a single stream of data.
	 */

	public static byte[] objectZByte(Serializable object) throws Exception {
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		GZIPOutputStream gzipStream = new GZIPOutputStream(byteStream);
		ObjectOutputStream objectStream = new ObjectOutputStream(gzipStream);
		objectStream.writeObject(object);
		objectStream.flush();
		objectStream.close();
		gzipStream.close();
		return byteStream.toByteArray();
	}

	/**
	 * 
	 * 通过GZIP算法压缩将字节数组转换成对象
	 * @param bytes
	 * @return
	 * @throws java.lang.Exception
	 * The ZIP format, which is record-based, is not really suitable for this
	 * job.
	 * The GZIP is more appropriate as it operates on a single stream of data.
	 */

	public static Object byteZObject(byte[] bytes) throws Exception {
		ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
		GZIPInputStream gzipStream = new GZIPInputStream(byteStream);
		ObjectInputStream objectStream = new ObjectInputStream(gzipStream);
		Object object = objectStream.readObject();
		objectStream.close();
		gzipStream.close();
		return object;
	}

	/**
	 * 通过GZIP算法压缩,DES加密将对象转换成字节数组
	 * 
	 * @param object
	 * @return
	 * @throws java.lang.Exception
	 * 
	 * The ZIP format, which is record-based, is not really suitable for this
	 * job. The GZIP is more appropriate as it operates on a single stream of
	 * data.
	 */
	public static byte[] objectEZByte(Serializable object) throws Exception {
		String Algorithm = "DESede";

		byte[] keybyte = key.getBytes();
		// 指定算法,这里为DES
		SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
		// 加密要用Cipher来实现
		Cipher cipher = Cipher.getInstance(Algorithm);
		cipher.init(Cipher.ENCRYPT_MODE, deskey);
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		CipherOutputStream cipherStream = new CipherOutputStream(byteStream,
				cipher);
		GZIPOutputStream gzipStream = new GZIPOutputStream(cipherStream);
		ObjectOutputStream objectStream = new ObjectOutputStream(gzipStream);
		objectStream.writeObject(object);
		objectStream.flush();
		objectStream.close();
		gzipStream.close();
		return byteStream.toByteArray();
	}

	/**
	 * 
	 * 通过DES解码,GZIP算法压缩将字节数组转换成对象
	 * 
	 * @param bytes
	 * @return
	 * @throws java.lang.Exception
	 * 
	 * The ZIP format, which is record-based, is not really suitable for this
	 * job. The GZIP is more appropriate as it operates on a single stream of
	 * data.
	 */

	public static Object byteEZObject(byte[] bytes) throws Exception {
		String Algorithm = "DESede";

		byte[] keybyte = key.getBytes();
		// 指定算法,这里为DES
		SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
		// 加密要用Cipher来实现
		Cipher cipher = Cipher.getInstance(Algorithm);
		cipher.init(Cipher.DECRYPT_MODE, deskey);
		ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
		CipherInputStream cipherStream = new CipherInputStream(byteStream,
				cipher);
		GZIPInputStream gzipStream = new GZIPInputStream(cipherStream);
		ObjectInputStream objectStream = new ObjectInputStream(gzipStream);
		Object object = objectStream.readObject();
		objectStream.close();
		gzipStream.close();
		return object;

	}

}
