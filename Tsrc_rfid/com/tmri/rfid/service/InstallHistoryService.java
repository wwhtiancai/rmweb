package com.tmri.rfid.service;

/**
 * Created by Joey on 2017/4/25.
 */
public interface InstallHistoryService {

    boolean create(String tid, String kh, String hphm, String hpzl, String azr);

}
