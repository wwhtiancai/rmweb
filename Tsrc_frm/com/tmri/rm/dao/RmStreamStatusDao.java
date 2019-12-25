package com.tmri.rm.dao;

import java.util.List;

import com.tmri.rm.bean.RmStreamConsumer;
import com.tmri.share.frm.dao.FrmDao;
import com.tmri.share.frm.util.PageController;

public interface RmStreamStatusDao extends FrmDao {

	List<RmStreamConsumer> getStreamConsumers(String yxjb, PageController controller) throws Exception;

}
