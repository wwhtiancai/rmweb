package com.tmri.rfid.service.impl;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.google.common.io.BaseEncoding;
import com.rfid.api.HsmApi;
import com.rfid.api.SAFResult;
import com.sansec.impl.util.Bytes;
import com.tmri.framework.web.support.UserState;
import com.tmri.rfid.bean.*;
import com.tmri.rfid.common.*;
import com.tmri.rfid.exception.OperationException;
import com.tmri.rfid.mapper.EriCustomizeRecordMapper;
import com.tmri.rfid.mapper.EriInitializeRecordMapper;
import com.tmri.rfid.mapper.EriMapper;
import com.tmri.rfid.mapper.EriUnbindMapper;
import com.tmri.rfid.property.ConfigProperty;
import com.tmri.rfid.property.RuntimeProperty;
import com.tmri.rfid.service.*;
import com.tmri.rfid.util.*;
import com.tmri.rfid.webservice.SecretKeyManagerServiceStub;
import com.tmri.share.frm.bean.Code;
import com.tmri.share.frm.service.GSysparaCodeService;
import com.tmri.share.frm.util.Constants;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Joey on 2015/9/6.
 */
@Service
public class EriServiceImpl extends BaseServiceImpl implements EriService {

    private final static Logger LOG = LoggerFactory.getLogger(EriServiceImpl.class);

    @Autowired
    private EriMapper eriMapper;

    @Autowired
    private EriCustomizeRecordMapper eriCustomizeRecordMapper;

    @Resource
    private VehicleService vehicleService;

    @Resource
    private VehicleLogService vehicleLogService;

    @Autowired
    private EriInitializeRecordMapper eriInitializeRecordMapper;

    @Resource
    private GSysparaCodeService gSysparaCodeService;

    @Resource
    private InventoryService inventoryService;

    @Resource
    private CustomizeTaskService customizeTaskService;

    @Resource
    private EncryptorIndexService encryptorIndexService;

    @Resource
    private ProductService productService;

    @Resource(name = "configProperty")
    private ConfigProperty configProperty;

    @Resource(name = "runtimeProperty")
    private RuntimeProperty runtimeProperty;

    @Resource
    private RemoteVehicleService remoteVehicleService;

    @Resource
    private OperationLogService operationLogService;

    @Resource
    private KMSService kmsService;

    private Code parseProvinceAbbr(String abbr) throws Exception{
        if (StringUtils.isEmpty(abbr)) {
            throw new Exception("省份简称为空");
        }
        return gSysparaCodeService.getCodeByDmsm2(CodeTableDefinition.PROVINCE, abbr);
    }

    public Code parseVehicleType(String type) throws Exception {
        if (StringUtils.isEmpty(type)) {
            throw new Exception("车辆类型为空");
        }
        return gSysparaCodeService.getCode(CodeTableDefinition.VEHICLE_TYPE, type);
    }

    public Long parseLicenseType(String type) throws Exception {
        Code code = gSysparaCodeService.getCode(CodeTableDefinition.VEHICLE_LICENCE_TYPE, type);
        if (code == null) throw new Exception("未找到对应的号牌种类");
        return code.getDmz2();
    }

    private int calculateLicensePlate(String licensePlateType) throws Exception {
        int typeValue = licensePlateType.toLowerCase().charAt(0) - 'a';
        if (typeValue < 0) {
            throw new RuntimeException("无效的发牌代号,发牌代号小于0");
        } else if (typeValue > 25) {
            throw new RuntimeException("无效的发牌代号,发牌代号大于25");
        }
        return typeValue & 0x1f; //发牌类型最多5位2进制
    }

    private String parseLicensePlate(int value) throws Exception {
        return String.valueOf((char) ( value + 'a')).toUpperCase();
    }



    public int parseUsingPurpose(String syxz) throws Exception {
        if (StringUtils.isEmpty(syxz)) {
            throw new Exception("使用性质为空");
        }
        Code code = gSysparaCodeService.getCode(CodeTableDefinition.VEHICLE_USING_PURPOSE, syxz);
        if (code == null) {
            return 7;
        } else {
            return code.getDmz2().intValue();
        }
    }

    @Override
    @Transactional
    public EriCustomizeContent customize(Eri eri, Vehicle vehicle, String pubKey) throws Exception {
        String kh = eri.getKh();
        String provinceCode = kh.substring(0, 2);
        String sxh = kh.substring(2);
        EriCustomizeContent eriCustomizeContent = new EriCustomizeContent();
        eriCustomizeContent.setSf(provinceCode);
        eriCustomizeContent.setSxh(Long.valueOf(sxh));
        eriCustomizeContent.setVehicle(vehicle);
        eriCustomizeContent.setPh(eri.getPh());
        eriCustomizeContent.setTid(eri.getTid());
        eriCustomizeContent.setKh(eri.getKh());
        calculateVehicleInfo(0, eriCustomizeContent);

        generateCipherCodeForCustomize(eriCustomizeContent, pubKey, eri.getScgxhrq() == null);
        return eriCustomizeContent;
    }

    @Override
    @Transactional
    public EriCustomizeContent customize(String tid, Long xh, String certStr) throws Exception {
        if (StringUtils.isEmpty(tid) || xh == null || StringUtils.isEmpty(certStr)) {
            throw new OperationException("09", "CUSTOMIZE", "TID、任务号、证书不能为空");
        }
        Cert cert = EriUtil.parseCert(certStr);
        if (!verifyCert(cert)) {
            throw new OperationException("01", "CUSTOMIZE", "校验证书失败");
        }
        Eri eri = eriMapper.queryById(tid);
        if (eri == null) throw new OperationException("02", "CUSTOMIZE", "电子标识未初始化或初始化失败");
        int eriStatus = eri.getZt();
        if (EriStatus.AVAILABLE.getStatus() != eriStatus) {
            throw new OperationException("03", "CUSTOMIZE", "标识当前状态无法个性化");
        }
        List<EriCustomizeRecord> ecrs = eriCustomizeRecordMapper.queryByCondition(
                MapUtilities.buildMap("zt", EriCustomizeStatus.IN_PROGRESS.getStatus(), "tid", tid));
        if (!ecrs.isEmpty()) {
            throw new OperationException("04", "CUSTOMIZE", "该标识正在进行个性化中，请重置");
        }
        String kh = eri.getKh();
        String provinceCode = kh.substring(0, 2);
        String sxh = kh.substring(2);
        CustomizeTask customizeTask = customizeTaskService.fetchByXh(xh);
        if (customizeTask == null) {
            throw new OperationException("05", "CUSTOMIZE", "未查找到个性化任务");
        }

        if (customizeTask.getZt() == CustomizeTaskStatus.DONE.getStatus()) {
            throw new OperationException("06", "CUSTOMIZE", "该任务已完成，无法重复进行个性化");
        }
        CustomizeTaskType customizeTaskType = CustomizeTaskType.getByType(customizeTask.getRwlx());
        if (customizeTaskType == null) throw new NullPointerException("任务类型无法识别");
        EriCustomizeContent eriCustomizeContent = new EriCustomizeContent();
        eriCustomizeContent.setSf(provinceCode);
        eriCustomizeContent.setSxh(Long.valueOf(sxh));
        eriCustomizeContent.setPh(eri.getPh());
        eriCustomizeContent.setTid(eri.getTid());
        eriCustomizeContent.setKh(eri.getKh());
        EriCustomizeRecord ecr = new EriCustomizeRecord();
        ecr.setTid(tid);
        ecr.setLsh(customizeTask.getLsh());
        ecr.setZt(EriCustomizeStatus.IN_PROGRESS.getStatus());
        ecr.setGxhrq(new Date());
        ecr.setGxhczr(UserState.getUser().getYhdh());
        ecr.setKh(kh);
        ecr.setQqxh(xh);
        Code province= gSysparaCodeService.getCode("00", "0032", provinceCode);
        int provinceValue = province.getDmz2().intValue();
        if (customizeTaskType == CustomizeTaskType.PRE_CUSTOMIZE) {
            eriCustomizeContent.setUser0sbq(
                    EriUtil.appendZero(calculateHexCardNumber(provinceValue, eriCustomizeContent.getSxh()), 18, 2));
            eriCustomizeContent.setUser0xbq("000000000000000000");
            generateCipherCodeForCustomize(eriCustomizeContent, cert.getPubKey(), eri.getScgxhrq() == null);
        } else if (customizeTaskType == CustomizeTaskType.MODIFY_PASSWORD) {
            eriCustomizeContent.setUser0sbq(
                    EriUtil.appendZero(calculateHexCardNumber(provinceValue, eriCustomizeContent.getSxh()), 18, 2));
            eriCustomizeContent.setUser0xbq("000000000000000000");
            generateCipherCodeForModifyPassword(eriCustomizeContent, cert.getPubKey(), "3202", "4403");
        } else {
            Vehicle vehicle = vehicleService.fetchById(customizeTask.getClxxid());
            if (eri.getClxxbfid() != null) {
                VehicleLog oldVehicle = vehicleLogService.fetchById(eri.getClxxbfid());
                if (!(oldVehicle.getHphm().equalsIgnoreCase(vehicle.getHphm()) &&
                        oldVehicle.getHpzl().equalsIgnoreCase(vehicle.getHpzl()) &&
                        oldVehicle.getFzjg().equalsIgnoreCase(vehicle.getFzjg())) &&
                        !(customizeTask.getRwlx() == CustomizeTaskType.TRANSFER.getType() ||
                                customizeTask.getRwlx() == CustomizeTaskType.MODIFY.getType())) {
                    Code hpzlCode = gSysparaCodeService.getCode(CodeTableDefinition.VEHICLE_LICENCE_TYPE, oldVehicle.getHpzl());
                    throw new OperationException("10", "CUSTOMIZE",
                            String.format("当前电子标识已写入了号牌种类为：%s、号牌号码为：%s的车辆信息，如需更新请使用“变更”业务办理。",
                                    hpzlCode.getDmsm1(), oldVehicle.getFzjg().substring(0, 1) + oldVehicle.getHphm()));
                }
            }
            Eri boundCard = fetchByVehicle(vehicle);
            if (boundCard != null && !boundCard.getTid().equalsIgnoreCase(tid)) {
                if (customizeTask.getRwlx() == CustomizeTaskType.CHANGE.getType()) {
                    this.updateByCondition(MapUtilities.buildMap("cond_tid", boundCard.getTid(),
                            "zt", EriStatus.DISABLE.getStatus(), "bz", "进行换领业务，原卡报废"));
                    operationLogService.log("CHANGE_CARD", boundCard.getTid(), tid);
                } else {
                    throw new OperationException("07", "CUSTOMIZE", "该车辆已与电子标识（" + boundCard.getKh() + "）绑定");
                }
            }
            Long clxxbfid = vehicleLogService.createLogById(vehicle.getId());
            if (clxxbfid == null) {
                throw new OperationException("08", "CUSTOMIZE", "生成车辆备份信息失败");
            }
            eriCustomizeContent.setVehicle(vehicle);
            if(vehicle.getHpzl().equals("MH")){
                calculateVehicleInfoMh(1, eriCustomizeContent);
            }else{
                calculateVehicleInfo(0, eriCustomizeContent);
            }
            generateCipherCodeForCustomize(eriCustomizeContent, cert.getPubKey(), eri.getScgxhrq() == null);
            ecr.setClxxbfid(clxxbfid);
        }
        eriCustomizeRecordMapper.create(ecr);
        return eriCustomizeContent;
    }

    public boolean checkCardStatus(String tid, VehicleInfo vehicle, int ywlx) throws Exception {
        Eri eri = eriMapper.queryById(tid);
        if (eri == null) throw new OperationException("02", "CUSTOMIZE", "电子标识未初始化或初始化失败");
        int eriStatus = eri.getZt();
        if (EriStatus.AVAILABLE.getStatus() != eriStatus) {
            throw new OperationException("03", "CUSTOMIZE", "标识当前状态无法个性化");
        }
        List<EriCustomizeRecord> ecrs = eriCustomizeRecordMapper.queryByCondition(
                MapUtilities.buildMap("zt", EriCustomizeStatus.IN_PROGRESS.getStatus(), "tid", tid));
        if (!ecrs.isEmpty()) {
            throw new OperationException("04", "CUSTOMIZE", "该标识正在进行个性化中，请重置");
        }
        if (eri.getClxxbfid() != null) {
            VehicleLog oldVehicle = vehicleLogService.fetchById(eri.getClxxbfid());
            if (!(oldVehicle.getHphm().equalsIgnoreCase(vehicle.getHphm()) &&
                    oldVehicle.getHpzl().equalsIgnoreCase(vehicle.getHpzl()) &&
                    oldVehicle.getFzjg().equalsIgnoreCase(vehicle.getFzjg())) &&
                    !(ywlx == CustomizeTaskType.TRANSFER.getType() ||
                            ywlx == CustomizeTaskType.MODIFY.getType())) {
                Code hpzlCode = gSysparaCodeService.getCode(CodeTableDefinition.VEHICLE_LICENCE_TYPE, oldVehicle.getHpzl());
                throw new OperationException("10", "CUSTOMIZE",
                        String.format("当前电子标识已写入了号牌种类为：%s、号牌号码为：%s的车辆信息，如需更新请使用“变更”业务办理。",
                                hpzlCode.getDmsm1(), oldVehicle.getFzjg().substring(0, 1) + oldVehicle.getHphm()));
            }
            Eri boundCard = fetchByVehicle(vehicle);
            if (ywlx == CustomizeTaskType.CHANGE.getType()) {
                if (boundCard == null) {
                    throw new OperationException("11", "该车辆未制止，请办理新申领业务");
                }
            } else {
                if (boundCard != null && !boundCard.getTid().equalsIgnoreCase(tid)) {
                    throw new OperationException("07", "CUSTOMIZE", "该车辆已与电子标识（" + boundCard.getKh() + "）绑定");
                }
            }
        }
        return true;
    }

    @Transactional
    public void saveCustomizeResult(Long xh, int result, String sbyy, String tid) throws Exception{
        if (xh != null) {
            CustomizeTask task = customizeTaskService.fetchByXh(xh);
            List<EriCustomizeRecord> ecrs = eriCustomizeRecordMapper.queryByCondition(
                    MapUtilities.buildMap("zt", EriCustomizeStatus.IN_PROGRESS.getStatus(), "qqxh", xh));
            if (result > 0) {
                if (ecrs.isEmpty()) {
                    throw new Exception("未找到正在个性化中的记录，请确认");
                } else if (ecrs.size() > 1) {
                    throw new Exception("找到多条正在个性化中的记录，请先将记录置为失败，再继续操作");
                } else if (ecrs.size() == 1) {
                    EriCustomizeRecord ecr = ecrs.get(0);
                    eriCustomizeRecordMapper.updateByCondition(MapUtilities.buildMap(
                            "cond_id", ecrs.get(0).getId(), "zt", EriCustomizeStatus.SUCCESS.getStatus()));
                    eriMapper.updateByCondition(MapUtilities.buildMap(
                            "cond_tid", ecr.getTid(), "clxxbfid", ecr.getClxxbfid(), "scgxhrq", new Date()));
                    task.setTid(ecr.getTid());
                    task.setZt(CustomizeTaskStatus.DONE.getStatus());
                    task.setWcrq(new Date());
                    customizeTaskService.update(task);
                    if (runtimeProperty.isRemoteFetchVehicle() && ecr.getClxxbfid() != null) {
                        VehicleLog vehicleLog = vehicleLogService.fetchById(ecr.getClxxbfid());
                        remoteVehicleService.upload(ecr.getId(), vehicleLog.getLsh(), vehicleLog.getJdcxh(), ecr.getKh());
                    }
                }
            } else {
                // 将所有进行中的记录都置为失败
                eriCustomizeRecordMapper.updateByCondition(MapUtilities.buildMap(
                        "cond_qqxh", xh, "zt", EriCustomizeStatus.FAIL.getStatus(),
                        "cond_zt", EriCustomizeStatus.IN_PROGRESS.getStatus(), "sbyy", sbyy));
                if (StringUtils.isNotEmpty(tid)) {
                    eriCustomizeRecordMapper.updateByCondition(MapUtilities.buildMap(
                            "cond_tid", tid, "zt", EriCustomizeStatus.FAIL.getStatus(),
                            "cond_zt", EriCustomizeStatus.IN_PROGRESS.getStatus(), "sbyy", sbyy));
                }
            }
        } else {
            // 将所有进行中的记录都置为失败
            eriCustomizeRecordMapper.updateByCondition(MapUtilities.buildMap(
                    "cond_tid", tid, "zt", EriCustomizeStatus.FAIL.getStatus(),
                    "cond_zt", EriCustomizeStatus.IN_PROGRESS.getStatus(), "sbyy", sbyy));
        }
    }

