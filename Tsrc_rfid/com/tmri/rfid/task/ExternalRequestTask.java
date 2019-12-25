package com.tmri.rfid.task;

import com.tmri.rfid.bean.ExternalRequest;
import com.tmri.rfid.common.ExternalRequestStatus;
import com.tmri.rfid.service.ExternalRequestHandler;
import com.tmri.rfid.service.ExternalRequestService;
import com.tmri.rfid.util.MapUtilities;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Joey on 2016-03-23.
 */
@Service
public class ExternalRequestTask implements Task{

    private final static Logger LOG = LoggerFactory.getLogger(ExternalRequestTask.class);

    @Resource
    private ExternalRequestService externalRequestService;

    private List<ExternalRequestHandler> externalRequestHandlers;

    public void setExternalRequestHandlers(List<ExternalRequestHandler> externalRequestHandlers) {
        this.externalRequestHandlers = externalRequestHandlers;
    }

    public void execute() throws Exception {
        List<ExternalRequest> requestList = externalRequestService
                .fetchByCondition(MapUtilities.buildMap("zt", ExternalRequestStatus.NEW.getStatus()));
        for (ExternalRequest externalRequest : requestList) {
            try {
                for (ExternalRequestHandler handler : externalRequestHandlers) {
                    handler.handle(externalRequest);
                }
            } catch (Exception re) {
                LOG.error("处理外部请求失败", re);
            }
        }
    }
}
