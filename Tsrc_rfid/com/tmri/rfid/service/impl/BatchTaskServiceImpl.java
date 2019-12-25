package com.tmri.rfid.service.impl;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.tmri.rfid.bean.BatchTask;
import com.tmri.rfid.mapper.BatchTaskMapper;
import com.tmri.rfid.service.BatchTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Joey on 2017/6/28.
 */
@Service
public class BatchTaskServiceImpl implements BatchTaskService {

    @Autowired
    private BatchTaskMapper batchTaskMapper;

    @Override
    public List<BatchTask> fetchByCondition(Map condition, int page, int pageSize) {
        return batchTaskMapper.queryByCondition(condition, new PageBounds(page, pageSize));
    }

    @Override
    public BatchTask fetchByXh(Long xh) {
        return batchTaskMapper.queryById(xh);
    }

    @Override
    public boolean update(BatchTask batchTask) {
        return batchTaskMapper.update(batchTask) > 0;
    }

    @Override
    public boolean updateByXh(Long xh, int zt) {
        BatchTask batchTask = fetchByXh(xh);
        if (batchTask != null) {
            batchTask.setZt(zt);
            batchTask.setFinishAt(new Date());
            return update(batchTask);
        }
        return false;
    }
}
