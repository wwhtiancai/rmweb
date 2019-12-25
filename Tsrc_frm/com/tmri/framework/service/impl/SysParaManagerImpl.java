package com.tmri.framework.service.impl;

import java.util.List;
import java.util.Vector;

import com.tmri.framework.listener.SysParaListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmri.framework.bean.FrmNoworkday;
import com.tmri.framework.bean.Log;
import com.tmri.framework.bean.SysInitObj;
import com.tmri.framework.bean.SysResult;
import com.tmri.framework.dao.CodeDao;
import com.tmri.framework.dao.SysParaDao;
import com.tmri.framework.dao.jdbc.FrmDataObjDaoJdbc;
import com.tmri.framework.service.SysParaManager;
import com.tmri.pub.service.SysService;
import com.tmri.share.frm.bean.Code;
import com.tmri.share.frm.bean.SysGroup;
import com.tmri.share.frm.bean.SysPara;
import com.tmri.share.frm.bean.SysparaValue;
import com.tmri.share.frm.dao.GSysparaCodeDao;
import com.tmri.share.frm.dao.SLogDao;
import com.tmri.share.frm.service.GSysparaCodeService;
import com.tmri.share.ora.bean.DbResult;

@Service
public class SysParaManagerImpl implements SysParaManager {
    @Autowired
    SysParaDao sysparaDao = null;

    @Autowired
    FrmDataObjDaoJdbc frmDataObjDao = null;

    @Autowired
    SysService sysService = null;

    @Autowired
    GSysparaCodeDao gSysparaCodeDao;

    @Autowired
    private SLogDao sLogDao;
    
    @Autowired
    private GSysparaCodeService gSysparaCodeService;

    @Autowired
    private List<SysParaListener> sysParaListeners;

    public SysResult saveSyspara(SysPara syspara, Log log) throws Exception {
        SysResult result = null;
        SysPara sysparatemp = gSysparaCodeDao.getSysPara(syspara.getXtlb(),
                syspara.getCslx(), syspara.getGjz());
        for (SysParaListener sysParaListener : sysParaListeners) {
            sysParaListener.before(syspara);
        }
        sysparatemp.setMrz(syspara.getMrz());
        result = frmDataObjDao.setOracleData(sysparatemp, 1);
        if (result.getFlag() == 0) {
            return result;
        }
        result = frmDataObjDao.setOracleData(log, 1);
        if (result.getFlag() == 0) {
            return result;
        }
        result = this.sysparaDao.saveSysPara();
        if (1 == result.getFlag()) {
            for (SysParaListener sysParaListener : sysParaListeners) {
                sysParaListener.after(syspara);
            }
        }
        return result;
    }

    public DbResult saveAnaSysParm(SysPara anaSysPara) throws Exception {
        DbResult result = null;
            result = this.sysparaDao.setOracleData("ana_setdata_pkg",
                    "Setana_Sys_Para", anaSysPara);
            if (result != null && result.getCode() == 1) {
                result = this.sysparaDao.save("Ana_Basic_Pkg.Saveana_Sys_Para");
            }
        return result;
    }

    public SysResult saveDepSysparaJb(SysPara syspara, Log log)
            throws Exception {
        SysResult result = null;
        SysPara sysparatemp = gSysparaCodeDao.getSysPara(syspara.getXtlb(),
                syspara.getCslx(), syspara.getGjz());
        sysparatemp.setXgjb(syspara.getXgjb());
        result = frmDataObjDao.setOracleData(sysparatemp, 1);
        if (result.getFlag() == 0) {
            return result;
        }
        result = frmDataObjDao.setOracleData(log, 1);
        if (result.getFlag() == 0) {
            return result;
        }

        result = this.sysparaDao.saveSysPara();

        sysparaDao.setSysParaToMem(sysparatemp);

        return result;
    }

    private String getSqzjcx(String sqzjcx, String ywlxs) {
        String resultString = "";
        if (ywlxs != null && !ywlxs.equals("")) {
            String[] sqywlxArr = ywlxs.split(",");
            for (int i = 0; i < sqywlxArr.length; i++) {
                int iPos1 = sqzjcx.indexOf("[" + sqywlxArr[i] + "]");
                int iPos2 = sqzjcx.indexOf("[/" + sqywlxArr[i] + "]");
                if (iPos1 >= 0 && iPos2 >= 0) {
                    resultString = resultString
                            + sqzjcx.substring(iPos1, iPos2
                                    + sqywlxArr[i].length() + 3);
                }
            }
        }
        return resultString;
    }

