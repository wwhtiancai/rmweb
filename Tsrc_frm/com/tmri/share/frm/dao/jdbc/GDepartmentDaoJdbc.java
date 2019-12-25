package com.tmri.share.frm.dao.jdbc;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Repository;

import com.tmri.share.cache.bean.SystemCache;
import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.dao.GDepartmentDao;
import com.tmri.share.frm.util.Constants;
import com.tmri.share.frm.util.StringUtil;

@Repository
public class GDepartmentDaoJdbc extends FrmDaoJdbc implements GDepartmentDao{

	/**
	 * ��ȡ����������
	 * @param glbm
	 * @return
	 */
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
	
	public String getDepartmentBmmc(String glbm) throws Exception{
		Department d=this.getDepartment(glbm);
		if(d==null){
			return glbm;
		}else{
			return d.getBmmc();
		}
	}
	
	public String getFzjgByglbm(String glbm) throws Exception{
		Department d=this.getDepartment(glbm);
		if(d==null){
			return glbm;
		}else{
			return d.getFzjg();
		}
	}
	
	public List<Department> getXjDepartmentBySjbm(String glbm) throws Exception{
		String key = "department_xj:" + glbm;
		Hashtable tab = (Hashtable) SystemCache.getInstance().getValue("department");
		List<Department> list=(List<Department>)tab.get(key);
		if(list==null){
			String strSql = "select * from frm_department where sjbm=:glbm order by glbm ";
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("glbm",glbm);
			List<Department> mylist=(List<Department>)jdbcTemplate.queryForList(strSql,map,Department.class);
			if(mylist!=null){
				tab.put(key,mylist);
			}
			return mylist;
		}else{
			return list;
		}
	}
	
	/**
     * ����getXjDepartmentBySjbm�������ڴ�ֱ�Ӷ����ݿ�
     * ��Ϊ�ڴ��еĲ���ֻ��1,2��������������Ҫ��ȡ�������ŵ���Ҫ
     * Ϊ����Ӱ����ǰ���õĴ��룬������һ����������
     * @author ʩһ��
     * @param glbm
     * @return
     */
	public List<Department> getAllXjDepartmentBySjbm(String glbm) throws Exception{
        String strSql = "select * from frm_department where sjbm=:glbm order by bmjb,glbm ";
	    HashMap<String,String> map = new HashMap<String,String>();
	    map.put("glbm",glbm);
	    List<Department> mylist=(List<Department>)jdbcTemplate.queryForList(strSql,map,Department.class);
	    return mylist;
	}
	
	
	/**
	 * ��ȡ���ҵ��ϼ������Ŵ���
	 * @param dept
	 * @return
	 */
	public String getOfficeSjbm(Department dept){
		if (null == dept) {
			return "";
		}
		String sql = "select Rm_Pub_Pkg.Getkssjglbm('"+dept.getGlbm()+"') from dual";
		String result= (String)this.jdbcTemplate.queryForObject(sql, String.class);
		return result;
	}
	
	
	public String getDldmGlbm(Department dept){
		String sql = "select Rm_Pub_Pkg.Getdldmglbm('"+dept.getGlbm()+"') from dual";
		String result= (String)this.jdbcTemplate.queryForObject(sql, String.class);
		return result;
	}
	
	public String getGlbmByFzjg(String fzjg){
		String sql = "select Vmc_Comm_Pkg.Get_Glbm_By_Fzjg('"+fzjg+"') from dual";
		String result= (String)this.jdbcTemplate.queryForObject(sql, String.class);
		return result;
	}
	
	
	public String getZdGlbm(Department dept){
		String sql = "select Rm_Pub_Pkg.Getzdglbm('"+dept.getGlbm()+"') from dual";
		String result= (String)this.jdbcTemplate.queryForObject(sql, String.class);
		return result;
	}
	
	/**
	 * ���ع�����ͷ
	 */
	public String getDepartmentHeadSQL(Department department) throws Exception{
		if (null != department) {
			if("1".equals(department.getBmjb())){
				return "";
			}else if("2".equals(department.getBmjb())){
				return department.getGlbm().substring(0,2);
			}else if("3".equals(department.getBmjb())){
				//�ж�ʡ����
				if(department.getGlbm().substring(2,4).equals("90")){
					return department.getGlbm().substring(0,6);
				}else{
					return department.getGlbm().substring(0,4);
				}
			}else if("4".equals(department.getBmjb())){
				return department.getGlbm().substring(0,8);
			}else if("5".equals(department.getBmjb())){
				return department.getGlbm().substring(0,10);
			}else{
				return department.getGlbm();
			}
		}else {
			return null;
		}
	}
	
