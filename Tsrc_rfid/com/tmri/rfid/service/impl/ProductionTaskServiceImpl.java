package com.tmri.rfid.service.impl;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.tmri.rfid.bean.Eri;
import com.tmri.rfid.bean.Inventory;
import com.tmri.rfid.bean.ProductionTask;
import com.tmri.rfid.common.EriStatus;
import com.tmri.rfid.common.InventoryStatus;
import com.tmri.rfid.common.ProductionTaskStatus;
import com.tmri.rfid.ctrl.view.ProductionTaskView;
import com.tmri.rfid.mapper.ProductionTaskMapper;
import com.tmri.rfid.service.EriService;
import com.tmri.rfid.service.InventoryService;
import com.tmri.rfid.service.ProductService;
import com.tmri.rfid.service.ProductionTaskService;
import com.tmri.rfid.util.MapUtilities;
import com.tmri.share.frm.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Joey on 2015/10/16.
 */
@Service
public class ProductionTaskServiceImpl implements ProductionTaskService {

    @Autowired
    private ProductionTaskMapper productionTaskMapper;

    @Resource
    private InventoryService inventoryService;

    @Resource
    private EriService eriService;

    @Override
    public void create(String sf, int qskh, int sl, String jbr) throws Exception {
        ProductionTask productionTask = new ProductionTask();
        productionTask.setZt(0);
        productionTask.setJbr(jbr);
        productionTask.setRwh(generateRwh(productionTask));
        productionTask.setJhrq(new Date());
    }

    @Override
    @Transactional
    public void create(ProductionTask productionTask, String sf, int qskh, int sl, String cpdm) throws Exception {
        productionTask.setRwh(generateRwh(productionTask));
        productionTaskMapper.create(productionTask);
        inventoryService.reserve(sf, qskh, sl, productionTask.getRwh(), cpdm);
    }

    @Override
    public List<ProductionTaskView> fetchByCondition(Map condition) throws Exception {
        return productionTaskMapper.queryForView(condition);
    }

    @Override
    public List<ProductionTaskView> fetchByCondition(Map condition, int page, int pageSize) throws Exception {
        return productionTaskMapper.queryForView(condition, new PageBounds(page, pageSize));
    }

    @Override
    public ProductionTask fetchByRwh(String rwh) throws Exception {
        return productionTaskMapper.queryById(rwh);
    }

    @Override
    public boolean startTask(String rwh) throws Exception {
        return productionTaskMapper.updateByCondition(MapUtilities.buildMap(
                "cond_rwh", rwh, "cond_zt", ProductionTaskStatus.PLANNED.getStatus(),
                "zt", ProductionTaskStatus.PRODUCING.getStatus(), "ksrq", new Date())) > 0;
    }

    @Override
    public void finishTask(String rwh) throws Exception {
        ProductionTask productionTask = productionTaskMapper.queryById(rwh);
        if (productionTask == null) throw new Exception("未打到对应的生产任务");
        List<Inventory> inventories = inventoryService.fetchByCondition(
                MapUtilities.buildMap("rwh", productionTask.getRwh(),
                        "not_zt", InventoryStatus.PRODUCED.getStatus()));
        if (inventories != null && !inventories.isEmpty()) {
            throw new Exception("存在未完成的任务");
        }
        productionTask.setZt(ProductionTaskStatus.PRODUCED.getStatus());
        productionTask.setWcrq(new Date());
        productionTaskMapper.update(productionTask);
    }

    private String generateRwh(ProductionTask task) {
        return task.getRwdm() + DateUtil.formatDate(new Date(), "yyyyMMddHHmmss");
    }

    @Override
    public void finishSubTask(String bzhh) throws Exception {
        Inventory subTask = inventoryService.fetchByBzhh(bzhh);
        if (subTask == null) throw new Exception("未打到对应的包装盒信息");
        List<Eri> finishedEri = eriService.fetchByCondition(
                MapUtilities.buildMap("zt", EriStatus.AVAILABLE.getStatus(),
                        "qskh", subTask.getQskh(), "zzkh", subTask.getZzkh())
        );
        for (Eri eri : finishedEri) {
            if (StringUtils.isEmpty(eri.getBzhh())) {
                eri.setBzhh(bzhh);
                eriService.update(eri);
            }
        }
        if (finishedEri.size() < InventoryService.BOX_CAPACITY) {
            throw new RuntimeException("任务未全部完成");
        } else {
            subTask.setZt(InventoryStatus.PRODUCED.getStatus());
            inventoryService.update(subTask);
        }
    }

    public static void main(String args[]) {
        System.out.println(DateUtil.formatDate(new Date(), "yyyyMMddHHmmss"));
    }
}
