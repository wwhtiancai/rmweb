package com.tmri.framework.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;


import com.tmri.framework.bean.FrmLogRow;
import com.tmri.framework.bean.FrmLogTask;
import com.tmri.framework.bean.FrmUpQueue;
import com.tmri.framework.bean.Log;
import com.tmri.framework.bean.Role;
import com.tmri.framework.dao.CodeDao;
import com.tmri.framework.dao.jdbc.ToolsDaoJdbc;
import com.tmri.share.frm.bean.Code;
import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.bean.SysUser;
import com.tmri.share.frm.bean.UserSession;
import com.tmri.share.frm.dao.GSysparaCodeDao;
@Service

public class ToolsManagerImpl implements ToolsManager{
	@Autowired
	ToolsDaoJdbc toolsDaoJdbc;
	@Autowired
	CodeDao codeDao;
	@Autowired
	GSysparaCodeDao gSysparaCodeDao;
	
//	public void setgSysparaCodeDao(GSysparaCodeDao gSysparaCodeDao) {
//		this.gSysparaCodeDao = gSysparaCodeDao;
//	}
//
//	public void setToolsDaoJdbc(ToolsDaoJdbc toolsDaoJdbc){
//		this.toolsDaoJdbc=toolsDaoJdbc;
//	}
//
//	public void setCodeDao(CodeDao codeDao){
//		this.codeDao=codeDao;
//	}

	
	/**
	 * ���ݴ������ȡ����ֵ��Ϣ
	 * 
	 * @param dmlb
	 * @return
	 * @throws Exception
	 */
	public List getDmlbCodesList(String dmlb) throws Exception{
		return this.toolsDaoJdbc.getDmlbCodesList(dmlb);
	};

	/**
	 * ���ݴ������ȡ����ֵ��Ϣ
	 * 
	 * @param dmlb
	 * @return
	 * @throws Exception
	 */
	public List getDmlbCodesList(String dmlb,String dmsm2) throws Exception{
		return this.toolsDaoJdbc.getDmlbCodesList(dmlb,dmsm2);
	};

	/**
	 * ���ݴ������ʹ���ֵ��ȡcodebean
	 * 
	 * @param dmlb
	 * @param dmz
	 * @return
	 * @throws Exception
	 */
	public Code getCode(String dmlb,String dmz){
		List list=(List)this.toolsDaoJdbc.getDmlbCodesList(dmlb);
		Code code=null;
		for(int i=0;i<list.size();i++){
			Code tmpcode=(Code)list.get(i);
			if(tmpcode.getDmz().equals(dmz)){
				code=tmpcode;
				break;
			}
		}
		return code;
	}

	// ����ʡ�ݷ�֤����ͷ����ȡ���з�֤���ؼ�����
	public String getOptionStr_fzjg(String fzjg,String bdfzjg){
		List list=toolsDaoJdbc.getCityFzjg(fzjg);
		String tmpstr="";
		for(int i=0;i<list.size();i++){
			Code code=(Code)list.get(i);
			if(code.getDmz().equals(bdfzjg)) tmpstr+="\n<option value='"+code.getDmz()+"' selected>"+code.getDmsm1()+"</option>";
			else tmpstr+="\n<option value='"+code.getDmz()+"'>"+code.getDmsm1()+"</option>";
		}
		return tmpstr;
	}

