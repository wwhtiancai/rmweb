package com.tmri.rfid.service;

import com.tmri.rfid.bean.EriReaderWriterActivation;

/**
 * Created by Joey on 2016-03-01.
 */
public interface EriReaderWriterActivationService {

    boolean create(EriReaderWriterActivation eriReaderWriterActivation) throws Exception;

    boolean update(EriReaderWriterActivation eriReaderWriterActivation) throws Exception;

    EriReaderWriterActivation fetchInProgress(String dxqxh) throws Exception;

    boolean reset(String dxqxh) throws Exception;

}
