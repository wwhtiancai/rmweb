package com.tmri.framework.dao.jdbc;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.tmri.framework.bean.FrmNoworkday;
import com.tmri.framework.bean.SysInitObj;
import com.tmri.framework.bean.SysResult;
import com.tmri.framework.dao.SysParaDao;
import com.tmri.share.cache.bean.SystemCache;
import com.tmri.share.frm.bean.SysPara;
import com.tmri.share.frm.bean.SysparaValue;
import com.tmri.share.frm.dao.GSysparaCodeDao;
import com.tmri.share.frm.dao.jdbc.FrmDaoJdbc;
import com.tmri.share.frm.util.Constants;
import com.tmri.share.frm.util.StringUtil;
import com.tmri.share.ora.FrmJdbcTemplate;
import com.tmri.share.ora.bean.CallableStatementCallbackImpl;
import com.tmri.share.ora.bean.DbParaInfo;
import com.tmri.share.ora.bean.DbResult;
@Repository
public class SysParaDaoJdbc extends FrmDaoJdbc implements SysParaDao{
	@Autowired
	FrmJdbcTemplate jdbcTemplate=null;
	@Autowired
	GSysparaCodeDao gSysparaCodeDao;


	public void setSysParaToMem(SysPara sysPara){
		String key="syspara:"+sysPara.getXtlb()+"_"+sysPara.getGjz();
		((Hashtable)SystemCache.getInstance().getValue(Constants.MEM_SYSPARA)).put(key,sysPara);
	}

	public void setSysParaValueToMem(SysparaValue sysparavalue){
		String key="sysparavalue:"+sysparavalue.getXtlb()+"_"+sysparavalue.getGlbm()+"_"+sysparavalue.getGjz();
		if(sysparavalue.getCsbj().equals("1")){
			((Hashtable)SystemCache.getInstance().getValue(Constants.MEM_SYSPARAVALUE)).remove(key);
		}else{
			((Hashtable)SystemCache.getInstance().getValue(Constants.MEM_SYSPARAVALUE)).put(key,sysparavalue.getCsz());	
		}
	}

	public List getFgzrwhList(String qsrq,String jsrq,String ywlb){
		String strSql="select * from frm_noworkday where sdate>=? and sdate<=? and bj='1' and ywlb=?";
		Object[] paraObjects=new Object[]{qsrq,jsrq,ywlb};
		return jdbcTemplate.queryForList(strSql,paraObjects,FrmNoworkday.class);
	}

	public List getDepparasShow(String xgjb,String gnlbString){
		String xtlbconString = "";
		HashMap map = new HashMap();
		if(StringUtil.checkBN(gnlbString)){
			String[] arr = gnlbString.split(",");
			
			for(int i=0;i<arr.length;i++){
				if(arr[i].equals("1020")){
					xtlbconString += " xtlb=:xtlb00 or";
					map.put("xtlb00","00");
				}else if(arr[i].equals("1021")){
					xtlbconString += " xtlb=:xtlb01 or";
					map.put("xtlb01","01");
				}else if(arr[i].equals("1022")){
					xtlbconString += " xtlb=:xtlb02 or";
					map.put("xtlb02","02");
				}else if(arr[i].equals("1023")){
					xtlbconString += " xtlb=:xtlb03 or";
					map.put("xtlb03","03");
				}else if(arr[i].equals("1024")){
					xtlbconString += " xtlb=:xtlb04 or";
					map.put("xtlb04","04");
				} else if(arr[i].equals("1025")){
					xtlbconString += " xtlb=:xtlb05 or";
					map.put("xtlb05","05");
				} 
			}
			xtlbconString = xtlbconString.substring(0,xtlbconString.length() - 3);
		}

		String tmpSql="select * from frm_syspara where sfxs='1' and cslx='1' and xgjb>=:xgjb ";
		if(!xtlbconString.equals("")){
			tmpSql=tmpSql+" and (" + xtlbconString + ") ";
		}
		tmpSql=tmpSql+" order by xtlb,fzmc,to_number(xssx)";
		map.put("xgjb",xgjb);
		return jdbcTemplate.queryForList(tmpSql,map,SysPara.class);
	}

	public SysResult saveSysPara(){
		String callString="{call FRM_SYS_PKG.Savefrm_Syspara(?,?,?)}";
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

	public SysResult saveDepPara(){
		String callString="{call FRM_SYS_PKG.Savefrm_Deppara(?,?,?)}";
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
	
	public SysResult saveDepParaNolog(){
		String callString="{call FRM_SYS_PKG.Savefrm_DepparaNolog(?,?,?)}";
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
	
	public SysResult saveDepParaglxzqh(){
		String callString="{call FRM_SYS_PKG.Savefrm_Glxzqhdeppara(?,?,?)}";
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
	public DbResult doSysInit(SysInitObj sysInitObj) throws Exception{
		String callString="{call FRM_SYS_PKG.doSysInit(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				DbParaInfo dbParaInfo=(DbParaInfo)this.getParameterObject();
				SysInitObj sysInitObj=(SysInitObj)dbParaInfo.getObject();
				cstmt.setString(1,sysInitObj.getDqsfdm());
				cstmt.setString(2,sysInitObj.getXtyxms());
				cstmt.setString(3,sysInitObj.getDjglbm());
				cstmt.setString(4,sysInitObj.getSjglbm());
				cstmt.setString(5,sysInitObj.getBmmc());
				cstmt.setString(6,sysInitObj.getBmqc());
				cstmt.setString(7,sysInitObj.getYzmc());
				cstmt.setString(8,sysInitObj.getFzjg());
				cstmt.setString(9,sysInitObj.getBdfzjg());
				cstmt.setString(10,sysInitObj.getGzkjzms());
				cstmt.setString(11,sysInitObj.getYhdh());
				cstmt.setString(12,sysInitObj.getXm());
				cstmt.setString(13,sysInitObj.getMm());
				cstmt.setString(14,sysInitObj.getGzdms());
				cstmt.setString(15,sysInitObj.getJrip());
				cstmt.setString(16,sysInitObj.getJrkip());
				cstmt.setString(17,sysInitObj.getWz1());
				cstmt.setString(18,sysInitObj.getWz2());
				cstmt.setString(19,sysInitObj.getWz3());
				cstmt.setString(20,dbParaInfo.getCallkey());
				cstmt.registerOutParameter(21,Types.NUMERIC);
				cstmt.registerOutParameter(22,Types.VARCHAR);
				cstmt.execute();
				DbResult result=new DbResult();
				result.setCode(cstmt.getLong(21));
				result.setMsg(cstmt.getString(22));
				return result;
			}
		};
		DbParaInfo dbParaInfo=new DbParaInfo();
		String key=jdbcTemplate.getTalkid("00","0000","");
		dbParaInfo.setCallkey(key);
		dbParaInfo.setObject(sysInitObj);
		callBack.setParameterObject(dbParaInfo);
		DbResult result=(DbResult)jdbcTemplate.execute(callString,callBack);
		return result;
	}
	

}
