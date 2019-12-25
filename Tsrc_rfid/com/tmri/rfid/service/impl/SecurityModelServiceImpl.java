package com.tmri.rfid.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tmri.rfid.util.EriUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmri.rfid.bean.SecurityModel;
import com.tmri.rfid.bean.SecurityModelLog;
import com.tmri.rfid.mapper.SecurityModelLogMapper;
import com.tmri.rfid.mapper.SecurityModelMapper;
import com.tmri.rfid.service.BaseServiceImpl;
import com.tmri.rfid.service.SecurityModelService;
import com.tmri.rfid.util.GsonHelper;
import com.tmri.rfid.util.MapUtilities;
import com.tmri.share.frm.service.LogService;
import com.tmri.share.frm.util.Constants;

/**
 * 
 * @author wuweihong
 * @date   2015-10-20
 */
@Service
public class SecurityModelServiceImpl extends BaseServiceImpl implements SecurityModelService{

	 private final static Logger LOG = LoggerFactory.getLogger(SecurityModelServiceImpl.class);
	
	@Autowired
	private SecurityModelMapper securityModelMapper;
	
	@Autowired
	private SecurityModelLogMapper securityModelLogMapper;

	@Autowired
	protected LogService logService;
	
	@Override
	public String initModel(String xh, String lx, String qulx, String xp1gy,
			String xp2gy, String cagysyh, String cagy, String xp1mybb,
			String xp1yhcxbb, String xp2yhcxbb, String stm32gjbb, Date ccrq,
			String dlbbb, String cshrq, String czr) throws Exception {
		// TODO Auto-generated method stub
		try{
			SecurityModel securityModel = new SecurityModel();
            securityModel.setXh(xh);
			securityModel.setLx(lx);
			securityModel.setQulx(qulx);
			securityModel.setXp1gy(xp1gy);
			securityModel.setXp2gy(xp2gy);
			securityModel.setCagysyh(cagysyh);
			securityModel.setCagy(cagy);
			securityModel.setXp1mybb(xp1mybb);
			securityModel.setXp1yhcxbb(xp1yhcxbb);
			securityModel.setXp2yhcxbb(xp2yhcxbb);
			securityModel.setStm32gjbb(stm32gjbb);
			securityModel.setCcrq(ccrq);
			securityModel.setDlbbb(dlbbb);
			securityModel.setCshrq(new Date());
			securityModel.setCzr(czr);
			int scode = securityModelMapper.create(securityModel);
			if(scode==0){
				return GsonHelper.getGson().toJson(MapUtilities.buildMap(
	                    "resultId", "99", "resultMsg", "安全模块更新入库失败"));
			}
			SecurityModelLog modelLog = new SecurityModelLog();
			modelLog.setId(securityModelLogMapper.generateSequence());
			modelLog.setXh(xh);
			modelLog.setLx(lx);
			modelLog.setQulx(qulx);
			modelLog.setXp1gy(xp1gy);
			modelLog.setXp2gy(xp2gy);
			modelLog.setCagysyh(cagysyh);
			modelLog.setCagy(cagy);
			modelLog.setXp1mybb(xp1mybb);
			modelLog.setXp1yhcxbb(xp1yhcxbb);
			modelLog.setXp2yhcxbb(xp2yhcxbb);
			modelLog.setStm32gjbb(stm32gjbb);
			modelLog.setCcrq(ccrq);
			modelLog.setDlbbb(dlbbb);
			modelLog.setCzlx(Constants.SECURY_MODEL_INIT);//初始化
			modelLog.setCzrq(new Date());
			modelLog.setCzr(czr);
			int code = securityModelLogMapper.create(modelLog);
			if(code==0){
				return GsonHelper.getGson().toJson(MapUtilities.buildMap(
	                    "resultId", "99", "resultMsg", "安全模块更新日志记录失败"));
			}
			 String result = GsonHelper.getGson().toJson(MapUtilities.buildMap("resultId", "00"));
	            LOG.info("------> call security-model.frm?method=init-model result = " + result);
	            return result;
		}catch (Exception e) {
			// TODO: handle exception
			 LOG.error("初始化安全模块失败", e);
	            return GsonHelper.getGson().toJson(MapUtilities.buildMap(
	                    "resultId", "99", "resultMsg", e.getMessage()));
		}
		
	}

