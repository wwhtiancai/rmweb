package com.tmri.rfid.service.impl;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tmri.rfid.ctrl.view.EriUnbindView;
import com.tmri.rfid.mapper.EriMapper;
import com.tmri.rfid.mapper.EriUnbindMapper;
import com.tmri.rfid.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by st on 2017/08/21.
 */
@Service
public class EriUnbindServiceImpl extends BaseServiceImpl implements EriUnbindService {

    private final static Logger LOG = LoggerFactory.getLogger(EriServiceImpl.class);

    @Autowired
    private EriUnbindMapper eriUnbindMapper;

	@Override
	public PageList<EriUnbindView> queryList(int pageIndex, int pageSize,
			Map condition) throws Exception {
		// TODO Auto-generated method stub
		return (PageList<EriUnbindView>) getPageList(EriUnbindMapper.class, "queryEriUnbindView",
				condition, pageIndex, pageSize);
	}


}
