package com.tmri.rfid.service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tmri.rfid.ctrl.view.EriUnbindView;

import java.util.Map;

/**
 * Created by st on 2017/8/21.
 */
public interface EriUnbindService {
	
	PageList<EriUnbindView> queryList(int pageIndex, int pageSize, Map condition) throws Exception;

}
