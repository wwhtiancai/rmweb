package com.tmri.framework.dao.jdbc;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tmri.framework.bean.BasLog;
import com.tmri.framework.bean.FrmLoginlog;
import com.tmri.framework.bean.FrmQueryerrlog;
import com.tmri.framework.bean.FrmWsLog;
import com.tmri.framework.bean.Log;
import com.tmri.framework.bean.Userloginfail;
import com.tmri.framework.dao.LogDao;
import com.tmri.share.frm.dao.jdbc.FrmDaoJdbc;
import com.tmri.share.frm.util.PageController;
import com.tmri.share.frm.util.StringUtil;


@Repository
public class LogDaoJdbc extends FrmDaoJdbc implements LogDao {
    private String strSelect=" select * from frm_log ";
    
    //获取list
    public List getLogList(Log obj) throws Exception{
        List queryList  = null;
        String sql=strSelect;
        queryList=jdbcTemplate.queryForList(sql,Log.class);
        return null;
    }
    
    private String getKsrq(String code,String val){
    	String sql="";
    	if(!val.equals("")){
        	sql+=" and "+code+" >= to_date('"+val+"','yyyy-mm-dd')";
        }
    	return sql;
    }
    
    private String getJsrq(String code,String val){
    	String sql="";
    	if(!val.equals("")){
        	sql+=" and "+code+" < to_date('"+val+"','yyyy-mm-dd')+1";
        }
    	return sql;
    }    
    
    private String getCodeval(String code,String val){
    	String sql="";
    	if(!val.equals("")){
        	sql+=" and "+code+" = '"+val+"'";
        }
    	return sql;    	
    }
    private String getGlbmval(String code,String val){
    	String sql="";
    	if(!val.equals("")){
        	sql+=" and "+code+"  like '"+val+"'";
        }
    	return sql;    	
    }
    private String getIp(String code,String val){
    	String sql="";
    	if(!val.equals("")){
        	sql+=" and "+code+" = '"+val+"'";
        }
    	return sql;
    }
    
    //获取分页list
    public List getLogList(Log obj, PageController controller)throws Exception{
        List querylist  = new ArrayList();
        String rzlx=obj.getRzlx();
        
        if(rzlx.equals("1005")){
        	//登录失败日志
        	querylist=getLoginfailList(obj,controller);    
        }else if(rzlx.equals("1001")){
        	//登录信息
        	querylist=getLoginList(obj,controller);  
        }else if(rzlx.equals("0009")){
        	//操作日志
        	querylist=getFrmlogList(obj,controller); 
        }else if(rzlx.equals("0008")){
        	//高频访问监控表
        	querylist=getQueryerrList(obj,controller); 
        }else if(rzlx.equals("1004")){
        	//接口访问日志
        	querylist=getWslogList(obj,controller); 
        }else if(rzlx.equals("0007")){
        	//警务日志
        	querylist=getBaslogList(obj,controller);     
        }
        return querylist;
    }
      
    private List getBaslogList(Log obj, PageController controller){
    	
    	List queryList=null;
    	String sql="select v1.bmdm,to_char(v1.clsj,'yyyy-mm-dd hh24:mi:ss') clsj,v1.czlx,v1.cznr,"
    		+"v1.jbr,v1.sm,v1.bz,v1.ip from bas_log v1 where 1=1";
    	sql+=getKsrq("clsj",obj.getKsrq());
    	sql+=getJsrq("clsj",obj.getJsrq());       	
    	sql+=getGlbmval("bmdm",obj.getGlbm());
    	sql+=getCodeval("jbr",obj.getYhdh());
    	sql+=getIp("ip",obj.getIp());
    	
    	if (StringUtil.checkBN(obj.getCznr())) {
            sql += " and v1.cznr like '%"+obj.getCznr()+"%'";
        } 
    	
    	sql+=" order by clsj";
        queryList=controller.getWarpedList(sql,BasLog.class,jdbcTemplate);
        return queryList;
    }     
    
    private List getWslogList(Log obj, PageController controller){
    	List queryList=null;
    	String sql="select to_char(v1.fwsj,'yyyy-mm-dd hh24:mi:ss') fwsj,v1.jkxlh,v1.jkid,v1.ip,"
    		+"v1.fwbj,v1.fhxx from frm_ws_log v1 where 1=1";
    	sql+=getKsrq("fwsj",obj.getKsrq());
    	sql+=getJsrq("fwsj",obj.getJsrq()); 
    	if (StringUtil.checkBN(obj.getFwbj())) {
            sql += " and v1.fwbj = '"+obj.getFwbj()+"'";
        }
    	sql+=" order by fwsj";
        queryList=controller.getWarpedList(sql,FrmWsLog.class,jdbcTemplate);
        return queryList;
    } 
    
