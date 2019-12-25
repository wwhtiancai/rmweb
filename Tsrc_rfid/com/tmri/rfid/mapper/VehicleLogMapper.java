package com.tmri.rfid.mapper;

import com.tmri.rfid.bean.VehicleLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Joey on 2016-03-08.
 */
@Repository
public interface VehicleLogMapper extends BaseMapper<VehicleLog> {

    Long createByClxxid(VehicleLog vehicleLog);

}
