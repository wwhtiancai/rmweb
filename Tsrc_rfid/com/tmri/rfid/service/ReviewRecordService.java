package com.tmri.rfid.service;

import com.tmri.rfid.bean.ReviewRecord;

import java.util.List;

/**
 * Created by Joey on 2016/10/11.
 */
public interface ReviewRecordService {

    void approve(String ywdm, Long ywxh, String yj);

    void reject(String ywdm, Long ywxh, String yj);

    List<ReviewRecord> fetch(String ywdm, Long ywxh);

}
