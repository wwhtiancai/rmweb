package com.tmri.share.frm.dao;

import java.sql.SQLException;
import java.util.List;

import com.tmri.share.frm.bean.Code;
import com.tmri.share.frm.bean.Codetype;
import com.tmri.share.frm.bean.FrmGispara;
import com.tmri.share.frm.bean.SysPara;

/**
 * 系统代码读写
 * 
 * @author jianghailong
 * 
 */
public interface GSysparaCodeDao {

	public Code getCode(String xtlb, String dmlb, String dmz) throws Exception;

	public List getCodes(String xtlb, String dmlb);
	
	public List getCodesByDb(String xtlb, String dmlb);

	public List getCodesByDmz(String xtlb, String dmlb, String dmz)
			throws Exception;

	public List getCodesByDmz2(String xtlb, String dmlb, String dmz2)
			throws Exception;

	public List getTjCodesByDmsm2(String dmlb, String dmsm2);

	public List getTjCodesByDmz(String dmlb, String strDmz) throws SQLException;

	public Code getTjCodeFromMem(String dmlb, String strDmz);

	public String getTjCodeDmsm1ByDmsm2(String dmlb, String strDmsm2);

	public List getDrvCityCodes(String dmlb, String strDmz) throws SQLException;

	/** 取得代码中的代码说明1的值 */
	public String getCodeValue(String xtlb, String dmlb, String dmz)
			throws Exception;

	// 获取机动车业务原因
	public String getVehywyy(String ywlx, String ywyy);

	// 获取提醒内容设置代码
	public Code getInformCode(String xxdm);

	public Codetype getCodetype(String xtlb, String dmlb);

	public SysPara getSysPara(String xtlb, String cslx, String gjz);
	
	public String getTableName(String tableName);

	public List getDeptParaGlbms(String xtlb, String gjz, String value);

	public List getSysparasShow(String xgjb);

	public List getDepSyspara(SysPara sysPara);

	public String getSysParaValue(String xtlb, String glbm, String gjz);
	public String transNewDmlb(String xtlb, String dmlb);
	
	public int enableCloudSystem() throws SQLException;
	
	public String getHpzlmc(String hpzl) throws Exception;
	public String getHpysmc(String hpys) throws Exception;
	public String getCsysmc(String csys) throws Exception;
	public String getCsysmc(String csys,String separator) throws Exception;
	public String getCllxmc(String cllx) throws Exception;
	public FrmGispara getGispara(String gjz) throws Exception ;
}
