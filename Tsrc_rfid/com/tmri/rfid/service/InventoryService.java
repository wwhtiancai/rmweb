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

    int VEST_CAPACITY = 20; //箱容量
    int BOX_CAPACITY = 20; //盒容量
    String VEST_NUMBER_PREFIX = "1"; //包装箱号标志符
    String BOX_NUMBER_PREFIX = "2"; //包装盒号标志符

    int saveInventory(Inventory inventory) throws Exception;

    Inventory fetchByBzhh(String bzhh) throws Exception;

	//List<Inventory> queryList(String bzhh, String kh, String ssbm) throws Exception;

	PageList<Inventory> queryList(int pageIndex, int pageSize, Map<String,Object> condition) throws Exception;

    List<Inventory> checkInventory(String bzh, String ssbm) throws Exception;

    /**
     * 查询所有已被占用的卡号
     * @param sf 省份代码
     * @param qskh 起始卡号
     * @param sl 箱数量（以箱为单位，一箱为VEST_CAPACITY盒，一盒为BOX_CAPACITY片）,即终止卡号为 qskh + VEST_CAPACITY * BOX_CAPACITY * sl
     * @return 所有已被占用的卡号，结果为一个列表，每条记录可为单个卡号或卡号段，卡号段格式为：A-B，A为起始卡号，B为结束卡号
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
