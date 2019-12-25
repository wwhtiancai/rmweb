package com.tmri.rfid.service.impl;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.tmri.framework.web.support.UserState;
import com.tmri.rfid.bean.*;
import com.tmri.rfid.common.*;
import com.tmri.rfid.exception.OperationException;
import com.tmri.rfid.mapper.EriCustomizeRecordMapper;
import com.tmri.rfid.mapper.EriCustomizeRequestMapper;
import com.tmri.rfid.service.*;
import com.tmri.rfid.util.GsonHelper;
import com.tmri.rfid.util.MapUtilities;
import com.tmri.share.frm.bean.SysPara;
import com.tmri.share.frm.bean.SysUser;
import com.tmri.share.frm.service.GSysparaCodeService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Joey on 2016-03-23.
 */
@Service
public class CustomizeRequestServiceImpl implements CustomizeRequestService {

    private final static Logger LOG = LoggerFactory.getLogger(CustomizeRequestServiceImpl.class);

    @Autowired
    private EriCustomizeRequestMapper eriCustomizeRequestMapper;

    @Resource
    private VehicleService vehicleService;

    @Resource
    private GSysparaCodeService gSysparaCodeService;

    @Resource
    private EriCustomizeDataService customizeDataService;

    @Resource
    private EriCustomizeDataService eriCustomizeDataService;

    @Resource
    private ExchangeService exchangeService;

    @Resource
    private CustomizeResultHandler customizeResultHandler;

    @Resource
    private OperationLogService operationLogService;

    @Resource
    private EriService eriService;

    @Resource
    private EriEquipmentService eriEquipmentService;

    @Resource
    private EriCustomizeRecordMapper eriCustomizeRecordMapper;

    @Resource
    private VehicleLogService vehicleLogService;

    @Resource
    private CustomizeTaskService customizeTaskService;

    @Override
    public EriCustomizeRequest fetchByXh(long xh) {
        return eriCustomizeRequestMapper.queryById(xh);
    }

    public boolean create(EriCustomizeRequest eriCustomizeRequest) {
        return eriCustomizeRequestMapper.create(eriCustomizeRequest) > 0;
    }

    @Override
    public boolean update(EriCustomizeRequest customizeRequest) {
        return eriCustomizeRequestMapper.update(customizeRequest) > 0;
    }

    //互联网端使用手持机生成
    @Override
    public EriCustomizeRequest create(String lsh, String hphm, String hpzl, String fzjg, String tid, String kh, int ywlx) throws Exception{
        EriCustomizeRequest request = fetchActiveByTid(tid);
        if (request != null ) {
            throw new RuntimeException(String.format("您已经发送过一次请求，若要再次请求，请先取消之前的请求"));
        }
        request = new EriCustomizeRequest();
        if (StringUtils.isNotEmpty(lsh)) {
            request.setLsh(lsh);
        } else if (ywlx == CustomizeTaskType.NEW.getType() ||
                ywlx == CustomizeTaskType.SUPPLY.getType()) {
            if (StringUtils.isEmpty(hphm) || StringUtils.isEmpty(hpzl)) {
                throw new RuntimeException("号牌号码或号牌种类为空");
            }
            request.setHphm(hphm);
            request.setHpzl(hpzl);
            SysPara sysPara = gSysparaCodeService.getSyspara("00", "2", "BDFZJG");
            if (StringUtils.isEmpty(sysPara.getMrz())) throw new RuntimeException("本地发证机关未设置");
            String[] bdfzjgs = sysPara.getMrz().toUpperCase().split(",");
            if (StringUtils.isEmpty(fzjg)) {
                if (bdfzjgs.length > 1) throw new RuntimeException("请输入发证机关");
                else request.setFzjg(bdfzjgs[0]);
            } else {
                request.setFzjg(fzjg);
            }
        }
        request.setTid(tid);
        request.setKh(kh);
        SysUser sysUser = UserState.getUser();
        request.setGlbm(sysUser.getGlbm());
        request.setZt(EriCustomizeRequestStatus.NEW.getStatus());
        request.setCjr(sysUser.getYhdh());
        request.setYwlx(ywlx);
        if (create(request)) {
            operationLogService.log("CREATE_CUSTOMIZE_REQUEST", request.getXh(),
                    String.format("流水号：%s，号牌号码：%s，号牌种类：%s，发证机关：%s，业务类型：%s", lsh, hphm, hpzl, fzjg, ywlx));
            exchangeService.saveData(request);
            return request;
        }
        return null;
    }

