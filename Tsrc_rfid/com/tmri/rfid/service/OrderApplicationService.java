package com.tmri.rfid.service;

import com.tmri.rfid.bean.OrderApplication;
import com.tmri.rfid.ctrl.view.OrderApplicationView;

import java.util.List;
import java.util.Map;

/**
 * Created by Joey on 2015/9/23.
 */
public interface OrderApplicationService {

    /**
     * 创建订单，自动获取当前登录用户信息，包含申请人和申请部门
     * @param cpdm  产品代码
     * @param sl    数量
     * @param bz    备注
     * @return  OrderApplication    订单
     */
    OrderApplication create(String cpdm, int sl, String bz) throws Exception;

    void create(OrderApplication orderApplication) throws Exception;

    /**
     * 更新订单
     * @param orderApplication 订单
     * @throws Exception
     */
    void update(OrderApplication orderApplication) throws Exception;

    /**
     * 通过申请单号获取订单
     * @param sqdh  申请新单号
     * @return 订单
     */
    OrderApplicationView fetchBySQDH(String sqdh) throws Exception;

    List<OrderApplicationView> list(Map condition) throws Exception;

    List<OrderApplicationView> list(Map condition, int page, int pageSize) throws Exception;

    String generateOrderNumber(String bmdm) throws Exception;

    void delete(String sqdh) throws Exception;

	void updateYcksl(String sqdh) throws Exception;

    boolean audit(String sqdh, int zt, String shyj) throws Exception;

}
