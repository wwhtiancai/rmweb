package com.tmri.rfid.service.impl;

import com.tmri.rfid.bean.EncryptorIndex;
import com.tmri.rfid.mapper.EncryptorIndexMapper;
import com.tmri.rfid.property.ConfigProperty;
import com.tmri.rfid.property.RuntimeProperty;
import com.tmri.rfid.service.EncryptorIndexService;
import com.tmri.rfid.util.MapUtilities;
import oracle.jdbc.driver.OracleSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Joey on 2016/1/18.
 */
@Service
public class EncryptorIndexServiceImpl implements EncryptorIndexService {

    private final static Logger LOG = LoggerFactory.getLogger(EncryptorIndexServiceImpl.class);

    private Map<String, Map<Integer, Integer>> encryptorIndexStatus;

    private Map<String, List<Integer>> availableEncryptorIndex;

    @Resource
    private EncryptorIndexMapper encryptorIndexMapper;

    @Resource(name = "configProperty")
    private ConfigProperty configProperty;

    private synchronized boolean loadIndex(String encryptorId, int index) {
        LOG.debug("load index {}, {}", encryptorId, index);
            Map<Integer, Integer> subEncryptorIndexStatus = encryptorIndexStatus.get(encryptorId);
            if (subEncryptorIndexStatus == null) {
                subEncryptorIndexStatus = new HashMap<Integer, Integer>();
                encryptorIndexStatus.put(encryptorId, subEncryptorIndexStatus);
            }
            if (subEncryptorIndexStatus.get(index) == null || subEncryptorIndexStatus.get(index) == INDEX_STATUS__AVAILABLE) {
                subEncryptorIndexStatus.put(index, INDEX_STATUS__OCCUPIED);
                return true;
            } else {
                return false;
        }
    }

    private int fetch(String encryptorId, int numberOfTimes) {
        LOG.debug("fetch index {}, {}", encryptorId, numberOfTimes);
        if (encryptorIndexStatus == null) {
            encryptorIndexStatus = new HashMap<String, Map<Integer, Integer>>();
        }
        if (availableEncryptorIndex == null) {
            availableEncryptorIndex = new HashMap<String, List<Integer>>();
        }
        List<Integer> subEncryptorIndex = availableEncryptorIndex.get(encryptorId);
        if (subEncryptorIndex == null) {
            subEncryptorIndex = new ArrayList<Integer>();
            availableEncryptorIndex.put(encryptorId, subEncryptorIndex);
            List<EncryptorIndex> indexList = encryptorIndexMapper.queryByCondition(
                    MapUtilities.buildMap("encryptorId", encryptorId, "zt", EncryptorIndexService.INDEX_STATUS__AVAILABLE));
            for (EncryptorIndex encryptorIndex : indexList) {
                subEncryptorIndex.add(encryptorIndex.getIndex());
            }
        }
        int total = subEncryptorIndex.size();
        int index = subEncryptorIndex.get((int) Math.round(Math.random() * 100) % total);
        if (loadIndex(encryptorId, index)) {
            LOG.debug("fetch index return {}", index);
            return index;
        } else {
            if (numberOfTimes > configProperty.getMaxFetchEncryptorIndexTimes()) {
                return -1;
            } else {
                try {
                    Thread.sleep(configProperty.getFetchEncryptorIndexInterval());
                    return fetch(encryptorId, numberOfTimes + 1);
                } catch (Exception e) {
                    LOG.error("获取加密机索引号，等待" + configProperty.getFetchEncryptorIndexInterval() + "毫秒异常", e);
                    return -1;
                }
            }
        }
    }

    @Override
    public int fetch(String encryptorId) throws RuntimeException{
        int index = fetch(encryptorId, 1);
        if (index >= 0) {
            try {
                EncryptorIndex encryptorIndex = lock(encryptorId, index);
                if (encryptorIndex == null) {
                    throw new RuntimeException("查找可用加密机索引失败(" + encryptorId + "),未找到可用索引");
                } else if (encryptorIndex.getZt() == EncryptorIndexService.INDEX_STATUS__AVAILABLE) {
                    if (occupy(encryptorId, index))
                        return index;
                    else
                        throw new RuntimeException("查找可用加密机索引失败(" + encryptorId + "),未能锁定索引");
                } else {
                    throw new RuntimeException("查找可用加密机索引失败(" + encryptorId + "),实际查询到的索引状态不为可用");
                }
            } catch (Exception e) {
                throw new RuntimeException("查找可用加密机索引失败(" + encryptorId + "),无法锁定索引", e);
            }
        } else {
            throw new RuntimeException("查找可用加密机索引失败(" + encryptorId + "),本地未查询到可用索引");
        }
    }

    @Override
    public EncryptorIndex lock(String encryptorId, int index) throws RuntimeException {
        for (int i = 0; i < configProperty.getMaxFetchEncryptorIndexTimes(); i++) {
            try {
                List<EncryptorIndex> list = encryptorIndexMapper.lock(encryptorId, index);
                if (list != null && !list.isEmpty()) {
                    return list.get(0);
                }
            } catch (CannotAcquireLockException cale) {
                LOG.debug("当前索引已被占用，等待重试", cale);
            }
            try {
                Thread.sleep(configProperty.getFetchEncryptorIndexInterval());
            } catch (Exception e) {
                LOG.error("等待进程异常", e);
            }
        }
        return null;
    }

    @Override
    public boolean occupy(String encryptorId, int index) {
        return encryptorIndexMapper.updateByCondition(MapUtilities.buildMap(
                "cond_encryptor_id", encryptorId, "cond_index", index,
                "cond_zt", INDEX_STATUS__AVAILABLE, "zt", INDEX_STATUS__OCCUPIED,
                "lock_time", new Date()
        )) > 0;
    }

    public void release(String encryptorId, int index) {
        LOG.debug("release index {}, {}", encryptorId, index);
        Map<Integer, Integer> subEncryptorIndexStatus = encryptorIndexStatus.get(encryptorId);
        if (subEncryptorIndexStatus != null) {
            subEncryptorIndexStatus.put(index, INDEX_STATUS__AVAILABLE);
        }
        encryptorIndexMapper.updateByCondition(MapUtilities.buildMap(
                "cond_encryptor_id", encryptorId, "cond_index", index,
                "zt", INDEX_STATUS__AVAILABLE
        ));
    }
}
