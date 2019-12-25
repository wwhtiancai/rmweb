package com.tmri.rfid.mapper;

import java.util.List;
import com.tmri.rfid.bean.MaterialInventory;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
 * Created by st on 2015/11/20.
 */
@Repository
public interface MaterialInventoryMapper extends BaseMapper<MaterialInventory> {
    
    void insertBatchInventory(@Param("materialInventorys") List<MaterialInventory> materialInventorys);
}
