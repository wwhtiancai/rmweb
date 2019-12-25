package com.tmri.rfid.service;

import com.tmri.rfid.common.CustomizeTaskType;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Joey on 2016/12/21.
 */
public interface StatisticsService {

    Map<String, Map> countCustomizationByYhdh(String qsrq, String zzrq, String yhdh);
    List<Map> countEri(String fzjg);

}
