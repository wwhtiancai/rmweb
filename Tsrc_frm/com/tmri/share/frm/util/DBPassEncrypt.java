/**
 * 
 */
package com.tmri.share.frm.util;

/**
 * @author duruoyao
 * 
 */
public abstract class DBPassEncrypt {

	/**
	 * ���ݿ���������㷨
	 * @param password
	 * @return
	 */
	public static String encryptDBPassWord(String password) {
		String result = "����ʧ��!";
		try {
			result = AES.encrypt(password, Constants.DB_PW_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
