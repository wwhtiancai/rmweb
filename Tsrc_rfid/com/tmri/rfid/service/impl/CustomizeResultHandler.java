package com.tmri.rfid.service.impl;

import com.tmri.rfid.bean.CustomizeTask;
import com.tmri.rfid.bean.EriCustomizeRecord;
import com.tmri.rfid.bean.EriCustomizeRequest;
import com.tmri.rfid.bean.ExternalRequest;
import com.tmri.rfid.common.CustomizeTaskStatus;
import com.tmri.rfid.common.EriCustomizeStatus;
import com.tmri.rfid.common.ExternalRequestStatus;
import com.tmri.rfid.mapper.EriCustomizeRecordMapper;
import com.tmri.rfid.service.*;
import com.tmri.rfid.util.EriUtil;
import com.tmri.rfid.util.GsonHelper;
import com.tmri.rfid.util.MapUtilities;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Joey on 2016-03-25.
 */
@Service
public class CustomizeResultHandler extends AbstractExternalRequestHandler {

    private final static String TASK_NAME = "CUSTOMIZE_RESULT";

    @Override
    public String getTaskName() {
        return TASK_NAME;
    }

    @Resource
    private ExternalRequestService externalRequestService;

    @Resource
    private ExchangeService exchangeService;

    @Autowired
    private EriCustomizeRecordMapper eriCustomizeRecordMapper;

    @Resource
    private EriService eriService;

    @Resource
    private CustomizeTaskService customizeTaskService;

    @Resource
    private CustomizeRequestService customizeRequestService;

    @Override
    public void execute(ExternalRequest externalRequest) throws Exception {
        Map<String, Object> qqcs = EriUtil.toMap(externalRequest.getQqcs());
        String sbyy = String.valueOf(qqcs.get("sbyy"));
        String aqmkxh = String.valueOf(qqcs.get("aqmkxh"));
        String qqxh = String.valueOf(qqcs.get("qqxh"));
        String czr = String.valueOf(qqcs.get("czr"));
        Object result = qqcs.get("result");
        if (StringUtils.isEmpty(qqxh) || result == null) {
            externalRequest.setCljg("请求参数QQXH或RESULT为空");
        } else {
            List<EriCustomizeRequest> customizeRequests = customizeRequestService.fetchByCondition(MapUtilities.buildMap("xh", qqxh));
            if (customizeRequests == null || customizeRequests.isEmpty()) {
                externalRequest.setCljg("无法找到外部请求");
            } else {
                EriCustomizeRequest customizeRequest = customizeRequests.get(0);
                CustomizeTask task = customizeTaskService.fetchByXh(customizeRequest.getQqxh());
                if (task == null) {
                    externalRequest.setCljg("无法找到对应的业务请求");
                } else {
                    int _result = Integer.valueOf(result.toString());
                    if (_result == 1) {
                        List<EriCustomizeRecord> ecrs = eriCustomizeRecordMapper.queryByCondition(
                                MapUtilities.buildMap("zt", EriCustomizeStatus.IN_PROGRESS.getStatus(), "qqxh", task.getXh()));
                        if (ecrs != null && !ecrs.isEmpty()) {
                            EriCustomizeRecord ecr = ecrs.get(0);
                            eriCustomizeRecordMapper.updateByCondition(MapUtilities.buildMap("cond_qqxh", task.getXh(),
                                    "zt", EriCustomizeStatus.SUCCESS.getStatus(), "gxhczr", czr));
                            eriService.updateByCondition(MapUtilities.buildMap(
                                    "cond_tid", customizeRequest.getTid(), "clxxbfid", ecr.getClxxbfid(), "scgxhrq", new Date()));
                        }
                        task.setZt(CustomizeTaskStatus.DONE.getStatus());
                        task.setWcrq(new Date());
                        task.setTid(customizeRequest.getTid());
                    } else {
                        eriCustomizeRecordMapper.updateByCondition(MapUtilities.buildMap("cond_qqxh", task.getXh(),
                                "zt", EriCustomizeStatus.FAIL.getStatus(), "sbyy", "(" + aqmkxh + ")" + sbyy,
                                "gxhczr", czr));
                        task.setZt(CustomizeTaskStatus.CANCEL.getStatus());
                        task.setBz("(" + aqmkxh + ")" + sbyy);
                    }
                    customizeTaskService.update(task);
                }
            }
        }
        externalRequest.setZt(ExternalRequestStatus.DONE.getStatus());
        externalRequestService.update(externalRequest);
        exchangeService.saveData(externalRequest);
    }

    @Override
    public void generate(Map<Object, Object> params) throws Exception {
        externalRequestService.create(TASK_NAME, GsonHelper.getGson().toJson(params));
    }
}
