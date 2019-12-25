package com.tmri.rfid.common;

/**
 * Created by Joey on 2015/11/10.
 */
public enum  CodeTableDefinition {

    VEHICLE_LICENCE_TYPE("00", "1007", "号牌种类"),
    PROVINCE("00", "0032", "省份"),
    VEHICLE_COLOR("00", "1008", "车身主颜色"),
    VEHICLE_TYPE("00", "1004", "车辆类型"),
    VEHICLE_USING_PURPOSE("00", "1003", "使用性质");

    private String xtlb;
    private String dmlb;
    private String desc;

    private CodeTableDefinition(String xtlb, String dmlb, String desc) {
        this.xtlb = xtlb;
        this.dmlb = dmlb;
        this.desc = desc;
    }

    public String getXtlb() {

        return xtlb;
    }

    public String getDmlb() {
        return dmlb;
    }

    public String getDesc() {
        return desc;
    }
}