    /**
    private List getVehlogList(Log obj, PageController controller){
    	List queryList=null;
    	String sql="select * from veh_log v1 where 1=1";
    	sql+=getCodeval("jbr",obj.getYhdh());
    	sql+=getGlbmval("glbm",obj.getGlbm());
    	sql+=getKsrq("clrq",obj.getKsrq());
    	sql+=getJsrq("clrq",obj.getJsrq());   
    	sql+=getIp("ip",obj.getIp());
    	//
        if (StringUtil.checkBN(obj.getYwlx())) {
            sql += " and v1.ywlx = '"+obj.getYwlx()+"'";
        }  
        if (StringUtil.checkBN(obj.getYwyy())) {
            sql += " and Veh_Comm_Pkg.Getywmc(v1.ywlx,v1.ywyy) like '%"+obj.getYwyy()+"%'";
        } 
        if (StringUtil.checkBN(obj.getLsh())) {
            sql += " and v1.lsh = '"+obj.getLsh()+"'";
        } 
        if (StringUtil.checkBN(obj.getHpzl())) {
            sql += " and v1.hpzl = '"+obj.getHpzl()+"'";
        } 
        if (StringUtil.checkBN(obj.getHphm())) {
            sql += " and v1.hphm = '"+obj.getHphm()+"'";
        }
    	sql+=" order by clrq";
        queryList=controller.getWarpedList(sql,Veh_log.class,jdbcTemplate);
        return queryList;
    }
    
    private List getDrvlogList(Log obj, PageController controller){
    	List queryList=null;
    	String sql="select * from drv_log v1 where 1=1";
    	sql+=getCodeval("jbr",obj.getYhdh());
    	sql+=getGlbmval("glbm",obj.getGlbm());
    	sql+=getKsrq("clrq",obj.getKsrq());
    	sql+=getJsrq("clrq",obj.getJsrq());   
    	sql+=getIp("ip",obj.getIp());
    	
        if (StringUtil.checkBN(obj.getYwlx())) {
            sql += " and v1.ywlx = '"+obj.getYwlx()+"'";
        }  
        
        if (StringUtil.checkBN(obj.getYwyy())) {
            sql += " and Drv_Util.getywyy(v1.ywlx,v1.ywyy) like  '%"+obj.getYwyy()+"%'";
        }         
        if (StringUtil.checkBN(obj.getYwgw())) {
            sql += " and v1.ywgw = '"+obj.getYwgw()+"'";
        }
        if (StringUtil.checkBN(obj.getLsh())) {
            sql += " and v1.lsh = '"+obj.getLsh()+"'";
        }
        if (StringUtil.checkBN(obj.getSfzmhm())) {
            sql += " and v1.sfzmhm = '"+obj.getSfzmhm()+"'";
        }        
    	sql+=" order by clrq";
        queryList=controller.getWarpedList(sql,DrvLog.class,jdbcTemplate);
        return queryList;
    }   */ 
    
    
    private List getQueryerrList(Log obj, PageController controller){
    	List queryList=null;
    	String sql="select v1.yhdh,to_char(v1.rq,'yyyy-mm-dd hh24:mi:ss')rq,v1.fwcs,v1.ipdz,v1.gnbh,v2.glbm"
    		+ " from frm_queryerr_log v1,frm_sysuser v2 "
    		+ " where v1.yhdh=v2.yhdh";
    	sql+=getCodeval("v1.yhdh",obj.getYhdh());
    	sql+=getGlbmval("v2.glbm",obj.getGlbm());
    	sql+=getKsrq("v1.rq",obj.getKsrq());
    	sql+=getJsrq("v1.rq",obj.getJsrq());   
    	
    	if (StringUtil.checkBN(obj.getFwcs())) {
            sql += " and v1.fwcs > "+obj.getFwcs();
        }
    	sql+=" order by v1.rq";
        queryList=controller.getWarpedList(sql,FrmQueryerrlog.class,jdbcTemplate);
        return queryList;
    }     
    
    
    private List getFrmlogList(Log obj, PageController controller){
    	List queryList=null;
    	String sql="select v1.glbm,v1.yhdh,to_char(v1.czsj,'yyyy-mm-dd hh24:mi:ss') czsj,"
    		+"v1.czlx,v1.czgn,v1.cznr,v1.ip,v1.bz from frm_log v1 where 1=1";
    	sql+=getCodeval("yhdh",obj.getYhdh());
    	sql+=getGlbmval("glbm",obj.getGlbm());
    	sql+=getKsrq("czsj",obj.getKsrq());
    	sql+=getJsrq("czsj",obj.getJsrq());   
    	sql+=getIp("ip",obj.getIp());
    	
    	if (StringUtil.checkBN(obj.getCznr())) {
            sql += " and v1.cznr like '%"+obj.getCznr()+"%'";
        }
    	
    	sql+=" order by czsj";
        queryList=controller.getWarpedList(sql,Log.class,jdbcTemplate);
        return queryList;
    }
    
