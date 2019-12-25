package com.tmri.rfid.mapper;

import java.util.List;
import com.tmri.rfid.bean.MaterialEri;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
 * Created by st on 2015/11/20.
 */
@Repository
public interface MaterialEriMapper extends BaseMapper<MaterialEri> {
    
    void insertBatchEri(@Param("materialEris") List<MaterialEri> materialEris);
}