    @Override
    public String generateSequence(String model, String type, int version) throws Exception{
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String sequenceName = "SEQ_SECURITY_MODEL__" + year;
        int sequence = -1;
        try {
            sequence = (int) securityModelMapper.generateSequence(sequenceName);
        } catch (Exception e) {
            LOG.debug("索引不存在", e);
        }
        if (sequence < 0) {
            securityModelMapper.createSequence(sequenceName);
            sequence = (int) securityModelMapper.generateSequence(sequenceName);
        }
        return model + type + EriUtil.appendZero(version + "", 2)
                + EriUtil.appendZero(year - 2014 + "", 3)
                + EriUtil.appendZero(sequence + "", 5);
    }

	@Override
	public String updateModel(String xh, String lx, String qulx, String xp1gy,
			String xp2gy, String cagysyh, String cagy, String xp1mybb,
			String xp1yhcxbb, String xp2yhcxbb, String stm32gjbb, Date ccrq,
			String dlbbb, String cshrq, String czr) {
		try{
			Map modelMap = new HashMap();
			modelMap.put("xh", xh);
			modelMap.put("lx", lx);
			modelMap.put("qulx", qulx);
			modelMap.put("xp1gy", xp1gy);
			modelMap.put("xp2gy", xp2gy);
			modelMap.put("cagysyh", cagysyh);
			modelMap.put("cagy", cagy);
			modelMap.put("xp1mybb", xp1mybb);
			modelMap.put("xp1yhcxbb", xp1yhcxbb);
			modelMap.put("xp2yhcxbb", xp2yhcxbb);
			modelMap.put("stm32gjbb", stm32gjbb);
			modelMap.put("ccrq", ccrq);
			modelMap.put("dlbbb", dlbbb);
			modelMap.put("scsjrq", new Date());
			modelMap.put("scsjczr", czr);
			int scode = securityModelMapper.updateByCondition(modelMap);
			if(scode==0){
				return GsonHelper.getGson().toJson(MapUtilities.buildMap(
	                    "resultId", "99", "resultMsg", "安全模块升级入库失败"));
			}
			SecurityModelLog modelLog = new SecurityModelLog();
			modelLog.setId(securityModelLogMapper.generateSequence());
			modelLog.setXh(xh);
			modelLog.setLx(lx);
			modelLog.setQulx(qulx);
			modelLog.setXp1gy(xp1gy);
			modelLog.setXp2gy(xp2gy);
			modelLog.setCagysyh(cagysyh);
			modelLog.setCagy(cagy);
			modelLog.setXp1mybb(xp1mybb);
			modelLog.setXp1yhcxbb(xp1yhcxbb);
			modelLog.setXp2yhcxbb(xp2yhcxbb);
			modelLog.setStm32gjbb(stm32gjbb);
			modelLog.setCcrq(ccrq);
			modelLog.setDlbbb(dlbbb);
			modelLog.setCzlx(Constants.SECURY_MODEL_UPDATE);//升级
			modelLog.setCzrq(new Date());
			modelLog.setCzr(czr);
			int code = securityModelLogMapper.create(modelLog);
			if(code==0){
				return GsonHelper.getGson().toJson(MapUtilities.buildMap(
	                    "resultId", "99", "resultMsg", "安全模块升级日志记录失败"));
			}
			String result = GsonHelper.getGson().toJson(MapUtilities.buildMap("resultId", "00"));
	            LOG.info("------> call security-model.frm?method=update-model result = " + result);
	            return result;
		}catch (Exception e) {
			// TODO: handle exception
			 LOG.error("升级安全模块失败", e);
	            return GsonHelper.getGson().toJson(MapUtilities.buildMap(
	                    "resultId", "99", "resultMsg", e.getMessage()));
		}
	}

	@Override
	public SecurityModel queryById(String xh) {
		// TODO Auto-generated method stub
		return securityModelMapper.queryById(xh);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PageList<SecurityModel> queryList(int pageIndex, int pageSize, Map<?, ?> condition)
			throws Exception {
		// TODO Auto-generated method stub
        return (PageList<SecurityModel>) getPageList(SecurityModelMapper.class, "queryList",
        		condition, pageIndex, pageSize);
	}
}
