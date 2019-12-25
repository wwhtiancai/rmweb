package com.tmri.framework.dao.jdbc;

import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.tmri.framework.bean.FrmInformsetup;
import com.tmri.framework.bean.SmsContent;
import com.tmri.framework.bean.SmsSetup;
import com.tmri.framework.bean.SysResult;
import com.tmri.framework.dao.CodeDao;
import com.tmri.framework.dao.CodetypeDao;
import com.tmri.framework.util.PrintContext;
import com.tmri.pub.service.SysService;
import com.tmri.share.cache.bean.SystemCache;
import com.tmri.share.frm.bean.Code;
import com.tmri.share.frm.bean.FrmXzqhLocal;
import com.tmri.share.frm.bean.SysUser;
import com.tmri.share.frm.dao.GSysparaCodeDao;
import com.tmri.share.frm.dao.jdbc.FrmDaoJdbc;
import com.tmri.share.frm.util.PageController;
import com.tmri.share.ora.bean.CallableStatementCallbackImpl;
@Repository
public class CodeDaoJdbc extends FrmDaoJdbc implements CodeDao {
	@Autowired
	SysService sysService = null;
	@Autowired
	CodetypeDao codetypeDao = null;
	@Autowired
	GSysparaCodeDao gSysparaCodeDao;

	public SysResult removeCode() throws SQLException {
		String callString = "{call FRM_SYS_PKG.deleteFRM_CODE(?,?,?)}";
		CallableStatementCallbackImpl callBack = new CallableStatementCallbackImpl() {
			public Object doInCallableStatement(CallableStatement cstmt)
					throws SQLException, DataAccessException {
				SysResult result = new SysResult();
				cstmt.registerOutParameter(1, Types.NUMERIC);
				cstmt.registerOutParameter(2, Types.VARCHAR);
				cstmt.registerOutParameter(3, Types.VARCHAR);
				cstmt.execute();
				result.setFlag(cstmt.getLong(1));
				result.setDesc(cstmt.getString(2));
				result.setDesc1(cstmt.getString(3));
				return result;
			}
		};
		SysResult result = (SysResult) jdbcTemplate.execute(callString,
				callBack);
		return result;
	}

	public SysResult saveCode(String modal) throws SQLException {
		String callString = "{call FRM_SYS_PKG.Savefrm_Code(?,?,?,?)}";
		CallableStatementCallbackImpl callBack = new CallableStatementCallbackImpl() {
			public Object doInCallableStatement(CallableStatement cstmt)
					throws SQLException, DataAccessException {
				SysResult result = new SysResult();
				String modal = (String) getParameterObject();
				cstmt.setString(1, modal);
				cstmt.registerOutParameter(2, Types.NUMERIC);
				cstmt.registerOutParameter(3, Types.VARCHAR);
				cstmt.registerOutParameter(4, Types.VARCHAR);
				cstmt.execute();
				result.setFlag(cstmt.getLong(2));
				result.setDesc(cstmt.getString(3));
				result.setDesc1(cstmt.getString(4));
				return result;
			}
		};
		callBack.setParameterObject(modal);
		SysResult result = (SysResult) jdbcTemplate.execute(callString,
				callBack);
		return result;
	}

	public Object getOtherMemTab(String tablename, String key) throws Exception {
		Hashtable tab = (Hashtable) SystemCache.getInstance().getValue(
				tablename);
		if (tab == null) {
			tab = new Hashtable();
			Code code = gSysparaCodeDao.getCode("00", "0006", tablename);
			Object m_Object = Class.forName(code.getDmsm2()).newInstance();
			Class classObject = m_Object.getClass();
			Class parameters[] = new Class[0];
			Object objvarargs[] = new Object[0];
			List list = jdbcTemplate.queryForList(code.getDmsm1(), classObject);
			Method meth = classObject.getMethod("get" + code.getDmsm3(),
					parameters);
			for (int i = 0; i < list.size(); i++) {
				m_Object = list.get(i);
				Object keyObject = meth.invoke(m_Object, objvarargs);
				tab.put(keyObject, m_Object);
			}
		}
		Object resultObject = tab.get(key);
		return resultObject;
	}

	public void setSysService(SysService sysService) {
		this.sysService = sysService;
	}

