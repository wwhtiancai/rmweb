package com.tmri.rfid.mapper;

import java.util.List;
import java.util.Map;

import com.tmri.rfid.bean.Eri;
import com.tmri.rfid.common.EriStatus;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Joey on 2015/9/14.
 */
@Repository
public interface EriMapper extends BaseMapper<Eri>{

    Long getCidSequence(String provinceCode);

    List<Eri> queryByRegion(@Param("qskh") String qskh, @Param("zzkh") String zzkh);
    
    void updateByInventoryId(@Param("bzhh") String bzhh, @Param("glbm") String glbm);

    void updateByXh(@Param("bzxh") String bzxh, @Param("glbm") String glbm);

	int fetchDqkc(@Param("cpdm") String cpdm,@Param("ssbm") String ssbm);
	
	List<Eri> queryByCkdh(@Param("ckdh") String ckdh);
	
	void warehouse(Eri eri);

    List<Eri> fetchByVehicle(Map<Object, Object> vehicleInfo);
	
	List<Eri> queryByHphm(@Param("hphm") String hphm);

    Eri fetchByJdcxh(@Param("jdcxh") String jdcxh);

	void unbindEri(@Param("tid") String tid);
}
