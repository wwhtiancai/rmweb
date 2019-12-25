package com.tmri.rfid.service;

import com.tmri.rfid.bean.ProductApply;
import com.tmri.rfid.ctrl.view.ProductApplyView;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Joey on 2015/10/11.
 */
@Service
public interface ProductApplyService {

    void create(ProductApply productApply, String[] bzh) throws Exception;

    List<ProductApply> list(Map condition) throws Exception;

    List<ProductApply> list(Map condition, int page, int pageSize) throws Exception;

    String generateEntryNumber(String glbm) throws Exception;

    ProductApply fetchByLYDH(String lydh) throws Exception;

}
