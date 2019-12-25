package com.tmri.rfid.mapper;

import java.util.List;
import java.util.Map;

import com.tmri.rfid.bean.ExWarehouseDetail;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by st on 2015/10/10.
 */
@Repository
public interface ExWarehouseDetailMapper extends BaseMapper<ExWarehouseDetail>{

	ExWarehouseDetail fetchByDetailId(@Param("dw") String dw, @Param("bzhm") String bzhm, @Param("ckdh") String ckdh);
	
	List<ExWarehouseDetail> queryList(Map<String, Object> map);
	
	List<ExWarehouseDetail> queryListByCkdh(@Param("ckdh") String ckdh);

	int deleteByBzhh(String bzhh);
	
	int deleteByCkdh(String ckdh);
}
