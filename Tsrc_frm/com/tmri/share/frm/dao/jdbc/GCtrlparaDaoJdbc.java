package com.tmri.share.frm.dao.jdbc;

import org.springframework.stereotype.Repository;

import com.tmri.share.frm.bean.FrmCtrlpara;
import com.tmri.share.frm.dao.GCtrlparaDao;

@Repository
public class GCtrlparaDaoJdbc extends FrmDaoJdbc implements GCtrlparaDao {
	public FrmCtrlpara getCtrlpara(String azdm,String gjz) throws Exception{
		String tmpSql="";
		tmpSql="select azdm,gjz,csmc,csz,gxsj,sxh,"
				+ "Frm_sys_pkg.Getfrm_Syspara_Zt(azdm||gjz||csz,jyw) jyw "
				+" from frm_ctrlpara where azdm='"+azdm+"' and gjz='"+gjz+"'";
		return (FrmCtrlpara)jdbcTemplate.queryForBean(tmpSql, FrmCtrlpara.class);
	}
}
