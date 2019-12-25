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
	 * ����Web����KEY ����Ϊ_RANDDOMKEY ��_RANDOMVALUE��ͬʱ����ִ�н���ʱ��
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
	 * ���Web����KEY�ĺϷ���
	 * 
	 * @param request
	 * @return 1-��ȷ 0-ʧ��
	 */
	public int checkWebkey(HttpServletRequest request, String keyvalue);

	public void genWebKey(HttpServletRequest request, String cdbh, String gnid);

	public void genWebKey(HttpServletRequest request);

	public void genWebKey(HttpServletRequest request, String keyname);

	/**
	 * ����Web����KEY ����Ϊ_RANDDOMKEY ��_RANDOMVALUE
	 * 
	 * @param request
	 */
	public void genWebKey(HttpServletRequest request, String cdbh, String gnid,
			String keyname);

	/**
	 * ����WebKEY��ȡFunction������Ҫ��checkWebkeyV1ִ��
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
	 * ��ȡexception��oracle���������Ϣ
	 * 
	 * @param e�쳣����
	 * @return�������ֵ
	 */
	public String getOraErrCode(String errMsg);

	public void saveRbspLog(FrmRbspLog frmRbspLog);

	public String procJspCheckCode(String xtlb, String cdbh, String xh,
			String zlj) throws Exception;

	public String transDepGlxzqhToOptionHtml(String glbm, String dmlb,
			String defauls, boolean havaNull, String showType);


	// ��ȡȫ�����������
	public String getQgsfOptionHtml(String defauls);

	// ��ȡ������������List
	public List getBdfzjgList();

	// ��ȡ������������List
	public List getBdfzjgList(String userFzjg);

	public List queryForList(String strSql, Class bClass);

	/**
	 * ��ȡ��������������Ϣ
	 * 
	 * @param glbm ������
	 * @param yhssyw �û�����ҵ�� 1-�Ƽ� 2-���� 3-�ݹ� 4-Υ�� 5-�¹� 6-�綾Ʒ 7-����
	 * @return
	 */
	public List getBamjList(String glbm, String yhssyw);

	/**
	 * ����ǰ�����������а참����ʾ����
	 * 
	 * @param glbm ������
	 * @param defaults ȱʡֵ
	 * @param haveNull �Ƿ�ɿ�
	 * @param showtype ��ʾ��ʽ 1:valueΪ���� 2:valueΪƴ��
	 * @return
	 */
	public String transBamjToOptionHtml(String glbm, String defauls,
			boolean havaNull, String showType, String yhssyw);

	public SysUser getYhxm(String yhdh);

	// �Ƿ�������
	// 20100316 zhoujn
	public void transBoolToOptionHtml(JspWriter out, String strDefault,
			boolean bXsdmz);

	public SysUser getSysUser(String yhdh);

	public SysUser getSysUser(String glbm, String xm);


	public BasPolice getRyjbxx(String sfzmhm, String sfmj) throws Exception;

	// ȡ�����
	public int getRand();

	/**
	 * ����ʡ��ͷ��ȡ��ؽ���Ӧ�÷�����IP��ַ
	 */
	public DbResult Getjhkwepip(String sft);

	// �����Ϣ��ʾ
	public String outputMessage(HttpSession session);


	public String getTjny(String strDqrq, String tjksday);

	public String getTjksrq(String strDqrq, String tjksday);

	public String getRemoteIpdz(HttpServletRequest request);
	
	public String getGnmc(String xtlb, String gnid);
}
