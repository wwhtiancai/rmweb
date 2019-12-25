package com.tmri.rfid.service.impl;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.tmri.rfid.bean.ProductApply;
import com.tmri.rfid.ctrl.view.ProductApplyView;
import com.tmri.rfid.mapper.ProductApplyMapper;
import com.tmri.rfid.service.ProductApplyDetailService;
import com.tmri.rfid.service.ProductApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Joey on 2015/10/11.
 */
@Service
public class ProductApplyServiceImpl implements ProductApplyService {

    @Autowired
    private ProductApplyMapper productApplyMapper;

    @Resource
    private ProductApplyDetailService productApplyDetailService;

    @Transactional(value = "transactionManager")
    public void create(ProductApply productApply, String[] bzh) throws Exception {
        productApply.setLyrq(new Date());
        productApply.setLydh(generateEntryNumber(productApply.getGlbm()));
        productApplyMapper.create(productApply);
        productApplyDetailService.create(productApply.getLydh(), bzh, productApply.getGlbm(), productApply.getLybm());
    }

    @Override
    public List<ProductApply> list(Map condition) throws Exception {
        return list(condition, 1, 30);
    }

    @Override
    public List<ProductApply> list(Map condition, int page, int pageSize) throws Exception {
        return productApplyMapper.queryByCondition(condition, new PageBounds(page, pageSize));
    }

    @Override
    public String generateEntryNumber(String glbm) throws Exception {
        Calendar now = Calendar.getInstance();
        String secStr = Long.toHexString(now.getTimeInMillis() / 1000);
        for ( int i = 0; i < 9 - secStr.length(); i++) {
            secStr = "0" + secStr;
        }
        String random = UUID.randomUUID().toString();
        return (glbm + secStr + random.substring(random.length() - 3)).toLowerCase();
    }

    @Override
    public ProductApply fetchByLYDH(String lydh) throws Exception {
        return productApplyMapper.queryById(lydh);
    }

}
