package com.tmri.pub.service.impl;

import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmri.framework.bean.FrmQueryerrlog;
import com.tmri.framework.bean.FrmRbspLog;
import com.tmri.framework.bean.FrmSysrun;
import com.tmri.framework.bean.SessionKey;
import com.tmri.framework.bean.SmsContent;
import com.tmri.framework.bean.SysResult;
import com.tmri.framework.dao.CodeDao;
import com.tmri.framework.dao.DepartmentDao;
import com.tmri.framework.dao.ProgramFoldDao;
import com.tmri.framework.dao.SysParaDao;
import com.tmri.framework.dao.SysuserDao;
import com.tmri.framework.dao.jdbc.FrmDataObjDaoJdbc;
import com.tmri.framework.dao.jdbc.SysDaoJdbc;
import com.tmri.pub.bean.BasPolice;
import com.tmri.pub.service.SysService;
import com.tmri.pub.util.WrapUtil;
import com.tmri.share.frm.bean.Code;
import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.bean.Function;
import com.tmri.share.frm.bean.Program;
import com.tmri.share.frm.bean.SysUser;
import com.tmri.share.frm.bean.UserSession;
import com.tmri.share.frm.dao.GDepartmentDao;
import com.tmri.share.frm.dao.GSysparaCodeDao;
import com.tmri.share.frm.service.GBasService;
import com.tmri.share.frm.service.GHtmlService;
import com.tmri.share.frm.util.Constants;
import com.tmri.share.frm.util.DateUtil;
import com.tmri.share.frm.util.PageController;
import com.tmri.share.frm.util.StringUtil;
import com.tmri.share.ora.bean.DbResult;
@Service

public class SysServiceImpl implements SysService {
	@Autowired
	CodeDao codeDao = null;
	@Autowired
	SysDaoJdbc sysDao;
	@Autowired
	SysParaDao sysparaDao;
	@Autowired
	DepartmentDao departmentDao;
	@Autowired
	ProgramFoldDao programFoldDao;
	@Autowired
	FrmDataObjDaoJdbc frmDataObjDao = null;
	@Autowired
	SysuserDao sysuserDao;
	@Autowired
	GSysparaCodeDao gSysparaCodeDao;
	@Autowired
	GDepartmentDao gDepartmentDao;
    @Autowired
    private GBasService gBasService;
	
	
	@Autowired
	GHtmlService gHtmlService;

	
	
