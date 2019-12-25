package com.tmri.rfid.common;

import com.tmri.share.frm.bean.SysUser;

/**
 * Created by Joey on 2016-03-23.
 */
public enum SystemPlatform {
    TMRI("1", "������"),
    CGS("2", "������"),
    JCX("3", "�����");

    private String code;
    private String desc;

    SystemPlatform(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
