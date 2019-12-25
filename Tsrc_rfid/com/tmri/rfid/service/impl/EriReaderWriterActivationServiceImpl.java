package com.tmri.rfid.service.impl;

import com.tmri.rfid.bean.EriReaderWriterActivation;
import com.tmri.rfid.common.EriReaderWriterActivateStatus;
import com.tmri.rfid.mapper.EriReaderWriterActivationMapper;
import com.tmri.rfid.service.EriReaderWriterActivationService;
import com.tmri.rfid.util.MapUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Joey on 2016-03-01.
 */
@Service
public class EriReaderWriterActivationServiceImpl implements EriReaderWriterActivationService {

    @Autowired
    private EriReaderWriterActivationMapper eriReaderWriterActivationMapper;

    @Override
    public boolean create(EriReaderWriterActivation eriReaderWriterActivation) {
        return eriReaderWriterActivationMapper.create(eriReaderWriterActivation) > 0;
    }

    @Override
    public boolean update(EriReaderWriterActivation eriReaderWriterActivation) {
        return eriReaderWriterActivationMapper.update(eriReaderWriterActivation) > 0;
    }

    @Override
    public EriReaderWriterActivation fetchInProgress(String dxqxh) {
        List<EriReaderWriterActivation> activationList =
                eriReaderWriterActivationMapper.queryByCondition(MapUtilities.buildMap("dxqxh", dxqxh,
                        "zt", EriReaderWriterActivateStatus.IN_PROGRESS.getStatus()));
        if (activationList == null || activationList.isEmpty()) {
            return null;
        } else if (activationList.size() > 1) {
            throw new RuntimeException("获取到多条正在激活中的记录，请先重置激活状态");
        } else {
            return activationList.get(0);
        }
    }

    @Override
    public boolean reset(String dxqxh) throws Exception {
        return eriReaderWriterActivationMapper.updateByCondition(
                MapUtilities.buildMap("cond_dxqxh", dxqxh, "cond_zt", EriReaderWriterActivateStatus.IN_PROGRESS.getStatus(),
                        "zt", EriReaderWriterActivateStatus.FAIL.getStatus(),
                        "sbyy", EriReaderWriterActivateStatus.RESET.getDesc())) >= 0;
    }
}