    private List getLoginList(Log obj, PageController controller){
    	List queryList=null;
    	String sql="select v1.yhdh,to_char(v1.dlsj,'yyyy-mm-dd hh24:mi:ss')dlsj,v1.ip,v1.dlms,v2.glbm " 
    		+ " from frm_login_log v1,frm_sysuser v2 where v1.yhdh=v2.yhdh";
    	sql+=getCodeval("v1.yhdh",obj.getYhdh());
    	sql+=getGlbmval("v2.glbm",obj.getGlbm());
    	sql+=getKsrq("v1.dlsj",obj.getKsrq());
    	sql+=getJsrq("v1.dlsj",obj.getJsrq());  
    	
    	if (StringUtil.checkBN(obj.getDlms())) {
            sql += " and v1.dlms = '"+obj.getDlms()+"'";
        }
    	
    	sql+=" order by v1.dlsj";
        queryList=controller.getWarpedList(sql,FrmLoginlog.class,jdbcTemplate);
        return queryList;
    }    
    
    private List getLoginfailList(Log obj, PageController controller){
    	List queryList=null;
    	String sql="select v1.yhdh,to_char(v1.dlsj,'yyyy-mm-dd hh24:mi:ss') dlsj,"
    		+"v1.ipdz,v1.yhmm,v1.bz from frm_userloginfail v1 where 1=1";
    	sql+=getCodeval("yhdh",obj.getYhdh());
    	sql+=getKsrq("dlsj",obj.getKsrq());
    	sql+=getJsrq("dlsj",obj.getJsrq());   
    	
    	if (StringUtil.checkBN(obj.getBz())) {
            sql += " and v1.bz like '%"+obj.getBz()+"%'";
        }
    	
    	sql+=" order by dlsj";
        queryList=controller.getWarpedList(sql,Userloginfail.class,jdbcTemplate);
        return queryList;
    }    
    
    //获取bean
    //?
    public Log getLog(Log obj)throws Exception{
        String sql=strSelect+ "where glbm=? and dmlb=? and dmz=?";
//        Object[] paraObjects=new Object[]{obj.getGlbm(),obj.getDmlb(),obj.getDmz()};
//        Log log=(Log)jdbcTemplate.queryForBean(sql,paraObjects,Log.class);
        return null;
    }
    
    //获取高频访问列表
    public List getQueryerrList(Log obj,String gpfwfz)throws Exception{
    	List queryList=null;
    	String sql="select v1.yhdh,to_char(v1.rq,'yyyy-mm-dd hh24:mi:ss') rq,v1.fwcs,v1.ipdz,v1.gnbh "
    		+ " from frm_queryerr_log v1 where fwcs>="+gpfwfz+" ";
    	sql+=getKsrq("v1.rq",obj.getKsrq());
    	sql+=getJsrq("v1.rq",obj.getJsrq());  
    	sql+=" order by v1.yhdh,v1.rq";
        queryList=jdbcTemplate.queryForList(sql, FrmQueryerrlog.class);	
        return queryList;
    }
    
    public String getJkmc(String jkid) throws Exception{
    	String sql="select jkmc from frm_ws_content where jkid='"+jkid+"'";
    	String result=(String)jdbcTemplate.queryForObject(sql, String.class).toString();
    	return result;
    }
    
