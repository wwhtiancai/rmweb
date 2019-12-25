package com.tmri.rfid.mapper;

import com.tmri.rfid.bean.ProductApplyDetail;

import java.util.List;

/**
 * Created by Joey on 2015/10/14.
 */
public interface ProductApplyDetailMapper extends BaseMapper<ProductApplyDetail> {

    int batchCreate(List<ProductApplyDetail> productApplyDetailList);
}
