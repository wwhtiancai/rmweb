package com.tmri.share.frm.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmri.share.frm.bean.BasAlldept;
import com.tmri.share.frm.bean.Code;
import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.dao.GDepartmentDao;
import com.tmri.share.frm.dao.GSystemDao;
import com.tmri.share.frm.service.GBasService;
import com.tmri.share.frm.service.GDepartmentService;
import com.tmri.share.frm.util.StringUtil;

@Service
public class GDepartmentServiceImpl  implements GDepartmentService{
	@Autowired
	private GDepartmentDao gDepartmentDao;
	@Autowired
	private GSystemDao gSystemDao;
	@Autowired
	private GBasService gBasService;
	

	public Department getDepartment(String glbm) throws Exception {
	    Department dept= this.gDepartmentDao.getDepartment(glbm);
	    if (dept == null) {
            // 如果部门表没有外省或外地信息，就到bas_dept中去查
            BasAlldept basdept = this.gBasService.getDeptByGlbm(glbm);
            if (basdept != null) {
                dept = new Department();
                dept.setGlbm(basdept.getGlbm());
                dept.setBmjb(basdept.getBmjb());
                dept.setBmmc(basdept.getBmjc());
                dept.setSjbm(basdept.getSjbm());
            }
        }
	    return dept;
	}

	public String getDepartmentBmmc(String glbm) throws Exception {
		return this.gDepartmentDao.getDepartmentBmmc(glbm);
	}
	
	public String getFzjgByglbm(String glbm) throws Exception {
		return this.gDepartmentDao.getFzjgByglbm(glbm);
	}

	/**
	 *获取下一级管理部门代码 只取一级，同时不列出科室
	 * 
	 * @param glbm
	 * @param includeself
	 * @return
	 * @throws Exception
	 */
	public List<Department> getXjDepartments(String glbm, boolean includeself) throws Exception {
		return this.getAllXjDepartments(glbm, includeself);
	}
	
    /**
     * 获取上级部门
     * @param glbm
     * @return
     * @throws Exception
     */
    public Department getSjDepartment(String glbm) throws Exception {
        Department curdept = getDepartment(glbm);
        Department sjbm = new Department();
        if (curdept != null) {
            String sj = curdept.getSjbm();
            sjbm = this.getDepartment(sj);
        }
        return sjbm;
    }

    /**
     * 判断管理部门是否为总队、支队、大队 不列出科室
     * 
     * @param glbm
     * @param i
     * @return
     */
	public boolean checkNotOffice(String glbm, int i) {
		String glbmw1 = "";
		String glbmw2 = "";
		for (int k = 0; k < 12 - i; k++) {
			glbmw1 += "0";
			if (k == 10 - i) {
				glbmw2 += "1"; // 高速
			} else {
				glbmw2 += "0";
			}
		}
		
		//
		if (glbm.substring(i).equals(glbmw1)
				|| glbm.substring(i).equals(glbmw2)) {
			return true;
		} else {
			return false;
		}
	}
	
	//用于总队级别判断下属部门是否是科室，
	//为了适应河北、湖北、安徽、黑龙江 
	//当选择到河北总队时，能够显示下属的高速支队；
	//从第几位开始判断是否


	public String getOfficeSjbm(Department dept) throws Exception {
		return this.gDepartmentDao.getOfficeSjbm(dept);
	}

	public String getDldmGlbm(Department dept) throws Exception {
		return this.gDepartmentDao.getDldmGlbm(dept);
	}

	public String getZdGlbm(Department dept) throws Exception {
		return this.gDepartmentDao.getZdGlbm(dept);
	}

	public String getDepartmentHeadSQL(Department department) throws Exception {
		return this.gDepartmentDao.getDepartmentHeadSQL(department);
	}

    public String getGlbmByHeadSQL(String glbmHead) throws Exception {
        return this.gDepartmentDao.getGlbmByHeadSQL(glbmHead);
    }

    public String getBmmcByHeadSQL(String glbmHead) throws Exception {
        String glbm = this.gDepartmentDao.getGlbmByHeadSQL(glbmHead);
        return this.getDepartmentSTDBmmc(glbm);
    }

