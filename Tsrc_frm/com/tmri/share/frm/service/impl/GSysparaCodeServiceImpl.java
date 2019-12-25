package com.tmri.share.frm.service.impl;

import java.sql.SQLException;
import java.util.*;

import com.tmri.rfid.common.CodeTableDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmri.share.frm.bean.Code;
import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.bean.FrmCtrlpara;
import com.tmri.share.frm.bean.FrmGispara;
import com.tmri.share.frm.bean.SysGroup;
import com.tmri.share.frm.bean.SysPara;
import com.tmri.share.frm.dao.GCtrlparaDao;
import com.tmri.share.frm.dao.GDepartmentDao;
import com.tmri.share.frm.dao.GSysparaCodeDao;
import com.tmri.share.frm.service.GSysparaCodeService;
import com.tmri.share.frm.util.StringUtil;

@Service
public class GSysparaCodeServiceImpl implements GSysparaCodeService {
	@Autowired
	private GSysparaCodeDao gSysparaCodeDao;
	@Autowired
	private GDepartmentDao gDepartmentDao;
	@Autowired
	private GCtrlparaDao gCtrlparaDao;

	private Map<String, List<Code>> codeCacheByDmz2 = new HashMap<String, List<Code>>();

    @Override
    public Code getCode(CodeTableDefinition definition, String dmz) {
        return getCode(definition.getXtlb(), definition.getDmlb(), dmz);
    }

    @Override
    public List getCodes(CodeTableDefinition definition) {
        return getCodes(definition.getXtlb(), definition.getDmlb());
    }

