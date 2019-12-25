package com.tmri.share.frm.util;

public final class PartitionKeyUtil {
	private PartitionKeyUtil() {

	}

	public static String getTfcPassPartitionKey(String hpzl, String hphm, String gcxh) {
		String key = hphm;
		if (null == hphm || "".equals(hphm) || hphm.length() < 5) {
			key = gcxh;
		}
		return key;
	}
}
