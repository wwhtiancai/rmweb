package com.tmri.rfid.mapper;

import org.springframework.stereotype.Repository;
import com.tmri.rfid.bean.EriPastedLog;

@Repository
public interface EriPastedMapper extends BaseMapper<EriPastedLog>{

	public void save(EriPastedLog EriPasted);
    
	public EriPastedLog getByTid(String tid);
}