    public Code getCode(String xtlb, String dmlb, String dmz) {
		Code code = null;
		try {
			code = this.gSysparaCodeDao.getCode(xtlb, dmlb, dmz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return code;
	}

	public Code getCode(String xtlb, String dmlb, String dmz, String ywdx) {
		Code code = null;
		try {
			code = this.gSysparaCodeDao.getCode(xtlb, dmlb, dmz);
			if (code == null) {
				return null;
			} else {
				if (!ywdx.equals("")) {
					if (code.getYwdx().equals("") || code.getYwdx().indexOf(ywdx) >= 0) {
						return code;
					} else {
						return null;
					}
				} else {
					return code;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return code;
	}

	public List getCodes(String xtlb, String dmlb) {
		List list = null;
		try {
			list = this.gSysparaCodeDao.getCodes(xtlb, dmlb);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List getCodesByDb(String xtlb, String dmlb) {
		List list = null;
		try {
			list = this.gSysparaCodeDao.getCodesByDb(xtlb, dmlb);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List getCodes(String xtlb, String dmlb, String ywdx) {
		List list = null;
		try {
			if (ywdx == null || ywdx.equals("")) {
				list = this.gSysparaCodeDao.getCodes(xtlb, dmlb);
			} else {
				list = new Vector();
				List listtmp = this.gSysparaCodeDao.getCodes(xtlb, dmlb);
				Code code = null;
				for (int i = 0; i < listtmp.size(); i++) {
					code = (Code) listtmp.get(i);
					if (code.getYwdx().equals("") || code.getYwdx().indexOf(ywdx) >= 0) {
						list.add(code);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public Code getCodeByDmsm2(String xtlb, String dmlb, String dmsm2) {
		List list = this.getCodes(xtlb, dmlb);
		Code code = null;
		for (int i = 0; i < list.size(); i++) {
			code = (Code) list.get(i);
			if (code.getDmsm2().equals(dmsm2)) {
				return code;
			}
		}
		return null;
	}

    public List getCodesByDmsm2(String xtlb, String dmlb, String dmsm2) {
        List list = this.getCodes(xtlb, dmlb);
        Code code = null;
        List rtn = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            code = (Code) list.get(i);
            if (code.getDmsm2().equals(dmsm2)) {
                rtn.add(code);
            }
        }
        return rtn;
    }

    public Code getCodeByDmsm2(CodeTableDefinition definition, String dmsm2) {
        return getCodeByDmsm2(definition.getXtlb(), definition.getDmlb(), dmsm2);
    }

    public List getCodesByDmsm3(String xtlb, String dmlb, String dmsm3) {
        List list = this.getCodes(xtlb, dmlb);
        Code code = null;
        List rtn = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            code = (Code) list.get(i);
            if (code.getDmsm3().equals(dmsm3)) {
                rtn.add(code);
            }
        }
        return rtn;
    }

    public List getCodesByDmsm2(CodeTableDefinition definition, String dmsm2) {
        return getCodesByDmsm2(definition.getXtlb(), definition.getDmlb(), dmsm2);
    }

    public List getCodesByDmsm4(String xtlb, String dmlb, String dmsm4) {
        List list = this.getCodes(xtlb, dmlb);
        Code code = null;
        List rtn = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            code = (Code) list.get(i);
            if (code.getDmsm4().equals(dmsm4)) {
                rtn.add(code);
            }
        }
        return rtn;
    }

	public HashMap getCodesH(String xtlb, String dmlb) {
		List list = null;
		HashMap hm = new HashMap();
		try {
			list = this.gSysparaCodeDao.getCodes(xtlb, dmlb);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				Code code = (Code) list.get(i);
				hm.put(code.getDmz(), code);
			}
		}
		return hm;
	}

	public List getSelectCodes(String xtlb, String dmlb, String ywdx, String type, String selectValue) {
		List list = this.getCodes(xtlb, dmlb, ywdx);
		List result = new Vector();
		Code code = null;
		for (int i = 0; i < list.size(); i++) {
			code = (Code) list.get(i);
			if (type.equals("2")) {
				if (code.getDmsm2().equals(selectValue)) {
					result.add(code);
				}
			}
			if (type.equals("3")) {
				if (code.getDmsm3().equals(selectValue)) {
					result.add(code);
				}
			}
			if (type.equals("4")) {
				if (code.getDmsm4().equals(selectValue)) {
					result.add(code);
				}
			}

		}
		return result;
	}

	public List getCodesByDmz(String xtlb, String dmlb, String dmz) throws Exception {
		return this.gSysparaCodeDao.getCodesByDmz(xtlb, dmlb, dmz);
	}

	public List<Code> getCodesByDmz2(String xtlb, String dmlb, String dmz2) throws Exception {
		List codes = codeCacheByDmz2.get(xtlb + ":" + dmlb + ":" + dmz2);
		if (codes == null) {
			codes = this.gSysparaCodeDao.getCodesByDmz2(xtlb, dmlb, dmz2);
			if (codes != null && !codes.isEmpty()) {
				codeCacheByDmz2.put(xtlb + ":" + dmlb + ":" + dmz2, codes);
			}
		}
		return codes;
	}

	public String transCode(String xtlb, String dmlb, String dmz) {
		String r = dmz;
		if ("--".equals(dmz)) {
			return dmz;
		}
		try {
			Code code = this.gSysparaCodeDao.getCode(xtlb, dmlb, dmz);
			if (code != null) {
				r = code.getDmsm1();
			} else {
				r = dmz;
			}
		} catch (Exception e) {
			System.out.println("代码出错:" + xtlb + dmlb + dmz);
			e.printStackTrace();
		}
		return r;
	}

	/**
	 * 获取代码的代码值（dmz）
	 * 
	 * @param xtlb 01-机动车 02-驾驶人 03-事故 04-违法
	 * @param dmlb
	 * @return
	 */
	public String transCodeByDmsm2(String xtlb, String dmlb, String dmsm2) {
		List list = this.getCodes(xtlb, dmlb);
		Code code = null;
		for (int i = 0; i < list.size(); i++) {
			code = (Code) list.get(i);
			if (code.getDmsm2().equals(dmsm2)) {
				return code.getDmz();
			}
		}
		return dmsm2;
	}

	public String transMultiCodeBySplit(String xtlb, String dmlb, String dmz, String split) {
		String resultString = "";
		String[] dmzs = dmz.split(split);
		for (int i = 0; i < dmzs.length; i++) {
			resultString += this.transCode(xtlb, dmlb, dmzs[i]) + split;
		}
		if (!resultString.equals("")) {
			resultString = resultString.substring(0, resultString.length() - split.length());
		}
		return resultString;
	}

	public String transMultiCode(String xtlb, String dmlb, String dmz, int length) {
		return transMultiCode(xtlb, dmlb, dmz, length, ",");
	}

	public String transMultiCode(String xtlb, String dmlb, String dmz, int length, String split) {
		String resultString = "";
		String dmzString = null;
		int iCount = dmz.length() / length;
		for (int i = 0; i < iCount; i++) {
			dmzString = dmz.substring(i * length, (i + 1) * length);
			resultString += this.transCode(xtlb, dmlb, dmzString) + split;
		}
		if (!resultString.equals("")) {
			resultString = resultString.substring(0, resultString.length() - split.length());
		}
		return resultString;
	}

	public List getTjCodesByDmsm2(String dmlb, String dmsm2) {
		return this.gSysparaCodeDao.getTjCodesByDmsm2(dmlb, dmsm2);
	}

	public List getTjCodesByDmz(String dmlb, String strDmz) throws SQLException {
		return this.gSysparaCodeDao.getTjCodesByDmz(dmlb, strDmz);
	}

	public Code getTjCodeFromMem(String dmlb, String strDmz) {
		return this.gSysparaCodeDao.getTjCodeFromMem(dmlb, strDmz);
	}

	public String getTjCodeDmsm1ByDmsm2(String dmlb, String strDmsm2) {
		return this.gSysparaCodeDao.getTjCodeDmsm1ByDmsm2(dmlb, strDmsm2);
	}

	public List getDrvCityListFromMem(String dmlb, String strDmz) throws SQLException {
		return this.gSysparaCodeDao.getDrvCityCodes(dmlb, strDmz);
	}

	public String getSysParaValue(String xtlb, String gjz) {
		SysPara sysPara = gSysparaCodeDao.getSysPara(xtlb, "2", gjz);
		if (sysPara == null) {
			return "";
		} else {
			if (sysPara.getJyw().equals("0")) {
				return "-0001";
			}
			String csz = sysPara.getMrz();
			if (csz == null || csz.toLowerCase().equals("null")) {
				csz = "";
			}
			return csz;
		}

	}

	public List getDeptParaGlbms(String xtlb, String gjz, String value) {
		return this.gSysparaCodeDao.getDeptParaGlbms(xtlb, gjz, value);
	}

	public List getSysparasShow(String xgjb) throws Exception {
		List resultList = new Vector();

		List sysparaList = this.gSysparaCodeDao.getSysparasShow(xgjb);

		setParaResultList(resultList, sysparaList);

		return resultList;
	}

	public List getDepSyspara(SysPara sysPara) {
		return this.gSysparaCodeDao.getDepSyspara(sysPara);
	}

	public SysPara getSyspara(String xtlb, String cslx, String gjz) {
		return this.gSysparaCodeDao.getSysPara(xtlb, cslx, gjz);
	}

	public String getCodeValue(String xtlb, String dmlb, String dmz) throws Exception {
		return this.gSysparaCodeDao.getCodeValue(xtlb, dmlb, dmz);
	}

	public String getParaValue(String xtlb, String gjz) {
		SysPara sysPara = gSysparaCodeDao.getSysPara(xtlb, "2", gjz.toUpperCase());
		if (sysPara == null) {
			return "";
		} else {
			if (sysPara.getJyw().equals("0")) {
				return "-0001";
			}
			String csz = sysPara.getMrz();
			if (csz == null || csz.toLowerCase().equals("null")) {
				csz = "";
			}
			return csz;
		}
	}
	
	/**
     * 
     * @param tableName 表名
     * @return 如果是总队版或部局版表名后面加_jg
     */
    public String getTableName(String tableName) {
        String xtyxms= getParaValue("00", "XTYXMS");
        if("3".equals(xtyxms) || "4".equals(xtyxms)) {
            tableName = tableName + "_jg";
        }
        return tableName;
    }

	public String getParaDepartValue(String glbm, String xtlb, String gjz) {
		String csz = gSysparaCodeDao.getSysParaValue(xtlb, glbm, gjz);
		String tmpXtlb = xtlb;
		if (tmpXtlb == null) {
			tmpXtlb = "";
		}
		if (csz == null) {
			SysPara sysPara = gSysparaCodeDao.getSysPara(xtlb, "1", gjz);
			if (sysPara == null)
				return "";
			if (sysPara.getJyw().equals("0")) {
				return "-0001";
			}
			if (sysPara.getCssx().equals("2")) {
				Department dep = null;
				int iCs = 0;
				String paraGlbm = glbm;
				do {
					iCs++;
					csz = gSysparaCodeDao.getSysParaValue(xtlb, paraGlbm, gjz);
					if (csz != null) {
						break;
					}
					if (iCs >= 6) {
						break;
					}
					dep = gDepartmentDao.getDepartment(paraGlbm);
					if (dep == null) {
						break;
					}
					paraGlbm = dep.getSjbm();
				} while (!paraGlbm.equals("010000000000"));
				if (csz == null) {
					csz = sysPara.getMrz();
				}
			} else {
				csz = sysPara.getMrz();
			}
		}
		if (csz == null || csz.toLowerCase().equals("null")) {
			csz = "";
		}
		return csz;
	}

	public String getHpzlmc(String hpzl) throws Exception {
		return this.getCodeValue("00", "1007", hpzl);
	}

	public String getHpysmc(String hpys) throws Exception {
		return getCodeValue("62", "0005", hpys);
	}

	public String getCsysmc(String csys) throws Exception {
		String csysmc = "";
		if (null != csys && !"".equals(csys)) {
			char[] csysAry = csys.toCharArray();
			for (char c : csysAry) {
				if (c == 'Z' || c == 'z') {
					csysmc += ",--";
				} else {
					csysmc += "," + this.getCodeValue("00", "1008", String.valueOf(c));
				}
			}
		}
		if (csysmc.startsWith(",")) {
			return csysmc.substring(1);
		}
		return csysmc;
	}

	public String getCllxmc(String cllx) throws Exception {
		return this.getCodeValue("00", "1004", cllx);
	}

	private void setParaResultList(List resultList, List sysparaList) throws Exception {
		SysPara sysPara = null;
		SysGroup sysGroup = null;
		String xtlbString = "";
		List tmpList = new Vector();
		String mc = "";

		for (int j = 0; j < sysparaList.size(); j++) {
			sysPara = (SysPara) sysparaList.get(j);
			if ((!sysPara.getXtlb().equals(xtlbString)) && (!xtlbString.equals(""))) {
				sysGroup = new SysGroup();

				Code code = gSysparaCodeDao.getCode("00", "0001", xtlbString);
				if (code != null) {
					mc = code.getDmsm1();
				} else {
					mc = "公共参数";
					xtlbString = "00";
				}
				sysGroup = new SysGroup();
				sysGroup.setMc(mc);
				sysGroup.setGjz(xtlbString);
				sysGroup.setList(tmpList);
				resultList.add(sysGroup);

				tmpList = new Vector();
				tmpList.add(sysPara);

			} else {
				tmpList.add(sysPara);
			}
			xtlbString = sysPara.getXtlb();
		}
		Code code = gSysparaCodeDao.getCode("00", "0001", xtlbString);
		if (code != null) {
			mc = code.getDmsm1();
		} else {
			mc = "公共参数";
			xtlbString = "00";
		}
		sysGroup = new SysGroup();
		sysGroup.setMc(mc);
		sysGroup.setGjz(xtlbString);
		sysGroup.setList(tmpList);
		resultList.add(sysGroup);
	}

	public String getVehywyy(String ywlx, String ywyy) {
		return this.gSysparaCodeDao.getVehywyy(ywlx, ywyy);
	}

	public String getDrvYwyy(String ywlx, String ywyy) {
		String returnValue = "无";
		if (ywlx == null) {
			returnValue = "无";
		} else {
			if (ywlx.equals("A")) {
				returnValue = transMultiCode("02", "0002", ywyy, 1);
			} else if (ywlx.equals("C")) {
				returnValue = transMultiCode("02", "0004", ywyy, 1);
			} else if (ywlx.equals("G")) {
				returnValue = transMultiCode("02", "0006", ywyy, 1);
			} else if (ywlx.equals("O")) {
				returnValue = transMultiCode("02", "0012", ywyy, 1);
			} else if (ywlx.equals("I")) {
				returnValue = transMultiCode("02", "0013", ywyy, 1);
			} else if (ywlx.equals("Q")) {
				returnValue = transMultiCode("02", "0014", ywyy, 1);
			}
		}
		return returnValue;
	}

	public String getFzjgFromHphm(String hphm) {
		if (hphm.length() < 2) {
			return hphm;
		} else {
			String hpt = hphm.substring(0, 2);
			if (hpt.indexOf("京") >= 0 || hpt.indexOf("津") >= 0 || hpt.indexOf("沪") >= 0 || hpt.indexOf("渝") >= 0) {
				hpt = hpt.substring(0, 1) + "A";
				return hpt;
			} else {
				hpt = this.transCode("00", "0037", hpt);
				return hpt;
			}
		}
	}

	// 根据号牌种类，号牌号码取发证机关
	public String getFzjgFromHphm(String hpzl, String hphm) {
		String fzjg = "";
		if ("23".equals(hpzl) || "24".equals(hpzl)) {
			String hphmt = hphm.substring(0, 1);
			if (hphmt.equals("京") || hphmt.equals("津") || hphmt.equals("沪") || hphmt.equals("渝")) {
				fzjg = hphm.substring(0, 1) + "A";
			} else {
				fzjg = hphm.substring(0, 1) + "O";
			}
		} else {
			if (hphm.length() < 2) {
				fzjg = hphm;
			} else {
				String hpt = hphm.substring(0, 2);
				if (hpt.indexOf("京") >= 0 || hpt.indexOf("津") >= 0 || hpt.indexOf("沪") >= 0 || hpt.indexOf("渝") >= 0) {
					fzjg = hpt.substring(0, 1) + "A";
				} else {
					fzjg = this.transCode("00", "0037", hpt);
				}
			}
		}
		return fzjg;
	}

	public String getInformCodeMc(String xxdm) {
		return this.gSysparaCodeDao.getInformCode(xxdm).getDmsm1();
	}

	public int enableCloudSystem() throws Exception {
		return this.gSysparaCodeDao.enableCloudSystem();
	}

	// 检测是否属于涉密车辆
	public boolean checkJchpt(String hphm) {
		boolean result = false;
		if (hphm == null) {
			return result;
		} else {
			List hptlist = this.getCodes("62", "0001");
			if (hptlist == null) {
				return result;
			} else {
				for (int i = 0; i < hptlist.size(); i++) {
					Code code = (Code) hptlist.get(i);
					if (hphm.indexOf(code.getDmz()) == 0) {
						result = true;
						break;
					}
				}
			}
		}
		return result;
	}

	public List<Code> getBklxList() {
		List<Code> result = null;

		try {
			List<Code> list = this.getCodes("63", "0101");
			if (null != list && !list.isEmpty()) {
				result = new ArrayList<Code>();
				for (Code code : list) {
					if ("1".equals(code.getDmsm3())) {
						result.add(code);
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return result;
	}

	public List<Code> getBdlxList(String sjly) {
		String subTypeStr = null;
		List<Code> result = null;
		Code tmp = null;
		try {
			List<Code> list = this.getCodes("63", "0136");
			if (null != list && !list.isEmpty()) {
				for (Code code : list) {
					if (code.getDmz().equals(sjly)) {
						subTypeStr = code.getDmsm3();
						break;
					}
				}
			}

			if (StringUtil.checkBN(subTypeStr)) {
				String[] subTypes = subTypeStr.split(",");
				result = new ArrayList<Code>();
				for (String subType : subTypes) {
					tmp = this.getCode("63", "0101", subType);
					if (null != tmp) {
						result.add(tmp);
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	public List<Code> getAlarmQueryBklxList() {
		List<Code> result = null;

		try {
			List<Code> list = this.getCodes("63", "0101");
			if (null != list && !list.isEmpty()) {
				result = new ArrayList<Code>();
				for (Code code : list) {
					if (!"41".equals(code.getDmz()) && !"42".equals(code.getDmz())) {
						result.add(code);
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return result;
	}



	public List<Code> getDeviceTypeList() {
		List<Code> result = null;

		try {
			List<Code> list = this.getCodes("60", "0009");
			if (null != list && !list.isEmpty()) {
				result = new ArrayList<Code>();
				for (Code code : list) {
					if ("1".equals(code.getDmsm2())) {
						result.add(code);
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return result;
	}

	public List<Code> getClflList() {
		List<Code> clflList = this.gSysparaCodeDao.getCodes("60", "0081");
		if (null != clflList && !clflList.isEmpty()) {
			List<Code> tmpList = new ArrayList<Code>();
			for (Code code : clflList) {
				if ("3".equals(code.getDmz()) || "4".equals(code.getDmz()) || "5".equals(code.getDmz()) || "6".equals(code.getDmz())) {
					tmpList.add(code);
				}
			}
			return tmpList;
		}
		return null;
	}
	
	
	public String getDeptParaValue(String glbm,String xtlb,String gjz){
		String csz=gSysparaCodeDao.getSysParaValue(xtlb,glbm,gjz);
		String tmpXtlb=xtlb;
		if(tmpXtlb==null){
			tmpXtlb="";
		}
		if(csz==null){
			SysPara sysPara=gSysparaCodeDao.getSysPara(xtlb,"1",gjz);// 部门参数
			if(sysPara==null) return "";
			if(sysPara.getJyw().equals("0")){
				return "-0001";
			}
			if(sysPara.getCssx().equals("2")){
				Department dep=null;
				int iCs=0;
				String paraGlbm=glbm;
				do{
					iCs++;
					csz=gSysparaCodeDao.getSysParaValue(xtlb,paraGlbm,gjz);
					if(csz!=null){
						break;
					}
					if(iCs>=6){
						break;
					}
					dep=gDepartmentDao.getDepartment(paraGlbm);
					if(dep==null){
						break;
					}
					if(tmpXtlb.equals("01")||tmpXtlb.equals("02")){
						//paraGlbm=dep.getSjcjzdbm();
						paraGlbm=dep.getSjbm();
					}else if(tmpXtlb.equals("03")){
						paraGlbm=dep.getSjbm();
					}else if(tmpXtlb.equals("04")){
						paraGlbm=dep.getSjbm();
					}else{
						paraGlbm=dep.getSjbm();
					}
				}while(!paraGlbm.equals("010000000000"));
				if(csz==null){
					csz=sysPara.getMrz();
				}
			}else{
				csz=sysPara.getMrz();
			}
		}
		if(csz==null||csz.toLowerCase().equals("null")){
			csz="";
		}
		return csz;
	}

	public String getJccllx(String syxz, String clyt, String cllx, String xzqh) {
		if (StringUtil.checkBN(syxz)) {
			if("B".equals(syxz) || "E".equals(syxz)){
				// 客车(公路客运、旅游客运)
				return "2";
			}
			
			if ("F".equals(syxz)) {
				// 货车
				return "1";
			}
			
			if ("R".equals(syxz)) {
				// 危化品运输车
				return "3";
			}
		}
		
		if (StringUtil.checkBN(clyt)) {
			if (clyt.startsWith("X")) {
				// 校车
				return "4";
			}
		}
		
		if (StringUtil.checkBN(cllx) && StringUtil.checkBN(xzqh) && xzqh.length() == 6) {
			if ("K49".equals(cllx)) {
				// 微型面包车
				return "5";
			}
			
			// 下面是农村面包车判断逻辑 暂不删除 留作备用
			/* if ("K39".equals(cllx) || "K49".equals(cllx)) {
				try {
					// XZQH后两位
					int tail = Integer.parseInt(xzqh.substring(4));
					
					if (tail > 20 && tail < 89) {
						// 农村面包车
						return "5";
					}
				} catch (Exception e) {
				}
			} */
		}
		
		// 其他
		return "9";
	}
	
	//
	public String getCtrlpara(String azdm,String gjz) throws Exception {
		String result="";
		FrmCtrlpara frmCtrlpara = this.gCtrlparaDao.getCtrlpara(azdm,gjz);
		if(frmCtrlpara!=null){
			if(frmCtrlpara.getJyw().equals("1")){
				result = frmCtrlpara.getCsz();
			}else{
				throw new Exception("校验位报错!");
			}
		}
		return result;
	}
	
	//获取gis参数信息
	public String getGispara(String gjz) {
		String result="";
		try{
			FrmGispara frmGispara = this.gSysparaCodeDao.getGispara(gjz);
			if(frmGispara!=null){
				result = frmGispara.getMrz();
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return result;
		
		
		
	}
}
