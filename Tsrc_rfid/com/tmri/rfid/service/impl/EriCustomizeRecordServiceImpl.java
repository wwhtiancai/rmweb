package com.tmri.rfid.service.impl;

import com.tmri.rfid.bean.EriCustomizeRecord;
import com.tmri.rfid.mapper.EriCustomizeRecordMapper;
import com.tmri.rfid.service.EriCustomizeRecordService;
import com.tmri.rfid.util.MapUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Joey on 2016-06-04.
 */
@Service
public class EriCustomizeRecordServiceImpl implements EriCustomizeRecordService  {

    @Autowired
    private EriCustomizeRecordMapper eriCustomizeRecordMapper;

    @Override
    public List<EriCustomizeRecord> fetchByCondition(Map<Object, Object> condition) {
        return eriCustomizeRecordMapper.queryByCondition(condition);
    }

    public boolean cancel(Long id) {
        return eriCustomizeRecordMapper.updateByCondition(MapUtilities.buildMap("id", id)) > 0;
    }
}
