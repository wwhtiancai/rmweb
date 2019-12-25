package com.tmri.share.frm.dao.jdbc;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tmri.share.cache.bean.SystemCache;
import com.tmri.share.frm.bean.BasAlldept;
import com.tmri.share.frm.bean.BasAllxzqh;
import com.tmri.share.frm.bean.Code;
import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.bean.FrmXzqhLocal;
import com.tmri.share.frm.bean.Machine;
import com.tmri.share.frm.dao.GBasDao;
import com.tmri.share.frm.util.Common;
import com.tmri.share.frm.util.Constants;
import com.tmri.share.frm.util.StringUtil;

@Repository
public class GBasDaoJdbc extends FrmDaoJdbc implements GBasDao {
	
	///本地行政区划
	public FrmXzqhLocal getFrmLocalxzqh(String xzqh) throws Exception {
		FrmXzqhLocal result=null;
        String sql = "select v.* from FRM_XZQH_LOCAL v where 1=1 ";
		
		HashMap<String, String> map = new HashMap<String, String>();
		if (StringUtil.checkBN(xzqh)) {
            sql += " and v.xzqh=:xzqh";
			map.put("xzqh", xzqh);
		}
		
		try {
			List<FrmXzqhLocal> list = jdbcTemplate.queryForList(sql, map, FrmXzqhLocal.class);
			if(list!=null&&list.size()>0){
				result=(FrmXzqhLocal)list.get(0);
			}
		} catch (Exception e) {
			result = new FrmXzqhLocal();
			result.setQhmc(xzqh);
		}
		return result;
	}
	
	//获取全部本地行政区划
	public List<FrmXzqhLocal> getFrmLocalxzqhList() throws Exception {
        String sql = "select v.* from FRM_XZQH_LOCAL v order by v.xzqh";
		List<FrmXzqhLocal> list = jdbcTemplate.queryForList(sql, FrmXzqhLocal.class);
		return list;
	}	

    // 获取部门对应的实际行政区划
    public String getSjxzqhs(String xzqh) throws Exception {
        String strSql = "select sjxzqh from frm_xzqh_local where xzqh=?";
        try {
        return jdbcTemplate.queryForObject(strSql, new Object[] { xzqh }, String.class);
        } catch (Exception e) {
            return null;
        }
    }
	////////////////////////////管理部门信息//////////////////////////////
//	/**
//	 * 获取单个管理部门
//	 * @param glbm
//	 * @return
//	 */
	public BasAlldept getBasAlldept(String glbm) throws Exception {
		BasAlldept basAlldept = null;
		String key = "bas_all_dept:" + glbm;
		Hashtable tab = (Hashtable) SystemCache.getInstance().getValue(Constants.MEM_ALL_DEPARTMENT);		
		if(tab!=null){
			basAlldept = (BasAlldept) (tab.get(key));
		}else{
			tab = (Hashtable) SystemCache.getInstance().getValue(Constants.MEM_ALL_DEPARTMENT);		
		}
		if(basAlldept==null){
			String strSql = "select * from bas_all_dept where jlzt='1' and glbm=:glbm";
			HashMap map = new HashMap();
			map.put("glbm",glbm);
			basAlldept = (BasAlldept)this.jdbcTemplate.queryForSingleObject(strSql,map,BasAlldept.class);			
			if(basAlldept!=null && tab!=null){
				tab.put(key,basAlldept);
			}
		}
		// 如果还是获取不到。取frm_department里面的数据
		if (basAlldept == null){
			Department dept = this.getDepartment(glbm);
			if (dept != null){
				basAlldept = new BasAlldept();
				basAlldept.setBmjb(dept.getBmjb());
				basAlldept.setGlbm(dept.getGlbm());
				basAlldept.setBmjc(dept.getBmmc());
				basAlldept.setBmmc(dept.getBmmc());
				basAlldept.setBmqc(dept.getBmqc());
				basAlldept.setFzjg(dept.getFzjg());
				basAlldept.setSjbm(dept.getSjbm());
				basAlldept.setXzqh(dept.getGlxzqh());
			}
		}
		return basAlldept;
	}
	
