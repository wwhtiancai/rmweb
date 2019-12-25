package com.tmri.framework.dao.jdbc;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.tmri.framework.bean.FrmWsAppForm;
import com.tmri.framework.bean.FrmWsContent;
import com.tmri.framework.bean.FrmWsControl;
import com.tmri.framework.bean.FrmWsIpxz;
import com.tmri.framework.bean.FrmWsLog;
import com.tmri.framework.bean.FrmWsStat;
import com.tmri.framework.bean.SysResult;
import com.tmri.share.frm.util.PageController;
import com.tmri.share.frm.dao.jdbc.FrmDaoJdbc;
import com.tmri.share.ora.bean.CallableStatementCallbackImpl;
@Repository
public class WsDaoJdbc extends FrmDaoJdbc {
	
	public SysResult saveFrmWsContent() throws Exception{
		String callString="{call Frm_Wscontent_Pkg.Savefrmwscontent(?,?,?)}";
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
	public SysResult saveWsStatAlign() throws Exception{
		String callString="{call Frm_Wscontent_Pkg.saveWsStatAlign(?,?,?)}";
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
	public SysResult saveFrmWsAppForm() throws Exception{
		String callString="{call Frm_Wscontent_Pkg.SaveFrmWsAppForm(?,?,?)}";
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
	public void updateFrmAppFormScbj(String xtlb,String azdm, String dyrjmc)throws DataAccessException 
	{
		String sql = "update frm_ws_appform set scbj='1' where xtlb=? and azdm=? and dyrjmc=?";
		Object[] paraObjects=new Object[]{xtlb,azdm,dyrjmc};
		jdbcTemplate.update(sql, paraObjects);
}	
	public SysResult saveFrmWsControl() throws Exception{
		String callString="{call Frm_Wscontent_Pkg.SaveFrmWsControl(?,?,?)}";
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
	public SysResult delFrmWsAppForm() throws Exception{
		String callString="{call Frm_Wscontent_Pkg.DelFrmWsAppForm(?,?,?)}";
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
	public FrmWsContent getWsContent(String jkid) throws Exception{
		String strSql="select * from frm_ws_content where jkid=?";
		Object[] paraObjects=new Object[]{jkid};
		FrmWsContent wscontent=(FrmWsContent)this.jdbcTemplate.queryForSingleObject(strSql,paraObjects,FrmWsContent.class);
		return wscontent;
	}
	public FrmWsControl getWsControl(String jkxlh) throws Exception{
		String strSql="select xtlb,azdm,dyrjmc,dyrjkfdw,dyzdw,fzjg,kfwjk,ksip,jsip,";
		strSql+="to_char(jkqsrq,'yyyy-MM-dd')jkqsrq,to_char(jkjzrq,'yyyy-MM-dd')jkjzrq,";
		strSql+="bz,jkxlh,jyw,dyfzjg from frm_ws_control where jkxlh=?";
		Object[] paraObjects=new Object[]{jkxlh};
		FrmWsControl wscontrol=(FrmWsControl)this.jdbcTemplate.queryForSingleObject(strSql,paraObjects,FrmWsControl.class);
		return wscontrol;
	}
	public FrmWsAppForm getWsAppForm(String xtlb,String azdm,String dyrjmc) throws Exception{
		String strSql="select xtlb,azdm,dyrjmc,dyrjkfdw,dyzdw,fzjg,kfwjk,ksip,jsip,";
		strSql+="to_char(jkqsrq,'yyyy-MM-dd')jkqsrq,to_char(jkjzrq,'yyyy-MM-dd')jkjzrq,";
		strSql+="bz,sqlx,scbj,dyfzjg from frm_ws_appform where xtlb=? and azdm=? and dyrjmc=?";
		Object[] paraObjects=new Object[]{xtlb,azdm,dyrjmc};
		FrmWsAppForm wsAppForm=(FrmWsAppForm)this.jdbcTemplate.queryForSingleObject(strSql,paraObjects,FrmWsAppForm.class);
		return wsAppForm;
	}	
	public List getWsContentList(String xtlb,String jksx) throws Exception
	{
		String strSql="select * from frm_ws_content ";
		if(xtlb==null||xtlb.equals("")||xtlb.toLowerCase().equals("null"))
		{
			strSql+="where instr(?,jksx)>0 order by jkid";
			Object[] paraObjects=new Object[]{jksx};
			return this.jdbcTemplate.queryForList(strSql,paraObjects,FrmWsContent.class);
		}
		else
		{
			strSql+="where jkid like :xtlb and instr(:jksx,jksx)>0  order by jkid";
			HashMap map=new HashMap();
			map.put("jksx",jksx);
			map.put("xtlb",xtlb+"%");
			return this.jdbcTemplate.queryForList(strSql,map,FrmWsContent.class);
		}	
	}
	public List getWsContentList(String jksx) throws Exception{
		String strSql="select * from frm_ws_content where instr(?,jksx)>0 order by jkid";
		Object[] paraObjects=new Object[]{jksx};
		return this.jdbcTemplate.queryForList(strSql,paraObjects,FrmWsContent.class);
	}
	public List getWsContentsByKfwjk(String kfwjk) throws Exception
	{
		String strSql="select * from frm_ws_content where instr(?,jkid)>0 order by jkid";
		Object[] paraObjects=new Object[]{kfwjk};
		return this.jdbcTemplate.queryForList(strSql,paraObjects,FrmWsContent.class);		
	}
	public String getJkmcByJkid(String jkid) throws Exception
	{
		String strSql="select jkmc from frm_ws_content where jkid=?";
		Object[] paraObjects=new Object[]{jkid};
		List list=this.jdbcTemplate.queryForList(strSql,paraObjects,FrmWsContent.class);
		String jkmc="";
		if(list==null||list.size()==0)
		{
			jkmc="Î´¶¨Òå£¨"+jkid+"£©";
		}
		else
		{
			jkmc=((FrmWsContent)list.get(0)).getJkmc();
		}
		return  jkmc;
	}
	public List getWsControlList(String jkid) throws Exception{
		String strSql="";
		if(jkid.equals("")){
			strSql="select * from frm_ws_control";	
			return this.jdbcTemplate.queryForList(strSql,FrmWsControl.class);
		}else{
			strSql="select * from frm_ws_control where instr(kfwjk,?)>0";
			Object[] paraObjects=new Object[]{jkid};
			return this.jdbcTemplate.queryForList(strSql,paraObjects,FrmWsControl.class);
		}
	}
	public List getWsControlList(String xtlb,String azdm,String sft,String fzjg,String dyrjmc) throws Exception{
		HashMap map=new HashMap();
		String strSql="select * from frm_ws_control where dyfzjg like '"+sft+"%'";
		if(!(fzjg==null||fzjg.equals("")||fzjg.toLowerCase().equals("null")))
		{
			   strSql+=" and dyfzjg=:fzjg";
			   map.put("fzjg",fzjg);
		}		
		if(!(xtlb==null||xtlb.equals("")||xtlb.toLowerCase().equals("null")))
		{
			strSql+=" and xtlb=:xtlb";
			map.put("xtlb",xtlb);
		}
		if(!(azdm==null||azdm.equals("")||azdm.toLowerCase().equals("null")))
		{
			strSql+=" and azdm=:azdm";
			map.put("azdm",azdm);
		}		
		if(!(dyrjmc==null||dyrjmc.toLowerCase().equals("null")||dyrjmc.equals("")))
		{
			strSql+=" and dyrjmc='"+dyrjmc+"'";
		}
		strSql+=" order by dyfzjg,dyrjmc";
		return this.jdbcTemplate.queryForList(strSql,map,FrmWsControl.class);
	}
	public List getWsAppFormList(String xtlb,String azdm,String scbj,String sft,String fzjg,String dyrjmc,String dyrjkfdw) throws Exception
	{
		HashMap map=new HashMap();
		String strSql="select * from frm_ws_appform where dyfzjg like '"+sft+"%'";
		if(!(xtlb==null||xtlb.equals("")||xtlb.toLowerCase().equals("null")))
		{
			strSql+=" and xtlb=:xtlb";
			map.put("xtlb",xtlb);
		}
		if(!(azdm==null||azdm.equals("")||azdm.toLowerCase().equals("null")))
		{
			strSql+=" and azdm=:azdm";
			map.put("azdm",azdm);
		}		
		if(!(fzjg==null||fzjg.equals("")||fzjg.toLowerCase().equals("null")))
		{
			strSql+=" and dyfzjg=:fzjg";
			map.put("fzjg",fzjg);
		}
		if(!(scbj==null||scbj.equals("")||scbj.toLowerCase().equals("null")))
		{
			strSql+=" and scbj=:scbj";
			map.put("scbj",scbj);
		}
		if(!(dyrjmc.equals("")))
		{
			strSql+=" and dyrjmc like '"+dyrjmc+"%'";
		}
		if(!(dyrjkfdw.equals("")))
		{
			strSql+=" and dyrjkfdw like '"+dyrjkfdw+"%'";
		}
		strSql+=" order by dyfzjg,dyrjmc";
		return this.jdbcTemplate.queryForList(strSql,map,FrmWsAppForm.class);	
	}
	public List getWsLogList(FrmWsLog wsLog,PageController controller) throws Exception{
		String strSql = "select a.*,b.jkmc from frm_ws_log a,frm_ws_content b where a.jkid=b.jkid";
		String strCondition = "";
		HashMap map=new HashMap();
		if(!wsLog.getJkid().equals("")){
			strCondition += " and a.jkid=:jkid";
			map.put("jkid",wsLog.getJkid());
		}
		if(!wsLog.getJkdysj().equals("")){
			strCondition += " and a.fwsj>=to_date(:jkdysj,'yyyy-mm-dd')";
			map.put("jkdysj",wsLog.getJkdysj());
		}
		if(!wsLog.getJkdysj1().equals("")){
			strCondition += " and a.fwsj<to_date(:jkdysj1,'yyyy-mm-dd') + 1";
			map.put("jkdysj1",wsLog.getJkdysj1());
		}
		if(!strCondition.equals("")){
			strSql += strCondition + " order by fwsj desc";
		}
		return controller.getWarpedList(strSql,map,FrmWsLog.class,jdbcTemplate);
	}
	public List getWsIpxzList(String jkid)throws Exception{
		String strSql="select * from frm_ws_ipxz where jkid=? order by fzjg,ip";
		Object[] paraObjects=new Object[]{jkid};
		return jdbcTemplate.queryForList(strSql,paraObjects,FrmWsIpxz.class);
	}
	public List getWsTotalList(FrmWsLog wsLog) throws Exception
	{
		String strSql="select jkid,sum(fwcs1)fwcs1,sum(fwcs2) fwcs2,sum(fwcs3) fwcs3,sum(fwcs4) fwcs4,sum(fwcs5) fwcs5,";
		strSql+="sum(fwcs6)fwcs6,sum(fwcs7)fwcs7,sum(fwcs8)fwcs8,sum(fwcs9)fwcs9,sum(fwcs10) fwcs10 from frm_ws_stat where 1=1";
		HashMap map=new HashMap();
		if(!wsLog.getJkdysj().equals("")){
			strSql += " and dyrq>=to_date(:jkdysj,'yyyy-mm-dd')";
			map.put("jkdysj",wsLog.getJkdysj());
		}
		if(!wsLog.getJkdysj1().equals("")){
			strSql += " and dyrq<to_date(:jkdysj1,'yyyy-mm-dd') + 1";
			map.put("jkdysj1",wsLog.getJkdysj1());
		}		
		if(!(wsLog.getJkxlh().equals("")))
		{
			strSql+=" and jkxlh=:jkxlh";
			map.put("jkxlh",wsLog.getJkxlh());
		}
		strSql+=" group by jkid";
		return  this.jdbcTemplate.queryForList(strSql,map,FrmWsStat.class);
	}
	public int getWsTotalCount(FrmWsLog wsLog) throws Exception
	{
		String strSql="select count(distinct(dyrq)) from frm_ws_stat where jkid=?";
		if(!wsLog.getJkdysj().equals("")){
			strSql += " and dyrq>=to_date('" + wsLog.getJkdysj() + "','yyyy-mm-dd')";
		}
		if(!wsLog.getJkdysj1().equals("")){
			strSql += " and dyrq<to_date('" + wsLog.getJkdysj1() + "','yyyy-mm-dd') + 1";
		}	
		if(!(wsLog.getJkxlh().equals("")))
		{
			strSql+=" and jkxlh='"+wsLog.getJkxlh()+"'";
		}		
		Object[] paraObjects=new Object[]{wsLog.getJkid()};
		int iCount =  this.jdbcTemplate.queryForInt(strSql,paraObjects);
		return iCount;
	}
	public List getDyrqList(FrmWsLog wsLog) throws Exception
	{
		String strSql="select distinct(to_char(dyrq,'yyyy-MM-dd')) dyrq from frm_ws_stat where jkxlh=?";
		if(!wsLog.getJkdysj().equals("")){
			strSql += " and dyrq>=to_date('" + wsLog.getJkdysj() + "','yyyy-mm-dd')";
		}
		if(!wsLog.getJkdysj1().equals("")){
			strSql += " and dyrq<to_date('" + wsLog.getJkdysj1() + "','yyyy-mm-dd') + 1";
		}	
		if(!(wsLog.getJkid().equals("")))
		{
			strSql+=" and jkid='"+wsLog.getJkid()+"'";
		}	
		Object[] paraObjects=new Object[]{wsLog.getJkxlh(),};
		return  this.jdbcTemplate.queryForList(strSql,paraObjects,FrmWsStat.class);
	}
	public FrmWsStat getFrmWsStat(String jkxlh,String dyrq,String jkid)
	{
		String strSql="select * from frm_ws_stat where jkxlh=?";
		strSql+=" and jkid=?";
		strSql += " and dyrq>=to_date('" + dyrq + "','yyyy-mm-dd')";
		strSql += " and dyrq<to_date('" + dyrq + "','yyyy-mm-dd') + 1";
		Object[] paraObjects=new Object[]{jkxlh,jkid};
		FrmWsStat wsStat=(FrmWsStat)this.jdbcTemplate.queryForSingleObject(strSql,paraObjects,FrmWsStat.class);
		return wsStat;		
	}
}
