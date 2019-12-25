package com.tmri.rfid.endpoints;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import com.tmri.rfid.bean.*;
import com.tmri.rfid.common.CustomizeTaskStatus;
import com.tmri.rfid.common.CustomizeTaskType;
import com.tmri.rfid.ctrl.view.CustomizeTaskView;
import com.tmri.rfid.exception.OperationException;
import com.tmri.rfid.service.CustomizeTaskService;
import com.tmri.rfid.service.ElectronicCertificateService;
import com.tmri.rfid.service.EriService;
import com.tmri.rfid.service.VehicleLogService;
import com.tmri.rfid.service.VehicleService;
import com.tmri.rfid.util.GsonHelper;
import com.tmri.rfid.util.MapUtilities;
import com.tmri.share.frm.util.Constants;
import com.tmri.share.frm.util.FuncUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Joey on 2015/12/7.
 */
@Controller
@RequestMapping("/be/customize-task.rfid")
public class CustomizeTaskEndPoint {

    private final static Logger LOG = LoggerFactory.getLogger(CustomizeTaskEndPoint.class);

    @Resource
    private CustomizeTaskService customizeTaskService;

    @Resource
    private VehicleService vehicleService;

    @Resource
    private VehicleLogService vehicleLogService;

    @Resource
    private EriService eriService;
    
    @Resource
    private ElectronicCertificateService electronicCertificateService;

