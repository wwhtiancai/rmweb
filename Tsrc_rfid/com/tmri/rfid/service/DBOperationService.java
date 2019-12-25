package com.tmri.rfid.service;

import com.tmri.rfid.bean.DBOperation;

import java.util.List;

/**
 * Created by Joey on 2015/11/10.
 */
public interface DBOperationService {

    List<DBOperation> fetchAll();

    List<DBOperation> fetchExecutable();

    void executeScripts() throws Exception;

}