	public void freshCodebyDmlb(Code code) {
		if (code == null || code.getXtlb() == null || code.getDmlb() == null
				|| code.getXtlb().equals("") || code.getDmlb().equals("")) {
			SystemCache.getInstance().remove("code");
			SystemCache.getInstance().remove("codehash");
			PrintContext.getInstance().refresh();
		} else {
			Hashtable tab = (Hashtable) SystemCache.getInstance().getValue(
					"code");
			Hashtable tabhash = (Hashtable) SystemCache.getInstance()
					.getValue("codehash");
			String dmlbnew = gSysparaCodeDao.transNewDmlb(code.getXtlb(), code.getDmlb());
			String key = "code:" + code.getXtlb() + "-" + dmlbnew;
			String keyhash = "codehash:" + code.getXtlb() + "-" + dmlbnew;
			try {
				if (tab != null)
					tab.remove(key);
				if (tabhash != null)
					tabhash.remove(keyhash);
			} catch (Exception ex) {

			}
		}
	}

	public List getXzqhList(String dmz, String dmsm1, String xzqh,
			PageController controller) throws Exception {
        // noresult-0050
        List<FrmXzqhLocal> xzqhList = null;
		HashMap map = new HashMap();
        // String strSql =
        // "select * from frm_code where xtlb='00' and dmlb='0050' and dmz like :xzqh ";
        // map.put("xzqh", xzqh + "%");
        // if (!dmz.equals("")) {
        // strSql = strSql + " and dmz like :dmz ";
        // map.put("dmz", dmz + "%");
        // }
        // if (!dmsm1.equals("")) {
        // strSql = strSql + " and dmsm1 like :dmsm1 ";
        // map.put("dmsm1", "%" + dmsm1 + "%");
        // }
        // strSql = strSql + " order by dmz";
        String strSql = "select * from FRM_XZQH_LOCAL where xzqh like :xzqh ";
		map.put("xzqh", xzqh + "%");
		if (!dmz.equals("")) {
            strSql = strSql + " and xzqh like :dmz ";
            map.put("dmz", dmz + "%");
		}
		if (!dmsm1.equals("")) {
            strSql = strSql + " and qhmc like :dmsm1 ";
            map.put("dmsm1", "%" + dmsm1 + "%");
		}
        strSql = strSql + " order by xzqh";
        xzqhList = controller.getWarpedList(strSql, map, FrmXzqhLocal.class, jdbcTemplate);
        List<Code> result = new ArrayList<Code>();
        for (FrmXzqhLocal obj : xzqhList) {
            Code code = new Code();
            code.setXtlb("00");
            code.setDmlb("0050");
            code.setDmz(obj.getXzqh());
            code.setDmsm1(obj.getQhmc());
            code.setDmsm2(obj.getQhsm());
            result.add(code);
        }
        return result;
	}

	public List getJxCodes(String fzjg) throws SQLException {
		String tmpSql = null;
		if (fzjg.equals("部")) {
			tmpSql = "select jxdm dmz,jxjc||'('||fzjg||')' dmsm1 from drv_schoolinfo order by jxdm";
		} else if (fzjg.length() == 1) {
			tmpSql = "select jxdm dmz,jxjc||'('||fzjg||')' dmsm1 from drv_schoolinfo where fzjg like '"
					+ fzjg + "%' order by jxdm";
		} else {
			tmpSql = "select jxdm dmz,jxjc||'('||fzjg||')' dmsm1 from drv_schoolinfo where fzjg = '"
					+ fzjg + "' order by jxdm";
		}
		List list = jdbcTemplate.queryForList(tmpSql, Code.class);
		return list;
	}

	public Code getJx(String jxdm, String fzjg) throws SQLException {
		String tmpSql = null;
		HashMap hashMap = new HashMap();
		tmpSql = "select jxdm dmz,jxjc dmsm1,fzjg dmsm2 from drv_schoolinfo where jxdm=:jxdm and fzjg=:fzjg";
		hashMap.put("jxdm", jxdm);
		hashMap.put("fzjg", fzjg);
		List list = jdbcTemplate.queryForList(tmpSql, hashMap, Code.class);
		if (list != null && list.size() > 0) {
			return (Code) list.get(0);
		} else {
			return null;
		}

	}

	public Code getJxByXh(String jxxh) throws SQLException {
		String tmpSql = null;
		HashMap hashMap = new HashMap();
		tmpSql = "select jxdm dmz,jxjc dmsm1,fzjg dmsm2 from drv_schoolinfo where xh=:jxxh";
		hashMap.put("jxxh", jxxh);
		List list = jdbcTemplate.queryForList(tmpSql, hashMap, Code.class);
		if (list != null && list.size() > 0) {
			return (Code) list.get(0);
		} else {
			return null;
		}

	}

	public void setCodetypeDao(CodetypeDao codetypeDao) {
		this.codetypeDao = codetypeDao;
	}