    /**
     * ���ݹ�����ͷ���ع�����
     */
    public String getGlbmByHeadSQL(String glbmHead) throws Exception{
        String name = "";
        String glbm = "";
        if (glbmHead.length() == 2) {
            glbm = glbmHead + "0000000000";
        }
        if (glbmHead.length() == 4) {
            name = getDepartmentSTDBmmc(glbmHead + "00000000");
            if (name.length() == 12) {
                glbm = glbmHead + "00000010";
            } else {
                glbm = glbmHead + "00000000";
            }
        }
        if (glbmHead.length() == 6) {
            name = getDepartmentSTDBmmc(glbmHead + "000000");
            if (name.length() == 12) {
                glbm = glbmHead + "000010";
            } else {
                glbm = glbmHead + "000000";
            }
        }
        if (glbmHead.length() == 8) {
            name = getDepartmentSTDBmmc(glbmHead + "0000");
            if (name.length() == 12) {
                glbm = glbmHead + "0010";
            } else {
                glbm = glbmHead + "0000";
            }
        }
        if (glbmHead.length() == 12) {
            glbm = glbmHead;
        }
        return glbm;
	}

	public List<Department> getPoliceStationList(String glbm) throws Exception {
		String strSql = "select * from pls_department where sjbm=:glbm order by glbm ";
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("glbm", glbm);
		List<Department> mylist = (List<Department>) jdbcTemplate.queryForList(
				strSql, map, Department.class);
		return mylist;
	}

	public Department getPoliceStation(String glbm) throws Exception {
		String key = "policestation:" + glbm;
		Hashtable tab = (Hashtable) SystemCache.getInstance().getValue(
				"policestation");
		Department department = (Department) (tab.get(key));
		if (department == null) {
			String strSql = "select * from pls_department where glbm=:glbm";
			HashMap<String, String> map = new HashMap<String, String>();
			if (StringUtil.checkBN(glbm)) {
				map.put("glbm", glbm);
			}else {
				map.put("glbm", "UNKNOWN");
			}
			department = (Department) this.jdbcTemplate.queryForSingleObject(
					strSql, map, Department.class);
			if (department != null) {
				tab.put(key, department);
			} else {
				department = new Department();
				department.setGlbm(glbm);
				department.setBmmc(glbm);
				department.setBmqc(glbm);
			}
		}
		return department;
	}

	public String getPoliceStationForshort(String glbm) throws Exception {
		Department d = this.getPoliceStation(glbm);
		return d.getBmmc();
	}

	public String getPoliceStationValue(String glbm) throws Exception {
		Department d = this.getPoliceStation(glbm);
		return d.getBmqc();
	}
	
	public List<Department> getOraDepartments() throws SQLException{
		String strSql = "select mrz from frm_syspara where xtlb='00' and gjz='XTYXMS'";
		String xtyxms = (String)jdbcTemplate.queryForSingleObject(strSql,String.class);		
		if(xtyxms!=null&&xtyxms.equals("4")){
            strSql = "select t.*,decode(substr(glbm,11,2),'10',substr(glbm,1,10)||'00',glbm) glbm2 from frm_department t where jlzt='1' and (bmjb='1' or bmjb='2' or bmjb='3') order by glbm";
		}else{
			strSql = "select t.*,decode(substr(glbm,11,2),'10',substr(glbm,1,10)||'00',glbm) glbm2 from frm_department t where jlzt='1' order by glbm";
		}
		return jdbcTemplate.queryForList(strSql, Department.class);
	}	
	
	
	//���ݹ����ţ���ȡ����������Ϣ
	//����ͳ�ƽ��չʾ����ѯ����
	//20141104
	public String getDepartmentSTDBmmc(String glbm) throws Exception{
        String strSql = "select bmmc from bas_all_dept where jlzt='1' and glbm='" + glbm + "'";
        String result = null;
        try {
            result = (String) this.jdbcTemplate.queryForObject(strSql, String.class);
        } catch (EmptyResultDataAccessException e) {
            return glbm;
        } catch (IncorrectResultSizeDataAccessException e) {
            return glbm;
        } catch (DataAccessException e) {
            return glbm;
        }
	    return result;
	}
	
}