    @Override
    public List<EriCustomizeRequest> fetchByCondition(Map condition) {
        return eriCustomizeRequestMapper.queryByCondition(condition);
    }

    @Override
    public List<EriCustomizeRequest> fetchByCondition(Map condition, int page, int pageSize) {
        return eriCustomizeRequestMapper.queryByCondition(condition, new PageBounds(page, pageSize));
    }

    public EriCustomizeRequest fetchActiveByTid(String tid) {
        List<EriCustomizeRequest> eriCustomizeRequestList = fetchByCondition(MapUtilities.buildMap(
                        "tid", tid, "zts", new int[] {
                        EriCustomizeRequestStatus.NEW.getStatus(),
                        EriCustomizeRequestStatus.FETCHED.getStatus(),
                        EriCustomizeRequestStatus.FETCHING.getStatus()}));

        if (eriCustomizeRequestList != null && !eriCustomizeRequestList.isEmpty()) {
            return eriCustomizeRequestList.get(0);
        } else {
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class, RuntimeException.class})
    public void handle(EriCustomizeRequest eriCustomizeRequest) throws Exception {
        if (eriCustomizeRequest.getZt() == EriCustomizeRequestStatus.NEW.getStatus()) {
            //循环处理新生成的请求
            Vehicle vehicle;
            CustomizeTask task = null;
            if (eriCustomizeRequest.getYwlx() == CustomizeTaskType.MODIFY.getType() ||
                    eriCustomizeRequest.getYwlx() == CustomizeTaskType.ANNUAL_INSPECTION.getType() ||
                    eriCustomizeRequest.getYwlx() == CustomizeTaskType.TRANSFER.getType() ||
                    eriCustomizeRequest.getYwlx() == CustomizeTaskType.CHANGE.getType()) {
                //对于业务类型为换领、过户、年检、变更的请求，判断是否已在发行管理系统内办理相关业务并已通过审核
                List<CustomizeTask> tasks = customizeTaskService.queryList(MapUtilities.buildMap(
                        "tid", eriCustomizeRequest.getTid(), "zt", CustomizeTaskStatus.SUBMIT.getStatus(),
                        "rwlx", eriCustomizeRequest.getYwlx()
                ));
                if (tasks == null || tasks.isEmpty() ) {
                    //未办理业务，返回报错
                    eriCustomizeRequest.setSbyy("请先在发行管理系统内办理相关业务并通过审核");
                    eriCustomizeRequest.setZt(EriCustomizeRequestStatus.FAIL.getStatus());
                    finish(eriCustomizeRequest);
                    return;
                } else {
                    //已办理业务，查询需要签注的车辆
                    task = tasks.get(0);
                    vehicle = vehicleService.fetchById(tasks.get(0).getClxxid());
                }
            } else {
                //根据用户输入的流水号、号牌号码等信息获取车辆信息
                vehicle = vehicleService.synchronize(eriCustomizeRequest.getLsh(),
                        eriCustomizeRequest.getFzjg(), eriCustomizeRequest.getHphm(), eriCustomizeRequest.getHpzl(),null);
                task = customizeTaskService.fetch(vehicle, CustomizeTaskType.NEW.getType(),
                        eriCustomizeRequest.getTid(), eriCustomizeRequest.getCjr());
            }
            if (vehicle == null) {
                //未获取到签注的车辆返回报错
                eriCustomizeRequest.setSbyy("未找到对应的车辆信息");
                eriCustomizeRequest.setZt(EriCustomizeRequestStatus.FAIL.getStatus());
                finish(eriCustomizeRequest);
                return;
            }
            Long clxxbfid = vehicleLogService.createLogById(vehicle.getId());
            if (clxxbfid == null) {
                eriCustomizeRequest.setSbyy("生成车辆备份信息失败");
                eriCustomizeRequest.setZt(EriCustomizeRequestStatus.FAIL.getStatus());
                finish(eriCustomizeRequest);
                return;
            }
            Eri eri = eriService.fetchByTid(eriCustomizeRequest.getTid());
            if (eri == null || EriStatus.AVAILABLE.getStatus() != eri.getZt()) {
                eriCustomizeRequest.setSbyy("电子标识未初始化或标识当前状态无法个性化");
                eriCustomizeRequest.setZt(EriCustomizeRequestStatus.FAIL.getStatus());
                finish(eriCustomizeRequest);
                return;
            }
            if (eri.getClxxbfid() != null) {
                VehicleLog oldVehicle = vehicleLogService.fetchById(eri.getClxxbfid());
                if (eriCustomizeRequest.getYwlx() != CustomizeTaskType.MODIFY.getType() &&
                        (!oldVehicle.getFzjg().equalsIgnoreCase(vehicle.getFzjg()) ||
                                !oldVehicle.getHpzl().equalsIgnoreCase(vehicle.getHpzl()) ||
                                !oldVehicle.getHphm().equalsIgnoreCase(vehicle.getHphm()))) {
                    //电子标识已签注车辆信息且当前要签注的车辆与已签注车辆不一致，需要通过变更业务进行
                    eriCustomizeRequest.setSbyy(
                            String.format("电子标识已与车辆%s（%s）绑定，请办理变更业务", oldVehicle.getFzjg().substring(0, 1) + oldVehicle.getHphm(),
                                    vehicle.getHpzl()));
                    eriCustomizeRequest.setZt(EriCustomizeRequestStatus.FAIL.getStatus());
                    finish(eriCustomizeRequest);
                    return;
                }
            }
            eriCustomizeRecordMapper.updateByCondition(MapUtilities.buildMap("cond_tid", eri.getTid(),
                    "cond_zt", EriCustomizeStatus.IN_PROGRESS.getStatus(),
                    "zt", EriCustomizeStatus.FAIL.getStatus(), "sbyy", "重复创建请求，取消进行中的请求"));
            Eri boundCard = eriService.fetchByVehicle(vehicle);

            if (boundCard != null) {
                if (task != null && task.getRwlx() == CustomizeTaskType.CHANGE.getType()) {
                    eriService.updateByCondition(MapUtilities.buildMap("cond_tid", boundCard.getTid(),
                            "zt", EriStatus.DISABLE.getStatus(), "bz", "进行换领业务，原卡报废"));
                    operationLogService.log("CHANGE_CARD", boundCard.getTid(), task.getTid());
                } else if (task != null && task.getRwlx() == CustomizeTaskType.ANNUAL_INSPECTION.getType()) {
                    operationLogService.log("ANNUAL_INSPECTION", boundCard.getTid(), task.getTid());
                } else {
                    eriCustomizeRequest.setSbyy("该车辆已与卡（" + boundCard.getKh() + "）绑定");
                    eriCustomizeRequest.setZt(EriCustomizeRequestStatus.FAIL.getStatus());
                    finish(eriCustomizeRequest);
                    return;
                }
            }
            List<EriEquipmentBean> eriEquipmentBeanList = eriEquipmentService.fetchByCondition(
                    MapUtilities.buildMap("glbm", eriCustomizeRequest.getGlbm(), "zt", EriReaderWriterRegisterStatus.REGISTERED.getStatus()));
            List<EriCustomizeData> dataList = new ArrayList<EriCustomizeData>();
            for (EriEquipmentBean eriEquipmentBean : eriEquipmentBeanList) {
                if (StringUtils.isEmpty(eriEquipmentBean.getGy())) continue;

                EriCustomizeContent content = eriService.customize(eri, vehicle, eriEquipmentBean.getGy());
                EriCustomizeData data = eriCustomizeDataService.create(eriCustomizeRequest.getXh(), eriEquipmentBean.getAqmkxh(),
                        eriCustomizeRequest.getTid(), GsonHelper.getGson().toJson(content));
                dataList.add(data);
            }
            EriCustomizeRecord ecr = new EriCustomizeRecord();
            ecr.setTid(eri.getTid());
            ecr.setZt(EriCustomizeStatus.IN_PROGRESS.getStatus());
            ecr.setGxhrq(new Date());
            ecr.setGxhczr(eriCustomizeRequest.getCjr());
            ecr.setClxxbfid(clxxbfid);
            ecr.setQqxh(task.getXh());
            ecr.setKh(eri.getKh());
            ecr.setLsh(vehicle.getLsh());
            eriCustomizeRecordMapper.create(ecr);
            eriCustomizeRequest.setKh(eri.getKh());
            eriCustomizeRequest.setZt(EriCustomizeRequestStatus.FETCHED.getStatus());
            eriCustomizeRequest.setLsh(vehicle.getLsh());
            eriCustomizeRequest.setQqxh(task.getXh());
            finish(eriCustomizeRequest, dataList);
        }
    }

