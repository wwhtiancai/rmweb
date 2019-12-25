package com.tmri.rfid.mapper;

import java.util.List;
import java.util.Map;

import com.tmri.rfid.bean.MaterialApply;
import com.tmri.rfid.bean.MaterialEri;
import com.tmri.rfid.bean.MaterialInventory;
import com.tmri.rfid.bean.MaterialWarehouse;
import com.tmri.rfid.bean.PackageCase;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
 * Created by st on 2015/11/6.
 */
@Repository
public interface MaterialWarehouseMapper extends BaseMapper<MaterialWarehouse> {

    String getMaxRkdh(@Param("rkdh") String rkdh);

    List<MaterialWarehouse> queryList(Map<String, Object> map);
    
    int deleteByRkdh(@Param("rkdh") String rkdh);
    
    void insertBatchEri(List<MaterialEri> materialEris);

	void insertBatchInventory(List<MaterialInventory> materialInventorys);

	List<PackageCase> queryWarehouseDetails(String rkdh);
}
