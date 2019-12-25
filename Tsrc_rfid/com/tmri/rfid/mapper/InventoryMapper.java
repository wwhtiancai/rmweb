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
     * ��ѯ�ڿ��Ŷ�qskh~zzkh�ڣ��Ƿ����б�ռ�õĿ���
     * @param qskh ��ʼ���ţ�����ʡ�ݴ��뿪ͷ
     * @param zzkh ��ֹ���ţ�����ʡ�ݴ��뿪ͷ
     * @return �����ѱ�ռ�õĿ��ţ����Ϊһ���б�ÿ����¼��Ϊ�������Ż򿨺ŶΣ����Ŷθ�ʽΪ��A-B��AΪ��ʼ���ţ�BΪ��������
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