    private void finish(EriCustomizeRequest eriCustomizeRequest) throws Exception {
        finish(eriCustomizeRequest, null);
    }

    private void finish(EriCustomizeRequest eriCustomizeRequest, List<EriCustomizeData> dataList) throws Exception {
        eriCustomizeRequestMapper.update(eriCustomizeRequest);
        exchangeService.saveData(eriCustomizeRequest);
        if (eriCustomizeRequest.getZt() == EriCustomizeRequestStatus.FETCHED.getStatus() && dataList != null) {
            for (EriCustomizeData data : dataList) {
                exchangeService.saveData(data);
            }
        }
    }

    @Override
    @Transactional
    public boolean updateCustomizeResult(long qqxh, String aqmkxh, int result, String sbyy) throws Exception {
        String _sbyy = sbyy;
        EriCustomizeRequest request = eriCustomizeRequestMapper.queryById(qqxh);
        if (result <= 0) {
            //写入失败
            operationLogService.log("CUSTOMIZE", qqxh, aqmkxh + "(" + sbyy + ")", OperationResult.FAIL);
            int failCount = operationLogService.failCount("CUSTOMIZE", qqxh);
            if (failCount >= 5) {
                _sbyy = _sbyy + "该请求已写入失败5次（" + sbyy + "）";
                request.setZt(EriCustomizeRequestStatus.FAIL.getStatus());
                request.setSbyy(_sbyy);
            } else {
                return true;
            }
        } else if (result == 1){
            eriCustomizeDataService.deleteByTid(request.getTid());
            request.setZt(EriCustomizeRequestStatus.WRITTEN.getStatus());
        } else {
            request.setZt(EriCustomizeRequestStatus.CANCELLED.getStatus());
            request.setSbyy(_sbyy);
        }
        eriCustomizeRequestMapper.update(request);
        eriCustomizeDataService.deleteByTid(request.getTid());
        Map<Object, Object> customizeResult = MapUtilities.buildMap("qqxh", qqxh, "aqmkxh", aqmkxh,
                "result", result, "sbyy", _sbyy, "czr", request.getCjr());
        customizeResultHandler.generate(customizeResult);
        exchangeService.saveData(request);
        return true;
    }

    @Override
    public boolean checkRequestStatus(long qqxh) throws Exception {
        EriCustomizeRequest request = eriCustomizeRequestMapper.queryById(qqxh);
        if (request == null || request.getZt() == EriCustomizeRequestStatus.CANCELLED.getStatus()
                || request.getZt() == EriCustomizeRequestStatus.FAIL.getStatus()
                || request.getZt() == EriCustomizeRequestStatus.WRITTEN.getStatus()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public EriCustomizeRequest fetchLast(String tid) {
        return eriCustomizeRequestMapper.queryLast(tid);
    }


}
