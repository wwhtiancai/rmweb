package com.tmri.share.frm.dao.jdbc;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.tmri.share.cache.bean.SystemCache;
import com.tmri.share.frm.bean.Code;
import com.tmri.share.frm.bean.Codetype;
import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.bean.FrmGispara;
import com.tmri.share.frm.bean.SysPara;
import com.tmri.share.frm.bean.SysparaValue;
import com.tmri.share.frm.dao.GSysparaCodeDao;
import com.tmri.share.frm.util.Constants;

@Repository
public class GSysparaCodeDaoJdbc extends FrmDaoJdbc implements GSysparaCodeDao {

	public Code getCode(String xtlb, String dmlb, String dmz) throws Exception {
		String dmlbnew = transNewDmlb(xtlb, dmlb);
		Codetype codetype = getCodetype(xtlb, dmlbnew);
		Code code = null;
		if (codetype != null) {
			if (codetype.getJznc().equals("1")) {
				// 加载内存处理
				Hashtable hashtable = this.getHashCodes(xtlb, dmlbnew);
				code = (Code) hashtable.get(dmz);
			} else {
				HashMap map = new HashMap();
				String strSql = "select * from frm_code where xtlb=:xtlb and dmlb=:dmlb and dmz=:dmz and zt='1'";
				map.put("xtlb", xtlb);
				map.put("dmlb", dmlbnew);
				map.put("dmz", dmz);
				code = (Code) jdbcTemplate.queryForSingleObject(strSql, map, Code.class);
			}
		}
		return code;
	}

	public List getCodes(String xtlb, String dmlb) {
		String dmlbnew = transNewDmlb(xtlb, dmlb);
		Codetype codetype = getCodetype(xtlb, dmlbnew);
		List codeList = null;
		if (null != codetype && codetype.getJznc().equals("1")) {
			// 加载内存处理
			Hashtable tab = (Hashtable) SystemCache.getInstance().getValue("code");
			if (tab == null)
				tab = new Hashtable();
			SystemCache.getInstance().reg("code", tab);
			String key = "code:" + xtlb + "-" + dmlbnew;
			if (tab.containsKey(key)) {
				codeList = (List) tab.get(key);
			} else {
				String strSql = "select * from frm_code where xtlb=? and dmlb=? and zt='1' order by sxh,dmz";
				Object[] objects = new Object[] { xtlb, dmlbnew };
				codeList = jdbcTemplate.queryForList(strSql, objects, Code.class);
				tab.put(key, codeList);
			}
		} else {
			HashMap map = new HashMap();
			String strSql = "select * from frm_code where xtlb=:xtlb and dmlb=:dmlb and zt='1' order by sxh,dmz";
			map.put("xtlb", xtlb);
			map.put("dmlb", transNewDmlb(xtlb, dmlb));
			codeList = jdbcTemplate.queryForList(strSql, map, Code.class);
		}
		return codeList;
	}

	public List getCodesByDb(String xtlb, String dmlb) {
		String dmlbnew = transNewDmlb(xtlb, dmlb);
		String strSql = "select * from frm_code where xtlb=? and dmlb=? and zt='1' order by sxh,dmz";
		Object[] objects = new Object[] { xtlb, dmlbnew };
		List codeList = jdbcTemplate.queryForList(strSql, objects, Code.class);
		return codeList;
	}

	public List getCodesByDmz(String xtlb, String dmlb, String dmz) throws Exception {
		List CodeList = null;
		HashMap map = new HashMap();
		String strSql = "select * from frm_code where xtlb=:xtlb and dmlb=:dmlb and instr(dmz,:dmz)>0 and zt='1' order by sxh,dmz";
		map.put("xtlb", xtlb);
		map.put("dmlb", transNewDmlb(xtlb, dmlb));
		map.put("dmz", dmz);
		CodeList = jdbcTemplate.queryForList(strSql, map, Code.class);
		return CodeList;
	}

