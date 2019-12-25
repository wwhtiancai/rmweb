package com.tmri.framework.dao;

import java.util.List;

import com.tmri.share.frm.bean.BasAllxzqh;
import com.tmri.share.frm.bean.FrmXzqhLocal;
import com.tmri.share.frm.dao.FrmDao;
import com.tmri.share.frm.util.PageController;
import com.tmri.share.ora.bean.DbResult;

public interface XzqhConfigDao extends FrmDao {

	List<FrmXzqhLocal> getFrmXzqhLocal(FrmXzqhLocal bean,
			PageController controller) throws Exception;

	List<BasAllxzqh> getBasAllXzqhList(FrmXzqhLocal bean) throws Exception;

	DbResult saveXzqhList(final List<FrmXzqhLocal> xzqhList) throws Exception;

}
