package com.tmri.framework.dao.jdbc;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;
import org.springframework.jdbc.support.incrementer.OracleSequenceMaxValueIncrementer;
import org.springframework.stereotype.Repository;

import com.tmri.framework.bean.Desktop;
import com.tmri.framework.bean.DesktopLinks;
import com.tmri.framework.dao.DesktopDao;
import com.tmri.share.frm.util.StringUtil;
import com.tmri.share.ora.FrmJdbcTemplate;
import com.tmri.share.ora.bean.CallableStatementCallbackImpl;
import com.tmri.share.ora.bean.DbResult;
@Repository
public class DesktopDaoJdbc implements DesktopDao{
	@Resource
	FrmJdbcTemplate jdbcTemplate;

    private DataFieldMaxValueIncrementer incrementer;

	public List getDesktops() throws SQLException{		
		String sql="select mkbh,mkmc from frm_desktop_program where mkbh<>'0' order by mkbh";
		return this.jdbcTemplate.queryForList(sql, Desktop.class);
	}
	public List getMyDesktops(String yhdh) throws SQLException{
		String sql="";
		sql+="select a.mkmc,a.lj,a.js,a.html,b.* from "; 
		sql+="(select mkbh,mkmc,lj,js,html from frm_desktop_program) a,";
		sql+="(select mkbh,mkwz,mksx1,mksx2,mksx3,mksx4 from frm_desktop_user where yhdh='"+yhdh+"' and mkbh<>'0' order by mkwz) b ";
		sql+="where a.mkbh=b.mkbh";
		return this.jdbcTemplate.queryForList(sql, Desktop.class);
	}
	public Desktop getDesktop(String mkbh) throws SQLException{
		String sql="select * from frm_desktop_program where mkbh='"+mkbh+"'";
		return (Desktop)jdbcTemplate.queryForBean(sql,Desktop.class);
	}
	public Desktop getMyDesktop(String yhdh,String mkbh) throws SQLException{
		String sql="select * from frm_desktop_user where yhdh='"+yhdh+"' and mkbh='"+mkbh+"'";
		return (Desktop)jdbcTemplate.queryForBean(sql,Desktop.class);
	}