	public Department getDepartment(String glbm) {
		Department department = null;
		String key = "department:" + glbm;
		//System.out.println(" --------------- "+key);
		Hashtable tab = (Hashtable) SystemCache.getInstance().getValue(Constants.MEM_DEPARTMENT);		
		if(tab!=null){
			department = (Department) (tab.get(key));
		}else{
			tab = (Hashtable) SystemCache.getInstance().getValue(Constants.MEM_DEPARTMENT);		
		}
		if(department==null){
			String strSql = "select * from frm_department where glbm=:glbm";
			HashMap map = new HashMap();
			map.put("glbm",glbm);
			department = (Department)this.jdbcTemplate.queryForSingleObject(strSql,map,Department.class);			
			if(department!=null && tab!=null){
				tab.put(key,department);
			}
		}
		return department;
	}
	
    //处理直辖市发证机关
    public BasAlldept getBasAlldeptByFzjg(String fzjg) throws Exception {
		if(Common.isZxsFzjg(fzjg)){
			fzjg=fzjg.substring(0,1)+"A";
		}
		
		BasAlldept result=null;
		String sql = "select v1.* from bas_all_dept v1 " +
				" where v1.glbm in ( " +
				" select min(v2.glbm) from bas_all_dept v2 where v2.jlzt='1' and ( v2.fzjg=:fzjg or v2.zfjg=:fzjg) " +
				" )";
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("fzjg", fzjg);
		
		List<BasAlldept> list = jdbcTemplate.queryForList(sql, map, BasAlldept.class);
		if(list!=null&&list.size()>0){
			result=(BasAlldept)list.get(0);
		}
		return result;
	}
	
	public BasAlldept getBasAlldeptByXzqh(String xzqh) throws Exception {
		BasAlldept result=null;
		String sql = "select v1.* from bas_all_dept v1 " +
				" where v1.glbm in ( " +
				" select min(v2.glbm) from bas_all_dept v2 where v2.jlzt='1' and v2.xzqh=:xzqh " +
				" )";
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("xzqh", xzqh);
		
		List<BasAlldept> list = jdbcTemplate.queryForList(sql, map, BasAlldept.class);
		if(list!=null&&list.size()>0){
			result=(BasAlldept)list.get(0);
		}
		return result;
	}	
	
	public List<Machine> getBasAlldeptList(String glbmHead) throws Exception {
		String sql = " select v1.fzjg,v1.bmjc  azdmc " +
				" from bas_all_dept v1 " +
				" 	where v1.glbm in ( " +
				" 	select min(v.glbm) from bas_all_dept v " +
				" 	where v.jlzt='1' and v.glbm like '" + glbmHead + "%'" +
				"     and v.fzjg not like '%ZD%' " +
				" 	group by v.fzjg " +
				" ) " +
				" order by v1.glbm ";
		return this.jdbcTemplate.queryForList(sql, Machine.class);
	}
	
	
//	public List<Code> getRemoteCity(String fzjg) throws Exception {
//		String fzjgHead = fzjg.substring(0, 1);
//        String sql = " select v.fzjg dmz,v.bmjc dmsm2 " + 
//        		" from bas_all_dept v " +
//                " where v.jlzt='1' and v.bmjb='3' and v.fzjg like '" + fzjgHead + "%'";
//				
//		if (!fzjgHead.equals("京") && !fzjgHead.equals("沪") && !fzjgHead.equals("渝") && !fzjgHead.equals("津")) {
//            sql += " and v.fzjg != '" + fzjg + "'";
//		} else {
//			sql += " and v.fzjg = '" + fzjg + "'";
//		}				
//        sql += "  order by v.glbm ";
//
//		return this.jdbcTemplate.queryForList(sql, Code.class);
//	}
	
    public List<BasAlldept> getBasDeptList() throws Exception {
        String sql = "select v.* from bas_all_dept v where v.jlzt='1' ";

        List<BasAlldept> list = jdbcTemplate.queryForList(sql, BasAlldept.class);
        return list;
    }
    
	public List<BasAlldept> getBasAlldeptListBySft(String sft) throws Exception {
        String sql = " select v.* from bas_all_dept v "
                + " 	where v.jlzt='1' and v.bmjb='3' and v.xzqh like '" + sft + "__00'"
                + "     order by v.glbm ";
		List<BasAlldept> list = this.jdbcTemplate.queryForList(sql, BasAlldept.class);
		return list;
	}
	
