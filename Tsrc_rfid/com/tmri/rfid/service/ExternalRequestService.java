package com.tmri.rfid.service;

import com.tmri.rfid.bean.ExternalRequest;

import java.util.List;
import java.util.Map;

/**
 * Created by Joey on 2016-03-23.
 */
public interface ExternalRequestService {

    final static String DEVICE_REGISTRATION = "DEVICE_REGISTRATION";

    ExternalRequest create(String qqmc, String qqcs) throws Exception;

    boolean updateByCondition(Map condition) throws Exception;

    List<ExternalRequest> fetchByCondition(Map condition) throws Exception;

    boolean update(ExternalRequest externalRequest) throws Exception;

}
