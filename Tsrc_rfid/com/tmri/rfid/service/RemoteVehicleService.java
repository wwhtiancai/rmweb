package com.tmri.rfid.service;

import com.tmri.rfid.bean.Vehicle;
import com.tmri.rfid.bean.VehicleInfo;

/**
 * Created by Joey on 2016-03-08.
 */
public interface RemoteVehicleService {

    Vehicle fetch(String fzjg, String hphm, String hpzl) throws Exception;

    Vehicle fetch(String hphm, String hpzl) throws Exception;

    Vehicle fetch(String lsh) throws Exception;

    boolean upload(long customizeRecordId, String lsh, String xh, String dzbsxlh) throws Exception;

}
