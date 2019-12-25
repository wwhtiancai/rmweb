package com.tmri.rfid.service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tmri.rfid.bean.Eri;
import com.tmri.rfid.bean.EriCustomizeRecord;
import com.tmri.rfid.bean.ExWarehouseDetail;
import com.tmri.rfid.bean.ExWarehouseEntry;
import com.tmri.rfid.bean.Inventory;
import com.tmri.rfid.bean.WarehouseDetail;
import com.tmri.share.frm.bean.RmLog;
import com.tmri.share.frm.util.PageController;

import java.util.List;
import java.util.Map;

/**
 * Created by Joey on 2015/9/6.
 */
public interface InventoryService {

    int VEST_CAPACITY = 20; //������
    int BOX_CAPACITY = 20; //������
    String VEST_NUMBER_PREFIX = "1"; //��װ��ű�־��
    String BOX_NUMBER_PREFIX = "2"; //��װ�кű�־��

    int saveInventory(Inventory inventory) throws Exception;

    Inventory fetchByBzhh(String bzhh) throws Exception;

	//List<Inventory> queryList(String bzhh, String kh, String ssbm) throws Exception;

	PageList<Inventory> queryList(int pageIndex, int pageSize, Map<String,Object> condition) throws Exception;

    List<Inventory> checkInventory(String bzh, String ssbm) throws Exception;

    /**
     * ��ѯ�����ѱ�ռ�õĿ���
     * @param sf ʡ�ݴ���
     * @param qskh ��ʼ����
     * @param sl ������������Ϊ��λ��һ��ΪVEST_CAPACITY�У�һ��ΪBOX_CAPACITYƬ��,����ֹ����Ϊ qskh + VEST_CAPACITY * BOX_CAPACITY * sl
     * @return �����ѱ�ռ�õĿ��ţ����Ϊһ���б�ÿ����¼��Ϊ�������Ż򿨺ŶΣ����Ŷθ�ʽΪ��A-B��AΪ��ʼ���ţ�BΪ��������
     * @throws Exception
     */
    List<String> findOccupiedKh(String sf, int qskh, int sl) throws Exception;

    void reserve(String sf, int qskh, int sl, String rwh, String cpdm) throws Exception;
	int checkInventoryForIn(Inventory inventory) throws Exception;

    int inventoryIn(WarehouseDetail whd, String glbm) throws Exception;
    int inventoryInCorps(WarehouseDetail whd, String glbm) throws Exception;
    //int inventoryOut(ExWarehouseDetail ewhd) throws Exception;

    List<Inventory> fetchByCondition(Map condition);

    List<Inventory> fetchByCondition(Map condition, PageBounds pageBounds);

    boolean update(Inventory inventory) throws Exception;

    List<Map> findAvailableKh(String sf) throws RuntimeException;

	void inventoryOutByCkdh(String ckdh);
    String findLastFinished(String rwh) throws Exception;

	int fetchDqkc(String cpdm, String ssbm) throws Exception;

	int getSumByCondition(Inventory condition) throws Exception;

	int getPrepareOutNum(String cpdm, String ssbm) throws Exception;
	
	List<Inventory> queryByCkdh(String ckdh) throws Exception;

	List<Inventory> getXhByKh(String qskh, String zzkh, String cpdm,String zt) throws Exception;

    int queryKc(String glbm) throws Exception;
}