	/**
	 * ���ݴ������ʹ���ֵ��ȡdmsm1
	 * 
	 * @param dmlb
	 * @param dmz
	 * @param _default
	 * @return
	 */
	public String getCodeDmsm1(String dmlb,String dmz,String _default){
		String strResult=_default;
		Code code;
		try{
			code=this.getCode(dmlb,dmz);
			if(code!=null){
				strResult=code.getDmsm1();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return strResult;
	}

	/**
	 * ��ȡ������Ϣ
	 * 
	 * @param glbm
	 * @return
	 */
	public Department getDepartment(String glbm){
		Department code=null;
		try{
			code=this.toolsDaoJdbc.getDepartment(glbm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return code;
	}

	/**
	 * ��ȡ��������
	 * 
	 * @param glbm
	 * @return
	 */
	public String getBmmc(String glbm){
		String _result=glbm;
		try{
			Department code=this.getDepartment(glbm);
			if(code!=null){
				_result=code.getBmmc();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return _result;
	}

	/**
	 * ��ȡ�û���Ϣ
	 * 
	 * @param yhdh
	 * @return
	 * @throws DataAccessException
	 */
	public SysUser getSysuser(String yhdh){
		SysUser code=null;
		try{
			code=this.toolsDaoJdbc.getSysuser(yhdh);
		}catch(Exception e){
			e.printStackTrace();
		}
		return code;
	}

	/**
	 * ��ȡ�û�����
	 * 
	 * @param yhdh
	 * @return
	 */
	public String getYhmc(String yhdh){
		String _result=yhdh;
		try{
			SysUser code=this.getSysuser(yhdh);
			if(code!=null){
				_result=code.getXm();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return _result;
	}

	
	/**
	 * ���ݴ����½�ļ��𼶱���ʾ����������
	 * 
	 * @param strDmlb String
	 * @param haveDefault String ��ȱʡ�Ŀ�ֵ
	 * @return String
	 */
	public String outputJb(String strJb,boolean bShowMax){
		String _returnStrings="";
		try{
			if(strJb.equals("1")||strJb.equals("2")){
				if(bShowMax){
					_returnStrings+="<option value='2' selected>�ܶ�</option>";
					_returnStrings+="<option value='3'>֧��</option>";
					_returnStrings+="<option value='4'>���</option>";
					_returnStrings+="<option value='5'>�ж�</option>";
					_returnStrings+="<option value='6'>ִ��վ</option>";
				}else{
					_returnStrings+="<option value='2'>�ܶ�</option>";
					_returnStrings+="<option value='3'>֧��</option>";
					_returnStrings+="<option value='4'>���</option>";
					_returnStrings+="<option value='5' >�ж�</option>";
					_returnStrings+="<option value='6' selected>ִ��վ</option>";
				}
			}
			if(strJb.equals("3")){
				if(bShowMax){
					_returnStrings+="<option value='3' selected>֧��</option>";
					_returnStrings+="<option value='4'>���</option>";
					_returnStrings+="<option value='5'>�ж�</option>";
					_returnStrings+="<option value='6'>ִ��վ</option>";
				}else{
					_returnStrings+="<option value='3'>֧��</option>";
					_returnStrings+="<option value='4'>���</option>";
					_returnStrings+="<option value='5'>�ж�</option>";
					_returnStrings+="<option value='6' selected>ִ��վ</option>";
				}
			}
			if(strJb.equals("4")){
				if(bShowMax){
					_returnStrings+="<option value='4' selected>���</option>";
					_returnStrings+="<option value='5'>�ж�</option>";
					_returnStrings+="<option value='6'>ִ��վ</option>";
				}else{
					_returnStrings+="<option value='4'>���</option>";
					_returnStrings+="<option value='5'>�ж�</option>";
					_returnStrings+="<option value='6' selected>ִ��վ</option>";
				}
			}
			if(strJb.equals("5")){
				if(bShowMax){
					_returnStrings+="<option value='5' selected>�ж�</option>";
					_returnStrings+="<option value='6'>ִ��վ</option>";
				}else{
					_returnStrings+="<option value='5'>�ж�</option>";
					_returnStrings+="<option value='6' selected>ִ��վ</option>";
				}
			}
			if(strJb.equals("6")){
				_returnStrings+="<option value='6' selected>�ж�</option>";
			}			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return _returnStrings;
	}

	/**
	 * ���ݴ����½�ļ��𼶱���ʾ����������
	 * 
	 * @param strDmlb String
	 * @param haveDefault String ��ȱʡ�Ŀ�ֵ
	 * @return String
	 */
	public String outputShowJb(String strJb,boolean bShowMax){
		String _returnStrings="";
		try{
			if(strJb.equals("1")||strJb.equals("2")){
				if(bShowMax){
					_returnStrings+="<option value='' selected>   </option>";
					_returnStrings+="<option value='2'>�ܶ�</option>";
					_returnStrings+="<option value='3'>֧��</option>";
					_returnStrings+="<option value='4'>���</option>";
					_returnStrings+="<option value='5'>�ж�</option>";
					_returnStrings+="<option value='6'>ִ��վ</option>";
				}else{
					_returnStrings+="<option value=''>   </option>";
					_returnStrings+="<option value='2' >�ܶ�</option>";
					_returnStrings+="<option value='3'>֧��</option>";
					_returnStrings+="<option value='4'>���</option>";
					_returnStrings+="<option value='5'>�ж�</option>";
					_returnStrings+="<option value='6' selected>ִ��վ</option>";
				}
			}
			if(strJb.equals("3")){
				if(bShowMax){
					_returnStrings+="<option value='' selected>   </option>";
					_returnStrings+="<option value='3'>֧��</option>";
					_returnStrings+="<option value='4'>���</option>";
					_returnStrings+="<option value='5'>�ж�</option>";
					_returnStrings+="<option value='6'>ִ��վ</option>";
				}else{
					_returnStrings+="<option value=''>   </option>";
					_returnStrings+="<option value='3'>֧��</option>";
					_returnStrings+="<option value='4'>���</option>";
					_returnStrings+="<option value='5'>�ж�</option>";
					_returnStrings+="<option value='6' selected>ִ��վ</option>";
				}
			}
			if(strJb.equals("4")){
				if(bShowMax){
					_returnStrings+="<option value='' selected>   </option>";
					_returnStrings+="<option value='4'>���</option>";
					_returnStrings+="<option value='5'>�ж�</option>";
					_returnStrings+="<option value='6'>ִ��վ</option>";
				}else{
					_returnStrings+="<option value=''>   </option>";
					_returnStrings+="<option value='4'>���</option>";
					_returnStrings+="<option value='5'>�ж�</option>";
					_returnStrings+="<option value='6' selected>ִ��վ</option>";
				}
			}
			if(strJb.equals("5")){
				if(bShowMax){
					_returnStrings+="<option value='' selected>   </option>";
					_returnStrings+="<option value='5'>�ж�</option>";
					_returnStrings+="<option value='6'>ִ��վ</option>";
				}else{
					_returnStrings+="<option value=''>   </option>";
					_returnStrings+="<option value='5'>�ж�</option>";
					_returnStrings+="<option value='6' selected>ִ��վ</option>";
				}
			}
			if(strJb.equals("6")){
				if(bShowMax){
					_returnStrings+="<option value='' selected>   </option>";
					_returnStrings+="<option value='6'>ִ��վ</option>";
				}else{
					_returnStrings+="<option value=''>   </option>";
					_returnStrings+="<option value='6' selected>ִ��վ</option>";
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return _returnStrings;
	}

	/**
	 * ��ȡ�����Ų�ѯ����
	 * 
	 * @param userSession
	 * @param Colname
	 * @return
	 */
	public String getGlbmQueryCondition(UserSession userSession,String Colname){
		String sql="";
		String glbm="";
		try{
			glbm=userSession.getDepartment().getGlbm();
			// �ܶ��û�
			if(userSession.getDepartment().getBmjb().equals("1")){
				sql=" "+Colname+" like '"+glbm.substring(0,2)+"%' ";
			}

			// ֧���û�
			if(userSession.getDepartment().getBmjb().equals("2")){
				sql=" "+Colname+" like '"+glbm.substring(0,4)+"%' ";
			}

			// ����û�
			if(userSession.getDepartment().getBmjb().equals("3")){
				sql=" "+Colname+" like '"+glbm.substring(0,8)+"%' ";
			}

			// �ж��û�
			if(userSession.getDepartment().getBmjb().equals("4")){
				sql=" "+Colname+" = '"+glbm+"' ";
			}

			if(sql.equals("")){
				sql=" 1=1 ";
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return sql;
	}

	/**
	 * ���ݴ�����𷵻�HTML��ʽ���ַ���
	 * 
	 * @param dmlb �������
	 * @param defauls Ĭ��ֵ
	 * @param havaNull �Ƿ��п�ֵ
	 * @param showType 1,ֻ��ʾ����;2,ֻ��ʾ����;3,��ʾ��������� ��ʾ��ʽ
	 * @return String xuxd 2007-02-01
	 */
	public String transDmlbToOptionHtml(String dmlb,String defauls,boolean havaNull,String showType) throws Exception{
		String _returnStrings="";
		List list=this.toolsDaoJdbc.getDmlbCodesList(dmlb);
		if(showType==null||(!showType.equals("1")&&!showType.equals("2")&&!showType.equals("3"))){
			showType="2";
		}
		if(havaNull){
			_returnStrings="<option value=''></option>";
		}
		if(list!=null){
			Iterator iterator=list.iterator();
			while(iterator.hasNext()){
				Code frmCode=(Code)iterator.next();
				// ֻ��ʾ����
				if(showType.equals("1")){
					if(frmCode.getDmz().equals(defauls)){
						_returnStrings=_returnStrings+"<option value="+frmCode.getDmz()+" selected>"+frmCode.getDmz()+"</option>";
					}else{
						_returnStrings=_returnStrings+"<option value="+frmCode.getDmz()+">"+frmCode.getDmz()+"</option>";
					}
				}
				// ֻ��ʾ����
				if(showType.equals("2")){
					if(frmCode.getDmz().equals(defauls)){
						_returnStrings=_returnStrings+"<option value="+frmCode.getDmz()+" selected>"+frmCode.getDmsm1()+"</option>";
					}else{
						_returnStrings=_returnStrings+"<option value="+frmCode.getDmz()+">"+frmCode.getDmsm1()+"</option>";
					}
				}
				// ��ʾ���������
				if(showType.equals("3")){
					if(frmCode.getDmz().equals(defauls)){
						_returnStrings=_returnStrings+"<option value="+frmCode.getDmz()+" selected>"+frmCode.getDmz()+":"+frmCode.getDmsm1()+"</option>";
					}else{
						_returnStrings=_returnStrings+"<option value="+frmCode.getDmz()+">"+frmCode.getDmz()+":"+frmCode.getDmsm1()+"</option>";
					}
				}
			}
		}
		return _returnStrings;
	}

	/**
	 * ���ݴ�����𷵻�HTML��ʽ���ַ���
	 * 
	 * @param dmlb �������
	 * @return String xuxd 2007-02-01
	 */
	public String transDmlbToCheckBoxHtml(String dmlb,String ctrlName) throws Exception{
		String _returnStrings="";
		List list=this.toolsDaoJdbc.getDmlbCodesList(dmlb);
		if(list!=null){
			Iterator iterator=list.iterator();
			while(iterator.hasNext()){
				Code frmCode=(Code)iterator.next();
				_returnStrings=_returnStrings+"<input type='checkbox' name='"+ctrlName+"' value='"+frmCode.getDmz()+"'>"+frmCode.getDmz()+":"+frmCode.getDmsm1()+"&nbsp;";
			}
		}
		return _returnStrings;
	}

	/**
	 * ���ش�������List
	 * 
	 * @param dmlb
	 * @return
	 * @throws DataAccessException
	 */
	public List getDmlbList(String dmlb) throws Exception{
		return this.toolsDaoJdbc.getDmlbCodesList(dmlb);

	}

	/**
	 * ��ȡ���Ŵ���XML
	 * 
	 * @param request
	 * @param response
	 */
	public List getDepartmentList(UserSession userSession,String jb){
		List QueryList=this.toolsDaoJdbc.getDepartmentList(userSession,jb);
		this.transCodeDepartmentList(QueryList);
		return QueryList;
	}

	/**
	 * ��ȡָ����������ָ����������в��Ŵ���List
	 * 
	 * @param dmlb
	 * @return list
	 * @throws DataAccessException 2007-02-01 xuxd
	 */
	public List getDepartmentList(UserSession userSession,Department department,String jb){
		List QueryList=this.toolsDaoJdbc.getDepartmentList(userSession,department,jb);
		this.transCodeDepartmentList(QueryList);
		return QueryList;
	}

	public List getDepartmentList1(UserSession userSession){
		List QueryList=this.toolsDaoJdbc.getDepartmentList1(userSession);
		this.transCodeDepartmentList(QueryList);
		return QueryList;
	}

	public List getDepartmentList2(UserSession userSession){
		List QueryList=this.toolsDaoJdbc.getDepartmentList2(userSession);
		this.transCodeDepartmentList(QueryList);
		return QueryList;
	}

	public List getUpDepartmentList(UserSession userSession) throws DataAccessException{
		List QueryList=this.toolsDaoJdbc.getUpDepartmentList(userSession);
		this.transCodeDepartmentList(QueryList);
		return QueryList;
	}

	public List getDepartmentList(Department department){
		List QueryList=this.toolsDaoJdbc.getDepartmentList(department);
		this.transCodeDepartmentList(QueryList);
		return QueryList;
	}


	/**
	 * ��ȡ���еĽ�ɫlist
	 */
	public List getRoleList(String jb){
		return this.toolsDaoJdbc.getRoleList(jb);
	}



	
	/**
	 * ����sysuser��ȡ�û���Ȩ�޲˵�
	 * 
	 * @param sysuser
	 */
	public void setSysuserRoles(SysUser sysuser){
		List list=null;
		if(sysuser!=null){
			// ��ȡ�û��Ľ�ɫ��¼
			// if (sysuser.getQxms().equals("1")) {
			// list = this.toolsDaoJdbc.getUserRole(sysuser.getYhdh());
			// if (list != null) {
			// String tmp = "";
			// Iterator iterator = list.iterator();
			// while (iterator.hasNext()) {
			// UserRole userRole = (UserRole) iterator.next();
			// tmp = tmp + userRole.getJsdh() + "A";
			// }
			// sysuser.setRoles(tmp);
			// }
			// }
			// ֱ����ȡ�û��Ĳ˵���¼
			// if (sysuser.getQxms().equals("2")) {
			// list = this.toolsDaoJdbc.getUserMenu(sysuser.getYhdh());
			// if (list != null) {
			// String tmp = "";
			// Iterator iterator = list.iterator();
			// while (iterator.hasNext()) {
			// UserMenu userMenu = (UserMenu) iterator.next();
			// tmp = tmp + userMenu.getCxdh() + "A";
			// }
			// sysuser.setCxdh(tmp);
			// }
			// }
		}
	}

	public String getCxsxName(String cxsxs){
		String _s="";
		for(int i=0;i<cxsxs.length();i++){
			_s=_s+" "+cxsxs.substring(i,i+1)+":"+getCodeDmsm1("000001",cxsxs.substring(i,i+1),cxsxs.substring(i,i+1));
		}
		return _s;
	}

	/**
	 * ��ȡ�����µ�������ԱLIST
	 */
	public List getSysuserListByGlbm(String glbm){
		return this.toolsDaoJdbc.getSysuserListByGlbm(glbm);
	}

	/**
	 * ��ȡ�û��б�
	 */
	public List getSysusers(UserSession userSession){
		return this.toolsDaoJdbc.getSysusers(userSession);
	}

	public String getSysDate() throws SQLException{
		return this.toolsDaoJdbc.getSysDate();
	}

	public String getZwSysDate() throws SQLException{
		return this.toolsDaoJdbc.getZwSysDate();
	}

	public String getSysDateTime() throws SQLException{
		return this.toolsDaoJdbc.getDBDateTime();
	}

	public String getSysDateTimeToMinute() throws SQLException{
		return this.toolsDaoJdbc.getDBDateTimeToMinute();
	}

	public String getSysDate(String val) throws SQLException{
		return this.toolsDaoJdbc.getSysDate(val);
	}

	public String getSysDateTime(String val) throws SQLException{
		return this.toolsDaoJdbc.getDBDateTime(val);
	}

	public String getDatetime(String datetime,String val) throws SQLException{
		return this.toolsDaoJdbc.getDateTime(datetime,val);
	}

	public String getDataBaseSysDate(String v) throws SQLException{
		return this.toolsDaoJdbc.getDataBaseSysDate(v);
	}

	/**
	 * �ж��ַ����Ƿ�Ϊ����NULL�����ǵĻ����滻Ϊ""�Ա���javascript����ʾ
	 * 
	 * @param strJava String
	 * @return String
	 */
	public String CheckNullAndReplace(String strJava){
		String r="";
		if(strJava==null||strJava.equals("null")){
			r="";
		}else{
			r=strJava;
		}
		return r;
	}

	/**
	 * ����Υ����Ϊ��������,��ȡ���д������list
	 */
	public List getWfxwDmflList(String dmzl){
		return this.toolsDaoJdbc.getWfxwDmflList(dmzl);
	}

	/**
	 * ���ݲ������ͱ������ȡ FRM_CODE ���� dmlb=99 �ı��ԭ�����
	 * 
	 * @param dmlb
	 * @return list
	 * @throws DataAccessException 2007-02-01 xuxd
	 */
	public List getSqyyDmList(String czlx){
		return this.toolsDaoJdbc.getSqyyDmList(czlx);
	}

	// �ж�vio_codewfdm ���У��λ
	public int Check_Wfdm_Jyw(String wfxw) throws DataAccessException{
		return this.toolsDaoJdbc.Check_Wfdm_Jyw(wfxw);
	}

	// �жϴ����������У��λ
	public int Check_Jdsbh_Jyw(String jdsbh) throws DataAccessException{

		return this.toolsDaoJdbc.Check_Jdsbh_Jyw(jdsbh);
	}

	// �ж�violation���У��λ
	public int Check_Violation_Jyw(String wfbh) throws DataAccessException{

		return this.toolsDaoJdbc.Check_Violation_Jyw(wfbh);
	}

	// �ж�force���У��λ
	public int Check_Force_Jyw(String pzbh) throws DataAccessException{
		return this.toolsDaoJdbc.Check_Force_Jyw(pzbh);
	}

	// �ж�surveil���У��λ
	public int Check_Surveil_Jyw(String xh) throws DataAccessException{
		return this.toolsDaoJdbc.Check_Surveil_Jyw(xh);
	}

	/**
	 * TransDriverZt �����ʻ��״̬ author xxd
	 * 
	 * @param String strZt
	 * @return String
	 * @throws Exception
	 */
	public String TransDriverZt(String strZt) throws Exception{
		String s_Para="";
		try{
			if(strZt!=null){
				for(int i=0;i<strZt.length();i++){
					s_Para=s_Para+strZt.substring(i,i+1)+":"+getCodeDmsm1("25",strZt.substring(i,i+1),strZt.substring(i,i+1));
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return s_Para;
	}

	/**
	 * TransVehicleZt ���������״̬ author xxd
	 * 
	 * @param String strZt
	 * @return String
	 * @throws Exception
	 */
	public String TransVehicleZt(String strZt) throws Exception{
		String s_Para="";
		try{
			if(strZt!=null){
				for(int i=0;i<strZt.length();i++){
					s_Para=s_Para+strZt.substring(i,i+1)+":"+getCodeDmsm1("32",strZt.substring(i,i+1),strZt.substring(i,i+1));
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return s_Para;
	}

	/**
	 * TransVehicleCsys ���������������ɫ author wjf
	 * 
	 * @param String strCsys
	 * @return String
	 * @throws Exception
	 */
	public String TransVehicleCsys(String strCsys) throws Exception{
		String s_Para="";
		try{
			if(strCsys!=null){
				for(int i=0;i<strCsys.length();i++){
					// s_Para = s_Para + strCsys.substring(i, i + 1) + ":"
					// + getCodeDmsm1("100", strCsys.substring(i, i + 1),
					// strCsys.substring(i, i + 1));
					s_Para=s_Para+getCodeDmsm1("100",strCsys.substring(i,i+1),strCsys.substring(i,i+1));
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return s_Para;
	}

	/**
	 * ��ȡǿ�ƴ�ʩ��������
	 */
	public String getQzcslxMc(String vQzcslx){
		String strMc="";
		try{
			if(vQzcslx!=null){
				for(int i=0;i<vQzcslx.length();i++){
					strMc=strMc+" "+vQzcslx.substring(i,i+1)+":"+getCodeDmsm1("11",vQzcslx.substring(i,i+1),vQzcslx.substring(i,i+1));
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return strMc;
	}

	/**
	 * ��ȡ��������
	 */
	public String getCfzlMc(String vCfzl){
		String strMc="";
		try{
			for(int i=0;i<vCfzl.length();i++){
				strMc=strMc+" "+vCfzl.substring(i,i+1)+":"+getCodeDmsm1("2",vCfzl.substring(i,i+1),vCfzl.substring(i,i+1));
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return strMc;
	}

	/**
	 * ��ȡ��������--�޴����������
	 */
	public String getCfzlMcNoCfzldm(String vCfzl){
		String strMc="";
		try{
			for(int i=0;i<vCfzl.length();i++){
				strMc=strMc+getCodeDmsm1("2",vCfzl.substring(i,i+1),vCfzl.substring(i,i+1))+" ";
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return strMc;
	}

	/**
	 * ��ȡ������Ŀ
	 */
	public String getCxxmMc(String vCxxm){
		String strMc="";
		try{
			for(int i=0;i<vCxxm.length();i++){
				strMc=strMc+" "+vCxxm.substring(i,i+1)+":"+getCodeDmsm1("22",vCxxm.substring(i,i+1),vCxxm.substring(i,i+1));
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return strMc;
	}

	/**
	 * ��ȡ������Ŀ
	 */
	public String getKlxmMc(String vKlxm){
		String strMc="";
		try{
			for(int i=0;i<vKlxm.length();i++){
				strMc=strMc+" "+vKlxm.substring(i,i+1)+":"+getCodeDmsm1("16",vKlxm.substring(i,i+1),vKlxm.substring(i,i+1));
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return strMc;
	}

	/**
	 * ��ȡ�ս���Ŀ
	 */
	public String getSjxmMc(String vSjxm){
		String strMc="";
		try{
			for(int i=0;i<vSjxm.length();i++){
				strMc=strMc+" "+vSjxm.substring(i,i+1)+":"+getCodeDmsm1("12",vSjxm.substring(i,i+1),vSjxm.substring(i,i+1));
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return strMc;
	}

	/**
	 * ��ʽ��dbȡ��������ʱ��Ϊyyyy-MM-dd HH:mm by:kris.wang
	 * 
	 * @param strDateTime String
	 * @return String
	 */
	public String formatDateTime(String strDateTime){
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if(strDateTime!=null&&!strDateTime.equals("")){
			try{
				strDateTime=format.format(format.parse(strDateTime));
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}else{
			strDateTime="";
		}
		return strDateTime;
	}

	public String formatDateTimeToSec(String strDateTime){
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(strDateTime!=null&&!strDateTime.equals("")){
			try{
				strDateTime=format.format(format.parse(strDateTime));
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}else{
			strDateTime="";
		}
		return strDateTime;
	}

	/**
	 * ��ʽ��dbȡ��������ʱ��Ϊyyyy-MM-dd
	 * 
	 * @param strDateTime String
	 * @return String
	 */
	public String formatDate(String strDateTime){
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		if(strDateTime!=null&&!strDateTime.equals("")){
			try{
				strDateTime=format.format(format.parse(strDateTime));
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}else{
			strDateTime="";
		}
		return strDateTime;
	}

	/**
	 * ��ҳ�渳ֵ,�������ǰҳ�渳ֵ���뽫���ô���ŵ�ҳ�����
	 * 
	 * @param _obj Object beanʵ��
	 * @param _pw PrintWriter ���ʵ��
	 * @param _formName String ������
	 * @param _formFieldAppendName String ���ֶ��������ַ���
	 * @param isParent boolean �Ƿ��и���
	 */
	public void SetPageValue(Object _obj,PrintWriter _pw,String _formName,String _formFieldAppendName,boolean isParent,boolean isEmpty){
		try{
			_pw.print("<script language=javascript>");
			Class classObject=_obj.getClass();
			Field m_field[]=classObject.getDeclaredFields();
			Class parameters[]=new Class[0];
			Object objvarargs[]=new Object[0];
			for(int i=0;i<m_field.length;i++){
				_pw.println("try{");
				String colName=m_field[i].getName();
				if(colName.length()>0){
					colName=colName.substring(0,1).toUpperCase()+colName.substring(1);
				}
				String methName="get"+colName;
				Method meth=classObject.getMethod(methName,parameters);
				String m_curValue="";
				if(meth.invoke(_obj,objvarargs)!=null){
					m_curValue=meth.invoke(_obj,objvarargs).toString();
					m_curValue=ReplaceString(m_curValue,"\'","\\\'");
					m_curValue=ReplaceString(m_curValue,"\"","\\\"");
					m_curValue=ReplaceString(m_curValue,"\n","\\n");
				}
				m_curValue=m_curValue==null?"":m_curValue;
				if(isParent){
					if(isEmpty){
						_pw.println("parent.document."+_formName+"."+m_field[i].getName().toString().toLowerCase()+_formFieldAppendName+".value='';");
					}else{
						_pw.println("parent.document."+_formName+"."+m_field[i].getName().toString().toLowerCase()+_formFieldAppendName+".value='"+m_curValue+"';");
					}

				}else{
					if(isEmpty){
						_pw.println(_formName+"."+m_field[i].getName().toString().toLowerCase()+_formFieldAppendName+".value='';");
					}else{
						_pw.println(_formName+"."+m_field[i].getName().toString().toLowerCase()+_formFieldAppendName+".value='"+m_curValue+"';");
					}
				}
				_pw.println("}catch(ex){}");
			}
			_pw.println("</script>");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public String getSetPageValue(Object _obj,String _formName,boolean isEmpty,String _formFieldAppendName){
		String result="";
		try{
			Class classObject=_obj.getClass();
			Field m_field[]=classObject.getDeclaredFields();
			Class parameters[]=new Class[0];
			Object objvarargs[]=new Object[0];
			for(int i=0;i<m_field.length;i++){
				result+="try{";
				String colName=m_field[i].getName();
				if(colName.length()>0){
					colName=colName.substring(0,1).toUpperCase()+colName.substring(1);
				}
				String methName="get"+colName;
				Method meth=classObject.getMethod(methName,parameters);
				String m_curValue="";
				if(meth.invoke(_obj,objvarargs)!=null){
					m_curValue=meth.invoke(_obj,objvarargs).toString();
					m_curValue=ReplaceString(m_curValue,"\'","\\\'");
					m_curValue=ReplaceString(m_curValue,"\"","\\\"");
					m_curValue=ReplaceString(m_curValue,"\n","\\n");
				}
				if(isEmpty){
					result+=_formName+"."+m_field[i].getName().toString().toLowerCase()+_formFieldAppendName+".value='';";
				}else{
					result+=_formName+"."+m_field[i].getName().toString().toLowerCase()+_formFieldAppendName+".value='"+m_curValue+"';";
				}
				result+="}catch(ex){}\n";
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return result;
	}

	public void SetPageValue(Object _obj,PrintWriter _pw,String _formName,boolean isParent,boolean isEmpty){
		SetPageValue(_obj,_pw,_formName,"",isParent,isEmpty);
	}

	public String ReplaceString(String original,String find,String replace){

		if(original==null){
			original="";
		}
		String returnStr="";
		if(original.indexOf(find)<0){
			returnStr=original;
		}
		try{
			while(original.indexOf(find)>=0){
				int indexbegin=original.indexOf(find);
				String leftstring=original.substring(0,indexbegin);
				original=original.substring(indexbegin+find.length());
				if(original.indexOf(find)<=0){
					returnStr+=leftstring+replace+original;
				}else{
					returnStr+=leftstring+replace;
				}
			}
			return (returnStr);
		}catch(Exception e){
			System.out.println(e.getMessage());
			return original;
		}
	}

	/**
	 * ����frm_role
	 * 
	 * @param code
	 */
	public void transCodeRoleBean(Role role){
		try{
			if(role!=null){
				String jssxString;
				jssxString=gSysparaCodeDao.getCode("00","0003",role.getJssx()).getDmsm1();
				role.setJssx(jssxString);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * ����frm_rolelist
	 * 
	 * @param list
	 */
	public void transCodeRoleList(List roleList){
		if(roleList!=null){
			for(int i=0;i<roleList.size();i++){
				Role r=(Role)roleList.get(i);
				transCodeRoleBean(r);
			}
		}
	}

	/**
	 * ����log
	 * 
	 * @param code
	 */
	public void transCodeLogBean(Log log){
		if(log!=null){
			// log.setBmmc(this.getBmmc(log.getGlbm()));
			// log.setCzsj(this.formatDateTimeToSec(log.getCzsj()));
			// log.setYhmc(this.getYhmc(log.getYhdh()));
			log.setCzlx(this.getCodeDmsm1("17",log.getCzlx(),log.getCzlx()));
		}
	}

	/**
	 * ����log
	 * 
	 * @param code
	 */
	public void transCodeLogList(List logList){
		if(logList!=null){
			for(int i=0;i<logList.size();i++){
				Log r=(Log)logList.get(i);
				transCodeLogBean(r);
			}
		}
	}
	
	/**
	 * ����sysuser
	 * 
	 * @param code
	 */
	public void transCodeSysuserBean(SysUser sysuser){
		if(sysuser!=null){
			// sysuser.setBmmc(this.getBmmc(sysuser.getGlbm()));
			// sysuser.setZhyxq(this.formatDate(sysuser.getZhyxq()));
			// sysuser.setMmyxq(this.formatDate(sysuser.getMmyxq()));
		}
	}

	/**
	 * ����sysuserlist
	 * 
	 * @param code
	 */
	public void transCodeSysuserList(List sysuserList){
		if(sysuserList!=null){
			for(int i=0;i<sysuserList.size();i++){
				SysUser r=(SysUser)sysuserList.get(i);
				transCodeSysuserBean(r);
			}
		}
	}

	/**
	 * ����department
	 * 
	 * @param code
	 */
	public void transCodeDepartmentBean(Department department){
		if(department!=null){
			 department.setSjbmmc(this.getBmmc(department.getSjbm()));
			 department.setJbmc(this.getCodeDmsm1("65", department.getBmjb(),
			 department.getBmjb()));
		}
	}

	/**
	 * ����departmentList
	 * 
	 * @param list
	 */
	public void transCodeDepartmentList(List departmentList){
		if(departmentList!=null){
			for(int i=0;i<departmentList.size();i++){
				Department r=(Department)departmentList.get(i);
				transCodeDepartmentBean(r);
			}
		}
	}

	public String getScriptfxwbutton(int wfxwcount){
		String str="";
		for(int i=1;i<=wfxwcount;i++){
			str+="document.all[\"swfxw"+i+"\"].style.display=\"inline\";";
		}
		return str;
	}

	// ���ַ����ָ�Ϊ�ַ�����
	public String[] splitString(String str,String split){
		String[] result;
		String tmpStr;
		tmpStr=str.trim();
		if(tmpStr.equals("")){
			result=null;
		}else{
			result=tmpStr.split(split);
		}
		return result;
	}

	public String getMonthSysdate(String date,String v){
		return this.toolsDaoJdbc.getMonthSysdate(date,v);
	}

	public String getDaySysdate(String date,String v){
		return this.toolsDaoJdbc.getDaySysdate(date,v);
	}

	public int compareDay(String date){
		return this.toolsDaoJdbc.compareDay(date);
	}

	/**
	 * �����������ݡ��·�
	 * 
	 * @param request
	 * @param num
	 * @throws SQLException
	 */
	public void setTjNy(HttpServletRequest request,int num) throws Exception{
		String sysdate=getSysDate("");
		if(Integer.parseInt(sysdate.substring(sysdate.length()-2))>num){
			request.setAttribute("nf",sysdate.substring(0,4));
			request.setAttribute("yf",sysdate.substring(5,7));
		}else{
			if(sysdate.substring(6,8).equals("01")){
				request.setAttribute("nf",(Integer.parseInt(sysdate.substring(0,4))-1)+"");
				request.setAttribute("yf","12");
			}else{
				request.setAttribute("nf",sysdate.substring(0,4));
				String yf=(Integer.parseInt(sysdate.substring(5,7))-1)+"";
				if(yf.length()==1){
					yf="0"+yf;
				}
				request.setAttribute("yf",yf);
			}
		}
	}

	/**
	 * �ж������Ƿ����ͨ��
	 * 
	 * @param httpurl
	 * @return
	 */
	public boolean isNetWorkLinked(String httpurl){
		String htmlCode="";
		try{
			InputStream in;
			URL url=new java.net.URL(httpurl);
			HttpURLConnection connection=(HttpURLConnection)url.openConnection();
			connection=(HttpURLConnection)url.openConnection();
			System.setProperty("sun.net.client.defaultConnectTimeout","50000");
			connection.setRequestProperty("User-Agent","Mozilla/4.0");
			connection.connect();
			in=connection.getInputStream();
			byte[] buffer=new byte[50];
			in.read(buffer);
			htmlCode=new String(buffer);
		}catch(Exception e){
		}
		if(htmlCode==null||htmlCode.equals("")){ return false; }
		return true;
	}

	/**
	 * �ж��Ƿ���Ȩ��
	 */
	public boolean checkUserRight(HttpSession session,String cxdh){
		Hashtable rightsobj=(Hashtable)session.getAttribute("rightsobj");
		if(rightsobj!=null){
			if(rightsobj.get(cxdh)!=null){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}

	/**
	 * �жϼ�ʻ֤״̬�Ƿ�Ϸ�����Ҫ���ڵ��Ӽ�ش���
	 */
	public String checkDriverZt(String zt) throws Exception{
		String _result="1";
		zt=zt.toUpperCase();
		if(zt.indexOf("E")>-1){
			_result="��ʻ֤Ϊ����״̬";
			return _result;
		}
		if(zt.indexOf("F")>-1){
			_result="��ʻ֤Ϊ����״̬";
			return _result;
		}
		if(zt.indexOf("G")>-1){
			_result="��ʻ֤Ϊע��״̬";
			return _result;
		}
		if(zt.indexOf("J")>-1){
			_result="��ʻ֤Ϊֹͣʹ��״̬";
			return _result;
		}
		if(zt.indexOf("M")>-1){
			_result="��ʻ֤Ϊϵͳע��״̬";
			return _result;
		}
		return _result;
	}

	/**
	 * �жϼ�ʻ֤�Ƿ��Ƿ��ѳ��֣���Ҫ���ڵ��Ӽ�ش���
	 */
	public String checkDriverJf(String jszh,String xm,String hpzl,String hphm,int jf) throws Exception{
		return this.toolsDaoJdbc.checkDriverJf(jszh,xm,hpzl,hphm,jf);
	}

	public void requestRemoteServer(String ip) {
		try {
				URL url = new URL(ip);
				System.out.println("ˢ��ʱͨѶ��ַ��" +ip);
				HttpURLConnection con = (HttpURLConnection) url
						.openConnection();
				con.setDoOutput(true);
				con.setDoInput(true);
				con.setUseCaches(false);
                con.setConnectTimeout(10000);  
                con.setReadTimeout(300000);  
				OutputStream outs = con.getOutputStream();
				InputStream in = con.getInputStream();
		} catch (Exception e) {
		}
	}		
	public int getSystemExpiryDate(){
		return this.toolsDaoJdbc.getSystemExpiryDate();
	}
	public List getInterfaceExpirylist(){
		return this.toolsDaoJdbc.getInterfaceExpirylist();
	}
	/**
	 * ����log
	 * 
	 * @param code
	 */
	public void transLogtaskBean(FrmLogTask log){
		if(log!=null){
			// log.setBmmc(this.getBmmc(log.getGlbm()));
			// log.setCzsj(this.formatDateTimeToSec(log.getCzsj()));
			// log.setYhmc(this.getYhmc(log.getYhdh()));
			log.setRwmc(this.getCodeDmsm1("902",log.getRwid(),log.getRwid()));
		}
	}

	/**
	 * ����log
	 * 
	 * @param code
	 */
	public void transLogtaskList(List logList){
		if(logList!=null){
			for(int i=0;i<logList.size();i++){
				FrmLogTask r=(FrmLogTask)logList.get(i);
				transLogtaskBean(r);
			}
		}
	}
	
	/**
	 * ����log
	 * 
	 * @param code
	 */
	public void transLogrowBean(FrmLogRow log){
		if(log!=null){
			// log.setBmmc(this.getBmmc(log.getGlbm()));
			// log.setCzsj(this.formatDateTimeToSec(log.getCzsj()));
			// log.setYhmc(this.getYhmc(log.getYhdh()));
			log.setSjmc(this.getCodeDmsm1("901",log.getSjlx(),log.getSjlx()));
		}
	}

	/**
	 * ����log
	 * 
	 * @param code
	 */
	public void transLogrowList(List logList){
		if(logList!=null){
			for(int i=0;i<logList.size();i++){
				FrmLogRow r=(FrmLogRow)logList.get(i);
				transLogrowBean(r);
			}
		}
	}
	/**
	 * ����log
	 * 
	 * @param code
	 */
	public void transUpqueueBean(FrmUpQueue log){
		if(log!=null){
			// log.setBmmc(this.getBmmc(log.getGlbm()));
			// log.setCzsj(this.formatDateTimeToSec(log.getCzsj()));
			// log.setYhmc(this.getYhmc(log.getYhdh()));
			log.setSjmc(this.getCodeDmsm1("901",log.getSjlx(),log.getSjlx()));
		}
	}

	/**
	 * ����log
	 * 
	 * @param code
	 */
	public void transUpqueueList(List logList){
		if(logList!=null){
			for(int i=0;i<logList.size();i++){
				FrmUpQueue r=(FrmUpQueue)logList.get(i);
				transUpqueueBean(r);
			}
		}
	}
	
	public void setSessionValue(HttpServletRequest req,UserSession userSession){
		if(req.getParameter("tjnf")!=null){
			userSession.setTjnf(req.getParameter("tjnf"));
		}
		if(req.getParameter("tjlx")!=null){
			System.out.println(req.getParameter("tjlx"));
			userSession.setTjlx((String)req.getParameter("tjlx"));
		}
		if(req.getParameter("fzjg")!=null){
			userSession.setFzjg(req.getParameter("fzjg"));
		}
		if(req.getParameter("tjksrq")!=null){
			userSession.setTjksrq(req.getParameter("tjksrq"));
		}
		if(req.getParameter("tjjsrq")!=null){
			userSession.setTjjsrq(req.getParameter("tjjsrq"));
		}


	}
	
	public List getFxnyrList(String inputdate1,String inputdate2) throws Exception{		
		List lst = new Vector();
		Code code = null;
		SimpleDateFormat   sdf   =   new   SimpleDateFormat( "yyyy-MM-dd"); 
		SimpleDateFormat   sdf2   =   new   SimpleDateFormat( "yyyyMMdd"); 
		SimpleDateFormat   sdf1   =   new   SimpleDateFormat( "yyyy��MM��dd��"); 
		Calendar cd1 = Calendar.getInstance();    
		Calendar cd2 = Calendar.getInstance();    
		cd1.setTime(sdf.parse(inputdate1));
		cd2.setTime(sdf.parse(inputdate2));
		while(cd1.getTime().before(cd2.getTime())||cd1.getTime().equals(cd2.getTime())){
			code = new Code();
			code.setDmz(sdf2.format(cd1.getTime()));
			code.setDmsm1(sdf1.format(cd1.getTime()));
			lst.add(code);
			cd1.add(Calendar.DATE, 1);//
		}
		return lst;
	}
	
	//��ȡ��ǰ��֮ǰ��N���·�
	public List getMonthList(int iMonth) throws Exception{		
		List lst = new Vector();
		Code code = null;
		String _result = toolsDaoJdbc.getSysDate().substring(0,7);//��ȡ��ǰ����
		String year=_result.substring(0,4);
		String mon=_result.substring(5,7);
		year=(new Integer(year).intValue()-1)+"";
		code = new Code();
		code.setDmz(year+mon);
		code.setDmsm1(year+"-"+mon);
		lst.add(code);
		for(int i=1;i<iMonth;i++)
		{
			/*if(mon.equals("01")||mon.equals("1"))
			{
				mon="12";
				year=(new Integer(year).intValue()-1)+"";
				code = new Code();
				code.setDmz(year+mon);
				code.setDmsm1(year+"-"+mon);
				lst.add(code);
			}else{
				mon=(new Integer(mon).intValue()-1)+"";
				if(mon.length()==1)
				{
					mon="0"+mon;
				}
				code = new Code();
				code.setDmz(year+mon);
				code.setDmsm1(year+"-"+mon);
				lst.add(code);
			}*/
			if(mon.equals("12"))
			{
				mon="01";
				year=(new Integer(year).intValue()+1)+"";
				code = new Code();
				code.setDmz(year+mon);
				code.setDmsm1(year+"-"+mon);
				lst.add(code);
			}else{
				mon=(new Integer(mon).intValue()+1)+"";
				if(mon.length()==1)
				{
					mon="0"+mon;
				}
				code = new Code();
				code.setDmz(year+mon);
				code.setDmsm1(year+"-"+mon);
				lst.add(code);
			}
			
		}
		return lst;
	}
	/*
	public List getDmlbListFromMemByDmsm2(String dmlb,String dmsm2) throws DataAccessException, SQLException{		
		return this.codeDao.getCodesByDmsm2(dmlb,dmsm2);
	}
	
	public String getCodeDmsm1ByDmsm2(String dmlb,String dmsm2) throws DataAccessException, SQLException{		
		String _result=dmsm2;
		List list =this.codeDao.getCodesByDmsm2(dmlb,dmsm2);
		if(list!=null){
			if(list.size()>0){
				Code code = (Code) list.get(0);
				_result=code.getDmsm1();
			}
		}
		return _result;
	}
	
	
	public List getDmlbListFromMem(String dmlb,String strDmz) throws DataAccessException, SQLException{		
		return this.codeDao.getCodesByDmz(dmlb,strDmz);
	}
	*/
	
	/**
	 * ��ȡͬ�ڱȵ�ͳ������
	 * @param strTjlx
	 * @return
	 */
	public String getTqCondition (String strTjlx,String strTjnf)  throws SQLException{
		String tmpStr="";
		String _result="";
		//�ϰ���
		if (strTjlx.equalsIgnoreCase("21")){
			_result = "tjlx='22' and tjnf='"+(Long.parseLong(strTjnf)-1)+""+"'";
		}else if(strTjlx.equalsIgnoreCase("22")){
			_result = "tjlx='22' and tjnf='"+(Long.parseLong(strTjnf)-1)+""+"'";
		}else{
			tmpStr=this.toolsDaoJdbc.getMonthSysdate(strTjnf+"-"+strTjlx+"-01", "-1");
			_result = "tjlx='"+tmpStr.substring(5,7)+"' and tjnf='"+tmpStr.substring(0,4)+"'";
		}		
		return _result;
	}
	public List getTjnfList(){
		String xtsyrq = "2010-04-01";
		ArrayList list = new ArrayList();
		try {
			String sysdate = this.getSysDate();
			int sysnf= Integer.parseInt(sysdate.substring(0,4));
			int xtnf = Integer.parseInt(xtsyrq.substring(0,4));
			if(xtnf>=sysnf){
				Code code = new Code();
				code.setDmz(xtnf+"");
				code.setDmsm1(xtnf+"");
				list.add(code);
			}else{
				int jgnf = sysnf-xtnf;
				for( int i=0;i<=jgnf;i++){
					Code code = new Code();
					code.setDmz(xtnf+i+"");
					code.setDmsm1(xtnf+i+"");
					list.add(code);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}