package com.tmri.rfid.service.impl;

import com.tmri.rfid.bean.InstallHistory;
import com.tmri.rfid.mapper.InstallHistoryMapper;
import com.tmri.rfid.service.InstallHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by Joey on 2017/4/25.
 */
@Service
public class InstallHistoryServiceImpl implements InstallHistoryService {

    private final static Logger LOG = LoggerFactory.getLogger(InstallHistoryServiceImpl.class);

    @Autowired
    private InstallHistoryMapper installHistoryMapper;

    @Override
    public boolean create(String tid, String kh, String hphm, String hpzl, String azr) {
        InstallHistory installHistory = new InstallHistory();
        installHistory.setTid(tid);
        installHistory.setKh(kh);
        installHistory.setHphm(hphm);
        installHistory.setHpzl(hpzl);
        installHistory.setAzr(azr);
        installHistory.setAzrq(new Date());
        return installHistoryMapper.create(installHistory) > 0;
    }
}
