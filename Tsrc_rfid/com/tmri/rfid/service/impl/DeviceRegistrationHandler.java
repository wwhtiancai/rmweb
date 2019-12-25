package com.tmri.rfid.service.impl;

import com.tmri.rfid.bean.EriEquipmentBean;
import com.tmri.rfid.bean.EriReaderWriter;
import com.tmri.rfid.bean.ExternalRequest;
import com.tmri.rfid.bean.SecurityModel;
import com.tmri.rfid.common.EriReaderWriterRegisterStatus;
import com.tmri.rfid.common.ExternalRequestStatus;
import com.tmri.rfid.service.*;
import com.tmri.rfid.util.EriUtil;
import com.tmri.rfid.util.GsonHelper;
import com.tmri.rfid.util.MapUtilities;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Joey on 2016-03-25.
 */
@Service
public class DeviceRegistrationHandler extends AbstractExternalRequestHandler {

    private final String TASK_NAME = "DEVICE_REGISTRATION";

    @Resource
    private EriEquipmentService eriEquipmentService;

    @Resource
    private SecurityModelService securityModelService;

    @Resource
    private ExchangeService exchangeService;

    @Resource
    private ExternalRequestService externalRequestService;

    @Resource
    private EriReaderWriterService eriReaderWriterService;

    @Override
    public String getTaskName() {
        return TASK_NAME;
    }

    @Transactional
    public void execute(ExternalRequest externalRequest) throws Exception {
        Map<String, Object> qqcs = EriUtil.toMap(externalRequest.getQqcs());
        Object aqmkxh = qqcs.get("aqmkxh");
        Object glbm = qqcs.get("glbm");
        Object qyztStr = qqcs.get("qyzt");
        if (aqmkxh == null || glbm == null || qyztStr == null
                || EriReaderWriterRegisterStatus.valueOf(qyztStr.toString()) == null) {
            externalRequest.setCljg("请求参数安全模块序号或管理部门为空或启用状态不合法");

        } else {
            EriReaderWriterRegisterStatus status = EriReaderWriterRegisterStatus.valueOf(qyztStr.toString());
            EriEquipmentBean eriEquipmentBean = eriEquipmentService.fetchByAqmkxh(String.valueOf(aqmkxh));
            List<EriReaderWriter> eriReaderWriter = eriReaderWriterService.fetchByCondition(MapUtilities.buildMap("aqmkxh", String.valueOf(aqmkxh)));
            SecurityModel securityModel = securityModelService.queryById(String.valueOf(aqmkxh));
            if (securityModel == null) {
                externalRequest.setCljg("找不到安全模块");
            } else if (eriEquipmentBean == null) {
                eriEquipmentBean = new EriEquipmentBean();
                eriEquipmentBean.setGy(securityModel.getXp1gy());
                eriEquipmentBean.setGlbm(String.valueOf(glbm));
                eriEquipmentBean.setZt(status.getStatus());
                eriEquipmentBean.setAqmkxh(String.valueOf(aqmkxh));
                eriEquipmentService.create(eriEquipmentBean);
                exchangeService.saveData(eriEquipmentBean);
            } else if (eriEquipmentBean.getZt() == EriReaderWriterRegisterStatus.DISABLED.getStatus()) {
                externalRequest.setCljg("设备已被禁用，无法修改启用状态");
            } else {
                eriEquipmentBean.setZt(status.getStatus());
                eriEquipmentBean.setGlbm(glbm.toString());
                eriEquipmentService.update(eriEquipmentBean);
                exchangeService.saveData(eriEquipmentBean);
            }
        }
        externalRequest.setZt(ExternalRequestStatus.DONE.getStatus());
        externalRequestService.update(externalRequest);
        exchangeService.saveData(externalRequest);
    }

    @Override
    public void generate(Map<Object, Object> params) throws Exception {
        String qqcs = GsonHelper.getGson().toJson(params);
        externalRequestService.create(TASK_NAME, qqcs);
    }
}
