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
	 * 数据库密码加密算法
	 * @param password
	 * @return
	 */
	public static String encryptDBPassWord(String password) {
		String result = "加密失败!";
		try {
			result = AES.encrypt(password, Constants.DB_PW_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
