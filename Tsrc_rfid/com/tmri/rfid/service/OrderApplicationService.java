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
     * �����������Զ���ȡ��ǰ��¼�û���Ϣ�����������˺����벿��
     * @param cpdm  ��Ʒ����
     * @param sl    ����
     * @param bz    ��ע
     * @return  OrderApplication    ����
     */
    OrderApplication create(String cpdm, int sl, String bz) throws Exception;

    void create(OrderApplication orderApplication) throws Exception;

    /**
     * ���¶���
     * @param orderApplication ����
     * @throws Exception
     */
    void update(OrderApplication orderApplication) throws Exception;

    /**
     * ͨ�����뵥�Ż�ȡ����
     * @param sqdh  �����µ���
     * @return ����
     */
    OrderApplicationView fetchBySQDH(String sqdh) throws Exception;

    List<OrderApplicationView> list(Map condition) throws Exception;

    List<OrderApplicationView> list(Map condition, int page, int pageSize) throws Exception;

    String generateOrderNumber(String bmdm) throws Exception;

    void delete(String sqdh) throws Exception;

	void updateYcksl(String sqdh) throws Exception;

    boolean audit(String sqdh, int zt, String shyj) throws Exception;

}
