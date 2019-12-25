package com.tmri.pub.bean.rbsp;

import java.util.HashMap;

public class FrmSysResult {
	private String code = "";
	private String message = "";
	private String msg1 = "";
	private String msg2 = "";
	private String msg3 = "";
	private String msg4 = "";
	private String msg5 = "";

	public String getMsg5() {
		return msg5;
	}

	public void setMsg5(String msg5) {
		this.msg5 = msg5;
	}

	private HashMap resultMap;

	public String getMsg4() {
		return msg4;
	}

	public void setMsg4(String msg4) {
		this.msg4 = msg4;
	}

	public String getMsg3() {
		return msg3;
	}

	public void setMsg3(String msg3) {
		this.msg3 = msg3;
	}

	public String getMsg1() {
		return msg1;
	}

	public void setMsg1(String msg1) {
		this.msg1 = msg1;
	}

	public String getMsg2() {
		return msg2;
	}

	public void setMsg2(String msg2) {
		this.msg2 = msg2;
	}

	public HashMap getResultMap() {
		return resultMap;
	}

	public void setResultMap(HashMap resultMap) {
		this.resultMap = resultMap;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}