package com.tmri.rfid.bean;

/**
 * Created by Joey on 2016-03-23.
 * ����Ϊר�������µ�ǩע�豸���ɸ��Ի�����
 */
public class EriCustomizeData {

    private long xh;
    /**
     * �Ծ���EriCustomizeRequest.xh
     */
    private long qqxh;
    private String aqmkxh;
    private String tid;
    private String data;
    private int zt;

    public long getXh() {
        return xh;
    }

    public void setXh(long xh) {
        this.xh = xh;
    }

    public long getQqxh() {
        return qqxh;
    }

    public void setQqxh(long qqxh) {
        this.qqxh = qqxh;
    }

    public String getAqmkxh() {
        return aqmkxh;
    }

    public void setAqmkxh(String aqmkxh) {
        this.aqmkxh = aqmkxh;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getZt() {
        return zt;
    }

    public void setZt(int zt) {
        this.zt = zt;
    }
}
