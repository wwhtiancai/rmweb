package com.tmri.framework.dao.jdbc;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.tmri.framework.bean.FrmGispara;
import com.tmri.framework.dao.FrmGisparaDao;
import com.tmri.share.frm.dao.jdbc.FrmDaoJdbc;
import com.tmri.share.frm.util.StringUtil;

@Repository
public class FrmGisparaDaoJdbc extends FrmDaoJdbc implements FrmGisparaDao {
    private static Logger logger = LoggerFactory.getLogger(FrmGisparaDaoJdbc.class);

    @SuppressWarnings("unchecked")
    public List<FrmGispara> getGisParaList(FrmGispara bean) throws Exception {
        StringBuffer sql = new StringBuffer(256);

        sql.append(" Select f.*,");
        sql.append(" (Case When f.Jyw=(Frm_Sys_Pkg.Encrypt(f.Gjz || f.Mrz || f.Sjgf ");
        sql.append(" ,f.Gjz)) then '1' else '0' end) as jzjyw ");
        sql.append(" From Frm_Gispara f where sfxs=?");
        sql.append(" Order By f.Xssx ");

        logger.info(sql.toString());
        return jdbcTemplate.queryForList(sql.toString(), new Object[] { bean.getSfxs() },
                FrmGispara.class);
    }

    public FrmGispara getGisParaByPk(String gjz) throws Exception {
        if (!StringUtil.checkBN(gjz)) {
            logger.error("getGisParaByPk is null.");
            return null;
        }
        StringBuffer sql = new StringBuffer(256);

        sql.append(" Select f.*,");
        sql.append(" (Case When f.Jyw=(Frm_Sys_Pkg.Encrypt(f.Gjz || f.Mrz || f.Sjgf ");
        sql.append(" ,f.Gjz)) then '1' else '0' end) as jzjyw ");
        sql.append(" From Frm_Gispara f where f.Gjz = ?");
        sql.append(" Order By f.Xssx desc ");

        logger.info(sql.toString());
        System.out.println("µÿÕº¿‡–Õ£∫"+sql.toString());
        return (FrmGispara) jdbcTemplate.queryForBean(sql.toString(), new Object[] { gjz },
                FrmGispara.class);
    }

}
