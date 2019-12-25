package com.tmri.rm.dao.jdbc;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.tmri.rm.bean.RmLastVersion;
import com.tmri.rm.bean.RmPkgVersion;
import com.tmri.rm.bean.RmVersion;
import com.tmri.rm.bean.RmWebVersion;
import com.tmri.rm.dao.RmVersionDao;
import com.tmri.share.frm.bean.Code;
import com.tmri.share.frm.dao.jdbc.FrmDaoJdbc;
import com.tmri.share.ora.bean.CallableStatementCallbackImpl;

@Repository
public class RmVersionDaoJdbc extends FrmDaoJdbc implements RmVersionDao {

	public List<RmVersion> getAzdmIpGjzList(RmVersion bean) throws Exception {
		String strSql = "select azdm,ip,gjz from RM_VERSION where gjz is not null";
		if (bean!=null)
		{
		    if (bean.getGjz()!=null && bean.getGjz().length()>0)
			    strSql += " and gjz='"+bean.getGjz()+"'";
		    if (bean.getIp()!=null && bean.getIp().length()>0)
			    strSql += " and ip like '%"+bean.getIp()+"%'";
		}
		strSql += " group by azdm,ip,gjz";
		
		List<RmVersion> list = jdbcTemplate.queryForList(strSql,
				RmVersion.class);
		return list;
	}
	
	public RmVersion getRmVersion(String azdm,String ip,String gjz) throws Exception {
		String strSql = "select * from rm_version where rownum<=1 and azdm ='"+azdm+"' and ip ='"+ip+"' and gjz='"+gjz+"' order by jcsj desc";
		List<RmVersion> list = jdbcTemplate.queryForList(strSql,
				RmVersion.class);
		if (list == null || list.size() == 0)
			return null;
		else
			return (RmVersion) list.get(0);
	}
	
	public List<RmLastVersion> getLastVersionList() throws Exception {
		String strSql = "select * from RM_VERSION";
		List<RmLastVersion> list = jdbcTemplate.queryForList(strSql,
				RmLastVersion.class);
		return list;
	}
	
	public RmLastVersion getLastVersion(String gjz) throws Exception {
		String strSql = "select * from RM_VERSION where rownum<=1 and gjz='"+gjz+"'";
		List<RmLastVersion> list = jdbcTemplate.queryForList(strSql,
				RmLastVersion.class);
		if (list == null || list.size() == 0)
			return null;
		else
			return (RmLastVersion) list.get(0);
	}

	public List<RmVersion> getSfList() throws Exception {
		String sql = "Select substr(azdm,1,2) \"SFDM\" from admin_whitelist where xtlb='60' and "
			       + "azdmc not like '%测试%' group by substr(azdm,1,2) order by substr(azdm,1,2)";
		return jdbcTemplate.queryForList(sql, RmVersion.class);
	}

	public RmVersion getSfDetail(String sfdm) throws Exception {
		String sfSql = "select dmsm1 \"SF\" from frm_code where dmlb='0032' and xtlb='00' and dmz = '" + sfdm + "'";
		String azdsSql = "select count(1) \"AZDS\" from admin_whitelist where substr(azdm,1,2)='" 
			           + sfdm + "' and azdmc not like '%测试%'";
		
		String sql = "select "
				   + "a.sf, b.azds "
			       + "from "
			       + "(" + sfSql + ")a,"
			       + "(" + azdsSql + ")b";
		
		return (RmVersion)jdbcTemplate.queryForBean(sql, RmVersion.class);
	}