	public List getCodesByDmz2(String xtlb, String dmlb, String dmz2) throws Exception {
		List CodeList = null;
		HashMap map = new HashMap();
		String strSql = "select * from frm_code where xtlb=:xtlb and dmlb=:dmlb and instr(dmz2,:dmz)>0 and zt='1' order by sxh,dmz2";
		map.put("xtlb", xtlb);
		map.put("dmlb", transNewDmlb(xtlb, dmlb));
		map.put("dmz", dmz2);
		CodeList = jdbcTemplate.queryForList(strSql, map, Code.class);
		return CodeList;
	}

	// 机动车监管业务系统

	public List getTjCodesByDmsm2(String dmlb, String strDmsm2) {

		List list = null;
		String strSql = "";
		HashMap map = new HashMap();
		if (strDmsm2 == null || strDmsm2.equals("")) {
			strSql = "select * from frm_codetj where dmlb=:dmlb  and zt='1'";
		} else {
			strSql = "select * from frm_codetj where dmlb=:dmlb and dmsm2 like :dmsm2 and zt='1'";
		}
		map.put("dmlb", dmlb);
		map.put("dmsm2", "%" + strDmsm2 + "%");
		list = (List) jdbcTemplate.queryForList(strSql, map, Code.class);
		return list;
	}

	public List getTjCodesByDmz(String dmlb, String strDmz) throws SQLException {

		List list = null;
		String strSql = "";
		HashMap map = new HashMap();
		if (strDmz == null || strDmz.equals("")) {
			strSql = "select * from frm_codetj where dmlb=:dmlb and zt='1'";
		} else {
			strSql = "select * from frm_codetj where dmlb=:dmlb and dmz like :dmz and zt='1'";
		}
		map.put("dmlb", dmlb);
		map.put("dmz", "%" + strDmz + "%");
		list = (List) jdbcTemplate.queryForList(strSql, map, Code.class);
		return list;
	}

	public Code getTjCodeFromMem(String dmlb, String strDmz) {
		HashMap map = new HashMap();
		String strSql = "select * from frm_codetj where dmlb=:dmlb and dmz =:dmz and zt='1'";
		map.put("dmlb", dmlb);
		map.put("dmz", strDmz);
		return (Code) jdbcTemplate.queryForSingleObject(strSql, map, Code.class);
	}

	public String getTjCodeDmsm1ByDmsm2(String dmlb, String strDmsm2) {
		String _result = "";
		List list = this.getTjCodesByDmsm2(dmlb, strDmsm2);
		if (list != null) {
			if (list.size() > 0) {
				Code code = (Code) list.get(0);
				_result = code.getDmsm1();
			}
		}
		return _result;
	}

	public List getDrvCityCodes(String dmlb, String strDmz) throws SQLException {

		List list = (List) getTjCodesByDmz(dmlb, "");
		Vector vector = new Vector();
		Code basCode = null;
		int iLength = strDmz.length();
		for (int i = 0; i < list.size(); i++) {
			basCode = (Code) list.get(i);
			if (basCode.getDmz().substring(0, iLength).equals(strDmz)) {
				if ((!basCode.getDmz().substring(1).equals("O")) || basCode.getDmz().equals("桂O") || basCode.getDmz().equals("藏O")) {
					vector.add(basCode);
				}
			}
		}
		return vector;
	}

