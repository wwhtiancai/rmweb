package com.tmri.rfid.service.impl;

import com.tmri.rfid.bean.ExternalRequest;
import com.tmri.rfid.common.ExternalRequestStatus;
import com.tmri.rfid.mapper.ExternalRequestMapper;
import com.tmri.rfid.service.ExchangeService;
import com.tmri.rfid.service.ExternalRequestService;
import com.tmri.rfid.util.GsonHelper;
import com.tmri.share.frm.service.GSysparaCodeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Joey on 2016-03-23.
 */
@Service
public class ExternalRequestServiceImpl implements ExternalRequestService {

    @Resource
    private ExternalRequestMapper externalRequestMapper;

    @Resource
    private ExchangeService exchangeService;

    @Resource
    private GSysparaCodeService gSysparaCodeService;

    @Override
    public ExternalRequest create(String qqmc, String qqcs) throws Exception{
        ExternalRequest externalRequest = new ExternalRequest();
        externalRequest.setQqmc(qqmc);
        externalRequest.setQqcs(qqcs);
        externalRequest.setCjsj(new Date());
        externalRequest.setZt(ExternalRequestStatus.NEW.getStatus());
        if (externalRequestMapper.create(externalRequest) > 0) {
            exchangeService.saveData(externalRequest);
            return externalRequest;
        } else {
            return null;
        }
    }

    @Override
    public boolean updateByCondition(Map condition) {
        return externalRequestMapper.updateByCondition(condition) > 0;
    }

    @Override
    public List<ExternalRequest> fetchByCondition(Map condition) throws Exception {
        return externalRequestMapper.queryByCondition(condition);
    }

    @Override
    public boolean update(ExternalRequest externalRequest) throws Exception {
        return externalRequestMapper.update(externalRequest) > 0;
    }
}
