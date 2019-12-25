package com.tmri.share.frm.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public final class AlarmHelper {
	private static final String KEY = "1234567890123456";
	
	private AlarmHelper(){
		
	}
	
	public static byte[] objectEZByte(Serializable object) throws Exception {
		String Algorithm = "AES";
		byte[] keybyte = KEY.getBytes();
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

	public static Object byteEZObject(byte[] bytes) throws Exception {
		String Algorithm = "AES";
		byte[] keybyte = KEY.getBytes();
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