	// 获取消息接收设置列表
	public List getSmsSetupContent(String xxdm) {
		String sql = "select a.xxdm,a.yhdh yhdh,b.xm,a.bz from frm_sms_setup a,frm_sysuser b where a.yhdh=b.yhdh";
		HashMap map = new HashMap();
		if (!xxdm.equals("")) {
			sql += " and a.xxdm=:xxdm";
			map.put("xxdm", xxdm);
		}
		sql += " order by a.xxdm,a.yhdh";
		return jdbcTemplate.queryForList(sql, map, SmsSetup.class);
	}

	// 获取用户列表
	public List getUsers(String xtgly, String kgywyhlx, String xm) {
		String sql = "select * from frm_sysuser where 1=1";
		HashMap map = new HashMap();
		if (!xtgly.equals("")) {
			sql += " and xtgly=:xtgly";
			map.put("xtgly", xtgly);
		}
		if (!kgywyhlx.equals("")) {
			if ("1234567".indexOf(kgywyhlx) >= 0)
				sql += " and substr(KGYWYHLX," + kgywyhlx + ",1)='1'";
		}
		if (!xm.equals("")) {
			sql += " and xm=:xm";
			map.put("xm", xm);
		}
		sql += " order by glbm,xm";
		return jdbcTemplate.queryForList(sql, map, SysUser.class);
	}

	// 保存消息设置
	public SysResult savesmssetup(SmsSetup smsSetup) throws Exception {
		String callString = "{call FRM_SYS_PKG.saveSms_setup(?,?,?,?,?)}";
		CallableStatementCallbackImpl callBack = new CallableStatementCallbackImpl() {
			public Object doInCallableStatement(CallableStatement cstmt)
					throws SQLException, DataAccessException {
				SmsSetup smsSetup = (SmsSetup) this.getParameterObject();
				cstmt.setString(1, smsSetup.getXxdm());
				cstmt.setString(2, smsSetup.getYhdh());
				SysResult result = new SysResult();
				cstmt.registerOutParameter(3, Types.NUMERIC);
				cstmt.registerOutParameter(4, Types.VARCHAR);
				cstmt.registerOutParameter(5, Types.VARCHAR);
				cstmt.execute();
				result.setFlag(cstmt.getLong(3));
				result.setDesc(cstmt.getString(4));
				result.setDesc1(cstmt.getString(5));
				return result;
			}
		};
		callBack.setParameterObject(smsSetup);
		SysResult result = (SysResult) jdbcTemplate.execute(callString,
				callBack);
		return result;
	}

	public int getSmsCount(String yhdh) {
		String sqlString = "select count(*) from frm_sms_content where jsyhdh=:yhdh and bj='0'";
		HashMap map = new HashMap();
		map.put("yhdh", yhdh);
		int result = jdbcTemplate.queryForInt(sqlString, map);
		return result;
	}

	// 获取用户消息列表
	public List getUserSmsList(SmsContent smsContent, PageController controller) {
		String sqlString = "select t.*,frm_sys_pkg.translateXxdm(t.xxdm) title from frm_sms_content t where jsyhdh=:jsyhdh";
		HashMap map = new HashMap();
		map.put("jsyhdh", smsContent.getJsyhdh());
		if (smsContent.getXxbh() != null && !smsContent.getXxbh().equals("")) {
			map.put("xxbh", smsContent.getXxbh());
			sqlString += " and xxbh=:xxbh";
		}
		if (smsContent.getBj() != null && !smsContent.getBj().equals("")) {
			map.put("bj", smsContent.getBj());
			sqlString += " and bj=:bj";
		}
		if (smsContent.getCjsjq() != null && !smsContent.getCjsjq().equals("")) {
			map.put("cjsjq", smsContent.getCjsjq());
			sqlString += " and cjsj>=to_date(:cjsjq,'yyyy-mm-dd')";
		}
		if (smsContent.getCjsjz() != null && !smsContent.getCjsjz().equals("")) {
			map.put("cjsjz", smsContent.getCjsjz());
			sqlString += " and cjsj>=to_date(:cjsjz,'yyyy-mm-dd')";
		}
		sqlString += " order by cjsj desc";
		List resultList = controller.getWarpedList(sqlString, map,
				SmsContent.class, this.jdbcTemplate);
		return resultList;
	}