	// 获取下级所有部门
	public List<Department> getAllXjDepartments(String glbm, boolean includeself)
			throws Exception {
		List<Department> result = new Vector<Department>();
		Department mydepartment = this.gDepartmentDao.getDepartment(glbm);
		String key = "";
		if (mydepartment == null || mydepartment.getGlbm() == null) {
			return null;
		}
		if ("1".equals(mydepartment.getBmjb())) {
			key = "010000000000";
			if (includeself) {
				result.add(this.gDepartmentDao.getDepartment(key));
			}
			List<Department> list = this.gDepartmentDao
					.getXjDepartmentBySjbm(key);
			for (Department d : list) {
				if ("2".equals(d.getBmjb())
						&& this.checkNotOffice(d.getGlbm(), 2)) {
					result.add(d);
				}
			}
		} else if ("2".equals(mydepartment.getBmjb())) {
			//增加江西高速总队特殊处理
			if(mydepartment.getGlbm().equals("360000000510")){
				result=this.getJXGsDeptList(mydepartment,includeself);
			}else{
				if (mydepartment.getGlbm().endsWith("0510")) {
					key = glbm;
				} else if (mydepartment.getGlbm().endsWith("10")) {
					key = glbm.substring(0, 2) + "0000000010";
				} else {
					key = glbm.substring(0, 2) + "0000000000";
				}
	
				if (includeself) {
					result.add(this.gDepartmentDao.getDepartment(key));
				}
				
				
				List<Department> list = this.gDepartmentDao.getXjDepartmentBySjbm(key);
				for (Department d : list) {
					//20141027未考虑省管县情况
					//20141104
					//if ("3".equals(d.getBmjb())	
					//		&& this.checkNotOffice(d.getGlbm(), 6)) {
					if(d.getGlbm().endsWith("0510")){
						//获取下属部门的时候特殊判断,当为总队的高速科室时，取下级部门
						//20141109增加
						List<Department> gslist = this.gDepartmentDao.getXjDepartmentBySjbm(d.getGlbm());
						for (Department dept : gslist) {
							if (this.checkNotOffice(dept.getGlbm(), 6)) {
								result.add(dept);
							}
						}
					}else{
						if (!"2".equals(d.getBmjb())&&this.checkNotOffice(d.getGlbm(), 6)) {
							result.add(d);
						}
					}
				}
			}	
		} else if ("3".equals(mydepartment.getBmjb())) {
			if (mydepartment.getGlbm().endsWith("10")) {
				key = glbm.substring(0, 4) + "00000010";
			} else {
				key = glbm.substring(0, 4) + "00000000";
			}

			if (includeself) {
				result.add(this.gDepartmentDao.getDepartment(key));
			}
			List<Department> list = this.gDepartmentDao
					.getXjDepartmentBySjbm(key);
			for (Department d : list) {
				if ("4".equals(d.getBmjb())
						&& this.checkNotOffice(d.getGlbm(), 8)) {
					result.add(d);
				}
			}
		} else if ("4".equals(mydepartment.getBmjb())) {
			if (mydepartment.getGlbm().endsWith("10")) {
				key = glbm.substring(0, 8) + "0010";
			} else {
				key = glbm.substring(0, 8) + "0000";
			}

			if (includeself) {
				result.add(this.gDepartmentDao.getDepartment(key));
			}
			List<Department> list = this.gDepartmentDao
					.getXjDepartmentBySjbm(key);
			for (Department d : list) {
				if ("5".equals(d.getBmjb())
						&& this.checkNotOffice(d.getGlbm(), 10)) {
					result.add(d);
				}
			}
		} else if ("5".equals(mydepartment.getBmjb())) {
			if (mydepartment.getGlbm().endsWith("10")) {
				key = glbm.substring(0, 10) + "10";
			} else {
				key = glbm.substring(0, 10) + "00";
			}

			if (includeself) {
				result.add(this.gDepartmentDao.getDepartment(key));
			} else if (!key.equals(mydepartment.getGlbm())) {
				result.add(this.gDepartmentDao.getDepartment(key));
			}
		} else {
			return null;
		}
		return result;
	}

