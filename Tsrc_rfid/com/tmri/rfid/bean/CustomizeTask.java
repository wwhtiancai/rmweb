package com.tmri.rfid.bean;

import java.util.Date;

/*
 *wuweihong
 *2015-11-13
 */
public class CustomizeTask {
    private Long xh;
    private String lsh;  //��ˮ��
    private String sqr;    //	������
    private String lxdh; //��ϵ�绰
    private String jbr;        //������  --ǰ̨ҵ��Ա����
    private Date sqrq;        //��������
    private int rwlx;    //�������� 1-�����죬2-���죬3-����
    private int zt;        //״̬ 1-�ύ��2-��ɣ�3-ȡ����4-�ȴ�����
    private String czr;  //������
    private Date wcrq;    //�������
    private Long clxxid; //������Ϣ��ţ���ӦRFID_VEHICLE.ID
    private Long yclxxid; //ԭ������Ϣ���
    private String tid; //���ӱ�ʶTID
    private String bz; //��ע
    private String tpdz; // ͼƬ��ַ

    public String getLsh() {
        return lsh;
    }

    public void setLsh(String lsh) {
        this.lsh = lsh;
    }

    public String getSqr() {
        return sqr;
    }

    public void setSqr(String sqr) {
        this.sqr = sqr;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    public String getJbr() {
        return jbr;
    }

    public void setJbr(String jbr) {
        this.jbr = jbr;
    }

    public Date getSqrq() {
        return sqrq;
    }

    public void setSqrq(Date sqrq) {
        this.sqrq = sqrq;
    }

    public int getRwlx() {
        return rwlx;
    }

    public void setRwlx(int rwlx) {
        this.rwlx = rwlx;
    }

    public int getZt() {
        return zt;
    }

    public void setZt(int zt) {
        this.zt = zt;
    }

    public String getCzr() {
        return czr;
    }

    public void setCzr(String czr) {
        this.czr = czr;
    }

    public Date getWcrq() {
        return wcrq;
    }

    public void setWcrq(Date wcrq) {
        this.wcrq = wcrq;
    }

    public Long getClxxid() {
        return clxxid;
    }

    public void setClxxid(Long clxxid) {
        this.clxxid = clxxid;
    }

    public Long getXh() {
        return xh;
    }

    public void setXh(Long xh) {
        this.xh = xh;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public Long getYclxxid() {
        return yclxxid;
    }

    public void setYclxxid(Long yclxxid) {
        this.yclxxid = yclxxid;
    }

    public String getTpdz() {
        return tpdz;
    }

    public void setTpdz(String tpdz) {
        this.tpdz = tpdz;
    }


}
