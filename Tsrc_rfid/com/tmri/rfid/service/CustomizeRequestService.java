package com.tmri.rfid.service;

import com.tmri.rfid.bean.EriCustomizeRequest;

import java.util.List;
import java.util.Map;

/**
 * Created by Joey on 2016-03-23.
 */
public interface CustomizeRequestService {

    boolean create(EriCustomizeRequest eriCustomizeRequest) throws Exception;

    EriCustomizeRequest create(String lsh, String hphm, String hpzl, String fzjg, String tid, String kh, int ywlx) throws Exception;

    boolean updateCustomizeResult(long qqxh, String aqmkxh, int result, String sbyy) throws Exception;

    List<EriCustomizeRequest> fetchByCondition(Map condition);

    List<EriCustomizeRequest> fetchByCondition(Map condition, int page, int pageSize);

    void handle(EriCustomizeRequest eriCustomizeRequest) throws Exception;

    boolean update(EriCustomizeRequest customizeRequest);

    EriCustomizeRequest fetchActiveByTid(String tid);

    boolean checkRequestStatus(long qqxh) throws Exception;

    EriCustomizeRequest fetchLast(String tid);

    EriCustomizeRequest fetchByXh(long qqxh);

}
