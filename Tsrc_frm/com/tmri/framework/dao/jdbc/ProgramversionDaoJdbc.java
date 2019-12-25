package com.tmri.framework.dao.jdbc;

import java.util.List;

import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

import com.tmri.framework.bean.FrmProgramversion;
import com.tmri.framework.dao.ProgramversionDao;
import com.tmri.share.frm.dao.jdbc.FrmDaoJdbc;

@Repository
public class ProgramversionDaoJdbc extends FrmDaoJdbc implements ProgramversionDao {
    private String strSelect=" select v1.xh,v1.mkm,v1.xgsm,v1.zxbb,v1.sxl,v1.bz "
    	+"from FRM_PROGRAM_VERSION v1 ";
    
    //获取list
    public List getProgramversionList() throws Exception{
        String sql=strSelect;
        List queryList=jdbcTemplate.queryForList(sql,FrmProgramversion.class);
        return queryList;
    }
    
    //获取存储过程版本
    public String getPkgversion(String pkgname){
    	String pkgversion="";
    	try{
	        String sql="select "+pkgname+".Get_Ver from dual";
	        pkgversion=jdbcTemplate.queryForSingleObject(sql, String.class).toString();
    	}catch(Exception ex){
    		pkgversion=ex.getMessage();
    	}
        return pkgversion;
    }

}
