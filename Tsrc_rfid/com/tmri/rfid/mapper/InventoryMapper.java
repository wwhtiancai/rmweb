package com.tmri.rfid.mapper;

import java.util.List;
import java.util.Map;

import com.tmri.rfid.bean.Inventory;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Joey on 2015/9/14.
 */
@Repository
public interface InventoryMapper extends BaseMapper<Inventory>{

    Inventory fetchByBzhh(String bzhh);
    
    int deleteByBzhh(String bzhh);
    
    int inventoryInByXh(@Param("bzxh") String bzxh,@Param("glbm") String glbm,@Param("zt") int zt);
    int inventoryInByHh(@Param("bzhh") String bzhh,@Param("glbm") String glbm,@Param("zt") int zt);
    
    //int inventoryOutByXh(String bzxh);
    //int inventoryOutByHh(String bzhh);
    
    List<Inventory> queryList(Map<Object, Object> map);

    List<Inventory> queryByBZXH(@Param("glbm") String glbm, @Param("bzxh") String bzxh);

    List<Inventory> fetchByJjh(String jjh);
    /**
     * 查询在卡号段qskh~zzkh内，是否已有被占用的卡号
     * @param qskh 起始卡号，且以省份代码开头
     * @param zzkh 终止卡号，且以省份代码开头
     * @return 所有已被占用的卡号，结果为一个列表，每条记录可为单个卡号或卡号段，卡号段格式为：A-B，A为起始卡号，B为结束卡号
     */
    List<String> queryOccupied(@Param("sf") String sf, @Param("qskh") String qskh, @Param("zzkh") String zzkh);

    void reserve(Map condition);


    List<Map> queryAvailableKh(@Param("sf") String sf, @Param("capacity") int capacity);

	void inventoryOutByCkdh(String ckdh);

    String queryLastFinished(@Param("rwh") String rwh, @Param("capacity") int capacity);

	int getSumByCondition(Inventory condition);

	int getPrepareOutNum(@Param("cpdm")String cpdm, @Param("ssbm")String ssbm);

	List<Inventory> queryByCkdh(String ckdh);
	
	List<Inventory> getXhByKh(@Param("qskh")String qskh, @Param("zzkh")String zzkh, @Param("cpdm")String cpdm, @Param("zt")String zt);

    int queryKc(@Param("glbm") String glbm);
}