    @RequestMapping(value = "method=update-status", method = RequestMethod.POST)
    public String updateStatus(@RequestParam(value = "status") int status,
                               @RequestParam(value = "qxyy", required = false) String qxyy) {
        LOG.info(String.format("------> calling /be/customize-task.rfid?method=update-status status = %s, qxyy = %s",
                        status, qxyy));
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            resultMap.put("resultId", "00");
        } catch (Exception e) {
            LOG.error("------> calling /be/customize-task.rfid?method=update-status fail", e);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", e.getMessage());
        }
        String result = GsonHelper.getGson().toJson(resultMap);
        LOG.info("------> calling /be/customize-task.rfid?method=update-status result=" + result);
        return result;
    }

    @RequestMapping(params = "method=check-card-status", method = RequestMethod.GET)
    @ResponseBody
    public String checkCardStatus(@RequestParam(value = "lsh", required = false) String lsh,
                                  @RequestParam(value = "hpzl", required = false) String hpzl,
                                  @RequestParam(value = "hphm", required = false) String hphm,
                                  @RequestParam(value = "fzjg", required = false) String fzjg,
                                  @RequestParam(value = "tid") String tid,
                                  @RequestParam(value = "ywlx") String ywlx,
                                  @RequestParam(value = "xh", required = false) Long xh) {
        LOG.info(String.format(
                "------> calling /customize-task.rfid?method=check-card-status, tid = %s, lsh = %s, hpzl = %s, hphm = %s, fzjg = %s, ywlx=%s",
                tid, lsh, hpzl, hphm, fzjg, ywlx));
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            if((StringUtils.isEmpty(lsh) && (StringUtils.isEmpty(hpzl) || StringUtils.isEmpty(hphm)) && xh == null)
                    || StringUtils.isEmpty(ywlx)) {
                resultMap.put("resultId", "01");
                resultMap.put("resultMsg", "�����������");
            } else {
                Vehicle vehicle;
                if (xh != null) {
                    CustomizeTask customizeTask = customizeTaskService.fetchByXh(xh);
                    vehicle = vehicleService.fetchById(customizeTask.getClxxid());
//                    vehicle = vehicleService.synchronize(lsh, FuncUtil.getFzjg(hphm), hphm.substring(1), hpzl,null);

                } else {
                    vehicle = vehicleService.synchronize(lsh, FuncUtil.getFzjg(hphm), hphm.substring(1), hpzl,null);
                }
                if (vehicle == null) {
                    resultMap.put("resultId", "02");
                    resultMap.put("resultMsg", "δ���ҵ���Ӧ�ĳ�����Ϣ");
                } else if (eriService.checkCardStatus(tid, vehicle, Integer.valueOf(ywlx))) {
                    Map<String, Object> vehicleInfo = vehicleService.translate(vehicle);
                    resultMap.put("resultId", "00");
                    resultMap.put("info", vehicleInfo);
                } else {
                    resultMap.put("resultId", "01");
                }
            }
        } catch (OperationException e) {
            LOG.error("customize failed ", e);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", e.getMessage());
            resultMap.put("errorCode", e.getCode());
        } catch (Exception e) {
            LOG.error("customize failed ", e);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", e.getMessage());
        }
        String result = GsonHelper.getGson().toJson(resultMap);
        LOG.info("------> calling /eri.frm?method=check-card-status result = " + result);
        return result;
    }
    

    @RequestMapping(params = "method=check-unBindcard-status", method = RequestMethod.GET)
    @ResponseBody
    public String checkCardStatus(@RequestParam(value = "tid") String tid,
    		@RequestParam(value = "certStr") String certStr) {
        LOG.info(String.format(
                "------> calling /customize-task.rfid?method=check-card-status, tid = %s",tid));
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
        	
        	EriCustomizeContent unbindContent = eriService.cleanCustomize(tid, certStr);
        	resultMap.put("resultId", "00");
        	resultMap.put("info", unbindContent);
        	
        } catch (OperationException e) {
            LOG.error("unbind failed ", e);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", e.getMessage());
            resultMap.put("errorCode", e.getCode());
        } catch (Exception e) {
            LOG.error("unbind failed ", e);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", e.getMessage());
        }
        String result = GsonHelper.getGson().toJson(resultMap);
        LOG.info("------> calling /eri.frm?method=check-card-status result = " + result);
        return result;
    }
    

    @RequestMapping(params = "method=modify-password", method= RequestMethod.GET)
    @ResponseBody
    public String modifyPassword(@RequestParam(value = "tid") String tid,
                                 @RequestParam(value = "content") String content,
                                 @RequestParam(value = "sourceFactor") String sourceFactor,
                                 @RequestParam(value = "destFactor") String destFactor,
                                 @RequestParam(value = "cert") String cert) {
        LOG.info(String.format("------> calling /customize-task.rfid?method=modify-password," +
                " tid = %s, content = %s, sourceFactor = %s, destFactor = %s, cert = %s",
                tid, content, sourceFactor, destFactor, cert));
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            EriCustomizeContent eriCustomizeContent = eriService.modifyPassword(tid, content, cert, sourceFactor, destFactor);
            resultMap.put("info", eriCustomizeContent);
            resultMap.put("resultId", "00");
        } catch (OperationException e) {
            LOG.error("customize failed ", e);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", e.getMessage());
            resultMap.put("errorCode", e.getCode());
        } catch (Exception e) {
            LOG.error("customize failed ", e);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", e.getMessage());
        }
        String result = GsonHelper.getGson().toJson(resultMap);
        LOG.info("------> calling /eri.frm?method=modify-password result = " + result);
        return result;
    }

    @RequestMapping(params = "method=fetch-data", method = RequestMethod.GET)
    @ResponseBody
    public String fetchData(@RequestParam(value = "lsh", required = false) String lsh,
                            @RequestParam(value = "hpzl", required = false) String hpzl,
                            @RequestParam(value = "hphm", required = false) String hphm,
                            @RequestParam(value = "fzjg", required = false) String fzjg,
                            @RequestParam(value = "tid") String tid,
                            @RequestParam(value = "cert") String cert,
                            @RequestParam(value = "ywlx") String ywlx,
                            @RequestParam(value = "xh", required = false) Long xh) {
        LOG.info(String.format(
                "------> calling /customize-task.rfid?method=fetch-data, tid = %s, cert = %s, lsh = %s, hpzl = %s, hphm = %s, fzjg = %s, ywlx=%s, xh = %s",
                tid, cert, lsh, hpzl, hphm, fzjg, ywlx, xh));
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            if (xh != null) {
                CustomizeTask customizeTask = customizeTaskService.fetchByXh(xh);
                resultMap.put("resultId", "00");
                resultMap.put("CustomizeTask", MapUtilities.transBean2Map(customizeTask));
                EriCustomizeContent content = eriService.customize(tid, customizeTask.getXh(), cert);
                resultMap.put("info", content);
            } else {
                if (StringUtils.isEmpty(lsh) && (StringUtils.isEmpty(hpzl) || StringUtils.isEmpty(hphm))) {
                    resultMap.put("resultId", "01");
                    resultMap.put("resultMsg", "�����������");
                } else {
                    CustomizeTask customizeTask = customizeTaskService.fetch(lsh, FuncUtil.getFzjg(hphm), hpzl, hphm.substring(1), Integer.valueOf(ywlx), tid);
                    if (customizeTask == null) {
                        resultMap.put("resultId", "02");
                        resultMap.put("resultMsg", "δ���ҵ����Ի�������Ϣ");
                    } else if (customizeTask.getRwlx() == CustomizeTaskType.PRE_CUSTOMIZE.getType()) {
                        resultMap.put("resultId", "00");
                        resultMap.put("CustomizeTask", MapUtilities.transBean2Map(customizeTask));
                        EriCustomizeContent content = eriService.customize(tid, customizeTask.getXh(), cert);
                        resultMap.put("info", content);
                    } else {
                        Vehicle vehicle = vehicleService.fetchById(customizeTask.getClxxid());
                        if (vehicle != null) {
                            resultMap.put("resultId", "00");
                            resultMap.put("CustomizeTask", MapUtilities.transBean2Map(customizeTask));
                            EriCustomizeContent content = eriService.customize(tid, customizeTask.getXh(), cert);
                            resultMap.put("info", content);
                        } else {
                            resultMap.put("resultId", "03");
                            resultMap.put("resultMsg", "δ��ѯ��������Ϣ");
                        }
                    }
                }
            }
        } catch (OperationException e) {
            LOG.error("customize failed ", e);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", e.getMessage());
            resultMap.put("errorCode", e.getCode());
        } catch (Exception e) {
            LOG.error("customize failed ", e);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", e.getMessage());
        }
        String result = GsonHelper.getGson().toJson(resultMap);
        LOG.info("------> calling /eri.frm?method=customize result = " + result);
        return result;
    }


    @RequestMapping(params = "method=fetch-data-with-tid", method = RequestMethod.POST)
    @ResponseBody
    public String fetchDataWithTid(@RequestParam(value = "lsh", required = false) String lsh,
                            @RequestParam(value = "hpzl", required = false) String hpzl,
                            @RequestParam(value = "hphm", required = false) String hphm,
                            @RequestParam(value = "fzjg", required = false) String fzjg,
                            @RequestParam(value = "tid") String tid,
                            @RequestParam(value = "aqmkxh",required = false) String aqmkxh,
                            @RequestParam(value = "cert",required = false) String cert,
                            @RequestParam(value = "ywlx", required = false) String ywlx,
                            @RequestParam(value = "xh", required = false) Long xh) {
    	LOG.info(String.format(
                "------> calling /customize-task.rfid?method=fetch-data-with-tid, tid = %s, cert = %s, lsh = %s, hpzl = %s, hphm = %s, fzjg = %s, ywlx=%s, xh = %s",
                tid, cert, lsh, hpzl, hphm, fzjg, ywlx, xh));
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            CustomizeTask customizeTask = customizeTaskService.fetchByTid(tid);
            
            if (customizeTask == null) {
                resultMap.put("resultId", "02");
                resultMap.put("resultMsg", "δ���ҵ����Ի�������Ϣ");
            }else{
            	if(StringUtils.isEmpty(cert)){//���֤��Ϊ��
            		if(StringUtils.isEmpty(aqmkxh)){//�����ȫģ�����Ϊ��
            			throw new Exception("��ȫģ����ź�֤�鲻�ɶ�Ϊ��");
            		}else{
            			List<ElectronicCertificate> certificates =
            					electronicCertificateService.fetchByCondition(MapUtilities.buildMap("ssztbh", aqmkxh, "zt", 1));
            	        if (certificates == null || certificates.isEmpty()) {
            	        	throw new Exception("���ݰ�ȫģ������Ҳ���֤��");
            	        } else{
            	        	ElectronicCertificate certificate = certificates.get(0);
            	        	cert = certificate.getZsnr();
            	        }
            	        	
            		}
            	}
            	
            	if(!StringUtils.isEmpty(cert)){
                    EriCustomizeContent content = eriService.customize(tid, Long.parseLong(customizeTask.getLsh()), cert);
                    resultMap.put("resultId", "00");
                    resultMap.put("info", content);
                    resultMap.put("lsh", customizeTask.getLsh());
            	}
            }
            
        } catch (OperationException e) {
            LOG.error("customize failed ", e);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", e.getMessage());
            resultMap.put("errorCode", e.getCode());
        } catch (Exception e) {
            LOG.error("customize failed ", e);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", e.getMessage());
        }
        String result = GsonHelper.getGson().toJson(resultMap);
        LOG.info("------> calling /eri.frm?method=fetch-data-with-tid result = " + result);
        return result;
    }
    
    @RequestMapping(params = "method=query")
    @ResponseBody
    public String query(@RequestParam(value = "lsh", required = false) String lsh,
                        @RequestParam(value = "hpzl", required = false) String hpzl,
                        @RequestParam(value = "hphm", required = false) String hphm,
                        @RequestParam(value = "fzjg", required = false) String fzjg) {
        LOG.info(String.format("------> calling /be/customize-task.rfid?method=query(%s, %s, %s, %s)", lsh, hpzl, hphm, fzjg));
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            if(StringUtils.isEmpty(lsh) && (StringUtils.isEmpty(hpzl) || StringUtils.isEmpty(hphm))) {
                resultMap.put("resultId", "01");
                resultMap.put("resultMsg", "�����������");
            } else {
                CustomizeTask customizeTask = customizeTaskService.fetch(lsh, fzjg, hpzl, hphm, CustomizeTaskType.NEW.getType(), null);
                if (customizeTask == null) {
                    resultMap.put("resultId", "02");
                    resultMap.put("resultMsg", "δ���ҵ����Ի�������Ϣ");
                } else if (customizeTask.getRwlx() == CustomizeTaskType.PRE_CUSTOMIZE.getType()) {
                    resultMap.put("resultId", "00");
                    resultMap.put("CustomizeTask", MapUtilities.transBean2Map(customizeTask));
                } else {
                    Map<String, Object> customizeTaskInfo = MapUtilities.transBean2Map(customizeTask);
                    Vehicle vehicle = vehicleService.fetchById(customizeTask.getClxxid());
                    if (vehicle != null) {
                        Map<String, Object> vehicleInfo = vehicleService.translate(vehicle);
                        vehicleInfo.remove("lsh");
                        customizeTaskInfo.putAll(vehicleInfo);
                        resultMap.put("resultId", "00");
                        resultMap.put("CustomizeTask", customizeTaskInfo);
                    } else {
                        resultMap.put("resultId", "03");
                        resultMap.put("resultMsg", "δ��ѯ��������Ϣ");
                    }
                }
            }
        } catch (Exception e) {
            LOG.error("��ѯ�������Ի���Ϣ����ʧ��", e);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", e.getMessage());
        }
        String result =  GsonHelper.getGson2DateFormat(Constants.DateFormate2day).toJson(resultMap);
        LOG.info("------> calling /be/customize-task.rfid?method=query, result = " + result);
        return result;
    }

    @RequestMapping(params = "method=fetch-modify-info")
    @ResponseBody
    public String fetchModifyInfo(@RequestParam(value = "kh", required = false) String kh,
                                  @RequestParam(value = "lsh", required = false) String lsh,
                                  @RequestParam(value = "hphm", required = false) String hphm,
                                  @RequestParam(value = "hpzl", required = false) String hpzl,
                                  @RequestParam(value = "fzjg", required = false) String fzjg) {
        LOG.info(String.format("------> calling /be/customize-task.rfid?method=fetch-modify-info kh = %s, lsh = %s, hphm = %s, hpzl = %s, fzjg = %s",
                kh, lsh, hphm, hpzl, fzjg));
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            if (StringUtils.isEmpty(kh) || (StringUtils.isEmpty(lsh) &&
                    StringUtils.isEmpty(hphm) || StringUtils.isEmpty(hpzl) || StringUtils.isEmpty(fzjg))) {
                resultMap.put("resultId", "01");
                resultMap.put("resultMsg", "�����뿨�Ż��߳��������Ϣ����ˮ�Ż���ƺ��롢�������ࡢ��֤���أ�");
            } else {
                Eri eri = eriService.fetchByKh(kh);
                if (eri.getClxxbfid() == null) {
                    resultMap.put("resultId", "02");
                    resultMap.put("resultMsg", "���ӱ�ʶδǩע������Ϣ����ȷ��������Ϣ�Ƿ���ȷ");
                } else {
                    VehicleInfo old = vehicleLogService.fetchById(eri.getClxxbfid());
                    VehicleInfo vehicle = vehicleService.synchronize(lsh, FuncUtil.getFzjg(hphm), hphm.substring(1), hpzl,null);
                    if (old == null) {
                        resultMap.put("resultId", "03");
                        resultMap.put("resultMsg", "�޷���ȡ�õ��ӱ�ʶǩע��ԭ������Ϣ������ϵ����Ա");
                    } else if (vehicle == null) {
                        resultMap.put("resultId", "04");
                        resultMap.put("resultMsg", "�޷���ȡҪǩע�ĳ�����Ϣ����ȷ��������Ϣ�Ƿ���ȷ");
                    } else {
                        if (eriService.checkCardStatus(eri.getTid(), vehicle, Integer.valueOf(CustomizeTaskType.MODIFY.getType()))) {
                            resultMap.put("resultId", "00");
                            resultMap.put("eri", eri);
                            resultMap.put("old", vehicleService.translate(old));
                            resultMap.put("new", vehicleService.translate(vehicle));
                        } else {
                            resultMap.put("resultId", "05");
                            resultMap.put("resultMsg", "������Ϣ�޷������ҵ������ϵ����Ա");
                        }

                    }
                }
            }
        } catch (Exception e) {
            LOG.error("------> calling /be/eri.rfid?method=fetch-modify-info error", e);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", e.getMessage());
        }
        String result = GsonHelper.getGson().toJson(resultMap);
        LOG.info("------> calling /be/eri.rfid?method=fetch-modify-info result = " + result);
        return result;
    }

    @RequestMapping(params = "method=fetch-change-info")
    @ResponseBody
    public String fetchChangeInfo(@RequestParam(value = "kh", required = false) String kh,
                                  @RequestParam(value = "hphm", required = false) String hphm,
                                  @RequestParam(value = "hpzl", required = false) String hpzl,
                                  @RequestParam(value = "fzjg", required = false) String fzjg) {
        LOG.info(String.format("------> calling /be/customize-task.rfid?method=fetch-change-info kh = %s, hphm = %s, hpzl = %s, fzjg = %s",
                kh, hphm, hpzl, fzjg));
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            if (StringUtils.isEmpty(hphm) || StringUtils.isEmpty(hpzl) || StringUtils.isEmpty(fzjg)) {
                resultMap.put("resultId", "01");
                resultMap.put("resultMsg", "��������ƺ��롢�������ࡢ��֤����");
            } else {
                if (StringUtils.isNotEmpty(kh)) {
                    Eri newEri = eriService.fetchByKh(kh);
                    if (newEri.getClxxbfid() != null) {
                        throw new RuntimeException("������ӱ�ʶ��ǩע������Ϣ");
                    }
                    resultMap.put("newEri", newEri);
                }
                Eri eri = eriService.fetchByVehicle(FuncUtil.getFzjg(hphm), hphm.substring(1), hpzl);
                if (eri == null || eri.getClxxbfid() == null) {
                    resultMap.put("resultId", "02");
                    resultMap.put("resultMsg", "�ó���δ������ӱ�ʶ��������״�����ҵ��");
                } else {
                    VehicleInfo old = vehicleLogService.fetchById(eri.getClxxbfid());
                    if (old == null) {
                        resultMap.put("resultId", "03");
                        resultMap.put("resultMsg", "�޷���ȡԭ���ӱ�ʶǩע��ԭ������Ϣ������ϵ����Ա");
                    } else {
                        resultMap.put("resultId", "00");
                        resultMap.put("oldEri", eri);
                        resultMap.put("bean", vehicleService.translate(old));
                    }
                }
            }
        } catch (Exception e) {
            LOG.error("------> calling /be/eri.rfid?method=fetch-change-info error", e);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", e.getMessage());
        }
        String result = GsonHelper.getGson().toJson(resultMap);
        LOG.info("------> calling /be/eri.rfid?method=fetch-change-info result = " + result);
        return result;
    }

    @RequestMapping(params = "method=fetch-annual-inspection-info")
    @ResponseBody
    public String fetchAnnualInspection(@RequestParam(value = "hphm") String hphm,
                                        @RequestParam(value = "hpzl") String hpzl) {
        LOG.info(String.format("------> calling /be/customize-task.rfid?method=fetch-annual-inspection-info hphm = %s, hpzl = %s", hphm, hpzl));
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            if(StringUtils.isEmpty(hpzl) || StringUtils.isEmpty(hphm)) {
                resultMap.put("resultId", "01");
                resultMap.put("resultMsg", "��������ƺ���ͺ�������");
            } else {
                String fzjg = FuncUtil.getFzjg(hphm);
                Eri eri = eriService.fetchByVehicle(fzjg, hphm.substring(1), hpzl);
                if (eri == null || eri.getClxxbfid() == null) {
                    resultMap.put("resultId", "02");
                    resultMap.put("resultMsg", "�ó���δ������ӱ�ʶ��������״�����ҵ��");
                } else {
                    Vehicle vehicle = vehicleService.synchronize(null, fzjg, hphm.substring(1), hpzl,null);
                    if (vehicle == null) {
                        resultMap.put("resultId", "03");
                        resultMap.put("resultMsg", "�޷���ȡ������Ϣ������ϵ����Ա");
                    } else {
                        resultMap.put("resultId", "00");
                        resultMap.put("eri", eri);
                        resultMap.put("bean", vehicleService.translate(vehicle));
                    }
                }
            }
        } catch (Exception e) {
            LOG.error("------> calling /be/eri.rfid?method=fetch-annual-inspection-info error", e);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", e.getMessage());
        }
        String result = GsonHelper.getGson().toJson(resultMap);
        LOG.info("------> calling /be/eri.rfid?method=fetch-annual-inspection-info result = " + result);
        return result;
    }

}
