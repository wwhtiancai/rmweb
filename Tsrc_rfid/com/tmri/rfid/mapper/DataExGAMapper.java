package com.tmri.rfid.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.tmri.rfid.bean.DataExch;
import com.tmri.rfid.mapper.BaseMapper;

/**
 * ������ͨ�����ݽ�����ȡ��洢
 * @author stone
 * @date 2016-1-18 ����3:52:10
 */
@Repository
public interface DataExGAMapper extends BaseMapper<DataExch>{

	void saveData(DataExch dataExch);
	void selectSaveBH(HashMap map);
	
	List<DataExch> getData(String sjlx);

	List<DataExch> getLoneData(@Param("sjlx")String sjlx);//��������
	List<DataExch> getFirstData(@Param("sjlx")String sjlx);//��������
	List<DataExch> getSonData(@Param("sjlx")String sjlx, @Param("mid")String mid);//������

	void updateFlag(Map<?, ?> map);
	
}
