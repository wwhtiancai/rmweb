package com.tmri.pub.util;

import java.io.*;
import java.util.zip.*;
import java.util.*;
import javax.crypto.*;
import javax.crypto.spec.*;

public class StreamUtil {
	// public static LinkedList<String> list=new LinkedList<String>();
	// public static void push(String str){
	// if(list.size()>5000){
	// list.removeFirst();
	// list.addLast(str);
	// }else
	// {
	// list.addLast(str);
	// }
	// }
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
	 * 
	 * @param object
	 * 
	 * @return
	 * 
	 * @throws java.lang.Exception
	 * 
	 * 
	 * 
	 *             The ZIP format, which is record-based, is not really suitable
	 *             for this job.
	 * 
	 *             The GZIP is more appropriate as it operates on a single
	 *             stream of data.
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
	 * 
	 * @param bytes
	 * 
	 * @return
	 * 
	 * @throws java.lang.Exception
	 * 
	 * 
	 * 
	 *             The ZIP format, which is record-based, is not really suitable
	 *             for this job.
	 * 
	 *             The GZIP is more appropriate as it operates on a single
	 *             stream of data.
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
	 * 
	 * 通过GZIP算法压缩,DES加密将对象转换成字节数组
	 * 
	 * @param object
	 * 
	 * @return
	 * 
	 * @throws java.lang.Exception
	 * 
	 * 
	 * 
	 *             The ZIP format, which is record-based, is not really suitable
	 *             for this job.
	 * 
	 *             The GZIP is more appropriate as it operates on a single
	 *             stream of data.
	 */
	public static byte[] objectEZByte_3des(Serializable object)
			throws Exception {
		String Algorithm = "DESede";
		String key = "1234567890abcdefghijklmn";
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

	public static byte[] objectEZByte(Serializable object) throws Exception {
		String Algorithm = "AES";
		String key = "1234567890abcdef";
		byte[] keybyte = key.getBytes();
		// 指定算法,这里为AES
		SecretKey aeskey = new SecretKeySpec(keybyte, Algorithm);
		// 加密要用Cipher来实现
		Cipher cipher = Cipher.getInstance(Algorithm);
		cipher.init(Cipher.ENCRYPT_MODE, aeskey);
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
	 * 
	 * @return
	 * 
	 * @throws java.lang.Exception
	 * 
	 * 
	 * 
	 *             The ZIP format, which is record-based, is not really suitable
	 *             for this job.
	 * 
	 *             The GZIP is more appropriate as it operates on a single
	 *             stream of data.
	 */

	public static Object byteEZObject_3des(byte[] bytes) throws Exception {
		String Algorithm = "DESede";
		String key = "1234567890abcdefghijklmn";
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

	public static Object byteEZObject(byte[] bytes) throws Exception {
		String Algorithm = "AES";
		String key = "1234567890abcdef";
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
	// public static boolean isChar(int ucs4char) {
	// return ((ucs4char >= 0x0020 && ucs4char <= 0xD7FF)
	// || ucs4char == 0x000A || ucs4char == 0x0009
	// || ucs4char == 0x000D
	// || (ucs4char >= 0xE000 && ucs4char <= 0xFFFD) || (ucs4char >= 0x10000 &&
	// ucs4char <= 0x10ffff));
	// }

	/**
	 * 过滤非法xml字符
	 * 
	 * @param xml
	 * @return
	 */
	// public static String toXmlChars(String xml) {
	// String newXml = xml;
	// for (int i = 0; i < xml.length(); i++) {
	// char ch = xml.charAt(i);
	// if (!isChar(ch)) {
	// newXml = newXml.substring(0, i) + " "
	// + newXml.substring(i + 1);
	// }
	// }
	// return newXml;
	// }
}
