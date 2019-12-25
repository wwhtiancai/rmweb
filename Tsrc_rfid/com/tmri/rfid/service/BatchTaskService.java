package com.tmri.rfid.service;

import com.tmri.rfid.bean.BatchTask;

import java.util.List;
import java.util.Map;

/**
 * Created by Joey on 2017/6/28.
 */
public interface BatchTaskService {

    List<BatchTask> fetchByCondition(Map condition, int page, int pageSize);

    BatchTask fetchByXh(Long xh);

    boolean updateByXh(Long xh, int zt);

    boolean update(BatchTask batchTask);

}
