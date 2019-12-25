package com.tmri.share.frm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmri.share.frm.bean.BasAlldept;
import com.tmri.share.frm.bean.BasAllxzqh;
import com.tmri.share.frm.bean.Code;
import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.bean.FrmXzqhLocal;
import com.tmri.share.frm.bean.Machine;
import com.tmri.share.frm.dao.GBasDao;
import com.tmri.share.frm.dao.GSysparaCodeDao;
import com.tmri.share.frm.service.GBasService;
import com.tmri.share.frm.service.GSysparaCodeService;
import com.tmri.share.frm.util.Constants;
import com.tmri.share.frm.util.StringUtil;

@Service
public class GBasServiceImpl implements GBasService {
	
	@Autowired
	private GBasDao gBasDao;
	
	@Autowired
	private GSysparaCodeDao gSysparaCodeDao;
	
	@Autowired
	private GSysparaCodeService gSysparaCodeService;
	
	
    // 根据行政区划编号，获取对象(本地行政区划)
	public FrmXzqhLocal getLocalxzqh(String xzqh) throws Exception {
		return this.gBasDao.getFrmLocalxzqh(xzqh);
	}
	
    // 根据行政区划编号，获取对象(本地行政区划)，并存入code中
    public Code getLocalxzqhCode(String xzqh) throws Exception {
        FrmXzqhLocal obj = this.gBasDao.getFrmLocalxzqh(xzqh);
        Code code = null;
        if (obj != null) {
            code = new Code();
            code.setXtlb("00");
            code.setDmlb("0050");
            code.setDmz(obj.getXzqh());
            code.setDmsm1(obj.getQhmc());
            code.setDmsm2(obj.getQhsm());
        }
        return code;
    }
    
    

    // 获取本地行政区划列表
	public List<FrmXzqhLocal> getLocalxzqhList() throws Exception {
		return this.gBasDao.getFrmLocalxzqhList();
	}

    // 获取行政区划对应的实际行政区划，如没有则返回自身
    public String getSjxzqhs(String xzqh) throws Exception {
        String sjxzqh = this.gBasDao.getSjxzqhs(xzqh);
        if (!StringUtil.checkBN(sjxzqh)) {
            sjxzqh = xzqh;
        }
        return sjxzqh;
    }

    // 获取行政区划对应的实际行政区划，如没有则返回自身
    public String getSjxzqhsByComma(String xzqh) throws Exception {
        String sjxzqh = getSjxzqhs(xzqh).replaceAll("#", ",");
        return sjxzqh;
    }
    // 获取本地行政区划列表，并存入code列表中
	public List<Code> getLocalXzqhCodeList() throws Exception {
		List<FrmXzqhLocal> list =  this.getLocalxzqhList();
		List<Code> result =  new ArrayList<Code>();
        if (list != null && !list.isEmpty()) {
            for (FrmXzqhLocal obj : list) {
                Code code = new Code();
                code.setXtlb("00");
                code.setDmlb("0050");
                code.setDmz(obj.getXzqh());
                code.setDmsm1(obj.getQhmc());
                code.setDmsm2(obj.getQhsm());
                result.add(code);
            }
        } else {
            result = this.getXzqhCodeList();
		}
		return result;
	}
	
    // 根据行政区划编号，获取对象(各地行政区划)
	public BasAllxzqh getXzqh(String xzqh) throws Exception {
		return this.gBasDao.getBasAllxzqh(xzqh);
	}
	
    // 根据行政区划编号，获取对象(本地行政区划)，并存入code中
    public Code getXzqhCode(String xzqh) throws Exception {
    	BasAllxzqh obj = this.gBasDao.getBasAllxzqh(xzqh);
        Code code = null;
        if (obj != null) {
            code = new Code();
            code.setXtlb("00");
            code.setDmlb("0050");
            code.setDmz(obj.getXzqh());
            code.setDmsm1(obj.getQhmc());
            code.setDmsm2(obj.getQhsm());
        }
        return code;
    }	
    
