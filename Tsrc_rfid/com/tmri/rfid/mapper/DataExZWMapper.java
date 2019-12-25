package com.tmri.rfid.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.tmri.rfid.bean.DataExch;
import com.tmri.rfid.mapper.BaseMapper;

/**
 * 专网通用数据交换获取与存储
 * @author stone
 * @date 2016-2-5 下午2:10:36
 */
@Repository
public interface DataExZWMapper extends BaseMapper<DataExch>{

	void saveData(DataExch dataExch);
	void selectSaveBH(HashMap map);
	
	List<DataExch> getData(String sjlx);
	
	List<DataExch> getLoneData(@Param("sjlx")String sjlx);//单挑数据
	List<DataExch> getFirstData(@Param("sjlx")String sjlx);//首条数据
	List<DataExch> getSonData(@Param("sjlx")String sjlx, @Param("mid")String mid);//子数据

	void updateFlag(Map<?, ?> map);
}
