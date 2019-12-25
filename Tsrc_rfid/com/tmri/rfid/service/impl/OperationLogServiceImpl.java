package com.tmri.rfid.service.impl;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.tmri.framework.web.support.UserState;
import com.tmri.rfid.bean.OperationLog;
import com.tmri.rfid.bean.ProductApply;
import com.tmri.rfid.common.OperationResult;
import com.tmri.rfid.mapper.OperationLogMapper;
import com.tmri.rfid.service.OperationLogService;
import com.tmri.rfid.util.MapUtilities;
import com.tmri.share.frm.bean.SysUser;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Joey on 2016-03-25.
 */
@Service
public class OperationLogServiceImpl implements OperationLogService {

    private final static Logger LOG = LoggerFactory.getLogger(OperationLogServiceImpl.class);

    @Autowired
    private OperationLogMapper operationLogMapper;

    @Override
    public void log(String czmc, Object gjz, String xxnr, OperationResult jg, String czr) {
        OperationLog log = new OperationLog();
        log.setCzmc(czmc);
        log.setGjz(String.valueOf(gjz));
        log.setXxnr(xxnr);
        log.setJg(jg.getResult());
        if (StringUtils.isEmpty(czr)) {
            SysUser user = UserState.getUser();
            if (user != null) log.setCzr(user.getYhdh());
            else log.setCzr("system");
        } else {
            log.setCzr(czr);
        }
        operationLogMapper.create(log);
    }

    @Override
    public void log(String czmc, Object gjz, String xxnr, OperationResult jg) {
        log(czmc, gjz, xxnr, jg, null);
    }

    @Override
    public void log(String czmc, Object gjz, String xxnr) {
        log(czmc, gjz, xxnr, OperationResult.NO_RESULT);
    }

    @Override
    public int failCount(String czmc, Object gjz) {
        return operationLogMapper.count(MapUtilities.buildMap("czmc", czmc , "gjz",
                String.valueOf(gjz), "jg", OperationResult.FAIL.getResult()));
    }

    @Override
    public List<OperationLog> fetchByCondition(Map condition) {
        return operationLogMapper.queryByCondition(condition);
    }

    @Override
    public List<OperationLog> list(Map condition, int page, int pageSize) throws Exception {
        return operationLogMapper.queryByCondition(condition, new PageBounds(page, pageSize));
    }
}
