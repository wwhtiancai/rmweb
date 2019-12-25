package com.tmri.framework.bean;

import com.tmri.share.frm.util.StringUtil;


/**
 * 系统级服务返回结果BEAN
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
	 * 返回结果标记 1-成功 0-失败
	 * @return
	 */
	public long getFlag(){
		return flag;
	}
	/**
	 * 设置结果标记 1-成功 0-失败
	 * @param flag
	 */
	public void setFlag(long flag){
		this.flag=flag;
	}
	/**
	 * 返回结果描述
	 * @return
	 */
	public String getDesc(){
		return desc;
	}
	
	/**
	 * 设置结果描述
	 * @param desc
	 */
	public void setDesc(String desc){
		this.desc=desc;
	}
	
	private String retcode;// 1:保存成功 2:失败
	private String retdesc;// 返回信息描述
	private String retval;// 返回值多用于存储过程返回值	
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
