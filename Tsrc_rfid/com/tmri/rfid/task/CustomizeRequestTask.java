package com.tmri.rfid.task;

import com.tmri.rfid.bean.EriCustomizeRequest;
import com.tmri.rfid.common.EriCustomizeRequestStatus;
import com.tmri.rfid.service.CustomizeRequestService;
import com.tmri.rfid.service.ExchangeService;
import com.tmri.rfid.util.MapUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Joey on 2016-03-24.
 */
@Service
public class CustomizeRequestTask implements Task {

    private final static Logger LOG = LoggerFactory.getLogger(CustomizeRequestTask.class);

    @Resource
    private CustomizeRequestService customizeRequestService;

    @Resource
    private ExchangeService exchangeService;

    public void execute() throws Exception {
        List<EriCustomizeRequest> customizeRequestList = customizeRequestService
                .fetchByCondition(MapUtilities.buildMap("zt", EriCustomizeRequestStatus.NEW.getStatus()));
        for (EriCustomizeRequest customizeRequest : customizeRequestList) {
            try {
                customizeRequestService.handle(customizeRequest);
            } catch (Exception e) {
                LOG.error(String.format("处理个性化请求失败（xh:%s，lsh:%s, hphm:%s,hpzl:%s,fzjg:%s）",
                                customizeRequest.getXh(), customizeRequest.getLsh(), customizeRequest.getHphm(),
                                customizeRequest.getHpzl(), customizeRequest.getFzjg()), e);
                customizeRequest.setSbyy(
                        String.format("处理个性化请求失败（xh:%s，lsh:%s, hphm:%s,hpzl:%s,fzjg:%s）",
                        customizeRequest.getXh(), customizeRequest.getLsh(), customizeRequest.getHphm(),
                        customizeRequest.getHpzl(), customizeRequest.getFzjg()) + e.getMessage());
                customizeRequest.setZt(EriCustomizeRequestStatus.FAIL.getStatus());
                customizeRequestService.update(customizeRequest);
                exchangeService.saveData(customizeRequest);
            }
        }
    }
}
