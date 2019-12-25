package com.tmri.framework.dao.jdbc;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.tmri.framework.bean.DepartmentCode;
import com.tmri.framework.bean.FrmInformsetup;
import com.tmri.framework.bean.FrmNoworkday;
import com.tmri.framework.bean.FrmNumSection;
import com.tmri.framework.bean.FrmQueryerrlog;
import com.tmri.framework.bean.FrmRbspLog;
import com.tmri.framework.bean.FrmSerialnumRange;
import com.tmri.framework.bean.FrmSmscontent;
import com.tmri.framework.bean.FrmSysrun;
import com.tmri.framework.bean.FrmUserloginfail;
import com.tmri.framework.bean.FrmWsAppForm;
import com.tmri.framework.bean.FrmWsContent;
import com.tmri.framework.bean.FrmWsControl;
import com.tmri.framework.bean.FrmWsIpxz;
import com.tmri.framework.bean.Log;
import com.tmri.framework.bean.Role;
import com.tmri.framework.bean.SysResult;
import com.tmri.framework.bean.UserRole;
import com.tmri.share.frm.bean.Code;
import com.tmri.share.frm.bean.SysPara;
import com.tmri.share.frm.bean.SysUser;
import com.tmri.share.frm.bean.SysparaValue;
import com.tmri.share.frm.dao.jdbc.FrmDaoJdbc;
import com.tmri.share.frm.util.FuncUtil;
import com.tmri.share.ora.FrmJdbcTemplate;
import com.tmri.share.ora.bean.CallableStatementCallbackImpl;
import com.tmri.share.ora.bean.DbParaInfo;

@Repository
public class FrmDataObjDaoJdbc extends FrmDaoJdbc {

	public String getKey() {
		return this.jdbcTemplate.getTalkid("00", "0000", "");
	}
	
	public FrmJdbcTemplate getJdbcTemplates(){
		return this.jdbcTemplate;
	}
	
	/**
	 * @param obj 赋值到oracle对象
	 * @param check 是否需要校验
	 * @return
	 */
	public SysResult setOracleData(Object obj,int check){
		SysResult result=null;
		if(obj.getClass().getName().equals("com.tmri.framework.bean.Role")){
			result=setRole((Role)obj,check);
		}else if(obj.getClass().getName().equals("com.tmri.framework.bean.Log")){
			result=setLog((Log)obj,check);
		}else if(obj.getClass().getName().equals("com.tmri.framework.bean.UserRole")){
			result=setUserRole((UserRole)obj,check);
		}else if(obj.getClass().getName().equals("com.tmri.share.frm.bean.SysUser")){
			result=setSysUser((SysUser)obj,check);
		}else if(obj.getClass().getName().equals("com.tmri.share.frm.bean.SysPara")){
			result=setSysPara((SysPara)obj,check);
		}else if(obj.getClass().getName().equals("com.tmri.share.frm.bean.SysparaValue")){
			result=setSysparaValue((SysparaValue)obj,check);
		}else if(obj.getClass().getName().equals("com.tmri.framework.bean.FrmNumSection")){
			result=setNumSection((FrmNumSection)obj,check);
		}else if(obj.getClass().getName().equals("com.tmri.framework.bean.FrmWsAppForm")){
			result=setFrmWsAppForm((FrmWsAppForm)obj,check);
		}else if(obj.getClass().getName().equals("com.tmri.framework.bean.FrmWsContent")){
			result=setFrmWsContent((FrmWsContent)obj,check);
		}else if(obj.getClass().getName().equals("com.tmri.framework.bean.FrmWsControl")){
			result=setFrmWsControl((FrmWsControl)obj,check);
		}else if(obj.getClass().getName().equals("com.tmri.framework.bean.FrmWsIpxz")){
			result=setFrmWsIpxz((FrmWsIpxz)obj,check);
		}else if(obj.getClass().getName().equals("com.tmri.framework.bean.FrmSerialnumRange")){
			result=setFrmSerialNumRanger((FrmSerialnumRange)obj,check);
		}else if(obj.getClass().getName().equals("com.tmri.share.frm.bean.Code")){
			result=setFrmCode((Code)obj,check);
		}else if(obj.getClass().getName().equals("com.tmri.framework.bean.FrmNoworkday")){
			result=setFrmNoworkday((FrmNoworkday)obj,check);
		}else if(obj.getClass().getName().equals("com.tmri.framework.bean.DepartmentCode")){
			result=setFrmDepartmentCode((DepartmentCode)obj,check);
		}else if(obj.getClass().getName().equals("com.tmri.framework.bean.FrmSysrun")){
			result=setFrmSysrun((FrmSysrun)obj,check);
		}else if(obj.getClass().getName().equals("com.tmri.framework.bean.FrmQueryerrlog")){
			result=setFrmQueryerrlog((FrmQueryerrlog)obj,check);
		}else if(obj.getClass().getName().equals("com.tmri.framework.bean.FrmRbspLog")){
			result=setFrmRbspLog((FrmRbspLog)obj,check);
		}else if(obj.getClass().getName().equals("com.tmri.framework.bean.FrmUserloginfail")){
			result=setFrmUserloginfail((FrmUserloginfail)obj,check);
		}else if(obj.getClass().getName().equals("com.tmri.framework.bean.FrmSmscontent")){
			result=setFrmSmscontent((FrmSmscontent)obj,check);
		}else if(obj.getClass().getName().equals("com.tmri.framework.bean.FrmInformsetup")){
			result=setFrmInformsetup((FrmInformsetup)obj,check);
		}
		return result;
	}

