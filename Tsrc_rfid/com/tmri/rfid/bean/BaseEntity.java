package com.tmri.rfid.bean;

import java.util.Date;

/**
 * Created by Joey on 2015/9/22.
 */
public class BaseEntity implements Auditable {

    private String cjr;
    private Date cjrq;
    private String gxr;
    private Date gxrq;

    @Override
    public String getCjr() {
        return cjr;
    }

    public void setCjr(String cjr) {
        this.cjr = cjr;
    }

    @Override
    public Date getCjrq() {
        return cjrq;
    }

    public void setCjrq(Date cjrq) {
        this.cjrq = cjrq;
    }

    @Override
    public String getGxr() {
        return gxr;
    }

    public void setGxr(String gxr) {
        this.gxr = gxr;
    }

    @Override
    public Date getGxrq() {
        return gxrq;
    }

    public void setGxrq(Date gxrq) {
        this.gxrq = gxrq;
    }
}
