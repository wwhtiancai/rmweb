package com.tmri.rfid.mapper;

import java.util.List;
import java.util.Map;

import com.tmri.rfid.bean.MaterialApply;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
 * Created by st on 2015/11/3.
 */
@Repository
public interface MaterialApplyMapper extends BaseMapper<MaterialApply> {

    String getMaxDgdh(@Param("dgdh") String dgdh);

    List<MaterialApply> queryList(Map<String, Object> map);
    
    int deleteByDgdh(@Param("dgdh") String dgdh);
    
    MaterialApply fetchByDGDH(@Param("dgdh") String dgdh);

	void deliveryByDgdh(@Param("dgdh") String dgdh);
	
	int getDgsl(@Param("dgdh") String dgdh);
	
	int getWrksl(@Param("dgdh") String dgdh);
	
	void updateYrksl(@Param("dgdh") String dgdh);
}
