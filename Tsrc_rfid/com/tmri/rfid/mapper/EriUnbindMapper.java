package com.tmri.rfid.mapper;

import com.tmri.rfid.bean.EriUnbind;
import com.tmri.rfid.ctrl.view.EriUnbindView;

import org.springframework.stereotype.Repository;

/**
 * Created by Joey on 2017/6/28.
 */
@Repository
public interface EriUnbindMapper extends BaseMapper<EriUnbindView> {

	void insert(EriUnbind eriUnbind);
	
	
}
