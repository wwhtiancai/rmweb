package com.tmri.rfid.common;

/**
 * Created by st on 2015/11/30.
 */
public enum  SecurityModelType {

	ROAD("R", "路面式"),
	INITIAL("I", "初始化"),
	DESKTOP("D", "桌面式"),
    HAND("H", "手持式"),
    PERSONALIZE("P", "个性化");

    private String dm;
    private String mc;

    SecurityModelType(String dm, String mc) {
        this.dm = dm;
        this.mc = mc;
    }

	public String getDm() {
		return dm;
	}

	public String getMc() {
		return mc;
	}

}