	// 获取机动车业务原因
	public String getVehywyy(String ywlx, String ywyy) {
		String result = "";
		try {
			HashMap map = new HashMap();
			String sql = "select Veh_Comm_Pkg.Getywmc('" + ywlx + "','" + ywyy + "') aaa from dual";
			map.put("ywlx", ywlx);
			map.put("ywyy", ywyy);
			result = jdbcTemplate.queryForSingleObject(sql, map, String.class).toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	// 获取提醒内容设置代码
	public Code getInformCode(String xxdm) {
		String sqlString = "select xxdm dmz,xxmc dmsm1,txzq dmsm2,txnr dmsm3,bmjb dmsm4 from frm_inform_code where xxdm=:xxdm";
		HashMap paras = new HashMap();
		paras.put("xxdm", xxdm);
		return (Code) jdbcTemplate.queryForSingleObject(sqlString, paras, Code.class);
	}

	public Codetype getCodetype(String xtlb, String dmlb) {
		Hashtable tab = (Hashtable) SystemCache.getInstance().getValue("codetype");
		if (tab == null) {
			tab = new Hashtable();
			SystemCache.getInstance().reg("codetype", tab);
		}
		String key = xtlb + "-" + transNewDmlb(xtlb, dmlb);
		if (!tab.containsKey(key)) {
			String tmpSql = "select * from frm_codetype where dmlb=:dmlb and xtlb=:xtlb";
			HashMap map = new HashMap();
			map.put("dmlb", dmlb);
			map.put("xtlb", xtlb);
			List list = jdbcTemplate.queryForList(tmpSql, map, Codetype.class);
			if (list == null || list.size() == 0)
				return null;
			else {
				tab.put(key, list.get(0));
				return (Codetype) list.get(0);
			}
		} else {
			return (Codetype) tab.get(key);
		}
	}

	public String getCodeValue(String xtlb, String dmlb, String dmz) throws Exception {
		if (null == dmz || "".equals(dmz)) {
			return "";
		}
		Code code = this.getCode(xtlb, dmlb, dmz);
		if (null == code) {
			return dmz;
		} else {
			return code.getDmsm1();
		}
	}

	public String transNewDmlb(String xtlb, String dmlb) {
		String result = dmlb;
		int len = dmlb.length();
		for (int i = 0; i < 4 - len; i++) {
			result = "0" + result;
		}

		return result;
	}

	private Hashtable getHashCodes(String xtlb, String dmlb) throws Exception {
		// 认为都加载到内存处理
		Hashtable resultHashtable = null;
		Hashtable tab = (Hashtable) SystemCache.getInstance().getValue("codehash");

		if (tab == null)
			tab = new Hashtable();
		SystemCache.getInstance().reg("codehash", tab);

		String key = "codehash:" + xtlb + "-" + dmlb;
		if (tab.containsKey(key)) {
			resultHashtable = (Hashtable) tab.get(key);
			return resultHashtable;
		} else {
			List list = this.getCodes(xtlb, dmlb);
			resultHashtable = transListToHashTable(list);
			tab.put(key, resultHashtable);
			return resultHashtable;
		}

	}

	private Hashtable transListToHashTable(List list) {
		Hashtable tab = new Hashtable();
		Code code = null;
		for (int i = 0; i < list.size(); i++) {
			code = (Code) list.get(i);
			tab.put(code.getDmz(), code);
		}
		return tab;
	}

	public SysPara getSysPara(String xtlb, String cslx, String gjz) {
		String key = "syspara:" + xtlb + "_" + gjz;
		Hashtable tab = (Hashtable) SystemCache.getInstance().getValue(Constants.MEM_SYSPARA);
		SysPara sysPara = (SysPara) tab.get(key);
		return sysPara;
	}
	
	/**
     * 
     * @param tableName 表名
     * @return 如果是总队版或部局版表名后面加_jg
     */
    public String getTableName(String tableName) {
        SysPara para= getSysPara("00", "2","XTYXMS");
        String xtyxms=para.getMrz();
        if("3".equals(xtyxms) || "4".equals(xtyxms)) {
            tableName = tableName + "_jg";
        }
        return tableName;
    }

	public List getDeptParaGlbms(String xtlb, String gjz, String value) {
		String sql = "select b.* from frm_syspara_value a,frm_department b where a.xtlb=:xtlb and a.gjz=:gjz and a.csz=:csz and a.glbm=b.glbm order by b.glbm";
		HashMap map = new HashMap();
		map.put("xtlb", xtlb);
		map.put("gjz", gjz);
		map.put("csz", value);
		return this.jdbcTemplate.queryForList(sql, map, Department.class);
	}

	public List getSysparasShow(String xgjb) {
		String tmpSql = "select * from frm_syspara where (sfxs='1' or sfxs='3') and cslx='2' and xgjb>=? order by xtlb,fzmc,to_number(xssx)";
		Object[] paraObjects = new Object[] { xgjb };
		return jdbcTemplate.queryForList(tmpSql, paraObjects, SysPara.class);
	}

	public List getDepSyspara(SysPara sysPara) {
		String sql = "select * from frm_syspara where cslx='1' ";
		HashMap map = new HashMap();
		if (sysPara.getXtlb() != null && !sysPara.getXtlb().equals("")) {
			sql += " and xtlb=:xtlb";
			map.put("xtlb", sysPara.getXtlb());
		}
		if (sysPara.getXgjb() != null && !sysPara.getXgjb().equals("")) {
			sql += " and xgjb=:xgjb";
			map.put("xgjb", sysPara.getXgjb());
		}
		if (sysPara.getGjz() != null && !sysPara.getGjz().equals("")) {
			sql += " and gjz=:gjz";
			map.put("gjz", sysPara.getGjz());
		}
		if (sysPara.getCsmc() != null && !sysPara.getCsmc().equals("")) {
			sql += " and csmc like :csmc";
			map.put("csmc", "%" + sysPara.getCsmc() + "%");
		}
		sql += " order by xtlb,xssx";
		return jdbcTemplate.queryForList(sql, map, SysPara.class);
	}

	public String getSysParaValue(String xtlb, String glbm, String gjz) {
		String key = "sysparavalue:" + xtlb + "_" + glbm + "_" + gjz;
		Hashtable tab = (Hashtable) SystemCache.getInstance().getValue(Constants.MEM_SYSPARAVALUE);
		String paravalue = (String) tab.get(key);
		if (paravalue == null) {
			String sql = "select csz from frm_syspara_value where xtlb=? and glbm=? and gjz=? and csbj='0'";
			Object[] paraObjects = new Object[] { xtlb, glbm, gjz };
			SysparaValue sysparaValue = (SysparaValue) this.jdbcTemplate.queryForSingleObject(sql, paraObjects, SysparaValue.class);
			if (sysparaValue != null) {
				tab.put(key, sysparaValue.getCsz());
				paravalue = sysparaValue.getCsz();
			}
		}
		return paravalue;
	}

	public int enableCloudSystem() throws SQLException {
		return 1;
	}

	public String getHpzlmc(String hpzl) throws Exception {
		return this.getCodeValue("00", "1007", hpzl);
	}

	public String getHpysmc(String hpys) throws Exception {
		return this.getCodeValue("62", "0005", hpys);
	}

	public String getCsysmc(String csys) throws Exception {
		String csysmc = "";
		if (null != csys && !"".equals(csys)) {
			char[] csysAry = csys.toCharArray();
			for (char c : csysAry) {
				if (c == 'Z' || c == 'z') {
					csysmc += ",--";
				} else {
					csysmc += "," + this.getCodeValue("00", "1008", String.valueOf(c));
				}
			}
		}
		if (csysmc.startsWith(",")) {
			return csysmc.substring(1);
		}
		return csysmc;
	}

	public String getCsysmc(String csys, String separator) throws Exception {
		String csysmc = "";
		if (null != csys && !"".equals(csys)) {
			char[] csysAry = csys.toCharArray();
			for (char c : csysAry) {
				csysmc += separator + this.getCodeValue("00", "1008", String.valueOf(c));
			}
		}
		if (csysmc.length() > 0)
			csysmc = csysmc.substring(separator.length());
		return csysmc;
	}

	public String getCllxmc(String cllx) throws Exception {
		return this.getCodeValue("00", "1004", cllx);
	}

	
	public FrmGispara getGispara(String gjz) throws Exception {
		String tmpSql="";
		tmpSql="select * from frm_gispara where gjz='"+gjz+"'";
		return (FrmGispara)jdbcTemplate.queryForBean(tmpSql, FrmGispara.class);
	}
	

}