    @Transactional
    public void saveInitializeResult(String tid, int result, String sbyy) throws Exception {
        List<EriInitializeRecord> eir = eriInitializeRecordMapper.queryByCondition(
                MapUtilities.buildMap("zt", EriInitializeStatus.IN_PROGRESS.getStatus(), "tid", tid));
        if (result > 0) {
            if (eir.isEmpty()) {
                throw new Exception("未找到正在初始化中的相应电子标识，请确认");
            } else if (eir.size() > 1){
                throw new Exception("找到多条正在初始化中的记录，当前初始化已失败，请重新操作");
            } else {
                eriInitializeRecordMapper.updateByCondition(MapUtilities.buildMap(
                        "cond_id", eir.get(0).getId(), "zt", EriInitializeStatus.SUCCESS.getStatus()));
                eriMapper.updateByCondition(MapUtilities.buildMap(
                        "cond_tid", tid, "zt", EriStatus.AVAILABLE.getStatus(),
                        "cshrq", new Date(), "scgxhrq", new Date()));
            }
        } else {
            eriInitializeRecordMapper.updateByCondition(MapUtilities.buildMap(
                    "cond_tid", tid, "cond_zt", EriInitializeStatus.IN_PROGRESS.getStatus(),
                    "zt", EriInitializeStatus.FAIL.getStatus(), "sbyy", sbyy));
            eriMapper.updateByCondition(MapUtilities.buildMap("cond_tid", tid, "zt", EriStatus.DISABLE.getStatus()));
        }
    }

    @Transactional
    public void saveFactoryInitializeResult(String tid, int result, String bzhh, String sbyy) throws Exception {
        List<EriInitializeRecord> eir = eriInitializeRecordMapper.queryByCondition(
                MapUtilities.buildMap("zt", EriInitializeStatus.IN_PROGRESS.getStatus(), "tid", tid));
        if (result > 0) {
            if (eir.isEmpty()) {
                throw new Exception("未找到正在初始化中的相应电子标识，请确认");
            } else if (eir.size() > 1){
                throw new Exception("找到多条正在初始化中的记录，当前初始化已失败，请重新操作");
            } else {
                eriInitializeRecordMapper.updateByCondition(MapUtilities.buildMap(
                        "cond_id", eir.get(0).getId(), "zt", EriInitializeStatus.SUCCESS.getStatus()));
                eriMapper.updateByCondition(MapUtilities.buildMap(
                        "cond_tid", tid, "zt", EriStatus.AVAILABLE.getStatus(),
                        "cshrq", new Date(), "bzhh", bzhh, "scgxhrq", new Date()));
            }
        } else {
            eriInitializeRecordMapper.updateByCondition(MapUtilities.buildMap(
                    "cond_tid", tid, "cond_zt", EriCustomizeStatus.IN_PROGRESS.getStatus(),
                    "zt", EriInitializeStatus.FAIL.getStatus(), "sbyy", sbyy));
            eriMapper.updateByCondition(MapUtilities.buildMap(
                    "cond_tid", tid, "zt", EriStatus.DISABLE.getStatus()));
        }
    }

    @Transactional
    public EriInitializeContent initialize(String tid, String province, String certStr) throws Exception{
        return initialize(tid, province, certStr, false);
    }

    /**
     * 由于卡号生成规则的不同，此初始化方法仅适用于卡类型为EriType.BY_TMRI的卡
     * @param tid
     * @param province
     * @throws Exception
     */
    @Transactional
    public EriInitializeContent initialize(String tid, String province, String certStr, boolean test) throws Exception{
        Cert cert = EriUtil.parseCert(certStr);
        if (!verifyCert(cert)) {
            throw new Exception("校验证书失败");
        }
        Eri eri = fetchByTid(tid);
        if (eri != null) {
            if (eri.getZt() != EriStatus.NEW.getStatus()) {
                throw new Exception("电子标识当前状态无法进行初始化");
            }
            if (!province.equalsIgnoreCase(eri.getSf())) {
                throw new Exception("该电子标识已被绑定，绑定省份代码为" + eri.getSf() + "，无法使用其它省份代码进行初始化");
            }
        } else {
            Long sequence = eriMapper.getCidSequence(province);
            eri = new Eri();
            eri.setKh(province + EriUtil.appendZero(String.valueOf(sequence), 10));
        }
        EriInitializeContent eriInitializeContent = initialize(tid, province, eri, true, cert, test);
        EriInitializeRecord eriInitializeRecord = new EriInitializeRecord();
        eriInitializeRecord.setZt(EriInitializeStatus.IN_PROGRESS.getStatus());
        eriInitializeRecord.setTid(tid);
        eriInitializeRecord.setCshczr("sys");
        eriInitializeRecord.setCshrq(new Date());
        eriInitializeRecord.setKh(eriInitializeContent.getKh());
        eriInitializeRecordMapper.create(eriInitializeRecord);
        return eriInitializeContent;
    }

    /**
     * @param tid
     * @param province 省份代码
     * @param eri
     * @param insert boolean 是否为第一次初始化
     * @throws Exception
     */
    private EriInitializeContent initialize(String tid, String province, Eri eri, boolean insert, Cert cert, boolean test) throws Exception {
        Long sequence = -1l;
        if (StringUtils.isEmpty(eri.getKh())) {
            sequence = eriMapper.getCidSequence(province);
        } else {
            sequence = Long.valueOf(eri.getKh().substring(2));
        }

        Code provinceCode = gSysparaCodeService.getCode("00", "0032", province);
        int provinceValue = provinceCode.getDmz2().intValue();

        String hexCardNum = calculateHexCardNumber(provinceValue, sequence);
        String hexBatchNumber = calculateBatchNumber(provinceValue, test);
        String cardNum = province + EriUtil.appendZero(String.valueOf(sequence), 10);

        EriInitializeContent eriInitializeContent = new EriInitializeContent();
        eriInitializeContent.setBkh(hexCardNum);
        eriInitializeContent.setKh(cardNum);
        eriInitializeContent.setPh(hexBatchNumber);
        eriInitializeContent.setTm(HOMEPAGE + cardNum);
        eriInitializeContent.setTid(tid);
        eriInitializeContent.setSf(province);
        eriInitializeContent.setXh(sequence);
        generateCipherCode(eriInitializeContent, cert.getPubKey());

        eri.setKlx(EriType.BY_TMRI.getType());
        eri.setSf(province);
        eri.setPh(hexBatchNumber);
        eri.setTid(tid);
        eri.setZt(EriStatus.NEW.getStatus());
        eri.setKh(cardNum);
        if (insert) {
            eriMapper.create(eri);
        } else {
            eriMapper.update(eri);
        }
        return eriInitializeContent;
    }

    @Transactional(rollbackForClassName = "Exception")
    public EriInitializeContent factoryInitialize(String tid, String kh, String bzhh, String rwh, String sbxh, String gwh, String czr, String certStr) throws Exception {
        return factoryInitialize(tid, kh, bzhh, rwh, sbxh, gwh, czr, certStr, false);
    }

    @Transactional(rollbackForClassName = "Exception")
    public EriInitializeContent factoryInitialize(String tid, String kh, String bzhh, String rwh, String sbxh, String gwh, String czr, String certStr, boolean test) throws Exception {
        Cert cert = EriUtil.parseCert(certStr);
        LOG.debug(String.format("解析证书成功，序列号：%s，版本：%s，公钥：%s", cert.getSerialNo(), cert.getVersion(), cert.getPubKey()));
        if (!verifyCert(cert)) {
            throw new Exception("校验证书失败");
        }
        Inventory inventory = inventoryService.fetchByBzhh(bzhh);
        if (inventory == null) {
            throw new Exception("未打到对应的库存记录");
        }
        if (inventory.getZt() != InventoryStatus.PLANNED.getStatus()
                && inventory.getZt() != InventoryStatus.PRODUCING.getStatus()) {
            throw new Exception("库存当前状态无法进行初始化");
        }
        Product product = productService.fetchByCPDM(inventory.getCpdm());
        if (!tid.toLowerCase().startsWith(product.getTzz().toLowerCase())) {
            throw new Exception("使用的电子标识与生产要求使用的电子标识生产厂家不一致");
        }
        String province = kh.substring(0, 2);
        int sequence = Integer.valueOf(kh.substring(2));
        int startSeq = Integer.valueOf(inventory.getQskh().substring(2));
        int endSeq = Integer.valueOf(inventory.getZzkh().substring(2));
        if (!province.equalsIgnoreCase(inventory.getQskh().substring(0, 2))
                || sequence < startSeq || sequence > endSeq) {
            throw new Exception("卡号和库存不对应");
        }
        Eri eri = fetchByTid(tid);
        EriInitializeContent eriInitializeContent;
        if (eri != null) {
            eriInitializeRecordMapper.updateByCondition(
                    MapUtilities.buildMap("cond_tid", tid, "cond_zt", EriInitializeStatus.IN_PROGRESS.getStatus(),
                            "zt", EriInitializeStatus.FAIL.getStatus(), "sbyy", "相同TID重复初始化"));
            if (eri.getZt() == EriStatus.AVAILABLE.getStatus()) {
                throw new Exception("电子标识当前状态无法进行初始化");
            }
            eri.setKh(kh);
            eriInitializeContent = initialize(tid, province, eri, false, cert, test);
        } else {
            eri = new Eri();
            eri.setBzhh(bzhh);
            eri.setKh(province + EriUtil.appendZero(String.valueOf(sequence), 10));
            eriInitializeContent = initialize(tid, province, eri, true, cert, test);
        }
        EriInitializeRecord eriInitializeRecord = new EriInitializeRecord();
        eriInitializeRecord.setZt(EriInitializeStatus.IN_PROGRESS.getStatus());
        eriInitializeRecord.setTid(tid);
        eriInitializeRecord.setCshrq(new Date());
        eriInitializeRecord.setKh(kh);
        eriInitializeRecord.setSbh(Long.valueOf(sbxh));
        eriInitializeRecord.setCshczr(czr);
        eriInitializeRecord.setSf(province);
        eriInitializeRecord.setPh(eriInitializeContent.getPh());
        eriInitializeRecord.setKlx(0);
        eriInitializeRecordMapper.create(eriInitializeRecord);
        if (inventory.getZt() == InventoryStatus.PLANNED.getStatus()) {
            inventory.setZt(InventoryStatus.PRODUCING.getStatus());
            inventoryService.update(inventory);
        }
        return eriInitializeContent;
    }

    @Override
    public Eri fetchByTid(String tid) throws Exception {
        return eriMapper.queryById(tid);
    }

    @Override
    public List<Eri> fetchByCondition(Map condition) throws Exception {
        return eriMapper.queryByCondition(condition);
    }

    @Override
    public List<Eri> fetchByRegion(String qskh, String zzkh) throws Exception {
        return eriMapper.queryByRegion(qskh, zzkh);
    }


    @Override
    public List<Eri> fetchByCondition(Map condition, PageBounds pageBounds) throws Exception {
        return eriMapper.queryByCondition(condition, pageBounds);
    }

    public void calculateVehicleInfoMh(int cardType,EriCustomizeContent eriCustomizeContent) throws Exception{
        if(StringUtils.isEmpty(eriCustomizeContent.getSf()))
            throw new Exception("电子标识上卡号不正确，省份代码为空");
        Vehicle vehicle = eriCustomizeContent.getVehicle();
        String hphm = vehicle.getHphm().substring(3);
        Code province = gSysparaCodeService.getCode("00", "0032", eriCustomizeContent.getSf());
        Code Mhcllx  = gSysparaCodeService.getCode("00", "2004", vehicle.getCllx());
        String fpdh = "";

        // 车辆信息上半区一共80位，采用两个long型数值进行拼装
        long[] info=new long[]{0l,0l};
        // info[0]存储卡号（37）和省份代码前3位(3)共40位，其中卡号又分卡类型（1）、省份代码（6）和顺序号（30）
        info[0]|=cardType;
        info[0]<<=6;
        info[0]|=56;
        info[0]<<=30;
        info[0]|=Integer.valueOf(eriCustomizeContent.getKh().substring(2));
        info[0]<<=6;
        info[0]|=calculateLicensePlate(vehicle.getHphm().substring(2,3));
        info[0]>>=3; // 删除省份代码后3位
        // info[1]存储省份代码后3位、发牌代号（5）、使用性质（4）、出厂日期（9）、车辆类型（9）、排量/功率标志位（1）、保留（1），共32位
        info[1]|=calculateLicensePlate(vehicle.getHphm().substring(2,3));
        info[1]<<=5;
        info[1]|=0x1F; // 取号牌号码第二位为发牌代号
        info[1]<<=4;
//        info[1]|=parseUsingPurpose(StringUtils.isEmpty(syxz)?"":syxz.trim());
        info[1]|=0;
        info[1]<<=9;
        info[1]|=0;
//        if(rfidEri.getCcrq()!=null){
//            calendarInstance.setTime(dateFormater.parse(rfidEri.getCcrq().substring(0,10)));
//            int numberOfMonths=(calendarInstance.get(Calendar.YEAR)-1990)*12+calendarInstance.get(Calendar.MONTH);
//            numberOfMonths=numberOfMonths>=511?511:((numberOfMonths<0?((-numberOfMonths/511)+1)*511+numberOfMonths:numberOfMonths)%511);
//            info[1]|=numberOfMonths;
//        }
        info[1]<<=9;
//        info[1]|=Integer.valueOf(parseVehicleType(rfidEri.getCllx()).getDmsm4());
        //车辆类型
        info[1]|=Integer.valueOf(vehicle.getCllx().trim());
        int plOrGl=0; // 排量/功率标志位，0-排量，1-功率
        info[1]<<=1;
//        if(rfidEri.getPl()==null||rfidEri.getPl().intValue()==0){
//            plOrGl=1;
//        }
//        // test
//        info[1]|=plOrGl;
        info[1]|=0;
        info[1]<<=1;
        info[1]&=0xffffffff; // 删除省份代码前3位
        String firstPartHex=Long.toHexString(info[0]);
        firstPartHex=EriUtil.appendZero(firstPartHex,10);
        String secondPartHex=Long.toHexString(info[1]);
        secondPartHex=EriUtil.appendZero(secondPartHex,8);
        String upperPartHex=firstPartHex+secondPartHex;
        // LOG.debug("上半区内容:{}", upperPartHex);
        eriCustomizeContent.setUser0sbq(upperPartHex);
        // 车辆信息下半区一共80位，也采用两个long型数值进行拼装
        info=new long[]{0l,0l};
        // info[0]存储号牌种类（4）、号牌号码序号（32），共36位，
//        info[0]|=parseLicenseType(rfidEri.getHpzl());
        info[0]<<=4;
        info[0]<<=32;
        info[0]|=EriUtil.parseHPHMToLong(hphm); // 去掉第一位的发牌代号
        // info[1]存储检验有效期（9）、强制报废期（5）、核定载客/总质量（10）、车身颜色（4）、排量（8）
//        if(rfidEri.getYxqz()!=null){
//            calendarInstance.setTime(dateFormater.parse(rfidEri.getYxqz().substring(0,10)));
//            int numberOfMonths=(calendarInstance.get(Calendar.YEAR)-2013)*12+calendarInstance.get(Calendar.MONTH);
//            numberOfMonths=numberOfMonths<0?511:numberOfMonths%511;
////            info[1]|=numberOfMonths;
//            info[1]|=0;
//        }
        info[1]<<=9;
        info[1]<<=5;
        info[1]|=0;
        info[1]<<=10;
//        if(rfidEri.getCllx().toUpperCase().startsWith("K")){
//            // 客车填写核定载客
//            if(rfidEri.getHdzk()==null)
//                throw new RuntimeException("核定载客为空");
//            info[1]|=rfidEri.getHdzk().intValue();
//        }else{
//            int zzl=getZzl(rfidEri);
//            if(zzl>=102300){
//                info[1]|=0x3ff;
//            }else{
//                info[1]|=Math.round(zzl/100.0);
//            }
//        }
        info[1]<<=4;
//        if(!StringUtils.isEmpty(rfidEri.getCsys())){
//            Code csysCode=systemDao.getCode("00","1008",rfidEri.getCsys().substring(0,1).toUpperCase());
//            info[1]|=csysCode==null?15:Integer.valueOf(csysCode.getDmsm4());
//        }
        // 排量/功率
        info[1]<<=8;
//        if(plOrGl==0){
//            // 排量
//            if(rfidEri.getPl().intValue()>=25600){
//                info[1]|=0xff;
//            }else{
//                info[1]|=Math.round(rfidEri.getPl().intValue()/100.0);
//            }
//        }else{
//            if(rfidEri.getGl()==null||rfidEri.getGl().intValue()<=1){
//                info[1]|=0x00;
//            }else if(rfidEri.getGl().intValue()>=255){
//                info[1]|=0xff;
//            }else{
//                info[1]|=Math.round(rfidEri.getGl().intValue());
//            }
//        }
        firstPartHex=Long.toHexString(info[0]);
        firstPartHex=EriUtil.appendZero(firstPartHex,9);
        secondPartHex=Long.toHexString(info[1]);
        secondPartHex=EriUtil.appendZero(secondPartHex,9);
        String lowerPartHex=firstPartHex+secondPartHex;
        // LOG.debug("下半区内容：{}", lowerPartHex);
        eriCustomizeContent.setUser0xbq(lowerPartHex);
    }

