/**
 * 
 */
package com.tmri.share.frm.datasource;

import org.apache.commons.dbcp.BasicDataSource;

import com.tmri.share.frm.util.AES;
import com.tmri.share.frm.util.Constants;

/**
 * @author trmi
 * 
 */
public class RmBasicDataSource extends BasicDataSource {

	/**
	 * �Ƿ��Ѿ�����
	 */
	protected boolean decrypted = false;

	@Override
	public synchronized void setPassword(String password) {
		super.setPassword(password);
		if (!decrypted) {
			try {
				this.password = AES.decrypt(this.password, Constants.DB_PW_KEY);
				decrypted = true;
			} catch (Exception e) {
				log("�������ݿ�����ʧ��!");
			}
		}
	}

	private void log(String message) {
		if (logWriter != null) {
			logWriter.println(message);
		}
	}
}
