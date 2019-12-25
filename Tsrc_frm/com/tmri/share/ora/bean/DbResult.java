package com.tmri.share.ora.bean;

public class DbResult {
	private long code = 0;
	private String msg = "";
	private String msg1 = "";
	private String msg2 = "";
	private String msg3 = "";
	private Object obj;// ∑µªÿ∂‘œÛ xuxd 100715

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

	public String getMsg3() {
		return msg3;
	}

	public void setMsg3(String msg3) {
		this.msg3 = msg3;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public long getCode() {
		return code;
	}

	public void setCode(long code) {
		this.code = code;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public void appendMsg1(String message) {
		this.msg1 = this.msg1 + message;
	}

	public void appendMsg(String message) {
		this.msg = this.msg + message;
	}
	
	
}
