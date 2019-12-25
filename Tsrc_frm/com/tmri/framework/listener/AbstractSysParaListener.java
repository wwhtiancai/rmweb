package com.tmri.framework.listener;

import com.tmri.share.frm.bean.SysPara;

/**
 * Created by Joey on 2017/4/14.
 */
public abstract class AbstractSysParaListener implements SysParaListener {

    public void before(SysPara sysPara) {
        if (getXtlb().equalsIgnoreCase(sysPara.getXtlb()) &&
                getGjz().equalsIgnoreCase(sysPara.getGjz())) {
            doBefore(sysPara);
        }
    }

    public void after(SysPara sysPara) {
        if (getXtlb().equalsIgnoreCase(sysPara.getXtlb()) &&
                getGjz().equalsIgnoreCase(sysPara.getGjz())) {
            doAfter(sysPara);
        }
    }

    protected abstract void doBefore(SysPara sysPara);

    protected abstract void doAfter(SysPara sysPara);

    protected abstract String getXtlb();

    protected abstract String getGjz();

}
