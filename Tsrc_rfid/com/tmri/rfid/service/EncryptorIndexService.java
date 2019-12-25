package com.tmri.rfid.service;

import com.tmri.rfid.bean.EncryptorIndex;

/**
 * Created by Joey on 2016/1/18.
 */
public interface EncryptorIndexService {

    int INDEX_STATUS__AVAILABLE = 1;
    int INDEX_STATUS__OCCUPIED = 2;

    int fetch(String encryptorId) throws RuntimeException;

    EncryptorIndex lock(String encryptorId, int index) throws RuntimeException;

    boolean occupy(String encryptorId, int index);

    void release(String encryptorId, int index);

}
