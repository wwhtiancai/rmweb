package com.tmri.rfid.service.impl;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.tmri.rfid.bean.Inventory;
import com.tmri.rfid.bean.ProductApplyDetail;
import com.tmri.rfid.common.PackageType;
import com.tmri.rfid.mapper.ProductApplyDetailMapper;
import com.tmri.rfid.service.InventoryService;
import com.tmri.rfid.service.ProductApplyDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Joey on 2015/10/14.
 */
@Service
public class ProductApplyDetailServiceImpl implements ProductApplyDetailService {

    @Autowired
    private ProductApplyDetailMapper productApplyDetailMapper;

    @Resource
    private InventoryService inventoryService;

    @Override
    public void create(String lydh, String[] bzh, String ssbm, String lybm) throws Exception {
        for (int i = 0; i < bzh.length; i++) {
            List<Inventory> inventories = inventoryService.checkInventory(bzh[i], ssbm);
            if (bzh[i].startsWith(InventoryService.BOX_NUMBER_PREFIX)) {
                ProductApplyDetail productApplyDetail = new ProductApplyDetail();
                productApplyDetail.setLydh(lydh);
                productApplyDetail.setBzh(bzh[i]);
                productApplyDetailMapper.create(productApplyDetail);
                if (inventories == null || inventories.isEmpty()) throw new Exception("盒" + bzh[i] + "不存在");
                Inventory inventory = inventories.get(0);
                inventory.setSsbm(lybm);
                inventoryService.saveInventory(inventory);
            } else if (bzh[i].startsWith(InventoryService.VEST_NUMBER_PREFIX)) {
                ProductApplyDetail productApplyDetail = new ProductApplyDetail();
                productApplyDetail.setLydh(lydh);
                productApplyDetail.setBzh(bzh[i]);
                productApplyDetailMapper.create(productApplyDetail);
                for ( Inventory inventory : inventories) {
                    inventory.setSsbm(lybm);
                    inventoryService.saveInventory(inventory);
                }
            } else {
                throw new Exception("未知的包装类型");
            }
        }
    }

    @Override
    public List<ProductApplyDetail> fetchByCondition(Map condition, int page, int pageSize) throws Exception {
        return productApplyDetailMapper.queryByCondition(condition, new PageBounds(page, pageSize));
    }

    @Override
    public List<ProductApplyDetail> fetchByCondition(Map condition) throws Exception {
        return productApplyDetailMapper.queryByCondition(condition);
    }
}
