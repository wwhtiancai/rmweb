package com.tmri.framework.service;

import java.util.List;

import com.tmri.framework.bean.FrmGispara;
import com.tmri.share.frm.bean.RmLog;
import com.tmri.share.ora.bean.DbResult;

/**
 * GIS参数配置服务层
 * @author shiyl 2014-10-23
 */
public interface FrmGisparaService {
    public List<FrmGispara> getGisParaList(FrmGispara para, RmLog log) throws Exception;

    public DbResult saveBean(FrmGispara bean, RmLog log) throws Exception;

    public FrmGispara getGisParaByPk(String gjz) throws Exception;
}
