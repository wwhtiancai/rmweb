package com.tmri.rfid.mapper;

import java.util.List;

import com.tmri.rfid.bean.EriOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author stone
 * @date 2016-3-16 ионГ11:04:41
 */
@Repository
public interface EriOperationMapper extends BaseMapper<EriOperation> {
	
	List<EriOperation> getCustomizeRecordByTid(String tid);

	List<EriOperation> queryRkByTid(String tid);

	List<EriOperation> queryCkByTid(String tid);

	List<EriOperation> queryZdrkByTid(String tid);

	List<EriOperation> queryLyByTid(String tid);

	List<EriOperation> queryBfByTid(@Param("tid") String tid);
}
