package com.tmri.rfid.mapper;

import com.tmri.rfid.bean.EriCustomizeData;

/**
 * Created by Joey on 2016-03-23.
 */
public interface EriCustomizeDataMapper extends BaseMapper<EriCustomizeData> {

    int deleteByTid(String tid);

    EriCustomizeData fetchAvailableData(String tid, String dxqxh);

}