	public SysResult execCommProcedure(String procedure){
		String callString="{call "+procedure+"(?,?,?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				SysResult result=new SysResult();
				cstmt.registerOutParameter(1,Types.NUMERIC);
				cstmt.registerOutParameter(2,Types.VARCHAR);
				cstmt.registerOutParameter(3,Types.VARCHAR);
				cstmt.execute();
				result.setFlag(cstmt.getLong(1));
				result.setDesc(cstmt.getString(2));
				result.setDesc1(cstmt.getString(3));
				return result;
			}
		};
		SysResult result=(SysResult)jdbcTemplate.execute(callString,callBack);
		return result;
	}

	public void execCommProcedure1(String procedure){
		String callString="{call "+procedure+"()}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				cstmt.execute();
				return null;
			}
		};
		jdbcTemplate.execute(callString,callBack);
	}

	// zhoujn 20100522
	private SysResult setRole(Role obj,int check){
		String callString="{call Frm_Setdata_Pkg.Setfrm_Role(?,?,?,?,?,?,?,?,?,?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				DbParaInfo para=(DbParaInfo)getParameterObject();
				Role frmRole=(Role)para.getObject();
				SysResult result=new SysResult();

				FuncUtil.bean2cstmt(frmRole,"glbm",cstmt,1);
				FuncUtil.bean2cstmt(frmRole,"jsdh",cstmt,2);
				FuncUtil.bean2cstmt(frmRole,"jsmc",cstmt,3);
				FuncUtil.bean2cstmt(frmRole,"jscj",cstmt,4);
				FuncUtil.bean2cstmt(frmRole,"sjjsdh",cstmt,5);
				FuncUtil.bean2cstmt(frmRole,"jssx",cstmt,6);
				FuncUtil.bean2cstmt(frmRole,"bz",cstmt,7);

				cstmt.registerOutParameter(8,Types.NUMERIC);
				cstmt.registerOutParameter(9,Types.VARCHAR);
				cstmt.registerOutParameter(10,Types.VARCHAR);
				cstmt.execute();
				result.setFlag((cstmt.getLong(8)));
				result.setDesc(cstmt.getString(9));
				result.setDesc1(cstmt.getString(10));
				if(result.getFlag()==0){
					result.setDesc1("输入有误，"+result.getDesc1());
				}
				return result;
			}
		};
		DbParaInfo para=new DbParaInfo();
		para.setObject(obj);
		para.setCheck(check);
		callBack.setParameterObject(para);
		SysResult result=(SysResult)jdbcTemplate.execute(callString,callBack);
		return result;
	}

	private SysResult setLog(Log obj,int check){
		String callString="{call Frm_Setdata_Pkg.Setfrm_log(?,?,?,?,?,?,?,?,?,?,?,?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				DbParaInfo para=(DbParaInfo)getParameterObject();
				Log frmLog=(Log)para.getObject();
				SysResult result=new SysResult();

				if(frmLog.getGlbm()==null)
					cstmt.setNull(1,Types.VARCHAR);
				else cstmt.setString(1,frmLog.getGlbm());
				if(frmLog.getYhdh()==null)
					cstmt.setNull(2,Types.VARCHAR);
				else cstmt.setString(2,frmLog.getYhdh());
				if(frmLog.getCzsj()==null)
					cstmt.setNull(3,Types.VARCHAR);
				else cstmt.setString(3,frmLog.getCzsj());
				if(frmLog.getCzlx()==null)
					cstmt.setNull(4,Types.VARCHAR);
				else cstmt.setString(4,frmLog.getCzlx());
				if(frmLog.getCzgn()==null)
					cstmt.setNull(5,Types.VARCHAR);
				else cstmt.setString(5,frmLog.getCzgn());
				if(frmLog.getCznr()==null)
					cstmt.setNull(6,Types.VARCHAR);
				else cstmt.setString(6,frmLog.getCznr());
				if(frmLog.getIp()==null)
					cstmt.setNull(7,Types.VARCHAR);
				else cstmt.setString(7,frmLog.getIp());
				if(frmLog.getBz()==null)
					cstmt.setNull(8,Types.VARCHAR);
				else cstmt.setString(8,frmLog.getBz());
				cstmt.setLong(9,para.getCheck());
				cstmt.registerOutParameter(10,Types.NUMERIC);
				cstmt.registerOutParameter(11,Types.VARCHAR);
				cstmt.registerOutParameter(12,Types.VARCHAR);
				cstmt.execute();
				result.setFlag((cstmt.getLong(10)));
				result.setDesc(cstmt.getString(11));
				result.setDesc1(cstmt.getString(12));
				if(result.getFlag()==0){
					result.setDesc1("输入有误，"+result.getDesc1());
				}
				return result;
			}
		};
		DbParaInfo para=new DbParaInfo();
		para.setObject(obj);
		para.setCheck(check);
		callBack.setParameterObject(para);
		SysResult result=(SysResult)jdbcTemplate.execute(callString,callBack);
		return result;
	}

	private SysResult setUserRole(UserRole obj,int check){
		String callString="{call Frm_Setdata_Pkg.SetFrm_Userrole(?,?,?,?,?,?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				DbParaInfo para=(DbParaInfo)getParameterObject();
				UserRole userRole=(UserRole)para.getObject();
				SysResult result=new SysResult();
				if(userRole.getYhdh()==null)
					cstmt.setNull(1,Types.VARCHAR);
				else cstmt.setString(1,userRole.getYhdh());
				if(userRole.getJsdh()==null)
					cstmt.setNull(2,Types.VARCHAR);
				else cstmt.setString(2,userRole.getJsdh());
				cstmt.setLong(3,para.getCheck());
				cstmt.registerOutParameter(4,Types.NUMERIC);
				cstmt.registerOutParameter(5,Types.VARCHAR);
				cstmt.registerOutParameter(6,Types.VARCHAR);
				cstmt.execute();
				result.setFlag((cstmt.getLong(4)));
				result.setDesc(cstmt.getString(5));
				result.setDesc1(cstmt.getString(6));
				if(result.getFlag()==0){
					result.setDesc1("输入有误，"+result.getDesc1());
				}
				return result;
			}
		};
		DbParaInfo para=new DbParaInfo();
		para.setObject(obj);
		para.setCheck(check);
		callBack.setParameterObject(para);
		SysResult result=(SysResult)jdbcTemplate.execute(callString,callBack);
		return result;
	}


	// zhoujn 20100524
	private SysResult setSysUser(SysUser obj,int check){
		String callString="{call Frm_Setdata_Pkg.Setfrm_sysuser("+"?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				DbParaInfo para=(DbParaInfo)getParameterObject();
				SysUser frmSysuser=(SysUser)para.getObject();
				SysResult result=new SysResult();
				FuncUtil.bean2cstmt(frmSysuser,"yhdh",cstmt,1);
				FuncUtil.bean2cstmt(frmSysuser,"xm",cstmt,2);
				FuncUtil.bean2cstmt(frmSysuser,"mm",cstmt,3);
				FuncUtil.bean2cstmt(frmSysuser,"glbm",cstmt,4);
				FuncUtil.bean2cstmt(frmSysuser,"sfzmhm",cstmt,5);
				FuncUtil.bean2cstmt(frmSysuser,"ipks",cstmt,6);
				FuncUtil.bean2cstmt(frmSysuser,"ipjs",cstmt,7);
				FuncUtil.bean2cstmt(frmSysuser,"zhyxq",cstmt,8);
				FuncUtil.bean2cstmt(frmSysuser,"mmyxq",cstmt,9);
				FuncUtil.bean2cstmt(frmSysuser,"jyd",cstmt,10);
				FuncUtil.bean2cstmt(frmSysuser,"spjb",cstmt,11);
				FuncUtil.bean2cstmt(frmSysuser,"spglbm",cstmt,12);
				FuncUtil.bean2cstmt(frmSysuser,"sfmj",cstmt,13);
				FuncUtil.bean2cstmt(frmSysuser,"rybh",cstmt,14);
				FuncUtil.bean2cstmt(frmSysuser,"qxms",cstmt,15);
				FuncUtil.bean2cstmt(frmSysuser,"zt",cstmt,16);
				FuncUtil.bean2cstmt(frmSysuser,"xtgly",cstmt,17);
				FuncUtil.bean2cstmt(frmSysuser,"zjdlsj",cstmt,18);
				FuncUtil.bean2cstmt(frmSysuser,"bz",cstmt,19);
				FuncUtil.bean2cstmt(frmSysuser,"kgywyhlx",cstmt,20);
				FuncUtil.bean2cstmt(frmSysuser,"yhssyw",cstmt,21);
				FuncUtil.bean2cstmt(frmSysuser,"mac",cstmt,22);
				FuncUtil.bean2cstmt(frmSysuser,"gdip",cstmt,23);
				FuncUtil.bean2cstmt(frmSysuser,"gdip1",cstmt,24);
				FuncUtil.bean2cstmt(frmSysuser,"gdip2",cstmt,25);
				cstmt.setLong(26,para.getCheck());
				cstmt.registerOutParameter(27,Types.NUMERIC);
				cstmt.registerOutParameter(28,Types.VARCHAR);
				cstmt.registerOutParameter(29,Types.VARCHAR);
				cstmt.execute();
				result.setFlag((cstmt.getLong(27)));
				result.setDesc(cstmt.getString(28));
				result.setDesc1(cstmt.getString(29));
				if(result.getFlag()==0){
					result.setDesc1("输入有误，"+result.getDesc1());
				}
				return result;
			}
		};
		DbParaInfo para=new DbParaInfo();
		para.setObject(obj);
		para.setCheck(check);
		callBack.setParameterObject(para);
		SysResult result=(SysResult)jdbcTemplate.execute(callString,callBack);
		return result;
	}
	
	private SysResult setSysPara(SysPara obj,int check){
		String callString="{call Frm_Setdata_Pkg.Setfrm_syspara(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				DbParaInfo para=(DbParaInfo)getParameterObject();
				SysPara frmSyspara=(SysPara)para.getObject();
				SysResult result=new SysResult();
				if(frmSyspara.getXtlb()==null)
					cstmt.setNull(1,Types.VARCHAR);
				else cstmt.setString(1,frmSyspara.getXtlb());
				if(frmSyspara.getCslx()==null)
					cstmt.setNull(2,Types.VARCHAR);
				else cstmt.setString(2,frmSyspara.getCslx());
				if(frmSyspara.getGjz()==null)
					cstmt.setNull(3,Types.VARCHAR);
				else cstmt.setString(3,frmSyspara.getGjz());
				if(frmSyspara.getCsmc()==null)
					cstmt.setNull(4,Types.VARCHAR);
				else cstmt.setString(4,frmSyspara.getCsmc());
				if(frmSyspara.getCssm()==null)
					cstmt.setNull(5,Types.VARCHAR);
				else cstmt.setString(5,frmSyspara.getCssm());
				if(frmSyspara.getMrz()==null)
					cstmt.setNull(6,Types.VARCHAR);
				else cstmt.setString(6,frmSyspara.getMrz());
				if(frmSyspara.getXgjb()==null)
					cstmt.setNull(7,Types.VARCHAR);
				else cstmt.setString(7,frmSyspara.getXgjb());
				if(frmSyspara.getSfxs()==null)
					cstmt.setNull(8,Types.VARCHAR);
				else cstmt.setString(8,frmSyspara.getSfxs());
				if(frmSyspara.getXssx()==null)
					cstmt.setNull(9,Types.VARCHAR);
				else cstmt.setString(9,frmSyspara.getXssx());
				if(frmSyspara.getSjlx()==null)
					cstmt.setNull(10,Types.VARCHAR);
				else cstmt.setString(10,frmSyspara.getSjlx());
				if(frmSyspara.getSjgf()==null)
					cstmt.setNull(11,Types.VARCHAR);
				else cstmt.setString(11,frmSyspara.getSjgf());
				if(frmSyspara.getCssx()==null)
					cstmt.setNull(12,Types.VARCHAR);
				else cstmt.setString(12,frmSyspara.getCssx());
				if(frmSyspara.getBz()==null)
					cstmt.setNull(13,Types.VARCHAR);
				else cstmt.setString(13,frmSyspara.getBz());
				cstmt.setLong(14,para.getCheck());
				cstmt.registerOutParameter(15,Types.NUMERIC);
				cstmt.registerOutParameter(16,Types.VARCHAR);
				cstmt.registerOutParameter(17,Types.VARCHAR);
				cstmt.execute();
				result.setFlag((cstmt.getLong(15)));
				result.setDesc(cstmt.getString(16));
				result.setDesc1(cstmt.getString(17));
				if(result.getFlag()==0){
					result.setDesc1("输入有误，"+result.getDesc1());
				}
				return result;
			}
		};
		DbParaInfo para=new DbParaInfo();
		para.setObject(obj);
		para.setCheck(check);
		callBack.setParameterObject(para);
		SysResult result=(SysResult)jdbcTemplate.execute(callString,callBack);
		return result;
	}

	private SysResult setSysparaValue(SysparaValue obj,int check){
		String callString="{call Frm_Setdata_Pkg.Setfrm_sysparavalue(?,?,?,?,?,?,?,?,?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				DbParaInfo para=(DbParaInfo)getParameterObject();
				SysparaValue frmSyspara_value=(SysparaValue)para.getObject();
				SysResult result=new SysResult();
				if(frmSyspara_value.getXtlb()==null)
					cstmt.setNull(1,Types.VARCHAR);
				else cstmt.setString(1,frmSyspara_value.getXtlb());
				if(frmSyspara_value.getGlbm()==null)
					cstmt.setNull(2,Types.VARCHAR);
				else cstmt.setString(2,frmSyspara_value.getGlbm());
				if(frmSyspara_value.getGjz()==null)
					cstmt.setNull(3,Types.VARCHAR);
				else cstmt.setString(3,frmSyspara_value.getGjz());
				if(frmSyspara_value.getCsz()==null)
					cstmt.setNull(4,Types.VARCHAR);
				else cstmt.setString(4,frmSyspara_value.getCsz());
				if(frmSyspara_value.getCsbj()==null)
					cstmt.setNull(5,Types.VARCHAR);
				else cstmt.setString(5,frmSyspara_value.getCsbj());
				cstmt.setLong(6,para.getCheck());
				cstmt.registerOutParameter(7,Types.NUMERIC);
				cstmt.registerOutParameter(8,Types.VARCHAR);
				cstmt.registerOutParameter(9,Types.VARCHAR);
				cstmt.execute();
				result.setFlag((cstmt.getLong(7)));
				result.setDesc(cstmt.getString(8));
				result.setDesc1(cstmt.getString(9));
				if(result.getFlag()==0){
					result.setDesc1("输入有误，"+result.getDesc1());
				}
				return result;
			}
		};
		DbParaInfo para=new DbParaInfo();
		para.setObject(obj);
		para.setCheck(check);
		callBack.setParameterObject(para);
		SysResult result=(SysResult)jdbcTemplate.execute(callString,callBack);
		return result;
	}

	private SysResult setNumSection(FrmNumSection obj,int check){
		String callString="{call Frm_Setdata_Pkg.Setfrm_num_section(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				DbParaInfo para=(DbParaInfo)getParameterObject();
				FrmNumSection frmNum_section=(FrmNumSection)para.getObject();
				SysResult result=new SysResult();
				if(frmNum_section.getXtlb()==null)
					cstmt.setNull(1,Types.VARCHAR);
				else cstmt.setString(1,frmNum_section.getXtlb());
				if(frmNum_section.getBmqddh()==null)
					cstmt.setNull(2,Types.VARCHAR);
				else cstmt.setString(2,frmNum_section.getBmqddh());
				if(frmNum_section.getGlbm()==null)
					cstmt.setNull(3,Types.VARCHAR);
				else cstmt.setString(3,frmNum_section.getGlbm());
				if(frmNum_section.getFldmz()==null)
					cstmt.setNull(4,Types.VARCHAR);
				else cstmt.setString(4,frmNum_section.getFldmz());
				if(frmNum_section.getCjsj()==null)
					cstmt.setNull(5,Types.VARCHAR);
				else cstmt.setString(5,frmNum_section.getCjsj());
				if(frmNum_section.getKsbh()==null)
					cstmt.setNull(6,Types.VARCHAR);
				else cstmt.setString(6,frmNum_section.getKsbh());
				if(frmNum_section.getJsbh()==null)
					cstmt.setNull(7,Types.VARCHAR);
				else cstmt.setString(7,frmNum_section.getJsbh());
				if(frmNum_section.getSybj()==null)
					cstmt.setNull(8,Types.VARCHAR);
				else cstmt.setString(8,frmNum_section.getSybj());
				if(frmNum_section.getSyxl()==null)
					cstmt.setNull(9,Types.VARCHAR);
				else cstmt.setString(9,frmNum_section.getSyxl());
				cstmt.setLong(10,para.getCheck());
				cstmt.registerOutParameter(11,Types.NUMERIC);
				cstmt.registerOutParameter(12,Types.VARCHAR);
				cstmt.registerOutParameter(13,Types.VARCHAR);
				cstmt.execute();
				result.setFlag((cstmt.getLong(11)));
				result.setDesc(cstmt.getString(12));
				result.setDesc1(cstmt.getString(13));
				if(result.getFlag()==0){
					result.setDesc1("输入有误，"+result.getDesc1());
				}
				return result;
			}
		};
		DbParaInfo para=new DbParaInfo();
		para.setObject(obj);
		para.setCheck(check);
		callBack.setParameterObject(para);
		SysResult result=(SysResult)jdbcTemplate.execute(callString,callBack);
		return result;
	}

	private SysResult setFrmWsAppForm(FrmWsAppForm obj,int check){
		String callString="{call Frm_Setdata_Pkg.setFRM_WS_APPFORM(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				DbParaInfo para=(DbParaInfo)getParameterObject();
				FrmWsAppForm obj=(FrmWsAppForm)para.getObject();
				SysResult result=new SysResult();
				FuncUtil.bean2cstmt(obj,"xtlb",cstmt,1);
				FuncUtil.bean2cstmt(obj,"azdm",cstmt,2);
				FuncUtil.bean2cstmt(obj,"dyrjmc",cstmt,3);
				FuncUtil.bean2cstmt(obj,"dyrjkfdw",cstmt,4);
				FuncUtil.bean2cstmt(obj,"dyzdw",cstmt,5);
				FuncUtil.bean2cstmt(obj,"fzjg",cstmt,6);
				FuncUtil.bean2cstmt(obj,"kfwjk",cstmt,7);
				FuncUtil.bean2cstmt(obj,"ksip",cstmt,8);
				FuncUtil.bean2cstmt(obj,"jsip",cstmt,9);
				FuncUtil.bean2cstmt(obj,"jkqsrq",cstmt,10);
				FuncUtil.bean2cstmt(obj,"jkjzrq",cstmt,11);
				FuncUtil.bean2cstmt(obj,"bz",cstmt,12);
				FuncUtil.bean2cstmt(obj,"sqlx",cstmt,13);
				FuncUtil.bean2cstmt(obj,"scbj",cstmt,14);
				FuncUtil.bean2cstmt(obj,"gxsj",cstmt,15);
				FuncUtil.bean2cstmt(obj,"dyfzjg",cstmt,16);
				cstmt.setLong(17,para.getCheck());
				cstmt.registerOutParameter(18,Types.NUMERIC);
				cstmt.registerOutParameter(19,Types.VARCHAR);
				cstmt.registerOutParameter(20,Types.VARCHAR);
				cstmt.execute();
				result.setFlag((cstmt.getLong(18)));
				result.setDesc(cstmt.getString(19));
				result.setDesc1(cstmt.getString(20));
				if(result.getFlag()==0){
					result.setDesc1("输入有误，"+result.getDesc1());
				}
				return result;
			}
		};
		DbParaInfo para=new DbParaInfo();
		para.setObject(obj);
		para.setCheck(check);
		callBack.setParameterObject(para);
		SysResult result=(SysResult)jdbcTemplate.execute(callString,callBack);
		return result;
	}

	private SysResult setFrmWsContent(FrmWsContent obj,int check){
		String callString="{call Frm_Setdata_Pkg.setFRM_WS_CONTENT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				DbParaInfo para=(DbParaInfo)getParameterObject();
				FrmWsContent obj=(FrmWsContent)para.getObject();
				SysResult result=new SysResult();
				FuncUtil.bean2cstmt(obj,"jkid",cstmt,1);
				FuncUtil.bean2cstmt(obj,"jkmc",cstmt,2);
				FuncUtil.bean2cstmt(obj,"jkzt",cstmt,3);
				FuncUtil.bean2cstmt(obj,"jklb",cstmt,4);
				FuncUtil.bean2cstmt(obj,"jksx",cstmt,5);
				FuncUtil.bean2cstmt(obj,"fzl",cstmt,6);
				FuncUtil.bean2cstmt(obj,"sxl",cstmt,7);
				FuncUtil.bean2cstmt(obj,"fhl",cstmt,8);
				FuncUtil.bean2cstmt(obj,"jdmc",cstmt,9);
				FuncUtil.bean2cstmt(obj,"gxsj",cstmt,10);
				FuncUtil.bean2cstmt(obj,"rzbj",cstmt,11);
				FuncUtil.bean2cstmt(obj,"nbipxz",cstmt,12);
				FuncUtil.bean2cstmt(obj,"bz",cstmt,13);
				FuncUtil.bean2cstmt(obj,"jyw",cstmt,14);
				cstmt.setLong(15,para.getCheck());
				cstmt.registerOutParameter(16,Types.NUMERIC);
				cstmt.registerOutParameter(17,Types.VARCHAR);
				cstmt.registerOutParameter(18,Types.VARCHAR);
				cstmt.execute();
				result.setFlag((cstmt.getLong(16)));
				result.setDesc(cstmt.getString(17));
				result.setDesc1(cstmt.getString(18));
				if(result.getFlag()==0){
					result.setDesc1("输入有误，"+result.getDesc1());
				}
				return result;
			}
		};
		DbParaInfo para=new DbParaInfo();
		para.setObject(obj);
		para.setCheck(check);
		callBack.setParameterObject(para);
		SysResult result=(SysResult)jdbcTemplate.execute(callString,callBack);
		return result;
	}

	private SysResult setFrmWsControl(FrmWsControl obj,int check){
		String callString="{call Frm_Setdata_Pkg.setFRM_WS_CONTROL(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				DbParaInfo para=(DbParaInfo)getParameterObject();
				FrmWsControl obj=(FrmWsControl)para.getObject();
				SysResult result=new SysResult();
				FuncUtil.bean2cstmt(obj,"jkxlh",cstmt,1);
				FuncUtil.bean2cstmt(obj,"xtlb",cstmt,2);
				FuncUtil.bean2cstmt(obj,"azdm",cstmt,3);
				FuncUtil.bean2cstmt(obj,"dyrjmc",cstmt,4);
				FuncUtil.bean2cstmt(obj,"dyrjkfdw",cstmt,5);
				FuncUtil.bean2cstmt(obj,"dyzdw",cstmt,6);
				FuncUtil.bean2cstmt(obj,"fzjg",cstmt,7);
				FuncUtil.bean2cstmt(obj,"kfwjk",cstmt,8);
				FuncUtil.bean2cstmt(obj,"ksip",cstmt,9);
				FuncUtil.bean2cstmt(obj,"jsip",cstmt,10);
				FuncUtil.bean2cstmt(obj,"jkqsrq",cstmt,11);
				FuncUtil.bean2cstmt(obj,"jkjzrq",cstmt,12);
				FuncUtil.bean2cstmt(obj,"bz",cstmt,13);
				FuncUtil.bean2cstmt(obj,"jyw",cstmt,14);
				FuncUtil.bean2cstmt(obj,"gxsj",cstmt,15);
				FuncUtil.bean2cstmt(obj,"dyfzjg",cstmt,16);
				cstmt.setLong(17,para.getCheck());
				cstmt.registerOutParameter(18,Types.NUMERIC);
				cstmt.registerOutParameter(19,Types.VARCHAR);
				cstmt.registerOutParameter(20,Types.VARCHAR);
				cstmt.execute();
				result.setFlag((cstmt.getLong(18)));
				result.setDesc(cstmt.getString(19));
				result.setDesc1(cstmt.getString(20));
				if(result.getFlag()==0){
					result.setDesc1("输入有误，"+result.getDesc1());
				}
				return result;
			}
		};
		DbParaInfo para=new DbParaInfo();
		para.setObject(obj);
		para.setCheck(check);
		callBack.setParameterObject(para);
		SysResult result=(SysResult)jdbcTemplate.execute(callString,callBack);
		return result;
	}

	private SysResult setFrmWsIpxz(FrmWsIpxz obj,int check){
		String callString="{call Frm_Setdata_Pkg.setFRM_WS_IPXZ(?,?,?,?,?,?,?,?,?,?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				DbParaInfo para=(DbParaInfo)getParameterObject();
				FrmWsIpxz obj=(FrmWsIpxz)para.getObject();
				SysResult result=new SysResult();
				FuncUtil.bean2cstmt(obj,"jkid",cstmt,1);
				FuncUtil.bean2cstmt(obj,"fzjg",cstmt,2);
				FuncUtil.bean2cstmt(obj,"ip",cstmt,3);
				FuncUtil.bean2cstmt(obj,"dyxtlb",cstmt,4);
				FuncUtil.bean2cstmt(obj,"dyxtmc",cstmt,5);
				FuncUtil.bean2cstmt(obj,"bz",cstmt,6);
				cstmt.setLong(7,para.getCheck());
				cstmt.registerOutParameter(8,Types.NUMERIC);
				cstmt.registerOutParameter(9,Types.VARCHAR);
				cstmt.registerOutParameter(10,Types.VARCHAR);
				cstmt.execute();
				result.setFlag((cstmt.getLong(8)));
				result.setDesc(cstmt.getString(9));
				result.setDesc1(cstmt.getString(10));
				if(result.getFlag()==0){
					result.setDesc1("输入有误，"+result.getDesc1());
				}
				return result;
			}
		};
		DbParaInfo para=new DbParaInfo();
		para.setObject(obj);
		para.setCheck(check);
		callBack.setParameterObject(para);
		SysResult result=(SysResult)jdbcTemplate.execute(callString,callBack);
		return result;
	}

	private SysResult setFrmSerialNumRanger(FrmSerialnumRange obj,int check){
		String callString="{call Frm_Setdata_Pkg.SetFrm_serialnum_ranger(?,?,?,?,?,?,?,?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				DbParaInfo para=(DbParaInfo)getParameterObject();
				FrmSerialnumRange frmSerialnum_range=(FrmSerialnumRange)para.getObject();
				SysResult result=new SysResult();
				if(frmSerialnum_range.getFzjg()==null)
					cstmt.setNull(1,Types.VARCHAR);
				else cstmt.setString(1,frmSerialnum_range.getFzjg());
				if(frmSerialnum_range.getZjbhlx()==null)
					cstmt.setNull(2,Types.VARCHAR);
				else cstmt.setString(2,frmSerialnum_range.getZjbhlx());
				if(frmSerialnum_range.getQsbh()==null)
					cstmt.setNull(3,Types.VARCHAR);
				else cstmt.setString(3,frmSerialnum_range.getQsbh());
				if(frmSerialnum_range.getJsbh()==null)
					cstmt.setNull(4,Types.VARCHAR);
				else cstmt.setString(4,frmSerialnum_range.getJsbh());
				cstmt.setLong(5,para.getCheck());
				cstmt.registerOutParameter(6,Types.NUMERIC);
				cstmt.registerOutParameter(7,Types.VARCHAR);
				cstmt.registerOutParameter(8,Types.VARCHAR);
				cstmt.execute();
				result.setFlag((cstmt.getLong(6)));
				result.setDesc(cstmt.getString(7));
				result.setDesc1(cstmt.getString(8));
				if(result.getFlag()==0){
					result.setDesc1("输入有误，"+result.getDesc1());
				}
				return result;
			}
		};
		DbParaInfo para=new DbParaInfo();
		para.setObject(obj);
		para.setCheck(check);
		callBack.setParameterObject(para);
		SysResult result=(SysResult)jdbcTemplate.execute(callString,callBack);
		return result;
	}

	private SysResult setFrmCode(Code obj,int check){
		String callString="{call Frm_Setdata_Pkg.Setfrm_code(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				DbParaInfo para=(DbParaInfo)getParameterObject();
				Code frmCode=(Code)para.getObject();
				SysResult result=new SysResult();
				if(frmCode.getXtlb()==null)
					cstmt.setNull(1,Types.VARCHAR);
				else cstmt.setString(1,frmCode.getXtlb());
				if(frmCode.getDmlb()==null)
					cstmt.setNull(2,Types.VARCHAR);
				else cstmt.setString(2,frmCode.getDmlb());
				if(frmCode.getDmz()==null)
					cstmt.setNull(3,Types.VARCHAR);
				else cstmt.setString(3,frmCode.getDmz());
				if(frmCode.getDmsm1()==null)
					cstmt.setNull(4,Types.VARCHAR);
				else cstmt.setString(4,frmCode.getDmsm1());
				if(frmCode.getDmsm2()==null)
					cstmt.setNull(5,Types.VARCHAR);
				else cstmt.setString(5,frmCode.getDmsm2());
				if(frmCode.getDmsm3()==null)
					cstmt.setNull(6,Types.VARCHAR);
				else cstmt.setString(6,frmCode.getDmsm3());
				if(frmCode.getDmsm4()==null)
					cstmt.setNull(7,Types.VARCHAR);
				else cstmt.setString(7,frmCode.getDmsm4());
				if(frmCode.getDmsx()==null)
					cstmt.setNull(8,Types.VARCHAR);
				else cstmt.setString(8,frmCode.getDmsx());
				if(frmCode.getZt()==null)
					cstmt.setNull(9,Types.VARCHAR);
				else cstmt.setString(9,frmCode.getZt());
				if(frmCode.getSxh()==null)
					cstmt.setNull(10,Types.NUMERIC);
				else cstmt.setLong(10,frmCode.getSxh().longValue());
				if(frmCode.getYwdx()==null)
					cstmt.setNull(11,Types.VARCHAR);
				else cstmt.setString(11,frmCode.getYwdx());
				cstmt.setLong(12,para.getCheck());
				cstmt.registerOutParameter(13,Types.NUMERIC);
				cstmt.registerOutParameter(14,Types.VARCHAR);
				cstmt.registerOutParameter(15,Types.VARCHAR);
				cstmt.execute();
				result.setFlag((cstmt.getLong(13)));
				result.setDesc(cstmt.getString(14));
				result.setDesc1(cstmt.getString(15));
				if(result.getFlag()==0){
					result.setDesc1("输入有误，"+result.getDesc1());
				}
				return result;
			}
		};
		DbParaInfo para=new DbParaInfo();
		para.setObject(obj);
		para.setCheck(check);
		callBack.setParameterObject(para);
		SysResult result=(SysResult)jdbcTemplate.execute(callString,callBack);
		return result;
	}

	private SysResult setFrmNoworkday(FrmNoworkday obj,int check){
		String callString="{call Frm_Setdata_Pkg.Setfrm_Noworkday(?,?,?,?,?,?,?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				DbParaInfo para=(DbParaInfo)getParameterObject();
				FrmNoworkday frmNoworkday=(FrmNoworkday)para.getObject();
				SysResult result=new SysResult();
				if(frmNoworkday.getSdate()==null)
					cstmt.setNull(1,Types.VARCHAR);
				else cstmt.setString(1,frmNoworkday.getSdate());
				if(frmNoworkday.getBj()==null)
					cstmt.setNull(2,Types.VARCHAR);
				else cstmt.setString(2,frmNoworkday.getBj());
				if(frmNoworkday.getYwlb()==null)
					cstmt.setNull(3,Types.VARCHAR);
				else cstmt.setString(3,frmNoworkday.getYwlb());
				cstmt.setLong(4,para.getCheck());
				cstmt.registerOutParameter(5,Types.NUMERIC);
				cstmt.registerOutParameter(6,Types.VARCHAR);
				cstmt.registerOutParameter(7,Types.VARCHAR);
				cstmt.execute();
				result.setFlag((cstmt.getLong(5)));
				result.setDesc(cstmt.getString(6));
				result.setDesc1(cstmt.getString(7));
				if(result.getFlag()==0){
					result.setDesc1("输入有误，"+result.getDesc1());
				}
				return result;
			}
		};
		DbParaInfo para=new DbParaInfo();
		para.setObject(obj);
		para.setCheck(check);
		callBack.setParameterObject(para);
		SysResult result=(SysResult)jdbcTemplate.execute(callString,callBack);
		return result;
	}

	private SysResult setFrmDepartmentCode(DepartmentCode obj,int check){
		String callString="{call Frm_Setdata_Pkg.setFrm_department_code(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				DbParaInfo para=(DbParaInfo)getParameterObject();
				DepartmentCode frmDepartment_code=(DepartmentCode)para.getObject();
				SysResult result=new SysResult();
				if(frmDepartment_code.getGlbm()==null)
					cstmt.setNull(1,Types.VARCHAR);
				else cstmt.setString(1,frmDepartment_code.getGlbm());
				if(frmDepartment_code.getBmmc()==null)
					cstmt.setNull(2,Types.VARCHAR);
				else cstmt.setString(2,frmDepartment_code.getBmmc());
				if(frmDepartment_code.getBmqc()==null)
					cstmt.setNull(3,Types.VARCHAR);
				else cstmt.setString(3,frmDepartment_code.getBmqc());
				if(frmDepartment_code.getYzmc()==null)
					cstmt.setNull(4,Types.VARCHAR);
				else cstmt.setString(4,frmDepartment_code.getYzmc());
				if(frmDepartment_code.getFzjg()==null)
					cstmt.setNull(5,Types.VARCHAR);
				else cstmt.setString(5,frmDepartment_code.getFzjg());
				if(frmDepartment_code.getBmjb()==null)
					cstmt.setNull(6,Types.VARCHAR);
				else cstmt.setString(6,frmDepartment_code.getBmjb());
				if(frmDepartment_code.getSfywbm()==null)
					cstmt.setNull(7,Types.VARCHAR);
				else cstmt.setString(7,frmDepartment_code.getSfywbm());
				if(frmDepartment_code.getYwlb()==null)
					cstmt.setNull(8,Types.VARCHAR);
				else cstmt.setString(8,frmDepartment_code.getYwlb());
				if(frmDepartment_code.getFzr()==null)
					cstmt.setNull(9,Types.VARCHAR);
				else cstmt.setString(9,frmDepartment_code.getFzr());
				if(frmDepartment_code.getLxr()==null)
					cstmt.setNull(10,Types.VARCHAR);
				else cstmt.setString(10,frmDepartment_code.getLxr());
				if(frmDepartment_code.getLxdh()==null)
					cstmt.setNull(11,Types.VARCHAR);
				else cstmt.setString(11,frmDepartment_code.getLxdh());
				if(frmDepartment_code.getCzhm()==null)
					cstmt.setNull(12,Types.VARCHAR);
				else cstmt.setString(12,frmDepartment_code.getCzhm());
				if(frmDepartment_code.getLxdz()==null)
					cstmt.setNull(13,Types.VARCHAR);
				else cstmt.setString(13,frmDepartment_code.getLxdz());
				if(frmDepartment_code.getTxzqfr()==null)
					cstmt.setNull(14,Types.VARCHAR);
				else cstmt.setString(14,frmDepartment_code.getTxzqfr());
				if(frmDepartment_code.getSjbm()==null)
					cstmt.setNull(15,Types.VARCHAR);
				else cstmt.setString(15,frmDepartment_code.getSjbm());
				if(frmDepartment_code.getBz()==null)
					cstmt.setNull(16,Types.VARCHAR);
				else cstmt.setString(16,frmDepartment_code.getBz());
				if(frmDepartment_code.getJcgglbm()==null)
					cstmt.setNull(17,Types.VARCHAR);
				else cstmt.setString(17,frmDepartment_code.getJcgglbm());
				if(frmDepartment_code.getJjgglbm()==null)
					cstmt.setNull(18,Types.VARCHAR);
				else cstmt.setString(18,frmDepartment_code.getJjgglbm());
				if(frmDepartment_code.getJwfglbm()==null)
					cstmt.setNull(19,Types.VARCHAR);
				else cstmt.setString(19,frmDepartment_code.getJwfglbm());
				if(frmDepartment_code.getJsgglbm()==null)
					cstmt.setNull(20,Types.VARCHAR);
				else cstmt.setString(20,frmDepartment_code.getJsgglbm());
				if(frmDepartment_code.getJjdpglbm()==null)
					cstmt.setNull(21,Types.VARCHAR);
				else cstmt.setString(21,frmDepartment_code.getJjdpglbm());
				if(frmDepartment_code.getJjjdglbm()==null)
					cstmt.setNull(22,Types.VARCHAR);
				else cstmt.setString(22,frmDepartment_code.getJjjdglbm());
				cstmt.setLong(23,para.getCheck());
				cstmt.registerOutParameter(24,Types.NUMERIC);
				cstmt.registerOutParameter(25,Types.VARCHAR);
				cstmt.registerOutParameter(26,Types.VARCHAR);
				cstmt.execute();
				result.setFlag((cstmt.getLong(24)));
				result.setDesc(cstmt.getString(25));
				result.setDesc1(cstmt.getString(26));
				if(result.getFlag()==0){
					result.setDesc1("输入有误，"+result.getDesc1());
				}
				return result;
			}
		};
		DbParaInfo para=new DbParaInfo();
		para.setObject(obj);
		para.setCheck(check);
		callBack.setParameterObject(para);
		SysResult result=(SysResult)jdbcTemplate.execute(callString,callBack);
		return result;
	}


	private SysResult setFrmSysrun(FrmSysrun obj,int check){
		String callString="{call Frm_Setdata_Pkg.setFRM_SYSRUN(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				DbParaInfo para=(DbParaInfo)getParameterObject();
				FrmSysrun obj=(FrmSysrun)para.getObject();
				SysResult result=new SysResult();
				FuncUtil.bean2cstmt(obj,"webip",cstmt,1);
				FuncUtil.bean2cstmt(obj,"rq",cstmt,2);
				FuncUtil.bean2cstmt(obj,"fwsd",cstmt,3);
				FuncUtil.bean2cstmt(obj,"xtlb",cstmt,4);
				FuncUtil.bean2cstmt(obj,"cdbh",cstmt,5);
				FuncUtil.bean2cstmt(obj,"gnid",cstmt,6);
				FuncUtil.bean2cstmt(obj,"czlx",cstmt,7);
				FuncUtil.bean2cstmt(obj,"fwcs",cstmt,8);
				FuncUtil.bean2cstmt(obj,"fwbhgcs",cstmt,9);
				FuncUtil.bean2cstmt(obj,"bcfwsj",cstmt,10);
				FuncUtil.bean2cstmt(obj,"bzfwsj",cstmt,11);
				FuncUtil.bean2cstmt(obj,"zfwsj",cstmt,12);
				FuncUtil.bean2cstmt(obj,"hgbj",cstmt,13);
				FuncUtil.bean2cstmt(obj,"bz",cstmt,14);
				cstmt.setLong(15,para.getCheck());
				cstmt.registerOutParameter(16,Types.NUMERIC);
				cstmt.registerOutParameter(17,Types.VARCHAR);
				cstmt.registerOutParameter(18,Types.VARCHAR);
				cstmt.execute();
				result.setFlag((cstmt.getLong(16)));
				result.setDesc(cstmt.getString(17));
				result.setDesc1(cstmt.getString(18));
				if(result.getFlag()==0){
					result.setDesc1("输入有误，"+result.getDesc1());
				}
				return result;
			}
		};
		DbParaInfo para=new DbParaInfo();
		para.setObject(obj);
		para.setCheck(check);
		callBack.setParameterObject(para);
		SysResult result=(SysResult)jdbcTemplate.execute(callString,callBack);
		return result;
	}

	private SysResult setFrmQueryerrlog(FrmQueryerrlog obj,int check){
		String callString="{call Frm_Setdata_Pkg.setFRM_QUERYERR_LOG(?,?,?,?,?,?,?,?,?,?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				DbParaInfo para=(DbParaInfo)getParameterObject();
				FrmQueryerrlog obj=(FrmQueryerrlog)para.getObject();
				SysResult result=new SysResult();
				FuncUtil.bean2cstmt(obj,"gnbh",cstmt,1);
				FuncUtil.bean2cstmt(obj,"yhdh",cstmt,2);
				FuncUtil.bean2cstmt(obj,"rq",cstmt,3);
				FuncUtil.bean2cstmt(obj,"fwcs",cstmt,4);
				FuncUtil.bean2cstmt(obj,"ipdz",cstmt,5);
				FuncUtil.bean2cstmt(obj,"bz",cstmt,6);

				cstmt.setLong(7,para.getCheck());
				cstmt.registerOutParameter(8,Types.NUMERIC);
				cstmt.registerOutParameter(9,Types.VARCHAR);
				cstmt.registerOutParameter(10,Types.VARCHAR);
				cstmt.execute();
				result.setFlag((cstmt.getLong(8)));
				result.setDesc(cstmt.getString(9));
				result.setDesc1(cstmt.getString(10));
				if(result.getFlag()==0){
					result.setDesc1("输入有误，"+result.getDesc1());
				}
				return result;
			}
		};
		DbParaInfo para=new DbParaInfo();
		para.setObject(obj);
		para.setCheck(check);
		callBack.setParameterObject(para);
		SysResult result=(SysResult)jdbcTemplate.execute(callString,callBack);
		return result;
	}

	private SysResult setFrmRbspLog(FrmRbspLog obj,int check){
		String callString="{call Frm_Setdata_Pkg.setFRM_RBSP_LOG(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				DbParaInfo para=(DbParaInfo)getParameterObject();
				FrmRbspLog obj=(FrmRbspLog)para.getObject();
				SysResult result=new SysResult();
				FuncUtil.bean2cstmt(obj,"fwsj",cstmt,1);
				FuncUtil.bean2cstmt(obj,"sjlx",cstmt,2);
				FuncUtil.bean2cstmt(obj,"xtlb",cstmt,3);
				FuncUtil.bean2cstmt(obj,"cdbh",cstmt,4);
				FuncUtil.bean2cstmt(obj,"gnid",cstmt,5);
				FuncUtil.bean2cstmt(obj,"cxtj",cstmt,6);
				FuncUtil.bean2cstmt(obj,"cxjg",cstmt,7);
				FuncUtil.bean2cstmt(obj,"bz",cstmt,8);
				FuncUtil.bean2cstmt(obj,"glbm",cstmt,9);
				FuncUtil.bean2cstmt(obj,"jbr",cstmt,10);
				cstmt.setLong(11,para.getCheck());
				cstmt.registerOutParameter(12,Types.NUMERIC);
				cstmt.registerOutParameter(13,Types.VARCHAR);
				cstmt.registerOutParameter(14,Types.VARCHAR);
				cstmt.execute();
				result.setFlag((cstmt.getLong(12)));
				result.setDesc(cstmt.getString(13));
				result.setDesc1(cstmt.getString(14));
				if(result.getFlag()==0){
					result.setDesc1("输入有误，"+result.getDesc1());
				}
				return result;
			}
		};
		DbParaInfo para=new DbParaInfo();
		para.setObject(obj);
		para.setCheck(check);
		callBack.setParameterObject(para);
		SysResult result=(SysResult)jdbcTemplate.execute(callString,callBack);
		return result;
	}

	private SysResult setFrmUserloginfail(FrmUserloginfail obj,int check){
		String callString="{call FRM_SETDATA_PKG.setFRM_USERLOGINFAIL(?,?,?,?,?,?,?,?,?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				DbParaInfo para=(DbParaInfo)getParameterObject();
				FrmUserloginfail obj=(FrmUserloginfail)para.getObject();
				SysResult result=new SysResult();
				FuncUtil.bean2cstmt(obj,"yhdh",cstmt,1);
				FuncUtil.bean2cstmt(obj,"dlsj",cstmt,2);
				FuncUtil.bean2cstmt(obj,"ipdz",cstmt,3);
				FuncUtil.bean2cstmt(obj,"yhmm",cstmt,4);
				FuncUtil.bean2cstmt(obj,"bz",cstmt,5);
				cstmt.setLong(6,para.getCheck());
				cstmt.registerOutParameter(7,Types.NUMERIC);
				cstmt.registerOutParameter(8,Types.VARCHAR);
				cstmt.registerOutParameter(9,Types.VARCHAR);
				cstmt.execute();
				result.setFlag((cstmt.getLong(7)));
				result.setDesc(cstmt.getString(8));
				result.setDesc1(cstmt.getString(9));
				if(result.getFlag()==0){
					result.setDesc1("输入有误，"+result.getDesc1());
				}
				return result;
			}
		};
		DbParaInfo para=new DbParaInfo();
		para.setObject(obj);
		para.setCheck(check);
		callBack.setParameterObject(para);
		SysResult result=(SysResult)jdbcTemplate.execute(callString,callBack);
		return result;

	}
	private SysResult setFrmSmscontent(FrmSmscontent obj,int check){
    String callString = "{call FRM_SETDATA_PKG.setFRM_SMS_CONTENT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		CallableStatementCallbackImpl callBack = new CallableStatementCallbackImpl() {
			public Object doInCallableStatement(CallableStatement cstmt)
					throws SQLException, DataAccessException {
				DbParaInfo para = (DbParaInfo) getParameterObject();
				FrmSmscontent obj = (FrmSmscontent)para.getObject();
				SysResult result = new SysResult();
        FuncUtil.bean2cstmt(obj,"xxbh",cstmt,1);
        FuncUtil.bean2cstmt(obj,"jsglbm",cstmt,2);
        FuncUtil.bean2cstmt(obj,"jsyhdh",cstmt,3);
        FuncUtil.bean2cstmt(obj,"xxdm",cstmt,4);
        FuncUtil.bean2cstmt(obj,"cjsj",cstmt,5);
        FuncUtil.bean2cstmt(obj,"xxnr",cstmt,6);
        FuncUtil.bean2cstmt(obj,"bj",cstmt,7);
        FuncUtil.bean2cstmt(obj,"fsglbm",cstmt,8);
        FuncUtil.bean2cstmt(obj,"fsr",cstmt,9);
        FuncUtil.bean2cstmt(obj,"bz",cstmt,10);
        FuncUtil.bean2cstmt(obj,"dxlx",cstmt,11);
        FuncUtil.bean2cstmt(obj,"xxbt",cstmt,12);
        cstmt.setLong(13,para.getCheck());
        cstmt.registerOutParameter(14,Types.NUMERIC);
        cstmt.registerOutParameter(15,Types.VARCHAR);
        cstmt.registerOutParameter(16,Types.VARCHAR);
        cstmt.execute();
        result.setFlag((cstmt.getLong(14)));
        result.setDesc(cstmt.getString(15));
        result.setDesc1(cstmt.getString(16));
		    if(result.getFlag()==0){
				      result.setDesc1("输入有误，"+result.getDesc1());
				}
				return result;
			}
		};
		DbParaInfo para = new DbParaInfo();
		para.setObject(obj);
		para.setCheck(check);
		callBack.setParameterObject(para);
		SysResult result = (SysResult)jdbcTemplate.execute(callString, callBack);
		return result;
	}
	private SysResult setFrmInformsetup(FrmInformsetup obj,int check){
		 String callString = "{call FRM_SETDATA_PKG.setFRM_INFORM_SETUP(?,?,?,?,?,?,?,?,?,?,?)}";
			CallableStatementCallbackImpl callBack = new CallableStatementCallbackImpl() {
				public Object doInCallableStatement(CallableStatement cstmt)
						throws SQLException, DataAccessException {
					DbParaInfo para = (DbParaInfo) getParameterObject();
					FrmInformsetup obj = (FrmInformsetup)para.getObject();
					SysResult result = new SysResult();
	        FuncUtil.bean2cstmt(obj,"xxdm",cstmt,1);
	        FuncUtil.bean2cstmt(obj,"glbm",cstmt,2);
	        FuncUtil.bean2cstmt(obj,"yhdh",cstmt,3);
	        FuncUtil.bean2cstmt(obj,"txsj",cstmt,4);
	        FuncUtil.bean2cstmt(obj,"zt",cstmt,5);
	        FuncUtil.bean2cstmt(obj,"bz",cstmt,6);
	        FuncUtil.bean2cstmt(obj,"sctxsj",cstmt,7);
	        cstmt.setLong(8,para.getCheck());
	        cstmt.registerOutParameter(9,Types.NUMERIC);
	        cstmt.registerOutParameter(10,Types.VARCHAR);
	        cstmt.registerOutParameter(11,Types.VARCHAR);
	        cstmt.execute();
	        result.setFlag((cstmt.getLong(9)));
	        result.setDesc(cstmt.getString(10));
	        result.setDesc1(cstmt.getString(11));
			    if(result.getFlag()==0){
					      result.setDesc1("输入有误，"+result.getDesc1());
					}
					return result;
				}
			};
			DbParaInfo para = new DbParaInfo();
			para.setObject(obj);
			para.setCheck(check);
			callBack.setParameterObject(para);
			SysResult result = (SysResult)jdbcTemplate.execute(callString, callBack);
			return result;
		}
	public String execFireVersion(){
		String callString="{?=call FRM_J_PKG.fire_version}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				SysResult result=new SysResult();
				cstmt.registerOutParameter(1,Types.VARCHAR);
				cstmt.execute();
				return cstmt.getString(1);
			}
		};
		String result=(String)jdbcTemplate.execute(callString,callBack);
		return result;
	}
	public String execFireGg(String key,String content){
		String callString="{? = call frm_j_pkg.fire_gg(?,?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				String[] obj=(String[]) this.getParameterObject();
				cstmt.registerOutParameter(1,Types.VARCHAR);
				cstmt.setString(2,obj[0]);
				cstmt.setString(3,obj[1]);
				cstmt.execute();
				return cstmt.getString(1);
			}
		};
		callBack.setParameterObject(new String[]{key,content});
		String result=(String)jdbcTemplate.execute(callString,callBack);
		return result;
	}
	
}
