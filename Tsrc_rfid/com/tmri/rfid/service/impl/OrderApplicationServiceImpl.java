package com.tmri.rfid.service.impl;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.tmri.rfid.bean.OrderApplication;
import com.tmri.rfid.common.OrderApplicationStatus;
import com.tmri.rfid.ctrl.view.OrderApplicationView;
import com.tmri.rfid.mapper.OrderApplicationMapper;
import com.tmri.rfid.service.OrderApplicationService;
import com.tmri.rfid.util.MapUtilities;
import com.tmri.share.frm.bean.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Joey on 2015/9/24.
 */
@Service
public class OrderApplicationServiceImpl implements OrderApplicationService {

    @Autowired
    private OrderApplicationMapper orderApplicationMapper;

    @Override
    public OrderApplication create(String cpdm, int sl, String bz) throws Exception {
        return null;
    }

    @Override
    public void create(OrderApplication orderApplication) throws Exception {
        orderApplication.setSqrq(new Date());
        orderApplicationMapper.create(orderApplication);
    }

    @Override
    public void update(OrderApplication orderApplication) throws Exception {
        orderApplicationMapper.update(orderApplication);

    }

    @Override
    public OrderApplicationView fetchBySQDH(String sqdh) throws Exception {
        List<OrderApplicationView> orderApplicationViews =
                list(MapUtilities.buildMap("sqdh", sqdh));
        if (orderApplicationViews == null || orderApplicationViews.isEmpty()) return null;
        else return orderApplicationViews.get(0);
    }

    @Override
    public List<OrderApplicationView> list(Map condition) throws Exception {
        return list(condition, 1, 30);
    }

    @Override
    public List<OrderApplicationView> list(Map condition, int page, int pageSize) throws Exception {
        return orderApplicationMapper.queryForView(condition, new PageBounds(page, pageSize));
    }

    @Override
    public String generateOrderNumber(String bmdm) throws Exception {
        Calendar now = Calendar.getInstance();
        String secStr = Long.toHexString(now.getTimeInMillis() / 1000);
        for ( int i = 0; i < 9 - secStr.length(); i++) {
            secStr = "0" + secStr;
        }
        String random = UUID.randomUUID().toString();
        return (bmdm + secStr + random.substring(random.length() - 3)).toUpperCase();
    }

    public void delete(String sqdh) throws Exception {
        orderApplicationMapper.updateByCondition(MapUtilities.buildMap("cond_sqdh", sqdh,
                "zt", OrderApplicationStatus.CANCEL.getStatus()));
    }
    
    @Override
    public void updateYcksl(String sqdh) throws Exception {
    	orderApplicationMapper.updateYcksl(sqdh);
    }

    @Override
    public boolean audit(String sqdh, int zt, String shyj) throws Exception {
        return orderApplicationMapper.updateByCondition(MapUtilities.buildMap("cond_sqdh", sqdh,
                "zt", zt, "shyj", shyj)) > 0;
    }
}
