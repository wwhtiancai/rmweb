package com.tmri.pub.service;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import com.tmri.framework.bean.FrmRbspLog;
import com.tmri.framework.bean.SysResult;
import com.tmri.pub.bean.BasPolice;
import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.bean.Function;
import com.tmri.share.frm.bean.SysUser;
import com.tmri.share.ora.bean.DbResult;

public interface SysService {

	/**
	 * 生成Web调用KEY 名称为_RANDDOMKEY 和_RANDOMVALUE，同时计算执行结束时间
	 * 
	 * @param request
	 */
	public void doEndCall(HttpServletRequest request, String xtlb, String cdbh,
			String gnid, String czlx, int jkyxsj) throws Exception;

	public void setPageValue(Object obj, PrintWriter pw, String formName,
			String formFieldAppendName, boolean isParent, boolean isEmpty);

	public String getSetPageValue(Object obj, String formName,
			String formFieldAppendName, boolean isParent, boolean isEmpty);

	public List getUserCxdhGnlb(HttpSession session, String xtlb, String cxdh);

	public List getUserCxdhGnlb_filter(HttpSession session, String xtlb,
			String cxdh, String filterGnid);

	public String getUserCxdhGnstr(HttpSession session, String xtlb, String cxdh);

	public Department getSessionDepInfo(HttpSession session);

	public SysResult checkSqlSafety(String sql);

	public int checkWebkey(HttpServletRequest request);

	/**
	 * 检测Web调用KEY的合法性
	 * 
	 * @param request
	 * @return 1-正确 0-失败
	 */
	public int checkWebkey(HttpServletRequest request, String keyvalue);

	public void genWebKey(HttpServletRequest request, String cdbh, String gnid);

	public void genWebKey(HttpServletRequest request);

	public void genWebKey(HttpServletRequest request, String keyname);

	/**
	 * 生成Web调用KEY 名称为_RANDDOMKEY 和_RANDOMVALUE
	 * 
	 * @param request
	 */
	public void genWebKey(HttpServletRequest request, String cdbh, String gnid,
			String keyname);

	/**
	 * 根据WebKEY获取Function对象，需要在checkWebkeyV1执行
	 * 
	 * @param request
	 * @throws Exception
	 */
	public Function getFunctionByWebKey(HttpServletRequest request)
			throws Exception;


	public Function getFunction(String xtlb, String cdbh, String gnid);

	public List getDepSysUsers(String glbm, String yhssyw);

	public List getDepPolices(String glbm);

	public void transDepPolicesToXml(HttpServletResponse response, String glbm)
			throws Exception;

	public BasPolice getPolice(String glbm, String jybh);

	public String transDepListToOptionHtml(List list, String defGlbm);

	public String transDepListToCheckHtml(List list, String fieldName,
			String defval, int rowsize);

	public String transGlbmdmToGlbmmc(String glbmdm, String fgf);

	public String transDmlbToOptionHtml_filter(String xtlb, String dmlb,
			String defauls, boolean havaNull, String showType, String filter);

	public String transDmlbToOptionHtml_assign(String xtlb, String dmlb,
			String defauls, boolean havaNull, String showType, String assign);

	public String transDmlbToJsArr(String xtlb, String dmlb, String ywdx,
			String arrname);

	public String transXzqhToOptionHtml(String xzqhStr, String defauls,
			boolean havaNull, String showType);

	public String transAllXzqhToOptionHtml(String xzqhStr, String defauls,
			boolean havaNull, String showType);

	public String getLocalServerIpdz();

	public String removeFzjh(String fzjhdz, String ip);

	public void doBeginCall(HttpServletRequest request, String xtlb,
			String cdbh, String gnid, String czlx) throws Exception;

	/**
	 * 获取exception中oracle错误代码信息
	 * 
	 * @param e异常对象
	 * @return错误代码值
	 */
	public String getOraErrCode(String errMsg);

	public void saveRbspLog(FrmRbspLog frmRbspLog);

	public String procJspCheckCode(String xtlb, String cdbh, String xh,
			String zlj) throws Exception;

	public String transDepGlxzqhToOptionHtml(String glbm, String dmlb,
			String defauls, boolean havaNull, String showType);


	// 获取全国身份下拉框
	public String getQgsfOptionHtml(String defauls);

	// 获取本地行政区划List
	public List getBdfzjgList();

	// 获取本地行政区划List
	public List getBdfzjgList(String userFzjg);

	public List queryForList(String strSql, Class bClass);

	/**
	 * 获取部门下属的民警信息
	 * 
	 * @param glbm 管理部门
	 * @param yhssyw 用户所属业务 1-科技 2-车管 3-驾管 4-违法 5-事故 6-剧毒品 7-其他
	 * @return
	 */
	public List getBamjList(String glbm, String yhssyw);

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
			boolean havaNull, String showType, String yhssyw);

	public SysUser getYhxm(String yhdh);

	// 是否下拉框
	// 20100316 zhoujn
	public void transBoolToOptionHtml(JspWriter out, String strDefault,
			boolean bXsdmz);

	public SysUser getSysUser(String yhdh);

	public SysUser getSysUser(String glbm, String xm);


	public BasPolice getRyjbxx(String sfzmhm, String sfmj) throws Exception;

	// 取随机数
	public int getRand();

	/**
	 * 根据省份头获取异地交换应用服务器IP地址
	 */
	public DbResult Getjhkwepip(String sft);

	// 输出消息提示
	public String outputMessage(HttpSession session);


	public String getTjny(String strDqrq, String tjksday);

	public String getTjksrq(String strDqrq, String tjksday);

	public String getRemoteIpdz(HttpServletRequest request);
	
	public String getGnmc(String xtlb, String gnid);
}