    public List getFgzrwhList(String qsrq, String jsrq, String ywlb) {
        return sysparaDao.getFgzrwhList(qsrq, jsrq, ywlb);
    }

    public SysResult saveNoworkday(FrmNoworkday noworkday, Log log)
            throws Exception {
        SysResult result = null;
        result = frmDataObjDao.setOracleData(noworkday, 1);
        if (result.getFlag() == 0) {
            return result;
        }
        result = frmDataObjDao.setOracleData(log, 1);
        if (result.getFlag() == 0) {
            return result;
        }
        result = this.frmDataObjDao
                .execCommProcedure("FRM_SYS_PKG.SaveNoworkday");

        return result;
    }

    public SysResult setInitNoworkday(FrmNoworkday noworkday, Log log)
            throws Exception {
        SysResult result = null;
        result = frmDataObjDao.setOracleData(noworkday, 1);
        if (result.getFlag() == 0) {
            return result;
        }
        result = frmDataObjDao.setOracleData(log, 1);
        if (result.getFlag() == 0) {
            return result;
        }
        result = this.frmDataObjDao
                .execCommProcedure("FRM_SYS_PKG.setInitNoworkday");

        return result;
    }


    public DbResult saveSysInit(SysInitObj sysInitObj) throws Exception {
        return sysparaDao.doSysInit(sysInitObj);
    }

    //修改 20140830
	public SysResult saveDeppara(SysparaValue sysparaValue,Log log) throws Exception{
		SysResult result=null;
		SysparaValue sysparaValue1 = null;
		result=frmDataObjDao.setOracleData(sysparaValue,1);
		if(result.getFlag()==0){
			return result;
		}
		result=frmDataObjDao.setOracleData(log,1);
		if(result.getFlag()==0){
			return result;
		}
		result=this.sysparaDao.saveDepPara();
		
		//如果是驾管授权业务类型，同步更新授权准驾车型
		if(sysparaValue.getXtlb().equals("02")&&sysparaValue.getGjz().equals("SQYWLX")){
			String sqglcx=gSysparaCodeService.getDeptParaValue(sysparaValue.getGlbm(),"02","SQGLCX");
			String sqglcxnew = getSqzjcx(sqglcx,sysparaValue.getCsz());
			if(!sqglcxnew.equals(sqglcx)){
				sysparaValue1 = new SysparaValue();
				sysparaValue1.setXtlb(sysparaValue.getXtlb());
				sysparaValue1.setGlbm(sysparaValue.getGlbm());
				sysparaValue1.setGjz("SQGLCX");
				sysparaValue1.setCsz(sqglcxnew);
				sysparaValue1.setCsbj(sysparaValue.getCsbj());
				result=frmDataObjDao.setOracleData(sysparaValue1,1);
				if(result.getFlag()==0){
					return result;
				}
				result=this.sysparaDao.saveDepParaNolog();
				sysparaDao.setSysParaValueToMem(sysparaValue1);
			}
		}

		sysparaDao.setSysParaValueToMem(sysparaValue);
		return result;
	}
	
	
    
    public List getDepparasShow(String xgjb,String gnlbString) {
    	return this.sysparaDao.getDepparasShow(xgjb, gnlbString);
    }
    
    
	public List getDepparasShow(String xgjb,String glbm,String gnlbString) throws Exception{
		List resultList=new Vector();
		List sysparaList=this.sysparaDao.getDepparasShow(xgjb,gnlbString);

		SysPara sysPara=null;
		for(int i=0;i<sysparaList.size();i++){
			sysPara=(SysPara)sysparaList.get(i);
			String paravalue=this.gSysparaCodeService.getDeptParaValue(glbm,sysPara.getXtlb(),sysPara.getGjz());
			if(paravalue==null){
				sysPara.setMrz("");
			}else{
				sysPara.setMrz(paravalue);
			}
		}
		setParaResultList(resultList,sysparaList);
		return resultList;
	}
	
	private void setParaResultList(List resultList, List sysparaList)
			throws Exception {
		SysPara sysPara = null;
		SysGroup sysGroup = null;
		String xtlbString = "";
		List tmpList = new Vector();
		String mc = "";

		for (int j = 0; j < sysparaList.size(); j++) {
			sysPara = (SysPara) sysparaList.get(j);
			if ((!sysPara.getXtlb().equals(xtlbString))
					&& (!xtlbString.equals(""))) {
				sysGroup = new SysGroup();

				Code code = this.gSysparaCodeDao.getCode("00", "0001", xtlbString);
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
		Code code = this.gSysparaCodeDao.getCode("00", "0001", xtlbString);
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
}
