package com.tmri.framework.dao;

import java.util.List;

import com.tmri.framework.bean.FrmGispara;
import com.tmri.share.frm.dao.FrmDao;

/**
 * GIS�����������ݲ�
 * @author shiyl 2014-10-23
 */
public interface FrmGisparaDao extends FrmDao {
    public List<FrmGispara> getGisParaList(FrmGispara bean) throws Exception;

    public FrmGispara getGisParaByPk(String gjz) throws Exception;
}
