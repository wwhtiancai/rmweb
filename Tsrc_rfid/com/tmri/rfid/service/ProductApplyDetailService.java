package com.tmri.rfid.service;

import com.tmri.rfid.bean.ProductApplyDetail;

import java.util.List;
import java.util.Map;

/**
 * Created by Joey on 2015/10/14.
 */
public interface ProductApplyDetailService {

    void create(String lydh, String[] bzh, String ssbm, String lybm) throws Exception;

    List<ProductApplyDetail> fetchByCondition(Map condition, int page, int pageSize) throws Exception;

    List<ProductApplyDetail> fetchByCondition(Map condition) throws Exception;

}
