package com.tmri.share.frm.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public final class StreamUtil {
	private StreamUtil() {

	}

	public static Object byte2Object(byte[] bytes) {
		ByteArrayInputStream byteStream = null;
		GZIPInputStream gzipStream = null;
		ObjectInputStream objectStream = null;

		Object object = null;
		try {
			byteStream = new ByteArrayInputStream(bytes);
			gzipStream = new GZIPInputStream(byteStream);
			objectStream = new ObjectInputStream(gzipStream);
			object = objectStream.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != objectStream) {
				try {
					objectStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != gzipStream) {
				try {
					gzipStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != byteStream) {
				try {
					byteStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return object;
	}

	public static byte[] object2Byte(Serializable object) {
		ByteArrayOutputStream byteStream = null;
		GZIPOutputStream gzipStream = null;
		ObjectOutputStream objectStream = null;

		try {
			byteStream = new ByteArrayOutputStream();
			gzipStream = new GZIPOutputStream(byteStream);
			objectStream = new ObjectOutputStream(gzipStream);
			objectStream.writeObject(object);
			objectStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != objectStream) {
				try {
					objectStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != gzipStream) {
				try {
					gzipStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != byteStream) {
				try {
					byteStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return byteStream.toByteArray();
	}
}