    /**
     *
     * @param cardType 标识类别
     * @param eriCustomizeContent 个性化信息
     */
    public void calculateVehicleInfo(int cardType, EriCustomizeContent eriCustomizeContent) throws Exception{
        if (StringUtils.isEmpty(eriCustomizeContent.getSf())) throw new Exception("电子标识上卡号不正确，省份代码为空");
        Vehicle vehicle = eriCustomizeContent.getVehicle();
        Code province = gSysparaCodeService.getCode("00", "0032", eriCustomizeContent.getSf());
        //车辆信息上半区一共80位，采用两个long型数值进行拼装
        long[] info = new long[] {0l, 0l};
        //info[0]存储卡号（37）和省份代码前3位(3)共40位，其中卡号又分卡类型（1）、省份代码（6）和顺序号（30）
        info[0] |= cardType;
        info[0] <<= 6;
        info[0] |= province.getDmz2();
        info[0] <<= 30;
        info[0] |= eriCustomizeContent.getSxh();
        info[0] <<= 6;
        Code vehicleProvince = parseProvinceAbbr(vehicle.getFzjg().substring(0, 1));
        info[0] |= vehicleProvince.getDmz2();
        info[0] >>= 3; //删除省份代码后3位
        //info[1]存储省份代码后3位、发牌代号（5）、使用性质（4）、出厂日期（9）、车辆类型（9）、排量/功率标志位（1）、保留（1），共32位
        info[1] |= vehicleProvince.getDmz2();
        info[1] <<= 5;
        info[1] |= calculateLicensePlate(vehicle.getHphm().substring(0, 1)); //取号牌号码第一位为发牌代号
        info[1] <<= 4;
        String syxz = vehicle.getSyxz();
        info[1] |= parseUsingPurpose(StringUtils.isEmpty(syxz) ? "" : syxz.trim());
        info[1] <<= 9;
        Calendar calendarInstance = Calendar.getInstance();
        if (vehicle.getCcrq() != null) {
            calendarInstance.setTime(vehicle.getCcrq());
            int numberOfMonths = (calendarInstance.get(Calendar.YEAR) - 1990) * 12 + calendarInstance.get(Calendar.MONTH);
            numberOfMonths = numberOfMonths >= 511 ? 511 :
                    ((numberOfMonths < 0 ? ((-numberOfMonths / 511) + 1) * 511 + numberOfMonths : numberOfMonths) % 511);
            info[1] |= numberOfMonths;
        }
        info[1] <<= 9;
        info[1] |= parseVehicleType(vehicle.getCllx()).getDmz2();
        int plOrGl = 0; //排量/功率标志位，0-排量，1-功率
        info[1] <<= 1;
        if (vehicle.getPl() == null || vehicle.getPl() == 0 || vehicle.getPl() == 1) {
            plOrGl = 1;
        }
        //test
        info[1] |= plOrGl;
        info[1] <<= 1;
        info[1] &= 0xffffffff; //删除省份代码前3位
        String firstPartHex = Long.toHexString(info[0]);
        firstPartHex = EriUtil.appendZero(firstPartHex, 10);
        String secondPartHex = Long.toHexString(info[1]);
        secondPartHex = EriUtil.appendZero(secondPartHex, 8);
        String upperPartHex = firstPartHex + secondPartHex;
        LOG.debug("上半区内容:{}", upperPartHex);
        eriCustomizeContent.setUser0sbq(upperPartHex);
        //车辆信息下半区一共80位，也采用两个long型数值进行拼装
        info = new long[] {0l, 0l};
        //info[0]存储号牌种类（4）、号牌号码序号（32），共36位，
        info[0] |= parseLicenseType(vehicle.getHpzl());
        info[0] <<= 32;
        info[0] |= EriUtil.parseHPHMToLong(vehicle.getHphm().substring(1)); //去掉第一位的发牌代号
        //info[1]存储检验有效期（9）、强制报废期（5）、核定载客/总质量（10）、车身颜色（4）、排量（8）
        if (vehicle.getYxqz() != null) {
            calendarInstance.setTime(vehicle.getYxqz());
            int numberOfMonths = (calendarInstance.get(Calendar.YEAR) - 2013) * 12 + calendarInstance.get(Calendar.MONTH);
            numberOfMonths = numberOfMonths < 0 ? 511 : numberOfMonths % 511;
            info[1] |= numberOfMonths;
        }
        info[1] <<= 5;
        if (vehicle.getQzbfqz() != null) {
            Calendar bfq = Calendar.getInstance();
            bfq.setTime(vehicle.getQzbfqz());
            int numberOfYears = bfq.get(Calendar.YEAR) - calendarInstance.get(Calendar.YEAR);
            numberOfYears = numberOfYears > 0x1f ? 0x1f : numberOfYears;
            info[1] |= numberOfYears;
        }
        info[1] <<= 10;
        if (vehicle.getCllx().toUpperCase().startsWith("K")) {
            //客车填写核定载客
            if (vehicle.getHdzk() == null) throw new RuntimeException("核定载客为空");
            info[1] |= vehicle.getHdzk();
        } else {
            int zzl = VehicleUtil.getZzl(vehicle);
            if (zzl >= 102300) {
                info[1] |= 0x3ff;
            } else {
                info[1] |= Math.round(zzl / 100.0);
            }
        }
        info[1] <<= 4;
        if (!StringUtils.isEmpty(vehicle.getCsys())) {
            Code csysCode = gSysparaCodeService.getCode(CodeTableDefinition.VEHICLE_COLOR, vehicle.getCsys().substring(0, 1).toUpperCase());
            info[1] |= csysCode == null ? Constants.DEFAULT_VEHICLE_COLOR_VALUE : csysCode.getDmz2();
        }
        //排量/功率
        info[1] <<= 8;
        if (plOrGl == 0) {
            //排量
            if (vehicle.getPl() >= 25600) {
                info[1] |= 0xff;
            } else {
                info[1] |= Math.round(vehicle.getPl() / 100.0);
            }
        } else {
            if (vehicle.getGl() == null || vehicle.getGl() <= 1) {
                info[1] |= 0x00;
            } else if (vehicle.getGl() >= 255) {
                info[1] |= 0xff;
            } else {
                info[1] |= Math.round(vehicle.getGl());
            }
        }
        firstPartHex = Long.toHexString(info[0]);
        firstPartHex = EriUtil.appendZero(firstPartHex, 9);
        secondPartHex = Long.toHexString(info[1]);
        secondPartHex = EriUtil.appendZero(secondPartHex, 9);
        String lowerPartHex = firstPartHex + secondPartHex;
        LOG.debug("下半区内容：{}", lowerPartHex);
        eriCustomizeContent.setUser0xbq(lowerPartHex);
    }

    /**
     * @param province 省份代码
     * @param sequence 卡序号
     * @return
     */
    private String calculateHexCardNumber(int province, Long sequence) {
        long cardNumValue = 0; //卡号共37位，加上8位校验位，采用一个long型即可
        if(province ==56){
            cardNumValue|=1; // 民航车辆
        }else{
            cardNumValue |= EriType.BY_TMRI.getType(); //添加卡类型
        }

        cardNumValue <<= 6;
        cardNumValue |= province; //添加6位省份代码
        cardNumValue <<= 30;
        cardNumValue |= sequence;
        cardNumValue <<= 3; //补3位0

        String hexCardNum = EriUtil.appendZero(Long.toHexString(cardNumValue), 10);
        LOG.info(String.format("------> 二进制卡号 = %s", hexCardNum));
        return hexCardNum;
    }

    private String calculateBatchNumber(int province, boolean test) throws Exception {
        int batchValue = 0;
        batchValue |= test ? 0 : 1; //1-正式版本，0-调试版本
        batchValue <<= 4;
        batchValue |= Integer.valueOf(gSysparaCodeService.getSyspara("00", "2", "ERI_CSHBB").getMrz()); //版本号，当前版本1
        batchValue <<= 1;   //添加卡类型
        if(province ==56){
            batchValue|=1; // 民航车辆
        }else{
            batchValue |= EriType.BY_TMRI.getType(); // 添加卡类型 暂时写死
        }

        batchValue <<= 6;
        batchValue |= province; //添加省份值
        batchValue <<= 1;
        //以下代码将原使用9位顺序号，修改为1位标识位，8位顺序号，标识位用于区分逻辑功能，见具体的逻辑功能代码
        batchValue |= 1;
        batchValue <<= 8;
        Calendar calendarInstance = Calendar.getInstance();
        int y = calendarInstance.get(Calendar.YEAR);//today.getYear();

        int years = y-2015 ;//年 2015年为第一年

        if (years > 511) {
            throw new RuntimeException("电子标识批号最多支持512年,当前已超出使用寿命");
        }

        batchValue |= years; //添加年值

        String batchNumber = EriUtil.appendZero(Integer.toHexString(batchValue), 6);
        int batchCheckNum = CRC8.calculate(batchNumber);
        String batchCheckStr = Integer.toHexString(batchCheckNum);
        batchCheckStr = BinaryUtil.coverBefore(batchCheckStr, 2);
        String finalBatchNumber = (batchNumber + batchCheckStr).toUpperCase();
        LOG.info(String.format("------> 批号 = %s", finalBatchNumber));

        return finalBatchNumber;
    }