	//获取行政区划名称
	public String getXzqhmc(String xzqh)  {
        if (!StringUtil.checkBN(xzqh)) {
            return "";
        }
		String result=xzqh;
		try{
			BasAllxzqh basSysXzqh =  this.getXzqh(xzqh);
			if(basSysXzqh!=null){
				result=basSysXzqh.getQhmc();
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		//如果全部行政区划未来得及同步
		if(xzqh.equals(result)){
			try{
				FrmXzqhLocal frmLocalxzqh =  this.getLocalxzqh(xzqh);
				if(frmLocalxzqh!=null){
					result=frmLocalxzqh.getQhmc();
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		
		return result;
	}

    // 根据部门编号获取管理部门对象
	public BasAlldept getDeptByGlbm(String glbm) throws Exception {
		return this.gBasDao.getBasAlldept(glbm);
	}
	
    // 根据部门编号获取部门名称
	public String getBmmcByGlbm(String glbm) throws Exception {
		String result=glbm;
		try{
			BasAlldept basDepartment =  this.getDeptByGlbm(glbm);
			if(basDepartment!=null){
				result=basDepartment.getBmmc();
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return result;
	}

    // 根据部门编号获取部门全称
	public String getBmqcByGlbm(String glbm) throws Exception {
		String result=glbm;
		try{
			BasAlldept basDepartment =  this.getDeptByGlbm(glbm);
			if(basDepartment!=null){
				result=basDepartment.getBmqc();
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return result;
	}
	
	public String getBmjcByGlbm(String glbm) throws Exception {
		String result=glbm;
		try{
			BasAlldept basDepartment =  this.getDeptByGlbm(glbm);
			if(basDepartment!=null){
				result=basDepartment.getBmjc();
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return result;
	}	
	
    // 根据发证机关获取部门对象
	public BasAlldept getDeptByFzjg(String fzjg) throws Exception {
		BasAlldept result = this.gBasDao.getBasAlldeptByFzjg(fzjg);
		if(result!=null){
			if(StringUtil.checkBN(result.getZfjg())){
				result.setFzjg(result.getZfjg());
			}
		}
		return result;
	}
	
    // 获取管理部门列表
    public List<BasAlldept> getDeptList() throws Exception {
        return this.gBasDao.getBasDeptList();
    }

    // 根据发证机关获取部门对象，并存入code中
    public Code getDeptCodeByFzjg(String fzjg) throws Exception {
        BasAlldept obj = this.gBasDao.getBasAlldeptByFzjg(fzjg);
        Code code = null;
        if (obj != null) {
            code = new Code();
            code.setXtlb("00");
            code.setDmlb("0234");
            if(StringUtil.checkBN(obj.getZfjg())){
            	code.setDmz(obj.getZfjg());
			}else{
				code.setDmz(obj.getFzjg());
			}
            code.setDmsm1(obj.getBmqc());
            code.setDmsm2(obj.getBmmc());
        }
        return code;
    }

    // 根据行政区划获取部门对象，并存入code中
    public Code getDeptCodeByXzqh(String xzqh) throws Exception {
        BasAlldept obj = this.getDeptByXzqh(xzqh);
        Code code = null;
        if (obj != null) {
            code = new Code();
            code.setXtlb("00");
            code.setDmlb("0234");
            code.setDmz(obj.getFzjg());
            code.setDmsm1(obj.getBmqc());
            code.setDmsm2(obj.getBmmc());
        }
        return code;
    }



    // 根据发证机关获取部门名称
	public String getBmmcByFzjg(String fzjg) throws Exception {
		String result=fzjg;
		try{
			BasAlldept basDepartment =  this.getDeptByFzjg(fzjg);
			if(basDepartment!=null){
				result=basDepartment.getBmmc();
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return result;
	}
	
    // 根据发证机关获取部门全称
	public String getBmqcByFzjg(String fzjg) throws Exception {
		String result=fzjg;
		try{
			BasAlldept basDepartment =  this.getDeptByFzjg(fzjg);
			if(basDepartment!=null){
				result=basDepartment.getBmqc();
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return result;
	}	
	
	public String getBmjcByFzjg(String fzjg) throws Exception {
		String result=fzjg;
		try{
			BasAlldept basDepartment =  this.getDeptByFzjg(fzjg);
			if(basDepartment!=null){
				result=basDepartment.getBmjc();
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return result;
	}		
	
    // 根据行政区划获取部门对象
	public BasAlldept getDeptByXzqh(String xzqh) throws Exception {
		return this.gBasDao.getBasAlldeptByXzqh(xzqh);
	}
	
    // 根据行政区划获取部门名称
	public String getBmmcByXzqh(String xzqh) throws Exception {
		String result=xzqh;
		try{
			BasAlldept basDepartment =  this.getDeptByXzqh(xzqh);
			if(basDepartment!=null){
				result=basDepartment.getBmmc();
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return result;
	}	
	
    // 根据行政区划获取部门全称
	public String getBmqcByXzqh(String xzqh) throws Exception {
		String result=xzqh;
		try{
			BasAlldept basDepartment =  this.getDeptByXzqh(xzqh);
			if(basDepartment!=null){
				result=basDepartment.getBmqc();
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return result;
	}
	
    // 根据行政区划获取部门全称
	public String getBmjcByXzqh(String xzqh) throws Exception {
		String result=xzqh;
		try{
			BasAlldept basDepartment =  this.getDeptByXzqh(xzqh);
			if(basDepartment!=null){
				result=basDepartment.getBmjc();
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return result;
	}	

	//cs为四位行政区划
	public String getCityName(String cs) throws Exception {
		String xzqh = cs;
		if (null != cs
				&& (cs.startsWith("11") || cs.startsWith("12")
						|| cs.startsWith("31") || cs.startsWith("50"))) {
			xzqh = cs.substring(0, 2) + "0000";
		} else {
			xzqh = cs + "00";
		}
		return this.getBmjcByXzqh(xzqh);
	}
	
	//cs六位行政区划
	public String getLdName(String cs) throws Exception {
		String xzqh = cs;
		if (null != cs
				&& (cs.startsWith("11") || cs.startsWith("12")
						|| cs.startsWith("31") || cs.startsWith("50"))) {
			xzqh = cs.substring(0, 2) + "0000";
		} 
		return this.getBmjcByXzqh(xzqh);
	}	
	
	public List<Machine> queryXzqhList(String glbmHead) throws Exception {
		return this.gBasDao.getBasAlldeptList(glbmHead);
	}
	
	
	public String getGlbmByFzjg(String fzjg) throws Exception {
		String result=fzjg;
		try{
			BasAlldept basDepartment =  this.getDeptByFzjg(fzjg);
			if(basDepartment!=null){
				result=basDepartment.getGlbm();
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return result;
	}	
	
    public List<BasAlldept> getDeptListBySft(String sft) throws Exception{
    	return this.gBasDao.getBasAlldeptListBySft(sft);
    }
    
    public List<BasAllxzqh> getXzqhBySft(String sft) throws Exception{
    	return this.gBasDao.getBasAllxzqhBySft(sft);
    }

    public List<Department> getStatOrg(Department dept, boolean includeSelf)
			throws Exception {
    	return this.gBasDao.getStatOrg(dept, includeSelf);
    }
    
    public List<Department> getStatOrg(Department dept) throws Exception{
    	return this.gBasDao.getStatOrg(dept);
    }
    
      
    public List<BasAllxzqh> getXzqhList() throws Exception {
    	return this.gBasDao.getBasAllxzqhList();
    }
    
    public List<Code> getXzqhCodeList() throws Exception {
    	return this.gBasDao.getBasAllXzqhCodeList();
    }    
    
    public String transXzqhBySplit(String xzqh, String split){
    	String resultString = "";
		String[] dmzs = xzqh.split(split);
		for (int i = 0; i < dmzs.length; i++) {
			resultString += this.getXzqhmc(dmzs[i]) + split;
		}
		if (!resultString.equals("")) {
			resultString = resultString.substring(0, resultString.length() - split.length());
		}
		return resultString;
    }
    
    
    
    
//	String fzjg = baseTfcPassManager.getFzjg(pass.getGcxh());
//
//	String url = "";
//	if (fzjg == null) {
//		if (pass.getGcxh().contains("4599")) {
//			url = "http://10.148.51.198:9080/rmweb";
//		} else if (pass.getGcxh().startsWith("3399")) {
//			url = "http://10.118.113.141:9080/rmweb";
//		} else if (pass.getGcxh().startsWith("5180")) {
//			url = "http://10.64.17.206:9080/rmweb";
//		}
//	} else {
//		if (fzjg.equals("桂D")) {
//			url = "http://10.152.63.237:9080/rmweb";
//		} else if (fzjg.equals("沪ZD")) {
//			url = "http://10.14.33.103:9080/rmweb";
//		} else {
//			if (pass.getGcxh().startsWith("11")) {
//				url = "http://10.10.13.116:9080/rmweb";
//			} else {
//				url = machineInfoService.getActiveWebIp("60", fzjg);
//			}
//		}
//	}
	//根据过车序号获取发证机关
  	//过车序号前七位为接入点编号
  	//接入点编号的编码规则为当前系统顶级部门的行政区划
  	
  	//第一步取过车序号的前六位作为xzqh
  	//第二部从frm_code(dmlb='0234' and xtlb='00')获取发证机关
  	//如果未获取到发证机关,则再从frm_code(dmlb='0234' and xtlb='60')获取发证机关
    //根据过车序号获取发证机关
    public String getFzjg(String gcxh) throws Exception {
		String xzqh=gcxh.substring(0,4)+"00";
		String fzjg="";
		//获取
//		List codelist=this.gSysparaCodeService.getCodes("00", "0234");
        // getBasDeptCodeList-0234
		try{
			BasAlldept dept=this.getDeptByXzqh(xzqh);
			if(dept!=null){
				if(StringUtil.checkBN(dept.getZfjg())){
					fzjg=dept.getZfjg();
				}else{
					fzjg=dept.getFzjg();
				}
				
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}

		
		//如果未获取到发证机关，则再从扩充代码获取
		if(fzjg.equals("")){
			List codelist=this.gSysparaCodeDao.getCodes("60", "0234");
			for (int i=0;i<codelist.size();i++) {
				Code code=(Code)codelist.get(i);
				if (code.getDmsm3().equals(xzqh)) {
					fzjg=code.getDmz();
					break;
				}
			}
		}
		
		if(fzjg.equals("")){
			System.out.println("获取FZJG失败" + gcxh);
		}
		
		return fzjg;
	}

	public List<Department> getStatOrg(String glbm, boolean includeSelf)
			throws Exception {
		return this.gBasDao.getStatOrg(glbm, includeSelf);
	}

	public List<Department> getStatOrgWithBase(String glbm,
			boolean includeSelf) throws Exception {
		return this.gBasDao.getStatOrgWithBase(glbm, includeSelf);
	}

}
