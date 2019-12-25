package com.tmri.rfid.service.impl;

import com.tmri.framework.util.WebServiceHelper;
import com.tmri.framework.ws.client.bean.ReturnInfo;
import com.tmri.rfid.bean.RemoteVehicle;
import com.tmri.rfid.bean.Vehicle;
import com.tmri.rfid.common.OperationResult;
import com.tmri.rfid.property.ConfigProperty;
import com.tmri.rfid.property.RuntimeProperty;
import com.tmri.rfid.service.OperationLogService;
import com.tmri.rfid.service.RemoteVehicleService;
import com.tmri.rfid.webservice.TmriJaxRpcOutNewAccessServiceStub;
import org.apache.axis2.AxisFault;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Joey on 2016-03-08.
 */
@Service
public class RemoteVehicleServiceImpl implements RemoteVehicleService {

    private final static Logger LOG = LoggerFactory.getLogger(RemoteVehicleServiceImpl.class);

    @Resource
    private TmriJaxRpcOutNewAccessServiceStub tmriJaxRpcOutNewAccessServiceStub;

    @Resource(name = "runtimeProperty")
    private RuntimeProperty runtimeProperty;

    @Resource
    private OperationLogService operationLogService;

    public Vehicle fetch(String fzjg, String hphm, String hpzl) throws Exception {
        if (StringUtils.isNotEmpty(fzjg)) {
            return fetch(fzjg.substring(0, 1) + hphm, hpzl);
        } else {
            return fetch(hphm, hpzl);
        }
    }

    @Override
    public Vehicle fetch(String hphm, String hpzl) throws Exception {
        LOG.info(String.format("------> calling RemoteVehicleService.fetch hphm=%s, hpzl=%s", hphm, hpzl));
        TmriJaxRpcOutNewAccessServiceStub.QueryObjectOut qoo = new TmriJaxRpcOutNewAccessServiceStub.QueryObjectOut();
        qoo.setJkxlh(runtimeProperty.getRemoteFetchVehicleSerialNo());
        Map<String, String> condition = new HashMap<String, String>();
        condition.put("hphm", hphm);
        condition.put("hpzl", hpzl);
        qoo.setXtlb("01");
        qoo.setJkid("01CE0");
        qoo.setDwjgdm(runtimeProperty.getRemoteFetchVehicleGlbm());
        qoo.setZdbs(runtimeProperty.getRemoteFetchVehicleIp());
        String queryXmlDoc = WebServiceHelper.getXMLFileHead() +
                WebServiceHelper.map2XMLUTF8(condition, "QueryCondition", "")
                + WebServiceHelper.getXMLFileFoot();
        qoo.setUTF8XmlDoc(queryXmlDoc);
        try {
            TmriJaxRpcOutNewAccessServiceStub.QueryObjectOutResponse response = tmriJaxRpcOutNewAccessServiceStub.queryObjectOut(qoo);
            LOG.info("------> calling remote query response = " + WebServiceHelper.decodeUTF8(response.getQueryObjectOutReturn()));
            ReturnInfo returnInfo = WebServiceHelper.parseResponse(
                    WebServiceHelper.decodeUTF8(response.getQueryObjectOutReturn()), RemoteVehicle.class);
            if ("1".equals(returnInfo.getCode())) {
                if (returnInfo.getRowNum() == 0) {
                    return null;
                } else if (returnInfo.getRowNum() == 1) {
                    return translate((RemoteVehicle)returnInfo.getResultList().get(0));
                } else {
                    throw new RuntimeException("查询到多条车辆信息");
                }
            } else {
                throw new RuntimeException("查询车辆信息失败，" + returnInfo.getMessage());
            }
        } finally {
            try {
                tmriJaxRpcOutNewAccessServiceStub._getServiceClient().cleanupTransport();
            } catch (Exception e ){
                LOG.error("clean up transport fail", e);
            }
        }

    }

