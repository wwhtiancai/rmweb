package com.tmri.rfid.service;

import com.tmri.rfid.bean.VehicleLog;

/**
 * Created by Joey on 2016-03-08.
 */
public interface VehicleLogService {

    Long createLogById(Long clxxid) throws Exception;

    VehicleLog fetchById(Long id) throws Exception;

    VehicleLog fetchBoundByTid(String tid) throws Exception;
}