	public Object getOtherMemTab(String table, String key) {
		Object result = null;
		try {
			result = this.codeDao.getOtherMemTab(table, key);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public String transDmlbToOptionHtmlByDmsm2(String xtlb, String dmlb,
			String dmsm2, String defauls, boolean havaNull, String showType)
			throws Exception {
		List list = this.gSysparaCodeDao.getCodes(xtlb, dmlb);
		List resultList = new Vector();
		Code code = null;
		for (int i = 0; i < list.size(); i++) {
			code = (Code) list.get(i);
			if (code.getDmsm2().equals(dmsm2)) {
				resultList.add(code);
			}
		}
		String resultString = this.gHtmlService.transListToOptionHtml(resultList, defauls,
				havaNull, showType);
		return resultString;
	}

	
	public void setPageValue(Object obj, PrintWriter pw, String formName,
			String formFieldAppendName, boolean isParent, boolean isEmpty) {
		try {
			pw.print("<script language=javascript>");
			Class classObject = obj.getClass();
			Field m_field[] = classObject.getDeclaredFields();
			Class parameters[] = new Class[0];
			Object objvarargs[] = new Object[0];
			for (int i = 0; i < m_field.length; i++) {
				pw.println("try{");
				String colName = m_field[i].getName();
				if (colName.length() > 0) {
					colName = colName.substring(0, 1).toUpperCase()
							+ colName.substring(1);
				}
				String methName = "get" + colName;
				Method meth = classObject.getMethod(methName, parameters);
				String m_curValue = "";
				if (meth.invoke(obj, objvarargs) != null) {
					m_curValue = meth.invoke(obj, objvarargs).toString();
					m_curValue = StringUtil.replaceString(m_curValue, "\'",
							"\\\'");
					m_curValue = StringUtil.replaceString(m_curValue, "\"",
							"\\\"");
					m_curValue = StringUtil.replaceString(m_curValue, "\n",
							"\\n");
				}
				m_curValue = m_curValue == null ? "" : m_curValue;
				if (isParent) {
					if (isEmpty) {
						pw.println("parent.document." + formName + "."
								+ m_field[i].getName().toString().toLowerCase()
								+ formFieldAppendName + ".value='';");
					} else {
						pw.println("parent.document." + formName + "."
								+ m_field[i].getName().toString().toLowerCase()
								+ formFieldAppendName + ".value='" + m_curValue
								+ "';");
					}

				} else {
					if (isEmpty) {
						pw.println(formName + "."
								+ m_field[i].getName().toString().toLowerCase()
								+ formFieldAppendName + ".value='';");
					} else {
						pw.println(formName + "."
								+ m_field[i].getName().toString().toLowerCase()
								+ formFieldAppendName + ".value='" + m_curValue
								+ "';");
					}
				}
				pw.println("}catch(ex){}");
			}
			pw.println("</script>");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public String getSetPageValue(Object obj, String formName,
			String formFieldAppendName, boolean isParent, boolean isEmpty) {
		StringBuffer result = new StringBuffer();
		try {
			Class classObject = obj.getClass();
			Field m_field[] = classObject.getDeclaredFields();
			Class parameters[] = new Class[0];
			Object objvarargs[] = new Object[0];
			for (int i = 0; i < m_field.length; i++) {
				result.append("try{\n");
				String colName = m_field[i].getName();
				if (colName.length() > 0) {
					colName = colName.substring(0, 1).toUpperCase()
							+ colName.substring(1);
				}
				String methName = "get" + colName;
				Method meth = classObject.getMethod(methName, parameters);
				String m_curValue = "";
				if (meth.invoke(obj, objvarargs) != null) {
					m_curValue = meth.invoke(obj, objvarargs).toString();
					m_curValue = StringUtil.replaceString(m_curValue, "\'",
							"\\\'");
					m_curValue = StringUtil.replaceString(m_curValue, "\"",
							"\\\"");
					m_curValue = StringUtil.replaceString(m_curValue, "\n",
							"\\n");
					m_curValue = StringUtil.replaceString(m_curValue, "\r",
							"\\r");
				}
				m_curValue = m_curValue == null ? "" : m_curValue;
				if (isParent) {
					if (isEmpty) {
						result.append("parent.document." + formName + "."
								+ m_field[i].getName().toString().toLowerCase()
								+ formFieldAppendName + ".value='';\n");
					} else {
						result.append("parent.document." + formName + "."
								+ m_field[i].getName().toString().toLowerCase()
								+ formFieldAppendName + ".value='" + m_curValue
								+ "';\n");
					}

				} else {
					if (isEmpty) {
						result.append(formName + "."
								+ m_field[i].getName().toString().toLowerCase()
								+ formFieldAppendName + ".value='';\n");
					} else {
						result.append(formName + "."
								+ m_field[i].getName().toString().toLowerCase()
								+ formFieldAppendName + ".value='" + m_curValue
								+ "';\n");
					}
				}
				result.append("}catch(ex){}\n");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result.toString();
	}

	public boolean checkUserRight(HttpSession session, String xtlb,
			String cxdh, String gndh) {
		Hashtable rightsobj = (Hashtable) session.getAttribute("rightsobj");
		if (rightsobj != null) {
			if (rightsobj.get(xtlb + "-" + cxdh) != null) {
				if (gndh == null || gndh.equals("")
						|| gndh.toLowerCase().equals("null")) {
					return true;
				} else {
					String tmpgndh = ((Program) rightsobj
							.get(xtlb + "-" + cxdh)).getGnid();
					if (tmpgndh.indexOf(gndh) >= 0) {
						return true;
					} else {
						return false;
					}
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public List getUserCxdhGnlb(HttpSession session, String xtlb, String cxdh) {
		String gnlbString = "";
		Hashtable rightsobj = (Hashtable) session.getAttribute("rightsobj");
		if (rightsobj != null) {
			if (rightsobj.get(xtlb + "-" + cxdh) != null) {
				gnlbString = ((Program) rightsobj.get(xtlb + "-" + cxdh))
						.getGnid();
			}
		}
		if (!(gnlbString == null || gnlbString.equals("") || gnlbString
				.toLowerCase().equals("null"))) {
			String[] gnlbStrings = gnlbString.split(",");
			Arrays.sort(gnlbStrings);
			List list = new Vector();
			Function function = null;
			for (int i = 0; i < gnlbStrings.length; i++) {
				function = programFoldDao.getOneFunction(xtlb, cxdh,
						gnlbStrings[i]);
				if (function != null)
					list.add(function);
			}
			return list;
		} else {
			return null;
		}
	}

	public List getUserCxdhGnlb_filter(HttpSession session, String xtlb,
			String cxdh, String filterGnid) {
		String gnlbString = "";
		Hashtable rightsobj = (Hashtable) session.getAttribute("rightsobj");
		if (rightsobj != null) {
			if (rightsobj.get(xtlb + "-" + cxdh) != null) {
				gnlbString = ((Program) rightsobj.get(xtlb + "-" + cxdh))
						.getGnid();
			}
		}
		if (!(gnlbString == null || gnlbString.equals("") || gnlbString
				.toLowerCase().equals("null"))) {
			String[] gnlbStrings = gnlbString.split(",");
			List list = new Vector();
			Function function = null;
			for (int i = 0; i < gnlbStrings.length; i++) {
				function = programFoldDao.getOneFunction(xtlb, cxdh,
						gnlbStrings[i]);
				if (function != null) {
					if (filterGnid != null && !filterGnid.equals("")) {
						if (filterGnid.indexOf(function.getGnid()) < 0) {
							list.add(function);
						}
					} else {
						list.add(function);
					}
				}
			}
			return list;
		} else {
			return null;
		}
	}

	public String getUserCxdhGnstr(HttpSession session, String xtlb, String cxdh) {
		String gnlbString = "";
		Hashtable rightsobj = (Hashtable) session.getAttribute("rightsobj");
		if (rightsobj != null) {
			if (rightsobj.get(xtlb + "-" + cxdh) != null) {
				gnlbString = ((Program) rightsobj.get(xtlb + "-" + cxdh))
						.getGnid();
			}
		}
		return gnlbString;
	}



	public Department getSessionDepInfo(HttpSession session) {
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");
		SysUser sysUser = userSession.getSysuser();
		Department department = gDepartmentDao.getDepartment(sysUser.getGlbm());
		return department;
	}

	public SysResult checkSqlSafety(String sql) {
		SysResult result = new SysResult();
		String tmpSql = sql.toLowerCase();
		if (tmpSql.indexOf("drop") > 0) {
			result.setFlag(0);
			result.setDesc("sql语句中含有非安全关键字:drop");
			return result;
		}
		if (tmpSql.indexOf("grant") > 0) {
			result.setFlag(0);
			result.setDesc("sql语句中含有非安全关键字:grant");
			return result;
		}
		if (tmpSql.indexOf("truncate") > 0) {
			result.setFlag(0);
			result.setDesc("sql语句中含有非安全关键字:truncate");
			return result;
		}
		return result;
	}

	public int checkWebkey(HttpServletRequest request) {
		// int result=0;
		// String
		// randName=(String)request.getSession().getAttribute("_RANDNAME");
		// String
		// randValue=(String)request.getSession().getAttribute("_RANDVALUE");
		// if(randName==null||randValue==null){
		// result=0;
		// }else{
		// String randValue1=request.getParameter(randName);
		// if(randValue1==null){
		// result=0;
		// }else{
		// if(randValue1.equals(randValue)){
		// result=1;
		// }else{
		// result=0;
		// }
		// }
		// }
		// return result;
		return this.checkWebkey(request, "COMMON_KEY");
	}

	/**
	 * 检测Web调用KEY的合法性
	 * 
	 * @param request
	 * @return 1-正确 0-失败
	 */
	public int checkWebkey(HttpServletRequest request, String keyvalue) {
		HashMap hashMap = (HashMap) request.getSession().getAttribute(
				Constants.SESSION_CHECKWEBKEY);
		if (hashMap == null)
			return 0;
		SessionKey sessionKey = (SessionKey) hashMap.get(keyvalue);
		String randvalue = request.getParameter(sessionKey.getRandname());
		if (randvalue != null) {
			if (randvalue.equals(sessionKey.getRandvalue())) {
				return 1;
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}

	public void genWebKey(HttpServletRequest request, String cdbh, String gnid) {
		genWebKey(request, cdbh, gnid, "COMMON_KEY");
	}

	public void genWebKey(HttpServletRequest request) {
		genWebKey(request, "COMMON_KEY");
	}

	public void genWebKey(HttpServletRequest request, String keyname) {
		this.genWebKey(request, "", "", keyname);
	}

	/**
	 * 生成Web调用KEY 名称为_RANDDOMKEY 和_RANDOMVALUE
	 * 
	 * @param request
	 */
	public void genWebKey(HttpServletRequest request, String cdbh, String gnid,
			String keyname) {
		HashMap map = (HashMap) request.getSession().getAttribute(
				Constants.SESSION_CHECKWEBKEY);
		if (map == null) {
			map = new HashMap();
			request.getSession().setAttribute(Constants.SESSION_CHECKWEBKEY,
					map);
		}
		;
		SessionKey sessionKey = null;
		if (map.get(keyname) == null) {
			sessionKey = new SessionKey();
			map.put(keyname, sessionKey);
		} else {
			sessionKey = (SessionKey) map.get(keyname);
		}
		String randName = "abcd" + DateUtil.getSystime()
				+ WrapUtil.getRandomNormalString(2);
		String randValue = "bcde" + WrapUtil.getRandomNormalString(8);
		String randKey = "";
		if (!cdbh.equals("")) {
			randKey = cdbh;
			if (gnid.equals(""))
				randKey += "0000";
			else
				randKey += gnid;
		}
		sessionKey.setRandname(randName);
		sessionKey.setRandvalue(randValue);
		sessionKey.setRandkey(randKey);
	}

	/**
	 * 根据WebKEY获取Function对象，需要在checkWebkeyV1执行
	 * 
	 * @param request
	 * @throws Exception
	 */
	public Function getFunctionByWebKey(HttpServletRequest request)
			throws Exception {
		HashMap map = (HashMap) request.getSession().getAttribute(
				Constants.SESSION_CHECKWEBKEY);
		if (map == null) {
			throw new Exception(Constants.SAFE_CTRL_MSG_103);
		}
		;
		SessionKey sessionKey = (SessionKey) map.get("COMMON_KEY");
		if (sessionKey != null) {
			Function function = new Function();
			function.setCdbh(sessionKey.getRandkey().substring(0, 4));
			function.setGnid(sessionKey.getRandkey().substring(4, 8));
			return function;
		} else {
			throw new Exception(Constants.SAFE_CTRL_MSG_103);
		}
	}



	public Function getFunction(String xtlb, String cdbh, String gnid) {
		Function function = this.programFoldDao
				.getOneFunction(xtlb, cdbh, gnid);
		return function;
	}

	public String getGnmc(String xtlb, String cdbh, String gnid) {
		return this.programFoldDao.getGnmc(xtlb, cdbh, gnid);
	}

	public String getGnmc(String xtlb, String gnid) {
		return this.programFoldDao.getGnmc(xtlb, gnid);
	}

	public List getDepSysUsers(String glbm,String yhssyw){
		return this.sysuserDao.getDepSsywusers(glbm,yhssyw);
	}

	public List getDepPolices(String glbm) {
		return this.sysuserDao.getPoliceUserList(glbm);
	}

	public void transDepPolicesToXml(HttpServletResponse response, String glbm)
			throws Exception {
		PrintWriter printwriter;
		String _returnStrings = "";
		List list = this.sysuserDao.getPoliceUserList(glbm);
		response.setContentType("text/html; charset=GBK");
		printwriter = response.getWriter();
		response.setContentType("application/xml");
		printwriter.println("<?xml version=\"1.0\" encoding=\"GB2312\"?>");
		if (list.size() > 0) {
			Iterator iterator = list.iterator();
			printwriter.println("<root>");
			while (iterator.hasNext()) {
				BasPolice basPolice = (BasPolice) iterator.next();
				printwriter.println("<police>");
				printwriter.println("<jh>" + basPolice.getJybh() + "</jh>");
				printwriter.println("<xm>" + basPolice.getXm() + "</xm>");
				printwriter.println("</police>");
			}
			printwriter.println("</root>");
		} else {
			printwriter.println("<root>");
			printwriter.println("</root>");
		}
	}

	public BasPolice getPolice(String glbm, String jybh) {
		return this.sysuserDao.getPolice(glbm, jybh);
	}

	public String transDepListToOptionHtml(List list,String defGlbm){
		String _returnStrings="";
		Department department=null;
		for(int i=0;i<list.size();i++){
			department=(Department)list.get(i);
			if(department.getGlbm().equals(defGlbm)){
				_returnStrings=_returnStrings+"<option value="+department.getGlbm()+" selected>"+department.getBmmc()+"</option>";
			}else{
				_returnStrings=_returnStrings+"<option value="+department.getGlbm()+">"+department.getBmmc()+"</option>";
			}
		}
		return _returnStrings;
	}

	public String transDepListToCheckHtml(List list, String fieldName,
			String defval, int rowsize) {
		String result = "";
		for (int i = 0; i < list.size(); i++) {
			Department dept = (Department) list.get(i);
			if (i != 0 && i % rowsize == 0) {
				result += "<br>";
			}
			if (defval.indexOf(dept.getGlbm()) >= 0) {
				result += "<input type='checkbox' id='"
						+ dept.getGlbm()
						+ i
						+ "' name='"
						+ fieldName
						+ "' value='"
						+ dept.getGlbm()
						+ "' checked>"
						+ "<label style='background-color: #EFEFFE;width:100' for='"
						+ dept.getGlbm() + i + "'>" + dept.getBmmc()
						+ "</label>";
			} else {
				result += "<input type='checkbox' id='"
						+ dept.getGlbm()
						+ i
						+ "' name='"
						+ fieldName
						+ "' value='"
						+ dept.getGlbm()
						+ "'>"
						+ "<label style='background-color: #EFEFFE;width:100' for='"
						+ dept.getGlbm() + i + "'>" + dept.getBmmc()
						+ "</label>";
			}
		}
		return result;
	}

	public String transGlbmdmToGlbmmc(String glbmdm, String fgf) {
		String[] glbmdmStrings = glbmdm.split(fgf);
		String resultString = "";
		for (int i = 0; i < glbmdmStrings.length; i++) {
			Department dept = this.gDepartmentDao
					.getDepartment(glbmdmStrings[i]);
			if (dept != null) {
				resultString = resultString + dept.getBmmc() + fgf;
			}
		}
		if (!resultString.equals("")) {
			resultString = resultString.substring(0, resultString.length() - 1);
		}
		return resultString;
	}

	public String transDmlbToOptionHtml_filter(String xtlb, String dmlb,
			String defauls, boolean havaNull, String showType, String filter) {

		try {
			String _returnStrings = "";
			List list = this.gSysparaCodeDao.getCodes(xtlb, dmlb);
			if (showType == null
					|| (!showType.equals("1") && !showType.equals("2") && !showType
							.equals("3"))) {
				showType = "2";
			}
			if (havaNull) {
				_returnStrings = "<option value=''></option>";
			}
			if (filter == null) {
				filter = "";
			}
			if (list != null) {
				Iterator iterator = list.iterator();
				while (iterator.hasNext()) {
					Code frmCode = (Code) iterator.next();
					if (filter.indexOf(frmCode.getDmz()) == -1) {
						// 只显示代码
						if (showType.equals("1")) {
							if (frmCode.getDmz().equals(defauls)) {
								_returnStrings = _returnStrings
										+ "<option value=" + frmCode.getDmz()
										+ " selected>" + frmCode.getDmz()
										+ "</option>";
							} else {
								_returnStrings = _returnStrings
										+ "<option value=" + frmCode.getDmz()
										+ ">" + frmCode.getDmz() + "</option>";
							}
						}
						// 只显示名称
						if (showType.equals("2")) {
							if (frmCode.getDmz().equals(defauls)) {
								_returnStrings = _returnStrings
										+ "<option value=" + frmCode.getDmz()
										+ " selected>" + frmCode.getDmsm1()
										+ "</option>";
							} else {
								_returnStrings = _returnStrings
										+ "<option value=" + frmCode.getDmz()
										+ ">" + frmCode.getDmsm1()
										+ "</option>";
							}
						}
						// 显示代码和名称
						if (showType.equals("3")) {
							if (frmCode.getDmz().equals(defauls)) {
								_returnStrings = _returnStrings
										+ "<option value=" + frmCode.getDmz()
										+ " selected>" + frmCode.getDmz() + ":"
										+ frmCode.getDmsm1() + "</option>";
							} else {
								_returnStrings = _returnStrings
										+ "<option value=" + frmCode.getDmz()
										+ ">" + frmCode.getDmz() + ":"
										+ frmCode.getDmsm1() + "</option>";
							}
						}
					}
				}
			}
			return _returnStrings;
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
	}

	public String transDmlbToOptionHtml_assign(String xtlb, String dmlb,
			String defauls, boolean havaNull, String showType, String assign) {

		try {
			String _returnStrings = "";
			List list = this.gSysparaCodeDao.getCodes(xtlb, dmlb);
			if (showType == null || showType.equals("")
					|| showType.toLowerCase().equals("null")) {
				showType = "2";
			}
			if (havaNull) {
				_returnStrings = "<option value=''></option>";
			}
			if (assign == null) {
				assign = "";
			}
			if (list != null) {
				Iterator iterator = list.iterator();
				while (iterator.hasNext()) {
					Code frmCode = (Code) iterator.next();
					if (assign.indexOf(frmCode.getDmz()) > -1) {
						// 只显示代码
						if (showType.equals("1")) {
							if (frmCode.getDmz().equals(defauls)) {
								_returnStrings = _returnStrings
										+ "<option value=" + frmCode.getDmz()
										+ " selected>" + frmCode.getDmz()
										+ "</option>";
							} else {
								_returnStrings = _returnStrings
										+ "<option value=" + frmCode.getDmz()
										+ ">" + frmCode.getDmz() + "</option>";
							}
						}
						// 只显示名称
						if (showType.equals("2")) {
							if (frmCode.getDmz().equals(defauls)) {
								_returnStrings = _returnStrings
										+ "<option value=" + frmCode.getDmz()
										+ " selected>" + frmCode.getDmsm1()
										+ "</option>";
							} else {
								_returnStrings = _returnStrings
										+ "<option value=" + frmCode.getDmz()
										+ ">" + frmCode.getDmsm1()
										+ "</option>";
							}
						}
						// 显示代码和名称
						if (showType.equals("3")) {
							if (frmCode.getDmz().equals(defauls)) {
								_returnStrings = _returnStrings
										+ "<option value=" + frmCode.getDmz()
										+ " selected>" + frmCode.getDmz() + ":"
										+ frmCode.getDmsm1() + "</option>";
							} else {
								_returnStrings = _returnStrings
										+ "<option value=" + frmCode.getDmz()
										+ ">" + frmCode.getDmz() + ":"
										+ frmCode.getDmsm1() + "</option>";
							}
						}

						// 显示代码和代码值+代码说明2
						if (showType.equals("4")) {
							if (frmCode.getDmz().equals(defauls)) {
								_returnStrings = _returnStrings
										+ "<option value=" + frmCode.getDmz()
										+ " selected>" + frmCode.getDmz() + ":"
										+ frmCode.getDmsm2() + "</option>";
							} else {
								_returnStrings = _returnStrings
										+ "<option value=" + frmCode.getDmz()
										+ ">" + frmCode.getDmz() + ":"
										+ frmCode.getDmsm2() + "</option>";
							}
						}
						// 显示代码和代码值+代码说明2
						if (showType.equals("5")) {
							if (frmCode.getDmsm2().equals(defauls)) {
								_returnStrings = _returnStrings
										+ "<option value=" + frmCode.getDmsm2()
										+ " selected>" + frmCode.getDmsm2()
										+ "</option>";
							} else {
								_returnStrings = _returnStrings
										+ "<option value=" + frmCode.getDmsm2()
										+ ">" + frmCode.getDmsm2()
										+ "</option>";
							}
						}
						// 显示代码和DMSM1 值为代码说明2
						if (showType.equals("6")) {
							if (frmCode.getDmsm2().equals(defauls)) {
								_returnStrings = _returnStrings
										+ "<option value=" + frmCode.getDmsm2()
										+ " selected>" + frmCode.getDmz() + ":"
										+ frmCode.getDmsm1() + "</option>";
							} else {
								_returnStrings = _returnStrings
										+ "<option value=" + frmCode.getDmsm2()
										+ ">" + frmCode.getDmz() + ":"
										+ frmCode.getDmsm1() + "</option>";
							}
						}
						// 显示DMSM1和DMSM1
						if (showType.equals("7")) {
							if (frmCode.getDmsm2().equals(defauls)) {
								_returnStrings = _returnStrings
										+ "<option value=" + frmCode.getDmsm1()
										+ " selected>" + frmCode.getDmsm1()
										+ "</option>";
							} else {
								_returnStrings = _returnStrings
										+ "<option value=" + frmCode.getDmsm1()
										+ ">" + frmCode.getDmsm1()
										+ "</option>";
							}
						}

					}
				}
			}
			return _returnStrings;
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
	}

	public String getLocalServerIpdz() {
		String ip = null;
		try {
			InetAddress localhost = InetAddress.getLocalHost();
			ip = localhost.getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return ip;
	}

	public String removeFzjh(String fzjhdz, String ip) {
		String iparr[] = ip.split(",");
		if (fzjhdz == null) {
			return iparr[0];
		}
		String fzjhdzarr[] = fzjhdz.split(",");

		for (int i = 0; i < iparr.length; i++) {
			boolean flag = true;
			for (int j = 0; j < fzjhdzarr.length; j++) {
				if (fzjhdzarr[j].equals(iparr[i])) {
					flag = false;
				}
			}
			if (flag == true) {
				return iparr[i];
			}
		}
		return null;
	}

	/*
	 * private String getMonKey(String xtlb,String cdbh,String gnid,String
	 * czlx){ String _key=xtlb+"-"+cdbh;
	 * if(!(gnid==null||gnid.equals("")||gnid.toLowerCase().equals("null"))){
	 * _key=_key+"-"+gnid; }else{ _key=_key+"-"+"0000"; }
	 * if(!(czlx==null||czlx.equals("")||czlx.toLowerCase().equals("null"))){
	 * _key=_key+"-"+czlx; } return _key; }
	 * 
	 * private String getNowTime(String _type){ Calendar
	 * ca=Calendar.getInstance(); String todaystr=""; if(_type.equals("1")){ int
	 * year=ca.get(Calendar.YEAR);// 获取年份 int month=ca.get(Calendar.MONTH);//
	 * 获取月份 int day=ca.get(Calendar.DATE);// 获取日
	 * todaystr=String.valueOf(year)+"-"
	 * +String.valueOf(month)+"-"+String.valueOf(day); }else
	 * if(_type.equals("2")){ int hour=ca.get(Calendar.HOUR_OF_DAY);// 小时
	 * todaystr=String.valueOf(hour); }else if(_type.equals("3")){ DateFormat
	 * d2=DateFormat.getDateTimeInstance(); Date now=new
	 * Date(System.currentTimeMillis()); todaystr=d2.format(now); } return
	 * todaystr; }
	 */
	/**
	 * 保存系统监控信息
	 * 
	 * @param request
	 */
	private void saveFrmSysrun(FrmSysrun frmSysrun) {
		SysResult result = null;
		try {
			result = frmDataObjDao.setOracleData(frmSysrun, 1);
			if (result.getFlag() == 1) {
				this.sysDao.saveFrmSysrun();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doBeginCall(HttpServletRequest request, String xtlb,
			String cdbh, String gnid, String czlx) throws Exception {
		HttpSession session = request.getSession();
		if (session == null) {
			throw new Exception("TERR01:系统登陆超时，连接已失效，请重新登陆系统！");
		}
		if (session.getAttribute("rand_code_state").equals("1")) {
			throw new Exception("TERR11:系统登陆超时，连接已失效，请重新登陆系统！");
		}
		if (checkUserRight(session, xtlb, cdbh, gnid) == false) {
			throw new Exception("TERR02:无权访问该功能模块！");
		}		
		UserSession userSession = (UserSession) session.getAttribute("userSession");
		SysUser sysUser = userSession.getSysuser();
		if (!(sysUser == null || sysUser.getYhdh() == null
				|| sysUser.getYhdh().equals("") || sysUser.getYhdh()
				.toLowerCase().equals("null"))) {
			String ip = getRemoteIpdz(request);
			FrmQueryerrlog queryErrLog = new FrmQueryerrlog();
			queryErrLog.setYhdh(sysUser.getYhdh());
			queryErrLog.setIpdz(ip);
			String strXtlb = xtlb;
			String strCdbh = cdbh;
			String strGnid = gnid;
			if (strXtlb == null || strXtlb.equals("")
					|| strXtlb.toLowerCase().equals("null")) {
				strXtlb = "00";
			}
			if (strCdbh == null || strCdbh.equals("")
					|| strCdbh.toLowerCase().equals("null")) {
				strCdbh = "0000";
			}
			if (strGnid == null || strGnid.equals("")
					|| strGnid.toLowerCase().equals("null")) {
				strGnid = "0000";
			}
			queryErrLog.setGnbh(strXtlb + "-" + strCdbh + "-" + strGnid);
			/*SysResult result = this.saveFrmQueryerrlog(queryErrLog);
			if (result != null && result.getFlag() == 0) {
				throw new Exception("TERR12:超过高频访问限制次数，当日不能再次访问该功能模块！");
			}*/
		}
		session.setAttribute("_BeginCall", String.valueOf(System
				.currentTimeMillis()));
	}

	/**
	 * 生成Web调用KEY 名称为_RANDDOMKEY 和_RANDOMVALUE，同时计算执行结束时间
	 * 
	 * @param request
	 */
	public synchronized void doEndCall(HttpServletRequest request, String xtlb,
			String cdbh, String gnid, String czlx, int jkyxsj) throws Exception {
		if (!(czlx == null || czlx.equals("") || czlx.toLowerCase().equals(
				"null"))) {
			HttpSession session = request.getSession();
			long st = Long.parseLong((String) session
					.getAttribute("_BeginCall"));
			long et = System.currentTimeMillis();
			long sjyxsj = et - st;
			FrmSysrun frmSysRun = new FrmSysrun();
			frmSysRun.setBcfwsj(String.valueOf(sjyxsj));
			frmSysRun.setBzfwsj(String.valueOf(jkyxsj));
			frmSysRun.setXtlb(xtlb);
			frmSysRun.setCdbh(cdbh);
			if (gnid == null || gnid.equals("")
					|| gnid.toLowerCase().equals("null")) {
				frmSysRun.setGnid("0000");
			} else {
				frmSysRun.setGnid(gnid);
			}
			frmSysRun.setCzlx(czlx);
			this.saveFrmSysrun(frmSysRun);
		}
	}

	/**
	 * 获取exception中oracle错误代码信息
	 * 
	 * @param e异常对象
	 * @return错误代码值
	 */
	public String getOraErrCode(String errMsg) {
		if (errMsg == null || errMsg.equals("")
				|| errMsg.toLowerCase().equals("null"))
			return "";
		String errcode = "";
		int index = 0;
		if (errMsg != null
				&& (index = errMsg.toUpperCase().indexOf("ORA-")) > -1) {
			errcode = errMsg.substring(index + 4, index + 9);
		}
		if (errMsg != null
				&& (index = errMsg.toUpperCase().indexOf("TERR")) > -1) {
			errcode = "TERR";
		}
		return errcode;
	}

	public void saveRbspLog(FrmRbspLog frmRbspLog) {
		SysResult result = null;
		try {
			result = frmDataObjDao.setOracleData(frmRbspLog, 1);
			if (result.getFlag() == 1) {
				this.sysDao.saveFrmRbspLog();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String procJspCheckCode(String xtlb, String cdbh, String xh,
			String zlj) throws Exception {
		String resultString = "";
		if (zlj.equals("")) {
			resultString = "if(checkallfields(checkfields,1)==0){\n";
			resultString += "return 0;";
			resultString += "}";
		} else {
			resultString = "if(checkallfields(checkfields_" + zlj
					+ ",1,checklen_" + zlj + ")==0){\n";
			resultString += "return 0;";
			resultString += "}";
		}
		return resultString;
	}

	public String transDepGlxzqhToOptionHtml(String glbm, String dmlb,
			String defauls, boolean havaNull, String showType) {
		return null;
	}

	// 获取全国身份下拉框
	public String getQgsfOptionHtml(String defauls) {

		try {
			String _returnStrings = "";
			List list = this.gSysparaCodeDao.getCodes("00", "0032");

			for (int i = 0; i < list.size(); i++) {
				Code frmCode = (Code) list.get(i);

				if (frmCode.getDmsm2().equals(defauls)) {
					_returnStrings = _returnStrings + "<option value="
							+ frmCode.getDmsm2() + " selected>"
							+ frmCode.getDmsm2() + "</option>";
				} else {
					_returnStrings = _returnStrings + "<option value="
							+ frmCode.getDmsm2() + ">" + frmCode.getDmsm2()
							+ "</option>";
				}
			}
			return _returnStrings;
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
	}

	// 获取本地行政区划List
	public List getBdfzjgList() {
		String bdfzjgString = this.gSysparaCodeDao.getSysPara("00", "2", "BDFZJG")
				.getMrz();
		List resultList = new Vector();
		String[] bdfzjgStrings = bdfzjgString.split(",");
		Code code = null;
		for (int i = 0; i < bdfzjgStrings.length; i++) {
			code = new Code();
			code.setDmz(bdfzjgStrings[i]);
			code.setDmsm1(bdfzjgStrings[i]);
			resultList.add(code);
		}
		return resultList;
	}

	// 获取本地行政区划List
	public List getBdfzjgList(String userFzjg) {
		String bdfzjgString = this.gSysparaCodeDao.getSysPara("00", "2", "BDFZJG")
				.getMrz();
		List resultList = new Vector();
		Code code = null;
		if (userFzjg.indexOf("O") >= 0) {
			String[] bdfzjgStrings = bdfzjgString.split(",");
			for (int i = 0; i < bdfzjgStrings.length; i++) {
				code = new Code();
				code.setDmz(bdfzjgStrings[i]);
				code.setDmsm1(bdfzjgStrings[i]);
				resultList.add(code);
			}
		} else {
			code = new Code();
			code.setDmz(userFzjg);
			code.setDmsm1(userFzjg);
			resultList.add(code);
		}
		return resultList;
	}

	public List queryForList(String strSql, Class bClass) {
		return this.sysDao.queryForList(strSql, bClass);
	}

	/**
	 * 获取部门下属的民警信息
	 * 
	 * @param glbm 管理部门
	 * @param yhssyw 用户所属业务 1-科技 2-车管 3-驾管 4-违法 5-事故 6-剧毒品 7-其他
	 * @return
	 */
	public List getBamjList(String glbm, String yhssyw) {
		List list = this.sysuserDao.getDepPoliceusers_Like(glbm, yhssyw);
		return list;
	}

	/**
	 * 将当前管理部门下所有办案民警显示出来
	 * 
	 * @param glbm 管理部门
	 * @param defaults 缺省值
	 * @param haveNull 是否可空
	 * @param showtype 显示方式 1:value为姓名 2:value为拼音
	 * @return
	 */
	public String transBamjToOptionHtml(String glbm, String defauls,
			boolean havaNull, String showType, String yhssyw) {

		try {

			String _returnStrings = "";

			if (havaNull) {
				_returnStrings = "<option value=''></option>";
			}
			List list = null;
			try {
				list = this.sysuserDao.getDepPoliceusers(glbm, yhssyw);
			} catch (Exception ex) {

			}
			int flag = 0;
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					SysUser tmp = (SysUser) list.get(i);
					if (tmp != null) {
						if (showType.equals("1")) {
							if (tmp.getXm().equals(defauls)) {
								flag = 1;
								_returnStrings = _returnStrings
										+ "<option value=" + tmp.getXm()
										+ " selected>" + tmp.getXm()
										+ "</option>";
							} else {
								_returnStrings = _returnStrings
										+ "<option value=" + tmp.getXm() + ">"
										+ tmp.getXm() + "</option>";
							}
						}
						if (showType.equals("2")) {
							if (tmp.getXm().equals(defauls)) {
								flag = 1;
								_returnStrings = _returnStrings
										+ "<option value="
										+ StringUtil.getAllFirstLetter(tmp
												.getXm()) + " selected>"
										+ tmp.getXm() + "</option>";
							} else {
								_returnStrings = _returnStrings
										+ "<option value="
										+ StringUtil.getAllFirstLetter(tmp
												.getXm()) + ">" + tmp.getXm()
										+ "</option>";
							}
						}
						if (showType.equals("3")) {
							if (tmp.getXm().equals(defauls)) {
								flag = 1;
								_returnStrings = _returnStrings
										+ "<option value=" + tmp.getYhdh()
										+ " selected>" + tmp.getXm()
										+ "</option>";
							} else {
								_returnStrings = _returnStrings
										+ "<option value=" + tmp.getYhdh()
										+ ">" + tmp.getXm() + "</option>";
							}
						}
					}
				}
				if (flag == 0 && !defauls.equals("")) {
					if (showType.equals("1")) {
						_returnStrings = _returnStrings + "<option value="
								+ defauls + " selected>" + defauls
								+ "</option>";
					} else {
						_returnStrings = _returnStrings + "<option value="
								+ StringUtil.getAllFirstLetter(defauls)
								+ " selected>" + defauls + "</option>";
					}
				}
			}
			return _returnStrings;
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
	}

	

	public SysUser getYhxm(String yhdh) {
		return sysuserDao.getSysuserFromMem(yhdh);
	}

	// 是否下拉框
	// 20100316 zhoujn
	public void transBoolToOptionHtml(JspWriter out, String strDefault,
			boolean bXsdmz) {
		try {
			if (strDefault == null)
				strDefault = "";

			if (strDefault.equals("1")) {
				out.println("<option value='1' selected>是</option>");
				out.println("<option value='2'>否</option>");
			} else if (strDefault.equals("2")) {
				out.println("<option value='1'>是</option>");
				out.println("<option value='2' selected>否</option>");
			} else {
				out.println("<option value='1' selected>是</option>");
				out.println("<option value='2'>否</option>");
			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}

	

	public SysUser getSysUser(String yhdh) {
		SysUser sysUser = this.sysuserDao.getSysuser(yhdh);
		return sysUser;
	}

	public SysUser getSysUser(String glbm, String xm) {
		return sysuserDao.getSysuser(glbm, xm);
	}

	public BasPolice getRyjbxx(String sfzmhm,String sfmj) throws Exception{
		return this.sysDao.getRyjbxx(sfzmhm,sfmj);
	}

	// 取随机数
	public int getRand() {
		Random rand = new Random();
		int randnum = Math.abs(rand.nextInt());
		return randnum;
	}


	/**
	 * 根据省份头获取异地交换应用服务器IP地址
	 */
	public DbResult Getjhkwepip(String sft) {
		DbResult result = new DbResult();
		try {
			result = this.sysDao.Getjhkwepip(sft);
		} catch (Exception e) {
			result.setCode(0);
			result.setMsg1(e.getMessage());
		}
		return result;
	}

	// 输出消息提示
	public String outputMessage(HttpSession session) {
		SmsContent smsContent = new SmsContent();
		PageController controller = new PageController();
		controller.setPageSize(10);
		
		UserSession userSession = (UserSession) session.getAttribute("userSession");
		String yhdh = userSession.getYhdh();
		
		smsContent.setJsyhdh(yhdh);
		smsContent.setBj("0");
		int sms = this.codeDao.getSmsCount(yhdh);
		List list = this.codeDao.getUserSmsList(smsContent, controller);
		StringBuffer resultBuffer = new StringBuffer();
		if (sms > 0) {
			resultBuffer.append("<div class=\"focus\" id=\"sitefocus\" >");
			resultBuffer.append("<div class=\"bm\">");
			resultBuffer.append("<div class=\"bm_h cl\">");
			resultBuffer
					.append("<a href=\"javascript:;\" onclick=\" closetip();\" class=\"y\" title=\"关闭\">关闭</a>");
			resultBuffer.append("<h2>工作提醒</h2>");
			resultBuffer.append("</div>");
			resultBuffer.append("<div class=\"bm_c\">");
			resultBuffer.append("<dl class=\"xld cl bbda\">");
			for (int i = 0; i < list.size(); i++) {
				SmsContent temp = (SmsContent) list.get(i);
				if (temp.getXxbt() != null && !temp.getXxbt().equals("")) {
					resultBuffer
							.append("<dt><a href=\"#\" class=\"xi2\" onclick=\"openwin('code.frm?method=displayUserSms&xxbh="
									+ temp.getXxbh()
									+ "');\">【"
									+ DateUtil.formatDate(temp.getCjsj(),
											"MM-dd")
									+ "】"
									+ temp.getXxbt()
									+ "</a></dt>");

				} else {
					resultBuffer
							.append("<dt><a href=\"#\" class=\"xi2\" onclick=\"openwin('code.frm?method=displayUserSms&xxbh="
									+ temp.getXxbh()
									+ "');\">【"
									+ DateUtil.formatDate(temp.getCjsj(),
											"MM-dd")
									+ "】"
									+ temp.getTitle()
									+ "</a></dt>");
				}
				// resultBuffer.append("<dd>"+temp.getXxnr()+"</dd>");
			}
			resultBuffer.append("</dl>");
			resultBuffer
					.append("<p class=\"ptn hm\"><a href=\"#\" class=\"xi2\" onclick=\"openwin('code.frm?method=queryUserSmsList');\" >共"
							+ sms + "条，查看 更多>></a></p>");
			resultBuffer.append("</div>");
			resultBuffer.append("</div>");
			resultBuffer.append("</div>");
		}
		// System.out.println(resultBuffer.toString());
		return resultBuffer.toString();
	}

	// 获取远程客户端地址
	public String getRemoteIpdz(HttpServletRequest request) {
		// 20120104，由于上海F5问题，只对上海从request获取IP地址
		// String djglbmString=this.getSysParaValue("00","DJGLBM");
		String ipString = "";
		ipString = request.getHeader("X-Forwarded-For");
		if (ipString == null || ipString.length() == 0
				|| "unknown".equalsIgnoreCase(ipString)) {
			ipString = request.getRemoteAddr();
		}
		return ipString;
	}

	public String getTjny(String strDqrq,String tjksday){
		String resultString = strDqrq;
		String yyyy = resultString.substring(0, 4);
		String mm = resultString.substring(5, 7);
		String dd = resultString.substring(8, 10);
		if (dd.compareTo(Constants.SYS_SG_TJKSR) >= 0) {
			if (Constants.SYS_SG_SFSYY.equals("2")) {
				resultString = yyyy + mm;
			} else {
				if (mm.equals("12")) {
					resultString = (new Integer(yyyy).intValue() + 1) + "01";
				} else {
					mm = (new Integer(mm).intValue() + 1) + "";
					if (mm.length() == 1) {
						resultString = yyyy + "0" + mm;
					} else {
						resultString = yyyy + mm;
					}
				}
			}
		} else {
			if (Constants.SYS_SG_SFSYY.equals("2")) {
				if (mm.equals("01")) {
					resultString = (new Integer(yyyy).intValue() - 1) + "12";
				} else {
					mm = (new Integer(mm).intValue() - 1) + "";
					if (mm.length() == 1) {
						resultString = yyyy + "0" + mm;
					} else {
						resultString = yyyy + mm;
					}
				}
			} else {
				resultString = yyyy + mm;
			}

		}
		return resultString;
	}

	public String getTjksrq(String strDqrq, String tjksday) {
		String resultString = strDqrq;
		String yyyy = resultString.substring(0, 4);
		String mm = resultString.substring(5, 7);
		String dd = resultString.substring(8, 10);
		if (dd.compareTo(Constants.SYS_SG_TJKSR) >= 0) {
			resultString = yyyy + "-" + mm + "-" + Constants.SYS_SG_TJKSR;
		} else {
			if (mm.equals("01")) {
				resultString = (new Integer(yyyy).intValue() - 1) + "-12-"
						+ Constants.SYS_SG_TJKSR;
			} else {
				mm = (new Integer(mm).intValue() - 1) + "";
				if (mm.length() == 1) {
					resultString = yyyy + "-0" + mm + "-"
							+ Constants.SYS_SG_TJKSR;
				} else {
					resultString = yyyy + "-" + mm + "-"
							+ Constants.SYS_SG_TJKSR;
				}
			}
		}
		return resultString;
	}

	public String transDmlbToJsArr(String xtlb, String dmlb, String ywdx,
			String arrname) {
		List list = this.getCodes(xtlb, dmlb, ywdx);
		StringBuffer buffer = new StringBuffer();
		buffer.append("var " + arrname + " = new Array(" + list.size() + ");");
		Code code = null;
		for (int i = 0; i < list.size(); i++) {
			code = (Code) list.get(i);
			buffer.append("var arr=new Array(5);");
			buffer.append("arr[0] = '" + code.getDmz() + "';");
			buffer.append("arr[1] = '" + code.getDmsm1() + "';");
			buffer.append("arr[2] = '" + code.getDmsm2() + "';");
			buffer.append("arr[3] = '" + code.getDmsm3() + "';");
			buffer.append("arr[4] = '" + code.getDmsm4() + "';");
			buffer.append(arrname + "[" + i + "] = arr;");
		}
		return buffer.toString();
	}

	/**
	 * 将行政区划转化为列表项HTML信息
	 * 
	 * @param xzqhStr 行政区划组合字符全，如320100,320200。
	 * @param defaults 缺省值
	 * @param haveNull 是否可空
	 * @param showtype 显示方式 1:只显示代码 2:只显示内容 3:显示代码和内容 4:value为xzqh:详细描述
	 *            caption为xzqh:简单描述
	 * @return
	 */
	public String transXzqhToOptionHtml(String xzqhStr, String defauls,
			boolean havaNull, String showType) {
		try {
			String _returnStrings = "";

			if (havaNull) {
				_returnStrings = "<option value=''></option>";
			}

			String glxzqhString = xzqhStr;
			String arr[] = null;
			if (glxzqhString != null) {
				arr = glxzqhString.split(",");
			} else {
				arr = new String[0];
			}
			String xzqh = null;
			Code frmCode = null;
			for (int i = 0; i < arr.length; i++) {
				xzqh = arr[i];
                // getFrmLocalxzqhCode-0050
                frmCode = gBasService.getLocalxzqhCode(xzqh);
				if (frmCode != null) {
					// 只显示代码
					if (showType.equals("1")) {
						if (frmCode.getDmz().equals(defauls)) {
							_returnStrings = _returnStrings + "<option value="
									+ frmCode.getDmz() + " selected>"
									+ frmCode.getDmz() + "</option>";
						} else {
							_returnStrings = _returnStrings + "<option value="
									+ frmCode.getDmz() + ">" + frmCode.getDmz()
									+ "</option>";
						}
					}
					// 只显示名称
					if (showType.equals("2")) {
						if (frmCode.getDmz().equals(defauls)) {
							_returnStrings = _returnStrings + "<option value="
									+ frmCode.getDmz() + " selected>"
									+ frmCode.getDmsm1() + "</option>";
						} else {
							_returnStrings = _returnStrings + "<option value="
									+ frmCode.getDmz() + ">"
									+ frmCode.getDmsm1() + "</option>";
						}
					}
					// 显示代码和名称
					if (showType.equals("3")) {
						if (frmCode.getDmz().equals(defauls)) {
							_returnStrings = _returnStrings + "<option value="
									+ frmCode.getDmz() + " selected>"
									+ frmCode.getDmz() + ":"
									+ frmCode.getDmsm1() + "</option>";
						} else {
							_returnStrings = _returnStrings + "<option value="
									+ frmCode.getDmz() + ">" + frmCode.getDmz()
									+ ":" + frmCode.getDmsm1() + "</option>";
						}
					}
					if (showType.equals("4")) {
						if (frmCode.getDmz().equals(defauls)) {
							_returnStrings = _returnStrings + "<option value="
									+ frmCode.getDmz() + ":"
									+ frmCode.getDmsm2() + " selected>"
									+ frmCode.getDmz() + ":"
									+ frmCode.getDmsm1() + "</option>";
						} else {
							_returnStrings = _returnStrings + "<option value="
									+ frmCode.getDmz() + ":"
									+ frmCode.getDmsm2() + ">"
									+ frmCode.getDmz() + ":"
									+ frmCode.getDmsm1() + "</option>";
						}
					}
				}
			}
			return _returnStrings;
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
	}

	/**
	 * 将行政区划转化为列表项HTML信息
	 * 
	 * @param xzqhStr 行政区划组合字符全，如320100,320200。
	 * @param defaults 缺省值
	 * @param haveNull 是否可空
	 * @param showtype 显示方式 1:只显示代码 2:只显示内容 3:显示代码和内容 4:value为xzqh:详细描述
	 *            caption为xzqh:简单描述
	 * @return
	 */
	public String transAllXzqhToOptionHtml(String xzqhStr, String defauls,
			boolean havaNull, String showType) {
		try {
			String _returnStrings = "";

			if (havaNull) {
				_returnStrings = "<option value=''></option>";
			}

			String glxzqhString = xzqhStr;
			String arr[] = null;
			if (glxzqhString != null) {
				arr = glxzqhString.split(",");
			} else {
				arr = new String[0];
			}
			String xzqh = null;
			Code frmCode = null;
			for (int i = 0; i < arr.length; i++) {
				xzqh = arr[i];
                // getFrmLocalxzqhCode-0050
                frmCode = gBasService.getXzqhCode(xzqh);
				if (frmCode != null) {
					// 只显示代码
					if (showType.equals("1")) {
						if (frmCode.getDmz().equals(defauls)) {
							_returnStrings = _returnStrings + "<option value="
									+ frmCode.getDmz() + " selected>"
									+ frmCode.getDmz() + "</option>";
						} else {
							_returnStrings = _returnStrings + "<option value="
									+ frmCode.getDmz() + ">" + frmCode.getDmz()
									+ "</option>";
						}
					}
					// 只显示名称
					if (showType.equals("2")) {
						if (frmCode.getDmz().equals(defauls)) {
							_returnStrings = _returnStrings + "<option value="
									+ frmCode.getDmz() + " selected>"
									+ frmCode.getDmsm1() + "</option>";
						} else {
							_returnStrings = _returnStrings + "<option value="
									+ frmCode.getDmz() + ">"
									+ frmCode.getDmsm1() + "</option>";
						}
					}
					// 显示代码和名称
					if (showType.equals("3")) {
						if (frmCode.getDmz().equals(defauls)) {
							_returnStrings = _returnStrings + "<option value="
									+ frmCode.getDmz() + " selected>"
									+ frmCode.getDmz() + ":"
									+ frmCode.getDmsm1() + "</option>";
						} else {
							_returnStrings = _returnStrings + "<option value="
									+ frmCode.getDmz() + ">" + frmCode.getDmz()
									+ ":" + frmCode.getDmsm1() + "</option>";
						}
					}
					if (showType.equals("4")) {
						if (frmCode.getDmz().equals(defauls)) {
							_returnStrings = _returnStrings + "<option value="
									+ frmCode.getDmz() + ":"
									+ frmCode.getDmsm2() + " selected>"
									+ frmCode.getDmz() + ":"
									+ frmCode.getDmsm1() + "</option>";
						} else {
							_returnStrings = _returnStrings + "<option value="
									+ frmCode.getDmz() + ":"
									+ frmCode.getDmsm2() + ">"
									+ frmCode.getDmz() + ":"
									+ frmCode.getDmsm1() + "</option>";
						}
					}
				}
			}
			return _returnStrings;
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
	}
	
	private List getCodes(String xtlb, String dmlb, String ywdx) {
		List list = null;
		try {
			if (ywdx == null || ywdx.equals("")) {
				list = this.gSysparaCodeDao.getCodes(xtlb, dmlb);
			} else {
				list = new Vector();
				List listtmp = this.gSysparaCodeDao.getCodes(xtlb, dmlb);
				Code code = null;
				for (int i = 0; i < listtmp.size(); i++) {
					code = (Code) listtmp.get(i);
					if (code.getYwdx().equals("")
							|| code.getYwdx().indexOf(ywdx) >= 0) {
						list.add(code);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}