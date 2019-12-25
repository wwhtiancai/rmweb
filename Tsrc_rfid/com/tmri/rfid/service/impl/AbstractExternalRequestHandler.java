package com.tmri.rfid.service.impl;

import com.tmri.rfid.bean.ExternalRequest;
import com.tmri.rfid.service.ExternalRequestHandler;

/**
 * Created by Joey on 2016-03-25.
 */
public abstract class AbstractExternalRequestHandler implements ExternalRequestHandler {

    public abstract String getTaskName();

    public abstract void execute(ExternalRequest externalRequest) throws Exception;

    public void handle(ExternalRequest externalRequest) throws Exception{
        if (getTaskName().equals(externalRequest.getQqmc())) {
            execute(externalRequest);
        }
    }

}
