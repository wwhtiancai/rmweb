package com.tmri.framework.dao.jdbc;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.tmri.framework.dao.CodetypeDao;
import com.tmri.share.frm.util.PageController;
import com.tmri.share.frm.bean.Codetype;
import com.tmri.share.frm.dao.jdbc.FrmDaoJdbc;
import com.tmri.share.ora.FrmJdbcTemplate;
@Repository
public class CodetypeDaoJdbc extends FrmDaoJdbc implements CodetypeDao{

	private String transNewDmlb(String xtlb,String dmlb){
		String result=dmlb;
		int len=dmlb.length();
		for(int i=0;i<4-len;i++){
			result="0"+result;
		}

		return result;
	}

	public List getCodetypes(Codetype codetype) throws SQLException{

		String tmpSql="";
		HashMap map=new HashMap();

		if(codetype.getDmlb()!=null&&!codetype.getDmlb().equals("")){
			tmpSql=" where dmlb like :dmlb";
			map.put("dmlb","%"+codetype.getDmlb()+"%");
		}

		if(codetype.getLbsm()!=null&&!codetype.getLbsm().equals("")){
			if(tmpSql!=""){
				tmpSql=tmpSql+" and lbsm like :lbsm";
			}else{
				tmpSql=" where lbsm like :lbsm";
			}
			map.put("lbsm","%"+codetype.getLbsm()+"%");
		}

		tmpSql="select * from frm_codetype "+tmpSql+" order by xtlb,to_number(dmlb)";
		List list=jdbcTemplate.queryForList(tmpSql,map,Codetype.class);
		return list;
	}

	public List getCodetypesByPageSize(Codetype codetype,PageController controller) throws SQLException{
		String tmpSql="";
		HashMap map=new HashMap();
		if(codetype.getXtlb()!=null&&!codetype.getXtlb().equals("")){
			tmpSql=" where xtlb = :xtlb";
			map.put("xtlb",codetype.getXtlb());
		}
		if(codetype.getDmlb()!=null&&!codetype.getDmlb().equals("")){
			if(tmpSql!="")
				tmpSql=tmpSql+" and dmlb = :dmlb";
			else tmpSql=" where dmlb = :dmlb";
			map.put("dmlb",codetype.getDmlb());
		}
		if(codetype.getLbsm()!=null&&!codetype.getLbsm().equals("")){
			if(tmpSql!=""){
				tmpSql=tmpSql+" and lbsm like :lbsm";
			}else{
				tmpSql=" where lbsm like :lbsm";
			}
			map.put("lbsm","%"+codetype.getLbsm()+"%");
		}
		if(codetype.getDmlx()!=null&&!codetype.getDmlx().equals("")){
			if(tmpSql!="")
				tmpSql=tmpSql+" and dmlx = :dmlx";
			else tmpSql=" where dmlx = :dmlx";
			map.put("dmlx",codetype.getDmlx());
		}
		if(codetype.getLbsx()!=null&&!codetype.getLbsx().equals("")){
			if(tmpSql!="")
				tmpSql=tmpSql+" and lbsx = :lbsx";
			else tmpSql=" where lbsx = :lbsx";
			map.put("lbsx",codetype.getLbsx());
		}
		tmpSql="select * from frm_codetype "+tmpSql+" order by dmsx,to_number(dmlb)";
		return controller.getWarpedList(tmpSql,map,Codetype.class,jdbcTemplate);
	}

}
