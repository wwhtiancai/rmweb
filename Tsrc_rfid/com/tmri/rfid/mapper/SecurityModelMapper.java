package com.tmri.rfid.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.tmri.rfid.bean.SecurityModel;

/**
 * 
 * @author wuweihong
 *
 */
@Repository
public interface SecurityModelMapper extends BaseMapper<SecurityModel>{
	SecurityModel fetchSecModelByXh(String xh);

    long generateSequence(@Param(value="sequenceName") String sequenceName);
    
    List<SecurityModel> queryList(Map<String, Object> map);

    void createSequence(@Param(value = "sequenceName") String sequenceName);
}
