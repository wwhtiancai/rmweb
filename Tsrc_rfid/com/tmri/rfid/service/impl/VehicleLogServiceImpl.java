package com.tmri.rfid.service.impl;

import com.tmri.rfid.bean.Eri;
import com.tmri.rfid.bean.Vehicle;
import com.tmri.rfid.bean.VehicleLog;
import com.tmri.rfid.mapper.VehicleLogMapper;
import com.tmri.rfid.service.EriService;
import com.tmri.rfid.service.VehicleLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Joey on 2016-03-08.
 */
@Service
public class VehicleLogServiceImpl implements VehicleLogService {

    private final static Logger LOG = LoggerFactory.getLogger(VehicleLogServiceImpl.class);

    @Autowired
    private VehicleLogMapper vehicleLogMapper;

    @Resource
    protected EriService eriService;

    @Override
    public Long createLogById(Long clxxid) throws Exception {
        VehicleLog vehicleLog = new VehicleLog();
        vehicleLog.setClxxid(clxxid);
        if (vehicleLogMapper.createByClxxid(vehicleLog) > 0) {
            return vehicleLog.getId();
        } else {
            return null;
        }
    }

    @Override
    public VehicleLog fetchById(Long id) throws Exception {
        return vehicleLogMapper.queryById(id);
    }

    @Override
    public VehicleLog fetchBoundByTid(String tid) throws Exception {
        Eri eri = eriService.fetchByTid(tid);
        if (eri != null && eri.getClxxbfid() != null) {
            return fetchById(eri.getClxxbfid());
        } else {
            return null;
        }

    }
}
