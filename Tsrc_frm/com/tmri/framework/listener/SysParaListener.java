package com.tmri.framework.listener;

import com.tmri.share.frm.bean.SysPara;

/**
 * Created by Joey on 2017/4/14.
 */
public interface SysParaListener {

    void before(SysPara sysPara);
    void after(SysPara sysPara);
}