	//360000000510  江西高速总队特殊处理
	public List<Department> getJXGsDeptList(Department dept,boolean includeself) {
		List<Department> result = new Vector<Department>();
		String key=dept.getGlbm();
		if (includeself) {
			result.add(this.gDepartmentDao.getDepartment(key));
		}
		
		//获取上级部门
		try{
			key = this.getOfficeSjbm(dept);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		List<Department> list = null;
		try{
			list = this.gDepartmentDao.getXjDepartmentBySjbm(key);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		for (Department d : list) {
			if(!d.getGlbm().equals(dept.getGlbm())&&d.getGlbm().endsWith("10")
					&&this.checkNotOffice(d.getGlbm(), 6)){
				result.add(d);
			}
		}	
		return result;
	}
    /**
     * 区别getAllXjDepartments，查找下级部门调用getAllXjDepartmentBySjbm;
     * 为避免影响以前引用的代码，另做了一个方法引用; 增加方法showoffice，判断是否显示科室
     * @author 施一珑
     * @param glbm
     * @param includeself
     * @return
     */
    public List<Department> getAllXjDepartments(String glbm,
            boolean includeself, boolean showoffice)
            throws Exception {
        List<Department> result = new Vector<Department>();
        Department mydepartment = this.gDepartmentDao.getDepartment(glbm);
        if (mydepartment == null || mydepartment.getGlbm() == null) {
            return null;
        }
        if ("1".equals(mydepartment.getBmjb())) {
            if (includeself) {
                result.add(this.gDepartmentDao.getDepartment(glbm));
            }
            List<Department> list = this.gDepartmentDao
                    .getAllXjDepartmentBySjbm(glbm);
            for (Department d : list) {
                if ("2".equals(d.getBmjb())) {
                    if (showoffice
                            || (!showoffice && this.checkNotOffice(d.getGlbm(),
                                    2))) {
                        result.add(d);
                    }
                }
            }
        } else if ("2".equals(mydepartment.getBmjb())) {

            if (includeself) {
                result.add(this.gDepartmentDao.getDepartment(glbm));
            }
            List<Department> list = this.gDepartmentDao
                    .getAllXjDepartmentBySjbm(glbm);
            for (Department d : list) {
                if (showoffice && "2".equals(d.getBmjb())
                        && !this.checkNotOffice(d.getGlbm(), 2)) {
                    result.add(d);
                }
                if ("3".equals(d.getBmjb())) {
                    if (showoffice
                            || (!showoffice && this.checkNotOffice(d.getGlbm(),
                                    4))) {
                        result.add(d);
                    }
                }
            }
        } else if ("3".equals(mydepartment.getBmjb())) {
            if (includeself) {
                result.add(this.gDepartmentDao.getDepartment(glbm));
            }
            List<Department> list = this.gDepartmentDao
                    .getAllXjDepartmentBySjbm(glbm);
            for (Department d : list) {
                if (showoffice && "3".equals(d.getBmjb())
                        && !this.checkNotOffice(d.getGlbm(), 4)) {
                    result.add(d);
                }
                if ("4".equals(d.getBmjb())) {
                    if (showoffice
                            || (!showoffice && this.checkNotOffice(d.getGlbm(),
                                    8))) {
                        result.add(d);
                    }
                }
            }
        } else if ("4".equals(mydepartment.getBmjb())) {

            if (includeself) {
                result.add(this.gDepartmentDao.getDepartment(glbm));
            }
            List<Department> list = this.gDepartmentDao
                    .getXjDepartmentBySjbm(glbm);
            for (Department d : list) {
                if (showoffice && "4".equals(d.getBmjb())
                        && !this.checkNotOffice(d.getGlbm(), 8)) {
                    result.add(d);
                }
                if ("5".equals(d.getBmjb())) {
                    if (showoffice
                            || (!showoffice && this.checkNotOffice(d.getGlbm(),
                                    10))) {
                        result.add(d);
                    }
                }
            }
        } else if ("5".equals(mydepartment.getBmjb())) {

            if (includeself) {
                result.add(this.gDepartmentDao.getDepartment(glbm));
            }
            else if (!glbm.equals(mydepartment.getGlbm())) {
                result.add(this.gDepartmentDao.getDepartment(glbm));
            }
        } else {
            return null;
        }
        return result;
    }

	public String getGlbmByFzjg(String fzjg){
		return this.gDepartmentDao.getGlbmByFzjg(fzjg);
	}
	
	public String getDepartmentName(String glbm) throws Exception {
		if (glbm != null && !glbm.equals("")) {
			Department dep = this.getDepartment(glbm);
			if (dep != null) {
				return dep.getBmmc();
			} else {
				if (glbm.length() < 10)
					return glbm;
				else {
					dep = this.getDepartment(glbm.substring(0, 10) + "10");
					if (dep != null) {
						return dep.getBmmc();
					} else {
						return glbm;
					}
				}
			}
		} else {
			return "";
		}
	}
	
	public String getDepartmentYzmc(String glbm) throws Exception{
		Department dep=this.getDepartment(glbm);
		if(dep!=null){
			return dep.getYzmc();
		}else{
			return glbm;
		}
	}
	
	/**
	 * 根据顶级部门编码 获取 大公安部门列表
	 * 
	 * @param glbm
	 * @return
	 * @throws Exception
	 */
	public List<Department> getPoliceDepartment(String glbm,boolean includeself) throws Exception{
		List<Department> result=new Vector<Department>();
		Department mydepartment=this.gDepartmentDao.getPoliceStation(glbm);
		String key="";
		if(mydepartment==null||mydepartment.getGlbm()==null){
			return null;
		}
		if("1".equals(mydepartment.getBmjb())){
			key="010000000000";
			if(includeself){
				result.add(this.gDepartmentDao.getPoliceStation(key));
			}
			List<Department> list=this.gDepartmentDao.getPoliceStationList(key);
			for(Department d:list){
				if("2".equals(d.getBmjb())){
					result.add(d);
				}
			}
		}else if("2".equals(mydepartment.getBmjb())){
			key=glbm.substring(0,2)+"0000000000";
			if(includeself){
				result.add(this.gDepartmentDao.getPoliceStation(key));
			}
			List<Department> list=this.gDepartmentDao.getPoliceStationList(key);
			for(Department d:list){
				if("3".equals(d.getBmjb())){
					result.add(d);
				}
			}
		}else if("3".equals(mydepartment.getBmjb())){
			key=glbm.substring(0,4)+"00000000";
			if(includeself){
				result.add(this.gDepartmentDao.getPoliceStation(key));
			}
			List<Department> list=this.gDepartmentDao.getPoliceStationList(key);
			for(Department d:list){
				if("4".equals(d.getBmjb())){
					result.add(d);
				}
			}
		}else if("4".equals(mydepartment.getBmjb())||"5".equals(mydepartment.getBmjb())){
			key=glbm.substring(0,8)+"0000";
			result.add(this.gDepartmentDao.getPoliceStation(key));
		}else{
			return null;
		}
		return result;
	}
		
	public Department getPoliceStation(String glbm) throws Exception{
		return this.gDepartmentDao.getPoliceStation(glbm);
	}
	public String getPoliceStationForshort(String glbm) throws Exception{
		return this.gDepartmentDao.getPoliceStationForshort(glbm);
	}
	public String getPolieStationValue(String glbm) throws Exception{
		return this.gDepartmentDao.getPoliceStationValue(glbm);
	}
	
    public String getDepartmentSTDBmmc(String glbm) {
        String result = glbm;
        try {
            result = this.gDepartmentDao.getDepartmentSTDBmmc(glbm);
            if (result.length() == 12) {
                result = this.gDepartmentDao.getDepartmentBmmc(glbm);
            }
        } catch (Exception ex2) {
            ex2.printStackTrace();
            result = glbm;
		}
		return result;
	}
	
	public List<Code> getDistrict(String glbm) throws Exception {
		String glxzqh = this.gSystemDao.getDistrictByDepartment(glbm);
		if(StringUtil.checkBN(glxzqh) == false) {
			return null;
		}
		String [] glxzqhs = glxzqh.split(",");
		ArrayList<Code> list = new ArrayList<Code>();
		for(String s : glxzqhs) {
            // getFrmLocalxzqhCode-0050
			Code code = this.gBasService.getLocalxzqhCode(s);
			if (code != null) {
				list.add(this.gBasService.getLocalxzqhCode(s));
			}
		}
		return list;
	}	

    // 根据管理部门获取用户所能查询的行政区划范围
    public String getXzqhSql(String glbm) throws Exception {
        return this.getXzqhSql(glbm, "");
    }

    public String getXzqhSql(String glbm, String table) throws Exception {
        Department dept = this.getDepartment(glbm);
        glbm = this.getOfficeSjbm(dept);
        String xzqhsql = "";
        if (StringUtil.checkBN(table)) {
            table = table + ".";
        }
        if ("2".equals(dept.getBmjb())) {
            xzqhsql = " And " + table + "xzqh like '" + glbm.substring(0, 2) + "%' ";
        } else if ("3".equals(dept.getBmjb())) {
            xzqhsql = " And " + table + "xzqh like '" + glbm.substring(0, 4) + "%' ";
        } else if (!"1".equals(dept.getBmjb())) {
            String glxzqh = this.gSystemDao.getDistrictByDepartment(glbm);
            xzqhsql = " And " + table + "xzqh in ('" + glxzqh.replaceAll(",", "','") + "') ";
        }
        return xzqhsql;
    }

}
