package com.tmri.rfid.service;

import com.tmri.rfid.bean.ExternalRequest;

import java.util.Map;

/**
 * Created by Joey on 2016-03-25.
 */
public interface ExternalRequestHandler {

    void handle(ExternalRequest externalRequest) throws Exception;

    void generate(Map<Object, Object> params) throws Exception;
}