    @Override
    public boolean verifyCert(Cert cert) throws Exception {
        String jmfs = gSysparaCodeService.getSysParaValue("00", "JMFS");
        if ("2".equals(jmfs)) {
            return verifyCertByKMS(cert);
        }

        HsmApi safapi =getInstance();
        byte[] dataBytes = Bytes.hex2bytes(cert.getBody());
        LOG.info("cert.getBody={"+cert.getBody()+"}");
        LOG.info("cert.getSign={"+cert.getSign()+"}");
        SAFResult safResult = safapi.SAF_SM3_HASH(1, 0, null, null, dataBytes.length, dataBytes);
        if (safResult.getResultCode() == 0) {
            LOG.info("生成证书摘要成功");
        } else {
            LOG.error("生成证书摘要失败");
            throw new Exception("生成证书摘要失败");
        }

        byte[] summary = Bytes.hex2bytes(new String(safResult.getData()));
        LOG.info("摘要: " + EriUtil.bytesToHex(summary));
        int groups = summary.length / 4 - 1;
        byte[] newSummary = new byte[summary.length];
        for (int i = 0; i < summary.length; i++ ) {
            int j = i / 4;
            int k = i % 4;
            newSummary[(groups - j) * 4 + k] = summary[i];
        }
        LOG.info("摘要: " + EriUtil.bytesToHex(newSummary));
//        byte[] newSummaryls = new byte[newSummary.length];
//        for (int i = 0; i < 2; i++) {
//            for (int j = 0; j < 32; j++) {
//                newSummaryls[32 * i + j] = newSummary[32 * i + 31 - j];
//            }
//        }
        byte[] signBytes = Bytes.hex2bytes(cert.getSign());
        LOG.info("签名: " + EriUtil.bytesToHex(signBytes));
        byte[] newSignBytes = new byte[signBytes.length];
        if (signBytes.length != 64) throw new Exception("证书签名长度错误");
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 32; j++) {
                newSignBytes[32 * i + j] = signBytes[32 * i + 31 - j];
            }
        }
        LOG.info("签名: " + EriUtil.bytesToHex(newSignBytes));
        safResult = safapi.SAF_SM2PubKeyVerify(1, new byte[64], newSignBytes, newSummary.length, newSummary);
        if (safResult.getResultCode() == 0) {
            LOG.info("校验证书成功" + safResult.getResultCode());
        } else {
            LOG.error("校验证书失败" + safResult.getResultCode());
            return true;
        }
        return true;
    }

    public boolean verifyCertByKMS(Cert cert) throws Exception {
        byte[] dataBytes = Bytes.hex2bytes(cert.getBody());

        String digest = kmsService.SM3Digest(new BASE64Encoder().encode(dataBytes));
        byte[] summary = new BASE64Decoder().decodeBuffer(digest);
        LOG.info("摘要: " + summary);
        int groups = summary.length / 4 - 1;
        byte[] newSummary = new byte[summary.length];
        for (int i = 0; i < summary.length; i++ ) {
            int j = i / 4;
            int k = i % 4;
            newSummary[(groups - j) * 4 + k] = summary[i];
        }
        LOG.info("摘要: " + EriUtil.bytesToHex(newSummary));
        byte[] signBytes = Bytes.hex2bytes(cert.getSign());
        LOG.info("签名: " + EriUtil.bytesToHex(signBytes));
        byte[] newSignBytes = new byte[signBytes.length];
        if (signBytes.length != 64) throw new Exception("证书签名长度错误");
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 32; j++) {
                newSignBytes[32 * i + j] = signBytes[32 * i + 31 - j];
            }
        }
        LOG.info("签名: " + EriUtil.bytesToHex(newSignBytes));

        String xtyxms = gSysparaCodeService.getSysParaValue("00", "XTYXMS");
        boolean verifyResult = false;
        if ("1".equals(xtyxms)) {
            //科研所内系统使用内部全国根密钥校验
            verifyResult = kmsService.SM2Verify(1, new BASE64Encoder().encode(newSummary),
                    new BASE64Encoder().encode(newSignBytes));
        } else {
            //使用全国根证书校验，外部密钥
            String qggzs = gSysparaCodeService.getSysParaValue("00", "QGGZS");
            Cert qggzsCert = EriUtil.parseCert(qggzs);
            verifyResult = kmsService.SM2Verify(qggzsCert.getPubKey(), new BASE64Encoder().encode(newSummary),
                    new BASE64Encoder().encode(newSignBytes));
        }

        if (verifyResult) {
            LOG.info("校验证书成功");
            return true;
        } else {
            LOG.error("校验证书失败");
            return false;
        }
    }


    /**
     * 生成批密钥、单卡密钥、锁定口令、灭活口令
     * @param eriInitializeContent EriInitializeContent
     * @throws Exception
     */
    private void generateCipherCode(EriInitializeContent eriInitializeContent, String key) throws Exception {
        String jmfs = gSysparaCodeService.getSysParaValue("00", "JMFS");
        if ("2".equals(jmfs)) {
            generateCipherCodebyKMS(eriInitializeContent, key);
            return;
        }
        int protectedKeyIndex = 100,user0sbqEncryptAndDecryptKeyIndex = 101;
        try {
//            protectedKeyIndex = encryptorIndexService.fetch(configProperty.getEncryptorIdOfProtectedKey());
//            user0sbqEncryptAndDecryptKeyIndex = encryptorIndexService.fetch(configProperty.getEncryptorIdOfEncryptAndDecryptKey());
            //修改20180920，删除生成保护密钥步骤
            HsmApi safapi =getInstance();
//            safResult = api.SAF_TransKey_LMK2SM2_FULL(protectedKeyIndex, 2, 0, us.hex2bytes(sampubkey), 0, null);
            SAFResult safResult = safapi.SAF_TransKey_LMK2SM2_FULL(protectedKeyIndex, 2, 0,Bytes.hex2bytes(key), 0, null);
            if (safResult.getResultCode() == 0) {
                LOG.debug("加密导出保护密钥成功");
            } else {
                LOG.error("加密导出保护密钥失败:"+safResult.getResultCode());
                throw new Exception("加密导出保护密钥失败");
            }
            String protectKey = EriUtil.bytesToHex(Bytes.hex2bytes(new String(safResult.getData())));
            protectKey = protectKey.substring(192) + protectKey.substring(0, 192);
            eriInitializeContent.setProtectKey(protectKey);

            LOG.debug("保护密钥：" + protectKey);

            byte[] phBytes = Bytes.hex2bytes(eriInitializeContent.getPh()); //批号为8位16进制字符串
            int versionType = phBytes[0] >> 4;      //0为测试版，1为正式版
            int version = (phBytes[0] & 0x0F - 1) % 4;   //批号第一位为密钥版本号，当前不使用。
            byte[] content = new byte[3];
            //加密内容需为16个字节，将批号后6位转为3个字节，并倒序放置到16个字节的加密内容中去。
            for (int i = 0; i < 3; i++) {
                content[i] = phBytes[3 - i];
            }
            int phNo = phBytes[2] & 0xFF;

            String tid = eriInitializeContent.getTid().substring(2, 14);
            byte[] tidBytes = Bytes.hex2bytes(tid);
            byte[] reverseTidBytes = new byte[tidBytes.length];
            for (int i = 0; i < tidBytes.length; i++) {
                reverseTidBytes[tidBytes.length - 1 - i] = tidBytes[i];
            }
            String reverseTid = EriUtil.bytesToHex(reverseTidBytes);

            //算法标识1：SM1  2：SM4
            //密钥索引1  ---- 身份认证根密钥
            //分散次数   1-3
            //分散因子1
            //密钥索引2  ---- 身份认证根密钥
            //分散因子2长度，为分散因子的字节数
            //分散因子   0-16
            //起始位置   0-15
            //保护密钥索引
            //保护公钥，当保护密钥索引为0时有效
            safResult = safapi.SAF_DivKeyEx(1, 5 + version, 1, EriUtil.appendZero(EriUtil.bytesToHex(content), 32).getBytes(), 5 + version, 6, reverseTid.getBytes(), 10, protectedKeyIndex, null);

            byte[] pmyAndDkmy = safResult.getData();
            if (safResult.getResultCode() == 0) {
                String pmyAndDkmyHex = new String(pmyAndDkmy);
                eriInitializeContent.setPmy(pmyAndDkmyHex.substring(0, 32));
                eriInitializeContent.setDkmy(pmyAndDkmyHex.substring(32));
                LOG.debug("生成批密钥单卡密钥成功(对称加密后)：" + pmyAndDkmyHex);
            } else {
                LOG.debug("生成批密钥单卡密钥失败(" + safResult.getResultCode() + ")");
                throw new Exception("生成批密钥单卡密钥失败");
            }

            //锁定口令, TID去头去尾，倒序，补0，16字节
            byte[] reverseTidAfterAppendZeroBytes = EriUtil.appendZero(reverseTid, 32).getBytes();
            safResult = safapi.SAF_Div_SM1AndSM4(2, 1, 13 + version, 1, reverseTidAfterAppendZeroBytes, protectedKeyIndex, null, 0);
            if (safResult.getResultCode() == 0) {
                LOG.debug("生成锁定口令成功(加密后)：" + new String(safResult.getData()));
                eriInitializeContent.setSdkl(new String(safResult.getData()));
            } else {
                LOG.debug("生成锁定口令失败(" + safResult.getResultCode() + ")");
                throw new RuntimeException("生成锁定口令失败");
            }
            //灭活口令
            safResult = safapi.SAF_Div_SM1AndSM4(2, 1, 9 + version, 1, reverseTidAfterAppendZeroBytes, protectedKeyIndex, null, 0);
            if (safResult.getResultCode() == 0) {
                LOG.debug("生成灭活口令成功：" + new String(safResult.getData()));
                eriInitializeContent.setMhkl(new String(safResult.getData()));
            } else {
                LOG.debug("生成灭活口令失败(" + safResult.getResultCode() + ")");
                throw new RuntimeException("生成灭活口令失败");
            }

            //USER0分区写口令
            /**
             * 2016-04-25 初始化立即写入地方写口令,并将scgxhrq更新表示已写入地方写口令
             safResult = safapi.SAF_Div_SM1AndSM4(2, 1, 33 + version, 1, reverseTidAfterAppendZeroBytes, protectedKeyIndex, null, 0);
             if (safResult.getResultCode() == 0) {
             LOG.debug("生成USER0区写口令成功：" + new String(safResult.getData()));
             eriInitializeContent.setU0xkl(new String(safResult.getData()));
             } else {
             LOG.debug("生成USER0区写口令失败(" + safResult.getResultCode() + ")");
             throw new RuntimeException("生成USER0区写口令失败");
             }*/
            //生成新的USER0写口令,2016-11-15修改使用全国根密钥
            safResult = safapi.SAF_Div_SM1AndSM4(2, 1, 21 + version, 1, reverseTidAfterAppendZeroBytes, protectedKeyIndex, null, 0);
            if (safResult.getResultCode() == 0) {
                LOG.debug("生成USER0区写口令成功" + new String(safResult.getData()));
            } else {
                LOG.error("生成USER0区写口令成功(" + safResult.getResultCode() + ")");
                throw new RuntimeException("生成USER0区写口令成功");
            }
            eriInitializeContent.setU0xkl(new String(safResult.getData()));

            //USER1分区写口令
            safResult = safapi.SAF_Div_SM1AndSM4(2, 1, 45 + version, 1, reverseTidAfterAppendZeroBytes, protectedKeyIndex, null, 0);
            if (safResult.getResultCode() == 0) {
                LOG.debug("生成USER1区写口令成功：" + new String(safResult.getData()));
                eriInitializeContent.setU1xkl(new String(safResult.getData()));
            } else {
                LOG.debug("生成USER1区写口令失败(" + safResult.getResultCode() + ")");
                throw new RuntimeException("生成USER0区写口令失败");
            }

            //USER2分区写口令
            safResult = safapi.SAF_Div_SM1AndSM4(2, 1, 53 + version, 1, reverseTidAfterAppendZeroBytes, protectedKeyIndex, null, 0);
            if (safResult.getResultCode() == 0) {
                LOG.debug("生成USER2区写口令成功：" + new String(safResult.getData()));
                eriInitializeContent.setU2xkl(new String(safResult.getData()));
            } else {
                LOG.debug("生成USER2区写口令失败(" + safResult.getResultCode() + ")");
                throw new RuntimeException("生成USER0区写口令失败");
            }
            //USER3分区写口令
            safResult = safapi.SAF_Div_SM1AndSM4(2, 1, 61 + version, 1, reverseTidAfterAppendZeroBytes, protectedKeyIndex, null, 0);
            if (safResult.getResultCode() == 0) {
                LOG.debug("生成USER3区写口令成功：" + new String(safResult.getData()));
                eriInitializeContent.setU3xkl(new String(safResult.getData()));
            } else {
                LOG.debug("生成USER3区写口令失败(" + safResult.getResultCode() + ")");
                throw new RuntimeException("生成USER0区写口令失败");
            }
            //USER4分区写口令
            safResult = safapi.SAF_Div_SM1AndSM4(2, 1, 69 + version, 1, reverseTidAfterAppendZeroBytes, protectedKeyIndex, null, 0);
            if (safResult.getResultCode() == 0) {
                LOG.debug("生成USER4区写口令成功：" + new String(safResult.getData()));
                eriInitializeContent.setU4xkl(new String(safResult.getData()));
            } else {
                LOG.debug("生成USER4区写口令失败(" + safResult.getResultCode() + ")");
                throw new RuntimeException("生成USER0区写口令失败");
            }
            //USER5分区写口令
            safResult = safapi.SAF_Div_SM1AndSM4(2, 1, 77 + version, 1, reverseTidAfterAppendZeroBytes, protectedKeyIndex, null, 0);
            if (safResult.getResultCode() == 0) {
                LOG.debug("生成USER5区写口令成功：" + new String(safResult.getData()));
                eriInitializeContent.setU5xkl(new String(safResult.getData()));
            } else {
                LOG.debug("生成USER5区写口令失败(" + safResult.getResultCode() + ")");
                throw new RuntimeException("生成USER0区写口令失败");
            }

            if (phNo > 1) {
                //2017年以后所有卡初始化时写入User1345读口令
                safResult = safapi.SAF_Div_SM1AndSM4(2, 1, 41 + version, 1, reverseTidAfterAppendZeroBytes, protectedKeyIndex, null, 0);
                if (safResult.getResultCode() == 0) {
                    LOG.debug("生成USER1区读口令成功" + new String(safResult.getData()));
                } else {
                    LOG.error("生成USER1区读口令失败(" + safResult.getResultCode() + ")");
                    throw new RuntimeException("生成USER1区读口令失败");
                }
                eriInitializeContent.setU1dkl(new String(safResult.getData()));

                safResult = safapi.SAF_Div_SM1AndSM4(2, 1, 57 + version, 1, reverseTidAfterAppendZeroBytes, protectedKeyIndex, null, 0);
                if (safResult.getResultCode() == 0) {
                    LOG.debug("生成USER3区读口令成功" + new String(safResult.getData()));
                } else {
                    LOG.error("生成USER3区读口令失败(" + safResult.getResultCode() + ")");
                    throw new RuntimeException("生成USER3区读口令失败");
                }
                eriInitializeContent.setU3dkl(new String(safResult.getData()));

                safResult = safapi.SAF_Div_SM1AndSM4(2, 1, 65 + version, 1, reverseTidAfterAppendZeroBytes, protectedKeyIndex, null, 0);
                if (safResult.getResultCode() == 0) {
                    LOG.debug("生成USER4区读口令成功" + new String(safResult.getData()));
                } else {
                    LOG.error("生成USER4区读口令失败(" + safResult.getResultCode() + ")");
                    throw new RuntimeException("生成USER4区读口令失败");
                }
                eriInitializeContent.setU4dkl(new String(safResult.getData()));

                safResult = safapi.SAF_Div_SM1AndSM4(2, 1, 73 + version, 1, reverseTidAfterAppendZeroBytes, protectedKeyIndex, null, 0);
                if (safResult.getResultCode() == 0) {
                    LOG.debug("生成USER5区读口令成功" + new String(safResult.getData()));
                } else {
                    LOG.error("生成USER5区读口令失败(" + safResult.getResultCode() + ")");
                    throw new RuntimeException("生成USER5区读口令失败");
                }
                eriInitializeContent.setU5dkl(new String(safResult.getData()));
            }

            //分散USER0上半区加解密密钥
            //Mode - 模式，1-分散后公钥加密导出，2-分散后对称加密导出，3-分散后保存到密码机指定索引
            //AlgMode - 算法标识，1-SM1, 2-SM4
            //DivKeyIndex - 要分散的密钥索引
            //DivNum - 分散次数
            //DivData - 分因子数据，数据长度为DivNum*32
            //ProtectKeyIndex - 保护密钥索引，当Mode=2时有效
            //ProtectPubKey - 保护公钥，当Mode=1时有效
            //SaveIndex - 分散后保存索引号，当Mode=3时有效
            /*
            safResult = safapi.SAF_Div_SM1AndSM4(3, 1, 25 + version, 1, reverseTidAfterAppendZeroBytes, 0, null, user0sbqEncryptAndDecryptKeyIndex); //对USER0上半区加解密根密钥进行分散，保存到70号索引
            if (safResult.getResultCode() == 0) {
                LOG.debug("分散USER0上半区加解密密钥成功");
            } else {
                LOG.debug("分散USER0上半区加解密密钥失败");
                throw new RuntimeException("分散USER0上半区加解密密钥失败");
            }*/
            //CFB加密，使用TID为初始向量
            byte[] iv = new byte[16];
            for (int i = 0; i < 6; i++) {
                iv[15 - i] = tidBytes[i];
                iv[5 - i] = tidBytes[i];
            }
            LOG.debug("加密卡号反向初始向量：" + Bytes.bytes2hex(iv));

            byte[] khBytes = Bytes.hex2bytes(EriUtil.appendZero(eriInitializeContent.getBkh(), 18, 2));
            LOG.debug("加密卡号：" + Bytes.bytes2hex(khBytes));

            /*byte[] reverseKhBytes = new byte[khBytes.length];
            for (int i = 0; i < khBytes.length; i++) {
                reverseKhBytes[i] = khBytes[khBytes.length - 1 - i];
            }

            LOG.debug("加密反向卡号：" + Bytes.bytes2hex(reverseKhBytes));
            使用wuxian1.4.jar不需要再将加密数据进行倒置，加密接口会自动进行倒置
            */
            /*新修改20180920
            byte[] CFBEncryptResult = safapi.SAF_SM1CFB(1, user0sbqEncryptAndDecryptKeyIndex, iv, khBytes);           
            String encryptHexCardNum = EriUtil.bytesToHex(CFBEncryptResult);
            */
            String ivstr=Bytes.bytes2hex(iv);
            String ivstr1=ivstr.substring(0, ivstr.indexOf(","));
            safResult = safapi.SAF_SM1_SM4_EncAndDec(1,25 + version,null,0,1,reverseTidAfterAppendZeroBytes,1,4,ivstr1.getBytes(),1,khBytes.length,khBytes);
            if (safResult.getResultCode() == 0) {
                LOG.debug("加密卡号成功" + new String(safResult.getData()));

            } else {
                LOG.error("加密卡号失败(" + safResult.getResultCode() + ")");
                throw new RuntimeException("加密卡号失败");
            }
//            String encryptHexCardNum = EriUtil.bytesToHex(safResult.getData());
            String encryptHexCardNum = new String(safResult.getData());//修改20191120

            LOG.debug("加密卡号密文(使用CFB加密接口)：" + encryptHexCardNum);

            int checkNum = CRC8.calculate(encryptHexCardNum);
            String checkStr = Integer.toHexString(checkNum).toUpperCase();
            checkStr = BinaryUtil.coverBefore(checkStr, 2);
            eriInitializeContent.setBkh(encryptHexCardNum + checkStr);

            content = Arrays.copyOf(eriInitializeContent.getKh().getBytes(), 32);
            safResult = safapi.SAF_SM2PriKeySign(1, 32, content);
            LOG.debug(EriUtil.bytesToHex(safResult.getData()));

            byte[] signBytes = Bytes.hex2bytes(new String(safResult.getData()));
            String sign = BaseEncoding.base32().encode(signBytes).replaceAll("=", "");//20191120 change
//            String sign = BaseEncoding.base32().encode(safResult.getData()).replaceAll("=", "");
            LOG.debug("签名二维码：" + sign);
            eriInitializeContent.setTm(eriInitializeContent.getTm() + sign);

            composeInitializeDateFrame(eriInitializeContent);
            LOG.debug("数据帧为：" + eriInitializeContent.getFrame());

            byte[] frameData = Bytes.hex2bytes(eriInitializeContent.getFrame());
            if (frameData.length % 2 > 0) {
                LOG.error("数据区长度为奇数");
                throw new RuntimeException("数据区长度为奇数");
            }
            safResult = safapi.SAF_SM3_HASH(1, 0, null, null, frameData.length, frameData);
//            byte[] summary = safResult.getData();
            byte[] summary = Bytes.hex2bytes(new String(safResult.getData()));//20191120 change
            if (safResult.getResultCode() == 0) {
                LOG.debug("生成数据帧摘要成功：" + EriUtil.bytesToHex(summary));
            } else {
                LOG.error("生成证书摘要失败");
                throw new RuntimeException("生成证书摘要失败");
            }

            safResult = safapi.SAF_SM2PriKeySign(1 + version, summary.length, summary);
            if (safResult.getResultCode() == 0) {
                eriInitializeContent.setSign(new String(safResult.getData()));
                LOG.debug("对初始化数据进行签名成功,签名为：" + eriInitializeContent.getSign());
            } else {
                LOG.error("对初始化数据进行签名失败");
                throw new RuntimeException("对个性化数据进行签名失败");
            }
        }  finally {
            if (protectedKeyIndex > 0) {
//                encryptorIndexService.release(configProperty.getEncryptorIdOfProtectedKey(), protectedKeyIndex);
            }
            if (user0sbqEncryptAndDecryptKeyIndex > 0) {
//                encryptorIndexService.release(configProperty.getEncryptorIdOfEncryptAndDecryptKey(), user0sbqEncryptAndDecryptKeyIndex);
            }
        }
    }

    public Map generateCipherCodeForUpgrade(String tid, String certStr) throws Exception {
        Eri eri = fetchByTid(tid);
        if (eri == null) {
            throw new OperationException("tid不存在");
        }
        return generateCipherCodeForUpgrade(eri, certStr);
    }

    public Map generateCipherCodeForUpgrade(Eri eri, String certStr) throws Exception {
        Cert cert = EriUtil.parseCert(certStr);
        Map<Object, Object> resultMap = new HashMap<Object, Object>();
        int protectedKeyIndex = -1,user0sbqEncryptAndDecryptKeyIndex = -1;
        try {
            protectedKeyIndex = encryptorIndexService.fetch(configProperty.getEncryptorIdOfProtectedKey());
            user0sbqEncryptAndDecryptKeyIndex = encryptorIndexService.fetch(configProperty.getEncryptorIdOfEncryptAndDecryptKey());
            //修改20180920，删除生成保护密钥步骤
            HsmApi safapi =getInstance();
            SAFResult safResult = safapi.SAF_TransKey_LMK2SM2(protectedKeyIndex, 0, Bytes.hex2bytes(cert.getPubKey()));
            if (safResult.getResultCode() == 0) {
                LOG.debug("加密导出保护密钥成功");
            } else {
                LOG.error("加密导出保护密钥失败");
                throw new Exception("加密导出保护密钥失败");
            }
            String protectKey = EriUtil.bytesToHex(safResult.getData());
            protectKey = protectKey.substring(192) + protectKey.substring(0, 192);
            resultMap.put("protectKey", protectKey);

            LOG.debug("保护密钥：" + protectKey);

            Code provinceCode = gSysparaCodeService.getCode("00", "0032", eri.getSf());
            int provinceValue = provinceCode.getDmz2().intValue();
            String ph = calculateBatchNumber(provinceValue, false);
            resultMap.put("ph", ph);
            String kh = eri.getKh();
            String bkh = calculateHexCardNumber(provinceValue, Long.valueOf(kh.substring(2)));
            byte[] phBytes = Bytes.hex2bytes(ph); //批号为8位16进制字符串
            int versionType = phBytes[0] >> 4;      //0为测试版，1为正式版
            int version = (phBytes[0] & 0x0F - 1) % 4;   //批号第一位为密钥版本号，当前不使用。
            byte[] content = new byte[3];
            //加密内容需为16个字节，将批号后6位转为3个字节，并倒序放置到16个字节的加密内容中去。
            for (int i = 0; i < 3; i++) {
                content[i] = phBytes[3 - i];
            }

            resultMap.put("tid", eri.getTid().toUpperCase());
            String tid = eri.getTid().substring(2, 14);
            byte[] tidBytes = Bytes.hex2bytes(tid);
            byte[] reverseTidBytes = new byte[tidBytes.length];
            for (int i = 0; i < tidBytes.length; i++) {
                reverseTidBytes[tidBytes.length - 1 - i] = tidBytes[i];
            }
            String reverseTid = EriUtil.bytesToHex(reverseTidBytes);

            //算法标识1：SM1  2：SM4
            //密钥索引1  ---- 身份认证根密钥
            //分散次数   1-3
            //分散因子1
            //密钥索引2  ---- 身份认证根密钥
            //分散因子2长度，为分散因子的字节数
            //分散因子   0-16
            //起始位置   0-15
            //保护密钥索引
            //保护公钥，当保护密钥索引为0时有效
            safResult = safapi.SAF_DivKeyEx(1, 5 + version, 1, EriUtil.appendZero(EriUtil.bytesToHex(content), 32).getBytes(), 5 + version, 6, reverseTid.getBytes(), 10, protectedKeyIndex, null);

            byte[] pmyAndDkmy = safResult.getData();
            if (safResult.getResultCode() == 0) {
                String pmyAndDkmyHex = new String(pmyAndDkmy);
                resultMap.put("pmy", pmyAndDkmyHex.substring(0, 32));
                resultMap.put("dkmy", pmyAndDkmyHex.substring(32));
                LOG.debug("生成批密钥单卡密钥成功(对称加密后)：" + pmyAndDkmyHex);
            } else {
                LOG.debug("生成批密钥单卡密钥失败(" + safResult.getResultCode() + ")");
                throw new Exception("生成批密钥单卡密钥失败");
            }

            //锁定口令, TID去头去尾，倒序，补0，16字节
            byte[] reverseTidAfterAppendZeroBytes = EriUtil.appendZero(reverseTid, 32).getBytes();
            safResult = safapi.SAF_Div_SM1AndSM4(2, 1, 13 + version, 1, reverseTidAfterAppendZeroBytes, protectedKeyIndex, null, 0);
            if (safResult.getResultCode() == 0) {
                LOG.debug("生成锁定口令成功(加密后)：" + new String(safResult.getData()));
                resultMap.put("sdkl", new String(safResult.getData()));
            } else {
                LOG.debug("生成锁定口令失败(" + safResult.getResultCode() + ")");
                throw new RuntimeException("生成锁定口令失败");
            }
            //灭活口令
            safResult = safapi.SAF_Div_SM1AndSM4(2, 1, 9 + version, 1, reverseTidAfterAppendZeroBytes, protectedKeyIndex, null, 0);
            if (safResult.getResultCode() == 0) {
                LOG.debug("生成灭活口令成功：" + new String(safResult.getData()));
                resultMap.put("mhkl", new String(safResult.getData()));
            } else {
                LOG.debug("生成灭活口令失败(" + safResult.getResultCode() + ")");
                throw new RuntimeException("生成灭活口令失败");
            }

            //USER0分区写口令
            /**
             * 2016-04-25 初始化立即写入地方写口令,并将scgxhrq更新表示已写入地方写口令
             safResult = safapi.SAF_Div_SM1AndSM4(2, 1, 33 + version, 1, reverseTidAfterAppendZeroBytes, protectedKeyIndex, null, 0);
             if (safResult.getResultCode() == 0) {
             LOG.debug("生成USER0区写口令成功：" + new String(safResult.getData()));
             eriInitializeContent.setU0xkl(new String(safResult.getData()));
             } else {
             LOG.debug("生成USER0区写口令失败(" + safResult.getResultCode() + ")");
             throw new RuntimeException("生成USER0区写口令失败");
             }*/
            //生成新的USER0写口令
            safResult = safapi.SAF_Div_SM1AndSM4(2, 1, 37 + version, 1, reverseTidAfterAppendZeroBytes, protectedKeyIndex, null, 0);
            if (safResult.getResultCode() == 0) {
                LOG.debug("生成USER0区写口令成功" + new String(safResult.getData()));
            } else {
                LOG.error("生成USER0区写口令成功(" + safResult.getResultCode() + ")");
                throw new RuntimeException("生成USER0区写口令成功");
            }
            resultMap.put("u0xkl", new String(safResult.getData()));

            //USER1分区写口令
            safResult = safapi.SAF_Div_SM1AndSM4(2, 1, 45 + version, 1, reverseTidAfterAppendZeroBytes, protectedKeyIndex, null, 0);
            if (safResult.getResultCode() == 0) {
                LOG.debug("生成USER1区写口令成功：" + new String(safResult.getData()));
                resultMap.put("u1xkl", new String(safResult.getData()));
            } else {
                LOG.debug("生成USER1区写口令失败(" + safResult.getResultCode() + ")");
                throw new RuntimeException("生成USER0区写口令失败");
            }

            //USER2分区写口令
            safResult = safapi.SAF_Div_SM1AndSM4(2, 1, 53 + version, 1, reverseTidAfterAppendZeroBytes, protectedKeyIndex, null, 0);
            if (safResult.getResultCode() == 0) {
                LOG.debug("生成USER2区写口令成功：" + new String(safResult.getData()));
                resultMap.put("u2xkl", new String(safResult.getData()));
            } else {
                LOG.debug("生成USER2区写口令失败(" + safResult.getResultCode() + ")");
                throw new RuntimeException("生成USER0区写口令失败");
            }
            //USER3分区写口令
            safResult = safapi.SAF_Div_SM1AndSM4(2, 1, 61 + version, 1, reverseTidAfterAppendZeroBytes, protectedKeyIndex, null, 0);
            if (safResult.getResultCode() == 0) {
                LOG.debug("生成USER3区写口令成功：" + new String(safResult.getData()));
                resultMap.put("u3xkl", new String(safResult.getData()));
            } else {
                LOG.debug("生成USER3区写口令失败(" + safResult.getResultCode() + ")");
                throw new RuntimeException("生成USER0区写口令失败");
            }
            //USER4分区写口令
            safResult = safapi.SAF_Div_SM1AndSM4(2, 1, 69 + version, 1, reverseTidAfterAppendZeroBytes, protectedKeyIndex, null, 0);
            if (safResult.getResultCode() == 0) {
                LOG.debug("生成USER4区写口令成功：" + new String(safResult.getData()));
                resultMap.put("u4xkl", new String(safResult.getData()));
            } else {
                LOG.debug("生成USER4区写口令失败(" + safResult.getResultCode() + ")");
                throw new RuntimeException("生成USER0区写口令失败");
            }
            //USER5分区写口令
            safResult = safapi.SAF_Div_SM1AndSM4(2, 1, 77 + version, 1, reverseTidAfterAppendZeroBytes, protectedKeyIndex, null, 0);
            if (safResult.getResultCode() == 0) {
                LOG.debug("生成USER5区写口令成功：" + new String(safResult.getData()));
                resultMap.put("u5xkl", new String(safResult.getData()));
            } else {
                LOG.debug("生成USER5区写口令失败(" + safResult.getResultCode() + ")");
                throw new RuntimeException("生成USER0区写口令失败");
            }

            //分散USER0上半区加解密密钥
            //Mode - 模式，1-分散后公钥加密导出，2-分散后对称加密导出，3-分散后保存到密码机指定索引
            //AlgMode - 算法标识，1-SM1, 2-SM4
            //DivKeyIndex - 要分散的密钥索引
            //DivNum - 分散次数
            //DivData - 分因子数据，数据长度为DivNum*32
            //ProtectKeyIndex - 保护密钥索引，当Mode=2时有效
            //ProtectPubKey - 保护公钥，当Mode=1时有效
            //SaveIndex - 分散后保存索引号，当Mode=3时有效
            /*修改20180920
            safResult = safapi.SAF_Div_SM1AndSM4(3, 1, 25 + version, 1, reverseTidAfterAppendZeroBytes, 0, null, user0sbqEncryptAndDecryptKeyIndex); //对USER0上半区加解密根密钥进行分散，保存到70号索引
            if (safResult.getResultCode() == 0) {
                LOG.debug("分散USER0上半区加解密密钥成功");
            } else {
                LOG.debug("分散USER0上半区加解密密钥失败");
                throw new RuntimeException("分散USER0上半区加解密密钥失败");
            }
            */
            //CFB加密，使用TID为初始向量
            byte[] iv = new byte[16];
            for (int i = 0; i < 6; i++) {
                iv[15 - i] = tidBytes[i];
                iv[5 - i] = tidBytes[i];
            }
            LOG.debug("加密卡号反向初始向量：" + Bytes.bytes2hex(iv));

            byte[] khBytes = Bytes.hex2bytes(EriUtil.appendZero(bkh, 18, 2));
            LOG.debug("加密卡号：" + Bytes.bytes2hex(khBytes));
						/*修改20180920
            byte[] CFBEncryptResult = safapi.SAF_SM1CFB(1, user0sbqEncryptAndDecryptKeyIndex, iv, khBytes);
            String encryptHexCardNum = EriUtil.bytesToHex(CFBEncryptResult);
            */
            String ivstr=Bytes.bytes2hex(iv);
            String ivstr1=ivstr.substring(0, ivstr.indexOf(","));
            safResult = safapi.SAF_SM1_SM4_EncAndDec(1,25 + version,null,0,1,reverseTidAfterAppendZeroBytes,1,4,ivstr1.getBytes(),1,khBytes.length,khBytes);
            if (safResult.getResultCode() == 0) {
                LOG.debug("加密卡号成功" + new String(safResult.getData()));

            } else {
                LOG.error("加密卡号失败(" + safResult.getResultCode() + ")");
                throw new RuntimeException("加密卡号失败");
            }
            String encryptHexCardNum = EriUtil.bytesToHex(safResult.getData());


            LOG.debug("加密卡号密文(使用CFB加密接口)：" + encryptHexCardNum);

            int checkNum = CRC8.calculate(encryptHexCardNum);
            String checkStr = Integer.toHexString(checkNum).toUpperCase();
            checkStr = BinaryUtil.coverBefore(checkStr, 2);
            resultMap.put("bkh", encryptHexCardNum + checkStr);

            String frame = composeUpgradeDataFrame(resultMap);
            LOG.debug("数据帧为：" + frame);

            resultMap.put("frame", frame);

            byte[] frameData = Bytes.hex2bytes(frame);
            if (frameData.length % 2 > 0) {
                LOG.error("数据区长度为奇数");
                throw new RuntimeException("数据区长度为奇数");
            }
            safResult = safapi.SAF_SM3_HASH(1, 0, null, null, frameData.length, frameData);
            byte[] summary = safResult.getData();
            if (safResult.getResultCode() == 0) {
                LOG.debug("生成数据帧摘要成功：" + EriUtil.bytesToHex(summary));
            } else {
                LOG.error("生成证书摘要失败");
                throw new RuntimeException("生成证书摘要失败");
            }

            safResult = safapi.SAF_SM2PriKeySign(10 + version, summary.length, summary);
            if (safResult.getResultCode() == 0) {
                String sign = EriUtil.bytesToHex(safResult.getData());
                resultMap.put("sign", sign);
                LOG.debug("对初始化数据进行签名成功,签名为：" + sign);
            } else {
                LOG.error("对初始化数据进行签名失败");
                throw new RuntimeException("对个性化数据进行签名失败");
            }
        }  finally {
            if (protectedKeyIndex > 0) {
                encryptorIndexService.release(configProperty.getEncryptorIdOfProtectedKey(), protectedKeyIndex);
            }
            if (user0sbqEncryptAndDecryptKeyIndex > 0) {
                encryptorIndexService.release(configProperty.getEncryptorIdOfEncryptAndDecryptKey(), user0sbqEncryptAndDecryptKeyIndex);
            }
        }
        return resultMap;
    }

    /**
     * 生成批密钥、单卡密钥、锁定口令、灭活口令
     * @param eriCustomizeContent 个性化信息
     * @param pubKey 公钥
     * @param create 是否为新申请,2016-04-13修改为始终为false,每张被发出去的卡都已经初始化过写口令了
     * @throws Exception
     */
    private void generateCipherCodeForCustomize(EriCustomizeContent eriCustomizeContent, String pubKey, boolean create) throws Exception {
        String jmfs = gSysparaCodeService.getSysParaValue("00", "JMFS");
        if ("2".equals(jmfs)) {
            generateCipherCodeForCustomizeByKMS(eriCustomizeContent, pubKey, create, true);
        } else {
            generateCipherCodeForCustomize(eriCustomizeContent, pubKey, create, true);
        }
    }

    /**
     * 生成批密钥、单卡密钥、锁定口令、灭活口令
     * @param eriCustomizeContent 个性化信息
     * @param pubKey 公钥
     * @param create 是否为新申请,2016-04-13修改为始终为false,每张被发出去的卡都已经初始化过写口令了
     * @param local 是否为本地发卡，默认为本地
     * @throws Exception
     */
    private void generateCipherCodeForCustomize(EriCustomizeContent eriCustomizeContent, String pubKey, boolean create, boolean local) throws Exception {
        int protectedKeyIndex = -1, encryptAndDecryptKeyIndex = -1, u0XklIndex = -1;
        try {
            protectedKeyIndex = encryptorIndexService.fetch(configProperty.getEncryptorIdOfProtectedKey());
            encryptAndDecryptKeyIndex = encryptorIndexService.fetch(configProperty.getEncryptorIdOfEncryptAndDecryptKey());
            byte[] phBytes = Bytes.hex2bytes(eriCustomizeContent.getPh());
            int versionType = phBytes[0] >> 4;      //0为测试版，1为正式版
            int version = phBytes[0] & 0x0F - 1;   //批号第一位为密钥版本号，当前不使用。
            boolean flag = (phBytes[1] & 0x01) > 0;
            //修改20180920，删除生成保护密钥步骤
            HsmApi safapi = getInstance();
            SAFResult safResult = safapi.SAF_TransKey_LMK2SM2(protectedKeyIndex, 0, Bytes.hex2bytes(pubKey));
            if (safResult.getResultCode() == 0) {
                LOG.debug("加密导出保护密钥成功");
            } else {
                LOG.error("加密导出保护密钥失败");
                throw new RuntimeException("加密导出保护密钥失败");
            }
            String protectKey = EriUtil.bytesToHex(safResult.getData());
            protectKey = protectKey.substring(192) + protectKey.substring(0, 192);
            eriCustomizeContent.setProtectKey(protectKey);

            String tid = eriCustomizeContent.getTid().substring(2, 14);
            byte[] tidBytes = Bytes.hex2bytes(tid);
            byte[] reverseTidBytes = new byte[tidBytes.length];
            for (int i = 0; i < tidBytes.length; i++) {
                reverseTidBytes[tidBytes.length - 1 - i] = tidBytes[i];
            }
            String reverseTid = EriUtil.bytesToHex(reverseTidBytes);
            byte[] reverseTidAfterAppendZeroBytes = EriUtil.appendZero(reverseTid, 32).getBytes();
            safResult = safapi.SAF_Div_SM1AndSM4(2, 1, 13 + version, 1, reverseTidAfterAppendZeroBytes, protectedKeyIndex, null, 0);
            if (safResult.getResultCode() == 0) {
                eriCustomizeContent.setSdkl(new String(safResult.getData()));
                LOG.debug("生成锁定口令成功(加密后)：" + eriCustomizeContent.getSdkl());
            } else {
                LOG.error("生成锁定口令失败(" + safResult.getResultCode() + ")");
                throw new RuntimeException("生成锁定口令失败");
            }
            //21-全国根密钥，33-全国临时根密钥，37-地市根密钥
            int user0xklIndex = (flag ? 21 : 37) + version;
            safResult = safapi.SAF_Div_SM1AndSM4(2, 1, user0xklIndex, 1, reverseTidAfterAppendZeroBytes, protectedKeyIndex, null, 0);
            if (safResult.getResultCode() == 0) {
                LOG.debug("生成USER0区写口令成功" + new String(safResult.getData()));
            } else {
                LOG.error("生成USER0区写口令成功(" + safResult.getResultCode() + ")");
                throw new RuntimeException("生成USER0区写口令成功");
            }
            if (create) {
                eriCustomizeContent.setU0xkl(new String(safResult.getData()));
                safResult = safapi.SAF_Div_SM1AndSM4(2, 1, 33 + version, 1, reverseTidAfterAppendZeroBytes, protectedKeyIndex, null, 0);
                if (safResult.getResultCode() == 0) {
                    eriCustomizeContent.setRootU0xkl(new String(safResult.getData()));
                    LOG.debug("生成USER0区写口令成功" + eriCustomizeContent.getRootU0xkl());
                } else {
                    LOG.error("生成USER0区写口令失败(" + safResult.getResultCode() + ")");
                    throw new RuntimeException("生成USER0区写口令失败");
                }
            } else {
                String originalU0xkl = new String(safResult.getData());
                eriCustomizeContent.setRootU0xkl(originalU0xkl);
                eriCustomizeContent.setU0xkl(originalU0xkl);
            }

            //分散USER0上半区加解密密钥
            //Mode - 模式，1-分散后公钥加密导出，2-分散后对称加密导出，3-分散后保存到密码机指定索引
            //AlgMode - 算法标识，1-SM1, 2-SM4
            //DivKeyIndex - 要分散的密钥索引
            //DivNum - 分散次数
            //DivData - 分因子数据，数据长度为DivNum*32
            //ProtectKeyIndex - 保护密钥索引，当Mode=2时有效
            //ProtectPubKey - 保护公钥，当Mode=1时有效
            //SaveIndex - 分散后保存索引号，当Mode=3时有效
            
            /*修改20180920
            safResult = safapi.SAF_Div_SM1AndSM4(3, 1, 25 + version, 1, reverseTidAfterAppendZeroBytes, 0, null, encryptAndDecryptKeyIndex); //对USER0上半区加解密根密钥进行分散
            if (safResult.getResultCode() == 0) {
                LOG.debug("分散USER0上半区加解密密钥成功");
            } else {
                LOG.error("分散USER0上半区加解密密钥失败");
                throw new RuntimeException("分散USER0上半区加解密密钥失败");
            }
            */

            //CFB加密，使用TID为初始向量
            byte[] iv = new byte[16];
            for (int i = 0; i < 6; i++) {
                iv[15 - i] = tidBytes[i];
                iv[5 - i] = tidBytes[i];
            }

            byte[] ivTmp = Arrays.copyOf(iv, 16);

            byte[] user0sbqBytes = Bytes.hex2bytes(eriCustomizeContent.getUser0sbq());

            /*byte[] reverseUser0sbqBytes = new byte[user0sbqBytes.length];
            for (int i = 0; i < user0sbqBytes.length; i++) {
                reverseUser0sbqBytes[i] = user0sbqBytes[user0sbqBytes.length - 1 - i];
            }
            使用wuxian1.4.jar不需要再将加密数据进行倒置，加密接口会自动进行倒置
            */
                        
            /*修改20180920
            byte[] CFBEncryptResult = safapi.SAF_SM1CFB(1, encryptAndDecryptKeyIndex, ivTmp, user0sbqBytes);
            String encryptHexUser0sbq = EriUtil.bytesToHex(CFBEncryptResult);
            */
            String ivstr=Bytes.bytes2hex(ivTmp);
            String ivstr1=ivstr.substring(0, ivstr.indexOf(","));
            safResult = safapi.SAF_SM1_SM4_EncAndDec(1,25 + version,null,0,1,reverseTidAfterAppendZeroBytes,1,4,ivstr1.getBytes(),1,user0sbqBytes.length,user0sbqBytes);
            if (safResult.getResultCode() == 0) {
                LOG.debug("加密上半区成功" + new String(safResult.getData()));

            } else {
                LOG.error("加密上半区失败(" + safResult.getResultCode() + ")");
                throw new RuntimeException("加密上半区失败");
            }
            String encryptHexUser0sbq = EriUtil.bytesToHex(safResult.getData());

            int checkNum = CRC8.calculate(encryptHexUser0sbq);
            String checkStr = Integer.toHexString(checkNum).toUpperCase();
            checkStr = BinaryUtil.coverBefore(checkStr, 2);
            eriCustomizeContent.setUser0sbq(encryptHexUser0sbq + checkStr);
            LOG.debug("加密上半区(使用CFB加密接口)：" + eriCustomizeContent.getUser0sbq());

            //分散USER0下半区加解密密钥
            //Mode - 模式，1-分散后公钥加密导出，2-分散后对称加密导出，3-分散后保存到密码机指定索引
            //AlgMode - 算法标识，1-SM1, 2-SM4
            //DivKeyIndex - 要分散的密钥索引
            //DivNum - 分散次数
            //DivData - 分因子数据，数据长度为DivNum*32
            //ProtectKeyIndex - 保护密钥索引，当Mode=2时有效
            //ProtectPubKey - 保护公钥，当Mode=1时有效
            //SaveIndex - 分散后保存索引号，当Mode=3时有效
            /*修改20180920
            safResult = safapi.SAF_Div_SM1AndSM4(3, 1, 29 + version, 1, reverseTidAfterAppendZeroBytes, 0, null, encryptAndDecryptKeyIndex); //对USER0上半区加解密根密钥进行分散，保存到70号索引
            if (safResult.getResultCode() == 0) {
                LOG.debug("分散USER0下半区加解密密钥成功");
            } else {
                LOG.error("分散USER0下半区加解密密钥失败");
                throw new RuntimeException("分散USER0下半区加解密密钥失败");
            }*/
            //CFB加密，使用TID为初始向量
            ivTmp = Arrays.copyOf(iv, 16);
            ivstr=Bytes.bytes2hex(ivTmp);
            ivstr1=ivstr.substring(0, ivstr.indexOf(","));
            byte[] user0xbqBytes = Bytes.hex2bytes(eriCustomizeContent.getUser0xbq());
            /*
            CFBEncryptResult = safapi.SAF_SM1CFB(1, encryptAndDecryptKeyIndex, ivTmp, user0xbqBytes);
            String encryptHexUser0xbq = EriUtil.bytesToHex(CFBEncryptResult);
            */
            safResult = safapi.SAF_SM1_SM4_EncAndDec(1,29 + version,null,0,1,reverseTidAfterAppendZeroBytes,1,4,ivstr1.getBytes(),1,user0xbqBytes.length,user0xbqBytes);
            if (safResult.getResultCode() == 0) {
                LOG.debug("加密下半区成功" + new String(safResult.getData()));

            } else {
                LOG.error("加密下半区失败(" + safResult.getResultCode() + ")");
                throw new RuntimeException("加密下半区失败");
            }
            String encryptHexUser0xbq = EriUtil.bytesToHex(safResult.getData());

            checkNum = CRC8.calculate(encryptHexUser0xbq);
            checkStr = Integer.toHexString(checkNum).toUpperCase();
            checkStr = BinaryUtil.coverBefore(checkStr, 2);
            eriCustomizeContent.setUser0xbq(encryptHexUser0xbq + checkStr);
            LOG.debug("加密下半区(使用CFB加密接口)：" + eriCustomizeContent.getUser0xbq());

            composeCustomizeDataFrame(eriCustomizeContent);
            LOG.debug("数据帧为：" + eriCustomizeContent.getFrame());

            byte[] frameData = Bytes.hex2bytes(eriCustomizeContent.getFrame());
            if (frameData.length % 2 > 0) {
                LOG.error("数据区长度为奇数");
                throw new RuntimeException("数据区长度为奇数");
            }
            safResult = safapi.SAF_SM3_HASH(1, 0, null, null, frameData.length, frameData);
            byte[] summary = safResult.getData();
            if (safResult.getResultCode() == 0) {
                LOG.debug("生成数据帧摘要成功：" + EriUtil.bytesToHex(summary));
            } else {
                LOG.error("生成证书摘要失败");
                throw new RuntimeException("生成证书摘要失败");
            }
            safResult = safapi.SAF_SM2PriKeySign(10 + version, summary.length, summary);
            if (safResult.getResultCode() == 0) {
                eriCustomizeContent.setSign(EriUtil.bytesToHex(safResult.getData()));
                LOG.debug("对个性化数据进行签名成功,签名为：" + eriCustomizeContent.getSign());
            } else {
                LOG.error("对个性化数据进行签名失败");
                throw new RuntimeException("对个性化数据进行签名失败");
            }
        } finally {
            if (protectedKeyIndex > 0) {
                encryptorIndexService.release(configProperty.getEncryptorIdOfProtectedKey(), protectedKeyIndex);
            }
            if (encryptAndDecryptKeyIndex > 0) {
                encryptorIndexService.release(configProperty.getEncryptorIdOfEncryptAndDecryptKey(), encryptAndDecryptKeyIndex);
            }
        }
    }

    public EriCustomizeContent modifyPassword(String tid, String content, String certStr, String sourceFactor, String destFactor) throws Exception{
        Cert cert = EriUtil.parseCert(certStr);
        if (!verifyCert(cert)) {
            throw new OperationException("01", "CUSTOMIZE", "校验证书失败");
        }
        Eri eri = fetchByTid(tid);
        EriCustomizeContent eriCustomizeContent = new EriCustomizeContent();
        eriCustomizeContent.setUser0sbq(content.substring(0, 18));
        if (content.length() > 20) {
            eriCustomizeContent.setUser0xbq(content.substring(20, 38));
        } else {
            eriCustomizeContent.setUser0xbq("000000000000000000");
        }
        eriCustomizeContent.setTid(tid);
        eriCustomizeContent.setKh(eri.getKh());
        eriCustomizeContent.setPh(eri.getPh());
        generateCipherCodeForModifyPassword(eriCustomizeContent, cert.getPubKey(), sourceFactor, destFactor);
        return eriCustomizeContent;
    }

    /**
     * 修改User0区写口令
     * @param eriCustomizeContent 个性化信息
     * @param pubKey 公钥
     * @param sourceFactor 原口令所属行政区划
     * @param destFactor 新口令所属行政区划
     * @throws Exception
     */
    private void generateCipherCodeForModifyPassword(EriCustomizeContent eriCustomizeContent, String pubKey, String sourceFactor, String destFactor) throws Exception {
        int protectedKeyIndex = -1, encryptAndDecryptKeyIndex = -1, u0XklIndex = -1;
        try {
            protectedKeyIndex = encryptorIndexService.fetch(configProperty.getEncryptorIdOfProtectedKey());
            encryptAndDecryptKeyIndex = encryptorIndexService.fetch(configProperty.getEncryptorIdOfEncryptAndDecryptKey());
            byte[] phBytes = Bytes.hex2bytes(eriCustomizeContent.getPh());
            int versionType = phBytes[0] >> 4;      //0为测试版，1为正式版
            int version = phBytes[0] & 0x0F - 1;   //批号第一位为密钥版本号，当前不使用。
            //修改20180920，删除生成保护密钥步骤
            HsmApi safapi = getInstance();
            SAFResult safResult = safapi.SAF_TransKey_LMK2SM2(protectedKeyIndex, 0, Bytes.hex2bytes(pubKey));
            if (safResult.getResultCode() == 0) {
                LOG.debug("加密导出保护密钥成功");
            } else {
                LOG.error("加密导出保护密钥失败");
                throw new RuntimeException("加密导出保护密钥失败");
            }

            String protectKey = EriUtil.bytesToHex(safResult.getData());
            protectKey = protectKey.substring(192) + protectKey.substring(0, 192);
            eriCustomizeContent.setProtectKey(protectKey);

            String tid = eriCustomizeContent.getTid().substring(2, 14);
            byte[] tidBytes = Bytes.hex2bytes(tid);
            byte[] reverseTidBytes = new byte[tidBytes.length];
            for (int i = 0; i < tidBytes.length; i++) {
                reverseTidBytes[tidBytes.length - 1 - i] = tidBytes[i];
            }
            String reverseTid = EriUtil.bytesToHex(reverseTidBytes);
            byte[] reverseTidAfterAppendZeroBytes = EriUtil.appendZero(reverseTid, 32).getBytes();

            safResult = safapi.SAF_Div_SM1AndSM4(2, 1, 13 + version, 1, reverseTidAfterAppendZeroBytes, protectedKeyIndex, null, 0);
            if (safResult.getResultCode() == 0) {
                eriCustomizeContent.setSdkl(new String(safResult.getData()));
                LOG.debug("生成锁定口令成功(加密后)：" + eriCustomizeContent.getSdkl());
            } else {
                LOG.error("生成锁定口令失败(" + safResult.getResultCode() + ")");
                throw new RuntimeException("生成锁定口令失败");
            }

            safResult = safapi.SAF_Div_SM1AndSM4(3, 1, 21, 1,
                    EriUtil.appendZero(sourceFactor, 32, 2).getBytes(), 0, null, encryptAndDecryptKeyIndex);
            if (safResult.getResultCode() == 0) {
                LOG.debug("分散原User0写口令根密钥成功");
            } else {
                LOG.error("分散原User0写口令根密钥失败(" + safResult.getResultCode() + ")");
                throw new RuntimeException("分散原User0写口令根密钥失败");
            }

            //生成原USER0写口令
            safResult = safapi.SAF_Div_SM1AndSM4(2, 1, encryptAndDecryptKeyIndex, 1, reverseTidAfterAppendZeroBytes, protectedKeyIndex, null, 0);
            if (safResult.getResultCode() == 0) {
                eriCustomizeContent.setRootU0xkl(new String(safResult.getData()));
                LOG.debug("生成原USER0区写口令成功" + eriCustomizeContent.getRootU0xkl());
            } else {
                LOG.error("生成原USER0区写口令失败(" + safResult.getResultCode() + ")");
                throw new RuntimeException("生成原USER0区写口令失败");
            }

            safResult = safapi.SAF_Div_SM1AndSM4(3, 1, 21, 1,
                    EriUtil.appendZero(destFactor, 32, 2).getBytes(), 0, null, encryptAndDecryptKeyIndex);
            if (safResult.getResultCode() == 0) {
                LOG.debug("分散新User0写口令根密钥成功");
            } else {
                LOG.error("分散新User0写口令根密钥失败(" + safResult.getResultCode() + ")");
                throw new RuntimeException("分散新User0写口令根密钥失败");
            }

            //生成新USER0写口令
            safResult = safapi.SAF_Div_SM1AndSM4(2, 1, encryptAndDecryptKeyIndex, 1, reverseTidAfterAppendZeroBytes, protectedKeyIndex, null, 0);
            if (safResult.getResultCode() == 0) {
                eriCustomizeContent.setU0xkl(new String(safResult.getData()));
                LOG.debug("生成USER0区新写口令成功" + eriCustomizeContent.getU0xkl());
            } else {
                LOG.error("生成USER0区新写口令失败(" + safResult.getResultCode() + ")");
                throw new RuntimeException("生成USER0区新写口令失败");
            }


            //分散USER0上半区加解密密钥
            //Mode - 模式，1-分散后公钥加密导出，2-分散后对称加密导出，3-分散后保存到密码机指定索引
            //AlgMode - 算法标识，1-SM1, 2-SM4
            //DivKeyIndex - 要分散的密钥索引
            //DivNum - 分散次数
            //DivData - 分因子数据，数据长度为DivNum*32
            //ProtectKeyIndex - 保护密钥索引，当Mode=2时有效
            //ProtectPubKey - 保护公钥，当Mode=1时有效
            //SaveIndex - 分散后保存索引号，当Mode=3时有效
            /*
            safResult = safapi.SAF_Div_SM1AndSM4(3, 1, 25 + version, 1, reverseTidAfterAppendZeroBytes, 0, null, encryptAndDecryptKeyIndex); //对USER0上半区加解密根密钥进行分散
            if (safResult.getResultCode() == 0) {
                LOG.debug("分散USER0上半区加解密密钥成功");
            } else {
                LOG.error("分散USER0上半区加解密密钥失败");
                throw new RuntimeException("分散USER0上半区加解密密钥失败");
            }
            */
            //CFB加密，使用TID为初始向量
            byte[] iv = new byte[16];
            for (int i = 0; i < 6; i++) {
                iv[15 - i] = tidBytes[i];
                iv[5 - i] = tidBytes[i];
            }

            byte[] ivTmp = Arrays.copyOf(iv, 16);
            String ivstr=Bytes.bytes2hex(ivTmp);
            String ivstr1=ivstr.substring(0, ivstr.indexOf(","));
            byte[] user0sbqBytes = Bytes.hex2bytes(eriCustomizeContent.getUser0sbq());
						/*
            byte[] CFBEncryptResult = safapi.SAF_SM1CFB(1, encryptAndDecryptKeyIndex, ivTmp, user0sbqBytes);
            String encryptHexUser0sbq = EriUtil.bytesToHex(CFBEncryptResult);
            */
            safResult = safapi.SAF_SM1_SM4_EncAndDec(1,25 + version,null,0,1,reverseTidAfterAppendZeroBytes,1,4,ivstr1.getBytes(),1,user0sbqBytes.length,user0sbqBytes);
            if (safResult.getResultCode() == 0) {
                eriCustomizeContent.setU0xkl(new String(safResult.getData()));
                LOG.debug("生成USER0上半区密文成功" + eriCustomizeContent.getU0xkl());
            } else {
                LOG.error("生成USER0上半区密文失败(" + safResult.getResultCode() + ")");
                throw new RuntimeException("生成USER上半区密文失败");
            }
            String encryptHexUser0sbq = EriUtil.bytesToHex(safResult.getData());

            int checkNum = CRC8.calculate(encryptHexUser0sbq);
            String checkStr = Integer.toHexString(checkNum).toUpperCase();
            checkStr = BinaryUtil.coverBefore(checkStr, 2);
            eriCustomizeContent.setUser0sbq(encryptHexUser0sbq + checkStr);
            LOG.debug("加密上半区(使用CFB加密接口)：" + eriCustomizeContent.getUser0sbq());

            //分散USER0下半区加解密密钥
            //Mode - 模式，1-分散后公钥加密导出，2-分散后对称加密导出，3-分散后保存到密码机指定索引
            //AlgMode - 算法标识，1-SM1, 2-SM4
            //DivKeyIndex - 要分散的密钥索引
            //DivNum - 分散次数
            //DivData - 分因子数据，数据长度为DivNum*32
            //ProtectKeyIndex - 保护密钥索引，当Mode=2时有效
            //ProtectPubKey - 保护公钥，当Mode=1时有效
            //SaveIndex - 分散后保存索引号，当Mode=3时有效
            /*
            safResult = safapi.SAF_Div_SM1AndSM4(3, 1, 29 + version, 1, reverseTidAfterAppendZeroBytes, 0, null, encryptAndDecryptKeyIndex); //对USER0上半区加解密根密钥进行分散，保存到70号索引
            if (safResult.getResultCode() == 0) {
                LOG.debug("分散USER0下半区加解密密钥成功");
            } else {
                LOG.error("分散USER0下半区加解密密钥失败");
                throw new RuntimeException("分散USER0下半区加解密密钥失败");
            }
            */
            //CFB加密，使用TID为初始向量
            ivTmp = Arrays.copyOf(iv, 16);
            ivstr=Bytes.bytes2hex(ivTmp);
            ivstr1=ivstr.substring(0, ivstr.indexOf(","));
            byte[] user0xbqBytes = Bytes.hex2bytes(eriCustomizeContent.getUser0xbq());
            /*
            CFBEncryptResult = safapi.SAF_SM1CFB(1, encryptAndDecryptKeyIndex, ivTmp, user0xbqBytes);
            String encryptHexUser0xbq = EriUtil.bytesToHex(CFBEncryptResult);
            */
            safResult = safapi.SAF_SM1_SM4_EncAndDec(1,29 + version,null,0,1,reverseTidAfterAppendZeroBytes,1,4,ivstr1.getBytes(),1,user0xbqBytes.length,user0xbqBytes);
            if (safResult.getResultCode() == 0) {
                eriCustomizeContent.setU0xkl(new String(safResult.getData()));
                LOG.debug("生成USER0下半区密文成功" + eriCustomizeContent.getU0xkl());
            } else {
                LOG.error("生成USER0下半区密文失败(" + safResult.getResultCode() + ")");
                throw new RuntimeException("生成USER下半区密文失败");
            }
            String encryptHexUser0xbq =EriUtil.bytesToHex(safResult.getData());

            checkNum = CRC8.calculate(encryptHexUser0xbq);
            checkStr = Integer.toHexString(checkNum).toUpperCase();
            checkStr = BinaryUtil.coverBefore(checkStr, 2);
            eriCustomizeContent.setUser0xbq(encryptHexUser0xbq + checkStr);
            LOG.debug("加密下半区(使用CFB加密接口)：" + eriCustomizeContent.getUser0xbq());

            composeCustomizeDataFrame(eriCustomizeContent);
            LOG.debug("数据帧为：" + eriCustomizeContent.getFrame());

            byte[] frameData = Bytes.hex2bytes(eriCustomizeContent.getFrame());
            if (frameData.length % 2 > 0) {
                LOG.error("数据区长度为奇数");
                throw new RuntimeException("数据区长度为奇数");
            }
            safResult = safapi.SAF_SM3_HASH(1, 0, null, null, frameData.length, frameData);
            byte[] summary = safResult.getData();
            if (safResult.getResultCode() == 0) {
                LOG.debug("生成数据帧摘要成功：" + EriUtil.bytesToHex(summary));
            } else {
                LOG.error("生成证书摘要失败");
                throw new RuntimeException("生成证书摘要失败");
            }

            safResult = safapi.SAF_SM2PriKeySign(10 + version, summary.length, summary);
            if (safResult.getResultCode() == 0) {
                eriCustomizeContent.setSign(EriUtil.bytesToHex(safResult.getData()));
                LOG.debug("对个性化数据进行签名成功,签名为：" + eriCustomizeContent.getSign());
            } else {
                LOG.error("对个性化数据进行签名失败");
                throw new RuntimeException("对个性化数据进行签名失败");
            }
        } finally {
            if (protectedKeyIndex > 0) {
                encryptorIndexService.release(configProperty.getEncryptorIdOfProtectedKey(), protectedKeyIndex);
            }
            if (encryptAndDecryptKeyIndex > 0) {
                encryptorIndexService.release(configProperty.getEncryptorIdOfEncryptAndDecryptKey(), encryptAndDecryptKeyIndex);
            }
        }
    }

    /**
     * 生成批密钥、单卡密钥、锁定口令、灭活口令
     * @param eriInitializeContent EriInitializeContent
     * @throws Exception
     */
    private void generateCipherCodebyKMS(EriInitializeContent eriInitializeContent, String pubKey) throws Exception {
        int protectedKeyIndex = -1,user0sbqEncryptAndDecryptKeyIndex = -1;
        try {
            protectedKeyIndex = encryptorIndexService.fetch(configProperty.getEncryptorIdOfProtectKeyByKMS());
            user0sbqEncryptAndDecryptKeyIndex = encryptorIndexService.fetch(configProperty.getEncryptorIdOfEncryptAndDecryptKeyByKMS());

            byte[] phBytes = Bytes.hex2bytes(eriInitializeContent.getPh()); //批号为8位16进制字符串
            int versionType = phBytes[0] >> 4;      //0为测试版，1为正式版
            int version = (phBytes[0] & 0x0F - 1) % 4 + 1;   //批号第一位为密钥版本号，当前不使用。
            byte[] content = new byte[3];
            //加密内容需为16个字节，将批号后6位转为3个字节，并倒序放置到16个字节的加密内容中去。
            for (int i = 0; i < 3; i++) {
                content[i] = phBytes[3 - i];
            }
            int phNo = phBytes[2] & 0xFF;

            String protectKey = kmsService.createTmpProKey(version,
                    new BASE64Encoder().encode(Bytes.hex2bytes(pubKey)), protectedKeyIndex);
            protectKey = protectKey.substring(192) + protectKey.substring(0, 192);
            eriInitializeContent.setProtectKey(protectKey);

            String tid = eriInitializeContent.getTid().substring(2, 14);
            byte[] tidBytes = Bytes.hex2bytes(tid);
            byte[] reverseTidBytes = new byte[tidBytes.length];
            for (int i = 0; i < tidBytes.length; i++) {
                reverseTidBytes[tidBytes.length - 1 - i] = tidBytes[i];
            }
            String reverseTid = EriUtil.bytesToHex(reverseTidBytes);

            String pmyAndDkmy = EriUtil.base64ToHex(kmsService.generatePMYAndDKMY(version, protectedKeyIndex,
                    new BASE64Encoder().encode(Bytes.hex2bytes(EriUtil.appendZero(EriUtil.bytesToHex(content), 32))),
                    new BASE64Encoder().encode(Bytes.hex2bytes(reverseTid)), 10));
            LOG.info("批密钥单卡密钥：" + pmyAndDkmy);
            eriInitializeContent.setPmy(pmyAndDkmy.substring(0, 32));
            eriInitializeContent.setDkmy(pmyAndDkmy.substring(32));

            eriInitializeContent.setSdkl(kmsService.generateLockPassword(version, reverseTid, protectedKeyIndex ));
            eriInitializeContent.setMhkl(kmsService.generateActivatePassword(version, reverseTid, protectedKeyIndex));
            //生成新的USER0写口令,2016-11-15修改使用全国根密钥
            eriInitializeContent.setU0xkl(kmsService.generateUserWritePassword(version, 0, reverseTid, protectedKeyIndex));
            eriInitializeContent.setU1xkl(kmsService.generateUserWritePassword(version, 1, reverseTid, protectedKeyIndex));
            eriInitializeContent.setU2xkl(kmsService.generateUserWritePassword(version, 2, reverseTid, protectedKeyIndex));
            eriInitializeContent.setU3xkl(kmsService.generateUserWritePassword(version, 3, reverseTid, protectedKeyIndex));
            eriInitializeContent.setU4xkl(kmsService.generateUserWritePassword(version, 4, reverseTid, protectedKeyIndex));
            eriInitializeContent.setU5xkl(kmsService.generateUserWritePassword(version, 5, reverseTid, protectedKeyIndex));

            if (phNo > 1) {
                eriInitializeContent.setU1dkl(kmsService.generateUserReadPassword(version, 1, reverseTid, protectedKeyIndex));
                eriInitializeContent.setU3dkl(kmsService.generateUserReadPassword(version, 3, reverseTid, protectedKeyIndex));
                eriInitializeContent.setU4dkl(kmsService.generateUserReadPassword(version, 4, reverseTid, protectedKeyIndex));
                eriInitializeContent.setU5dkl(kmsService.generateUserReadPassword(version, 5, reverseTid, protectedKeyIndex));
            }

            kmsService.generateUserEncryptPassword(version, 0, true, reverseTid, user0sbqEncryptAndDecryptKeyIndex);

            //CFB加密，使用TID为初始向量
            byte[] iv = new byte[16];
            for (int i = 0; i < 6; i++) {
                iv[15 - i] = tidBytes[i];
                iv[5 - i] = tidBytes[i];
            }
            LOG.debug("加密卡号反向初始向量：" + Bytes.bytes2hex(iv));

            byte[] khBytes = Bytes.hex2bytes(EriUtil.appendZero(eriInitializeContent.getBkh(), 18, 2));
            LOG.debug("加密卡号：" + Bytes.bytes2hex(khBytes));

            String encryptHexCardNum = kmsService.CFBEncrypt(user0sbqEncryptAndDecryptKeyIndex, true, EriUtil.bytesToHex(iv), new BASE64Encoder().encode(khBytes));
            LOG.debug("加密卡号密文(使用CFB加密接口)：" + encryptHexCardNum);
            int checkNum = CRC8.calculate(encryptHexCardNum);
            String checkStr = Integer.toHexString(checkNum).toUpperCase();
            checkStr = BinaryUtil.coverBefore(checkStr, 2);
            eriInitializeContent.setBkh(encryptHexCardNum + checkStr);

            content = Arrays.copyOf(eriInitializeContent.getKh().getBytes(), 32);
            String sign = BaseEncoding.base32().encode(
                    new BASE64Decoder().decodeBuffer(kmsService.SM2Sign(version, 1, "", new BASE64Encoder().encode(content))))
                    .replaceAll("=", "");
            LOG.debug("签名二维码：" + sign);
            eriInitializeContent.setTm(eriInitializeContent.getTm() + sign);

            composeInitializeDateFrame(eriInitializeContent);
            LOG.debug("数据帧为：" + eriInitializeContent.getFrame());

            byte[] frameData = Bytes.hex2bytes(eriInitializeContent.getFrame());
            if (frameData.length % 2 > 0) {
                LOG.error("数据区长度为奇数");
                throw new RuntimeException("数据区长度为奇数");
            }

            String summary = kmsService.SM3Digest(new BASE64Encoder().encode(frameData));
            eriInitializeContent.setSign(EriUtil.base64ToHex(kmsService.SM2Sign(version, 1, "", summary)));

        }  finally {
            if (protectedKeyIndex >= 0) {
                encryptorIndexService.release(configProperty.getEncryptorIdOfProtectKeyByKMS(), protectedKeyIndex);
            }
            if (user0sbqEncryptAndDecryptKeyIndex >= 0) {
                encryptorIndexService.release(configProperty.getEncryptorIdOfEncryptAndDecryptKeyByKMS(), user0sbqEncryptAndDecryptKeyIndex);
            }
        }
    }


    /**
     * 生成批密钥、单卡密钥、锁定口令、灭活口令
     * @param eriCustomizeContent 个性化信息
     * @param pubKey 公钥
     * @param create 是否为新申请,2016-04-13修改为始终为false,每张被发出去的卡都已经初始化过写口令了
     * @param local 是否为本地发卡，默认为本地
     * @throws Exception
     */
    private void generateCipherCodeForCustomizeByKMS(EriCustomizeContent eriCustomizeContent, String pubKey, boolean create, boolean local) throws Exception {
        int protectedKeyIndex = -1, encryptAndDecryptKeyIndex = -1;
        try {
            protectedKeyIndex = encryptorIndexService.fetch(configProperty.getEncryptorIdOfProtectKeyByKMS());
            encryptAndDecryptKeyIndex = encryptorIndexService.fetch(configProperty.getEncryptorIdOfEncryptAndDecryptKeyByKMS());
            String useraccount = configProperty.getEncryptorWebUseraccountKey();
            String password = configProperty.getEncryptorWebPasswordKey();
            String url = configProperty.getEncryptorWebUrlKey();
            byte[] phBytes = Bytes.hex2bytes(eriCustomizeContent.getPh());
            int versionType = phBytes[0] >> 4;      //0为测试版，1为正式版
            int version = (phBytes[0] & 0x0F - 1) % 4 + 1;   //批号第一位为密钥版本号，当前不使用。
            boolean flag = (phBytes[1] & 0x01) > 0;

            String protectKey = kmsService.createTmpProKey(version, new BASE64Encoder().encode(Bytes.hex2bytes(pubKey)), protectedKeyIndex);
            protectKey = protectKey.substring(192) + protectKey.substring(0, 192);
            eriCustomizeContent.setProtectKey(protectKey);

            SecretKeyManagerServiceStub service = new SecretKeyManagerServiceStub(url);

            String tid = eriCustomizeContent.getTid().substring(2, 14);
            byte[] tidBytes = Bytes.hex2bytes(tid);
            byte[] reverseTidBytes = new byte[tidBytes.length];
            for (int i = 0; i < tidBytes.length; i++) {
                reverseTidBytes[tidBytes.length - 1 - i] = tidBytes[i];
            }
            String reverseTid = EriUtil.bytesToHex(reverseTidBytes);

            eriCustomizeContent.setSdkl(kmsService.generateLockPassword(version, reverseTid, protectedKeyIndex));

            if (create) {
                eriCustomizeContent.setRootU0xkl(kmsService.generateUser0WritePasswordOld(
                        version, reverseTid, protectedKeyIndex, false));
                eriCustomizeContent.setU0xkl(kmsService.generateUser0WritePasswordOld(
                        version, reverseTid, protectedKeyIndex, true));
            } else {
                //不更改口令的情况，不需要传入锁定口令，所以传入32位随机数
                if (flag) {
                    String u0xkl = kmsService.generateUserWritePassword(version, 0, reverseTid, protectedKeyIndex);
                    eriCustomizeContent.setRootU0xkl(u0xkl);
                    eriCustomizeContent.setU0xkl(u0xkl);
                } else {
                    String u0xkl = kmsService.generateUser0WritePasswordOld(version, reverseTid, protectedKeyIndex, true);
                    eriCustomizeContent.setRootU0xkl(u0xkl);
                    eriCustomizeContent.setU0xkl(u0xkl);
                }
            }

            kmsService.generateUserEncryptPassword(version, 0, true, reverseTid, encryptAndDecryptKeyIndex);

            //CFB加密，使用TID为初始向量
            byte[] iv = new byte[16];
            for (int i = 0; i < 6; i++) {
                iv[15 - i] = tidBytes[i];
                iv[5 - i] = tidBytes[i];
            }

            byte[] ivTmp = Arrays.copyOf(iv, 16);

            byte[] user0sbqBytes = Bytes.hex2bytes(eriCustomizeContent.getUser0sbq());

            byte[] reverseUser0sbqBytes = new byte[user0sbqBytes.length];
            for (int i = 0; i < user0sbqBytes.length; i++) {
                reverseUser0sbqBytes[i] = user0sbqBytes[user0sbqBytes.length - 1 - i];
            }
            //使用wuxian1.4.jar不需要再将加密数据进行倒置，加密接口会自动进行倒置

            //SM1CFBEncrypt
            String encryptHexUser0sbq = kmsService.CFBEncrypt(encryptAndDecryptKeyIndex, true, EriUtil.bytesToHex(ivTmp),
                    new BASE64Encoder().encode(user0sbqBytes));
            int checkNum = CRC8.calculate(encryptHexUser0sbq);
            String checkStr = Integer.toHexString(checkNum).toUpperCase();
            checkStr = BinaryUtil.coverBefore(checkStr, 2);
            eriCustomizeContent.setUser0sbq(encryptHexUser0sbq + checkStr);
            LOG.debug("加密上半区(使用CFB加密接口)：" + eriCustomizeContent.getUser0sbq());

            kmsService.generateUserEncryptPassword(version, 0, false, reverseTid, encryptAndDecryptKeyIndex);

            //CFB加密，使用TID为初始向量
            ivTmp = Arrays.copyOf(iv, 16);

            byte[] user0xbqBytes = Bytes.hex2bytes(eriCustomizeContent.getUser0xbq());

            byte[] reverseUser0xbqBytes = new byte[user0xbqBytes.length];
            for (int i = 0; i < user0xbqBytes.length; i++) {
                reverseUser0xbqBytes[i] = user0xbqBytes[user0xbqBytes.length - 1 - i];
            }

            String encryptHexUser0xbq = kmsService.CFBEncrypt(encryptAndDecryptKeyIndex, false, EriUtil.bytesToHex(ivTmp),
                    new BASE64Encoder().encode(user0xbqBytes));
            checkNum = CRC8.calculate(encryptHexUser0xbq);
            checkStr = Integer.toHexString(checkNum).toUpperCase();
            checkStr = BinaryUtil.coverBefore(checkStr, 2);
            eriCustomizeContent.setUser0xbq(encryptHexUser0xbq + checkStr);
            LOG.debug("加密下半区(使用CFB加密接口)：" + encryptHexUser0xbq);

            composeCustomizeDataFrame(eriCustomizeContent);
            LOG.debug("数据帧为：" + eriCustomizeContent.getFrame());

            byte[] frameData = Bytes.hex2bytes(eriCustomizeContent.getFrame());
            if (frameData.length % 2 > 0) {
                LOG.error("数据区长度为奇数");
                throw new RuntimeException("数据区长度为奇数");
            }

            String summary = kmsService.SM3Digest(new BASE64Encoder().encode(frameData));
            String sign = EriUtil.base64ToHex(kmsService.SM2Sign(version, 1, "", summary));
            eriCustomizeContent.setSign(sign);
        } finally {
            if (protectedKeyIndex >= 0) {
                encryptorIndexService.release(configProperty.getEncryptorIdOfProtectKeyByKMS(), protectedKeyIndex);
            }
            if (encryptAndDecryptKeyIndex >= 0) {
                encryptorIndexService.release(configProperty.getEncryptorIdOfEncryptAndDecryptKeyByKMS(), encryptAndDecryptKeyIndex);
            }
        }
    }
    private String composeUpgradeDataFrame(Map content) throws Exception {
        StringBuilder data = new StringBuilder();
        data.append("0100"); //header
        data.append(content.get("protectKey"));
        data.append(content.get("tid"));
        data.append(content.get("pmy"));
        data.append(content.get("dkmy"));
        data.append(content.get("sdkl"));
        data.append(content.get("mhkl"));
        data.append(content.get("u0xkl"));
        data.append(content.get("u1xkl"));
        data.append(content.get("u2xkl"));
        data.append(content.get("u3xkl"));
        data.append(content.get("u4xkl"));
        data.append(content.get("u5xkl"));
        data.append("5A"); //fgf
        data.append("02"); //写分区数
        data.append("100002"); //编码区
        data.append(content.get("ph"));
        data.append("300405"); //用户区：数据区（5）
        data.append(content.get("bkh"));
        if (data.length() / 2 % 2 != 0) {
            data.append("00"); //由于数据区长度此时为奇数，为保证读写器能按字正常传输，需要补0，保证数据区长度为偶数。
        }
        return data.toString();
    }

    private void composeInitializeDateFrame(EriInitializeContent eriInitializeContent) throws Exception {
        StringBuilder data = new StringBuilder();
        //header + bhmy + tid + my + sdkl + mhkl + xkl + fgf + banksum + phHeader + ph + User0Header + User0;
        byte[] phBytes = Bytes.hex2bytes(eriInitializeContent.getPh());
        int phNo = phBytes[2] & 0xFF;
        if (phNo > 1) {
            data.append("04");
        } else {
            data.append("01");
        }

        data.append("00");
        data.append(eriInitializeContent.getProtectKey());
        data.append(eriInitializeContent.getTid());
        data.append(eriInitializeContent.getPmy());
        data.append(eriInitializeContent.getDkmy());
        data.append(eriInitializeContent.getSdkl());
        data.append(eriInitializeContent.getMhkl());
        data.append(eriInitializeContent.getU0xkl());
        data.append(eriInitializeContent.getU1xkl());
        data.append(eriInitializeContent.getU2xkl());
        data.append(eriInitializeContent.getU3xkl());
        data.append(eriInitializeContent.getU4xkl());
        data.append(eriInitializeContent.getU5xkl());
        if (phNo > 1) {
            data.append(eriInitializeContent.getU1dkl());
            data.append(eriInitializeContent.getU3dkl());
            data.append(eriInitializeContent.getU4dkl());
            data.append(eriInitializeContent.getU5dkl());
        }
        data.append("5A"); //fgf
        data.append("02"); //写分区数
        data.append("100002"); //编码区
        data.append(eriInitializeContent.getPh());
        data.append("300405"); //用户区：数据区（5）
        data.append(eriInitializeContent.getBkh());
//        data.append("00");
        eriInitializeContent.setFrame(data.toString());
    }

    private void composeCustomizeDataFrame(EriCustomizeContent eriCustomizeContent) {
        String data = "0200";   //数据格式版本02，数据域标识00
        data += eriCustomizeContent.getProtectKey();
        data += eriCustomizeContent.getTid();
        data += eriCustomizeContent.getSdkl();
        data += eriCustomizeContent.getRootU0xkl();
        if (StringUtils.isEmpty(eriCustomizeContent.getU0xkl())) {
            data += eriCustomizeContent.getRootU0xkl();
        } else {
            data += eriCustomizeContent.getU0xkl();
        }
        data += "5A"; //分区数据分隔符
        data += "01"; //写分区数
        data += "30040A"; //User0头
        data += eriCustomizeContent.getUser0sbq();
        data += eriCustomizeContent.getUser0xbq();
        if (data.length() / 2 % 2 != 0) {
            data += "00"; //由于数据区长度此时为奇数，为保证读写器能按字正常传输，需要补0，保证数据区长度为偶数。
        }
        eriCustomizeContent.setFrame(data);
    }

    public static void main(String args[]) {
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String generateUrlByKh(String kh) throws Exception {
        HsmApi safapi = getInstance();
        SAFResult safResult;
        byte[] content = Arrays.copyOf(kh.getBytes(), 32);
        safResult = safapi.SAF_SM2PriKeySign(1, 32, content);
        String sign = BaseEncoding.base32().encode(safResult.getData()).replaceAll("=", "");
        return "HTTP://192.168.191.1:8080/M/ID.HTM?ID=" + kh + sign;
    }

    public boolean verifyIdentity(String id, String sign) {
        try {
            byte[] signData = BaseEncoding.base32().decode(sign);
            HsmApi safapi = getInstance();
            byte[] data = Arrays.copyOf(id.getBytes(), 32);
            SAFResult safResult = safapi.SAF_SM2PubKeyVerify(1, null, signData, data.length, data);
            return safResult.getResultCode() == 0;
        } catch (Exception e) {
            LOG.error("验签失败", e);
            return false;
        }
    }

    public User0Data parseUser0Data(String data) throws Exception {
        //车辆信息上半区一共80位，采用两个long型数值进行拼装
        //info[0]存储卡号（37）和省份代码前3位(3)共40位，其中卡号又分卡类型（1）、省份代码（6）和顺序号（30）

        //info[1]存储省份代码后3位、发牌代号（5）、使用性质（4）、出厂日期（9）、车辆类型（9）、排量/功率标志位（1）、保留（1），共32位
        User0Data user0Data = new User0Data();
        int cardType = Integer.valueOf(data.substring(0, 1), 16) >> 3;
        user0Data.setCardType(cardType);
        int sf = Integer.valueOf(data.substring(0, 2), 16) >> 1 & 0x3F;
        long kh = Long.valueOf(data.substring(0, 10), 16) >> 3 & 0x3FFFFFFF;
        user0Data.setKh(gSysparaCodeService.getCodesByDmz2("00", "0032", sf + "").get(0).getDmz() + EriUtil.appendZero(kh + "", 10));
        sf = Integer.valueOf(data.substring(9, 11), 16) >> 1 & 0x3F;
        user0Data.setSf(gSysparaCodeService.getCodesByDmz2("00", "0032", sf + "").get(0).getDmsm2());
        int fpdh = Integer.valueOf(data.substring(10, 12), 16) & 0x1F;
        user0Data.setFpdh(parseLicensePlate(fpdh));
        int syxz = Integer.valueOf(data.substring(12, 13), 16);
        user0Data.setSyxz(gSysparaCodeService.getCodesByDmz2("00", "1003", syxz + "").get(0).getDmsm1());
        int ccrq = Integer.valueOf(data.substring(13, 16), 16) >> 3;
        Calendar now = Calendar.getInstance();
        Calendar basic = Calendar.getInstance();
        basic.set(1990, Calendar.JANUARY, 1);
        if ((now.get(Calendar.YEAR) - 1990) * 12 + now.get(Calendar.MONTH) + 1 < ccrq ) {
            ccrq = ccrq - 511;
        }
        if (ccrq != 511) {
            basic.add(Calendar.MONTH, ccrq);
            user0Data.setCcrq(basic.getTime());
        }
        int cllx = Integer.valueOf(data.substring(15, 18), 16) >> 2 & 0x01FF;
        user0Data.setCllx(gSysparaCodeService.getCodesByDmz2("00", "1004", cllx + "").get(0).getDmsm1());
        int glOrPlSign = Integer.valueOf(data.substring(17, 18), 16) >> 1 & 0x01;
        user0Data.setPlOrGlSign(glOrPlSign);
        int hpzl = Integer.valueOf(data.substring(20, 21), 16);
        List<Code> hpzlList = gSysparaCodeService.getCodesByDmz2("00", "1007", hpzl + "");
        String hpzlStr = "";
        for (Code hpzlCode : hpzlList) {
            hpzlStr = hpzlStr + hpzlCode.getDmsm1() + "、";
        }
        if (hpzlStr.endsWith("、")) {
            hpzlStr = hpzlStr.substring(0, hpzlStr.length() - 1);
        }
        user0Data.setHpzl(hpzlStr);
        long hphm = Long.valueOf(data.substring(21, 29), 16);
        user0Data.setHphm(EriUtil.parseLongToHPHM(hphm));
        int yxqz = Integer.valueOf(data.substring(29, 32), 16) >> 3;
        basic.set(2013, Calendar.JANUARY, 1);
        if (yxqz != 511) {
            basic.add(Calendar.MONTH, yxqz);
            user0Data.setYxqz(basic.getTime());
        }
        int qzbfqz = Integer.valueOf(data.substring(31, 33), 16) >> 2 & 0x1F;
        if (yxqz != 511) {
            basic.add(Calendar.YEAR, qzbfqz);
            user0Data.setQzbfqz(basic.getTime());
        }
        int hdzkOrZzl = Integer.valueOf(data.substring(32, 35), 16) & 0x03FF;
        if (user0Data.getCllx().toUpperCase().startsWith("K")) {
            user0Data.setHdzk(hdzkOrZzl);
        } else {
            user0Data.setZzl(hdzkOrZzl * 100);
        }
        int csys = Integer.valueOf(data.substring(35, 36), 16);
        user0Data.setCsys(gSysparaCodeService.getCodesByDmz2("00", "1008", csys + "").get(0).getDmsm1());
        int plOrGl = Integer.valueOf(data.substring(36, 38), 16);
        if (glOrPlSign == 0) {
            user0Data.setPl(plOrGl * 100);
        } else {
            user0Data.setGl(plOrGl);
        }
        return user0Data;
    }

    @Override
    @Transactional
    public void modifyPasswordResult(String tid, int result, String sbyy) throws Exception {
        Eri eri = fetchByTid(tid);
        if (eri == null) {
            throw new OperationException("01", "TID对应的卡不存在");
        }
        eri.setClxxbfid(null);
        update(eri);
        EriCustomizeRecord eriCustomizeRecord = new EriCustomizeRecord();
        eriCustomizeRecord.setKh(eri.getKh());
        eriCustomizeRecord.setTid(tid);
        eriCustomizeRecord.setGxhrq(new Date());
        eriCustomizeRecord.setGxhczr(UserState.getUser().getYhdh());
        eriCustomizeRecord.setZt(result > 0 ? EriCustomizeStatus.SUCCESS.getStatus() : EriCustomizeStatus.FAIL.getStatus());
        eriCustomizeRecord.setSbyy(sbyy);
        eriCustomizeRecordMapper.create(eriCustomizeRecord);
    }

    @Override
    public void update(Eri eri) throws Exception {
        eriMapper.update(eri);
    }

    @Override
    public List<Eri> queryByCkdh(String ckdh) throws Exception {
        // TODO Auto-generated method stub
        return eriMapper.queryByCkdh(ckdh);
    }

    @Override
    public void warehouse(Eri eri) throws Exception{
        eriMapper.warehouse(eri);
    }

    @Override
    public Eri fetchByVehicle(String fzjg, String hphm, String hpzl) throws Exception {
        List<Eri> eriList = eriMapper.fetchByVehicle(MapUtilities.buildMap("fzjg", fzjg, "hphm", hphm, "hpzl", hpzl));
        if (eriList == null || eriList.isEmpty()) {
            return null;
        } else if (eriList.size() > 1) {
            throw new RuntimeException("数据异常，查找到多张绑定的电子标识");
        } else {
            return eriList.get(0);
        }
    }

    @Override
    public Eri fetchByVehicle(VehicleInfo vehicle) throws Exception {
        return fetchByVehicle(vehicle.getFzjg(), vehicle.getHphm(), vehicle.getHpzl());
    }

    @Override
    public boolean updateByCondition(Map<Object, Object> condition) throws Exception {
        return eriMapper.updateByCondition(condition) > 0;
    }

    @Override
    public Eri fetchByJdcxh(String jdcxh) throws Exception {
        return eriMapper.fetchByJdcxh(jdcxh);
    }

    @Override
    public Eri fetchByKh(String kh) throws Exception {
        List<Eri> eriList = fetchByCondition(MapUtilities.buildMap("zt", EriStatus.AVAILABLE.getStatus(),
                "kh", kh));
        if (eriList == null || eriList.isEmpty()) {
            return null;
        } else if (eriList.size() > 1) {
            throw new RuntimeException("存在多于一个卡号为" + kh + "的电子标识");
        } else {
            return eriList.get(0);
        }
    }

    /**
     *
     * @param cardType 标识类别
     * @param eriCustomizeContent 个性化信息重置
     */
    public void resetVehicleInfo(int cardType, EriCustomizeContent eriCustomizeContent) throws Exception{
        if (StringUtils.isEmpty(eriCustomizeContent.getSf())) throw new Exception("电子标识上卡号不正确，省份代码为空");
//       Vehicle vehicle = eriCustomizeContent.getVehicle();
        Code province = gSysparaCodeService.getCode("00", "0032", eriCustomizeContent.getSf());
        //车辆信息上半区一共80位，采用两个long型数值进行拼装
        long[] info = new long[] {0l, 0l};
        //info[0]存储卡号（37）和省份代码前3位(3)共40位，其中卡号又分卡类型（1）、省份代码（6）和顺序号（30）
        info[0] |= cardType;
        info[0] <<= 6;
        info[0] |= province.getDmz2();
        info[0] <<= 30;
        info[0] |= eriCustomizeContent.getSxh();
        info[0] <<= 6;
//       Code vehicleProvince = parseProvinceAbbr(vehicle.getFzjg().substring(0, 1));
        info[0] |= province.getDmz2();
        info[0] >>= 3; //删除省份代码后3位
        info[1] <<= 27;
        int plOrGl = 0; //排量/功率标志位，0-排量，1-功率
        info[1] <<= 1;
        //test
        info[1] |= plOrGl;
        info[1] <<= 1;
        info[1] &= 0xffffffff; //删除省份代码前3位
        String firstPartHex = Long.toHexString(info[0]);
        firstPartHex = EriUtil.appendZero(firstPartHex, 10);
        String secondPartHex = Long.toHexString(info[1]);
        secondPartHex = EriUtil.appendZero(secondPartHex, 8);
        String upperPartHex = firstPartHex + secondPartHex;
        LOG.debug("上半区内容:{}", upperPartHex);
        eriCustomizeContent.setUser0sbq(upperPartHex);
        info = new long[] {0l, 0l};
        info[0] <<= 36;
        firstPartHex = Long.toHexString(info[0]);
        firstPartHex = EriUtil.appendZero(firstPartHex, 9);
        secondPartHex = Long.toHexString(info[1]);
        secondPartHex = EriUtil.appendZero(secondPartHex, 9);
        String lowerPartHex = firstPartHex + secondPartHex;
        LOG.debug("下半区内容：{}", lowerPartHex);
        eriCustomizeContent.setUser0xbq(lowerPartHex);
    }
    public HsmApi getInstance() throws Exception{
//        HsmApi api = new HsmApi("10.2.43.19", 1818, 3); //公安网
        HsmApi api = new HsmApi("172.19.17.111", 8008, 3);
        return api;
    }

    @Override
    public EriCustomizeContent cleanCustomize(String tid, String certStr) throws Exception {
        Cert cert = EriUtil.parseCert(certStr);
        if (!verifyCert(cert)) {
            throw new OperationException("01", "UNBIND", "校验证书失败");
        }
        Eri eri = eriMapper.queryById(tid);
        if (eri == null) {
            throw new OperationException("02", "UNBIND", "电子标识未初始化或未入库");
        }else {
            if(eri.getClxxbfid() == null){
                throw new OperationException("03", "UNBIND", "该电子标识无绑定车辆信息");
            }
        }


//       int eriStatus = eri.getZt();
//       if (EriStatus.AVAILABLE.getStatus() != eriStatus) {
//           throw new OperationException("03", "CUSTOMIZE", "标识当前状态无法个性化");
//       }
//       List<EriCustomizeRecord> ecrs = eriCustomizeRecordMapper.queryByCondition(
//               MapUtilities.buildMap("zt", EriCustomizeStatus.IN_PROGRESS.getStatus(), "tid", tid));
//       if (!ecrs.isEmpty()) {
//           throw new OperationException("04", "CUSTOMIZE", "该标识正在进行个性化中，请重置");
//       }
        String kh = eri.getKh();
        String provinceCode = kh.substring(0, 2);
        String sxh = kh.substring(2);
//       CustomizeTask customizeTask = customizeTaskService.fetchByXh(xh);
        EriCustomizeContent eriCustomizeContent = new EriCustomizeContent();
        eriCustomizeContent.setSf(provinceCode);
        eriCustomizeContent.setSxh(Long.valueOf(sxh));
        eriCustomizeContent.setPh(eri.getPh());
        eriCustomizeContent.setTid(eri.getTid());
        eriCustomizeContent.setKh(eri.getKh());
        resetVehicleInfo(0, eriCustomizeContent);
        generateCipherCodeForCustomize(eriCustomizeContent, cert.getPubKey(), eri.getScgxhrq() == null);
        return eriCustomizeContent;
    }


    @Autowired
    private EriUnbindMapper eriUnbindMapper;

    @Override
    public void unbindEri(String tid) {
        // TODO Auto-generated method stub
        EriUnbind eriUnbind = new EriUnbind();
        Eri eri = eriMapper.queryById(tid);
        eriUnbind.setTid(tid);
        eriUnbind.setKh(eri.getKh());
        eriUnbind.setClxxbfid(eri.getClxxbfid());
        eriUnbind.setCzr(UserState.getUser().getYhdh());

        eriUnbindMapper.insert(eriUnbind);
        eriMapper.unbindEri(tid);

    }
}