	public List<Department> getStatOrg(Department dept, boolean includeSelf)
			throws Exception {
		String qc = "";
		if ("1".equals(dept.getBmjb())) {
			// 公安部
			qc = "a.glbm like '__0000000000' and a.bmjb='2'";
		} else if ("2".equals(dept.getBmjb())) {
			// 总队
			if (dept.getGlbm().endsWith("10")) {
				// 高速
				qc = "a.glbm like '" + dept.getGlbm().substring(0, 2)
						+ "______0010' and a.bmjb='3'";
			} else {
				qc = "a.glbm like '" + dept.getGlbm().substring(0, 2)
						+ "______00_0' and a.bmjb='3'";
			}
		} else if ("3".equals(dept.getBmjb())) {
			// 支队
			if (dept.getGlbm().endsWith("10")) {
				// 高速
				qc = "a.glbm like '" + dept.getGlbm().substring(0, 4)
						+ "____0010' and a.bmjb='4'";
			} else {
				qc = "a.glbm like '" + dept.getGlbm().substring(0, 4)
						+ "____00_0' and a.bmjb='4'";
			}
		} else if ("4".equals(dept.getBmjb())) {
			// 大队
			qc = "a.glbm like '" + dept.getGlbm().substring(0, 8)
					+ "%' ";
			if(!includeSelf){
			    qc+=" and a.bmjb='5' ";
			}
		} else {
			qc = "a.glbm like '" + dept.getGlbm().substring(0, 10) + "%'";
		}
		if (includeSelf) {
            qc = "(" + qc + " or a.glbm='" + dept.getGlbm() + "') and a.jlzt = '1'";
        } else {
            qc = "(" + qc + ") and a.glbm<>'" + dept.getGlbm() + "' and a.jlzt = '1'";
		}
        // Bas_Department-Frm_Department_Std
        String sql = "select a.glbm, nvl(b.bmjc, a.bmmc) bmmc, a.bmjb, a.sjbm from frm_department a ,bas_all_dept b " +
        		"where a.glbm = b.glbm(+) and " + qc + " order by a.glbm";
		System.out.println(sql);
		List<Department> list = this.jdbcTemplate.queryForList(sql,	Department.class);
		return list;
	}
	
	public List<Department> getStatOrg(Department dept) throws Exception {
		String qc = "";
		if ("1".equals(dept.getBmjb())) {
			// 公安部
			//qc = "a.glbm like '__0000000000' and a.bmjb='2'";
		} else if ("2".equals(dept.getBmjb())) {
			// 总队
			if (dept.getGlbm().endsWith("10")) {
				// 高速
				qc = "a.glbm like '" + dept.getGlbm().substring(0, 2)
						+ "______0010' and a.bmjb='3'";
			} else {
				qc = "a.glbm like '" + dept.getGlbm().substring(0, 2)
						+ "______00_0' and a.bmjb='3'";
			}
		} else if ("3".equals(dept.getBmjb())) {
			qc = "a.glbm = '" + dept.getGlbm() + "' and a.bmjb = '3'";
			// 支队
//			if (dept.getGlbm().endsWith("10")) {
//				// 高速
//				qc = "a.glbm like '" + dept.getGlbm().substring(0, 4)
//						+ "____0010' and a.bmjb='4'";
//			} else {
//				qc = "a.glbm like '" + dept.getGlbm().substring(0, 4)
//						+ "____00_0' and a.bmjb='4'";
//			}
		} else if ("4".equals(dept.getBmjb())) {
			// 大队
//			qc = "a.glbm like '" + dept.getGlbm().substring(0, 8)
//					+ "%' and a.bmjb='5'";
		} else {
//			qc = "a.glbm like '" + dept.getGlbm().substring(0, 10) + "%'";
		}
        qc = "(" + qc + ") and a.jlzt = '1' ";
        String sql = "select a.glbm, a.bmjc bmmc, a.bmjb, a.sjbm from bas_all_dept a " +
        		"where " + qc + " order by a.glbm";
//		System.out.println(sql);
		List<Department> list = this.jdbcTemplate.queryForList(sql, Department.class);
		return list;
		
	}	
	
	
	
