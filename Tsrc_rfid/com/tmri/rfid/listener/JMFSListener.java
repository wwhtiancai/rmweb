package com.tmri.rfid.listener;

import com.tmri.framework.listener.AbstractSysParaListener;
import com.tmri.framework.listener.SysParaListener;
import com.tmri.rfid.service.KMSService;
import com.tmri.share.frm.bean.SysPara;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by Joey on 2017/4/14.
 */
@Component
public class JMFSListener extends AbstractSysParaListener {

    public static final Logger LOG = LoggerFactory.getLogger(JMFSListener.class);

    @Resource
    private KMSService kmsService;

    @Override
    protected void doBefore(SysPara sysPara) {

    }

    @Override
    protected void doAfter(SysPara sysPara) {
        try {
            kmsService.reset(sysPara.getMrz());
        } catch (Exception e) {
            LOG.error("重置密钥管理系统服务地址失败", e);
        }
    }

    @Override
    protected String getXtlb() {
        return "00";
    }

    @Override
    protected String getGjz() {
        return "JMFWDZ";
    }
}