	public RmVersion getCount(String sfdm) throws Exception {
		
		String callString="{call RM_PKG.Count_Version_By_Prov(?,?,?,?,?,?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				String sfdm=(String)getParameterObject();
				RmVersion result = new RmVersion();
				if(sfdm==null) cstmt.setNull(1, Types.NULL); else cstmt.setString(1,sfdm);
				cstmt.registerOutParameter(2,Types.VARCHAR);
				cstmt.registerOutParameter(3,Types.NUMERIC);
				cstmt.registerOutParameter(4,Types.NUMERIC);
				cstmt.registerOutParameter(5,Types.NUMERIC);
				cstmt.registerOutParameter(6,Types.NUMERIC);
				cstmt.execute();
				result.setSf(cstmt.getString(2));
				result.setAzds(cstmt.getInt(3));
				result.setNewWebCount(cstmt.getInt(4));
				result.setNewPkgCount(cstmt.getInt(5));
				result.setNormalCount(cstmt.getInt(6));
				return result;
			}
		};
		callBack.setParameterObject(sfdm);
		RmVersion rmVersion = (RmVersion)jdbcTemplate.execute(callString, callBack);
		
		return rmVersion;
	}

	public List<RmVersion> getDsList(String sfdm) throws Exception {
		String sql = "select fzjg,azdm from admin_whitelist where substr(azdm,1,2)='" 
			+ sfdm + "' and azdmc not like '%测试%' order by fzjg";
	return jdbcTemplate.queryForList(sql, RmVersion.class);
	}

	public RmVersion getDsCount(String fzjg) throws Exception {
		String callString="{call RM_PKG.Count_Version_By_City(?,?,?,?,?,?,?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				String fzjg=(String)getParameterObject();
				RmVersion result = new RmVersion();
				if(fzjg==null) cstmt.setNull(1, Types.NULL); else cstmt.setString(1,fzjg);
				cstmt.registerOutParameter(2,Types.VARCHAR);
				cstmt.registerOutParameter(3,Types.NUMERIC);
				cstmt.registerOutParameter(4,Types.NUMERIC);
				cstmt.registerOutParameter(5,Types.NUMERIC);
				cstmt.registerOutParameter(6,Types.NUMERIC);
				cstmt.registerOutParameter(7,Types.NUMERIC);
				cstmt.execute();
				result.setDs(cstmt.getString(2));
				result.setNewWebCount(cstmt.getInt(3));
				result.setNewPkgCount(cstmt.getInt(4));
				result.setNormalCount(cstmt.getInt(5));
				result.setWebSc(cstmt.getInt(6));
				result.setPkgSc(cstmt.getInt(7));
				return result;
			}
		};
		callBack.setParameterObject(fzjg);
		RmVersion rmVersion = (RmVersion)jdbcTemplate.execute(callString, callBack);
		
		return rmVersion;
	}

	public List<RmPkgVersion> getPkgList(String fzjg) throws Exception {
		String pkgSql = "select * from rm_version where gjz<>'web'";
		List<RmLastVersion> verList = jdbcTemplate.queryForList(pkgSql, RmLastVersion.class);
		
		String sql = "";
		RmPkgVersion rmPkgVersion = null;
		List<RmPkgVersion> result = new ArrayList<RmPkgVersion>();
		if(null != verList){
			for (RmLastVersion v : verList) {
				sql = "select gjz \"pkg\", version \"cur\", jcsj from rm_version_jg_last "
					+"where gjz='"+v.getGjz()+"' and fzjg='"+fzjg+"' and rownum=1 order by gxsj desc";
				rmPkgVersion = (RmPkgVersion)jdbcTemplate.queryForBean(sql, RmPkgVersion.class);
				if(null != rmPkgVersion){
					rmPkgVersion.setLast(v.getVersion());
				}else {
					rmPkgVersion = new RmPkgVersion();
					rmPkgVersion.setPkg(v.getGjz());
					rmPkgVersion.setLast(v.getVersion());
					rmPkgVersion.setCur("未上传");
					rmPkgVersion.setJcsj("未上传");
				}
				result.add(rmPkgVersion);
			}
		}
		
		return result;
	}

	public List<RmWebVersion> getWebList(String fzjg) throws Exception {
		String ipSql = "select distinct(ip) \"ip\" from rm_version_jg_last where fzjg='" + fzjg + "' and gjz ='web'";
		List<RmWebVersion> ipList = jdbcTemplate.queryForList(ipSql, RmWebVersion.class);
		
		String sql = "";
		RmWebVersion rmWebVersion = null;
		List<RmWebVersion> result = new ArrayList<RmWebVersion>();
		if(null != ipList){
			for (RmWebVersion ip : ipList) {
				sql = "select a.ip,b.version \"last\",a.version \"cur\",jcsj " 
					+ "from(select * from rm_version_jg_last where "
					+ "fzjg='"+fzjg+"' and ip = '"+ip.getIp()+"' and gjz='web' "
					+ "and rownum=1 order by gxsj desc)a,"
					+ "(select * from rm_version where gjz = 'web')b";
				try {
					rmWebVersion = (RmWebVersion)jdbcTemplate.queryForBean(sql, RmWebVersion.class);
				} catch (Exception e) {
					System.out.println("无版本信息 fzjg：" + fzjg);
				}
				
				if(null != rmWebVersion){
					result.add(rmWebVersion);
				}
			}
		}
		return result;
	}


	public List<Code> getSfMz(String sfdm) throws Exception {
		String sql = "select *  from frm_code where xtlb='00' and dmlb='0032' and zt='1' and dmsm2='" + sfdm + "'";
		return jdbcTemplate.queryForList(sql, Code.class);
	}

	public List<RmVersion> getDsJc(String sfdm) throws Exception {
		String sql = " select fzjg from admin_whitelist where fzjg like '" + sfdm + "%' order by fzjg"  ;
		return jdbcTemplate.queryForList(sql, RmVersion.class);
	}

	public List<RmVersion> getSfJc() throws Exception {
		String sql = " select substr(fzjg,1,1) \"SFDM\" from admin_whitelist group by substr(fzjg,1,1) order by substr(fzjg,1,1)";
		return jdbcTemplate.queryForList(sql, RmVersion.class);
	}	
}