	////////////////////////全部行政区划信息/////////////////////////////
	public BasAllxzqh getBasAllxzqh(String xzqh) throws Exception {
		BasAllxzqh basAllxzqh = null;
		try {
			String key = "bas_all_xzqh:" + xzqh;
			Hashtable tab = (Hashtable) SystemCache.getInstance().getValue(Constants.MEM_ALL_XZQH);		
			if(tab!=null){
				basAllxzqh = (BasAllxzqh) (tab.get(key));
			}else{
				tab = (Hashtable) SystemCache.getInstance().getValue(Constants.MEM_ALL_XZQH);		
			}
			if(basAllxzqh==null){
				String strSql = "select * from bas_all_xzqh where jlzt='1' and xzqh=:xzqh";
				HashMap map = new HashMap();
				map.put("xzqh",xzqh);
				basAllxzqh = (BasAllxzqh)this.jdbcTemplate.queryForSingleObject(strSql,map,BasAllxzqh.class);			
				if(basAllxzqh!=null && tab!=null){
					tab.put(key,basAllxzqh);
				}
			}
		} catch (Exception e) {
		}
		return basAllxzqh;
	}	


    
    public List<BasAllxzqh> getBasAllxzqhList() throws Exception {
        String sql = "select v.* from bas_all_xzqh v where v.jlzt='1' ";

        List<BasAllxzqh> list = jdbcTemplate.queryForList(sql, BasAllxzqh.class);
        return list;
    }   
    
    public List<Code> getBasAllXzqhCodeList() throws Exception {
        String sql = "select v.xzqh dmz,v.qhmc dmsm1 from bas_all_xzqh v where v.jlzt='1' ";
        List<Code> list = jdbcTemplate.queryForList(sql, Code.class);
        return list;
    }    
    
	//从行政区划信息表获取信息
	public List<BasAllxzqh> getBasAllxzqhBySft(String sft) throws Exception {
        String sql = "select v.* from bas_all_xzqh v " +
        		" where substr(v.Xzqh, 5, 2) <> '00' "  +
				" And v.Xzqh Like '" + sft + "%' " +
				" Order By v.Xzqh Asc ";
		List<BasAllxzqh> list = jdbcTemplate.queryForList(sql, BasAllxzqh.class);
		return list;
	}

	public List<Department> getStatOrg(String glbm, boolean includeSelf) throws Exception {
		BasAlldept dept = this.getBasAlldept(glbm);
		// 修改如果bas_all_dept获取不到管理部门时报错
		Department department;
		if (dept == null){
			department = this.getDepartment(glbm);
		}
		else{
			department = new Department();
			department.setGlbm(glbm);
			department.setBmjb(dept.getBmjb());
		}
		return this.getStatOrg(department, includeSelf);
	}

