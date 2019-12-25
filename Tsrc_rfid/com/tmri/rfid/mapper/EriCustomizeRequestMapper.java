package com.tmri.rfid.mapper;

import com.tmri.rfid.bean.EriCustomizeRequest;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Joey on 2016-03-23.
 */
public interface EriCustomizeRequestMapper extends BaseMapper<EriCustomizeRequest> {

    EriCustomizeRequest queryLast(@Param("tid") String tid);

}
