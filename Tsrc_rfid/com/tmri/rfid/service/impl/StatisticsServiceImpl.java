package com.tmri.rfid.service.impl;

import com.tmri.rfid.common.CustomizeTaskType;
import com.tmri.rfid.mapper.StatisticsMapper;
import com.tmri.rfid.service.StatisticsService;
import com.tmri.rfid.util.MapUtilities;
import com.tmri.share.frm.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.*;

/**
 * Created by Joey on 2016/12/21.
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private StatisticsMapper statisticsMapper;

    @Override
    public Map<String, Map> countCustomizationByYhdh(String qsrq, String zzrq, String yhdh) {
        Map<String, Object> condition = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(qsrq)) {
            condition.put("qsrq", DateUtil.formatDate(qsrq, "yyyy-MM-dd"));
        } else {
            condition.put("qsrq", DateUtil.formatDate(new Date(), "yyyy-MM-dd"));
        }
        if (StringUtils.isNotEmpty(zzrq)) {
            condition.put("zzrq", DateUtil.formatDate(zzrq, "yyyy-MM-dd"));
        } else {
            condition.put("zzrq", DateUtil.formatDate(new Date(), "yyyy-MM-dd"));
        }
        if (StringUtils.isNotEmpty(yhdh)) {
            condition.put("yhdh", yhdh);
        }
        List<Map> queryResult = statisticsMapper.countCustomizationByYhdh(condition);
        Map<String, Map> resultMap = new LinkedHashMap<String, Map>();
        for (Map record : queryResult) {
            Map recordMap = resultMap.get(record.get("CZR"));
            if (recordMap == null) {
                recordMap = new HashMap();
                resultMap.put(String.valueOf(record.get("CZR")), recordMap);
            }
            recordMap.put("YWLX_" + record.get("YWLX"), Integer.valueOf(String.valueOf(record.get("SL"))));
        }
        for (Map.Entry<String, Map> resultEntry : resultMap.entrySet()) {
            int total = 0;
            for (Object sl : resultEntry.getValue().values()) {
                total += (Integer)sl;
            }
            resultEntry.getValue().put("TOTAL", total);
        }

        return resultMap;
    }

    @Override
    public  List<Map> countEri(String fzjg) {
        Map<String, Object> condition = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(fzjg)) {
            condition.put("fzjg", fzjg);
        }
        List<Map> queryResult = statisticsMapper.countEri(condition);
        Map<String, String> resultMap = new LinkedHashMap<String, String>();
        Long  zksl = 0L;
        for (Map record : queryResult) {
            zksl += ((BigDecimal) record.get("ZKSL")).longValue();
        }
        resultMap.put("ZKSL", zksl + "");
        resultMap.put("CLLX", "×Ü¼Æ");
        queryResult.add(resultMap);

        return queryResult;
    }
}