    @Override
    public Vehicle fetch(String lsh) throws Exception {
        LOG.info(String.format("------> calling RemoteVehicleService.fetch lsh=%s", lsh));
        TmriJaxRpcOutNewAccessServiceStub.QueryObjectOut qoo = new TmriJaxRpcOutNewAccessServiceStub.QueryObjectOut();
        qoo.setJkxlh(runtimeProperty.getRemoteFetchVehicleSerialNo());
        Map<String, String> condition = new HashMap<String, String>();
        condition.put("lsh", lsh);
        qoo.setXtlb("01");
        qoo.setJkid("01CE0");
        qoo.setDwjgdm(runtimeProperty.getRemoteFetchVehicleGlbm());
        qoo.setZdbs(runtimeProperty.getRemoteFetchVehicleIp());
        String queryXmlDoc = WebServiceHelper.getXMLFileHead() +
                WebServiceHelper.map2XMLUTF8(condition, "QueryCondition", "")
                + WebServiceHelper.getXMLFileFoot();
        qoo.setUTF8XmlDoc(queryXmlDoc);
        try {
            TmriJaxRpcOutNewAccessServiceStub.QueryObjectOutResponse response = tmriJaxRpcOutNewAccessServiceStub.queryObjectOut(qoo);
            LOG.info("------> calling remote query response = " + WebServiceHelper.decodeUTF8(response.getQueryObjectOutReturn()));
            ReturnInfo returnInfo = WebServiceHelper.parseResponse(
                    WebServiceHelper.decodeUTF8(response.getQueryObjectOutReturn()), RemoteVehicle.class);
            if ("1".equals(returnInfo.getCode())) {
                if (returnInfo.getRowNum() == 0) {
                    return null;
                } else if (returnInfo.getRowNum() == 1) {
                    return translate((RemoteVehicle)returnInfo.getResultList().get(0));
                } else {
                    throw new RuntimeException("查询到多条车辆信息");
                }
            } else {
                throw new RuntimeException("查询车辆信息失败，" + returnInfo.getMessage());
            }
        } finally {
            try {
                tmriJaxRpcOutNewAccessServiceStub._getServiceClient().cleanupTransport();
            } catch (Exception e ){
                LOG.error("clean up transport fail", e);
            }
        }
    }

    public boolean upload(long customizeRecordId, String lsh, String xh, String dzbsxlh) {
        try {
            LOG.info(String.format("remote upload lsh = %s, xh = %s, dzbsxlh = %s", lsh, xh, dzbsxlh));
            TmriJaxRpcOutNewAccessServiceStub.WriteObjectOut woo = new TmriJaxRpcOutNewAccessServiceStub.WriteObjectOut();
            woo.setJkxlh(runtimeProperty.getRemoteFetchVehicleSerialNo());
            Map<String, String> condition = new HashMap<String, String>();
            if (StringUtils.isNotEmpty(xh)) {
                condition.put("xh", xh);
            } else if (StringUtils.isNotEmpty(lsh)) {
                condition.put("lsh", lsh);
            } else {
                operationLogService.log("REMOTE_UPLOAD_CUSTOMIZE_RESULT", customizeRecordId, "流水号和序号同时为空", OperationResult.FAIL);
                return false;
            }
            condition.put("dzbsxlh", dzbsxlh);
            woo.setXtlb("01");
            woo.setJkid("01CE5");
            woo.setDwjgdm(runtimeProperty.getRemoteFetchVehicleGlbm());
            woo.setZdbs(runtimeProperty.getRemoteFetchVehicleIp());
            String writeXmlDoc = WebServiceHelper.getXMLFileHead() +
                    WebServiceHelper.map2XMLUTF8(condition, "vehpara", "")
                    + WebServiceHelper.getXMLFileFoot();
            woo.setUTF8XmlDoc(writeXmlDoc);
            TmriJaxRpcOutNewAccessServiceStub.WriteObjectOutResponse response = tmriJaxRpcOutNewAccessServiceStub.writeObjectOut(woo);
            ReturnInfo returnInfo = WebServiceHelper.parseResponse(
                    WebServiceHelper.decodeUTF8(response.getWriteObjectOutReturn()), Vehicle.class);
            if ("1".equals(returnInfo.getCode())) {
                LOG.info("remote upload success");
                return true;
            } else {
                operationLogService.log("REMOTE_UPLOAD_CUSTOMIZE_RESULT", customizeRecordId,
                        String.format("写入数据失败，code = %s, message = %s", returnInfo.getCode(), returnInfo.getMessage()), OperationResult.FAIL);
                return false;
            }
        } catch (Exception e) {
            operationLogService.log("REMOTE_UPLOAD_CUSTOMIZE_RESULT", customizeRecordId,
                    e.getMessage(), OperationResult.FAIL);
            return false;
        } finally {
            try {
                tmriJaxRpcOutNewAccessServiceStub._getServiceClient().cleanupTransport();
            } catch (Exception e ){
                LOG.error("clean up transport fail", e);
            }
        }
    }

    private Vehicle translate(RemoteVehicle remoteVehicle) throws Exception{
        Vehicle vehicle = new Vehicle();
        BeanUtils.copyProperties(vehicle, remoteVehicle);
        vehicle.setJdcxh(remoteVehicle.getXh());
        return remoteVehicle == null ? null : vehicle;
    }
}
