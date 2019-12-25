package com.tmri.rfid.service;

import com.tmri.rfid.bean.OperationLog;
import com.tmri.rfid.common.OperationResult;

import java.util.List;
import java.util.Map;

/**
 * Created by Joey on 2016-03-25.
 */
public interface OperationLogService {

    void log(String czmc, Object gjz, String xxnr, OperationResult jg, String czr);

    void log(String czmc, Object gjz, String xxnr, OperationResult jg);

    void log(String czmc, Object gjz, String xxnr);

    int failCount(String czmc, Object gjz);

    List<OperationLog> fetchByCondition(Map condition);

    List<OperationLog> list(Map condition, int page, int pageSize) throws Exception;

}