    public FrmLoginlog getUserLogin(String yhdh) throws Exception{
    	FrmLoginlog log=new FrmLoginlog();
    	int c=0;
    	String sql="select * from (select * from frm_login_log where yhdh='"+yhdh+"' order by dlsj desc) where rownum<=2";
    	List list=jdbcTemplate.queryForList(sql,FrmLoginlog.class);
    	if (list==null&&list.size()>0){
    		log.setDlsj("");
    		log.setIp("");
    		log.setDlms("");
    		log.setYhdh("0");
    		log.setGlbm("0");
    	}else{
    		for(Iterator it=list.iterator();it.hasNext();){
      		log=(FrmLoginlog)it.next();
      	}
    		log.setDlsj(log.getDlsj().substring(0,19));
      	sql="select count(*) c from frm_login_log where yhdh='"+yhdh+"' and dlsj>=to_date(to_char(sysdate-1,'yyyymmdd')||'000000','yyyymmddhh24miss') and dlsj<=to_date(to_char(sysdate-1,'yyyymmdd')||'235959','yyyymmddhh24miss')";
      	c=jdbcTemplate.queryForInt(sql);
      	log.setYhdh(String.valueOf(c));
      	sql="select count(*) c from frm_login_log where yhdh='"+yhdh+"'";
      	c=jdbcTemplate.queryForInt(sql);
      	log.setGlbm(String.valueOf(c));
    	}
    	return log;
    }
    
    
    public List getBaslogtrackList(Log obj){
    	List queryList=null;
    	String sql="select v1.bmdm,to_char(v1.clsj,'yyyy-mm-dd hh24:mi:ss') clsj,v1.czlx,v1.cznr,"
    		+"v1.jbr,v1.sm,v1.bz,v1.ip from bas_log v1 where v1.czlx in ('R941','R942','R943','R944')";
    	sql += getKsrq("clsj",obj.getKsrq());
    	sql += getJsrq("clsj",obj.getJsrq());       	
    	sql += getGlbmval("bmdm",obj.getGlbm());
    	sql += getCodeval("jbr",obj.getYhdh());
    	sql += getIp("ip",obj.getIp());
    	sql += " and v1.cznr like '%管理部门由%'";
    	sql += " and v1.cznr like '%"+obj.getYhdh()+"%'";
    	sql+=" order by clsj";
    	
        queryList=jdbcTemplate.queryForList(sql, BasLog.class);
        return queryList;
    }      
    
    
    //操作日志
    public List getFrmlogtrackList(Log obj,PageController controller){
    	List queryList=null;
    	String sql="select v1.glbm,v1.yhdh,to_char(v1.czsj,'yyyy-mm-dd hh24:mi:ss') czsj,"
    		+"v1.czlx,v1.czgn,v1.cznr,v1.ip,v1.bz from frm_log v1 where 1=1 ";
    	sql+=getGlbmval("glbm",obj.getGlbm());
    	sql+=getKsrq("czsj",obj.getKsrq());
    	sql+=getJsrq("czsj",obj.getJsrq());   
    	sql+=getIp("ip",obj.getIp());
    	
    	if(obj.getIsxls().equals("1")){
    		sql += " and v1.czlx='K012'";
	    	sql += " and v1.cznr like '%"+obj.getYhdh()+"%'";
	    	sql += " and (v1.cznr like '%用户角色%' or v1.cznr like '%用户权限%')";
    	}else if(obj.getIsxls().equals("2")){
    		sql += " and v1.czlx='K012'";
    		sql+=getCodeval("v1.yhdh",obj.getYhdh());
    		sql += " and (v1.cznr like '%新增用户%' or v1.cznr like '%修改用户%' or v1.cznr like '%删除用户%')";
    	}else if(obj.getIsxls().equals("3")){
    		sql += " and v1.czlx='K012'";
    		sql+=getCodeval("v1.yhdh",obj.getYhdh());
	    	sql += " and (v1.cznr like '%用户角色%' or v1.cznr like '%用户权限%')";
    	}else if(obj.getIsxls().equals("4")){
    		sql += " and v1.czlx='K011'";
    		sql+=getCodeval("v1.yhdh",obj.getYhdh());
    		sql += " and (v1.cznr like '%新增角色%' or v1.cznr like '%修改角色%' or v1.cznr like '%删除角色%')";
    	}else if(obj.getIsxls().equals("5")){
    		sql += " and v1.czlx='K011'";
    		sql += " and v1.cznr like '%角色代号:"+obj.getJsdh()+"%'";
    	}else if(obj.getIsxls().equals("6")||obj.getIsxls().equals("7")){
    		//通用查询
    		//管理部门？？
    		//
    		sql += " and v1.czlx in('K011','K012')";
        	sql+=getCodeval("v1.yhdh",obj.getYhdh());
        	//
        	if(!obj.getCznr().equals("")){
        		sql += " and v1.cznr like '%"+obj.getCznr()+"%'";
        	}
    	}
    	
    	sql+=" order by czsj";
        queryList=controller.getWarpedList(sql,Log.class,jdbcTemplate);
        return queryList;
    }    

}
