package com.tmri.rfid.bean;

import java.util.Date;

/**
 * Created by Joey on 2015/11/13.
 */
public class ElectronicCertificate {

    private Long xh;        //����֤�����
    private String zsnr;    //֤������
    private Date scrq;      //��������
    private int zt;         //״̬�� 0-��Ч��1-��Ч
    private int ssztlx;     //�����������ͣ�1-��ȫģ�飬2-��д�豸
    private String ssztbh;  //����������
    private int zsbh;       //֤���ţ���ͬһ�����ж��֤��ʱʹ��

    public Long getXh() {
        return xh;
    }

    public void setXh(Long xh) {
        this.xh = xh;
    }

    public String getZsnr() {
        return zsnr;
    }

    public void setZsnr(String zsnr) {
        this.zsnr = zsnr;
    }

    public Date getScrq() {
        return scrq;
    }

    public void setScrq(Date scrq) {
        this.scrq = scrq;
    }

    public int getZt() {
        return zt;
    }

    public void setZt(int zt) {
        this.zt = zt;
    }

    public int getSsztlx() {
        return ssztlx;
    }

    public void setSsztlx(int ssztlx) {
        this.ssztlx = ssztlx;
    }

    public String getSsztbh() {
        return ssztbh;
    }

    public void setSsztbh(String ssztbh) {
        this.ssztbh = ssztbh;
    }

    public int getZsbh() {
        return zsbh;
    }

    public void setZsbh(int zsbh) {
        this.zsbh = zsbh;
    }
}
