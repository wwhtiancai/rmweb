package com.tmri.rfid.service.impl;

import com.tmri.rfid.service.PlateNumberHandler;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Joey on 2015/9/25.
 */
@Service
public class PlateNumberHandlerManager {

    private Map<String, PlateNumberHandler> handlerMap;

    public PlateNumberHandlerManager() {};

    public PlateNumberHandlerManager(Map<String, PlateNumberHandler> handlerMap) {
        this.handlerMap = handlerMap;
    }

    public PlateNumberHandler getHandlerByPlateNumberType(String hpzl) {
        return handlerMap.get(hpzl);
    }

}
