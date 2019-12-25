package com.tmri.rfid.service;

import com.tmri.rfid.bean.EriCustomizeRecord;

import java.util.List;
import java.util.Map;

/**
 * Created by Joey on 2016-06-04.
 */
public interface EriCustomizeRecordService {

    List<EriCustomizeRecord> fetchByCondition(Map<Object, Object> condition);

    boolean cancel(Long id);

}
