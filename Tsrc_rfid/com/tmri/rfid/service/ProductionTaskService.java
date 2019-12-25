package com.tmri.rfid.service;

import com.tmri.rfid.bean.ProductionTask;
import com.tmri.rfid.ctrl.view.ProductionTaskView;

import java.util.List;
import java.util.Map;

/**
 * Created by Joey on 2015/10/16.
 */
public interface ProductionTaskService {

    void create(String sf, int qskh, int sl, String jbr) throws Exception;

    void create(ProductionTask productionTask, String sf, int qskh, int sl, String cpdm) throws Exception;

    List<ProductionTaskView> fetchByCondition(Map condition) throws Exception;

    List<ProductionTaskView> fetchByCondition(Map condition, int page, int pageSize) throws Exception;

    ProductionTask fetchByRwh(String rwh) throws Exception;

    boolean startTask(String rwh) throws Exception;

    void finishTask(String rwh) throws Exception;

    void finishSubTask(String bzhh) throws Exception;
}
