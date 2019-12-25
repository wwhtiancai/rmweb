package com.tmri.framework.bean;

import com.tmri.share.frm.util.StringUtil;


/**
 * ϵͳ�����񷵻ؽ��BEAN
 * @author jianghailong
 *
 */

public class SysResult implements java.io.Serializable{
	private long flag;
	private String desc;
	private String desc1;
	public String getDesc1(){
		return desc1;
	}
	public void setDesc1(String desc1){
		this.desc1=desc1;
	}
	public SysResult(){
		flag = 1;
	}
	/**
	 * ���ؽ����� 1-�ɹ� 0-ʧ��
	 * @return
	 */
	public long getFlag(){
		return flag;
	}
	/**
	 * ���ý����� 1-�ɹ� 0-ʧ��
	 * @param flag
	 */
	public void setFlag(long flag){
		this.flag=flag;
	}
	/**
	 * ���ؽ������
	 * @return
	 */
	public String getDesc(){
		return desc;
	}
	
	/**
	 * ���ý������
	 * @param desc
	 */
	public void setDesc(String desc){
		this.desc=desc;
	}
	
	private String retcode;// 1:����ɹ� 2:ʧ��
	private String retdesc;// ������Ϣ����
	private String retval;// ����ֵ�����ڴ洢���̷���ֵ	
	public String getRetcode() {
		return retcode;
	}
	public void setRetcode(String retcode) {
		this.retcode = retcode;
	}
	public String getRetdesc() {
		return StringUtil.transBlank(retdesc);
	}
	public void setRetdesc(String retdesc) {
		this.retdesc = retdesc;
	}
	public String getRetval() {
		return StringUtil.transBlank(retval);
	}
	public void setRetval(String retval) {
		this.retval = retval;
	}
}
