package com.tmri.rfid.service;

import com.tmri.rfid.bean.EriCustomizeData;

import java.util.Map;

/**
 * Created by Joey on 2016-03-23.
 */
public interface EriCustomizeDataService {

    EriCustomizeData create(long qqxh, String aqmkxh, String tid, String data);

    EriCustomizeData fetchByXh(String xh);

    boolean update(EriCustomizeData eriCustomizeData);

    boolean updateByCondition(Map<String, String> condition);

    EriCustomizeData fetchByTidAndAqmkxh(String tid, String aqmkxh) throws Exception;

    boolean deleteByTid(String tid) throws Exception;
}
