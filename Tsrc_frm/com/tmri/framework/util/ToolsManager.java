package com.tmri.framework.util;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.dao.DataAccessException;

import com.tmri.framework.bean.FrmLogRow;
import com.tmri.framework.bean.FrmLogTask;
import com.tmri.framework.bean.FrmUpQueue;
import com.tmri.framework.bean.Log;
import com.tmri.framework.bean.Role;
import com.tmri.share.frm.bean.Code;
import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.bean.SysUser;
import com.tmri.share.frm.bean.UserSession;


public interface ToolsManager {
	
	public boolean checkUserRight(HttpSession session,String cxdh);
//	public void setToolsDaoJdbc(ToolsDaoJdbc toolsDaoJdbc);
//
//	public void setCodeDao(CodeDao codeDao);

	public List getDmlbCodesList(String dmlb) throws Exception;

	public List getDmlbCodesList(String dmlb, String dmsm2) throws Exception;

	public Code getCode(String dmlb, String dmz);

	public String getOptionStr_fzjg(String fzjg, String bdfzjg);

	public String getCodeDmsm1(String dmlb, String dmz, String _default);

	public Department getDepartment(String glbm);

	public String getBmmc(String glbm);

	public SysUser getSysuser(String yhdh);

	public String getYhmc(String yhdh);

	public String outputJb(String strJb, boolean bShowMax);

	public String outputShowJb(String strJb, boolean bShowMax);

	public String getGlbmQueryCondition(UserSession userSession, String Colname);

	public String transDmlbToOptionHtml(String dmlb, String defauls,
			boolean havaNull, String showType) throws Exception;

	public String transDmlbToCheckBoxHtml(String dmlb, String ctrlName)
			throws Exception;

	public List getDmlbList(String dmlb) throws Exception;

	public List getDepartmentList(UserSession userSession, String jb);

	public List getDepartmentList(UserSession userSession,
			Department department, String jb);

	public List getDepartmentList1(UserSession userSession);

	public List getDepartmentList2(UserSession userSession);

	public List getUpDepartmentList(UserSession userSession)
			throws DataAccessException;

	public List getDepartmentList(Department department);

	//public String cScriptInfoStr(String strJava);



	public List getRoleList(String jb);

	

	

	public String getCxsxName(String cxsxs);

	public List getSysuserListByGlbm(String glbm);

	public List getSysusers(UserSession userSession);


	public String getSysDate() throws SQLException;
	public String getZwSysDate() throws SQLException;
	public String getSysDateTime() throws SQLException;

	public String getSysDateTimeToMinute() throws SQLException;

	public String getSysDate(String val) throws SQLException;

	public String getSysDateTime(String val) throws SQLException;

	public String getDatetime(String datetime, String val) throws SQLException;

	public String getDataBaseSysDate(String v) throws SQLException;

	public String CheckNullAndReplace(String strJava);

	public List getSqyyDmList(String czlx);

	public int Check_Wfdm_Jyw(String wfxw) throws DataAccessException;

	public int Check_Jdsbh_Jyw(String jdsbh) throws DataAccessException;

	public int Check_Violation_Jyw(String wfbh) throws DataAccessException;

	public int Check_Force_Jyw(String pzbh) throws DataAccessException;

	public int Check_Surveil_Jyw(String xh) throws DataAccessException;

	public String TransDriverZt(String strZt) throws Exception;

	public String TransVehicleZt(String strZt) throws Exception;

	public String getQzcslxMc(String vQzcslx);

	public String getCfzlMc(String vCfzl);

	public String getCfzlMcNoCfzldm(String vCfzl);

	public String getCxxmMc(String vCxxm);

	public String getKlxmMc(String vKlxm);

	public String getSjxmMc(String vSjxm);

	public String formatDateTime(String strDateTime);

	public String formatDateTimeToSec(String strDateTime);

	public String formatDate(String strDateTime);

	public void SetPageValue(Object _obj, PrintWriter _pw, String _formName,
			String _formFieldAppendName, boolean isParent, boolean isEmpty);

	public String getSetPageValue(Object _obj, String _formName,
			boolean isEmpty, String _formFieldAppendName);

	public void SetPageValue(Object _obj, PrintWriter _pw, String _formName,
			boolean isParent, boolean isEmpty);

	//public String ReplaceString(String original, String find, String replace);

	public void transCodeRoleBean(Role role);

	public void transCodeRoleList(List roleList);

	public void transCodeSysuserBean(SysUser sysuser);

	public void transCodeSysuserList(List sysuserList);

	public void transCodeLogBean(Log log);

	public void transCodeLogList(List logList);

	public void transCodeDepartmentBean(Department department);

	public void transCodeDepartmentList(List departmentList);

    public String[] splitString(String str, String split);

	public String getMonthSysdate(String date, String v);

	public String getDaySysdate(String date, String v);

	public int compareDay(String date);
	

	/**
	 * 访问远程机器
	 * @param ip
	 */
	public void requestRemoteServer(String ip);
	/**
	 * 安全备案即将到期天数
	 */
	public int getSystemExpiryDate();
	
	public List getInterfaceExpirylist();
	
	/**
	 * 翻译log
	 * 
	 * @param code
	 */
	public void transLogtaskBean(FrmLogTask log);

	/**
	 * 翻译log
	 * 
	 * @param code
	 */
	public void transLogtaskList(List logList);
	
	/**
	 * 翻译log
	 * 
	 * @param code
	 */
	public void transLogrowBean(FrmLogRow log);

	/**
	 * 翻译log
	 * 
	 * @param code
	 */
	public void transLogrowList(List logList);
	/**
	 * 翻译log
	 * 
	 * @param code
	 */
	public void transUpqueueBean(FrmUpQueue log);

	/**
	 * 翻译log
	 * 
	 * @param code
	 */
	public void transUpqueueList(List logList);
	public void setSessionValue(HttpServletRequest req,UserSession userSession);
	public List getFxnyrList(String inputdate1,String inputdate2) throws Exception;
	public List getMonthList(int iMonth) throws Exception;
	
	
	//public List getDmlbListFromMemByDmsm2(String dmlb,String dmsm2) throws DataAccessException, SQLException;		

	
	//public String getCodeDmsm1ByDmsm2(String dmlb,String dmsm2) throws DataAccessException, SQLException;	
	
	
	//public List getDmlbListFromMem(String dmlb,String strDmz) throws DataAccessException, SQLException;
	
	/**
	 * 设置输出的年份、月份
	 * 
	 * @param request
	 * @param num
	 * @throws SQLException
	 */
	public void setTjNy(HttpServletRequest request,int num) throws Exception;
	
	/**
	 * 获取同期比的统计条件
	 * @param strTjlx
	 * @return
	 */
	public String getTqCondition (String strTjlx,String strTjnf)  throws SQLException;

	public List getTjnfList();
}