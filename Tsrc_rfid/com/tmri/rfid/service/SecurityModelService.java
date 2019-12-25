package com.tmri.rfid.service;

import java.util.Date;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tmri.rfid.bean.SecurityModel;

/**
 * 
 * @author wuweihong
 * @date   2015-10-20
 */
public interface SecurityModelService {

    String initModel(String xh, String lx, String qulx, String xp1gy, String xp2gy, String cagysyh, String cagy, String xp1mybb, String xp1yhcxbb, String xp2yhcxbb, String stm32gjbb, Date ccrq, String dlbbb, String cshrq, String czr)throws Exception;

    String generateSequence(String model, String type, int version) throws Exception;
    
    String updateModel(String xh, String lx, String qulx, String xp1gy, String xp2gy, String cagysyh, String cagy, String xp1mybb, String xp1yhcxbb, String xp2yhcxbb, String stm32gjbb, Date ccrq, String dlbbb, String cshrq, String czr);
    
    SecurityModel queryById(String xh);
    
    PageList<SecurityModel> queryList(int pageIndex, int pageSize,Map<?, ?> condition) throws Exception;

}
