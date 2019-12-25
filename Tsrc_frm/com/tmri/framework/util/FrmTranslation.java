package com.tmri.framework.util;

import java.util.List;

import com.tmri.framework.bean.FrmInformsetup;
import com.tmri.pub.service.SysService;
import com.tmri.share.frm.bean.SysUser;
import com.tmri.share.frm.service.GDepartmentService;
import com.tmri.share.frm.service.GSysparaCodeService;

/**
 * bean���봦��
 * 
 */
public class FrmTranslation{
	private static SysService sysService;
	private static FrmTranslation frmTranslation;
	
	private GSysparaCodeService gSysparaCodeService;
	private GDepartmentService gDepartmentService;
	
	public void setgSysparaCodeService(GSysparaCodeService gSysparaCodeService) {
		this.gSysparaCodeService = gSysparaCodeService;
	}
	public void setgDepartmentService(GDepartmentService gDepartmentService) {
		this.gDepartmentService = gDepartmentService;
	}
	private FrmTranslation(){

	}
	public static FrmTranslation getInstance(SysService sysService1){
		if(frmTranslation==null){
			frmTranslation=new FrmTranslation();
		}
		frmTranslation.sysService=sysService1;
		return frmTranslation;
	}
	public void transFrminformSetup(FrmInformsetup frmInformsetup,String showType){
		if(frmInformsetup.getYhdh()!=null&&!frmInformsetup.getYhdh().equals("")){
			String yhdhmc = "";
			String[] yhdhs = frmInformsetup.getYhdh().split(",");
			for(int i = 0;i<yhdhs.length;i++){
				SysUser sysUser = sysService.getYhxm(yhdhs[i]);
				if(sysUser!=null){
					yhdhmc += sysUser.getXm() + ",";
				}
			}
			if(!yhdhmc.equals("")){
				yhdhmc = yhdhmc.substring(0,yhdhmc.length() - 1);
			}
			frmInformsetup.setYhdhmc(yhdhmc);
		}
		//1-ÿ�� 2-ÿ���� 3-ÿ�� 4-ÿ�� 5-ÿ��
		if(frmInformsetup.getTxzq().equals("1")){
			frmInformsetup.setTxsjmc("ÿ��" + frmInformsetup.getTxsj());
		}else if(frmInformsetup.getTxzq().equals("2")){
			frmInformsetup.setTxsjmc("ÿ���ȵ�" + frmInformsetup.getTxsj() + "��");
		}else if(frmInformsetup.getTxzq().equals("3")){
			frmInformsetup.setTxsjmc("ÿ�µ�" + frmInformsetup.getTxsj() + "��");
		}else if(frmInformsetup.getTxzq().equals("4")){
			if(frmInformsetup.getTxsj().equals("7")){
				frmInformsetup.setTxsjmc("ÿ��������");	
			}else{
				frmInformsetup.setTxsjmc("ÿ������" + frmInformsetup.getTxsj());	
			}
		}else if(frmInformsetup.getTxzq().equals("5")){
			frmInformsetup.setTxsjmc("ÿ��");	
		}
		try {
			frmInformsetup.setBmmc(gDepartmentService.getDepartmentName(frmInformsetup.getGlbm()));
		} catch (Exception e) {
			frmInformsetup.setBmmc(frmInformsetup.getGlbm());
		}
		frmInformsetup.setXxmc(gSysparaCodeService.getInformCodeMc(frmInformsetup.getXxdm()));
		if(frmInformsetup.getSctxsj()==null){
			frmInformsetup.setSctxsj("");
		}else{
			if(frmInformsetup.getSctxsj().length()>18)
			frmInformsetup.setSctxsj(frmInformsetup.getSctxsj().substring(0,19));
		}
	}
	public void transFrminformSetupList(List list,String showType){
		if(list==null) return;
		FrmInformsetup frmInformsetup=null;
		for(int i=0;i<list.size();i++){
			frmInformsetup=(FrmInformsetup)list.get(i);
			transFrminformSetup(frmInformsetup,showType);
		}
	}
}
