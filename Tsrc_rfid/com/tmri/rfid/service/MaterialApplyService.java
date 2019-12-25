package com.tmri.rfid.service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tmri.rfid.bean.MaterialApply;
import com.tmri.rfid.bean.MaterialEri;
import com.tmri.rfid.bean.OrderApplication;
import com.tmri.rfid.ctrl.view.OrderApplicationView;

import java.util.List;
import java.util.Map;

/**
 * Created by st on 2015/11/3.
 */
public interface MaterialApplyService {

    void create(MaterialApply materialApply) throws Exception;

    /**
     * ªÒ»°∂©π∫µ•∫≈
     * @param cpdm
     * @return
     * @throws Exception
     */
    String generateOrderNumber(String cpdm) throws Exception;

	PageList<MaterialApply> queryList(int pageIndex, int pageSize, Map condition)
			throws Exception;

	MaterialApply fetchByDGDH(String dgdh) throws Exception;

	void update(MaterialApply materialApply) throws Exception;

	void delete(String dgdh) throws Exception;

	void delivery(String dgdh) throws Exception;
	
	String RKCheck(String dgdh) throws Exception;
	
	String RKCheck(String dgdh, List<MaterialEri> materialEris) throws Exception;

	void updateYrksl(String dgdh) throws Exception;
}