	// 获取单个用户消息
	public SmsContent saveReadOneUserSms(SmsContent smsContent) {
		String sqlString = "select * from frm_sms_content  where xxbh=:xxbh";
		HashMap map = new HashMap();
		map.put("xxbh", smsContent.getXxbh());

		SmsContent smsContent2 = (SmsContent) jdbcTemplate
				.queryForSingleObject(sqlString, map, SmsContent.class);

		// 获取URL列表
		if (smsContent2.getXxdm().substring(0, 1).equals("9")) {
			String sql = "select '1' dmz,cxmc dmsm1,xtlb||cdbh dmsm2 from frm_program a,frm_inform_code b where b.xxdm=:xxdm and instr(b.qxbh,xtlb||cdbh)>0";
			map.put("xxdm", smsContent2.getXxdm());
			List codes = this.jdbcTemplate.queryForList(sql, map, Code.class);
			smsContent2.setUrlcodes(codes);
		}
		if (smsContent2.getBj().equals("0")) {
			String callString = "{call FRM_SYS_PKG.saveReadOneUserSms(?)}";
			CallableStatementCallbackImpl callBack = new CallableStatementCallbackImpl() {
				public Object doInCallableStatement(CallableStatement cstmt)
						throws SQLException, DataAccessException {
					SmsContent smsContent = (SmsContent) getParameterObject();
					cstmt.setString(1, smsContent.getXxbh());
					cstmt.execute();
					return 1;
				}
			};
			callBack.setParameterObject(smsContent);
			jdbcTemplate.execute(callString, callBack);
		}

		return smsContent2;
	}

	// 获取提醒内容设置代码列表
	public List getInformCodes(String dqyhbmjb, String djbmjb) {
		String sqlString = "select xxdm dmz,xxmc dmsm1 from frm_inform_code where zt='1' and (bmjb like :bmjb";
		HashMap paras = new HashMap();
		paras.put("bmjb", "%" + dqyhbmjb + "%");
		if (dqyhbmjb.equals(djbmjb)) {
			sqlString += " or bmjb='A') ";
		} else {
			sqlString += ") ";
		}
		sqlString += " order by sxh";
		return jdbcTemplate.queryForList(sqlString, paras, Code.class);
	}

	// 获取提醒内容设置列表
	public List getInformSetups(FrmInformsetup command,
			PageController controller) {
		String sqlString = "select a.*,b.txzq,b.xxmc,c.bmmc from frm_inform_setup a,frm_inform_code b,frm_department c where a.xxdm=b.xxdm and a.glbm=c.glbm and a.zt='1'";
		HashMap paras = new HashMap();
		if (command.getBaxjbm().equals("1")) {
			// 包含下级部门
			if (command.getBmjb().equals("2")) {
				sqlString += " and a.glbm like :glbm ";
				paras.put("glbm", command.getGlbm().substring(0, 2) + "%");
			} else if (command.getBmjb().equals("3")) {
				sqlString += " and a.glbm like :glbm ";
				if (command.getGlbm().substring(2, 4).equals("90")) {
					paras.put("glbm", command.getGlbm().substring(0, 6) + "%");
				} else {
					paras.put("glbm", command.getGlbm().substring(0, 4) + "%");
				}
			} else if (command.getBmjb().equals("4")) {
				sqlString += " and a.glbm like :glbm ";
				paras.put("glbm", command.getGlbm().substring(0, 8) + "%");
			} else if (command.getBmjb().equals("5")) {
				sqlString += " and a.glbm = :glbm ";
				paras.put("glbm", command.getGlbm());
			}
			if (command.getGlbm().substring(10).equals("10"))
				sqlString += " and a.glbm like '%10' ";
		} else {
			sqlString += " and a.glbm=:glbm";
			paras.put("glbm", command.getGlbm());
		}
		if (!command.getXxdm().equals("")) {
			sqlString += " and a.xxdm=:xxdm";
			paras.put("xxdm", command.getXxdm());
		}
		sqlString += " order by a.glbm,a.xxdm";
		return controller.getWarpedList(sqlString, paras, FrmInformsetup.class,
				jdbcTemplate);
	}

	// 获取单条提醒内容设置信息
	public FrmInformsetup getInformSetup(FrmInformsetup command) {
		String sqlString = "select a.*,b.txzq from frm_inform_setup a,frm_inform_code b where a.xxdm=:xxdm and a.glbm=:glbm and a.xxdm=b.xxdm and a.zt='1'";
		HashMap paras = new HashMap();
		paras.put("glbm", command.getGlbm());
		paras.put("xxdm", command.getXxdm());
		return (FrmInformsetup) this.jdbcTemplate.queryForSingleObject(
				sqlString, paras, FrmInformsetup.class);
	}

}
