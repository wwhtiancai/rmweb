package com.tmri.rfid.common;

/**
 * Created by st on 2015/11/30.
 */
public enum  SecurityModelType {

	ROAD("R", "·��ʽ"),
	INITIAL("I", "��ʼ��"),
	DESKTOP("D", "����ʽ"),
    HAND("H", "�ֳ�ʽ"),
    PERSONALIZE("P", "���Ի�");

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
