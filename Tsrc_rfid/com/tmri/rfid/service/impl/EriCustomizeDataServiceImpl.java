package com.tmri.rfid.service.impl;

import com.tmri.rfid.bean.EriCustomizeData;
import com.tmri.rfid.bean.EriCustomizeRequest;
import com.tmri.rfid.mapper.EriCustomizeDataMapper;
import com.tmri.rfid.service.CustomizeRequestService;
import com.tmri.rfid.service.EriCustomizeDataService;
import com.tmri.rfid.service.OperationLogService;
import com.tmri.rfid.util.EriUtil;
import com.tmri.rfid.util.MapUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Joey on 2016-03-23.
 */
@Service
public class EriCustomizeDataServiceImpl implements EriCustomizeDataService {

    @Autowired
    protected EriCustomizeDataMapper eriCustomizeDataMapper;

    @Resource
    private CustomizeRequestService customizeRequestService;

    @Resource
    private OperationLogService operationLogService;

    @Override
    public EriCustomizeData create(long qqxh, String aqmkxh, String tid, String data) {
        EriCustomizeData eriCustomizeData = new EriCustomizeData();
        eriCustomizeData.setQqxh(qqxh);
        eriCustomizeData.setAqmkxh(aqmkxh);
        eriCustomizeData.setData(data);
        eriCustomizeData.setTid(tid);
        eriCustomizeData.setZt(1);
        if (eriCustomizeDataMapper.create(eriCustomizeData) > 0) {
            return eriCustomizeData;
        } else {
            return null;
        }
    }

    @Override
    public EriCustomizeData fetchByXh(String xh) {
        return eriCustomizeDataMapper.queryById(xh);
    }

    @Override
    public EriCustomizeData fetchByTidAndAqmkxh(String tid, String aqmkxh) throws Exception {
        String _aqmkxh = aqmkxh;
        if (aqmkxh.length() == 16) {
            _aqmkxh = EriUtil.parseSecurityModelXh(aqmkxh);
        }
        EriCustomizeRequest request = customizeRequestService.fetchActiveByTid(tid);
        if (request == null) return null;
        List<EriCustomizeData> dataList = eriCustomizeDataMapper
                .queryByCondition(MapUtilities.buildMap("qqxh", request.getXh(), "tid", tid, "aqmkxh", _aqmkxh, "zt", 1));
        if (dataList == null || dataList.isEmpty()) return null;
        else return dataList.get(0);
    }

    @Override
    public boolean update(EriCustomizeData eriCustomizeData) {
        return eriCustomizeDataMapper.update(eriCustomizeData) > 0;
    }

    @Override
    public boolean updateByCondition(Map<String, String> condition) {
        return eriCustomizeDataMapper.updateByCondition(condition) > 0;
    }

    @Override
    public boolean deleteByTid(String tid) {
        return eriCustomizeDataMapper.deleteByTid(tid) > 0;
    }
}