    public List menuRightMaps(String yhdh, String xtlbstr, String cdbhstr) throws Exception {
        StringBuffer sql = new StringBuffer(256);
        sql.append(" select xtlb||'#'||cdbh as cdbh from ");
        sql.append(" (select b.* from (select jsdh from frm_userrole where yhdh = '");
        sql.append(yhdh);
        sql.append("')a, frm_rolemenu b where a.jsdh = b.jsdh) where 1=1 ");
        String[] xtlbs = xtlbstr.split(",");
        String[] cdbhs = cdbhstr.split(",");
        if (StringUtil.checkBN(xtlbstr) && xtlbs != null && xtlbs.length > 0) {
            sql.append(" and ( ");
            for (int i = 0; i < xtlbs.length; i++) {
                String xtlb = xtlbs[i];
                String cdbh = cdbhs[i];
                if (i != 0) {
                    sql.append(" or ");
                }
                sql.append(" (cdbh='").append(cdbh).append("'");
                sql.append(" and xtlb='").append(xtlb).append("')");
            }
            sql.append(" ) ");
        }
        // System.out.println("[sql]" + sql.toString());
        List<Map<String, Object>> result = this.jdbcTemplate.queryForList(sql.toString());
        return result;
    }
	public int saveDesktopUserAttribute(Desktop d) throws SQLException {
		String callString = "{call FRM_DESKTOP_PKG.SaveDesktopUserAttribute(?,?,?,?,?,?,?)}";
		CallableStatementCallbackImpl callBack = new CallableStatementCallbackImpl() {
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException, DataAccessException {
				Desktop modal = (Desktop) getParameterObject();
				if(modal.getYhdh()==null) cstmt.setNull(1,Types.VARCHAR);else cstmt.setString(1,modal.getYhdh());
				if(modal.getMkbh()==null) cstmt.setNull(2,Types.VARCHAR);else cstmt.setString(2,modal.getMkbh());
				if(modal.getMksx1()==null) cstmt.setNull(3,Types.VARCHAR);else cstmt.setString(3,modal.getMksx1());
				if(modal.getMksx2()==null) cstmt.setNull(4,Types.VARCHAR);else cstmt.setString(4,modal.getMksx2());
				if(modal.getMksx3()==null) cstmt.setNull(5,Types.VARCHAR);else cstmt.setString(5,modal.getMksx3());
				if(modal.getMksx4()==null) cstmt.setNull(6,Types.VARCHAR);else cstmt.setString(6,modal.getMksx4());
				cstmt.registerOutParameter(7,Types.NUMERIC);
				cstmt.execute();
				int iResult = cstmt.getInt(7);
				return new Integer(iResult);
			}
		};
		callBack.setParameterObject(d);
		int result = (Integer)jdbcTemplate.execute(callString,callBack);
		return result;
	}
	public int saveDesktopUserPosition(String yhdh,String mkbh,String position) throws SQLException {
		String callString = "{call FRM_DESKTOP_PKG.SaveDesktopUserPosition(?,?,?,?)}";
		CallableStatementCallbackImpl callBack = new CallableStatementCallbackImpl() {
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException, DataAccessException {
				Desktop modal = (Desktop) getParameterObject();
				if(modal.getYhdh()==null) cstmt.setNull(1,Types.VARCHAR);else cstmt.setString(1,modal.getYhdh());
				if(modal.getMkbh()==null) cstmt.setNull(2,Types.VARCHAR);else cstmt.setString(2,modal.getMkbh());
				if(modal.getMkwz()==null) cstmt.setNull(3,Types.VARCHAR);else cstmt.setString(3,modal.getMkwz());
				cstmt.registerOutParameter(4,Types.NUMERIC);
				cstmt.execute();
				int iResult = cstmt.getInt(4);
				return new Integer(iResult);
			}
		};
		Desktop m=new Desktop();
		m.setYhdh(yhdh);
		m.setMkbh(mkbh);
		m.setMkwz(position);
		callBack.setParameterObject(m);
		int result = (Integer)jdbcTemplate.execute(callString,callBack);
		return result;
	}
	public int saveDesktopUser(Desktop d) throws SQLException {
		String callString = "{call FRM_DESKTOP_PKG.SaveDesktopUser(?,?,?,?,?,?,?,?,?)}";
		CallableStatementCallbackImpl callBack = new CallableStatementCallbackImpl() {
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException, DataAccessException {
				Desktop modal = (Desktop) getParameterObject();
				if(modal.getYhdh()==null) cstmt.setNull(1,Types.VARCHAR);else cstmt.setString(1,modal.getYhdh());
				if(modal.getMkbh()==null) cstmt.setNull(2,Types.VARCHAR);else cstmt.setString(2,modal.getMkbh());
				if(modal.getMkwz()==null) cstmt.setNull(3,Types.VARCHAR);else cstmt.setString(3,modal.getMkwz());
				if(modal.getMksx1()==null) cstmt.setNull(4,Types.VARCHAR);else cstmt.setString(4,modal.getMksx1());
				if(modal.getMksx2()==null) cstmt.setNull(5,Types.VARCHAR);else cstmt.setString(5,modal.getMksx2());
				if(modal.getMksx3()==null) cstmt.setNull(6,Types.VARCHAR);else cstmt.setString(6,modal.getMksx3());
				if(modal.getMksx4()==null) cstmt.setNull(7,Types.VARCHAR);else cstmt.setString(7,modal.getMksx4());
				if(modal.getBz()==null) cstmt.setNull(8,Types.VARCHAR);else cstmt.setString(8,modal.getBz());
				cstmt.registerOutParameter(9,Types.NUMERIC);
				cstmt.execute();
				int iResult = cstmt.getInt(9);
				return new Integer(iResult);
			}
		};
		callBack.setParameterObject(d);
		int result = (Integer)jdbcTemplate.execute(callString,callBack);
		return result;
	}
	public int deleteDesktopUserByCondition(String condition) throws SQLException{
		String callString = "{call FRM_DESKTOP_PKG.DeleteDesktopUserByCondition(?,?)}";
		CallableStatementCallbackImpl callBack = new CallableStatementCallbackImpl() {
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException, DataAccessException {
				String condition = (String) getParameterObject();
				if(condition==null) cstmt.setNull(1,Types.VARCHAR);else cstmt.setString(1,condition);
				cstmt.registerOutParameter(2,Types.NUMERIC);
				cstmt.execute();
				int iResult = cstmt.getInt(2);
				return new Integer(iResult);
			}
		};
		callBack.setParameterObject(condition);
		int result = (Integer)jdbcTemplate.execute(callString,callBack);
		return result;
	}
	public List getMyDesktopLinkss(String condition) throws SQLException{
        String sql = "select xh,ljmc,ljdz,bz from frm_desktop_links " + condition
                + " order by xh+1";
		return this.jdbcTemplate.queryForList(sql, DesktopLinks.class);
	}
	public List getMyDesktopDownloads(String condition) throws SQLException{
		String sql="select xh,fl ljfl,mc ljmc,dz ljdz,gxsj,bz from frm_desktop_downloads "+condition+" order by xh";
		return this.jdbcTemplate.queryForList(sql, DesktopLinks.class);
	}
    public DbResult delDesktop(String xh) throws SQLException {
        DbResult result = new DbResult();
        if (!StringUtil.checkBN(xh)) {
            result.setCode(-1);
            result.setMsg("传值有误！");
        }
        String sql = "delete from frm_desktop_links where xh='" + xh + "'";
        this.jdbcTemplate.update(sql);
        result.setCode(1);
        result.setMsg("删除成功！");
        return result;
    }
    public DbResult saveMyDesktopLinks(final List<DesktopLinks> list) throws Exception {
        StringBuffer sql = new StringBuffer(64);
        sql.append(" Insert Into frm_desktop_links ");
        sql.append(" (Xh, Ljmc, Ljdz, Cjjg) ");
        sql.append(" Values (?, ?, ?, ?) ");

        if (incrementer == null) {
            incrementer = new OracleSequenceMaxValueIncrementer(jdbcTemplate.getDataSource(),
                    "seq_desklink");
        }

        jdbcTemplate.batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, incrementer.nextStringValue());
                ps.setString(2, list.get(i).getLjmc());
                ps.setString(3, list.get(i).getLjdz());
                ps.setString(4, list.get(i).getCjjg());
            }

            public int getBatchSize() {
                return list.size();
            }
        });

        DbResult dbResult = new DbResult();
        dbResult.setCode(1);
        dbResult.setMsg("success");
        return dbResult;
    }
}
