package com.tmri.tfc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.bean.RmLog;
import com.tmri.share.frm.dao.GSysparaCodeDao;
import com.tmri.share.frm.dao.SLogDao;
import com.tmri.share.frm.service.GDepartmentService;
import com.tmri.share.frm.util.FuncUtil;
import com.tmri.share.frm.util.PageController;
import com.tmri.share.ora.bean.DbResult;
import com.tmri.tfc.bean.TfcSpecial;
import com.tmri.tfc.dao.SpecialVehDao;
import com.tmri.tfc.service.SpecialVehService;

/**
@author Yangzm
@data: 2014-06-04  time: œ¬ŒÁ04:44:37

 */
@Service

public class SpecialVehServiceImpl implements SpecialVehService {
	@Autowired
	private SpecialVehDao  specialVehDao;
	@Autowired
	private GSysparaCodeDao gSysparaCodeDao;
	@Autowired
	private SLogDao sLogDao;
	@Autowired
	private GDepartmentService gDepartmentService;
	
	public List<TfcSpecial> getSpecialVehList(TfcSpecial sv,RmLog log,PageController controller) throws Exception {
		
		List<TfcSpecial> list = this.specialVehDao.getSpecialVehList(sv, controller);
		this.sLogDao.setRmLog(log);
		this.sLogDao.saveRmLog();
		for(TfcSpecial bean:list) {
			translate(bean);
		}
		return list;
	}
	
	public TfcSpecial getSpecial(String xh) throws Exception{	
		TfcSpecial bean = this.specialVehDao.getSpecial(xh);
		translate(bean);
		return bean;
	}
	
	private TfcSpecial translate(TfcSpecial bean) throws Exception{
		if (bean==null)
			return bean;
		bean.setHpzlmc(this.gSysparaCodeDao.getHpzlmc(bean.getHpzl()));
		bean.setGlbmmc(gDepartmentService.getDepartmentBmmc(bean.getGlbm()));
		Department dept = gDepartmentService.getDepartment(bean.getGlbm());
		if (dept!=null){
			bean.setBmjb(dept.getBmjb());
		}
		return bean;
	}
	
	public DbResult saveSpecial(TfcSpecial bean,RmLog log) throws Exception {
		DbResult result = this.specialVehDao.setSpecial(bean);
		if(result.getCode()==1){
			this.sLogDao.setRmLog(log);
			if(result.getCode() == 1){
				String hpxx = FuncUtil.encrypt(bean.getHpzl()+bean.getHphm());
				result = this.specialVehDao.saveSpecialVeh(hpxx);
				if(result.getCode()!=1){
					throw new RuntimeException("“Ï≥££∫"+result.getCode()+"£¨¥ÌŒÛ£∫"+result.getMsg());
				}
			}
		}
		return result;
	}
	
	
	public DbResult delSpecial(String  xh,RmLog log) throws Exception {
		String[] xhAry = xh.split(",");
		TfcSpecial sv = new TfcSpecial();
		DbResult result = null;
		for(String x:xhAry) {
			sv.setXh(x);
			result = this.specialVehDao.setSpecial(sv);
			
			if(result.getCode()==1){
				this.sLogDao.setRmLog(log);
				if(result.getCode()==1){
					result = this.specialVehDao.delSpecial();
					if(result.getCode()!=1){
						throw new RuntimeException("“Ï≥££∫"+result.getCode()+"£¨¥ÌŒÛ£∫"+result.getMsg());
					}
				}
			}
		}
		return result;
	}

	

}