	public List<Department> getStatOrgWithBase(String glbm, boolean includeSelf)
			throws Exception {
		BasAlldept dept = this.getBasAlldept(glbm);
		if (dept != null) {
			String qc = "";
			if ("1".equals(dept.getBmjb())) {
				// 公安部
				qc = "a.glbm like '__0000000000' and a.bmjb='2'";
			} else if ("2".equals(dept.getBmjb())) {
				// 总队
				if (dept.getGlbm().endsWith("10")) {
					// 高速
					qc = "a.glbm like '" + dept.getGlbm().substring(0, 2)
							+ "______0010' and a.bmjb='3'";
				} else {
					qc = "a.glbm like '" + dept.getGlbm().substring(0, 2)
							+ "______00_0' and a.bmjb='3'";
				}
			} else if ("3".equals(dept.getBmjb())) {
				// 支队
				if (dept.getGlbm().endsWith("10")) {
					// 高速
					qc = "a.glbm like '" + dept.getGlbm().substring(0, 4)
							+ "____0010' and a.bmjb='4'";
				} else {
					qc = "a.glbm like '" + dept.getGlbm().substring(0, 4)
							+ "____00_0' and a.bmjb='4'";
				}
			} else if ("4".equals(dept.getBmjb())) {
				// 大队
				qc = "a.glbm like '" + dept.getGlbm().substring(0, 8)
						+ "%' and a.bmjb='5'";
			} else {
				qc = "a.glbm like '" + dept.getGlbm().substring(0, 10) + "%'";
			}
			if (includeSelf) {
	            qc = "(" + qc + " or a.glbm='" + dept.getGlbm() + "') and a.jlzt = '1'";
	        } else {
	            qc = "(" + qc + ") and a.glbm<>'" + dept.getGlbm() + "' and a.jlzt = '1'";
			}
	        // Bas_Department-Frm_Department_Std
	        String sql = "select a.glbm, nvl(a.bmjc, b.bmmc) bmmc, a.bmjb, a.sjbm from bas_all_dept a ,frm_department b " +
	        		"where a.glbm = b.glbm(+) and " + qc + " order by a.glbm";
			System.out.println(sql);
			List<Department> list = this.jdbcTemplate.queryForList(sql,	Department.class);
			return list;
		}
		else{
			return null;
		}
	}
	public List<Department> getStatOrg(Department dept, boolean includeSelf,
			boolean isContainOffice) throws Exception {
		String qc = "";
		if ("1".equals(dept.getBmjb())) {
			// 公安部
			qc = "a.glbm like '__0000000000' and a.bmjb='2'";
		} else if ("2".equals(dept.getBmjb())) {
			// 总队
			if (dept.getGlbm().endsWith("10")) {
				// 高速
				qc = "a.glbm like '" + dept.getGlbm().substring(0, 2)
						+ "______0010' ";
			} else {
				qc = "a.glbm like '" + dept.getGlbm().substring(0, 2)
						+ "______00_0' ";
			}
			if(!isContainOffice){
				qc+= " and a.bmjb='3'";
			}
		} else if ("3".equals(dept.getBmjb())) {
			// 支队
			if (dept.getGlbm().endsWith("10")) {
				// 高速
				qc = "a.glbm like '" + dept.getGlbm().substring(0, 4)
						+ "____0010' ";
			} else {
				qc = "a.glbm like '" + dept.getGlbm().substring(0, 4)
						+ "____00_0' ";
			}
			if(!isContainOffice){
				qc+= " and a.bmjb='4 '";
			}
		} else if ("4".equals(dept.getBmjb())) {
			// 大队
			qc = "a.glbm like '" + dept.getGlbm().substring(0, 8) + "%' ";
			if (!isContainOffice) {
				qc += " and a.bmjb='5' ";
			}
		} else {
			qc = "a.glbm like '" + dept.getGlbm().substring(0, 10) + "%'";
		}
		if (includeSelf) {
			qc = "(" + qc + " or a.glbm='" + dept.getGlbm()
					+ "') and a.jlzt = '1'";
		} else {
			qc = "(" + qc + ") and a.glbm<>'" + dept.getGlbm()
					+ "' and a.jlzt = '1'";
		}
		// Bas_Department-Frm_Department_Std
		String sql = "select a.glbm, nvl(b.bmjc, a.bmmc) bmmc, a.bmjb, a.sjbm from frm_department a ,bas_all_dept b "
				+ "where a.glbm = b.glbm(+) and " + qc + " order by a.glbm";
		System.out.println(sql);
		List<Department> list = this.jdbcTemplate.queryForList(sql,
				Department.class);
		return list;
	}
//	
//	public List<Department> getAllXjDepartmentBySjbm(String glbm) throws Exception{
//		String key = "department_xj:" + glbm;
//		Hashtable tab = (Hashtable) SystemCache.getInstance().getValue(Constants.MEM_ALL_DEPARTMENT_XJ);
//		List<Department> list=(List<Department>)tab.get(key);
//		if(list==null){
//			String strSql = "select * from frm_department where sjbm=:glbm order by glbm ";
//			HashMap<String,String> map = new HashMap<String,String>();
//			map.put("glbm",glbm);
//			List<Department> mylist=(List<Department>)jdbcTemplate.queryForList(strSql,map,Department.class);
//			if(mylist!=null){
//				tab.put(key,mylist);
//			}
//			return mylist;
//		}else{
//			return list;
//		}
//	}	
}
